package com.onekin.tagcloud.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_release")
public class ProductRelease {

	@Id
	@Column(name="idproductrelease")
	int idProductRelease;
	String name;// tag
	Date date;
	String commits_set;

	public ProductRelease() {
	}

	public ProductRelease(int idProductRelease, String name, Date date, String commits_set) {
		super();
		this.idProductRelease = idProductRelease;
		this.name = name;
		this.date = date;
		this.commits_set = commits_set;
		// this.idproduct = id_product;
	}

	
	public ProductRelease(int idProductRelease, String name) {
		super();
		this.idProductRelease = idProductRelease;
		this.name = name;
	}

	public int getIdProductRelease() {
		return idProductRelease;
	}

	public void setIdProductRelease(int idProductRelease) {
		this.idProductRelease = idProductRelease;
	}

	public String getName() {
		return name;
	}

	public String getNameFormated(String split) {
		String pname = this.name;
		if (this.name.contains(split))
			pname = this.name.split(split)[0];

		return pname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCommits_set() {
		return commits_set;
	}

	public void setCommits_set(String commits_set) {
		this.commits_set = commits_set;
	}

}
