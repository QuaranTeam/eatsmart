package com.eatSmart;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import courses.models.Course;
import courses.models.Topic;

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

	@RequestMapping("")
	public Iterable<Recipe> findAllRecipes() {
		return recipeRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	@ResponseBody
	public Recipe addRecipe(@RequestBody Recipe recipe) {
		recipeRepo.save(recipe);
		
		return recipe;
	}

	@RequestMapping("/{id}/ingredients")
	public Iterable<Ingredient> findAllIngredients(@PathVariable long id) {

		Optional<Recipe> recipe = recipeRepo.findById(id);

		Collection<Ingredient> ingredients = recipe.get().getIngredients();

		return ingredients;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{name}/ingredients")
	public Ingredient addIngredient(@PathVariable String name, @RequestBody String ingredientName) {

		Ingredient ingredient = new Ingredient(ingredientName, 1);
		ingredientRepo.save(ingredient);
		
//		Ingredient ingredient = ingredientRepo.findByName(ingredientName); //look for an existing ingredient
//		 if(ingredient == null) {
//				Ingredient newIngredient = new Ingredient(ingredientName, 1);  //if it's not there, make a new one
//				ingredientRepo.save(newIngredient);
//		 }
//		
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
	public String deleteRecipeByName(@PathVariable String name, Model model) {

		Optional<Recipe> recipeToRemoveResult = recipeRepo.findByName(name);
		Recipe recipeToRemove = recipeToRemoveResult.get();
		
		//from EatSmart controller
//		if (recipeRepo.findByName(name) != null) {
//			Recipe deletedRecipe = recipeRepo.findByName(name);
//			recipeRepo.delete(deletedRecipe);
//		}
		for (Meal meal : recipeToRemove.getMeals()) {
			meal.removeRecipe(recipeToRemove);
			mealRepo.save(meal);
		}

		recipeRepo.delete(recipeToRemove);

		model.addAttribute("topicsModel", topicRepo.findAll());
		return "partials/topics-list-removed"; 
	}
		
		
		// page refresh, no spaces
		return "redirect:/recipes"; 

	}
	
	
	
	
	@RequestMapping("/meals/{mealName}")
	public Collection<Recipe> findAllRecipesByMeal(@PathVariable(value = "mealName") String mealName) {
		Meal meal = mealRepo.findByNameIgnoreCaseLike(mealName);
		return recipeRepo.findByMealsContains(meal);
	}
}