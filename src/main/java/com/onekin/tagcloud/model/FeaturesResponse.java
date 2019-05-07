package com.onekin.tagcloud.model;

import java.util.List;

public class FeaturesResponse {

	private List<Feature> features;
	private int maxModifiedLines;
	private String newickString;
	public FeaturesResponse(List<Feature> features, int maxModifiedLines, String newickString) {
		super();
		this.features = features;
		this.maxModifiedLines = maxModifiedLines;
		this.setNewickString(newickString);
	}
	public FeaturesResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	public int getMaxModifiedLines() {
		return maxModifiedLines;
	}
	public void setMaxModifiedLines(int maxModifiedLines) {
		this.maxModifiedLines = maxModifiedLines;
	}

	public String getNewickString() {
		return newickString;
	}
	public void setNewickString(String newickString) {
		this.newickString = newickString;
	}
	@Override
	public String toString() {
		return "FeaturesResponse [features=" + features + ", maxModifiedLines=" + maxModifiedLines + ", newickString="
				+ newickString + "]";
	}
	
}
