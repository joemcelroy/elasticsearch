///*
// * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
// * or more contributor license agreements. Licensed under the Elastic License
// * 2.0; you may not use this file except in compliance with the Elastic License
// * 2.0.
// */
//
//public class Analytics implements Writeable, ToXContentObject {
//
//    private final String name;
//    /**
//     *
//     * @param name The name of the analytics collection
//     */
//    public Analytics(String name) {
//        this.name = name;
//    }
//
//    public Analytics(StreamInput in) throws IOException {
//        this.name = in.readString();
//    }
//
//    public void writeTo(StreamOutput out) throws IOException {
//        out.writeString(name);
//    }
//
//    public String name() {
//        return name;
//    }
//
//    @Override
//    public String toString() {
//        return Strings.toString(this);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = Objects.hash(name);
//        return result;
//    }
//}
