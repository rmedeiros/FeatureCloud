package com.onekin.tagcloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.utils.NewickUtils;

@Service
public class ReleaseService {

	@Autowired
	private FeatureDAO featuresDao;

	@Autowired
	private VariationPointDAO vpDao;

	public List<Feature> getFeatures() {
		return featuresDao.getAllFeatures();
	}

	public String getReleaseNewickTree(List<String> featureIdList) {
		String featureStringList = featuresDao.getTanglingFeatureList(featureIdList);
		return NewickUtils.getNewickFormatString(featureStringList);

	}

	public List<VariationPoint> getReleaseVariationPoint(String featureName) {

		return vpDao.getReleaseVariationPoints(featureName);
	}

	public CoreAsset getVPBody(Integer variationPointId) {

		return vpDao.getVPContentAndAsset(variationPointId);
	}

}
