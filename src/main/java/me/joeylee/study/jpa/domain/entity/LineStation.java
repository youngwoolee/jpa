package me.joeylee.study.jpa.domain.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@Getter
@Entity
@IdClass(LineStationId.class)
public class LineStation extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Embedded
    private PreviousStation previousStation;

    public LineStation(PreviousStation previousStation, Line line, Station station) {
        this.previousStation = previousStation;
        this.line = line;
        this.station = station;
    }
}
