package com.mysite.sbb.repository;

import com.mysite.sbb.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findByTitle(String title);
    Question findByTitleAndContent(String title, String content);
    List<Question> findByTitleLike(String title);
}
