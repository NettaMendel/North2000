/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public final class Consts {
    
    private Consts() {
        throw new AssertionError();
    }
    
    public static final String CONN_STR = 
            "jdbc:ucanaccess://src/model/Tirgul05_north2000.accdb;COLUMNORDER=DISPLAY";
    
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM TblCustomers";
    public static final String INSERT_CUSTOMER = "{ call qryInsertCustomer(?,?,?,?,?,?,?,?,?); }";
    public static final String UPDATE_CUSTOMER = "{ call qryUpdateCustomer(?,?,?,?,?,?,?,?,?); }";
	public static final String DELETE_CUSTOMER = "{ call qryDeleteCustomer(?); }";
    
    public enum Manipulation {
    	UPDATE, INSERT, DELETE;
    }
}
