package ubs.application;

import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.mockito.invocation.InvocationOnMock;

import ubs.domain.Movie;
import ubs.services.ICommentService;
import ubs.services.IMovieService;
import ubs.domain.Comment;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Configuration
@Profile("dev")
public class MockConfig {

	private HashMap<Long, Movie> movies = new HashMap<>();

	private HashMap<Long, Comment> comments = new HashMap<>();

	private AtomicLong movieIds = new AtomicLong(0);
	private AtomicLong commentIds = new AtomicLong(0);

	@Bean
	public IMovieService mockMovieService() {
		IMovieService movieService = mock(IMovieService.class);
		Mockito.when(movieService.read(Mockito.anyLong())).thenAnswer(
				invocation -> doGet((Long) invocation.getArguments()[0], movies));
		Mockito.when(movieService.create(Mockito.any())).thenAnswer(
				invocation -> createMovie(invocation));
		return movieService;
	}

	@Bean
	public ICommentService mockCommentService() {
		ICommentService commentService = mock(ICommentService.class);
		Mockito.when(commentService.read(Mockito.anyLong())).thenAnswer(
				invocation -> doGet((Long)invocation.getArguments()[0], comments));
		Mockito.when(commentService.create(Mockito.any())).thenAnswer(
				invocation -> createComment(invocation));
		Mockito.when(commentService.getCommentsForMovie(Mockito.anyLong())).thenAnswer(
				invocation -> getCommentsForMovie(invocation));
		return commentService;
	}

	private Collection<Comment> getCommentsForMovie(InvocationOnMock invocation){
		Long movieId = (Long)invocation.getArguments()[0];
		return comments.values().stream().filter(comment -> movieId.equals(comment.getMovieId())).collect(Collectors.toList());
	}

	private Comment createComment(InvocationOnMock invocation){
		Comment comment = (Comment) invocation.getArguments()[0];
		comment.setId(commentIds.incrementAndGet());
		comments.put(comment.getId(), comment);
		return comment;
	}

	private Movie createMovie(InvocationOnMock invocation){
		Movie movie = (Movie) invocation.getArguments()[0];
		movie.setId(movieIds.incrementAndGet());
		movies.put(movie.getId(), movie);
		return movie;
	}

	private <T> T doGet(Long id, Map<Long, T> map){
		T result = map.get(id);
		if (result == null){
			throw new NoSuchElementException();
		}
		return result;
	}

}