package com.egabi.cjexec.view.beans.cjexec.installmentrequest;

//import com.egabi.cjexec.view.beans.parlawyer.ParLawyerBean;
import com.egabi.cjexec.view.beans.cjexec.enableopposition.CallProcedure;
import com.egabi.cjexec.view.beans.cjexec.enableopposition.UcmAttahcmentInfo;
import com.egabi.cjexec.view.beans.parlawyer.ParLawyerBean;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import net.sf.jasperreports.engine.JRException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPanelWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.util.ComponentReference;

public class InstallmentRequestBean extends PrintEnabledBean {

    // Lawyer List
    private boolean validLawyer;
    // Lawyer Info Panel Box
    private boolean lawyerInfo;
    private RichSelectBooleanCheckbox checkBoxStatus;
    private ComponentReference popUp;
    public String decisionPath;

    private boolean fileUpload;

    public void setFileUpload(boolean fileUpload) {
        this.fileUpload = fileUpload;
    }

    public boolean isFileUpload() {
        return fileUpload;
    }

    public void setPartyId(String partyId) {
        this.partyId = ADFUtil.evaluateEL("#{bindings.PartyId.inputValue}") + "";
    }

    public void setParId(String parId) {
        this.parId = ADFUtil.evaluateEL("#{bindings.ParId.inputValue}") + "";
    }

    public void setBusinessCaseId(String businessCaseId) {
        this.businessCaseId = ADFUtil.evaluateEL("#{bindings.BusinessCaseId.inputValue}") + "";
    }

    public String partyId;
    public String parId;

    public String refreshLawyers() {
        System.out.println(">>>>>>>>>>>>>>      Inside Refresh Lawyers  <<<<<<<<<<<<<<<<<<");
        ADFUtil.refreshPage();
        if (lawyerBind != null) {
            //DCBindingContainer bindi = (DCBindingContainer) BindingContext.getCurrent().getCurrentBindingsEntry();
            //            DCIteratorBinding roleI = bindi.findIteratorBinding("ExcFolderProceduresVOIterator");
            //  ADFUtil.findIterator("ExcFolderProceduresVOIterator").executeQuery();
            System.out.println(">>>>>>>>>>>>        After Iterator  <<<<<<<<<<<<<<<<<<<<<");
            //            roleI.executeQuery();

            AdfFacesContext.getCurrentInstance().addPartialTarget(getLawyerBind());
            System.out.println(">>>>>>>>>>>>>.   Refresh Lawyer List        <<<<<<<<<<<<<<<<");
        }
        return "next";
    }

    public String getPartyId() {
        System.out.println(">>>>>>>>>>>>>     PartyId    " + partyId);
        return partyId;
    }

    public String getParId() {
        System.out.println(">>>>>>>>>>>>>>>   ParId     " + parId);
        return parId;
    }

    public String getBusinessCaseId() {
        System.out.println(">>>>>>>>>>>     BC  " + businessCaseId);
        return businessCaseId;
    }
    public String businessCaseId;

    public String getDecisionPath() {
        return decisionPath;
    }
    private ComponentReference panelWindowBind;
    // Criminal Info Panel Box
    public void setRestAmount(BigDecimal restAmount) {
        this.restAmount = restAmount;
    }

    private void rollback() throws Exception {
        ADFUtil.findOperation("Rollback").execute();
    }

    private void commit() throws Exception {
        ADFUtil.findOperation("Commit").execute();
    }

    public void doCommit() {
        BindingContext bc = BindingContext.getCurrent();
        String dcfName = bc.getCurrentDataControlFrame();
        DataControlFrame dcf = bc.findDataControlFrame(dcfName);
        dcf.commit();
    }

