package com.eatSmart;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
	
	
	@OneToMany
	private Collection<Ingredient> ingredients;
		
	//getters
	public long getId() {
		return id;
	}
	public String getRecipeName() {
		return name;
	}
	public String getRecipeDescription() {
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
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
