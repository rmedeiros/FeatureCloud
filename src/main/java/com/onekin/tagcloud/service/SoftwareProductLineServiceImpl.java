package com.onekin.tagcloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.repository.CoreAssetRepository;

@Service
public class SoftwareProductLineServiceImpl implements SoftwareProductLineService {
	
	
	@Autowired
	private VariationPointDAO variationPointDAO;
	
	
	@Autowired
	private FeatureDAO featureDAO;
	
	
	@Autowired
	private CoreAssetRepository coreAssetRepo;
	
	
	@Override
	public List<VariationPoint> getAllVariationPoints() {
		
		return variationPointDAO.getAllVariationPoints();
	}



	@Override
	public List<VariationPoint> getVariationPointsWithFeature() {
		return variationPointDAO.getVariationPointsWithFeature();
	}



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

	


}
