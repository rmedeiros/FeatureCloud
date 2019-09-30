package com.onekin.tagcloud.repository;

import com.onekin.tagcloud.model.CoreAsset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoreAssetRepository extends CrudRepository<CoreAsset, Long> {

    CoreAsset getCoreAssetByIdcoreasset(int idcoreasset);
}
