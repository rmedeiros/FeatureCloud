package com.onekin.featurecloud.service;

import com.onekin.featurecloud.dao.FeatureDAO;
import com.onekin.featurecloud.dao.FeatureSiblingDAO;
import com.onekin.featurecloud.dao.VariationPointDAO;
import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.repository.ComponentPackageRepository;
import com.onekin.featurecloud.repository.CoreAssetRepository;
import com.onekin.featurecloud.repository.ProductRepository;
import com.onekin.featurecloud.utils.NewickUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseDeltaServiceImpl implements ReleaseDeltaService {


    @Autowired
    private VariationPointDAO variationPointDAO;

    @Autowired
    private FeatureDAO featureDAO;


    @Autowired
    private FeatureSiblingDAO featureSiblingDAO;

    @Autowired
    private CoreAssetRepository coreAssetRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ComponentPackageRepository componentPackageRepo;

    @Override
    public List<Feature> getFeatures() {
        return featureDAO.getFeatures();
    }


    @Override
    public CoreAsset getCoreAssetContent(Integer coreAssetId) {
        return coreAssetRepo.getCoreAssetByIdcoreasset(coreAssetId);
    }


    @Override
    public List<Feature> getFeaturesFiltered(Filter filter) {

        return featureDAO.getFeaturesFiltered(filter);
    }


    @Override
    public String getNewickTree(List<String> featureIdList) {
        String tanglingFeatureList = "";
        for (String featureId : featureIdList) {
            tanglingFeatureList += " aaaa " + featureId + ' ' + featureId;
        }
        tanglingFeatureList += featureDAO.getDeltaTangling(featureIdList);

        return NewickUtils.getNewickFormatString(tanglingFeatureList);
    }

    @Override
    public String getNewickTreeByProduct(List<String> featureIdList) {
        String tanglingFeatureList = "";
        for (String featureId : featureIdList) {
            tanglingFeatureList += " aaaa " + featureId + ' ' + featureId;
        }
        tanglingFeatureList += featureDAO.getDeltaTanglingByProduct(featureIdList);

        return NewickUtils.getNewickFormatString(tanglingFeatureList);
    }

    @Override
    public List<FeatureSibling> getModifiedFeaturesiblings(String idFeature) {
        return featureSiblingDAO.getModifiedFeaturesiblings(idFeature);
    }


    @Override
    public List<CustomDiff> getDiffValues(Integer variationPointId) {
        return variationPointDAO.getDiffValues(variationPointId);
    }


    @Override
    public Iterable<Product> getProductIds() {
        return productRepo.findAll();
    }


    @Override
    public List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId) {
        return featureDAO.getFeaturesByProduct(productId, packageId);
    }


    @Override
    public Iterable<ComponentPackage> getComponentPackages() {
        return componentPackageRepo.findAll();
    }


}
