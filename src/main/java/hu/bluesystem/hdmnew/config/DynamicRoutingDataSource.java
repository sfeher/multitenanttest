/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.bluesystem.hdmnew.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author sfeher
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger LOG = LogManager.getLogger(DynamicRoutingDataSource.class.getName());
    private final Map<Object, Object> dataSourceMap = new ConcurrentHashMap<>();
    private final Set<String> keyMap = ConcurrentHashMap.newKeySet();

    public DynamicRoutingDataSource() {
        setLenientFallback(false);
        setTargetDataSources(Collections.unmodifiableMap(dataSourceMap));
        afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        LOG.info("DynamicRoutingDataSource.determineCurrentLookupKey()");
        String key = DatabaseContextHolder.getBranch();
        if(key==null) {
            key="MAIN";
        }
        LOG.info("Routing to database key = {}", key);
        if (key == null) {
            throw new IllegalStateException("No branch key found in context");
        }
        return key;
    }

    public synchronized void addDataSource(String key, DataSource dataSource) {
        dataSourceMap.put(key, dataSource);
        keyMap.add(key);
        setTargetDataSources(Collections.unmodifiableMap(dataSourceMap));
        afterPropertiesSet();
    }

    public boolean hasDataSource(String key) {
        return keyMap.contains(key);
    }

    public synchronized void removeDataSource(String key) {
        DataSource ds = (DataSource) dataSourceMap.remove(key);
        keyMap.remove(key);
        setTargetDataSources(new HashMap<>(dataSourceMap));
        afterPropertiesSet();
        if (ds instanceof HikariDataSource hikariDataSource) {
            hikariDataSource.close(); // zárjuk a poolt
        }
    }
}
