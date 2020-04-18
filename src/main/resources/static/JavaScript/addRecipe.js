let recipes = []; // lets us use the variable recipes throughout the whole page

class Recipe {
    constructor(name, description, id) {
        this.name = name;
        this.description = description;
        this.id = id;
        // need ingredient collection here
        this.ingredients = [];
    }
}

// UI Class: Handle UI Tasks
class UI {
    static displayRecipes() {
        console.log("In displayRecipes");
        UI.hideIngredients();
        Store.getRecipes(function (results) {
            results = JSON.parse(results);
            console.log("RESULTS callback from getRecipes -->");
            console.log(results);
            console.log(typeof results[0]);

            for (let key in results) {
                UI.addRecipeToList(results[key]);
            }

        });
    }

    static addRecipeToList(recipe) {
        const list = document.querySelector("#recipe-list");

        const row = document.createElement("tr");
        console.log(recipe);
        row.innerHTML = `
      <td>${recipe.name}</td>
      <td>${recipe.description || recipe.recipeDescription || ""}</td>
      <td><input type="button" id="addIngredients" class="btn btn-success btn-block" value="Add Ingredient"></input><ul id="ingredientsContainer"></ul></td>
      <td><a href="#" class="btn btn-danger btn-sm delete" id="killRecipe">X</a></td>
    `;
        list.appendChild(row);
    }
    
    static addIngredientToList(ingredient, recipeNode) {

        console.log("Ingredient added to Recipe list");

        const listEl = document.createElement("li");

        listEl.innerText = ingredient;
        // targetNode.nextSibling.appendChild(listEl);
        const ingredientsContainer = recipeNode.querySelector("#ingredientsContainer");
        ingredientsContainer.appendChild(listEl);
    }

    static deleteRecipe(el) {
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
        const form = document.querySelector("#recipe-form");
        container.insertBefore(div, form);

        // Vanish in 3 seconds
        setTimeout(() => document.querySelector(".alert").remove(), 3000);
    }

    static clearFields() {
        document.querySelector("#title").value = "";
        document.querySelector("#description").value = "";
        document.querySelector("#ingredientItem").value = "";
    }

    static hideIngredients() {
        document.getElementById("ingredients-group").style.display = "none";
    }

    static showIngredients(row) {
        document.getElementById("ingredients-group").style.display = "block";
    }

    static hideRecipes() {
        document.getElementById("recipes-group").style.display = "none";
    }

    static showRecipes() {
        document.getElementById("recipes-group").style.display = "block";
    }

    static swapRecipesAndIngredients() {
        if (document.getElementById("recipes-group").style.display != "none") {
            UI.hideRecipes();
            UI.showIngredients();
        } else {
            UI.showRecipes();
            UI.hideIngredients();
        }
    }

    static showIngredientsHideRecipes() {
        UI.hideRecipes();
        UI.showIngredients();
    }

    static showRecipesHideIngredients() {
        UI.showRecipes();
        UI.hideIngredients();
    }

    // HELPER function - getRecipeID
    static getRecipeID() {
        const recipeID = document.getElementById("recipeID").value;
        return recipeID;
    }

    static getRecipeNodeFromID(recipeID) {
        // traverse "recipe-list" -> children in a loop. look for text contents of 1st child

        const recipeList = document.getElementById("recipe-list").childNodes;
        var matchedNode;
        for (var i = 0; i < recipeList.length; i++) {
            if (recipeList[i].firstChild.nextSibling.textContent == recipeID) {
                console.log("Match found!  " + recipeID);
                matchedNode = recipeList[i];
                break;
            }
        }

        return matchedNode;
    }
}

