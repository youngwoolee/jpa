package me.joeylee.study.jpa.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int age;

    private String email;

    private String password;

    @OneToMany(mappedBy = "member")
    private List<Favorite> favorites = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Member(int age, String email, String password) {
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }
}
