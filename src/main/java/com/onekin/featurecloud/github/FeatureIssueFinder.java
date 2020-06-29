package com.onekin.featurecloud.github;

import com.onekin.featurecloud.model.Feature;
import com.onekin.featurecloud.utils.NewickUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FeatureIssueFinder {
    private GHRepository repo;
    private GitHub gitHub;
    @Autowired
    private GHIssueCache ghIssueCache;
    private static final Logger logger = LogManager.getLogger(FeatureIssueFinder.class);

    public FeatureIssueFinder() {
        try {
            gitHub = new GitHubBuilder().withOAuthToken("479bbb6102ce4e8bb2bd7fbe8de48cbd646ad3bc").build();
            repo = gitHub.getRepository("marlinfirmware/marlin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public GHRepository getRepo() {
        return repo;
    }

    public void setRepo(GHRepository repo) {
        this.repo = repo;
    }

    public void setHasFeatureIssues(List<Feature> features) {
        RestTemplate restTemplate = new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer 479bbb6102ce4e8bb2bd7fbe8de48cbd646ad3bc");
            return execution.execute(request, body);
        })).build();
        for (Feature feature : features) {
            feature.setHasIssues(ghIssueCache.getFeatureHasIssues(feature.getId(), restTemplate));


        }
    }


    public List<GHIssue> getFeatureIssues(String featureName) {
        try {
            return ghIssueCache.getFeatureIssues(featureName, gitHub);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
