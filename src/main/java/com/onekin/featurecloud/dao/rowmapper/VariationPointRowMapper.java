package com.onekin.featurecloud.dao.rowmapper;

import com.onekin.featurecloud.model.VariationPoint;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VariationPointRowMapper implements RowMapper<VariationPoint> {

    @Override
    public VariationPoint mapRow(ResultSet resultSet, int arg1) throws SQLException {
        VariationPoint variationPointCust = new VariationPoint();
        variationPointCust.setId(resultSet.getInt(1));
        variationPointCust.setLinesAdded(resultSet.getInt(2));

        variationPointCust.setCoreAssetId(resultSet.getInt(3));
        variationPointCust.setCoreAssetName(resultSet.getString(4));

        variationPointCust.setExpression(resultSet.getString(5));
        variationPointCust.setContent(resultSet.getString(6));

        return variationPointCust;
    }

}
