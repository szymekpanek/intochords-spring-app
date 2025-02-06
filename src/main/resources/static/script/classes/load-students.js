document.addEventListener("DOMContentLoaded", function () {
    const classId = document.getElementById("classId").value;
    const userSelect = document.getElementById("userId");

    fetch(`/api/class/get-students?classId=${classId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Błąd pobierania uczniów: " + response.status);
            }
            return response.json();
        })
        .then(users => {
            userSelect.innerHTML = ""; // Czyścimy stare opcje

            if (users.length === 0) {
                userSelect.innerHTML = '<option value="">No available students</option>';
                return;
            }

            users.forEach(user => {
                const option = document.createElement("option");
                option.value = user.userId;
                option.textContent = `${user.name} ${user.surname}`;
                userSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Błąd pobierania uczniów:", error);
            userSelect.innerHTML = '<option value="">Error loading students</option>';
        });
});
