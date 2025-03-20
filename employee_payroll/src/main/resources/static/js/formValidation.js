function validateSignup() {
    let isValid = true;

    // Name Validation
    let name = document.getElementById("name").value.trim();
    let nameError = document.getElementById("nameError");
    let namePattern = /^[A-Za-z\s]+$/;
    if (!namePattern.test(name)) {
        nameError.classList.remove("hidden");
        isValid = false;
    } else {
        nameError.classList.add("hidden");
    }

    // Email Validation
    let email = document.getElementById("email").value.trim();
    let emailError = document.getElementById("emailError");
    let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
        emailError.classList.remove("hidden");
        isValid = false;
    } else {
        emailError.classList.add("hidden");
    }

    // Password Validation
    let password = document.getElementById("password").value.trim();
    let passwordError = document.getElementById("passwordError");
    let passwordPattern = /^(?=.[A-Z])(?=.\d)(?=.[@$!%?&])[A-Za-z\d@$!%*?&]{8,}$/;
    if (!passwordPattern.test(password)) {
        passwordError.classList.remove("hidden");
        isValid = false;
    } else {
        passwordError.classList.add("hidden");
    }

    return isValid;
}

function validateLogin() {
    let isValid = true;

    // Email Validation
    let email = document.getElementById("loginEmail").value.trim();
    let emailError = document.getElementById("loginEmailError");
    let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
        emailError.classList.remove("hidden");
        isValid = false;
    } else {
        emailError.classList.add("hidden");
    }

    // Password Validation
    let password = document.getElementById("loginPassword").value.trim();
    let passwordError = document.getElementById("loginPasswordError");
    if (password.length < 8) {
        passwordError.classList.remove("hidden");
        isValid = false;
    } else {
        passwordError.classList.add("hidden");
    }

    return isValid;
}
