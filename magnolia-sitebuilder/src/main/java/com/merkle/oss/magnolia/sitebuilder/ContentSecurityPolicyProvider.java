package com.merkle.oss.magnolia.sitebuilder;

import java.util.function.Function;

import com.merkle.oss.magnolia.sitebuilder.annotation.Site;

public interface ContentSecurityPolicyProvider extends Function<Site, String> {}
