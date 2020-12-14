package com.onekin.featurecloud.service;

import com.onekin.featurecloud.dao.FeatureDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl implements GenericService {

    @Autowired
    private FeatureDAO featureDAO;
    @Override
    public List<String> getTangledFeaturesIds(String featureId) {
        return featureDAO.getTangledFeaturesIds(featureId);
    }
}
