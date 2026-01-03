package dev.ohhoonim.policy.model;

import java.util.Map;

public class PolicyAttributeSet {
    private final Map<String, Object> subject;
    private final Map<String, Object> resource;
    private final Map<String, Object> environment;

    public PolicyAttributeSet(Map<String, Object> subject, Map<String, Object> resource,
            Map<String, Object> environment) {
        this.subject = subject;
        this.resource = resource;
        this.environment = environment;
    }

    /**
     * 속성 소스(Subject, Resource, Environment)와 속성 이름(Role, SensitivityLevel 등)을 이용해 값을 조회합니다.
     */
    public Object getAttribute(String source, String name) {
        switch (source) {
            case "Subject":
                return subject.get(name);
            case "Resource":
                return resource.get(name);
            case "Environment":
                return environment.get(name);
            default:
                return null;
        }
    }
}

