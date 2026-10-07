package com.egabi.common;


import com.egabi.model.SMSConfiguration;

import com.egabi.model.SmsData;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class SmsDBOperation {
    
    public Connection createConnection() throws NamingException, SQLException {
        Connection conn = null;
        InitialContext ic = new InitialContext();
        javax.sql.DataSource ds = (DataSource) ic.lookup("CJEXECDS");
        conn = ds.getConnection();
        return conn;
    }

    public SMSConfiguration getSmsConfig() {
        SMSConfiguration smsConfig = new SMSConfiguration();
        Connection dbConnection = null; 
        String sqlUrl = " SELECT value FROM cj_config where key = 'SMS_ENDPOINT_URL'";
        String sqlSender = " SELECT value FROM cj_config where key = 'SMS_SENDER'";
        String sqlUser = " SELECT value FROM cj_config where key = 'SMS_USER2'";
        String sqlPassword = " SELECT value FROM cj_config where key = 'SMS_PASSWORD2'";
        //String sqlInitiatePassword = " SELECT value FROM cj_config where key = 'INITIAL_CJ_PASSWORD'";

        PreparedStatement preparedStmt = null;
        PreparedStatement preparedStmtSender = null;
        PreparedStatement preparedStmtUser = null;
        PreparedStatement preparedStmtPassword = null;
        //PreparedStatement preparedStmtInitiatePassword = null;
        
        ResultSet rs = null;
        ResultSet rsSender = null;
        ResultSet rsUser = null;
        ResultSet rsPassword = null;
        //ResultSet rsInitiatePassword = null;
        try {
            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(sqlUrl);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) != null) {
                        smsConfig.setSmsEndPointUrl(String.valueOf( rs.getObject(1)));
                    }
                }
                
                preparedStmtSender = dbConnection.prepareStatement(sqlSender);
                rsSender = preparedStmtSender.executeQuery();
                while (rsSender.next()) {
                    if (rsSender.getObject(1) != null) {
                        smsConfig.setSmsSender(String.valueOf( rsSender.getObject(1)));
                    }
                }
                
                preparedStmtUser = dbConnection.prepareStatement(sqlUser);
                rsUser = preparedStmtUser.executeQuery();
                while (rsUser.next()) {
                    if (rsUser.getObject(1) != null) {
                        smsConfig.setSmsUser(String.valueOf( rsUser.getObject(1)));
                    }
                }
                
                preparedStmtPassword = dbConnection.prepareStatement(sqlPassword);
                rsPassword = preparedStmtPassword.executeQuery();
                while (rsPassword.next()) {
                    if (rsPassword.getObject(1) != null) {
                        smsConfig.setSmsPassword( String.valueOf( rsPassword.getObject(1)));
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
                    
                    rsSender.close();
                    preparedStmtSender.close();
                    
                    rsUser.close();
                    preparedStmtUser.close();
                    
                    rsPassword.close();
                    preparedStmtPassword.close();
                    
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return smsConfig;
    }
    
    public String firstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }
    
    public List<SmsData> readSmsQueueB() {
        List<SmsData> smsList = new ArrayList<SmsData>();
        String batchNo = "";
        int batchSize = 50;
        if (checkSmsQueueNotBatched(batchSize)) {
            batchNo = getBatchNo();
            updateSmsQueueBNo(batchNo,batchSize);
            smsList = readSmsQueue(batchNo);
    //            System.out.println("ntraRequestList : "+ntraRequestList.size());
        }
        return smsList;
    }
    
    private List<SmsData> readSmsQueue(String batchNo) {
        List<SmsData> smsList = new ArrayList<SmsData>();
        SmsData smsData = new SmsData();
        Connection conn = null;
        String mobileNo = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = createConnection();
            String sql = "SELECT ID , mobile_no , SMS_BODY \n" + 
            "from EXC_SMS_QUEUE \n" + 
            "where batch_no =?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, batchNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                smsData.setId(rs.getString(1));
                mobileNo = rs.getString(2) ;
                if (!firstTwo(mobileNo).equals("+2")){
                    mobileNo = "+2" + mobileNo; 
                }
                smsData.setMobileNo(mobileNo);
                smsData.setSmsBody(rs.getString(3)); 
                
                smsList.add(smsData);
                smsData = new SmsData();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    rs.close();
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return smsList;
    }

    private boolean checkSmsQueueNotBatched(int batchSize) {
        boolean check = false;
        int count = -100;
        String Query =
            "SELECT COUNT(*) from EXC_SMS_QUEUE \n" + "where IS_SEND = 'N' \n" +
            "and rownum <= ?";

        PreparedStatement preparedStmt = null;
        java.sql.Connection dbConnection = null;
        ResultSet rs = null;
        try {
            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(Query);
                preparedStmt.setObject(1, batchSize);
                rs = preparedStmt.executeQuery();
                rs.next();
                count = rs.getInt(1);
                if (count > 0) {
                    check = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return check;
    }

    private String getBatchNo() {
        String seqNo = "";
        String Query = "SELECT EXC_SMS_BATCH_NO_SEQ.NEXTVAL FROM SYS.DUAL";
        PreparedStatement preparedStmt = null;
        java.sql.Connection dbConnection = null;
        ResultSet rs = null;
        try {
            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(Query);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    seqNo = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return seqNo;
    }

    private void updateSmsQueueBNo(String batchNo ,int batchSize) {
            PreparedStatement pstmt1 = null;
            String sqlU = "";
            Connection conn = null;
            try {
                conn = createConnection();
                sqlU = "update EXC_SMS_QUEUE set batch_no = ? \n" + 
                "where is_send = 'N' \n" + 
                "and rownum <= ?";
                
                pstmt1 = conn.prepareStatement(sqlU);
                pstmt1.setObject(1, batchNo);
                pstmt1.setObject(2, batchSize);
                pstmt1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();

            } catch (NamingException e) {
                e.printStackTrace();
        } finally {
                if (pstmt1 != null) {
                    try {
                        pstmt1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
      
    public void updateSMSRequestResult(String id,String smsResponse) {
        System.err.println("smsResponse--->" + smsResponse);
            PreparedStatement pstmt1 = null;
            String sqlU = "";
            Connection conn = null;
            try {
                conn = createConnection();
                sqlU = "update EXC_SMS_QUEUE set SMS_RESPONSE = ? , is_send = 'Y' \n" + 
                "where id = ?";
                
                pstmt1 = conn.prepareStatement(sqlU);
                pstmt1.setObject(1, smsResponse);
                pstmt1.setObject(2, id);
                pstmt1.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();

            } catch (NamingException e) {
                e.printStackTrace();
        } finally {
                if (pstmt1 != null) {
                    try {
                        pstmt1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    
    
    
    
}
