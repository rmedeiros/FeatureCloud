package com.onekin.tagcloud.model;

import java.util.List;

public class VariationPointsResponse {

	private List<VariationPoint> variationPoints;

	private int totalLines;

	public VariationPointsResponse(List<VariationPoint> variationPoints, int totalLines) {
		super();
		this.variationPoints = variationPoints;
		this.totalLines = totalLines;
	}

	public VariationPointsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<VariationPoint> getVariationPoints() {
		return variationPoints;
	}

	public void setVariationPoints(List<VariationPoint> variationPoints) {
		this.variationPoints = variationPoints;
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
		builder.append("VariationPointsResponse [variationPoints=");
		builder.append(variationPoints);
		builder.append(", totalLines=");
		builder.append(totalLines);
		builder.append("]");
		return builder.toString();
	}

}