    public void insertLawyer(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            try {
                System.err.println(">>>>>>>>>>> inside Lawyer Commit");
                ADFUtil.refreshPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                rollback();
                System.err.println(">>>>>>>>>>> inside Lawyer Rollback");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String lawyerId = ADFUtil.evaluateEL("#{sessionScope.lawyerId}") + "";
        System.out.println(">>>>>>>>>>>>>   lawyerId = " + lawyerId);
    }

    public boolean isSelected() {
        Object attachmentType = JSFUtils.resolveExpression("#{row.bindings.AttachmentType.inputValue}");
        if (attachmentType == null) {
            selected = false;
        } else {
            selected = true;
        }
        return selected;
    }

    public boolean selected;

    public BigDecimal getRestAmount() {

        String decsion = ADFUtil.evaluateEL("#{bindings.AdvancePayment.inputValue}") + "";
        BigDecimal amount = (BigDecimal) ADFUtil.evaluateEL("#{bindings.Amount.inputValue}");
        BigDecimal advancePayment = (BigDecimal) ADFUtil.evaluateEL("#{bindings.AdvancePaymentAmount.inputValue}");
        if (decsion.equals("y")) {
            amount = amount.add(advancePayment);
            restAmount = amount;
        } else {
            restAmount = amount;
        }
        return restAmount;
    }

    private boolean criminalInfo;
    // Employee Edit and Visible Fields
    private boolean firEmployee;
    // Chef Edit and Visible Fields
    private boolean chef;
    // Second Employee Edit and Visible Fields
    private boolean sEmployee;

    private BigDecimal restAmount;

    public String getUserId() {
        return userId = JSFUtils.getFromSession("userId") + "";
    }
    public String userId;

    private boolean checkFolder;

    final CallProcedure _callProcedure = new CallProcedure();
    private BigDecimal folderId = (BigDecimal) ADFUtil.evaluateEL("#{pageFlowScope.folderId}");

    private Integer stepNumber;
    private boolean showTable;
    String path = "";
    private RichInputFile fileUploaded;
    private RichSelectOneChoice lawyerBind;
    

    public InstallmentRequestBean() {
        super();
    }

    public boolean getDisabled() {
        if (path.equals("create")) {
            System.out.println(">>>>>>>>>>>>>> disabled False");
            return false;
        } else
            return true;
    }

    public String getStepsNumber() {

        //        String roleId = JSFUtils.getFromSession("roleId") + "";
        //        String path = "";
        //        if (roleId.equals("222")) {
        //            String procedureStatus = ADFUtil.evaluateEL("#{bindings.ProcedureStatus.inputValue}") + "";
        //            if (procedureStatus.length() > 0) {
        //                stepNumber = 3;
        //                path = "insert";
        //            } else {
        //                stepNumber = 0;
        //                path = "create";
        //            }
        //        } else if (roleId.equals("181")) {
        //            stepNumber = 1;
        //            path = "insert";
        //        } else if (roleId.equals("162")) {
        //            stepNumber = 2;
        //            path = "insert";
        //        }
        //        System.out.println(">>>>>>>>>>>>>>>>>>>>>> role Id = " + roleId);
        //        System.out.println(">>>>>>>>>>>>>       path  = " + path);
        //        return path;
        return "create";
    }

    public void setValidLawyer(boolean validLawyer) {
        this.validLawyer = validLawyer;
    }

    public boolean isValidLawyer() {
        String capacityType = ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}") + "";
        String lawyerId = ADFUtil.evaluateEL("#{bindings.PrimaryLawyerId.inputValue}") + "";
        if (capacityType.equals("7") && !capacityType.isEmpty()) {
            if (lawyerId == "0") {
                JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
                validLawyer = false;
            }
        } else {
            validLawyer = true;
        }
        return validLawyer;
    }

    public void setLawyerInfo(boolean lawyerInfo) {
        this.lawyerInfo = lawyerInfo;
    }

    public boolean isLawyerInfo() {
        String capacityType = ADFUtil.getBoundAttributeValue("CapacityType") + "";
        if (capacityType.equals("7")) {
            String lawyerId = ADFUtil.getBoundAttributeValue("PrimaryLawyerId") + "";
            System.out.println(">>>>>>>>>>>> Primary Lawyer Id = " + ADFUtil.getBoundAttributeValue("PrimaryLawyerId"));
            System.out.println(">>>>>>>>>>>> Lawyer Id = " + lawyerId);
            ADFUtil.findOperation("LawyerDetails").execute();
            if (lawyerId == null || lawyerId.equals("null") || lawyerId.equals("")) {
                //                JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
                lawyerInfo = false;
            } else {
                if (lawyerBind != null) {
                    System.out.println("lawyerBind" + lawyerBind);
                    lawyerInfo = true;
                } else {
                    lawyerInfo = false;
                }
            }
        } else {
            lawyerInfo = false;
        }
        return lawyerInfo;
    }

