document.addEventListener("DOMContentLoaded", () => {
    const classId = getClassIdFromUrl();

    if (classId) {
        fetchClassData(classId);
    } else {
        console.error("❌ Błąd: Brak ID klasy w URL.");
    }
});

function getClassIdFromUrl() {
    const urlParts = window.location.pathname.split("/");
    const lastPart = urlParts[urlParts.length - 1];

    if (!isNaN(lastPart) && lastPart.trim() !== "") {
        return lastPart; // Jeśli to liczba, zwracamy jako ID klasy
    } else {
        console.error("❌ Błąd: Brak poprawnego ID klasy w URL!");
        return null;
    }
}

function fetchClassData(classId) {
    const apiUrl = `/api/class/${classId}`;
    console.log(`📡 Pobieranie danych z API: ${apiUrl}`);

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.error) {
                console.error("❌ Błąd API: ", data.error);
                return;
            }

            document.getElementById("class-name").textContent = data.className;
            updateGameStatsTable(data.students);
        })
        .catch(error => console.error("❌ Błąd pobierania danych: ", error));
}

function updateGameStatsTable(students) {
    const tableBody = document.getElementById("game-stats-body");
    const noDataMessage = document.getElementById("no-data-message");
    tableBody.innerHTML = ""; // Czyścimy tabelę

    let hasData = false;
    const groupedStats = new Map();

    students.forEach(student => {
        student.gameStats.forEach(stat => {
            const gameDate = stat.gameDate.split("T")[0]; // Pobieramy tylko część `YYYY-MM-DD`
            const key = `${student.studentName}-${stat.gameName}-${gameDate}`;

            if (!groupedStats.has(key)) {
                groupedStats.set(key, {
                    userName: student.studentName,
                    gameName: stat.gameName,
                    gameDate: gameDate,
                    correctSum: 0,
                    incorrectSum: 0
                });
            }

            // Sumujemy poprawne i błędne odpowiedzi
            const existingEntry = groupedStats.get(key);
            existingEntry.correctSum += stat.correctAnswer;
            existingEntry.incorrectSum += stat.incorrectAnswer;
        });
    });

    // Teraz dodajemy zagregowane wyniki do tabeli
    groupedStats.forEach(stat => {
        hasData = true;

        const row = document.createElement("tr");
        row.classList.add("hover:bg-gray-50", "transition", "duration-200");

        row.innerHTML = `
            <td class="px-6 py-4 border border-gray-300 font-medium">${stat.userName}</td>
            <td class="px-6 py-4 border border-gray-300">${stat.gameName}</td>
            <td class="px-6 py-4 border border-gray-300 text-center">${stat.gameDate}</td>
            <td class="px-6 py-4 border border-gray-300 text-center font-bold text-green-600">${stat.correctSum}</td>
            <td class="px-6 py-4 border border-gray-300 text-center font-bold text-red-600">${stat.incorrectSum}</td>
        `;

        tableBody.appendChild(row);
    });

    // Jeśli brak danych, pokaż komunikat
    noDataMessage.classList.toggle("hidden", hasData);
}


function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString("pl-PL", { year: "numeric", month: "2-digit", day: "2-digit" }) +
        " " +
        date.toLocaleTimeString("pl-PL", { hour: "2-digit", minute: "2-digit" });
}
