package com.onekin.tagcloud.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.utils.Formatting;

public class CustomDiffRowMapper implements RowMapper<CustomDiff> {

	@Override
	public CustomDiff mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomDiff customDiff = new CustomDiff();
		customDiff.setContent(Formatting.decodeFromBase64(rs.getString(1)));
		customDiff.setType(rs.getString(2));
		customDiff.setPath(rs.getString(3));
		return customDiff;
	}

}