    public void setCriminalInfo(boolean criminalInfo) {
        this.criminalInfo = criminalInfo;
    }

    public boolean isCriminalInfo() {
        String capacityType = ADFUtil.getBoundAttributeValue("CapacityType") + "";
        System.out.println(">>>>>>>>>>>>>       User Role  + capacity Type =     " + capacityType);
        if (capacityType == "6" && capacityType.equals("6")) {
            criminalInfo = true;
        } else {
            criminalInfo = false;
        }
        return criminalInfo;
    }

    public void setFirEmployee(boolean firEmployee) {
        this.firEmployee = firEmployee;
    }

    public boolean isFirEmployee() {

        if (stepNumber == 0 && stepNumber.equals(0)) {
            firEmployee = true;
        } else {
            firEmployee = false;
        }
        System.out.println(">>>>>>>>>>      Employee Edit and Visible Fields = " + firEmployee);
        return firEmployee;
    }


    public void setChef(boolean chef) {
        this.chef = chef;
    }

    public boolean isChef() {
        if (stepNumber == 1 && stepNumber.equals(1)) {
            chef = true;
        } else if (stepNumber == 2 && stepNumber.equals(2)) {
            chef = true;
        } else {
            chef = false;
        }
        System.out.println(">>>>>>>>>>>>>   Chef Edit and Visible Fields = " + chef);
        return chef;
    }

    //    public String checkValidation() {
    //        // Add event code here...
    //        String value = null;
    //        String procedureText = ADFUtil.getBoundAttributeValue("ProcedureText") + "";
    //        System.out.println(">>>>>>>>>>      " + procedureText);
    //        if (procedureText != "" && !procedureText.isEmpty()) {
    //            System.out.println("Inside IF Condition" + procedureText);
    //            value = "insert";
    //        } else {
    //            JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INSERT_VALIDATION}") + "");
    //            value = null;
    //        }
    //        return value;
    //    }

    //    public String getDecisionType(){
    //        String decisionType = ADFUtil.getBoundAttributeValue("DECISION_TYPE1") + "" ;
    //        System.out.println(">>>>>>>>>>>>>       " + decisionType);
    //        if(decisionType.equals("1") && decisionType=="1"){
    //           return "1" ;
    //        }
    //        else {
    //            return "2" ;
    //        }
    //    }

    public void lawyerPopUp(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String lawyerId = ADFUtil.getBoundAttributeValue("PrimaryLawyerId") + "";
        if (lawyerId == null && lawyerId.isEmpty()) {
            JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
        }
    }

