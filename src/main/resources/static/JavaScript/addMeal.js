let meals = [];

class Meal {
  constructor(name, description, id) {
    this.name = name;
    this.description = description;
    this.id = id;
    //need ingredient collection here
    this.recipes = []; //instead of ingredients
  }
}

// UI Class: Handle UI Tasks
class UI {
  static displayMeals() {
    console.log("In displayMeals");
    UI.hideRecipes();
    Store.getMeals();
  }

  static addMealToList(meal) {
    const list = document.querySelector("#meal-list");

    const row = document.createElement("tr");
    console.log(meal);
    row.innerHTML = `
   <td>${meal.name}</td>
   <td>${meal.description}</td>
   <td><input type="button" id="addRecipes" class="btn btn-success btn-block" value="Add Recipes"></input><ul id="recipesContainer"></ul></td>
   <td><a href="#" class="btn btn-danger btn-sm delete" id="killMeal">X</a></td>
 `;
    list.appendChild(row);
  }
  static addRecipeToList(recipe, mealNode) {
    //was ingredient
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
    // document.querySelector("#recipeItem").value = "";
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
  static getMeals(callback) {
    //TODO: add ajax instad of local storage
    //     let meals;
    //     if (localStorage.getItem("meals") === null) {
    //         meals = [];
    //     } else {
    //         meals = JSON.parse(localStorage.getItem("meals"));
    //     }
    //
    //     return meals;
    // }
    console.log("In get meals");
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        recipes = JSON.parse(xhr.responseText);

        // recipes = [];
        callback(); // calls back to line 18
      }

      xhr.open("GET", "/meals", true);
      xhr.send();
    };
    console.log("After get meals.");
  }

  static addMeal(meal, callback) {
    //TODO. AJAX call to controller - add the recipe to meal
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        console.log("YAY!!! after added meal.");
        callback();
        //html changes -- how to show recipe is added
      } else {
        console.log("ready state is - " + xhr.readyState);
        console.log("status code is - " + xhr.status);
        console.log("status code is - " + xhr.statusText);
      }
    };

    xhr.open("POST", "/meals/addMeal", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    //xhr.send(JSON.stringify(recipe));
    xhr.send(
      JSON.stringify({
        //object literal  - matches naming in Java getters and setters in Recipe.java
        recipeName: meal.name,
        recipeDescription: meal.description,
      })
    );
  }

  static removeMeal(mealName, callback) {
    const meals = Store.getMeals();

    //removing a meal
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        //recipe.ingredients.push(ingredient); // push = add in js
        console.log("Meal was removed");
        callback();
      }
    };

    xhr.open("POST", "/meals/remove/" + mealName, true);
    xhr.send(
      JSON.stringify({
        //object literal  - matches naming in Java getters and setters in Recipe.java
        mealsName: mealName,
      })
    );
  }

  static addRecipe(recipe, mealName, callback) {
    //TODO. AJAX call to controller - add the ingredient to recipe
    console.log("Here in addRecipe -- " + recipe);
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        //recipe.ingredients.push(ingredient); // push = add in js
        console.log("Recipe was added");
        callback();
      }
    };

    xhr.open("POST", "/meals/" + 1 + "/recipes", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(recipe); //was already a string...don't need to stringify
  }

  static removeRecipe(recipe, meal) {
    //TODO. AJAX call to controller - remove the ingredient from recipe
  }

  static getRecipes(meal) {
    //TODO. AJAX call to controller - get the ingredient stored for recipe

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        let recipes = JSON.parse(xhr.responseText);

        meal.recipes = recipes; //sets ingredients to this recipe
      }
    };
    xhr.open("GET", "/meals/" + meal.id + "/recipes", true);
    xhr.send();
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
  console.log("In add Receipe event.");
  const recipeItem = document.querySelector("#recipeItem").value;
  console.log("recipe item = " + recipeItem);

  const mealID = UI.getMealID();
  console.log("mealID= " + mealID);

  const mealNode = UI.getMealNodeFromID(mealID);
  console.log("node is ");
  console.log(mealNode);

  Store.addRecipe(recipeItem, mealID, function () {
    console.log("In add Recipe");
    //saves to API

    // Add Ingredient to UI
    UI.addRecipeToList(recipeItem, mealNode);

    // Show success message
    UI.showAlert("Recipe Added", "success");

    // Clear fields
    UI.clearFields();
  });
});

// Event: Add a Meal
document.querySelector("#next").addEventListener("click", (e) => {
  console.log("click");

  // Prevent actual submit

  e.preventDefault();
  // Get form values
  const name = document.querySelector("#title").value;
  console.log("name " + name);
  const description = document.querySelector("#description").value;
  console.log("description " + description);
  // Validate
  if (name === "" || description === "") {
    UI.showAlert("Please fill in all fields", "danger");
  } else {
    // Instatiate meal
    const meal = new Meal(name, description);
    // Add Meal to UI
    Store.addMeal(meal, function () {
      // Show success message
      UI.showAlert("Meal Added", "success");

      // Add Recipe to UI
      UI.addMealToList(meal); // was outside -- moved inside here so it happens after the recipe is sent to server

      // Clear fields
      UI.clearFields(); // now only works if this was successfully added to the server because it's a callback...?
    });
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
