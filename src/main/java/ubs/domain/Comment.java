package ubs.domain;

public class Comment {

	private Long id;

	private Long movieId;

	private String userName;

	private String message;

	private Comment() {
	}

	public Comment(Long movieId, String userName, String message) {
		this.movieId = movieId;
		this.userName = userName;
		this.message = message;
	}

	public Long getId() {
		return this.id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Long getMovieId() {
		return this.movieId;
	}

	private void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getUserName() {
		return this.userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}