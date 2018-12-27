package com.onekin.tagcloud.dao;

import java.util.List;

import com.onekin.tagcloud.model.VariationPoint;

public interface VariationPointDAO {

	
	List<VariationPoint> getAllVariationPoints();

	List<VariationPoint> getVariationPointsWithFeature();

	List<VariationPoint> getFeatureVariationPoints(String featureName);
}
