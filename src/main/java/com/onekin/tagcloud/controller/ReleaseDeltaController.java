package com.onekin.tagcloud.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.FeatureSibling;
import com.onekin.tagcloud.model.FeaturesResponse;
import com.onekin.tagcloud.model.Product;
import com.onekin.tagcloud.service.SoftwareProductLineService;

@Controller
public class ReleaseDeltaController {

	@Autowired
	private SoftwareProductLineService softwareProductLineService;

	@GetMapping("/")
	public String getVariationPoints(Model model) {
		return "index";
	}

	@GetMapping("/features/")
	public String getFeatureTagCloud(Model model,
			@RequestParam(required = false, name = "product", defaultValue = "0") String productId) {
		// TODO delete developer filter
		List<Feature> features = softwareProductLineService.getFeatures();
		List<Integer> modifiedLinesList = new ArrayList<>();
		for (Feature feature : features) {
			modifiedLinesList.add(feature.getLinesAdded() + feature.getLinesDeleted());
		}
		int maxModifiedLines = Collections.max(modifiedLinesList);
		String newickString = softwareProductLineService
				.getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
		Iterable<Product> products = softwareProductLineService.getProductIds();
		model.addAttribute("products", products);
		model.addAttribute("newickString", newickString);
		model.addAttribute("features", features);
		model.addAttribute("maxModifiedLines", maxModifiedLines);

		return "features";
	}

	@GetMapping("/features/{featureName}/")
	public String getFeatureVariationPoints(@PathVariable(value = "featureName") String featureName, Model model) {

		List<FeatureSibling> featureSiblings = softwareProductLineService.getModifiedFeaturesiblings(featureName);
		int totalLines = featureSiblings.stream().map(FeatureSibling::getModifiedLines)
				.collect(Collectors.summingInt(i -> i));

		model.addAttribute("featureSiblings", featureSiblings);
		model.addAttribute("totalLines", totalLines);
		model.addAttribute("currentFeature", featureName);
		return "feature_siblings";
	}

	/*
	 * @GetMapping("/features/{featureName}/asset/{coreAssetId}") public String
	 * getCoreAssetContent(@PathVariable(value = "coreAssetId") Integer coreAssetId,
	 * 
	 * @PathVariable(value = "featureName") String featureName,
	 * 
	 * @RequestParam(required = false, name = "product", defaultValue = "0") Integer
	 * productId,
	 * 
	 * @RequestParam(required = false, name = "developer", defaultValue = "0")
	 * Integer developerId, Model model) { CoreAsset coreAsset =
	 * softwareProductLineService.getCoreAssetContent(coreAssetId);
	 * model.addAttribute("coreAsset", coreAsset);
	 * model.addAttribute("product",productId);
	 * model.addAttribute("developer",developerId);
	 * model.addAttribute("currentFeature", featureName); return "core_asset";
	 * 
	 * }
	 */

	@GetMapping("/features/{featureName}/asset/{variationPointId}/")
	public String getCoreAssetContent(@PathVariable(value = "variationPointId") Integer variationPointId,
			@PathVariable(value = "featureName") String featureName,
			@RequestParam(required = false, name = "product", defaultValue = "0") Integer productId, Model model) {
		List<CustomDiff> diffValues = softwareProductLineService.getDiffValues(variationPointId);
		model.addAttribute("diffValues", diffValues);
		model.addAttribute("currentFeature", featureName);
		return "core_asset";

	}

	@ResponseBody
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, path = "/features/filtered")
	public FeaturesResponse getFeaturesFiltered(@RequestParam("product") String productId) {
		List<Feature> features;
		String newickString;
		if ("All".equalsIgnoreCase(productId)) {
			features = softwareProductLineService.getFeatures();
			
			newickString = softwareProductLineService
					.getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
		} else {
			features = softwareProductLineService.getFeaturesFilteredByProduct(productId);
			newickString = softwareProductLineService
					.getNewickTreeByProduct(features.stream().map(Feature::getId).collect(Collectors.toList()));
		}
		List<Integer> modifiedLinesList = new ArrayList<>();
		modifiedLinesList.add(0);
		for (Feature feature : features) {
			modifiedLinesList.add(feature.getLinesAdded() + feature.getLinesDeleted());
		}
		int maxModifiedLines = Collections.max(modifiedLinesList);

		return new FeaturesResponse(features, maxModifiedLines, newickString);

	}

}
