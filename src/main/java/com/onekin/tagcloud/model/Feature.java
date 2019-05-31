package com.onekin.tagcloud.model;

public class Feature {

	private String id;
	private String name;
	private Integer linesDeleted;
	private Integer linesAdded;
	private DeveloperGroupCustInFeature mostImportantDeveloperGroup;
	private int featureScattering;
	private int tangling;

	public Feature() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feature(String id, String name, Integer linesDeleted, Integer linesAdded,
			DeveloperGroupCustInFeature mostImportantDeveloperGroup, int tangling) {
		super();
		this.name = name;
		this.linesDeleted = linesDeleted;
		this.linesAdded = linesAdded;
		this.id = id;
		this.mostImportantDeveloperGroup = mostImportantDeveloperGroup;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLinesDeleted() {
		return linesDeleted;
	}

	public void setLinesDeleted(Integer linesDeleted) {
		this.linesDeleted = linesDeleted;
	}

	public Integer getLinesAdded() {
		return linesAdded;
	}

	public void setLinesAdded(Integer linesAdded) {
		this.linesAdded = linesAdded;
	}

	public DeveloperGroupCustInFeature getMostImportantDeveloperGroup() {
		return mostImportantDeveloperGroup;
	}

	public void setMostImportantDeveloperGroup(DeveloperGroupCustInFeature mostImportantDeveloperGroup) {
		this.mostImportantDeveloperGroup = mostImportantDeveloperGroup;
	}

	public int getTangling() {
		return tangling;
	}

	public void setTangling(int tangling) {
		this.tangling = tangling;
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
		Feature other = (Feature) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int getFeatureScattering() {
		return featureScattering;
	}

	public void setFeatureScattering(int featureScattering) {
		this.featureScattering = featureScattering;
	}

	@Override
	public String toString() {
		return "Feature [id=" + id + ", name=" + name + ", linesDeleted=" + linesDeleted + ", linesAdded=" + linesAdded
				+ ", mostImportantDeveloperGroup=" + mostImportantDeveloperGroup + ", featureScattering="
				+ featureScattering + ", tangling=" + tangling + "]";
	}

}
