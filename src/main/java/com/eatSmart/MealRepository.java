package com.eatSmart;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal, Long> {

	Collection<Meal> findByRecipesContains(Recipe recipes);

	Collection<Meal> findByRecipesId(long recipeId);

	Meal findByName(String mealName);

	Meal findAllByOrderByNameAsc();

}
