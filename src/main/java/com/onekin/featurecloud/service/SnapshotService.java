package com.onekin.featurecloud.service;

import com.onekin.featurecloud.model.*;

import java.util.List;

public interface SnapshotService {
    List<Feature> getFeatures();

    CoreAsset getCoreAssetContent(Integer coreAssetId);

    List<Feature> getFeaturesFiltered(Filter filter);

    String getNewickTree(List<String> collect);

    Iterable<Product> getProductIds();

    List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId);

    Iterable<ComponentPackage> getComponentPackages();

    List<VariationPoint> getReleaseVariationPoint(String featureName);

    CoreAsset getVariationPointBody(Integer variationPointId);
}
