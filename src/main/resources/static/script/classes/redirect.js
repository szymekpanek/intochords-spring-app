document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Zapobiegamy przeładowaniu strony

        const formData = new FormData(form);

        fetch("/api/class/create", {
            method: "POST",
            body: new URLSearchParams(formData),
        })
            .then(response => response.text())
            .then(url => {
                if (url.startsWith("/class-panel/add-students")) {
                    window.location.href = url; // 🔹 Automatyczne przekierowanie
                } else {
                    alert(url); // Wyświetlenie błędu, jeśli wystąpił
                }
            })
            .catch(error => {
                console.error("Error creating class:", error);
            });
    });
});

