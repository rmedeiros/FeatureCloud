package com.onekin.featurecloud.model;

import java.util.HashSet;
import java.util.Set;

public class FeatureSibling {

    private int id;
    private Set<String> features = new HashSet<>();
    private String featureExpression;
    private int modifiedLines;

    public FeatureSibling(int id, Set<String> features, int modifiedLines) {
        super();
        this.id = id;
        this.features = features;
        this.featureExpression = String.join(" - ", features);
        this.modifiedLines = modifiedLines;
    }

    public FeatureSibling() {
        super();
    }

    public FeatureSibling(Set<String> listFeatures) {
        this.features.addAll(listFeatures);
    }

    public FeatureSibling(int id, String noFeature) {
        this.id = id;
        features.add(noFeature);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getFeatures() {
        return features;
    }

    public void setFeatures(Set<String> features) {
        this.features = features;
    }


    public String getFeatureExpression() {
        return featureExpression;
    }

    public void setFeatureExpression(String featureExpression) {
        this.featureExpression = featureExpression;
    }


    public int getModifiedLines() {
        return modifiedLines;
    }

    public void setModifiedLines(int modifiedLines) {
        this.modifiedLines = modifiedLines;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeatureSibling other = (FeatureSibling) obj;
        return (id == other.id);
    }

    @Override
    public String toString() {
        return "FeatureSibling [id=" + id + ", features=" + features + ", featureExpression=" + featureExpression
                + ", modifiedLines=" + modifiedLines + "]";
    }


}
