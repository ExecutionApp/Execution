package com.egabi.cjexec.view.beans.oppositions;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.model.appmodules.OppositionAMImpl;
import com.egabi.cjexec.view.beans.parlawyer.ParLawyerBean;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.io.InputStream;

import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.model.UploadedFile;

public class OppositionsBean extends PrintEnabledBean {
    private String idNumber, fullName, nickname, age, address, jobTitle, procedureSerial, procedureStatus, fullContextPath;
    private String decisionPath;
    private Date maxDate;
    private UploadedFile file;
    String format = "pdf";
    private RichPopup attachsPopup;
    private RichPopup partyPopup;
    private RichPopup lawyersPopup;

    public OppositionsBean() {

    }

    public OppositionsBean(String reportFolder, String reportName) {
        super(reportFolder, reportName);
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void createOpposition() {
//        ADFUtil.setEL("#{sessionScope.entityId}", 59);
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();

        int folderActionId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.folderID}") == null ? "5" : ADFUtil.evaluateEL("#{sessionScope.folderID}").toString());
        
        int resPartyId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.resPartyId}") == null ? "7248" :
                             ADFUtil.evaluateEL("#{sessionScope.resPartyId}").toString());
        int userId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") == null ? "802" :
                             ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());

        String[] party = oppoAM.getParty(resPartyId); // resPartyId
        ADFUtil.setEL("#{pageFlowScope.partyId}", party[0]);
        ADFUtil.setEL("#{pageFlowScope.parId}", party[7]);
        fullName = party[1];
        idNumber = party[2];
        nickname = party[3];
        age = party[4];
        address = party[5];
        jobTitle = party[6];
        ADFUtil.setEL("#{pageFlowScope.parName}", fullName);
        String procedureSerial = oppoAM.getProcedureIdFromExcFolderActions(folderActionId);
        if (!procedureSerial.equals("")) {
            this.procedureSerial = procedureSerial;
            this.procedureStatus =
                "\u062a\u0645 \u0627\u0639\u062a\u0645\u0627\u062f \u0631\u0626\u064a\u0633 \u0627\u0644\u0646\u064a\u0627\u0628\u0629 " +
                "\u0644\u0637\u0644\u0628 \u0627\u0644\u062a\u0645\u0643\u064a\u0646";
        }
        ViewObject view = oppoAM.getExcFolderProceduresView1();
        Row row = view.getCurrentRow();
        row.setAttribute("ResPartyId", resPartyId); // resPartyId
        row.setAttribute("CreatedBy", userId); // #{sessionScope. userId}

        if (ADFUtil.evaluateEL("#{sessionScope.oppositionType}") != null) {
            int oppositionType = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.oppositionType}").toString());
            if (oppositionType == 2) {
                ViewObject oppositionsEnableAttachmentsView = oppoAM.getOppositionsEnableAttachmentsRVO1();
                oppositionsEnableAttachmentsView.setWhereClause("ID = 4");
                oppositionsEnableAttachmentsView.executeQuery();
            } else {
                ViewObject oppositionsAttachmentsView = oppoAM.getOppositionsAttachmentsRVO1();
                oppositionsAttachmentsView.executeEmptyRowSet();
            }
        }
        long businessCaseId = oppoAM.getBusinessCaseId(resPartyId) ;
        
        String[] caseDetails = oppoAM.getBusinessCaseDetails(businessCaseId);
        if(caseDetails[1] != null){
            if(validateHearingDate(caseDetails[1])){
                ADFUtil.setEL("#{pageFlowScope.validateHearingDate}", true);
                row.setAttribute("CourtCircleDatesHearingId", caseDetails[5]);
                ADFUtil.setEL("#{pageFlowScope.reservationId}", caseDetails[6]);
                ADFUtil.setEL("#{pageFlowScope.hearingDateMsg}", "\u062a\u0645 \u0627\u0644\u0636\u0645 \u0644\u0644\u062c\u0644\u0633\u0629 " +
                    "\u0627\u0644\u0645\u0628\u064a\u0646\u0629");
            }
        }
        ADFUtil.setEL("#{pageFlowScope.businessCaseNumber}", caseDetails[0]);
        ADFUtil.setEL("#{pageFlowScope.businessCaseName}", caseDetails[3]);
        ADFUtil.setEL("#{pageFlowScope.hearingDate}", caseDetails[1]);
        ADFUtil.setEL("#{pageFlowScope.businessCaseDegree}", caseDetails[2]);
        ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
        ADFUtil.setEL("#{pageFlowScope.enablePrint}", null);
    }

    public void getFees(ValueChangeEvent valueChangeEvent) {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();


        int resPartyId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.resPartyId}") == null ? "7522" :
                             ADFUtil.evaluateEL("#{sessionScope.resPartyId}").toString());

        ViewObject excCourtBcPartyDebtView = oppoAM.getExcCourtBcPartyDebtRVO1();
        excCourtBcPartyDebtView.setNamedWhereClauseParam("capacityType", valueChangeEvent.getNewValue());
        excCourtBcPartyDebtView.setNamedWhereClauseParam("resPartyId", resPartyId); // resPartyId
        excCourtBcPartyDebtView.executeQuery();

        ViewObject oppositionsAttachmentsView = oppoAM.getOppositionsAttachmentsRVO1();
        oppositionsAttachmentsView.setWhereClause("CAPACITY_TYPE = " + valueChangeEvent.getNewValue());
        oppositionsAttachmentsView.executeQuery();
    }

    public String saveOpposition() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();

        int folderActionId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.folderID}").toString());
        int resPartyId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.resPartyId}") == null ? "7522" : ADFUtil.evaluateEL("#{sessionScope.resPartyId}").toString());
        int userId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") == null ? "802" : ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        int logedInEntityId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.entityId}") == null ? "59" : ADFUtil.evaluateEL("#{sessionScope.userInfo.entityId}").toString());
        int entityId = oppoAM.getEntityId(resPartyId);
        int oppositionType = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.oppositionType}").toString());
        int reservationId = ADFUtil.evaluateEL("#{pageFlowScope.reservationId}") == null ? 0 : 
            Integer.parseInt(ADFUtil.evaluateEL("#{pageFlowScope.reservationId}").toString()) ;
        int attachmentType = 0 ;
        int actionType = 0 ;        
        
        ViewObject oppositionsAttachmentsView = null ;
        ViewObject oppositionsEnableAttachmentsView = null ;
        Row ro = null ;
        
        if(oppositionType == 1){
            actionType = 2 ;
            oppositionsAttachmentsView = ADFUtil.findIterator("OppositionsAttachmentsRVO1Iterator").getViewObject() ;
            ro = oppositionsAttachmentsView.getCurrentRow();
            attachmentType = Integer.parseInt(ro.getAttribute("AttachmentType").toString());
            
            RowSetIterator rsi = oppositionsAttachmentsView.createRowSetIterator("");
            while (rsi.hasNext()) {
                Row row1 = rsi.next();
                if (row1.getAttribute("InsertFlag") == null) {
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "REQUIRED_UPLOAD");
                    return null;
                }
            }
        }else if(oppositionType == 2){
            actionType = 8 ;
            attachmentType = 6 ;
            oppositionsEnableAttachmentsView = oppoAM.getOppositionsEnableAttachmentsRVO1();
            ro = oppositionsEnableAttachmentsView.getCurrentRow();
            attachmentType = Integer.parseInt(ro.getAttribute("AttachmentType").toString());
        }        
        

        ViewObject excFolderProceduresView = oppoAM.getExcFolderProceduresView1();
        Row excFolderProceduresRow = excFolderProceduresView.getCurrentRow();
        excFolderProceduresRow.setAttribute("ProsecutionId", entityId);
        long excFolderProceduresId = Long.parseLong(excFolderProceduresRow.getAttribute("Id").toString());
        ADFUtil.setEL("#{pageFlowScope.procedureIdSave}", excFolderProceduresId);
        long courtCircleDatesHearingId =
            Long.parseLong(excFolderProceduresRow.getAttribute("CourtCircleDatesHearingId").toString());
        long capacityType = Long.parseLong(excFolderProceduresRow.getAttribute("CapacityType").toString());
        excFolderProceduresRow.setAttribute("ProcedureStatus", 1);

        ViewObject view = oppoAM.getExcCourtBcPartyDebtRVO1();
        view.setNamedWhereClauseParam("capacityType", capacityType);
        view.setNamedWhereClauseParam("resPartyId", resPartyId); // resPartyId
        view.executeQuery();
        long rowCount = view.getEstimatedRowCount();
        if (rowCount > 0) {
            for (int i = 0; i < rowCount; i++) {
                Row row = view.getCurrentRow();
                long paidAmount =
                    Long.parseLong(row.getAttribute("PaidAmount") == null ? "0" :
                                   row.getAttribute("PaidAmount").toString());
                if (paidAmount == 0) {
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "NOT_PAID_AMOUNT");
                    return null;
                }
            }
        }

        long partyId = Long.parseLong(ADFUtil.evaluateEL("#{pageFlowScope.partyId}").toString());

        ViewObject excFolderDataView = oppoAM.getExcFolderDataView1();
        excFolderDataView.setWhereClause("ID = " + folderActionId); // folderActionId
        excFolderDataView.executeQuery();
        Row excFolderDataRow = excFolderDataView.first();
        excFolderDataRow.setAttribute("JudgementStatus", 3);
        excFolderDataRow.setAttribute("ExecutionFlag", 2);
        long folderDataId = Long.parseLong(excFolderDataRow.getAttribute("Id").toString());
        String bcdId =
            excFolderDataRow.getAttribute("BusinessCaseDetailId") == null ? "0" :
            excFolderDataRow.getAttribute("BusinessCaseDetailId").toString();
        long businessCaseDetailId = Long.parseLong(bcdId);

        am.getDBTransaction().commit();
                
        ViewObject excFolderActionsView = oppoAM.getExcFolderActionsView1();
        if (ADFUtil.evaluateEL("#{pageFlowScope.newFolderActionId}") != null) {
            Row excFolderActionRow = excFolderActionsView.getCurrentRow();
            excFolderActionRow.setAttribute("ProcedureId", excFolderProceduresId);
            excFolderActionRow.setAttribute("FolderRecordId", folderActionId);
            excFolderActionRow.setAttribute("ActionType", 2);
        } else {
            Row excFolderActionRow = excFolderActionsView.createRow();
            excFolderActionRow.setAttribute("ProcedureId", excFolderProceduresId);
            excFolderActionRow.setAttribute("FolderRecordId", folderActionId);
            excFolderActionRow.setAttribute("ActionType", 2);
            excFolderActionsView.insertRow(excFolderActionRow);
        }
        
        ViewObject attachsView = oppoAM.getExcFolderActionsAttachView2();
        Row attachsRow = attachsView.getCurrentRow();
        attachsRow.setAttribute("DocumentId", ADFUtil.evaluateEL("#{pageFlowScope.documentId}"));
        attachsRow.setAttribute("AttachmentType", attachmentType);
        
        long businessCaseId = oppoAM.getBusinessCaseId(resPartyId);
        
        ViewObject courtBcHearingReservationView = oppoAM.getCourtBcHearingReservationView1();
        if(reservationId == 0){        
            Row courtBcHearingReservationRow = courtBcHearingReservationView.createRow();
            courtBcHearingReservationRow.setAttribute("CircleHearingDateId", courtCircleDatesHearingId);
            courtBcHearingReservationRow.setAttribute("IsActive", "Y");
            courtBcHearingReservationRow.setAttribute("SourceEntityId", logedInEntityId); // #{sessionScope. entityId}
            courtBcHearingReservationRow.setAttribute("CreatedBy", userId); // #{sessionScope. userId}
            courtBcHearingReservationRow.setAttribute("BusinessCaseId", businessCaseId);
            courtBcHearingReservationView.insertRow(courtBcHearingReservationRow);
            
            am.getDBTransaction().commit();
        }
        view.executeQuery();

        Row courtBcHearingReservationRowAfterInsert = courtBcHearingReservationView.getCurrentRow();

        ViewObject courtBcHearingResPartiesView = oppoAM.getCourtBcHearingResPartiesView1();
        Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.createRow();
        courtBcHearingResPartiesRow.setAttribute("BusinessCaseDetailsId", businessCaseDetailId);
        courtBcHearingResPartiesRow.setAttribute("CreatedEntityId", logedInEntityId); // #{sessionScope. entityId}
        courtBcHearingResPartiesRow.setAttribute("PartyId", partyId);
        courtBcHearingResPartiesRow.setAttribute("CreatedBy", userId); // #{sessionScope. userId}
        courtBcHearingResPartiesRow.setAttribute("CourtBcHearingResId", reservationId != 0 ? reservationId : courtBcHearingReservationRowAfterInsert.getAttribute("Id"));

        am.getDBTransaction().commit();


        //TO INSERT THE PARTY'S PERSONAL PHOTO FROM CAMERA
        if (ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}") != null) {
            String documentId = ADFUtil.evaluateEL("#{sessionScope.DOCUMENTID}").toString();

            ADFUtil.findOperation("CreateInsert").execute();
            ViewObject excFolderActionsAttachView = oppoAM.getExcFolderActionsAttachView2();
            Row excFolderActionsAttachRow = excFolderActionsAttachView.getCurrentRow();
            excFolderActionsAttachRow.setAttribute("AttachmentType", 8);
            excFolderActionsAttachRow.setAttribute("DocumentId", documentId);
            am.getDBTransaction().commit();
        }
        String partyName = ADFUtil.evaluateEL("#{pageFlowScope.parName}").toString() ;
        int procedureId = Integer.parseInt(excFolderProceduresRow.getAttribute("Id").toString());
        String bcId = String.valueOf(businessCaseDetailId);
        String createdBy = String.valueOf(userId);
        String parId = String.valueOf(partyId);
        
        oppoAM.excRequestHistory(procedureId, createdBy, partyName, parId, actionType, bcId);
        
        JSFUtils.showFacesInfoMessageFromBundle("bundle", "OPPOSITION_SAVED");
        ADFUtil.setEL("#{pageFlowScope.enablePrint}", "yes");
        return "back" ;
    }


    public void setProcedureSerial(String procedureSerial) {
        this.procedureSerial = procedureSerial;
    }

    public String getProcedureSerial() {
        return procedureSerial;
    }

    public void setProcedureStatus(String procedureStatus) {
        this.procedureStatus = procedureStatus;
    }

    public String getProcedureStatus() {
        return procedureStatus;
    }

    public void upload(ValueChangeEvent valueChangeEvent) {

        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();
        int attachmentType = 0 ;
        
//        if (ADFUtil.evaluateEL("#{sessionScope.oppositionType}") != null) {
//            int oppositionType = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.oppositionType}").toString());
//            if (oppositionType == 2) {
//                ViewObject oppositionsEnableAttachmentsView = oppoAM.getOppositionsEnableAttachmentsRVO1();
//                Row oppositionsEnableAttachmentsRow = oppositionsEnableAttachmentsView.getCurrentRow();
//                oppositionsEnableAttachmentsRow.setAttribute("InsertFlag", true);
//                attachmentType = Integer.parseInt(oppositionsEnableAttachmentsRow.getAttribute("AttachmentType").toString());
//            } else {
//                ViewObject oppositionsAttachmentsView = oppoAM.getOppositionsAttachmentsRVO1();
//                Row oppositionsAttachmentsRow = oppositionsAttachmentsView.getCurrentRow();
//                oppositionsAttachmentsRow.setAttribute("InsertFlag", 1);
//                attachmentType = Integer.parseInt(oppositionsAttachmentsRow.getAttribute("AttachmentType").toString());
//            }
//        }
        
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();

            String documentId = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, uf.getFilename()).get("dID");
            ADFUtil.setEL("#{pageFlowScope.documentId}", documentId);            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String cancel() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");

        am.getDBTransaction().rollback();
        return "back";
    }


    public String openAttachsPopup() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();

        if (ADFUtil.evaluateEL("#{pageFlowScope.newFolderActionId}") == null) {
            ADFUtil.findOperation("CreateInsert1").execute();
            ADFUtil.setEL("#{pageFlowScope.newFolderActionId}", "yes");
        }

        ADFUtil.findOperation("CreateInsert").execute();

        if (ADFUtil.evaluateEL("#{sessionScope.oppositionType}") != null) {
            int oppositionType = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.oppositionType}").toString());
            if (oppositionType == 2) {
                ViewObject oppositionsEnableAttachmentsView = oppoAM.getOppositionsEnableAttachmentsRVO1();
                Row oppositionsEnableAttachmentsRow = oppositionsEnableAttachmentsView.getCurrentRow();
                oppositionsEnableAttachmentsRow.setAttribute("InsertFlag", 1);
                ADFUtil.setEL("#{pageFlowScope.attachmentType}",
                              oppositionsEnableAttachmentsRow.getAttribute("AttachmentType"));
            } else {
                ViewObject oppositionsAttachmentsView = oppoAM.getOppositionsAttachmentsRVO1();
                Row oppositionsAttachmentsRow = oppositionsAttachmentsView.getCurrentRow();
                oppositionsAttachmentsRow.setAttribute("InsertFlag", 1);
                ADFUtil.setEL("#{pageFlowScope.attachmentType}",
                              oppositionsAttachmentsRow.getAttribute("AttachmentType"));
            }
        }


        ADFUtil.setEL("#{pageFlowScope.openAttachs}", "yes");

        return null;
    }

    public String closePopup() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        OppositionAMImpl oppoAM = (OppositionAMImpl) am.getOppositionAM1();

        ViewObject folderProceduresView = oppoAM.getExcFolderProceduresView1();
        Row folderProceduresRow = folderProceduresView.getCurrentRow();

        ViewObject oppositionsAttachmentsView = oppoAM.getOppositionsAttachmentsRVO1();
        oppositionsAttachmentsView.setWhereClause("CAPACITY_TYPE = " +
                                                  folderProceduresRow.getAttribute("CapacityType"));
        oppositionsAttachmentsView.executeQuery();

        attachsPopup.hide();
        return null;
    }


    public void setAttachsPopup(RichPopup attachsPopup) {
        this.attachsPopup = attachsPopup;
    }

    public RichPopup getAttachsPopup() {
        return attachsPopup;
    }


    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public Date getMaxDate1() {
        return maxDate;
    }

    public Date getMaxDate() {
        try {
            Calendar cal = Calendar.getInstance();
            java.util.Date date = cal.getTime();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = formatter.format(date);
            maxDate = formatter.parse(currentDate);
            return formatter.parse(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String saveParty() {
        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        am.updateParPartiesLog(Long.parseLong(ADFUtil.evaluateEL("#{pageFlowScope.partyId}").toString()));
        partyPopup.hide();
        return null;
    }
    String pathReport = "/u02/config/report/";

    public String printReport() {
        ViewObject view = ADFUtil.findIterator("ExcFolderProceduresView1Iterator").getViewObject();
        Row row = view.getCurrentRow();

        String nameOfReport = "";
        String reportName = "" ;

        BigDecimal capacityType = new BigDecimal(row.getAttribute("CapacityType").toString());
        if (capacityType == new BigDecimal(1)) {
            nameOfReport = "Person";
            reportName = "????? ??????" ;
        } else if (capacityType == new BigDecimal(2)) {
            nameOfReport = "Authorization";
            reportName = "????? ??????" ;
        } else {
            nameOfReport = "AuthorizationOnAuthorization";
            reportName = "????? ??????" ;
        }
       
        try {
            super.print(pathReport,nameOfReport, "opposition" , reportName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            super.print(pathReport,"Implement", "opposition" , reportName);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void populateParamsMap(HashMap parametersMap) {
        int userId =
            Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") == null ? "802" :
                             ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());

        ViewObject view = ADFUtil.findIterator("ExcFolderProceduresView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        parametersMap.put("PROCEDURE_ID", new BigDecimal(row.getAttribute("Id").toString())); // 387
        parametersMap.put("userId", userId); // #{sessionScope. userId}
        parametersMap.put("RES_PARTY_ID", new BigDecimal(row.getAttribute("ResPartyId").toString()));
    }


    public void addLawyer(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            try {
                System.err.println(">>>>>>>>>>> inside Lawyer Commit");
                ADFUtil.refreshPage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
//                rollback();
                System.err.println(">>>>>>>>>>> inside Lawyer Rollback");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String lawyerId = ADFUtil.evaluateEL("#{sessionScope.lawyerId}") + "";
        System.out.println(">>>>>>>>>>>>>   lawyerId = " + lawyerId);
    }

    
    
    public void saveLawyer(ActionEvent actionEvent) {
        try {
            ParLawyerBean plb = new ParLawyerBean();
            plb.clickButton(actionEvent);
            lawyersPopup.hide();
            ADFUtil.refreshPage();            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void caneclPopup(ActionEvent actionEvent) {

        try {
            ADFUtil.refreshPage();

            lawyersPopup.hide();

            decisionPath = "rollback";
            BigDecimal lawyerId = (BigDecimal) ADFUtil.evaluateEL("#{sessionScope.lawyerId}");
            ADFUtil.setEL("#{bindings.PrimaryLawyerId.inputValue}", lawyerId);
            ADFUtil.refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private boolean validateHearingDate(String hearing){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
        
        Date hearingDate;
        try {
            hearingDate = sdf.parse(hearing);
            Date toDay = new Date() ;
            
            long toDayLong = toDay.getTime();
            long hearingDateLong = hearingDate.getTime();
            long dayLong = 24 * 60 * 60 * 100 ;
            System.out.println("toDayLong issssssssss " +  toDayLong) ;
            System.out.println("hearingDateLong isssssssss " + hearingDateLong);
            if(hearingDateLong > toDayLong){
                return true ;
            }                                    
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false ;
    }
    

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setPartyPopup(RichPopup partyPopup) {
        this.partyPopup = partyPopup;
    }

    public RichPopup getPartyPopup() {
        return partyPopup;
    }


    public void setLawyersPopup(RichPopup lawyersPopup) {
        this.lawyersPopup = lawyersPopup;
    }

    public RichPopup getLawyersPopup() {
        return lawyersPopup;
    }

    public void setDecisionPath(String decisionPath) {
        this.decisionPath = decisionPath;
    }

    public String getDecisionPath() {
        return decisionPath;
    }
}
