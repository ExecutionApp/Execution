package com.egabi.cjexec.view.beans.bcDetails;

import com.egabi.cjexec.model.baseclasses.ArabicLiteralNumberParser;
import com.egabi.cjexec.model.baseclasses.SimplifiedMode;
import com.egabi.cjexec.model.roviews.bcdetails.ValidatePoliceReportROVORowImpl;
import com.egabi.cjexec.model.views.bcdetails.CourtBcHearingResPartiesVORowImpl;
import com.egabi.cjexec.model.views.bcdetails.ExcCourtBcChrgDecDepOrdVORowImpl;
import com.egabi.cjexec.view.beans.common.ExcTemplate;
import com.egabi.cjexec.view.beans.inventory.InventoryBean;
import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PersonData;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Vector;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;

import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.faces.model.SelectItem;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.ItemEvent;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import oracle.jbo.uicli.binding.JUCtrlValueBindingRef;

import oracle.stellent.ridc.model.DataObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;




public class BCDetails {
    private RichPanelHeader details;
    private RichInputListOfValues crimType;
    private RichInputListOfValues crimTypeAll;
    private RichActiveOutputText not_Exisit_Label;
    private RichActiveOutputText exisit_Label;
    private RichButton addParty_ExisitBC;
    private RichPanelGroupLayout addParty_BCNotExisit;
    private RichTable exisitDescTable;
    private RichSelectBooleanCheckbox checkFlag;
    private RichPanelLabelAndMessage btNMode;
    private RichSelectBooleanCheckbox returnFlag;
    private RichButton btnOneParty;
    private RichButton btnManyPary;
    private RichPanelGroupLayout searchImge;
    private RichButton saveBTN;
    private RichInputDate hearingDate;
    private RichPanelLabelAndMessage btnMode2;
    private RichLink testLink;
    private RichButton onePartyBTN;
    private RichPanelHeader chargMany;
    private RichPanelHeader descMany;
    private RichPanelHeader chargOne;
    private RichPanelHeader descOne;
    private BigDecimal counting = new BigDecimal(1);
    private RichActiveOutputText error_label;
    private RichButton saveReport25;
    private RichSelectOneChoice judgmentTypeIdBind;
    HashMap<String, String> dateList ;
    public BCDetails() {
    }

    public String EditImageParty() {
        // Add event code here...
        JSFUtils.showPopup("p4");
        return null;
    }

