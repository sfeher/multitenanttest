/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.bluesystem.hdmnew.config;

/**
 *
 * @author sfeher
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String getBranch() {
        return CONTEXT.get();
    }

    public static void setBranch(String branch) {
        CONTEXT.set(branch);
    }

    public static void clearBranch() {
        CONTEXT.remove();
    }
}
