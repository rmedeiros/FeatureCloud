package com.onekin.featurecloud.service;

import com.onekin.featurecloud.dao.FeatureDAO;
import com.onekin.featurecloud.dao.FeatureSiblingDAO;
import com.onekin.featurecloud.dao.VariationPointDAO;
import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.repository.ComponentPackageRepository;
import com.onekin.featurecloud.repository.ProductRepository;
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

    @Autowired
    private ComponentPackageRepository componentPackageRepo;

    @Autowired
    private ProductRepository productsRepo;

    @Autowired
    private FeatureSiblingDAO featureSiblingDao;

    @Override
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
        String tanglingFeatureList="";
        for (String featureId : featureIdList) {
            tanglingFeatureList += " aaaa " + featureId + ' ' + featureId;
        }
        tanglingFeatureList += featuresDao.getTanglingFeatureList(featureIdList);
        return NewickUtils.getNewickFormatString(tanglingFeatureList);
    }

    @Override
    public Iterable<Product> getProductIds() {
        return productsRepo.findAll();
    }

    @Override
    public List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId) {
        return featuresDao.getSnapshotFeaturesByProduct(productId,packageId);
    }

    @Override
    public Iterable<ComponentPackage> getComponentPackages() {
        return componentPackageRepo.findAll();
    }

    @Override
    public List<FeatureSibling> getFeatureFeatureSiblings(String featureName) {

        return featureSiblingDao.getAllFeaturesiblingsByFeature(featureName);
    }

    @Override
    public String getNewickTreeFiltered(List<String> featureIdList, int packageId) {
        String tanglingFeatureList="";
        for (String featureId : featureIdList) {
            tanglingFeatureList += " aaaa " + featureId + ' ' + featureId;
        }
        tanglingFeatureList += featuresDao.getTanglingFeatureListByPackage(featureIdList,packageId);
        return NewickUtils.getNewickFormatString(tanglingFeatureList);
    }

    @Override
    public List<VariationPoint> getFeatureSiblingVariationPointsBody(Integer featureSiblingId) {
        return variationPointDao.getVariationPointsByFeatureSibling(featureSiblingId);
    }

}
