package com.onekin.tagcloud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.FeatureSiblingDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Developer;
import com.onekin.tagcloud.model.DeveloperGroup;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.FeatureSibling;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.ProductRelease;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.repository.CoreAssetRepository;
import com.onekin.tagcloud.repository.DeveloperGroupRepository;
import com.onekin.tagcloud.repository.DeveloperRepository;
import com.onekin.tagcloud.repository.ProductReleaseRepository;
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
	public List<FeatureSibling> getModifiedFeaturesiblings(String idFeature) {
		return featureSiblingDAO.getModifiedFeaturesiblings(idFeature);
	}




	@Override
	public List<CustomDiff> getDiffValues(Integer variationPointId) {
		return variationPointDAO.getDiffValues(variationPointId);
	}
}
