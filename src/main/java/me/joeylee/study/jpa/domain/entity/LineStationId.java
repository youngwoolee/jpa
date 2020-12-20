package me.joeylee.study.jpa.domain.entity;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class LineStationId implements Serializable {

    private Long line;

    private Long station;
}
