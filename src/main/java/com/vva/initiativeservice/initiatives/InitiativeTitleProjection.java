package com.vva.initiativeservice.initiatives;

public interface InitiativeTitleProjection {
    Long getId();
    String getState();
    String getTitle();

    default String getStateId() {
        return getState() + getId();
    }
}
