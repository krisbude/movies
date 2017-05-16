package ubs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ubs.domain.Movie;
import ubs.services.IMovieService;

@RestController
@RequestMapping("/movies")
public class MovieController{

	@Autowired
	private IMovieService movieService;

	@Cacheable("movies")
	@RequestMapping(value="/{id}", produces = { "application/json"}, method = RequestMethod.GET)
	public Movie getMovie(@PathVariable Long id){
		return movieService.read(id);
	}

}