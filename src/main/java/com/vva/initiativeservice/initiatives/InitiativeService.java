package com.vva.initiativeservice.initiatives;

import com.vva.initiativeservice.sponsors.Sponsor;
import com.vva.initiativeservice.sponsors.SponsorRepository;
import com.vva.initiativeservice.utils.UpdateUtils;
import com.vva.initiativeservice.votes.Vote;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InitiativeService {

    private final InitiativeRepository initiativeRepository;
    private final SponsorRepository sponsorRepository;

    public InitiativeService(InitiativeRepository initiativeRepository, SponsorRepository sponsorRepository) {
        this.initiativeRepository = initiativeRepository;
        this.sponsorRepository = sponsorRepository;
    }

    public List<Initiative> getInitiatives() {
        return this.initiativeRepository.findAll();
    }

    public List<InitiativeTitleProjection> getInitiativesTitleOrderById() {
        return this.initiativeRepository.findAllInitiativesTitleOrderedIdAsc();
    }

    public List<InitiativeTitleProjection> getInitiativeTitlesOrderByUserVotesAndState(
            String vvaUserId,
            String userState
    ) {
        List<InitiativeTitleProjection> notVotedOn = initiativeRepository.findInitiativesNotVotedOn(vvaUserId, userState);
        List<InitiativeTitleProjection> votedOn = initiativeRepository.findInitiativesVotedOn(vvaUserId, userState);
        List<InitiativeTitleProjection> notAllowedToVoteOn = initiativeRepository.findInitiativesNotAllowedToVoteOn(userState);

        List<InitiativeTitleProjection> allInitiatives = new ArrayList<>();
        allInitiatives.addAll(notVotedOn);
        allInitiatives.addAll(votedOn);
        allInitiatives.addAll(notAllowedToVoteOn);

        return allInitiatives;
    }

    public Initiative getInitiative(Long initiativeId) {
        return this.initiativeRepository.findById(initiativeId).orElseThrow(() -> {
            return new IllegalStateException("Initiative with the id "+initiativeId+" does not exist.");
        });
    }

    public void createNewInitiative(@Valid Initiative newInitiative) {

        for (Sponsor sponsor: newInitiative.getSponsors()) {
            sponsor.setInitiative((newInitiative));
        }

        this.initiativeRepository.save(newInitiative);
    }

    @Transactional
    public void updateInitiative(Long initiativeId, @Valid Initiative updatedInitiative) {
        Initiative initiative = this.initiativeRepository.findById(initiativeId).orElseThrow(() -> {
            return new IllegalStateException("Initiative with the id "+initiativeId+" does not exist.");
        });

        UpdateUtils.updateFieldIfChanged(initiative::getTitle, initiative::setTitle, updatedInitiative.getTitle());
        UpdateUtils.updateFieldIfChanged(initiative::getState, initiative::setState, updatedInitiative.getState());
        UpdateUtils.updateFieldIfChanged(initiative::getDescription, initiative::setDescription, updatedInitiative.getDescription());
        UpdateUtils.updateFieldIfChanged(initiative::getCategory, initiative::setCategory, updatedInitiative.getCategory());
        UpdateUtils.updateFieldIfChanged(initiative::isApprovedfordisplay, initiative::setApprovedfordisplay, updatedInitiative.isApprovedfordisplay());
        UpdateUtils.updateFieldIfChanged(initiative::isArchived, initiative::setArchived, updatedInitiative.isArchived());
        UpdateUtils.updateFieldIfChanged(initiative::getBarcode, initiative::setBarcode, updatedInitiative.getBarcode());
        UpdateUtils.updateFieldIfChanged(initiative::getProOrganizationId, initiative::setProOrganizationId, updatedInitiative.getProOrganizationId());
        UpdateUtils.updateFieldIfChanged(initiative::getProText, initiative::setProText, updatedInitiative.getProText());
        UpdateUtils.updateFieldIfChanged(initiative::getConOrganizationId, initiative::setConOrganizationId, updatedInitiative.getConOrganizationId());
        UpdateUtils.updateFieldIfChanged(initiative::getConText, initiative::setConText, updatedInitiative.getConText());

        // Handle Sponsors
        // You do this by using the organization Ids are a unique key
        // You can do this because the sponsor has a composite key including
        //      initiativeId
        //      organiziationId
        // but you are updating an initaitive here so that is always the same
        // therefor the organization_id is unique between them

        // Delete sponsors not in the updated initiative
        // Make a hashmap of the new sponsors by organizationId for reference
        HashMap<String, Boolean> updatedSponsorOrganizationIdMap = new HashMap<String, Boolean>();
        for (Sponsor sponsor : updatedInitiative.getSponsors()) {
            updatedSponsorOrganizationIdMap.put(sponsor.getId().getOrganizationId(), true);
        }
        // Find the sponsors in the DB that need to be removed
        List<Sponsor> sponsorsToRemove = initiative.getSponsors().stream().filter((sponsor) -> {
            return !updatedSponsorOrganizationIdMap.containsKey(sponsor.getId().getOrganizationId());
        }).toList();
        // Remove the sponsors that need to go
        initiative.getSponsors().removeAll(sponsorsToRemove);

        // Modify or add sponsors
        // Get HashMap of existing sponsors by organizationId for reference
        HashMap<String, Sponsor> existingSponsorsMap = new HashMap<String, Sponsor>();
        for (Sponsor sponsor : initiative.getSponsors()) {
            existingSponsorsMap.put(sponsor.getId().getOrganizationId(), sponsor);
        }
        // Go through the new sponsors and see what needs to be added or updated
        for (Sponsor sponsor: updatedInitiative.getSponsors()) {
            if (existingSponsorsMap.containsKey(sponsor.getId().getOrganizationId())) {
                Sponsor eSponsor = existingSponsorsMap.get(sponsor.getId().getOrganizationId());
                UpdateUtils.updateFieldIfChanged(eSponsor::isLeadSponsor, eSponsor::setLeadSponsor, sponsor.isLeadSponsor());
            } else {
                sponsor.getId().setInitiativeId(initiativeId);
                sponsor.setInitiative(initiative);

                initiative.getSponsors().add(sponsor);
            }
        }
    }
}
