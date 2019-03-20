package com.onekin.tagcloud.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.service.ReleaseService;

@Controller
@RequestMapping(value = "/release")
public class ReleaseContoller {

	@Autowired
	private ReleaseService releaseService;

	@GetMapping("/")
	public String getVariationPoints(Model model) {
		return "index";
	}

	@GetMapping("/features/")
	public String getFeatureTagCloud(Model model,
			@RequestParam(required = false, name = "product", defaultValue = "0") String productId) {
		// TODO delete developer filter
		List<Feature> features = releaseService.getFeatures();
		List<Integer> modifiedLinesList = new ArrayList<>();
		List<Integer> scatteringLevel = new ArrayList<>();
		for (Feature feature : features) {
			modifiedLinesList.add(feature.getLinesAdded());
			scatteringLevel.add(feature.getFeatureScattering());
		}
		int maxModifiedLines = Collections.max(modifiedLinesList);
		String newickString = releaseService
				.getReleaseNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
		model.addAttribute("newickString", newickString);
		model.addAttribute("features", features);
		model.addAttribute("maxModifiedLines", maxModifiedLines);
		model.addAttribute("maxScattering", Collections.max(scatteringLevel));
		model.addAttribute("minScattering", Collections.min(scatteringLevel));

		return "release_features";
	}

	@GetMapping("/features/{featureName}/")
	public String getFeatureVariationPoints(@PathVariable(value = "featureName") String featureName, Model model) {

		List<VariationPoint> variationPoints = releaseService.getReleaseVariationPoint(featureName);
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded)
				.collect(Collectors.summingInt(i -> i));
		model.addAttribute("variationPoints", variationPoints);
		model.addAttribute("totalLines", totalLines);
		model.addAttribute("currentFeature", featureName);
		return "release_variation_points";
	}



	@GetMapping("/features/{featureName}/asset/{variationPointId}/")
	public String getCoreAssetContent(@PathVariable(value = "variationPointId") Integer variationPointId,
			@PathVariable(value = "featureName") String featureName, Model model) {
		CoreAsset coreAsset = releaseService.getVPBody(variationPointId);
		model.addAttribute("coreAsset", coreAsset);
		model.addAttribute("currentFeature", featureName);
		return "release_vp_code";

	}
/*
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

	}*/

}
