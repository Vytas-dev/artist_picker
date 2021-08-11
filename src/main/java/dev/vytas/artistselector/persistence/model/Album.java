package dev.vytas.artistselector.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.vytas.artistselector.http.external.client.dto.AlbumDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "album")
public class Album {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "artist_id", nullable = false)
  private Artist artist;

  private String name;

  private Date releaseDate;

  @CreationTimestamp
  @Column(name = "created_at")
  private Date createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Date updatedAt;

  public Album(Artist artist, String name, Date releaseDate) {
    this.artist = artist;
    this.name = name;
    this.releaseDate = releaseDate;
  }
}
