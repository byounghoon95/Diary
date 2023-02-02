package com.example.diary.login.repository;

import com.example.diary.vo.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<MemberEntity,Long> {

}
