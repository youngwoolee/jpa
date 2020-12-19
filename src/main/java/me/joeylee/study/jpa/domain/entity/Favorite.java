package me.joeylee.study.jpa.domain.entity;

import javax.persistence.*;

@Entity
public class Favorite extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Station departureStation;

    @OneToOne
    private Station arrivedStation;

    public Long getId() {
        return id;
    }

}
