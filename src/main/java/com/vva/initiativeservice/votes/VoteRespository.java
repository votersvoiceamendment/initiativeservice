package com.vva.initiativeservice.votes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRespository extends JpaRepository<Vote, VoteId> {

    // Get the total count for a specific initiative
    Long countByInitiativeId(Long initiativeId);

    // Get the count of yesno, true OR false depending on what
    // you pass in for a specific initiative
    Long countByInitiativeIdAndYesno(Long initiativeId, boolean yesno);

    @Query("SELECT v.id.initiative AS initiativeId, i.state AS state, i.title AS title, COUNT(v) AS voteCount " +
            "FROM Vote v " +
            "JOIN Initiative i ON v.id.initiative = i.id " +
            "GROUP BY v.id.initiative, i.state, i.title")
    List<InitiativeMetaAndVoteCount> findAllInitiativeVoteCounts();

}
