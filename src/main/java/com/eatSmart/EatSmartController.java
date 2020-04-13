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

<<<<<<< HEAD


=======
>>>>>>> dev
	@Resource

	private MealRepository mealRepo;


<<<<<<< HEAD



=======
>>>>>>> dev
	@RequestMapping("/recipe")

	public String findOneRecipe(@RequestParam(value="id")long id, Model model) throws RecipeNotFound {

		Optional<Recipe> recipe = recipeRepo.findById(id);

<<<<<<< HEAD


=======
>>>>>>> dev
		if(recipe.isPresent()) {

			model.addAttribute("recipeModel",recipe.get());
<<<<<<< HEAD

=======
>>>>>>> dev
			return "recipe";

		}

<<<<<<< HEAD


=======
>>>>>>> dev
	 	throw new RecipeNotFound();



<<<<<<< HEAD




	}



	@RequestMapping("/show-recipes")

=======
	}

	@RequestMapping("/recipes")
>>>>>>> dev
	public String findAllRecipes(Model model) {

		model.addAttribute("recipesModel", recipeRepo.findAll());

		return("recipes");

	}





<<<<<<< HEAD






	@RequestMapping("/meal")

=======
	@RequestMapping("/meal")
>>>>>>> dev
	public String findOneMeal(@RequestParam(value="id")long id, Model model) throws RecipeNotFound {



		Optional<Meal> meal = mealRepo.findById(id);

<<<<<<< HEAD


=======
>>>>>>> dev
		if(meal.isPresent()) {

			model.addAttribute("mealModel",meal.get());
<<<<<<< HEAD

=======
>>>>>>> dev
			return "meal";

		}

<<<<<<< HEAD


	 	throw new RecipeNotFound();	



	}





	@RequestMapping("/show-meals")

=======
	 	throw new RecipeNotFound();	

	}


	@RequestMapping("/meals")
>>>>>>> dev
	public String findAllMeals(Model model) {

		model.addAttribute("mealsModel", mealRepo.findAll());

		return("meals");

	}



<<<<<<< HEAD




=======
>>>>>>> dev
	@RequestMapping({"/homepage", "", "/"})

	public String showHomePage(){
<<<<<<< HEAD

		return "homepage";

	}





=======
		return "homepage";
	}


>>>>>>> dev
	@RequestMapping("/team-page")

	public String showTeamPage(){
<<<<<<< HEAD

		return "team-page";

=======
		return "team-page";
>>>>>>> dev
	}




<<<<<<< HEAD





=======
>>>>>>> dev
	@RequestMapping("/calendar")

	public String showCalendar(){
<<<<<<< HEAD

		return "calendar";

	}





=======
		return "calendar";
	}


>>>>>>> dev
	@RequestMapping("myaccount")

	public String showMyAccount(Model model){
<<<<<<< HEAD

		return "myaccount";

=======
		return "myaccount";
>>>>>>> dev
	}




<<<<<<< HEAD





=======
>>>>>>> dev
	@RequestMapping("/add-meal")

		public String addMeal(String mealName, String mealDescription, String recipeName, String recipeDescription) {

		Recipe recipe = recipeRepo.findByName(recipeName);

		if(recipe==null) {

			recipe = new Recipe(recipeName, recipeDescription);

			recipe = recipeRepo.save(recipe);

		}

		Meal newMeal = mealRepo.findByName(mealName);


<<<<<<< HEAD



		if(newMeal==null) {

														 //Object

=======
		if(newMeal==null) {
														 //Object
>>>>>>> dev
			newMeal = new Meal(mealName, mealDescription,recipe);

			mealRepo.save(newMeal);

		}

<<<<<<< HEAD


=======
>>>>>>> dev
			//page refresh, no spaces

		return"redirect:/meals";

<<<<<<< HEAD


	}





=======
	}


>>>>>>> dev
	@RequestMapping("/delete-meal")

	public String deleteMealByName(String mealName) {

		if(mealRepo.findByName(mealName) != null) {

			Meal deletedMeal = mealRepo.findByName(mealName);

			mealRepo.delete(deletedMeal);

		}

<<<<<<< HEAD


=======
>>>>>>> dev
				//page refresh, no spaces

				return"redirect:/meals";


<<<<<<< HEAD



	}





=======
	}


>>>>>>> dev
	@RequestMapping("/add-recipe")

	public String addRecipe(String recipeName, String recipeDescription, String ingredientName) {

		Recipe recipe = recipeRepo.findByName(recipeName);

		if(recipe==null) {

			recipe = new Recipe(recipeName, recipeDescription);

			recipe = recipeRepo.save(recipe);

		}

<<<<<<< HEAD


		return"redirect:/recipes";



=======
		return"redirect:/recipes";

>>>>>>> dev
	}



	public String deleteRecipeByName(String recipeName) {

		if(recipeRepo.findByName(recipeName) != null) {

			Recipe deletedRecipe = recipeRepo.findByName(recipeName);

			recipeRepo.delete(deletedRecipe);

		}

<<<<<<< HEAD


=======
>>>>>>> dev
				//page refresh, no spaces

				return"redirect:/recipes";

<<<<<<< HEAD


	}





=======
	}


>>>>>>> dev
	@RequestMapping("/sorted-meals")

	public String sortCourses(Model model ) {

		model.addAttribute("meals", mealRepo.findAllByOrderByNameAsc());

		return "meals"; // returning temp instead of endpoint


<<<<<<< HEAD



=======
>>>>>>> dev
	}
















<<<<<<< HEAD

















}
=======
}
>>>>>>> dev
