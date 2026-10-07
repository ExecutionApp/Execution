package com.egabi.cjexec.view.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public abstract class PrintEnabledBean {
    protected String jasperFile;
    protected String jrxmlFile;
    protected String reportName;
    protected String reportFolder;
    protected String path;
    String fullContextPath;
    JasperPrint fillReport;
    String format = "pdf";
    //  String realPath;

    public PrintEnabledBean() {
        //    ServletContext ext = (ServletContext) JSFUtils.getFacesContext().getExternalContext().getContext();
        //    String tempPath = ext.getRealPath("");
        //    realPath = tempPath;
        //System.out.println("realPath = tempPath ISSSSSSSSSSSSSSSSS >>>>>>>>>>>>>>>>>>>>>>>>>> " + realPath);
    }

    public PrintEnabledBean(String reportFolder, String reportName) {
        this.reportFolder = reportFolder;
        this.reportName = reportName;
    }

    @PostConstruct
    public void postConstruct() {
        //    System.err.println("realPath >>> " + realPath);
        //    path = realPath + "reports" + File.separator + reportFolder + File.separator;
        //    System.out.println("---- path = " + path);
        //    constructJasperFile();
    }

    public void constructJasperFile() {
        //    jasperFile = path + reportName + ".jasper";
        //    jrxmlFile = path + reportName + ".jrxml";
        //    System.err.println(jrxmlFile);
    }

    public HashMap getParamsMap() {
        HashMap paramsMap = new HashMap();
        populateParamsMap(paramsMap);
        return paramsMap;
    }

    public abstract void populateParamsMap(HashMap parametersMap);

    public void print(ActionEvent e) throws FileNotFoundException, JRException, IOException, Exception {
        postConstruct();
        if (e.getComponent() != null && e.getComponent().getId().contains("docx")) {
            format = "docx";
        } else if (e.getComponent() != null && e.getComponent().getId().contains("xlsx")) {
            format = "xlsx";
        } else {
            format = "pdf";
        }
        print();
    }

    private Connection prepareToPrint() throws Exception {
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pdfFileName = reportName + "-" + toDate + "." + format;
        Connection conn;
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("CJEXECDS");
        conn = ds.getConnection();
        System.err.println(conn.isClosed());
        Locale locale = JSFUtils.getFacesContext().getViewRoot().getLocale();
        TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-3");
        HttpServletRequest request = JSFUtils.getRequest();
        System.err.println("request == " + request.getProtocol());
        String protocol = "";
        if (request.getProtocol().contains("HTTPS") || request.getProtocol().contains("https"))
            protocol = "HTTPS://";
        else
            protocol = "HTTP://";
        String serverName = request.getServerName();
        String port = request.getServerPort() + "";
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder(protocol).append(serverName).append(":").append(port).append(contextPath);
        System.err.println("url == " + url);
        fullContextPath = url.toString();
        System.out.println("fullContextPath:" + fullContextPath);
        //                        String imageUrl = fullContextPath ;
        HashMap parameterMap = getParamsMap();
        parameterMap.put("REPORT_LOCALE", locale);
        parameterMap.put("REPORT_TIME_ZONE", timezone);
        parameterMap.put("REPORT_PATH", path);
        parameterMap.put("CONTEXT_PATH", fullContextPath);
        fillReport = fillReport(conn, parameterMap);
        JSFUtils.setSessionAttribute("fillRep", fillReport);
        JSFUtils.setSessionAttribute("repName", pdfFileName);
        return conn;
    }

    public void print() throws FileNotFoundException, JRException, IOException, Exception {
        postConstruct();
        Connection conn = null;
        try {
            conn = prepareToPrint();
            FacesContext context = JSFUtils.getFacesContext();
            String servletUrl = fullContextPath + "/reportServlet?format=" + format;
            System.out.println("servletUrl:" + servletUrl);
            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, "doPrint(\"" + servletUrl + "\");");

        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }


    private JasperPrint fillReport(Connection conn, HashMap parameterMap) throws JRException, FileNotFoundException {
        JasperPrint fillReport = null;
        System.err.println("jasperFile" + jasperFile);
        FileInputStream reportStream = new FileInputStream(jasperFile);
        fillReport = JasperFillManager.fillReport(reportStream, parameterMap, conn);
        return fillReport;
    }

    public void printReport(ActionEvent e, List dataList) throws FileNotFoundException, JRException, IOException,
                                                                 Exception {
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pdfFileName = reportName + "-" + toDate + ".pdf";
        Locale locale = JSFUtils.getFacesContext().getViewRoot().getLocale();
        TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-3");
        HttpServletRequest request = JSFUtils.getRequest();
        String protocol = "HTTPS://";
        String serverName = request.getServerName();
        String port = request.getServerPort() + "";
        String contextPath = request.getContextPath();
        StringBuilder url = new StringBuilder(protocol).append(serverName).append(":").append(port).append(contextPath);
        String fullContextPath = url.toString();
        //                        String imageUrl = fullContextPath ;
        HashMap parameterMap = getParamsMap();
        parameterMap.put("REPORT_LOCALE", locale);
        parameterMap.put("REPORT_TIME_ZONE", timezone);
        parameterMap.put("CONTEXT_PATH", fullContextPath);
        try {
            JasperPrint fillReport = fillReport(dataList, parameterMap);
            JSFUtils.setSessionAttribute("fillRep", fillReport);
            JSFUtils.setSessionAttribute("repName", pdfFileName);
            FacesContext context = JSFUtils.getFacesContext();
            String servletUrl = fullContextPath + "/reportServlet";
            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, "doPrint(\"" + servletUrl + "\");");
        } finally {
        }
    }


    private JasperPrint fillReport(List dataList, HashMap parameterMap) throws JRException, FileNotFoundException {
        JasperPrint fillReport = null;
        FileInputStream reportStream = new FileInputStream(jasperFile);
        fillReport = JasperFillManager.fillReport(reportStream, parameterMap, new JRBeanCollectionDataSource(dataList));
        return fillReport;
    }

    protected void invokeServlet(JasperPrint fillReport) {
        String url = "/reportServlet";
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            HttpServletRequest req = (HttpServletRequest) JSFUtils.getRequest();
            req.setAttribute("fillRep", fillReport);
            req.setAttribute("repName", reportName);
            context.getExternalContext().dispatch(url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            context.responseComplete();
        }
    }

    public void setReportFolder(String reportFolder) {
        this.reportFolder = reportFolder;
    }

    public String getReportFolder() {
        return reportFolder;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void print(String pathReport ,String nameOfReport, String folderOfReport, String reportName) throws Exception {
        System.err.println("In print >>> ");
        Connection conn = null;
        try {
            conn = prepareToPrint(pathReport,nameOfReport, folderOfReport, reportName);
            System.err.println("fullContextPath = " + fullContextPath);
            FacesContext context = JSFUtils.getFacesContext();
            String servletUrl = fullContextPath + "/reportServlet?format=" + format;
            System.err.println("servletUrl == " + servletUrl);
            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, "doPrint(\"" + servletUrl + "\");");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    
    public void print2(String pathReport ,String nameOfReport, String folderOfReport, String reportName, BigDecimal procedureId, int userId) throws Exception {
        System.err.println("In print >>> ");
        Connection conn = null;
        try {
            System.err.println("userId=print2::::::::::::::::::::::" + userId);
            System.err.println("procedureId=print2::::::::::::::::::::::" +procedureId);
            conn = prepareToPrint2(pathReport,nameOfReport, folderOfReport, reportName, procedureId, userId);
            System.err.println("fullContextPath = " + fullContextPath);
            FacesContext context = JSFUtils.getFacesContext();
            String servletUrl = fullContextPath + "/reportServlet?format=" + format;
            System.err.println("servletUrl == " + servletUrl);
            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, "doPrint(\"" + servletUrl + "\");");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    
    public void print3(String pathReport ,String nameOfReport, String folderOfReport, String reportName, BigDecimal folderId, int userId) throws Exception {
        System.err.println("In print >>> ");
        Connection conn = null;
        try {
            conn = prepareToPrint3(pathReport,nameOfReport, folderOfReport, reportName, folderId, userId);
            System.err.println("fullContextPath = " + fullContextPath);
            FacesContext context = JSFUtils.getFacesContext();
            String servletUrl = fullContextPath + "/reportServlet?format=" + format;
            System.err.println("servletUrl == " + servletUrl);
            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(), ExtendedRenderKitService.class);
            erks.addScript(context, "doPrint(\"" + servletUrl + "\");");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    

    private Connection prepareToPrint(String pathReport,String nameOfReport, String folderOfReport, String reportName) throws Exception {
        System.err.println("Prepare report ");
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pdfFileName = reportName + "-" + toDate + "." + format;
        Connection conn;
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("CJEXECDS");
        conn = ds.getConnection();
        //    folderOfReport = "procedures";
        path = pathReport ;
//            getValueFromDB(conn, 1);
        //    path = "D:\\Work\\cjexec-app\\EXEC_UI\\CjExecViewController\\public_html\\reports\\dafater" ;
        if (!path.endsWith(File.separator))
            path += File.separator;

        System.err.println("path2 = " + path);
        Locale locale = JSFUtils.getFacesContext().getViewRoot().getLocale();
        TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-3");
        HttpServletRequest request = JSFUtils.getRequest();
        String protocol = "";

        if (request.getRequestURL().toString().startsWith("HTTPS") ||
            request.getRequestURL().toString().startsWith("https"))
            protocol = "HTTPS://";
        else
            protocol = "HTTP://";

        String serverName = request.getServerName();
        String port = request.getServerPort() + "";
        String contextPath = request.getContextPath();
        StringBuilder url = new StringBuilder(protocol).append(serverName).append(":").append(port).append(contextPath);
        fullContextPath = url.toString();


        String documentId = "12122";
        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            documentId = ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}").toString();

        HashMap parameterMap = getParamsMap();
        parameterMap.put("REPORT_LOCALE", locale);
        parameterMap.put("REPORT_TIME_ZONE", timezone);
        parameterMap.put("CONTEXT_PATH", fullContextPath);
        parameterMap.put("REPORT_PATH", path);
        
        // omar
        

        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            parameterMap.put("DID", new BigDecimal(documentId));

        fillReport = fillReport(path ,conn, parameterMap, nameOfReport);
        System.err.println("fillReport1 == " + fillReport);
        JSFUtils.setSessionAttribute("fillRep", fillReport);
        JSFUtils.setSessionAttribute("repName", pdfFileName);

        return conn;
    }
    
    
    
    private Connection prepareToPrint2(String pathReport ,String nameOfReport, String folderOfReport, String reportName, BigDecimal procedureId, int userId) throws Exception {
        System.err.println("Prepare report ");
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pdfFileName = reportName + "-" + toDate + "." + format;
        Connection conn;
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("CJEXECDS");
        conn = ds.getConnection();
        //    folderOfReport = "procedures";
        path = pathReport;
// getValueFromDB(conn, 1);
//        //    path = "D:\\Work\\cjexec-app\\EXEC_UI\\CjExecViewController\\public_html\\reports\\dafater" ;
        if (!path.endsWith(File.separator))
            path += File.separator;

//        System.err.println("path2 = " + path);
        Locale locale = JSFUtils.getFacesContext().getViewRoot().getLocale();
        TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-3");
        HttpServletRequest request = JSFUtils.getRequest();
        String protocol = "";

        if (request.getRequestURL().toString().startsWith("HTTPS") ||
            request.getRequestURL().toString().startsWith("https"))
            protocol = "HTTPS://";
        else
            protocol = "HTTP://";

        String serverName = request.getServerName();
        String port = request.getServerPort() + "";
        String contextPath = request.getContextPath();
        StringBuilder url = new StringBuilder(protocol).append(serverName).append(":").append(port).append(contextPath);
        fullContextPath = url.toString();


        String documentId = "12122";
        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            documentId = ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}").toString();

        HashMap parameterMap = new HashMap();;
        parameterMap.put("REPORT_LOCALE", locale);
        parameterMap.put("REPORT_TIME_ZONE", timezone);
        parameterMap.put("CONTEXT_PATH", fullContextPath);
        parameterMap.put("REPORT_PATH", path);
        parameterMap.put("PROCEDURE_ID", procedureId);
        parameterMap.put("userId", userId);
        // omar
        

        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            parameterMap.put("DID", new BigDecimal(documentId));

        fillReport = fillReport(pathReport,conn, parameterMap, nameOfReport);
        System.err.println("fillReport1 == " + fillReport);
        JSFUtils.setSessionAttribute("fillRep", fillReport);
        JSFUtils.setSessionAttribute("repName", pdfFileName);

        return conn;
    }




    private Connection prepareToPrint3(String pathReport ,String nameOfReport, String folderOfReport, String reportName, BigDecimal folderId, int userId) throws Exception {
        System.err.println("Prepare report ");
        String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String pdfFileName = reportName + "-" + toDate + "." + format;
        Connection conn;
        Context initContext = new InitialContext();
        DataSource ds = (DataSource) initContext.lookup("CJEXECDS");
        conn = ds.getConnection();
        //    folderOfReport = "procedures";
        path = pathReport;
    // getValueFromDB(conn, 1);
    //        //    path = "D:\\Work\\cjexec-app\\EXEC_UI\\CjExecViewController\\public_html\\reports\\dafater" ;
        if (!path.endsWith(File.separator))
            path += File.separator;

    //        System.err.println("path2 = " + path);
        Locale locale = JSFUtils.getFacesContext().getViewRoot().getLocale();
        TimeZone timezone = TimeZone.getTimeZone("Etc/GMT-3");
        HttpServletRequest request = JSFUtils.getRequest();
        String protocol = "";

        if (request.getRequestURL().toString().startsWith("HTTPS") ||
            request.getRequestURL().toString().startsWith("https"))
            protocol = "HTTPS://";
        else
            protocol = "HTTP://";

        String serverName = request.getServerName();
        String port = request.getServerPort() + "";
        String contextPath = request.getContextPath();
        StringBuilder url = new StringBuilder(protocol).append(serverName).append(":").append(port).append(contextPath);
        fullContextPath = url.toString();


        String documentId = "12122";
        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            documentId = ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}").toString();

        HashMap parameterMap = new HashMap();;
        parameterMap.put("REPORT_LOCALE", locale);
        parameterMap.put("REPORT_TIME_ZONE", timezone);
        parameterMap.put("CONTEXT_PATH", fullContextPath);
        parameterMap.put("REPORT_PATH", path);
        parameterMap.put("FOLDER_ID", folderId);
        parameterMap.put("userId", userId);
        // omar
        

        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null)
            parameterMap.put("DID", new BigDecimal(documentId));

        fillReport = fillReport(pathReport,conn, parameterMap, nameOfReport);
        System.err.println("fillReport1 == " + fillReport);
        JSFUtils.setSessionAttribute("fillRep", fillReport);
        JSFUtils.setSessionAttribute("repName", pdfFileName);

        return conn;
    }

    

    private JasperPrint fillReport(String pathReport ,Connection conn, HashMap parameterMap, String nameOfReport) throws JRException,
                                                                                                      FileNotFoundException {
        System.err.println("In fill report ");
        JasperPrint fillReport = null;
        String reportPath = pathReport;
//            getValueFromDB(conn, 1);
        //    String reportPath = "D:\\Work\\cjexec-app\\EXEC_UI\\CjExecViewController\\public_html\\reports\\dafater" ;
//       reportPath = "D:\\Egabi\\App\\ExecutionApp\\ExecutionReports\\report" ;
        System.err.println("reportPath = " + reportPath);
        if (!reportPath.endsWith(File.separator))
            reportPath += File.separator;

        System.err.println("reportPath = " + reportPath);
        String jasperFileRport = reportPath + nameOfReport + ".jasper";
        jrxmlFile = reportPath + nameOfReport + ".jrxml";
        System.err.println("jrxmlFile = " + jrxmlFile);
        JSFUtils.addToPageFlowScope("jrxmlFile", jrxmlFile);
        FileInputStream reportStream = new FileInputStream(jrxmlFile);
        System.err.println("reportStream = " + reportStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        System.err.println("jasperReport == " + jasperReport);
        fillReport = JasperFillManager.fillReport(jasperReport, parameterMap, conn);

        return fillReport;
    }

    // This Method return value of Data from table cj_config
    // conn --> Datasource Connection
    private String getValueFromDB(Connection conn, int id) {
        String sql = "select value from cj_config where id  = " + id + "";
        Object value = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null ;
        try  
        {
              pstmt = conn.prepareStatement(sql, 0);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                value = rs.getObject(1);
            }
            
        } catch (SQLException e) {
            System.err.println("ERRROR >>> " + e.getMessage());
        }
        finally{
                try {
                rs.close();
                pstmt.close();
//                conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
      
        return value == null ? null : value + "";
    }
}
