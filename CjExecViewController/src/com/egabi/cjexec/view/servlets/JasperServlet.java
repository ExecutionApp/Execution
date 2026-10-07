package com.egabi.cjexec.view.servlets;


import java.io.File;
import java.io.IOException;


import java.math.BigDecimal;

import java.sql.Connection;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.sql.DataSource;


import net.sf.jasperreports.engine.JasperRunManager;

import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRProperties;


@WebServlet(name = "JasperServlet", urlPatterns = { "/jasperservlet" })
public class JasperServlet extends HttpServlet {

    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    String reportPath = "E:/Reports/";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        try {
            conn = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("inside report servlet");
        response.setContentType(CONTENT_TYPE);

        try {
//            Map parameters = new HashMap();
            
//            parameters.put("INV_Report_ID", new BigDecimal(request.getParameter("investigationReportId")));
            Map<String, Object> parameters = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            runReport(request, response, parameters, conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getDataSourceConnection(String dataSourceName) throws Exception {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(dataSourceName);
        return ds.getConnection();
    }

    private Connection getConnection() throws Exception {
        return getDataSourceConnection("CJ_Execution");
    }


    public void runReport(HttpServletRequest request, HttpServletResponse response, Map parameters, Connection conn) {
        try {
            boolean isWindows = true;
            boolean isUnix = false;

            ServletContext ext = getServletContext();
            String tempPath = ext.getRealPath(File.separator);
            String realPath = null;

            if (isWindows)
                realPath = tempPath;
            else if (isUnix){
                realPath = tempPath + File.separator;
            }
            String reportName = request.getParameter("report");
            
            
            
            File reportFile = new File(reportPath + reportName + ".jasper"); 
            
            JRProperties.setProperty(JRQueryExecuterFactory.QUERY_EXECUTER_FACTORY_PREFIX + "plsql", "com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");

            byte[] bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters , conn);

            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);

            outStream.flush();
            outStream.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
