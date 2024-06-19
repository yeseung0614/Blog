package org.example.blog_project.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findMemberByLoginId(String loginId);
    Member findMemberByEmail(String email);
}
