package com.egabi.cjexec.view.beans.requests;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.view.utils.ADFUtil;

import java.text.SimpleDateFormat;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class RequestSearchBean {
    private String requestsTitle = "\u0627\u0644\u0628\u062d\u062b \u0641\u064a \u0627\u0644\u0637\u0644\u0628\u0627\u062a" ;
    
    public void resetSearch() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        ViewObject resultView =  am.getRequestsSearchResultRVO1(); // ADFUtil.findIterator("RequestsSearchResultRVO1Iterator").getViewObject() ;
        resultView.executeEmptyRowSet();
        
        String requestType = ADFUtil.evaluateEL("#{sessionScope.requestType}") == null ? "" : ADFUtil.evaluateEL("#{sessionScope.requestType}").toString() ;
        String requestFilters = " 1 = 1 " ;                            // THE DEFAULT VALUE GETS ALL REQUESTS
        if(requestType.equals("myRequests")){
            requestFilters = " NEXT_ASSIGNED_USER = "+/*rania*/ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}");     // loged in user
            requestsTitle = "\u0627\u0644\u0628\u062d\u062b \u0641\u064a \u0637\u0644\u0628\u0627\u062a\u064a" ;
        }else if(requestType.equals("openedRequests")){
            requestFilters = " CLOSED_FLAG = 0 " ;     
            requestsTitle = "\u0627\u0644\u0628\u062d\u062b \u0641\u064a \u0627\u0644\u0637\u0644\u0628\u0627\u062a \u0627\u0644\u0642\u0627\u0626\u0645\u0629" ;
        } 
        ADFUtil.setEL("#{pageFlowScope.requestFilters}", requestFilters);
        ADFUtil.setEL("#{sessionScope.requestType}", null);
    }

    public String searchRequests() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        ViewObject searchView = am.getRequestsSearchRVO1();
        Row searchRow = searchView.getCurrentRow();
        
        String condition = ADFUtil.evaluateEL("#{pageFlowScope.requestFilters}").toString() ;
        
        if(searchRow.getAttribute("RequestType") != null){
            condition += " AND ACTS_TYPE_ID = " + searchRow.getAttribute("RequestType") ;
        }
        
        if(searchRow.getAttribute("IdNmumber") != null){
            condition += " AND ID_NUMBER = '" + searchRow.getAttribute("IdNmumber") + "' " ;
        }
        
        if(searchRow.getAttribute("RequestNumber") != null){
            condition += " AND PROCEDURE_SERIAL = '" + searchRow.getAttribute("RequestNumber") + "' ";
        }
        
        if(searchRow.getAttribute("BCNumber") != null){
            condition += " AND BC_NUMBER = '" + searchRow.getAttribute("BCNumber") + "' ";
        }
                
        if(searchRow.getAttribute("FromDate") != null && searchRow.getAttribute("ToDate") != null){
            condition += " AND PROCEDURE_DATE BETWEEN TO_DATE('" + searchRow.getAttribute("FromDate") + "' , 'yyyy-mm-dd') AND " +
                " TO_DATE('" + searchRow.getAttribute("ToDate") + "' , 'yyyy-mm-dd')";
        }
        System.out.println(searchRow.getAttribute("FromDate"));
        System.out.println(searchRow.getAttribute("ToDate"));
        ViewObject resultView = am.getRequestsSearchResultRVO1() ;
        resultView.setWhereClause(condition) ;
        resultView.executeQuery() ;
        return null;
    }


    public void setRequestsTitle(String requestsTitle) {
        this.requestsTitle = requestsTitle;
    }

    public String getRequestsTitle() {
        return requestsTitle;
    }

    public String goToRequestDetails() {
        ViewObject requestsView = ADFUtil.findIterator("RequestsSearchResultRVO1Iterator").getViewObject();
        Row requestRow = requestsView.getCurrentRow();
        
        String actionTypeId = requestRow.getAttribute("ActsTypeId") == null ? "0" : requestRow.getAttribute("ActsTypeId").toString() ;
        int actsTypeId = Integer.parseInt(actionTypeId);
        
        String result = "" ;
        
        if(actsTypeId == 3){
            return "enableOpposition" ;
        }else if(actsTypeId == 4){
            return "installmentRequest" ;
        } else{
            return null ;
        }
    }
}
