package com.egabi.cjexec.view.NTRA;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class NtraDBHelper {
    private static String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    //    private static String JDBC_URL = "jdbc:oracle:thin:@10.3.1.62:1521/rrm";

    private static String JDBC_URL = "jdbc:oracle:thin:@10.21.38.8:29183/cj";
    //    private static String JDBC_URL_LOCAL = "jdbc:oracle:thin:@localhost:1521:orcl";


    //    private static String JDBC_USER = "cj_egabi";
    private static String JDBC_USER = "PRODCJ_26_11_2019";
    //    private static String JDBC_PASSWORD = "cj_egabi";
    private static String JDBC_PASSWORD = "welcome1";
    private static Driver driver = null;
    
    public static synchronized Connection getConnection() throws SQLException {
            //// this connection is valid if you make test for web services
        Connection conn = null;
        InitialContext ic = null;
        javax.sql.DataSource ds = null;
        
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("CJEXECDS");
            conn = ds.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } 

        return conn;
    }

//    public static synchronized Connection getConnection() throws SQLException {
//
//        try {
//            Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
//            driver = (Driver) jdbcDriverClass.newInstance();
//            DriverManager.registerDriver(driver);
//
//        } catch (Exception e) {
//            System.out.println("Failed to initialise JDBC driver");
//            e.printStackTrace();
//        }
//        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
//    }
    
    
    public NtraPPOConfig getNtraPPOConfig(Connection dbConnection) {
        NtraPPOConfig ntraConfig = new NtraPPOConfig();
        String sqlUrl = " SELECT value FROM cj_config where key = 'NTRA_ENDPOINT_URL'";
        String sqlUserName = " SELECT value FROM cj_config where key = 'NTRA_USERNAME'";
        String sqlPassword = " SELECT value FROM cj_config where key = 'NTRA_PASSWORD'";
//        String sqlOriginatingChannel =
//            " SELECT property_value FROM bpm_system_properties where property = 'TAWTHEEQ_ORIGINATING_CHANNEL'";

        PreparedStatement preparedStmt = null;
//        java.sql.Connection dbConnection = null;
        ResultSet rs = null;
        try {
//            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(sqlUrl);
                
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) != null) {
                        ntraConfig.setNtraUrl(String.valueOf(rs.getObject(1)));
                    }
                }

                preparedStmt = dbConnection.prepareStatement(sqlUserName);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) != null) {
                        ntraConfig.setNtraUserName(String.valueOf(rs.getObject(1)));
                    }
                }

                preparedStmt = dbConnection.prepareStatement(sqlPassword);
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) != null) {
                        ntraConfig.setNtraPassword(String.valueOf(rs.getObject(1)));
                    }
                }

