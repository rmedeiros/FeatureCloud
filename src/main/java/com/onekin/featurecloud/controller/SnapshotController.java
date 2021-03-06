package com.onekin.featurecloud.controller;

import com.onekin.featurecloud.exceptions.CoreAssetNotFoundException;
import com.onekin.featurecloud.model.*;
import com.onekin.featurecloud.service.SnapshotService;
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
@RequestMapping(value = "/release")
public class SnapshotController {

    @Autowired
    private SnapshotService snapshotService;

    @GetMapping("/")
    public String getVariationPoints(Model model) {
        return "index";
    }

    @GetMapping("/features/")
    public String getFeatureTagCloud(Model model,
                                     @RequestParam(required = false, name = "product", defaultValue = "0") String productId) {
        List<Feature> features = snapshotService.getFeatures();
        List<Integer> modifiedLinesList = new ArrayList<>();
        List<Integer> scatteringLevel = new ArrayList<>();
        for (Feature feature : features) {
            modifiedLinesList.add(feature.getLinesAdded());
            scatteringLevel.add(feature.getFeatureScattering());
        }

        SnapshotMetada snapMetadata= snapshotService.getMetadataBox();

        Iterable<Product> products = snapshotService.getProductIds();
        Iterable<ComponentPackage> componentPackages = snapshotService.getComponentPackages();

        model.addAttribute("products", products);
        model.addAttribute("componentPackages", componentPackages);

        int maxModifiedLines = Collections.max(modifiedLinesList);
        String newickString = snapshotService
                .getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
        model.addAttribute("metaData",snapMetadata);
        model.addAttribute("newickString", newickString);
        model.addAttribute("features", features);
        model.addAttribute("maxModifiedLines", maxModifiedLines);
        model.addAttribute("maxScattering", Collections.max(scatteringLevel));
        model.addAttribute("minScattering", Collections.min(scatteringLevel));

        return "release_features";
    }

    @GetMapping("/features/{featureName}/")
    public String getFeatureFeatureSiblings(@PathVariable(value = "featureName") String featureName, Model model) {

        List<FeatureSibling> featureSiblings = snapshotService.getFeatureFeatureSiblings(featureName);
        int totalLines = featureSiblings.stream().map(FeatureSibling::getModifiedLines)
                .collect(Collectors.summingInt(i -> i));
        model.addAttribute("featureSiblings", featureSiblings);
        model.addAttribute("totalLines", totalLines);
        model.addAttribute("currentFeature", featureName);
        return "release_feature_siblings";
    }


    @GetMapping("/features/{featureName}/asset/{featureSiblingId}/")
    public String getVariationPointsByFeatureSibling(@PathVariable(value = "featureSiblingId") Integer featureSiblingId,
                                                     @PathVariable(value = "featureName") String featureName, Model model) {
        List<VariationPoint> variationPoints = snapshotService.getFeatureSiblingVariationPointsBody(featureSiblingId);
        model.addAttribute("variationPoints", variationPoints);
        model.addAttribute("currentFeature", featureName);
        return "release_vp_code";

    }

    @ResponseBody
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, path = "/features/filtered")
    public FeaturesResponse getFeaturesFiltered(@RequestParam(value = "product", defaultValue = "All") String productId,
                                                @RequestParam(value = "packageId", defaultValue = "0", required = false) Integer packageId) {
        List<Feature> features;
        String newickString;
        if (("All".equalsIgnoreCase(productId) || "".equalsIgnoreCase(productId)) && packageId == 0) {
            features = snapshotService.getFeatures();
        } else {
            features = snapshotService.getFeaturesFilteredByProductAndPackage(productId, packageId);
        }
        if (packageId == 0) {
            newickString = snapshotService
                    .getNewickTree(features.stream().map(Feature::getId).collect(Collectors.toList()));
        } else {
            newickString = snapshotService
                    .getNewickTreeFiltered(features.stream().map(Feature::getId).collect(Collectors.toList()), packageId);
        }
        List<Integer> modifiedLinesList = new ArrayList<>();
        modifiedLinesList.add(0);
        for (Feature feature : features) {
            modifiedLinesList.add(feature.getLinesAdded());
        }
        int maxModifiedLines = Collections.max(modifiedLinesList);

        return new FeaturesResponse(features, maxModifiedLines, newickString);

    }

    @ResponseBody
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
            MediaType.TEXT_PLAIN_VALUE}, path = "/newick/filtered")
    public String getNewickStringFiltered(@RequestBody List<String> features) {
        return snapshotService.getNewickTree(features);

    }

    @ResponseBody
    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE}, path = "/coreasset/{coreaAssetId}")
    public CoreAsset getCoreAsset(@PathVariable(value = "coreaAssetId") Integer coreaAssetId) throws CoreAssetNotFoundException {
        return snapshotService.getCoreAsset(coreaAssetId);
    }


}
