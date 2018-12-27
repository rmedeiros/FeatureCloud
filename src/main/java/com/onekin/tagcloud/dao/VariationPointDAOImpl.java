package com.onekin.tagcloud.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.onekin.tagcloud.model.VariationPoint;

@Component
public class VariationPointDAOImpl implements VariationPointDAO {

	private static final Log LOG = LogFactory.getLog(VariationPointDAOImpl.class);

	private static final String GET_WITH_FEATURE = "get.variation.point.customizations.with.feature";

	private static final String GET_ALL = "get.variation.point.customizations";

	private static final String GET_FEATURE_VARIATION_POINTS = "get.feature.variation.points";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<VariationPoint> getAllVariationPoints() {
		return jdbcTemplate.query(sqlQueries.getProperty(GET_ALL), new VariationPointRowMapper());
	}

	@Override
	public List<VariationPoint> getVariationPointsWithFeature() {
		return jdbcTemplate.query(sqlQueries.getProperty(GET_WITH_FEATURE), new VariationPointRowMapper());
	}

	@Override
	public List<VariationPoint> getFeatureVariationPoints(String featureName) {
		return jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS), new PreparedStatementSetter() {

			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, featureName);
			}
		}, new VariationPointRowMapper());

	}
}
