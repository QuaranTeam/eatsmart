package com.eatSmart;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @ManyToOne
	private Collection<Meal> meals;
    @ManyToOne
	private Collection<Recipe> recipes;
    public Long getId() {
    	return id;
    }
    public String getFirstName() {
    	return firstName;
    }
    public String getLastName() {
    	return lastName;
    }
    public String getUsername() {
    	return username;
    }
    public void setId(Long id) {
    	this.id = id;
    }
    public String getPassword() {
    	return password;
    }
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }
    public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
    public void setUsername(String username) {
    	this.username = username;
    }
    public void setPassword(String password) {
    	this.password = password;
    }
    public Collection<Meal> getMeals(){
		return meals;
	}
    public Collection<Recipe> getRecipes(){
		return recipes;
	}
    public User() {}
    public User(String firstName, String lastName, String username, String password, Recipe[] recipes, Meal[] meals) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.meals = new HashSet<>(Arrays.asList(meals));
        this.recipes = new HashSet<>(Arrays.asList(recipes));
    }
   
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", username='" + username + '\'' +
            ", password='" + "*********";
    }
}