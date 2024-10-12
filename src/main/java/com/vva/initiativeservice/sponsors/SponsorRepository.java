package com.vva.initiativeservice.sponsors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, SponsorId> {
}
