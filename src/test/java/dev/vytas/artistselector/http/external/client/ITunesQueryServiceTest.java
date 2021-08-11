package dev.vytas.artistselector.http.external.client;

import dev.vytas.artistselector.http.external.client.dto.AlbumDto;
import dev.vytas.artistselector.http.external.client.dto.ArtistDto;
import dev.vytas.artistselector.http.external.client.dto.WrapperType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ITunesQueryServiceTest {

  @Autowired private ITunesQueryService service;

  @Autowired private RestTemplate restTemplate;

  private MockRestServiceServer mockServer;

  @BeforeEach
  public void init() {
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  @Test
  void searchForArtist() {
    String mockResponse =
        "{\n"
            + "  \"resultCount\":2,\n"
            + "  \"results\": [\n"
            + "    {\"wrapperType\":\"artist\", \"artistType\":\"Artist\", \"artistName\":\"ABBA\", \"artistLinkUrl\":\"https://music.apple.com/us/artist/abba/372976?uo=4\", \"artistId\":372976, \"amgArtistId\":3492, \"primaryGenreName\":\"Pop\", \"primaryGenreId\":14},\n"
            + "    {\"wrapperType\":\"artist\", \"artistType\":\"Artist\", \"artistName\":\"ABBA-DJ\", \"artistLinkUrl\":\"https://music.apple.com/us/artist/abba-dj/105311823?uo=4\", \"artistId\":105311823, \"amgArtistId\":612090, \"primaryGenreName\":\"Dance\", \"primaryGenreId\":17}\n"
            + "]\n"
            + "}";
    mockServer
        .expect(
            ExpectedCount.once(),
            requestTo("https://itunes.apple.com/search?entity=allArtist&term=ABBA"))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.OK).contentType(TEXT_PLAIN).body(mockResponse));

    List<ArtistDto> artists = service.searchForArtist("ABBA");
    mockServer.verify();
    assertEquals(artists.size(), 2);
    List<String> artistNames =
        artists.stream().map(artist -> artist.artistName).collect(Collectors.toList());
    assertTrue(artistNames.containsAll(List.of("ABBA", "ABBA-DJ")));
  }

  @Test
  void getTopAlbumsForArtist() {
    String mockResponse =
        "{\n"
            + "  \"resultCount\":6,\n"
            + "  \"results\": [\n"
            + "    {\"wrapperType\":\"artist\", \"artistType\":\"Artist\", \"artistName\":\"ABBA\", \"artistLinkUrl\":\"https://music.apple.com/us/artist/abba/372976?uo=4\", \"artistId\":372976, \"amgArtistId\":3492, \"primaryGenreName\":\"Pop\", \"primaryGenreId\":14},\n"
            + "    {\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":376154, \"collectionId\":1440767912, \"amgArtistId\":51752, \"artistName\":\"Benny Andersson, Björn Ulvaeus, Meryl Streep & Amanda Seyfried\", \"collectionName\":\"Mamma Mia! (The Movie Soundtrack feat. the Songs of ABBA) [Bonus Track Version]\", \"collectionCensoredName\":\"Mamma Mia! (The Movie Soundtrack feat. the Songs of ABBA) [Bonus Track Version]\", \"artistViewUrl\":\"https://music.apple.com/us/artist/benny-andersson/376154?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/mamma-mia-movie-soundtrack-feat-songs-abba-bonus-track/1440767912?uo=4\", \"artworkUrl60\":\"https://is3-ssl.mzstatic.com/image/thumb/Music115/v4/3f/a2/26/3fa22621-de13-9500-9e89-b76106ab2e26/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is3-ssl.mzstatic.com/image/thumb/Music115/v4/3f/a2/26/3fa22621-de13-9500-9e89-b76106ab2e26/source/100x100bb.jpg\", \"collectionPrice\":5.99, \"collectionExplicitness\":\"notExplicit\", \"trackCount\":18, \"copyright\":\"This Compilation ℗ 2008 Littlestar Services Limited, Under Exclusive License to Polydor Ltd.\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2008-01-01T08:00:00Z\", \"primaryGenreName\":\"Musicals\"},\n"
            + "    {\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":372976, \"collectionId\":1422648512, \"amgArtistId\":3492, \"artistName\":\"ABBA\", \"collectionName\":\"ABBA Gold: Greatest Hits\", \"collectionCensoredName\":\"ABBA Gold: Greatest Hits\", \"artistViewUrl\":\"https://music.apple.com/us/artist/abba/372976?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/abba-gold-greatest-hits/1422648512?uo=4\", \"artworkUrl60\":\"https://is3-ssl.mzstatic.com/image/thumb/Music115/v4/51/19/c3/5119c3a9-67c2-0d87-63f4-f9d891ad2628/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is3-ssl.mzstatic.com/image/thumb/Music115/v4/51/19/c3/5119c3a9-67c2-0d87-63f4-f9d891ad2628/source/100x100bb.jpg\", \"collectionPrice\":7.99, \"collectionExplicitness\":\"notExplicit\", \"trackCount\":19, \"copyright\":\"This Compilation ℗ 2014 Polar Music International AB\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2014-01-01T08:00:00Z\", \"primaryGenreName\":\"Pop\"},\n"
            + "    {\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":376154, \"collectionId\":1380464428, \"amgArtistId\":51752, \"artistName\":\"Benny Andersson, Björn Ulvaeus & Lily James\", \"collectionName\":\"Mamma Mia! Here We Go Again (The Movie Soundtrack feat. the Songs of ABBA)\", \"collectionCensoredName\":\"Mamma Mia! Here We Go Again (The Movie Soundtrack feat. the Songs of ABBA)\", \"artistViewUrl\":\"https://music.apple.com/us/artist/benny-andersson/376154?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/mamma-mia-here-we-go-again-movie-soundtrack-feat-songs/1380464428?uo=4\", \"artworkUrl60\":\"https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/8b/63/9d/8b639daa-dfe6-2c1d-78c4-1503e5607a6f/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/8b/63/9d/8b639daa-dfe6-2c1d-78c4-1503e5607a6f/source/100x100bb.jpg\", \"collectionPrice\":11.99, \"collectionExplicitness\":\"notExplicit\", \"trackCount\":19, \"copyright\":\"A Polydor Records release; ℗ 2018 Littlestar Services Limited, under exclusive licence to Universal Music Operations Limited\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2018-07-13T07:00:00Z\", \"primaryGenreName\":\"Musicals\"},\n"
            + "    {\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":372976, \"collectionId\":1440802799, \"amgArtistId\":3492, \"artistName\":\"ABBA\", \"collectionName\":\"More ABBA Gold\", \"collectionCensoredName\":\"More ABBA Gold\", \"artistViewUrl\":\"https://music.apple.com/us/artist/abba/372976?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/more-abba-gold/1440802799?uo=4\", \"artworkUrl60\":\"https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/a9/ac/ae/a9acae95-b67b-90c8-d1ca-1e076fffaa51/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is5-ssl.mzstatic.com/image/thumb/Music124/v4/a9/ac/ae/a9acae95-b67b-90c8-d1ca-1e076fffaa51/source/100x100bb.jpg\", \"collectionPrice\":7.99, \"collectionExplicitness\":\"notExplicit\", \"trackCount\":20, \"copyright\":\"This Compilation ℗ 2008 Polar Music International AB\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2008-01-01T08:00:00Z\", \"primaryGenreName\":\"Pop\"},\n"
            + "    {\"wrapperType\":\"collection\", \"collectionType\":\"Album\", \"artistId\":372976, \"collectionId\":1444205055, \"amgArtistId\":3492, \"artistName\":\"ABBA\", \"collectionName\":\"The Essential Collection\", \"collectionCensoredName\":\"The Essential Collection\", \"artistViewUrl\":\"https://music.apple.com/us/artist/abba/372976?uo=4\", \"collectionViewUrl\":\"https://music.apple.com/us/album/the-essential-collection/1444205055?uo=4\", \"artworkUrl60\":\"https://is2-ssl.mzstatic.com/image/thumb/Music114/v4/8a/82/0d/8a820d38-c1b7-fa71-f469-804e62e905e2/source/60x60bb.jpg\", \"artworkUrl100\":\"https://is2-ssl.mzstatic.com/image/thumb/Music114/v4/8a/82/0d/8a820d38-c1b7-fa71-f469-804e62e905e2/source/100x100bb.jpg\", \"collectionPrice\":17.99, \"collectionExplicitness\":\"notExplicit\", \"trackCount\":39, \"copyright\":\"This Compilation ℗ 2012 Polar Music International AB\", \"country\":\"USA\", \"currency\":\"USD\", \"releaseDate\":\"2012-01-01T08:00:00Z\", \"primaryGenreName\":\"Pop\"}]\n"
            + "}";
    mockServer
        .expect(
            ExpectedCount.once(),
            requestTo("https://itunes.apple.com/lookup?entity=album&limit=5&amgArtistId=3492"))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.OK).contentType(TEXT_PLAIN).body(mockResponse));

    List<AlbumDto> albums = service.getTopAlbumsForArtist(3492L);
    mockServer.verify();
    assertEquals(albums.size(), 6);
    List<String> albumNames =
        albums.stream()
            .filter(albumDto -> albumDto.wrapperType == WrapperType.COLLECTION)
            .map(album -> album.collectionName)
            .collect(Collectors.toList());

    assertTrue(
        albumNames.containsAll(
            List.of(
                "Mamma Mia! (The Movie Soundtrack feat. the Songs of ABBA) [Bonus Track Version]",
                "ABBA Gold: Greatest Hits",
                "Mamma Mia! Here We Go Again (The Movie Soundtrack feat. the Songs of ABBA)",
                "More ABBA Gold",
                "The Essential Collection")));
  }
}
