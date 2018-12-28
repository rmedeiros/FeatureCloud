package com.onekin.tagcloud.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.service.SoftwareProductLineService;

@Controller
public class MainController {

	@Autowired
	private SoftwareProductLineService softwareProductLineService;

	@GetMapping("/all")
	public String getAllVariationPoints(Model model) {
		List<VariationPoint> variationPoints = softwareProductLineService.getAllVariationPoints();
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded)
				.collect(Collectors.summingInt(i -> i));
		totalLines += variationPoints.stream().map(VariationPoint::getLinesDeleted)
				.collect(Collectors.summingInt(i -> i));
		model.addAttribute("variationPoints", variationPoints);
		model.addAttribute("totalLines", totalLines);
		return "index";
	} 

	@GetMapping("/")
	public String getVariationPoints(Model model) {
		List<VariationPoint> variationPoints = softwareProductLineService.getVariationPointsWithFeature();
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded)
				.collect(Collectors.summingInt(i -> i));
		totalLines += variationPoints.stream().map(VariationPoint::getLinesDeleted)
				.collect(Collectors.summingInt(i -> i));
		model.addAttribute("variationPoints", variationPoints);
		model.addAttribute("totalLines", totalLines);
		return "index";
	}

	@GetMapping("/features")
	public String getFeatureTagCloud(Model model) {
		List<Feature> features = softwareProductLineService.getFeatures();
		int totalLines = features.stream().map(Feature::getLinesAdded).collect(Collectors.summingInt(i -> i));
		totalLines += features.stream().map(Feature::getLinesDeleted).collect(Collectors.summingInt(i -> i));
		model.addAttribute("features", features);
		model.addAttribute("totalLines", totalLines);
		return "features";
	}

	@GetMapping("/features/{featureName}")
	public String getFeatureVariationPoints(@PathVariable( value = "featureName") String featureName, Model model) {
		List<VariationPoint> variationPoints = softwareProductLineService.getFeatureVariationPoints(featureName);
		int totalLines = variationPoints.stream().map(VariationPoint::getLinesAdded).collect(Collectors.summingInt(i -> i));
		totalLines += variationPoints.stream().map(VariationPoint::getLinesDeleted).collect(Collectors.summingInt(i -> i));
		model.addAttribute("variationPoints", variationPoints);
		model.addAttribute("totalLines", totalLines);
		return "index";
	}
	
	
	
	@GetMapping("/asset/{coreAssetId}")
	public String getCoreAssetContent(@PathVariable( value = "coreAssetId") Integer coreAssetId, Model model) {
		CoreAsset coreAsset = softwareProductLineService.getCoreAssetContent(coreAssetId);
		model.addAttribute("coreAssetContent",coreAsset.getContent());
		return "core_asset";
		
	}

}
