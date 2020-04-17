package com.ultipro.main.repository;

import com.ultipro.main.Beans.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {

    Optional<Product> findById(long id);

    Product deleteById(Long id);
}
