document.addEventListener("DOMContentLoaded", function () {
    const gameId = 2; // ID gry (Chords Game)
    const chartCanvas = document.getElementById("gameStatsChart").getContext("2d");

    function fetchUserStats() {
        fetch(`/api/user-stats/${gameId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Błąd pobierania danych: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                if (!Array.isArray(data) || data.length === 0) {
                    console.warn("Brak danych do wyświetlenia.");
                    displayNoDataMessage();
                } else {
                    generateChart(data);
                }
            })
            .catch(error => console.error("Błąd pobierania danych:", error));
    }

    function displayNoDataMessage() {
        document.getElementById("gameStatsChart").parentElement.innerHTML =
            '<p class="text-center text-gray-500 font-semibold">Brak danych do wyświetlenia.</p>';
    }

    function generateChart(data) {
        const labels = data.map(item => item.gameDate);
        const correctAnswers = data.map(item => item.correct);
        const incorrectAnswers = data.map(item => item.incorrect);

        new Chart(chartCanvas, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Poprawne odpowiedzi',
                        data: correctAnswers,
                        backgroundColor: '#4CAF50',
                        borderWidth: 1
                    },
                    {
                        label: 'Niepoprawne odpowiedzi',
                        data: incorrectAnswers,
                        backgroundColor: '#F44336',
                        borderWidth: 1
                    }
                ]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: { display: true, text: 'Data' }
                    },
                    y: {
                        title: { display: true, text: 'Liczba odpowiedzi' },
                        beginAtZero: true
                    }
                }
            }
        });
    }

    fetchUserStats();
});
