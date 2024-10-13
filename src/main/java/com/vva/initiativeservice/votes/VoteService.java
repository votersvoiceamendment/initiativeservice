package com.vva.initiativeservice.votes;

import com.vva.initiativeservice.initiatives.Initiative;
import com.vva.initiativeservice.initiatives.InitiativeRepository;
import com.vva.initiativeservice.utils.UpdateUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {

    private final VoteRespository voteRespository;
    private final InitiativeRepository initiativeRepository;

    public VoteService(VoteRespository voteRespository, InitiativeRepository initiativeRepository) {
        this.voteRespository = voteRespository;
        this.initiativeRepository = initiativeRepository;
    }

    public Long getTotalVoteCount() {
        return this.voteRespository.count();
    }

    public List<Vote> getInitiativeVotes(Long initiativeId) {
        return this.voteRespository.findByIdInitiativeId(initiativeId).orElseThrow(() -> {
            return new IllegalStateException("Votes with the initiative id "+initiativeId+" do not exist");
        });
    }

    public VoteCount getInitiativeVoteCount(Long initiativeId) {
        boolean exists = this.voteRespository.existsByIdInitiativeId(initiativeId);
        if (!exists) {
            throw new IllegalStateException("Votes with the initiative id "+initiativeId+" do not exist");
        }
        Long yesCount = this.voteRespository.countByInitiativeIdAndYesno(initiativeId, true);
        Long noCount = this.voteRespository.countByInitiativeIdAndYesno(initiativeId, false);
        return new VoteCount(yesCount, noCount);
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

//        System.out.println("HEY HE YEWEO");

        Initiative initiative = this.initiativeRepository.findById(initiativeId).orElseThrow(() -> {
            return new IllegalStateException("The initiative "+initiativeId+" does not exist and so you cannot vote on it.");
        });

//        System.out.println("2");

        Vote newVote = new Vote(vvaUserId, initiativeId);
//        System.out.println("3");
        newVote.setInitiative(initiative);
//        System.out.println("4");
        newVote.setYesno(yesno);
//        System.out.println("5");
//        System.out.println(newVote);
//        System.out.println("6");
        this.voteRespository.save(newVote);
    }

    @Transactional
    public void updateVote(String vvaUserId, Long initiativeId, boolean yesno) {
        VoteId voteId = new VoteId(vvaUserId, initiativeId);

        Vote vote = this.voteRespository.findById(voteId).orElseThrow(() -> {
            return new IllegalStateException("A vote by the user "+vvaUserId+" on the initiative "+initiativeId+" does not exist.");
        });

        UpdateUtils.updateFieldIfChanged(vote::isYesno, vote::setYesno, yesno);

    }

}