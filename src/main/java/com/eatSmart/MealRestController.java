package com.eatSmart;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/meals")
public class MealRestController {
	@Resource
	private RecipeRepository recipeRepo;
	
	@Resource
	private MealRepository mealRepo;
	
	@RequestMapping("")
	public Iterable<Meal> findAllMeals() {
		return mealRepo.findAll();
	}
	
	@RequestMapping("/{mealName}/recipes")
	public Collection<Recipe> findAllRecipesByMeal(@PathVariable(value="mealName") String mealName) {
		Meal meal = mealRepo.findByNameIgnoreCaseLike(mealName);
		return recipeRepo.findByMealsContains(meal);
	}
}
