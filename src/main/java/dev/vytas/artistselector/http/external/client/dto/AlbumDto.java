package dev.vytas.artistselector.http.external.client.dto;

import java.util.Date;

public class AlbumDto extends ArtistDto {
    public String collectionType;
    public Long collectionId;
    public String collectionName;
    public String collectionCensoredName;
    public String artistViewUrl;
    public String collectionViewUrl;
    public String artworkUrl60;
    public String artworkUrl100;
    public double collectionPrice;
    public String collectionExplicitness;
    public int trackCount;
    public String copyright;
    public String country;
    public String currency;
    public Date releaseDate;
}
