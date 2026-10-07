package com.egabi.cjexec.view.beans.mainrepotrs;

import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.JSFUtils;

import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.math.BigDecimal;

import java.sql.SQLException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;

public class MainReportsBean extends PrintEnabledBean {

    private boolean check;
    private int period = 1;
    private int year = 2020;
    private int folderTypeId;

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPeriod() {
        return period;
    }

    public void setFolderTypeId(int folderTypeId) {
        this.folderTypeId = folderTypeId;
    }

    public int getFolderTypeId() {
        return folderTypeId;
    }

    public static int currentYear() {
        return Calendar.getInstance().getTime().getYear() + 1900;
    }

    private String getMessage(int code) {
        HashMap<Integer, String> lhm = new LinkedHashMap<Integer, String>();
        lhm.put(200, "Ok");
        lhm.put(55,
                "\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u0627\u0644\u0646\u064A\u0627\u0628\u0629 \u0627\u0644\u062C\u0632\u0626\u064A\u0629");
        lhm.put(56, "\u064a\u062c\u0628 \u0627\u062e\u062a\u064a\u0627\u0631 \u062a\u0627\u0631\u064a\u062e");
        lhm.put(57,
                "\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        lhm.put(58,
                "\u0628\u0631\u062C\u0627\u0621 \u0627\u062E\u062A\u064A\u0627\u0631 \u0627\u0644\u0646\u0648\u0639");
        return lhm.get(code);
    }

    private HashMap<Object, Object> validatesearch() {
        ViewObject searchView = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject();
        Row searchRow = searchView.getCurrentRow();
        HashMap<Object, Object> lhm = null;
        lhm = new LinkedHashMap<Object, Object>();
        int id = 0;
        int deg=0;
        if (searchRow.getAttribute("NeyabGozayaa") == null && check) {
            lhm.put(55, getMessage(55));
            return lhm;
        }
        if (searchRow.getAttribute("NeyabGozayaa") != null && check) {
            id = Integer.parseInt(searchRow.getAttribute("NeyabGozayaa").toString());
            deg=3;
        } else if (searchRow.getAttribute("NeyabKolyaa") != null && !check) {
            id = Integer.parseInt(searchRow.getAttribute("NeyabKolyaa").toString());
            deg=39;
        }
        if (searchRow.getAttribute("DateFrom") == null) {
            lhm.put(56, getMessage(56));
            return lhm;
        }

        if (period == 1 && searchRow.getAttribute("DateTo") == null) {
            lhm.put(56, getMessage(56));
            return lhm;
        }

        if (period == 2)
            searchRow.setAttribute("DateTo", null);
        lhm.put("id", id);
        lhm.put("deg", deg);
        lhm.put("DateFrom", searchRow.getAttribute("DateFrom"));
        lhm.put("DateTo", searchRow.getAttribute("DateTo"));
        lhm.put("Year", searchRow.getAttribute("Year"));
        lhm.put(200, getMessage(200));
        return lhm;
    }

    private Object formatDate(Object ob) {
        if (ob != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(new java.util.Date(ob.toString().replace("-", "/")));
        } else
            return null;
    }

