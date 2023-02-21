/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.entsearch.analytics.action;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActionFilters;
import org.elasticsearch.action.support.HandledTransportAction;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.transport.TransportService;
import org.elasticsearch.xpack.entsearch.analytics.AnalyticsEventsIntakeService;
public class EventsIntakeTransportAction extends HandledTransportAction<EventsIntakeAction.Request, EventsIntakeAction.Response> {

    @Inject
    public EventsIntakeTransportAction(TransportService transportService, ActionFilters actionFilters) {
        super(EventsIntakeAction.NAME, transportService, actionFilters, EventsIntakeAction.Request::new);
    }

    @Override
    protected void doExecute(Task task, EventsIntakeAction.Request request, ActionListener<EventsIntakeAction.Response> listener) {

        final AnalyticsEventsIntakeService a = new AnalyticsEventsIntakeService();
        a.searchEvent();
        listener.onResponse(new EventsIntakeAction.Response(request.getName()));
    }
}
