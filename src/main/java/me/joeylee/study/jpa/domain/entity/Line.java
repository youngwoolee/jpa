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
@Table(indexes = {@Index(name = "UK_line_name",unique = true, columnList="name")})
public class Line extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private String name;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineStation> stations = new ArrayList<>();


    public Line(final String name, final String color) {
        this.name = name;
        this.color = color;
    }

    public void updateName(String name) {
        this.name = name;
    }
}
