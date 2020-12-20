package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Line;
import me.joeylee.study.jpa.domain.entity.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class LineRepositoryTest {

    @Autowired
    private LineRepository lineRepository;

    @BeforeEach
    void setUp() {
        lineRepository.save(new Line("2호선", "GREEN"));
        lineRepository.save(new Line("4호선", "BLUE"));
    }

    @Test
    @DisplayName("단일 조회 테스트")
    void findByName() {
        String expected = "2호선";
        String actual = lineRepository.findByName(expected).getName();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        int expectedLength = 2;

        List<Line> actualAll = lineRepository.findAll();
        List<String> nameAll = actualAll.stream().map(Line::getName).collect(Collectors.toList());

        assertAll(
                () -> assertThat(actualAll).hasSize(expectedLength),
                () -> assertThat(nameAll).contains("2호선", "4호선")
        );

    }

    @Test
    @DisplayName("insert 테스트")
    void insert() {
        Line expected = new Line("3호선", "CYAN");
        Line actual = lineRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getColor()).isEqualTo(expected.getColor())
        );

    }

    @Test
    @DisplayName("동일한 이름이 insert 되면 DataIntegrityViolationException이 발생한다.")
    void insertDuplicateName() {
        Line newLine = new Line("2호선", "GREEN");

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    lineRepository.save(newLine);
                });
    }

    @Test
    @DisplayName("update 테스트")
    void update() {
        String expected = "3호선";
        Line line = lineRepository.findByName("2호선");
        line.updateName(expected);
        Line check = lineRepository.findByName(expected);

        assertAll(
                () -> assertThat(check.getId()).isEqualTo(line.getId()),
                () -> assertThat(check.getName()).isEqualTo(line.getName())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() {
        Line line = lineRepository.findByName("2호선");
        lineRepository.delete(line);
        Line check = lineRepository.findByName("2호선");

        assertThat(check).isNull();
    }

//
//    @Test
//    void 지하철역이_어느_노선에_속해있는지_알수있다() {
//        final Line line = lineRepository.save(new Line("2호선"));
//        final Station station = new Station("잠실역");
//        station.setLine(line);
//        //persist
//        final Station actual = stationRepository.save(station);
//
//        final Station station2 = stationRepository.findByName("잠실역");
//        assertThat(station2.getLine().getName()).isEqualTo("2호선");
//    }
//
//    @Test
//    void findByNameWithLine() {
//        final Station station = stationRepository.findByName("교대역");
//        assertThat(station).isNotNull();
//        assertThat(station.getLine().getName()).isEqualTo("3호선");
//    }
//
//    @Test
//    void updateWithLine() {
//        final Station station = stationRepository.findByName("교대역");
//        station.setLine(lineRepository.save(new Line("2호선")));
//        stationRepository.flush();
//    }
//
//    @Test
//    void removeWithLine() {
//        final Station station = stationRepository.findByName("교대역");
//        station.setLine(null);
//        stationRepository.flush();
//    }
//
//    @Test
//    void findById() {
//        final Line line = lineRepository.findByName("3호선");
//        assertThat(line.getStations()).hasSize(1);
//    }
//
//    @Test
//    void master() {
//        final Line line = lineRepository.findByName("3호선");
//        assertThat(line.getStations()).hasSize(1);
//        final Station station = new Station("양재역");
//        stationRepository.save(station);
//        line.getStations().add(station);
//        assertThat(line.getStations()).hasSize(2);
//        stationRepository.flush();
//    }
//
//    @Test
//    void 편의_메서드() {
//        final Line line = lineRepository.findByName("3호선");
//        assertThat(line.getStations()).hasSize(1);
//        final Station station = new Station("양재역");
//        stationRepository.save(station);
//
//        line.addStation(station);
//        assertThat(line.getStations()).hasSize(2);
//
//        stationRepository.flush();
//    }

}