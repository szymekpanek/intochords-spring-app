document.addEventListener("DOMContentLoaded", function () {
    const apiUrl = "/api/user-panel";

    const intervalChartCanvas = document.getElementById("intervalGameStatsChart").getContext("2d");
    const chordsChartCanvas = document.getElementById("chordsGameStatsChart").getContext("2d");

    document.addEventListener("DOMContentLoaded", function () {
        const apiUrl = "/api/user-panel";

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error fetching data: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                if (data.user) {
                    document.getElementById("user-name").textContent = data.user.name;
                    document.getElementById("user-surname").textContent = data.user.surname;
                    document.getElementById("user-login").textContent = data.user.login;
                    document.getElementById("user-class").textContent = data.user.className;
                }
            })
            .catch(error => console.error("Błąd pobierania danych:", error));
    });

    function fetchUserStats() {
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error fetching data: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                if (!data.gameStats || data.gameStats.length === 0) {
                    console.warn("No data to show");
                    displayNoDataMessage();
                } else {
                    const intervalStats = data.gameStats.filter(stat => stat.gameId === 1);
                    const chordsStats = data.gameStats.filter(stat => stat.gameId === 2);

                    if (intervalStats.length > 0) {
                        processAndGenerateChart(intervalStats, intervalChartCanvas, "Interval Game");
                    } else {
                        displayNoDataMessage("intervalGameStatsChart");
                    }

                    if (chordsStats.length > 0) {
                        processAndGenerateChart(chordsStats, chordsChartCanvas, "Chords Game");
                    } else {
                        displayNoDataMessage("chordsGameStatsChart");
                    }
                }
            })
            .catch(error => console.error("Data download error:", error));
    }

    function processAndGenerateChart(gameStats, canvas, gameTitle) {
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

        generateChart(labels, correctAnswers, incorrectAnswers, canvas, gameTitle);
    }

    function generateChart(labels, correctAnswers, incorrectAnswers, canvas, gameTitle) {
        new Chart(canvas, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Correct Answers:',
                        data: correctAnswers,
                        backgroundColor: '#4CAF50', // Zielony
                        borderWidth: 1,
                        barPercentage: 0.5,
                        categoryPercentage: 0.7
                    },
                    {
                        label: 'Incorrect Answers',
                        data: incorrectAnswers,
                        backgroundColor: '#F44336', // Czerwony
                        borderWidth: 1,
                        barPercentage: 0.5,
                        categoryPercentage: 0.7
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    x: {
                        title: { display: true, text: 'Game date' },
                        grid: { display: false }
                    },
                    y: {
                        title: { display: true, text: 'Number of answers' },
                        beginAtZero: true
                    }
                }
            }
        });
    }

    function displayNoDataMessage(canvasId) {
        document.getElementById(canvasId).parentElement.innerHTML =
            '<p class="text-center text-gray-500 font-semibold">No data to display.</p>';
    }

    fetchUserStats();
});
