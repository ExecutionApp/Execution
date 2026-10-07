package com.egabi.cjexec.view.beans.common;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.IOException;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import java.math.BigDecimal;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.ViewPortContext;
import oracle.adf.share.ADFContext;

public class ExcTemplate  implements Serializable {

    public ExcTemplate() {
        cc = ControllerContext.getInstance();  
    }

    /** Added by M.Salah - Check dirty data **/
    private String navPage = null;
    private String source = null;

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setNavPage(String navPage) {
        this.navPage = navPage;
    }

    public String getNavPage() {
        return navPage;
    }
    public void handleExceptionShowMessageInPopupDialog(){
        ControllerContext cc = ControllerContext.getInstance();

        Exception ex = cc.getCurrentViewPort().getExceptionData();
        String message = ex.getMessage();
        
        System.err.println("Error Message : "+message);

        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage facesMessage =
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error : Please Contact the Administrator", null);
        fc.addMessage(null, facesMessage);

     cc.getCurrentRootViewPort().clearException();
        fc.renderResponse();
      
    }
    private ControllerContext cc;  
    
    public String getStacktrace() {  
          if ( cc.getCurrentViewPort().getExceptionData()!=null ) {  
              StringWriter sw = new StringWriter();  
              PrintWriter pw = new PrintWriter(sw);   
              cc.getCurrentViewPort().getExceptionData().printStackTrace(pw);   
              return sw.toString();              
          }  
          return null;  
      } 

    public String checkDirtyDataAction() {
        // Add event code here.../
        JSFUtils.addToSession("pSequence", "");
        JSFUtils.addToSession("pYear","");
        JSFUtils.addToSession("pBCType","");
        JSFUtils.addToSession("pType", "");
        JSFUtils.addToSession("DailyEnt", "");
        JSFUtils.addToSession("DailyDate", "");
//        JSFUtils.addToSession("pPolice","");
        
        ControllerContext controllerContext = ControllerContext.getInstance();
        ViewPortContext currentRootViewPort = null;
        Boolean isDataDirty = null;
        if (controllerContext != null) {
            currentRootViewPort = controllerContext.getCurrentRootViewPort();
        }
        if (currentRootViewPort != null) {
            isDataDirty = currentRootViewPort.isDataDirty();
        }
//       Boolean flg = (Boolean) JSFUtils.resolveExpression("#{sessionScope.pMoveFlg}");
//        System.err.println(flg+"  isDataDirty : " + isDataDirty);
//        if (isDataDirty||flg) 
          if (isDataDirty) {
            JSFUtils.showPopup("pt_p1");
            System.err.println("inside true");
        } else {
            leaveAction();
            System.err.println("inside else");
        }
//        JSFUtils.addToSession("pMoveFlg", false);
        System.err.println("#{sessionScope.reportHasr} >>>>>"+navPage+">>>>>>>> " + ADFUtil.evaluateEL("#{sessionScope.reportHasr}"));
        return navPage;
    }

    public String stayAction() {
        // Add event code here...
        JSFUtils.hidePopup("pt_p1");
        setNavPage(null);
        System.err.println("..--> " + source);
        return null;
    }

    public Object resolvElDC(String data) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Application app = fc.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{data." + data + ".dataProvider}", Object.class);
        return valueExp.getValue(elContext);
    }

    public CjExecAMImpl getAm() {
        CjExecAMImpl am = (CjExecAMImpl) resolvElDC("CjExecAMDataControl");
        return am;
    }

    public String leaveAction() {
        // Add event code here...
        ADFContext.getCurrent().getSessionScope().put("pClaimDeleted", "A");
        JSFUtils.addToSession("DailyEnt", "");
        JSFUtils.addToSession("DailyDate", "");
        JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        JSFUtils.addToSession("NavigationName",null);
        JSFUtils.hidePopup("pt_p1");
        setNavPage(source);
        System.err.println("-----> " + source);
        JSFUtils.addToSession("NavigationName", source);
        if (getAm() != null) {
            getAm().getDBTransaction().rollback();
        }
        return source;
    }

    public String logout() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        // change is activate to F
        String url = ectx.getRequestContextPath() + "/faces/Logout";
        HttpSession session = (HttpSession) ectx.getSession(false);
        session.setAttribute("isUserSignedIn", null);
        session.setAttribute("userInfo", null);
        session.invalidate();
        try {
            ectx.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fctx.responseComplete();
        return null;
    }

}
