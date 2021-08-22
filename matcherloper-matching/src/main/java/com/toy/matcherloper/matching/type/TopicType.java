package com.toy.matcherloper.matching.type;

public enum TopicType {
    MATCHING("matching");

    private final String token;

    TopicType(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
