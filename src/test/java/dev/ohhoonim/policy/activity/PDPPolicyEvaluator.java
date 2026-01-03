package dev.ohhoonim.policy.activity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import dev.ohhoonim.policy.model.PolicyAttributeSet;
import dev.ohhoonim.policy.model.PolicyRuleSet;

public class PDPPolicyEvaluator {

    // ------------------------------------
    // 1. 인메모리 데이터 정의 (PIP 수집 데이터)
    // ------------------------------------

    // Subject, Resource, Environment 데이터를 단순화하여 첫 번째 샘플 데이터만 사용합니다.
    private static final Map<String, Object> SUBJECT_DATA =
            Map.of("UserID", "U001", "Role", "Manager", "Department", "Sales", "Location",
                    "Internal Network", "ActionType", "Crud.Read", "IsAuthenticated", true);

    private static final Map<String, Object> RESOURCE_DATA =
            Map.of("MenuCode", "M_SALES_RPT", "ResourceType", "Report", "SensitivityLevel",
                    "Confidential", "OwnerID", "U001", "RequiredPermissionTag", "SALES_REPORT_R");

    private static final Map<String, Object> ENVIRONMENT_DATA =
            Map.of("TimeOfDay", "10:30:00", "DayOfWeek", "Weekday", "NetworkType", "InternalLAN",
                    "DeviceType", "Desktop", "IsBusinessHours", true, "IsInternalIP", true);

    // ------------------------------------
    // 2. 정책 규칙 정의
    // ------------------------------------

    private static final List<PolicyRuleSet> POLICY_RULES = Arrays.asList(
            // R001: Permit - 내부 관리자가 Confidential 리소스를 업무 시간에 내부 IP로 읽기 접근
            new PolicyRuleSet("R001", "Permit", "Crud.Read",
                    Arrays.asList(
                            Map.of("Attribute", "Subject.Role", "Function", "string-equal", "Value",
                                    "Manager"),
                            Map.of("Attribute", "Resource.SensitivityLevel", "Function",
                                    "string-equal", "Value", "Confidential"),
                            Map.of("Attribute", "Environment.IsInternalIP", "Function",
                                    "boolean-equal", "Value", true),
                            Map.of("Attribute", "Environment.IsBusinessHours", "Function",
                                    "boolean-equal", "Value", true))),
            // R002: Deny - 비업무 시간 접근 거부 규칙
            new PolicyRuleSet("R002", "Deny", "Crud.Read", Arrays.asList(Map.of("Attribute",
                    "Environment.IsBusinessHours", "Function", "boolean-equal", "Value", false))));

    // ------------------------------------
    // 3. PDP 핵심 평가 로직
    // ------------------------------------

    private static boolean evaluateCondition(PolicyAttributeSet attributeSet,
            Map<String, Object> condition) {
        String fullAttr = (String) condition.get("Attribute");
        String function = (String) condition.get("Function");
        Object expectedValue = condition.get("Value");

        // Attribute 파싱 (예: "Subject.Role" -> "Subject", "Role")
        String[] parts = fullAttr.split("\\.");
        String source = parts[0];
        String attrName = parts[1];

        // 실제 값 조회
        Object actualValue = attributeSet.getAttribute(source, attrName);

        // 단순화된 평가 (string-equal, boolean-equal만 지원)
        if ("string-equal".equals(function)) {
            return String.valueOf(actualValue).equals(String.valueOf(expectedValue));
        } else if ("boolean-equal".equals(function)) {
            // Java Boolean 객체는 직접 비교
            return actualValue instanceof Boolean && actualValue.equals(expectedValue);
        }

        return false; // 지원하지 않는 함수
    }

    public static String evaluatePolicy(PolicyAttributeSet attributeSet,
            List<PolicyRuleSet> policyRules) {
        String requestedAction = (String) attributeSet.getAttribute("Subject", "ActionType");

        System.out.println("\n--- PDP 정책 평가 시작 ---");

        for (PolicyRuleSet rule : policyRules) {
            // 1. Target Matching: ActionType 일치 확인
            if (!rule.getTargetAction().equals(requestedAction)) {
                continue;
            }

            boolean isMatch = true;
            System.out.println("\n-> 규칙 " + rule.getRuleId() + " '" + rule.getEffect() + "' 검토 시작");

            // 2. Condition Evaluation: 모든 조건 충족 여부 확인 (AND 조건)
            for (Map<String, Object> condition : rule.getConditions()) {
                boolean conditionResult = evaluateCondition(attributeSet, condition);

                String conditionStr = String.format("%s %s %s", condition.get("Attribute"),
                        condition.get("Function"), condition.get("Value"));

                System.out.printf("  - 조건 '%s': %s%n", conditionStr,
                        conditionResult ? "충족" : "미충족");

                if (!conditionResult) {
                    isMatch = false;
                    break;
                }
            }

            // 3. Final Decision (Rule Combining: first-applicable)
            if (isMatch) {
                System.out.println("**-> 규칙 " + rule.getRuleId() + "가 완전히 충족됨. 최종 결정: "
                        + rule.getEffect() + "**");
                return rule.getEffect();
            }
        }

        System.out.println("--- 모든 규칙 검토 완료 ---");
        return "NotApplicable";
    }

    // ------------------------------------
    // 4. 시뮬레이션 실행 (main 메서드)
    // ------------------------------------

    public static void main(String[] args) {
        // 4.1. PIP 역할: 속성 통합 (Attribute Set 생성)
        PolicyAttributeSet attributeSet =
                new PolicyAttributeSet(SUBJECT_DATA, RESOURCE_DATA, ENVIRONMENT_DATA);

        System.out.println("### 속성 집합 수집 완료 (PIP 역할) ###");
        System.out.println("요청 주체: " + SUBJECT_DATA.get("Role") + "/" + SUBJECT_DATA.get("UserID"));
        System.out.println("요청 자원: " + RESOURCE_DATA.get("MenuCode") + " ("
                + RESOURCE_DATA.get("SensitivityLevel") + ")");
        System.out.println("요청 환경: " + ENVIRONMENT_DATA.get("NetworkType") + " (업무시간: "
                + ENVIRONMENT_DATA.get("IsBusinessHours") + ")");

        // 4.2. PDP 역할: 평가 실행
        String finalDecision = evaluatePolicy(attributeSet, POLICY_RULES);

        // 4.3. PEP 역할: 결정 적용
        System.out.println("\n--- PEP 접근 제어 ---");
        if ("Permit".equals(finalDecision)) {
            System.out.println("✅ 접근 허용: 요청이 정책을 통과했습니다.");
        } else {
            System.out.println("❌ 접근 거부: 최종 결정 " + finalDecision + " (규칙 미충족 또는 Deny)");
        }
    }
}
