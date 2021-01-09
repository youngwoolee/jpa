package me.joeylee.study.jpa.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class Line extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> lineStations = new ArrayList<>();


    public void addStationLines(LineStation lineStation) {
        lineStations.add(lineStation);
        lineStation.setLine(this);
    }

    public Line(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
