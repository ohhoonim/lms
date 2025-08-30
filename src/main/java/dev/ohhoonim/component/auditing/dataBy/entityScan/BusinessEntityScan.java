package dev.ohhoonim.component.auditing.dataBy.entityScan;

import java.lang.annotation.*;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EntitySetBeanRegistrar.class)
public @interface BusinessEntityScan {
    String[] basePackages();
}
