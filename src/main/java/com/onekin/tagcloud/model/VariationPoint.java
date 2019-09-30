package com.onekin.tagcloud.model;

import com.onekin.tagcloud.utils.Formatting;

public class VariationPoint {

    private Integer id;
    private Integer linesAdded;
    private Integer linesDeleted;
    private String expression;
    private Integer coreAssetId;
    private String coreAssetName;
    private DeveloperGroupCustInVariationPoint mostImportantDeveloperGroup;

    public VariationPoint() {
        super();
    }

    public VariationPoint(Integer id, Integer linesAdded, Integer linesDeleted, String expression, Integer coreAssetId,
                          String coreAssetName, DeveloperGroupCustInVariationPoint mostImportantDeveloperGroup) {
        super();
        this.id = id;
        this.linesAdded = linesAdded;
        this.linesDeleted = linesDeleted;
        this.expression = expression;
        this.coreAssetId = coreAssetId;
        this.coreAssetName = coreAssetName;
        this.mostImportantDeveloperGroup = mostImportantDeveloperGroup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLinesAdded() {
        return linesAdded;
    }

    public void setLinesAdded(Integer linesAdded) {
        this.linesAdded = linesAdded;
    }

    public Integer getLinesDeleted() {
        return linesDeleted;
    }

    public void setLinesDeleted(Integer linesDeleted) {
        this.linesDeleted = linesDeleted;
    }

    public String getCoreAssetName() {
        return coreAssetName;
    }

    public void setCoreAssetName(String coreAssetName) {
        this.coreAssetName = coreAssetName;
    }

    public Integer getCoreAssetId() {
        return coreAssetId;
    }

    public void setCoreAssetId(Integer coreAssetId) {
        this.coreAssetId = coreAssetId;
    }

    public String getExpression() {
        try {
            return Formatting.decodeFromBase64(expression);
        } catch (Exception e) {
            return expression;
        }
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public DeveloperGroupCustInVariationPoint getMostImportantDeveloperGroup() {
        return mostImportantDeveloperGroup;
    }

    public void setMostImportantDeveloperGroup(DeveloperGroupCustInVariationPoint mostImportantDeveloperGroup) {
        this.mostImportantDeveloperGroup = mostImportantDeveloperGroup;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        VariationPoint other = (VariationPoint) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
