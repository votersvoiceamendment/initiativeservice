package com.vva.initiativeservice.comments;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/initiatives/{initiativeId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getComments(@PathVariable("initiativeId") Long initiativeId) {
        return this.commentService.getComments(initiativeId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public void addComment(@PathVariable("initiativeId") Long initiativeId, @Valid @RequestBody Comment newComment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();
        newComment.setVvaUserId((userId));
        this.commentService.addComment(initiativeId, newComment);
    }
}
