package ubs.application;

import static org.mockito.Mockito.mock;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ubs.domain.Movie;
import ubs.services.ICommentService;
import ubs.services.IMovieService;

@Configuration
@Profile("dev")
public class MockConfig {

	@Bean
	public IMovieService mockMovieService() {
		IMovieService movieService = mock(IMovieService.class);
		Mockito.when(movieService.read(Mockito.anyLong())).thenAnswer(
				invocation -> createMovie((Long) invocation.getArguments()[0]));
		return movieService;
	}

	@Bean
	public ICommentService mockCommentService() {
		ICommentService commentService = mock(ICommentService.class);
		Mockito.when(commentService.create(Mockito.any())).thenReturn(1L);
		return commentService;
	}

	private Movie createMovie(Long id) {
		Movie movie = new Movie("Movie number " + id);
		movie.setId(id);
		movie.setDescription("A mocked movie");
		return movie;
	}

}