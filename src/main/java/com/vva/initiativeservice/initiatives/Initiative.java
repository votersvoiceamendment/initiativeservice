package com.vva.initiativeservice.initiatives;


import com.vva.initiativeservice.comments.Comment;
import com.vva.initiativeservice.sponsors.Sponsor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.vva.initiativeservice.votes.Vote;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Initiative {

    @Id
    @SequenceGenerator(
            name = "initiative_sequence",
            sequenceName = "initiative_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "initiative_sequence"
    )
    private long id;

    @NotBlank(message = "Initiative title cannot be null or empty")
    @Size(max = 500, message = "Initiative title cannot be more than 500 characters")
    @Column(length = 500, nullable = false)
    private String title;

    @NotBlank(message = "State cannot be null or empty")
    @Size(max = 2, message = "State code cannot be more than 2 characters")
    // Hibernate no like
//    @Column(length = 2, nullable = false, columnDefinition = "CHAR(2) DEFAULT 'US'") NO LIKE
    @Column(length = 2, nullable = false)
    private String state = "US";

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1")
    private Integer version = 1;

    @NotBlank(message = "Initiative description cannot be null or empty")
    @Size(max = 2000, message = "Initiative description cannot be more than 2000 characters")
    @Column(length = 2000, nullable = false)
    private String description;

    @NotBlank(message = "Category description cannot be null or empty")
    @Size(max = 25, message = "Category description cannot be more than 25 characters")
    @Column(length = 25, nullable = false)
    private String category;

    @Column(columnDefinition = "boolean default false")
    private boolean approvedfordisplay = false;

    @Column(columnDefinition = "boolean default false")
    private boolean archived = false;

    @NotBlank(message = "Barcode description cannot be null or empty")
    @Size(max = 45, message = "Barcode description cannot be more than 45 characters")
    @Column(length = 45, nullable = false)
    private String barcode;

    @Size(min = 36, max = 36, message = "Pro Organization must be 36 characters")
    @Column(length = 36, nullable = true, updatable = true)
    private String proOrganizationId;

    @Size(max = 2000, message = "Pro_text description cannot be more than 2000 characters")
    @Column(length = 2000, nullable = true)
    private String proText;

    @Size(min = 36, max = 36, message = "Con Organization must be 36 characters")
    @Column(length = 36, nullable = true, updatable = true)
    private String conOrganizationId;

    @Size(max = 2000, message = "Con_text description cannot be more than 2000 characters")
    @Column(length = 2000, nullable = true)
    private String conText;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "initiative", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Sponsor> sponsors = new ArrayList<>();

    @OneToMany(mappedBy = "initiative", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Vote> votes = new ArrayList<>();

    @OneToMany(mappedBy = "initiative", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> comments = new ArrayList<>();

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

    public Initiative() {
    }

    public long getId() {
        return id;
    }

    public @NotBlank(message = "Initiative title cannot be null or empty") @Size(max = 500, message = "Initiative title cannot be more than 500 characters") String getTitle() {
        return title;
    }

    public @NotBlank(message = "State cannot be null or empty") @Size(max = 2, message = "State code cannot be more than 2 characters") String getState() {
        return state;
    }

    public Integer getVersion() {
        return version;
    }

    public @NotBlank(message = "Initiative description cannot be null or empty") @Size(max = 2000, message = "Initiative description cannot be more than 2000 characters") String getDescription() {
        return description;
    }

    public @NotBlank(message = "Category description cannot be null or empty") @Size(max = 25, message = "Category description cannot be more than 25 characters") String getCategory() {
        return category;
    }

    public boolean isApprovedfordisplay() {
        return approvedfordisplay;
    }

    public boolean isArchived() {
        return archived;
    }

    public @NotBlank(message = "Barcode description cannot be null or empty") @Size(max = 45, message = "Barcode description cannot be more than 45 characters") String getBarcode() {
        return barcode;
    }

    public @Size(min = 36, max = 36, message = "Pro Organization must be 36 characters") String getProOrganizationId() {
        return proOrganizationId;
    }

    public @Size(max = 2000, message = "Pro_text description cannot be more than 2000 characters") String getProText() {
        return proText;
    }

    public @Size(min = 36, max = 36, message = "Con Organization must be 36 characters") String getConOrganizationId() {
        return conOrganizationId;
    }

    public @Size(max = 2000, message = "Con_text description cannot be more than 2000 characters") String getConText() {
        return conText;
    }

    public List<Sponsor> getSponsors() {
        return sponsors;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setTitle(@NotBlank(message = "Initiative title cannot be null or empty") @Size(max = 500, message = "Initiative title cannot be more than 500 characters") String title) {
        this.title = title;
    }

    public void setState(@NotBlank(message = "State cannot be null or empty") @Size(max = 2, message = "State code cannot be more than 2 characters") String state) {
        this.state = state;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setDescription(@NotBlank(message = "Initiative description cannot be null or empty") @Size(max = 2000, message = "Initiative description cannot be more than 2000 characters") String description) {
        this.description = description;
    }

    public void setCategory(@NotBlank(message = "Category description cannot be null or empty") @Size(max = 25, message = "Category description cannot be more than 25 characters") String category) {
        this.category = category;
    }

    public void setApprovedfordisplay(boolean approvedfordisplay) {
        this.approvedfordisplay = approvedfordisplay;
    }

    public void setBarcode(@NotBlank(message = "Barcode description cannot be null or empty") @Size(max = 45, message = "Barcode description cannot be more than 45 characters") String barcode) {
        this.barcode = barcode;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setProOrganizationId(@Size(min = 36, max = 36, message = "Pro Organization must be 36 characters") String proOrganizationId) {
        this.proOrganizationId = proOrganizationId;
    }

    public void setProText(@Size(max = 2000, message = "Pro_text description cannot be more than 2000 characters") String proText) {
        this.proText = proText;
    }

    public void setConOrganizationId(@Size(min = 36, max = 36, message = "Con Organization must be 36 characters") String conOrganizationId) {
        this.conOrganizationId = conOrganizationId;
    }

    public void setConText(@Size(max = 2000, message = "Con_text description cannot be more than 2000 characters") String conText) {
        this.conText = conText;
    }

    @Override
    public String toString() {
        return "Initiative{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", version=" + version +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", approvedfordisplay=" + approvedfordisplay +
                ", archived=" + archived +
                ", barcode='" + barcode + '\'' +
                ", proOrganizationId='" + proOrganizationId + '\'' +
                ", proText='" + proText + '\'' +
                ", conOrganizationId='" + conOrganizationId + '\'' +
                ", conText='" + conText + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", sponsors=" + sponsors +
                ", votes=" + votes +
                '}';
    }
}
