package com.onekin.tagcloud.dao;

import java.util.List;

import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;

public interface FeatureDAO {

	List<Feature> getFeatures();

	List<Feature> getFeaturesFiltered(Filter filter);

	String getTanglingFeatureList(List<String> featureIdList);

}
