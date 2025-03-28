package com.smartInventory.backend.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MovementType {
    @JsonProperty("IN") ADDED,
    @JsonProperty("OUT") CONSUMED,
    @JsonProperty("REMOVE") DELETED,
    @JsonProperty("UPDATE") UPDATED;
}