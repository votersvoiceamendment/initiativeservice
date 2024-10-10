package com.vva.initiativeservice.votes;

import com.vva.initiativeservice.utils.UpdateUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRespository voteRespository;

    public VoteService(VoteRespository voteRespository) {
        this.voteRespository = voteRespository;
    }

    public Long getTotalVoteCount() {
        return this.voteRespository.count();
    }

    public VoteBreakdown getInitiativeVoteCount(Long initiativeId) {
        Long yesCount = this.voteRespository.countByInitiativeIdAndYesno(initiativeId, true);
        Long noCount = this.voteRespository.countByInitiativeIdAndYesno(initiativeId, false);
        return new VoteBreakdown(yesCount, noCount);
    }

    public TotalVotesSummary getVoteCountSummary() {
        Long totalCount = this.voteRespository.count();
        List<InitiativeMetaAndVoteCount> initiativesCount = this.voteRespository.findAllInitiativeVoteCounts();

        return new TotalVotesSummary(totalCount, initiativesCount);
    }

    public Vote getUserVoteForInitiative(String vvaUserId, Long initiativeId) {
        VoteId voteId = new VoteId(vvaUserId, initiativeId);
        return this.voteRespository.findById(voteId).orElseThrow(() -> {
            return new IllegalStateException("A vote by the user "+vvaUserId+" on the initiative "+initiativeId+" does not exist.");
        });
    }

    // This handles if the vote is new or an update to an existing vote
    @Transactional
    public void castVote(String vvaUserId, Long initiativeId, boolean yesno) {
        VoteId voteId = new VoteId(vvaUserId, initiativeId);

        this.voteRespository.findById(voteId).ifPresentOrElse((vote) -> {
            UpdateUtils.updateFieldIfChanged(vote::isYesno, vote::setYesno, yesno);
        }, () -> {
            Vote newVote = new Vote();
            newVote.setId(voteId);
            newVote.setYesno(yesno);
            this.voteRespository.save(newVote);
        });

    }

}