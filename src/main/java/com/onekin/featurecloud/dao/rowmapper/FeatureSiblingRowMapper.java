package com.onekin.featurecloud.dao.rowmapper;

import com.onekin.featurecloud.model.FeatureSibling;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FeatureSiblingRowMapper implements RowMapper<FeatureSibling> {

    @Override
    public FeatureSibling mapRow(ResultSet rs, int rowNum) throws SQLException {
        FeatureSibling fs = new FeatureSibling();
        fs.setId(rs.getInt(1));
        fs.setModifiedLines(rs.getInt(2) + rs.getInt(3));
        fs.setFeatureExpression(rs.getString(4));
        return fs;
    }

}
