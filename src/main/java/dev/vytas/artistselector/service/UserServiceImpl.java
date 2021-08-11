package dev.vytas.artistselector.service;

import dev.vytas.artistselector.http.external.client.QueryService;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.http.external.client.dto.WrapperType;
import dev.vytas.artistselector.persistence.model.Album;
import dev.vytas.artistselector.persistence.model.Artist;
import dev.vytas.artistselector.persistence.model.User;
import dev.vytas.artistselector.persistence.repository.ArtistRepository;
import dev.vytas.artistselector.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ArtistRepository artistRepository;
  private final QueryService queryService;

  @Override
  public User getUser(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceAccessException("User does not exists, id:" + id));
  }

  @Override
  public void saveArtist(Long id, ArtistDto artistDto) {
    var artist = artistRepository.findById(artistDto.amgArtistId).orElse(saveNewArtist(artistDto));

    userRepository.findById(id).orElse(saveNewUser(id, artist));
  }

  @Override
  public void refreshAllArtistTopAlbums() {
    artistRepository.findAll().forEach(this::refreshArtistTopAlbums);
  }

  private Artist saveNewArtist(ArtistDto artistDto) {
    var artist = artistRepository.save(new Artist(artistDto.amgArtistId, artistDto.artistName));
    refreshArtistTopAlbums(artist);
    return artist;
  }

  private void refreshArtistTopAlbums(Artist artist) {
    var albums =
            queryService.getTopAlbumsForArtist(artist.getId()).stream()
                    .filter(albumDto -> albumDto.wrapperType == WrapperType.COLLECTION)
                    .map(albumDto -> new Album(artist, albumDto.collectionName, albumDto.releaseDate))
                    .collect(Collectors.toSet());
    artist.getAlbums().clear();
    artist.getAlbums().addAll(albums);
    artistRepository.save(artist);
  }

  private User saveNewUser(Long id, Artist artist) {
    return userRepository.save(new User(id, artist));
  }
}
