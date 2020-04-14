package com.eatSmart;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;




@RunWith(SpringJUnit4ClassRunner.class) 
@DataJpaTest
public class JPAMappingTest {
	
	@Resource
	private TestEntityManager entityManager;
	
	
	@Resource
	private RecipeRepository recipeRepo;
	
	
	@Resource
	private MealRepository mealRepo;
	
	
//	@Resource IngredientRepository ingredientRepo;
	
	
	@Test
	public void shouldSaveandLoadRecipe() {
		Recipe recipe = recipeRepo.save(new Recipe("name","description"));
		long recipeId = recipe.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Recipe> result = recipeRepo.findById(recipeId);
		recipe = result.get();
		assertThat(recipe.getRecipeName(),is("name"));
		
		
	}
	
	
	
	@Test
	public void shouldGenerateRecipeId() {
		Recipe recipe = recipeRepo.save(new Recipe("name","description"));
		long recipeId = recipe.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(recipeId, is(greaterThan(0L)));
	}
	
	
	@Test
	public void shouldSaveandLoadMeal() {
		Meal meal = mealRepo.save(new Meal("name","description"));
		long mealId = meal.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Meal> result = mealRepo.findById(mealId);
		meal = result.get();
		assertThat(meal.getMealName(),is("name"));
		
		
	}
	
	
	@Test
	public void shouldGenerateMealId(){
		Meal meal = mealRepo.save(new Meal("name","description"));
		long mealId = meal.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(mealId, is(greaterThan(0L)));
	}
	

	@Test
	public void shouldEstablishMealToReviewRecipe(){
		
		Recipe recipeOne = recipeRepo.save(new Recipe("nameOne", "description"));
		Recipe recipeTwo = recipeRepo.save(new Recipe("nameTwo","description"));
		
		Meal meal = mealRepo.save(new Meal("name", "description", recipeOne, recipeTwo));
		long mealId = meal.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		Optional<Meal>result = mealRepo.findById(mealId); //finding specific review we just created
		meal = result.get(); // getting that specific review and assigning it to the variable review 
		
		assertThat(meal.getRecipes(), containsInAnyOrder(recipeOne, recipeTwo));
		
	}
	
	
	@Test
	public void shouldFindMealsForRecipe() {
		Recipe recipeOne = recipeRepo.save(new Recipe("nameOne", "description"));
		
		Meal meal = mealRepo.save(new Meal("name", "description", recipeOne));
		

		entityManager.flush();  
		entityManager.clear();
		
		Collection<Meal> mealForRecipe = mealRepo.findByRecipesContains(recipeOne);
		
		assertThat(mealForRecipe, containsInAnyOrder(meal));
		
	}
	
	
	@Test
	public void shouldFindMealsForRecipeId() {
		Recipe recipe = recipeRepo.save(new Recipe("nameOne", "description"));
		long recipeId = recipe.getId();
		Meal meal = mealRepo.save(new Meal("name", "description", recipe));
		

		entityManager.flush();  
		entityManager.clear();
		
		Collection<Meal> mealForRecipe = mealRepo.findByRecipesId(recipeId);
		
		assertThat(mealForRecipe, containsInAnyOrder(meal));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
