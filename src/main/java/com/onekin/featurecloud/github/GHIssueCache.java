package com.onekin.featurecloud.github;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueSearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class GHIssueCache {

    private static final Logger logger = LogManager.getLogger(GHIssueCache.class);

    @Cacheable(value = "feature-issues", key = "#feature")
    public List<GHIssue> getFeatureIssues(String feature, GitHub gitHub) throws IOException {
        GHIssueSearchBuilder issueSearchBuilder = gitHub.searchIssues();
        feature = feature.replaceAll("_", " ");
        issueSearchBuilder = issueSearchBuilder.q("\"" + feature + "\" repo:marlinfirmware/marlin is:issue is:open");
        PagedIterable<GHIssue> issues = issueSearchBuilder.list();
        return issues.toList();

    }

    @Cacheable(value = "feature-hasissues", key = "#feature")
    public boolean getFeatureHasIssues(String feature, RestTemplate restTemplate) {
        try {
            Map<String, String> issues = restTemplate.getForObject("https://api.github.com/search/issues?q=\"" + feature + "\" repo:marlinfirmware/marlin is:issue is:open", Map.class);
            logger.info("Got issues info of feature: " + feature);
            Object total = issues.get("total_count");
            if ((int) total > 0) {
                return true;
            } else {
                return false;
            }
        } catch (HttpClientErrorException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}