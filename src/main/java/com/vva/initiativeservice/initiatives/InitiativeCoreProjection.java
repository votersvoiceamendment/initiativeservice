package com.vva.initiativeservice.initiatives;

import com.vva.initiativeservice.sponsors.Sponsor;

import java.time.LocalDateTime;
import java.util.List;

public interface InitiativeCoreProjection {
    Long getId();
    String getTitle();
    String getState();
    Integer getVersion();
    String getDescription();
    String getCategory();
    String getBarcode();
    String getProOrganizationId();
    String getProText();
    String getConOrganizationId();
    String getConText();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    List<Sponsor> getSponsors();
}
