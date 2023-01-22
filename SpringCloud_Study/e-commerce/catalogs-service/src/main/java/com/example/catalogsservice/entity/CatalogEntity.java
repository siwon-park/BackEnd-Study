package com.example.catalogsservice.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "catalog")
public class CatalogEntity implements Serializable { // 데이터 직렬화
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false, updatable = false, insertable = false) // 업데이트 불가, 삽입 불가(기본값 지정 필요)
    @ColumnDefault(value = "CURRENT_TIMESTAMP") // 현재 시간을 기본값으로 삽입
    private Date createdAt;

}