    public void procedureStatus(ActionEvent actionEvent) {
        // Add event code here...
        String decisionType = (String) ADFUtil.evaluateEL("#{bindings.DECISION_TYPE.inputValue}");
        System.out.println(">>>>>>>>>>>>>>      Decision Type = " + decisionType);
        //        if (stepNumber == 0) {
        System.out.println(">>>>>>>>>>>>> Inside Step Number =  <<<< " + stepNumber);
        ADFUtil.setBoundAttributeValue("ProcedureStatus", 13);
        ADFUtil.setBoundAttributeValue("CreatedBy", userId);
        ADFUtil.setBoundAttributeValue("DecisionType", 1);
        ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
        ADFUtil.setBoundAttributeValue("StepId", 3);
        ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
        ADFUtil.setEL("#{pageFlowScope.stepNumber}", 1);
        ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
        try {
            commit();
        } catch (Exception e) {
        }
        //        }
        /*else if (stepNumber == 1) {
            if (decisionType.equals("a")) {
                ADFUtil.setBoundAttributeValue("DecisionType", 1);
                ADFUtil.setBoundAttributeValue("StepId", 4);
                ADFUtil.setBoundAttributeValue("CreatedBy", userId);
                ADFUtil.setBoundAttributeValue("ProcedureStatus", 7);
                ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 2);
                ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
                System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
            } else if (decisionType.equals("r")) {
                ADFUtil.setBoundAttributeValue("ProcedureStatus", 6);
                ADFUtil.setBoundAttributeValue("DecisionType", 2);
                ADFUtil.setBoundAttributeValue("StepId", 4);
                ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
                ADFUtil.setBoundAttributeValue("CreatedBy", userId);
                ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 2);
                ADFUtil.setEL("#{pageFlowScope.decisionType}", 2);
                System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
            }
        } else if (stepNumber == 2) {
            if (decisionType.equals("a")) {
                ADFUtil.setBoundAttributeValue("ProcedureStatus", 9);
                ADFUtil.setBoundAttributeValue("DecisionType", 1);
                ADFUtil.setBoundAttributeValue("StepId", 5);
                ADFUtil.setBoundAttributeValue("CreatedBy", userId);
                ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 3);
                ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
                System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
                doCommit();
            } else if (decisionType.equals("r")) {
                ADFUtil.setBoundAttributeValue("ProcedureStatus", 8);
                ADFUtil.setBoundAttributeValue("DecisionType", 2);
                ADFUtil.setBoundAttributeValue("StepId", 5);
                ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
                ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
                ADFUtil.setBoundAttributeValue("CreatedBy", userId);
                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 3);
                ADFUtil.setEL("#{pageFlowScope.decisionType}", 2);
                System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
            }
        } else if (stepNumber == 3) {
            System.out.println(">>>>>>>>>>>>> Inside Step Number =  <<<< " + stepNumber);
            ADFUtil.setBoundAttributeValue("ProcedureStatus", 13);
            ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
            ADFUtil.setBoundAttributeValue("DecisionType", 1);
            ADFUtil.setBoundAttributeValue("ProcedureText1", ADFUtil.getAttributeValue("ProcedureText"));
            ADFUtil.setBoundAttributeValue("StepId", 6);
            ADFUtil.setBoundAttributeValue("CreatedBy", userId);
            ADFUtil.setEL("#{pageFlowScope.stepNumber}", 4);
            ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
            System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
        }*/
        //        return "commit";
    }

    public void showTable(ActionEvent actionEvent) {
        // Add event code here...
        showTable = true;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public boolean getShowTable() {
        return showTable;
    }

    public void setSEmployee(boolean sEmployee) {
        this.sEmployee = sEmployee;
    }

    public boolean isSEmployee() {
        if (stepNumber == 3 && stepNumber.equals(3)) {
            sEmployee = true;
        } else {
            sEmployee = false;
        }
        System.out.println(">>>>>>>>>>>>>   Second Employee Edit and Visible Fields = " + sEmployee);
        return sEmployee;
    }

    public BigDecimal getSeven() {
        return new BigDecimal("7");
    }

    public BigDecimal getSix() {
        return new BigDecimal("6");
    }

    Map<String, InputStream> lof = new HashMap<>();
    List<UcmAttahcmentInfo> attachs = new ArrayList();

    public void uploadFile(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            System.out.println(">>>>>>>>>>>> upload File = " + valueChangeEvent.getNewValue());
            UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
            try {
                lof.put(file.getFilename(), file.getInputStream());
                Integer attachmentId = (Integer) ADFUtil.evaluateEL("#{row.bindings.AttachmentType.inputValue}");
                if (attachmentId != null) {
                    BigInteger attach = BigInteger.valueOf(attachmentId);
                    System.out.println(">>>>>>>>>>>     attachmentId  = " + attachmentId);
                    attachmentValidation();
                    attachs.add(new UcmAttahcmentInfo(file.getInputStream(), file.getFilename(),
                                                      new BigDecimal(attach)));
                    fileUpload = true;
                }
            } catch (IOException e) {

                System.out.println(">>>>>>>>>>>>>>>>Error 1 " + e.getMessage());
            }
        }
    }

