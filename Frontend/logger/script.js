document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('tracker-form');
    const logList = document.getElementById('log-list');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const weightInput = document.getElementById('weight');
        const caloriesInput = document.getElementById('calories');

        const weight = weightInput.value.trim();
        const calories = caloriesInput.value.trim();

        if (weight !== '' || calories !== '') {
            const entry = document.createElement('li');
            entry.textContent = `Weight: ${weight} kg, Calories: ${calories}`;
            logList.appendChild(entry);

            // Clear input fields and disable button after logging
            weightInput.value = '';
            caloriesInput.value = '';
            toggleLogButton();
        }
    });

    const weightInput = document.getElementById('weight');
    const caloriesInput = document.getElementById('calories');
    const logButton = document.getElementById('log-btn');

    // Function to enable/disable log button based on input fields
    function toggleLogButton() {
        if (weightInput.value.trim() !== '' || caloriesInput.value.trim() !== '') {
            logButton.disabled = false;
        } else {
            logButton.disabled = true;
        }
    }

    // Event listeners for input fields
    weightInput.addEventListener('input', toggleLogButton);
    caloriesInput.addEventListener('input', toggleLogButton);
});
