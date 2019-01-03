package com.onekin.tagcloud.model;

import java.util.List;

public class FeaturesResponse {

	List<Feature> features;
	int totalLines;
	public FeaturesResponse(List<Feature> features, int totalLines) {
		super();
		this.features = features;
		this.totalLines = totalLines;
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
	public int getTotalLines() {
		return totalLines;
	}
	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeaturesResponse [features=");
		builder.append(features);
		builder.append(", totalLines=");
		builder.append(totalLines);
		builder.append("]");
		return builder.toString();
	}
	
}
