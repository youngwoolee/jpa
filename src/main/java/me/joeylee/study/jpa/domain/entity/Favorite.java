package me.joeylee.study.jpa.domain.entity;

import javax.persistence.*;

@Entity
public class Favorite extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "start__id")
    private Station departureStation;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Station arrivedStation;

    public Long getId() {
        return id;
    }

}
