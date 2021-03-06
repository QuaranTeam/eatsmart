package com.eatSmart;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
@Entity
public class Meal {
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private String description;
	
	@ManyToMany
	private Collection<Recipe> recipes;
	
	
	@ManyToOne
	private User user;
	
	//getters
	public long getId() {
		return id;
	}
	public String getMealName() {
		return getName();
	}
	public String getDescription() {
		return description;
	}

	public Collection<Recipe> getRecipes(){
		return recipes;
	}
	
	public void removeRecipe(Recipe recipeToRemove) {
		// TODO Auto-generated method stub
		
	}
	
	//default constructor
	public Meal() {
	}
	public Meal(String name, String description, Recipe... recipes) {
		this.setName(name);
		this.description = description;
		this.recipes = new HashSet<>(Arrays.asList(recipes));
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
		Meal other = (Meal) obj;
		if (id != other.id)
			return false;
		return true;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}