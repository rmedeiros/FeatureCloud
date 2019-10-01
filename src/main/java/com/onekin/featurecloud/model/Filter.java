package com.onekin.featurecloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filter {

    @JsonProperty("developerId")
    private int developerId;
    @JsonProperty("productReleaseId")
    private String productReleaseId;
    private String featureName;

    public Filter(int developerId, String productReleaseId, String featureName) {
        super();
        this.developerId = developerId;
        this.productReleaseId = productReleaseId;
        this.featureName = featureName;
    }

    public Filter() {
        super();
    }

    public Filter(String productId, Integer developerId) {
        this.productReleaseId = productId;
        this.developerId = developerId;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public String getProductReleaseId() {
        return productReleaseId;
    }

    public void setProductReleaseId(String productReleaseId) {
        this.productReleaseId = productReleaseId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Filter [developerId=");
        builder.append(developerId);
        builder.append(", productReleaseId=");
        builder.append(productReleaseId);
        builder.append(", featureName=");
        builder.append(featureName);
        builder.append("]");
        return builder.toString();
    }

}
