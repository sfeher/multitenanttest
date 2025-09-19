package hu.bluesystem.hdmnew.controller;

import hu.bluesystem.hdmnew.branch.BranchEntity;
import hu.bluesystem.hdmnew.branch.BranchQueryService;
import hu.bluesystem.hdmnew.config.BranchDatabaseService;
import hu.bluesystem.hdmnew.config.DbConfigProperties;
import hu.bluesystem.hdmnew.main.MainEntity;

import hu.bluesystem.hdmnew.main.MainQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sfeher
 */
@RequestMapping("/api/v1/test")
@RestController
public class TestController {

    @Autowired
    ApplicationContext ctx;

    @Autowired
    BranchQueryService queryService;

    @Autowired
    BranchDatabaseService branchDatabaseService;

    @Autowired
    MainQueryService mainQueryService;

    @GetMapping
    public ResponseEntity<?> getSize(@RequestParam("schema1") String schema1,
            @RequestParam("schema2") String schema2) {
        String url = ctx.getEnvironment().getProperty("spring.main-datasource.jdbc-url");

        DbConfigProperties db1 = new DbConfigProperties();
        db1.setName(schema1);
        db1.setDbUrl(url);
        db1.setDbUsername(schema1);
        db1.setDbPassword("ber");

        DbConfigProperties db2 = new DbConfigProperties();
        db2.setName(schema2);
        db2.setDbUrl(url);
        db2.setDbUsername(schema2);
        db2.setDbPassword("ber");

        // Query 1 for schema1
        branchDatabaseService.switchDatabase(db1);
        BranchEntity b = new BranchEntity();
        b.setTorzsszam(-1);
        b.setNev("test1");
        queryService.addBranchEntity(b);
        int size1 = queryService.queryBranch();
        
        branchDatabaseService.clear();

        // Query 2 for schema2
        branchDatabaseService.switchDatabase(db2);        
        b = new BranchEntity();
        b.setTorzsszam(-2);
        b.setNev("test2");
        queryService.addBranchEntity(b);
        int size2 = queryService.queryBranch();        
        
        branchDatabaseService.clear();
        
        // Query 3 for main        
        branchDatabaseService.switchToMain();
        int size3 = mainQueryService.queryBranch();
        MainEntity m = new MainEntity();
        m.setTorzsszam(-3);
        m.setNev("test3");
        mainQueryService.addBranchEntity(m);

        return ResponseEntity.ok("OK-> s1:" + size1 + " s2:" + size2 + " s3:" + size3);
    }

}