    private String searchView(String iterator) {
        HashMap<Object, Object> lhm = validatesearch();
        if (lhm.containsKey(55)) {
            ADFUtil.showMessage(lhm.get(55).toString(), FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (lhm.containsKey(56)) {
            ADFUtil.showMessage(lhm.get(56).toString(), FacesMessage.SEVERITY_ERROR);
            return null;
        }

        RichInputText yearHasr = (RichInputText) JSFUtils.findComponentInRoot("it1");
        if (yearHasr != null)
            System.err.println(yearHasr.getValue() + "----lhm = " + lhm);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ViewObject resultView = ADFUtil.findIterator(iterator).getViewObject();
        System.err.println(lhm.get("id"));
        resultView.setNamedWhereClauseParam("createdDateBind", formatDate(lhm.get("DateFrom")));
        resultView.setNamedWhereClauseParam("createdDateToBind", formatDate(lhm.get("DateTo")));
        if (iterator.equalsIgnoreCase("TotalHasrReportRVO1Iterator"))
        resultView.setNamedWhereClauseParam("entity_degree", lhm.get("deg"));
        BigDecimal errandEntUser =new BigDecimal( ADFContext.getCurrent().getSessionScope().get("errandEntUser")+"");
               if(errandEntUser.compareTo(new BigDecimal(0)) == 1)
                   resultView.setNamedWhereClauseParam("gozeyaIdBind", errandEntUser);
                   else
        resultView.setNamedWhereClauseParam("gozeyaIdBind", lhm.get("id"));
        if (yearHasr != null)
            resultView.setNamedWhereClauseParam("year", yearHasr.getValue());

        resultView.executeQuery();
        System.err.println(resultView.getQuery());
        return null;
    }

    public String searchProcedureReport() {
        System.err.println("validation == ");
        return searchView("TotalProceduresReportRVO1Iterator");
    }

    public String searchTotalHasrReport() {
        return searchView("TotalHasrReportRVO1Iterator");
    }

    public String searchTotalMotalabatReport() {
        return searchView("TotalMotalabatReportRVO1Iterator");
    }

    public String searchHasrIdNumbReport() {
        return searchView("HasrIdNumbReportRVO1Iterator");
    }

    public String searchHasrDetailsReport() {
        return searchView("HasrDetailsReportRVO1Iterator");
    }


    public String searchHasrNotIn() {
        ViewObject searchView = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject();
        Row searchRow = searchView.getCurrentRow();
        int id = 0;
        int criminalType = 0;
        int folderTypeId = 0;
        int entityTypeId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.entityDegree}").toString());
        int fromSequence = 0;
        int toSequence = 0;
        if (!check && entityTypeId == 39) {
            if (searchRow.getAttribute("CriminalTypeFlag") == null) {
                ADFUtil.showMessage(getMessage(58).toString(), FacesMessage.SEVERITY_ERROR);
                return null;
            }

            if (searchRow.getAttribute("FolderTypeIdGenay") != null) {
                folderTypeId = Integer.parseInt(searchRow.getAttribute("FolderTypeIdGenay").toString());
                searchRow.setAttribute("FolderTypeId", null);
            }
        } else {
            searchRow.setAttribute("FolderTypeIdGenay", null);
            if (searchRow.getAttribute("FolderTypeId") != null)
                folderTypeId = Integer.parseInt(searchRow.getAttribute("FolderTypeId").toString());
        }

        if (!check && searchRow.getAttribute("FolderTypeIdGenay") == null && entityTypeId == 39) {
            ADFUtil.showMessage(getMessage(57).toString(), FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (check && (searchRow.getAttribute("OrigEntity") == null || folderTypeId == 0)) {
            ADFUtil.showMessage(getMessage(57).toString(), FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (entityTypeId == 3 &&
            (searchRow.getAttribute("NeyabGozayaa") == null || searchRow.getAttribute("OrigEntity") == null ||
             folderTypeId == 0)) {
            ADFUtil.showMessage(getMessage(57).toString(), FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (!check && entityTypeId == 39) {
            id = Integer.parseInt(searchRow.getAttribute("NeyabKolyaa").toString());
            criminalType = Integer.parseInt(searchRow.getAttribute("CriminalTypeFlag").toString());
        } else {

            if (searchRow.getAttribute("OrigEntity") != null) {
                id = Integer.parseInt(searchRow.getAttribute("OrigEntity").toString());
                criminalType = 1;
            }
        }
        if (searchRow.getAttribute("Year") == null) {
            year = currentYear();
        } else {
            year = Integer.parseInt(searchRow.getAttribute("Year") + "");
        }
        if (searchRow.getAttribute("FromSequence") != null) {
            fromSequence = Integer.parseInt(searchRow.getAttribute("FromSequence") + "");
        }
        if (searchRow.getAttribute("ToSequence") != null) {
            toSequence = Integer.parseInt(searchRow.getAttribute("ToSequence") + "");
        }
        if (isUserGozeya())
            criminalType = 1;
        String appealFlag = "N" ;
            
        if(criminalType == 3 ){
            System.out.println("DDDDDDDDD");
                criminalType = 2 ;
                appealFlag = "Y" ;
            }

        ViewObject resultView = ADFUtil.findIterator("HasrNoInDBRVO1Iterator").getViewObject();
        resultView.setNamedWhereClauseParam("appealFlag", appealFlag);
        resultView.setNamedWhereClauseParam("folderTypeBind", folderTypeId);
        resultView.setNamedWhereClauseParam("yearBind", year);
        resultView.setNamedWhereClauseParam("entityIdBind", id);
        resultView.setNamedWhereClauseParam("criminalType", criminalType);
        if (fromSequence != 0)
        resultView.setNamedWhereClauseParam("fromHasr", fromSequence);
        if (toSequence != 0)
        resultView.setNamedWhereClauseParam("toHasr", toSequence);
        resultView.executeQuery();
        //        System.err.println("entityIdBind" + id + " criminalType = "+
        //                                            criminalType + " folderTypeBind = " + folderTypeId + " yearBind = " + year);
        System.err.println(resultView.getQuery());
        return null;
    }

    public boolean isUserGozeya() {
        int entityTypeId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.entityDegree}").toString());
        if (entityTypeId == 3) {
            ADFUtil.setEL("#{pageFlowScope.isUserGozeya}", "1");
            RichPanelGroupLayout criminalFlag = (RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl10");
            ADFUtil.setAttributeValue("SearchInventoryRVO1Iterator", "CriminalTypeFlag", 1);
            if (criminalFlag != null)
                criminalFlag.setVisible(false);
            return true;
        } else {
            ADFUtil.setEL("#{pageFlowScope.isUserGozeya}", null);
            return false;
        }
    }

    private Object getAttributeValue(String iterator, String attrName) {
        return ADFUtil.findIterator(iterator).getCurrentRow().getAttribute(attrName);
    }

    private void setAttributeValue(String iterator, String attrName, Object value) {
        ADFUtil.findIterator(iterator).getCurrentRow().setAttribute(attrName, value);
    }

    public void folderTypeIdGenayListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null && "3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
            setAttributeValue("SearchInventoryRVO1Iterator", "CriminalTypeFlag", "1");
        }
    }

    @Override
    public void populateParamsMap(HashMap parametersMap) {
        // TODO Implement this method


        parametersMap.put("koleya", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.attributeValue}"));
        parametersMap.put("gozeya", JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.attributeValue}"));
        parametersMap.put("entityIdBind", JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
        parametersMap.put("folderTypeBind", JSFUtils.resolveExpression("#{bindings.FolderTypeId.inputValue}"));
        parametersMap.put("criminalType", JSFUtils.resolveExpression("#{bindings.CriminalTypeFlag.inputValue}"));
        parametersMap.put("yearBind", JSFUtils.resolveExpression("#{bindings.Year.inputValue}"));
        parametersMap.put("fromHasr", JSFUtils.resolveExpression("#{bindings.FromSequence.inputValue}"));
        parametersMap.put("toHasr", JSFUtils.resolveExpression("#{bindings.ToSequence.inputValue}"));

        //       System.out.println(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}"));
        //        System.out.println(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}"));
        //        System.out.println(JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        parametersMap.put("createdDateBind", JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}"));
        parametersMap.put("createdDateToBind",
                          JSFUtils.resolveExpression("#{pageFlowScope.mainReportsBean.period eq '2' ? null : bindings.DateTo.inputValue}"));
        if (JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") != "")
            parametersMap.put("gozeyaIdBind", JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        else
            parametersMap.put("gozeyaIdBind", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
    }

    public String printReportTotalMotalabatReport() {
        String nameOfReport = "TotalMotalabatReport";
        String reportName = "TotalMotalabatReport";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }
    String pathReport = "/u02/config/report/";
    public String printReportHasrIdNumbReport() {
        String nameOfReport = "HasrIdNumbReport";
        String reportName = "HasrIdNumbReport";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }


    public String printReportHasrDetailsReport() {
        String nameOfReport = "HasrDetailsReport";
        String reportName = "HasrDetailsReport";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }


    public String printReportHasrReport() {
        String nameOfReport = "HasrReport";
        String reportName = "HasrReport";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }

    public String printReportTotalProceudresReport() {
        String nameOfReport = "TotalProceudresReport";
        String reportName = "TotalProceudresReport";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }

    public String printReport() {
        String nameOfReport = "HasrNotInDB";
        String reportName = "HasrNotInDB";

        try {
            super.print(pathReport,nameOfReport, "procedures", reportName);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }

}
