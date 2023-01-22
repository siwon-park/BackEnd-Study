package com.example.catalogsservice.service;

import com.example.catalogsservice.entity.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
