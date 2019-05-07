package com.onekin.tagcloud.service;

import java.util.List;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.FeatureSibling;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.Product;

public interface SoftwareProductLineService {

	List<Feature> getFeatures();

	CoreAsset getCoreAssetContent(Integer coreAssetId);

	List<Feature> getFeaturesFiltered(Filter filter);

	List<CustomDiff> getDiffValues(Integer variationPointId);

	String getNewickTree(List<String> collect);

	List<FeatureSibling> getModifiedFeaturesiblings(String idFeature);

	Iterable<Product> getProductIds();


	List<Feature> getFeaturesFilteredByProduct(String productId);

	String getNewickTreeByProduct(List<String> collect);

}
