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
import com.onekin.tagcloud.utils.QueriesConstants;

@Service
public class FeatureDAOImpl implements FeatureDAO {

	private static final String GET_FEATURES = "get.features";

	private static final String GET_GROUPS_BY_FEATURE = "get.features.by.devgroup";

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
		List<Feature> features;
		List<DeveloperGroupCustInFeature> developerGroups;
		if (filter.getDeveloperId() == 0 && filter.getProductReleaseId() == 0) {
			features = getFeatures();
			developerGroups = jdbcTemplate.query(sqlQueries.getProperty(GET_GROUPS_BY_FEATURE),
					new DeveloperGroupCustInFeatureMapper());
		} else if (filter.getDeveloperId() == 0) {
			features = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES + QueriesConstants.FILTER_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new FeatureRowMapper());

			developerGroups = jdbcTemplate.query(
					sqlQueries.getProperty(GET_GROUPS_BY_FEATURE + QueriesConstants.FILTER_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new DeveloperGroupCustInFeatureMapper());
		} else if (filter.getProductReleaseId() == 0) {
			features = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES + QueriesConstants.FILTER_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getDeveloperId());
						}
					}, new FeatureRowMapper());
			developerGroups = jdbcTemplate.query(
					sqlQueries.getProperty(GET_GROUPS_BY_FEATURE + QueriesConstants.FILTER_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getDeveloperId());
						}
					}, new DeveloperGroupCustInFeatureMapper());
		} else {
			features = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES + QueriesConstants.FILTER_ALL),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(2, filter.getDeveloperId());
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new FeatureRowMapper());
			developerGroups = jdbcTemplate.query(
					sqlQueries.getProperty(GET_GROUPS_BY_FEATURE + QueriesConstants.FILTER_ALL),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(2, filter.getDeveloperId());
							preparedStatement.setObject(1, filter.getProductReleaseId());
						}
					}, new DeveloperGroupCustInFeatureMapper());
		}

		for (Feature feature : features) {
			feature.setMostImportantDeveloperGroup(developerGroups.stream()
					.filter(group -> group.getFeatureId().equals(feature.getId())).findFirst().get());
		}

		return features;

	}

}
