package com.onekin.tagcloud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Developer;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.ProductRelease;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.repository.CoreAssetRepository;
import com.onekin.tagcloud.repository.DeveloperRepository;
import com.onekin.tagcloud.repository.ProductReleaseRepository;

@Service
public class SoftwareProductLineServiceImpl implements SoftwareProductLineService {
	
	
	@Autowired
	private VariationPointDAO variationPointDAO;
	
	
	@Autowired
	private FeatureDAO featureDAO;
	
	
	@Autowired
	private CoreAssetRepository coreAssetRepo;
	
	
	@Autowired
	private ProductReleaseRepository productReleaseRepo;
	
	@Autowired
	private DeveloperRepository developerRepo;
	
	



	@Override
	public List<Feature> getFeatures() {
		return featureDAO.getFeatures();
	}



	@Override
	public List<VariationPoint> getFeatureVariationPoints(String featureName) {
		
		return variationPointDAO.getFeatureVariationPoints(featureName);
	}



	@Override
	public CoreAsset getCoreAssetContent(Integer coreAssetId) {
		return coreAssetRepo.getCoreAssetByIdcoreasset(coreAssetId);
	}



	@Override
	public Iterable<ProductRelease> getProductRealeses() {
		return productReleaseRepo.findAll();
	}



	@Override
	public Iterable<Developer> getDevelopers() {
		
		return developerRepo.findAll();
	}



	@Override
	public List<Feature> getFeaturesFiltered(Filter filter) {
		
		return featureDAO.getFeaturesFiltered(filter);
	}



	@Override
	public List<VariationPoint> getVariationPointsFiltered(Filter filter) {
	
		return variationPointDAO.getVariationPointsFiltered(filter);
	}



	@Override
	public Developer getFilterDeveloper(Iterable<Developer> developers, int developerId) {
		
		
		Optional<Developer> optionalDeveloper = StreamSupport.stream(developers.spliterator(),false).filter(x -> x.getIdDeveloper()==developerId).findFirst();
		if(optionalDeveloper.isPresent()) {
			return optionalDeveloper.get();
		}else {
			return new Developer(0,"All"); 
		}
	}



	@Override
	public ProductRelease getFilterProduct(Iterable<ProductRelease> productReleases, int productId) {
		Optional<ProductRelease> optionalProduct = StreamSupport.stream(productReleases.spliterator(),false).filter(x -> x.getIdProductRelease()==productId).findFirst();
		if(optionalProduct.isPresent()) {
			return optionalProduct.get();
		}else {
			return new ProductRelease(0,"All");
		}
	}

	


}
