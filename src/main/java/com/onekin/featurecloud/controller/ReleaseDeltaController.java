package com.onekin.featurecloud.controller;

import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.service.ReleaseDeltaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReleaseDeltaController {

    @Autowired
    private ReleaseDeltaService releaseDeltaService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/features/")
    public String getFeatureTagCloud(Model model,
                                     @RequestParam(required = false, name = "product", defaultValue = "0") String productId) {
        List<Feature> features = releaseDeltaService.getFeatures();
        List<Integer> modifiedLinesList = new ArrayList<>();
        for (Feature feature : features) {
            modifiedLinesList.add(feature.getLinesAdded() + feature.getLinesDeleted());
        }
        int maxModifiedLines = Collections.max(modifiedLinesList);
        String newickString = releaseDeltaService
                .getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
        Iterable<Product> products = releaseDeltaService.getProductIds();
        Iterable<ComponentPackage> componentPackages = releaseDeltaService.getComponentPackages();

        model.addAttribute("products", products);
        model.addAttribute("componentPackages", componentPackages);
        model.addAttribute("newickString", newickString);
        model.addAttribute("features", features);
        model.addAttribute("maxModifiedLines", maxModifiedLines);

        return "features";
    }

    @GetMapping("/features/{featureName}/")
    public String getFeatureFeatureSiblings(@PathVariable(value = "featureName") String featureName, Model model) {

        List<FeatureSibling> featureSiblings = releaseDeltaService.getModifiedFeaturesiblings(featureName);
        int totalLines = featureSiblings.stream().map(FeatureSibling::getModifiedLines)
                .collect(Collectors.summingInt(i -> i));

        model.addAttribute("featureSiblings", featureSiblings);
        model.addAttribute("totalLines", totalLines);
        model.addAttribute("currentFeature", featureName);
        return "feature_siblings";
    }

    @GetMapping("/features/{featureName}/asset/{variationPointId}/")
    public String getCoreAssetContent(@PathVariable(value = "variationPointId") Integer variationPointId,
                                      @PathVariable(value = "featureName") String featureName,
                                      @RequestParam(required = false, name = "product", defaultValue = "0") Integer productId, Model model) {
        List<CustomDiff> diffValues = releaseDeltaService.getDiffValues(variationPointId);
        model.addAttribute("diffValues", diffValues);
        model.addAttribute("currentFeature", featureName);
        return "core_asset";

    }

    @ResponseBody
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/features/filtered")
    public FeaturesResponse getFeaturesFiltered(@RequestParam(value = "product", defaultValue = "All") String productId,
                                                @RequestParam(value = "packageId", required = false) Integer packageId) {
        List<Feature> features;
        String newickString;
        if (("All".equalsIgnoreCase(productId) || "".equalsIgnoreCase(productId)) &&   0 == packageId) {
            features = releaseDeltaService.getFeatures();

            newickString = releaseDeltaService
                    .getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
        } else {
            features = releaseDeltaService.getFeaturesFilteredByProductAndPackage(productId, packageId);
            newickString = releaseDeltaService
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

    @ResponseBody
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.TEXT_PLAIN_VALUE}, path = "/newick/filtered")
    public String getNewickStringFiltered(@RequestBody List<String> features) {
        String newick = releaseDeltaService.getNewickTree(features);
        return newick;

    }

}
