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
    private String vvaUserId;

    private Long initiativeId;

    public VoteId() {
    }

    public VoteId(String vvaUserId, Long initiativeId) {
        this.vvaUserId = vvaUserId;
        this.initiativeId = initiativeId;
    }

    public @Size(max = 36, min = 36, message = "vvaUserId for vote must be 36 characters") String getVvaUserId() {
        return vvaUserId;
    }

    public Long getInitiativeId() {
        return initiativeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteId)) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(this.vvaUserId, voteId.vvaUserId) &&
                Objects.equals(this.initiativeId, voteId.initiativeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.initiativeId, this.initiativeId);
    }

    @Override
    public String toString() {
        return "VoteId{" +
                "vvaUserId='" + vvaUserId + '\'' +
                ", initiativeId=" + initiativeId +
                '}';
    }
}
