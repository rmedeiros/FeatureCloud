package com.onekin.tagcloud;

import com.onekin.tagcloud.config.MvcConfig;
import com.onekin.tagcloud.controller.ControllerMarker;
import com.onekin.tagcloud.dao.DaoMarker;
import com.onekin.tagcloud.repository.RepositoryMarker;
import com.onekin.tagcloud.service.ServiceMarker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {ControllerMarker.class, DaoMarker.class, ServiceMarker.class, RepositoryMarker.class})
@ImportResource({"classpath:META-INF/sql/queries.xml"})
@Import(MvcConfig.class)
public class SplTagCloudApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SplTagCloudApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(SplTagCloudApplication.class, args);
    }
}
