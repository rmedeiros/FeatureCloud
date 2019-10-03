package com.onekin.featurecloud.dao;

import com.onekin.featurecloud.model.Feature;
import com.onekin.featurecloud.model.Filter;

import java.util.List;

public interface FeatureDAO {

    List<Feature> getFeatures();

    List<Feature> getFeaturesFiltered(Filter filter);

    String getTanglingFeatureList(List<String> featureIdList);

    String getTanglingFeatureListByPackage(List<String> featureIdList, int packageId);

    List<Feature> getAllFeatures();

    List<Feature> getSnapshotFeaturesByProduct(String productId, int packageId);

    String getDeltaTangling(List<String> featureIdList);

    List<Feature> getFeaturesByProduct(String productId, int packageId);

    String getDeltaTanglingByProduct(List<String> featureIdList);


}
