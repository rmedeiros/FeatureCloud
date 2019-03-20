package com.onekin.tagcloud.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.CoreAsset;

public class CoreAssetRowMapper implements RowMapper<CoreAsset> {

	@Override
	public CoreAsset mapRow(ResultSet rs, int rowNum) throws SQLException {

		CoreAsset coreAsset = new CoreAsset();
		coreAsset.setIdcoreasset(rs.getInt(1));
		coreAsset.setName(rs.getString(2));
		coreAsset.setContent(rs.getString(3));
		return coreAsset;
	}

}
