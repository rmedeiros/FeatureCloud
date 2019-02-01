package com.onekin.tagcloud.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.onekin.tagcloud.model.DeveloperGroupCustInVariationPoint;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.VariationPoint;

@Component
public class VariationPointDAOImpl implements VariationPointDAO {


	private static final String GET_FEATURE_VARIATION_POINTS = "get.feature.variation.points";
	
	private static final String GET_FEATURE_VARIATION_POINTS_BY_DEVELOPER_AND_PRODUCT = "get.feature.variation.points.filtered";

	private static final String GET_FEATURE_VARIATION_POINTS_BY_PRODUCT = "get.feature.variation.points.product";

	private static final String GET_FEATURE_VARIATION_POINTS_BY_DEVELOPER = "get.feature.variation.points.developer";
	
	private static final String GET_GROUPS_BY_VP = "get.vp.by.devgroup";



	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;


	@Override
	public List<VariationPoint> getFeatureVariationPoints(String featureId) {
		List<VariationPoint> variationPoints =  jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS), new PreparedStatementSetter() {

			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, featureId);
			}
		}, new VariationPointRowMapper());

		List<DeveloperGroupCustInVariationPoint> developerGroups = jdbcTemplate
				.query(sqlQueries.getProperty(GET_GROUPS_BY_VP), new PreparedStatementSetter() {

					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1, featureId);
					}
				}, new DeveloperGroupCustInVariationPointRowMapper());
		for (VariationPoint variationPoint : variationPoints) {
			System.out.println("ID VP: "+ variationPoint.getId());
			variationPoint.setMostImportantDeveloperGroup(developerGroups.stream()
					.filter(group -> group.getIdVariationPoint()==variationPoint.getId()).findFirst().get());
		}
		
		return variationPoints;
	}

	@Override
	public List<VariationPoint> getVariationPointsFiltered(Filter filter) {
		if (filter.getDeveloperId() == 0 && filter.getProductReleaseId() == 0) {
			return getFeatureVariationPoints(filter.getFeatureName());
		} else if (filter.getDeveloperId() == 0) {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS_BY_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getFeatureName());
							preparedStatement.setObject(2, filter.getProductReleaseId());
						}
					}, new VariationPointRowMapper());
		} else if (filter.getProductReleaseId() == 0) {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS_BY_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getFeatureName());
							preparedStatement.setObject(2, filter.getDeveloperId());
						}
					}, new VariationPointRowMapper());
		} else {
			return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS_BY_DEVELOPER_AND_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setString(1, filter.getFeatureName());
							preparedStatement.setInt(2, filter.getDeveloperId());
							preparedStatement.setInt(3, filter.getProductReleaseId());
						}
					}, new VariationPointRowMapper());
		}
	}
}
