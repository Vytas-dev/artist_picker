package dev.vytas.artistselector.http.controller;

import dev.vytas.artistselector.http.external.client.QueryService;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("search")
public class QueryController {

  private final QueryService queryService;

  @GetMapping(value = "/artist", produces = "application/json")
  public @ResponseBody List<ArtistDto> searchForArtist(@RequestParam String term) {
    return queryService.searchForArtist(term);
  }
}
