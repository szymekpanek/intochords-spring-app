document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const classId = urlParams.get("classId");
    const fileList = document.getElementById("class-files-list");

    if (!classId) {
        fileList.innerHTML = `<li class="text-red-500 text-center">Błąd: Nie znaleziono ID klasy.</li>`;
        return;
    }

    fetch(`/api/teacher/files/${classId}`)
        .then(response => response.json())
        .then(data => {
            fileList.innerHTML = "";

            if (data.error) {
                fileList.innerHTML = `<li class="text-red-500 text-center">${data.error}</li>`;
                return;
            }

            if (data.length === 0) {
                fileList.innerHTML = '<li class="text-gray-500 text-center">No files available.</li>';
                return;
            }

            data.forEach(file => {
                const li = document.createElement("li");
                li.classList.add("flex", "justify-between", "p-3", "bg-white", "rounded-lg", "shadow");

                li.innerHTML = `
                    <div>
                        <span class="font-semibold">${file.fileName}</span>
                        <p class="text-sm text-gray-500">Dodano: ${new Date(file.uploadDate).toLocaleDateString()}</p>
                    </div>
                    <div>
                        <a href="${file.filePath}" 
                           class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
                           download>
                            ⬇ Download
                        </a>
                    </div>
                `;

                fileList.appendChild(li);
            });
        })
        .catch(error => console.error("Błąd ładowania plików:", error));
});
