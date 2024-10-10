package com.vva.initiativeservice.votes;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VoteId implements Serializable {

    // This is taken care of using PrePersist later
    // This is because the vvaUserId is in the JWT
    // @NotBlank(message = "vvaUserId for comment cannot be null or empty")
    @Size(max = 36, min = 36, message = "vvaUserId for vote must be 36 characters")
    @Column(length = 36, updatable = false)
    private final String vvaUserId;

    @Column(name = "initiative_id", updatable = false)
    private final Long initiative;

    public VoteId(String vvaUserId, Long initiative) {
        this.vvaUserId = vvaUserId;
        this.initiative = initiative;
    }

    public @Size(max = 36, min = 36, message = "vvaUserId for vote must be 36 characters") String getVvaUserId() {
        return vvaUserId;
    }

    public Long getInitiative() {
        return initiative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteId)) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(this.vvaUserId, voteId.vvaUserId) &&
                Objects.equals(this.initiative, voteId.initiative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.vvaUserId, this.initiative);
    }
}
