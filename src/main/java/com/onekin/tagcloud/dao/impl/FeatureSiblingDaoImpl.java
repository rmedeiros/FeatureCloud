package com.onekin.tagcloud.dao.impl;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.FeatureSiblingDAO;
import com.onekin.tagcloud.dao.rowmapper.FeatureSiblingRowMapper;
import com.onekin.tagcloud.model.FeatureSibling;

@Service
public class FeatureSiblingDaoImpl implements FeatureSiblingDAO {

	private static final String GET_FEATURE_SIBLINGS_DELTA = "get.featuresibling.delta";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;
	
	
	@Override
	public List<FeatureSibling> getModifiedFeaturesiblings(String id_feature) {
		return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_SIBLINGS_DELTA), new String[] {id_feature} ,new FeatureSiblingRowMapper());
	}

}
