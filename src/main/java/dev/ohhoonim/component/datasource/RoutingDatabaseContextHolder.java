package dev.ohhoonim.component.datasource;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class RoutingDatabaseContextHolder {
    private static final ThreadLocal<DataSourceType> CONTEXT = ThreadLocal.withInitial(() -> DataSourceType.MAIN);

    private RoutingDatabaseContextHolder() {

    }

    public static void set(DataSourceType type) {
        CONTEXT.set(type);
    }

    public static DataSourceType getClientDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
