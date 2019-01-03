package com.onekin.tagcloud.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Developer;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.FeaturesResponse;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.ProductRelease;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.model.VariationPointsResponse;
import com.onekin.tagcloud.service.SoftwareProductLineService;

@Controller
public class MainController {

	@Autowired
	private SoftwareProductLineService softwareProductLineService;

	@GetMapping("/")
	public String getVariationPoints(Model model) {
		/*
		 * List<VariationPoint> variationPoints =
		 * softwareProductLineService.getVariationPointsWithFeature(); int totalLines =
		 * variationPoints.stream().map(VariationPoint::getLinesAdded)
		 * .collect(Collectors.summingInt(i -> i)); totalLines +=
		 * variationPoints.stream().map(VariationPoint::getLinesDeleted)
		 * .collect(Collectors.summingInt(i -> i));
		 * model.addAttribute("variationPoints", variationPoints);
		 * model.addAttribute("totalLines", totalLines);
		 */
		return "index";
	}

	@GetMapping("/features")
	public String getFeatureTagCloud(Model model) {
		List<Feature> features = softwareProductLineService.getFeatures();
		int totalLines = features.stream().map(Feature::getLinesDeleted).collect(Collectors.summingInt(i -> i))
				+ features.stream().map(Feature::getLinesAdded).collect(Collectors.summingInt(i -> i));
		model.addAttribute("features", features);
		model.addAttribute("totalLines", totalLines);
		model.addAttribute("products", softwareProductLineService.getProductRealeses());
		model.addAttribute("developers", softwareProductLineService.getDevelopers());
		return "features";
	}

	@GetMapping("/features/{featureName}")
	public String getFeatureVariationPoints(@PathVariable(value = "featureName") String featureName,
			@RequestParam(required = false, name = "product", defaultValue = "0") Integer productId,
			@RequestParam(required = false, name = "developer", defaultValue = "0") Integer developerId, Model model) {
		Filter filter = new Filter(developerId, productId, featureName);
		List<VariationPoint> variationPoints = softwareProductLineService.getVariationPointsFiltered(filter);
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded)
				.collect(Collectors.summingInt(i -> i));
		totalLines += variationPoints.stream().map(VariationPoint::getLinesDeleted)
				.collect(Collectors.summingInt(i -> i));
		model.addAttribute("variationPoints", variationPoints);
		model.addAttribute("totalLines", totalLines);
		Iterable<ProductRelease> productReleases = softwareProductLineService.getProductRealeses();
		model.addAttribute("products", productReleases);

		Iterable<Developer> developers = softwareProductLineService.getDevelopers();
		model.addAttribute("developers", developers);

		model.addAttribute("filterProduct", softwareProductLineService.getFilterProduct(productReleases, productId));
		model.addAttribute("filterDeveloper", softwareProductLineService.getFilterDeveloper(developers, developerId));

		return "variation_points";
	}

	@GetMapping("/asset/{coreAssetId}")
	public String getCoreAssetContent(@PathVariable(value = "coreAssetId") Integer coreAssetId, Model model) {
		CoreAsset coreAsset = softwareProductLineService.getCoreAssetContent(coreAssetId);
		model.addAttribute("coreAsset", coreAsset);
		return "core_asset";

	}

	@ResponseBody
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/features/filtered")
	public FeaturesResponse getFeaturesFiltered(@RequestBody Filter filter) {
		List<Feature> features = softwareProductLineService.getFeaturesFiltered(filter);
		int totalLines = features.stream().map(Feature::getLinesAdded).collect(Collectors.summingInt(i -> i))
				+ features.stream().map(Feature::getLinesDeleted).collect(Collectors.summingInt(i -> i));
		return new FeaturesResponse(features, totalLines);

	}

	@ResponseBody
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE }, path = "/variationpoints/filtered")
	public VariationPointsResponse getVariationPointsFiltered(@RequestBody Filter filter) {
		List<VariationPoint> variationPoints = softwareProductLineService.getVariationPointsFiltered(filter);
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded)
				.collect(Collectors.summingInt(i -> i))
				+ variationPoints.stream().map(VariationPoint::getLinesDeleted).collect(Collectors.summingInt(i -> i));
		return new VariationPointsResponse(variationPoints, totalLines);

	}

}
