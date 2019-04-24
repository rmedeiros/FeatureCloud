package com.onekin.tagcloud.dao;

import java.util.List;

import org.springframework.data.util.Pair;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.VariationPoint;

public interface VariationPointDAO {

	

	List<VariationPoint> getFeatureVariationPoints(String featureName);

	List<VariationPoint> getVariationPointsFiltered(Filter filter);

	List<CustomDiff> getDiffValues(Integer variationPointId);

	List<VariationPoint> getReleaseVariationPoints(String featureName);

	CoreAsset getVPContentAndAsset(Integer variationPointId);
}
