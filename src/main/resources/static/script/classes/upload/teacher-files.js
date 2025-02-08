document.addEventListener("DOMContentLoaded", function () {
    const fileList = document.getElementById("teacher-files-list");
    const fileInput = document.getElementById("file-input");
    const uploadForm = document.getElementById("upload-file-form");
    const uploadStatus = document.getElementById("upload-status");

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
                    fileList.innerHTML = '<li class="text-gray-500 text-center">Brak przesłanych plików.</li>';
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
                    <div class="flex gap-2">
                    <a href="/api/teacher/files/download/${file.fileId}" 
                        class="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600" 
                        download>
                        ⬇️ Download
                    </a>
                    <button class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600" 
                            onclick="deleteFile(${file.fileId})">
                        🗑️ Delete
                    </button>
                    </div>
                    `;
                    fileList.appendChild(li);
                });
            })
            .catch(error => console.error("Błąd ładowania plików:", error));
    }

    // Obsługa przesyłania plików
    uploadForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const file = fileInput.files[0];
        if (!file) {
            alert("Wybierz plik do przesłania.");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        fetch("/api/teacher/files/upload", {
            method: "POST",
            body: formData
        })
            .then(response => response.text())
            .then(message => {
                alert(message);
                fileInput.value = "";
                loadFiles();
            })
            .catch(error => console.error("Błąd przesyłania pliku:", error));
    });

    // ✅ Usunięta walidacja roli - nauczyciel może usunąć dowolny plik
    window.deleteFile = function (fileId) {
        if (!confirm("Czy na pewno chcesz usunąć ten plik?")) return;

        fetch(`/api/teacher/files/delete/${fileId}`, {method: "DELETE"})
            .then(response => response.json()) // ✅ Oczekujemy poprawnego JSON-a
            .then(data => {
                if (data.error) {
                    alert("Błąd: " + data.error);
                    return;
                }
                alert(data.message);
                loadFiles();
            })
            .catch(error => console.error("Błąd usuwania pliku:", error));
    };

    // Wczytaj pliki po załadowaniu strony
    loadFiles();
});
