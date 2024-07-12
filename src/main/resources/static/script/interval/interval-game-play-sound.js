document.getElementById('playSounds').addEventListener('click', function() {
    var firstSound = document.getElementById('firstSound');
    var secondSound = document.getElementById('secondSound');
    var isFinalPlay = false;
    var time = 600;
    firstSound.currentTime = 0;
    secondSound.currentTime = 0;


    firstSound.play();

    firstSound.onended = function() {
        if (!isFinalPlay) {
            setTimeout(function() {
                secondSound.currentTime = 0;
                secondSound.play();
            }, time); // Przerwa 1 sekunda
        }
    };

    secondSound.onended = function() {
        if (!isFinalPlay) {
            setTimeout(function() {
                firstSound.currentTime = 0;
                secondSound.currentTime = 0;
                firstSound.play();
                secondSound.play();
                isFinalPlay = true;
            }, time); // Przerwa 1 sekunda
        }
    };
});

// Pobierz suwak
var volumeSlider = document.getElementById('volumeSlider');

// Aktualizuj głośność, gdy suwak jest przesuwany
volumeSlider.oninput = function() {
    firstSound.volume = this.value;
    secondSound.volume = this.value;
};


function selectInterval(button) {
    const buttons = document.querySelectorAll('.interval-button');
    buttons.forEach(btn => btn.classList.remove('selected'));
    button.classList.add('selected');
    document.getElementById('userAnswer').value = button.value;

    // Włączenie przycisku "Sprawdź odpowiedź" po zaznaczeniu opcji
    document.getElementById('submitAnswer').disabled = false;
}

document.getElementById('playSounds').addEventListener('click', function() {
    document.getElementById('resultMessage').classList.add('hidden');
});