
// Meal Class: Represents a Meal instead of Recipe
    class Meal {
        constructor(title, description) {
            this.title = title;
            this.description = description;
            //need ingredient collection here
            this.recipes = [];  //instead of ingredients
        }
    }

    // UI Class: Handle UI Tasks
    class UI {
        static displayMeals() {
            UI.hideRecipes();
            const meals = Store.getMeals();

            meals.forEach((meal) => UI.addMealToList(meal));
        }

        static addMealToList(meal) {
            const list = document.querySelector("#meal-list");

            const row = document.createElement("tr");

            row.innerHTML = `
      <td>${meal.title}</td>
      <td>${meal.description}</td>
      <td><input type="button" id="addRecipes" class="btn btn-success btn-block" value="Add Recipes"></button><ul id="recipesContainer"></ul></td>
      <td><a href="#" class="btn btn-danger btn-sm delete" id="killMeal">X</a></td>
    `;
            list.appendChild(row);
            /*
        newCol = document.createElement("td");
        ingredientCol = newRow.appendChild(newCol);
        ingredientButton = document.createElement("button");
        ingredientButton.value = "Add Ingredients";
        ingredientCol.appendChild(ingredientButton);
*/
        }
        static addRecipeToList(recipe, mealNode) { //was ingredient
            console.log("Recipe added to meal list");

            const listEl = document.createElement("li");

            listEl.innerText = recipe;
            //targetNode.nextSibling.appendChild(listEl);
            const recipesContainer = mealNode.querySelector("#recipesContainer");
            recipesContainer.appendChild(listEl);
        }

        
        static deleteMeal(el) {
            if (el.classList.contains("delete")) {
                el.parentElement.parentElement.remove();
            }
        }

        static showAlert(message, className) {
            const div = document.createElement("div");
            div.className = `alert alert-${className}`;
            1;
            div.appendChild(document.createTextNode(message));
            const container = document.querySelector(".container");
            const form = document.querySelector("#meal-form");
            container.insertBefore(div, form);

            // Vanish in 3 seconds
            setTimeout(() => document.querySelector(".alert").remove(), 3000);
        }

        static clearFields() {
            document.querySelector("#title").value = "";
            document.querySelector("#description").value = "";
            document.querySelector("#recipeItem").value = "";
        }

        static hideRecipes() {
            document.getElementById("recipes-group").style.display = "none";
        }

        static showRecipes(row) {
            document.getElementById("recipes-group").style.display = "block";
        }

        static hideMeals() {
            document.getElementById("meals-group").style.display = "none";
        }

        static showMeals() {
            document.getElementById("meals-group").style.display = "block";
        }

        static swapMealsAndRecipes() {
            if (document.getElementById("meals-group").style.display != "none") {
                UI.hideMeals();
                UI.showRecipes();
            } else {
                UI.showMeals();
                UI.hideRecipes();
            }
        }

        static showRecipesHideMeals() {
            UI.hideMeals();
            UI.showRecipes();
        }

        static showMealsHideRecipes() {
            UI.showMeals();
            UI.hideRecipes();
        }

        //HELPER function - getMealID
        static getMealID() {
            const mealID = document.getElementById("mealID").value;
            return mealID;
        }

        static getMealNodeFromID(mealID) {
            //traverse "meal-list" -> children in a loop. look for text contents of 1st child

            const mealList = document.getElementById("meal-list").childNodes;
            var matchedNode;
            for (var i = 0; i < mealList.length; i++) {

                if (mealList[i].firstChild.nextSibling.textContent == mealID) {
                    console.log("Match found!  " + mealID);
                    matchedNode = mealList[i];
                    break;
                }
            }

            return matchedNode;
        }
    }

    // Store Class: Handles Storage
    class Store {
        static getMeals() {
            //TODO: add ajax instad of local storage
            let meals;
            if (localStorage.getItem("meals") === null) {
                meals = [];
            } else {
                meals = JSON.parse(localStorage.getItem("meals"));
            }

            return meals;
        }

        static addMeal(meal) {
            //TODO: add ajax instad of local storage
            const meals = Store.getMeals();
            meals.push(meal);
            localStorage.setItem("meals", JSON.stringify(meals));
        }

        static removeMeal(item) {
            //TODO: add ajax instad of local storage
            const meals = Store.getMeals();

            meals.forEach((meal, index) => {
                if (meal.title === item) {
                    meals.splice(index, 1); //starting position and then delete count
                }
            });

            localStorage.setItem("meals", JSON.stringify(meals));
        }

        static addRecipe(recipe, meal) {
            //TODO. AJAX call to controller - add the recipe to meal

        }


        static removeRecipe(recipe, meal) {
            //TODO. AJAX call to controller - remove the recipe from meal

        }


        static getRecipes(meal) {
            //TODO. AJAX call to controller - get the recipe stored for meal

        }

    }
    // Event: Display Meals
    document.addEventListener("DOMContentLoaded", UI.displayMeals);


    //Event: Back to Meals (from recipes)
    document
        .querySelector("#show-meals-button")
        .addEventListener("click", UI.showMealsHideRecipes);

    // Event: Add an Recipe
    document.querySelector("#addRecipe").addEventListener("click", (e) => {
        const recipeItem = document.querySelector("#recipeItem").value;
        const mealID = UI.getMealID();
        const mealNode = UI.getMealNodeFromID(mealID);

        // Add Recipe to UI
        UI.addRecipeToList(recipeItem, mealNode);

        // Add Recipe to store
        Store.addRecipe(recipeItem);

        // Show success message
        UI.showAlert("Recipe Added", "success");

        // Clear fields
        UI.clearFields();
    });

    // Event: Add a Meal
    document.querySelector("#next").addEventListener("click", (e) => {
        // Prevent actual submit

        e.preventDefault();
        // Get form values
        const title = document.querySelector("#title").value;
        const description = document.querySelector("#description").value;

        // Validate
        if (title === "" || description === "") {
            UI.showAlert("Please fill in all fields", "danger");
        } else {
            // Instatiate meal
            const meal = new Meal(title, description);
            // Add Meal to UI
            UI.addMealToList(meal);

            // Add Meal to store
            Store.addMeal(meal);

            // Show success message
            UI.showAlert("Meal Added", "success");

            // Clear fields
            UI.clearFields();
        }
    });

    // Event: Remove a Meal OR add Recipes to Meal
    document.querySelector("#meal-list").addEventListener("click", (e) => {
        row = e.target.parentElement.parentElement;
        titleFromRow = row.getElementsByTagName("td")[0].textContent;

        if (e.target.id == "killMeal") {
            // Remove Meal from UI
            UI.deleteMeal(e.target);

            // Remove Meal from store

            Store.removeMeal(titleFromRow);
            //TODO make sure removeMeal also removes the recipes and the join between meals and recipes

            // Show success message
            UI.showAlert("Meal Removed", "success");
        } else {
            //add recipes
            UI.showRecipesHideMeals();
            //update the form's hidden field so we know which row was passed in...
            document.getElementById("mealID").value = titleFromRow;

        }
    });