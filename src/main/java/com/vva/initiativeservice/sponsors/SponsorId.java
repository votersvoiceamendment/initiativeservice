package com.vva.initiativeservice.sponsors;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Embeddable
public class SponsorId {


    @NotBlank(message = "OrganizationId for sponsor cannot be null or empty")
    @Size(min=36, max = 36, message = "OrganizationId for sponsor must be 36 characters")
    @Column(length = 36, updatable = false)
    private final String organizationId;

    @Column(name = "initiative_id", updatable = false)
    private final Long initiative;

    public SponsorId(String organizationId, Long initiative) {
        this.organizationId = organizationId;
        this.initiative = initiative;
    }

    public @NotBlank(message = "OrganizationId for sponsor cannot be null or empty") @Size(min = 36, max = 36, message = "OrganizationId for sponsor must be 36 characters") String getOrganizationId() {
        return organizationId;
    }

    public Long getInitiative() {
        return initiative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SponsorId)) return false;
        SponsorId sponsorId = (SponsorId) o;
        return Objects.equals(this.organizationId, sponsorId.organizationId) &&
                Objects.equals(this.initiative, sponsorId.initiative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.organizationId, this.initiative);
    }
}
