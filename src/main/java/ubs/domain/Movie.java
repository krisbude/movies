package ubs.domain;

import java.io.Serializable;

public class Movie implements Serializable{

	private Long id;

	private String title;

	private String description;

	private Movie(){
	}

	public Movie(String title){
		this.title = title;
	}

	public Long getId(){
		return this.id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getTitle(){
		return this.title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getDescription(){
		return this.description;
	}

	public void setDescription(String description){
		this.description = description;
	}

}