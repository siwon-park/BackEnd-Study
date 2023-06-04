package com.example.demo.repository;

import com.example.demo.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.entity.QProduct.product;

@Component
@RequiredArgsConstructor
public class ProductQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Product> findAllBySoldOut(boolean status) {
        return jpaQueryFactory
                .select(product) // Qclass
                .from(product)
                .where(product.isSoldOut.eq(status))
                .fetch();
    }
}
