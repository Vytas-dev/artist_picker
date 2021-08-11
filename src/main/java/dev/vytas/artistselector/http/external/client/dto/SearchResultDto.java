package dev.vytas.artistselector.http.external.client.dto;

import java.io.Serializable;
import java.util.List;

public abstract class SearchResultDto<T> implements Serializable {
  public int resultCount;
  public List<T> results;
}
