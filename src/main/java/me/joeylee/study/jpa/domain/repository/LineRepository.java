package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
    Line findByName(String name);
}
