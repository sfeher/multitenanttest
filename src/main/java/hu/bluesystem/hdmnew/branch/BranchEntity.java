/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.bluesystem.hdmnew.branch;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author sfeher
 */
@Entity
@Table(name = "BranchEntity")
public class BranchEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "TORZSSZAM")
    Integer torzsszam;

    @Basic(optional = true)
    @Column(name = "NEV")
    String nev;

    public Integer getTorzsszam() {
        return torzsszam;
    }

    public void setTorzsszam(Integer torzsszam) {
        this.torzsszam = torzsszam;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

}
