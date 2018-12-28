package com.onekin.tagcloud.service;

import java.util.List;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.VariationPoint;

public interface SoftwareProductLineService {

	List<VariationPoint> getAllVariationPoints();
	
	List<VariationPoint> getVariationPointsWithFeature();

	List<Feature> getFeatures();

	List<VariationPoint> getFeatureVariationPoints(String featureName);

	CoreAsset getCoreAssetContent(Integer coreAssetId);

}
