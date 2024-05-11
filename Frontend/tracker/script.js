const token = sessionStorage.getItem("token");
const username = sessionStorage.getItem("username");
fetch("http://localhost:42000/progress?username=" + username, {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${token}`
  }
})
  .then(response => response.json())
  .then(data => {
    data = data.results;
    console.log(data);
    //barchart data
    const labelsBarChartSteps = data.labelsBarChartSteps;
    const labelsBarChartCalories = data.labelsBarChartCalories;
    const barChartCaloriesBurned = data.caloriesBarChart;
    const barChartStepsTaken = data.stepsBarChart;

    //donut graph data
    const labelsDoughNutChart = data.labelsDoughNutChart;
    const donutGraphCaloriesBurned = data.caloriesDoughNutChart;
    const donutGraphStepsTaken = data.stepsDoughNutChart;
    const donutGraphCaloriesColors = ["#03A9F4", "#ccc"];
    const donutGraphStepsColors = ["#03A9F4", "#ccc"]

    // Creating bar graph
    const barGraphs = document.getElementById('barGraphs').getContext('2d');
    new Chart(barGraphs, {
      type: 'bar',
      data: {
        labels: labelsBarChartCalories,
        datasets: [{
          label: 'Calories Burned',
          data: barChartCaloriesBurned,
          backgroundColor: "#0286c2",
          borderWidth: 1
        }, {
          label: 'Steps Taken',
          data: barChartStepsTaken,
          backgroundColor: "#03A9F4",
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
        labels: labelsDoughNutChart,
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
        labels: labelsDoughNutChart,
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
  })
  .catch(error => {
    console.error(error);
  });




