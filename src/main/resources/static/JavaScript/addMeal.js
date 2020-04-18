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
    UI.hideRecipes();
    Store.getMeals(function (results) {
      results = JSON.parse(results);
      for (let key in results) {
        const row = UI.addMealToList(results[key]);
        UI.displayRecipes(results[key].recipes,row);
        //Store.getRecipes(results[key], displayRecipes);
      }
    });
  }
  static displayRecipes(recipes,mealNode){
    //get mealNode from mealName
    for (let key in recipes) {
      UI.addRecipeToList(recipes[key].name,mealNode);
    }
  }
  static getAllRecipes() {
    Store.getAllRecipes(function (results) {
      for (let key in results) {
        UI.addRecipeToDropdown(results[key]);
      }
    });
  }

  static addRecipeToDropdown(recipe) {
    const list = document.querySelector("#items");
    const recipeOption = document.createElement("option");
    recipeOption.text = recipe.id;
    recipeOption.value = recipe.name;
    list.appendChild(recipeOption);
  }
  static addMealToList(meal) {
    const list = document.querySelector("#meal-list");

    const row = document.createElement("tr");
    row.innerHTML = `
   <td>${meal.name}</td>
   <td>${meal.description}</td>
   <td><input type="button" id="addRecipes" class="btn btn-success btn-block" value="Add Recipes"></input><ul id="recipesContainer"></ul></td>
   <td><a href="#" class="btn btn-danger btn-sm delete" id="killMeal">X</a></td>
 `;
    list.appendChild(row);
    return row;
  } // mimic where we add meals to show recipes inide each meal on load?

  static addRecipeToList(recipe, mealNode) {
    //was ingredient
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
//TO DO -- consider changing this to getMealNodeFromMealName
//OR updating all to look for it by id
    const mealList = document.getElementById("meal-list").childNodes;
    var matchedNode;
    for (var i = 0; i < mealList.length; i++) {
      if (mealList[i].firstChild.nextSibling.textContent == mealID) {
      
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
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        callback(xhr.response); // calls back to line 18
      }
    };

    xhr.open("GET", "/meals/findAllMeals", true);
    xhr.send();
  }

  static addMeal(meal, callback) {
    //TODO. AJAX call to controller - add the recipe to meal
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        callback();
      }
    };

    xhr.open("POST", "/meals/addMeal", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(meal));
  }

  static removeMeal(mealName, callback) {
    const meals = Store.getMeals();

    //removing a meal
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        //recipe.ingredients.push(ingredient); // push = add in js
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

  static getAllRecipes(callback) {
 
    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        let recipes = JSON.parse(xhr.responseText);
        callback(recipes);
      }
    };
    xhr.open("GET", "/recipes/findAllRecipes", true);
    xhr.send();
  }

  static addRecipe(recipeName, mealID, callback) {
    //TODO Add recipe to meal object
   let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
        //recipe.ingredients.push(ingredient); // push = add in js
        let recipe = JSON.parse(xhr.responseText);
        callback();
      }
    };

    xhr.open("POST", "/meals/" + mealID + "/recipes", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(recipeName); //was already a string...don't need to stringify
  }

  static removeRecipe(recipe, meal) {
    //TODO. AJAX call to controller - remove the ingredient from recipe
  }

}

// Event: Display Meals
document.addEventListener("DOMContentLoaded", (e) => {
  UI.displayMeals();
  UI.getAllRecipes();
});

//Event: Back to Meals (from recipes)
document
  .querySelector("#show-meals-button")
  .addEventListener("click", UI.showMealsHideRecipes);

// Event: Add an Recipe
document.querySelector("#addRecipe").addEventListener("click", (e) => {
  const recipeItem = document.querySelector("#recipeItem").value;
  const mealID = UI.getMealID();
  const mealNode = UI.getMealNodeFromID(mealID);

  Store.addRecipe(recipeItem, mealID, function () {
 
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
  // Prevent actual submit
 e.preventDefault();
  // Get form values
  const name = document.querySelector("#title").value;
  const description = document.querySelector("#description").value;
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
    // Show success message
    UI.showAlert("Meal Removed", "success");
  } else {
    //add recipes
    UI.showRecipesHideMeals();
    //update the form's hidden field so we know which row was passed in...
    document.getElementById("mealID").value = titleFromRow;
  }
});
