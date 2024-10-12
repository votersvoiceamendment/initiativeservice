package com.vva.initiativeservice.initiatives;

import com.vva.initiativeservice.sponsors.Sponsor;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/initiatives")
public class InitiativeController {

    private InitiativeService initiativeService;

    public InitiativeController(InitiativeService initiativeService) {
        this.initiativeService = initiativeService;
    }

    @GetMapping
    public List<Initiative> getInitiatives() {
        return this.initiativeService.getInitiatives();
    }

    @GetMapping(path = "/titleOrderById")
    public List<InitiativeTitleProjection> getInitiativesTitleOrderById() {
        return this.initiativeService.getInitiativesTitleOrderById();
    }

//    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(path = "/titleOrderByUserVotesAndState/{state}")
    public List<InitiativeTitleProjection> getInitiativeTitlesOrderByUserVotesAndState(@PathVariable("state") String state) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getSubject();

//        System.out.println( userId+"  "  + state);

        return this.initiativeService.getInitiativeTitlesOrderByUserVotesAndState(userId, state.toUpperCase());
    }

    @GetMapping(path = "{initiativeId}/core")
    public InitiativeCoreProjection getInitiativeCore(@PathVariable("initiativeId") Long initiativeId) {
        return this.initiativeService.getInitiativeCore(initiativeId);
    }

    @GetMapping(path = "{initiativeId}")
    public Initiative getInitiative(@PathVariable("initiativeId") Long initiativeId) {
        return this.initiativeService.getInitiative(initiativeId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public void createNewInitiative(@Valid @RequestBody Initiative newInitiative) {
        this.initiativeService.createNewInitiative(newInitiative);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping(path = "{initiativeId}")
    public void updateInitiative(@PathVariable("initiativeId") Long initiativeId, @Valid @RequestBody Initiative updatedInitiative) {
        this.initiativeService.updateInitiative(initiativeId, updatedInitiative);
    }

}
