package com.onekin.tagcloud.dao;

import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;

import java.util.List;

public interface FeatureDAO {

    List<Feature> getFeatures();

    List<Feature> getFeaturesFiltered(Filter filter);

    String getTanglingFeatureList(List<String> featureIdList);

    List<Feature> getAllFeatures();

    String getDeltaTangling(List<String> featureIdList);

    List<Feature> getFeaturesByProduct(String productId, int packageId);

    String getDeltaTanglingByProduct(List<String> featureIdList);


}
