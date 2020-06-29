package com.onekin.featurecloud.github;

import org.junit.Test;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.PagedIterable;

import java.io.IOException;
import java.util.List;

public class FeatureIssueFinderTest {
    @Test
    public void constructorTest() {
        FeatureIssueFinder test = new FeatureIssueFinder();
        assert (!test.getRepo().equals(null));
    }

    @Test
    public void issueFinderTest() throws IOException {
        FeatureIssueFinder test = new FeatureIssueFinder();
    }

}
