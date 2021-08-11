package dev.vytas.artistselector.persistence.repository;

import dev.vytas.artistselector.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
