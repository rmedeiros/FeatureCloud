package com.onekin.tagcloud.dao;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.model.Feature;

@Service
public class FeatureDAOImpl implements FeatureDAO{

	
	private static final String GET_FEATURES = "get.features.customizations";
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="queries")
	private Properties sqlQueries;
	
	
	@Override
	public List<Feature> getFeatures() {
		 return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());

	}


}
