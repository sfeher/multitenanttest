/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.bluesystem.hdmnew.branch;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sfeher
 */
@Service
public class BranchQueryService {
         
     @Autowired
     BranchEntityRepo repo;
    

    @Transactional(transactionManager = "branchTransactionManager",propagation = Propagation.REQUIRES_NEW)
    public int queryBranch() {        
        List<BranchEntity> list = repo.findAll();        
        return list != null ? list.size() : 0;
    }
    
    
    public void addBranchEntity(BranchEntity m) {
        repo.save(m);       
    }
}
