package com.onekin.featurecloud.service;

import com.onekin.featurecloud.exceptions.CoreAssetNotFoundException;
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

    List<FeatureSibling> getFeatureFeatureSiblings(String featureName);

    String getNewickTreeFiltered(List<String> featureIdList, int packageId);

    List<VariationPoint> getFeatureSiblingVariationPointsBody(Integer featureSiblingId);

    CoreAsset getCoreAsset(Integer coreaAssetId) throws CoreAssetNotFoundException;
}
