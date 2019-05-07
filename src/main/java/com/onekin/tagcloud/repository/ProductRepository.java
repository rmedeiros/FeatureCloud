package com.onekin.tagcloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.tagcloud.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

}
