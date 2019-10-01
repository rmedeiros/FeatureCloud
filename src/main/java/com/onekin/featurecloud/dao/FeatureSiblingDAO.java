package com.onekin.featurecloud.dao;

import com.onekin.featurecloud.model.FeatureSibling;

import java.util.List;

public interface FeatureSiblingDAO {

    List<FeatureSibling> getModifiedFeaturesiblings(String id_feature);

}
