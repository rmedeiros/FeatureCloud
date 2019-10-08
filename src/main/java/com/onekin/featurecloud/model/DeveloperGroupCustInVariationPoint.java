package com.onekin.featurecloud.model;

public class DeveloperGroupCustInVariationPoint {

    private DeveloperGroup devGroup;
    private int modifiedLines;
    private int idVariationPoint;

    public DeveloperGroupCustInVariationPoint() {
        super();
    }

    public DeveloperGroupCustInVariationPoint(DeveloperGroup devGroup, int modifiedLines, int idVariationPoint) {
        super();
        this.devGroup = devGroup;
        this.modifiedLines = modifiedLines;
        this.idVariationPoint = idVariationPoint;
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

    public int getIdVariationPoint() {
        return idVariationPoint;
    }

    public void setIdVariationPoint(int idVariationPoint) {
        this.idVariationPoint = idVariationPoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((devGroup == null) ? 0 : devGroup.hashCode());
        result = prime * result + idVariationPoint;
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
        DeveloperGroupCustInVariationPoint other = (DeveloperGroupCustInVariationPoint) obj;
        if (devGroup == null) {
            if (other.devGroup != null)
                return false;
        } else if (!devGroup.equals(other.devGroup))
            return false;
        if (idVariationPoint != other.idVariationPoint)
            return false;
        if (modifiedLines != other.modifiedLines)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DeveloperGroupCustInFeature [devGroup=");
        builder.append(devGroup);
        builder.append(", modifiedLines=");
        builder.append(modifiedLines);
        builder.append(", idVariationPoint=");
        builder.append(idVariationPoint);
        builder.append("]");
        return builder.toString();
    }

}
