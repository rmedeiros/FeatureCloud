package com.onekin.featurecloud.dao.rowmapper;

import com.onekin.featurecloud.model.SnapshotMetada;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SnapshotMetadataExtractor implements ResultSetExtractor<SnapshotMetada> {


    @Override
    public SnapshotMetada extractData(ResultSet rs) throws SQLException {
        SnapshotMetada snapshotMetada = new SnapshotMetada();

        if(rs.next()) {
            snapshotMetada.setFeatures(rs.getInt(1));
            snapshotMetada.setVariableCode(rs.getLong(2));
            snapshotMetada.setVariationPoints(rs.getInt(3));
        }
        return snapshotMetada;
    }
}
