package hu.bluesystem.hdmnew.branch;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sfeher
 */
@Repository
public interface BranchEntityRepo extends JpaRepository<BranchEntity, String>, JpaSpecificationExecutor<BranchEntity> {
    
}
