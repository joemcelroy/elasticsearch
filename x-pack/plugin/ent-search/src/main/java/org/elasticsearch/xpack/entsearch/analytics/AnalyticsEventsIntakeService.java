/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.entsearch.analytics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.StringMapMessage;

public class AnalyticsEventsIntakeService {

    private static final Logger logger = LogManager.getLogger(AnalyticsEventsIntakeService.class);
    private static final Marker AUDIT_MARKER = MarkerManager.getMarker("org.elasticsearch.xpack.entsearch.analytics");

    private class AnalyticsEventBuilder {
        private final StringMapMessage logEntry;
        AnalyticsEventBuilder() {
            logEntry = new StringMapMessage();
        }

        AnalyticsEventBuilder with(String key, String value) {
            if (value != null) {
                logEntry.with(key, value);
            }
            return this;
        }

        void build() {
            logger.info(AUDIT_MARKER, logEntry);
        }
    }

    public void searchEvent() {
        new AnalyticsEventBuilder().with("query", "puggles").build();
    }
}
