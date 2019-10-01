package com.onekin.featurecloud;

import com.onekin.featurecloud.config.MvcConfig;
import com.onekin.featurecloud.controller.ControllerMarker;
import com.onekin.featurecloud.dao.DaoMarker;
import com.onekin.featurecloud.service.ServiceMarker;
import com.onekin.featurecloud.repository.RepositoryMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {ControllerMarker.class, DaoMarker.class, ServiceMarker.class, RepositoryMarker.class})
@ImportResource({"classpath:META-INF/sql/queries.xml","classpath:META-INF/sql/snapshot-queries.xml"})
@Import(MvcConfig.class)
public class FeatureCloudApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FeatureCloudApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(FeatureCloudApplication.class, args);
    }
}
