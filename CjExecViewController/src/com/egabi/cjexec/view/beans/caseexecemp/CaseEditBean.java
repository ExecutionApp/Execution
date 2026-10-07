package com.egabi.cjexec.view.beans.caseexecemp;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.ItemEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class CaseEditBean {
    private RichPanelBox detentionPanelBox;
    private RichPopup changeSequenceNumberPopup;

    public CaseEditBean() {
    }

    public String createChargeForAll() {
        try {
            commit();
            rollback();
            ADFUtil.setBoundAttributeValue("JudgementType", BigDecimal.ONE);
            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_FOR_ALL");
            ADFUtil.executrQueryIterator("CaseEditMasterROVIterator");
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }

    public String createChargeForEach() {
        try {
            commit();
            rollback();
            ADFUtil.setBoundAttributeValue("JudgementType", BigDecimal.ONE.add(BigDecimal.ONE));
            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_EACH_CONVICTION");
         // yasser mohamed 
            // charge for one
//            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARG_FOR_ONE");
         // charges for one
//          ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_FOR_ONE");
              
            
            //
         
            ADFUtil.executrQueryIterator("CaseEditMasterROVIterator");
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }
    
    
    public String createChargeSForONE() {
        try {
            commit();
            rollback();
            ADFUtil.setBoundAttributeValue("CopyFlg", BigDecimal.ONE);
//            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_EACH_CONVICTION");
         // yasser mohamed 
            // charge for one
    //            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARG_FOR_ONE");
         // charges for one
              ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_FOR_ONE");
              
            System.err.println("oneoneome");
            //
         
            ADFUtil.executrQueryIterator("CaseEditMasterROVIterator"); 
             ADFUtil.executrQueryIterator("CasePartiesConvictionsROVIterator");
            rollback();
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }
    
    
    
    
    public String createChargeForEachONE() {
        try {
            commit();
            rollback();
            ADFUtil.setBoundAttributeValue("CopyFlg", BigDecimal.ZERO );
//            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_EACH_CONVICTION");
         // yasser mohamed 
            // charge for one
                ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARG_FOR_ONE");
         // charges for one
    //          ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___CREATE_CHARGE_FOR_ONE");
              
    System.err.println("000000000000000000");
            //
         
            ADFUtil.executrQueryIterator("CaseEditMasterROVIterator");

            ADFUtil.executrQueryIterator("CasePartiesConvictionsROVIterator");
            rollback();
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }
    

    public Object getImprisonmentDays() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("ImprisonmentDays"));
    }

    public void setImprisonmentDays(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("ImprisonmentDays"), value);
    }

    public Object getImprisonmentMonths() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("ImprisonmentMonths"));
    }

    public void setImprisonmentMonths(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("ImprisonmentMonths"), value);
    }

    public Object getImprisonmentYears() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("ImprisonmentYears"));
    }

    public void setImprisonmentYears(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("ImprisonmentYears"), value);
    }


    public Object getActionTypeId() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("ActionTypeId"));
    }

    public void setActionTypeId(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("ActionTypeId"), value);
    }

    public Object getStopExecutionFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("StopExecutionFlag"));
    }

    public void setStopExecutionFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("StopExecutionFlag"), value);
    }

    public Object getGuaranteeFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("GuaranteeFlag"));
    }

    public void setGuaranteeFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("GuaranteeFlag"), value);
    }


    public Object getGuranteeAmount() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("GuranteeAmount"));
    }

    public void setGuranteeAmount(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("GuranteeAmount"), value);
    }

    public Object getPenaltyAmount() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("PenaltyAmount"));
    }

    public void setPenaltyAmount(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("PenaltyAmount"), value);
    }


    public Object getPenaltyAmountFixedFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("PenaltyAmountFixedFlag"));
    }

    public void setPenaltyAmountFixedFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("PenaltyAmountFixedFlag"), value);
    }


    public Object getWorkAmountFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmountFlag"));
    }

    public void setWorkAmountFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmountFlag"), value);
    }


    public Object getWorkAmount() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmount"));
    }

    public void setWorkAmount(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmount"), value);
    }


    public Object getWorkAmountMultiplierFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmountMultiplierFlag"));
    }

    public void setWorkAmountMultiplierFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmountMultiplierFlag"), value);
    }


    public Object getWorkAmountMultiplier() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmountMultiplier"));
    }

    public void setWorkAmountMultiplier(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmountMultiplier"), value);
    }


    public Object getWorkAmountPercentFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmountPercentFlag"));
    }

    public void setWorkAmountPercentFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmountPercentFlag"), value);
    }


    public Object getWorkAmountPercent() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("WorkAmountPercent"));
    }

    public void setWorkAmountPercent(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("WorkAmountPercent"), value);
    }


    public Object getChargeAnnouncmentDate() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("ChargeAnnouncmentDate"));
    }

    public void setChargeAnnouncmentDate(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("ChargeAnnouncmentDate"), value);
    }


    public Object getPenaltySharedFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("PenaltySharedFlag"));
    }

    public void setPenaltySharedFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("PenaltySharedFlag"), value);
    }

    public Object getDependentOrderFlag() {
        return ADFUtil.getBoundAttributeValue(getAttributeName("DependentOrderFlag"));
    }

    public void setDependentOrderFlag(Object value) {
        ADFUtil.setBoundAttributeValue(getAttributeName("DependentOrderFlag"), value);
    }


    public boolean isDetention() {
        return "3".equals(getActionTypeId() + "") || "1".equals(getActionTypeId() + "");
    }

    public boolean isFine() {
        return "3".equals(getActionTypeId() + "") || "2".equals(getActionTypeId() + "");
    }


    private String getAttributeName(String string) {
        Object judgementType = ADFUtil.getBoundAttributeValue("JudgementType");
        Object CopyFlg = ADFUtil.getBoundAttributeValue("CopyFlg");
        return ("1".equals((judgementType + ""))) ? string : string + "Par";
    }

    public void dependantOrderFlagVCL(ValueChangeEvent valueChangeEvent) {
        ADFUtil.deleteAllIteratorRows("ExcCourtBcChrgDecDepOrdViewIterator");
        ADFUtil.deleteAllIteratorRows("PartiesExcCourtBcChrgDecDepOrdViewIterator");
        ADFUtil.deleteAllIteratorRows("PartiesExcCourtBcChrgDecPenPartViewIterator");
        ADFUtil.deleteAllIteratorRows("ExcCourtBcChrgDecPenPartViewIterator");
    }


    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String addPartie() {
        Object hearingResId = JSFUtils.resolveExpression("#{pageFlowScope.HearingResId}");
        Object DecesionId = JSFUtils.resolveExpression("#{bindings.Id1.inputValue}");
        
        ViewObject vo = ADFUtil.findIterator("PartiesExcCourtBcChrgDecPenPartViewIterator").getViewObject();
        Row row = vo.createRow();
        row.setAttribute("ChargeDecisionId", DecesionId);
        row.setAttribute("HearingResId", hearingResId);
        row.setAttribute("PartyId", BigDecimal.ZERO.subtract(BigDecimal.ONE));
        vo.insertRow(row);
        return null;
    }


    public String addPartieAll() {
        Object hearingResId = JSFUtils.resolveExpression("#{pageFlowScope.HearingResId}");
        Object DecesionId = JSFUtils.resolveExpression("#{bindings.Id1.inputValue}");
        
        System.out.println("++++++" + hearingResId);
        ViewObject vo = ADFUtil.findIterator("ExcCourtBcChrgDecPenPartViewIterator").getViewObject();
        Row row = vo.createRow();
        row.setAttribute("ChargeDecisionId", DecesionId);
        row.setAttribute("HearingResId", hearingResId);
        row.setAttribute("PartyId", BigDecimal.ZERO.subtract(BigDecimal.ONE));
        System.out.println("-------------" + row.getAttribute("HearingResId"));
        vo.insertRow(row);
        return null;
    }

    public String createCountingFolder() {

//        try {
//            commit();
//            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___VALIDATE_CHARGE_DECISIONS");
//            commit();
//            ADFUtil.executeVoidOperation("ExecuteValidationErrors");
//            DCIteratorBinding validateChargeDecisionsErrROVIterator =
//                ADFUtil.findIterator("ValidateChargeDecisionsErrROVIterator");
//            if (validateChargeDecisionsErrROVIterator.getEstimatedRowCount() > 0) {
//                JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALIDATION_ERRORS_IN_DECESION_CHARGE");
//                return "";
//            }
//        } catch (Exception e) {
//            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
//            e.printStackTrace();
//            return "";
//        }


        try {
            ADFUtil.executeVoidOperation("callCREATE_COUNTING_FOLDER");
          
            
            commit();
            ADFUtil.executeVoidOperation("ExecuteFolderData");
            ADFUtil.executeVoidOperation("ExecutePartyDebt");

            JSFUtils.showFacesInfoMessageFromBundle("bundle", "COUNT_SUCCESS");

        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }

    private void rollback() throws Exception {
        ADFUtil.executeVoidOperation("Rollback");
    }

    private void commit() throws Exception {
        ADFUtil.executeVoidOperation("Commit");
    }

    public String setClassified() {
//        try {
//            commit();
//            ADFUtil.executeVoidOperation("callEXC_COURT_BC_PKG___VALIDATE_CHARGE_DECISIONS");
//            commit();
//            ADFUtil.executeVoidOperation("ExecuteValidationErrors");
//            DCIteratorBinding validateChargeDecisionsErrROVIterator =
//                ADFUtil.findIterator("ValidateChargeDecisionsErrROVIterator");
//            if (validateChargeDecisionsErrROVIterator.getEstimatedRowCount() > 0) {
//                JSFUtils.showFacesErrorMessageFromBundle("bundle", "VALIDATION_ERRORS_IN_DECESION_CHARGE");
//                return "";
//            }
//        } catch (Exception e) {
//            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
//            e.printStackTrace();
//            return "";
//        }
        try {
            ADFUtil.setBoundAttributeValue("ClassifiedFlag", BigDecimal.ONE);
            ADFUtil.executeVoidOperation("updateSessionFlag");
            commit();
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "CLASSIFIED_SUCCES");
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }


    public boolean isReadonly() {
        if (isViewOnlyMode()) {
            return true;
        }
        Object classifiedFlag = ADFUtil.getBoundAttributeValue("ClassifiedFlag");
System.out.println("Yasser ::   >>>>> classifiedFlag   Test  >> "+classifiedFlag);
        return "1".equals(classifiedFlag + "");
    }

    public String save() {
        // Add event code here...
        try {
            commit();
            JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
        }
        return null;
    }

    private boolean isViewOnlyMode() {
        Object viewOnlyMode = JSFUtils.resolveExpression("#{pageFlowScope.viewOnlyMode}");
        return viewOnlyMode != null && "true".equals(viewOnlyMode + "");
    }


    public void setChangeSequenceNumberPopup(RichPopup changeSequenceNumberPopup) {
        this.changeSequenceNumberPopup = changeSequenceNumberPopup;
    }

    public RichPopup getChangeSequenceNumberPopup() {
        return changeSequenceNumberPopup;
    }

    public void changeSequenceDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            try {
                String result =
                    (String) ADFUtil.executeOperation("callEXC_COURT_BC_PKG___CHANGE_SEQUENCE_NUMBER", null);

                if ("UPDATED_WITH_WARN".equals(result)) {
                    JSFUtils.showFacesWarningMessageFromBundle("bundle", "UPDATED_WITH_WARN");
                } else if ("NOT_UPDATED".equals(result)) {
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "NOT_UPDATED");
                }
                commit();
                ADFUtil.executeOperation("ExecuteFolderData", null);
            } catch (Exception e) {
                e.printStackTrace();
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
            }
        }
    }
    
    
    public void changeEditconvictedDialogListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            try {
               
                
                //updateParPartiesLog
                OperationBinding binding = ADFUtil.findOperation("updateParPartiesLog");
                binding.getParamsMap().put("parPartyId", this.getPartyID());
                binding.execute();
                commit();
                ADFUtil.executrQueryIterator("CaseConvictedPartiesROVIterator");
            } catch (Exception e) {
                e.printStackTrace();
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
            }
        }
        else 
        {
                    try {
                       
                        rollback();
                        ADFUtil.executrQueryIterator("CaseConvictedPartiesROVIterator");
                    } catch (Exception e) {
                        e.printStackTrace();
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "UNEXPECTED_ERROR_ON_CHARGE_GENERATE");
                    }
                }
        
    }
    

    public void SelectItem(ItemEvent itemEvent) {
        // Add event code here...
        System.out.println("****" + itemEvent.getSource());
    }
    BigDecimal partyID;

    public void discolse(DisclosureEvent disclosureEvent) {
        // Add event code here...

        if (disclosureEvent.isExpanded() == true) {
            String currentDepartmentId = disclosureEvent.getComponent().getAttributes().get("PartyID").toString();
            String CourtCircleDateId =
                disclosureEvent.getComponent().getAttributes().get("CourtCircleDateId").toString();
            JSFUtils.setExpressionValue("#{bindings.PartyId.inputValue}", currentDepartmentId);
            System.out.println("Disclose " + currentDepartmentId);
            BigDecimal bigDecimalCurrency = new BigDecimal(currentDepartmentId);
            BigDecimal CourtCircleDate = new BigDecimal(CourtCircleDateId);
            this.setPartyID(bigDecimalCurrency);

            ViewObject vo = ADFUtil.findIterator("CasePartiesConvictionsROVIterator").getViewObject();
            System.out.println("vo " + vo.getRowCount());
            ViewCriteria createViewCriteria = vo.createViewCriteria();
            ViewCriteriaRow createViewCriteriaRow = createViewCriteria.createViewCriteriaRow();
            createViewCriteriaRow.setAttribute("PartyId", bigDecimalCurrency);
            createViewCriteriaRow.setAttribute("CourtCircleDateId", CourtCircleDate);
            createViewCriteria.insertRow(createViewCriteriaRow);
            vo.applyViewCriteria(createViewCriteria);
            vo.executeQuery();
            System.out.println("vo " + vo.getRowCount());
        }


        //
        //                if(disclosureEvent.isExpanded()){
        //                     DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //
        //                     OperationBinding operBinding = bindings.getOperationBinding("RefreshEmployeesForDept");
        //
        //                     if(operBinding != null){
        //                          operBinding.getParamsMap().put("p_DeptId", currentDepartmentId);
        //                          operBinding.execute();
        //                     }

    }

    public void setPartyID(BigDecimal partyID) {
        this.partyID = partyID;
    }

    public BigDecimal getPartyID() {
        return partyID;
    }

    public String EditParty() {
        // Add event code here...
        JSFUtils.showPopup("p3");
        return null;
    }

    public void penVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("valueChangeEvent "+valueChangeEvent.getNewValue());
        JSFUtils.refreshComponent("s6");
    }

    public String EditImageParty() {
        // Add event code here...
        JSFUtils.showPopup("p4");
        return null;
    }

    public String Add1DepOrderers() {
        // Add event code here...
        Object DecesionId = JSFUtils.resolveExpression("#{bindings.Id1.inputValue}");
        System.err.println("ChargeDecisionId  "+DecesionId);
        ViewObject vo = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdViewIterator").getViewObject();
        Row row = vo.createRow();
        row.setAttribute("ChargeDecisionId", DecesionId);
        //        row.setAttribute("HearingResId", hearingResId);
                row.setAttribute("DependentOrderType", BigDecimal.ZERO.subtract(BigDecimal.ONE));
        vo.insertRow(row);
//JSFUtils.setExpressionValue("#{bindings.ChargeDecisionId.inputValue}", DecesionId);
//        row.setAttribute("ChargeDecisionId", DecesionId);
        return null;
    }

    public String AddDepOrders() {
        // Add event code here...
        Object DecesionId = JSFUtils.resolveExpression("#{bindings.Id1.inputValue}");
        System.err.println("ChargeDecisionId  "+DecesionId);

//        JSFUtils.setExpressionValue("#{row.ChargeDecisionId.inputValue}", DecesionId);
        ViewObject vo = ADFUtil.findIterator("PartiesExcCourtBcChrgDecDepOrdViewIterator").getViewObject();
        Row row = vo.createRow();
        row.setAttribute("ChargeDecisionId", DecesionId);
//        row.setAttribute("HearingResId", hearingResId);
        row.setAttribute("DependentOrderType", BigDecimal.ZERO.subtract(BigDecimal.ONE));
        vo.insertRow(row);
        return null;
    }
}
