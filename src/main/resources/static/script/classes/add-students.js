

document.addEventListener("DOMContentLoaded", function () {
    const classId = getClassIdFromUrl(); // Get class ID from URL
    if (!classId) {
        alert("Class ID is missing! Redirecting...");
        window.location.href = "/class-panel";
        return;
    }

    const studentsList = document.getElementById("students-list");
    const searchInput = document.getElementById("student-search");

    let allStudents = []; // Store students for filtering

    // Fetch available students
    fetch(`/api/class/get-available-students`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Error fetching students list");
            }
            return response.json();
        })
        .then(data => {
            allStudents = data; // Store full list for filtering
            renderStudents(allStudents);
        })
        .catch(error => console.error("Error loading students:", error));

    // Function to render the student list
    function renderStudents(students) {
        studentsList.innerHTML = ""; // Clear previous list

        if (students.length === 0) {
            studentsList.innerHTML = '<li class="text-gray-500 text-center">No matching students found.</li>';
            return;
        }

        students.forEach(student => {
            const isAlreadyInClass = student.classId === parseInt(classId); // Check if already in class

            const li = document.createElement("li");
            li.classList.add("flex", "justify-between", "items-center", "p-3", "rounded-lg", "bg-gray-100");

            li.innerHTML = `
                <span>${student.name} ${student.surname} (${student.login})</span>
                <input type="checkbox" class="student-checkbox" value="${student.userId}" 
                    ${isAlreadyInClass ? "disabled title='Already in this class'" : ""}>
            `;

            studentsList.appendChild(li);
            console.log("DUPA")
        });
    }

    // **Event listener for search input**
    searchInput.addEventListener("input", function () {
        const searchValue = searchInput.value.trim().toLowerCase();

        if (searchValue === "") {
            // Show full list when search is cleared
            renderStudents(allStudents);
            return;
        }

        // Filter students
        const filteredStudents = allStudents.filter(student =>
            student.name.toLowerCase().includes(searchValue) ||
            student.surname.toLowerCase().includes(searchValue) ||
            student.login.toLowerCase().includes(searchValue)
        );

        renderStudents(filteredStudents);
    });

    // Submit button logic for adding students
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
                window.location.href = `/class-panel/${classId}`; // Redirect after success
            })
            .catch(error => console.error("Error:", error));
    });
});

// Helper function to get class ID from URL
function getClassIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    const classId = params.get("classId");

    if (!classId || isNaN(classId)) {
        console.error("❌ Error: Invalid class ID in URL!");
        return null;
    }

    return parseInt(classId);
}
