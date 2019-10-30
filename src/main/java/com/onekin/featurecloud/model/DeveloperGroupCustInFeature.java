package com.onekin.featurecloud.model;

public class DeveloperGroupCustInFeature {

    private DeveloperGroup devGroup;
    private int modifiedLines;
    private String featureId;

    public DeveloperGroupCustInFeature() {
        super();
    }

    public DeveloperGroupCustInFeature(DeveloperGroup devGroup, int modifiedLines, String featureId) {
        super();
        this.devGroup = devGroup;
        this.modifiedLines = modifiedLines;
        this.featureId = featureId;
    }

    public DeveloperGroup getDevGroup() {
        return devGroup;
    }

    public void setDevGroup(DeveloperGroup devGroup) {
        this.devGroup = devGroup;
    }

    public int getModifiedLines() {
        return modifiedLines;
    }

    public void setModifiedLines(int modifiedLines) {
        this.modifiedLines = modifiedLines;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((devGroup == null) ? 0 : devGroup.hashCode());
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        result = prime * result + modifiedLines;
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
        DeveloperGroupCustInFeature other = (DeveloperGroupCustInFeature) obj;
        if (devGroup == null) {
            if (other.devGroup != null)
                return false;
        } else if (!devGroup.equals(other.devGroup))
            return false;
        if (featureId == null) {
            if (other.featureId != null)
                return false;
        } else if (!featureId.equals(other.featureId))
            return false;
        return (modifiedLines == other.modifiedLines);

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DeveloperGroupCustInFeature [devGroup=");
        builder.append(devGroup);
        builder.append(", modifiedLines=");
        builder.append(modifiedLines);
        builder.append(", featureId=");
        builder.append(featureId);
        builder.append("]");
        return builder.toString();
    }

}
