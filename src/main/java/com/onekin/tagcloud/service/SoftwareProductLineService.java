package com.onekin.tagcloud.service;

import java.util.List;

import org.springframework.data.util.Pair;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Developer;
import com.onekin.tagcloud.model.DeveloperGroup;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.ProductRelease;
import com.onekin.tagcloud.model.VariationPoint;

public interface SoftwareProductLineService {



	List<Feature> getFeatures();

	List<VariationPoint> getFeatureVariationPoints(String featureName);

	CoreAsset getCoreAssetContent(Integer coreAssetId);

	Iterable<ProductRelease> getProductRealeses();

	Iterable<Developer> getDevelopers();


	List<Feature> getFeaturesFiltered(Filter filter);

	List<VariationPoint> getVariationPointsFiltered(Filter filter);

	DeveloperGroup getFilterDeveloper(Iterable<DeveloperGroup> developerGroups, int developerId);

	ProductRelease getFilterProduct(Iterable<ProductRelease> productReleases, String productId);

	Iterable<DeveloperGroup> getDeveloperGroups();

	List<Pair<String, String>> getDiffValues(Integer variationPointId);

	String getNewickTree(List<String> collect);

}
