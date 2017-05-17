package ubs.services;

import ubs.domain.Comment;
import ubs.services.ICrudService;

import java.util.Collection;

public interface ICommentService extends ICrudService<Comment> {

    Collection<Comment> getCommentsForMovie(Long movieId);

}
