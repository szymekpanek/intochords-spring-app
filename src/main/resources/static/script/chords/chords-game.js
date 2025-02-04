const majorChordsMap = {
    "C": ["E", "G"], "C#": ["E#", "G#"], "Db": ["F", "Ab"], "D": ["F#", "A"],
    "D#": ["G", "A#"], "Eb": ["G", "Bb"], "E": ["G#", "B"], "F": ["A", "C"],
    "F#": ["A#", "C#"], "Gb": ["Bb", "Db"], "G": ["B", "D"], "G#": ["B#", "D#"],
    "Ab": ["C", "Eb"], "A": ["C#", "E"], "A#": ["D", "F"], "Bb": ["D", "F"],
    "B": ["D#", "F#"]
};

const minorChordsMap = {
    "C": ["Eb", "G"], "C#": ["E", "G#"], "Db": ["Fb", "Ab"], "D": ["F", "A"],
    "D#": ["F#", "A#"], "Eb": ["Gb", "Bb"], "E": ["G", "B"], "F": ["Ab", "C"],
    "F#": ["A", "C#"], "Gb": ["Bbb", "Db"], "G": ["Bb", "D"], "G#": ["B", "D#"],
    "Ab": ["Cb", "Eb"], "A": ["C", "E"], "A#": ["C#", "F"], "Bb": ["Db", "F"],
    "B": ["D", "F#"], "B#": ["D#", "F##"]
};

document.addEventListener("DOMContentLoaded", function () {
    let isMajor = true;
    let correctMatrix = generateChordsMatrix(isMajor);

    document.getElementById("major-button").addEventListener("click", function () {
        isMajor = true;
        updateChordLabels(isMajor);
        correctMatrix = generateChordsMatrix(isMajor);
    });

    document.getElementById("minor-button").addEventListener("click", function () {
        isMajor = false;
        updateChordLabels(isMajor);
        correctMatrix = generateChordsMatrix(isMajor);
    });

    document.getElementById("check-button").addEventListener("click", function () {
        let { correctCount, incorrectCount } = checkAnswers(correctMatrix, isMajor);
        sendGameResults(correctCount, incorrectCount); // Wysyłanie wyników do bazy
    });

    document.getElementById("next-chord-button").addEventListener("click", function () {
        correctMatrix = generateChordsMatrix(isMajor);
        resetGameBoard();
        toggleNextButton(false);
    });
});

/**
 * Generuje nową tablicę akordów
 */
function generateChordsMatrix(isMajor) {
    const notes = Object.keys(isMajor ? majorChordsMap : minorChordsMap);
    let rootNote = notes[Math.floor(Math.random() * notes.length)];
    let matrix = [
        [rootNote, "", ""],
        ["", rootNote, ""],
        ["", "", rootNote]
    ];

    fillLockedCells(matrix);
    return matrix;
}

/**
 * Wypełnia pola po przekątnej i blokuje je
 */
function fillLockedCells(matrix) {
    for (let i = 0; i < 3; i++) {
        let cell = document.getElementById(`cell-${i}-${i}`);
        cell.value = matrix[i][i];
        cell.readOnly = true;
        cell.classList.add("bg-gray-200");
    }
}

/**
 * Sprawdza odpowiedzi użytkownika i podświetla pola
 */
function checkAnswers(correctMatrix, isMajor) {
    let selectedMap = isMajor ? majorChordsMap : minorChordsMap;
    let isFullyCorrect = true; // Cała tabela poprawna?

    for (let i = 0; i < 3; i++) {
        let rootNote = correctMatrix[i][i];
        let correctValues = selectedMap[rootNote];

        for (let j = 0; j < 3; j++) {
            if (i === j) continue;

            let inputCell = document.getElementById(`cell-${i}-${j}`);
            let userAnswer = inputCell.value.trim().toUpperCase(); // Normalizacja do wielkich liter

            if (correctValues.includes(userAnswer)) {
                inputCell.classList.add("correct");
                inputCell.classList.remove("incorrect");
            } else {
                inputCell.classList.add("incorrect");
                inputCell.classList.remove("correct");
                isFullyCorrect = false; // Co najmniej jedno pole błędne
            }
        }
    }

    let resultMessage = document.getElementById("result-message");
    resultMessage.textContent = isFullyCorrect ? "✅ Correct!" : "❌ Incorrect!";
    resultMessage.classList.remove("hidden");

    toggleNextButton(true);

    sendGameResults(isFullyCorrect ? 1 : 0, isFullyCorrect ? 0 : 1); // Jeśli poprawne = 1, jeśli błędne = 1
}


/**
 * Wysyła wyniki gry do serwera
 */
function sendGameResults(correct, incorrect) {
    fetch("/api/chords-game/save-game-results", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `correct=${correct}&incorrect=${incorrect}`
    });
}

/**
 * Przełącza widoczność przycisków Submit i Next Chord
 */
function toggleNextButton(show) {
    document.getElementById("next-chord-button").classList.toggle("hidden", !show);
    document.getElementById("check-button").classList.toggle("hidden", show);
}

/**
 * Resetuje planszę gry po wylosowaniu nowego akordu
 */
function resetGameBoard() {
    document.querySelectorAll(".chord-cell").forEach(cell => {
        if (!cell.classList.contains("locked")) {
            cell.value = "";
            cell.classList.remove("correct", "incorrect");
        }
    });

    document.getElementById("result-message").classList.add("hidden");
}
/**
 * Aktualizuje oznaczenia kolumn w zależności od wybranego typu akordu
 */
/**
 * Aktualizuje oznaczenia kolumn w zależności od wybranego typu akordu
 * oraz podświetla aktywny przycisk trybu akordu
 */
function updateChordLabels(isMajor) {
    document.getElementById("label-1").textContent = "1"; // Pryma zawsze "1"
    document.getElementById("label-3").textContent = isMajor ? "3" : "3>"; // Tercja
    document.getElementById("label-5").textContent = "5"; // Kwinta

    // Zmiana wizualna podświetlenia trybu
    const majorButton = document.getElementById("major-button");
    const minorButton = document.getElementById("minor-button");

    if (isMajor) {
        majorButton.classList.add("bg-blue-500", "text-white");
        majorButton.classList.remove("bg-gray-500");

        minorButton.classList.add("bg-gray-500");
        minorButton.classList.remove("bg-blue-500", "text-white");
    } else {
        minorButton.classList.add("bg-blue-500", "text-white");
        minorButton.classList.remove("bg-gray-500");

        majorButton.classList.add("bg-gray-500");
        majorButton.classList.remove("bg-blue-500", "text-white");
    }
}
