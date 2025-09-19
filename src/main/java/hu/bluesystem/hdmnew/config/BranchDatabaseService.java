/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.bluesystem.hdmnew.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author sfeher
 */
@Service
public class BranchDatabaseService {
    
    private static final Logger LOG = LogManager.getLogger(BranchDatabaseService.class.getName());
    private final DynamicRoutingDataSource routingDataSource;
    
    public BranchDatabaseService(@Qualifier("branchDataSource") DynamicRoutingDataSource routingDataSource) {
        this.routingDataSource = routingDataSource;
    }
    
    public void switchDatabase(DbConfigProperties props) {
        
        String branchKey = getDataSourceKey(props.getName(), props.getDbUsername());
        LOG.info("BranchDatabaseService.switchDatabase={}", branchKey);
        
        if (!routingDataSource.hasDataSource(branchKey)) {
            /*DataSource branchDataSource = DataSourceBuilder.create()
                    .url(props.getDbUrl())
                    .username(props.getDbUsername())
                    .password(props.getDbPassword())
                    .driverClassName("oracle.jdbc.OracleDriver")
                    .build();*/
            HikariDataSource branchDataSource = DataSourceFactory.create(props.getDbUrl(), props.getDbUsername(), props.getDbPassword());
            
            routingDataSource.addDataSource(branchKey, branchDataSource);
        }
        
        DatabaseContextHolder.setBranch(branchKey);
    }
    
    public void clear() {
        DatabaseContextHolder.clearBranch();
    }
    
    public void switchToMain() {
        DatabaseContextHolder.setBranch(AppConfig.INIT_DB);
    }
    
    private String getDataSourceKey(String teamName, String dbUsername) {
        
        return teamName + "_" + dbUsername;
    }
}
