package com.onekin.featurecloud.dao.impl;

import com.onekin.featurecloud.dao.MetadaBoxDAO;
import com.onekin.featurecloud.dao.rowmapper.SnapshotMetadataExtractor;
import com.onekin.featurecloud.model.SnapshotMetada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.OperationNotSupportedException;
import java.util.Properties;
@Service
public class MetadataBoxDAOImpl implements MetadaBoxDAO {

    @Resource(name = "snapshot-queries")
    private Properties snapshotSqlQueries;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public SnapshotMetada getSnapshotMetadataBox() {
       return  namedJdbcTemplate.query(snapshotSqlQueries.getProperty("get.metadata"), new SnapshotMetadataExtractor());

    }

    @Override
    public SnapshotMetada getDeltaMetadataBox() throws OperationNotSupportedException {
        throw new OperationNotSupportedException();
    }
}