    public void handleExceptionShowMessageInPopupDialog(){
        ControllerContext cc = ControllerContext.getInstance();

        Exception ex = cc.getCurrentViewPort().getExceptionData();
        String message = ex.getMessage();
        
        System.out.println("Error Message : "+message);

        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage facesMessage =
          new FacesMessage(FacesMessage.SEVERITY_ERROR, "Internal Error : Please Contact the Administrator", null);
        fc.addMessage(null, facesMessage);

     cc.getCurrentRootViewPort().clearException();
        fc.renderResponse();
      
    }
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public Date getMaxDate() {
        try {

            Object dd = "2019-12-05";

            Object[] array = ADFUtil.findIterator("FinancialYearROVO1Iterator")
                                    .values()
                                    .toArray();
            System.out.println("---------->>>>" + array[0]);

            //                   ADFUtil.findIterator("FinancialYearROVO1Iterator").getRowSetIterator().getRowSet().get;
            //                          System.out.println("---------->>>>" + array[0]);


            ADFUtil.findIterator("FinancialYearROVO1Iterator").values();
            //                       (Object) ADFUtil.findIterator("FinancialYearROVO1Iterator").getCurrentRow().getAttribute("ToDate");
            System.err.println("DD  " + dd);
            Calendar now = Calendar.getInstance();
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dd.toString());
            System.err.println("d" + d);
            now.setTime(d);
            java.util.Date date = now.getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(date);
            return formatter.parse(currentDate);
        } catch (Exception e) {
            return null;
        }
    }


    public List<Date> getDateList(FacesContext facesContext, Calendar calendar, Date date, Date date2) {
        List<java.util.Date> disabledDates = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            disabledDates.add(sdf.parse("01/12/2019"));
            disabledDates.add(sdf.parse("25/12/2019"));
        } catch (ParseException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return disabledDates;
    }

    public String judgType_1() {
        // Add event code here...
        ADFUtil.setBoundAttributeValue("JudgementType", BigDecimal.ONE.add(BigDecimal.ONE));
        return null;
    }

    public String JudgType_2() {
        // Add event code here...
        ADFUtil.setBoundAttributeValue("JudgementType", BigDecimal.ONE.subtract(BigDecimal.ONE));
        return null;
    }
    public BigDecimal BC_Exisit = BigDecimal.ZERO;
    Vector vec = new Vector();
    Vector vecParties = new Vector();

    public String ValidateBC() {
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");
     System.err.println(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue ne null}")+"*****");
     if ((Boolean)JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue ne null}"))
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("OrigEntity", null);
     
        {
        JSFUtils.addToSession("skipPenValid", false);
        JSFUtils.addToPageFlowScope("judge", "false");
        // Add event code here....
        RichPanelHeader phCrimeMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("mapSeq", mapSeq);
        if (phCrimeMany != null)
            phCrimeMany.setRendered(false);
        if (this.getSaveBTN() != null)
            this.getSaveBTN().setRendered(true);
        JSFUtils.addToSession("Gnayaat", false);
        if (this.getSaveReport25() != null)
            this.getSaveReport25().setRendered(true);
        RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc34");
        if (sc != null)
            sc.setReadOnly(false);
        System.err.println("ValidateBC****************");
        JSFUtils.addToSession("prosequtionFlag", new BigDecimal(0));
        RichSelectOneChoice ch = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        if (ch != null)
            ch.setDisabled(false);
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("vec3", vec);
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("vecParty", vecParties);
        JSFUtils.addToSession("judgeText", "00");
        BigDecimal ney = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");

        BigDecimal bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
        JSFUtils.addToPageFlowScope("newKolyeaBC", "false");
        JSFUtils.addToSession("NeyabaaKolyaa", ney);
        BigDecimal neyKolyaaaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        BigDecimal neyGozayaaaaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}");
        JSFUtils.addToSession("NeyabaaKolyaa", neyKolyaaaa);
        JSFUtils.addToSession("NeyabaaGozayaa", neyGozayaaaaa);  
        JSFUtils.addToPageFlowScope("CJP", "false");
        JSFUtils.storeOnSession("undefinedPenaltyComb", "0");
        JSFUtils.storeOnSession("workAmountPenaltyComb", "0");
        JSFUtils.storeOnSession("PenaltyComb", "0");

        RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it110");
        RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it109");
        RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
        if(JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")!=null){
    BigDecimal pNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}");
            inputNum.setValue(pNum); 
        }
        if(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")!=null){
        BigDecimal pPoliceNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}");
            inputPoliceNum.setValue(pPoliceNum); 
        }

    
        if(JSFUtils.resolveExpression("#{bindings.ParYear1.inputValue}")!=null){
            BigDecimal pYear = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ParYear1.inputValue}");
            inputYear.setValue(pYear); 
        }


        BigDecimal returnValue = (BigDecimal) ADFUtil.findOperation("ValidatePoliceReport").execute();
        System.err.println("returnValue " + returnValue);
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        System.err.println("getStatusForBusinessCaseId--Star");
        System.out.println("****"+JSFUtils.getFromSession("BC_ID"));
        BigDecimal businessCaseId = (BigDecimal) JSFUtils.getFromSession("BC_ID");

            
        OperationBinding oper = getBindings().getOperationBinding("getStatusForBusinessCaseId");
                oper.getParamsMap().put("businessCaseId", businessCaseId);
                
        Object obj = oper.execute();
        BigDecimal returnStatus = null;
        if (obj != null) {
            returnStatus = new BigDecimal(obj.toString());
                if (returnStatus.compareTo(new BigDecimal("2")) == 0) {
                JSFUtils.showFacesInfoMessageFromBundle("bundle","caseIsUnderInvestigation");
//                  showErrorMessageForComponentById("\u0627\u0644\u0642\u0636\u064A\u0629 \u0642\u064A\u062F \u0627\u0644\u062A\u062D\u0642\u064A\u0642 \u0628\u0627\u0644\u0646\u0638\u0627\u0645");
                return null;
            }
        }
        
        System.err.println("returnStatus----------=" + returnStatus);            
        System.err.println("getStatusForBusinessCaseId--End");
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        
//            ViewObject object = ADFUtil.findIterator("BusinessCaseDetailsVO1Iterator").getViewObject() ;
//            object.executeQuery();
//            System.out.println(object.getRowCount()+"--------------");
        JSFUtils.addToPageFlowScope("backLog", false);
        this.setBC_Exisit(returnValue);
        JSFUtils.addToPageFlowScope("disabledValidate", true);
        RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
        System.out.println("****"+JSFUtils.getFromSession("BC_ID"));
        if (newRes != null)
            newRes.setDisabled(false);
        this.setCounting(new BigDecimal(1));
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        if (degreeChoice != null)
            degreeChoice.setDisabled(false);
        RichSelectOneChoice dateChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc18");
        if (dateChoice != null)
            dateChoice.setDisabled(false);
        
        if (returnValue == null)
            returnValue=new BigDecimal(11);
        if (returnValue.compareTo(new BigDecimal(3)) == 0) {
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(false);
            error_label.setVisible(true);
        }
        if (returnValue.compareTo(new BigDecimal(4)) == 0) {
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToPageFlowScope("detail", true);
            JSFUtils.addToPageFlowScope("BCFound", true);
            JSFUtils.addToPageFlowScope("back", "0");
            backLog();
        }


        else if (returnValue.compareTo(BigDecimal.ZERO) == 0) {
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToPageFlowScope("showDate", "true");
            this.getBtNMode().setVisible(true);
            this.getReturnFlag().setValue("false");
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(true);
            error_label.setVisible(false);
            JSFUtils.addToPageFlowScope("BCFound", false);
            JSFUtils.addToPageFlowScope("partyName", true);
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}") ||
                    JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))

            {
                JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
                //                if (bcType.compareTo(new BigDecimal(15))!=0)
                insertHasrKolyaa();
            }

            //            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));

        } else if (returnValue.compareTo(BigDecimal.ONE) == 0) {
            JSFUtils.addToPageFlowScope("judge", "true");
            if (dateChoice != null)
                dateChoice.setDisabled(false);

            Boolean prosecutionFlag = false;

                prosecutionFlag = (Boolean) ADFContext.getCurrent()
                                                      .getSessionScope()
                                                      .get("ProsecutionCJ");
          System.out.println("prosecutionFlag ="+prosecutionFlag+"-");
            if (prosecutionFlag) {

                JSFUtils.addToPageFlowScope("showDate", "true");
                JSFUtils.addToPageFlowScope("detail", "true");
                this.getSaveBTN().setDisabled(false);
                this.getSaveReport25().setDisabled(false);

                JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
                //                 RichSelectOneChoice ch = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
                if (ch != null)
                    ch.setDisabled(true);
                JSFUtils.refreshComponent("b1");
                prosecution();
            } else {
                //            JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal (1));

                if (this.getBtnMode2() != null)
                    this.getBtnMode2().setVisible(true);
                if (this.getBtNMode() != null)
                    //            this.getSaveBTN().setDisabled(false);
                    JSFUtils.addToPageFlowScope("partyName", false);
                JSFUtils.addToPageFlowScope("BCFound", true);
                JSFUtils.addToPageFlowScope("back", "0");
                RichButton but = (RichButton) JSFUtils.findComponentInRoot("b28");
                if (but != null)
                    but.setSelected(false);
                JSFUtils.refreshComponent("b28");
                //            JSFUtils.addToPageFlowScope("detail", true);
                JSFUtils.addToPageFlowScope("showDate", "false");
                if (this.getReturnFlag() != null)
                    this.getReturnFlag().setValue("true");
                if (not_Exisit_Label != null)
                    not_Exisit_Label.setVisible(true);
                if (exisit_Label != null)
                    exisit_Label.setVisible(false);
                if (error_label != null)
                    error_label.setVisible(false);
                //            this.getSaveBTN().setDisabled(false);
                //            btnManyPary.setVisible(false);
            }

                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}") ||
                    JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))

            {
                if (JSFUtils.resolveExpressionAsBoolean("#{bindings.Id9.inputValue == null}")) {
                    if (JSFUtils.resolveExpression("#{bindings.CheckFlag.inputValue}") != null) {
                        JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
                        //                        if (bcType.compareTo(new BigDecimal(15))!=0)
                        insertHasrKolyaa();
                    }
                }
            }

        }
        if (returnValue.compareTo(new BigDecimal(11)) != 0) {
        
        RichButton butt = (RichButton) JSFUtils.findComponentInRoot("b32");
        butt.setDisabled(false);

        //        else
        //            JSFUtils.calljqHelper("$.growl.notice({ message: \"ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ \" });");
        JSFUtils.addToPageFlowScope("degFlag", true);
        }
        
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("OrigEntity",
                           new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") + ""));
    }
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
            JSFUtils.setExpressionValue("#{bindings.ManualSeq.inputValue}", "Y");
            System.out.println("ManualSeq--  ----Y  "+JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
     
        return null;
    }


    public String validateBC_PI() {
      
      
      
        RichInputDate richInputDate = (RichInputDate) JSFUtils.findComponentInRoot("id21");
        richInputDate.setRendered(true);
        RichButton richButton = (RichButton) JSFUtils.findComponentInRoot("b4");
        RichButton richButtonl25 = (RichButton) JSFUtils.findComponentInRoot("l25");
        richButton.setRendered(true);
        richButtonl25.setRendered(true);
        // Add event code here....
        System.err.println("ValidateBC*********PI*******");
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");

        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("vec3", vec);

        BigDecimal neyKolyaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        BigDecimal neyGozayaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}");
        JSFUtils.addToSession("NeyabaaKolyaa", neyKolyaa);
        JSFUtils.addToSession("NeyabaaGozayaa", neyGozayaa);
        
        
        RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it110");
        RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it109");
        RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
        if(JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")!=null){
        BigDecimal pNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}");
            inputNum.setValue(pNum); 
        }
        if(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")!=null){
        BigDecimal pPoliceNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}");
            inputPoliceNum.setValue(pPoliceNum); 
        }
        if(JSFUtils.resolveExpression("#{bindings.ParYear1.inputValue}")!=null){
            BigDecimal pYear = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ParYear1.inputValue}");
            inputYear.setValue(pYear); 
        }

        BigDecimal returnValue = (BigDecimal) ADFUtil.findOperation("ValidatePoliceReport").execute();
        System.err.println("returnValue " + returnValue);
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        System.err.println("getStatusForBusinessCaseId--Star");
        System.out.println("****"+JSFUtils.getFromSession("BC_ID"));
        BigDecimal businessCaseId = (BigDecimal) JSFUtils.getFromSession("BC_ID");

            
        OperationBinding oper = getBindings().getOperationBinding("getStatusForBusinessCaseId");
                oper.getParamsMap().put("businessCaseId", businessCaseId);
                
        Object obj = oper.execute();
        BigDecimal returnStatus = null;
        if (obj != null) {
            returnStatus = new BigDecimal(obj.toString());
                if (returnStatus.compareTo(new BigDecimal("2")) == 0) {
                JSFUtils.showFacesInfoMessageFromBundle("bundle","caseIsUnderInvestigation");
        //                  showErrorMessageForComponentById("\u0627\u0644\u0642\u0636\u064A\u0629 \u0642\u064A\u062F \u0627\u0644\u062A\u062D\u0642\u064A\u0642 \u0628\u0627\u0644\u0646\u0638\u0627\u0645");
                return null;
            }
        }
        
        System.err.println("returnStatus----------=" + returnStatus);            
        System.err.println("getStatusForBusinessCaseId--End");
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        JSFUtils.addToPageFlowScope("backLog", false);
        this.setBC_Exisit(returnValue);
        JSFUtils.addToPageFlowScope("disabledValidate", true);

        this.setCounting(new BigDecimal(1));
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        degreeChoice.setDisabled(false);

        if (returnValue.compareTo(new BigDecimal(3)) == 0) {
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(false);
            error_label.setVisible(true);
        }

        else if (returnValue.compareTo(new BigDecimal(2)) == 0) {
            Cancel();
        } else if (returnValue.compareTo(BigDecimal.ZERO) == 0) {
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToPageFlowScope("showDate", "true");
            this.getBtNMode().setVisible(true);
            this.getReturnFlag().setValue("false");
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(true);
            error_label.setVisible(false);
            JSFUtils.addToPageFlowScope("BCFound", false);
            JSFUtils.addToPageFlowScope("partyName", true);
            if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}"))

            {
                JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
                //                insertHasrKolyaa();
            }

            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            JSFUtils.refreshComponent("b1");
            newFirstResParty();
            //            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));

        } else if (returnValue.compareTo(BigDecimal.ONE) == 0) {
            RichSelectOneChoice dateChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc18");
            dateChoice.setDisabled(false);

            Boolean prosecutionFlag = false;

            prosecutionFlag = (Boolean) ADFContext.getCurrent()
                                                  .getSessionScope()
                                                  .get("ProsecutionCJ");
            if (prosecutionFlag) {

                JSFUtils.addToPageFlowScope("showDate", "true");
                JSFUtils.addToPageFlowScope("detail", "true");
                this.getSaveBTN().setDisabled(false);
                /** unCommented by M.Salah **/
                this.getSaveReport25().setDisabled(false);

                JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
                //                 RichSelectOneChoice ch = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");

                JSFUtils.refreshComponent("b1");
                prosecution();
            } else {
                //            JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal (1));

                if (this.getBtnMode2() != null)
                    this.getBtnMode2().setVisible(true);
                if (this.getBtNMode() != null)
                    //            this.getSaveBTN().setDisabled(false);
                    JSFUtils.addToPageFlowScope("partyName", false);
                JSFUtils.addToPageFlowScope("BCFound", true);
                JSFUtils.addToPageFlowScope("back", "0");
                RichButton but = (RichButton) JSFUtils.findComponentInRoot("b28");
                but.setSelected(false);
                JSFUtils.refreshComponent("b28");
                //            JSFUtils.addToPageFlowScope("detail", true);
                JSFUtils.addToPageFlowScope("showDate", "false");
                this.getReturnFlag().setValue("true");
                not_Exisit_Label.setVisible(true);
                exisit_Label.setVisible(false);
                error_label.setVisible(false);

            }

            //   this.getSaveBTN().setDisabled(true);

        }
        RichButton butt = (RichButton) JSFUtils.findComponentInRoot("b32");
        butt.setDisabled(false);

        JSFUtils.addToPageFlowScope("degFlag", true);
        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("OrigEntity",
                       new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") + ""));
        return null;
    }


    public void setDetails(RichPanelHeader details) {
        this.details = details;
    }

    public RichPanelHeader getDetails() {
        return details;
    }

    public void DescVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println(JSFUtils.resolveExpression("#{bindings.Id2.inputValue}") + "$$$$$$$$$" +
                           valueChangeEvent.getNewValue());
        //        System.out.println("*0"+ADFUtil.findIterator("PartCrimTypesROVO1Iterator").getViewObject().getCurrentRow().getAttribute("id"));

    }


    public void enter(ClientEvent clientEvent) {
        // Add event code here...RichButton#{bindings.SchemaId.inputValue ne '3643'}
        BigDecimal schemaId = new BigDecimal(3643);
        if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
            schemaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
        System.err.println("#####00000000#####" + JSFUtils.resolveExpression("#{bindings.Id12.inputValue}"));
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov1");

        if (lov.getValue() != null) {
            this.getSaveBTN().setDisabled(false);
            if (schemaId.compareTo(new BigDecimal(3643)) == 0)
                this.getSaveReport25().setDisabled(false);
            JSFUtils.refreshComponent("b1");
            System.err.println("GGGGGGGGGGGGGGGG");
            ADFUtil.findOperation("AsignDescionDesToParty").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("ilov1");
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            JSFUtils.refreshComponent("b1");

        }
        JSFUtils.refreshComponent("pg25");
        System.err.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    public String pressAddDes() {
        System.err.println("#####00000000#####" + JSFUtils.resolveExpression("#{bindings.Id12.inputValue}"));
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov1");
        if (lov.getValue() != null) {
            this.getSaveBTN().setDisabled(false);
            this.getSaveReport25().setDisabled(false);
            JSFUtils.refreshComponent("b1");
            System.err.println("GGGGGGGGGGGGGGGG");
            ADFUtil.findOperation("AsignDescionDesToParty").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("ilov1");
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            JSFUtils.refreshComponent("b1");

        }
        JSFUtils.refreshComponent("pg25");
        System.err.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        return null;
    }

    public String ssss() {
        System.err.println("#######5555555###");
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov1");
        if (lov.getValue() != null) {
            this.getSaveBTN().setDisabled(false);
            this.getSaveReport25().setDisabled(false);
            JSFUtils.refreshComponent("b1");
            System.err.println("GGGGGGGGGGGGGGGG");
            ADFUtil.findOperation("AsignDescionDesToParty").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("ilov1");
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            JSFUtils.refreshComponent("b1");
        }
        JSFUtils.refreshComponent("pg25");

        return null;
    }


    public void enter2(ClientEvent clientEvent) {
        // Add event code here...RichButton
        System.err.println("$$$$$$$$$$$$$$$$$$$");
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov2");
        if (lov.getValue() != null) {
            this.getSaveBTN().setDisabled(false);
            this.getSaveReport25().setDisabled(false);
            JSFUtils.refreshComponent("b1");
            System.err.println("GGGGGGGGGGGGGGGG22222222");
            ADFUtil.findOperation("AsignDescionDesToParty").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("ilov2");

        }
        JSFUtils.refreshComponent("pg25");
        System.err.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
    }


    public String AssignDescToParty() {
        // Add event code here...
        this.crimType.setValue("");
        return null;
    }

    public void setCrimType(RichInputListOfValues crimType) {
        this.crimType = crimType;
    }

    public RichInputListOfValues getCrimType() {
        return crimType;
    }

    public String deleteDescForOneParty() {
        // Add event code here...
        //        ADFUtil.findOperation("Delete3").execute();
        ViewObject vo = ADFUtil.findIterator("DecisionDescVO1Iterator").getViewObject();

        vo.executeQuery();
        //        System.err.println(JSFUtils.resolveExpression("#{bindings.DescriptionId1.inputValue}") + "******"+vo.getCurrentRow().getAttribute("DescriptionId"));

        return null;
    }

    public void setCrimTypeAll(RichInputListOfValues crimTypeAll) {
        this.crimTypeAll = crimTypeAll;
    }

    public RichInputListOfValues getCrimTypeAll() {
        return crimTypeAll;
    }

    public void setBC_Exisit(BigDecimal BC_Exisit) {
        this.BC_Exisit = BC_Exisit;
    }

    public BigDecimal getBC_Exisit() {
        return BC_Exisit;
    }

    public void setNot_Exisit_Label(RichActiveOutputText not_Exisit_Label) {
        this.not_Exisit_Label = not_Exisit_Label;
    }

    public RichActiveOutputText getNot_Exisit_Label() {
        return not_Exisit_Label;
    }

    public void setExisit_Label(RichActiveOutputText exisit_Label) {
        this.exisit_Label = exisit_Label;
    }

    public RichActiveOutputText getExisit_Label() {
        return exisit_Label;
    }

    public void setAddParty_ExisitBC(RichButton addParty_ExisitBC) {
        this.addParty_ExisitBC = addParty_ExisitBC;
    }

    public RichButton getAddParty_ExisitBC() {
        return addParty_ExisitBC;
    }

    public void setAddParty_BCNotExisit(RichPanelGroupLayout addParty_BCNotExisit) {
        this.addParty_BCNotExisit = addParty_BCNotExisit;
    }

    public RichPanelGroupLayout getAddParty_BCNotExisit() {
        return addParty_BCNotExisit;
    }

    public void setExisitDescTable(RichTable exisitDescTable) {
        this.exisitDescTable = exisitDescTable;
    }

    public RichTable getExisitDescTable() {
        return exisitDescTable;
    }
    BigDecimal partyID;

    public void discolse(DisclosureEvent disclosureEvent) {
        // Add event code here...
//        if (!ValidatePenaltyParty())
//        { JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALID_DATA");}



 



String tmp = "";
if (JSFUtils.resolveExpression("#{bindings.Nickname.inputValue}") != null)
tmp=JSFUtils.resolveExpression("#{bindings.Nickname.inputValue}")+"";
        JSFUtils.setExpressionValue("#{bindings.Nickname.inputValue}", tmp.trim()+'ـ');
        
//        JSFUtils.setExpressionValue("#{bindings.PartyFullName.inputValue}",  
//        
//        
//        JSFUtils.resolveExpression("#{bindings.Fname.inputValue}")+" "+
//         
//        JSFUtils.resolveExpression("#{bindings.Sname.inputValue}" )+" "+
//  
//        JSFUtils.resolveExpression("#{bindings.Tname.inputValue}" )+" "+
//        
//        JSFUtils.resolveExpression("#{bindings.Fourthname.inputValue}" ));
        
        
        
        
        Map<BigDecimal, BigDecimal> map = (Map<BigDecimal, BigDecimal>) ADFContext.getCurrent()
                                                                                  .getSessionScope()
                                                                                  .get("ChargMap");
        if (disclosureEvent.isExpanded() == true) {
            String PartyID = disclosureEvent.getComponent()
                                            .getAttributes()
                                            .get("PartyID")
                                            .toString();
            String CourtBcHearingResPartyId = disclosureEvent.getComponent()
                                                             .getAttributes()
                                                             .get("CourtBcHearingResPartyId")
                                                             .toString();

            System.out.println("Disclose " + PartyID + " CourtBcHearingResId " + CourtBcHearingResPartyId);
            BigDecimal partID = new BigDecimal(PartyID);
            BigDecimal ReservaionParyId = new BigDecimal(CourtBcHearingResPartyId);


            //

            ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            System.out.println("CourtBcHearingResPartiesVO1Iterator " + vo.getRowCount());
            RowSetIterator rws = vo.createRowSetIterator(null);
            while (rws.hasNext()) {
                Row rw = rws.next();
                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
                BigDecimal PartyId = (BigDecimal) rw.getAttribute("PartyId");
                System.err.println(ReservaionParyId + "%%" + rwsPartyId);
                if (rwsPartyId.compareTo(ReservaionParyId) == 0) {


                    vo.setCurrentRow(rw);
                    break;
                }
            }

            JSFUtils.refreshComponent("pt5");
 BigDecimal judg = new BigDecimal(1) ;
 if(JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}")!= null)
     judg = (BigDecimal)JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
            ViewObject vo2 = ADFUtil.findIterator("ExcFolderDataVO1Iterator").getViewObject();
            System.out.println("ExcFolderDataVO1Iterator " + vo.getRowCount());
            RowSetIterator rws2 = vo2.createRowSetIterator(null);
            while (rws2.hasNext()) {
                Row rw = rws2.next();
                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("ResPartyId");
                System.err.println(ReservaionParyId + "%%" + rwsPartyId);
                if (rwsPartyId.compareTo(ReservaionParyId) == 0) {
                    //  JSFUtils.setExpressionValue("#{bindings.UpdatedDate2.inputValue}", null);
                    if (judg.compareTo(new BigDecimal(1)) != 0) {
                    BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
                   if (bcType.compareTo(new BigDecimal(1))==1)
                   rw.setAttribute("FolderType",new BigDecimal (3));
                    }
                    System.out.println("SSSSSSSSSSS5555555555555  " + rw.getAttribute("SequenceNumber"));
                    System.out.println("SSSSSSSSSSS5555555555555  " + rw.getAttribute("sequenceFold"));
                    vo2.setCurrentRow(rw);
                }
            }
            ////            vo2.executeQuery();
            ////            ExcCourtBcChargeDecisionsVO1
            //
            BigDecimal ChargeDecisionId = new BigDecimal(0);
            //                        ViewObject vo3 = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
            //                        System.out.println("ExcCourtBcChargesPartiesVO2Iterator " + vo.getRowCount());
            //                        RowSetIterator rws3 = vo3.createRowSetIterator(null);
            //                        while (rws3.hasNext()) {
            //                            Row rw = rws3.next();
            //                            BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("ResPartyId");
            //                              ChargeDecisionId = (BigDecimal) rw.getAttribute("ChargeDecisionId");
            //                            System.out.println("ChargeDecisionId  "+ChargeDecisionId);
            //                            System.err.println(ReservaionParyId + "%*******%" + rwsPartyId);
            //                            if (rwsPartyId.compareTo(ReservaionParyId) == 0) {
            //                                vo3.setCurrentRow(rw);
            //            break;
            //
            //
            //                            }
            //                        }


            //
            boolean ccc = true;
            if (ADFContext.getCurrent()
                          .getSessionScope()
                          .get("ChargMap") != null) {
                System.err.println("map         : " + map.size());
                for (Map.Entry<BigDecimal, BigDecimal> entry : map.entrySet()) {
                    if (ccc) {
                        System.out.println(entry.getKey() + " ===================== " +
                                           entry.getValue()); //ChargeDecisionId
                        ViewObject viewImp =
                            ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
                        System.out.println(viewImp.getRowCount() + ">>>>>>>>>>>>>>>>>>>");

                        RowSetIterator rws4 = viewImp.createRowSetIterator(null);
                        while (rws4.hasNext()) {
                            Row rw4 = rws4.next();
                            BigDecimal rwsPartyId = (BigDecimal) rw4.getAttribute("Id");
                            BigDecimal resPartyId = (BigDecimal) rw4.getAttribute("ResPartyId");
                            String val = (String) entry.getValue().toString();
                            String val2 = rwsPartyId.toString();
                            System.out.println("val" + val + "-");
                            System.out.println("val" + val2 + "-");
                            System.err.println(map.entrySet() + "%%" + rwsPartyId);
                            if (val2.compareToIgnoreCase(val) == 0 && resPartyId.compareTo(ReservaionParyId) == 0) {

                                rw4.setAttribute("ChargeDecisionId", entry.getKey());
                                ChargeDecisionId = (BigDecimal) entry.getKey();
                                System.out.println(rwsPartyId + " HGH  " + ChargeDecisionId + "   " +
                                                   val2.compareToIgnoreCase(val));
                                viewImp.setCurrentRow(rw4);
                                ccc = false;
                                break;

                            }
                        }

                        //                                  viewImp.createRowSetIterator(null).next().setAttribute("ChargeDecisionId", entry.getKey());
                    }
                }


            }

            //

            //            vo3.executeQuery();

            //
            ViewObject vo4 = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
            System.out.println("ExcCourtBcChargeDecisionsVO1Iterator " + vo4.getRowCount());
            RowSetIterator rws4 = vo4.createRowSetIterator(null);
            while (rws4.hasNext()) {
                Row rw4 = rws4.next();
                BigDecimal Id = (BigDecimal) rw4.getAttribute("Id");
                System.err.println(ChargeDecisionId + "%---------------%" + Id);
                if (Id.compareTo(ChargeDecisionId) == 0) {
                    //                    System.out.println("YEARS   "+rw4.getAttribute("ImprisonmentYears"));
                    //                    System.out.println("moths   "+rw4.getAttribute("ImprisonmentMonths"));
                    //                    System.out.println("days   "+rw4.getAttribute("ImprisonmentDays"));
                    //
                    //                    JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", rw4.getAttribute("ImprisonmentYears"));

                    vo4.setCurrentRow(rw4);
                    break;
                }
            }
            //                        vo4.executeQuery();

            System.out.println("vo " + vo.getRowCount());


            ViewObject ParPartiesvo = ADFUtil.findIterator("ParPartiesVO1Iterator").getViewObject();
            RowSetIterator ParPartiesrws = ParPartiesvo.createRowSetIterator(null);
            while (ParPartiesrws.hasNext()) {
                Row rw = ParPartiesrws.next();
                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
                System.err.println("%%" + partID);
                if (rwsPartyId.compareTo(partID) == 0) {
                    ParPartiesvo.setCurrentRow(rw);
                    break;
                }
            }
            //            ParPartiesvo.executeQuery();

            //            ViewObject FormCaseDescriptionPartiesvo =
            //                ADFUtil.findIterator("FormCaseDescriptionPartiesVO2Iterator").getViewObject();
            //            BigDecimal FormCaseDescriptionPartiesId =
            //                (BigDecimal) FormCaseDescriptionPartiesvo.getCurrentRow().getAttribute("Id");
            //            System.err.println("Row " + FormCaseDescriptionPartiesId);
            //            ViewObject FormCaseDescriptionDesPartvo =
            //                ADFUtil.findIterator("FormCaseDescriptionDesPartVO2Iterator").getViewObject();
            //            System.err.println("COUNT " + FormCaseDescriptionDesPartvo.getRowCount());
            //
            //
            //            RowSetIterator FormCaseDescriptionDesPartrws = FormCaseDescriptionDesPartvo.createRowSetIterator(null);
            //                        if(FormCaseDescriptionDesPartrws.hasNext())
            //                        {
            //                              Row rw =  FormCaseDescriptionDesPartrws.next();
            //                             BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
            //                            System.err.println("%FormCaseDescriptionDesPartrws%"+partID);
            //                            {
            //                                                ViewObject ExcCourtBcChargesPartiesVO = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
            //
            //                                                RowSetIterator ExcCourtBcChargesPartiesrws = ExcCourtBcChargesPartiesVO.createRowSetIterator(null);
            //                                                if(ExcCourtBcChargesPartiesrws.hasNext())
            //                                                {
            //                                                      Row ExcCourtBcChargesPartiesrw =  ExcCourtBcChargesPartiesrws.next();
            //                                                     BigDecimal DecisionId= (BigDecimal) ExcCourtBcChargesPartiesrw.getAttribute("Id");
            //                                                    System.err.println("%ExcCourtBcChargesPartiesrws%"+DecisionId);
            //
            //                                                    ViewObject ExcCourtBcChargeDecisionsVO = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
            //                                                    RowSetIterator ExcCourtBcChargeDecisionsrws = ExcCourtBcChargeDecisionsVO.createRowSetIterator(null);
            //                                                    while(ExcCourtBcChargeDecisionsrws.hasNext())
            //                                                    {
            //                                                          Row ExcCourtBcChargeDecisionsrw =  ExcCourtBcChargeDecisionsrws.next();
            //                                                         BigDecimal ExcCourtBcChargeDecisionsId = (BigDecimal) ExcCourtBcChargeDecisionsrw.getAttribute("Id");
            //                                                        System.err.println("%ExcCourtBcChargeDecisionsrws%"+ExcCourtBcChargeDecisionsId);
            //                                                        if (ExcCourtBcChargeDecisionsId.compareTo(DecisionId)==0){
            //                                                            ParPartiesvo.setCurrentRow(ExcCourtBcChargeDecisionsrw);
            //                                                        break;
            //                                                        }
            //                                                        }
            //
            //                        //
            //                                                }
            //                                }
            //
            //                            }
            // it14

        }
        //        callJsFunction("pressButton");
        this.getSaveBTN().setDisabled(false);
        this.getSaveReport25().setDisabled(false);
        JSFUtils.refreshComponent("b1");
        JSFUtils.refreshComponent("ph8");
        JSFUtils.refreshComponent("ph4");
        JSFUtils.refreshComponent("pg21");
        JSFUtils.refreshComponent("pgl1");
        JSFUtils.refreshComponent("it14");
        JSFUtils.refreshComponent("it81");

    }

    public void discolsePI(DisclosureEvent disclosureEvent) {
        // Add event code here...

        Map<BigDecimal, BigDecimal> map = (Map<BigDecimal, BigDecimal>) ADFContext.getCurrent()
                                                                                  .getSessionScope()
                                                                                  .get("ChargMap");
        if (disclosureEvent.isExpanded() == true) {
            String PartyID = disclosureEvent.getComponent()
                                            .getAttributes()
                                            .get("PartyID")
                                            .toString();
            String CourtBcHearingResPartyId = disclosureEvent.getComponent()
                                                             .getAttributes()
                                                             .get("CourtBcHearingResPartyId")
                                                             .toString();

            System.out.println("Disclose " + PartyID + " CourtBcHearingResId " + CourtBcHearingResPartyId);
            BigDecimal partID = new BigDecimal(PartyID);
            BigDecimal ReservaionParyId = new BigDecimal(CourtBcHearingResPartyId);


            //

            ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            System.out.println("CourtBcHearingResPartiesVO1Iterator " + vo.getRowCount());
            RowSetIterator rws = vo.createRowSetIterator(null);
            while (rws.hasNext()) {
                Row rw = rws.next();
                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
                BigDecimal PartyId = (BigDecimal) rw.getAttribute("PartyId");
                System.err.println(ReservaionParyId + "%%" + rwsPartyId);
                if (rwsPartyId.compareTo(ReservaionParyId) == 0) {


                    vo.setCurrentRow(rw);
                    break;
                }
            }

            JSFUtils.refreshComponent("pt5");

            //            ViewObject vo2 = ADFUtil.findIterator("ExcFolderDataVO1Iterator").getViewObject();
            //            System.out.println("ExcFolderDataVO1Iterator " + vo.getRowCount());
            //            RowSetIterator rws2 = vo2.createRowSetIterator(null);
            //            while (rws2.hasNext()) {
            //                Row rw = rws2.next();
            //                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("ResPartyId");
            //                System.err.println(ReservaionParyId + "%%" + rwsPartyId);
            //                if (rwsPartyId.compareTo(ReservaionParyId) == 0) {
            //                    vo2.setCurrentRow(rw);
            //                }
            //            }
            ////            vo2.executeQuery();
            ////            ExcCourtBcChargeDecisionsVO1
            //
            BigDecimal ChargeDecisionId = new BigDecimal(0);
            //                        ViewObject vo3 = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
            //                        System.out.println("ExcCourtBcChargesPartiesVO2Iterator " + vo.getRowCount());
            //                        RowSetIterator rws3 = vo3.createRowSetIterator(null);
            //                        while (rws3.hasNext()) {
            //                            Row rw = rws3.next();
            //                            BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("ResPartyId");
            //                              ChargeDecisionId = (BigDecimal) rw.getAttribute("ChargeDecisionId");
            //                            System.out.println("ChargeDecisionId  "+ChargeDecisionId);
            //                            System.err.println(ReservaionParyId + "%*******%" + rwsPartyId);
            //                            if (rwsPartyId.compareTo(ReservaionParyId) == 0) {
            //                                vo3.setCurrentRow(rw);
            //            break;
            //
            //
            //                            }
            //                        }


            //
            boolean ccc = true;
            if (ADFContext.getCurrent()
                          .getSessionScope()
                          .get("ChargMap") != null) {
                System.err.println("map         : " + map.size());
                for (Map.Entry<BigDecimal, BigDecimal> entry : map.entrySet()) {
                    if (ccc) {
                        System.out.println(entry.getKey() + " ===================== " +
                                           entry.getValue()); //ChargeDecisionId
                        ViewObject viewImp =
                            ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
                        System.out.println(viewImp.getRowCount() + ">>>>>>>>>>>>>>>>>>>");

                        RowSetIterator rws4 = viewImp.createRowSetIterator(null);
                        while (rws4.hasNext()) {
                            Row rw4 = rws4.next();
                            BigDecimal rwsPartyId = (BigDecimal) rw4.getAttribute("Id");
                            BigDecimal resPartyId = (BigDecimal) rw4.getAttribute("ResPartyId");
                            String val = (String) entry.getValue().toString();
                            String val2 = rwsPartyId.toString();
                            System.out.println("val" + val + "-");
                            System.out.println("val" + val2 + "-");
                            System.err.println(map.entrySet() + "%%" + rwsPartyId);
                            if (val2.compareToIgnoreCase(val) == 0 && resPartyId.compareTo(ReservaionParyId) == 0) {

                                rw4.setAttribute("ChargeDecisionId", entry.getKey());
                                ChargeDecisionId = (BigDecimal) entry.getKey();
                                System.out.println(rwsPartyId + " HGH  " + ChargeDecisionId + "   " +
                                                   val2.compareToIgnoreCase(val));
                                viewImp.setCurrentRow(rw4);
                                ccc = false;
                                break;

                            }
                        }

                        //                                  viewImp.createRowSetIterator(null).next().setAttribute("ChargeDecisionId", entry.getKey());
                    }
                }


            }

            //

            //            vo3.executeQuery();

            //
            ViewObject vo4 = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
            System.out.println("ExcCourtBcChargeDecisionsVO1Iterator " + vo4.getRowCount());
            RowSetIterator rws4 = vo4.createRowSetIterator(null);
            while (rws4.hasNext()) {
                Row rw4 = rws4.next();
                BigDecimal Id = (BigDecimal) rw4.getAttribute("Id");
                System.err.println(ChargeDecisionId + "%---------------%" + Id);
                if (Id.compareTo(ChargeDecisionId) == 0) {
                    //                    System.out.println("YEARS   "+rw4.getAttribute("ImprisonmentYears"));
                    //                    System.out.println("moths   "+rw4.getAttribute("ImprisonmentMonths"));
                    //                    System.out.println("days   "+rw4.getAttribute("ImprisonmentDays"));
                    //
                    //                    JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", rw4.getAttribute("ImprisonmentYears"));

                    vo4.setCurrentRow(rw4);
                    break;
                }
            }
            //                        vo4.executeQuery();

            System.out.println("vo " + vo.getRowCount());


            ViewObject ParPartiesvo = ADFUtil.findIterator("ParPartiesVO1Iterator").getViewObject();
            RowSetIterator ParPartiesrws = ParPartiesvo.createRowSetIterator(null);
            while (ParPartiesrws.hasNext()) {
                Row rw = ParPartiesrws.next();
                BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
                System.err.println("%%" + partID);
                if (rwsPartyId.compareTo(partID) == 0) {
                    ParPartiesvo.setCurrentRow(rw);
                    break;
                }
            }
            //            ParPartiesvo.executeQuery();

            //            ViewObject FormCaseDescriptionPartiesvo =
            //                ADFUtil.findIterator("FormCaseDescriptionPartiesVO2Iterator").getViewObject();
            //            BigDecimal FormCaseDescriptionPartiesId =
            //                (BigDecimal) FormCaseDescriptionPartiesvo.getCurrentRow().getAttribute("Id");
            //            System.err.println("Row " + FormCaseDescriptionPartiesId);
            //            ViewObject FormCaseDescriptionDesPartvo =
            //                ADFUtil.findIterator("FormCaseDescriptionDesPartVO2Iterator").getViewObject();
            //            System.err.println("COUNT " + FormCaseDescriptionDesPartvo.getRowCount());
            //
            //
            //            RowSetIterator FormCaseDescriptionDesPartrws = FormCaseDescriptionDesPartvo.createRowSetIterator(null);
            //                        if(FormCaseDescriptionDesPartrws.hasNext())
            //                        {
            //                              Row rw =  FormCaseDescriptionDesPartrws.next();
            //                             BigDecimal rwsPartyId = (BigDecimal) rw.getAttribute("Id");
            //                            System.err.println("%FormCaseDescriptionDesPartrws%"+partID);
            //                            {
            //                                                ViewObject ExcCourtBcChargesPartiesVO = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
            //
            //                                                RowSetIterator ExcCourtBcChargesPartiesrws = ExcCourtBcChargesPartiesVO.createRowSetIterator(null);
            //                                                if(ExcCourtBcChargesPartiesrws.hasNext())
            //                                                {
            //                                                      Row ExcCourtBcChargesPartiesrw =  ExcCourtBcChargesPartiesrws.next();
            //                                                     BigDecimal DecisionId= (BigDecimal) ExcCourtBcChargesPartiesrw.getAttribute("Id");
            //                                                    System.err.println("%ExcCourtBcChargesPartiesrws%"+DecisionId);
            //
            //                                                    ViewObject ExcCourtBcChargeDecisionsVO = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
            //                                                    RowSetIterator ExcCourtBcChargeDecisionsrws = ExcCourtBcChargeDecisionsVO.createRowSetIterator(null);
            //                                                    while(ExcCourtBcChargeDecisionsrws.hasNext())
            //                                                    {
            //                                                          Row ExcCourtBcChargeDecisionsrw =  ExcCourtBcChargeDecisionsrws.next();
            //                                                         BigDecimal ExcCourtBcChargeDecisionsId = (BigDecimal) ExcCourtBcChargeDecisionsrw.getAttribute("Id");
            //                                                        System.err.println("%ExcCourtBcChargeDecisionsrws%"+ExcCourtBcChargeDecisionsId);
            //                                                        if (ExcCourtBcChargeDecisionsId.compareTo(DecisionId)==0){
            //                                                            ParPartiesvo.setCurrentRow(ExcCourtBcChargeDecisionsrw);
            //                                                        break;
            //                                                        }
            //                                                        }
            //
            //                        //
            //                                                }
            //                                }
            //
            //                            }
            // it14

        }
        //        callJsFunction("pressButton");
        this.getSaveBTN().setDisabled(false);
        //        this.getSaveReport25().setDisabled(false);
        JSFUtils.refreshComponent("b1");
        JSFUtils.refreshComponent("ph8");
        JSFUtils.refreshComponent("ph4");
        JSFUtils.refreshComponent("pg21");
        JSFUtils.refreshComponent("pgl1");
        JSFUtils.refreshComponent("it14");
        JSFUtils.refreshComponent("it81");

    }


    public void setPartyID(BigDecimal partyID) {
        this.partyID = partyID;
    }

    public BigDecimal getPartyID() {
        return partyID;
    }

    // upload BusinessCase Attach

    private UploadedFile fileSessionRepor;
    private UploadedFile fileJudgeDecision;
    private UploadedFile fileNeyaba;
    private UploadedFile fileDocument;
    private UploadedFile filePolice;
    private UploadedFile fileDocument28;
    private UploadedFile fileDocument29;

    public void setFileJudgeDecision(UploadedFile fileJudgeDecision) {
        this.fileJudgeDecision = fileJudgeDecision;
    }

    public UploadedFile getFileJudgeDecision() {
        return fileJudgeDecision;
    }

    public void setFileNeyaba(UploadedFile fileNeyaba) {
        this.fileNeyaba = fileNeyaba;
    }

    public UploadedFile getFileNeyaba() {
        return fileNeyaba;
    }

    public void setFileDocument(UploadedFile fileDocument) {
        this.fileDocument = fileDocument;
    }

    public UploadedFile getFileDocument() {
        return fileDocument;
    }

    public void setFilePolice(UploadedFile filePolice) {
        this.filePolice = filePolice;
    }

    public UploadedFile getFilePolice() {
        return filePolice;
    }


    public void setFileSessionRepor(UploadedFile fileSessionRepor) {
        this.fileSessionRepor = fileSessionRepor;
    }

    public UploadedFile getFileSessionRepor() {
        return fileSessionRepor;
    }

    public String uploadFileAttach(BigDecimal type, InputStream inputStream, UploadedFile uploadedFile) {


        String documentId = "";
        String dDocName = "";
        try {
            DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(inputStream, 1, 1, 1, 1, uploadedFile.getFilename());
            documentId = do_.get("dID");
            dDocName = do_.get("dDocName");
            System.out.println(uploadedFile.getFilename() + " ---------------- documentId  " + documentId);

            //            vo.getCurrentRow().setAttribute("DocumentId", new BigDecimal (documentId));
            //            OperationBinding Operation = ADFUtil.findOperation("addAttachBC");
            //            Operation.getParamsMap().put("attachType", type);
            //            Operation.getParamsMap().put("documentId", new BigDecimal(documentId));
            //            Operation.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documentId + "," + dDocName;
    }

    public void upload11Document(ValueChangeEvent valueChangeEvent) {

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(11), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(10), null,
                              null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAttacheFile(BigDecimal businessCaseDetailId, BigDecimal businessCaseId, BigDecimal userId,
                                  String did, String Ddocname, BigDecimal entityId, BigDecimal partyId, String fileName,
                                  BigDecimal typeId, BigDecimal Parid, BigDecimal caseSchemaId,
                                  BigDecimal procedureId) {
        System.out.println("------insertAttacheFile-------------");

        OperationBinding myOperation = ADFUtil.findOperation("insertNewCriminalJusticeFilesDetailsVO2");
        myOperation.getParamsMap().put("businessCaseDetailId", businessCaseDetailId);
        myOperation.getParamsMap().put("businessCaseId", businessCaseId);
        myOperation.getParamsMap().put("createdBy", userId);
        myOperation.getParamsMap().put("did", did);
        myOperation.getParamsMap().put("Ddocname", Ddocname);

        myOperation.getParamsMap().put("entityId", entityId);
        myOperation.getParamsMap().put("partyId", partyId);
        myOperation.getParamsMap().put("fileName", fileName);
        myOperation.getParamsMap().put("typeId", typeId);
        myOperation.getParamsMap().put("Parid", Parid);
        myOperation.getParamsMap().put("caseSchemaId", caseSchemaId);
        myOperation.getParamsMap().put("procedureId", procedureId);
        myOperation.execute();
    }


    public void upload28Document(ValueChangeEvent valueChangeEvent) {

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(28), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(28), null,
                              null, null);
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if4");
            file.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void upload29Document(ValueChangeEvent valueChangeEvent) {

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(33), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(33), null,
                              null, null);
            
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if7");
            file.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadType10ReportPolice(ValueChangeEvent valueChangeEvent) {
        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(11), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(11), null,
                              null, null);
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if6");
            file.setValue(null);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadType4Session(ValueChangeEvent valueChangeEvent) {
        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(11), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(12), null,
                              null, null);
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if5");
            file.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uploadType9Neyaba(ValueChangeEvent valueChangeEvent) {
        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(11), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(13), null,
                              null, null);
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if3");
            file.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadType5HokmAlqdy(ValueChangeEvent valueChangeEvent) {

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = uploadFileAttach(new BigDecimal(11), is, uf);
            String[] args = documentId.split(",");

            String DID = args[0];
            String DName = args[1];

            System.err.println("Files  --------------");

            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            Row businessCaseRow = businessCaseView.getCurrentRow();

            ViewObject courtBcHearingResPartiesView =
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId), DID, DName,
                              new BigDecimal(entityId), new BigDecimal(partyId), filName, new BigDecimal(14), null,
                              null, null);
            
            RichInputFile file = (RichInputFile) JSFUtils.findComponentInRoot("if2");
            file.setValue(null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String radio_OneParty() {
        // Add event code here...
        String decision = "0";
        if (ADFContext.getCurrent()
                      .getPageFlowScope()
                      .get("decision") != null)
            decision = (String) ADFContext.getCurrent()
                                          .getPageFlowScope()
                                          .get("decision");
        if (decision.compareTo("0") == 0) {

            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            JSFUtils.refreshComponent("b1");
            RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
            CustomSave.setRendered(false);


            RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
            oneParty.setRendered(true);
            RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
            manyParty.setRendered(false);

            System.out.println("One Party");
            JSFUtils.addToPageFlowScope("BCFound", false);
            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "false");
            this.getCheckFlag().setValue(false);
            this.getDetails().setRendered(true);
            //        searchImge.setRendered(false);
            //        searchImge.setVisible(false);
            JSFUtils.addToPageFlowScope("decision", "1");

            JSFUtils.setExpressionValue("#{pageFlowScope.judgText}", null);
            //        RichButton lov = (RichButton) JSFUtils.findComponentInRoot("b19");
            //        lov.setSelected(true);
            //        lov.setDisabled(true);


            ADFUtil.findOperation("addNewconvicted_1_1").execute();
            JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
                                        "\u0627\u0644\u0645\u062A\u0647\u0645 \u0627\u0644\u0623\u0648\u0644  ");
            //            ArabicLiteralNumberParser.literalValueOf(order);

            //        ADFUtil.findOperation("CreateInsertFolderData").execute();
            this.getSaveBTN().setDisabled(true);
            this.getSaveReport25().setDisabled(true);
            JSFUtils.refreshComponent("pg29");
            JSFUtils.refreshComponent("pg48");
            JSFUtils.refreshComponent("ph5");
         
        }
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
            JSFUtils.setExpressionValue("#{bindings.ManualSeq1.inputValue}", "Y");
            System.out.println("ManualSeq-- 555 ----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
        return null;
    }

    public String newReservation() {

        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);

        RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
        newRes.setDisabled(true);

        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        oneParty.setRendered(true);
        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        manyParty.setRendered(false);

        this.getSaveBTN().setDisabled(false);
        this.getDetails().setRendered(true);
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        degreeChoice.setDisabled(true);

        ADFUtil.findOperation("newSessionJudg").execute();
        ADFUtil.findOperation("addNewconvicted_1_1").execute();
        this.getSaveBTN().setDisabled(false);
        this.getSaveReport25().setDisabled(false);
        ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
        vo.createRowSetIterator(null).last();
        JSFUtils.refreshComponent("b1");
        //        JSFUtils.refreshComponent("pg51");
        ADFUtil.setBoundAttributeValue("JudgmentType", BigDecimal.ONE );

        return null;
    }
    
    public String newGonahReservationPI(){
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
            CustomSave.setRendered(false);

            RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
            newRes.setDisabled(true);

            //        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
            //        oneParty.setRendered(true);
            //        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
            //        manyParty.setRendered(false);

            this.getSaveBTN().setDisabled(false);
            this.getDetails().setRendered(true);
            RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
            degreeChoice.setDisabled(true);

            ADFUtil.findOperation("gonahReservation").execute();
            // ADFUtil.findOperation("Commit").execute();
            JSFUtils.addToSession("skipgonahReservation", true);
            return null;    
        }
    
    public String newReservationGnayatPI() {
        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);

        RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
        newRes.setDisabled(true);

        //        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        //        oneParty.setRendered(true);
        //        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        //        manyParty.setRendered(false);

        this.getSaveBTN().setDisabled(false);
        this.getDetails().setRendered(true);
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        degreeChoice.setDisabled(true);

        ADFUtil.findOperation("generateReservation").execute();
       // ADFUtil.findOperation("Commit").execute();

        return null;  
    }


    public String newReservationPI() {

        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);

        RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
        newRes.setDisabled(true);

        //        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        //        oneParty.setRendered(true);
        //        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        //        manyParty.setRendered(false);

        this.getSaveBTN().setDisabled(false);
        this.getDetails().setRendered(true);
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        degreeChoice.setDisabled(true);

        ADFUtil.findOperation("newSessionJudg").execute();
        //        ADFUtil.findOperation("addNewconvicted_1_1").execute();
        newFirstResParty();
        this.getSaveBTN().setDisabled(false);
        //        this.getSaveReport25().setDisabled(false);
        ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
        vo.createRowSetIterator(null).last();
        JSFUtils.refreshComponent("b1");
        //        JSFUtils.refreshComponent("pg51");

        return null;
    }


    public void newFirstResParty() {
        RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");


        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        manyParty.setRendered(true);

        String decision = "0";
        if (ADFContext.getCurrent()
                      .getPageFlowScope()
                      .get("decision") != null)
            decision = (String) ADFContext.getCurrent()
                                          .getPageFlowScope()
                                          .get("decision");
        if (decision.compareTo("0") == 0) {
            System.out.println("Many  Party");
            JSFUtils.addToPageFlowScope("BCFound", false);
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "true");
            this.getCheckFlag().setValue(true);
            this.getDetails().setRendered(true);
            //        searchImge.setRendered(false);
            //        searchImge.setVisible(false);
            JSFUtils.addToPageFlowScope("decision", "2");

            //        RichButton radioBtnManyPart = (RichButton) JSFUtils.findComponentInRoot("21");
            //        radioBtnManyPart.setSelected(true);
            //        radioBtnManyPart.setDisabled(true);

            ADFUtil.findOperation("addNewconvictedMany").execute();
            JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
                                        " \u0627\u0644\u0645\u062A\u0647\u0645 \u0627\u0644\u0623\u0648\u0644 ");
        }
    }

    public String radio_ManyParty() {
        // Add event code here...
        RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
        RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
        RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
        RichPanelHeader phCrimeMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");

        phCrimeMany.setRendered(true);
        phChargMany.setRendered(true);
        phDescOne.setRendered(false);
        phChargOne.setRendered(false);

        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(false);
        JSFUtils.refreshComponent("b1");
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(true);
        this.getSaveBTN().setRendered(false);
        this.getSaveReport25().setRendered(false);
        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        oneParty.setRendered(false);
        String decision = "0";
        if (ADFContext.getCurrent()
                      .getPageFlowScope()
                      .get("decision") != null)
            decision = (String) ADFContext.getCurrent()
                                          .getPageFlowScope()
                                          .get("decision");
        if (decision.compareTo("0") == 0) {
            newFirstResParty();
            //            ArabicLiteralNumberParser.literalValueOf(order);
            ADFUtil.findOperation("CreateInsertFolderData").execute();
            ADFUtil.findOperation("CreateInsert2").execute();
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "true");

            JSFUtils.refreshComponent("ph7");
            
        }
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
            JSFUtils.setExpressionValue("#{bindings.ManualSeq1.inputValue}", "Y");
            System.out.println("ManualSeq-- 66 ----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
        return null;
    }

    public void setCheckFlag(RichSelectBooleanCheckbox checkFlag) {
        this.checkFlag = checkFlag;
    }

    public RichSelectBooleanCheckbox getCheckFlag() {
        return checkFlag;
    }

    public void setBtNMode(RichPanelLabelAndMessage btNMode) {
        this.btNMode = btNMode;
    }

    public RichPanelLabelAndMessage getBtNMode() {
        return btNMode;
    }

    public void setReturnFlag(RichSelectBooleanCheckbox returnFlag) {
        this.returnFlag = returnFlag;
    }

    public RichSelectBooleanCheckbox getReturnFlag() {
        return returnFlag;
    }

    public void setBtnOneParty(RichButton btnOneParty) {
        this.btnOneParty = btnOneParty;
    }

    public RichButton getBtnOneParty() {
        return btnOneParty;
    }

    public void setBtnManyPary(RichButton btnManyPary) {
        this.btnManyPary = btnManyPary;
    }

    public RichButton getBtnManyPary() {
        return btnManyPary;
    }


    public String cancelPI() {
        // Add event code here...
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");
        JSFUtils.addToPageFlowScope("backLog", null);
        JSFUtils.addToPageFlowScope("detail", false);
        this.getDetails().setRendered(false);
        if (this.getBtNMode() != null)
            this.getBtNMode().setVisible(false);
        if (this.getBtnMode2() != null)
            this.getBtnMode2().setVisible(false);
        this.getReturnFlag().setValue("false");

        not_Exisit_Label.setVisible(false);
        exisit_Label.setVisible(false);
        error_label.setVisible(false);
        //        searchImge.setRendered(true);
        //        searchImge.setVisible(true);
        JSFUtils.addToPageFlowScope("disabledValidate", false);
        JSFUtils.addToPageFlowScope("decision", "0");
        JSFUtils.addToPageFlowScope("back", "0");
        this.getSaveBTN().setDisabled(true);

        fileSessionRepor = null;
        fileJudgeDecision = null;
        fileNeyaba = null;
        fileDocument = null;
        filePolice = null;
        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
        btnOne.setSelected(false);
        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
        btnMany.setSelected(false);

        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
        btnNew.setSelected(false);
        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
        btnOld.setSelected(false);


        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);
        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
        searchBtn.setDisabled(false);


        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        manyParty.setRendered(false);

        ADFUtil.findOperation("Rollback").execute();
        return null;
    }

    public String Cancel() {
        // Add event code here...
        JSFUtils.addToSession("pSequence", "");
        JSFUtils.addToSession("pYear", "");
        JSFUtils.addToSession("pBCType", "");
        JSFUtils.addToSession("pType", "");
        JSFUtils.addToSession("pPolice", "");
        JSFUtils.addToPageFlowScope("backLog", null);
        JSFUtils.addToPageFlowScope("detail", false);
        this.getDetails().setRendered(false);
        if (this.getBtNMode() != null)
            this.getBtNMode().setVisible(false);
        if (this.getBtnMode2() != null)
            this.getBtnMode2().setVisible(false);
        this.getReturnFlag().setValue("false");

        not_Exisit_Label.setVisible(false);
        exisit_Label.setVisible(false);
        error_label.setVisible(false);
        //        searchImge.setRendered(true);
        //        searchImge.setVisible(true);
        JSFUtils.addToPageFlowScope("disabledValidate", false);
        JSFUtils.addToPageFlowScope("decision", "0");
        JSFUtils.addToPageFlowScope("back", "0");
        this.getSaveBTN().setDisabled(true);
        this.getSaveReport25().setDisabled(true);
        fileSessionRepor = null;
        fileJudgeDecision = null;
        fileNeyaba = null;
        fileDocument = null;
        filePolice = null;
        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
        btnOne.setSelected(false);
        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
        btnMany.setSelected(false);

        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
        btnNew.setSelected(false);
        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
        btnOld.setSelected(false);


        RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
        RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
        RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
        RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
        phDescOne.setRendered(true);
        phChargOne.setRendered(true);
        phChargMany.setRendered(false);
        phDescMany.setRendered(false);


        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);
        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
        searchBtn.setDisabled(false);

        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        oneParty.setRendered(true);
        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        manyParty.setRendered(false);

        ADFUtil.findOperation("Rollback").execute();
        return null;
    }

    public void setSearchImge(RichPanelGroupLayout searchImge) {
        this.searchImge = searchImge;
    }

    public RichPanelGroupLayout getSearchImge() {
        return searchImge;
    }

    public void loadSessionScope() {


        SimplifiedMode sm = new SimplifiedMode();
        sm.setAddress(JSFUtils.resolveExpressionAsString("#{bindings.Address.inputValue}"));
        JSFUtils.storeOnSession("SM", sm);
    }

    public void loadSM() {
        SimplifiedMode sm = new SimplifiedMode();

        if (JSFUtils.resolveExpression("#{bindings.Id9.inputValue}") == null) {
            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null)
                sm.setNeyabaa(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
            else
                sm.setNeyabaa("" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));

        } else {
            sm.setNeyabaa(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId());
        }


        JSFUtils.storeOnSession("SM", sm);

    }

    public String newOneParty() {
        // Add event code here...
        //        if (JSFUtils.resolveExpression("#{bindings.Id9.inputValue}") == null)
        //        {
        //            JSFUtils.addToSession("Neyabaa", ADFContext.getCurrent().getSessionScope().get("entityId1"));
        //
        //            }
        //        else
        //        {
        //                JSFUtils.addToSession("Neyabaa", ADFContext.getCurrent().getSessionScope().get("entityId2"));
        //            }
        //        ADFUtil.findOperation("validateCourtBCHearingDate").execute();
        //        Object execute = ADFUtil.findOperation("Commit").execute();
        //        System.err.println("SAVE COMMIT"+execute);

        if (ValidatePenaltyParty()) {
            loadSessionScope();
            this.getSaveBTN().setDisabled(true);
            this.getSaveReport25().setDisabled(true);
            partyStatus();
            ADFUtil.findOperation("addNewconvicted_1_1").execute();
            //        Object expression = JSFUtils.resolveExpression("#{bindings.PartyOrder1.inputValue}");


            //        JSFUtils.setExpressionValue("#{bindings.PartyOrder1.inputValue}","ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ");
            //            ArabicLiteralNumberParser.literalValueOf(order);

            JSFUtils.setExpressionValue("#{bindings.PartyOrder.inputValue}", this.getCounting());
            BigDecimal order = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyOrder.inputValue}");

            if (JSFUtils.resolveExpressionAsBoolean("#{bindings.Id9.inputValue != null}"))
                JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", (new BigDecimal(2)));


            BigDecimal typeFlag = new BigDecimal(0);

            if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                typeFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");


            if (typeFlag.compareTo(new BigDecimal(3643)) == 0) {
                if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));

            }


            JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
                                        "\u0627\u0644\u0645\u062A\u0647\u0645 " +
                                        ArabicLiteralNumberParser.literalValueOf(order.add(new BigDecimal(1))));
            this.setCounting(this.getCounting().add(new BigDecimal(1)));

            BigDecimal flag = new BigDecimal(1);
                if (JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}")!= null)
              flag =   (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
            System.out.println("-------------------------------------flag  ----  " + flag);
            if (flag.compareTo(new BigDecimal(2)) == 0) {
                BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
                if (bcType.compareTo(new BigDecimal(1))==1){
                JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(3)));
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", (new BigDecimal(3)));
                JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", (new BigDecimal(3)));
                }
                JSFUtils.refreshComponent("soc3");
            }


            //        ADFUtil.findOperation("CreateInsertFolderData").execute();

        } else {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "PENALTY_MESSAGE");
        }
        return null;
    }

    public String newManyParty() {
        // Add event code here...
        loadSessionScope();
        partyStatus();
        ADFUtil.findOperation("addNewconvictedMany").execute();
        ADFUtil.findOperation("CreateInsertFolderData").execute();
        JSFUtils.setExpressionValue("#{bindings.PartyOrder.inputValue}", this.getCounting());
        BigDecimal order = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyOrder.inputValue}");
        BigDecimal flag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
        System.out.println("flag  ----  " + flag);
        if (flag.compareTo(new BigDecimal(2)) == 0) {
            BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
            if (bcType.compareTo(new BigDecimal(1))==1){
            JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(3)));
            JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", (new BigDecimal(3)));
          JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", (new BigDecimal(3)));
            }
            JSFUtils.refreshComponent("soc3");
        }
        if (flag.compareTo(new BigDecimal(3)) == 0) {
            BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
            if (bcType.compareTo(new BigDecimal(1))==1){
       JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(3)));
            }
        }

        if (JSFUtils.resolveExpressionAsBoolean("#{bindings.Id9.inputValue != null}"))
            JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", (new BigDecimal(2)));

        JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
                                    " \u0627\u0644\u0645\u062A\u0647\u0645 " +
                                    ArabicLiteralNumberParser.literalValueOf(order.add(new BigDecimal(1))));
        this.setCounting(this.getCounting().add(new BigDecimal(1)));

        return null;
    }


    public String newPartyPI() {
        // Add event code here...
        loadSessionScope();
        partyStatus();
        ADFUtil.findOperation("addNewconvictedMany").execute();

        JSFUtils.setExpressionValue("#{bindings.PartyOrder.inputValue}", this.getCounting());
        BigDecimal order = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyOrder.inputValue}");


        JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
                                    " \u0627\u0644\u0645\u062A\u0647\u0645 " +
                                    ArabicLiteralNumberParser.literalValueOf(order.add(new BigDecimal(1))));
        this.setCounting(this.getCounting().add(new BigDecimal(1)));
        this.getSaveBTN().setDisabled(false);
        return null;
    }


    public String normalSave() {
        System.out.println(" JSFUtils.getFromPageFlowScope"+ JSFUtils.getFromPageFlowScope("backLog"));
        boolean flg = true ;
        String  BL = "false";
            if( JSFUtils.getFromPageFlowScope("backLog") != null )
           BL =   JSFUtils.getFromPageFlowScope("backLog").toString();
        if (BL.compareToIgnoreCase("true") ==0 )
        {
            if( JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!= null)
            {
                
                    int year = 2024;
                 
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null){
                        Date currentDate = sd.parse(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}") + "");
                          year=currentDate.getYear()+1900;
                        System.out.println("!!!!!---"+year);
                            JSFUtils.addToSession("PPYEAR", year);
                            if (year>=2029){
                                flg = false   ;
                                  
                            }}
             
            
            }
       
        catch(  Exception e)
                {
        
        
                }
                }
        }
        
        if (  flg){
        
        JSFUtils.addToSession("automationFlag", false);
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");
        JSFUtils.addToSession("judgeFlag", new BigDecimal (0));
        
        RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
        System.out.println("Save With origin id = "+inputPoliceNum.getValue().toString());
                if(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")==null){
                  JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}",inputPoliceNum.getValue().toString());
                  
                  
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("OrigEntity", new BigDecimal(inputPoliceNum.getValue().toString()));
                    
                }
       
        System.err.println("business-----------------" +
                           JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") +
                  "PoliceId-----------------"+JSFUtils.resolveExpression("#{bindings.PoliceId.inputValue}") );
