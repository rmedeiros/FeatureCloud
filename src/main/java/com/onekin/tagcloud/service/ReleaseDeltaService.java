package com.onekin.tagcloud.service;

import com.onekin.tagcloud.model.*;

import java.util.List;

public interface ReleaseDeltaService {

    List<Feature> getFeatures();

    CoreAsset getCoreAssetContent(Integer coreAssetId);

    List<Feature> getFeaturesFiltered(Filter filter);

    List<CustomDiff> getDiffValues(Integer variationPointId);

    String getNewickTree(List<String> collect);

    List<FeatureSibling> getModifiedFeaturesiblings(String idFeature);

    Iterable<Product> getProductIds();


    List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId);

    String getNewickTreeByProduct(List<String> collect);

    Iterable<ComponentPackage> getComponentPackages();

}
