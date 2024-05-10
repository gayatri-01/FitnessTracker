const dateInput = document.getElementById('date');
const durationInput = document.getElementById('duration');
const stepsInput = document.getElementById('steps');
const caloriesInput = document.getElementById('calories');
const logButton = document.getElementById('log-btn');

const nonNegativeIntegerPattern = /^(0|[1-9]\d*)$/;

function toggleLogButton() {
    if (
        dateInput.value.trim() !== '' &&
        durationInput.value.trim() !== '' &&
        stepsInput.value.trim() !== '' &&
        caloriesInput.value.trim() !== '' &&
        nonNegativeIntegerPattern.test(durationInput.value) &&
        nonNegativeIntegerPattern.test(stepsInput.value.trim()) &&
        nonNegativeIntegerPattern.test(caloriesInput.value.trim())
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

// Function to log entry using fetch API
const token = sessionStorage.getItem("token");
function logEntry() {
  const data = {
    date: dateInput.value,
    duration: parseInt(durationInput.value),
    steps: parseInt(stepsInput.value),
    caloriesBurnt: parseInt(caloriesInput.value),
  };
  fetch('http://localhost:42000/api/activity', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(data)
    })
    .then(response => response.json())
                 .then(data => {
                   console.log(data);
                 })
                 .catch(error => {
                   console.error(error);
                 });
    console.log("Add code to process data after logging entry");
}

logButton.addEventListener('click', function() {
  if (!toggleLogButton()) {
    return;
  }
  logEntry();
});
