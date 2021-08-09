package dev.vytas.artistselector.http.controller;

import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.persistence.model.Album;
import dev.vytas.artistselector.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "{id}/albums", produces = "application/json")
    public @ResponseBody
    Set<Album> getTopAlbums(@PathVariable Long id) {
        var user = userService.getUser(id);
        return user.getArtist().getAlbums();
    }

    @PostMapping(value = "/{id}/artist", produces = "application/json")
    public void saveArtist(@RequestBody ArtistDto artistDto, @PathVariable Long id) {
        log.debug("Saving artist {} for user {}", artistDto.artistName, id);
        userService.saveArtist(id, artistDto);
    }
}
