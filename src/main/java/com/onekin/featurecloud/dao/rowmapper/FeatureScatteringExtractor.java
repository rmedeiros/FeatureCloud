package com.onekin.featurecloud.dao.rowmapper;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FeatureScatteringExtractor implements ResultSetExtractor<Map<String, Integer>> {

    @Override
    public Map<String, Integer> extractData(ResultSet rs) throws SQLException {
        Map<String, Integer> returnMap = new HashMap<>();
        while (rs.next()) {
            returnMap.put(rs.getString(1), rs.getInt(2));
        }
        return returnMap;
    }

}
