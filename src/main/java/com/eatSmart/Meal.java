package com.eatSmart;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Meal {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	
	@ManyToMany
	private Collection<Recipe> recipes;
	//getters
	public long getId() {
		return id;
	}
	public String getMealName() {
		return name;
	}
	public String getDescription() {
		return description;
	}

	public Collection<Recipe> getRecipes(){
		return recipes;
	}
	//default constructor
	public Meal() {
	}
	public Meal(String name, String description, Recipe... recipes) {
		this.name = name;
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
		// TODO Auto-generated method stub
		return name;
	}
}