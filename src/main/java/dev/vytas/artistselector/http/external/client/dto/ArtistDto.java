package dev.vytas.artistselector.http.external.client.dto;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class ArtistDto implements Serializable {
  public WrapperType wrapperType;
  public String artistType;
  public String artistName;
  public String artistLinkUrl;
  public Long artistId;
  public Long amgArtistId;
  public String primaryGenreName;
  public Long primaryGenreId;
}
