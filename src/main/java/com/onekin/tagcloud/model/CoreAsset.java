package com.onekin.tagcloud.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.onekin.tagcloud.utils.Formatting;

@Entity
@Table(name = "core_asset")
public class CoreAsset {

	@Id
	private int idcoreasset;
	private String name;
	private String path;
	private String content;
	private int size;
	private int isnewasset;
	private int idpackage;

	public CoreAsset() {
	}

	public CoreAsset(String name, String path, String content) {
		super();
		this.name = name;
		this.path = path;
		this.content = content;
	}

	public int getIdcoreasset() {
		return idcoreasset;
	}

	public void setIdcoreasset(int idcoreasset) {
		this.idcoreasset = idcoreasset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		try {
			return Formatting.decodeFromBase64(content);
		} catch (Exception e) {
			return content;
		}

	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIsnewasset() {
		return isnewasset;
	}

	public void setIsnewasset(int isnewasset) {
		this.isnewasset = isnewasset;
	}

	public int getIdpackage() {
		return idpackage;
	}

	public void setIdpackage(int idpackage) {
		this.idpackage = idpackage;
	}

}
