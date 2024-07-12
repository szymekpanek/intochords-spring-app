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