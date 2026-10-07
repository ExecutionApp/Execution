package com.egabi.cjexec.view.beans.cjexec.enableopposition;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class CallProcedure {
    public CallProcedure() {
        super();
    }
    int p_FOLDER_RECORD_ID;
    int p_ACTION_TYPE;
    int p_STEP_NUMBER;


    public void setP_FOLDER_RECORD_ID(int p_FOLDER_RECORD_ID) {
        this.p_FOLDER_RECORD_ID = p_FOLDER_RECORD_ID;
    }

    public int getP_FOLDER_RECORD_ID() {
        return p_FOLDER_RECORD_ID;
    }

    public void setP_ACTION_TYPE(int p_ACTION_TYPE) {
        this.p_ACTION_TYPE = p_ACTION_TYPE;
    }

    public int getP_ACTION_TYPE() {
        return p_ACTION_TYPE;
    }

    public void setP_STEP_NUMBER(int p_STEP_NUMBER) {
        this.p_STEP_NUMBER = p_STEP_NUMBER;
    }

    public int getP_STEP_NUMBER() {
        return p_STEP_NUMBER;
    }

    public void callStepsNumberProcedure() throws SQLException{

        Connection conn = null;
        ResourceBundle bundle = ResourceBundle.getBundle("com.egabi.cjexec.view.CjExecViewControllerBundle");
        String jDBC_DATASOURCE = bundle.getString("JDBC_DATASOURCE");

        try {
            // Obtain our environment naming context
            Context initCtx = new InitialContext();
            // Look up our data source
            DataSource ds = (DataSource)initCtx.lookup("CJ_Execution");

            // Allocate and use a connection from the pool   
            conn = ds.getConnection();
            
//            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
//            Establish a connection
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.3.1.62:1521:rrm", "PRE_PRODCJ", "PRE_PRODCJ");
            
            System.out.println(">>>>>>>>>>> connection DB Done");
            CallableStatement pstmt =
                conn.prepareCall("{call EXC_STEPS_NUMBER(?, ?, ?)}");
            pstmt.setInt(1, p_FOLDER_RECORD_ID);
            pstmt.setInt(2, p_ACTION_TYPE);
            pstmt.registerOutParameter(3, Types.INTEGER);
            System.out.println(">>>>>>>>>>> connection Procedure Done");
            System.out.println(">>>>>>>>>>> Set Output Parameters Done");
            pstmt.executeUpdate();
            System.out.println(">>>>>>>>>>> execute Update Done");
            pstmt.executeQuery();
            System.out.println(">>>>>>>>>>> execute Query Done");
            p_STEP_NUMBER = pstmt.getInt(3) ;
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
