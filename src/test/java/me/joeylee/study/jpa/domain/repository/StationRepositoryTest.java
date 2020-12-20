package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Line;
import me.joeylee.study.jpa.domain.entity.LineStation;
import me.joeylee.study.jpa.domain.entity.Station;
import me.joeylee.study.jpa.domain.repository.LineRepository;
import me.joeylee.study.jpa.domain.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
class StationRepositoryTest {

    @Autowired
    private StationRepository stationRepository;


    @BeforeEach
    void setUp() {
        Line line1 = new Line("2호선", "GREEN");
        Line line2 = new Line("4호선", "BLUE");
        stationRepository.save(new Station("잠실역", Arrays.asList(line1)));
        stationRepository.save(new Station("교대역", Arrays.asList(line1, line2)));
    }


    @Test
    @DisplayName("insert 테스트")
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
    @DisplayName("update 테스트")
    void update() {
        final Station station1 = stationRepository.findByName("잠실역");
        station1.changeName("몽촌토성역");
        final Station station2 = stationRepository.findByName("몽촌토성역"); //영속성컨텍스트에서 name으로는 조회 불가능
        assertThat(station2).isNotNull();

    }

    @Test
    @DisplayName("delete 테스트")
    void delete() {
        Station station = stationRepository.findByName("잠실역");
        stationRepository.delete(station);
        Station check = stationRepository.findByName("잠실역");

        assertThat(check).isNull();
    }

    @Test
    @DisplayName("1차캐시 테스트")
    void search() {
        Optional<Station> station = stationRepository.findById(1L);
        Optional<Station> station1 = stationRepository.findById(1L);

        assertThat(station).isEqualTo(station1);
    }

    @Test
    @DisplayName("지하철역 조회 시 어느 노선에 속한지 볼 수 있다.")
    void lineStation() {
        Station station = stationRepository.findByName("교대역");
        List<LineStation> lineStations = station.getLineStations();
        List<Line> lines = lineStations.stream()
                .map(LineStation::getLine)
                .collect(Collectors.toList());
        List<String> lineNames = lines.stream()
                .map(Line::getName)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(lines).hasSize(2),
                () -> assertThat(lineNames).contains("2호선", "4호선")
        );
    }


}