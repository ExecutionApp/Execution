package com.egabi.cjexec.view.NTRA;

import java.sql.Connection;
import java.sql.SQLException;

public class NtraTest {
    public static void main(String[] args) {
        NtraClient client = new NtraClient();
        NtraDBHelper op = new NtraDBHelper();
        GeneralResponse response = new GeneralResponse();
        Connection dbConnection = null;
        try {
            dbConnection = op.getConnection();
            response = client.callNtraSubmit(dbConnection, "29009011404754", "01099195097", "kamal");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
