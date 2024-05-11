const weightInput = document.getElementById('weight');
const planButton = document.getElementById('plan-btn');

const positiveIntegerPattern = /^[1-9]\d*$/;

function togglePlanButton() {
    const gender = document.querySelector('input[name="gender"]:checked');
    if (
        weightInput.value.trim() !== '' &&
        positiveIntegerPattern.test(weightInput.value.trim()) && gender !== null
    ) {
        planButton.disabled = false;
    } else {
        planButton.disabled = true;
    }
}

weightInput.addEventListener('input', togglePlanButton);

const genderRadios = document.querySelectorAll('input[name="gender"]');
genderRadios.forEach(radio => radio.addEventListener('change', togglePlanButton));

planButton.addEventListener('click', async function handleClick() {
  const weight = weightInput.value.trim();
  const gender = document.querySelector('input[name="gender"]:checked').value;
  const token = sessionStorage.getItem("token");
    fetch("http://localhost:42000/api/workout/workoutPlan?" + new URLSearchParams({
    gender: gender,
    weight: weight}), {
                 method: 'GET',
                 headers: {
                     'Content-Type': 'application/json',
                     Authorization: `Bearer ${token}`
                 }
             })
             .then(response => response.json())
             .then(data => {
               console.log(data);
             })
             .catch(error => {
               console.error(error);
             });
  console.log("Add the code to target the div for displaying the workout plan");
});
