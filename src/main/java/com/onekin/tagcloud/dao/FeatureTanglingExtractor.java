package com.onekin.tagcloud.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FeatureTanglingExtractor implements ResultSetExtractor<String> {

	@Override
	public String extractData(ResultSet rs) throws SQLException, DataAccessException {
		String featureTanglingList = "";
		List<String> repeated = new ArrayList<String>();
		while (rs.next()) {
			if (!repeated.contains(rs.getString(1) + ' ' + rs.getString(2))) {
				if (rs.isLast()) {
					featureTanglingList += "aaaa " + rs.getString(1) + ' ' + rs.getString(2);

				} else {
					featureTanglingList += "aaaa " + rs.getString(1) + ' ' + rs.getString(2) + "  ";
				}
				if(rs.getString(1).equals(rs.getString(2))) {
					repeated.add(rs.getString(1) + ' ' + rs.getString(2));
				}
				
			}
		}
		System.out.println(featureTanglingList);
		return featureTanglingList;
	}

}
