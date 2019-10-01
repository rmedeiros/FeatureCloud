package com.onekin.featurecloud;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ReleaseDeltaControllerTest.class,
        SnapshotControllerTest.class
})
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = FeatureCloudApplication.class)
@AutoConfigureMockMvc
public class FeatureCloudTest {
}
