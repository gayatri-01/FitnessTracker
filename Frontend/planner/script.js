const weightInput = document.getElementById('weight');
const planButton = document.getElementById('plan-btn');

const positiveIntegerPattern = /^[1-9]\d*$/;

function togglePlanButton() {
  const gender = document.querySelector('input[name="gender"]:checked');
  console.log("plan button");
  if (
    weightInput.value.trim() !== '' &&
    positiveIntegerPattern.test(weightInput.value.trim()) && gender !== null
  ) {
    console.log("plan button enabled");
    planButton.disabled = false;
  } else {
    planButton.disabled = true;
  }
}

weightInput.addEventListener('input', togglePlanButton);

const genderRadios = document.querySelectorAll('input[name="gender"]');
genderRadios.forEach(radio => radio.addEventListener('change', togglePlanButton));

function populatePlans(data) {
  const tableContainer = document.getElementById("plan-container");
  tableContainer.style.display = "block";
  const tableBody = document.querySelector('#data-table tbody');

  // Clear existing table content
  tableBody.innerHTML = '';

  // Loop through the data and create table rows
  data.results.forEach(plan => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${plan.exercise}</td>
      <td>${plan.frequency}</td>
    `;
    tableBody.appendChild(row);
  });
}


const form = document.getElementById('planner-form');

form.addEventListener('submit', (event) => {
  event.preventDefault();
  const weight = weightInput.value.trim();
  const gender = document.querySelector('input[name="gender"]:checked').value;
  const token = sessionStorage.getItem("token");
  fetch("http://localhost:42000/workout/workoutPlans?gender=" + gender + "&weight=" + weight, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`
    }
  })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      //Re-populate activities
      populatePlans(data);

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
