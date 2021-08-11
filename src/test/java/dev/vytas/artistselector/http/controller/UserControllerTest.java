package dev.vytas.artistselector.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.persistence.model.Album;
import dev.vytas.artistselector.persistence.model.Artist;
import dev.vytas.artistselector.persistence.model.User;
import dev.vytas.artistselector.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean RestTemplate restTemplate;

  @BeforeEach
  void init() {
    var artist = new Artist();
    artist.setAlbums(Set.of(new Album(), new Album(), new Album()));
    when(userService.getUser(1L)).thenReturn(new User(1L, artist));
  }

  @Test
  void gettingUserTopAlbums() throws Exception {

    mockMvc
        .perform(get("/user/1/albums").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "[{\"id\":null,\"name\":null,\"releaseDate\":null,\"createdAt\":null,\"updatedAt\":null},{\"id\":null,\"name\":null,\"releaseDate\":null,\"createdAt\":null,\"updatedAt\":null},{\"id\":null,\"name\":null,\"releaseDate\":null,\"createdAt\":null,\"updatedAt\":null}]"));

    verify(userService, times(1)).getUser(1L);
  }

  @Test
  void postingSavedArtistForUser() throws Exception {
    var artistDto = new ArtistDto();
    artistDto.amgArtistId = 100L;
    var objectMapper = new ObjectMapper();

    mockMvc
        .perform(
            post("/user/1/artist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(artistDto)))
        .andExpect(status().isOk());

    verify(userService, times(1)).saveArtist(1L, artistDto);
  }
}
