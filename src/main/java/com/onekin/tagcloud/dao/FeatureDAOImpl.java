package com.onekin.tagcloud.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;

@Service
public class FeatureDAOImpl implements FeatureDAO {

	private static final String GET_FEATURES = "get.features.customizations";

	private static final String GET_FEATURES_FILTERED_BY_PRODUCT_AND_DEVELOPER = "get.features.filtered";

	private static final String GET_FEATURES_FILTERED_BY_PRODUCT = "get.features.filtered.product";

	private static final String GET_FEATURES_FILTERED_BY_DEVELOPER = "get.features.filtered.developer";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<Feature> getFeatures() {
		return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());

	}

	@Override
	public List<Feature> getFeaturesFiltered(Filter filter) {

		if (filter.getDeveloperId() == 0 && filter.getProductReleaseId() == 0) {
			return getFeatures();
		} else if (filter.getDeveloperId() == 0) {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES_FILTERED_BY_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new FeatureRowMapper());
		} else if (filter.getProductReleaseId() == 0) {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES_FILTERED_BY_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getDeveloperId());
						}
					}, new FeatureRowMapper());
		} else {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES_FILTERED_BY_PRODUCT_AND_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(2, filter.getDeveloperId());
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new FeatureRowMapper());
		}

	}

}
