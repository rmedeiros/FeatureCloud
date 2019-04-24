package com.onekin.tagcloud.dao;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.onekin.tagcloud.dao.rowmapper.FeatureScatteringExtractor;
import com.onekin.tagcloud.model.Feature;
import com.onekin.tagcloud.model.Filter;

@Service
public class FeatureDAOImpl implements FeatureDAO {

	private static final String GET_FEATURES = "get.features";

	private static final String GET_RELEASE_FEATURES = "get.release.features";

	private static final String GET_TANGLING = "get.tangling";

	private static final String GET_SCATTERING = "get.scattering";

	private static final String GET_SCATTERING_DELTA = "get.scattering.delta";

	private static final String GET_TANGLING_DELTA = "get.tangling.delta";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Resource(name = "queries")
	private Properties sqlQueries;

	@Override
	public List<Feature> getFeatures() {
		List<Feature> features = jdbcTemplate.query(sqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());
		setFeatureScattering(features, GET_SCATTERING_DELTA);
		return features;
	}

	private void setFeatureScattering(List<Feature> features, String sql) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
		parameters.addValue("ids", featureIds);
		Map<String, Integer> featuresScatteringMap = namedJdbcTemplate.query(sqlQueries.getProperty(sql), parameters,
				new FeatureScatteringExtractor());
		int scatteringDelta;
		for (Feature feature : features) {
			scatteringDelta = featuresScatteringMap.get(feature.getId()) == null ? 0
					: featuresScatteringMap.get(feature.getId());
			feature.setFeatureScattering(scatteringDelta);
		}
	}

	@Override
	public List<Feature> getFeaturesFiltered(Filter filter) {
		List<Feature> features;
		features = getFeatures();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
		parameters.addValue("ids", featureIds);
		Map<String, Integer> featuresScatteringMap = namedJdbcTemplate.query(sqlQueries.getProperty(GET_SCATTERING),
				parameters, new FeatureScatteringExtractor());
		int scatteringDelta;
		for (Feature feature : features) {
			scatteringDelta = featuresScatteringMap.get(feature.getId()) == null ? 0
					: featuresScatteringMap.get(feature.getId());
			feature.setFeatureScattering(scatteringDelta);
		}
		return features;

	}

	@Override
	public String getTanglingFeatureList(List<String> featureIdList) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", featureIdList);
		return namedJdbcTemplate.query(sqlQueries.getProperty(GET_TANGLING), parameters,
				new FeatureTanglingExtractor());
	}

	@Override
	public String getDeltaTangling(List<String> featureIdList) {
		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", featureIdList);
		return namedJdbcTemplate.query(sqlQueries.getProperty(GET_TANGLING_DELTA),
				parameters, new FeatureTanglingExtractor());
	}

	@Override
	public List<Feature> getAllFeatures() {

		List<Feature> features = namedJdbcTemplate.query(sqlQueries.getProperty(GET_RELEASE_FEATURES),
				new FeatureRowMapper());
		setFeatureScattering(features, GET_SCATTERING);
		return features;
	}

}
