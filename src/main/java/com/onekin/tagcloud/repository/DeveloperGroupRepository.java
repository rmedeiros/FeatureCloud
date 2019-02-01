package com.onekin.tagcloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.tagcloud.model.DeveloperGroup;

@Repository
public interface DeveloperGroupRepository extends CrudRepository<DeveloperGroup, Integer> {

}
