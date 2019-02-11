package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import com.onekin.tagcloud.utils.Formatting;


public class PairRowMapper implements RowMapper<Pair<String,String>>{

	@Override
	public Pair<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
		String diffContent = Formatting.decodeFromBase64(rs.getString(1));
		return Pair.of(diffContent,rs.getString(2));
	}

}
