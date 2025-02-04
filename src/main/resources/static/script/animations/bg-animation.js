document.addEventListener("DOMContentLoaded", function () {
    console.log("GSAP animation loaded!"); // Sprawdzenie czy skrypt się ładuje

    gsap.to("#wave1", {
        duration: 4,
        repeat: -1,
        yoyo: true,
        ease: "sine.inOut",
        attr: { d: "M0 60L15 70C30 80 60 100 90 110C120 120 150 130 180 125C210 120 ..." }
    });

    gsap.to("#wave2", {
        duration: 6,
        repeat: -1,
        yoyo: true,
        ease: "sine.inOut",
        attr: { d: "M0 130L15 145C30 160 60 190 90 200C120 210 150 205 180 195C210 185 ..." }
    });

    gsap.to("#wave3", {
        duration: 5,
        repeat: -1,
        yoyo: true,
        ease: "sine.inOut",
        attr: { d: "M0 220L15 230C30 240 60 260 90 265C120 270 150 260 180 250C210 240 ..." }
    });
});