//if(JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") !=null)
//        JSFUtils.addToSession("BC_ID",
//                             new BigDecimal( "" + JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}")));
//        if(JSFUtils.resolveExpression("#{bindings.PoliceId.inputValue}") !=null)
//        JSFUtils.addToSession("Police_ID",
//                             new BigDecimal( "" + JSFUtils.resolveExpression("#{bindings.PoliceId.inputValue}")));
        if (JSFUtils.resolveExpression("#{bindings.Source.inputValue}") != null) {
            String source = (String) JSFUtils.resolveExpression("#{bindings.Source.inputValue}");
            if (source.compareTo("police") == 0)
                JSFUtils.setExpressionValue("#{bindings.Source.inputValue}", "CriminalJustice");
        }

        JSFUtils.addToSession("judgeText", "00");

        if (ValidatePenaltyParty()) {
            actionTypes();
            getDegree();
            System.err.println("*********** Norma Save ************--" + ADFContext.getCurrent()
                                                                                   .getSessionScope()
                                                                                   .get("actionTypeId"));
//            JSFUtils.storeOnSession("actionTypeId", JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
            Boolean check = true;
            if (JSFUtils.resolveExpression("#{bindings.Source.inputValue}") == null)
                JSFUtils.setExpressionValue("#{bindings.Source.inputValue}", "CriminalJustice");
            // Add event code here...
            System.err.println("*****************   " + ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
            System.err.println("*****************   " +
                               JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            if (JSFUtils.resolveExpression("#{bindings.Id9.inputValue}") == null) {
                if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null)
                    JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
                else
                    JSFUtils.addToSession("Neyabaa",
                                          "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));

            } else {
                JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId());
            }


            BigDecimal LedgerId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CASE_SCHEMA_ID.inputValue}");
            JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", LedgerId);
            System.err.println(JSFUtils.getFromPageFlowScope("showDate") + "YYYYYYYYYYYYYYYYYYYYYYY     " +
                               JSFUtils.getFromPageFlowScope("backLog"));
            //        if (this.getReturnFlag().getValue() == "false")
            BigDecimal prosecutionJudegementFlag =
                (BigDecimal) JSFUtils.resolveExpression("#{bindings.ProsecutionJudegementFlag.inputValue}");
            System.err.println("prosecutionJudegementFlag" + prosecutionJudegementFlag);
            if (prosecutionJudegementFlag.compareTo(new BigDecimal(0)) == 0) {
            }
            if (JSFUtils.getFromPageFlowScope("showDate")
                        .toString()
                        .equalsIgnoreCase("true") && JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!= null) {
                check = (Boolean) ADFUtil.findOperation("validateCourtBCHearingDate").execute();
            } else
                ADFContext.getCurrent()
                          .getSessionScope()
                          .put("CheckHearingDate", 0);
            if (check) {
                if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}",
                                                JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
                BigDecimal flag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
                JSFUtils.addToSession("judgeFlag", flag);
                Boolean valide = false;
                if (JSFUtils.getFromPageFlowScope("newKolyeaBC")
                            .toString()
                            .compareTo("true") == 0)
                    valide = validateHasrKolyaa();
                if (!valide) {

                    partyStatus();
                    ADFUtil.findOperation("deleteReservations").execute();
                    try {
                        ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
                        System.err.println("--------****" + ADFUtil.findOperation("Commit").getErrors());

//                        ViewObject bcLogVO = ADFUtil.findIterator("BusinessCaseLogVO2Iterator").getViewObject();
//                        Row bcLogRow = bcLogVO.createRow();
////                        System.err.println("entityId = "+entityId);
//                        String entityId = ADFUtil.evaluateEL("#{bindings.NeyabGozayaa.attributeValue}")+"";
//                        String policeNo = ADFUtil.evaluateEL("#{bindings.PoliceNo.inputValue}")+"";
//                        String bcType = ADFUtil.evaluateEL("#{bindings.BusinessCaseType1.attributeValue}")+"";
//                        ViewObject entityVO = ADFUtil.findIterator("EntityROV1Iterator").getViewObject();
//                        entityVO.setWhereClause("ID = "+entityId);
//                        entityVO.executeQuery();
//                        RowSetIterator rws = entityVO.createRowSetIterator(null);
//                        String assignedTo = null;
//
//                        if (rws.hasNext()) {
//                           Row rw = rws.next();
//                           assignedTo = rw.getAttribute("EntityPresident")+"";
//                        }
//                        
//                        bcLogRow.setAttribute("EntityId", entityId);
//                        bcLogRow.setAttribute("CaseNumber", policeNo);
//                        bcLogRow.setAttribute("BusinessCaseId", ADFContext.getCurrent().getSessionScope().get("BC_ID"));
//                        bcLogRow.setAttribute("AssignedTo", new BigDecimal (assignedTo));
//                        bcLogRow.setAttribute("CaseType", bcType);
//                        bcLogVO.insertRow(bcLogRow);
                        
                        saveCommit();
//                    
                    } catch (Exception ex) {

                        System.out.println("HHHHHHHHHHHHHHHHh");
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALID_DATA");
                    }

                } else {

                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "HASR_BC_KOLYAA");

                }

            } else {
                System.out.println("WWWWWW");
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "DATE_RESERVATION");

            }

        } else {
               JSFUtils.showFacesErrorMessageFromBundle("bundle", "PENALTY_MESSAGE");            
        }
        }
        else
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALID_DATA");

        return null;
    }

    private boolean isFolderType() {
        System.out.println("isFolderType"+JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}"));
        Object folderType = JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
        if (null != folderType) {
            int folderTypeVar = Integer.parseInt(folderType.toString());
            if ((13 == folderTypeVar ||10 == folderTypeVar || 29 == folderTypeVar || 16 == folderTypeVar || 15 == folderTypeVar 
                 || 7 == folderTypeVar || 42 == folderTypeVar || 27 == folderTypeVar || 9 == folderTypeVar || 14 == folderTypeVar 
                 || 17 == folderTypeVar || 18 == folderTypeVar ||  19 == folderTypeVar   ||
                 36 == folderTypeVar ||34 == folderTypeVar || 9 == folderTypeVar || 23 == folderTypeVar || 31 == folderTypeVar 
                 || 39 == folderTypeVar || 40 == folderTypeVar || 41 == folderTypeVar || 43 == folderTypeVar || 26 == folderTypeVar ||
               23 == folderTypeVar || 45 == folderTypeVar || 46 == folderTypeVar || 47 == folderTypeVar || 48 == folderTypeVar  
                 || 49 == folderTypeVar || 32 == folderTypeVar || 33 == folderTypeVar )
            ||
                (62 == folderTypeVar ||60 == folderTypeVar || 79 == folderTypeVar || 66 == folderTypeVar || 65 == folderTypeVar 
                                 || 57 == folderTypeVar || 92 == folderTypeVar || 77 == folderTypeVar || 69 == folderTypeVar || 64 == folderTypeVar 
                                 || 67 == folderTypeVar || 68 == folderTypeVar ||  69 == folderTypeVar   ||
                                 86 == folderTypeVar ||84 == folderTypeVar || 59 == folderTypeVar || 73 == folderTypeVar || 91 == folderTypeVar 
                                 || 89 == folderTypeVar || 90 == folderTypeVar || 91 == folderTypeVar || 93 == folderTypeVar || 76 == folderTypeVar ||
                               73 == folderTypeVar || 95 == folderTypeVar || 96 == folderTypeVar || 97 == folderTypeVar || 98 == folderTypeVar  
                                 || 99 == folderTypeVar || 82 == folderTypeVar || 83 == folderTypeVar ||72 == folderTypeVar ||74 == folderTypeVar 
                 ||75 == folderTypeVar ||80 == folderTypeVar  ||81 == folderTypeVar ||85 == folderTypeVar ||87 == folderTypeVar  ||88 == folderTypeVar)
            
            || 
                (105 == folderTypeVar || 144 == folderTypeVar ||  113 == folderTypeVar ||110 == folderTypeVar  ||112 == folderTypeVar  || 129 == folderTypeVar || 116 == folderTypeVar || 115 == folderTypeVar 
                            || 125 == folderTypeVar      || 107 == folderTypeVar || 142 == folderTypeVar || 128 == folderTypeVar  || 127 == folderTypeVar || 111 == folderTypeVar || 114 == folderTypeVar 
                                 || 117 == folderTypeVar || 118 == folderTypeVar ||  119 == folderTypeVar   ||
                                 137 == folderTypeVar ||134 == folderTypeVar || 109 == folderTypeVar || 123 == folderTypeVar || 131 == folderTypeVar 
                                 || 139 == folderTypeVar || 140 == folderTypeVar || 141 == folderTypeVar || 143 == folderTypeVar || 126 == folderTypeVar ||
                               124 == folderTypeVar || 145 == folderTypeVar || 146 == folderTypeVar || 147 == folderTypeVar || 148 == folderTypeVar  
                                 || 149 == folderTypeVar || 132 == folderTypeVar || 133 == folderTypeVar  || 130 == folderTypeVar || 135 == folderTypeVar || 138 == folderTypeVar )
            
            ) {
                return true;
            }
        }
        return false;
    }
    
    public void saveCommit() {
        if (ADFUtil.findIterator("ExcCourtBcDecisionFeesVO1Iterator")
                   .getViewObject()
                   .getRowCount() > 0) {
        
        Boolean manualSeq = (Boolean) JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}");
        if(!manualSeq && JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"))
        JSFUtils.showPopup("p3");
        else{
                  codeSave();
        }
        } else
        JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALIDATE_FEES");
    
    }
    
    
    public void custSaveCommit() {
        if (ADFUtil.findIterator("ExcCourtBcDecisionFeesVO1Iterator")
                   .getViewObject()
                   .getRowCount() > 0) {
        
        Boolean manualSeq = (Boolean) JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}");
        if(!manualSeq && JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"))
        JSFUtils.showPopup("p5");
        else{
                setFolderId();
                  custSave();
        }
        } else
        JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALIDATE_FEES");
    
    }
    public void insertFolderDate()
      {
          int year = 2020;
          int years=2020 ;
          SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
          try {
              if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null){
              Date currentDate = sd.parse(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}") + "");
                year=currentDate.getYear()+1900;
              System.out.println("!!!!!---"+year);
                  JSFUtils.addToSession("PPYEAR", year);
                  if (year>2022){
                      JSFUtils.addToSession("automationFlag", true);
                      ADFContext.getCurrent().getSessionScope().put("automationYear",year+"");
                  }
              }
              else if (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")!=null){
                              OperationBinding myOperation = ADFUtil.findOperation("getDatesHearing");
                              myOperation.getParamsMap().put("id", new BigDecimal (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")+""));
                          String dateQuery = (String) myOperation.execute();
                          System.out.println(dateQuery+"dateQuery");
                           years= Integer.parseInt(dateQuery);
                              System.out.println("!!!!!"+years);
                                  JSFUtils.addToSession("PPYEAR", years);
                                  if (years>2022){
                                      JSFUtils.addToSession("automationFlag", true);
                                      ADFContext.getCurrent().getSessionScope().put("automationYear",years+""); 
                                  }
                          }
          } catch (ParseException e) {
          }
        
//          String judgmentSubType=(String)JSFUtils.resolveExpression("#{bindings.JudgmentSubType.inputValue}");
                Row currentRow = ADFUtil.findIterator("CourtBcHearingReservationVO1Iterator").getCurrentRow();
        currentRow.getAttribute("JudgmentTypeId");
          System.err.println("judgmentTypeId ==> " +JSFUtils.resolveExpression("#{bindings.JudgmentSubType.inputValue}") +"===>" +JSFUtils.resolveExpression("#{bindings.JudgmentTypeId.inputValue}")+"  JudgmentTypeId==>"+ currentRow.getAttribute("JudgmentTypeId"));
        BigDecimal unspecializedFlag=new BigDecimal(0);
        System.err.println("size ==> insertFolderDate insertFolderDate");
          BigDecimal judgmentTypeId=new BigDecimal(0);
          if(JSFUtils.resolveExpression("#{bindings.JudgmentSubType.inputValue}")!=null )
              judgmentTypeId= (BigDecimal)JSFUtils.resolveExpression("#{bindings.JudgmentSubType.inputValue}") ;
          if(JSFUtils.resolveExpression("#{bindings.JudgmentTypeId.inputValue}") !=null && !JSFUtils.resolveExpression("#{bindings.JudgmentTypeId.inputValue}").equals("")&& !JSFUtils.resolveExpression("#{bindings.JudgmentTypeId.inputValue}").equals(" "))
           unspecializedFlag=(BigDecimal)JSFUtils.resolveExpression("#{bindings.JudgmentTypeId.inputValue}");
          ViewObject courtBcHearingResPar = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject(); 
        System.out.println(judgmentTypeId+"***********"+unspecializedFlag);
          if((judgmentTypeId != null&&(judgmentTypeId.intValue()==11||judgmentTypeId.intValue()==12||judgmentTypeId.intValue()==24   ||judgmentTypeId.intValue()==14 ||judgmentTypeId.intValue()==13))
          ||(unspecializedFlag !=null &&(unspecializedFlag.intValue()==16||unspecializedFlag.intValue()==21||unspecializedFlag.intValue()==22 ||unspecializedFlag.intValue()==23 ||unspecializedFlag.intValue()==25
              ||unspecializedFlag.intValue()==34 ||unspecializedFlag.intValue()==33 ||unspecializedFlag.intValue()==32 ||unspecializedFlag.intValue()==31 ||unspecializedFlag.intValue()==30 ||unspecializedFlag.intValue()==29 ||unspecializedFlag.intValue()==28 ||unspecializedFlag.intValue()==27 ||unspecializedFlag.intValue()==26  
             )))
          {
              System.err.println(" BigDecimal folderTypelkp=new BigDecimal(0)");
              BigDecimal folderTypelkp=new BigDecimal(0);
              if(judgmentTypeId.intValue()==11)
                  folderTypelkp=new BigDecimal(23);
             else if(judgmentTypeId.intValue()==12)
                  folderTypelkp=new BigDecimal(24);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==25)
                   folderTypelkp=new BigDecimal(34);
             else if(judgmentTypeId.intValue()==14)
                  folderTypelkp=new BigDecimal(25);
              else if(judgmentTypeId.intValue()==22)
                   folderTypelkp=new BigDecimal(9);
              else if(unspecializedFlag.intValue()==23)
                   folderTypelkp=new BigDecimal(31);
              else if(judgmentTypeId.intValue()==13)
                   folderTypelkp=new BigDecimal(30);
             else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==16)
                  folderTypelkp=new BigDecimal(26);
             else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==21)
                  folderTypelkp=new BigDecimal(27);
             else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==22)
                  folderTypelkp=new BigDecimal(28);
              
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==26)
                   folderTypelkp=new BigDecimal(36);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==27)
                   folderTypelkp=new BigDecimal(37);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==28)
                   folderTypelkp=new BigDecimal(38);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==29)
                   folderTypelkp=new BigDecimal(39);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==30)
                   folderTypelkp=new BigDecimal(40);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==31)
                   folderTypelkp=new BigDecimal(41);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==32)
                   folderTypelkp=new BigDecimal(42);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==33)
                   folderTypelkp=new BigDecimal(34);
              else if(unspecializedFlag !=null &&unspecializedFlag.intValue()==34)
                   folderTypelkp=new BigDecimal(43);
          ViewObject excFolderDataVO1 = ADFUtil.findIterator("ExcFolderDataVOspecialIterator").getViewObject();
          RowSetIterator rws = courtBcHearingResPar.createRowSetIterator(null);
          rws.reset();
                        System.out.println("size ==>"+rws.getEstimatedRangePageCount());
              OperationBinding ob = ADFUtil.findOperation("getSequenceNumber");
                             ob.getParamsMap().put("folderTypeIdParam", folderTypelkp);
                             ob.getParamsMap().put("bindYearParam", 2020);
              ob.execute();
            BigDecimal sequenceNumber =(BigDecimal) ob.getResult();
              System.err.println("folderTypelkp"+folderTypelkp+"<==>" +ob.getResult());
