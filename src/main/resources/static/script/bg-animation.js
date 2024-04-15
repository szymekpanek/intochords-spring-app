document.getElementById('change-background').addEventListener('click', function(e) {
    e.preventDefault();
    document.body.classList.add('new-background');
    setTimeout(function() {
        window.location.href = "/interval-game";
    }, 1000);
});