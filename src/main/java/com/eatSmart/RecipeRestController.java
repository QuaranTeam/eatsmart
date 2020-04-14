package com.eatSmart;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	

	@RequestMapping("/{id}/ingredients")
	public Iterable<Ingredient> findAllIngredients(@PathVariable long id) {
		
		Optional<Recipe> recipe = recipeRepo.findById(id);
		
		Collection<Ingredient> ingredients = recipe.get().getIngredients();
				
		return ingredients;
	}
	
	
	
	
	@RequestMapping("/{id}")
	public Optional<Recipe> findOneRecipe(@PathVariable long id) {
		return recipeRepo.findById(id);
	}
	
	@RequestMapping("/meals/{mealName}")
	public Collection<Recipe> findAllRecipesByMeal(@PathVariable(value="mealName") String mealName) {
		Meal meal = mealRepo.findByNameIgnoreCaseLike(mealName);
		return recipeRepo.findByMealsContains(meal);
	}
}