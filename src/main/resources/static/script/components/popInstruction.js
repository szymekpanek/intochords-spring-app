document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("info-modal");
    const openModalButton = document.getElementById("open-modal");
    const closeModalButton = document.getElementById("close-modal");

    // Show modal when the button is clicked
    openModalButton.addEventListener("click", function () {
        modal.classList.remove("hidden");
    });

    // Hide modal when the close button is clicked
    closeModalButton.addEventListener("click", function () {
        modal.classList.add("hidden");
    });

    // Hide modal when clicking outside the modal content
    modal.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.classList.add("hidden");
        }
    });
});
