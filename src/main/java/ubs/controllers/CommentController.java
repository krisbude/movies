package ubs.controllers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ubs.domain.Comment;
import ubs.services.ICommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private ICommentService commentService;

	@RequestMapping(value = "", consumes = { "application/json" }, method = RequestMethod.POST)
	public Long createComment(@RequestBody Comment comment)
			throws InterruptedException, ExecutionException {
		return executorService.submit(() -> commentService.create(comment)).get();
	}

}