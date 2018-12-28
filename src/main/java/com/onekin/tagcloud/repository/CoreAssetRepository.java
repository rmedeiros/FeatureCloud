package com.onekin.tagcloud.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onekin.tagcloud.model.CoreAsset;

@Repository
public interface CoreAssetRepository extends CrudRepository <CoreAsset, Long>{

	CoreAsset getCoreAssetByIdcoreasset(int idcoreasset);
}
