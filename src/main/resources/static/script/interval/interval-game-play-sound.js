document.addEventListener("DOMContentLoaded", function () {
    let gameSessionStarted = false;
    let correctInterval = null;

    function startGameSession() {
        if (!gameSessionStarted) {
            gameSessionStarted = true;
            correctInterval = document.getElementById("correctInterval")?.value; // Pobranie poprawnej odpowiedzi
            console.log("Game session started. Correct interval: " + correctInterval);
        }
    }

    // Rozpocznij sesję gry po wejściu na stronę
    startGameSession();

    // Obsługa przycisku "Play"
    document.getElementById("playSounds")?.addEventListener("click", function () {
        document.getElementById("firstSound").play();
        setTimeout(() => document.getElementById("secondSound").play(), 1000);
    });

    // Obsługa wyboru interwału
    document.querySelectorAll(".interval-button").forEach(button => {
        button.addEventListener("click", function () {
            document.getElementById("userAnswer").value = this.value;
            document.getElementById("submitAnswer").disabled = false;
        });
    });

    // Obsługa sprawdzania odpowiedzi
    document.getElementById("submitAnswer")?.addEventListener("click", function (event) {
        event.preventDefault(); // Zapobiega domyślnemu wysłaniu formularza
        const userAnswer = document.getElementById("userAnswer").value;

        if (!userAnswer) {
            alert("Please select an interval first.");
            return;
        }

        const resultMessage = document.getElementById("resultMessage");
        if (userAnswer === correctInterval) {
            resultMessage.innerHTML = '<p class="bg-green-500 p-4 text-white text-center rounded-lg">Correct!</p>';
        } else {
            resultMessage.innerHTML = '<p class="bg-red-500 p-4 text-white text-center rounded-lg">Incorrect!</p>';
        }
    });
});
