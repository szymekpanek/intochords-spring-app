

const chordOptionsMinor = [
    [["C", "EB", "G"], ["AB", "C", "EB"], ["F", "AB", "C"]],
    [["D", "F", "A"], ["BB", "D", "F"], ["G", "BB", "D"]],
    // [["D#","",""],["","D#",""],["","","D#"]],
    [["E", "G", "B"], ["C", "EB", "G"], ["A", "C", "E"]],
    [["F", "AB", "C"], ["DB", "F", "AB"], ["BB", "DB", "F"]],
    // [["F#","",""],["","F#",""],["","","F#"]],
    [["G", "BB", "D"], ["EB", "G", "BB"], ["C", "EB", "G"]],
    // [["","",""],["","",""],["","",""]],
    [["A", "C", "E"], ["F", "AB", "C"], ["D", "F", "A"]],
    // [["","",""],["","",""],["","",""]],
    [["B", "D", "F#"], ["G", "BB", "D"], ["E", "G", "B"]]
];

const chordOptionsMajor = [
    // [["CB","EB","GB"],["","",""],["","",""]],

    [["C", "E", "G"], ["AB", "C", "EB"], ["F", "A", "C"]],

    // [["C#", "F", "G#"], ["A", "C#", "E"], ["","","C#"]], //dokoncz
    [["D", "F#", "A"], ["BB", "D", "F"], ["G", "B", "D"]],

    // [["D#","",""],["B", "D#", "F#"],["","",""]],

    [["E", "G#", "B"], ["C", "E", "G"], ["A", "C#", "E"]],
    [["F", "A", "C"], ["DB", "F", "AB"], ["BB", "D", "F"]],
    [["G", "B", "D"], ["EB", "G", "BB"], ["C", "E", "G"]],
    [["A", "C#", "E"], ["F", "A", "C"], ["D", "F#", "A"]],
    [["B", "D#", "F#"], ["G", "B", "D"], ["E", "G#", "B"]]
];






document.addEventListener("DOMContentLoaded", function () {
    let isMajor = true; // Domyślnie akordy durowe
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
        let results = checkAnswers(correctMatrix);
        if (results) {
            sendGameResults(results.isFullyCorrect);
        }
    });


    document.getElementById("next-chord-button").addEventListener("click", function () {
        correctMatrix = generateChordsMatrix(isMajor);
        resetGameBoard();
        toggleNextButton(false);
    });
});

/**
 * Losuje jeden zestaw akordów z odpowiedniej mapy (durowe lub molowe)
 */
function generateChordsMatrix(isMajor) {
    console.log("🎹 Losowanie nowego akordu...");
    let chordOptions = isMajor ? chordOptionsMajor : chordOptionsMinor;
    let randomIndex = Math.floor(Math.random() * chordOptions.length);
    let matrix = chordOptions[randomIndex];

    console.log(`✅ Wygenerowana tablica akordów (${isMajor ? "DUROWE" : "MOLOWE"}):`);
    console.table(matrix);

    fillLockedCells(matrix);
    return matrix;
}

/**
 * Wypełnia pola na przekątnej poprawnymi wartościami
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
 * Sprawdza poprawność odpowiedzi użytkownika
 */
function checkAnswers(correctMatrix) {
    console.log("🔍 Sprawdzanie odpowiedzi...");

    if (!correctMatrix) {
        console.error("❌ Błąd: correctMatrix jest niezdefiniowane!");
        return null;
    }

    let isFullyCorrect = true;
    let correctCount = 0;
    let incorrectCount = 0;

    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (i === j) continue; // Pomijamy przekątną (już jest wypełniona)

            let inputCell = document.getElementById(`cell-${i}-${j}`);
            let userAnswer = inputCell.value.trim().toUpperCase();
            let correctAnswer = correctMatrix[i][j];

            if (userAnswer === correctAnswer) {
                inputCell.classList.add("correct");
                inputCell.classList.remove("incorrect");
                correctCount++;
            } else {
                inputCell.classList.add("incorrect");
                inputCell.classList.remove("correct");
                isFullyCorrect = false;
                incorrectCount++;
            }
        }
    }

    let resultMessage = document.getElementById("result-message");
    resultMessage.textContent = isFullyCorrect ? "✅ Correct!" : "❌ Incorrect!";
    resultMessage.classList.remove("hidden");

    toggleNextButton(true);

    return { isFullyCorrect };
}

/**
 * Wysyła wynik gry do serwera
 */
function sendGameResults(isFullyCorrect) {
    let correctAns;
    let incorrectAns;

    if (isFullyCorrect){
        correctAns = 1;
        incorrectAns = 0;
    }else {
        correctAns = 0;
        incorrectAns = 1;
    }

    const body = `correct=${correctAns}&incorrect=${incorrectAns}`;

    fetch("/api/chords-game/save-game-results", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: body
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Błąd podczas zapisywania wyników.");
            }
            return response.text();
        })
        .then(data => {
            console.log("📡 Wyniki gry zapisane:", data);
        })
        .catch(error => {
            console.error("❌ Błąd podczas zapisu wyników:", error);
        });
}

/**
 * Przełącza widoczność przycisków "Sprawdź" i "Nowy akord"
 */
function toggleNextButton(show) {
    document.getElementById("next-chord-button").classList.toggle("hidden", !show);
    document.getElementById("check-button").classList.toggle("hidden", show);
}

/**
 * Resetuje planszę gry
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
 * Aktualizuje etykiety akordów i podświetla wybrany tryb
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




// const majorChordsMap = new Map([
//     ["C", ["E", "G"]],
//     ["C#", ["E#", "G#"]],
//     ["DB", ["F", "AB"]],
//     ["D", ["F#", "A"]],
//     ["D#", ["G", "A#"]],
//     ["EB", ["G", "BB"]],
//     ["E", ["G#", "B"]],
//     ["F", ["A", "C"]],
//     ["F#", ["A#", "C#"]],
//     ["GB", ["BB", "DB"]],
//     ["G", ["B", "D"]],
//     ["G#", ["B#", "D#"]],
//     ["AB", ["C", "EB"]],
//     ["A", ["C#", "E"]],
//     ["A#", ["D", "F"]],
//     ["BB", ["D", "F"]],
//     ["B", ["EB", "GB"]],
//     ["B", ["DB", "GB"]],
//     ["D", ["GB", "A"]],
//     ["B", ["D#", "F#"]]
// ]);

// const minorChordsMap = {
//     "C": ["EB", "G"], "C#": ["E", "G#"], "DB": ["FB", "AB"], "D": ["F", "A"],
//     "D#": ["F#", "A#"], "EB": ["GB", "BB"], "E": ["G", "B"], "F": ["AB", "C"],
//     "F#": ["A", "C#"], "GB": ["BBB", "DB"], "G": ["BB", "D"], "G#": ["B", "D#"],
//     "AB": ["CB", "EB"], "A": ["C", "E"], "A#": ["C#", "F"], "BB": ["DB", "F"],
//     "B": ["D", "F#"], "B#": ["D#", "F##"]
// };