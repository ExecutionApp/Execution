package com.egabi.cjexec.view.beans.cjexec.enableopposition;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

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
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class EnableOppositionBean extends PrintEnabledBean {
    // Lawyer List
    private boolean validLawyer;
    // Lawyer Info Panel Box
    private boolean lawyerInfo;
    // Criminal Info Panel Box
    private boolean userRole;

    public boolean isSelected() {
        return selected;
    }
    // Employee Edit and Visible Feilds
    private boolean employeeFeilds;
    // Chef Edit and Visible Fields
    private boolean chefFeilds;

    private boolean selected = false;

    private boolean checkFolder;
    String userId = JSFUtils.getFromSession("userId") + "";

    final CallProcedure _callProcedure = new CallProcedure();
    private BigDecimal folderId = (BigDecimal) ADFUtil.evaluateEL("#{bindings.folderId.inputValue}");
    Integer stepNumber;
    private UploadedFile attachmentFile;
    private RichInputFile fileUploaded;
    private RichRegion imageBind;
    private RichSelectOneChoice lawyerBind;
    //    = (Integer) ADFUtil.evaluateEL("#{pageFlowScope.stepsNumber}");


    public String getStepsNumber() {
        String roleId = JSFUtils.getFromSession("roleId") + "";
        String path = "";
        //rania
        //if(roleId.equals("341")){
        //        if (roleId.equals("222")) {
        //            stepNumber = 0;
        //            path = "create";
        //        } else {
        //            stepNumber = 1;
        //            path = "insert";
        //        }
        //        System.out.println(">>>>>>>>>       role Id  = " + roleId);
        //        System.out.println(">>>>>>>>>       path  = " + path);
        //        return path;
        return "create";
    }

    public EnableOppositionBean() {
        super();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>     Folder Id =  " + folderId);
    }


    public void setValidLawyer(boolean validLawyer) {
        this.validLawyer = validLawyer;
    }

    public boolean isValidLawyer() {
        String capacityType = ADFUtil.getBoundAttributeValue("CapacityType") + "";
        BigDecimal lawyerId = (BigDecimal) ADFUtil.evaluateEL("#{bindings.Lawyer_ID.inputValue}");
        if (capacityType.equals("5")) {
            if (lawyerId == null) {
                JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
                validLawyer = false;
            } else {
                validLawyer = true;
            }
        } else {
            validLawyer = false;
        }
        return validLawyer;
    }

    public void setLawyerInfo(boolean lawyerInfo) {
        this.lawyerInfo = lawyerInfo;
    }

    public boolean isLawyerInfo() {
        String capacityType = ADFUtil.getBoundAttributeValue("CapacityType") + "";
        if (capacityType.equals("5")) {
            String lawyerId = ADFUtil.getBoundAttributeValue("Lawyer_ID") + "";
            System.out.println(">>>>>>>>>>>> Lawyer Id = " + ADFUtil.getBoundAttributeValue("Lawyer_ID"));
            System.out.println(">>>>>>>>>>>> Lawyer Id = " + lawyerId);
            if (lawyerId == null || lawyerId.equals("null") || lawyerId.equals("")) {
                JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
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

    public void setUserRole(boolean userRole) {
        this.userRole = userRole;
    }

    public boolean isUserRole() {
        String capacityType = ADFUtil.getBoundAttributeValue("CapacityType") + "";
        System.out.println(">>>>>>>>>>>>>       User Role  + capacity Type =     " + capacityType);
        if (capacityType == "4" && capacityType.equals("4")) {
            try {
                ADFUtil.executeVoidOperation("ExecuteWithParams1", null);
            } catch (Exception e) {
            }
            userRole = true;
        } else {
            userRole = false;
        }
        return userRole;
    }


    public void setEmployeeFeilds(boolean employeeFeilds) {
        this.employeeFeilds = employeeFeilds;
    }

    public boolean isEmployeeFeilds() {
        if (stepNumber.equals(1) && stepNumber == 1) {
            employeeFeilds = false;
        } else {
            employeeFeilds = true;

        }
        System.out.println(">>>>>>>>>>>>>>> Employee Feilds = " + employeeFeilds);
        return employeeFeilds;
    }

    public void setChefFeilds(boolean chefFeilds) {
        this.chefFeilds = chefFeilds;
    }

    public boolean isChefFeilds() {
        if (stepNumber.equals(1) && stepNumber == 1) {
            chefFeilds = true;
        } else {
            chefFeilds = false;
        }
        System.out.println(">>>>>>>>>>>>>>> Chef Feilds = " + chefFeilds);
        return chefFeilds;
    }

    public void lawyerPopUp(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BigDecimal lawyerId = (BigDecimal) ADFUtil.evaluateEL("#{bindings.Lawyer_ID.inputValue}");
        if (lawyerId == null) {
            System.out.println(">>>>>>>>>>> Lawyer Pop up");
            JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.INVALID_LAWYER}") + "");
        } else {
            System.out.println(">>>>>>>>>       you Choose Criminal ");
        }
    }

    public void doCommit() {
        BindingContext bc = BindingContext.getCurrent();
        String dcfName = bc.getCurrentDataControlFrame();
        DataControlFrame dcf = bc.findDataControlFrame(dcfName);
        dcf.commit();
    }


    public void procedureStatus(ActionEvent actionEvent) {
        // Add event code here...
        String decisionType = (String) ADFUtil.evaluateEL("#{bindings.DECISION_TYPE.inputValue}");
        System.out.println(">>>>>>>>>>>>>>      Decision Type = " + decisionType);
        String capacity = ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}") + "";
        //        if (stepNumber == 0) {
        ADFUtil.setBoundAttributeValue("CreatedBy", userId);
        ADFUtil.setBoundAttributeValue("DecisionType", 1);
        ADFUtil.setBoundAttributeValue("StepId", 1);
        ADFUtil.setBoundAttributeValue("ProcedureStatus", 3);
        ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
        ADFUtil.setEL("#{pageFlowScope.stepNumber}", 1);
        ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
        doCommit();

        //        }
        //
        //        else if (stepNumber == 1) {
        //            if (decisionType.equals("a")) {
        //                System.out.println(">>>>>>>>>>>>>       Approve Condition");
        //                ADFUtil.setBoundAttributeValue("ProcedureStatus", 3);
        //                ADFUtil.setBoundAttributeValue("DecisionType", 1);
        //                ADFUtil.setBoundAttributeValue("StepId", 2);
        //                ADFUtil.setBoundAttributeValue("CreatedBy", userId);
        //                ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
        //                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 2);
        //                ADFUtil.setEL("#{pageFlowScope.decisionType}", 1);
        //            } else if (decisionType.equals("r")) {
        //                System.out.println(">>>>>>>>>>>>>       Reject Condition");
        //                ADFUtil.setBoundAttributeValue("ProcedureStatus", 4);
        //                ADFUtil.setBoundAttributeValue("DecisionType", 2);
        //                ADFUtil.setBoundAttributeValue("StepId", 2);
        //                ADFUtil.setBoundAttributeValue("CreatedBy", 587);
        //                ADFUtil.setBoundAttributeValue("ClosedFlag", 1);
        //                ADFUtil.setEL("#{pageFlowScope.stepNumber}", 2);
        //                ADFUtil.setEL("#{pageFlowScope.decisionType}", 2);
        //                System.out.println(">>>>>>>>>>>     stepNumber = " + ADFUtil.evaluateEL("#{pageFlowScope.stepNumber}"));
        //            }
        //
        //        }
        //        return "insert";
    }

    public void uploadAttachments(ActionEvent actionEvent) {
        // Add event code here...
        InputStream is;

    }

    public BigDecimal getFour() {
        return new BigDecimal("4");
    }

    public BigDecimal getFive() {
        return new BigDecimal("5");
    }
    Map<String, InputStream> lof = new HashMap<>();
    List<UcmAttahcmentInfo> attachs = new ArrayList();

    public void uploadFile(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            boolean isFound = false;
            System.out.println(">>>>>>>>>>>> upload File = " + valueChangeEvent.getNewValue());
            Row row = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator").getCurrentRow();
            UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
            try {
                lof.put(file.getFilename(), file.getInputStream());
                BigDecimal attachmentType = new BigDecimal("9");
                BigDecimal attachmentId = (BigDecimal) ADFUtil.evaluateEL("#{bindings.AttachId.inputValue}");
                if (selected == true) {
                    attachs.add(new UcmAttahcmentInfo(file.getInputStream(), file.getFilename(), attachmentType));
                } else {
                    attachs.add(new UcmAttahcmentInfo(file.getInputStream(), file.getFilename(), attachmentId));
                }
            } catch (IOException e) {
                System.out.println(">>>>>>>>>>>>>>>>Error 1 " + e.getMessage());
            }
        }
    }

    public boolean isCheckFolder() {
        if (fileUploaded != null) {
            System.out.println(">>>>>>>>>>>>>>>>>>>         file Uploaded = " + fileUploaded);
            checkFolder = true;
        }
        return checkFolder;
    }

    private String attachmentValidation() {
        Row[] rowAttach = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator").getAllRowsInRange();
        Row[] rowRequiredAttach = ADFUtil.findIterator("RequiredAttachmentROVIterator").getAllRowsInRange();
        int i = 0;
        for (Row row : rowRequiredAttach) {
            BigDecimal attachmentId = (BigDecimal) row.getAttribute("Id");
            for (Row rrow : rowAttach) {
                BigDecimal attachmentType = (BigDecimal) rrow.getAttribute("AttachmentType");
                if (attachmentId == attachmentType) {
                    i++;
                    break;
                }
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>     rowRequiredAttach.length =  " + rowRequiredAttach.length);
        System.out.println(">>>>>>>>>>>>>>>     i = " + i);
        if (rowRequiredAttach.length == i) {
            return "insert";
        } else {
            JSFUtils.addFacesErrorMessage(ADFUtil.evaluateEL("#{bundle.REQUIRED_ATTACHMENT_MESSAGE}") + "");
            return "";
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

    public String startUpload() {
        // Add event code here...
        BigDecimal businessCaseId = (BigDecimal) ADFUtil.getBoundAttributeValue("BusinessCaseId");
        oracle.jbo.domain.Number partID = (oracle.jbo.domain.Number) ADFUtil.getBoundAttributeValue("PartyId");
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
        doCommit();

        BigDecimal capacityType = (BigDecimal) ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}");
        if (capacityType != new BigDecimal(5)) {
            try {
                imageUpload();
                System.out.println(">>>>>>>>>>>>>> Image Uploadded");
            } catch (ParseException e) {
                e.getMessage();

            }
        }

        return attachmentValidation();

    }

    public void fireTable(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null &&
            valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()) {
            System.out.println(">>>>>>>>>>>> Attachment Table = " + valueChangeEvent.getNewValue());
            try {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                ADFUtil.executeVoidOperation("ExecuteWithParams1", null);
            } catch (Exception e) {
            }
            ADFUtil.refreshComponent("t1");
            try {
                ADFUtil.executeVoidOperation("ExecuteWithParams1", null);
            } catch (Exception e) {
            }
        }
    }

    public void setFileUploaded(RichInputFile fileUploaded) {
        this.fileUploaded = fileUploaded;
    }

    public RichInputFile getFileUploaded() {
        return fileUploaded;
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
        if (this.reportName.equals("EnableOpposition")) {
            parametersMap.put("PROCEDURE_ID", id);
            parametersMap.put("userId", userId);
        }


    }

    public void setImageBind(RichRegion imageBind) {
        this.imageBind = imageBind;
    }

    public RichRegion getImageBind() {
        return imageBind;
    }

    public void imageUpload() throws ParseException {
        // Add event code here...
        System.out.println(">>>>>>>>>>>>>       imageUpload = ");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        String docID = ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") + "";
        System.out.println(">>>>>>>>>>>>>       DocID = " + docID);
        BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(docID);
        oracle.jbo.domain.Number folderId =
            (oracle.jbo.domain.Number) ADFUtil.evaluateEL("#{bindings.folderId.inputValue}");

        DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderActionsAttachVOIterator");
        ViewObject vo = iter.getViewObject();
        Row row = vo.createRow();
        // set attributes
        row.setAttribute("FolderActionId", folderId);
        row.setAttribute("AttachmentType", 8);
        row.setAttribute("DocumentId", bigDecimal);
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

    public String getAttachment() {
        // Add event code here...

        ADFUtil.findOperation("CreateInsert").execute();
        System.out.println(">>>>>>>>>>>     Done    CreateInsert ");
        return null;
    }

    public String addAdditionAttach() {
        // Add event code here...
        ADFUtil.findOperation("CreateInsert").execute();
        selected = true;
        System.out.println(">>>>>>>>>>>     Done    CreateWithParams ");
        return null;
    }

    
}

