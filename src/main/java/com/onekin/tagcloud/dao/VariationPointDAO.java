package com.onekin.tagcloud.dao;

import java.util.List;

import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.VariationPoint;

public interface VariationPointDAO {

	

	List<VariationPoint> getFeatureVariationPoints(String featureName);

	List<VariationPoint> getVariationPointsFiltered(Filter filter);
}
