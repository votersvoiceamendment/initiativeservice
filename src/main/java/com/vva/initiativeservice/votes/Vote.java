package com.vva.initiativeservice.votes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vva.initiativeservice.initiatives.Initiative;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Vote {

    @EmbeddedId
    // composite id, two things
    // vvaUserId
    // initiativeId
    private VoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("initiativeId")
    @JoinColumn(name = "initiativeId", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Initiative initiative;

    @Column(columnDefinition = "boolean default false")
    private boolean yesno = false;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // This makes the createdAt and updatedAt be the time when the row is made
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // This makes the updatedAt be the time when the row is updated
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Vote() {
    }

    public VoteId getId() {
        return id;
    }

    public Initiative getInitiative() {
        return initiative;
    }

    public boolean isYesno() {
        return yesno;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(VoteId id) {
        this.id = id;
    }

    public void setYesno(boolean yesno) {
        this.yesno = yesno;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", initiativeId=" + initiative.getId() +
                ", yesno=" + yesno +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
