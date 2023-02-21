/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */
package org.elasticsearch.xpack.entsearch.analytics.action;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.ActionType;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.xcontent.ParseField;
import org.elasticsearch.xcontent.ToXContent;
import org.elasticsearch.xcontent.ToXContentObject;
import org.elasticsearch.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Objects;

public class EventsIntakeAction extends ActionType<EventsIntakeAction.Response> {

    public static final EventsIntakeAction INSTANCE = new EventsIntakeAction();
    public static final String NAME = "cluster:hello_world";

    private EventsIntakeAction() {
        super(NAME, Response::new);
    }

    public static class Request extends ActionRequest {
        private final String name;

        public Request(String name) {
            this.name = name;
        }

        public Request(StreamInput in) throws IOException {
            this(in.readString());
        }

        public String getName() {
            return name;
        }

        @Override
        public ActionRequestValidationException validate() {
            return null;
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeString(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EventsIntakeAction.Request request = (EventsIntakeAction.Request) o;
            return name.equals(request.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    public static class Response extends ActionResponse implements ToXContentObject {

        public static final ParseField MESSAGE_FIELD = new ParseField("message");
        private final String name;

        public Response(String name) {
            this.name = name;
        }

        public Response(StreamInput in) throws IOException {
            this(in.readString());
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            out.writeString(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EventsIntakeAction.Response response = (EventsIntakeAction.Response) o;
            return name.equals(response.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public XContentBuilder toXContent(XContentBuilder builder, ToXContent.Params params) throws IOException {
            builder.startObject();
            builder.field(MESSAGE_FIELD.getPreferredName(), getMessage());
            builder.endObject();
            return builder;
        }

        private String getMessage() {
            return "Hello, !";
        }
    }
}