//          BigDecimal sequenceNumber=new BigDecimal(0);
             
            while(rws.hasNext())
          {//ExcFolderDataVO1Iterator
          sequenceNumber=sequenceNumber.add(new BigDecimal(1));
              Row next = rws.next();
                
              Row createRow = excFolderDataVO1.createRow();
              System.err.println("folderTypelkp"+folderTypelkp+"<==  sequenceNumber >" +sequenceNumber +"= res ID=>"+next.getAttribute("Id"));
              createRow.setAttribute("ResPartyId", next.getAttribute("Id"));
              createRow.setAttribute("FolderType", folderTypelkp);
              
              createRow.setAttribute("SequenceNumber", sequenceNumber);
              
              
              createRow.setAttribute("JudgementStatus", 2);
              if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null){
              if (year<2023)
              createRow.setAttribute("Year", 2020);
              if (year>2022)
              createRow.setAttribute("Year", new BigDecimal(1900));
              }else if (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")!=null){
                  if (years<2023)
                  createRow.setAttribute("Year", 2020);
                  if (years>2022)
                  createRow.setAttribute("Year", new BigDecimal(1900));   
              }
              createRow.setAttribute("PoliceStationEntityId", JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
              createRow.setAttribute("RepeatedFlag", "N");
              excFolderDataVO1.insertRow(createRow);
          }
          rws.closeRowSetIterator();
          }
        
      }
   
    public String saveBC_PI() {
        ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
        JSFUtils.addToSession("automationFlag", false);
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");
        JSFUtils.addToSession("PPYEAR", 0);
        Boolean     check = true ;
        Boolean     skipgonahReservation = false ;
      skipgonahReservation = (Boolean) ADFContext.getCurrent().getSessionScope().get("skipgonahReservation");
      if ( skipgonahReservation )
      {
          
              ADFUtil.findOperation("Commit").execute();
                                        JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                                        System.err.println("SAVE COMMIT");
                                        JSFUtils.addToPageFlowScope("backLog", false);
                                        System.err.println("cOMMIT");
                                        //                        ADFUtil.findOperation("Rollback").execute();
                                        JSFUtils.addToPageFlowScope("backLog", null);
                                        //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
                                        //            ADFUtil.findOperation("Rollback").execute();
                                        this.getDetails().setRendered(false);
                                        if (this.getBtNMode() != null)
                                            this.getBtNMode().setVisible(false);
                                        if (this.getBtnMode2() != null)
                                            this.getBtnMode2().setVisible(false);
                                        this.getReturnFlag().setValue("false");
                                        JSFUtils.addToPageFlowScope("decision", "0");
                                        JSFUtils.addToPageFlowScope("back", "0");
                                        this.getSaveBTN().setDisabled(true);
                                        //                    this.getSaveReport25().setDisabled(true);
                                        not_Exisit_Label.setVisible(false);
                                        exisit_Label.setVisible(false);
                                        error_label.setVisible(false);
                                        //        searchImge.setRendered(true);
                                        //        searchImge.setVisible(true);
                                        JSFUtils.addToPageFlowScope("disabledValidate", false);

                                        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
                                        btnOne.setSelected(false);
                                        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
                                        btnMany.setSelected(false);
                                        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
                                        btnNew.setSelected(false);
                                        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
                                        btnOld.setSelected(false);


                                        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
                                        normalSave.setRendered(true);
                                        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
                                        CustomSave.setRendered(false);

                                        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
                                        searchBtn.setDisabled(false);


                                        JSFUtils.addToPageFlowScope("detail", false);
                        
                        
                        return null;
                        
                        }
        
//        if (JSFUtils.resolveExpression("#{bindings.FirstSession.inputValue}")== null  )
//        {
//
//            ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
//            RowSetIterator createRowSetIterator = vo.createRowSetIterator(null);
//            while(createRowSetIterator.hasNext()){
//                Row next = createRowSetIterator.next();
//                
//                System.out.println(next.getAttribute("UnspecializedFlag")+"****");
//                if(next.getAttribute("UnspecializedFlag")== null ||new BigDecimal( ""+next.getAttribute("UnspecializedFlag")).compareTo(new BigDecimal(0))==0)
//                {    JSFUtils.showFacesInfoMessageFromBundle("bundle", "JUDGE_VALIDATION");
//                return null;
//                }
//            }
//        }
        
        
        //***** Bayoumi---------
        
        
        
        String judgmentSubType=(String)JSFUtils.resolveExpression(" #{bindings.JudgmentSubType.inputValue}");
        
       /**
        *Start Bayoumi
        * 
        * */       
       int judgmentSubTypeVar= (null!=judgmentSubType &&!" ".equals(judgmentSubType) ?(Integer.parseInt(judgmentSubType.trim())):0);
        System.err.println("BBB>>> judgmentSubType"+judgmentSubTypeVar);
        
        
        if (judgmentSubTypeVar== 1){
                ADFUtil.findOperation("delayGnayat").execute();
            
                      
                //            ADFUtil.findOperation("Commit").execute();
                            JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                            System.err.println("SAVE COMMIT");
                            JSFUtils.addToPageFlowScope("backLog", false);
                            System.err.println("cOMMIT");
                            //                        ADFUtil.findOperation("Rollback").execute();
                            JSFUtils.addToPageFlowScope("backLog", null);
                            //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
                            //            ADFUtil.findOperation("Rollback").execute();
                            this.getDetails().setRendered(false);
                            if (this.getBtNMode() != null)
                                this.getBtNMode().setVisible(false);
                            if (this.getBtnMode2() != null)
                                this.getBtnMode2().setVisible(false);
                            this.getReturnFlag().setValue("false");
                            JSFUtils.addToPageFlowScope("decision", "0");
                            JSFUtils.addToPageFlowScope("back", "0");
                            this.getSaveBTN().setDisabled(true);
                            //                    this.getSaveReport25().setDisabled(true);
                            not_Exisit_Label.setVisible(false);
                            exisit_Label.setVisible(false);
                            error_label.setVisible(false);
                            //        searchImge.setRendered(true);
                            //        searchImge.setVisible(true);
                            JSFUtils.addToPageFlowScope("disabledValidate", false);

                            RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
                            btnOne.setSelected(false);
                            RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
                            btnMany.setSelected(false);
                            RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
                            btnNew.setSelected(false);
                            RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
                            btnOld.setSelected(false);


                            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
                            normalSave.setRendered(true);
                            RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
                            CustomSave.setRendered(false);

                            RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
                            searchBtn.setDisabled(false);


                            JSFUtils.addToPageFlowScope("detail", false);
            
            
            return null;
            
            }
        
        
        if (judgmentSubTypeVar>0 && (12==judgmentSubTypeVar ||24==judgmentSubTypeVar  ||23==judgmentSubTypeVar
              ||14==judgmentSubTypeVar
            || 11==judgmentSubTypeVar ||13==judgmentSubTypeVar)
        ){
            if (11==judgmentSubTypeVar )
            {
                
                    ViewObject vo = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
                                RowSetIterator createRowSetIterator = vo.createRowSetIterator(null);
                              while(createRowSetIterator.hasNext()){
                                   Row next = createRowSetIterator.next();
                                  next.setAttribute("UnspecializedFlag", new BigDecimal (10));
                              }
                
                }
            
            
            Object judgmentSubTypeId=(Object) getJudgmentTypeIdBind().getValue();

                        if ((null ==judgmentSubTypeId || judgmentSubTypeId=="")&&24==judgmentSubTypeVar)
                        {
                            JSFUtils.showFacesInfoMessageFromBundle("bundle", "JUDGE_VALIDATION");
                            return  null;
                        }
                        
                        
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null){
                Date currentDate = sd.parse(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}") + "");
                int year=currentDate.getYear()+1900;
                System.out.println("!!!!!"+year);
                    JSFUtils.addToSession("PPYEAR", year);
                    if (year>2022)
                        JSFUtils.addToSession("automationFlag", true);
                    ADFContext.getCurrent().getSessionScope().put("automationYear",year+"");
                }
                else if (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")!=null){
                                OperationBinding myOperation = ADFUtil.findOperation("getDatesHearing");
                                myOperation.getParamsMap().put("id", new BigDecimal (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")+""));
                            String dateQuery = (String) myOperation.execute();
                            System.out.println(dateQuery+"dateQuery");
                           int year= Integer.parseInt(dateQuery);
                                System.out.println("!!!!!"+year);
                                    JSFUtils.addToSession("PPYEAR", year);
                                    if (year>2022){
                                        JSFUtils.addToSession("automationFlag", true);
                                        ADFContext.getCurrent().getSessionScope().put("automationYear",year+"");
                                    }
                            }
            } catch (ParseException e) {
            }
                        
            JSFUtils.addToSession("skipPenValid", true);
//            JSFUtils.addToSession("automationFlag", false);
             if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null)
                   check = (Boolean)      ADFUtil.findOperation("validateCourtBCHearingDate").execute();
           
             if(check){
                 insertFolderDate();
                System.out.println("The First asignCrimesToParty");
               ADFUtil.findOperation("asignCrimesToParty").execute();
      
//            ADFUtil.findOperation("Commit").execute();
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
            System.err.println("SAVE COMMIT");
            JSFUtils.addToPageFlowScope("backLog", false);
            System.err.println("cOMMIT");
            //                        ADFUtil.findOperation("Rollback").execute();
            JSFUtils.addToPageFlowScope("backLog", null);
            //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
            //            ADFUtil.findOperation("Rollback").execute();
            this.getDetails().setRendered(false);
            if (this.getBtNMode() != null)
                this.getBtNMode().setVisible(false);
            if (this.getBtnMode2() != null)
                this.getBtnMode2().setVisible(false);
            this.getReturnFlag().setValue("false");
            JSFUtils.addToPageFlowScope("decision", "0");
            JSFUtils.addToPageFlowScope("back", "0");
            this.getSaveBTN().setDisabled(true);
            //                    this.getSaveReport25().setDisabled(true);
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(false);
            error_label.setVisible(false);
            //        searchImge.setRendered(true);
            //        searchImge.setVisible(true);
            JSFUtils.addToPageFlowScope("disabledValidate", false);

            RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
            btnOne.setSelected(false);
            RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
            btnMany.setSelected(false);
            RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
            btnNew.setSelected(false);
            RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
            btnOld.setSelected(false);


            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
            CustomSave.setRendered(false);

            RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
            searchBtn.setDisabled(false);


            JSFUtils.addToPageFlowScope("detail", false);
           
             }
            else {
                            System.out.println("WWWWWW");
                            JSFUtils.showFacesErrorMessageFromBundle("bundle", "DATE_RESERVATION");

                        }
           
            return null;
        }
        
      

        

        RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
        System.out.println("Save With origin id = "+inputPoliceNum.getValue().toString());
                if(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")==null){
                  JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}",inputPoliceNum.getValue().toString());
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("OrigEntity", new BigDecimal(inputPoliceNum.getValue().toString()));
                }
        System.out.println("@@@@@@@@"+JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}"));
        System.out.println("@@@@@@@@"+JSFUtils.resolveExpression("#{bindings.DateId.inputValue}"));
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}")!=null){
            Date currentDate = sd.parse(JSFUtils.resolveExpression("#{bindings.HearingDate.inputValue}") + "");
            int year=currentDate.getYear()+1900;
            System.out.println("!!!!!"+year);
                JSFUtils.addToSession("PPYEAR", year);
                if (year>2022){
                    JSFUtils.addToSession("automationFlag", true);
                    ADFContext.getCurrent().getSessionScope().put("automationYear",year+"");
                }
            }else if (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")!=null){
                OperationBinding myOperation = ADFUtil.findOperation("getDatesHearing");
                myOperation.getParamsMap().put("id", new BigDecimal (JSFUtils.resolveExpression("#{bindings.DateId.inputValue}")+""));
            String dateQuery = (String) myOperation.execute();
            System.out.println(dateQuery+"dateQuery");
           int year= Integer.parseInt(dateQuery);
                System.out.println("!!!!!"+year);
                    JSFUtils.addToSession("PPYEAR", year);
                    if (year>2022){
                        JSFUtils.addToSession("automationFlag", true);
                        ADFContext.getCurrent().getSessionScope().put("automationYear",year+"");
                    }
            }
            
        } catch (ParseException e) {
        }
         JSFUtils.addToSession("skipPenValid", true);
            actionTypes();
            getDegree();
//            JSFUtils.storeOnSession("actionTypeId", JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
            
            System.err.println("*********** Norma Save ************");
              check = true;
            //  if (JSFUtils.resolveExpression("#{bindings.Source.inputValue}") == null)
            JSFUtils.setExpressionValue("#{bindings.Source.inputValue}", "");
            // Add event code here...
            System.err.println("*****************   " + ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
            System.err.println("*****************   " +
                               JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            if (JSFUtils.resolveExpression("#{bindings.Id9.inputValue}") == null) {
                if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null)
                    JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
                else
                    JSFUtils.addToSession("Neyabaa",
                                          "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));

            } else {
                JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId());
            }


            BigDecimal LedgerId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CASE_SCHEMA_ID.inputValue}");
            JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", LedgerId);
            System.err.println(JSFUtils.getFromPageFlowScope("showDate") + "YYYYYYYYYYYYYYYYYYYYYYY     " +
                               JSFUtils.getFromPageFlowScope("backLog"));
            //        if (this.getReturnFlag().getValue() == "false")
            BigDecimal prosecutionJudegementFlag =
                (BigDecimal) JSFUtils.resolveExpression("#{bindings.ProsecutionJudegementFlag.inputValue}");
            System.err.println("prosecutionJudegementFlag" + prosecutionJudegementFlag);
            if (prosecutionJudegementFlag.compareTo(new BigDecimal(0)) == 0) {
            }
            if (JSFUtils.getFromPageFlowScope("showDate")
                        .toString()
                        .equalsIgnoreCase("true")) {
                JSFUtils.addToPageFlowScope("backLog", true);
                check = (Boolean) ADFUtil.findOperation("validateCourtBCHearingDate").execute();
            } else
                ADFContext.getCurrent()
                          .getSessionScope()
                          .put("CheckHearingDate", 0);

            System.out.println("checkcheckcheckcheckcheckcheck  ***" + check);
            if (check) {
                if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}",
                                                JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));

                Boolean valide = false;

                if (!valide) {

                    partyStatus();


                    Boolean checkFlg = (Boolean) ADFUtil.findOperation("delayReservation").execute();
                    if (null!=checkFlg&&checkFlg) {
                        
                        ADFUtil.findOperation("deleteReservations").execute();
                        System.out.println("The secound asignCrimesToParty");
                        Object execute = ADFUtil.findOperation("asignCrimesToParty").execute();
                        System.err.println("SAVE COMMIT" + execute);
                        JSFUtils.addToPageFlowScope("backLog", false);
                        System.err.println("cOMMIT");
//                        ADFUtil.findOperation("Rollback").execute();
                        JSFUtils.addToPageFlowScope("backLog", null);
                        //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
                        //            ADFUtil.findOperation("Rollback").execute();
                        this.getDetails().setRendered(false);
                        if (this.getBtNMode() != null)
                            this.getBtNMode().setVisible(false);
                        if (this.getBtnMode2() != null)
                            this.getBtnMode2().setVisible(false);
                        this.getReturnFlag().setValue("false");
                        JSFUtils.addToPageFlowScope("decision", "0");
                        JSFUtils.addToPageFlowScope("back", "0");
                        this.getSaveBTN().setDisabled(true);
                        //                    this.getSaveReport25().setDisabled(true);
                        not_Exisit_Label.setVisible(false);
                        exisit_Label.setVisible(false);
                        error_label.setVisible(false);
                        //        searchImge.setRendered(true);
                        //        searchImge.setVisible(true);
                        JSFUtils.addToPageFlowScope("disabledValidate", false);

                        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
                        btnOne.setSelected(false);
                        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
                        btnMany.setSelected(false);
                        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
                        btnNew.setSelected(false);
                        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
                        btnOld.setSelected(false);


                        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
                        normalSave.setRendered(true);
                        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
                        CustomSave.setRendered(false);

                        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
                        searchBtn.setDisabled(false);


                        JSFUtils.addToPageFlowScope("detail", false);
                        JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                    } else {
                        JSFUtils.showFacesInfoMessageFromBundle("bundle", "JUDGE_VALIDATION");
                    }

                } else {

                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "HASR_BC_KOLYAA");

                }

            } else {
                System.out.println("WWWWWW");
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "DATE_RESERVATION");

            }

        JSFUtils.addToSession("automationFlag", false);
        ADFContext.getCurrent().getSessionScope().put("automationYear","anonymous");
