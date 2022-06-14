package com.example.icc.repository;

import com.example.icc.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface ContentRepository extends JpaRepository<Content, Integer> {
    public List<Content> findTop100ByOrderByUidDesc(); // Top100 내림차순, uid 오름차순 정렬

}
