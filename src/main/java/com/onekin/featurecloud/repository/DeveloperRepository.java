package com.onekin.featurecloud.repository;


import com.onekin.featurecloud.model.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends CrudRepository<Developer, Long> {

}
