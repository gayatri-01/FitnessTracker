const loginToggle = document.getElementById('loginToggle');
const signupToggle = document.getElementById('signupToggle');
const loginForm = document.getElementById('loginForm');
const signupForm = document.getElementById('signupForm');

loginToggle.addEventListener('click', function() {
    console.log("loginToggle")
    loginForm.classList.add('active-form');
    signupForm.classList.remove('active-form');
    loginToggle.classList.add('active-tab');
    signupToggle.classList.remove('active-tab');
});

signupToggle.addEventListener('click', function() {
    console.log("signupToggle")
    signupForm.classList.add('active-form');
    loginForm.classList.remove('active-form');
    loginToggle.classList.remove('active-tab');
    signupToggle.classList.add('active-tab');
});

document.querySelector('.form-container-login form').addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.querySelector('.form-container-login .username').value;
    const password = document.querySelector('.form-container-login .password').value;
    // Implement login functionality here
    console.log('Login:', username, password);
});

document.querySelector('.form-container-signup form').addEventListener('submit', (event) => {
    event.preventDefault();
    const username = document.querySelector('.form-container-signup .username').value;
    const password = document.querySelector('.form-container-signup .password').value;
    const confirmPassword = document.querySelector('.form-container-signup .confirm-password').value;
    // Implement signup functionality here
    console.log('Signup:', username, password, confirmPassword);
});
