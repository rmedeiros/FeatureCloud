package com.onekin.tagcloud.dao;

import com.onekin.tagcloud.model.FeatureSibling;

import java.util.List;

public interface FeatureSiblingDAO {

    List<FeatureSibling> getModifiedFeaturesiblings(String id_feature);

}
