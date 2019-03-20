package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.Feature;

public class FeatureRowMapper implements RowMapper<Feature> {

	@Override
	public Feature mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Feature featureCust = new Feature();
		featureCust.setName(resultSet.getString(1));
		featureCust.setLinesAdded(resultSet.getInt(2));
		if (resultSet.getMetaData().getColumnCount() == 3) {
			featureCust.setId(resultSet.getString(3));
		} else {
			featureCust.setLinesDeleted(resultSet.getInt(3));
			featureCust.setId(resultSet.getString(4));
		}
		return featureCust;
	}

}
