package dev.ohhoonim.component.auditing.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AssignableTypeFilter;
import tools.jackson.databind.util.NamingStrategyImpls;

public class EntitySetBeanRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
            @NonNull BeanDefinitionRegistry registry) {
        Map<String, Object> attrs =
                importingClassMetadata.getAnnotationAttributes(BusinessEntityScan.class.getName());
        if (attrs == null) {
            throw new IllegalArgumentException(
                    "@BusinessEntityScan annotation attributes not found.");
        }
        Object basePackagesObj = attrs.get("basePackages");
        if (!(basePackagesObj instanceof String[])) {
            throw new IllegalArgumentException("basePackages attribute must be a String array.");
        }
        String[] basePackages = (String[]) basePackagesObj;

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(Entity.class));

        Set<String> entities = new HashSet<>();

        for (String basePackage : basePackages) {
            for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
                String beanClassName = bd.getBeanClassName();
                if (beanClassName != null) {
                    entities.add(beanClassName);
                }
            }
        }

        RootBeanDefinition beanDefinition =
                new RootBeanDefinition(BusinessEntitiesFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(entities);

        registry.registerBeanDefinition("businessEntityMap", beanDefinition);
    }

    public static class BusinessEntitiesFactoryBean
            implements FactoryBean<Map<String, Class<? extends Entity>>> {

        private final Set<String> entityClassNames;

        public BusinessEntitiesFactoryBean(Set<String> entityClassNames) {
            this.entityClassNames = entityClassNames;
        }

        @Override
        public Map<String, Class<? extends Entity>> getObject() throws Exception {
            Map<String, Class<? extends Entity>> entities = new HashMap<>();
            for (String className : entityClassNames) {
                Class<? extends Entity> clazz = (Class<? extends Entity>) Class.forName(className);
                String entityName = NamingStrategyImpls.SNAKE_CASE.translate(clazz.getSimpleName());

                entities.put(entityName, clazz);
            }
            return entities;
        }

        @Override
        public Class<?> getObjectType() {
            return Map.class;
        }
    }
}
