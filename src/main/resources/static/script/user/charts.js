document.addEventListener('DOMContentLoaded', function () {
    var incGames = parseInt(/* użyj th:text="${user.interval_answer_inc}" */);
    var decGames = parseInt(/* użyj th:text="${user.interval_answer_dec}" */);

    var ctx = document.getElementById('gameStatsChart').getContext('2d');
    var gameStatsChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: ['Games Won', 'Games Lost'],
            datasets: [{
                label: 'Game Statistics',
                data: [incGames, decGames],
                backgroundColor: [
                    '#68d391', // kolor dla wygranych gier
                    '#fc8181'  // kolor dla przegranych gier
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            tooltips: {
                callbacks: {
                    label: function(tooltipItem, data) {
                        var dataset = data.datasets[tooltipItem.datasetIndex];
                        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
                            return previousValue + currentValue;
                        });
                        var currentValue = dataset.data[tooltipItem.index];
                        var percent = Math.round((currentValue / total) * 100);
                        return `${data.labels[tooltipItem.index]}: ${percent}%`;
                    }
                }
            }
        }
    });
});