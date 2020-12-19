package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
