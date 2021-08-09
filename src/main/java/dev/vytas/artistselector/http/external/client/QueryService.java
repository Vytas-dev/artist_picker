package dev.vytas.artistselector.http.external.client;

import dev.vytas.artistselector.http.external.client.dto.AlbumDto;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;

import java.util.List;

public interface QueryService {
    List<ArtistDto> searchForArtist(String searchTerm);

    List<AlbumDto> getTopAlbumsForArtist(Long artistId);
}
