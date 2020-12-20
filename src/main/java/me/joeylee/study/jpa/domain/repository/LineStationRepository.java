package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.LineStation;
import me.joeylee.study.jpa.domain.entity.LineStationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineStationRepository extends JpaRepository<LineStation, LineStationId> {

}
