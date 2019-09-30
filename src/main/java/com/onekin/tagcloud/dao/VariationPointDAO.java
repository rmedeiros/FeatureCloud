package com.onekin.tagcloud.dao;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.VariationPoint;

import java.util.List;

public interface VariationPointDAO {


    List<VariationPoint> getFeatureVariationPoints(String featureName);

    List<VariationPoint> getVariationPointsFiltered(Filter filter);

    List<CustomDiff> getDiffValues(Integer variationPointId);

    List<VariationPoint> getReleaseVariationPoints(String featureName);

    CoreAsset getVPContentAndAsset(Integer variationPointId);
}
