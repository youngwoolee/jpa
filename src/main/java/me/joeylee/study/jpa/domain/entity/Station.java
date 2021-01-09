package me.joeylee.study.jpa.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(indexes = {@Index(name = "UK_station_name",unique = true, columnList="name")})
public class Station extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> lineStations = new ArrayList<>();

    public Station(String name) {
        this.name = name;
    }

    public Station(String name, List<Line> lines) {
        this.name = name;
        this.lineStations = lines.stream()
                .map(line -> new LineStation(null, line, this))
                .collect(Collectors.toList());
    }

    public void changeName(final String name) {
        this.name = name;
    }

}
