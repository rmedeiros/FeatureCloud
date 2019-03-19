package com.onekin.tagcloud.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureDAO;
import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Developer;
import com.onekin.tagcloud.model.DeveloperGroup;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.ProductRelease;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.repository.CoreAssetRepository;
import com.onekin.tagcloud.repository.DeveloperGroupRepository;
import com.onekin.tagcloud.repository.DeveloperRepository;
import com.onekin.tagcloud.repository.ProductReleaseRepository;
import com.onekin.tagcloud.utils.NewickTreeFormat;

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

	@Autowired
	private DeveloperGroupRepository developerGroupRepo;

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
	public DeveloperGroup getFilterDeveloper(Iterable<DeveloperGroup> developers, int developerId) {

		Optional<DeveloperGroup> optionalDeveloper = StreamSupport.stream(developers.spliterator(), false)
				.filter(x -> x.getIdDeveloperGroup()== developerId).findFirst();
		if (optionalDeveloper.isPresent()) {
			return optionalDeveloper.get();
		} else {
			return new DeveloperGroup(0, "All");
		}
	}

	@Override
	public ProductRelease getFilterProduct(Iterable<ProductRelease> productReleases, String productId) {
		Optional<ProductRelease> optionalProduct = StreamSupport.stream(productReleases.spliterator(), false)
				.filter(x -> x.getIdProductRelease().equals(productId)).findFirst();
		if (optionalProduct.isPresent()) {
			return optionalProduct.get();
		} else {
			return new ProductRelease("0", "All");
		}
	}

	@Override
	public Iterable<DeveloperGroup> getDeveloperGroups() {
		return developerGroupRepo.findAll();
	}

	@Override
	public List<Pair<String,String>> getDiffValues(Integer variationPointId) {
		return variationPointDAO.getDiffValues(variationPointId);
	}

	@Override
	public String getNewickTree(List<String> featureIdList) {
		String tanglingFeatureList = featureDAO.getTanglingFeatureList(featureIdList);
		return NewickTreeFormat.getNewickFormatString(tanglingFeatureList);
	}

}
