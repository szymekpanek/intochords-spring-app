document.addEventListener("DOMContentLoaded", function () {
    const createClassForm = document.getElementById("create-class-form");
    const classNameInput = document.getElementById("class-name");
    const errorMessage = document.getElementById("error-message");

    createClassForm.addEventListener("submit", function (event) {
        event.preventDefault(); // ✅ Blokuje domyślne wysyłanie formularza

        const className = classNameInput.value.trim();

        if (!className) {
            errorMessage.textContent = "Class name cannot be empty.";
            errorMessage.classList.remove("hidden");
            return;
        }

        // Wysyłamy żądanie do API
        fetch("/api/class/create", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: `className=${encodeURIComponent(className)}`
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text); });
                }
                return response.text();
            })
            .then(redirectUrl => {
                window.location.href = redirectUrl; // ✅ Przenosi do class-panel
            })
            .catch(error => {
                errorMessage.textContent = error.message;
                errorMessage.classList.remove("hidden");
            });
    });
});
