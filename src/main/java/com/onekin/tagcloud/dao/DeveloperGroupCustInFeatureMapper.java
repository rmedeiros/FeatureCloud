package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.DeveloperGroup;
import com.onekin.tagcloud.model.DeveloperGroupCustInFeature;

public class DeveloperGroupCustInFeatureMapper implements  RowMapper<DeveloperGroupCustInFeature> {

	@Override
	public DeveloperGroupCustInFeature mapRow(ResultSet rs, int arg1) throws SQLException {
		DeveloperGroupCustInFeature group = new DeveloperGroupCustInFeature();
		group.setFeatureId(rs.getString(1));
		group.setDevGroup(new DeveloperGroup());
		group.getDevGroup().setIdDeveloperGroup(rs.getInt(2));
		group.setModifiedLines(rs.getInt(3));
		return group;

	}

}
