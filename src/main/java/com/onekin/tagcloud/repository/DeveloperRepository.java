package com.onekin.tagcloud.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.tagcloud.model.Developer;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {

}
