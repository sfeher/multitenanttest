package hu.bluesystem.hdmnew.config;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = AppConfig.MAIN_DB_PACKAGE, // <-- ide kerülnek a MAIN repo-k
        entityManagerFactoryRef = "mainEntityManagerFactory",
        transactionManagerRef = "mainTransactionManager"
)
@EntityScan(basePackages = AppConfig.MAIN_DB_PACKAGE) // <-- ide kerülnek a MAIN entity-k
public class MainDbConfig {

    @Bean(name = "mainDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.main-datasource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mainEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            @Qualifier("mainDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(AppConfig.MAIN_DB_PACKAGE);
        em.setPersistenceUnitName("main");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean(name = "mainTransactionManager")
    @Primary
    public PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManagerFactory") EntityManagerFactory mainEmf) {
        return new JpaTransactionManager(mainEmf);
    }
}
