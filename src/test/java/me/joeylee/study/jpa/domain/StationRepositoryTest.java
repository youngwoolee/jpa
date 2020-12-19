package me.joeylee.study.jpa.domain;

import me.joeylee.study.jpa.domain.entity.Line;
import me.joeylee.study.jpa.domain.entity.Station;
import me.joeylee.study.jpa.domain.repository.LineRepository;
import me.joeylee.study.jpa.domain.repository.StationRepository;
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
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineRepository lineRepository;



    @BeforeEach
    void setUp() {
        stationRepository.save(new Station("잠실역"));
        stationRepository.save(new Station("교대역"));
    }


    @Test
    void save() {
        final Station expected = new Station("몽촌토성역");

        final Station actual = stationRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("몽촌토성역")
        );
    }

    @Test
    @DisplayName("동일한 이름이 insert 되면 DataIntegrityViolationException이 발생한다.")
    void insertDuplicateName() {
        Station newStation = new Station("잠실역");

        assertThatExceptionOfType(DataIntegrityViolationException.class)
                .isThrownBy(() -> {
                    stationRepository.save(newStation);
                });
    }

    @DisplayName("단일 조회 테스트")
    @Test
    void findByName() {
        String expected = "잠실역";
        final Station actual = stationRepository.findByName(expected);
        assertThat(actual.getName()).isEqualTo(expected);
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        int expectedLength = 2;
        List<Station> actualAll = stationRepository.findAll();

        List<String> nameAll = actualAll.stream().map(Station::getName).collect(Collectors.toList());

        assertAll(
                () -> assertThat(actualAll).hasSize(expectedLength),
                () -> assertThat(nameAll).contains("잠실역", "교대역")
        );
    }

    @Test
    void findByName1() {
        String expected = "잠실역";
        stationRepository.save(new Station("잠실역"));
        String actual = stationRepository.findByName(expected).getName();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void identify() {
        final Station station1 = stationRepository.save(new Station("잠실역"));
        final Station station2 = stationRepository.findById(station1.getId()).get() ;
        assertThat(station1 == station2).isTrue();

        final Station station3 = stationRepository.findByName("잠실역");
        assertThat(station1 == station3).isTrue();
    }

    @Test
    void update() {
        final Station station1 = stationRepository.save(new Station("잠실역"));
        station1.changeName("몽촌토성역");
        final Station station2 = stationRepository.findByName("몽촌토성역"); //영속성컨텍스트에서 name으로는 조회 불가능
        assertThat(station2).isNotNull();

    }

    @Test
    void 지하철역이_어느_노선에_속해있는지_알수있다() {
        final Line line = lineRepository.save(new Line("2호선"));
        final Station station = new Station("잠실역");
        station.setLine(line);
        //persist
        final Station actual = stationRepository.save(station);

        final Station station2 = stationRepository.findByName("잠실역");
        assertThat(station2.getLine().getName()).isEqualTo("2호선");
    }

    @Test
    void findByNameWithLine() {
        final Station station = stationRepository.findByName("교대역");
        assertThat(station).isNotNull();
        assertThat(station.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    void updateWithLine() {
        final Station station = stationRepository.findByName("교대역");
        station.setLine(lineRepository.save(new Line("2호선")));
        stationRepository.flush();
    }

    @Test
    void removeWithLine() {
        final Station station = stationRepository.findByName("교대역");
        station.setLine(null);
        stationRepository.flush();
    }

    @Test
    void findById() {
        final Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
    }

    @Test
    void master() {
        final Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
        final Station station = new Station("양재역");
        stationRepository.save(station);
        line.getStations().add(station);
        assertThat(line.getStations()).hasSize(2);
        stationRepository.flush();
    }

    @Test
    void 편의_메서드() {
        final Line line = lineRepository.findByName("3호선");
        assertThat(line.getStations()).hasSize(1);
        final Station station = new Station("양재역");
        stationRepository.save(station);

        line.addStation(station);
        assertThat(line.getStations()).hasSize(2);

        stationRepository.flush();
    }

}