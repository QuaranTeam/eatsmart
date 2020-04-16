package com.eatSmart.Controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eatSmart.Recipe;
import com.eatSmart.RecipeRepository;
import com.eatSmart.Meal;
import com.eatSmart.MealRepository;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/meals")
public class MealRestController {

	@Resource
	private RecipeRepository recipeRepo;

	@Resource
	private MealRepository mealsRepo;



	@RequestMapping("")
	public Iterable<Meal> findAllMeals() {
		return mealsRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	@ResponseBody
	public Meal addMeal(@RequestBody Meal meals) {
		mealsRepo.save(meals);
		
		return meals;
	}

	@RequestMapping("/{id}/recipes")
	public Iterable<Recipe> findAllRecipes(@PathVariable long id) {

		Optional<Meal> meals = mealsRepo.findById(id);

		Collection<Recipe> recipes = meals.get().getRecipes();

		return recipes;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{name}/recipes")
	public Recipe addMeal(@PathVariable String name, @RequestBody String recipeName, String recipeDescription) {

		Recipe recipe = new Recipe(recipeName, recipeDescription);
		recipeRepo.save(recipe);
		
//		Recipe recipe = recipeRepo.findByName(recipeName); //look for an existing recipe
//		 if(recipe == null) {
//				Recipe newRecipe = new Recipe(recipeName, 1);  //if it's not there, make a new one
//				recipeRepo.save(newRecipe);
//		 }
//		
		Meal meals = mealsRepo.findByName(name);

		meals.getRecipes().add(recipe);
		recipe.addMeal(meals);
		mealsRepo.save(meals);
		
		return recipe;

	}

	@RequestMapping("/{id}")
	public Optional<Meal> findOneMeal(@PathVariable long id) {
		return mealsRepo.findById(id);
	}

	
	
	
	@RequestMapping(path = "/remove/{mealsName}", method = RequestMethod.POST)
	public void deleteMealByName(@PathVariable String mealsName) {

		Meal mealsToRemove = mealsRepo.findByName(mealsName);
	
		mealsRepo.delete(mealsToRemove);


	}
	
	
	
	
	@RequestMapping("/recipes/{recipeName}")
	public Collection<Meal> findAllMealsByRecip(@PathVariable(value = "recipeName") String recipeName) {
		Recipe recipe = recipeRepo.findByNameIgnoreCaseLike(recipeName);
		return mealsRepo.findByRecipesContains(recipe);
	}
}