package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.VariationPoint;

public class VariationPointRowMapper implements RowMapper<VariationPoint> {

	@Override
	public VariationPoint mapRow(ResultSet resultSet, int arg1) throws SQLException {
		VariationPoint variationPointCust =  new VariationPoint();
		variationPointCust.setId(resultSet.getInt(1));
		variationPointCust.setLinesAdded(resultSet.getInt(2));
		variationPointCust.setLinesDeleted(resultSet.getInt(3));
		variationPointCust.setCoreAssetId(resultSet.getInt(4));
		variationPointCust.setCoreAssetName(resultSet.getString(5));
		return variationPointCust;
	}

}
