package com.egabi.cjexec.view.beans.reports;

import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;

import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;

public class PrintDafatertBean extends PrintEnabledBean {
    private String HABS_GHARAMA_ITERATOR = "HasrDataReportView1Iterator";
    private String MAIN_REPORT_ITERATOR = "MainReports1Iterator";
    private String SEARCH_PARAMS_ITERATOR = "SearchInventoryRVO1Iterator";
    private boolean check;
    private RichPanelGroupLayout niyabaKolya;
    private RichPanelGroupLayout niyabaGozayaa;
    private RichPanelGroupLayout originEntity;
    private RichPanelGroupLayout mamoreya;
    private RichSelectOneChoice niyabaGozayaaAncillary;

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }
    /* Global Methods */
    private Object getAttributeValue(String iterator, String attrName) {
        return ADFUtil.findIterator(iterator).getCurrentRow().getAttribute(attrName);
    }

    private void setAttributeValue(String iterator, String attrName, Object value) {
        ADFUtil.findIterator(iterator).getCurrentRow().setAttribute(attrName, value);
    }

    private BigDecimal getBigDecimalValueFromObject(Object value) {
        BigDecimal theVal = value != null ? new BigDecimal(value.toString()) : null;
        return theVal;
    }

    public void showErrorMessageForComponentById(String message, String id) {
        FacesContext.getCurrentInstance().addMessage(ADFUtil.findComponentInRoot(id).getClientId(),
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

    private String getNavigationName() {
        String navigationName =
            ADFUtil.evaluateEL("#{sessionScope.NavigationName}") == null ? null :
            ADFUtil.evaluateEL("#{sessionScope.NavigationName}").toString();
        return navigationName;
    }
    /* /Global Methods */

    @Override
    public void populateParamsMap(HashMap parametersMap) {
        String navigationName = getNavigationName();
        String text = "\u062f\u0641\u062a\u0631 \u062d\u0635\u0631 \u0623\u062d\u0643\u0627\u0645";
        String daftar = "\u062f\u0641\u062a\u0631";

        if (navigationName != null && navigationName.equals("HasrPage")) {
            parametersMap.put("folderTypeId", 2);
            parametersMap.put("folderTypeName", text + " \u0627\u0644\u062d\u0628\u0633");
            parametersMap.put("KOLYA_ID", ADFUtil.getAttributeValue("SearchInventoryRVO1Iterator", "NeyabKolyaa"));
            parametersMap.put("QUERY", " AND " + ADFUtil.getFromPageFlowScope("Query"));
        } else if (navigationName != null && navigationName.equals("HasrGharamaReport")) {
            parametersMap.put("folderTypeId", 3);
            parametersMap.put("folderTypeName", text + " \u0627\u0644\u063a\u0631\u0627\u0645\u0629");
            parametersMap.put("KOLYA_ID", ADFUtil.getAttributeValue("SearchInventoryRVO1Iterator", "NeyabKolyaa"));
            parametersMap.put("QUERY", " AND " + ADFUtil.getFromPageFlowScope("Query"));
        } else if (navigationName != null && navigationName.equals("Moarda")) {
            parametersMap.put("procedureTypeId", 2);
            parametersMap.put("procedureTypeName", daftar + " \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0627\u062a");
        } else if (navigationName != null && navigationName.equals("Estanaf")) {
            parametersMap.put("procedureTypeId", 1);
            parametersMap.put("procedureTypeName", daftar + " \u0627\u0644\u0627\u0633\u062a\u0626\u0646\u0627\u0641");
        } else if (navigationName != null && navigationName.equals("MoardaEstanafia")) {
            parametersMap.put("procedureTypeId", 9);
            parametersMap.put("procedureTypeName",
                              daftar + " \u0645\u0639\u0627\u0631\u0636\u0629 " +
                              "\u0625\u0633\u062a\u0626\u0646\u0627\u0641\u064a\u0629");
        } else if (navigationName != null && navigationName.equals("EatradAmrGenaya")) {
            parametersMap.put("procedureTypeId", 14);
            parametersMap.put("procedureTypeName",
                              daftar + " \u0627\u0644\u0627\u0639\u062a\u0631\u0627\u0636 \u0639\u0644\u064a " +
                              "\u0627\u0644\u0627\u0648\u0627\u0645\u0631 \u0627\u0644\u062c\u0646\u0627\u0626\u064a\u0629");
        } else if (navigationName != null && navigationName.equals("TaanReportPage")) {
            parametersMap.put("procedureTypeId", 20);
            parametersMap.put("procedureTypeName",
                              daftar + " \u0627\u0644\u0637\u0639\u0648\u0646 " +
                              "\u0628\u0627\u0644\u0646\u0642\u0636");
        }
        
        if (navigationName != null && "AbroadRequestReport".equalsIgnoreCase(navigationName)) {
            parametersMap.put("mode", ADFUtil.getAttributeValue(SEARCH_PARAMS_ITERATOR, "SearchTypeWafedeen"));
            parametersMap.put("KOLYA_ID", ADFUtil.getAttributeValue("SearchInventoryRVO1Iterator", "NeyabKolyaa"));
        } else if (navigationName != null && "MotalbatReportPage".equalsIgnoreCase(navigationName)) {
            parametersMap.put("year", ADFUtil.getAttributeValue("CalimYear"));
        }else {
            parametersMap.put("year", ADFUtil.getAttributeValue("Year"));
        }
        parametersMap.put("gozeyaId", ADFUtil.getAttributeValue("NeyabGozayaa"));
        parametersMap.put("dateFrom", ADFUtil.getAttributeValue("DateFrom"));
        parametersMap.put("dateTo", ADFUtil.getAttributeValue("DateTo"));
    }
    String pathReport = "/u02/config/report/";

    public String printDaftarHasrReport() {
        this.reportName = "HasrDafater";

        try {
            print(pathReport,"HasrDafater", "procedures", reportName);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }

    public String printDaftarProceduresReport() {
        this.reportName = "ProceduresDafater";

        try {
            print(pathReport,"ProceduresDafater", "procedures", reportName);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }

    public String printDaftarMotalbatReport() {
        this.reportName = "MotalbatDafater";

        try {
            print(pathReport,"MotalbatDafater", "procedures", reportName);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }

    private RichPanelGroupLayout getCriminalTypeComp() {
        RichPanelGroupLayout criminalTypeComp = (RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl8");
        return criminalTypeComp;
    }

    public boolean isUserGozaya() {
        int entityTypeId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.entityDegree}").toString());
        if (entityTypeId == 3) {
            getCriminalTypeComp().setVisible(false);
            return true;
        } else {
            getCriminalTypeComp().setVisible(true);
            return false;
        }
    }

    public boolean isVisable() {
        if (isUserGozaya() || !getNavigationName().equals("HasrPage")) {
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag", "1");
        }
        isGharama();
        return true;
    }

    public boolean isGharama() {
        //        String gozayType = null;
        //        Object gozayKoly = getAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag");
        //        if(gozayKoly != null){
        //        gozayType = (String) gozayKoly;
        RichSelectOneChoice criminalFlag = (RichSelectOneChoice) ADFUtil.findComponentInRoot("soc4");
        RichPanelGroupLayout dafterFlag = (RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl10");
        if (!getNavigationName().equals("HasrPage") || isUserGozaya()) {
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag", "1");
            criminalFlag.setDisabled(false);
//            dafterFlag.setVisible(false);
            return true;
        } else {
            criminalFlag.setDisabled(false);
//            dafterFlag.setVisible(true);
            return false;
        }
    }

    private boolean messageRequiredValidation() {
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa") == null) {
            showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644  \u0627\u0644\u0646\u064A\u0627\u0628\u0629 \u0627\u0644\u062C\u0632\u0626\u064A\u0629",
                                             "soc2");
            return false;
        }
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity") == null) {
            showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0642\u0633\u0645 \u0627\u0644\u0634\u0631\u0637\u0629",
                                             "soc3");
            return false;
        }
        return true;
    }

    public boolean isSuper() {
        return true;
    }

    public void searchMain(ActionEvent ae) {
        System.err.println(isUserGozaya());
       
        if (isUserGozaya() && ADFUtil.evaluateEL("#{sessionScope.errandF}").toString().equalsIgnoreCase("N")) {
            if (!messageRequiredValidation())
                return;
        }

        //        if (!isUserGozaya() && getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag") == null && getNavigationName().equals("HasrPage")) {
        //            showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u0642\u0636\u064A\u0629 \u0644\u0627\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0628\u062D\u062B",
        //                                             "soc4");
        //            return;
        //        }


        BigDecimal toggle = new BigDecimal (1);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "TypeToggle")) != null)
          toggle =  getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "TypeToggle")) ;
        System.out.println("toggle  is "+toggle);
        BigDecimal startNum = new BigDecimal (0);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) != null)
            startNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) ;
        BigDecimal endNum = new BigDecimal (0);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) != null)
            endNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) ;

        String navigationName = getNavigationName(), bcDegreeSpecId = null , attendance = null;
        BigDecimal folderType = null, casYear = null, policeOrNyabaaId = null; 
        if (navigationName != null && navigationName.equals("MainReport")) {
            if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "FolderTypesId") == null && !isUserGozaya()) {
                showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u062C\u062F\u0648\u0644",
                                                 "soc5");
                return;
            }
            if (!isUserGozaya() && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false")){
                folderType =
                    getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR,
                                                                   "FolderTypesId").toString());
                System.out.println("criminalType     is "+getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag"));
                if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("1"))
                bcDegreeSpecId = "3,4,8";
            } else{
                if (JSFUtils.resolveExpression("#{bindings.FolderTypesId.inputValue}") != null)
                folderType = new BigDecimal (JSFUtils.resolveExpression("#{bindings.FolderTypesId.inputValue}")+"");
        
        }}
            else if (navigationName != null && navigationName.equals("MainReportScreen")) {
            folderType = new BigDecimal(3);
            if (!isUserGozaya() && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false")){
                bcDegreeSpecId = "3,4,8";
            }
        }
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag") != null ){
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("3"))
        bcDegreeSpecId = "7,8";
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("2"))
        bcDegreeSpecId = "1";
    }
        casYear = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "Year"));
        Date dateFrom = (Date) getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateFrom");
        Date dateTo = (Date) getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateTo");
        String criminalType = "1";
             if( getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag") != null)
                 criminalType = (String) getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag");
             
        String status = "1";
             if( getAttributeValue(SEARCH_PARAMS_ITERATOR, "Status") != null)
                 status = (String) getAttributeValue(SEARCH_PARAMS_ITERATOR, "Status");
             
        Object val = getAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag");
        if(isUserGozaya()){
            val = "Y";
        }
        if (val != null && "Y".equalsIgnoreCase(val + "")) {
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity"));
        } else {
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabKolyaa"));
        }
        if (ADFUtil.evaluateEL("#{sessionScope.errandF}").toString().equalsIgnoreCase("Y") && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false"))
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa"));
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya") != null && !getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya").equals(""))
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya"));
     
        if(ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}") != null)
            if(ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}").toString().length()>0)
            bcDegreeSpecId = ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}").toString(); ;
        
        System.out.println("***"+ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}"));
           if(ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}") != null)
               if(ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}").toString().length()>0)
               attendance = ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}").toString(); 
           if (criminalType.equalsIgnoreCase("3"))
               criminalType = 2+"" ;
        JSFUtils.addToSession("PFlAG", new BigDecimal (1));
        executeMainView(folderType, casYear, policeOrNyabaaId, dateFrom, dateTo, criminalType,status, bcDegreeSpecId , attendance , toggle , startNum , endNum);
        JSFUtils.addToSession("PFlAG", new BigDecimal (0));
    }
    
    /* reports  business */
    private void executeMainView(BigDecimal folderType, BigDecimal casYear, BigDecimal policeId, Date dateFrom,
                                 Date dateTo, String criminal,String status , String bcDegreeSpecId ,String attendance , BigDecimal toggle , BigDecimal startSeq , BigDecimal endSeq) {
        ViewObjectImpl vo = (ViewObjectImpl) ADFUtil.findIterator(MAIN_REPORT_ITERATOR).getViewObject();
        StringBuilder where = new StringBuilder();
        StringBuilder jasperQuery = new StringBuilder();
        long daysDiff =0 ;
        where.append(" YEAR = " + casYear + " ");
        if (criminal != null)
        where.append(" AND CRIMINAL_TYPE = " + criminal);
        
        if (status.equalsIgnoreCase("2"))
            where.append(" and Execution_date is not null"); 
        if (status.equalsIgnoreCase("3"))
            where.append(" and Execution_date is  null"); 
        if (bcDegreeSpecId != null){
            if(folderType.compareTo(new BigDecimal(49))==1 && bcDegreeSpecId.length()>1)
                System.out.println("#####");
            else
        where.append(" AND BC_DEGREE_SPEC_ID IN (" + bcDegreeSpecId + ")"); 
        
        }
        if (attendance != null)
        where.append(" AND attendance IN (" + attendance + ")"); 
        if (dateFrom != null && dateTo != null && toggle.compareTo(new BigDecimal(1))==0){
            java.util.Date dateFrom2;
            java.util.Date dateTo2;
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            long diff  = 0;
            
             try {
                dateFrom2 = sdformat.parse(dateFrom+"");
                dateTo2 = sdformat.parse(dateTo+"");
             diff = dateTo2.getTime() - dateFrom2.getTime() ;
                 daysDiff= (diff/(1000*60*60*24)) ;
                 System.out.println("diff  is&&&&&&"+ diff);
                 System.out.println("daysDiff  is"+ daysDiff);
                 
                 
                 
                 
             } catch (ParseException e) {
            }

          
              
              if ( daysDiff <= 31 && daysDiff >= 0 )
              {
                      where.append("  AND HEARING_DATE BETWEEN TO_DATE('" + dateFrom + "', 'yyyy/mm/dd') AND TO_DATE('" + dateTo +
                                   "' , 'yyyy/mm/dd')+1   ");
                  
                  }
              else
                  JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
            
         
            
        }
        BigDecimal numDiff = endSeq.subtract(startSeq);
        
        if (  toggle.compareTo(new BigDecimal(2))==0 && (endSeq.intValue() > 0 && numDiff.intValue() >=0 && numDiff.intValue() <=300))
        {
                where.append("  AND SEQUENCE_NUMBER >= "+startSeq+" and SEQUENCE_NUMBER <= " +endSeq);
            }
        where.append("  AND  FOLDER_TYPE = " + folderType);
        where.append("   AND  POLICE_STATION_ENTITY_ID = " + policeId);
        System.err.println(where);
        
       
        jasperQuery.append(" FOLDER.YEAR = " + casYear + " ");
        jasperQuery.append(" AND FOLDER.CRIMINAL_TYPE = " + criminal);
        if (dateFrom != null && dateTo != null){
            
            
            
            jasperQuery.append("  AND CIRCLES.HEARING_DATE BETWEEN TO_DATE('" + dateFrom +
                               "', 'yyyy/mm/dd') AND TO_DATE('" + dateTo + "' , 'yyyy/mm/dd')+1   ");
            
        }
        jasperQuery.append("  AND  FOLDER.FOLDER_TYPE = " + folderType);
        jasperQuery.append("   AND  FOLDER.POLICE_STATION_ENTITY_ID = " + policeId);
        
        if (( daysDiff <= 31 && daysDiff >= 0 && numDiff.intValue() >=0 && numDiff.intValue() <=300) || ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString().equalsIgnoreCase("hn"))
        
        { 
            JSFUtils.addToSession("PFlAG", new BigDecimal (1));
        vo.setWhereClause(where.toString());
        vo.executeQuery();
           // JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        }

        JSFUtils.addToPageFlowScope("Query", jasperQuery.toString());
      //  JSFUtils.addToSession("PFlAG", new BigDecimal (0));
    }
    
    public void search(ActionEvent ae) {
        System.err.println(isUserGozaya());
        if (isUserGozaya() && ADFUtil.evaluateEL("#{sessionScope.errandF}").toString().equalsIgnoreCase("N")) {
            if (!messageRequiredValidation())
                return;
        }

        //        if (!isUserGozaya() && getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag") == null && getNavigationName().equals("HasrPage")) {
        //            showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u0642\u0636\u064A\u0629 \u0644\u0627\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0628\u062D\u062B",
        //                                             "soc4");
        //            return;
        //        }

        String navigationName = getNavigationName(), bcDegreeSpecId = null, attendance = null;
        BigDecimal folderType = null, casYear = null, policeOrNyabaaId = null; 
        RichSelectOneChoice inputYear = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc5");
        System.out.println("********"+inputYear.getValue());
        if (navigationName != null && (navigationName.equals("HasrPage") || navigationName.equals("FirstDegree"))) {
            if (inputYear.getValue() == null && !isUserGozaya()) {
                showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u062C\u062F\u0648\u0644",
                                                 "soc5");
                return;
            }
            if (!isUserGozaya() && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false")){
                folderType =
                    getBigDecimalValueFromObject(inputYear.getValue().toString());
                System.out.println("criminalType     is "+getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag"));
                if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("1"))
                bcDegreeSpecId = "3,4,8";
            } else{
                if (JSFUtils.resolveExpression("#{bindings.GenayWithoutGharama.inputValue}") != null)
                folderType = new BigDecimal (JSFUtils.resolveExpression("#{bindings.GenayWithoutGharama.inputValue}")+"");
        
        }}
            else if (navigationName != null && navigationName.equals("HasrGharamaReport")) {
            folderType = new BigDecimal(3);
            if (!isUserGozaya() && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false")){
                bcDegreeSpecId = "3,4,8";
            }
        }
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("3"))
        bcDegreeSpecId = "7,8";
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag").toString().equalsIgnoreCase("2"))
        bcDegreeSpecId = "1";
        casYear = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "Year"));
        Date dateFrom = (Date) getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateFrom");
        Date dateTo = (Date) getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateTo");
        String criminalType = (String) getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag");
        Object val = getAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag");
        if(isUserGozaya()){
            val = "Y";
        }
        
        BigDecimal toggle = new BigDecimal (1);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "TypeToggle")) != null)
          toggle =  getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "TypeToggle")) ;
        System.out.println("toggle  is "+toggle);
        BigDecimal startNum = new BigDecimal (0);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) != null)
            startNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) ;
        BigDecimal endNum = new BigDecimal (0);
        if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) != null)
            endNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) ;
          
        
        if (val != null && "Y".equalsIgnoreCase(val + "")) {
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity"));
        } else {
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabKolyaa"));
        }
        if (ADFUtil.evaluateEL("#{sessionScope.errandF}").toString().equalsIgnoreCase("Y") && ADFUtil.evaluateEL("#{bindings.EstanafGozayaFlag.inputValue}").toString().equals("false"))
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa"));
        if (getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya") != null && !getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya").equals(""))
            policeOrNyabaaId = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya"));
     if(ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}") != null)
         if(ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}").toString().length()>0)
         bcDegreeSpecId = ADFUtil.evaluateEL("#{bindings.DegreeId.inputValue}").toString(); ;
     
     System.out.println("***"+ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}"));
        if(ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}") != null)
            if(ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}").toString().length()>0)
            attendance = ADFUtil.evaluateEL("#{bindings.Attendance.inputValue}").toString(); 
     
        if (criminalType.equalsIgnoreCase("3"))
            criminalType = 2+"" ;
         executeHasrView(folderType, casYear, policeOrNyabaaId, dateFrom, dateTo, criminalType, bcDegreeSpecId , attendance , toggle , startNum , endNum );
    }

    /* reports  business */
    private void executeHasrView(BigDecimal folderType, BigDecimal casYear, BigDecimal policeId, Date dateFrom,
                                 Date dateTo, String criminal, String bcDegreeSpecId , String attendance , BigDecimal toggle , BigDecimal startSeq , BigDecimal endSeq) {
        ViewObjectImpl vo = (ViewObjectImpl) ADFUtil.findIterator(HABS_GHARAMA_ITERATOR).getViewObject();
        JSFUtils.addToSession("PFlAG", new BigDecimal (1));
        StringBuilder where = new StringBuilder();
        StringBuilder jasperQuery = new StringBuilder();
        long daysDiff =0 ;
        where.append(" YEAR = " + casYear + " ");
        where.append(" AND CRIMINAL_TYPE = " + criminal);
        if (bcDegreeSpecId != null)
        where.append(" AND BC_DEGREE_SPEC_ID IN (" + bcDegreeSpecId + ")"); 
        if (attendance != null)
        where.append(" AND attendance IN (" + attendance + ")"); 
        if (dateFrom != null && dateTo != null && toggle.compareTo(new BigDecimal(1))==0){
            
            java.util.Date dateFrom2;
            java.util.Date dateTo2;
            SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
            long diff  = 0;
           
             try {
                dateFrom2 = sdformat.parse(dateFrom+"");
                dateTo2 = sdformat.parse(dateTo+"");
             diff = dateTo2.getTime() - dateFrom2.getTime() ;
                 daysDiff= (diff/(1000*60*60*24)) ;
                 System.out.println("diff  is-----"+ diff);
                 System.out.println("daysDiff  is"+ daysDiff);
                 
                 
                 
                 
             } catch (ParseException e) {
            }

        
              
              if ( daysDiff <= 31 && daysDiff >= 0 )
              {
                      where.append("  AND HEARING_DATE BETWEEN TO_DATE('" + dateFrom + "', 'yyyy/mm/dd') AND TO_DATE('" + dateTo +
                                   "' , 'yyyy/mm/dd')+1   ");
                  
                  }
              else
                  JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
            
           
       
            
        }
        BigDecimal numDiff = endSeq.subtract(startSeq);
       
        if (  toggle.compareTo(new BigDecimal(2))==0 && (endSeq.intValue() > 0 && numDiff.intValue() >=0 && numDiff.intValue() <=300))
        {
                where.append("  AND INVENTORY_NUMBER >= "+startSeq+" and INVENTORY_NUMBER <= " +endSeq);
            }
        
        
        where.append("  AND  FOLDER_TYPE = " + folderType);
        where.append("   AND  POLICE_STATION_ENTITY_ID = " + policeId);
        System.err.println(where);
        if (( daysDiff <= 31 && daysDiff >= 0 && numDiff.intValue() >=0 && numDiff.intValue() <=300) || ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString().equalsIgnoreCase("hn"))
        {
            System.out.println("numDiff is *****"+numDiff);
        vo.setWhereClause(where.toString());
        vo.executeQuery();
          //  JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        }
        JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        jasperQuery.append(" FOLDER.YEAR = " + casYear + " ");
        jasperQuery.append(" AND FOLDER.CRIMINAL_TYPE = " + criminal);
        if (dateFrom != null && dateTo != null)
            jasperQuery.append("  AND CIRCLES.HEARING_DATE BETWEEN TO_DATE('" + dateFrom +
                               "', 'yyyy/mm/dd') AND TO_DATE('" + dateTo + "' , 'yyyy/mm/dd')+1   ");
        jasperQuery.append("  AND  FOLDER.FOLDER_TYPE = " + folderType);
        jasperQuery.append("   AND  FOLDER.POLICE_STATION_ENTITY_ID = " + policeId);

        JSFUtils.addToPageFlowScope("Query", jasperQuery.toString());
    }
    /*  / reports  business */

    public void criminalTypeFlagListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null && ("2".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())  || "3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())  )) {
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity", null);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa", null);
        }
    }

    private boolean isKoly() {
        Object crimnal = getAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag");
        if (isUserGozaya())
            return false;
        else if (crimnal != null && "1".equalsIgnoreCase(crimnal.toString()) &&
                 getAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity") != null) {
            return false;
        } else if (crimnal != null && "1".equalsIgnoreCase(crimnal.toString()) &&
                   getAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity") == null) {
            return true;
        }
        return true;
    }
    
    public void mamoreyaCheckBox(ValueChangeEvent valueChangeEvent) {
 
        if (valueChangeEvent.getNewValue() != null && Boolean.parseBoolean(valueChangeEvent.getNewValue()+"") == true){
           
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag", "N");
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabKolyaa", null);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa", null);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "OrigEntity", null);
            niyabaKolya.setVisible(false); 
            niyabaGozayaa.setVisible(false);
            originEntity.setVisible(false);
            mamoreya.setVisible(true);
