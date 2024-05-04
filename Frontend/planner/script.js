const weightInput = document.getElementById('weight');
const planButton = document.getElementById('plan-btn');

const positiveIntegerPattern = /^[1-9]\d*$/;

function togglePlanButton() {
    if (
        weightInput.value.trim() !== '' &&
        positiveIntegerPattern.test(weightInput.value.trim())
    ) {
        planButton.disabled = false;
    } else {
        planButton.disabled = true;
    }
}

weightInput.addEventListener('input', togglePlanButton);
