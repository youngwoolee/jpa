package me.joeylee.study.jpa.domain.repository;

import me.joeylee.study.jpa.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
