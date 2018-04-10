package com.practice.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.practice.model.Product;

@RepositoryRestResource
public interface ProductRepository extends CrudRepository<Product, Long> {

}