//        insertFolderDate();
//        ADFUtil.findOperation("Commit").execute();
        return null;
    }

    public Boolean validateHasrKolyaa() {
        BigDecimal entityId = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") != null)
            entityId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        String errandFlag = (String) ADFContext.getCurrent()
                                               .getSessionScope()
                                               .get("errandF");
        if (errandFlag.compareToIgnoreCase("Y")==0){
            entityId = (new BigDecimal(ADFContext.getCurrent()
                                                 .getSessionScope()
                                                 .get("errandEntity")
                                                 .toString()));
        
            BigDecimal errandEntUser = new BigDecimal(ADFContext.getCurrent()
                                                                .getSessionScope()
                                                                .get("errandEntUser") + "");
                   if(errandEntUser.compareTo(new BigDecimal(0)) == 1)
                       entityId=(errandEntUser);
        }
        //#{bindings.LedgerId.inputValue}
        System.err.println(JSFUtils.resolveExpression("#{bindings.LedgerId.inputValue}")+"validateHasrKolyaa  Entity id is "+entityId);
        BigDecimal year = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.Year1.inputValue}") != null)
            year = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Year1.inputValue}");
        BigDecimal seq = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.Seq.inputValue}") != null)
            seq = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Seq.inputValue}");
        BigDecimal CASE_SCHEMA_ID = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null)
            CASE_SCHEMA_ID = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}");

        OperationBinding myOperation = ADFUtil.findOperation("validateHasrBC");
        myOperation.getParamsMap().put("entityId", entityId);
        myOperation.getParamsMap().put("year", year);
        myOperation.getParamsMap().put("seq", seq);
        myOperation.getParamsMap().put("CASE_SCHEMA_ID", CASE_SCHEMA_ID);
        myOperation.getParamsMap().put("ledgerId",""+ JSFUtils.resolveExpression("#{bindings.LedgerId.inputValue}"));
        Boolean result = (Boolean) myOperation.execute();
        return result;
    }


    public String customSave() {
        // Add event code here...


        if (validatePenalty()) {
            actionTypes();
            getDegree();
//            JSFUtils.storeOnSession("actionTypeId", JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
            
            System.err.println("*****************   " + ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
            System.err.println("*****************   " +
                               JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            if (JSFUtils.resolveExpression("#{bindings.Id9.inputValue}") == null) {
                if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null)
                    JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId());
                else
                    JSFUtils.addToSession("Neyabaa",
                                          "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));

            } else {
                JSFUtils.addToSession("Neyabaa", ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId());
            }
            BigDecimal LedgerId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CASE_SCHEMA_ID.inputValue}");
            JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", LedgerId);
            //        if (this.getReturnFlag().getValue() == "false")
            Boolean check = (Boolean) ADFUtil.findOperation("validateCourtBCHearingDate").execute();
            if (check) {

                if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}",
                                                JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));

                Boolean valide = false;
                if (JSFUtils.getFromPageFlowScope("newKolyeaBC")
                            .toString()
                            .compareTo("true") == 0)
                    valide = validateHasrKolyaa();
                if (!valide) {
                    partyStatus();
                    ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
                  custSaveCommit();
                    
                } else {

                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "HASR_BC_KOLYAA");

                }

            } else {
                System.out.println("WWWWWW");

                RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
                normalSave.setRendered(false);
                RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
                CustomSave.setRendered(true);


                RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
                oneParty.setRendered(false);
                RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
                manyParty.setRendered(true);


                JSFUtils.showFacesErrorMessageFromBundle("bundle", "DATE_RESERVATION");

            }

        } else {
            this.getSaveBTN().setRendered(false);
            this.getSaveReport25().setRendered(false);
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "PENALTY_MESSAGE");
        }


        JSFUtils.addToSession("judgeText", "00");
        return null;
    }
    
    public void custSave(){
            ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
        setFolderId();
            ADFUtil.findOperation("customSaveBeforeCommit").execute();
            JSFUtils.addToPageFlowScope("backLog", false);
            System.err.println("commit");
            JSFUtils.addToPageFlowScope("backLog", null);
            //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
            //        ADFUtil.findOperation("Rollback").execute();
            JSFUtils.addToPageFlowScope("decision", "0");
            JSFUtils.addToPageFlowScope("back", "0");

            this.getDetails().setRendered(false);
            if (this.getBtNMode() != null)
                this.getBtNMode().setVisible(false);
            if (this.getBtnMode2() != null)
                this.getBtnMode2().setVisible(false);
            this.getReturnFlag().setValue("false");
            this.getSaveBTN().setDisabled(true);
            this.getSaveReport25().setDisabled(true);
            not_Exisit_Label.setVisible(false);
            exisit_Label.setVisible(false);
            error_label.setVisible(false);
            //        searchImge.setRendered(true);
            //        searchImge.setVisible(true);
            JSFUtils.addToPageFlowScope("disabledValidate", false);
            JSFUtils.addToPageFlowScope("decision", "0");
            JSFUtils.addToPageFlowScope("back", "0");
            RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
            btnOne.setSelected(false);
            RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
            btnMany.setSelected(false);

            RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
            btnNew.setSelected(false);
            RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
            btnOld.setSelected(false);

            RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
            RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
            RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
            RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
            phDescOne.setRendered(true);
            phChargOne.setRendered(true);
            phChargMany.setRendered(false);
            phDescMany.setRendered(false);

            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
            CustomSave.setRendered(false);

            RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
            searchBtn.setDisabled(false);

            RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
            oneParty.setRendered(true);
            RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
            manyParty.setRendered(false);
            JSFUtils.addToPageFlowScope("detail", false);
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
            ADFUtil.findOperation("Rollback").execute();
        }

    public String test() {
        // Add event code here...
        //
        JSFUtils.calljqHelper("$.growl.notice({ message: \" Data Saved Successfully  ..)\" });");
        System.err.println("***********");
        return null;
    }

    public void setName(String fullName) {

        String[] args = fullName.split(" ");
        if (args.length > 0) {
            String fName = args[0];
            System.out.println("fName ...>" + fName);
            JSFUtils.setExpressionValue("#{bindings.Fname.inputValue}", fName);
        }

        if (args.length > 1) {
            String sName = args[1];
            System.out.println("sName ...>" + sName);
            JSFUtils.setExpressionValue("#{bindings.Sname.inputValue}", sName);
        }
        if (args.length > 2) {
            String tName = args[2];
            System.out.println("tName ...>" + tName);
            JSFUtils.setExpressionValue("#{bindings.Tname.inputValue}", tName);
        }
        if (args.length > 3) {
            String lName = args[3];
            if (args.length > 4) {
                lName = lName+" "+args[4];
            }
            if (args.length > 5) {
                lName = lName+" "+args[5];
            }
            if (args.length > 6) {
                lName = lName+" "+args[6];
            }
            if (args.length > 7) {
                lName = lName+" "+args[7];
            }
            System.out.println("lName ...>" + lName);
            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", lName);
        }
    }

    public void fullNameVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String fullName = (String) valueChangeEvent.getNewValue();
        setName(fullName);
        JSFUtils.setExpressionValue("#{bindings.PartyName1.inputValue}", fullName);
    }

    public String newBusinessCaseLedgerSeq() {
        // Add event code here...
        ADFUtil.findOperation("CreateInserBCDetals").execute();
        ADFUtil.findOperation("CreateInsertLedgerSeq").execute();
        return null;
    }

    public void ledgerSeqVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println();
    }

    public String validateNewBC() {
        // Add event code here...
        return null;
    }

    public void actionTypes() {
        //        if (JSFUtils.getFromSession("GnayaatFlag") != null)
        {
            System.out.println("%%%%%%%%%%" + ADFContext.getCurrent()
                                                        .getSessionScope()
                                              .get("actionTypeId"));
            
            BigDecimal neyKolyaaaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
            BigDecimal neyGozayaaaaa = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}");
            JSFUtils.addToSession("NeyabaaKolyaa", neyKolyaaaa);
            JSFUtils.addToSession("NeyabaaGozayaa", neyGozayaaaaa);  
            
            JSFUtils.addToSession("NeyabaaKolyaa",
                                  "" + JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
            
            
            if(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}")!= null){
            
            Boolean flg = (Boolean) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue eq '3643'}");
                if (flg) {
                    Boolean flg2 = (Boolean) JSFUtils.resolveExpression("#{bindings.Errand_flag.inputValue eq 'Y'}");
                    if (flg2){
                      
                        JSFUtils.addToSession("NeyabaaKolyaa",
                                              JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
                  
                        if (ADFContext.getCurrent()
                                      .getSessionScope()
                                      .get("actionTypeId") == null) {
             
                            System.out.println("flg2 flg2 flg2 flg2 ERRANDDDDDDDDDDDDDDDDD" +
                                               JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
  
                            JSFUtils.addToSession("actionTypeId",
                                                  JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
                
                     }
                    }
                
                }
            
            }
            
            Boolean GnayaatFlag = (Boolean) JSFUtils.getFromSession("GnayaatFlag");
            System.out.println(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") +
                               "GnayaatFlag ++++++++++++++ " + GnayaatFlag);
            if (!GnayaatFlag) {
                Boolean obj =
                    (Boolean) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue ne '3646' and bindings.SchemaId.inputValue ne '3647' and  bindings.SchemaId.inputValue ne '3648' and   bindings.SchemaId.inputValue ne '3643'}");
                System.err.println("****" + obj);

                Boolean obj2 = (Boolean) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue eq '3643'}");
                System.err.println("****2" + obj2);
                if (obj) {
                    System.out.println("-----1-------");
//                    if (ADFContext.getCurrent().getSessionScope().get("actionTypeId") == null)
                    JSFUtils.storeOnSession("actionTypeId",
                                            JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
                    JSFUtils.storeOnSession("GnayaatFlag", false);
                } else if (obj2) {
                    System.out.println("-----2-------");
                    if (JSFUtils.resolveExpression("#{bindings.BcDegreeGonaha.inputValue}") != null) {
                        JSFUtils.storeOnSession("actionTypeId",
                                            JSFUtils.resolveExpression("#{bindings.BcDegreeGonaha.inputValue}"));
                        System.out.println("***********************----" +
                                           JSFUtils.resolveExpression("#{bindings.BcDegreeGonaha.inputValue}"));
                    JSFUtils.storeOnSession("GnayaatFlag", false);
                    }
                } else {
                    System.out.println("-----3-------");
                 //   if (ADFContext.getCurrent().getSessionScope().get("actionTypeId") == null)
                    JSFUtils.storeOnSession("actionTypeId",
                                            JSFUtils.resolveExpression("#{bindings.BcDegreeGnayaat.inputValue}"));
                    JSFUtils.storeOnSession("GnayaatFlag", true);
                }
            }
        }
    }

    public void entityVCL() {
        BigDecimal typeFlag = new BigDecimal(0);
        Boolean  updatedDateFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.UpdatedDate2.inputValue eq null}");
        JSFUtils.addToSession("NeyabaaGozayaa", "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        System.err.println("###  " + JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
            typeFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
        if (typeFlag.compareTo(new BigDecimal(3643)) == 0) {
            if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));
            if (JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") != null && updatedDateFlag)
//                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",

//                                            JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                System.out.println("errandEntity is :"+JSFUtils.getFromSession("errandEntity"));
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                            JSFUtils.getFromSession("errandEntity"));
        }

    }

    public void degreeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here.....BcDegreeGonaha
        try {
            System.out.println(" valueChangeEvent.getNewValue()****Degree*****"+ valueChangeEvent.getNewValue());
            
            BigDecimal type = (BigDecimal) valueChangeEvent.getNewValue();
            {
                
                    OperationBinding myOperation = ADFUtil.findOperation("gethearingDates");
                    myOperation.getParamsMap().put("degree", type);
                    if(JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") !=null)
                {
                    System.err.println("Bayoumi >>> BussinesId>"+JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}"));
                    myOperation.getParamsMap()
                        .put("bcID", JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") + "");
                } else
                {
                        System.err.println("Bayoumi >>***> BC_ID>"+JSFUtils.getFromSession("BC_ID"));

                    myOperation.getParamsMap().put("bcID", JSFUtils.getFromSession("BC_ID")+"");
                        
                    }

                  Map<BigDecimal, String> res = (Map<BigDecimal, String>) myOperation.execute();
                    newSelectItemList = new ArrayList<SelectItem>();
                    dateList = new HashMap<String, String>();
                    if (res.size() > 0)
                        for (Map.Entry m : res.entrySet()) {
                            BigDecimal key = (BigDecimal) m.getKey();
                        dateList.put(key+"", m.getValue()+"");
                    newSelectItemList.add(new SelectItem(key, m.getValue()+""));
                    }
                  
                  this.setSelectItemList(newSelectItemList);
                
                }
     
            Boolean updatedDateFlag =
                (Boolean) JSFUtils.resolveExpression("#{bindings.UpdatedDate2.inputValue eq null}");
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
           
            if (JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree1.inputValue}") != null)
            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree1.inputValue}",type);
//            JSFUtils.setExpressionValue("#{bindings.BcDegreeGonaha.inputValue}",type);
              
            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}",type);
            BigDecimal typeFlag = new BigDecimal(0);
            JSFUtils.addToSession("NeyabaaGozayaa",
                                  "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            System.err.println("###  " + JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
            if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                typeFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
            if (typeFlag.compareTo(new BigDecimal(3643)) == 0) {
                if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));
                if (JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") != null &&
                    updatedDateFlag) {
                    if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))
                        JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                                    JSFUtils.resolveExpression("#{sessionScope.userInfo.errandEntUser}") +
                                                    "");
                    else{
                        if  (type.compareTo(new BigDecimal(3)) == 0 ||
                type.compareTo(new BigDecimal(4)) == 0 )
                    JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                                JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                    
                    //--
                    }
                }
            }
            System.err.println("typeFlag   " + typeFlag);
            if (typeFlag.compareTo(new BigDecimal(3643)) != 0 && typeFlag.compareTo(new BigDecimal(0)) == 1 &&
                updatedDateFlag) {
                           if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(2));
                if (JSFUtils.resolveExpression("#{bindings.FolderTypeCrime.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", new BigDecimal(2));
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))
                    JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                                JSFUtils.resolveExpression("#{sessionScope.userInfo.errandEntUser}") +
                                                "");
                else{
                    if  (type.compareTo(new BigDecimal(3)) == 0 ||
                    type.compareTo(new BigDecimal(4)) == 0 )
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                //--
                }
                //             callJsFunction("pressButton");
                System.out.println("HHHHHHHHHHHHHHHHH");
            }
            //         else
            //             JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));
            actionTypes();
            System.err.println("   valueChangeEvent.getNewValue()   " + valueChangeEvent.getNewValue());
           
            Boolean ff =
                JSFUtils.resolveExpressionAsBoolean("#{bindings.CourtBcHearingResPartiesVO1.estimatedRowCount > '0'}");
           System.out.println("ffffffffff**----*-**-*-*-"+ff);
            RichPanelGroupLayout rpg = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl53");
            if (type.compareTo(new BigDecimal(1)) == 0 && ff) {
if (rpg != null)
                rpg.setRendered(true);
            } else {
                if (JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}") != null)
                
                JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
//                rpg.setRendered(false);
                System.out.println("FFFFFFFFFFFFFFFFFFFTTTTTTTTTTTTTTTTTTTTTTT");
            }
            JSFUtils.refreshComponent("pg48");
            JSFUtils.refreshComponent("pgl53");
            JSFUtils.storeOnSession("actionTypeId", type);
            //        System.err.println("VCL "+type);
            //        if (type.compareTo(new BigDecimal (2)) == 0)
            //        JSFUtils.storeOnSession("actionType", new BigDecimal (2));
            //        else if (type.compareTo(new BigDecimal (3)) == 0)
            //        JSFUtils.storeOnSession("actionType", new BigDecimal (1));
            //        else if (type.compareTo(new BigDecimal (4)) == 0)
            //        JSFUtils.storeOnSession("actionType", new BigDecimal (9));

            JSFUtils.refreshComponent("pg29");
            JSFUtils.refreshComponent("pg106");
            JSFUtils.refreshComponent("pg96");
            JSFUtils.refreshComponent("soc33");
            
            
            if (type.compareTo(new BigDecimal(1)) == 1) {
                    JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
                }

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }

    public void policeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here
        // ...
        BigDecimal type = (BigDecimal) valueChangeEvent.getNewValue();
        JSFUtils.storeOnSession("policeId", type);
        RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
         inputPoliceNum.setValue(type); 
        System.out.println(inputPoliceNum.getValue()+"police Value Change Listener" + type);
    }

    public void hearingVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Hearing VCL " + valueChangeEvent.getNewValue());
    }
    
    public void getDegree() {
            RichSelectOneChoice soc11 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
           System.out.println("soc11----"+soc11.getValue());
            RichSelectOneChoice soc34 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc34");
            System.out.println("soc34----"+soc34.getValue());
            RichSelectOneChoice soc35 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc35");
            System.out.println("soc35----"+soc35.getValue());
           
            if (soc11.getValue()!= null)
            JSFUtils.storeOnSession("actionTypeId",soc11.getValue() );
           
            if (soc34.getValue()!= null)
            JSFUtils.storeOnSession("actionTypeId",soc34.getValue() );
           
            if (soc35.getValue()!= null)
            JSFUtils.storeOnSession("actionTypeId",soc35.getValue() );
        
        }

    public String fetchProcedure() {
        // Add event code here...
        System.out.println("fetchProcedure ---------------------");
        JSFUtils.addToPageFlowScope("search", "true");
        Boolean returnFlag = (Boolean) ADFUtil.findOperation("validateFolderProcedure").execute();
        if (returnFlag) {
            this.getDetails().setRendered(true);
            RichSelectOneChoice dateChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc18");
            dateChoice.setDisabled(true);
            RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
            degreeChoice.setDisabled(true);
            RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
            searchBtn.setDisabled(true);
           
            callJsFunction("pressButton");
            
            BigDecimal deg =
                new BigDecimal(JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}") + "");
            BigDecimal bType =
                new BigDecimal(JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}") + "");     
            if (bType.compareTo(new BigDecimal(4)) == 0){
                JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
             //   JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", new BigDecimal(50));
               // JSFUtils.setExpressionValue("#{bindings.FolderTypeEconomy.inputValue}", new BigDecimal(50));
            }
            
            if   (deg.compareTo(new BigDecimal(1)) == 1)
                JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetails());
        }

        else {
            this.getDetails().setRendered(false);
            //               JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");

        }
        JSFUtils.refreshComponent("ph3");
        JSFUtils.refreshComponent("ph4");
        JSFUtils.refreshComponent("t12");
        JSFUtils.refreshComponent("pgl10");
        JSFUtils.refreshComponent("pg48");
        JSFUtils.refreshComponent("pgl53");
        JSFUtils.refreshComponent("t2");
        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setDisabled(false);
        this.getSaveBTN().setDisabled(false);
        this.getSaveReport25().setDisabled(false);
        JSFUtils.refreshComponent("b1");

 JSFUtils.refreshComponent("pg25");
 System.out.println("Validate");
        callJsFunction("pressButton");
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
            JSFUtils.setExpressionValue("#{bindings.ManualSeq.inputValue}", "Y");
            System.out.println("ManualSeq--  44478----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
        return null;
    }


    public String 
    fetchResParties() {
        // Add event code here...
        System.out.println("Maged ---------------------");
        System.out.println("@@@@@@@@@"+ JSFUtils.resolveExpression("#{bindings.DateId.inputValue}"));
        JSFUtils.addToPageFlowScope("search", "true");
        
            OperationBinding Operation = ADFUtil.findOperation("validateResPartiesPI");
        Operation.getParamsMap().put("hearingDate", JSFUtils.resolveExpression("#{bindings.DateId.inputValue}"));
        Boolean returnFlag = (Boolean) Operation.execute();
        if (returnFlag) {
            
            RichInputDate richInputDate = (RichInputDate) JSFUtils.findComponentInRoot("id21");
            richInputDate.setRendered(false);
            RichButton richButton = (RichButton) JSFUtils.findComponentInRoot("b4");
            RichButton richButtonl25 = (RichButton) JSFUtils.findComponentInRoot("l25");
            richButton.setRendered(false);
            richButtonl25.setRendered(false);
            
            
            this.getDetails().setRendered(true);
            this.getSaveBTN().setDisabled(false);
            RichSelectOneChoice dateChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc18");
            dateChoice.setDisabled(true);
            RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
            degreeChoice.setDisabled(true);
            RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
            searchBtn.setDisabled(true);

            callJsFunction("pressButton");
            //            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getDetails());
        }

        else {
            this.getDetails().setRendered(false);
            //               JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");

        }

        JSFUtils.refreshComponent("ph4");
        JSFUtils.refreshComponent("pgl10");
        JSFUtils.refreshComponent("t2");
        System.out.println("Validate");
        return null;
    }

    public void callJsFunction(String funName) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, funName + "();");
    }

    HashMap<BigDecimal, BigDecimal> mapSeq = new HashMap<BigDecimal, BigDecimal>(); //Creating HashMap


    public void sequenceFolder() {
        boolean check = true;
        HashMap<BigDecimal, BigDecimal> map = new HashMap<BigDecimal, BigDecimal>();
        ;
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("mapSeq") != null) {
            map = (HashMap<BigDecimal, BigDecimal>) ADFContext.getCurrent()
                                                              .getSessionScope()
                                                              .get("mapSeq");
        }


        BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id14.inputValue}");
        System.out.println("Iterating Hashmap...");
        if (map.size() > 0)
            for (Map.Entry m : map.entrySet()) {
                BigDecimal key = (BigDecimal) m.getKey();
                if (key.compareTo(id) == 0) {
                    check = false;
                    JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", m.getValue());
                    JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", m.getValue());
                    break;
                }
                System.out.println(m.getKey() + " " + m.getValue());
            }

        if (check) {
            OperationBinding binding = ADFUtil.findOperation("getSequenceValue");
            binding.getParamsMap().put("SequenceName", "EXC_FOLDER_DATA2_SEQ");
            BigDecimal seq = (BigDecimal) binding.execute();
            JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", seq);
            JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", seq);

            map.put(id, seq);


            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("mapSeq", map);
        }

    }

    public void folderTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        JSFUtils.setExpressionValue("#{bindings.GuiltlessnessFlag.inputValue}", "N");
        BigDecimal type = (BigDecimal) valueChangeEvent.getNewValue(); 
        JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", type);
        System.out.println("Oraby" + type);
        if (type.compareTo(new BigDecimal(1)) == 0) {

            sequenceFolder();


        }
        if (type.compareTo(new BigDecimal(22)) == 0) {
//                          JSFUtils.setExpressionValue("#{bindings.ExecutionFlag.inputValue}",  true);
            //                JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}",  "");


        }
        changeExecutionFlg();
    }

    public void insertHasrKolyaa() {


        JSFUtils.addToPageFlowScope("newKolyeaBC", "true");
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
        //            JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
        //            JSFUtils.setExpressionValue("#{bindings.BcDegreeGonaha.inputValue}", new BigDecimal(3));
        //            JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(2));
        //            JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
        ADFUtil.findOperation("CreateInserBCDetals").execute();
        ADFUtil.findOperation("CreateInsertLedgerSeq").execute();
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseId.inputValue}", (BigDecimal) ADFContext.getCurrent()
                                                                                                    .getSessionScope()
                                                                                                    .get("BC_ID"));
        System.out.println("Business Case ID  Is    :::::  " + ADFContext.getCurrent()
                                                                         .getSessionScope()
                                                                         .get("BC_ID"));

        this.getSaveBTN().setDisabled(false);
        this.getSaveReport25().setDisabled(false);
    }

    public void removeHasrKolyaa() {

        JSFUtils.addToPageFlowScope("newKolyeaBC", "false");

        ADFUtil.findOperation("DeleteLedger").execute();
        ADFUtil.findOperation("DeleteBCDetail").execute();
        if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
            JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
        JSFUtils.storeOnSession("GnayaatFlag", false);
        //            JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}", JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
    }

    public void newHasrBC(ValueChangeEvent valueChangeEvent) {
        // Add event code here....
        BigDecimal bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
        System.err.println(valueChangeEvent.getNewValue());
        Boolean type = (Boolean) valueChangeEvent.getNewValue();
        BigDecimal errandEntUser = new BigDecimal(ADFContext.getCurrent()
                                                            .getSessionScope()
                                                            .get("errandEntUser") + "");
        Boolean  updatedDateFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.UpdatedDate2.inputValue eq null}");
             System.err.println("FF-----"+updatedDateFlag);
        if (type) {

            insertHasrKolyaa();
            if (JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") != null &&
                errandEntUser.compareTo(new BigDecimal(0)) != 0 && updatedDateFlag)
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
            //--

        } else {
            removeHasrKolyaa();
            if (JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") != null &&
                errandEntUser.compareTo(new BigDecimal(0)) != 0 && updatedDateFlag)
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));

        }
    }

    public void addParty(ClientEvent clientEvent) {
        // Add event code here... ...
        JSFUtils.addToPageFlowScope("dimmed", true);
        Boolean check = true;
        BigDecimal parId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyId2.inputValue}");

        Vector vect = new Vector();
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("vecParty") != null)
            vect = (Vector) ADFContext.getCurrent()
                                      .getSessionScope()
                                      .get("vecParty");


        for (int x = 0; x < vect.size(); x++)

        {
            System.out.println(parId + " ===================== " + vect.get(x));


            BigDecimal partyId = (BigDecimal) vect.get(x);

            if (partyId.compareTo(parId) == 0) {
                check = false;
                System.out.println("FOUNDDDDDDDDDDDDDDDDDD");

            }
        }

        if (check) {

            Boolean type = true;

            System.err.println("GGGGGGGGGGGGGGGG" + JSFUtils.resolveExpression("#{bindings.PartyId2.inputValue}"));
            ADFUtil.findOperation("addNewconvictedParty").execute();
            if (JSFUtils.resolveExpression("#{bindings.PartyFound.inputValue}") != null)
                type = (Boolean) JSFUtils.resolveExpression("#{bindings.PartyFound.inputValue}");
            if (type) {
                System.err.println("type " + type);
                if(false)
                ADFUtil.findOperation("CreateInsertFolderData").execute();
                ADFUtil.findOperation("newFolderData").execute();
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator")
                       .getViewObject()
                       .executeQuery();
                System.out.println("User Name   : " + JSFUtils.resolveExpression("#{sessionScope.userInfo.userName}"));
                JSFUtils.setExpressionValue("#{bindings.UpdatedBy1.inputValue}",
                                            "" + JSFUtils.resolveExpression("#{sessionScope.userInfo.userName}"));
                JSFUtils.setExpressionValue("#{bindings.PartyName1.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.PartyFullName.inputValue}"));


                String flg = (String) ADFContext.getCurrent()
                                                .getSessionScope()
                                                .get("policeFlag");
                System.out.println("flgflgflgflgflgflg" + flg);
                if (flg.compareTo("0") == 0) {
                  //  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal(2));
                  //  JSFUtils.setExpressionValue("#{bindings.IsActive.inputValue}", "N");
                }
                
                        callJsFunction("pressButton");
                    
               
                if (JSFUtils.resolveExpression(" #{bindings.CaseSchemaId.inputValue}") != null) {

                    String caseSchemaId = "0";
                    if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null)
                        caseSchemaId = (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") + "");
                    System.err.println(3647 + "FFFFFFFF" + caseSchemaId);
                    BigDecimal csId = new BigDecimal(0);

                    if (JSFUtils.resolveExpression(" #{bindings.CaseSchemaId.inputValue}") != null)
                        csId = new BigDecimal(Integer.parseInt(caseSchemaId));

                    if (csId.compareTo(new BigDecimal(3646)) == 0 || csId.compareTo(new BigDecimal(3647)) == 0 ||
                        csId.compareTo(new BigDecimal(3648)) == 0) {
                        System.err.println("NOT EQUALE NULL ");
                        JSFUtils.addToSession("Gnayaat", true);

                        System.out.println("OMARRRRRRRRR   -" +
                                           JSFUtils.resolveExpression(" #{bindings.CriminalType.inputValue}"));
                        if (JSFUtils.resolveExpression(" #{bindings.CriminalType.inputValue}") != null &&
                            JSFUtils.resolveExpression(" #{bindings.CriminalType.inputValue}") != "")
                            JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(2));
                        //   ADFUtil.findIterator("ExcFolderDataVO1Iterator").getViewObject().getCurrentRow().setAttribute("CriminalType", new BigDecimal(2));
                    } else {
                        if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
                            JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));

                    }

                }
                if (JSFUtils.resolveExpression(" #{bindings.JudgmentType.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
                JSFUtils.refreshComponent("pt5");
                this.getSaveBTN().setDisabled(false);
                this.getSaveReport25().setDisabled(false);
                JSFUtils.refreshComponent("b1");

            }
            
            
            BigDecimal degree = (BigDecimal)  JSFUtils.resolveExpression ("#{bindings.BusinessCaseDegree.inputValue}");
            Boolean ff =
                JSFUtils.resolveExpressionAsBoolean("#{bindings.CourtBcHearingResPartiesVO1.estimatedRowCount > '0'}");
            System.out.println("ffffffffff**----*-**-*-*-"+ff);
            RichPanelGroupLayout rpg = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl53");
            if (degree.compareTo(new BigDecimal(1)) == 0 && ff) {

                rpg.setRendered(true);
            } else {

                JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
//                rpg.setRendered(false);
                System.out.println("FFFFFFFFFFFFFFFFFFFTTTTTTTTTTTTTTTTTTTTTTT");
            }
            JSFUtils.refreshComponent("pg48");
            JSFUtils.refreshComponent("pgl53");
            
            //        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov3");
            //        lov.setValue("");
            callJsFunction("pressButton");

            vec55.add(parId);
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("vecParty", vec55);
            

        }
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov3");
        lov.setValue("");
        JSFUtils.refreshComponent("ilov3");
        JSFUtils.refreshComponent("ph5");
        callJsFunction("pressButton");
    }
    
    
    public void addPartyPI(ClientEvent clientEvent) {
        // Add event code here... ...
        JSFUtils.addToPageFlowScope("dimmed", true);
        JSFUtils.addToSession("ADD_PARTY_PI", false);
        Boolean check = true;
        BigDecimal parId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyId2.inputValue}");

        Vector vect = new Vector();
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("vecParty") != null)
            vect = (Vector) ADFContext.getCurrent()
                                      .getSessionScope()
                                      .get("vecParty");


        for (int x = 0; x < vect.size(); x++)

        {
            System.out.println(parId + " ===================== " + vect.get(x));


            BigDecimal partyId = (BigDecimal) vect.get(x);

            if (partyId.compareTo(parId) == 0) {
                check = false;
                System.out.println("FOUNDDDDDDDDDDDDDDDDDD");

            }
        }

        if (check) {

            Boolean type = true;

            System.err.println("GGGGGGGGGGGGGGGG" + JSFUtils.resolveExpression("#{bindings.PartyId2.inputValue}"));
            ADFUtil.findOperation("addNewconvictedParty").execute();
            if (JSFUtils.resolveExpression("#{bindings.PartyFound.inputValue}") != null)
                type = (Boolean) JSFUtils.resolveExpression("#{bindings.PartyFound.inputValue}");
            if (type) {
                System.err.println("type " + type);
//                ADFUtil.findOperation("CreateInsertFolderData").execute();
                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator")
                       .getViewObject()
                       .executeQuery();
                System.out.println("User Name   : " + JSFUtils.resolveExpression("#{sessionScope.userInfo.userName}"));
                JSFUtils.setExpressionValue("#{bindings.UpdatedBy1.inputValue}",
                                            "" + JSFUtils.resolveExpression("#{sessionScope.userInfo.userName}"));
                JSFUtils.setExpressionValue("#{bindings.PartyName1.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.PartyFullName.inputValue}"));


                String flg = (String) ADFContext.getCurrent()
                                                .getSessionScope()
                                                .get("policeFlag");
                System.out.println("flgflgflgflgflgflg" + flg);
                
                
                        callJsFunction("pressButton");
                    
               
                if (JSFUtils.resolveExpression(" #{bindings.CaseSchemaId.inputValue}") != null) {

                    String caseSchemaId = "0";
                    if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null)
                        caseSchemaId = (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") + "");
                    System.err.println(3647 + "FFFFFFFF" + caseSchemaId);
                    BigDecimal csId = new BigDecimal(0);

                    if (JSFUtils.resolveExpression(" #{bindings.CaseSchemaId.inputValue}") != null)
                        csId = new BigDecimal(Integer.parseInt(caseSchemaId));

                    if (csId.compareTo(new BigDecimal(3646)) == 0 || csId.compareTo(new BigDecimal(3647)) == 0 ||
                        csId.compareTo(new BigDecimal(3648)) == 0) {
                        System.err.println("NOT EQUALE NULL ");
                        JSFUtils.addToSession("Gnayaat", true);

                        
                }
                if (JSFUtils.resolveExpression(" #{bindings.JudgmentType.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
                JSFUtils.refreshComponent("pt5");
                this.getSaveBTN().setDisabled(false);
                this.getSaveReport25().setDisabled(false);
                JSFUtils.refreshComponent("b1");

            }
            
            
                BigDecimal degree =
                    (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}");
                Boolean ff =
                    JSFUtils.resolveExpressionAsBoolean("#{bindings.CourtBcHearingResPartiesVO1.estimatedRowCount > '0'}");
            System.out.println("ffffffffff**----*-**-*-*-"+ff);
            RichPanelGroupLayout rpg = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl53");
            if (degree.compareTo(new BigDecimal(1)) == 0 && ff) {

                rpg.setRendered(true);
            } else {

                JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal(1));
//                rpg.setRendered(false);
                System.out.println("FFFFFFFFFFFFFFFFFFFTTTTTTTTTTTTTTTTTTTTTTT");
            }
            JSFUtils.refreshComponent("pg48");
            JSFUtils.refreshComponent("pgl53");
            
            //        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov3");
            //        lov.setValue("");
            callJsFunction("pressButton");

            vec55.add(parId);
                ADFContext.getCurrent()
                          .getSessionScope()
                          .put("vecParty", vec55);
            

        }
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov3");
        lov.setValue("");
        JSFUtils.refreshComponent("ilov3");
        callJsFunction("pressButton");
    }
    }

    public String backLog() {
        // Add event code here...newSessionJudg

        String flag = (String) ADFUtil.evaluateEL("#{pageFlowScope.back}");
        System.err.println("$$$  " + flag);
        //        RichButton backLogBtn = (RichButton) JSFUtils.findComponentInRoot("b24");

        this.getSaveBTN().setDisabled(true);
        this.getSaveReport25().setDisabled(true);
        //        backLogBtn.setSelected(true);
        //        backLogBtn.setDisabled(true);

        JSFUtils.addToPageFlowScope("showDate", "true");
        if (new BigDecimal(flag).compareTo(new BigDecimal(1)) != 0)
            ADFUtil.findOperation("newSessionJudg").execute();
        JSFUtils.addToPageFlowScope("partyName", false);
        JSFUtils.addToPageFlowScope("backLog", true);
        JSFUtils.refreshComponent("pg51");
        //        this.getSaveBTN().setDisabled(false);
        this.getDetails().setRendered(true);
        JSFUtils.addToPageFlowScope("back", "1");
        //        this.getHearingDate().setRendered(true);
        return null;
    }
    
    public String newGnayaat_newCase() {
        newGnayaat();
        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("policeFlag", "1");
        RichButton butt = (RichButton) JSFUtils.findComponentInRoot("b32");
        butt.setDisabled(true);
        
        return null;
    }

    public String forwardLogGnayaat() {
        // Add event code here...
        BigDecimal type = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
        BigDecimal CaseSchemaId = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null)
            CaseSchemaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}");

        String flag = (String) ADFUtil.evaluateEL("#{pageFlowScope.back}");
        System.err.println(CaseSchemaId + "$$$  " + flag);
        //        RichButton backLogBtn = (RichButton) JSFUtils.findComponentInRoot("b24");
        if (type.compareTo(new BigDecimal(5)) != 0) {
            this.getSaveBTN().setDisabled(true);
            this.getSaveReport25().setDisabled(true);

            //        backLogBtn.setSelected(true);
            //        backLogBtn.setDisabled(true);

            JSFUtils.addToPageFlowScope("showDate", "true");
            if (new BigDecimal(flag).compareTo(new BigDecimal(1)) != 0 &&
                CaseSchemaId.compareTo(new BigDecimal(3646)) != 0 &&
                CaseSchemaId.compareTo(new BigDecimal(3647)) != 0 &&
                CaseSchemaId.compareTo(new BigDecimal(3648)) != 0) {
                ADFUtil.findOperation("newSessionJudg").execute();
                this.getDetails().setRendered(true);
            }
            JSFUtils.addToPageFlowScope("partyName", false);
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.refreshComponent("pg51");
            //        this.getSaveBTN().setDisabled(false);

            JSFUtils.addToPageFlowScope("back", "2");

            callJsFunction("pressButton");
            //        this.getHearingDate().setRendered(true);

        }
        return null;
    }


    public String newGnayaat() {
        // Add event code here...
        BigDecimal type = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
        BigDecimal CaseSchemaId = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null)
            CaseSchemaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}");

        String flag = (String) ADFUtil.evaluateEL("#{pageFlowScope.back}");
        System.err.println(CaseSchemaId + "$$$  " + flag+"    "+type);
        //        RichButton backLogBtn = (RichButton) JSFUtils.findComponentInRoot("b24");
        if (type.compareTo(new BigDecimal(5)) != 0) {
            try{
            this.getSaveBTN().setDisabled(true);
            this.getSaveReport25().setDisabled(true);
            } catch (Exception e) {
            }
            System.err.println(CaseSchemaId + "dddd  " + flag);
            //        backLogBtn.setSelected(true);
            //        backLogBtn.setDisabled(true);

            JSFUtils.addToPageFlowScope("showDate", "true");
            if (new BigDecimal(flag).compareTo(new BigDecimal(1)) != 0) {
                ADFUtil.findOperation("newSessionJudg").execute();
                System.err.println(CaseSchemaId + "qqqqq  " + flag);
                this.getDetails().setRendered(true);
                RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc34");
                sc.setReadOnly(true);
                System.err.println(CaseSchemaId + "ttttttttt  " + flag);
            }
            JSFUtils.addToPageFlowScope("partyName", false);
            JSFUtils.addToPageFlowScope("backLog", true);
            JSFUtils.refreshComponent("pg51");
            //        this.getSaveBTN().setDisabled(false);

            JSFUtils.addToPageFlowScope("back", "2");

            callJsFunction("pressButton");
            System.err.println(CaseSchemaId + "uuuuuuuu  " + flag);
            //        this.getHearingDate().setRendered(true);
            ADFUtil.setBoundAttributeValue("JudgmentType", BigDecimal.ONE );
        }
        return null;
    }

    public String forwardLog() {
        // Add event code here...
        JSFUtils.addToPageFlowScope("back", "2");
        JSFUtils.addToPageFlowScope("showDate", "false");
        JSFUtils.addToPageFlowScope("backLog", false);
        JSFUtils.addToPageFlowScope("partyName", true);
        this.getSaveBTN().setDisabled(false);
        /** Added by M.Salah **/
        this.getSaveReport25().setDisabled(false);
        JSFUtils.refreshComponent("pg51");
        return null;
    }


    public void setSaveBTN(RichButton saveBTN) {
        this.saveBTN = saveBTN;
    }

    public RichButton getSaveBTN() {
        return saveBTN;
    }

    public void setHearingDate(RichInputDate hearingDate) {
        this.hearingDate = hearingDate;
    }

    public RichInputDate getHearingDate() {
        return hearingDate;
    }

    public String testMessage() {
        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");

        return null;
    }

    public void setBtnMode2(RichPanelLabelAndMessage btnMode2) {
        this.btnMode2 = btnMode2;
    }

    public RichPanelLabelAndMessage getBtnMode2() {
        return btnMode2;
    }

    public void setTestLink(RichLink testLink) {
        this.testLink = testLink;
    }

    public RichLink getTestLink() {
        return testLink;
    }

    public void setOnePartyBTN(RichButton onePartyBTN) {
        this.onePartyBTN = onePartyBTN;
    }

    public RichButton getOnePartyBTN() {
        return onePartyBTN;
    }

    public void setChargMany(RichPanelHeader chargMany) {
        this.chargMany = chargMany;
    }

    public RichPanelHeader getChargMany() {
        return chargMany;
    }

    public void setDescMany(RichPanelHeader descMany) {
        this.descMany = descMany;
    }

    public RichPanelHeader getDescMany() {
        return descMany;
    }

    public void setChargOne(RichPanelHeader chargOne) {
        this.chargOne = chargOne;
    }

    public RichPanelHeader getChargOne() {
        return chargOne;
    }

    public void setDescOne(RichPanelHeader descOne) {
        this.descOne = descOne;
    }

    public RichPanelHeader getDescOne() {
        return descOne;
    }

    public void setCounting(BigDecimal counting) {
        this.counting = counting;
    }

    public BigDecimal getCounting() {
        return counting;
    }

    public void setError_label(RichActiveOutputText error_label) {
        this.error_label = error_label;
    }

    public RichActiveOutputText getError_label() {
        return error_label;
    }

    public String getOrder() {
        BigDecimal order = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyOrder1.inputValue}");
        System.err.println("AAAAAAAAA  " + order);
        String literalValueOf = ArabicLiteralNumberParser.literalValueOf(order);
        return "ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ " + literalValueOf;
    }


    public void getNIDValues(ReturnEvent returnEvent) {
        System.out.println("returnEvent National Id Service ");

        // Add event code here...
        String clickedButton = null;
        if (JSFUtils.getFromSession("clickedButton") != null) {
            clickedButton = JSFUtils.getFromSession("clickedButton").toString();
        }
        if (clickedButton != null && clickedButton.equals("save")) {
            System.err.println("^&^&^&^&^&^&" + returnEvent.getReturnValue());
            PersonData pd = (PersonData) JSFUtils.getFromPageFlowScope("nIdResponseParam");
            if (pd != null) {

                System.err.println("######################" + pd.getFirstName());
                JSFUtils.setExpressionValue("#{bindings.IdNumber.inputValue}", pd.getNationalId());
                JSFUtils.setExpressionValue("#{bindings.PartyFullName.inputValue}",
                                            pd.getFirstName() + " " + pd.getFatherFullName());
                JSFUtils.refreshComponent("it16");
                JSFUtils.setExpressionValue("#{bindings.City.inputValue}", new BigDecimal(pd.getGovernateCode()));

                System.out.println("Police :::" + pd.getPoliceAddressCode());
                if (pd.getPoliceAddressCode().length() > 0)
                    JSFUtils.setExpressionValue("#{bindings.EntityPolice.inputValue}",
                                                new BigDecimal(pd.getPoliceAddressCode()));
                JSFUtils.setExpressionValue("#{bindings.Address.inputValue}",
                                            pd.getGovernate() + " - " + pd.getPoliceAddress() + " - " +
                                            pd.getAddress());
                RichInputText lov = (RichInputText) JSFUtils.findComponentInRoot("it16");
               
                setName((String) JSFUtils.resolveExpression("#{bindings.PartyFullName.inputValue}"));
                lov.setValue(""+pd.getFirstName() + " " + pd.getFatherFullName());
                JSFUtils.refreshComponent("it14");
                JSFUtils.refreshComponent("it16");
                JSFUtils.refreshComponent("it12");
                JSFUtils.refreshComponent("sdi1");
                JSFUtils.refreshComponent("soc23");
                JSFUtils.refreshComponent("pg102");
                JSFUtils.refreshComponent("pg108");
                JSFUtils.refreshComponent("pg18");
                JSFUtils.refreshComponent("pg19");
                JSFUtils.refreshComponent("it16");
                JSFUtils.refreshComponent("pg19");
               
                //            JSFUtils.refreshComponent("pg17");


                pd.getNationalId();
                pd.getFactoryCardNumber();
                pd.getCardExpirationDate();
                pd.getJobDescription();
                pd.getGovernate();
                pd.getPoliceAddress();
                pd.getDateOfDeath();
                pd.getFirstName();
                pd.getFatherFullName();
                pd.getGender();
                pd.getBirthDate();
                pd.getMaritalStatus();
                pd.getMotherFirstName();
                pd.getMotherSecondName();
                pd.getMotherThirdName();
                pd.getAddress();
                pd.getStatus();
                pd.setResponseCode(new Integer(0));
System.out.println("GENDER    ----"+ pd.getGender());
if( pd.getGender().compareTo("1")==0)
{
        JSFUtils.setExpressionValue("#{bindings.Gender.inputValue}", "M");
    }
else
    JSFUtils.setExpressionValue("#{bindings.Gender.inputValue}", "F");
                JSFUtils.setExpressionValue("#{bindings.PartyStatus.inputValue}", new BigDecimal(1));
                JSFUtils.setExpressionValue("#{bindings.IdType.inputValue}", new BigDecimal(2));
                JSFUtils.setExpressionValue("#{bindings.UpdateType.inputValue}",
                                            "Y");
                //          ..it16  ADFUtil.setBoundAttributeValue("ClaimNumber", personData.getFirstName());
                //            ADFUtil.refreshComponent("it1");
            }


        } else if (clickedButton != null && clickedButton.equals("cancel")) {
            
            PersonData pd = (PersonData) JSFUtils.getFromPageFlowScope("nIdResponseParam");
            if (pd != null) {
                
                if(pd.getResponseMessage().trim().equalsIgnoreCase("\u0628\u0637\u0627\u0642\u0629 \u0645\u0646\u062a\u0647\u064a\u0629 \u0627\u0644\u0635\u0644\u0627\u062d\u064a\u0629"))
                {  System.err.println(pd.getResponseCode()+"Expired NID"+pd.getResponseMessage());

                    JSFUtils.setExpressionValue("#{bindings.UpdateType.inputValue}",
                                                "E");
            
                }
               else if(pd.getResponseMessage().trim().equalsIgnoreCase("\u0627\u0644\u0628\u0637\u0627\u0642\u0629 \u063a\u064a\u0631 \u0645\u0648\u062c\u0648\u062f\u0629"))
                {  System.err.println(pd.getResponseCode()+"Unknown NID"+pd.getResponseMessage());

                    JSFUtils.setExpressionValue("#{bindings.UpdateType.inputValue}",
                                                "U");
                
                }
                
                else{
            
            System.err.println("CANCELLLLLLLLLLLLLLLLLL");
            JSFUtils.setExpressionValue("#{bindings.PartyStatus.inputValue}", new BigDecimal(3));
            JSFUtils.setExpressionValue("#{bindings.IdNumber.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.IdType.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.UpdateType.inputValue}",
                                        "N");
                }
            }
        }


        System.err.println("-----------------------" + clickedButton);
    }


    public void LedgerVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(3));
        System.err.println(valueChangeEvent.getNewValue()+"&&&" + JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        if (valueChangeEvent != null) {
            if (JSFUtils.resolveExpression("#{bindings.FolderTypeCrime.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", new BigDecimal(1));
            //        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(3));


            if (new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"))
                .compareTo(new BigDecimal(3643)) == 0) {
                if (JSFUtils.resolveExpression("#{bindings.FolderTypeAll.inputValue}") != null)
                    JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", new BigDecimal(2));
                JSFUtils.setExpressionValue("#{bindings.CrimeDate.inputValue}", "");
                JSFUtils.setExpressionValue("#{bindings.CrimeDateTo.inputValue}", "");
                JSFUtils.setExpressionValue("#{bindings.InTimeDescription.inputValue}", "");
                //
                System.out.println("bindings.FolderTypeAll.inputValue}");
            } else {
                if (JSFUtils.resolveExpression("#{bindings.FolderTypeCrime.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", new BigDecimal(2));
                System.out.println("HHHHHHHHHHHHHHHHHH");
            }
        }
    }


    public Boolean ValidatePenaltyParty() {
        Boolean check = false;
        Boolean foreverFlag = false;
        Boolean executionFlag = false;
        BigDecimal years = BigDecimal.ZERO;
        BigDecimal days = BigDecimal.ZERO;
        BigDecimal months = BigDecimal.ZERO;

        BigDecimal guranteeAmount = BigDecimal.ZERO;
        BigDecimal workAmount = BigDecimal.ZERO;
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        BigDecimal unDefineedPenalty = BigDecimal.ZERO;
        BigDecimal folderType = BigDecimal.ZERO;
        BigDecimal bussinessCaseType = BigDecimal.ZERO;
        Boolean stopExecutionPrison =
            (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}") != null)
            bussinessCaseType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}") != null)
            foreverFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}") != null)
            executionFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") != null)
            folderType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}") != null)
            months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}") != null)
            days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}") != null)
            years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}");


        if (JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}") != null)
            unDefineedPenalty = (BigDecimal) JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}") != null)
            workAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}") != null)
            penaltyAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}");


        if (JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}") != null)
            guranteeAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}");

        if ((bussinessCaseType.compareTo(new BigDecimal(5)) == 0 ||
             bussinessCaseType.compareTo(new BigDecimal(15)) == 0) &&
            JSFUtils.resolveExpression("#{bindings.MeasureEndDate1.inputValue}") == null ||
            JSFUtils.resolveExpression("#{bindings.MeasureType1.inputValue}") == null) {
            check = false;
            System.err.println("111111111111111111111111111");
        }

        if (JSFUtils.resolveExpression("#{bindings.MeasureEndDate1.inputValue}") != null || !JSFUtils.resolveExpression("#{bindings.MeasureType1.inputValue}")
                                                                                                     .toString()
                                                                                                     .isEmpty()) {
            check = true;
            System.err.println(JSFUtils.resolveExpression("#{bindings.MeasureEndDate1.inputValue}") +
                               "22222222222222222222222222" + JSFUtils.resolveExpression("#{bindings.MeasureType1.inputValue}")
                                                                      .toString()
                                                                      .isEmpty());
        }

        System.out.println("years " + years + " days " + days + " months " + months + " guranteeAmount " +
                           guranteeAmount + guranteeAmount + " workAmount " + workAmount + " penaltyAmount " +
                           penaltyAmount + " unDefineedPenalty " + unDefineedPenalty+"  stopExecutionPrison "+stopExecutionPrison + " foreverFlag "+foreverFlag +" executionFlag "+executionFlag);
        if (
            folderType.compareTo(new BigDecimal(50)) == 0 || folderType.compareTo(new BigDecimal(51)) == 0 ||  folderType.compareTo(new BigDecimal(100)) == 0 || folderType.compareTo(new BigDecimal(101)) == 0 ||
        folderType.compareTo(new BigDecimal(2)) == 0 || folderType.compareTo(new BigDecimal(1)) == 0 ||
            folderType.compareTo(new BigDecimal(5)) == 0   || folderType.compareTo(new BigDecimal(21)) == 0
                  || folderType.compareTo(new BigDecimal(20)) == 0   || folderType.compareTo(new BigDecimal(22)) == 0
            ) {


            if ((years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
                months.compareTo(BigDecimal.ZERO) == 1 )|| (guranteeAmount).intValue() > 0 || foreverFlag ||
                executionFlag) {
                check = true;
                System.out.println("YYYYYYYYYYYYY");
                if (stopExecutionPrison) {
                 
                    ViewObject vo = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO1Iterator").getViewObject();
                    
                    if ((workAmount).intValue() > 0 || (penaltyAmount).intValue() > 0 ||
                        unDefineedPenalty.intValue() > 0 ||  vo.getRowCount() > 0) {
                        check = true;
                    } else
                        check = false;

                }
                
                
               
                
                
            }


        }

        else if ((workAmount).intValue() > 0 || (penaltyAmount).intValue() > 0 || unDefineedPenalty.intValue() > 0) {
            check = true;
        }

        BigDecimal foldType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
        ViewObject vo = ADFUtil.findIterator("ExcCourtBcDecisionFeesVO1Iterator").getViewObject();
        if ((  foldType.compareTo(new BigDecimal(53)) == 0 || foldType.compareTo(new BigDecimal(6)) == 0) &&
            vo.getRowCount() > 0) {
            System.out.println("555555555555");
            check = true;
        }
        
        
        if (folderType.compareTo(new BigDecimal (12))==0){
            check = true;
        }

        //        if (bussinessCaseType.compareTo(new BigDecimal (5))==0 ||bussinessCaseType.compareTo(new BigDecimal (15))==0){
        //            check = true; }
        if (isFolderType()) {
           check=true;
       }
        
        if (folderType.compareTo(new BigDecimal(51)) == 0 || folderType.compareTo(new BigDecimal(50)) == 0 || folderType.compareTo(new BigDecimal(1)) == 0 
            || folderType.compareTo(new BigDecimal(2)) == 0 || folderType.compareTo(new BigDecimal(101)) == 0|| folderType.compareTo(new BigDecimal(100)) == 0){
            
            
        if((years.compareTo(BigDecimal.ZERO) == 0 && days.compareTo(BigDecimal.ZERO) == 0 &&
        months.compareTo(BigDecimal.ZERO) == 0 ))
        
        {
                check = false; 
            }
        
        
                if ((years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
                    months.compareTo(BigDecimal.ZERO) == 1 )|| (guranteeAmount).intValue() > 0 || foreverFlag ||
                    executionFlag) {
                    check = true;}
        
            }
        
        
        if (folderType.compareTo(new BigDecimal(3)) == 0 ){
            
                if ((workAmount).intValue() > 0 || (penaltyAmount).intValue() > 0 || unDefineedPenalty.intValue() > 0) {
                            check = true;
                        } 
       
            }
        
        
        System.out.println(" Validate Penalty Check  :---:0000:  " + check);
        return check;
    }

    public Boolean validatePenalty() {

        Boolean check = false;
        Boolean foreverFlag = false;
        Boolean executionFlag = false;
        BigDecimal years = BigDecimal.ZERO;
        BigDecimal days = BigDecimal.ZERO;
        BigDecimal months = BigDecimal.ZERO;

        BigDecimal guranteeAmount = BigDecimal.ZERO;
        BigDecimal workAmount = BigDecimal.ZERO;
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        BigDecimal bussinessCaseType = BigDecimal.ZERO;
        Boolean stopExecutionPrison =
            (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag1.inputValue}");


        if (JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}") != null)
            bussinessCaseType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ForeverFlag1.inputValue}") != null)
            foreverFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ForeverFlag1.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ExecutionFlag3.inputValue}") != null)
            executionFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ExecutionFlag3.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.WorkAmount1.inputValue}") != null)
            workAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.WorkAmount1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.PenaltyAmount1.inputValue}") != null)
            penaltyAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PenaltyAmount1.inputValue}");

        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths1.inputValue}") != null)
            months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentDays1.inputValue}") != null)
            days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears1.inputValue}") != null)
            years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.GuranteeAmount1.inputValue}") != null)
            guranteeAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.GuranteeAmount1.inputValue}");

        System.out.println("HHHHHHHHHHHHHHHHHHH     " +
                           ((bussinessCaseType.compareTo(new BigDecimal(5)) == 0 ||
                             bussinessCaseType.compareTo(new BigDecimal(15)) == 0) + "   JJJJ  " +
                            (JSFUtils.resolveExpression("#{bindings.MeasureEndDate.inputValue}") == null ||
                             JSFUtils.resolveExpression("#{bindings.MeasureType.inputValue}") == null)));
        if ((bussinessCaseType.compareTo(new BigDecimal(5)) == 0 ||
             bussinessCaseType.compareTo(new BigDecimal(15)) == 0) &&
            JSFUtils.resolveExpression("#{bindings.MeasureEndDate.inputValue}") == null ||
            JSFUtils.resolveExpression("#{bindings.MeasureType.inputValue}") == null) {
            check = false;
            System.err.println("111111111111111111111111111");
        }

        if (JSFUtils.resolveExpression("#{bindings.MeasureEndDate.inputValue}") != null || !JSFUtils.resolveExpression("#{bindings.MeasureType.inputValue}")
                                                                                                    .toString()
                                                                                                    .isEmpty()) {
            check = true;
            System.err.println(JSFUtils.resolveExpression("#{bindings.MeasureEndDate.inputValue}") +
                               "22222222222222222222222222" + JSFUtils.resolveExpression("#{bindings.MeasureType.inputValue}")
                                                                      .toString()
                                                                      .isEmpty());
        }
        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
            months.compareTo(BigDecimal.ZERO) == 1 || ((BigDecimal) guranteeAmount).intValue() > 0 ||
            ((BigDecimal) workAmount).intValue() > 0 || ((BigDecimal) penaltyAmount).intValue() > 0 || foreverFlag ||
            executionFlag) {


            check = true;
            System.err.println("3333333333333333333333333333333");

            if (stopExecutionPrison) {
                if ((workAmount).intValue() > 0 || (penaltyAmount).intValue() > 0) {
                    check = true;
                } else
                    check = false;

            }

        }


        System.out.println(" Validate Penalty Check  :***::  " + check);
        return check;
    }

    public void selectionListen(SelectionEvent selectionEvent) {
        System.err.println("SELECTIONNNNNNNNNNNNNNNNNNNNN");
        RichTable _table = (RichTable) selectionEvent.getSource();
        //the Collection Model is the object that provides the
        //structured data
        //for the table to render
        CollectionModel _tableModel = (CollectionModel) _table.getValue();
        //the ADF object that implements the CollectionModel is
        //JUCtrlHierBinding. It is wrapped by the CollectionModel API
        JUCtrlHierBinding _adfTableBinding = (JUCtrlHierBinding) _tableModel.getWrappedData();
        //Acess the ADF iterator binding that is used with
        //ADF table binding
        DCIteratorBinding _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding();

        //the role of this method is to synchronize the table component

        //selection with the selection in the ADF model
        Object _selectedRowData = _table.getSelectedRowData();
        //cast to JUCtrlHierNodeBinding, which is the ADF object
        //that represents a row
        JUCtrlHierNodeBinding _nodeBinding = (JUCtrlHierNodeBinding) _selectedRowData;
        //get the row key from the node binding and set it
        //as the current row in the iterator
        Key _rwKey = _nodeBinding.getRowKey();
        _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true));

        ViewObject vo = ADFUtil.findIterator("DecisionDescVO1Iterator").getViewObject();


        System.err.println(JSFUtils.resolveExpression("#{bindings.DescriptionId1.inputValue}") + "***SSSS***" +
                           vo.getCurrentRow().getAttribute("DescriptionId"));


        // Add event code here...
    }

    public void undefinedPenaltyCombVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.storeOnSession("undefinedPenaltyComb", valueChangeEvent.getNewValue());
    }


    public void workAmountPenaltyCombVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.storeOnSession("workAmountPenaltyComb", valueChangeEvent.getNewValue());
    }


    public void PenaltyCombVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.storeOnSession("PenaltyComb", valueChangeEvent.getNewValue());
    }


    public void undefinedPenaltyCombFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        if ((Boolean) valueChangeEvent.getNewValue()) {
            if (JSFUtils.getFromSession("undefinedPenaltyComb") == null)
                JSFUtils.storeOnSession("undefinedPenaltyComb",
                                        JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}"));
            else

                JSFUtils.setExpressionValue("#{bindings.UnDefineedPenalty.inputValue}",
                                            JSFUtils.getFromSession("undefinedPenaltyComb"));
        }

    }


    public void workAmountPenaltyCombFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        if ((Boolean) valueChangeEvent.getNewValue()) {

            if (JSFUtils.getFromSession("workAmountPenaltyComb") == null)
                JSFUtils.storeOnSession("workAmountPenaltyComb",
                                        JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}"));

            else

                JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}",
                                            JSFUtils.getFromSession("workAmountPenaltyComb"));

        }
    }


    public void PenaltyCombFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        if ((Boolean) valueChangeEvent.getNewValue()) {

            if (JSFUtils.getFromSession("PenaltyComb") == null)
                JSFUtils.storeOnSession("PenaltyComb",
                                        JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}"));

            else

                JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}",
                                            JSFUtils.getFromSession("PenaltyComb"));
        }
    }


    public String prosecution() {
        // Add event code here...
        ADFUtil.findOperation("newProsectionJudgment").execute();
        this.getDetails().setRendered(true);
        if (JSFUtils.resolveExpression("#{bindings.Source.inputValue}") == null)
            JSFUtils.setExpressionValue("#{bindings.Source.inputValue}", "Criminal Justice");
        //        JSFUtils.addToPageFlowScope("partyName", false);
        //        JSFUtils.addToPageFlowScope("backLog", true);
        //        JSFUtils.refreshComponent("pg51");
        JSFUtils.refreshComponent("ph1");
        //        //        this.getSaveBTN().setDisabled(false);
        //        this.getDetails().setRendered(true);
        //        JSFUtils.addToPageFlowScope("back", "1");


        callJsFunction("pressButton");
        return null;
    }

    public void changeExecutionFlg() {


        //                BigDecimal bcDeg = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}");
        //            BigDecimal attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}");
        //            BigDecimal folderType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
        //               Boolean stopExec = (Boolean)  JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}");
        //            Boolean stopExecPrison = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");
        //     System.out.println("bcDeg:"+bcDeg+" folderType:"+folderType+" attendance:"+attendance+" stopExec:"+stopExec+" stopExecPrison:"+stopExecPrison);

        //          if(bcDeg.compareTo(new BigDecimal(1))==0 && attendance.compareTo(new BigDecimal(1))==0 &&stopExec && (folderType.compareTo(new BigDecimal(1))==0 ||   folderType.compareTo(new BigDecimal(2))==0))
        //          {
        //              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +2);
        //                  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
        //
        //              }
        //          else if(bcDeg.compareTo(new BigDecimal(2))==0 &&stopExec)
        //          {
        //              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +2);
        //                  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
        //
        //              }
        //          else{
        //              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +1);
        //            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (1));
        //          }


    }

    public void partyStatusVCL(ValueChangeEvent valueChangeEvent) {

       // changeExecutionFlg();
        // Add event code here...
        //        System.out.println("FFFFFFFFF  "+valueChangeEvent.getNewValue());
        //        BigDecimal val = new BigDecimal (0);
        //
        //           String nValue =   valueChangeEvent.getNewValue().toString()  ;
        //        if (nValue.equalsIgnoreCase("1")) {
        //            System.err.println("ExecutionFlag = "+4);
        //            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (4));
        //        }
        //
        //        if (nValue.equalsIgnoreCase("2")) {
        //            System.err.println("ExecutionFlag = "+1);
        //            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (1));
        //        }
        //        if (nValue.equalsIgnoreCase("3")) {
        //            System.err.println("ExecutionFlag = "+2);
        //            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
        //        }

        String  automationFlag= (String)JSFUtils.resolveExpression("#{sessionScope.userInfo.automationFlag}");
        System.err.println("Bayoumi >>>>>> automationFlag"+automationFlag);
        if (automationFlag.equals("N")) {
//            JSFUtils.setExpressionValue("#{bindings.ManualSeq.inputValue}", "Y");
            JSFUtils.setExpressionValue("#{bindings.ManualSeq.inputValue}", true);
            System.err.println("Bayoumi >>>>>> ManualSeq");

            System.out.println("ManualSeq-- 0000 ----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
    }


    public void partyStatus()

    {
        //String nValue = JSFUtils.resolveExpression("#{bindings.Flex1.inputValue}").toString();
      //  if (nValue.equalsIgnoreCase("1")) {
            //             JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (4));
       // }

        changeExecutionFlg();
    }

    public void setFileDocument28(UploadedFile fileDocument28) {
        this.fileDocument28 = fileDocument28;
    }

    public UploadedFile getFileDocument28() {
        return fileDocument28;
    }

    public String newCJProsecution() {
        // Add event code here...
        return null;
    }
    BigDecimal IdDesc = new BigDecimal(0);


    public void enterMany(ClientEvent clientEvent) {
        // Add event code here...
        System.err.println("##########");

        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("descionId2");
        if (lov.getValue() != null) {
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(false);
            JSFUtils.refreshComponent("b1");
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "true");
            //   RichPanelHeader
            RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
            RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
            RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
            RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
            phDescOne.setRendered(false);
            phChargOne.setRendered(false);
            phChargMany.setRendered(true);
            phDescMany.setRendered(true);
            System.err.println("GGGGGGGGGGGGGGGG");
            BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id3.inputValue}");
            System.err.println("GGGGGGGGGGGGGGGG" + id);
            this.setIdDesc(id);
            if (check(id))
                ADFUtil.findOperation("CreateWithParams").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("descionId2");
        }
        JSFUtils.refreshComponent("pg25");
    }

    public void insertCrime(ClientEvent clientEvent) {
        // Add event code here...
        System.err.println("##########");

        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("descionId2");
        if (lov.getValue() != null) {
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(true);
            normalSave.setDisabled(false);
            JSFUtils.refreshComponent("b1");
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "true");
            //   RichPanelHeader

            System.err.println("GGGGGGGGGGGGGGGG");
            BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id3.inputValue}");
            System.err.println("GGGGGGGGGGGGGGGG" + id);
            this.setIdDesc(id);
            if (check(id))
                ADFUtil.findOperation("CreateWithParams").execute();
            this.getSaveBTN().setDisabled(false);
            lov.setValue("");
            JSFUtils.refreshComponent("descionId2");
        }
        JSFUtils.refreshComponent("pg25");
    }


    Vector vec33 = new Vector();
    Vector vec55 = new Vector();

    public Boolean check(BigDecimal id) {

        System.out.println("GGG  " + id);
        Boolean check = true;
        Vector vect = new Vector();
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("vec3") != null)
            vect = (Vector) ADFContext.getCurrent()
                                      .getSessionScope()
                                      .get("vec3");

        System.err.println("VEC-------***" + ADFContext.getCurrent()
                                                       .getSessionScope()
                                                       .get("vec3"));

        for (int x = 0; x < vect.size(); x++)

        {
            System.out.println(" ===================== " + vect.get(x));


            BigDecimal crimId = (BigDecimal) vect.get(x);

            if (crimId.compareTo(id) == 0) {
                check = false;
                System.out.println("FOUNDDDDDDDDDDDDDDDDDD");
                return check;
            }

        }
        vec33.add(id);
        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("vec3", vec33);

        return check;
    }

    public void customListenerdesTable(SelectionEvent selectionEvent) {

        //               vo.applyViewCriteria(null);
        //            vo.executeQuery();
        String adfSelectionListener = "#{bindings.FormCaseDescriptionDesVO1.collectionModel.makeCurrent}";
        //                "#{bindings.DecisionDescVO1.collectionModel.makeCurrent}";
        //make sure the default selection listener functionality is
        //preserved. you don't need to do this for multi select trees
        //as the ADF binding only supports single current row selection

        /* START PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();

        MethodExpression me = null;
        me =
            exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class,
                                               new Class[] { SelectionEvent.class });
        me.invoke(elCtx, new Object[] { selectionEvent });
        System.err.println("::::::::::");
        this.onTableRowSelection(selectionEvent);
        DCIteratorBinding itorBinding = ADFUtil.findIterator("FormCaseDescriptionDesVO1Iterator");
        BigDecimal descriptionId = (BigDecimal) itorBinding.getCurrentRow().getAttribute("DescriptionId");

        BigDecimal Id = (BigDecimal) itorBinding.getCurrentRow().getAttribute("Id");
        System.out.println(Id + "---Id--------descriptionId----" + descriptionId);
        this.setIdDesc(descriptionId);
        //            ViewObject vo = ADFUtil.findIterator("FormCaseDescriptionDesPartVO2Iterator").getViewObject();
        //            //
        //            RowSetIterator rws = vo.createRowSetIterator(null);
        //            System.out.println("%%%"+rws.getRowCount());
        //                while (rws.hasNext()) {
        //                Row rw = rws.next();
        //
        //                BigDecimal id = (BigDecimal) rw.getAttribute("Id");
        //                System.out.println("^^^" + id + "****" + Id);
        //                    if (id.compareTo(Id) == 0) {
        //
        //                        System.out.println("^^^" + id + "**000000**" + Id);
        //
        //                        vo.setCurrentRow(rw);
        //                        break;
        //                    }
        //                }


        //           ViewCriteria vc = vo.createViewCriteria();
        //          ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        //          vcr.setAttribute("Id", Id);
        //          vc.insertRow(vcr);
        //          vo.appendViewCriteria(vc);
        //          vo.executeQuery();
    }


    public void customListener(SelectionEvent selectionEvent) {

        //               vo.applyViewCriteria(null);
        //            vo.executeQuery();
        String adfSelectionListener = "#{bindings.DecisionDescVO11.collectionModel.makeCurrent}";
        //                "#{bindings.DecisionDescVO1.collectionModel.makeCurrent}";
        //make sure the default selection listener functionality is
        //preserved. you don't need to do this for multi select trees
        //as the ADF binding only supports single current row selection

        /* START PRESERVER DEFAULT ADF SELECT BEHAVIOR */
        FacesContext fctx = FacesContext.getCurrentInstance();
        Application application = fctx.getApplication();
        ELContext elCtx = fctx.getELContext();
        ExpressionFactory exprFactory = application.getExpressionFactory();

        MethodExpression me = null;
        me =
            exprFactory.createMethodExpression(elCtx, adfSelectionListener, Object.class,
                                               new Class[] { SelectionEvent.class });
        me.invoke(elCtx, new Object[] { selectionEvent });
        System.err.println("::::::::::");
        this.onTableRowSelection(selectionEvent);
        DCIteratorBinding itorBinding = ADFUtil.findIterator("DecisionDescVO1Iterator");
        BigDecimal descriptionId = (BigDecimal) itorBinding.getCurrentRow().getAttribute("DescriptionId");

        BigDecimal Id = (BigDecimal) itorBinding.getCurrentRow().getAttribute("Id");
        System.err.println(Id + "---Id--------descriptionId----" + descriptionId);
//        ViewObject vo = ADFUtil.findIterator("FormCaseDescriptionDesPartVO2Iterator").getViewObject();
        //
//        RowSetIterator rws = vo.createRowSetIterator(null);
//        System.out.println("%%%" + rws.getRowCount());
//        while (rws.hasNext()) {
//            Row rw = rws.next();

//            BigDecimal id = (BigDecimal) rw.getAttribute("Id");
//            System.out.println("^^^" + id + "****" + Id);
//            if (id.compareTo(Id) == 0) {

//                System.out.println("^^^" + id + "**000000**" + Id);

//                vo.setCurrentRow(rw);
//                
                
                ViewObject vo2 = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
                //
                RowSetIterator rws2 = vo2.createRowSetIterator(null);
                System.out.println("%%%" + rws2.getRowCount());
                while (rws2.hasNext()) {
                    Row rw2 = rws2.next();

                    BigDecimal id2 = (BigDecimal) rw2.getAttribute("PartyCrimeId");
                    BigDecimal id4 = (BigDecimal) rw2.getAttribute("ChargeDecisionId");
                    System.err.println("^^^" + id2 + "****" + id4);
                    if (id2.compareTo(Id) == 0) {

                        System.out.println("^^^" + Id + "**000000**" + id2);

                        vo2.setCurrentRow(rw2);
                        
                        
                        ViewObject vo3 = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
                        //
                        vo3.applyViewCriteria(null);
                        vo3.executeQuery();
                        RowSetIterator rws3 = vo3.createRowSetIterator(null);
                        System.out.println("%%%--" + rws3.getRowCount());
                        while (rws3.hasNext()) {
                            Row rw3 = rws3.next();

                            BigDecimal id3 = (BigDecimal) rw3.getAttribute("Id");
                            System.out.println("^^^--------" + id2 + "****" + Id);
                            if (id3.compareTo(id4) == 0) {

                                System.out.println("^^^--------*****----------" + id4 + "**000000**" + id3);

                                vo3.setCurrentRow(rw3);
                                
                                
//                                ViewCriteria vc = vo3.createViewCriteria();
//                              ViewCriteriaRow vcr = vc.createViewCriteriaRow();
//                                                 vcr.setAttribute("Id", id3);
//                                                 vc.insertRow(vcr);
//                                                 vo3.applyViewCriteria(vc);
//                                                 vo3.executeQuery();
                                
                                break;
                            }
                        }
                        
                        
                        
                        break;
                    }
                }
                
                
                
                
                
//                break;
//            }
//        }


        //           ViewCriteria vc = vo.createViewCriteria();
        //          ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        //          vcr.setAttribute("Id", Id);
        //          vc.insertRow(vcr);
        //          vo.appendViewCriteria(vc);
        //          vo.executeQuery();
    }


    public void onTableRowSelection(SelectionEvent selectionEvent) {
        RichTable _table = (RichTable) selectionEvent.getSource();
        CollectionModel model = (CollectionModel) _table.getValue();
        JUCtrlHierBinding _binding = (JUCtrlHierBinding) model.getWrappedData();
        DCIteratorBinding iteratorBinding = _binding.getDCIteratorBinding();
        Object _selectedRowData = _table.getSelectedRowData();
        JUCtrlHierNodeBinding node = (JUCtrlHierNodeBinding) _selectedRowData;
        Key rwKey = node.getRowKey();
        iteratorBinding.setCurrentRowWithKey(rwKey.toStringFormat(true));


    }

    public void setFileDocument29(UploadedFile fileDocument29) {
        this.fileDocument29 = fileDocument29;
    }

    public UploadedFile getFileDocument29() {
        return fileDocument29;
    }

    public void addressVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println(" ----  " + valueChangeEvent.getNewValue());
        // Add event code here...CityId
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        if (flag) {
            JSFUtils.setExpressionValue("#{bindings.EntityPolice.inputValue}",
                                        JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
            JSFUtils.setExpressionValue("#{bindings.City.inputValue}",
                                        JSFUtils.resolveExpression("#{bindings.CityId.inputValue}"));
            JSFUtils.setExpressionValue("#{bindings.Address.inputValue}",
                                        "\u0644\u0627 \u064A\u0648\u062C\u062F \u0639\u0646\u0648\u0627\u0646");
        } else {

            JSFUtils.setExpressionValue("#{bindings.EntityPolice.inputValue}", new BigDecimal(0));
            JSFUtils.setExpressionValue("#{bindings.City.inputValue}", new BigDecimal(0));

            JSFUtils.setExpressionValue("#{bindings.Address.inputValue}", "");
        }
    }

    //    public void closePreviewReports(ActionEvent actionEvent)

    public String closePreviewReports() {
        // Add event code here.....


        actionTypes();
        getDegree();
//        JSFUtils.storeOnSession("actionTypeId", JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}"));
        
        Boolean check = true;
        if (JSFUtils.getFromPageFlowScope("showDate")
                    .toString()
                    .equalsIgnoreCase("true")) {
            check = (Boolean) ADFUtil.findOperation("validateCourtBCHearingDate").execute();
            System.err.println("CloseEEE  " + check);
        } else
            ADFContext.getCurrent()
                      .getSessionScope()
                      .put("CheckHearingDate", 0);

        JSFUtils.addToPageFlowScope("backLog", null);
        JSFUtils.addToPageFlowScope("detail", false);
        this.getDetails().setRendered(false);
        if (this.getBtNMode() != null)
            this.getBtNMode().setVisible(false);
        if (this.getBtnMode2() != null)
            this.getBtnMode2().setVisible(false);
        this.getReturnFlag().setValue("false");

        not_Exisit_Label.setVisible(false);
        exisit_Label.setVisible(false);
        error_label.setVisible(false);
        //        searchImge.setRendered(true);
        //        searchImge.setVisible(true);
        JSFUtils.addToPageFlowScope("disabledValidate", false);
        JSFUtils.addToPageFlowScope("decision", "0");
        JSFUtils.addToPageFlowScope("back", "0");
        this.getSaveBTN().setDisabled(true);
        this.getSaveReport25().setDisabled(true);
        fileSessionRepor = null;
        fileJudgeDecision = null;
        fileNeyaba = null;
        fileDocument = null;
        filePolice = null;
        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
        btnOne.setSelected(false);
        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
        btnMany.setSelected(false);

        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
        btnNew.setSelected(false);
        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
        btnOld.setSelected(false);


        RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
        RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
        RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
        RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
        phDescOne.setRendered(true);
        phChargOne.setRendered(true);
        phChargMany.setRendered(false);
        phDescMany.setRendered(false);


        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
        normalSave.setRendered(true);
        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
        CustomSave.setRendered(false);
        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
        searchBtn.setDisabled(false);

        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
        oneParty.setRendered(true);
        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
        manyParty.setRendered(false);

        if (check) {
            System.out.println("&&&&&&&&.......&&&&");
            ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
            ADFUtil.findOperation("Commit").execute();

        }
        return "PreviewReports";
    }

    public String back() {
        JSFUtils.setExpressionValue("#{bindings.NeyabGozayaa.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.NeyabKolyaa.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.ParYear.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseType.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.PoliceNo.inputValue}", "");
        ADFUtil.findOperation("Rollback").execute();
        System.err.println("Rollback Rollback Rollback Rollback");
        return "finish";
    }


    public String deleteGenericDesc() {
        // Add event code here...
        //        ADFUtil.evaluateEL(el)

        BigDecimal formCaseDescriptionDesID = this.getIdDesc();
        //            (BigDecimal) ADFUtil.findIterator("FormCaseDescriptionDesVO1Iterator").getViewObject().getCurrentRow().getAttribute("DescriptionId");
        //        ADFUtil.evaluateEL("#{row.bindings.DescriptionId.inputValue}");
        System.err.println("formCaseDescriptionDesID  " + formCaseDescriptionDesID);


        Vector vect = new Vector();
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("vec3") != null)
            vect = (Vector) ADFContext.getCurrent()
                                      .getSessionScope()
                                      .get("vec3");

        System.err.println("VEC" + ADFContext.getCurrent()
                                             .getSessionScope()
                                             .get("vec3"));

        for (int x = 0; x < vect.size(); x++)

        {
            System.out.println(" ===================== " + vect.get(x));


            BigDecimal crimId = (BigDecimal) vect.get(x);

            if (crimId.compareTo(formCaseDescriptionDesID) == 0) {

                System.out.println("DeleteEEEEEEEEEEEEEEEEEE");
                vect.remove(x);
            }

        }

        ADFContext.getCurrent()
                  .getSessionScope()
                  .put("vec3", vect);
        return null;
    }

    public void setIdDesc(BigDecimal IdDesc) {
        this.IdDesc = IdDesc;
    }

    public BigDecimal getIdDesc() {
        return IdDesc;
    }

    public void setSaveReport25(RichButton saveReport25) {
        this.saveReport25 = saveReport25;
    }

    public RichButton getSaveReport25() {
        return saveReport25;
    }


    public void downloadAllAttachedFiles(FacesContext context, OutputStream out) throws IOException {

        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(ADFUtil.evaluateEL("#{row.Did}").toString(), "");
            fis = content.getFileContent();

            int read;
            byte[] bytes = new byte[1024];
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.flush();
        }
    }

    public void hasrNumVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        BigDecimal neyabbaKolyaa = new BigDecimal(0);
        BigDecimal degree = new BigDecimal(0);
        BigDecimal businessCaseType = new BigDecimal(0);
        businessCaseType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");

        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("actionTypeId") != null)
            degree = (BigDecimal) ADFContext.getCurrent()
                                            .getSessionScope()
                                            .get("actionTypeId");

        Boolean  updatedDateFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.UpdatedDate2.inputValue eq null}");
        Boolean gnayaatFlag = false;
        Boolean journalistFlag = false;
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("JournalistFlag") != null)
            journalistFlag = (Boolean) ADFContext.getCurrent()
                                                 .getSessionScope()
                                                 .get("JournalistFlag");
        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("GnayaatFlag") != null)
            gnayaatFlag = (Boolean) ADFContext.getCurrent()
                                              .getSessionScope()
                                              .get("GnayaatFlag");

        if (ADFContext.getCurrent()
                      .getSessionScope()
                      .get("NeyabaaKolyaa") != null) {
            neyabbaKolyaa = new BigDecimal(ADFContext.getCurrent()
                                                     .getSessionScope()
                                                     .get("NeyabaaKolyaa") + "");

            if (journalistFlag || gnayaatFlag || degree.compareTo(new BigDecimal(3)) == 0 ||
                degree.compareTo(new BigDecimal(4)) == 0 || businessCaseType.compareTo(new BigDecimal(5)) == 0 ||
                businessCaseType.compareTo(new BigDecimal(15)) == 0 && updatedDateFlag) {
                //                    this.setPoliceStationEntityId(neyabbaKolyaa);
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}", neyabbaKolyaa);
                //--
                System.err.println("folder Data Store Neyabaa Kolyaa insted of Police--------------");
            } else

            {
                if( updatedDateFlag)
                JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
            }

        }

    }

    public void inTimeVcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        JSFUtils.setExpressionValue("#{bindings.CrimeDate.inputValue}", "");


    }
    
    public void print() {
        String judgText = "";
        String judg = "";
        String comments = "";
        BigDecimal years = BigDecimal.ZERO;
        BigDecimal days = BigDecimal.ZERO;
        BigDecimal months = BigDecimal.ZERO;
        BigDecimal attendance = BigDecimal.ZERO;
        BigDecimal guranteeAmount = BigDecimal.ZERO;
        BigDecimal workAmount = BigDecimal.ZERO;
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        BigDecimal undefinedPenalty = BigDecimal.ZERO;
        BigDecimal PenaltyFlag = BigDecimal.ONE;
        Boolean executionFlag = false ;
        Boolean foreverFlag = false ;
        Boolean hardLabourFlag = false ;
        Boolean stopExecutionPrison = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");
        System.out.println("JSFUtils.resolveExpression(\"#{bindings.ExecutionFlag.inputValue}\")"+JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}"));
        
        if (JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}") != null)
            executionFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}") != null)
            foreverFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.HardLabourFlag.inputValue}") != null)
            hardLabourFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.HardLabourFlag.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}") != null)
            judg = (String) JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}") != null)
            PenaltyFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}");
        else if (JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}") != null)
            workAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}") != null)
            penaltyAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}") != null)
            undefinedPenalty = (BigDecimal) JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}");
        comments = (String) JSFUtils.resolveExpression("#{bindings.Comments.inputValue}");
        System.err.println(attendance + "              PenaltyFlag         " + PenaltyFlag);
        //            BigDecimal attendance = new BigDecimal (attend.toString());bindings.Comments.inputValue
        if (PenaltyFlag.compareTo(new BigDecimal(2)) == 0) {
            judgText = " \u0627\u0645\u0631\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629 ";

        } else if (PenaltyFlag.compareTo(new BigDecimal(1)) == 0) {
            judgText = " \u062d\u0643\u0645\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629";
        } else if (PenaltyFlag.compareTo(new BigDecimal(3)) == 0) {
            judgText = "\u0627\u0645\u0631\u062a \u0627\u0644\u0646\u064a\u0627\u0628\u0629 ";
        }
        
        System.out.println("attendance   *** "+attendance);
        
        if (attendance.compareTo(new BigDecimal(1)) == 0) {
            judgText = judgText + "  \u062d  ";
        } else if (attendance.compareTo(new BigDecimal(2)) == 0) {
            judgText = judgText + "  \u062d \u002d \u0623 ";
        } else if (attendance.compareTo(new BigDecimal(3)) == 0) {
            judgText = judgText + " \u063a ";
        }
        else if (attendance.compareTo(new BigDecimal(4)) == 0) {
                   judgText = judgText + " \u062d \u002d\u062a  ";
               }
         else if (attendance.compareTo(new BigDecimal(5)) == 0) {
            System.out.println("5555");
            judgText = judgText + " \u063a\u002d\u0642  ";
        }
        
        
        
        if (judg != null)
        {
            judgText = judgText +     judg + " ";
            }
        
        System.out.println("GFFGFFGFG   - "+ JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}"));

        
      
        Boolean stopExecution = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}");
        Boolean penFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.PenaltyFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}") != null)
            months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}") != null)
            days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}") != null)
            years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}");

        if (foreverFlag)
            judgText = judgText + " \u0628\u0627\u0644\u0645\u0624\u0628\u062f ";

        
    if (executionFlag)
    judgText = judgText + " \u0628\u0627\u0644\u0627\u0639\u062f\u0627\u0645 ";
    
        if( JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null){
        int SchemaId  = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}")+"");
        if (SchemaId== 3647  || SchemaId== 3646  || SchemaId== 3648){
        if(years.compareTo(new BigDecimal(5)) == 1)
        judgText = judgText + " \u0633\u062c\u0646 ";
        else
         judgText = judgText + " \u062d\u0628\u0633 ";
        }
        }
        else{
            BigDecimal foldType = new BigDecimal (1);
            if(JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") != null)
                foldType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
            
            if ((foldType.compareTo(new BigDecimal(13)) == 0 || foldType.compareTo(new BigDecimal(15)) == 0 ||
                foldType.compareTo(new BigDecimal(14)) == 0 || foldType.compareTo(new BigDecimal(18)) == 0 ||
                foldType.compareTo(new BigDecimal(16)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0
                || foldType.compareTo(new BigDecimal(19)) == 0
            ) ){
                if (foldType.compareTo(new BigDecimal(13))  == 0)
                judgText = judgText + " \u062a\u0648\u0628\u064a\u062e ";
                
                if (foldType.compareTo(new BigDecimal(14))  == 0)
                judgText = judgText + " \u062a\u0633\u0644\u064a\u0645 \u0627\u0644\u062d\u062f\u062b ";
                
                if (foldType.compareTo(new BigDecimal(15))  == 0)
                judgText = judgText + " \u0627\u0644\u062a\u062d\u0627\u0642 \u0628\u062a\u062f\u0631\u064a\u0628 \u0645\u0647\u0646\u0649 ";
                
                if (foldType.compareTo(new BigDecimal(16))  == 0)
                judgText = judgText + " \u0627\u0644\u0632\u0627\u0645 \u0628\u0648\u0627\u062c\u0628\u0627\u062a \u0645\u0639\u064a\u0646\u0629 ";
                
                if (foldType.compareTo(new BigDecimal(17))  == 0)
                judgText = judgText + " \u0627\u0644\u0627\u062e\u062a\u0628\u0627\u0631 \u0627\u0644\u0642\u0636\u0627\u0626\u0649 ";
                
                if (foldType.compareTo(new BigDecimal(18))  == 0)
                judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0624\u0633\u0633\u0627\u062a \u0627\u0644\u0631\u0639\u0627\u064a\u0629 \u0627\u0644\u0627\u062c\u062a\u0645\u0627\u0639\u064a\u0629 ";
                
                if (foldType.compareTo(new BigDecimal(19))  == 0)
                judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0633\u062a\u0634\u0641\u064a\u0627\u062a \u0627\u0644\u0645\u062a\u062e\u0635\u0635\u0629 ";
                
                if ((foldType.compareTo(new BigDecimal(15)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0) )
                judgText = judgText + " \u0639\u062f\u062f ";
            }
            else
            judgText = judgText + " \u062d\u0628\u0633 ";
            
        }
    
    
    
        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||

            months.compareTo(BigDecimal.ZERO) == 1) {
            if( JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null){
    int SchemaId  = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}")+"");
    if (SchemaId== 3647  || SchemaId== 3646  || SchemaId== 3648){
        if(years.compareTo(new BigDecimal(5)) == 1)
        judgText = judgText + " \u0633\u062c\u0646 ";  
        else
             judgText = judgText + " \u062d\u0628\u0633 ";
    }
            }
            else{
                BigDecimal foldType = new BigDecimal (1);
                if(JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") != null)
                    foldType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
                
                if ((foldType.compareTo(new BigDecimal(13)) == 0 || foldType.compareTo(new BigDecimal(15)) == 0 ||
                    foldType.compareTo(new BigDecimal(14)) == 0 || foldType.compareTo(new BigDecimal(18)) == 0 ||
                    foldType.compareTo(new BigDecimal(16)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0
                    || foldType.compareTo(new BigDecimal(19)) == 0
                ) ){
                    if (foldType.compareTo(new BigDecimal(13))  == 0)
                    judgText = judgText + " \u062a\u0648\u0628\u064a\u062e ";
                    
                    if (foldType.compareTo(new BigDecimal(14))  == 0)
                    judgText = judgText + " \u062a\u0633\u0644\u064a\u0645 \u0627\u0644\u062d\u062f\u062b ";
                    
                    if (foldType.compareTo(new BigDecimal(15))  == 0)
                    judgText = judgText + " \u0627\u0644\u062a\u062d\u0627\u0642 \u0628\u062a\u062f\u0631\u064a\u0628 \u0645\u0647\u0646\u0649 ";
                    
                    if (foldType.compareTo(new BigDecimal(16))  == 0)
                    judgText = judgText + " \u0627\u0644\u0632\u0627\u0645 \u0628\u0648\u0627\u062c\u0628\u0627\u062a \u0645\u0639\u064a\u0646\u0629 ";
                    
                    if (foldType.compareTo(new BigDecimal(17))  == 0)
                    judgText = judgText + " \u0627\u0644\u0627\u062e\u062a\u0628\u0627\u0631 \u0627\u0644\u0642\u0636\u0627\u0626\u0649 ";
                    
                    if (foldType.compareTo(new BigDecimal(18))  == 0)
                    judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0624\u0633\u0633\u0627\u062a \u0627\u0644\u0631\u0639\u0627\u064a\u0629 \u0627\u0644\u0627\u062c\u062a\u0645\u0627\u0639\u064a\u0629 ";
                    
                    if (foldType.compareTo(new BigDecimal(19))  == 0)
                    judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0633\u062a\u0634\u0641\u064a\u0627\u062a \u0627\u0644\u0645\u062a\u062e\u0635\u0635\u0629 ";
                    
                    if ((foldType.compareTo(new BigDecimal(15)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0) )
                    judgText = judgText + " \u0639\u062f\u062f ";
                }
                else
                judgText = judgText + " \u062d\u0628\u0633 ";
                
            }
            if (years.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + years + " \u0633\u0646\u0629 ";
            }
            if (months.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + months + " \u0634\u0647\u0631 ";
            }
            if (days.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + days + " \u064a\u0648\u0645 ";
            }


        }
        
        if (hardLabourFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0627\u0634\u063a\u0627\u0644 \u0627\u0644\u0634\u0627\u0642\u0647 ";

        if (JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}") != null)
            guranteeAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}");
        if (((BigDecimal) guranteeAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u0643\u0641\u0627\u0644\u0629 " + guranteeAmount + " \u062c\u002d\u0645 ";
        }

        if (((BigDecimal) workAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u064a\u0648\u0645\u064a\u0629  " + workAmount + " \u062c\u002d\u0645 ";
        }
        if (((BigDecimal) penaltyAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u062b\u0627\u0628\u062a\u0629  " + penaltyAmount + " \u062c\u002d\u0645 ";
        }
        if (((BigDecimal) undefinedPenalty).intValue() > 0) {
            judgText = judgText + " \u0648\u063a\u0631\u0627\u0645\u0629 " + undefinedPenalty + " % ";
        }
        
        System.err.println(stopExecution + "@@@@@@@@@@  " + penFlag);
        if (!stopExecution && penFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0646\u0641\u0627\u0630 ";
        else if (stopExecution && !penFlag) {
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u0627\u0644\u062a\u0646\u0641\u064a\u0630 ";
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));
        } else if (stopExecution && penFlag)
            judgText = judgText + " ";


        if (stopExecutionPrison)
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u062a\u0646\u0641\u064a\u0630 \u0627\u0644\u062d\u0628\u0633 ";

        System.err.println("MAGGGGGGGGGGG" + comments);
        if (comments != null) {
            //                    judgText = judgText+" ï¿½  " +comments ;
            judgText = judgText + "  " + comments;
        }

        JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", judgText);
        System.out.println(stopExecutionPrison +"Judge  >>> " + judgText);
        

        System.err.println(years);
        
        JSFUtils.refreshComponent("ot8999");
    }

    public void folderChildVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        BigDecimal nValue = (BigDecimal) valueChangeEvent.getNewValue(); 
        JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", nValue);
        System.out.println("nValue   is"+nValue);
        JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", nValue);
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}") != null) {
          
           
            if (nValue.compareTo(new BigDecimal(8)) == 0) {
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", new BigDecimal(0));
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", new BigDecimal(0));
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", new BigDecimal(0));
                JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));
            } else {
                JSFUtils.setExpressionValue("#{bindings.MeasureType1.inputValue}", null);
                JSFUtils.setExpressionValue("#{bindings.MeasureEndDate1.inputValue}", null);

            }
        }
//        if (null!=valueChangeEvent.getNewValue()) {
//           JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", valueChangeEvent.getNewValue());
//       }
         //=========================reset Data================
       
        resetDateWhenChooseFolderType(valueChangeEvent);
        
        changeExecutionFlg();
        
        print();
       
    }
    
    private void resetDateWhenChooseFolderType(ValueChangeEvent valueChangeEvent) {
           Object newValue = valueChangeEvent.getNewValue();
           if (null != newValue) {
               int folderChildNew = Integer.parseInt(newValue.toString());
               if (13 == folderChildNew || 14 == folderChildNew || 15 == folderChildNew || 16 == folderChildNew ||
                   17 == folderChildNew || 18 == folderChildNew || 19 == folderChildNew) {
                   JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", new BigDecimal(0));
                   JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", new BigDecimal(0));
                   JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", new BigDecimal(0));
                   JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));
                   JSFUtils.setExpressionValue("#{bindings.GuaranteeFlag1.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.StopExecutionPrisonFlag.inputValue}", false);

                   JSFUtils.setExpressionValue("#{bindings.WorkAmountFlag1.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.PenaltyAmountFixedFlag1.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.UndefinedPenaltyFlag.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.WorkAmountComFlag.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.PenaltyAmountComFlag.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.UndefineedPenaltyComFlag.inputValue}", false);
                   JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}", null);
                   JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}", null);
                   JSFUtils.setExpressionValue("#{bindings.UnDefineedPenalty.inputValue}", null);

                   JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", " ");
               }
           }
       }
    
    public boolean checkNumberIsInteger(int num) {
        boolean flag = false;
        
        
        if (num>=1)
            flag= true ;
                else
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "DUB_FOLDER_NUM");
        
        
        return flag ;
        
        }
    

    public void seqVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    
        
        try {
           valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
           JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", valueChangeEvent.getNewValue());
            System.out.println(JSFUtils.resolveExpression("#{bindings.SequenceNumber.inputValue}") + "---" +
                               valueChangeEvent.getNewValue());
           entityVCL();
//           validateSequenceNumber();
       } catch (Exception e) {
            // TODO: Add catch code
//            e.printStackTrace();
            System.err.println("DUB_FOLDER_NUM   DUB_FOLDER_NUM");
                 
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "DUB_FOLDER_NUM");
        }
     
    }

    public void subJudgmentVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        // Add event code here...
        System.out.println("valueChangeEvent.getNewValue()     ---  " + valueChangeEvent.getNewValue()
                                                                                        .toString()
                                                                                        .length());
       
