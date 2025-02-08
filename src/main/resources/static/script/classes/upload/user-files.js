document.addEventListener("DOMContentLoaded", function () {
    const fileList = document.getElementById("user-files-list");

    function loadFiles() {
        fetch("/api/teacher/files/get-files")
            .then(response => response.json())
            .then(data => {
                fileList.innerHTML = "";

                if (data.error) { // ✅ Obsługa błędów z backendu
                    fileList.innerHTML = `<li class="text-red-500 text-center">${data.error}</li>`;
                    return;
                }

                if (data.length === 0) {
                    fileList.innerHTML = '<li class="text-gray-500 text-center">Brak dostępnych plików.</li>';
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
                            <a href="/api/teacher/files/download/${file.fileId}" 
                               class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
                               download>
                                ⬇ Pobierz
                            </a>
                        </div>
                    `;

                    fileList.appendChild(li);
                });
            })
            .catch(error => console.error("Błąd ładowania plików:", error));
    }

    // Wczytaj pliki po załadowaniu strony
    loadFiles();
});
