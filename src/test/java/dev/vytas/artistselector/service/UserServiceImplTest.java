package dev.vytas.artistselector.service;

import dev.vytas.artistselector.http.external.client.QueryService;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.persistence.model.Artist;
import dev.vytas.artistselector.persistence.model.User;
import dev.vytas.artistselector.persistence.repository.ArtistRepository;
import dev.vytas.artistselector.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

  @MockBean private UserRepository userRepository;

  @MockBean private ArtistRepository artistRepository;

  @MockBean private QueryService queryService;

  private final Long userId = 1L;
  private final Long artistId = 100L;
  private final Artist mockArtist = new Artist(artistId, "mock_artist");

  private UserService userService;

  @BeforeEach
  void setUp() {
    when(userRepository.findById(userId)).thenReturn(Optional.of(new User(userId, new Artist())));

    when(artistRepository.findById(artistId)).thenReturn(Optional.of(mockArtist));

    userService = new UserServiceImpl(userRepository, artistRepository, queryService);
  }

  @Test
  void getUserExists() {
    assertNotNull(userService.getUser(userId));

    verify(userRepository, times(1)).findById(userId);
  }

  @Test
  void getUserDoesntExists() {
    Exception exception = assertThrows(ResourceAccessException.class, () -> {
      userService.getUser(2L);
    });
    assertEquals(exception.getMessage(), "User does not exists, id:2");
    verify(userRepository, times(1)).findById(2L);
  }

  @Test
  void saveUserArtistExists() {
    ArtistDto artistDto = new ArtistDto();
    artistDto.amgArtistId = artistId;

    userService.saveArtist(userId, artistDto);

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void saveUserArtistDoesntExists() {
    ArtistDto artistDto = new ArtistDto();
    artistDto.amgArtistId = 200L;

    when(artistRepository.save(any(Artist.class))).thenReturn(mockArtist);

    userService.saveArtist(userId, artistDto);

    verify(queryService, times(1)).getTopAlbumsForArtist(artistId);
    verify(artistRepository, times(2)).save(any(Artist.class));
    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void refreshAllArtistTopAlbums() {
    when(artistRepository.findAll()).thenReturn(List.of(mockArtist, mockArtist, mockArtist));

    userService.refreshAllArtistTopAlbums();

    verify(queryService, times(3)).getTopAlbumsForArtist(artistId);
    verify(artistRepository, times(3)).save(any(Artist.class));

  }
}
