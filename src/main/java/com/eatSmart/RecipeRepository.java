package com.eatSmart;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Recipe findByName(String recipeName);

	Collection<Recipe> findByMealsContains(Meal meal);

	Object findAllByOrderByNameAsc();

}
