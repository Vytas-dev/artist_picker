package dev.vytas.artistselector.http.controller;

import dev.vytas.artistselector.http.external.client.QueryService;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QueryController.class)
class QueryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private QueryService queryService;

  @MockBean
  RestTemplate restTemplate;


  @Test
  void searchForArtist() throws Exception {
    when(queryService.searchForArtist("ABBA")).thenReturn(List.of(new ArtistDto()));

    mockMvc
        .perform(get("/search/artist?term=ABBA").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    "[{\"wrapperType\":null,\"artistType\":null,\"artistName\":null,\"artistLinkUrl\":null,\"artistId\":null,\"amgArtistId\":null,\"primaryGenreName\":null,\"primaryGenreId\":null}]"));

    verify(queryService, times(1)).searchForArtist("ABBA");
  }
}