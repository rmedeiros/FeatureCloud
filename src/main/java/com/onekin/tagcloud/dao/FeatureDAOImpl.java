package com.onekin.tagcloud.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.model.DeveloperGroupCustInFeature;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.utils.QueriesConstants;

@Service
public class FeatureDAOImpl implements FeatureDAO {

	private static final String GET_FEATURES = "get.features";

	private static final String GET_GROUPS_BY_FEATURE = "get.features.by.devgroup";

	private static final String GET_TANGLING = "get.tangling";
	
	private static final String GET_SCATTERING = "get.scattering";


	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;


	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<Feature> getFeatures() {
		List<Feature> features =jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
		parameters.addValue("ids", featureIds);
	 	Map<String,Integer> featuresScatteringMap=namedJdbcTemplate.query(sqlQueries.getProperty(GET_SCATTERING), parameters, new FeatureScatteringExtractor());

		for (Feature feature : features) {
			feature.setFeatureScattering(featuresScatteringMap.get(feature.getId()));
		}
		return features;
	}

	@Override
	public List<Feature> getFeaturesFiltered(Filter filter) {
		List<Feature> features;
		List<DeveloperGroupCustInFeature> developerGroups;
		if (filter.getDeveloperId() == 0 && filter.getProductReleaseId().equals("0")) {
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
		} else if (filter.getProductReleaseId().equals("0")) {
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
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
		parameters.addValue("ids", featureIds);
	 	Map<String,Integer> featuresScatteringMap=namedJdbcTemplate.query(sqlQueries.getProperty(GET_SCATTERING), parameters, new FeatureScatteringExtractor());

		for (Feature feature : features) {
			feature.setMostImportantDeveloperGroup(developerGroups.stream()
					.filter(group -> group.getFeatureId().equals(feature.getId())).findFirst().get());
			feature.setFeatureScattering(featuresScatteringMap.get(feature.getId()));
		}

		return features;

	}

	@Override
	public String getTanglingFeatureList(List<String> featureIdList) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", featureIdList);
		return 	namedJdbcTemplate.query(sqlQueries.getProperty(GET_TANGLING), parameters, new FeatureTanglingExtractor());
	}

}
