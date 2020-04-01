package com.eatSmart;

import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	Recipe findByName(String recipeName);

}
