package dev.vytas.artistselector.http.external.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.vytas.artistselector.http.external.client.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ITunesQueryService implements QueryService {
    private final String ITUNES_API_URL = "https://itunes.apple.com/"; // TODO: make more general (eg. 'EXTERNAL_API') and maybe move to the properties file
    private final RestTemplate restTemplate;

    @Override
    public List<ArtistDto> searchForArtist(String searchTerm) {
        String url = ITUNES_API_URL + "search?entity=allArtist&term=" + searchTerm;
        log.debug("Searching for artist by term {}, url: {}", searchTerm, url);
        // we're reading to String first since API returns text/javascript and not application/json
        String response = restTemplate.postForObject(url, null, String.class);
        // TODO: handle exceptions from external API
        ArtistSearchResultDto result = mapJsonStringToObject(response, ArtistSearchResultDto.class);
        return result != null ? result.results : Collections.emptyList();
    }

    @Override
    public List<AlbumDto> getTopAlbumsForArtist(Long artistId) {
        String url = ITUNES_API_URL + "lookup?entity=album&limit=5&amgArtistId=" + artistId;
        log.debug("Fetching top albums for artist id {}, url: {}", artistId, url);
        String response = restTemplate.postForObject(url, null, String.class);
        AlbumSearchResultDto result = mapJsonStringToObject(response, AlbumSearchResultDto.class);
        // TODO: handle exceptions from external API
        return result != null ? result.results : Collections.emptyList();
    }

    private <T> T mapJsonStringToObject(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        T result = null;
        try {
            result = objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }
}
