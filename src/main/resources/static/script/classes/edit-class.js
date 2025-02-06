document.addEventListener("DOMContentLoaded", function () {
    const classId = new URLSearchParams(window.location.search).get("classId");
    if (!classId) {
        alert("Class ID is missing! Redirecting...");
        window.location.href = "/class-panel";
        return;
    }

    fetch(`/api/class/${classId}`)
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                alert("Class not found.");
                window.location.href = "/class-panel";
                return;
            }

            document.getElementById("class-name").value = data.className;
            updateStudentList(data.students, classId);
        })
        .catch(error => console.error("Error loading class data:", error));

    document.getElementById("save-class-name").addEventListener("click", function () {
        const newName = document.getElementById("class-name").value.trim();

        if (!newName) {
            alert("Class name cannot be empty.");
            return;
        }

        fetch(`/api/class/edit-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ classId: Number(classId), newName })
        })
            .then(response => response.text())
            .then(() => {
                alert("Class name updated successfully!");
            })
            .catch(error => console.error("Error updating class name:", error));
    });
});

function updateStudentList(students, classId) {
    const studentsList = document.getElementById("students-list");
    studentsList.innerHTML = "";

    students.forEach(student => {
        const li = document.createElement("li");
        li.classList.add("flex", "justify-between", "items-center", "p-3", "rounded-lg");

        li.innerHTML = `
            <span>${student.studentName} (${student.login})</span>
            <button class="remove-student bg-red-500 text-white px-3 py-1 rounded-lg hover:bg-red-600"
                    data-student-id="${student.userId}" data-class-id="${classId}">
                Remove
            </button>
        `;

        studentsList.appendChild(li);
    });

    document.querySelectorAll(".remove-student").forEach(button => {
        button.addEventListener("click", function () {
            const studentId = this.getAttribute("data-student-id");
            const classId = this.getAttribute("data-class-id");

            if (!studentId || studentId === "null" || isNaN(studentId)) {
                console.error("❌ Błąd: studentId jest null lub nie jest liczbą!", studentId);
                alert("Błąd: studentId jest niepoprawny.");
                return;
            }

            console.log(`📡 Wysyłanie zapytania: remove-student -> classId: ${classId}, studentId: ${studentId}`);

            fetch(`/api/class/remove-student`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ classId: Number(classId), studentId: Number(studentId) })
            })
                .then(response => response.text())
                .then(() => {
                    alert("Student removed successfully!");
                    this.parentElement.remove();
                })
                .catch(error => console.error("❌ Błąd usuwania studenta:", error));
        });
    });
}
