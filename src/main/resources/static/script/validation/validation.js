function validate() {
    var loginField = document.getElementById("login-field");
    var passField = document.getElementById("pass-field");

    var regex = /^[A-Za-z0-9]{5,}$/;

    if(!regex.test(loginField.value)) {
        console.log("JS NIE DZIALA 1 IF")
        return false;
    }

    if(!regex.test(passField.value)) {
        console.log("JS NIE DZIALA 2 IF")
        return false;
    }

    return true;
}