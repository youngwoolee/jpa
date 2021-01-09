package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Line;
import me.joeylee.study.jpa.domain.entity.LineStation;
import me.joeylee.study.jpa.domain.entity.PreviousStation;
import me.joeylee.study.jpa.domain.entity.Station;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LineStationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineStationRepository lineStationRepository;

    @Test
    void save() {
        PreviousStation previousStation = new PreviousStation(new Station("강남역"), 3);
        Station station = stationRepository.save(new Station("사당역"));
        Line line = lineRepository.save(new Line("2호선", " GREEN"));

        LineStation lineStation = new LineStation(previousStation, line, station);

        LineStation actual = lineStationRepository.save(lineStation);

        assertThat(lineStation.getLine().getId()).isEqualTo(actual.getLine().getId());
    }
}
