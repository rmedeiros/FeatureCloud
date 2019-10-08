package com.onekin.featurecloud.repository;

import com.onekin.featurecloud.model.DeveloperGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperGroupRepository extends CrudRepository<DeveloperGroup, Integer> {

}
