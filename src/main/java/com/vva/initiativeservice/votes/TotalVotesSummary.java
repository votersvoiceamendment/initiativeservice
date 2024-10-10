package com.vva.initiativeservice.votes;

import java.util.List;

public class TotalVotesSummary {

    private final Long total;
    private final List<InitiativeMetaAndVoteCount> initiatives;

    public TotalVotesSummary(Long total, List<InitiativeMetaAndVoteCount> initiatives) {
        this.total = total;
        this.initiatives = initiatives;
    }

    public Long getTotal() {
        return total;
    }

    public List<InitiativeMetaAndVoteCount> getInitiatives() {
        return initiatives;
    }
}
