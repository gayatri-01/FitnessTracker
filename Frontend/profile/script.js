document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.getElementById("username");
    const nameInput = document.getElementById("name");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const caloriesInput = document.getElementById("calories");
    const stepsInput = document.getElementById("steps");
    const updateBtn = document.getElementById("updateBtn");

    // Need to change, this data we will get after the post request to load user profile
    const token = sessionStorage.getItem("token");
    const username = sessionStorage.getItem("username");
    let originalData = {
        username: username,
        name: "",
        email: "",
        password: "",
        caloriesGoal: "",
        stepsGoal: "",
    };

    fetch("http://localhost:42000/users/" + username, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
        },
    })
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            if (data.status == "200") {
                console.log("Profile page");

                originalData.name = data.results.name;
                originalData.email = data.results.email;
                originalData.password = data.results.password;
                originalData.caloriesGoal = data.results.caloriesGoal;
                originalData.stepsGoal = data.results.stepsGoal;

                usernameInput.value = originalData.username;
                nameInput.value = originalData.name;
                emailInput.value = originalData.email;
                passwordInput.value = originalData.password;
                caloriesInput.value = originalData.caloriesGoal;
                stepsInput.value = originalData.stepsGoal;

            } else {
                console.error("Get Profile failed:", data.message);
            }
        })
        .catch((error) => {
            console.error("Error:", error);
        });

    
    // Function to compare original data with new data
    function hasChanged() {
        return (
            nameInput.value !== originalData.name ||
            emailInput.value !== originalData.email ||
            passwordInput.value !== originalData.password ||
            caloriesInput.value !== originalData.caloriesGoal ||
            stepsInput.value !== originalData.stepsGoal
        );
    }

    
    // Function to enable/disable update button based on changes
    function toggleUpdateButton() {
        if (hasChanged()) {
            updateBtn.removeAttribute("disabled");
        } else {
            updateBtn.setAttribute("disabled", true);
        }
    }

    
    // Event listeners for input fields
    nameInput.addEventListener("input", toggleUpdateButton);
    emailInput.addEventListener("input", toggleUpdateButton);
    passwordInput.addEventListener("input", toggleUpdateButton);
    caloriesInput.addEventListener("input", toggleUpdateButton);
    stepsInput.addEventListener("input", toggleUpdateButton);

    // Event listener for update button
    updateBtn.addEventListener("click", function () {
        if (!hasChanged()) {
            return; // No changes, nothing to update
        }

        const updatedData = {
            name: nameInput.value,
            email: emailInput.value,
            password: passwordInput.value,
            stepsGoal: stepsInput.value,
            caloriesGoal: caloriesInput.value
        };
        fetch("http://localhost:42000/users/" + username, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(updatedData),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.status == "200") {
                    console.log("Profile updated successfully:", data.message);
                    originalData = updatedData;
                    updateBtn.setAttribute("disabled", true);
                } else {
                    console.error("Profile update failed:", data.message);
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        console.log("Updated Profile Data");
        updateBtn.setAttribute("disabled", true);
    });
});
