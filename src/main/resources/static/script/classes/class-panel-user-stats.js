document.addEventListener("DOMContentLoaded", async function () {
    const classId = document.body.getAttribute("data-class-id");
    const classNameElement = document.getElementById("class-name");
    const studentsListElement = document.getElementById("students-list");
    const gameStatsTableBody = document.getElementById("game-stats-table");

    try {
        const response = await fetch(`/get-class-stats/${classId}`);
        if (!response.ok) {
            throw new Error("Failed to fetch class data");
        }

        const data = await response.json();

        if (data.error) {
            classNameElement.textContent = "Class not found";
            return;
        }

        classNameElement.innerHTML = `klasa <span class="text-blue-600">${data.className}</span>`;

        if (data.students.length === 0) {
            studentsListElement.innerHTML = "<li class='text-gray-500'>No students found</li>";
            return;
        }

        // Wypełnianie listy uczniów
        data.students.forEach(student => {
            const studentItem = document.createElement("li");
            studentItem.textContent = `${student.studentName} (${student.login})`;
            studentItem.classList.add("p-2", "border-b", "border-gray-300");
            studentsListElement.appendChild(studentItem);
        });

        // Wypełnianie tabeli wyników gier
        data.students.forEach(student => {
            student.gameStats.forEach(game => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td class="px-4 py-2 border">${student.studentName}</td>
                    <td class="px-4 py-2 border">${game.gameName}</td>
                    <td class="px-4 py-2 border">${game.gameDate}</td>
                    <td class="px-4 py-2 border text-green-600">${game.correctAnswer}</td>
                    <td class="px-4 py-2 border text-red-600">${game.incorrectAnswer}</td>
                `;
                gameStatsTableBody.appendChild(row);
            });
        });
    } catch (error) {
        console.error("Error fetching class stats:", error);
        classNameElement.textContent = "Error loading class data";
    }
});
