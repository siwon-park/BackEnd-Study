package com.mysite.sbb.model;

import java.util.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // @Entity 애너테이션을 적용해야 JPA가 해당 클래스를 Entity로 인식한다
public class Question {
    @Id // @Id 애너테이션을 적용하여 id(primary key)로 지정 가능
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTOINCREMENT와 유사 // strategy는 고유번호 생성
    private Integer id;

    @Column(length = 200) // @Column 애너테이션을 사용하지 않더라도 필드로 인식함
    private String title;

    @Column(columnDefinition = "TEXT") // 필드(칼럼)으로 인식하고 싶지 않을 경우엔 @Transient 애너테이션을 사용
    private String content;

    private LocalDateTime createdAt; // 실제 DB에 필드명은 created_at으로 생성됨

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // @OneToMany 1:N관계 설정 => 역참조
    private List<Answer> answerList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
