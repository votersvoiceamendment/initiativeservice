package com.vva.initiativeservice.comments;

import com.vva.initiativeservice.initiatives.Initiative;
import com.vva.initiativeservice.initiatives.InitiativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final InitiativeRepository initiativeRepository;
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository, InitiativeRepository initiativeRepository) {
        this.initiativeRepository = initiativeRepository;
        this.commentRepository = commentRepository;

    }

    public List<Comment> getComments(Long initiativeId) {
        boolean initiativeExists = this.initiativeRepository.existsById(initiativeId);
        if (!initiativeExists) {
            throw new IllegalStateException("An initiative with the id "+initiativeId+" does not exist, and so no comments can be returned.");
        }

        return this.commentRepository.findByInitiativeIdOrderByCreatedAtDesc(initiativeId);
    }

    public void addComment(Long initiativeId, Comment newComment) {
        Initiative initiative = this.initiativeRepository.findById(initiativeId).orElseThrow(() -> {
           return new IllegalStateException("A initiative with the id " + initiativeId + " does not exist, so you cannot add a comment to it.");
        });

        newComment.setInitiative(initiative);

        this.commentRepository.save(newComment);
    }
}
