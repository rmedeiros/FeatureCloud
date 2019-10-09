package com.onekin.featurecloud.model;

public class SnapshotMetada {
    private int variationPoints;
    private int features;
    private long variableCode;

    public SnapshotMetada(int variationPoints, int features,  long variableCode) {
        this.variationPoints = variationPoints;
        this.features = features;
        this.variableCode = variableCode;
    }

    public SnapshotMetada() {
        super();
    }

    public int getVariationPoints() {
        return variationPoints;
    }

    public void setVariationPoints(int variationPoints) {
        this.variationPoints = variationPoints;
    }

    public int getFeatures() {
        return features;
    }

    public void setFeatures(int features) {
        this.features = features;
    }

    public long getVariableCode() {
        return variableCode;
    }

    public void setVariableCode(long variableCode) {
        this.variableCode = variableCode;
    }
}
