document.addEventListener("DOMContentLoaded", function () {
    let correctInterval;
    let firstSoundPath;
    let secondSoundPath;
    let selectedButton = null; // Przechowuje referencję do zaznaczonego przycisku

    function fetchIntervalData() {
        fetch("/api/interval-game/get-random")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Błąd serwera: " + response.status);
                }
                return response.json();
            })
            .then(data => {
                correctInterval = data.intervalName;
                firstSoundPath = data.firstSound;
                secondSoundPath = data.secondSound;

                document.getElementById("firstSound").src = firstSoundPath;
                document.getElementById("secondSound").src = secondSoundPath;
            })
            .catch(error => console.error("Błąd pobierania danych:", error));
    }

    function checkAnswer(userAnswer) {
        const resultMessage = document.getElementById("resultMessage");
        const resultText = document.getElementById("resultText");

        if (userAnswer === correctInterval) {
            resultText.textContent = "✅ Poprawna odpowiedź!";
            resultMessage.className = "mt-6 max-w-md mx-auto bg-green-500 p-4 text-white text-center rounded-lg";
            updateScore(1, 0);
        } else {
            resultText.textContent = `❌ Zła odpowiedź! Prawidłowy interwał to: ${correctInterval}`;
            resultMessage.className = "mt-6 max-w-md mx-auto bg-red-500 p-4 text-white text-center rounded-lg";
            updateScore(0, 1);
        }

        resultMessage.style.display = "block";
        setTimeout(() => {
            resultMessage.style.display = "none";
            fetchIntervalData();
        }, 3000);
    }

    function updateScore(correct, incorrect) {
        fetch("/api/interval-game/save-game-results", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `correct=${correct}&incorrect=${incorrect}`
        });
    }

    document.querySelectorAll(".interval-button").forEach(button => {
        button.addEventListener("click", function () {
            // Usunięcie zaznaczenia z poprzedniego przycisku
            if (selectedButton) {
                selectedButton.classList.remove("bg-blue-800", "border-2", "border-gray-900");
                selectedButton.classList.add("bg-blue-500", "hover:bg-blue-700");
            }

            // Zaznaczenie nowego przycisku
            this.classList.remove("bg-blue-500", "hover:bg-blue-700");
            this.classList.add("bg-blue-800", "border-2", "border-gray-900");

            // Zapisanie wybranego przycisku
            selectedButton = this;

            // Ustawienie wartości w ukrytym polu input
            document.getElementById("userAnswer").value = this.value;

            // Aktywowanie przycisku "Check answer"
            document.getElementById("submitAnswer").disabled = false;
        });
    });

    document.getElementById("submitAnswer").addEventListener("click", function () {
        const userAnswer = document.getElementById("userAnswer").value;
        checkAnswer(userAnswer);

        // Resetowanie zaznaczenia przycisku po kliknięciu "Check answer"
        if (selectedButton) {
            selectedButton.classList.remove("bg-blue-800", "border-2", "border-gray-900");
            selectedButton.classList.add("bg-blue-500", "hover:bg-blue-700");
            selectedButton = null;
        }

        // Resetowanie wartości inputa i wyłączenie przycisku
        document.getElementById("userAnswer").value = "";
        document.getElementById("submitAnswer").disabled = true;
    });

    document.getElementById("playSounds").addEventListener("click", function () {
        var firstSound = document.getElementById("firstSound");
        var secondSound = document.getElementById("secondSound");
        var isFinalPlay = false;
        var time = 500; // Przerwa między dźwiękami

        firstSound.currentTime = 0;
        secondSound.currentTime = 0;
        firstSound.play();

        firstSound.onended = function () {
            if (!isFinalPlay) {
                setTimeout(function () {
                    secondSound.currentTime = 0;
                    secondSound.play();
                }, time);
            }
        };

        secondSound.onended = function () {
            if (!isFinalPlay) {
                setTimeout(function () {
                    firstSound.currentTime = 0;
                    secondSound.currentTime = 0;
                    firstSound.play();
                    secondSound.play();
                    isFinalPlay = true;
                }, time);
            }
        };
    });

    fetchIntervalData();
});
