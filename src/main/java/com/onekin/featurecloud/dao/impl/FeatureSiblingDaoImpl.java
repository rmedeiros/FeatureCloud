package com.onekin.featurecloud.dao.impl;

import com.onekin.featurecloud.dao.rowmapper.FeatureSiblingRowMapper;
import com.onekin.featurecloud.model.FeatureSibling;
import com.onekin.featurecloud.dao.FeatureSiblingDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

@Service
public class FeatureSiblingDaoImpl implements FeatureSiblingDAO {

    private static final String GET_FEATURE_SIBLINGS_DELTA = "get.featuresibling.delta";

    private static final String GET_FEATURE_SIBLINGS = "get.featuresibling";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Resource(name = "queries")
    private Properties sqlQueries;

    @Resource(name = "snapshot-queries")
    private Properties snapshotQueries;


    @Override
    public List<FeatureSibling> getModifiedFeaturesiblings(String id_feature) {
        return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_SIBLINGS_DELTA), new String[]{id_feature}, new FeatureSiblingRowMapper());
    }


    @Override
    public List<FeatureSibling> getAllFeaturesiblingsByFeature(String id_feature) {
        return jdbcTemplate.query(snapshotQueries.getProperty(GET_FEATURE_SIBLINGS), new String[]{id_feature}, new FeatureSiblingRowMapper());
    }


}
