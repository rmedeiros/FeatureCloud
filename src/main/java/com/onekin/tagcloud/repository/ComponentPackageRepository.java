package com.onekin.tagcloud.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.onekin.tagcloud.model.ComponentPackage;

@Transactional
public interface ComponentPackageRepository extends CrudRepository<ComponentPackage, Long> {


}
