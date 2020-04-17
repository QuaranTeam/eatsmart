package com.eatSmart.Controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
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
	private MealRepository mealRepo;



	@GetMapping(value ="/findAllMeals")
	public Iterable<Meal> findAllMeals() {
		return mealRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addMeal")
	@ResponseBody
	public Meal addMeal(@RequestBody Meal meals) {
		Meal myMeal = mealRepo.save(meals);
		return myMeal; 
	}

	@RequestMapping("/{id}/recipes")
	public Iterable<Recipe> findAllRecipes(@PathVariable long id) {

		Optional<Meal> meals = mealRepo.findById(id);

		Collection<Recipe> recipes = meals.get().getRecipes();

		return recipes;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{name}/recipe")
	public Recipe addRecipe(@PathVariable String name, @RequestBody String recipeName) {

		Recipe recipe = recipeRepo.findByName(recipeName);
		Meal meals = mealRepo.findByName(name);

		meals.getRecipes().add(recipe);
		recipe.addMeal(meals);
		mealRepo.save(meals);
		return recipe;

	}

	@RequestMapping("/{id}")
	public Optional<Meal> findOneMeal(@PathVariable long id) {
		return mealRepo.findById(id);
	}

	
	
	
	@RequestMapping(path = "/remove/{mealsName}", method = RequestMethod.POST)
	public String deleteMealByName(@PathVariable String mealsName) {

		Meal mealsToRemove = mealRepo.findByName(mealsName);
		if(mealsToRemove != null) {
			mealRepo.delete(mealsToRemove);
			return "Heya!" + mealsToRemove.toString();
		}
		else {
			return "Yikes! mealsToRemove isn't found. " + mealsName;
		}
	}
	
	
	
	
	@RequestMapping("/recipes/{recipeName}")
	public Collection<Meal> findAllMealsByRecipe(@PathVariable(value = "recipeName") String recipeName) {
		Recipe recipe = recipeRepo.findByNameIgnoreCaseLike(recipeName);
		return mealRepo.findByRecipesContains(recipe);
	}
}