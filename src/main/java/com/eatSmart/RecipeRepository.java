package com.eatSmart;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Collection<Recipe> findByMealsContains(Meal meal);

	Collection<Recipe> findByMealsId(long mealId);
		
	Recipe findByName(String recipeName);

	Recipe findByNameIgnoreCaseLike(String recipeName);

	Object findAllByOrderByNameAsc();


}
