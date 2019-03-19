package com.onekin.tagcloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.tagcloud.model.ProductRelease;

@Repository
public interface ProductReleaseRepository extends CrudRepository<ProductRelease, String>{

	Iterable<ProductRelease> getProductReleaseByName(String name);

}
