package com.eatSmart;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;


public class EatSmartControllerTest {
	
	@InjectMocks
	private EatSmartController underTest;
	
	@Mock
	private Recipe recipeOne;
	
	@Mock
	private Recipe recipeTwo;
	
	@Mock
	private RecipeRepository recipeRepo;
	
	
	@Mock
	private Meal mealOne;

	@Mock
	private Meal mealTwo;
	
	
	@Mock
	private MealRepository mealRepo;
	
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
	}
	
	@Test
	public void shouldAddSingleRecipeToModel() throws RecipeNotFound {
		long arbId = 1;
		when(recipeRepo.findById(arbId)).thenReturn(Optional.of(recipeOne));
		underTest.findOneRecipe(arbId, model);
		verify(model).addAttribute("recipeModel",recipeOne);	
		
	}
	
	
	@Test
	public void shouldAddAllRecipesToModel(){
		Collection<Recipe> allRecipes = Arrays.asList(recipeOne,recipeTwo);
		
		when(recipeRepo.findAll()).thenReturn(allRecipes);
		
		underTest.findAllRecipes(model);
		verify(model).addAttribute("recipesModel", allRecipes);

		
	}
	
	
	
	@Test
	public void shouldAddSingleMealToModel() throws MealNotFound, RecipeNotFound {
		long arbId = 1;
		when(mealRepo.findById(arbId)).thenReturn(Optional.of(mealOne));
		underTest.findOneMeal(arbId, model);
		verify(model).addAttribute("mealModel",mealOne);	
		
	}
	
	
	@Test
	public void shouldAddAllMealsToModel(){
		Collection<Meal> allMeals = Arrays.asList(mealOne,mealTwo);
		
		when(mealRepo.findAll()).thenReturn(allMeals);
		
		underTest.findAllMeals(model);
		verify(model).addAttribute("mealsModel", allMeals);

		
	}
	
	
	@Test
	public void shouldAddAdditionalMealToModel() {
		String recipeName = "recipe name";
		String recipeDescription = "recipe description";
		Recipe newRecipe = recipeRepo.findByName(recipeName);
		String mealName = "new meal";
		String mealDescription = "new meal description";
		
		underTest.addMeal(mealName,mealDescription,recipeName,recipeDescription);
		
		Meal newMeal = new Meal(mealName,mealDescription,newRecipe);
		when(mealRepo.save(newMeal)).thenReturn(newMeal);
		
		
	}
	
	
	
	@Test
	public void shouldRemoveMealFromModelByName() {
		String mealName = mealOne.getMealName();
		when(mealRepo.findByName(mealName)).thenReturn(mealOne);
		underTest.deleteMealByName(mealName);
		verify(mealRepo).delete(mealOne);
	
		
		
	}
	
	
	
	@Test
	public void shouldAddAdditionalRecipeToModel() {
		String recipeName = "recipe name";
		String recipeDescription = "recipe description";
		String ingredientName = "ingredients";
		Recipe newRecipe = recipeRepo.findByName(recipeName);
		
		underTest.addRecipe(recipeName,recipeDescription,ingredientName);
		
		when(recipeRepo.save(newRecipe)).thenReturn(newRecipe);
		
		
	}
	
	
	@Test
	public void shouldRemoveRecipeFromModelByName() {
		String recipeName = recipeOne.getRecipeName();
		when(recipeRepo.findByName(recipeName)).thenReturn(recipeOne);
		underTest.deleteRecipeByName(recipeName);
		verify(recipeRepo).delete(recipeOne);
	
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
