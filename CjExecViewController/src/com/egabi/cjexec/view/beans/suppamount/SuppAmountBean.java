package com.egabi.cjexec.view.beans.suppamount;

import javax.faces.event.ActionEvent;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.PollEvent;

public class SuppAmountBean {


    private boolean haveInstallments;

    public SuppAmountBean()
  {
        if(ADFUtil.getAttributeValue("rowConts")!=null&&Integer.valueOf(ADFUtil.getAttributeValue("rowConts").toString())>0){
        setHaveInstallments(true);
        }
        else
        setHaveInstallments(false);

    }

  

   
    public String saveAction() {
        // Add event code here...
            System.out.println(">>>>>>>>>>Have istall"+getHaveInstallments());
        try {
            BigDecimal procID= addProcedure();
            
            System.out.println(procID);
            boolean isValid=updateInstallmentValues(procID);
            if(isValid){
           // addAction(procID);
            updateSelectedValues(procID);
            
            ADFUtil.executeVoidOperation("Commit", null);
            return "save";
            }
            
        } catch (Exception e) {
            System.out.println(">>>>>>>>Error"+e.getMessage());
        }
        return null;
    }
    
    public void updateSelectedValues(BigDecimal procID)
    {
        DCIteratorBinding iter = ADFUtil.findIterator("ExcCourtBcPartyDebtIterator");
        ViewObject vo = iter.getViewObject();
        Row[] values = vo.getFilteredRows("isSelected",true);
        Row[] unSelectedvalues = vo.getFilteredRows("isSelected",null);


    
        for(Row r:values)
        {
            
            System.out.println(r.getAttribute("isSelected") );
            r.setAttribute("SettReqProcId", procID);
        }
        
            for(Row r:unSelectedvalues)
            {
                if(!r.getAttribute("isPaid").equals(true))
                 r.setAttribute("SettReqProcId", null);
            }
        
        }
    
    
    
    
    public boolean updateInstallmentValues(BigDecimal procID)
    {
        DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderProcInstallIterator");
        ViewObject vo = iter.getViewObject();
        Row[] values = vo.getFilteredRows("installSelected",true);
        Row[] unSelectedvalues = vo.getFilteredRows("installSelected",null);
        boolean isValid=true;
        
        for(Row sNumber:values)
        {
            
            for(Row uNumber:unSelectedvalues)
            {
                int sInstallNum=Integer.valueOf(sNumber.getAttribute("InstallmentNumber").toString());
                int uInstallNum=Integer.valueOf(uNumber.getAttribute("InstallmentNumber").toString());
                if(sInstallNum>uInstallNum && uNumber.getAttribute("SettlementProcId")==null){
                    isValid=false;
                    break;
                }
                
            }
            if(!isValid)
            break;
        }
        if(isValid){
        for(Row r:values)
        {
            
            System.out.println(r.getAttribute("installSelected") );
            r.setAttribute("SettReqProcId", procID);
        }
        
            for(Row r:unSelectedvalues)
            {
                
                System.out.println(">>>>>>>>"+r.getAttribute("SettReqProcId"));
                if(!r.getAttribute("isPaid").equals(true))
                r.setAttribute("SettReqProcId", null);
            }
        }else {
            
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "INSTALLMENT_ERROR_MESSAGE");
            }
        return isValid;
        }
    

    
    public BigDecimal addProcedure() throws Exception {
        
        BigDecimal procID =(BigDecimal)ADFUtil.executeOperation("createFProcedureAndAction", null);
        
        System.out.println(procID);
          /* java.sql.Date currentTime=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
           DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderProceduresIterator");
           ViewObject vo = iter.getViewObject();
           Row row = vo.createRow();
           
           row.setAttribute("ResPartyId", ADFUtil.getAttributeValue("ResPartyId"));
         //  row.setAttribute("CapacityType", 1);
           row.setAttribute("CreatedDate", currentTime);
           row.setAttribute("CreatedBy", 3169);
           row.setAttribute("ProcedureStatus", 11);
           row.setAttribute("ProcedureDate", currentTime);

           vo.insertRow(row);
          
           try {
               ADFUtil.executeVoidOperation("Commit", null);
     
               iter.executeQuery();
               vo.executeQuery();
               
          
           } catch (Exception e) {
               System.out.println(">>>>>>Error"+e.getMessage());
           }
*/
           return procID;
       }
       
     /*  public void addAction(BigDecimal procID)
       {
               DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderActionsIterator");
               ViewObject vo = iter.getViewObject();
               
               Row row = vo.createRow();
               row.setAttribute("ActionType", 6);
              // row.setAttribute("FolderRecordId", ADFUtil.getAttributeValue("Id"));
               row.setAttribute("ProcedureId", procID);

               vo.insertRow(row);
           
           }*/


    

    public void setVisiblity() {
        // Add event code here...
        System.out.println(ADFUtil.getAttributeValue("ResPartyId"));
       
        try {
            ADFUtil.executeVoidOperation("Execute", null);
            
            
           
           
        } catch (Exception e) {
        }

    }


    public void setHaveInstallments(boolean haveInstallments) {
        this.haveInstallments = haveInstallments;
    }

    public boolean getHaveInstallments() {
        return haveInstallments;
    }
}
