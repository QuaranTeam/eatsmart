package com.eatSmart.Controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatSmart.Ingredient;
import com.eatSmart.IngredientRepository;
import com.eatSmart.Meal;
import com.eatSmart.MealRepository;
import com.eatSmart.Recipe;
import com.eatSmart.RecipeRepository;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeRestController {

	@Resource
	private IngredientRepository ingredientRepo;

	@Resource
	private RecipeRepository recipeRepo;

	@Resource
	private MealRepository mealRepo;

	// @RequestMapping("")

	@GetMapping(value = "/findAllRecipes")
	public Iterable<Recipe> findAllRecipes() {
		return recipeRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addRecipe")
	@ResponseBody
	public Recipe addRecipe(@RequestBody Recipe recipe) {
		Recipe myRecipe = recipeRepo.save(recipe);
		return myRecipe;
	}

	@RequestMapping("/{id}/ingredients")
	public Iterable<Ingredient> findAllIngredients(@PathVariable long id) {

		Optional<Recipe> recipe = recipeRepo.findById(id);

		Collection<Ingredient> ingredients = recipe.get().getIngredients();

		return ingredients;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{name}/ingredients")
	public Ingredient addIngredient(@PathVariable String name, @RequestBody String ingredientName) {

		Ingredient ingredient = ingredientRepo.findByName(ingredientName);
		
		if(ingredient == null) {
			ingredient = new Ingredient(ingredientName, 1);
			ingredientRepo.save(ingredient);
		}
				
		Recipe recipe = recipeRepo.findByName(name);

		recipe.getIngredients().add(ingredient);
		ingredient.addRecipe(recipe);
		recipeRepo.save(recipe);

		return ingredient;

	}

	@RequestMapping("/{id}")
	public Optional<Recipe> findOneRecipe(@PathVariable long id) {
		return recipeRepo.findById(id);
	}

	@RequestMapping(path = "/remove/{recipeName}", method = RequestMethod.POST)
	public String deleteRecipeByName(@PathVariable String recipeName) {

		Recipe recipeToRemove = recipeRepo.findByName(recipeName);
		if (recipeToRemove != null) {
			recipeRepo.delete(recipeToRemove);
			return "This recipe was deleted: " + recipeToRemove.toString();
		} else {
			return "Oh no! This recipe was not found: " + recipeName;
		}
	}

	@RequestMapping("/meals/{mealName}")
	public Collection<Recipe> findAllRecipesByMeal(@PathVariable(value = "mealName") String mealName) {
		Meal meal = mealRepo.findByNameIgnoreCaseLike(mealName);
		return recipeRepo.findByMealsContains(meal);
	}
}