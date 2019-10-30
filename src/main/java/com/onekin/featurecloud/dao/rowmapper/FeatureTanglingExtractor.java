package com.onekin.featurecloud.dao.rowmapper;


import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeatureTanglingExtractor implements ResultSetExtractor<String> {

    @Override
    public String extractData(ResultSet rs) throws SQLException {
        StringBuilder featureTanglingList = new StringBuilder();
        List<String> repeated = new ArrayList<>();

        while (rs.next()) {
            if (!repeated.contains(rs.getString(1) + ' ' + rs.getString(2))) {
                if (!rs.isLast() ) {
                    featureTanglingList .append( " aaaa " + rs.getString(1) + ' ' + rs.getString(2));

                } else {
                    featureTanglingList .append(" aaaa " + rs.getString(1) + ' ' + rs.getString(2) + ' ');
                }
                if (rs.getString(1).equals(rs.getString(2))) {
                    repeated.add(rs.getString(1) + ' ' + rs.getString(2));
                }

            }
        }
        return featureTanglingList.toString();
    }

}
