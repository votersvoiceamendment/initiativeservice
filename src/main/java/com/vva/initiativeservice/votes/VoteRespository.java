package com.vva.initiativeservice.votes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRespository extends JpaRepository<Vote, VoteId> {

    // Get the total count for a specific initiative
    Long countByInitiativeId(Long initiativeId);

    // Method to check if a vote exists for a specific initiativeId
    boolean existsByIdInitiativeId(Long initiativeId);

    // Get the count of yesno, true OR false depending on what
    // you pass in for a specific initiative
    Long countByInitiativeIdAndYesno(Long initiativeId, boolean yesno);

    // Get all the votes for an InitiativeId
    Optional<List<Vote>> findByIdInitiativeId(Long initiativeId);

    @Query("SELECT v.id.initiativeId AS initiativeId, i.state AS state, i.title AS title, COUNT(v) AS totalVotes " +
            "FROM Vote v " +
            "JOIN Initiative i ON v.id.initiativeId = i.id " +
            "GROUP BY v.id.initiativeId, i.state, i.title")
    List<InitiativeMetaAndVoteCount> findAllInitiativeVoteCounts();

}
