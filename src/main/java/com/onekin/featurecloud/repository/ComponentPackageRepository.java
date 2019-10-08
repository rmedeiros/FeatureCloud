package com.onekin.featurecloud.repository;

import com.onekin.featurecloud.model.ComponentPackage;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ComponentPackageRepository extends CrudRepository<ComponentPackage, Long> {


}
