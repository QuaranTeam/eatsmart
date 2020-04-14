package com.eatsmart;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class Meal {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	//need to make a Recipe class
	private Collection<Recipe> recipes;
	//getters
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	//need to make a Recipe class
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Meal other = (Meal) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}