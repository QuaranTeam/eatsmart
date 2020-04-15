    let recipes = []; //lets us use the variable recipes throughout the whole page

    // Book Class: Represents a Recipe
    class Recipe {
        constructor(name, description, id) {
            this.name = name;
            this.description = description;
            this.id = id;
            //need ingredient collection here
            this.ingredients = [];
        }
    }

    // UI Class: Handle UI Tasks
    class UI {
        static displayRecipes() {
            UI.hideIngredients();
            Store.getRecipes(function (results) { // took aout recipes becasue it's global now

                results.forEach((recipe) => UI.addRecipeToList(recipe)); //runs after getRecipes -- cahanged "recipes" to "resuts" so it doesn't affect global recipes

            });

        }

        static addRecipeToList(recipe) {
            const list = document.querySelector("#recipe-list");

            const row = document.createElement("tr");

            row.innerHTML = `
      <td>${recipe.name}</td>
      <td>${recipe.description}</td>
      <td><input type="button" id="addIngredients" class="btn btn-success btn-block" value="Add Ingredient"></button><ul id="ingredientsContainer"></ul></td>
      <td><a href="#" class="btn btn-danger btn-sm delete" id="killRecipe">X</a></td>
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
        static addIngredientToList(ingredient, recipeNode) {
            console.log("Ingredent to list");

            const listEl = document.createElement("li");

            listEl.innerText = ingredient;
            //targetNode.nextSibling.appendChild(listEl);
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

        //HELPER function - getRecipeID
        static getRecipeID() {
            const recipeID = document.getElementById("recipeID").value;
            return recipeID;
        }

        static getRecipeNodeFromID(recipeID) {
            //traverse "recipe-list" -> children in a loop. look for text contents of 1st child

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
            //TODO: add ajax instad of local storage
            // let recipes;
            // if (localStorage.getItem("recipes") === null) {
            //     recipes = [];
            // } else {
            //     recipes = JSON.parse(localStorage.getItem("recipes"));
            // }
            // return recipes;

            // let recipes;  ---> done globally at the top
            let xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    recipes = JSON.parse(xhr.responseText);

                    console.log(recipes);
                    // recipes = [];
                    callback(recipes); // calls back to line 18

                };

                xhr.open("GET", "/recipes", true);
                xhr.send();
            }
        }

        static addRecipe(recipe, callback) {
            //TODO: add ajax instad of local storage
            // const recipes = Store.getRecipes();
            // recipes.push(recipe);
            // localStorage.setItem("recipes", JSON.stringify(recipes));

            let xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    callback();
                    //html changes -- how to show recipe is added      
                }
            };

            xhr.open('POST', "/recipes", true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            //xhr.send(JSON.stringify(recipe));
            xhr.send(JSON.stringify({      //object literal  - matches naming in Java getters and setters in Recipe.java
                recipeName: recipe.name,
                recipeDescription: recipe.description
            }));

        }

        static removeRecipe(item) {
            //TODO: add ajax instad of local storage
            const recipes = Store.getRecipes();

            recipes.forEach((recipe, index) => {
                if (recipe.name === item) {
                    recipes.splice(index, 1); //starting position and then delete count
                }
            });

            localStorage.setItem("recipes", JSON.stringify(recipes));
        }

        static addIngredient(ingredient, recipeName) {
            //TODO. AJAX call to controller - add the ingredient to recipe
            let xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    //recipe.ingredients.push(ingredient); // push = add in js      
                    console.log("Ingredient was added");
                }
            };

            xhr.open('POST', "/recipes/" + recipeName + "/ingredients", true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify(ingredient));

        }


        static removeIngredient(ingredient, recipe) {
            //TODO. AJAX call to controller - remove the ingredient from recipe

        }


        static getIngredients(recipe) {
            //TODO. AJAX call to controller - get the ingredient stored for recipe

            let xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    let ingredients = JSON.parse(xhr.responseText);

                    recipe.ingredients = ingredients; //sets ingredients to this recipe

                }
            };

            xhr.open("GET", "/recipes/" + recipe.id + "/ingredients", true);
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
    document.querySelector("#addIngredient").addEventListener("click", (e) => {    // callback for addEventListener

        const ingredientItem = document.querySelector("#ingredientItem").value; // pulls values from the UI fields -- retrieves
        const recipeID = UI.getRecipeID();
        const recipeNode = UI.getRecipeNodeFromID(recipeID);

        // Add Ingredient to store
        Store.addIngredient(ingredientItem, recipeID, function () { //saves to API

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
        // Prevent actual submit

        e.preventDefault();
        // Get form values
        const title = document.querySelector("#title").value;
        const description = document.querySelector("#description").value;

        // Validate
        if (title === "" || description === "") {
            UI.showAlert("Please fill in all fields", "danger");
        } else {
            // Instatiate recipe
            const recipe = new Recipe(title, description);

            // Add Recipe to store
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
            // Remove Recipe from UI
            UI.deleteRecipe(e.target);

            // Remove Recipe from store

            Store.removeRecipe(titleFromRow);
            //TODO make sure removeRecipe also removes the ingredients and the join between recipes and ingredients

            // Show success message
            UI.showAlert("Recipe Removed", "success");
        } else {
            //add ingredients
            UI.showIngredientsHideRecipes();
            //update the form's hidden field so we know which row was passed in...
            document.getElementById("recipeID").value = titleFromRow;

        }
    });