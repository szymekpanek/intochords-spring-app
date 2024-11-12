// document.addEventListener('DOMContentLoaded', function () {
//     const gameSessions = /* [[${gameSessions}]] */;
//     const gameNames = Array.from(new Set(gameSessions.map(session => session.gameName)));
//
//     const datasets = gameNames.map(name => {
//         const sessionsForGame = gameSessions.filter(session => session.gameName === name);
//         const dates = sessionsForGame.map(session => session.timestamp);
//         const correctCounts = sessionsForGame.map(session => session.isCorrect ? 1 : 0);
//         const incorrectCounts = sessionsForGame.map(session => session.isCorrect ? 0 : 1);
//
//         return {
//             label: `${name} - Correct`,
//             data: correctCounts,
//             borderColor: 'green',
//             fill: false
//         }, {
//             label: `${name} - Incorrect`,
//             data: incorrectCounts,
//             borderColor: 'red',
//             fill: false
//         };
//     });
//
//     const ctx = document.getElementById('gameStatsChart').getContext('2d');
//     new Chart(ctx, {
//         type: 'line',
//         data: {
//             labels: dates,
//             datasets: datasets
//         },
//         options: {
//             responsive: true,
//             scales: {
//                 x: {type: 'time', time: {unit: 'day'}},
//                 y: {beginAtZero: true}
//             }
//         }
//     });
// });
//
