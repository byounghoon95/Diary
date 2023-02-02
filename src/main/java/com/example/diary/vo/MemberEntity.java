package com.example.diary.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "Member")
@Entity
public class MemberEntity {
    @Id
    private Long id;
    @Column(unique = true, nullable = false)
    private String memId;
    @NotNull
    private String password;
    @NotNull
    private String name;

}
