/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.application.analytics;

import org.elasticsearch.ResourceNotFoundException;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.regex.Regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class AnalyticsCollectionResolver {
    private final IndexNameExpressionResolver indexNameExpressionResolver;

    @Inject
    public AnalyticsCollectionResolver(IndexNameExpressionResolver indexNameExpressionResolver) {
        this.indexNameExpressionResolver = indexNameExpressionResolver;
    }

    public AnalyticsCollection collection(ClusterState state, String collectionName) {
        AnalyticsCollection collection = new AnalyticsCollection(collectionName);

        if (state.metadata().dataStreams().containsKey(collection.getEventDataStream()) == false) {
            throw new ResourceNotFoundException("no such analytics collection [{}] ", collectionName);
        }

        return collection;
    }

    public List<AnalyticsCollection> collections(ClusterState state, String... expressions) {
        // Listing data streams that are matching the analytics collection pattern.
        List<String> dataStreams = indexNameExpressionResolver.dataStreamNames(
            state,
            IndicesOptions.lenientExpandOpen(),
            AnalyticsTemplateRegistry.EVENT_DATA_STREAM_INDEX_PATTERN
        );

        Map<String, AnalyticsCollection> collections = dataStreams.stream()
            .map(AnalyticsCollection::fromDataStreamName)
            .filter(analyticsCollection -> matchAnyExpression(analyticsCollection, expressions))
            .collect(Collectors.toMap(AnalyticsCollection::getName, Function.identity()));

        List<String> missingCollections = Arrays.stream(expressions)
            .filter(not(Regex::isMatchAllPattern))
            .filter(not(Regex::isSimpleMatchPattern))
            .filter(not(collections::containsKey))
            .toList();

        if (missingCollections.isEmpty() == false) {
            throw new ResourceNotFoundException("no such analytics collection [{}] ", missingCollections.get(0));
        }

        return new ArrayList<>(collections.values());
    }

    private boolean matchExpression(String collectionName, String expression) {
        if (Strings.isNullOrEmpty(expression)) {
            return false;
        }

        if (Regex.isMatchAllPattern(expression)) {
            return true;
        }

        if (Regex.isSimpleMatchPattern(expression)) {
            return Regex.simpleMatch(expression, collectionName);
        }

        return collectionName.equals(expression);
    }

    private boolean matchAnyExpression(String collectionName, String... expressions) {
        return Arrays.stream(expressions).anyMatch(expression -> matchExpression(collectionName, expression));
    }

    private boolean matchAnyExpression(AnalyticsCollection collection, String... expressions) {
        return matchAnyExpression(collection.getName(), expressions);
    }
}
