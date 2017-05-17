package ubs.services;

import ubs.domain.Comment;

import java.util.Collection;

public interface ICommentService extends ICrudService<Comment> {

    Collection<Comment> getCommentsForMovie(Long movieId);

}
