package ubs.endpoints.movies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import ubs.domain.Comment;
import ubs.domain.Movie;
import ubs.services.ICommentService;
import ubs.services.IMovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private IMovieService movieService;

	@Autowired
	private ICommentService commentService;

	@RequestMapping(value = "/{id}/comments", produces = { "application/json" }, method = RequestMethod.GET)
	public MovieDetailResource getMovieDetails(@PathVariable Long id) throws InterruptedException,
			ExecutionException {
        logger.info(()-> "retrieving movie with comments with id "+id);
        Future<Movie> movieFuture = executorService.submit(() -> movieService.read(id));
        Future<Collection<Comment>> commentsFuture = executorService.submit(() -> commentService.getCommentsForMovie(id));
        MovieDetailResource movieDetailResource = new MovieDetailResource(movieFuture.get());
        movieDetailResource.setComments(commentsFuture.get());
        return movieDetailResource;
	}

    @Cacheable("movies")
    @RequestMapping(value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
    public MovieDetailResource getMovie(@PathVariable Long id) throws InterruptedException,
            ExecutionException {
	    logger.info(()-> "retrieving movie with id "+id);
        Future<Movie> movieFuture = executorService.submit(() -> movieService.read(id));
        MovieDetailResource movieDetailResource = new MovieDetailResource(movieFuture.get());
        return movieDetailResource;
    }

    @CachePut(cacheNames="movies", key="#movie.id")
    @RequestMapping(value = "", consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.POST)
    public MovieDetailResource createMovie(@RequestBody Movie movie)
            throws InterruptedException, ExecutionException {
        logger.info(()-> "create movie with title "+movie.getTitle());
	    return new MovieDetailResource(executorService.submit(() -> movieService.create(movie)).get());
    }

    @CachePut(cacheNames="movies", key="#movie.id")
    @RequestMapping(value = "", consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.PUT)
    public void updateMovie(@RequestBody Movie movie)
            throws InterruptedException, ExecutionException {
	    if (movie.getId() != null){
            logger.info(()-> "update movie with id "+movie.getId());
            executorService.submit(() -> movieService.update(movie)).get();
        }
    }

    @RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
    public Collection<MovieDetailResource> getAllMovies()
            throws InterruptedException, ExecutionException {
        logger.info(()-> "Retrieving all movies");
        Collection<Movie> movies = executorService.submit(() -> movieService.readAll()).get();
        List<MovieDetailResource> movieDetails = new ArrayList<MovieDetailResource>();
        return movies.stream().map(movie -> new MovieDetailResource(movie)).collect(Collectors.toList());
    }

}