const dateInput = document.getElementById('date');
const durationInput = document.getElementById('duration');
const stepsInput = document.getElementById('steps');
const caloriesInput = document.getElementById('calories');
const logButton = document.getElementById('log-btn');

function toggleLogButton() {
    if (
        dateInput.value.trim() !== '' &&
        durationInput.value.trim() !== '' &&
        stepsInput.value.trim() !== '' &&
        caloriesInput.value.trim() !== ''
    ) {
        logButton.disabled = false;
    } else {
        logButton.disabled = true;
    }
}

dateInput.addEventListener('input', toggleLogButton);
durationInput.addEventListener('input', toggleLogButton);
stepsInput.addEventListener('input', toggleLogButton);
caloriesInput.addEventListener('input', toggleLogButton);
