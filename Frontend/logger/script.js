const dateInput = document.getElementById('date');
const durationInput = document.getElementById('duration');
const stepsInput = document.getElementById('steps');
const caloriesInput = document.getElementById('calories');
const logButton = document.getElementById('log-btn');

const nonNegativeIntegerPattern = /^(0|[1-9]\d*)$/;

const nonNegativeFloatPattern = /^(?:0|[1-9]\d*)(?:\.\d+)?$/;

function toggleLogButton() {
  if (
    dateInput.value.trim() !== '' &&
    durationInput.value.trim() !== '' &&
    stepsInput.value.trim() !== '' &&
    caloriesInput.value.trim() !== '' &&
    nonNegativeIntegerPattern.test(durationInput.value) &&
    nonNegativeIntegerPattern.test(stepsInput.value.trim()) &&
    nonNegativeFloatPattern.test(caloriesInput.value.trim())
  ) {
    logButton.disabled = false;
    return true;
  } else {
    logButton.disabled = true;
    return false;
  }
}

dateInput.addEventListener('input', toggleLogButton);
durationInput.addEventListener('input', toggleLogButton);
stepsInput.addEventListener('input', toggleLogButton);
caloriesInput.addEventListener('input', toggleLogButton);

// Function to log entry using fetch API
const token = sessionStorage.getItem("token");


populateActivities();

function populateActivities() {
  fetch('http://localhost:42000/api/activity?username=' + sessionStorage.getItem("username"), {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  })
    .then(response => response.json())
    .then(data => {
      const tableBody = document.querySelector('#data-table tbody');

      // Clear existing table content
      tableBody.innerHTML = '';

      // Loop through the data and create table rows
      data.results.forEach(activity => {
        const row = document.createElement('tr');
        row.innerHTML = `
      <td>${activity.date}</td>
      <td>${activity.duration}</td>
      <td>${activity.steps}</td>
      <td>${activity.caloriesBurnt}</td>
    `;
        tableBody.appendChild(row);
      });
    })
    .catch(error => console.error('Error fetching data:', error));
}
const form = document.getElementById('tracker-form');

form.addEventListener('submit', (event) => {
  event.preventDefault();
  const data = {
    username: sessionStorage.getItem("username"),
    date: dateInput.value,
    duration: parseInt(durationInput.value),
    steps: parseInt(stepsInput.value),
    caloriesBurnt: parseFloat(caloriesInput.value)
  };
  console.log(data);

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
      
      // Reset the form fields
      form.reset();

      //Re-populate activities
      populateActivities();

      // Display the toaster
      toaster.style.display = "block";

      // Hide the toaster after 5 seconds
      setTimeout(function () {
        toaster.style.display = "none";
      }, 2000);
    })
    .catch(error => {
      console.error(error);
    });

});
