package com.onekin.featurecloud;

import com.onekin.featurecloud.config.CacheConfig;
import com.onekin.featurecloud.config.CacheEventLogger;
import com.onekin.featurecloud.config.MvcConfig;
import com.onekin.featurecloud.controller.ControllerMarker;
import com.onekin.featurecloud.dao.DaoMarker;
import com.onekin.featurecloud.github.GitHubMarker;
import com.onekin.featurecloud.repository.RepositoryMarker;
import com.onekin.featurecloud.service.ServiceMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;


@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {ControllerMarker.class, DaoMarker.class, ServiceMarker.class, RepositoryMarker.class, GitHubMarker.class})
@ImportResource({"classpath:META-INF/sql/queries.xml", "classpath:META-INF/sql/snapshot-queries.xml"})
@Import({CacheConfig.class, MvcConfig.class, CacheEventLogger.class})
public class FeatureCloudApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(FeatureCloudApplication.class, args);
    }
}
