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

import com.onekin.tagcloud.model.DeveloperGroupCustInFeature;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;

@Service
public class FeatureDAOImpl implements FeatureDAO {

	private static final String GET_FEATURES = "get.features.customizations";

	private static final String GET_FEATURES_FILTERED_BY_PRODUCT_AND_DEVELOPER = "get.features.filtered";

	private static final String GET_FEATURES_FILTERED_BY_PRODUCT = "get.features.filtered.product";

	private static final String GET_FEATURES_FILTERED_BY_DEVELOPER = "get.features.filtered.developer";

	private static final String GET_GROUPS_BY_FEATURE = "get.features.by.devgroup";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<Feature> getFeatures() {
		List<Feature> features = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());
		List<DeveloperGroupCustInFeature> developerGroups = jdbcTemplate
				.query(sqlQueries.getProperty(GET_GROUPS_BY_FEATURE), new DeveloperGroupCustInFeatureMapper());
		for (Feature feature : features) {
			feature.setMostImportantDeveloperGroup(developerGroups.stream()
					.filter(group -> group.getFeatureId().equals(feature.getId())).findFirst().get());
		}
		return features;
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
