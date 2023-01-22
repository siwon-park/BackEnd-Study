package com.example.catalogsservice.controller;

import com.example.catalogsservice.dto.ResponseCatalogDto;
import com.example.catalogsservice.entity.CatalogEntity;
import com.example.catalogsservice.service.CatalogServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalogs-service")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment env;
    private final CatalogServiceImpl catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalogDto>> getCatalogs() {
        Iterable<CatalogEntity> catalogList = catalogService.getAllCatalogs();

        List<ResponseCatalogDto> result = new ArrayList<>();
        catalogList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCatalogDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
