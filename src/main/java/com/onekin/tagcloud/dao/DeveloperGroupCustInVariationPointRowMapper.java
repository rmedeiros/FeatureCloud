package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.model.DeveloperGroup;
import com.onekin.tagcloud.model.DeveloperGroupCustInVariationPoint;

public class DeveloperGroupCustInVariationPointRowMapper implements RowMapper<DeveloperGroupCustInVariationPoint> {


	@Override
	public DeveloperGroupCustInVariationPoint mapRow(ResultSet rs, int rowNum) throws SQLException {
		DeveloperGroupCustInVariationPoint  group = new DeveloperGroupCustInVariationPoint();
		group.setDevGroup(new DeveloperGroup());
		group.getDevGroup().setIdDeveloperGroup(rs.getInt(1));
		group.setModifiedLines(rs.getInt(2));
		group.setIdVariationPoint(rs.getInt(3));
		
		return group;
	}

}
