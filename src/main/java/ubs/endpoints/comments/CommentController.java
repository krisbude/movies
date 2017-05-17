package ubs.endpoints.comments;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import ubs.annotations.FallbackCacheable;
import ubs.domain.Comment;
import ubs.services.ICommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private ICommentService commentService;

	@CachePut(cacheNames="comments", key="#comment.id")
	@RequestMapping(value = "", consumes = { "application/json" }, produces = { "application/json" }, method = RequestMethod.POST)
	public Comment createComment(@RequestBody Comment comment)
			throws InterruptedException, ExecutionException {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info(()-> "Creating a comment for user: "+user.getUsername());
		comment.setUserName(user.getUsername());
		return executorService.submit(() -> commentService.create(comment)).get();
	}

	@FallbackCacheable(value= "comments", key = "#id")
	@RequestMapping(value = "/{id}", produces = { "application/json" }, method = RequestMethod.GET)
	public Comment getComment(@PathVariable Long id)
			throws InterruptedException, ExecutionException {
		logger.info(()-> "Retrieving a comment: "+ id);
		return executorService.submit(() -> commentService.read(id)).get();
	}

}