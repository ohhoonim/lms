package dev.ohhoonim.component.auditing.model;

import java.util.List;

public non-sealed interface MasterCode extends DataBy {

    public String groupCode();

    public String masterCode();

    public String langCode();

    public static <E extends MasterCode> E enumCode(
            Class<E> enumCode,
            String masterCode) {
        return List.of(enumCode.getEnumConstants()).stream()
                .filter(constant -> masterCode.equals(constant.masterCode()))
                .findFirst()
                .orElse(null);
    }
}
