package dev.ohhoonim.component.datasource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Configuration
public class DataSourceConfig {
    // // @ConfigurationProperties(prefix = "spring.datasource.main")
    // @Bean
    // public DataSourceProperties mainDataSourceProperties() {
    //     return new DataSourceProperties();
    // }

    // // @ConfigurationProperties(prefix = "spring.datasource.tgt")
    // @Bean
    // public DataSourceProperties tgtDataSourceProperties() {
    //     return new DataSourceProperties();
    // }

    // // jndi 사용할 경우

    // // @Bean("mainDataSource")
    // // public DataSource mainDataSource(DataSourceProperties mainDataSourceProperties) {
    // //     return mainDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    // // }

    // // @Bean("tgtDataSource")
    // // public DataSource tgtDataSource(DataSourceProperties tgtDataSourceProperties) {
    // //     return new JndiDataSourceLookup().getDataSource(edwDataSourceProperties.getJndiName());
    // // }

    // @Bean("mainDataSource")
    // public DataSource mainDataSource(DataSourceProperties mainDataSourceProperties) {
    //     return mainDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    // }

    // @Bean("edwDataSource")
    // public DataSource tgtDataSource(DataSourceProperties edwDataSourceProperties) {
    //     return edwDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    // }

    // @Bean
    // @Primary
    // public DataSource routingDataSource(
    //         @Qualifier("mainDataSource") DataSource mainDataSource,
    //         @Qualifier("tgtDataSource") DataSource tgtDataSource) {
    //     AbstractRoutingDataSource routingDataSource = new AbstractRoutingDataSource() {
    //         @Override
    //         protected Object determineCurrentLookupKey() {
    //             if (TransactionSynchronizationManager.isActualTransactionActive() &&
    //                     !TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
    //                 return DataSourceType.MAIN;
    //             }
    //             return RoutingDatabaseContextHolder.getClientDatabase();
    //         }
    //     };
    //     Map<Object, Object> targetDataSource = new HashMap<>();
    //     targetDataSource.put(DataSourceType.MAIN, mainDataSource);
    //     targetDataSource.put(DataSourceType.TGT, tgtDataSource);
    //     routingDataSource.setTargetDataSources(targetDataSource);
    //     routingDataSource.setDefaultTargetDataSource(mainDataSource);
    //     routingDataSource.afterPropertiesSet();
    //     return new LazyConnectionDataSourceProxy(routingDataSource);
    // }

    // @Bean
    // public PlatformTransactionManager jdbcTransactionManager(DataSource routingDataSource) {
    //     return new DataSourceTransactionManager(routingDataSource);
    // }

    // // @Bean
    // // @Primary
    // // public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    // //     return new JpaTransactionManager(entityManagerFactory);
    // // }
}
