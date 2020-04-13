package com.eatSmart;


import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private int amount;

	@JsonIgnore
	@ManyToMany(mappedBy = "ingredients")
	private Collection <Recipe> recipe;

	public String getIngredientName() {
		return name;
	}
	public int getIngredientAmount() {
		return amount;
	}

	public Ingredient() {

	}

	public Ingredient(String name, int amount) {
		this.name = name;
		this.amount = amount;
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
		Ingredient other = (Ingredient) obj;
		if (id != other.id)
			return false;
		return true;
	}


	
}
