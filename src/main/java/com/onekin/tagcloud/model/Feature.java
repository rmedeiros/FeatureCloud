package com.onekin.tagcloud.model;

public class Feature {

	private String id;
	private String name;
	private Integer linesDeleted;
	private Integer linesAdded;
	private DeveloperGroupCustInFeature mostImportantDeveloperGroup;

	public Feature() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feature(String id, String name, Integer linesDeleted, Integer linesAdded,
			DeveloperGroupCustInFeature mostImportantDeveloperGroup) {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feature [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", linesDeleted=");
		builder.append(linesDeleted);
		builder.append(", linesAdded=");
		builder.append(linesAdded);
		builder.append(", mostImportantDeveloperGroup=");
		builder.append(mostImportantDeveloperGroup);
		builder.append("]");
		return builder.toString();
	}

}
