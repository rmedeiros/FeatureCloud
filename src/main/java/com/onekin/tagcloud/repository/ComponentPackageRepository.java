package com.onekin.tagcloud.repository;

import com.onekin.tagcloud.model.ComponentPackage;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ComponentPackageRepository extends CrudRepository<ComponentPackage, Long> {


}
