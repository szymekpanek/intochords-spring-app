document.addEventListener("DOMContentLoaded", function () {
    const classId = getClassIdFromUrl(); // Pobieramy poprawnie `classId`
    if (!classId) {
        alert("Class ID is missing! Redirecting...");
        window.location.href = "/class-panel";
        return;
    }

    fetch(`/api/class/get-available-students`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Error fetching students list");
            }
            return response.json();
        })
        .then(data => {
            const studentsList = document.getElementById("students-list");
            studentsList.innerHTML = ""; // Czyszczenie listy

            if (data.length === 0) {
                studentsList.innerHTML = '<li class="text-gray-500 text-center">No available students.</li>';
                return;
            }

            data.forEach(student => {
                const isAlreadyInClass = student.classId === parseInt(classId); // Sprawdzamy, czy student jest już w tej klasie

                const li = document.createElement("li");
                li.classList.add("flex", "justify-between", "items-center", "p-3", "rounded-lg");

                li.innerHTML = `
                    <span>${student.name} ${student.surname} (${student.login})</span>
                    <input type="checkbox" class="student-checkbox" value="${student.userId}" 
                        ${isAlreadyInClass ? "disabled title='Already in this class'" : ""}>
                `;

                studentsList.appendChild(li);
            });
        })
        .catch(error => console.error("Error loading students:", error));

    document.getElementById("submit-button").addEventListener("click", function () {
        const selectedStudents = Array.from(document.querySelectorAll(".student-checkbox:checked"))
            .map(checkbox => parseInt(checkbox.value));

        if (selectedStudents.length === 0) {
            alert("Please select at least one student.");
            return;
        }

        fetch("/api/class/add-students", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ classId, students: selectedStudents })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error adding students");
                }
                return response.text();
            })
            .then(() => {
                alert("Students added successfully!");
                window.location.href = `/class-panel/${classId}`; // ✅ Poprawne przekierowanie
            })
            .catch(error => console.error("Error:", error));
    });
});

function getClassIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    const classId = params.get("classId");

    if (!classId || isNaN(classId)) {
        console.error("❌ Błąd: Brak poprawnego ID klasy w URL!");
        return null;
    }

    return parseInt(classId); // ✅ Zawsze zwracamy `Number`, nie `String`
}