//        RichSelectOneChoice soc3 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc3");
         if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue()
                                                                      .toString()
                                                                      .length() > 0) {
//             soc3.setRequired(false);
            BigDecimal newValue = (BigDecimal) valueChangeEvent.getNewValue();
            getJudgmentTypeIdBind().setValue(null);
            getJudgmentTypeIdBind().setValue(null);
            getJudgmentTypeIdBind().setValue(null);
            getJudgmentTypeIdBind().setValue(null);

                getJudgmentTypeIdBind().setValue(null);
                
           
            if (newValue.compareTo(new BigDecimal(7)) != 0 && newValue.compareTo(new BigDecimal(8)) != 0 &&
                newValue.compareTo(new BigDecimal(11)) != 0 && newValue.compareTo(new BigDecimal(12)) != 0 &&
                newValue.compareTo(new BigDecimal(14)) != 0)
                JSFUtils.setExpressionValue("#{bindings.SecondSession.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.UnspecializedFlag.inputValue}",new BigDecimal(0));
        }
//         else
//        soc3.setRequired(true);
    }

    public void guaranteeFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean newValue = (Boolean) valueChangeEvent.getNewValue();
        System.out.println("newValue   " + newValue);
        if (!newValue)
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));

    }

    public void guaranteeFlagVCL2(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean newValue = (Boolean) valueChangeEvent.getNewValue();
        System.out.println("newValue   " + newValue);
        if (!newValue)
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount1.inputValue}", new BigDecimal(0));

    }

    public void inTimeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.CrimeDate.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.CrimeDateTo.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.InTimeDescription.inputValue}", "");
    }

    public void foldVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here... #{bindings.GuiltlessnessFlag.inputValue}
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        try{
        BigDecimal seq = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}");
        JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", "");

        System.out.println(seq + "seq" + JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}"));
        //            JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", seq);
        JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", seq);
        
        System.out.println("@@22"+JSFUtils.resolveExpression("#{bindings.SequenceNumber.inputValue}"));
        } catch (Exception e) {
             // TODO: Add catch code
        //            e.printStackTrace();
             System.err.println("DUB_FOLDER_NUM   DUB_FOLDER_NUM");
                  
             JSFUtils.showFacesErrorMessageFromBundle("bundle", "DUB_FOLDER_NUM");
         }
        BigDecimal newValue = (BigDecimal) valueChangeEvent.getNewValue();
        JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", newValue);
        if (newValue.compareTo(new BigDecimal(3)) == 0) {

            System.out.println("newValue   ------  " + newValue);
        }
        JSFUtils.setExpressionValue("#{bindings.GuiltlessnessFlag.inputValue}", "N");
        TestBean testBean = new TestBean();
        testBean.print();
        changeExecutionFlg();
    }

    public void load(ClientEvent clientEvent) {
        System.out.println("GGGGGGGGGGGGGGGGGG");
        //        moveToProcedureScreen();
    }

    public String moveToProcedureScreen() {

        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.cancelFlag eq 'true'}")){
        // Add event code here...
        //        ADFUtil.findOperation("Rollback").execute();
        ExcTemplate temp = new ExcTemplate();
        temp.checkDirtyDataAction();
        System.out.println("MOVED");
        JSFUtils.setExpressionValue("#{bindings.SearchType.inputValue}", new BigDecimal(1));
        JSFUtils.addToSession("pSequence",
                              new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")));
        JSFUtils.addToSession("pYear",
                              new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.ParYear1.inputValue}")));
        JSFUtils.addToSession("pBCType",
                              new BigDecimal("" +
                                             JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}")));
        JSFUtils.addToSession("pType", new Integer(1));
        JSFUtils.addToSession("pMoveFlg", true);
        JSFUtils.addToSession("pPolice",
                              new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")));
        //        InventoryBean inv = new InventoryBean();
        //        inv.doSearchInventory();
        JSFUtils.addToSession("cancelFlag","false");
        }
        return null;
    }

    public void ageVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("AGE is -----  *" + valueChangeEvent.getNewValue());

        if (valueChangeEvent.getNewValue() != null) {

            BigDecimal age = (BigDecimal) valueChangeEvent.getNewValue();
//            if (age.compareTo(new BigDecimal(12)) == 0 || age.compareTo(new BigDecimal(13)) == 0 ||
//                age.compareTo(new BigDecimal(14)) == 0 || age.compareTo(new BigDecimal(15)) == 0) {
//                JSFUtils.setExpressionValue("#{bindings.FolderChild.inputValue}", new BigDecimal(8));
//            }
        }
    }

    public void showErrorMessageForComponentById(String message, String id) {
        FacesContext.getCurrentInstance()
            .addMessage(ADFUtil.findComponentInRoot(id).getClientId(),
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

    private void validateSequenceNumber() {
        Boolean rep = JSFUtils.resolveExpressionAsBoolean("#{bindings.RepeatedFlag.inputValue}");
        OperationBinding bindingId = ADFUtil.findOperation("validateHasr");
        
        bindingId.getParamsMap()
            .put("folderType", JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") + "");
        if (rep)
        bindingId.getParamsMap().put("repeatedFlag", "Y");
        else
            bindingId.getParamsMap().put("repeatedFlag", "N");   
        bindingId.getParamsMap()
            .put("sequenceNumber", JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}") + "");
        bindingId.getParamsMap().put("year", JSFUtils.resolveExpression("#{bindings.Year.inputValue}")+"");
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))
            bindingId.getParamsMap()
                .put("policenumber", JSFUtils.resolveExpression("#{sessionScope.userInfo.errandEntUser}") + "");
        else
            bindingId.getParamsMap()
                .put("policenumber", JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") + "");
        bindingId.getParamsMap()
            .put("criminalType", JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") + "");
        Object obj = bindingId.execute();
        if (obj != null && Integer.parseInt(obj + "") > 0) {
             JSFUtils.setExpressionValue("#{bindings.SequenceNum.inputValue}", null);
            showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062D\u0635\u0631 \u0645\u0648\u062C\u0648\u062F \u0633\u0627\u0628\u0642\u0627",
                                             "it107");
        }
    }

    public void foldYearVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        try{
        BigDecimal seq = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}");
        JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", "");

        System.out.println(seq + "seq" + JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}"));
        //            JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", seq);
        JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", seq);
        
        System.out.println("@@22"+JSFUtils.resolveExpression("#{bindings.SequenceNumber.inputValue}"));
        } catch (Exception e) {
             // TODO: Add catch code
        //            e.printStackTrace();
             System.err.println("DUB_FOLDER_NUM   DUB_FOLDER_NUM");
                  
             JSFUtils.showFacesErrorMessageFromBundle("bundle", "DUB_FOLDER_NUM");
         }

    }

    public void guiltlessVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("FFFFF" + valueChangeEvent.getNewValue());
        Boolean flg = (Boolean) valueChangeEvent.getNewValue();
        
        
        Boolean flag2 = (Boolean) JSFUtils.resolveExpression("#{bindings.Flag.inputValue}");


        if (flg){
            JSFUtils.setExpressionValue("#{bindings.UnspecializedFlag.inputValue}", new BigDecimal(5));
            if (!flag2)
                JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}",
                                            "\u0628\u0631\u0627\u0621\u0629 \u0628\u0627\u0644\u0645\u0635\u0627\u0631\u064a\u0641");
       
       
        } else
            JSFUtils.setExpressionValue("#{bindings.UnspecializedFlag.inputValue}", new BigDecimal(0));
    }

    public String addFees() {
        // Add event code here...
//        JSFUtils.addToSession("skipPenValid", true);
        return null;
    }
    
    List<SelectItem> selectItemList = null;
    ArrayList<SelectItem> newSelectItemList = null;
    SelectItem s = null;

    private SelectItem getSelectItem(SelectItem li) {
        SelectItem s = new SelectItem();
        s.setLabel(li.getLabel());
        s.setValue(li.getValue());
        return s;
    }

    public List<SelectItem> getSelectItemList() {
        return selectItemList;
        
        
    }

    public void setSelectItemList(List<SelectItem> selectItemList) {
        this.selectItemList = selectItemList;
    }

    public void hearingDateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
      
        System.out.println(dateList.get(valueChangeEvent.getNewValue()+"")+"          hearingDateVCL----"+valueChangeEvent.getNewValue());
        JSFUtils.addToSession("depDate", dateList.get(valueChangeEvent.getNewValue()+"").split("-")[0]);
        JSFUtils.setExpressionValue("#{bindings.DateId.inputValue}",
                                    new BigDecimal(valueChangeEvent.getNewValue() + ""));
       System.out.println("@@@@@@@@@"+ JSFUtils.resolveExpression("#{bindings.DateId.inputValue}"));
    }

    public void repeatFolderSeqVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        
        BigDecimal seq = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}");
        JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", "");
        JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", "");
        RichInputText it107 = (RichInputText) JSFUtils.findComponentInRoot("it107");
        it107.setValue("");
        System.out.println(seq + "seq" + JSFUtils.resolveExpression("#{bindings.SeqFolder.inputValue}"));
        //            JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", seq);
        try {
           JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", seq);
       } catch (Exception e) {
            // TODO: Add catch code
           
        }
       
        
        it107.setValue("seq");
        System.out.println("@@2211111"+JSFUtils.resolveExpression("#{bindings.SequenceNumber.inputValue}"));

    }

    public String exceptionTest() {
        // Add event code here...
        for (int x =3; x>-1; x--) {
            System.out.println("################"+x);
            System.out.println(3/x);
           System.err.println("################"+x);
        }
        
        return null;
    }
    
    public List onDecisionSuggestparty(String decision) {
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
          System.err.println("+++++ "+decision);
          BindingContext bctx = BindingContext.getCurrent();
          BindingContainer bindings = bctx.getCurrentBindingsEntry();

          OperationBinding setVariable = (OperationBinding) bindings.get("ExecuteWithParams1");
        setVariable.getParamsMap().put("pBC", new BigDecimal(JSFUtils.getFromSession("PoliceReportId")+""));
          setVariable.getParamsMap().put("BindName", decision);
          setVariable.execute();

        ViewObject vo = ADFUtil.findIterator("FormCaseDescriptionPartiesROVO1Iterator").getViewObject();
        System.err.println("rows = "+vo.getRowCount());

        RowSetIterator rws = vo.createRowSetIterator(null);
             while(rws.hasNext()) {
         Row rw = rws.next();
            selectItems.add(new SelectItem((String) rw.getAttribute("PartyName"),
          (String)rw.getAttribute("PartyName")));
          }

        return selectItems;
    }

    public List onDecisionSuggest(String decision) {
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
          System.err.println("+++++ "+decision);
          BindingContext bctx = BindingContext.getCurrent();
          BindingContainer bindings = bctx.getCurrentBindingsEntry();

          OperationBinding setVariable = (OperationBinding) bindings.get("ExecuteWithParams");
          setVariable.getParamsMap().put("BindName", decision);
          setVariable.execute();

        ViewObject vo = ADFUtil.findIterator("PartCrimTypesROVO1Iterator").getViewObject();
        System.err.println("rows = "+vo.getRowCount());

        RowSetIterator rws = vo.createRowSetIterator(null);
             while(rws.hasNext()) {
         Row rw = rws.next();
            selectItems.add(new SelectItem((String) rw.getAttribute("Name"), (String) rw.getAttribute("Name")));
          }

        return selectItems;
    }

    public List ddd(FacesContext facesContext, AutoSuggestUIHints autoSuggestUIHints) {
        // Add event code here...
        return null;
    }

    public void confirmationSave(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes){
            
           
        JSFUtils.addToSession("automationFlag", true);
            
        codeSave();

        
    }
    }
    
    public void confirmationSave2(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome() == DialogEvent.Outcome.yes){
            
            
        JSFUtils.addToSession("automationFlag", true);
            
        custSave();

        
    }
    }
    
    
    public void codeSave() {
            ADFContext.getCurrent().getSessionScope().put("BC_LAST_STATUS","1");
        setFolderId();
            JUCtrlActionBinding commit = (JUCtrlActionBinding) ADFUtil.findOperation("Commit");
                                    commit.invoke();
                                    if (commit.getErrors().isEmpty()) {
            //                            RichSelectOneChoice soc11 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
            //                            soc11.setValue("");
                                        RichInputDate id20 = (RichInputDate) JSFUtils.findComponentInRoot("id20");
                                        id20.setValue("");
                                        RichInputText it57 = (RichInputText) JSFUtils.findComponentInRoot("it57");
                                        it57.setValue("");
                                        RichInputText it58 = (RichInputText) JSFUtils.findComponentInRoot("it58");
                                        it58.setValue("");
                                        RichSelectOneChoice soc9 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc9");
                                        soc9.setValue("");
                                        RichSelectOneChoice soc100 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc10");
                                        soc100.setValue("");
            
                                                OperationBinding Operation = ADFUtil.findOperation("automationFees");
                                                  Operation.getParamsMap().put("reservationId",Integer.parseInt( JSFUtils.resolveExpression("#{bindings.Id18.inputValue}")+""));
                                            Operation.execute();
                                        commit.invoke();
                                        System.err.println("SAveEEEEEEEEEEEEEEEEE");
                                        /* Error list not empty. Something went wrong. */  
            
            
                                        //                Object execute = ADFUtil.findOperation("Commit").execute();
                                        //                System.err.println("SAVE COMMIT" + execute);
            
            
                                        JSFUtils.addToPageFlowScope("backLog", false);
                                        System.err.println("cOMMIT");
                                        JSFUtils.addToPageFlowScope("backLog", null);
                                        //        JSFUtils.calljqHelper("$.growl.notice({ message: \" ï¿½ï¿½ ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½\" });");
                                                    ADFUtil.findOperation("Rollback").execute();
            //                        JUCtrlActionBinding rollback = (JUCtrlActionBinding) ADFUtil.findOperation("Rollback");
            //                        rollback.invoke();
                                            //     JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                                        // callJsFunction("pressButtonCancel");
                                        this.getDetails().setRendered(false);
                                        if (this.getBtNMode() != null)
                                            this.getBtNMode().setVisible(false);
                                        if (this.getBtnMode2() != null)
                                            this.getBtnMode2().setVisible(false);
                                        this.getReturnFlag().setValue("false");
                                        JSFUtils.addToPageFlowScope("decision", "0");
                                        JSFUtils.addToPageFlowScope("back", "0");
                                        this.getSaveBTN().setDisabled(true);
                                        this.getSaveReport25().setDisabled(true);
                                        not_Exisit_Label.setVisible(false);
                                        exisit_Label.setVisible(false);
                                        error_label.setVisible(false);
                                        //        searchImge.setRendered(true);
                                        //        searchImge.setVisible(true);
                                        JSFUtils.addToPageFlowScope("disabledValidate", false);
            
                                        RichButton btnOne = (RichButton) JSFUtils.findComponentInRoot("b19");
                                        btnOne.setSelected(false);
                                        RichButton btnMany = (RichButton) JSFUtils.findComponentInRoot("b21");
                                        btnMany.setSelected(false);
                                        RichButton btnNew = (RichButton) JSFUtils.findComponentInRoot("b277");
                                        btnNew.setSelected(false);
                                        RichButton btnOld = (RichButton) JSFUtils.findComponentInRoot("b24");
                                        btnOld.setSelected(false);
            
                                        RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
                                        RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
                                        RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
                                        RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
                                        phDescOne.setRendered(true);
                                        phChargOne.setRendered(true);
                                        phChargMany.setRendered(false);
                                        phDescMany.setRendered(false);
            
                                        RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
                                        normalSave.setRendered(true);
                                        RichButton CustomSave = (RichButton) JSFUtils.findComponentInRoot("b12");
                                        CustomSave.setRendered(false);
            
                                        RichButton searchBtn = (RichButton) JSFUtils.findComponentInRoot("b23");
                                        searchBtn.setDisabled(false);
            
                                        RichLink oneParty = (RichLink) JSFUtils.findComponentInRoot("l14");
                                        oneParty.setRendered(true);
                                        RichLink manyParty = (RichLink) JSFUtils.findComponentInRoot("l15");
                                        manyParty.setRendered(false);
            
                                        JSFUtils.addToPageFlowScope("detail", false);
                                        JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                                        System.err.println("cOMMIT");
                                    } else {
                                        //  JSFUtils.addToPageFlowScope("edit", true);
                                        // JSFUtils.setExpressionValue("#{bindings.UpdatedDate2.inputValue}", null);
                                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALID_DATA");
                                    }
            
        }


    public void insertDateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
            JSFUtils.setExpressionValue("#{bindings.ManualSeq1.inputValue}", "Y");
            System.out.println("ManualSeq-- 0000 ----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
    }

    public void courtDecisionVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());

        Row currentRow = ADFUtil.findIterator("CourtBcHearingReservationVO1Iterator").getCurrentRow();
        System.err.println("BBBBBBB>>>> BBB>>>>>> JudgmentTypeId >>> "+valueChangeEvent.getNewValue());
        currentRow.setAttribute("JudgmentTypeId", valueChangeEvent.getNewValue());
        System.err.println("BBBBBBB>>>> BBB>>>>>> JudgmentTypeId >>> "+valueChangeEvent.getNewValue());
       

}

    public void depVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("OTHMAN 1");
