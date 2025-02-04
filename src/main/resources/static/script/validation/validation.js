document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("create-user-form").addEventListener("submit", function(event) {
        if (!validateForm()) {
            event.preventDefault();
        }
    });
});

function validateForm() {
    let valid = true;

    // Pobranie wartości pól
    let name = document.getElementById("name").value.trim();
    let surname = document.getElementById("surname").value.trim();
    let login = document.getElementById("login").value.trim();
    let password = document.getElementById("password").value.trim();
    let role = document.getElementById("role").value;

    // Reset komunikatów błędów
    document.getElementById("name-error").classList.add("hidden");
    document.getElementById("surname-error").classList.add("hidden");
    document.getElementById("login-error").classList.add("hidden");
    document.getElementById("password-error").classList.add("hidden");

    // Walidacja imienia (minimum 2 znaki, tylko litery)
    if (!/^[A-Za-z]{2,}$/.test(name)) {
        document.getElementById("name-error").classList.remove("hidden");
        valid = false;
    }

    // Walidacja nazwiska (minimum 2 znaki, tylko litery)
    if (!/^[A-Za-z]{2,}$/.test(surname)) {
        document.getElementById("surname-error").classList.remove("hidden");
        valid = false;
    }

    // Walidacja loginu (minimum 4 znaki)
    if (login.length < 4) {
        document.getElementById("login-error").classList.remove("hidden");
        valid = false;
    }

    // Walidacja hasła (minimum 6 znaków)
    if (password.length < 6) {
        document.getElementById("password-error").classList.remove("hidden");
        valid = false;
    }

    // Walidacja wyboru roli (musi być `USER` lub `TEACHER`)
    if (role !== "USER" && role !== "TEACHER") {
        alert("Invalid role selected.");
        valid = false;
    }

    return valid;
}
