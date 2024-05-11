// Pobierz element zawierający dźwięki z widoku HTML
const soundListElement = document.getElementById('sounds');

// Sprawdź, czy element istnieje
if (soundListElement) {
    // Pobierz listę elementów audio
    const audioElements = soundListElement.querySelectorAll('audio');

    // Odtwórz każdy element audio
    audioElements.forEach(audioElement => {
        audioElement.addEventListener('loadedmetadata', () => {
            audioElement.play();
        });
    });
} else {
    console.error('Element zawierający dźwięki nie został znaleziony');
}