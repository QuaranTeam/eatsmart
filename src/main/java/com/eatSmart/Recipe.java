package com.eatSmart;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
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
	
	@JsonIgnore
	@ManyToMany(mappedBy = "recipes") // Because recipes can be in multiple meals?
	private Collection<Meal> meals;
	
	private Collection<Ingredient> ingredients;
		
	//getters
	public long getId() {
		return id;
	}
	public String getRecipeName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
	public Collection<Ingredient> getIngredients(){
		return ingredients;
	}
	
	//default constructor
	public Recipe() {
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((meals == null) ? 0 : meals.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (meals == null) {
			if (other.meals != null)
				return false;
		} else if (!meals.equals(other.meals))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