//                preparedStmt = dbConnection.prepareStatement(sqlOriginatingChannel);
//                rs = preparedStmt.executeQuery();
//                while (rs.next()) {
//                    if (rs.getObject(1) != null) {
//                        tawtheeqInfo.setOriginatingChannel(String.valueOf(rs.getObject(1)));
//                    }
//                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
//                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return ntraConfig;
    }


    public String checkNtraIntegration(Connection dbConnection) {
        String ntraIntegrationFlag = "";
        String sqlUrl = " SELECT value FROM cj_config where key = 'NTRA_INTEGRATION_FLAG'";
        
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(sqlUrl);
                
                rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    if (rs.getObject(1) != null) {
                        ntraIntegrationFlag = String.valueOf(rs.getObject(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return ntraIntegrationFlag;
    }

    public void insertNtraLog(Connection dbConnection , String apiName, String token, String verficationResult, String comments,
                                      String userName, String requestDate,
                                      String responseCode, String responseMessage, String requestBody,
                                      String responseBody,String nationalId,String mobileNo) {
        PreparedStatement preparedStmt = null;
//        java.sql.Connection dbConnection = null;
        long lastTransactionId = 0;
        String query =
            " INSERT INTO ntra_log (\n" +
            "    api_name,token,VERFICATION_RESULT,COMMENTS,user_name,\n" +
            "    request_date,response_date,response_code,response_message,REQUEST_BODY,RESPONSE_BODY,NATIONAL_ID,MOBILE_NO\n" +
            ") VALUES (?,?,?,?,?,TO_DATE(?, 'dd-MM-yyyy hh24:mi:ss'),sysdate,?,?,?,?,?,?)";
        try {
//            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(query);
                preparedStmt.setObject(1, apiName);
                preparedStmt.setObject(2, token);
                preparedStmt.setObject(3, verficationResult);
                preparedStmt.setObject(4, comments);
                preparedStmt.setObject(5, userName);
                preparedStmt.setObject(6, requestDate);
                preparedStmt.setObject(7, responseCode);
                preparedStmt.setObject(8, responseMessage);
                preparedStmt.setObject(9, requestBody);
                preparedStmt.setObject(10, responseBody);
                preparedStmt.setObject(11, nationalId);
                preparedStmt.setObject(12, mobileNo);
                // execute the preparedstatement
                preparedStmt.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            if (dbConnection != null) {
                try {
                    preparedStmt.close();
//                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    
//    public void updateCitizenRequestNtraResult(Connection dbConnection, String requestId , String verficationResult,String ntraComments) {
//        PreparedStatement pstmt1 = null;
//        String sqlU = "";
//        try {
//            sqlU = "update CITIZEN_REQUESTS set NTRA_VERFICATION_RESULT =  ?  ,NTRA_COMMENTS = ? where ID = ?";
//            pstmt1 = dbConnection.prepareStatement(sqlU);
//
//            pstmt1.setObject(1, verficationResult);
//            pstmt1.setObject(2, ntraComments);
//            pstmt1.setObject(3, requestId);
//            pstmt1.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        } finally {
//            if (pstmt1 != null) {
//                try {
//                    pstmt1.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public void insertCitizenRequestNtraResult(Connection dbConnection ,String requestId , String nationalId,String mobileNo ,String verficationResult ,String userName,int applicantType) {
        PreparedStatement preparedStmt = null;
        String query =
            "INSERT INTO citizen_requests_ntra_results (\n" + 
            "    request_id,\n" + 
            "    national_id,\n" + 
            "    mobile_no,\n" + 
            "    created_by,VERFICATION_RESULT,APLLICANT_TYPE\n" + 
            ") VALUES (\n" + 
            "    ?,?,?,?,?,?\n" + 
            ") ";
        try {
    //            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(query);
                preparedStmt.setObject(1, requestId);
                preparedStmt.setObject(2, nationalId);
                preparedStmt.setObject(3, mobileNo);
                preparedStmt.setObject(4, userName);
                preparedStmt.setObject(5, verficationResult);
                preparedStmt.setObject(6, applicantType);
                // execute the preparedstatement
                preparedStmt.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            if (dbConnection != null) {
                try {
                    preparedStmt.close();
    //                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    public void insertSecUsersNtraResult(Connection dbConnection ,String userId , String nationalId,String mobileNo ,String verficationResult ,String userName) {
        PreparedStatement preparedStmt = null;
        String query =
            "INSERT INTO sec_users_ntra_results (\n" + 
            "    national_id,\n" + 
            "    mobile_no,\n" + 
            "    created_by,\n" + 
            "    verfication_result,\n" + 
            "    user_id\n" + 
            ") VALUES (\n" + 
            "    ?,?,?,?,?\n" + 
            ")";
        try {
    //            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(query);
                preparedStmt.setObject(1, nationalId);
                preparedStmt.setObject(2, mobileNo);
                preparedStmt.setObject(3, userName);
                preparedStmt.setObject(4, verficationResult);
                preparedStmt.setObject(5, userId);
                
                // execute the preparedstatement
                preparedStmt.execute();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            if (dbConnection != null) {
                try {
                    preparedStmt.close();
    //                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
    
    
    public boolean checkNtraPerviousCall(Connection dbConnection,String mobileNo ,String nationalId) {
        boolean check = false;
        int count = -100;
        String Query =
            "select count(*) from citizen_requests_ntra_results\n" + 
            "where mobile_no = ? \n" + 
            "and national_id = ?\n" + 
            "and verfication_result = 'T'";

        PreparedStatement preparedStmt = null;
//        java.sql.Connection dbConnection = null;
        ResultSet rs = null;
        try {
//            dbConnection = createConnection();
            if (dbConnection != null) {
                preparedStmt = dbConnection.prepareStatement(Query);
                preparedStmt.setObject(1, mobileNo);
                preparedStmt.setObject(2, nationalId);
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
        } finally {
            if (dbConnection != null) {
                try {
                    rs.close();
                    preparedStmt.close();
//                    dbConnection.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return check;
    }

    

}
