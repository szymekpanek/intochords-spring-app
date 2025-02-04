document.addEventListener("DOMContentLoaded", function () {
    const apiUrl = "/api/user-panel"; // Endpoint API
    const chartCanvas = document.getElementById("gameStatsChart").getContext("2d");
    const gameTitleElement = document.getElementById("gameTitle"); // Element do wyświetlenia nazwy gry

    function fetchUserStats() {
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Błąd pobierania danych: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                if (!data.gameStats || data.gameStats.length === 0) {
                    console.warn("Brak danych do wyświetlenia.");
                    displayNoDataMessage();
                } else {
                    processAndGenerateChart(data.gameStats);
                    displayGameTitle(data.gameStats);
                }
            })
            .catch(error => console.error("Błąd pobierania danych:", error));
    }

    function processAndGenerateChart(gameStats) {
        const aggregatedStats = {};

        gameStats.forEach(stat => {
            const dateKey = stat.gameDate.split("T")[0]; // Pobranie tylko daty (YYYY-MM-DD)
            if (!aggregatedStats[dateKey]) {
                aggregatedStats[dateKey] = { correct: 0, incorrect: 0 };
            }
            aggregatedStats[dateKey].correct += stat.correctAnswer;
            aggregatedStats[dateKey].incorrect += stat.incorrectAnswer;
        });

        const labels = Object.keys(aggregatedStats);
        const correctAnswers = labels.map(date => aggregatedStats[date].correct);
        const incorrectAnswers = labels.map(date => aggregatedStats[date].incorrect);

        generateChart(labels, correctAnswers, incorrectAnswers);
    }

    function generateChart(labels, correctAnswers, incorrectAnswers) {
        new Chart(chartCanvas, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Poprawne odpowiedzi',
                        data: correctAnswers,
                        backgroundColor: '#4CAF50', // Zielony
                        borderWidth: 1,
                        barPercentage: 0.5, // Szerokość słupków (0.5 to węższe słupki)
                        categoryPercentage: 0.7 // Odstępy między kategoriami
                    },
                    {
                        label: 'Niepoprawne odpowiedzi',
                        data: incorrectAnswers,
                        backgroundColor: '#F44336', // Czerwony
                        borderWidth: 1,
                        barPercentage: 0.5, // Szerokość słupków (mniejsza szerokość)
                        categoryPercentage: 0.7 // Odstępy między kategoriami
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        title: { display: true, text: 'Data gry' },
                        grid: { display: false }
                    },
                    y: {
                        title: { display: true, text: 'Liczba odpowiedzi' },
                        beginAtZero: true
                    }
                }
            }
        });
    }

    function displayGameTitle(gameStats) {
        if (gameStats.length > 0) {
            const gameId = gameStats[0].gameId; // Pobranie ID gry z pierwszego rekordu
            let gameName = "Nieznana gra";

            if (gameId === 1) {
                gameName = "Interval Game";
            } else if (gameId === 2) {
                gameName = "Chords Game";
            }

            gameTitleElement.textContent = `${gameName}`;
        }
    }

    function displayNoDataMessage() {
        document.getElementById("gameStatsChart").parentElement.innerHTML =
            '<p class="text-center text-gray-500 font-semibold">Brak danych do wyświetlenia.</p>';
    }

    fetchUserStats();
});
