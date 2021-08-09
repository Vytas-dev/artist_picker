package dev.vytas.artistselector.service;

import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.persistence.model.User;

public interface UserService {

    User getUser(Long id);

    void saveArtist(Long id, ArtistDto artistDto);
}
