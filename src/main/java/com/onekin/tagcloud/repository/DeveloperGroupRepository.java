package com.onekin.tagcloud.repository;

import com.onekin.tagcloud.model.DeveloperGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperGroupRepository extends CrudRepository<DeveloperGroup, Integer> {

}
