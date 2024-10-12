package com.vva.initiativeservice.initiatives;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {

    List<Initiative> findAllByOrderByIdAsc();

    @Query("SELECT i.id AS id, i.state AS state, i.title AS title FROM Initiative i ORDER BY i.id ASC")
    List<InitiativeTitleProjection> findAllInitiativesTitleOrderedIdAsc();

    // Initiatives the user can vote on but hasn't yet
    @Query("SELECT i.id AS id, i.state AS state, i.title AS title FROM Initiative i WHERE (i.state = :state OR i.state = 'US') AND NOT EXISTS (SELECT v FROM Vote v WHERE v.initiative = i AND v.id.vvaUserId = :userId)")
    List<InitiativeTitleProjection> findInitiativesNotVotedOn(@Param("userId") String userId, @Param("state") String state);

    // Initiatives the user has already voted on
    @Query("SELECT i.id AS id, i.state AS state, i.title AS title FROM Initiative i WHERE (i.state = :state OR i.state = 'US') AND EXISTS (SELECT v FROM Vote v WHERE v.initiative = i AND v.id.vvaUserId = :userId)")
    List<InitiativeTitleProjection> findInitiativesVotedOn(@Param("userId") String userId, @Param("state") String state);

    // Initiatives the user is not allowed to vote on
    @Query("SELECT i.id AS id, i.state AS state, i.title AS title FROM Initiative i WHERE i.state <> :state AND i.state <> 'US'")
    List<InitiativeTitleProjection> findInitiativesNotAllowedToVoteOn(@Param("state") String state);

}