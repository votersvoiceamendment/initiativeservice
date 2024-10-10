package com.vva.initiativeservice.initiatives;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InitiativeRepository extends JpaRepository<Initiative, Long> {
}
