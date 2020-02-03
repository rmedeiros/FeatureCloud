package com.onekin.featurecloud.service;

import com.onekin.featurecloud.FeatureCloudApplication;
import com.onekin.featurecloud.model.SnapshotMetada;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=FeatureCloudApplication.class)
@DataJpaTest
public class SnapshotServiceTest {

    @TestConfiguration
    static class SnapshotServiceTestContextConfiguration {

        @Bean
        public SnapshotService snapshotService() {
            return new SnapshotServiceImpl();
        }
    }

    @Autowired
    private SnapshotService snapshotService;


    @Test
    public void getMetadataText(){
        SnapshotMetada snapshotMetada = snapshotService.getMetadataBox();
        assert (snapshotMetada.getFeatures()>0);
        assert(snapshotMetada.getVariationPoints()>0);
        assert(snapshotMetada.getVariableCode()>0);
    }
}
