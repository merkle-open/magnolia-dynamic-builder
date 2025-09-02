package com.merkle.oss.magnolia.appbuilder.builder.detail;

import info.magnolia.ui.api.location.Location;
import info.magnolia.ui.contentapp.detail.ContentDetailSubApp;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.machinezoo.noexception.Exceptions;
import com.merkle.oss.magnolia.appbuilder.action.add.NodeNameProvider;

import jakarta.annotation.Nullable;

public class ExtendedDetailLocation extends ContentDetailSubApp.DetailLocation {
    @Nullable
    private final String nodeType;
    @Nullable
    private final Class<? extends NodeNameProvider> nodeNameProviderClass;

    public ExtendedDetailLocation(final String appName, final String subAppId, final String parameter) {
        super(appName, subAppId, parameter);
        this.nodeType = extractNodeType(parameter);
        this.nodeNameProviderClass = extractNodeNameProviderClassName(parameter);
    }

    public ExtendedDetailLocation(
            final ContentDetailSubApp.DetailLocation location,
            @Nullable final String nodeType,
            @Nullable final Class<? extends NodeNameProvider> nodeNameProviderClass
    ) {
        super(location.getAppName(), location.getSubAppId(), location.getViewType(), location.getNodePath(), location.getVersion());
        this.nodeType = escapeSpecialCharacters(nodeType);
        this.nodeNameProviderClass = nodeNameProviderClass;
        updateParameter();
    }

    public static ExtendedDetailLocation wrap(final Location location) {
        return new ExtendedDetailLocation(location.getAppName(), location.getSubAppId(), location.getParameter());
    }

    @Override
    protected void updateParameter() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getNodePath());
        sb.append(":");
        sb.append(getViewType());
        sb.append(":");
        sb.append(Optional.ofNullable(getVersion()).filter(StringUtils::isNotBlank).orElse("null"));
        sb.append(":");
        sb.append(Optional.ofNullable(nodeType).orElse("null"));
        sb.append(":");
        sb.append(Optional.ofNullable(nodeNameProviderClass).map(Class::getName).orElse(""));
        super.setParameter(sb.toString());
    }

    private String extractNodeType(final String parameter) {
        return Optional
                .of(getParameter(parameter, 3))
                .filter(StringUtils::isNotBlank)
                .orElse(null);
    }

    private Class<? extends NodeNameProvider> extractNodeNameProviderClassName(final String parameter) {
        return Optional
                .of(getParameter(parameter, 4))
                .filter(StringUtils::isNotBlank)
                .map(className -> Exceptions.wrap().get(() -> (Class<? extends NodeNameProvider>)Class.forName(className)))
                .orElse(null);
    }

    @Override
    public void setVersion(final String version) {
        super.setVersion(Objects.equals("null", version) ? "" : version);
    }

    public Optional<String> getNodeType() {
        return Optional.ofNullable(nodeType).map(super::unescapeSpecialCharacters);
    }

    public Optional<Class<? extends NodeNameProvider>> getNodeNameProviderClass() {
        return Optional.ofNullable(nodeNameProviderClass);
    }
}
