package com.example.catalogsservice.repository;

import com.example.catalogsservice.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
    CatalogEntity findByProductId(String productId);
}
