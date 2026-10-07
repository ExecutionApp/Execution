package cjexecviewcontroller;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class HasrClaim {
    public HasrClaim() {
    }
    private Boolean validateIDNumber() {
        Boolean valid = true ;
        RichInputText it131 = (RichInputText) JSFUtils.findComponentInRoot("it131");
        it131.setValue(new BigDecimal (0));
        System.out.println(JSFUtils.resolveExpression("#{bindings.IdType.inputValue}")+"validateIDNumber"+ JSFUtils.resolveExpression("#{bindings.IdNumber.inputValue}"));
    String id="21111111111111";
    
        BigDecimal  attendance = new BigDecimal(3);
        if (JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}") != null ){
          attendance = new BigDecimal(JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}")+"");
        }
    
        if ((attendance.compareTo(new BigDecimal(1))==0  ||attendance.compareTo(new BigDecimal(4))==0) && JSFUtils.resolveExpression("#{bindings.IdNumber.inputValue}") ==null)
        {
        
       showErrorMessageForComponentById("\u0628\u0631\u062c\u0627\u0621 \u0627\u0644\u062a\u0623\u0643\u062f \u0645\u0646 \u062a\u062d\u0642\u064a\u0642 \u0627\u0644\u0634\u062e\u0635\u064a\u0629 \u0644\u062c\u0645\u064a\u0639 \u0627\u0644\u0627\u0637\u0631\u0627\u0641" , "id20");
        }
    
    
    
    
      BigDecimal  IdType = new BigDecimal(2);
        if (JSFUtils.resolveExpression("#{bindings.IdType.inputValue}") != null ){
          IdType = new BigDecimal(JSFUtils.resolveExpression("#{bindings.IdType.inputValue}")+"");
        }
        
        if (JSFUtils.resolveExpression("#{bindings.IdNumber.inputValue}") != null ){
          id = JSFUtils.resolveExpression("#{bindings.IdNumber.inputValue}")+"";
      
        
        
        if( IdType.compareTo(new BigDecimal(1)) == 0   )
        {
            if(  ( id.length() < 8))
            
            {valid =false ;
                
                    JSFUtils.showFacesInfoMessageFromBundle("bundle", "CALIMPASS");
                    showErrorMessageForComponentById("\u062C\u0648\u0627\u0632 \u0627\u0644\u0633\u0641\u0631 \u063A\u064A\u0631 \u0635\u062D\u064A\u062D",
                                                     "id20");
                }
            }
        
        
        if( (IdType.compareTo(new BigDecimal(2)) == 0 ||IdType.compareTo(new BigDecimal(3))==0 )  )
        {
            if(  (id.length() < 14))
            
            {valid =false ;
                     JSFUtils.showFacesInfoMessageFromBundle("bundle", "CLAIMID");
                    showErrorMessageForComponentById("\u0628\u0631\u062C\u0627\u0621 \u0627\u0644\u062A\u062D\u0642\u0642 \u0645\u0646 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0642\u0648\u0645\u0649 \u0644\u062C\u0645\u064A\u0639 \u0627\u0644\u0627\u0637\u0631\u0627\u0641",
                                                     "id20");
                }
            }
        
        }
        
        
        return valid ;

    }
    private Boolean validateSequenceNumber() {
        Boolean valid = true ;
        if (JSFUtils.resolveExpression("#{bindings.sequenceFold.inputValue}") != null ){
        String rep = JSFUtils.resolveExpression("#{bindings.RepeatedFlag.inputValue}")+"";
        OperationBinding bindingId = ADFUtil.findOperation("validateHasr");
        
        bindingId.getParamsMap()
            .put("folderType", JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") + "");
       // if (rep)
        bindingId.getParamsMap().put("repeatedFlag", rep);
       // else
          //  bindingId.getParamsMap().put("repeatedFlag", "N");   
        bindingId.getParamsMap()
            .put("sequenceNumber", JSFUtils.resolveExpression("#{bindings.sequenceFold.inputValue}") + "");
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
             JSFUtils.setExpressionValue("#{bindings.sequenceFold.inputValue}", null);
            valid = false ;
            showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062D\u0635\u0631 \u0645\u0648\u062C\u0648\u062F \u0633\u0627\u0628\u0642\u0627",
                                             "it107");
            
        }
        }
        else
            valid = false ;  
        return valid ;
    }

    public void showErrorMessageForComponentById(String message, String id) {
        FacesContext.getCurrentInstance()
            .addMessage(ADFUtil.findComponentInRoot(id).getClientId(),
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }
    public String validateBC() {
        JSFUtils.addToSession("ClaimDetails", true);
        RichSelectOneChoice soc11 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        soc11.setDisabled(false);
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
    
        
        System.err.println("ValidateBC****************");
        JSFUtils.addToSession("prosequtionFlag", new BigDecimal(0));
         
        JSFUtils.addToSession("judgeText", "00");
        BigDecimal ney = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");

        BigDecimal bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
        JSFUtils.addToPageFlowScope("newKolyeaBC", "false");
        JSFUtils.addToSession("NeyabaaKolyaa", ney);
        JSFUtils.addToPageFlowScope("CJP", "false");
        JSFUtils.storeOnSession("undefinedPenaltyComb", "0");
        JSFUtils.storeOnSession("workAmountPenaltyComb", "0");
        JSFUtils.storeOnSession("PenaltyComb", "0");

         
  

        
         


        BigDecimal returnValue = (BigDecimal) ADFUtil.findOperation("ValidatePoliceReport").execute();
        System.err.println("returnValue " + returnValue);
        //            ViewObject object = ADFUtil.findIterator("BusinessCaseDetailsVO1Iterator").getViewObject() ;
        //            object.executeQuery();
        //            System.out.println(object.getRowCount()+"--------------");
        JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToSession("ClaimDetails", false);
        JSFUtils.addToPageFlowScope("disabledValidate", true);
        
        
            
//            RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it110");
//            RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it109");
//            RichInputText inputPoliceNum = (RichInputText) JSFUtils.findComponentInRoot("it111");
//            if(JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")!=null){
//            BigDecimal pNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}");
//                inputNum.setValue(pNum); 
//            }
//            if(JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")!=null){
//            BigDecimal pPoliceNum = (BigDecimal) JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}");
//                inputPoliceNum.setValue(pPoliceNum); 
//            }
//            if(JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}")!=null){
//                BigDecimal pYear = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}");
//                inputYear.setValue(pYear); 
//            }
//        
//        
        RichButton newRes = (RichButton) JSFUtils.findComponentInRoot("l25");
        System.out.println("****"+JSFUtils.getFromSession("BC_ID"));
        if (newRes != null)
            newRes.setDisabled(false);
       
        RichSelectOneChoice degreeChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        if (degreeChoice != null)
            degreeChoice.setDisabled(false);
        RichSelectOneChoice dateChoice = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc18");
        if (dateChoice != null)
            dateChoice.setDisabled(false);
        
        if (returnValue == null)
            returnValue=new BigDecimal(11);
        if (returnValue.compareTo(new BigDecimal(3)) == 0) {
             
        }
        if (returnValue.compareTo(new BigDecimal(4)) == 0) {
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToPageFlowScope("detail", true);
            JSFUtils.addToPageFlowScope("BCFound", true);
            JSFUtils.addToPageFlowScope("back", "0");
           
        }


        else if (returnValue.compareTo(BigDecimal.ZERO) == 0) {
            
            System.out.println("################");
            
            
            OneParty();
            JSFUtils.addToPageFlowScope("backLog", false);
            JSFUtils.addToPageFlowScope("showDate", "true");
            
            JSFUtils.addToPageFlowScope("BCFound", false);
            JSFUtils.addToPageFlowScope("partyName", true);
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}") ||
                    JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))

            {
                JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
                //                if (bcType.compareTo(new BigDecimal(15))!=0)
              
            }

            //            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
                
            RichPanelGroupLayout pg51 = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl7");
            pg51.setVisible(true);
            
            
            System.out.println(JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}")+"*****"+JSFUtils.resolveExpression("#{sessionScope.userInfo.entityDegree}"));
            if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.entityDegree eq '39'}") ||
                JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser ne '0'}"))

            {
//            if (JSFUtils.resolveExpressionAsBoolean("#{bindings.Id9.inputValue == null}")) {
//                if (JSFUtils.resolveExpression("#{bindings.CheckFlag.inputValue}") != null) {
//                    JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
//                    //                        if (bcType.compareTo(new BigDecimal(15))!=0)sbc21
//                    insertHasrKolyaa();
//                }
//            }
                 RichSelectBooleanCheckbox sbc21 = (RichSelectBooleanCheckbox) JSFUtils.findComponentInRoot("sbc21");
                sbc21.setValue(true);
                JSFUtils.setExpressionValue("#{bindings.CheckFlag.inputValue}", true);
                insertHasrKolyaa();
            }
           

        } else if (returnValue.compareTo(BigDecimal.ONE) == 0) {
            
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "CLAIMBC");
            
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
               

                JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
                //                 RichSelectOneChoice ch = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
                
            } else {
                //            JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", new BigDecimal (1));

                
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
                      
                    }
                }
            }

        }
        if (returnValue.compareTo(new BigDecimal(11)) != 0) {
           // JSFUtils.showFacesInfoMessageFromBundle("bundle", "CLAIMBC");
       

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
    
    
    public String OneParty() {
        // Add event code here...
        String decision = "0";
        if (ADFContext.getCurrent()
                      .getPageFlowScope()
                      .get("decision") != null)
            
//            decision = (String) ADFContext.getCurrent()
//                                          .getPageFlowScope()
//                                          .get("decision");
        System.out.println("decision  is "+decision);
        
        
//        RichTable t1 = (RichTable) JSFUtils.findComponentInRoot("t1");
//                       t1.setSelected(true);
        
        
        if (decision.compareTo("0") == 0) {

         

            System.out.println("One Party");
            JSFUtils.addToPageFlowScope("BCFound", false);
          //  JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1));
        //    JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "false");
           
            JSFUtils.addToPageFlowScope("decision", "1");

            JSFUtils.setExpressionValue("#{pageFlowScope.judgText}", null);
            //        RichButton lov = (RichButton) JSFUtils.findComponentInRoot("b19");
            //        lov.setSelected(true);
            //        lov.setDisabled(true);


            ADFUtil.findOperation("addNewconvicted_1_1").execute();
//            JSFUtils.setExpressionValue("#{bindings.OrderName.inputValue}",
//                                        "\u0627\u0644\u0645\u062A\u0647\u0645 \u0627\u0644\u0623\u0648\u0644  ");
            //            ArabicLiteralNumberParser.literalValueOf(order);

            //        ADFUtil.findOperation("CreateInsertFolderData").execute();
         
            JSFUtils.refreshComponent("pg29");
            JSFUtils.refreshComponent("pg48");
            JSFUtils.refreshComponent("ph5");
            JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}",  JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
        }
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}")) {
          //  JSFUtils.setExpressionValue("#{bindings.ManualSeq1.inputValue}", "Y");
            System.out.println("ManualSeq-- 555 ----Y  " +
                               JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}"));
            }
        return null;
    }  
    
    private java.util.Date date = new java.util.Date();  
    
    public java.util.Date getDate() {
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(date);
           calendar.add(Calendar.DATE, 0);
           Date yesterday = calendar.getTime();

           System.out.println(yesterday);
           System.out.println(date);
        return yesterday;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate1() {
        return date;
    }

    public void enter(ClientEvent clientEvent) {
        // Add event code here...RichButton#{bindings.SchemaId.inputValue ne '3643'}
        BigDecimal schemaId = new BigDecimal(3643);
        if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
            schemaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
        System.err.println("#####00000000#####" + JSFUtils.resolveExpression("#{bindings.Id12.inputValue}"));
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov1");

        if (lov.getValue() != null) {
           
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
    public String cancel() {
        ADFUtil.findOperation("Rollback").execute();
        JSFUtils.addToPageFlowScope("disabledValidate", false);
        return null;
    }
    
    
    public String save() {
        // Add event code here...
        Boolean check = true;
        JSFUtils.addToSession("automationFlag", false);
            check = (Boolean) ADFUtil.findOperation("validateCourtBCHearingDate").execute();
            System.err.println("CloseEEE  " + check);
        
        if (check) {
                if(validateIDNumber()){
            if (    ValidatePenaltyParty()){
                if (validateSequenceNumber()){
                   
                    JSFUtils.addToSession("ClaimDetails", true);
            System.out.println("&&&&&&&&.......&&&&");
                    JSFUtils.setExpressionValue("#{bindings.UnspecializedFlag.inputValue}", new BigDecimal (0));
                    RichInputText it131 = (RichInputText) JSFUtils.findComponentInRoot("it131");
                    it131.setValue(new BigDecimal (0));
            ADFUtil.findOperation("Commit").execute();
            JSFUtils.addToPageFlowScope("disabledValidate", false);
            ADFUtil.findOperation("Rollback").execute();
            RichPanelGroupLayout pg51 = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl7");
            pg51.setVisible(false);
                    JSFUtils.addToSession("ClaimDetails", false);
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVED_MESSAGE");
                }
                }
            }
            else {
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "PENALTY_MESSAGE");
                    }
        }
        return null;
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
             bussinessCaseType.compareTo(new BigDecimal(15)) == 0) ) {
            check = false;
            System.err.println("111111111111111111111111111");
        }

//        if (JSFUtils.resolveExpression("#{bindings.MeasureEndDate1.inputValue}") != null || !JSFUtils.resolveExpression("#{bindings.MeasureType1.inputValue}")
//                                                                                                     .toString()
//                                                                                                     .isEmpty()) {
//            check = true;
//            System.err.println(JSFUtils.resolveExpression("#{bindings.MeasureEndDate1.inputValue}") +
//                               "22222222222222222222222222" + JSFUtils.resolveExpression("#{bindings.MeasureType1.inputValue}")
//                                                                      .toString()
//                                                                      .isEmpty());
//        }

        System.out.println("years " + years + " days " + days + " months " + months + " guranteeAmount " +
                           guranteeAmount + guranteeAmount + " workAmount " + workAmount + " penaltyAmount " +
                           penaltyAmount + " unDefineedPenalty " + unDefineedPenalty+"  stopExecutionPrison"+stopExecutionPrison);
        if (
            folderType.compareTo(new BigDecimal(50)) == 0 || folderType.compareTo(new BigDecimal(51)) == 0 ||
        folderType.compareTo(new BigDecimal(2)) == 0 || folderType.compareTo(new BigDecimal(1)) == 0 ||
            folderType.compareTo(new BigDecimal(5)) == 0   || folderType.compareTo(new BigDecimal(21)) == 0
                  || folderType.compareTo(new BigDecimal(20)) == 0   || folderType.compareTo(new BigDecimal(22)) == 0
            ) {


            if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
                months.compareTo(BigDecimal.ZERO) == 1 || (guranteeAmount).intValue() > 0 || foreverFlag ||
                executionFlag) {
                check = true;
                if (stopExecutionPrison) {
                 
                 //   ViewObject vo = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO1Iterator").getViewObject();
                    
                    if ((workAmount).intValue() > 0 || (penaltyAmount).intValue() > 0 ||
                        unDefineedPenalty.intValue() > 0 //||  vo.getRowCount() > 0
                    ) {
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
//        ViewObject vo = ADFUtil.findIterator("ExcCourtBcDecisionFeesVO1Iterator").getViewObject();
//        if ((foldType.compareTo(new BigDecimal(3)) == 0 || foldType.compareTo(new BigDecimal(53)) == 0 || foldType.compareTo(new BigDecimal(6)) == 0) &&
//            vo.getRowCount() > 0) {
//            System.out.println("555555555555");
//            check = true;
//        }
        
        
        if (folderType.compareTo(new BigDecimal (12))==0){
            check = true;
        }

        //        if (bussinessCaseType.compareTo(new BigDecimal (5))==0 ||bussinessCaseType.compareTo(new BigDecimal (15))==0){
        //            check = true; }
        if (isFolderType()) {
           check=true;
       }
        System.out.println(" Validate Penalty Check  :---:0000:  " + check);
        return check;
    }

    private boolean isFolderType() {
        System.out.println("isFolderType");
        Object folderType = JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
        if (null != folderType) {
            int folderTypeVar = Integer.parseInt(folderType.toString());
            if ((13 == folderTypeVar ||10 == folderTypeVar || 29 == folderTypeVar || 16 == folderTypeVar || 15 == folderTypeVar || 7 == folderTypeVar || 9 == folderTypeVar || 14 == folderTypeVar || 17 == folderTypeVar || 18 == folderTypeVar ||
                  19 == folderTypeVar)
            ||
                (36 == folderTypeVar ||34 == folderTypeVar || 9 == folderTypeVar || 23 == folderTypeVar || 31 == folderTypeVar || 39 == folderTypeVar || 40 == folderTypeVar || 41 == folderTypeVar || 43 == folderTypeVar || 26 == folderTypeVar ||
                                  23 == folderTypeVar || 45 == folderTypeVar || 46 == folderTypeVar || 47 == folderTypeVar || 48 == folderTypeVar  || 49 == folderTypeVar || 32 == folderTypeVar || 33 == folderTypeVar )
            
            
            ) {
                return true;
            }
        }
        return false;
    }
    
    public void inTimeVcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        JSFUtils.setExpressionValue("#{bindings.CrimeDate.inputValue}", "");


    }
    public void actionTypes() {
        //        if (JSFUtils.getFromSession("GnayaatFlag") != null)
        {
            System.out.println("%%%%%%%%%%" + ADFContext.getCurrent()
                                                        .getSessionScope()
                                                        .get("actionTypeId"));
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
    public void degreeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here.....BcDegreeGonaha
        try {
            BigDecimal type = new BigDecimal(1);
            if(valueChangeEvent.getNewValue() != null){
              type = (BigDecimal) valueChangeEvent.getNewValue();
            
        //    JSFUtils.setExpressionValue("#{row.bindings.BcDegreeSpecId.inputValue}",type);
                RichInputText it129 = (RichInputText) JSFUtils.findComponentInRoot("it129");
                it129.setValue(type);
            
            }
            
//            {
//                
//                    OperationBinding myOperation = ADFUtil.findOperation("gethearingDates");
//                    myOperation.getParamsMap().put("degree", type);
//                    if(JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") !=null)
//                {
//                    System.err.println("Bayoumi >>> BussinesId>"+JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}"));
//                    myOperation.getParamsMap()
//                        .put("bcID", JSFUtils.resolveExpression("#{bindings.BussinesId.inputValue}") + "");
//                } else
//                {
//                        System.err.println("Bayoumi >>> BC_ID>"+JSFUtils.getFromSession("BC_ID"));
//
//                    myOperation.getParamsMap().put("bcID", JSFUtils.getFromSession("BC_ID")+"");
//                        
//                    }
//
//                  Map<BigDecimal, String> res = (Map<BigDecimal, String>) myOperation.execute();
//                    newSelectItemList = new ArrayList<SelectItem>();
//                    dateList = new HashMap<String, String>();
//                    if (res.size() > 0)
//                        for (Map.Entry m : res.entrySet()) {
//                            BigDecimal key = (BigDecimal) m.getKey();
//                        dateList.put(key+"", m.getValue()+"");
//                    newSelectItemList.add(new SelectItem(key, m.getValue()+""));
//                    }
//                  
//                  this.setSelectItemList(newSelectItemList);
//                
//                }
     
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
    
    
    public void guaranteeFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean newValue = (Boolean) valueChangeEvent.getNewValue();
        System.out.println("newValue   " + newValue);
        if (!newValue)
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));

    }


    public String addClaim() {
        // Add event code here...ClaimSequenceNumber
        
        ADFUtil.findOperation("CreateInsert").execute();
        JSFUtils.setExpressionValue("#{bindings.ClaimTypeId.inputValue}", new BigDecimal(JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}") +""));
        RichInputText it128 = (RichInputText) JSFUtils.findComponentInRoot("it128");
        it128.setValue(""+JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}"));
        
        System.out.println("paarty id"+JSFUtils.resolveExpression("#{bindings.Id4.inputValue}"));
        RichInputText it110 = (RichInputText) JSFUtils.findComponentInRoot("it110");
        it110.setValue(""+JSFUtils.resolveExpression("#{bindings.Id4.inputValue}"));
        
        RichInputText it117 = (RichInputText) JSFUtils.findComponentInRoot("it117");
        it117.setValue( new BigDecimal(ADFContext.getCurrent()  .getSessionScope() .get("BC_ID")+""));
        
        
        JSFUtils.setExpressionValue("#{bindings.PartyName.inputValue}",  JSFUtils.resolveExpression("#{bindings.PartyFullName.inputValue}") );
        JSFUtils.setExpressionValue("#{bindings.IdNumber1.inputValue}",  JSFUtils.resolveExpression("#{bindings.IdNumber.inputValue}") );
        JSFUtils.setExpressionValue("#{bindings.PartyId.inputValue}",  JSFUtils.resolveExpression("#{bindings.Id4.inputValue}") );
        JSFUtils.setExpressionValue("#{bindings.ClaimSequenceNumber.inputValue}",   JSFUtils.resolveExpression("#{bindings.Year.inputValue}")+"/"+
        JSFUtils.resolveExpression("#{bindings.sequenceFold.inputValue}")+"/"+
                                    JSFUtils.resolveExpression("#{bindings.OriginName.inputValue}") );
      int BusinessCaseType = Integer.parseInt( JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}")+"") ;
        String BusinessCaseTypetext = "جنح";
        if(BusinessCaseType == 5)
            BusinessCaseTypetext = "جنح طفل";
        else if(BusinessCaseType == 7)
            BusinessCaseTypetext = "جنح مباني";
        else if(BusinessCaseType == 10)
            BusinessCaseTypetext = "جنح أمن دولة طوارئ";
        else if(BusinessCaseType == 1)
            BusinessCaseTypetext = "مخالفات";
        else if(BusinessCaseType == 4)
            BusinessCaseTypetext = "جنح إقتصادية";
        else if(BusinessCaseType == 11)
            BusinessCaseTypetext = "جنح مرور";
        else if(BusinessCaseType == 12)
            BusinessCaseTypetext = "جنح صحفيين";
        else if(BusinessCaseType == 16)
            BusinessCaseTypetext = "جنايات طفل";
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseNumberFull.inputValue}",  JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}")+"/"+ JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")+
                        "/"+BusinessCaseTypetext+"/"+             JSFUtils.resolveExpression("#{bindings.OriginName.inputValue}") );
        System.out.println("**********"+ JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        BigDecimal  scID = new BigDecimal (0);
        if(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null )
        scID = (BigDecimal)JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
        if(scID.compareTo(new BigDecimal (0))==1)
            JSFUtils.setExpressionValue("#{bindings.ClaimPoliceStationId.inputValue}",  JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") );
            
            else
        JSFUtils.setExpressionValue("#{bindings.ClaimPoliceStationId.inputValue}",  JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseId.inputValue}", new BigDecimal(ADFContext.getCurrent()  .getSessionScope() .get("BC_ID")+""));
        
        JSFUtils.setExpressionValue("#{bindings.PartyId.inputValue}",  JSFUtils.resolveExpression("#{bindings.Id4.inputValue}") );
        
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

    public void nameVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String fullName = (String) valueChangeEvent.getNewValue();
        setName(fullName);
    }
    // Make the disclosed row the current
    private void makeDisclosedRowCurrent(RichTable table, RowKeySet keySet) {
        table.setSelectedRowKeys(keySet);
        CollectionModel tableModel = (CollectionModel) table.getValue();
        JUCtrlHierBinding tableHierBinding = null;
        tableHierBinding = (JUCtrlHierBinding) (tableModel).getWrappedData();
        DCIteratorBinding dCIteratorBindin = null;
        dCIteratorBindin = tableHierBinding.getDCIteratorBinding();
        Iterator keySetIter = keySet.iterator();
        List firstKey = (List) keySetIter.next();
        oracle.jbo.Key key = (oracle.jbo.Key) firstKey.get(0);
        dCIteratorBindin.setCurrentRowWithKey(key.toStringFormat(true));
    }
    
    //Disclusore Listener for table row
    public void rowDisclosureListenerContractTable(RowDisclosureEvent rowDisclosureEvent) {
        // make row current //
        //restMerge();
        RichTable table = (RichTable) rowDisclosureEvent.getSource();
        RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
        RowKeySet lastAddedRowKeySet = rowDisclosureEvent.getAddedSet();
        Iterator lastAddedRowKeySetIter = lastAddedRowKeySet.iterator();
        if (lastAddedRowKeySetIter.hasNext()) {
            discloseRowKeySet.clear();
            Object lastRowKey = lastAddedRowKeySetIter.next();
            discloseRowKeySet.add(lastRowKey);
            makeDisclosedRowCurrent(table, lastAddedRowKeySet);
            AdfFacesContext adfFacesContext = null;
            adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(table.getParent());
        }
        RichSelectOneChoice soc11 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
        soc11.setDisabled(true);
        // / Make row current ////
 

    }
    public void LedgerVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(3));
        System.err.println(valueChangeEvent.getNewValue()+"&&&" + JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        RichInputText it107 = (RichInputText) JSFUtils.findComponentInRoot("it126");
        it107.setValue(""+JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        if (valueChangeEvent != null) {
            if (JSFUtils.resolveExpression("#{bindings.FolderTypeCrime.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", new BigDecimal(1));
            //        JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(3));
           BigDecimal scID = new BigDecimal (0);
            if(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null )
            scID = (BigDecimal)JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
            if(scID.compareTo(new BigDecimal (0))==1)
            {
                if (JSFUtils.resolveExpression("#{bindings.ClaimPoliceStationId.inputValue}") != null)
                JSFUtils.setExpressionValue("#{bindings.ClaimPoliceStationId.inputValue}",  JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") );
                
            }  else{
                if (JSFUtils.resolveExpression("#{bindings.ClaimPoliceStationId.inputValue}") != null)
            JSFUtils.setExpressionValue("#{bindings.ClaimPoliceStationId.inputValue}",  JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") );
            
            }
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
    

    public String newParty() {
        // Add event code here...
        OneParty();
        return null;
    }
    public void seqFoldVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("valueChangeEvent.getNewValue()  Seq Fold is"+valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null){
        JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", new BigDecimal(valueChangeEvent.getNewValue().toString()));
            RichInputText it8 = (RichInputText) JSFUtils.findComponentInRoot("it8");
            it8.setValue( new BigDecimal(valueChangeEvent.getNewValue().toString()));
        
        }
    }

    public void ClaimAmount(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("FFFFFFFFFFF"+valueChangeEvent.getNewValue());
        if(valueChangeEvent.getNewValue() != null){
        JSFUtils.setExpressionValue("#{bindings.FixedPenaltyAmount.inputValue}", new BigDecimal(valueChangeEvent.getNewValue().toString()));
            RichInputText it112 = (RichInputText) JSFUtils.findComponentInRoot("it112");
            it112.setValue( new BigDecimal(valueChangeEvent.getNewValue().toString()));
        }  
    }

    public void cancelClaim(ActionEvent actionEvent) {
        // Add event code here...
        RichPanelGroupLayout pg51 = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl7");
        pg51.setVisible(false);
    }

    public void DailyVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("Daily VCL"+valueChangeEvent.getNewValue());
        System.out.println("work amount"+JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}"));
        System.out.println("work PenaltyDueDate"+JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}"));
        if(valueChangeEvent.getNewValue().equals(true))
        {
            if(JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}")!= null && JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}")!= null ){
                Date    date1  = new Date();
                String    date  = "";
                  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                  try {
                   // date1 =  sdf.parse(JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}")+"");
                      date =  sdf.format(JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}"));
                     
                      RichInputText it240 = (RichInputText) JSFUtils.findComponentInRoot("it240");
                      it240.setValue( sdf.format(JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}")));  
                  
                      RichInputText it241 = (RichInputText) JSFUtils.findComponentInRoot("it241");
                      it241.setValue(sdf.format(JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}")) );
                      JSFUtils.setExpressionValue("#{bindings.PenaltyDueDate.inputValue}", sdf.format(JSFUtils.resolveExpression("#{bindings.PenaltyDueDate1.inputValue}")));
                  } catch (Exception e) {
                      e.printStackTrace();
                      
                  }
                
                 
          
                 
                 RichInputText it239 = (RichInputText) JSFUtils.findComponentInRoot("it239");
                 it239.setValue( new BigDecimal(JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}")+""));
                 
             
                 
             
                 
                RichSelectBooleanCheckbox sbc40 = (RichSelectBooleanCheckbox) JSFUtils.findComponentInRoot("sbc40");
                sbc40.setValue(true);
                sbc40.setSelected(true);
                JSFUtils.setExpressionValue("#{bindings.DailyPenaltyFlag.inputValue}", true);
                JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}", "Y");
            }
            else
            {
                    JSFUtils.setExpressionValue("#{bindings.DailyPenaltyFlag.inputValue}", false);
                    
                    JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}", "N");
                    RichSelectBooleanCheckbox sbc40 = (RichSelectBooleanCheckbox) JSFUtils.findComponentInRoot("sbc40");
                    sbc40.setSelected(false);
                    JSFUtils.showFacesInfoMessageFromBundle("bundle", "CLAIMWORKV");
                }  
             }
    }
}
