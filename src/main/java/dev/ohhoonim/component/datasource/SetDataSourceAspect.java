package dev.ohhoonim.component.datasource;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;

/**
 * DB 변환 aop
 */
// @EnableAspectJAutoProxy
// @Aspect
// @Component
public class SetDataSourceAspect {
    /**
     * datasource 지정
     */

    @Before("@annotation(*.SetDataSource) && @annotation(target)")
    public void setDataSource(DataSourceType target) {
        RoutingDatabaseContextHolder.set(target);
    }

    /**
     * 기본 datasource로 원복
     */
    @After("@annotation(*.SetDataSource)")
    public void resetDataSource() {
        RoutingDatabaseContextHolder.clear();
    }
}
