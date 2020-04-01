package com.eatSmart;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EatSmartController {

	@Resource
	private RecipeRepository recipeRepo;
	
	@Resource
	private MealRepository mealRepo;
	
	
	@RequestMapping("/recipe")
	public String findOneRecipe(@RequestParam(value="id")long id, Model model) throws RecipeNotFound {
		Optional<Recipe> recipe = recipeRepo.findById(id);
		
		if(recipe.isPresent()) {
			model.addAttribute("recipeModel",recipe.get());
		
		
			return "recipe";
		}
		
	 	throw new RecipeNotFound();
		
		
		
	}
	
	@RequestMapping("/recipes")
	public String findAllRecipes(Model model) {
		model.addAttribute("recipesModel", recipeRepo.findAll());
		return("recipes");
	}

	@RequestMapping("/meal")	
	public String findOneMeal(@RequestParam(value="id")long id, Model model) throws RecipeNotFound {

		Optional<Meal> meal = mealRepo.findById(id);
		
		if(meal.isPresent()) {
			model.addAttribute("mealModel",meal.get());
		
			return "meal";
		}
		
	 	throw new RecipeNotFound();	
		
	}
	
	
	@RequestMapping("/meals")
	public String findAllMeals(Model model) {
		model.addAttribute("mealsModel", mealRepo.findAll());
		return("meals");
	}
	
	
	
	@RequestMapping("/homepage")
	public String showHomePage(){
		return "homepage";	
	}
	
	
	@RequestMapping("/team-page")
	public String showTeamPage(){
		return "team-page";	
	}
	
	
	@RequestMapping("/login")
	public String showLogIn(){
		return "login";	
	}
	
	
	@RequestMapping("/calendar")
	public String showCalendar(){
		return "calendar";	
	}
	
	
	@RequestMapping("myaccount")
	public String showMyAccount(Model model){
		return "myaccount";	
	}
	
	
	
	
	@RequestMapping("/add-meal")
		public String addMeal(String mealName, String mealDescription, String recipeName, String recipeDescription) {
		Recipe recipe = recipeRepo.findByName(recipeName);
		if(recipe==null) {
			recipe = new Recipe(recipeName, recipeDescription);
			recipe = recipeRepo.save(recipe);
		}
		Meal newMeal = mealRepo.findByName(mealName);
		
		
		if(newMeal==null) {
														 //Object		
			newMeal = new Meal(mealName, mealDescription,recipe);
			mealRepo.save(newMeal);
		}
		
			//page refresh, no spaces
		return"redirect:/meals";
		
	}

	
	@RequestMapping("/delete-meal")
	public String deleteMealByName(String mealName) {
		if(mealRepo.findByName(mealName) != null) {
			Meal deletedMeal = mealRepo.findByName(mealName);
			mealRepo.delete(deletedMeal);
		}
		
				//page refresh, no spaces
				return"redirect:/meals";
		
		
	}
	
	
	@RequestMapping("/add-recipe")
	public String addRecipe(String recipeName, String recipeDescription) {
		Recipe recipe = recipeRepo.findByName(recipeName);
		if(recipe==null) {
			recipe = new Recipe(recipeName, recipeDescription);
			recipe = recipeRepo.save(recipe);
		}
	
		return"redirect:/recipes";
		
	}

	public String deleteRecipeByName(String recipeName) {
		if(recipeRepo.findByName(recipeName) != null) {
			Recipe deletedRecipe = recipeRepo.findByName(recipeName);
			recipeRepo.delete(deletedRecipe);
		}
		
				//page refresh, no spaces
				return"redirect:/recipes";
		
	}
	
	
	@RequestMapping("/sorted-meals")
	public String sortCourses(Model model ) {
		model.addAttribute("meals", mealRepo.findAllByOrderByNameAsc());
		return "meals"; // returning temp instead of endpoint
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
