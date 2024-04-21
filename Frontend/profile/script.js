document.addEventListener('DOMContentLoaded', function() {
    const nameInput = document.getElementById('name');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const updateBtn = document.getElementById('updateBtn');

    // Need to change, this data we will get after the post request to load user profile 
    let originalData = {
        name: 'Lorem Ipsum',
        email: 'lorem.ipsum@example.com',
        password: 'password123'
    };

    // Function to compare original data with new data
    function hasChanged() {
        return (
            nameInput.value !== originalData.name ||
            emailInput.value !== originalData.email ||
            passwordInput.value !== originalData.password
        );
    }

    // Function to enable/disable update button based on changes
    function toggleUpdateButton() {
        if (hasChanged()) {
            updateBtn.removeAttribute('disabled');
        } else {
            updateBtn.setAttribute('disabled', true);
        }
    }

    // Event listeners for input fields
    nameInput.addEventListener('input', toggleUpdateButton);
    emailInput.addEventListener('input', toggleUpdateButton);
    passwordInput.addEventListener('input', toggleUpdateButton);

    // Event listener for update button
    updateBtn.addEventListener('click', function() {
        // Here you can implement the code to update the profile data
        console.log('Updated Profile Data');
        // Update original data with new data
        originalData.name = nameInput.value;
        originalData.email = emailInput.value;
        originalData.password = passwordInput.value;
        // Disable update button after updating
        updateBtn.setAttribute('disabled', true);
    });
});
