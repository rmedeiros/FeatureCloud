package com.onekin.tagcloud.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.FeatureSibling;

public class FeatureSiblingRowMapper implements RowMapper<FeatureSibling> {

	@Override
	public FeatureSibling mapRow(ResultSet rs, int rowNum) throws SQLException {
		FeatureSibling fs = new FeatureSibling();
		fs.setId(rs.getInt(1));
		fs.setModifiedLines(rs.getInt(2)+rs.getInt(3));
		fs.setFeatureExpression(rs.getString(4));
		return fs;
	}

}
