package com.onekin.featurecloud.dao.impl;

import com.onekin.featurecloud.dao.FeatureDAO;
import com.onekin.featurecloud.dao.rowmapper.FeatureRowMapper;
import com.onekin.featurecloud.dao.rowmapper.FeatureScatteringExtractor;
import com.onekin.featurecloud.dao.rowmapper.FeatureTanglingExtractor;
import com.onekin.featurecloud.model.Feature;
import com.onekin.featurecloud.model.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class FeatureDAOImpl implements FeatureDAO {

    private static final String GET_FEATURES = "get.features";

    private static final String GET_RELEASE_FEATURES = "get.release.features";

    private static final String GET_TANGLING = "get.tangling";

    private static final String GET_TANGLING_BY_PACKAGE = "get.tangling.by.package";


    private static final String GET_SCATTERING = "get.scattering";

    private static final String GET_SCATTERING_DELTA = "get.scattering.delta";

    private static final String GET_TANGLING_DELTA = "get.tangling.delta";
    private static final String GET_TANGLING_METRIC = "get.tangling.metric";
    private static final String GET_TANGLING_METRIC_BY_PACKAGE = "get.tangling.metric.by.package";


    private static final String GET_TANGLING_DELTA_BY_PRODUCT = "get.tangling.delta.by.product";


    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Resource(name = "queries")
    private Properties deltaSqlQueries;

    @Resource(name = "snapshot-queries")
    private Properties snapshotSqlQueries;


    @Override
    public List<Feature> getFeatures() {
        List<Feature> features = namedJdbcTemplate.query(deltaSqlQueries.getProperty(GET_FEATURES), new FeatureRowMapper());
        setFeatureScattering(features, GET_SCATTERING_DELTA,deltaSqlQueries);
        setFeatureTanglingMetric(features, deltaSqlQueries);
        return features;
    }

    private void setFeatureTanglingMetric(List<Feature> features, Properties sqlQueries) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
        parameters.addValue("ids", featureIds);
        Map<String, Integer> featuresTanglingMap = namedJdbcTemplate.query(sqlQueries.getProperty(GET_TANGLING_METRIC), parameters,
                new FeatureScatteringExtractor());
        int tanglingMetric;
        for (Feature feature : features) {
            tanglingMetric = featuresTanglingMap.get(feature.getId()) == null ? 0
                    : featuresTanglingMap.get(feature.getId());
            feature.setTangling(tanglingMetric);
        }
    }

    private void setFeatureScattering(List<Feature> features, String sql, Properties queryProperties) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
        parameters.addValue("ids", featureIds);
        Map<String, Integer> featuresScatteringMap = namedJdbcTemplate.query(queryProperties.getProperty(sql), parameters,
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
        Map<String, Integer> featuresScatteringMap = namedJdbcTemplate.query(deltaSqlQueries.getProperty(GET_SCATTERING),
                parameters, new FeatureScatteringExtractor());
        setFeatureTanglingMetric(features, deltaSqlQueries);

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
        return namedJdbcTemplate.query(snapshotSqlQueries.getProperty(GET_TANGLING), parameters,
                new FeatureTanglingExtractor());
    }


    @Override
    public String getDeltaTanglingByProduct(List<String> featureIdList) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", featureIdList);
        return namedJdbcTemplate.query(deltaSqlQueries.getProperty(GET_TANGLING_DELTA_BY_PRODUCT), parameters,
                new FeatureTanglingExtractor());
    }

    @Override
    public String getTanglingFeatureListByPackage(List<String> featureIdList, int packageId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("packageId", packageId);
        parameters.addValue("ids", featureIdList);
        return namedJdbcTemplate.query(snapshotSqlQueries.getProperty(GET_TANGLING_BY_PACKAGE), parameters,
                new FeatureTanglingExtractor());
    }

    @Override
    public List<Feature> getAllFeatures() {

        List<Feature> features = namedJdbcTemplate.query(snapshotSqlQueries.getProperty(GET_RELEASE_FEATURES),
                new FeatureRowMapper());
        setFeatureScattering(features, GET_SCATTERING, snapshotSqlQueries);
        setFeatureTanglingMetric(features, snapshotSqlQueries);
        return features;
    }

    @Override
    public List<Feature> getFeaturesByProduct(String productId, int packageId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query;

        if (productId != null && !("".equals(productId.trim())) && !("All".equals(productId)) && (packageId != 0)) {
            parameters.addValue("productId", productId);
            parameters.addValue("idpackage", packageId);
            query = "get.features.by.all";
        } else if (packageId != 0) {
            parameters.addValue("idpackage", packageId);
            query = "get.features.by.package";
        } else {
            parameters.addValue("productId", productId);
            query = "get.features.by.product";
        }
        List<Feature> features = namedJdbcTemplate.query(deltaSqlQueries.getProperty(query), parameters,
                new FeatureRowMapper());
        setFeatureScattering(features, GET_SCATTERING_DELTA,deltaSqlQueries);
        setFeatureTanglingMetric(features, deltaSqlQueries);
        return features;
    }


    @Override
    public List<Feature> getSnapshotFeaturesByProduct(String productId, int packageId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String query;

        if (productId != null && !("".equals(productId.trim())) && !("All".equals(productId)) && (packageId != 0)) {
            parameters.addValue("productId", productId);
            parameters.addValue("idpackage", packageId);
            query = "get.features.by.all";
        } else if (packageId != 0) {
            parameters.addValue("idpackage", packageId);
            query = "get.features.by.package";
        } else {
            parameters.addValue("productId", productId);
            query = "get.features.by.product";
        }
        List<Feature> features = namedJdbcTemplate.query(snapshotSqlQueries.getProperty(query), parameters,
                new FeatureRowMapper());
        setFeatureScattering(features, GET_SCATTERING, snapshotSqlQueries);

        if(packageId==0) {
            setFeatureTanglingMetric(features, snapshotSqlQueries);

        }else {
            setFeatureTanglingMetricFiltered(features, snapshotSqlQueries,packageId);
        }
        return features;
    }

    private void setFeatureTanglingMetricFiltered(List<Feature> features, Properties snapshotSqlQueries, int packageId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        List<String> featureIds = features.stream().map(Feature::getId).collect(Collectors.toList());
        parameters.addValue("ids", featureIds);
        parameters.addValue("packageId", packageId);

        Map<String, Integer> featuresTanglingMap = namedJdbcTemplate.query(snapshotSqlQueries.getProperty(GET_TANGLING_METRIC_BY_PACKAGE), parameters,
                new FeatureScatteringExtractor());
        int tanglingMetric;
        for (Feature feature : features) {
            tanglingMetric = featuresTanglingMap.get(feature.getId()) == null ? 0
                    : featuresTanglingMap.get(feature.getId());
            feature.setTangling(tanglingMetric);
        }
    }


    @Override
    public String getDeltaTangling(List<String> featureIdList) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", featureIdList);
        return namedJdbcTemplate.query(deltaSqlQueries.getProperty(GET_TANGLING_DELTA), parameters,
                new FeatureTanglingExtractor());
    }

}
