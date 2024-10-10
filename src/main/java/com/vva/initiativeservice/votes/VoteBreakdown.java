package com.vva.initiativeservice.votes;

// No setters in this DTO. It can only be created and then retrieved from
public class VoteBreakdown {

    public Long yes;
    public Long no;

    public VoteBreakdown(Long yes, Long no) {
        this.yes = yes;
        this.no = no;
    }

    public Long getYes() {
        return yes;
    }

    public Long getNo() {
        return this.no;
    }

    public Long getTotal() {
        return this.yes + this.no;
    }

}
