package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setup() {
        memberRepository.save(new Member(20, "test1@test.com", "12345"));
        memberRepository.save(new Member(30, "test2@test.com", "abcdef"));
    }

    @DisplayName("단일 조회 테스트")
    @Test
    void findById() {
        Member actual = memberRepository.findById(1L).get();
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getId()).isNotNull()

        );
    }

    @DisplayName("전체 조회 테스트")
    @Test
    void findAll() {
        int expectLength = 2;

        List<Member> actualAll = memberRepository.findAll();

        assertThat(actualAll).hasSize(expectLength);
    }

    @Test
    @DisplayName("insert 테스트")
    void insert() {
        final Member expected = new Member(40, "test3@test.com", "abcdef");

        final Member actual = memberRepository.save(expected);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo("test3@test.com")
        );
    }

    @Test
    @DisplayName("update 테스트")
    void update() {
        String newEmail = "test3@test.com";
        Member member1 = memberRepository.findByEmail("test2@test.com");
        member1.changeEmail(newEmail);
        Member member2 = memberRepository.findByEmail("test3@test.com");
        assertThat(member2).isNotNull();

    }


}