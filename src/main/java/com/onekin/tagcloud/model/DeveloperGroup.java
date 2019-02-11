package com.onekin.tagcloud.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "developer_group")
public class DeveloperGroup {

	@Id
	private Integer idDeveloperGroup;
	private String color;
	@Transient
	private List<Developer> developers;
	private String name;

	public DeveloperGroup(Integer idDeveloperGroup, List<Developer> developers, String color) {
		super();
		this.idDeveloperGroup = idDeveloperGroup;
		this.developers = developers;
		this.color = color;
	}

	public DeveloperGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeveloperGroup(int id, String name) {
		this.idDeveloperGroup = id;
		this.setName(name);
	}

	public Integer getIdDeveloperGroup() {
		return idDeveloperGroup;
	}

	public void setIdDeveloperGroup(Integer idDeveloperGroup) {
		this.idDeveloperGroup = idDeveloperGroup;
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDeveloperGroup == null) ? 0 : idDeveloperGroup.hashCode());
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
		DeveloperGroup other = (DeveloperGroup) obj;
		if (idDeveloperGroup == null) {
			if (other.idDeveloperGroup != null)
				return false;
		} else if (!idDeveloperGroup.equals(other.idDeveloperGroup))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeveloperGroup [idDeveloperGroup=");
		builder.append(idDeveloperGroup);
		builder.append(", color=");
		builder.append(color);
		builder.append(", developers=");
		builder.append(developers);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}

}
