package com.onekin.tagcloud.model;

public class Feature {

	private String name;
	private Integer linesDeleted;
	private Integer linesAdded;

	public Feature() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feature(Integer id, String name, Integer linesDeleted, Integer linesAdded) {
		super();
		this.name = name;
		this.linesDeleted = linesDeleted;
		this.linesAdded = linesAdded;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Feature [name=");
		builder.append(name);
		builder.append(", linesDeleted=");
		builder.append(linesDeleted);
		builder.append(", linesAdded=");
		builder.append(linesAdded);
		builder.append("]");
		return builder.toString();
	}

}
