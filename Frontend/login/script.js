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
    fetch('http://localhost:42000/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(response => response.json())
        .then(data => {
        console.log(data);
                if (data.status=='200') {
                    // Login successful, handle redirection or token storage
                    console.log('Login successful:', data.message);
                    // (Your logic for successful login)                    
                    sessionStorage.setItem('token', data.token);
                    sessionStorage.setItem('username', username);
                    window.location.href = '../home/home.html';
                } else {
                    // Login failed, display error message
                    console.error('Login failed:', data.message);
                    // (Your logic for login failure)
                }
            })
            .catch(error => {
                    console.error('Error:', error);
                    // (Your logic for handling errors)
                });
    console.log('Login:', username, password);
});

document.querySelector('.form-container-signup form').addEventListener('submit', (event) => {
    event.preventDefault();
    const name = document.querySelector('.form-container-signup .name').value;
    const username = document.querySelector('.form-container-signup .username').value;
    const password = document.querySelector('.form-container-signup .password').value;
    const email = document.querySelector('.form-container-signup .email').value;
    //const confirmPassword = document.querySelector('.form-container-signup .confirm-password').value;
    // Implement signup functionality here
       fetch('http://localhost:42000/api/auth/register', {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json'
               },
               body: JSON.stringify({
                   name: name,
                   username: username,
                   password: password,
                   email: email
               })
           })
           .then(response => response.json())
           .then(data => {
           console.log(data);
                   if (data.status=='200') {
                       // Signup successful, handle redirection
                       console.log('Signup successful:', data.message)
                       const messageElement = document.createElement('p');
                                 messageElement.textContent = 'Signup successful! Please log in.';
                                 messageElement.classList.add('signup-success-message'); // Add a CSS class for styling
                                 document.querySelector('.form-container-signup').appendChild(messageElement);
                                 setTimeout(() => {
                                     window.location.href = 'login.html';
                                 }, 3000);
                   } else {
                       // Signup failed, display error message
                       console.error('Signup failed:', data.message);
                       const errorMessageElement = document.querySelector('.signup-error-message');
                       if (errorMessageElement) {
                        errorMessageElement.textContent = data.message;
                        errorMessageElement.classList.add('show'); // Add a CSS class to style the message
                        }
                   }
               })
               .catch(error => {
                       console.error('Error:', error);
                       // (Your logic for handling errors)
                       const errorMessageElement = document.querySelector('.signup-error-message');
                       if (errorMessageElement) {
                            errorMessageElement.textContent = 'An unexpected error occurred. Please try again later.';
                            errorMessageElement.classList.add('show'); // Add a CSS class to style the message
                       }
                   });
});
