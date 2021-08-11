package dev.vytas.artistselector.persistence.repository;

import dev.vytas.artistselector.persistence.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {}
