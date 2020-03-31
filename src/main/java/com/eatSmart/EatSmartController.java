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
	
	@RequestMapping("recipes.html")
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
	
	
	@RequestMapping("meals.html")
	public String findAllMeals(Model model) {
		model.addAttribute("mealsModel", mealRepo.findAll());
		return("meals");
	}
	
	
	@RequestMapping("team-page.html")
	public String showTeamPage(){
		return "team-page";	
	}
	
	
	@RequestMapping("login.html")
	public String showLogIn(){
		return "login";	
	}
	
	
	@RequestMapping("calendar.html")
	public String showCalendar(){
		return "calendar";	
	}
	
	
	@RequestMapping("myaccount.html")
	public String showMyAccount(){
		return "myaccount";	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
