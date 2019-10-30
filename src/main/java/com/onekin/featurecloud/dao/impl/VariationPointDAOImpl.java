package com.onekin.featurecloud.dao.impl;

import com.onekin.featurecloud.dao.VariationPointDAO;
import com.onekin.featurecloud.dao.rowmapper.CustomDiffRowMapper;
import com.onekin.featurecloud.dao.rowmapper.VariationPointRowMapper;
import com.onekin.featurecloud.model.CustomDiff;
import com.onekin.featurecloud.model.Filter;
import com.onekin.featurecloud.model.VariationPoint;
import com.onekin.featurecloud.utils.QueriesConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class VariationPointDAOImpl implements VariationPointDAO {

    private static final String GET_FEATURE_VARIATION_POINTS = "get.feature.variation.points";

    private static final String GET_DIFFVALUES = "get.diffvalues";

    private static final String GET_RELEASE_VP_CONTENT = "get.release.vp.content";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queries")
    private Properties deltaSqlQueries;

    @Resource(name = "snapshot-queries")
    private Properties snapshotSqlQueries;

    @Override
    public List<VariationPoint> getFeatureVariationPoints(String featureId) {
        return jdbcTemplate.query(deltaSqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS),
                new PreparedStatementSetter() {

                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, featureId);
                    }
                }, new VariationPointRowMapper());
    }

    @Override
    public List<VariationPoint> getVariationPointsFiltered(Filter filter) {
        if (filter.getDeveloperId() == 0 && filter.getProductReleaseId().equals("0")) {
            return getFeatureVariationPoints(filter.getFeatureName());

        } else if (filter.getDeveloperId() == 0) {
            return jdbcTemplate.query(
                    deltaSqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_PRODUCT),
                    new PreparedStatementSetter() {

                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setObject(1, filter.getFeatureName());
                            preparedStatement.setObject(2, filter.getProductReleaseId());
                        }
                    }, new VariationPointRowMapper());
        } else if (filter.getProductReleaseId().equals("0")) {
            return jdbcTemplate.query(
                    deltaSqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_DEVELOPER),
                    new PreparedStatementSetter() {

                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setObject(1, filter.getFeatureName());
                            preparedStatement.setObject(2, filter.getDeveloperId());
                        }
                    }, new VariationPointRowMapper());
        } else {
            return jdbcTemplate.query(
                    deltaSqlQueries.getProperty(GET_FEATURE_VARIATION_POINTS + QueriesConstants.FILTER_ALL),
                    new PreparedStatementSetter() {

                        public void setValues(PreparedStatement preparedStatement) throws SQLException {
                            preparedStatement.setString(1, filter.getFeatureName());
                            preparedStatement.setInt(2, filter.getDeveloperId());
                            preparedStatement.setString(3, filter.getProductReleaseId());
                        }
                    }, new VariationPointRowMapper());

        }

    }

    @Override
    public List<CustomDiff> getDiffValues(Integer variationPointId) {
        return jdbcTemplate.query(deltaSqlQueries.getProperty(GET_DIFFVALUES),
                new Object[]{variationPointId}, new CustomDiffRowMapper());
    }


    @Override
    public List<VariationPoint> getVariationPointsByFeatureSibling(Integer featureSiblingId) {
        return jdbcTemplate.query(snapshotSqlQueries.getProperty(GET_RELEASE_VP_CONTENT),
                new Object[]{featureSiblingId}, new VariationPointRowMapper());

    }
}
