package com.eatSmart;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;

public class EatSmartPopulator implements CommandLineRunner {
	
	@Resource
	private RecipeRepository recipeRepo;
	
	@Resource
	private MealRepository mealRepo;
	
	@Resource	
	private IngredientRepository ingredientRepo;
	


	@Override
	public void run(String... args) throws Exception {
		
		
		
		Recipe recipe = new Recipe("this recipe","that recipe");
		
		recipe = recipeRepo.save(recipe);
	}
	
	
	
	
	
	
	
	
	
	

}
