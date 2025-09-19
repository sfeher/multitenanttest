package hu.bluesystem.hdmnew.main;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


import hu.bluesystem.hdmnew.main.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author sfeher
 */
@Repository
public interface MainEntityRepo extends JpaRepository<MainEntity, String>, JpaSpecificationExecutor<MainEntity> {
    
}