    private void attachmentValidation() {
        Row[] rowAttach = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator").getAllRowsInRange();
        Row[] rowRequiredAttach = ADFUtil.findIterator("RequiredAttachmentROVIterator").getAllRowsInRange();
        for (Row reqRow : rowRequiredAttach) {
            System.out.println(">>>>>>>>>> Inside Attachment Validation");
            BigDecimal required = (BigDecimal) reqRow.getAttribute("Id");
            System.out.println(">>>>>>>>>>>>       Id = " + required);
            for (Row attachRow : rowAttach) {
                BigDecimal attachType = (BigDecimal) attachRow.getAttribute("AttachmentType");
                //                BigInteger attach = BigInteger.valueOf(attachType);
                System.out.println(">>>>>>>>>>   Attachment Type = " + attachType);
                if (required.equals(attachType)) {
                    System.out.println(">>>>>>>>>>>>>>      attachmentValidation    ");
                    reqRow.setAttribute("Updatestatus", new BigDecimal(1));
                    break;
                }
            }
        }
    }

    public String startUpload() {
        // Add event code here...
        BigDecimal businessCaseId = (BigDecimal) ADFUtil.getBoundAttributeValue("BusinessCaseId");
        BigDecimal partID = (BigDecimal) ADFUtil.getBoundAttributeValue("PartyId");
        BigDecimal entityID = (BigDecimal) ADFUtil.getBoundAttributeValue("EntityId");
        if (attachs != null && attachs.size() > 0) {
            for (UcmAttahcmentInfo attach : attachs) {
                try {
                    String dId =
                        CjUtils.insertAttachmentAsFileIntoUCM(attach.fileInputStream, 1, businessCaseId.intValue(),
                                                              partID.intValue(), entityID.intValue(), attach.fileName).get("dID");

                    System.out.println(">>>>>>>>>>DID  " + dId);
                    addAttachRecord(dId, attach.attachId);
                } catch (Exception e) {
                    System.out.println(">>>>>>>>>>Error 3" + e.getMessage());
                }
            }
        }
        try {
            commit();
        } catch (Exception e) {
        }
        BigDecimal capacityType = (BigDecimal) ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}");
        System.out.println(">>>>>>>>>>>>        " + capacityType);
        if (capacityType != new BigDecimal(7)) {
            imageUpload();
            System.out.println(">>>>>>>>>>>>>> Upload Image");
        }
        ADFUtil.findOperation("requestHistory").execute();
        ADFUtil.findOperation("alterPlentyInstallments").execute();
        return "insert";
    }

    private void updateInstallTable() {
        BigDecimal id = (BigDecimal) ADFUtil.evaluateEL("#{bindings.Id.inputValue}");
        System.out.println(">>>>>>>>>>>>>>>>>       Alter Install Table with ID = " + id);
        DCIteratorBinding ib = ADFUtil.findIterator("PlentyInstallmentDetailsIterator");
        long count = ib.getEstimatedRowCount();
        RowSetIterator rowSet = ib.getRowSetIterator();

        for (int i = 0; i < count; i++) {
            Row row = rowSet.getRowAtRangeIndex(i);
            row.setAttribute("", id);
        }

    }

    private void addAttachRecord(String diD, BigDecimal attachID) throws ParseException {

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        oracle.jbo.domain.Number folderId =
            (oracle.jbo.domain.Number) ADFUtil.evaluateEL("#{bindings.folderId.inputValue}");

        System.out.println(">>>>>>>>>>>>>.      attachmentId  " + attachID);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>     Folder Id =  " + folderId);
        java.sql.Date currentTime = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator");
        ViewObject vo = iter.getViewObject();
        Row row = vo.createRow();
        // set attributes
        row.setAttribute("FolderActionId", folderId);
        row.setAttribute("AttachmentType", attachID);
        row.setAttribute("DocumentId", (BigDecimal) decimalFormat.parse(diD));
        vo.insertRow(row);

        try {

            iter.executeQuery();
            vo.executeQuery();


        } catch (Exception e) {
            System.out.println(">>>>>>Error  2" + e.getMessage());
        }

    }

    public boolean isCheckFolder() {
        if (fileUploaded != null) {
            System.out.println(">>>>>>>>>>>>>>>>>>>         file Uploaded = " + fileUploaded);
            checkFolder = true;
        }
        return checkFolder;
    }

    public void setFileUploaded(RichInputFile fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public RichInputFile getFileUploaded() {
        return fileUploaded;
    }

    public void fireTable(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String bCId = "";
        String parId = "";
        String partyId = "";
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            System.out.println(">>>>>>>>>>>> Attachment Table = " + valueChangeEvent.getNewValue());
            try {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                System.err.println("**********      capacity type  " + ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}").toString());
                ADFUtil.findOperation("RequiredAttachment").execute();
                ADFUtil.refreshComponent("t7");
                bCId = ADFUtil.evaluateEL("#{bindings.BusinessCaseId.inputValue}").toString();
                parId = ADFUtil.evaluateEL("#{bindings.ParId.inputValue}").toString();
                partyId = ADFUtil.evaluateEL("#{bindings.PartyId.inputValue}").toString();
                ADFUtil.setEL("#{pageFlowScope.BusinessCaseId}", bCId);
                ADFUtil.setEL("#{pageFlowScope.ParID}", parId);
                ADFUtil.setEL("#{pageFlowScope.PartyID}", partyId);
                System.out.println(">>>>>>>>>> PartyId =  " + ADFUtil.evaluateEL("#{pageFlowScope.PartyID}"));
            } catch (Exception e) {
            }
        }
    }

    public void printReports(ActionEvent actionEvent) throws FileNotFoundException, JRException, IOException,
                                                             Exception {
        // Add event code here...
        String reportName = "", reportFolder = "";
        reportName = ADFUtil.evaluateEL("#{pageFlowScope.reportName}").toString();
        reportFolder = ADFUtil.evaluateEL("#{pageFlowScope.reportFolder}").toString();
        this.reportFolder = reportFolder;
        this.reportName = reportName;
        print();

    }

    @Override
    @SuppressWarnings("unchecked")
    public void populateParamsMap(HashMap parametersMap) {
        // TODO Implement this method
        BigDecimal id = (BigDecimal) ADFUtil.evaluateEL("#{bindings.Id.inputValue}");
        if (this.reportName.equals("InstallmentFine")) {
            parametersMap.put("PROCEDURE_ID", id);
            parametersMap.put("userId", userId);
        }

    }

    public void imageUpload() {
        // Add event code here...
        System.out.println(">>>>>>>>>>>>>       imageUpload = ");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal docID = (BigDecimal) ADFUtil.evaluateEL("#{pageFlowScope.document_ID}");
        oracle.jbo.domain.Number folderId =
            (oracle.jbo.domain.Number) ADFUtil.evaluateEL("#{bindings.folderId.inputValue}");

        System.out.println(">>>>>>>>>>>>>       DocID = " + docID);
        DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator");
        ViewObject vo = iter.getViewObject();
        Row row = vo.createRow();
        // set attributes
        row.setAttribute("FolderActionId", folderId);
        row.setAttribute("AttachmentType", 8);
        row.setAttribute("DocumentId", docID);
        vo.insertRow(row);

        try {

            iter.executeQuery();
            vo.executeQuery();


        } catch (Exception e) {
            System.out.println(">>>>>>Error  " + e.getMessage());
        }
    }

    public void setLawyerBind(RichSelectOneChoice lawyerBind) {
        this.lawyerBind = lawyerBind;
    }

    public RichSelectOneChoice getLawyerBind() {
        return lawyerBind;
    }

    public String addAdditionAttach() {
        // Add event code here...
        ADFUtil.findOperation("CreateInsert").execute();
        return null;
    }

    public void setCheckBoxStatus(RichSelectBooleanCheckbox checkBoxStatus) {
        this.checkBoxStatus = checkBoxStatus;
    }

    public RichSelectBooleanCheckbox getCheckBoxStatus() {
        return checkBoxStatus;
    }

    public void updateAttachStatus(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

    }

    public boolean isAttachmentUploaded() {
        Object attachmentTypeId = JSFUtils.resolveExpression("#{row.Id}");
        RowSetIterator uploadedAttachmentsIterator =
            ADFUtil.findIterator("ExcFolderActionsAttachVOIterator").getViewObject().createRowSetIterator("");
        while (uploadedAttachmentsIterator.hasNext()) {
            Row uploadedAttachmentRow = uploadedAttachmentsIterator.next();
            System.out.println("attachmentTypeId " + attachmentTypeId + " : " + attachmentTypeId.getClass());
            System.out.println("AttachmentType  " + uploadedAttachmentRow.getAttribute("AttachmentType") + " : " +
                               uploadedAttachmentRow.getAttribute("AttachmentType").getClass());
            if (attachmentTypeId.equals(uploadedAttachmentRow.getAttribute("AttachmentType")))
                return true;
        }
        return false;
    }

    public void setPopUp(RichPopup popUp) {
        this.popUp = ComponentReference.newUIComponentReference(popUp);
    }

    public RichPopup getPopUp() {
        return popUp == null ? null : (RichPopup) popUp.getComponent();
    }

    public void setPanelWindowBind(RichPanelWindow panelWindowBind) {
        this.panelWindowBind = ComponentReference.newUIComponentReference(panelWindowBind);
    }

    public RichPanelWindow getPanelWindowBind() {
        return panelWindowBind == null ? null : (RichPanelWindow) panelWindowBind.getComponent();
    }
    //    ActionEvent actionEvent
    public void closePopup(ActionEvent actionEvent) {
        // Add event code here...
        try {
            System.err.println(">>>>>>>>>>> inside Lawyer Commit");
            ParLawyerBean plb = new ParLawyerBean();
            plb.clickButton(actionEvent);
            ADFUtil.refreshPage();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getPopUp().hide();
            System.out.println(">>>>>>>>>>>>>  committed    ");
            ADFUtil.refreshPage();
        } catch (Exception e) {
        }
    }

    public void caneclPopup(ActionEvent actionEvent) {
        // Add event code here...
        try {
            System.err.println(">>>>>>>>>>> inside Lawyer Rollback");
            ADFUtil.refreshPage();
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            this.getPopUp().hide();
            System.out.println(">>>>>>>>>>>>>  Rollback    ");
            decisionPath = "rollback";
            BigDecimal lawyerId = (BigDecimal) ADFUtil.evaluateEL("#{sessionScope.lawyerId}");
            System.out.println(">>>>>>>>>>>>>   lawyerId = " + lawyerId);
            ADFUtil.setEL("#{bindings.PrimaryLawyerId.inputValue}", lawyerId);
            ADFUtil.refreshPage();
        } catch (Exception e) {
        }

    }

    public void serverEventHandler(ClientEvent clientEvent) {
        // Add event code here...
        double _width = (Double) clientEvent.getParameters().get("width");
        double _height = (Double) clientEvent.getParameters().get("height");
        int width = (int) _width;
        int height = (int) _height;
        this.getPanelWindowBind().setContentHeight(Math.max(100, height - 100));
        this.getPanelWindowBind().setContentWidth(Math.max(100, width - 100));
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        System.out.println(">>>>>>>>>>>>    Inside Server Handler           <<<<<<<<<<<<<<<<<<<<");
        this.getPopUp().show(hints);
    }

    public void refreshPage(ReturnEvent returnEvent) {
        // Add event code here...
        ADFUtil.refreshPage();
        ADFUtil.refreshPage();
    }

    public void lawyerRefresh(ReturnEvent returnEvent) {
        // Add event code here...
        System.out.println(">>>>>>>>>>>>>>      Inside Refresh Lawyers  <<<<<<<<<<<<<<<<<<");
        /*ADFUtil.findIterator("CriminalLawyersROVIterator").getViewObject().clearCache();
        ADFUtil.findIterator("CriminalLawyersROVIterator").getViewObject().executeQuery();
        AdfFacesContext.getCurrentInstance().addPartialTarget(getLawyerBind());
        System.out.println(">>>>>>>>>>>>>.   Refresh Lawyer List        <<<<<<<<<<<<<<<<");*/
        JUCtrlValueBinding binding = ADFUtil.findCtrlBinding("PrimaryLawyerId");
        binding.executeQuery();
        ADFUtil.refreshPage();

    }
}
