package com.egabi.cjexec.view.beans.parties;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.model.appmodules.UtilsAppModuleAMImpl;
import com.egabi.cjexec.view.utils.ADFUtil;

import javax.faces.application.FacesMessage;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class ParPartiesBean {
    
    public void executeEmptyParParties() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl"); 
        
        ViewObject parPartiesLogResultView = am.getParPartiesLogSearchResultRVO1() ;
        parPartiesLogResultView.executeEmptyRowSet();
    }
    
    
    public String searchParPartiesLog(){
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl"); 
        
        ViewObject searchParPartiesLogView = am.getSearchParPartiesLogRVO1();
        Row searchParPartiesLogRow = searchParPartiesLogView.getCurrentRow();
        
        ViewObject parPartiesLogResultView = am.getParPartiesLogSearchResultRVO1() ;
        
        String condition = " 1 = 1 " ;
        
        if(searchParPartiesLogRow.getAttribute("IdNumber") == null && searchParPartiesLogRow.getAttribute("FullName") == null){
            ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0623\u064a \u0645\u0646 \u0645\u062d\u062f\u062f\u0627\u062a " +
                "\u0627\u0644\u0628\u062d\u062b", FacesMessage.SEVERITY_ERROR);
            return null ;
        }
        
        if(searchParPartiesLogRow.getAttribute("IdNumber") != null){
            condition += " AND ID_NUMBER LIKE '%" + searchParPartiesLogRow.getAttribute("IdNumber") + "%'" ;
        }
        if(searchParPartiesLogRow.getAttribute("FullName") != null){
            condition += " AND (FNAME LIKE '%" + searchParPartiesLogRow.getAttribute("FullName") + "%' " ;
            condition += " OR SNAME LIKE '%" + searchParPartiesLogRow.getAttribute("FullName") + "%' " ;
            condition += " OR TNAME LIKE '%" + searchParPartiesLogRow.getAttribute("FullName") + "%' " ;
            condition += " OR FOURTHNAME LIKE '%" + searchParPartiesLogRow.getAttribute("FullName") + "%') " ;
        }
        
        parPartiesLogResultView.setWhereClause(condition);
        parPartiesLogResultView.executeQuery();
        System.out.println(parPartiesLogResultView.getQuery());
        return null ;
    }
    
    
    public String resetParPartiesLogResult(){
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl"); 
        
        ViewObject parPartiesLogResultView = am.getParPartiesLogSearchResultRVO1() ;
        parPartiesLogResultView.executeEmptyRowSet();
        
        return null ;
    }
    
    
    
    
    public String saveParty() {
        UtilsAppModuleAMImpl am = (UtilsAppModuleAMImpl) ADFUtil.getApplicationModuleForDataControl("UtilsAppModuleAMDataControl");
        String userName = ADFUtil.evaluateEL("#{sessionScope.UsernameSessionParam}").toString() ;
        int inserted = am.updateParPartiesLog(Long.parseLong(ADFUtil.evaluateEL("#{pageFlowScope.partyId}").toString()) , userName);
        
        if(inserted == 1){
            ADFUtil.showMessage("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638", FacesMessage.SEVERITY_INFO);
        }
        
        return null;
    }

    public void executeParParties() {
        UtilsAppModuleAMImpl am = (UtilsAppModuleAMImpl) ADFUtil.getApplicationModuleForDataControl("UtilsAppModuleAMDataControl");
        ViewObject view = am.getParPartiesView1();
        view.setWhereClause("ID = " + ADFUtil.evaluateEL("#{sessionScope.partyId}"));
        view.executeQuery();
    }
}
