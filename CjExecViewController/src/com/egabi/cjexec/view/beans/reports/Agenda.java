package com.egabi.cjexec.view.beans.reports;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.servlet.http.HttpServletRequest;
 
import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class Agenda implements Serializable {
    public Agenda() {
    }

    public void gozVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
     
   
   BigDecimal goz = new BigDecimal(0);
   if (valueChangeEvent.getNewValue().toString() != null && valueChangeEvent.getNewValue().toString().length()>0)
       goz = new BigDecimal( valueChangeEvent.getNewValue()+"") ;
         if (goz.compareTo(new BigDecimal (0))==1)
        {
                ADFContext.getCurrent().getSessionScope().put("AgendaEnt",new BigDecimal (valueChangeEvent.getNewValue()+"")); 
            }
        else
        ADFContext.getCurrent().getSessionScope().put("AgendaEnt",new BigDecimal (JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"")); 
        
   
   
   
        
    }

    public void search(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Goz"+JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        System.err.println("Kol"+JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
        System.out.println("Goz"+JSFUtils.resolveExpression("#{bindings.GozID.inputValue}"));
        System.err.println("Kol"+JSFUtils.resolveExpression("#{bindings.KolID.inputValue}"));
        
        
        
        BigDecimal deg = new BigDecimal(99);
        if (JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}").toString().length()>0 ){
            deg = new BigDecimal( JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}")+"") ;
        }
        ADFContext.getCurrent().getSessionScope().put("pDeg",deg); 
        
        BigDecimal goz = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}").toString().length()>0 ){
            goz = new BigDecimal( JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}")+"") ;
              if (goz.compareTo(new BigDecimal (0))==1)
             {
                  
                 ADFContext.getCurrent().getSessionScope().put("AgendaDateId",new BigDecimal (JSFUtils.resolveExpression("#{bindings.GozID.inputValue}")+"")); 
                    
                  
             }
        }
              else 
        ADFContext.getCurrent().getSessionScope().put("AgendaDateId",new BigDecimal (JSFUtils.resolveExpression("#{bindings.KolID.inputValue}")+"")); 
        
        if (  ADFContext.getCurrent().getSessionScope().get("fold") == null)
            ADFContext.getCurrent().getSessionScope().put("fold",new BigDecimal (99+""));
        
        ViewObject object = ADFUtil.findIterator("AgendaReservations1Iterator").getViewObject() ;
                 object.executeQuery();
        
    }


    public void searchActivation(ActionEvent actionEvent) {
        // Add event code here...
        System.out.println("Goz"+JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        System.err.println("Kol"+JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
        System.out.println("Goz"+JSFUtils.resolveExpression("#{bindings.GozID.inputValue}"));
        System.err.println("Kol"+JSFUtils.resolveExpression("#{bindings.KolID.inputValue}"));
        
        
        
        BigDecimal deg = new BigDecimal(99);
        if (JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}").toString().length()>0 ){
            deg = new BigDecimal( JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}")+"") ;
        }
        ADFContext.getCurrent().getSessionScope().put("pDegA",deg); 
        
        BigDecimal goz = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}").toString().length()>0 ){
            goz = new BigDecimal( JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}")+"") ;
            System.out.println("***dddddddddd****");
              if (goz.compareTo(new BigDecimal (0))==1)
             {
                  System.out.println("*******"+JSFUtils.resolveExpression("#{bindings.GozID.inputValue}"));
                 ADFContext.getCurrent().getSessionScope().put("AgendaDateIdA",new BigDecimal (JSFUtils.resolveExpression("#{bindings.GozID.inputValue}")+"")); 
                    
                  
             }
        }
        else {
        ADFContext.getCurrent().getSessionScope().put("AgendaDateIdA",new BigDecimal (JSFUtils.resolveExpression("#{bindings.KolID.inputValue}")+"")); 
            System.out.println("***dddddddddddddddddddddddddddddddddddddddddddddddd****"); 
        }
        
        if (  ADFContext.getCurrent().getSessionScope().get("fold") == null)
            ADFContext.getCurrent().getSessionScope().put("fold",new BigDecimal (99+""));
        
        ViewObject object = ADFUtil.findIterator("ActivateAgendaROVO1Iterator").getViewObject() ;
                 object.executeQuery();
        
    }


    public void dateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }
    
    public String port(){
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            String  serv = request.getServerName() ;
            int serverPort = request.getServerPort();
        return serv;
        }


    public void judgeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        int type =Integer.parseInt(  valueChangeEvent.getNewValue()+"");
        System.out.println(type +"*********");
        
        if (type == 0){
                ADFContext.getCurrent().getSessionScope().put("judg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("nojudg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("postpon",new BigDecimal (99+"")); 
                 
            }
        if (type == 2){
                ADFContext.getCurrent().getSessionScope().put("judg",new BigDecimal (1+"")); 
                ADFContext.getCurrent().getSessionScope().put("nojudg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("postpon",new BigDecimal (99+"")); 
                 
            }
        if (type == 1){
            
                ADFContext.getCurrent().getSessionScope().put("judg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("nojudg",new BigDecimal (1+"")); 
                ADFContext.getCurrent().getSessionScope().put("postpon",new BigDecimal (99+"")); 
                 
            }
        if (type == 3){
            
                ADFContext.getCurrent().getSessionScope().put("judg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("nojudg",new BigDecimal (99+"")); 
                ADFContext.getCurrent().getSessionScope().put("postpon",new BigDecimal (1+"")); 
                 
            
            }
        
        
        
        
        
        
        
        
    }

    public void foldVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BigDecimal fold = new BigDecimal(99);
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().length()>0)
            fold = new BigDecimal( valueChangeEvent.getNewValue()+"") ;
        ADFContext.getCurrent().getSessionScope().put("fold",new BigDecimal (fold+"")); 
    }

    public void DailySearch(ActionEvent actionEvent) {
        // Add event code here...
        
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
         
        System.err.println("Kol"+JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
        System.out.println("OrigEntity"+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
        System.err.println("DailyDate"+JSFUtils.resolveExpression("#{bindings.DailyDate.inputValue}"));
        
        ADFContext.getCurrent().getSessionScope().put("DailyEnt",  (JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"")); 
        
        BigDecimal goz = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}").toString().length()>0 ){
            ADFContext.getCurrent().getSessionScope().put("DailyEnt",  (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"")); 
               
        } 
        ADFContext.getCurrent().getSessionScope().put("DailyDate", (JSFUtils.resolveExpression("#{bindings.DailyDate.inputValue}")+"")); 
        
        
        
        ViewObject object = ADFUtil.findIterator("ExecutionDailyROVO1Iterator").getViewObject() ;
                 object.executeQuery();
        
        
        
        
    }

    public void ActivateReservations(ActionEvent actionEvent) {
        // Add event code here...
        ViewObject resVO = ADFUtil.findIterator("ActivateAgendaROVO1Iterator").getViewObject() ;
        ViewObject resVO1 = ADFUtil.findIterator("CourtBcHearingReservationVO3Iterator").getViewObject() ;
        RowSetIterator rws = resVO.createRowSetIterator(null);
        while (rws.hasNext()) {
            
         
            Row row = rws.next();
            resVO.setCurrentRow(row);
             {
                System.out.println("Previous Folder Data"+row.getAttribute("BusinessCaseNumber"));
               
//                ViewCriteria vc = resVO1.createViewCriteria();
//                ViewCriteriaRow vcr = vc.createViewCriteriaRow();
//              
//                vcr.setAttribute("Id", row.getAttribute("Id"));
//                 
//                vc.insertRow(vcr);
//                resVO1.applyViewCriteria(vc);
//                resVO1.executeQuery();
                RowSetIterator rws1 = resVO1.createRowSetIterator(null);
                System.err.println("count"+rws1.getRowCount());
                while (rws1.hasNext()) {
                    
                  
                    Row row1 = rws1.next();
                         System.out.println("Previous Folder Data"+row1.getAttribute("Id"));
                     {
                row1.setAttribute("ActiveCircleDate", new BigDecimal(1) );
                row1.setAttribute("ActivateDate", new Date() );
                     }}
//                resVO1.applyViewCriteria(null);
//                resVO1.executeQuery();
            }
        }
                 
        
        ADFUtil.findOperation("Commit").execute();  
        JSFUtils.showFacesInfoMessageFromBundle("bundle", "ADD_SUC");
        resVO.executeQuery();
    }
}
