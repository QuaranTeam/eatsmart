package com.eatSmart;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Calendar {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	//need to make a Recipe class
	private Collection<Meal> meals;
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
	public Collection<Meal> getRecipes(){
		return meals;
	}
	//default constructor
	public Calendar() {
	}
	public Calendar(String name, String description, Meal... meals) {
		this.name = name;
		this.description = description;
		this.meals = new HashSet<>(Arrays.asList(meals));
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
			if (other.getDescription() != null)
				return false;
		} else if (!description.equals(other.getDescription()))
			return false;
		if (id != other.getId())
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}
}
	


