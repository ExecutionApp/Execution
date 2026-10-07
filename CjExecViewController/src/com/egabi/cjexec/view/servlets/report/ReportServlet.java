package com.egabi.cjexec.view.servlets.report;


import java.io.IOException;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;

/**
 * Servlet implementation class testServlet
 */
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performAction(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performAction(request, response);
    }

    protected void performAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        JasperPrint fillReport = (JasperPrint)request.getSession().getAttribute("fillRep");
        String reportName = (String)request.getSession().getAttribute("repName");
        
        String format = request.getParameter("format");
        
        OutputStream out = response.getOutputStream();

        try {
            if (format != null && format.equals("docx")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
                response.setContentType("application/msword");

                JRDocxExporter exporter = new JRDocxExporter();

                mirrorLayout(fillReport);

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, fillReport);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

                exporter.exportReport();
            } else   if (format != null && format.equals("xlsx")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportName + "\"");
                response.setContentType("application/vnd.ms-excel");
                JRXlsExporter exporter = new JRXlsExporter();
              //  JRDocxExporter exporter = new JRDocxExporter();

                mirrorLayout(fillReport);

                exporter.setParameter(JRExporterParameter.JASPER_PRINT, fillReport);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);

                exporter.exportReport();
            } else {
                System.out.println("reportName >>>>>>>>> <<<<<<<<<<<<<< " + reportName);
                response.setHeader("Content-Disposition", "inline; filename=\"" + reportName + "\"");
                response.setContentType("application/pdf");
//                response.setCharacterEncoding("UTF-8");

                JasperExportManager.exportReportToPdfStream(fillReport, out);
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

        out.flush();


    }

    public static void mirrorLayout(JasperPrint print) {
        int pageWidth = print.getPageWidth();
        for (Object element : print.getPages()) {
            JRPrintPage page = (JRPrintPage)element;
            mirrorLayout(page.getElements(), pageWidth);
        }
    }

    /**
     * mirror a list of elements
     * @param print
     */
    protected static void mirrorLayout(List<?> elements, int totalWidth) {
        for (Iterator<?> it = elements.iterator(); it.hasNext(); ) {
            JRPrintElement element = (JRPrintElement)it.next();
            if(element instanceof JRTemplatePrintText){
                JRTemplatePrintText textElement = (JRTemplatePrintText)element;
               // textElement.setText(StringUtils.reverseSection(textElement.getText()));
            }
            /*int mirrorX = totalWidth - element.getX() - element.getWidth();
            element.setX(mirrorX);

            if (element instanceof JRPrintFrame) {
                JRPrintFrame frame = (JRPrintFrame)element;
                mirrorLayout(frame.getElements(), frame.getWidth());
            }*/
        }
    }

}
