package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

    Station findByName(String name);
}
