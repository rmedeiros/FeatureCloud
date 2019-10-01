package com.onekin.featurecloud.service;

import com.onekin.featurecloud.dao.FeatureDAO;
import com.onekin.featurecloud.dao.VariationPointDAO;
import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.utils.NewickUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SnapshotServiceImpl implements SnapshotService {

    @Autowired
    private FeatureDAO featuresDao;

    @Autowired
    private VariationPointDAO variationPointDao;

    public List<Feature> getFeatures() {
        return featuresDao.getAllFeatures();
    }

    @Override
    public CoreAsset getCoreAssetContent(Integer coreAssetId) {
        return null;
    }

    @Override
    public List<Feature> getFeaturesFiltered(Filter filter) {
        return null;
    }

    @Override
    public String getNewickTree(List<String> featureIdList) {
        String featureStringList = featuresDao.getTanglingFeatureList(featureIdList);
        return NewickUtils.getNewickFormatString(featureStringList);
    }

    @Override
    public Iterable<Product> getProductIds() {
        return null;
    }

    @Override
    public List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId) {
        return null;
    }

    @Override
    public String getNewickTreeByProduct(List<String> collect) {
        return null;
    }

    @Override
    public Iterable<ComponentPackage> getComponentPackages() {
        return null;
    }


    public List<VariationPoint> getReleaseVariationPoint(String featureName) {

        return variationPointDao.getReleaseVariationPoints(featureName);
    }
    @Override
    public CoreAsset getVariationPointBody(Integer variationPointId) {

        return variationPointDao.getVPContentAndAsset(variationPointId);
    }

}