//        UIComponent c = valueChangeEvent.getComponent();
//        c.processUpdates(FacesContext.getCurrentInstance());
//        FacesContext.getCurrentInstance().renderResponse();
//        if (null != valueChangeEvent.getNewValue()) {
//            System.out.println("valueChangeEvent :" + valueChangeEvent.getNewValue());
//            //            BigDecimal dependentType = new BigDecimal(valueChangeEvent.getNewValue().toString());
//            // Get main data from report
//            DCIteratorBinding reportItr = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator");
//            ValidatePoliceReportROVORowImpl reportRow = (ValidatePoliceReportROVORowImpl) reportItr.getCurrentRow();
//            BigDecimal year = reportRow.getParYear();
//            BigDecimal businessCaseType = reportRow.getBusinessCaseType();
//            //            BigDecimal veyabGozayaa = reportRow.getNeyabGozayaa();
//            Object attributeValue = ADFUtil.getAttributeValue("PoliceStationEntityId");
//            BigDecimal policeStationEntityId = new BigDecimal(0);
//            if (null != attributeValue) {
//                policeStationEntityId = new BigDecimal(attributeValue.toString());
//                System.out.println("policeStationEntityId : " + policeStationEntityId);
//            }
//            Object id4 = ADFUtil.getAttributeValue("Id4");
//            BigDecimal folderId = new BigDecimal(0);
//            if (null != id4) {
//                folderId = new BigDecimal(id4.toString());
//                System.out.println("folderId : " + folderId);
//            }
//            Object id5 = ADFUtil.getAttributeValue("Id5");
//            BigDecimal decisionId = new BigDecimal(0);
//            if (null != id5) {
//                decisionId = new BigDecimal(id5.toString());
//                System.out.println("decisionId : " + decisionId);
//            }
//            // Set values in ExcCourtBcChrgDecDepOrd current row
//            DCIteratorBinding excCourtBcChrgDecDepOrdItr = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO1Iterator");
//            ViewObject object = excCourtBcChrgDecDepOrdItr.getViewObject();
//            ExcCourtBcChrgDecDepOrdVORowImpl currentRow = (ExcCourtBcChrgDecDepOrdVORowImpl) object.getCurrentRow();
//            currentRow.setEntityId(policeStationEntityId);
//            currentRow.setYear(year);
//            currentRow.setCriminalType(businessCaseType);
//            //            currentRow.setFolderId(folderId);
//            currentRow.setChargeDecisionId(decisionId);
//            //get degree
//            BigDecimal currentDegree = getCurrentDegree(reportRow);
//            System.out.println("currentDegree : " + currentDegree);
//            // get attendance
//            BigDecimal attendance = getCurrentAttendance();
//            System.out.println("attendance : " + attendance);
//            // If degree 1
//            if (currentDegree.compareTo(new BigDecimal("1")) == 0 &&
//                (attendance.compareTo(new BigDecimal("2")) == 0 || attendance.compareTo(new BigDecimal("3")) == 0 ||
//                 attendance.compareTo(new BigDecimal("5")) == 0)) {
//                System.out.println(" SequenceNumber : " + 0);
//                currentRow.setSequenceNumber(new BigDecimal(0));
//            } else {
//                System.out.println(" Get a new sequenceNumber  ");
//                currentRow.setSequenceNumber(new BigDecimal(1));
//            }
//
//        }
    }

    public void depVCL2(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("OTHMAN 2");
        UIComponent c = valueChangeEvent.getComponent();
        c.processUpdates(FacesContext.getCurrentInstance());
        FacesContext.getCurrentInstance().renderResponse();
        if (null != valueChangeEvent.getNewValue()) {
            System.out.println("valueChangeEvent :" + valueChangeEvent.getNewValue());
            //            BigDecimal dependentType = new BigDecimal(valueChangeEvent.getNewValue().toString());
            // Get main data from report
            DCIteratorBinding reportItr = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator");
            ValidatePoliceReportROVORowImpl reportRow = (ValidatePoliceReportROVORowImpl) reportItr.getCurrentRow();
            BigDecimal year = reportRow.getParYear();
            BigDecimal businessCaseType = reportRow.getBusinessCaseType();
            //            BigDecimal veyabGozayaa = reportRow.getNeyabGozayaa();
            Object attributeValue = ADFUtil.getAttributeValue("PoliceStationEntityId");
            BigDecimal policeStationEntityId = new BigDecimal(0);
            if (null != attributeValue) {
                policeStationEntityId = new BigDecimal(attributeValue.toString());
                System.out.println("policeStationEntityId : " + policeStationEntityId);
            }
            Object id4 = ADFUtil.getAttributeValue("Id4");
            BigDecimal folderId = new BigDecimal(0);
            if (null != id4) {
                folderId = new BigDecimal(id4.toString());
                System.out.println("folderId : " + folderId);
            }
            Object id5 = ADFUtil.getAttributeValue("Id5");
            BigDecimal decisionId = new BigDecimal(0);
            if (null != id5) {
                decisionId = new BigDecimal(id5.toString());
                System.out.println("decisionId : " + decisionId);
            }
            // Set values in ExcCourtBcChrgDecDepOrd current row
            DCIteratorBinding excCourtBcChrgDecDepOrdItr = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO2Iterator");
            ViewObject object = excCourtBcChrgDecDepOrdItr.getViewObject();
            ExcCourtBcChrgDecDepOrdVORowImpl currentRow = (ExcCourtBcChrgDecDepOrdVORowImpl) object.getCurrentRow();
            currentRow.setEntityId(policeStationEntityId);
            currentRow.setYear(year);
            currentRow.setCriminalType(businessCaseType);
            currentRow.setFolderId(folderId);
            currentRow.setChargeDecisionId(decisionId);
            //get degree
            BigDecimal currentDegree = getCurrentDegree(reportRow);
            System.out.println("currentDegree : " + currentDegree);
            // get attendance
            BigDecimal attendance = getCurrentAttendance();
            System.out.println("attendance : " + attendance);
            // If degree 1
            if (currentDegree.compareTo(new BigDecimal("1")) == 0 &&
                (attendance.compareTo(new BigDecimal("2")) == 0 || attendance.compareTo(new BigDecimal("3")) == 0 ||
                 attendance.compareTo(new BigDecimal("5")) == 0)) {
                System.out.println(" SequenceNumber : " + 0);
                currentRow.setSequenceNumber(new BigDecimal(0));
            } else {
                currentRow.setSequenceNumber(new BigDecimal(1));
            }

        }
    }

    public BigDecimal getCurrentDegree(ValidatePoliceReportROVORowImpl reportRow) {
        //Degree
        BigDecimal degree = new BigDecimal(0);
        if (null != reportRow.getBcDegreeGonaha() && !reportRow.getBcDegreeGonaha().equals(degree)) {
            degree = reportRow.getBcDegreeGonaha();
            return degree;
        }
        if (null != reportRow.getBcDegreeGnayaat() && !reportRow.getBcDegreeGnayaat().equals(degree)) {
            degree = reportRow.getBcDegreeGnayaat();
            return degree;
        }
        if (null != reportRow.getBusinessCaseDegree() && !reportRow.getBusinessCaseDegree().equals(degree)) {
            degree = reportRow.getBusinessCaseDegree();
            return degree;
        }
        return degree;
    }

    public BigDecimal getCurrentAttendance() {
        //attendance
        DCIteratorBinding resPartiesItr = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator");
        CourtBcHearingResPartiesVORowImpl resPartiesRow =
            (CourtBcHearingResPartiesVORowImpl) resPartiesItr.getCurrentRow();

        BigDecimal attendance = new BigDecimal(0);
        if (null != resPartiesRow.getAttend() && !resPartiesRow.getAttend().equals(attendance)) {
            attendance = resPartiesRow.getAttend();
            return attendance;
        }
        if (null != resPartiesRow.getAttendance() && !resPartiesRow.getAttendance().equals(attendance)) {
            attendance = resPartiesRow.getAttendance();
            return attendance;
        }
        if (null != resPartiesRow.getAttendanceAll() && !resPartiesRow.getAttendanceAll().equals(attendance)) {
            attendance = resPartiesRow.getAttendanceAll();
            return attendance;
        }
        return attendance;
    }


    public void setFolderId() {
        // Add event code here...
        try {
            System.out.println(" **** setFolderId ***** ");
            Object id4 = ADFUtil.getAttributeValue("Id4");
            BigDecimal folderId = new BigDecimal(0);
            if (null != id4) {
                folderId = new BigDecimal(id4.toString());
                System.out.println("folderId : " + folderId);
            }
            DCIteratorBinding excCourtBcChrgDecDepOrdItr = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO1Iterator");
            Row[] allRowsInRange = excCourtBcChrgDecDepOrdItr.getAllRowsInRange();
            System.out.println("allRowsInRange.length : " + allRowsInRange.length);
            if (allRowsInRange.length > 0) {
                for (Row row : allRowsInRange) {
                    ExcCourtBcChrgDecDepOrdVORowImpl currentRow = (ExcCourtBcChrgDecDepOrdVORowImpl) row;
                    currentRow.setFolderId(folderId);
                }
    //                ADFUtil.findOperation("Commit1").execute();
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
    }

    public void setJudgmentTypeIdBind(RichSelectOneChoice judgmentTypeIdBind) {
        this.judgmentTypeIdBind = judgmentTypeIdBind;
    }

    public RichSelectOneChoice getJudgmentTypeIdBind() {
        return judgmentTypeIdBind;
    }

    public void itemListenerVCL(ItemEvent itemEvent) {
        // Add event code here...
    }
}
