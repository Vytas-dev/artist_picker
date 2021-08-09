package dev.vytas.artistselector.persistence.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "artist"/*, indexes = @Index(name = "amgArtistIdIdx", columnList = "amgArtistId", unique = true)*/)
public class Artist {

    @Id
    private Long id; // its unique amgArtistId from JSON

    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy="artist", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy="artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Album> albums = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
