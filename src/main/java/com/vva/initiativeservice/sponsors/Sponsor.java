package com.vva.initiativeservice.sponsors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vva.initiativeservice.initiatives.Initiative;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table
public class Sponsor {

    @EmbeddedId
    private SponsorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("initiativeId")
    @JoinColumn(name = "initiativeId", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Initiative initiative;

    @Column(columnDefinition = "boolean default false")
    private boolean leadSponsor = false;

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

    public Sponsor() {
    }

    public Initiative getInitiative() {
        return initiative;
    }

    public SponsorId getId() {
        return id;
    }

    public boolean isLeadSponsor() {
        return leadSponsor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(SponsorId id) {
        this.id = id;
    }

    public void setLeadSponsor(boolean leadSponsor) {
        this.leadSponsor = leadSponsor;
    }

    public void setInitiative(Initiative initiative) {
        this.initiative = initiative;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", initiativeId=" + initiative.getId() +
                ", leadSponsor=" + leadSponsor +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
