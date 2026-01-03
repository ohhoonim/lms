package dev.ohhoonim.policy.model;

import java.util.List;
import java.util.Map;

public class PolicyRuleSet {
    private final String ruleId;
    private final String effect; // Permit 또는 Deny
    private final String targetAction; // Crud.Read 등
    private final List<Map<String, Object>> conditions;

    public PolicyRuleSet(String ruleId, String effect, String targetAction,
            List<Map<String, Object>> conditions) {
        this.ruleId = ruleId;
        this.effect = effect;
        this.targetAction = targetAction;
        this.conditions = conditions;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getEffect() {
        return effect;
    }

    public String getTargetAction() {
        return targetAction;
    }

    public List<Map<String, Object>> getConditions() {
        return conditions;
    }
}
