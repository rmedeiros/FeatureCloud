package com.onekin.featurecloud.service;

import com.onekin.featurecloud.dao.FeatureDAO;
import com.onekin.featurecloud.dao.FeatureSiblingDAO;
import com.onekin.featurecloud.dao.MetadaBoxDAO;
import com.onekin.featurecloud.dao.VariationPointDAO;
import com.onekin.featurecloud.exceptions.CoreAssetNotFoundException;
import com.onekin.featurecloud.github.FeatureIssueFinder;
import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.repository.ComponentPackageRepository;
import com.onekin.featurecloud.repository.CoreAssetRepository;
import com.onekin.featurecloud.repository.ProductRepository;
import com.onekin.featurecloud.utils.NewickUtils;
import org.kohsuke.github.GHIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private CoreAssetRepository coreAssetRepository;

    @Autowired
    private MetadaBoxDAO metadataBoxDao;

    @Autowired
    private FeatureIssueFinder featureIssueFinder;

    @Override
    public List<Feature> getFeatures() {
        List<Feature> features = featuresDao.getAllFeatures();
        featureIssueFinder.setHasFeatureIssues(features);
        return features;
    }

    @Override
    public CoreAsset getCoreAssetContent(Integer coreAssetId) {
        return null;
    }

    @Override
    public List<Feature> getFeaturesFiltered(Filter filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNewickTree(List<String> featureIdList) {
        StringBuilder bld = new StringBuilder();
        for (String featureId : featureIdList) {
            bld.append(" aaaa " + featureId + ' ' + featureId);
        }
        bld.append(featuresDao.getTanglingFeatureList(featureIdList));
        return NewickUtils.getNewickFormatString(bld.toString());
    }

    @Override
    public Iterable<Product> getProductIds() {
        return productsRepo.findAll();
    }

    @Override
    public List<Feature> getFeaturesFilteredByProductAndPackage(String productId, int packageId) {
        return featuresDao.getSnapshotFeaturesByProduct(productId, packageId);
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
        StringBuilder bld = new StringBuilder();
        for (String featureId : featureIdList) {
            bld.append(" aaaa " + featureId + ' ' + featureId);
        }
        bld.append(featuresDao.getTanglingFeatureListByPackage(featureIdList, packageId));
        return NewickUtils.getNewickFormatString(bld.toString());
    }

    @Override
    public List<VariationPoint> getFeatureSiblingVariationPointsBody(Integer featureSiblingId) {
        return variationPointDao.getVariationPointsByFeatureSibling(featureSiblingId);
    }

    @Override
    public CoreAsset getCoreAsset(Integer coreaAssetId) throws CoreAssetNotFoundException {

        Optional<CoreAsset> coreAssetOptional = coreAssetRepository.findById(coreaAssetId);
        if (coreAssetOptional.isPresent()) {
            return coreAssetOptional.get();
        } else {
            throw new CoreAssetNotFoundException("Core asset with id " + coreaAssetId + "not found");
        }
    }

    @Override
    public SnapshotMetada getMetadataBox() {
        return metadataBoxDao.getSnapshotMetadataBox();
    }

    @Override
    @ModelAttribute("featureIssues")
    public List<GHIssue> getFeatureIssues(String featureName) {
        return featureIssueFinder.getFeatureIssues(featureName);
    }

}
