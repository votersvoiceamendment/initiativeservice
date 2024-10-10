package com.vva.initiativeservice.votes;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService =voteService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "total")
    public Long getTotalVoteCount() {
        return this.voteService.getTotalVoteCount();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(path = "summary")
    public TotalVotesSummary getSummaryVoteCount() {
        return this.voteService.getVoteCountSummary();
    }

    @GetMapping(path = "/initiatives/{initiativeId}")
    public VoteBreakdown getInitiativeVoteCount(@PathVariable("initiativeId") Long initiativeId) {
        return this.voteService.getInitiativeVoteCount(initiativeId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(path = "/initiatives/{initiativeId}")
    public Vote getUserVoteForInitiative(@PathVariable("initiativeId") Long initiativeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        return this.voteService.getUserVoteForInitiative(userId, initiativeId);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(path = "/initiatives/{initiativeId}")
    public void castVote(
            @PathVariable("initiativeId") Long initiativeId,
            @RequestBody VoteRequest voteRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

        this.voteService.castVote(userId, initiativeId, voteRequest.isYesno());
    }
}