//            ADFUtil.setEL("#{bindings.NeyabKolyaa.inputValue}", null);
//            JSFUtils.refreshComponent("pg46");
//            JSFUtils.refreshComponent("pgl9");
        }else{  
            niyabaKolya.setVisible(true);   
            mamoreya.setVisible(false);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya", null);

//            ADFUtil.setEL("#{bindings.NeyabaMamoreya.inputValue}", null);
//            JSFUtils.refreshComponent("pg46");
//            JSFUtils.refreshComponent("pgl9");
        }
    }

    public void gozayOrKoly(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        RichSelectOneChoice criminalFlag = (RichSelectOneChoice) ADFUtil.findComponentInRoot("soc4");
        RichPanelGroupLayout dafterFlag = (RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl10");
        System.err.println(valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null &&
            "true".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag", "1");
            criminalFlag.setDisabled(false);
            niyabaGozayaa.setVisible(true);
            originEntity.setVisible(true);
            mamoreya.setVisible(false);
            niyabaKolya.setVisible(true);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya", null);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "MamoreyaFlag", "N");
            if (getNavigationName() != null && getNavigationName().equals("HasrPage")) {
                
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
//                dafterFlag.setVisible(false); 
            }
            else if (getNavigationName() != null && getNavigationName().equals("MainReport")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
//                dafterFlag.setVisible(false); 
            }
        }

        else {
            criminalFlag.setDisabled(false);
            niyabaGozayaa.setVisible(false);
            originEntity.setVisible(false);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabaMamoreya", null);
            if (getNavigationName() != null && getNavigationName().equals("HasrPage")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
                dafterFlag.setVisible(true); 
            }
            else if (getNavigationName() != null && getNavigationName().equals("MainReport")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
            }
        }
    }

    public boolean isGozaySearch() {
        if ((getAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag") != null &&
             "Y".equalsIgnoreCase(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EstanafGozayaFlag") + "")) ||
            isUserGozaya() && ADFUtil.evaluateEL("#{sessionScope.errandF}").toString().equalsIgnoreCase("N")) {
            return true;
        } else {
            return false;
        }
    }

    public String printAbroadRequestsReport() {

        try {
            print(pathReport,"AbroadRequestReport", "procedures", "AbroadRequestReport");
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }

    public void searchAbroadScreen(ActionEvent ae) {
        Object mode = getAttributeValue(SEARCH_PARAMS_ITERATOR, "SearchTypeWafedeen");
        ViewObjectImpl logView = (ViewObjectImpl) ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Object gozaya = getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa");
        Object kolya = getAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabKolyaa");
        String where = "1 = 1";
        Object dateFrom = getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateFrom");
        Object dateTo = getAttributeValue(SEARCH_PARAMS_ITERATOR, "DateTo");

        if (dateFrom != null && dateTo != null) {
            where +=
                " AND CREATED_DATE BETWEEN to_date('" + dateFrom + "','yyyy-mm-dd') AND to_date('" + dateTo +
                "','yyyy-mm-dd') ";
        }

        if (mode != null && "1".equalsIgnoreCase(mode.toString())) {
            if (gozaya == null) {
                where +=
                    "AND (LOGGED_IN_ENTITY_ID = " + kolya +
                    " OR LOGGED_IN_ENTITY_ID IN (SELECT ID FROM ENTITY WHERE PARENT_ENTITY_ID = " + kolya + ")) ";
            } else {
                where += " AND LOGGED_IN_ENTITY_ID =  " + gozaya;
            }
        } else if (mode != null && "2".equalsIgnoreCase(mode.toString())) {
            if (gozaya == null) {
                where +=
                    " AND (ENTITY_ID = " + kolya + " OR ENTITY_ID IN (SELECT ID FROM ENTITY WHERE PARENT_ENTITY_ID = " +
                    kolya + ")) ";
            } else {
                where += " AND ENTITY_ID =" + gozaya;
            }
        } else {
            showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u0628\u062D\u062B",
                                             "sor1");
            return;
        }
        System.err.println("LLLLL = " + where);
        logView.setWhereClause(where);
        logView.executeQuery();
      //  JSFUtils.addToSession("PFlAG", new BigDecimal (0));
    }

    private UserInfo getUserInfo() {
        return (UserInfo) JSFUtils.getFromSession("userInfo");
    }

    public void setNiyabaGozayaa(RichPanelGroupLayout niyabaGozayaa) {
        this.niyabaGozayaa = niyabaGozayaa;
    }

    public RichPanelGroupLayout getNiyabaGozayaa() {
        return niyabaGozayaa;
    }

    public void setOriginEntity(RichPanelGroupLayout originEntity) {
        this.originEntity = originEntity;
    }

    public RichPanelGroupLayout getOriginEntity() {
        return originEntity;
    }

    public String validateBeforeSearch() {
        // Add event code here....
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateFrom;
        java.util.Date dateTo;
        long diff  = 0;
        long daysDiff =0 ;
         try {
            dateFrom = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo.getTime() - dateFrom.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("diff  is*****"+ diff);
             System.out.println("daysDiff  is"+ daysDiff);
             
             
             
             
         } catch (ParseException e) {
        }


        System.out.println(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}"));
        System.out.println(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}"));
          
          if ( daysDiff <= 31 && daysDiff >= 0)
          {
             ADFUtil.findOperation("executeProcedureView1").execute(); 
              
              }
          else
              JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
          
          return null;
    }

    public String validateBeforeSearchAncillaryProvisions() {
        // Add event code here...
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateFrom;
        java.util.Date dateTo;
        long diff  = 0;
        long daysDiff =0 ;
         try {
            dateFrom = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo.getTime() - dateFrom.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("diff  is#####"+ diff);
             System.out.println("daysDiff  is"+ daysDiff);
             
             
             
             
         } catch (ParseException e) {
        }
    
        System.out.println(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}"));
        System.out.println(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}"));

        Object neyabKolyaa = JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.attributeValue}");
        Object neyabGozayaa = JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.attributeValue}");
        Object origEntity = JSFUtils.resolveExpression("#{bindings.OrigEntity.attributeValue}");
        Object estanafGozayaFlag = JSFUtils.resolveExpression("#{bindings.EstanafGozayaFlag.inputValue}");
         System.err.println("origEntity:"+origEntity);
         if (null!=estanafGozayaFlag&&"true".equals(estanafGozayaFlag.toString())) {
            
            if (null==neyabGozayaa) {
               showErrorMessageForComponentById("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644  \u0627\u0644\u0646\u064A\u0627\u0628\u0629 \u0627\u0644\u062C\u0632\u0626\u064A\u0629",
                                                "soc2");
               return null;
           }
            if (null==origEntity) {
               showErrorMessageForComponentById("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644  \u062c\u0647\u0629 \u0627\u0644\u0642\u064a\u062f \u002d \u0642\u0633\u0645 \u0627\u0644\u0634\u0631\u0637\u0629",
                                                "soc3");
               return null;
           }
       }
//            
        ADFUtil.findOperation("executeProcedureView1").execute(); 
//          if ( daysDiff <= 14 && daysDiff >= 0)
//          {
//             ADFUtil.findOperation("executeProcedureView1").execute(); 
//              
//              }
//          else
//              JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
          
          return null;
    }
    
    public void gozayOrKolyCheckBox(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        RichSelectOneChoice criminalFlag = (RichSelectOneChoice) ADFUtil.findComponentInRoot("soc4");
        RichPanelGroupLayout dafterFlag = (RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl10");
        System.err.println(valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null &&
            "true".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "CriminalTypeFlag", "1");
            criminalFlag.setDisabled(false);
            niyabaGozayaa.setVisible(true);
//            niyabaGozayaaAncillary.setVisible(true);
            originEntity.setVisible(true);
            if (getNavigationName() != null && getNavigationName().equals("HasrPage")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
    //                dafterFlag.setVisible(false);
            }
            else if (getNavigationName() != null && getNavigationName().equals("MainReport")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
    //                dafterFlag.setVisible(false);
            }
        }

        else {
            criminalFlag.setDisabled(false);
            niyabaGozayaa.setVisible(false);
            setAttributeValue(SEARCH_PARAMS_ITERATOR, "NeyabGozayaa", null);
//            niyabaGozayaaAncillary.setVisible(false);
            originEntity.setVisible(false);
            if (getNavigationName() != null && getNavigationName().equals("HasrPage")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
                dafterFlag.setVisible(true); 
            }
            else if (getNavigationName() != null && getNavigationName().equals("MainReport")) {
                setAttributeValue(SEARCH_PARAMS_ITERATOR, "GenayWithoutGharama", null);
            }
        }
    }

    public void setNiyabaGozayaaAncillary(RichSelectOneChoice niyabaGozayaaAncillary) {
        this.niyabaGozayaaAncillary = niyabaGozayaaAncillary;
    }

    public RichSelectOneChoice getNiyabaGozayaaAncillary() {
        return niyabaGozayaaAncillary;
    }

    public void setNiyabaKolya(RichPanelGroupLayout niyabaKolya) {
        this.niyabaKolya = niyabaKolya;
    }

    public RichPanelGroupLayout getNiyabaKolya() {
        return niyabaKolya;
    }

    public void setMamoreya(RichPanelGroupLayout mamoreya) {
        this.mamoreya = mamoreya;
    }

    public RichPanelGroupLayout getMamoreya() {
        return mamoreya;
    }
    
    public void searchDafaterFees(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("SSSSSSSSSSSSSs");
        java.util.Date dateFrom2;
        java.util.Date dateTo2;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        long diff  = 0;
        long daysDiff =0 ;
         try {
            dateFrom2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo2.getTime() - dateFrom2.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("dateFrom  is "+ dateFrom2);
             System.out.println("dateTo  is "+ dateTo2);
             System.out.println("diff  is !!!!!!!!!!"+ diff);
             System.out.println("daysDiff  is!!!!!!!!!! "+ daysDiff);
             System.err.println("dateFrom  is "+ dateFrom2.getTime());
             System.err.println("dateTo  is "+ dateTo2.getTime());
             
             
             
             
         } catch (ParseException e) {
        }

        
          
          if ( daysDiff <= 30 && daysDiff >= 0)
          {
        
        String origin = null;
        
//        BindingContext bctx = BindingContext.getCurrent();
//        BindingContainer bindings = bctx.getCurrentBindingsEntry();
//        OperationBinding setVariable = (OperationBinding) bindings.get("filterFeesProcedureRO");
//              setVariable.getParamsMap().put("dateFrom", new Date (JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+""));
//              setVariable.getParamsMap().put("dateTo", new Date (JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+""));
//            
              System.out.println(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+"---------"+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
              if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null && (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"" ).length()>0)
              { 
                  //setVariable.getParamsMap().put("originEntity", new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+""));
              origin =JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"";}
                  else  if (JSFUtils.resolveExpression("#{bindings.NeyabGozayaa1.inputValue}") != null && (JSFUtils.resolveExpression("#{bindings.NeyabGozayaa1.inputValue}")+"" ).length()>0)
              {   // setVariable.getParamsMap().put("originEntity", new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabGozayaa1.inputValue}")+""));
                origin =JSFUtils.resolveExpression("#{bindings.NeyabGozayaa1.inputValue}")+""; }
                else
                {  // setVariable.getParamsMap().put("originEntity", new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+""));
              origin =JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+"";}
//              
//              setVariable.getParamsMap().put("folderCriminalType", new BigDecimal(JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}")+""));
//                       
//        setVariable.getParamsMap().put("type", new BigDecimal(JSFUtils.resolveExpression("#{bindings.FeesFolderType.inputValue}")+""));
//              
//                 
//             setVariable.getParamsMap().put("deg",   JSFUtils.resolveExpression("#{bindings.DegreeId.inputValue}"));
//              setVariable.getParamsMap().put("attend",  JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}") );
//               
//        setVariable.execute();
             
             String deg = "0";
             if(JSFUtils.resolveExpression("#{bindings.DegreeId.inputValue}")!=null)
               deg =   JSFUtils.resolveExpression("#{bindings.DegreeId.inputValue}")+"";
              String attend = "0" ;
              if(JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}")!=null)
                 attend =  JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}")+"";
              if(deg != null)
                  if(deg.length()<1)
                      deg="0";
              
              if(attend != null)
                  if(attend.length()<1)
                      attend="0";
              if(attend.equalsIgnoreCase(null))
                  attend="0";
              if(deg.equalsIgnoreCase(null))
                  deg="0";
              System.out.println("attend is"+attend+"-");
              System.out.println(origin+"deg is"+deg+"-");
              JSFUtils.storeOnSession("dateFrom", new Date (JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"")+"" );
              JSFUtils.storeOnSession("dateTo", new Date (JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"")+"" );
              JSFUtils.storeOnSession("attend", attend );
              JSFUtils.storeOnSession("pOrigin", origin ); 
              JSFUtils.storeOnSession("pBindType", JSFUtils.resolveExpression("#{bindings.FeesFolderType.inputValue}")+"" );
              JSFUtils.storeOnSession("pDeg",  deg);
              JSFUtils.storeOnSession("pcriminalType", JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}")+""  );
        ADFUtil.findIterator("FeesProcedureROVO1Iterator").getViewObject().executeQuery();
          }
        else
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
        
    }
    public void searchDafaterMotalba(ActionEvent actionEvent) {
        // Add event code here....
        
        java.util.Date dateFrom2;
        java.util.Date dateTo2;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        long diff  = 0;
        long daysDiff =0 ;
         try {
            dateFrom2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo2.getTime() - dateFrom2.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("dateFrom  is "+ dateFrom2);
             System.out.println("dateTo  is "+ dateTo2);
             System.out.println("diff  is %%%%%%"+ diff);
             System.out.println("daysDiff  is %%%%%%"+ daysDiff);
             System.err.println("dateFrom  is "+ dateFrom2.getTime());
             System.err.println("dateTo  is "+ dateTo2.getTime());
             
             
             
             
         } catch (ParseException e) {
        }

        
          
          if ( daysDiff <= 30 && daysDiff >= 0)
          {
        
        
        
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        OperationBinding setVariable = (OperationBinding) bindings.get("executeMotalbatView");
        setVariable.getParamsMap().put("claimFolderType", new BigDecimal(JSFUtils.resolveExpression("#{bindings.ClaimFolderType.inputValue}")+""));
        setVariable.getParamsMap().put("casYear",  new BigDecimal(JSFUtils.resolveExpression("#{bindings.CalimYear.inputValue}")+""));
      System.out.println("---------"+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
        if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null && (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"" ).length()>0)
        setVariable.getParamsMap().put("nyabaaGozaya", new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+""));
        else
            setVariable.getParamsMap().put("nyabaaGozaya", new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+""));
              
                if(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}") != null)
        setVariable.getParamsMap().put("dateFrom", new Date (JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+""));
              if(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}") != null)
        setVariable.getParamsMap().put("dateTo", new Date (JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+""));
       
       
             
              BigDecimal startNum = new BigDecimal (0);
              if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) != null)
                  startNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) ;
              BigDecimal endNum = new BigDecimal (0);
              if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) != null)
                  endNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) ;
              
              BigDecimal numDiff = endNum.subtract(startNum);
              System.out.println(endNum+"numDiff   is "+numDiff);
              if (  (numDiff.intValue() >=0 && numDiff.intValue() <=300 )  || ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString().equalsIgnoreCase("hn"))
              {
                      if(JSFUtils.resolveExpression("#{bindings.StartNum.inputValue}") != null)
                      setVariable.getParamsMap().put("startNum", new BigDecimal (JSFUtils.resolveExpression("#{bindings.StartNum.inputValue}")+""));
                      if(JSFUtils.resolveExpression("#{bindings.EndNum.inputValue}") != null)
                      setVariable.getParamsMap().put("endNum", new BigDecimal (JSFUtils.resolveExpression("#{bindings.EndNum.inputValue}")+""));
                      
                      
                      
                      setVariable.execute(); 
                  }
       
       
       
            
          }
        else
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
        
    }

    public void searchProcMotalba(ActionEvent actionEvent) {
        // Add event code here....
        
        java.util.Date dateFrom2;
        java.util.Date dateTo2;
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        long diff  = 0;
        long daysDiff =0 ;
         try {
            dateFrom2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo2.getTime() - dateFrom2.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("dateFrom  is "+ dateFrom2);
             System.out.println("dateTo  is "+ dateTo2);
             System.out.println("diff  is %%%%%%"+ diff);
             System.out.println("daysDiff  is %%%%%%"+ daysDiff);
             System.err.println("dateFrom  is "+ dateFrom2.getTime());
             System.err.println("dateTo  is "+ dateTo2.getTime());
             
             
             
             
         } catch (ParseException e) {
        }

        
          
          if ( daysDiff <= 30 && daysDiff >= 0)
          {
        
        
        
        BindingContext bctx = BindingContext.getCurrent();
        BindingContainer bindings = bctx.getCurrentBindingsEntry();
        OperationBinding setVariable = (OperationBinding) bindings.get("executeMotalbatView");
              setVariable.getParamsMap().put("claimFolderType", new BigDecimal(JSFUtils.resolveExpression("#{bindings.ClaimFolderType.inputValue}")+""));
              
        System.out.println("RRRRRRRRRRRRR  "+ (JSFUtils.resolveExpression("#{bindings.ProcClaimType.inputValue}").toString().length()));
              setVariable.getParamsMap().put("procType",  null);
              
       // if( JSFUtils.resolveExpression("#{bindings.ProcClaimType.inputValue}") != null )
            if( JSFUtils.resolveExpression("#{bindings.ProcClaimType.inputValue}").toString().length()>0)
              setVariable.getParamsMap().put("procType", new BigDecimal(JSFUtils.resolveExpression("#{bindings.ProcClaimType.inputValue}")+""));
             System.out.println("aaaaaaaaa"+JSFUtils.resolveExpression("#{bindings.CalimYear.inputValue}"));
              if(JSFUtils.resolveExpression("#{bindings.CalimYear.inputValue}").toString().length()>0)
        setVariable.getParamsMap().put("casYear",  new BigDecimal(JSFUtils.resolveExpression("#{bindings.CalimYear.inputValue}")+""));
      else
          setVariable.getParamsMap().put("casYear", new BigDecimal(0));
      System.out.println("---------"+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
        if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null && (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"" ).length()>0)
        setVariable.getParamsMap().put("nyabaaGozaya", new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+""));
        else
            setVariable.getParamsMap().put("nyabaaGozaya", new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+""));
              
                if(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}") != null)
        setVariable.getParamsMap().put("dateFrom", new Date (JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+""));
              if(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}") != null)
        setVariable.getParamsMap().put("dateTo", new Date (JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+""));
       
       
             
              BigDecimal startNum = new BigDecimal (0);
              if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) != null)
                  startNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "StartNum")) ;
              BigDecimal endNum = new BigDecimal (0);
              if(getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) != null)
                  endNum = getBigDecimalValueFromObject(getAttributeValue(SEARCH_PARAMS_ITERATOR, "EndNum")) ;
              
              BigDecimal numDiff = endNum.subtract(startNum);
              System.out.println(endNum+"numDiff   is "+numDiff);
              if (  (numDiff.intValue() >=0 && numDiff.intValue() <=300 )  || ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString().equalsIgnoreCase("hn"))
              {
                      if(JSFUtils.resolveExpression("#{bindings.StartNum.inputValue}") != null)
                      setVariable.getParamsMap().put("startNum", new BigDecimal (JSFUtils.resolveExpression("#{bindings.StartNum.inputValue}")+""));
                      if(JSFUtils.resolveExpression("#{bindings.EndNum.inputValue}") != null)
                      setVariable.getParamsMap().put("endNum", new BigDecimal (JSFUtils.resolveExpression("#{bindings.EndNum.inputValue}")+""));
                       
                      setVariable.execute(); 
                  }
       
       
       
            
          }
        else
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
        
    }
    
    
    public void searchDafaterMotalba2(ActionEvent actionEvent) {
        // Add event code here...
        
        JSFUtils.addToSession("PFlAG", new BigDecimal (1));
        
        
        ViewObject vo =  ADFUtil.findIterator("ProcedurePaiedROVO1Iterator").getViewObject();
        StringBuilder where = new StringBuilder();
        StringBuilder jasperQuery = new StringBuilder();
        long daysDiff =0 ;
        
        if ( JSFUtils.resolveExpression("#{bindings.ClaimFolderType.inputValue}")  != null)
        where.append("  CRIMINAL_TYPE = " + new BigDecimal(JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}")+""));
      
        java.util.Date dateFrom2 = new java.util.Date();
        java.util.Date dateTo2 = new java.util.Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        long diff  = 0;
        
         try {
            dateFrom2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+"");
            dateTo2 = sdformat.parse(JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}")+"");
         diff = dateTo2.getTime() - dateFrom2.getTime() ;
             daysDiff= (diff/(1000*60*60*24)) ;
             System.out.println("dateFrom  is ***   "+ (JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}")+""));
             System.out.println("dateTo  is "+ dateTo2);
             System.out.println("diff  is %%%%%%"+ diff);
             System.out.println("daysDiff  is %%%%%%"+ daysDiff);
             System.err.println("dateFrom  is "+ dateFrom2.getTime());
             System.err.println("dateTo  is "+ dateTo2.getTime());
             
             
             
             
         } catch (ParseException e) {
             System.out.println(e);
        }
String d1 = JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}") +"";
String d2 = JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}") +"";
       System.err.println("FFFFFFFF");
        BigDecimal ent = new BigDecimal (0);
        if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null && (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"" ).length()>0)
        ent =  new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"");
        else
          ent =  new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa1.inputValue}")+"");
        
        System.err.println("AAAAAAAAA");
        where.append("  AND  CLAIM_FOLDER_ID = " +  new BigDecimal(JSFUtils.resolveExpression("#{bindings.ClaimFolderType.inputValue}")+""));
        where.append("   AND   POLICE_STATION_ENTITY_ID = " + ent);
        
        System.err.println("QQQQQ"+where);
         
  //      if (1 == 2)
        {
              if ( daysDiff <= 31 && daysDiff >= 0   )
              {
                  if(JSFUtils.resolveExpression("#{bindings.DateFrom.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.DateTo.inputValue}") != null)
                      where.append("  AND PROCEDURE_DATE BETWEEN TO_DATE('" +  d1  + "', 'yyyy-mm-dd') AND TO_DATE('" +  d2   +  "' , 'yyyy-mm-dd')   ");
                  
                  }
              else
                  JSFUtils.showFacesInfoMessageFromBundle("bundle", "VALIDATION_DATE");
        }
           
       if (( daysDiff <= 31 && daysDiff >= 0 ) || ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString().equalsIgnoreCase("hn"))
        
        {
            System.err.println("RRRR  "+where);
        vo.setWhereClause(where.toString());
        vo.executeQuery();
        System.out.println("OOO "+vo.getQuery());
        }

       // JSFUtils.addToPageFlowScope("Query", jasperQuery.toString());
        
        
       JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        
        
        
        
        
        
        
        
        
        
         
        
    }


    public void neyabaaGozayaaVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("---"+valueChangeEvent.getNewValue().toString());
     
        RichSelectOneChoice policeSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc7");
        policeSoc.setDisabled(false);
        
        JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}",null);
        policeSoc.setValue(null);
        if(valueChangeEvent.getNewValue() == null || valueChangeEvent.getNewValue().toString().length()==0){
        
        policeSoc.setDisabled(true);
        }
    }
}
