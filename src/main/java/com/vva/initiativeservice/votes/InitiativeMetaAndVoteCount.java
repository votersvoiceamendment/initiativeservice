package com.vva.initiativeservice.votes;

public interface InitiativeMetaAndVoteCount {
    Long getInitiativeId();
    String getState();
    String getTitle();
    Long getVoteCount();
}
