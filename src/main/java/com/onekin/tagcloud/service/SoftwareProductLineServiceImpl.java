package com.onekin.tagcloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.FeatureSiblingDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.ComponentPackage;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.FeatureSibling;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.Product;
import com.onekin.tagcloud.repository.ComponentPackageRepository;
import com.onekin.tagcloud.repository.CoreAssetRepository;
import com.onekin.tagcloud.repository.ProductRepository;
import com.onekin.tagcloud.utils.NewickUtils;

@Service
public class SoftwareProductLineServiceImpl implements SoftwareProductLineService {


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
		String tanglingFeatureList = featureDAO.getDeltaTangling(featureIdList);
		for(String featureId : featureIdList) {
			tanglingFeatureList+=" aaaa "+featureId+' '+featureId;
		}
		return NewickUtils.getNewickFormatString(tanglingFeatureList);
	}
	
	@Override
	public String getNewickTreeByProduct(List<String> featureIdList) {
		String tanglingFeatureList = featureDAO.getDeltaTanglingByProduct(featureIdList);
		for(String featureId : featureIdList) {
			tanglingFeatureList+=" aaaa "+featureId+' '+featureId;
		}
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
