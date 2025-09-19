package hu.bluesystem.hdmnew.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = AppConfig.BRANCH_DB_PACKAGE, // <-- ide kerülnek a BRANCH repo-k
        entityManagerFactoryRef = "branchEntityManagerFactory",
        transactionManagerRef = "branchTransactionManager"
)
@EntityScan(basePackages = AppConfig.BRANCH_DB_PACKAGE) // <-- ide kerülnek a BRANCH entity-k
public class BranchDbConfig {

    //private final DynamicRoutingDataSource dynamicRoutingDataSource;

/*    public BranchDbConfig(@Qualifier("branchDataSource") DynamicRoutingDataSource dynamicRoutingDataSource) {
        this.dynamicRoutingDataSource = dynamicRoutingDataSource;
    }

    @Bean(name = "branchDataSource")
    public DataSource branchDataSource() {
        return dynamicRoutingDataSource; // dinamikus datasource
    }*/
    
    
    @Bean(name = "branchDataSource")
    public DynamicRoutingDataSource branchDataSource(@Qualifier("mainDataSource") DataSource mainDataSource) {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();
        routingDataSource.addDataSource("MAIN", mainDataSource);
        return routingDataSource;
    }
    
    

    @Bean(name = "branchEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean branchEntityManagerFactory(
            @Qualifier("branchDataSource") DataSource dataSource) {

         Map<String, Object> properties = new HashMap<>();
        // ⬇⬇ Fixált Dialect, így nincs többé metadata hiba
        properties.put("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        
        
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);        
        em.setPackagesToScan(AppConfig.BRANCH_DB_PACKAGE);
        em.setPersistenceUnitName("branch");
        em.setJpaPropertyMap(properties);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "branchTransactionManager")
    public PlatformTransactionManager branchTransactionManager(
            @Qualifier("branchEntityManagerFactory") EntityManagerFactory branchEmf) {
        return new JpaTransactionManager(branchEmf);
    }
}