// Store Class: Handles Storage
class Store {
    static getRecipes(callback) {
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log(xhr.response);
                console.log(xhr.responseText);
                //recipes = JSON.parse(xhr.responseText);
                //console.log(recipes);
                callback(xhr.response); // calls back to line 18
            }
        };

        xhr.open("GET", "/recipes/findAllRecipes", true);
        xhr.send();
        console.log("After get recipes.");
    }

    static addRecipe(recipe, callback) {
        // AJAX call to controller - add the ingredient to recipe
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("YAY!!! after added recipe.");
                callback();
                //html changes -- how to show recipe is added
            } else {
                console.log("ready state is - " + xhr.readyState);
                console.log("status code is - " + xhr.status);
                console.log("status code is - " + xhr.statusText);
            }
        };

        xhr.open("POST", "/recipes/addRecipe", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(recipe));
    }

    static removeRecipe(recipeName, callback) {
        const recipes = Store.getRecipes();

        //removing a meal
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                //recipe.ingredients.push(ingredient); // push = add in js
                console.log("Recipe was removed");
                callback();
            }
        };

        xhr.open("POST", "/recipes/remove/" + recipeName, true);
        xhr.send(
            JSON.stringify({

                recipeName: recipeName,
            })
        );
    }

    static addIngredient(ingredient, recipeName, callback) {
        //AJAX call to controller - add the ingredient to recipe
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                //recipe.ingredients.push(ingredient); // push = add in js
                console.log("Ingredient was added");
                callback();
            }
        };

        xhr.open("POST", "/recipes/" + recipeName + "/ingredients", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(ingredient);
    }


    static removeIngredient(ingredient, recipe) {
        // TODO. AJAX call to controller - remove the ingredient from recipe
    }

    static getIngredients(recipe) {
        // AJAX call to controller - get the ingredient stored for recipe

        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                let ingredients = JSON.parse(xhr.responseText);

                recipe.ingredients = ingredients; //sets ingredients to this recipe
            }
        };
        xhr.open("GET", "/recipes/" + meal.id + "/ingredients", true);
        xhr.send();
    }
}

// Event: Display Recipes
document.addEventListener("DOMContentLoaded", UI.displayRecipes);

//Event: Back to Recipes (from ingredients)
document
    .querySelector("#show-recipes-button")
    .addEventListener("click", UI.showRecipesHideIngredients);

// Event: Add an Ingredient
document.querySelector("#addIngredient").addEventListener("click", (e) => {
    console.log("In add Ingredient event.");
    const ingredientItem = document.querySelector("#ingredientItem").value;
    console.log("ingredinet item = " + ingredientItem);

    const recipeID = UI.getRecipeID();
    console.log("recipeID = " + recipeID);

    const recipeNode = UI.getRecipeNodeFromID(recipeID);
    console.log("node is ");
    console.log(recipeNode);

    Store.addIngredient(ingredientItem, recipeID, function () {
        console.log("In add Ingredient");
        // saves to API

        // Add Ingredient to UI
        UI.addIngredientToList(ingredientItem, recipeNode);

        // Show success message
        UI.showAlert("Ingredient Added", "success");

        // Clear fields
        UI.clearFields();
    });
});

// Event: Add a Recipe
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
        // Instatiate recipe
        const recipe = new Recipe(name, description);
        // Add Recipe to UI
        Store.addRecipe(recipe, function () {
            // Show success message
            UI.showAlert("Recipe Added", "success");

            // Add Recipe to UI
            UI.addRecipeToList(recipe); // was outside -- moved inside here so it happens after the recipe is sent to server

            // Clear fields
            UI.clearFields(); // now only works if this was successfully added to the server because it's a callback...?
        });
    }
});

// Event: Remove a Recipe OR add Ingredients to Recipe
document.querySelector("#recipe-list").addEventListener("click", (e) => {
    row = e.target.parentElement.parentElement;
    titleFromRow = row.getElementsByTagName("td")[0].textContent;

    if (e.target.id == "killRecipe") {
        // Remove Meal from UI
        UI.deleteRecipe(e.target);
    
        // Remove Recipe from store
    
        Store.removeRecipe(titleFromRow);
        // removeRecipe also removes the ingredinets and the join between recipes and ingredinets

        // Show success message
        UI.showAlert("Recipe Removed", "success");
      } else {
        //add recipes
        UI.showIngredientsHideRecipes();
        //update the form's hidden field so we know which row was passed in...
        document.getElementById("recipeID").value = titleFromRow;
      }
    });
