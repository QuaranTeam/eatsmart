package com.eatSmart;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Calendar {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String description;
	


	public Calendar() {
	}
	public Calendar(String name, String description, Meal... meals) {
		this.name = name;
		this.description = description;
	}
}
	


