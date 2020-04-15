package com.eatSmart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static java.lang.String.format;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Recipe {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;

	@ManyToMany(mappedBy = "recipes") // Because recipes can be in multiple meals?
	private Collection<Meal> meals;

	@ManyToMany
	private Collection<Ingredient> ingredients;

	// getters
	public long getId() {
		return id;
	}

	public String getRecipeName() {
		return name;
	}

	public String getRecipeDescription() {
		return description;
	}

	public void setRecipeName(String name) {
		this.name = name;
	}

	public void setRecipeDescription(String description) {
		this.description = description;
	}

	public Collection<Ingredient> getIngredients() {
		return ingredients;
	}

	public Collection<String> getMealsUrls() {
		Collection<String> urls = new ArrayList<>();
		if (meals != null) {
			for (Meal t : meals) {
				urls.add(format("/recipes/%d/meals/%s", this.getId(), t.getMealName()));
			}
		}
		return urls;  //if null, returns an empty collection
	}

	// default constructor
	public Recipe() {

	}

	public Recipe(String recipeName, String recipeDescription, Meal meal) {
	}

	public Recipe(String name, String description, Ingredient... ingredients) {
		this.name = name;
		this.description = description;
		this.ingredients = new HashSet<>(Arrays.asList(ingredients));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
