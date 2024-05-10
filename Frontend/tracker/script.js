const token = sessionStorage.getItem("token");
const username = sessionStorage.getItem("username");
fetch("http://localhost:42000/api/progress/" + username, {
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
//barchart data
const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
//const barChartCaloriesBurned = [350, 300, 400, 320, 380, 420, 300];
//const barChartStepsTaken = [8000, 7500, 9000, 7000, 8500, 9500, 7200];
const barChartCaloriesBurned = data.caloriesBarChart;
const barChartStepsTaken = data.stepsBarChart;

//donut graph data
const weeks = ["Week 1", "Week 2", "Week 3", "Week 4"];
//const donutGraphCaloriesBurned = [2450, 2800, 3100, 2700];
//const donutGraphStepsTaken = [45000, 49000, 53000, 48000];
const donutGraphCaloriesBurned = data.caloriesDoughNutChart;
const donutGraphStepsTaken = data.stepsDoughNutChart;
const donutGraphCaloriesColors = ["#b91d47","#00aba9","#2b5797","#e8c3b9"];
const donutGraphStepsColors = ["#faa3ad", "#ed559", "#ffe30f", "#ffc25c"]

// Creating bar graph
const barGraphs = document.getElementById('barGraphs').getContext('2d');
new Chart(barGraphs, {
    type: 'bar',
    data: {
        labels: days,
        datasets: [{
            label: 'Calories Burned',
            data: barChartCaloriesBurned,
            backgroundColor: "lightgrey",
            borderWidth: 1
        }, {
            label: 'Steps Taken',
            data: barChartStepsTaken,
            backgroundColor: "grey",
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

//Creating calorie donut graph
new Chart("donutGraphCalories", {
  type: "doughnut",
  data: {
    labels: weeks,
    datasets: [{
      backgroundColor: donutGraphCaloriesColors,
      data: donutGraphCaloriesBurned
    }]
  },
  options: {
    title: {
      display: true,
      text: "Calories Burned per Week"
    }
  }
});

//Creating steps donut graph
new Chart("donutGraphSteps", {
    type: "doughnut",
    data: {
      labels: weeks,
      datasets: [{
        backgroundColor: donutGraphStepsColors,
        data: donutGraphStepsTaken
      }]
    },
    options: {
      title: {
        display: true,
        text: "Steps Taken per Week"
      }
    }
  });
  

