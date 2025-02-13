// ✅ Używamy Map() zamiast zwykłego obiektu
const majorChordsMap = new Map([
    ["C", ["E", "G"]],
    ["C#", ["E#", "G#"]],
    ["DB", ["F", "AB"]],
    ["D", ["F#", "A"]],
    ["D#", ["G", "A#"]],
    ["EB", ["G", "BB"]],
    ["E", ["G#", "B"]],
    ["F", ["A", "C"]],
    ["F#", ["A#", "C#"]],
    ["GB", ["BB", "DB"]],
    ["G", ["B", "D"]],
    ["G#", ["B#", "D#"]],
    ["AB", ["C", "EB"]],
    ["A", ["C#", "E"]],
    ["A#", ["D", "F"]],
    ["BB", ["D", "F"]],
    ["B", ["EB", "GB"]],
    ["B", ["DB", "GB"]],
    ["D", ["GB", "A"]],
    ["B", ["D#", "F#"]]
]);

/**
 * ✅ Funkcja zwracająca poprawny akord dla danego dźwięku
 * Jeśli dźwięk nie istnieje w Map(), zwracamy domyślny akord [X, X]
 */
function getChord(rootNote) {
    if (majorChordsMap.has(rootNote)) {
        return majorChordsMap.get(rootNote);
    } else {
        console.warn(`⚠️ Akord dla ${rootNote} nie istnieje! Generujemy zastępczy.`);
        return ["X", "X"]; // Zastępczy akord, jeśli go nie ma w mapie
    }
}


const minorChordsMap = {
    "C": ["EB", "G"], "C#": ["E", "G#"], "DB": ["FB", "AB"], "D": ["F", "A"],
    "D#": ["F#", "A#"], "EB": ["GB", "BB"], "E": ["G", "B"], "F": ["AB", "C"],
    "F#": ["A", "C#"], "GB": ["BBB", "DB"], "G": ["BB", "D"], "G#": ["B", "D#"],
    "AB": ["CB", "EB"], "A": ["C", "E"], "A#": ["C#", "F"], "BB": ["DB", "F"],
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
        let results = checkAnswers(correctMatrix, isMajor);

        if (results) {
            let {correctCount, incorrectCount} = results;
            sendGameResults(correctCount, incorrectCount);
        } else {
            console.error("❌ Błąd: checkAnswers zwróciło undefined!");
        }
    });

    document.getElementById("next-chord-button").addEventListener("click", function () {
        correctMatrix = generateChordsMatrix(isMajor);
        resetGameBoard();
        toggleNextButton(false);
    });
});

/*
 * Generuje tablicę akordów na podstawie jednego dźwięku na przekątnej
 */
function generateChordsMatrix(isMajor) {
    console.log("🎹 Generowanie nowej tablicy akordów...");

    const chordMap = isMajor ? majorChordsMap : minorChordsMap;
    const notes = Array.from(chordMap.keys()); // Pobieramy dostępne klucze
    let diagonalNote = notes[Math.floor(Math.random() * notes.length)];

    let matrix = [
        [diagonalNote, "", ""],
        ["", diagonalNote, ""],
        ["", "", diagonalNote]
    ];

    console.log(`🎵 Wylosowany dźwięk na przekątnej: ${diagonalNote}`);

    // Pobieramy akordy dla każdego indeksu
    let chords = [
        getChord(diagonalNote), // Akord dla indeksu 0
        getChord(diagonalNote), // Akord dla indeksu 1
        getChord(diagonalNote)  // Akord dla indeksu 2
    ];

    // Uzupełniamy macierz znalezionymi akordami
    if (chords[0]) {
        matrix[0][1] = chords[0][0];
        matrix[0][2] = chords[0][1];
    }
    if (chords[1]) {
        matrix[1][0] = chords[1][0];
        matrix[1][2] = chords[1][1];
    }
    if (chords[2]) {
        matrix[2][0] = chords[2][0];
        matrix[2][1] = chords[2][1];
    }

    console.log("✅ Poprawnie wygenerowana tablica akordów:");
    console.table(matrix);

    fillLockedCells(matrix);
    return matrix;
}


function fillLockedCells(matrix) {
    for (let i = 0; i < 3; i++) {
        let cell = document.getElementById(`cell-${i}-${i}`);
        cell.value = matrix[i][i];
        cell.readOnly = true;
        cell.classList.add("bg-gray-200");
    }
}

function checkAnswers(correctMatrix, isMajor) {
    console.log("🔍 Sprawdzanie odpowiedzi...");

    // Tworzymy macierz poprawnych wartości w uppercase
    let normalizedCorrectMatrix = correctMatrix.map(row => row.map(note => note.toUpperCase()));

    console.log("📌 Oczekiwana tablica akordów (uppercase):");
    console.table(normalizedCorrectMatrix);

    let isFullyCorrect = true; // Czy cała tablica jest poprawna?

    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (i === j) continue; // Pomijamy przekątną (już jest wypełniona)

            let inputCell = document.getElementById(`cell-${i}-${j}`);
            let userAnswer = inputCell.value.trim().toUpperCase(); // Użytkownik wpisuje uppercase

            if (normalizedCorrectMatrix[i][j] === userAnswer) {
                inputCell.classList.add("correct");
                inputCell.classList.remove("incorrect");
            } else {
                inputCell.classList.add("incorrect");
                inputCell.classList.remove("correct");
                isFullyCorrect = false;
            }
        }
    }

    console.log("📝 Wynik końcowy: " + (isFullyCorrect ? "✅ Wszystko poprawne!" : "❌ Co najmniej jedno błędne"));

    let resultMessage = document.getElementById("result-message");
    resultMessage.textContent = isFullyCorrect ? "✅ Correct!" : "❌ Incorrect!";
    resultMessage.classList.remove("hidden");

    toggleNextButton(true);

    // ✅ Wysyłamy do bazy: 1 poprawna odpowiedź lub 1 błędna odpowiedź
    sendGameResults(isFullyCorrect ? 1 : 0, isFullyCorrect ? 0 : 1);
}

/**
 * Wysyła wyniki gry do serwera
 */
function sendGameResults(correct, incorrect) {
    fetch("/api/chords-game/save-game-results", {
        method: "POST",
        headers: {"Content-Type": "application/x-www-form-urlencoded"},
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