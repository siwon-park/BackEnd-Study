package com.mysite.sbb.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne // N:1 관계 설정(질문 하나에 답변은 여러 개 달릴 수 있음)
    private Question question;
}

