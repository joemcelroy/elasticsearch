
/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.entsearch.analytics.rest;

import org.elasticsearch.client.internal.node.NodeClient;
import org.elasticsearch.xpack.entsearch.analytics.action.EventsIntakeAction;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.RestToXContentListener;

import java.util.List;
import java.util.function.Supplier;

import static org.elasticsearch.rest.RestRequest.Method.POST;

public class RestEventsIntakeAction extends BaseRestHandler {
    @Override
    public String getName() {
        return "events_intake_action";
    }

    @Override
    public List<Route> routes() {
        return List.of(new Route(POST, "/_behavorial_analytics/events"));
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) {
        EventsIntakeAction.Request actionRequest = new EventsIntakeAction.Request("test");
        return channel -> client.execute(EventsIntakeAction.INSTANCE, actionRequest, new RestToXContentListener<>(channel));
    }
}
