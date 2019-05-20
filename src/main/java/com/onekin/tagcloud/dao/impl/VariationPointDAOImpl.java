package com.onekin.tagcloud.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.onekin.tagcloud.dao.VariationPointDAO;
import com.onekin.tagcloud.dao.rowmapper.CoreAssetRowMapper;
import com.onekin.tagcloud.dao.rowmapper.CustomDiffRowMapper;
import com.onekin.tagcloud.dao.rowmapper.VariationPointRowMapper;
import com.onekin.tagcloud.model.CoreAsset;
import com.onekin.tagcloud.model.CustomDiff;
import com.onekin.tagcloud.model.DeveloperGroupCustInVariationPoint;
import com.onekin.tagcloud.model.Filter;
import com.onekin.tagcloud.model.VariationPoint;
import com.onekin.tagcloud.utils.QueriesConstants;

@Component
public class VariationPointDAOImpl implements VariationPointDAO {

	private static final String GET_FEATURE_VARIATION_POINTS = "get.feature.variation.points";

	private static final String GET_GROUPS_BY_VP = "get.vp.by.devgroup";

	private static final String GET_DIFFVALUES = "get.diffvalues";

	private static final String GET_RELEASE_FEATURE_VARIATION_POINTS = "get.release.feature.variation.points";

	private static final String GET_RELEASE_VP_CONTENT = "get.release.vp.content";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<VariationPoint> getFeatureVariationPoints(String featureId) {
		List<VariationPoint> variationPoints = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS),
				new PreparedStatementSetter() {

					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1, featureId);
					}
				}, new VariationPointRowMapper());
		return variationPoints;
	}

	@Override
	public List<VariationPoint> getVariationPointsFiltered(Filter filter) {
		List<DeveloperGroupCustInVariationPoint> developerGroups;
		List<VariationPoint> variationPoints;
		if (filter.getDeveloperId() == 0 && filter.getProductReleaseId().equals("0")) {
			variationPoints = getFeatureVariationPoints(filter.getFeatureName());

		} else if (filter.getDeveloperId() == 0) {
			variationPoints = jdbcTemplate.query(
					sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_PRODUCT),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getFeatureName());
							preparedStatement.setObject(2, filter.getProductReleaseId());
						}
					}, new VariationPointRowMapper());
		} else if (filter.getProductReleaseId().equals("0")) {
			variationPoints = jdbcTemplate.query(
					sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_DEVELOPER),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setObject(1, filter.getFeatureName());
							preparedStatement.setObject(2, filter.getDeveloperId());
						}
					}, new VariationPointRowMapper());
		} else {
			variationPoints = jdbcTemplate.query(
					sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_ALL),
					new PreparedStatementSetter() {

						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setString(1, filter.getFeatureName());
							preparedStatement.setInt(2, filter.getDeveloperId());
							preparedStatement.setString(3, filter.getProductReleaseId());
						}
					}, new VariationPointRowMapper());

		}
		return variationPoints;

	}

	@Override
	public List<CustomDiff> getDiffValues(Integer variationPointId) {
		List<CustomDiff> data = jdbcTemplate.query(sqlQueries.getProperty(GET_DIFFVALUES),
				new Object[] { variationPointId }, new CustomDiffRowMapper());
		return data;
	}

	@Override
	public List<VariationPoint> getReleaseVariationPoints(String featureName) {
		List<VariationPoint> variationPoints = jdbcTemplate
				.query(sqlQueries.getProperty(GET_RELEASE_FEATURE_VARIATION_POINTS), new PreparedStatementSetter() {

					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1, featureName);
					}
				}, new VariationPointRowMapper());

		return variationPoints;
	}

	@Override
	public CoreAsset getVPContentAndAsset(Integer variationPointId) {
		CoreAsset coreAsset = jdbcTemplate.queryForObject(sqlQueries.getProperty(GET_RELEASE_VP_CONTENT),
				new Object[] { variationPointId }, new CoreAssetRowMapper());

		return coreAsset;
	}
}
