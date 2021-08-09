package dev.vytas.artistselector.http.external.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum WrapperType {
    @JsonProperty("artist") ARTIST,
    @JsonProperty("collection") COLLECTION;

}
