package com.onekin.tagcloud.dao;

import java.util.List;

import com.onekin.tagcloud.model.FeatureSibling;

public interface FeatureSiblingDAO {

	List<FeatureSibling> getModifiedFeaturesiblings(String id_feature);

}
