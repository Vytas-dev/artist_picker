package dev.vytas.artistselector.persistence.repository;

import dev.vytas.artistselector.persistence.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
