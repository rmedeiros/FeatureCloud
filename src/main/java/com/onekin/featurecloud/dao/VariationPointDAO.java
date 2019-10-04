package com.onekin.featurecloud.dao;

import com.onekin.featurecloud.model.CustomDiff;
import com.onekin.featurecloud.model.Filter;
import com.onekin.featurecloud.model.VariationPoint;

import java.util.List;

public interface VariationPointDAO {


    List<VariationPoint> getFeatureVariationPoints(String featureName);

    List<VariationPoint> getVariationPointsFiltered(Filter filter);

    List<CustomDiff> getDiffValues(Integer variationPointId);
    

    List<VariationPoint> getVariationPointsByFeatureSibling(Integer featureSiblingId);
}
