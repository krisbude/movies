package ubs.endpoints.movies;

import ubs.domain.Comment;
import ubs.domain.Movie;

import java.io.Serializable;
import java.util.Collection;

public class MovieDetailResource implements Serializable{

    private Movie movie;

    private Collection<Comment> comments;

    public MovieDetailResource(Movie movie){
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }
}
