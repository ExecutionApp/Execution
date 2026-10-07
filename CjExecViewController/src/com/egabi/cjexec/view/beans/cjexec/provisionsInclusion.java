package com.egabi.cjexec.view.beans.cjexec;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.InputStream;

import java.math.BigDecimal;

import java.util.Date;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelAccordion;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.RowFinder;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class provisionsInclusion {

    private String policeNumber;
    private String caseYear;
    private String deletedTableName;
    private BigDecimal deletedId;

    public void setOperationNumber(oracle.jbo.domain.Number operationNumber) {
        this.operationNumber = operationNumber;
    }

    public oracle.jbo.domain.Number getOperationNumber() {
        return operationNumber;
    }
    private oracle.jbo.domain.Number operationNumber;

    public void setCaseYear(String caseYear) {
        this.caseYear = caseYear;
    }

    public String getCaseYear() {
        return caseYear;
    }
    private String idNumber;
    private String fullName;
    private oracle.jbo.domain.Number resId;
    private oracle.jbo.domain.Number hearingDateId;
    private oracle.jbo.domain.Number businessCaseId;
    private oracle.jbo.domain.Number degreeId;
    private String businessCaseNumber;
    private String businessCaseDegree;
    private String businessCaseName;
    private oracle.jbo.domain.Date hearingDate;

    private String mode;
    private UploadedFile file;

    private BigDecimal partyIdTF;
    private BigDecimal partyIdTFEdit;
    private RichShowDetailItem det;
    private RichPanelAccordion dett;

    public void setDegreeId(oracle.jbo.domain.Number degreeId) {
        this.degreeId = degreeId;
    }

    public oracle.jbo.domain.Number getDegreeId() {
        return degreeId;
    }

    public void setHearingDateId(oracle.jbo.domain.Number hearingDateId) {
        this.hearingDateId = hearingDateId;
    }

    public oracle.jbo.domain.Number getHearingDateId() {
        return hearingDateId;
    }

    public void setBusinessCaseId(oracle.jbo.domain.Number businessCaseId) {
        this.businessCaseId = businessCaseId;
    }

    public oracle.jbo.domain.Number getBusinessCaseId() {
        return businessCaseId;
    }

    public void setBusinessCaseNumber(String businessCaseNumber) {
        this.businessCaseNumber = businessCaseNumber;
    }

    public String getBusinessCaseNumber() {
        return businessCaseNumber;
    }

    public void setBusinessCaseName(String businessCaseName) {
        this.businessCaseName = businessCaseName;
    }

    public String getBusinessCaseName() {
        return businessCaseName;
    }

    public void setHearingDate(oracle.jbo.domain.Date hearingDate) {
        this.hearingDate = hearingDate;
    }

    public oracle.jbo.domain.Date getHearingDate() {
        return hearingDate;
    }

    public void setBusinessCaseDegree(String businessCaseDegree) {
        this.businessCaseDegree = businessCaseDegree;
    }

    public String getBusinessCaseDegree() {
        return businessCaseDegree;
    }

    public void setPartyIdTF(BigDecimal partyIdTF) {
        this.partyIdTF = partyIdTF;
    }

    public BigDecimal getPartyIdTF() {
        return partyIdTF;
    }

    public void setPartyIdTFEdit(BigDecimal partyIdTFEdit) {
        this.partyIdTFEdit = partyIdTFEdit;
    }

    public BigDecimal getPartyIdTFEdit() {
        return partyIdTFEdit;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFormCasePartyIdPK(BigDecimal formCasePartyIdPK) {
        this.formCasePartyIdPK = formCasePartyIdPK;
    }

    public BigDecimal getFormCasePartyIdPK() {
        return formCasePartyIdPK;
    }
    private BigDecimal formCasePartyIdPK;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setNationalId(String nationalId) {
        this.idNumber = nationalId;
    }

    public String getNationalId() {
        return idNumber;
    }

    public void setQuadrantName(String quadrantName) {
        this.fullName = quadrantName;
    }

    public String getQuadrantName() {
        return fullName;
    }

    public void setResId(oracle.jbo.domain.Number resId) {
        this.resId = resId;
    }

    public oracle.jbo.domain.Number getResId() {
        return resId;
    }

    public provisionsInclusion() {
        super();
    }

    public String search() {
        // Add event code here...
        oracle.jbo.domain.Number circleId = null;
        oracle.jbo.domain.Number businessCaseType = null;
        oracle.jbo.domain.Number businessCaseDegree = null;
        oracle.jbo.domain.Number hearingDateId = null;
        oracle.jbo.domain.Number originEntity = null;
        oracle.jbo.domain.Number classifiedFlag = null;

        Row row1 = ADFUtil.findIterator("EntityCircleRVO1Iterator").getCurrentRow();
        if (row1 != null) {
            circleId = (oracle.jbo.domain.Number) row1.getAttribute("Id");
        }

        Row row2 = ADFUtil.findIterator("SearchFormRVO1Iterator").getCurrentRow();
        if (row2 != null) {
            businessCaseType = (oracle.jbo.domain.Number) row2.getAttribute("BusinessCaseType");
            businessCaseDegree = (oracle.jbo.domain.Number) row2.getAttribute("BusinessCaseDegree");
            hearingDateId = (oracle.jbo.domain.Number) row2.getAttribute("CourtCircleHearingDateId");
            originEntity = (oracle.jbo.domain.Number) row2.getAttribute("OriginEntity");
            classifiedFlag = (oracle.jbo.domain.Number) row2.getAttribute("ClassifiedFlag");
        }

        ViewObject searchResultsVO = ADFUtil.findIterator("SearchResultsRVO1Iterator").getViewObject();
        searchResultsVO.setNamedWhereClauseParam("businessCaseTypeParam", businessCaseType);
        searchResultsVO.setNamedWhereClauseParam("businessCaseDegreeParam", businessCaseDegree);
        searchResultsVO.setNamedWhereClauseParam("circleIdParam", circleId);
        searchResultsVO.setNamedWhereClauseParam("fullNameParam", fullName);
        searchResultsVO.setNamedWhereClauseParam("hearingIdParam", hearingDateId);
        searchResultsVO.setNamedWhereClauseParam("idNumberParam", idNumber);
        searchResultsVO.setNamedWhereClauseParam("originEntityParam", originEntity);
        searchResultsVO.setNamedWhereClauseParam("policeNumberParam", policeNumber);
        searchResultsVO.setNamedWhereClauseParam("parYearParam", caseYear);
        searchResultsVO.setNamedWhereClauseParam("classifiedFlagParam", classifiedFlag);
        searchResultsVO.executeQuery();

        System.err.println("businessCaseType : " + businessCaseType + "\n - businessCaseDegree : " +
                           businessCaseDegree + "\n - fullName : " + fullName + "\n - hearingDateId : " +
                           hearingDateId + "\n - idNumber : " + idNumber + "\n - originEntity : " + originEntity +
                           "\n - policeNumber : " + policeNumber + "\n - circleId : " + circleId +
                           "\n - classifiedFlag : " + classifiedFlag + "\n - caseYear : " + caseYear);

        System.err.println("xx : " + searchResultsVO.getEstimatedRowCount());

        return null;
    }

    //    public String resetSearch() {
    //        Row row = ADFUtil.findIterator("SearchFormRVO1Iterator").getCurrentRow();
    //        if (row != null) {
    //            row.setAttribute("BusinessCaseType", null);
    //            row.setAttribute("BusinessCaseDegree", null);
    //            row.setAttribute("CourtCircleHearingDateId", null);
    //            row.setAttribute("OriginEntity", null);
    //            row.setAttribute("ClassifiedFlag", null);
    //        }
    //        setQuadrantName(null);
    //        setNationalId(null);
    //        setCaseId(null);
    //        ADFUtil.findIterator("SearchResultsRVO1Iterator").getViewObject().executeEmptyRowSet();
    //
    //        return null;
    //    }

    public static java.util.Date convertDomainDateToUtilDate(oracle.jbo.domain.Date domainDate) {
        java.util.Date date = null;
        if (domainDate != null) {
            java.sql.Date sqldate = domainDate.dateValue();
            date = new Date(sqldate.getTime());
        }
        return date;
    }


    public String newProvisions() {
        // Add event code here...
        System.err.println("resId_bean: new -- " + resId);
        System.err.println("hearingDateId_bean: new -- " + hearingDateId);
        System.err.println("businessCaseId_bean: new -- " + businessCaseId);
        System.err.println("degreeId_bean: new -- " + degreeId);

        setOperationNumber((oracle.jbo.domain.Number) ADFUtil.findOperation("getOperationNumber").execute());
        System.err.println("OperationNumber : " + operationNumber);

        setMode("new");

        System.err.println("session user : " + new BigDecimal(JSFUtils.getFromSession("userId").toString()));
        OperationBinding ob = ADFUtil.findOperation("copySessionFormCase");
        ob.getParamsMap().put("reservationId", resId);
        ob.getParamsMap().put("userId", new BigDecimal(JSFUtils.getFromSession("userId").toString()));
        ob.execute();

        return "new";
    }

    public String editProvisions() {
        // Add event code here...
        System.err.println("resId_bean: edit -- " + resId);
        System.err.println("hearingDateId_bean: edit -- " + hearingDateId);
        System.err.println("businessCaseId_bean: edit -- " + businessCaseId);

        setOperationNumber((oracle.jbo.domain.Number) ADFUtil.findOperation("getOperationNumber").execute());
        System.err.println("OperationNumber : " + operationNumber);

        setMode("edit");

        return "edit";
    }

    public String viewProvisions() {
        // Add event code here...
        System.err.println("resId_bean: view -- " + resId);
        System.err.println("hearingDateId_bean: view -- " + hearingDateId);
        System.err.println("businessCaseId_bean: view -- " + businessCaseId);

        setMode("view");

        return "view";
    }

    public String back() {

        Row row = ADFUtil.findIterator("SearchFormRVO1Iterator").getCurrentRow();
        if (row != null) {
            row.setAttribute("BusinessCaseType", null);
            row.setAttribute("BusinessCaseDegree", null);
            row.setAttribute("CourtCircleHearingDateId", null);
            row.setAttribute("OriginEntity", null);
            row.setAttribute("ClassifiedFlag", null);
        }
        setQuadrantName(null);
        setNationalId(null);
        setCaseYear(null);
        setPoliceNumber(null);
        ADFUtil.findIterator("SearchResultsRVO1Iterator").getViewObject().executeEmptyRowSet();

        //        OperationBinding ob = ADFUtil.findOperation("Rollback");
        //        if (ob != null) {
        //            ob.execute();
        //        }
        //        if (ob.getErrors().isEmpty()) {
        //            System.err.println("succes");
        //        } else {
        //            System.err.println("fail");
        //        }

        setMode(null);

        return "back";
    }

    public void hearingDateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Row row1 = ADFUtil.findIterator("SearchFormRVO1Iterator").getCurrentRow();
        oracle.jbo.domain.Number degreeId = null;
        oracle.jbo.domain.Number hearingDateId = null;
        if (row1 != null) {
            degreeId = (oracle.jbo.domain.Number) row1.getAttribute("BusinessCaseDegree");
            hearingDateId = (oracle.jbo.domain.Number) row1.getAttribute("CourtCircleHearingDateId");
        }

        Row row2 = ADFUtil.findIterator("EntityCircleRVO1Iterator").getCurrentRow();
        oracle.jbo.domain.Number entityId = null;
        if (row2 != null) {
            entityId = (oracle.jbo.domain.Number) row2.getAttribute("Id");
        }

        ViewObject ob1 = ADFUtil.findIterator("ProvisionedCasesInSession1Iterator").getViewObject();
        ViewObject ob2 = ADFUtil.findIterator("AllCasesInSessionRVO1Iterator").getViewObject();

        ob1.setNamedWhereClauseParam("degreeId", degreeId);
        ob1.setNamedWhereClauseParam("entityId", entityId);
        ob1.setNamedWhereClauseParam("hearingDateId", hearingDateId);

        ob2.setNamedWhereClauseParam("degreeId", degreeId);
        ob2.setNamedWhereClauseParam("entityId", entityId);
        ob2.setNamedWhereClauseParam("hearingDateId", hearingDateId);

        ob1.executeQuery();
        ob2.executeQuery();

        Row row4 = ADFUtil.findIterator("ProvisionedCasesInSession1Iterator").getCurrentRow();
        oracle.jbo.domain.Number c1 = null;
        if (row4 != null) {
            c1 = (oracle.jbo.domain.Number) row4.getAttribute("ProvisionedCasesCount");
        }

        Row row5 = ADFUtil.findIterator("AllCasesInSessionRVO1Iterator").getCurrentRow();
        oracle.jbo.domain.Number c2 = null;
        if (row5 != null) {
            c2 = (oracle.jbo.domain.Number) row5.getAttribute("AllCasesCount");
        }

        System.err.println(entityId + " -- " + hearingDateId + " -- " + degreeId + " -- " + c1 + " -- " + c2);

        RichOutputFormatted o1 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of1");
        RichOutputFormatted o2 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of2");
        RichOutputFormatted o3 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of3");
        RichOutputFormatted o4 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of4");
        RichOutputFormatted o5 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of5");
        RichOutputFormatted o6 =
            (RichOutputFormatted) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "of6");
        AdfFacesContext.getCurrentInstance().addPartialTarget(o1);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o3);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o4);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o5);
        AdfFacesContext.getCurrentInstance().addPartialTarget(o6);
    }

    public String saveInclusion() {
        // Add event code here...

        ViewObjectImpl sesVO =
            (ViewObjectImpl) ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getViewObject();
        Row sesRow = sesVO.getCurrentRow();
        boolean isFound = false;
        if (sesRow != null) {
            RowFinder rowFinder = (sesVO).lookupRowFinder("ExcCourtBcSessionJudgmentVORowFinder");
            NameValuePairs nvp = new NameValuePairs();
            nvp.setAttribute("ResIdParam", (BigDecimal) sesRow.getAttribute("CourtBcHearingResId"));
            RowIterator rowFound = rowFinder.execute(nvp, (sesVO).getDefaultRowSet());
            System.err.println("rowFound : " + rowFound);
            if (rowFound.hasNext()) {
                isFound = true;
            }
        }

        RowSetIterator reqAttachIter = ADFUtil.findIterator("ExcLkpSessionAttachView1Iterator").getRowSetIterator();
        Row reqAttachRow = reqAttachIter.first();
        boolean isNotFound = false;

        BigDecimal uploadStatus = null;
        if (reqAttachRow != null) {
            uploadStatus =
                (BigDecimal) (reqAttachRow.getAttribute("UploadStatus") == null ? new BigDecimal(0) :
                              reqAttachRow.getAttribute("UploadStatus"));

            System.err.println("uploadStatus : " + uploadStatus);
            if (uploadStatus != null && uploadStatus.equals(new BigDecimal(0))) {
                isNotFound = true;
            }
        }
        if (isNotFound == false) {
            while (reqAttachIter.hasNext()) {
                Row row = reqAttachIter.next();
                uploadStatus =
                    (BigDecimal) (row.getAttribute("UploadStatus") == null ? new BigDecimal(0) :
                                  row.getAttribute("UploadStatus"));
                System.err.println("uploadStatus : " + uploadStatus);
                if (uploadStatus != null && uploadStatus.equals(new BigDecimal(0))) {
                    isNotFound = true;
                    break;
                }
            }
        }

        System.err.println("isNotFound : " + isNotFound);
        if (isFound == true) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "PRE_PROVISIONED");
        } else if (isNotFound == true) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "REQUIRED_ATTACHMENTS_NOT_FOUND");
        } else {
            RowIterator iter = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getRowSetIterator();
            Row first = iter.first();
            Row row = null;
            if (first != null && first.getAttribute("JudgementType") == null) {
                System.err.println("first JudgementType res is null");
                first.setAttribute("JudgementType", new BigDecimal(1));
            }
            while (iter.hasNext()) {
                row = iter.next();
                if (row != null && row.getAttribute("JudgementType") == null) {
                    System.err.println("res JudgementType is null");
                    row.setAttribute("JudgementType", new BigDecimal(1));
                }
            }

            /** History - Update **/
            if (mode.equals("edit")) {
                Row sessionRow = ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getCurrentRow();
                if (sessionRow != null) {
                    OperationBinding ob2 = ADFUtil.findOperation("updateSessionHistory");
                    ob2.getParamsMap().put("operationNumber", operationNumber);
                    ob2.getParamsMap().put("sesResId", resId);
                    ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                    ob2.getParamsMap().put("sesJudType", sessionRow.getAttribute("JudgmentType"));
                    ob2.getParamsMap().put("sesJudSubType", sessionRow.getAttribute("JudgmentSubType"));
                    ob2.getParamsMap().put("sesFirstSessionId", sessionRow.getAttribute("FirstSessionId"));
                    ob2.getParamsMap().put("sesSecondSessionId", sessionRow.getAttribute("SecondSessionId"));
                    ob2.getParamsMap().put("sesCircleId", sessionRow.getAttribute("CircleId"));
                    ob2.getParamsMap().put("sesJudTypeToggleId", sessionRow.getAttribute("JudgementTypeToggle"));
                    ob2.getParamsMap().put("sesAttendance", sessionRow.getAttribute("Attendance"));
                    ob2.getParamsMap().put("sesJudgClass", sessionRow.getAttribute("JudgmentClassification"));
                    ob2.getParamsMap().put("sesJudgClassDetail", sessionRow.getAttribute("JudgementClassDetail"));
                    ob2.getParamsMap().put("sesResolution", sessionRow.getAttribute("JudgementResolution"));
                    ob2.execute();
                    if (ob2.getErrors().isEmpty()) {
                        System.err.println("history res insert succes XX");
                    } else {
                        System.err.println("history res insert fail XX");
                    }
                }

            } else if (mode.equals("edit") || mode.equals("new")) {

                RowIterator iterRes = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getRowSetIterator();
                Row firstRes = iterRes.first();
                Row rowRes = null;
                Row current = null;
                if (firstRes != null) {
                    ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").setCurrentRowWithKeyValue(firstRes.getAttribute("Id").toString());
                    current = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getCurrentRow();
                    if (current != null) {
                        OperationBinding ob2 = ADFUtil.findOperation("updateResHistory");
                        ob2.getParamsMap().put("operationNumber", operationNumber);
                        ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                        ob2.getParamsMap().put("resIdPk", current.getAttribute("Id"));
                        ob2.getParamsMap().put("resJudType", current.getAttribute("JudgementType"));
                        ob2.getParamsMap().put("resAttendance", current.getAttribute("Attendance"));
                        ob2.getParamsMap().put("resJudgClass", current.getAttribute("JudgmentClassification"));
                        ob2.getParamsMap().put("resJudgClassDetail", current.getAttribute("JudgementClassDetail"));
                        ob2.getParamsMap().put("resJudgResolution", current.getAttribute("JudgementResolutionC"));
                        ob2.execute();
                        if (ob2.getErrors().isEmpty()) {
                            System.err.println("history res insert succes XX");

                            Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
                            BigDecimal formCaseDescriptionId = null;
                            if (row1 != null) {
                                formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
                            }
                            System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

                            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
                            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
                            ob.getParamsMap().put("partyId", current.getAttribute("PartyId"));
                            BigDecimal formCasePartyId = (BigDecimal) ob.execute();
                            System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);

                            ViewObjectImpl desPartsVO =
                                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
                            if (formCasePartyId != null) {

                                ViewCriteria vc = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                                VariableValueManager vm = desPartsVO.ensureVariableManager();
                                vm.setVariableValue("partyIdParam", formCasePartyId);
                                desPartsVO.applyViewCriteria(vc);
                                desPartsVO.executeQuery();

                                /** Update - History **/
                                Row firstDesPartRow = desPartsVO.first();
                                if (firstDesPartRow != null) {

                                    if (firstDesPartRow.getAttribute("CreateFlag") != null &&
                                        firstDesPartRow.getAttribute("CreateFlag").equals("NEW")) {

                                        OperationBinding ob3 = ADFUtil.findOperation("insertDesPartHistory");
                                        ob3.getParamsMap().put("operationNumber", operationNumber);
                                        ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                        ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                        ob3.getParamsMap().put("formCaseDesId",
                                                               firstDesPartRow.getAttribute("FormCaseDesId"));
                                        ob3.getParamsMap().put("formCasePartyId",
                                                               firstDesPartRow.getAttribute("FormCasePartyId"));
                                        ob3.getParamsMap().put("desPartJudClass",
                                                               firstDesPartRow.getAttribute("JudgmentClassification"));
                                        ob3.getParamsMap().put("desPartJudClassDetail",
                                                               firstDesPartRow.getAttribute("JudgementClassDetail"));
                                        ob3.execute();

                                    } else {

                                        OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                        ob3.getParamsMap().put("operationNumber", operationNumber);
                                        ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                        ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                        ob3.getParamsMap().put("desPartJudgClass",
                                                               firstDesPartRow.getAttribute("JudgmentClassification"));
                                        ob3.getParamsMap().put("desPartJudgClassDetail",
                                                               firstDesPartRow.getAttribute("JudgementClassDetail"));
                                        ob3.execute();
                                    }
                                }
                                Row desPartRow = null;
                                while (desPartsVO.hasNext()) {
                                    desPartRow = desPartsVO.next();
                                    if (desPartRow.getAttribute("CreateFlag") != null &&
                                        desPartRow.getAttribute("CreateFlag").equals("NEW")) {

                                        OperationBinding ob3 = ADFUtil.findOperation("insertDesPartHistory");
                                        ob3.getParamsMap().put("operationNumber", operationNumber);
                                        ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                        ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                        ob3.getParamsMap().put("formCaseDesId",
                                                               desPartRow.getAttribute("FormCaseDesId"));
                                        ob3.getParamsMap().put("formCasePartyId",
                                                               desPartRow.getAttribute("FormCasePartyId"));
                                        ob3.getParamsMap().put("desPartJudClass",
                                                               desPartRow.getAttribute("JudgmentClassification"));
                                        ob3.getParamsMap().put("desPartJudClassDetail",
                                                               desPartRow.getAttribute("JudgementClassDetail"));
                                        ob3.execute();

                                    } else {

                                        OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                        ob3.getParamsMap().put("operationNumber", operationNumber);
                                        ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                        ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                        ob3.getParamsMap().put("desPartJudgClass",
                                                               desPartRow.getAttribute("JudgmentClassification"));
                                        ob3.getParamsMap().put("desPartJudgClassDetail",
                                                               desPartRow.getAttribute("JudgementClassDetail"));
                                        ob3.execute();
                                    }
                                }
                            }
                        } else {
                            System.err.println("history res insert fail XX");
                        }
                    }
                }
                while (iterRes.hasNext()) {
                    rowRes = iterRes.next();
                    if (rowRes != null) {
                        ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").setCurrentRowWithKeyValue(rowRes.getAttribute("Id").toString());
                        current = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getCurrentRow();
                        if (current != null) {
                            OperationBinding ob2 = ADFUtil.findOperation("updateResHistory");
                            ob2.getParamsMap().put("operationNumber", operationNumber);
                            ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                            ob2.getParamsMap().put("resIdPk", current.getAttribute("Id"));
                            ob2.getParamsMap().put("resJudType", current.getAttribute("JudgementType"));
                            ob2.getParamsMap().put("resAttendance", current.getAttribute("Attendance"));
                            ob2.getParamsMap().put("resJudgClass", current.getAttribute("JudgmentClassification"));
                            ob2.getParamsMap().put("resJudgClassDetail", current.getAttribute("JudgementClassDetail"));
                            ob2.getParamsMap().put("resJudgResolution", current.getAttribute("JudgementResolutionC"));
                            ob2.execute();
                            if (ob2.getErrors().isEmpty()) {
                                System.err.println("history res insert succes XX");
                                Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
                                BigDecimal formCaseDescriptionId = null;
                                if (row1 != null) {
                                    formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
                                }
                                System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

                                OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
                                ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
                                ob.getParamsMap().put("partyId", current.getAttribute("PartyId"));
                                BigDecimal formCasePartyId = (BigDecimal) ob.execute();
                                System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);

                                ViewObjectImpl desPartsVO =
                                    (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
                                if (formCasePartyId != null) {

                                    ViewCriteria vc =
                                        desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                                    VariableValueManager vm = desPartsVO.ensureVariableManager();
                                    vm.setVariableValue("partyIdParam", formCasePartyId);
                                    desPartsVO.applyViewCriteria(vc);
                                    desPartsVO.executeQuery();

                                    /** Update - History **/
                                    Row firstDesPartRow = desPartsVO.first();
                                    if (firstDesPartRow != null) {
                                        if (firstDesPartRow.getAttribute("CreateFlag") != null &&
                                            firstDesPartRow.getAttribute("CreateFlag").equals("NEW")) {

                                            OperationBinding ob3 = ADFUtil.findOperation("insertDesPartHistory");
                                            ob3.getParamsMap().put("operationNumber", operationNumber);
                                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                            ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                            ob3.getParamsMap().put("formCaseDesId",
                                                                   firstDesPartRow.getAttribute("FormCaseDesId"));
                                            ob3.getParamsMap().put("formCasePartyId",
                                                                   firstDesPartRow.getAttribute("FormCasePartyId"));
                                            ob3.getParamsMap().put("desPartJudClass",
                                                                   firstDesPartRow.getAttribute("JudgmentClassification"));
                                            ob3.getParamsMap().put("desPartJudClassDetail",
                                                                   firstDesPartRow.getAttribute("JudgementClassDetail"));
                                            ob3.execute();

                                        } else {
                                            OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                            ob3.getParamsMap().put("operationNumber", operationNumber);
                                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                            ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                            ob3.getParamsMap().put("desPartJudgClass",
                                                                   firstDesPartRow.getAttribute("JudgmentClassification"));
                                            ob3.getParamsMap().put("desPartJudgClassDetail",
                                                                   firstDesPartRow.getAttribute("JudgementClassDetail"));
                                            ob3.execute();
                                        }
                                    }
                                    Row desPartRow = null;
                                    while (desPartsVO.hasNext()) {
                                        desPartRow = desPartsVO.next();
                                        if (desPartRow.getAttribute("CreateFlag") != null &&
                                            desPartRow.getAttribute("CreateFlag").equals("NEW")) {

                                            OperationBinding ob3 = ADFUtil.findOperation("insertDesPartHistory");
                                            ob3.getParamsMap().put("operationNumber", operationNumber);
                                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                            ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                            ob3.getParamsMap().put("formCaseDesId",
                                                                   desPartRow.getAttribute("FormCaseDesId"));
                                            ob3.getParamsMap().put("formCasePartyId",
                                                                   desPartRow.getAttribute("FormCasePartyId"));
                                            ob3.getParamsMap().put("desPartJudClass",
                                                                   desPartRow.getAttribute("JudgmentClassification"));
                                            ob3.getParamsMap().put("desPartJudClassDetail",
                                                                   desPartRow.getAttribute("JudgementClassDetail"));
                                            ob3.execute();

                                        } else {
                                            OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                            ob3.getParamsMap().put("operationNumber", operationNumber);
                                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                            ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                            ob3.getParamsMap().put("desPartJudgClass",
                                                                   desPartRow.getAttribute("JudgmentClassification"));
                                            ob3.getParamsMap().put("desPartJudgClassDetail",
                                                                   desPartRow.getAttribute("JudgementClassDetail"));
                                            ob3.execute();
                                        }
                                    }
                                } else {
                                    System.err.println("history res insert fail XX");
                                }
                            }
                        }
                    }
                }


                /** NEW - Des - History **/
                RowIterator iterDes = ADFUtil.findIterator("FormCaseDescriptionDesView1Iterator").getRowSetIterator();
                Row firstDes = iterDes.first();
                Row rowDes = null;
                String des = null;
                if (firstDes != null) {
                    if (firstDes.getAttribute("CreateFlag") != null) {
                        des = firstDes.getAttribute("CreateFlag").toString();
                        if (des.equals("NEW")) {
                            OperationBinding ob3 = ADFUtil.findOperation("insertDesHistory");
                            ob3.getParamsMap().put("operationNumber", operationNumber);
                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                            ob3.getParamsMap().put("desIdPk", firstDes.getAttribute("Id"));
                            ob3.getParamsMap().put("formCaseDescId", firstDes.getAttribute("FormCaseDescriptionId"));
                            ob3.getParamsMap().put("desId", firstDes.getAttribute("DescriptionId"));
                            ob3.execute();
                        }
                    }
                    System.err.println("des-first : " + des);
                }
                while (iterDes.hasNext()) {
                    rowDes = iterDes.next();
                    if (rowDes.getAttribute("CreateFlag") != null) {
                        des = rowDes.getAttribute("CreateFlag").toString();
                        if (des.equals("NEW")) {
                            OperationBinding ob3 = ADFUtil.findOperation("insertDesHistory");
                            ob3.getParamsMap().put("operationNumber", operationNumber);
                            ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                            ob3.getParamsMap().put("desIdPk", rowDes.getAttribute("Id"));
                            ob3.getParamsMap().put("formCaseDescId", rowDes.getAttribute("FormCaseDescriptionId"));
                            ob3.getParamsMap().put("desId", rowDes.getAttribute("DescriptionId"));
                            ob3.execute();
                        }
                    }
                    System.err.println("des-row : " + des);
                }
            }

            OperationBinding ob = ADFUtil.findOperation("Commit");
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                if (mode.equals("new")) {
                    JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_INCLUSION");
                    ADFUtil.findOperation("Rollback").execute();
                    setMode(null);
                } else if (mode.equals("edit")) {
                    JSFUtils.showFacesInfoMessageFromBundle("bundle", "EDIT_INCLUSION");
                    setMode(null);
                }
            }

            System.err.println("resId insert XX : " + resId);
            OperationBinding ob1 = ADFUtil.findOperation("excFolderDataInsertion");
            ob1.getParamsMap().put("reservationId", resId);
            ob1.execute();

            if (ob1.getErrors().isEmpty()) {
                System.err.println("insert succes XX");
            } else {
                System.err.println("insert fail XX");
            }
            return "back";
        }
        return null;
    }

    public String deleteDesPart() {
        /** History - Delete **/
        Row desPartRow = ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getCurrentRow();

        OperationBinding ob1 = ADFUtil.findOperation("isDesPartChargesFound");
        ob1.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
        boolean found = (Boolean) ob1.execute();
        if (found == true) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "CHILD_ROW_FOUND");
        } else {

            OperationBinding ob2 = ADFUtil.findOperation("deleteDesPartHistory");

            ob2.getParamsMap().put("operationNumber", operationNumber);
            ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
            if (desPartRow != null) {
                System.err.println("desPartRow : " + desPartRow + " - id : " + desPartRow.getAttribute("Id"));
                ob2.getParamsMap().put("deletedId", desPartRow.getAttribute("Id"));
            }

            ob2.execute();
            if (ob2.getErrors().isEmpty()) {
                System.err.println("history delete des part succes XX - " + desPartRow.getAttribute("Id"));
            } else {
                System.err.println("history delete des part fail XX - " + desPartRow.getAttribute("Id"));
            }

            ADFUtil.findOperation("Delete2").execute();
        }

        return null;
    }

    public String deleteDes() {
        /** History - Delete **/
        Row desRow = ADFUtil.findIterator("FormCaseDescriptionDesView1Iterator").getCurrentRow();

        ViewObjectImpl desPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView3Iterator").getViewObject();

        RowFinder rowFinder = (desPartsVO).lookupRowFinder("FormCaseDescriptionDesPartRowFinder");
        NameValuePairs nvp = new NameValuePairs();
        nvp.setAttribute("desIdParam", (oracle.jbo.domain.Number) desRow.getAttribute("Id"));

        RowIterator rowFound = rowFinder.execute(nvp, (desPartsVO).getDefaultRowSet());
        System.err.println("rowFound : " + rowFound);
        Row roww1 = null;
        if (rowFound.hasNext()) {
            roww1 = rowFound.next();
            System.err.println("row WW -- " + roww1 + " -- " + roww1.getAttribute("FormCaseDesId"));
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "CHILD_ROW_FOUND");
        } else {

            OperationBinding ob2 = ADFUtil.findOperation("deleteDesHistory");

            ob2.getParamsMap().put("operationNumber", operationNumber);
            ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
            if (desRow != null) {
                System.err.println("desRow : " + desRow + " - id : " + desRow.getAttribute("Id"));
                ob2.getParamsMap().put("deletedId", desRow.getAttribute("Id"));
            }

            ob2.execute();
            if (ob2.getErrors().isEmpty()) {
                System.err.println("history delete des succes XX - " + desRow.getAttribute("Id"));
            } else {
                System.err.println("history delete des fail XX - " + desRow.getAttribute("Id"));
            }

            ADFUtil.findOperation("Delete1").execute();
        }

        return null;
    }

    public String saveAndInclose() {

        ViewObjectImpl sesVO =
            (ViewObjectImpl) ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getViewObject();
        Row sesRow = sesVO.getCurrentRow();
        boolean isFound = false;
        if (sesRow != null) {
            RowFinder rowFinder = (sesVO).lookupRowFinder("ExcCourtBcSessionJudgmentVORowFinder");
            NameValuePairs nvp = new NameValuePairs();
            nvp.setAttribute("ResIdParam", (BigDecimal) sesRow.getAttribute("CourtBcHearingResId"));
            RowIterator rowFound = rowFinder.execute(nvp, (sesVO).getDefaultRowSet());
            System.err.println("rowFound : " + rowFound);
            if (rowFound.hasNext()) {
                isFound = true;
            }
        }

        RowSetIterator reqAttachIter = ADFUtil.findIterator("ExcLkpSessionAttachView1Iterator").getRowSetIterator();
        Row reqAttachRow = reqAttachIter.first();
        boolean isNotFound = false;

        BigDecimal uploadStatus = null;
        if (reqAttachRow != null) {
            uploadStatus =
                (BigDecimal) (reqAttachRow.getAttribute("UploadStatus") == null ? new BigDecimal(0) :
                              reqAttachRow.getAttribute("UploadStatus"));
            System.err.println("uploadStatus : " + uploadStatus);
            if (uploadStatus != null && uploadStatus.equals(new BigDecimal(0))) {
                isNotFound = true;
            }
        }
        if (isNotFound == false) {
            while (reqAttachIter.hasNext()) {
                Row row = reqAttachIter.next();
                uploadStatus =
                    (BigDecimal) (row.getAttribute("UploadStatus") == null ? new BigDecimal(0) :
                                  row.getAttribute("UploadStatus"));
                System.err.println("uploadStatus : " + uploadStatus);
                if (uploadStatus != null && uploadStatus.equals(new BigDecimal(0))) {
                    isNotFound = true;
                    break;
                }
            }
        }

        System.err.println("isNotFound : " + isNotFound);
        if (isFound == true) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "PRE_PROVISIONED");
        } else if (isNotFound == true) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "REQUIRED_ATTACHMENTS_NOT_FOUND");
        } else {
            RowIterator iter = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getRowSetIterator();
            Row first = iter.first();
            Row row = null;
            if (first != null && first.getAttribute("JudgementType") == null) {
                System.err.println("first JudgementType res is null");
                first.setAttribute("JudgementType", new BigDecimal(1));
            }
            while (iter.hasNext()) {
                row = iter.next();
                if (row != null && row.getAttribute("JudgementType") == null) {
                    System.err.println("res JudgementType is null");
                    row.setAttribute("JudgementType", new BigDecimal(1));
                }
            }

            /** History - Update **/

            Row sessionRow = ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getCurrentRow();
            if (sessionRow != null) {
                OperationBinding ob2 = ADFUtil.findOperation("updateSessionHistory");
                ob2.getParamsMap().put("operationNumber", operationNumber);
                ob2.getParamsMap().put("sesResId", resId);
                ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                ob2.getParamsMap().put("sesJudType", sessionRow.getAttribute("JudgmentType"));
                ob2.getParamsMap().put("sesJudSubType", sessionRow.getAttribute("JudgmentSubType"));
                ob2.getParamsMap().put("sesFirstSessionId", sessionRow.getAttribute("FirstSessionId"));
                ob2.getParamsMap().put("sesSecondSessionId", sessionRow.getAttribute("SecondSessionId"));
                ob2.getParamsMap().put("sesCircleId", sessionRow.getAttribute("CircleId"));
                ob2.getParamsMap().put("sesJudTypeToggleId", sessionRow.getAttribute("JudgementTypeToggle"));
                ob2.getParamsMap().put("sesAttendance", sessionRow.getAttribute("Attendance"));
                ob2.getParamsMap().put("sesJudgClass", sessionRow.getAttribute("JudgmentClassification"));
                ob2.getParamsMap().put("sesJudgClassDetail", sessionRow.getAttribute("JudgementClassDetail"));
                ob2.getParamsMap().put("sesResolution", sessionRow.getAttribute("JudgementResolution"));
                ob2.execute();
                if (ob2.getErrors().isEmpty()) {
                    System.err.println("history res insert succes XX");
                } else {
                    System.err.println("history res insert fail XX");
                }
            }

            RowIterator iterRes = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getRowSetIterator();
            Row firstRes = iterRes.first();
            Row rowRes = null;
            Row current = null;
            if (firstRes != null) {
                ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").setCurrentRowWithKeyValue(firstRes.getAttribute("Id").toString());
                current = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getCurrentRow();
                if (current != null) {
                    OperationBinding ob2 = ADFUtil.findOperation("updateResHistory");
                    ob2.getParamsMap().put("operationNumber", operationNumber);
                    ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                    ob2.getParamsMap().put("resIdPk", current.getAttribute("Id"));
                    ob2.getParamsMap().put("resJudType", current.getAttribute("JudgementType"));
                    ob2.getParamsMap().put("resAttendance", current.getAttribute("Attendance"));
                    ob2.getParamsMap().put("resJudgClass", current.getAttribute("JudgmentClassification"));
                    ob2.getParamsMap().put("resJudgClassDetail", current.getAttribute("JudgementClassDetail"));
                    ob2.getParamsMap().put("resJudgResolution", current.getAttribute("JudgementResolutionC"));
                    ob2.execute();
                    if (ob2.getErrors().isEmpty()) {
                        System.err.println("history res insert succes XX");

                        Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
                        BigDecimal formCaseDescriptionId = null;
                        if (row1 != null) {
                            formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
                        }
                        System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

                        OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
                        ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
                        ob.getParamsMap().put("partyId", current.getAttribute("PartyId"));
                        BigDecimal formCasePartyId = (BigDecimal) ob.execute();
                        System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);

                        ViewObjectImpl desPartsVO =
                            (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
                        if (formCasePartyId != null) {

                            ViewCriteria vc = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                            VariableValueManager vm = desPartsVO.ensureVariableManager();
                            vm.setVariableValue("partyIdParam", formCasePartyId);
                            desPartsVO.applyViewCriteria(vc);
                            desPartsVO.executeQuery();

                            /** Update - History **/
                            Row firstDesPartRow = desPartsVO.first();
                            if (firstDesPartRow != null) {
                                OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                ob3.getParamsMap().put("operationNumber", operationNumber);
                                ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                ob3.getParamsMap().put("desPartJudgClass",
                                                       firstDesPartRow.getAttribute("JudgmentClassification"));
                                ob3.getParamsMap().put("desPartJudgClassDetail",
                                                       firstDesPartRow.getAttribute("JudgementClassDetail"));
                                ob3.execute();
                            }
                            Row desPartRow = null;
                            while (desPartsVO.hasNext()) {
                                desPartRow = desPartsVO.next();
                                OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                ob3.getParamsMap().put("operationNumber", operationNumber);
                                ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                ob3.getParamsMap().put("desPartJudgClass",
                                                       desPartRow.getAttribute("JudgmentClassification"));
                                ob3.getParamsMap().put("desPartJudgClassDetail",
                                                       desPartRow.getAttribute("JudgementClassDetail"));
                                ob3.execute();
                            }
                        }
                    } else {
                        System.err.println("history res insert fail XX");
                    }
                }
            }
            while (iterRes.hasNext()) {
                rowRes = iterRes.next();
                if (rowRes != null) {
                    ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").setCurrentRowWithKeyValue(rowRes.getAttribute("Id").toString());
                    current = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getCurrentRow();
                    if (current != null) {
                        OperationBinding ob2 = ADFUtil.findOperation("updateResHistory");
                        ob2.getParamsMap().put("operationNumber", operationNumber);
                        ob2.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                        ob2.getParamsMap().put("resIdPk", current.getAttribute("Id"));
                        ob2.getParamsMap().put("resJudType", current.getAttribute("JudgementType"));
                        ob2.getParamsMap().put("resAttendance", current.getAttribute("Attendance"));
                        ob2.getParamsMap().put("resJudgClass", current.getAttribute("JudgmentClassification"));
                        ob2.getParamsMap().put("resJudgClassDetail", current.getAttribute("JudgementClassDetail"));
                        ob2.getParamsMap().put("resJudgResolution", current.getAttribute("JudgementResolutionC"));
                        ob2.execute();
                        if (ob2.getErrors().isEmpty()) {
                            System.err.println("history res insert succes XX");
                            Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
                            BigDecimal formCaseDescriptionId = null;
                            if (row1 != null) {
                                formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
                            }
                            System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

                            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
                            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
                            ob.getParamsMap().put("partyId", current.getAttribute("PartyId"));
                            BigDecimal formCasePartyId = (BigDecimal) ob.execute();
                            System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);

                            ViewObjectImpl desPartsVO =
                                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
                            if (formCasePartyId != null) {

                                ViewCriteria vc = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                                VariableValueManager vm = desPartsVO.ensureVariableManager();
                                vm.setVariableValue("partyIdParam", formCasePartyId);
                                desPartsVO.applyViewCriteria(vc);
                                desPartsVO.executeQuery();

                                /** Update - History **/
                                Row firstDesPartRow = desPartsVO.first();
                                if (firstDesPartRow != null) {
                                    OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                    ob3.getParamsMap().put("operationNumber", operationNumber);
                                    ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                    ob3.getParamsMap().put("desPartIdPk", firstDesPartRow.getAttribute("Id"));
                                    ob3.getParamsMap().put("desPartJudgClass",
                                                           firstDesPartRow.getAttribute("JudgmentClassification"));
                                    ob3.getParamsMap().put("desPartJudgClassDetail",
                                                           firstDesPartRow.getAttribute("JudgementClassDetail"));
                                    ob3.execute();
                                }
                                Row desPartRow = null;
                                while (desPartsVO.hasNext()) {
                                    desPartRow = desPartsVO.next();
                                    OperationBinding ob3 = ADFUtil.findOperation("updateDesPartHistory");
                                    ob3.getParamsMap().put("operationNumber", operationNumber);
                                    ob3.getParamsMap().put("userId", JSFUtils.getFromSession("userId"));
                                    ob3.getParamsMap().put("desPartIdPk", desPartRow.getAttribute("Id"));
                                    ob3.getParamsMap().put("desPartJudgClass",
                                                           desPartRow.getAttribute("JudgmentClassification"));
                                    ob3.getParamsMap().put("desPartJudgClassDetail",
                                                           desPartRow.getAttribute("JudgementClassDetail"));
                                    ob3.execute();
                                }
                            } else {
                                System.err.println("history res insert fail XX");
                            }
                        }
                    }
                }
            }

            OperationBinding ob = ADFUtil.findOperation("Commit");
            ob.execute();
            if (ob.getErrors().isEmpty()) {
                // JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_INCLUSION");
                System.err.println("Inclusion Done");
                return "inclose";
            } else {
                System.err.println("an error occured in inclusion");
            }

            System.err.println("resId insert XX : " + resId);
            OperationBinding ob1 = ADFUtil.findOperation("excFolderDataInsertion");
            ob1.getParamsMap().put("reservationId", resId);
            ob1.execute();

            if (ob1.getErrors().isEmpty()) {
                System.err.println("insert succes XX");
            } else {
                System.err.println("insert fail XX");
            }

        }

        return null;
    }

    public void judgmentSubTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        RichSelectOneChoice circle =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc1");
        circle.setValue(null);
    }

    public String addAttachment() {
        // Add event code here...
        ADFUtil.findOperation("CreateInsert2").execute();
        return null;
    }

    public String deleteAttachment() {

        BigDecimal attachmentType = null;
        Row row1 = ADFUtil.findIterator("ExcCourtBcSessionAttachView2Iterator").getCurrentRow();
        System.err.println("ExcCourtBcSessionAttachView2Iterator -- row : " + row1 + " -- " +
                           row1.getAttribute("AttachmentType"));
        if (row1 != null) {
            attachmentType = (BigDecimal) row1.getAttribute("AttachmentType");
            if (attachmentType != null) {
                ViewObject sessionAttachVO = ADFUtil.findIterator("ExcLkpSessionAttachView1Iterator").getViewObject();
                RowFinder rowFinder = ((ViewObjectImpl) sessionAttachVO).lookupRowFinder("sessionAttachRowFinder");
                NameValuePairs nvp = new NameValuePairs();
                nvp.setAttribute("attachmentTypeParam", attachmentType);
                RowIterator rowFound = rowFinder.execute(nvp, ((ViewObjectImpl) sessionAttachVO).getDefaultRowSet());
                // Row row2 = null;
                if (rowFound.hasNext()) {
                    System.err.println("can't delete - that attachment found as required - " + attachmentType);
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "REQUIRED_ATTCH_DELETION");
                    // row2 = rowFound.next();
                    // System.err.println("row2 -- attach*lkp -- " + row2 + " -- " + row2.getAttribute("AttachmentType"));
                    // row2.setAttribute("UploadStatus", new BigDecimal(0));
                } else {
                    ADFUtil.findOperation("Delete3").execute();
                }
            }
        }
        return null;
    }

    public void uploadFile(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("upload file vcl ...");
        UploadedFile uf1 = (UploadedFile) valueChangeEvent.getNewValue();
        //        System.err.println("uf1 : " + uf1 + "\n -- " + uf1.getFilename());
        //value="#{pageFlowScope.provisionsInclusionBean.file}"
        InputStream is = null;
        String documentId = null;

        BigDecimal attachmentType = null;
        Row row1 = ADFUtil.findIterator("ExcCourtBcSessionAttachView2Iterator").getCurrentRow();
        System.err.println("ExcCourtBcSessionAttachView2Iterator -- row : " + row1 + " -- " +
                           row1.getAttribute("AttachmentType"));
        RichInputFile ff =
            (RichInputFile) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "if1");
        if (row1 != null) {
            attachmentType = (BigDecimal) row1.getAttribute("AttachmentType");
            if (attachmentType != null) {
                ff.setValue(uf1.getFilename());
                AdfFacesContext.getCurrentInstance().addPartialTarget(ff);
                try {
                    is = uf1.getInputStream();
                    System.err.println("is -- " + is);
                    documentId = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, uf1.getFilename()).get("dID");;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (documentId != null) {
                        System.err.println("documentId : " + documentId);
                        row1.setAttribute("DocumentId", documentId);

                        ViewObject sessionAttachVO =
                            ADFUtil.findIterator("ExcLkpSessionAttachView1Iterator").getViewObject();
                        RowFinder rowFinder =
                            ((ViewObjectImpl) sessionAttachVO).lookupRowFinder("sessionAttachRowFinder");
                        NameValuePairs nvp = new NameValuePairs();
                        nvp.setAttribute("attachmentTypeParam", attachmentType);
                        RowIterator rowFound =
                            rowFinder.execute(nvp, ((ViewObjectImpl) sessionAttachVO).getDefaultRowSet());
                        Row row2 = null;
                        while (rowFound.hasNext()) {
                            System.err.println("row found - " + attachmentType);
                            row2 = rowFound.next();
                            System.err.println("row2 -- attach*lkp -- " + row2 + " -- " +
                                               row2.getAttribute("AttachmentType"));
                            row2.setAttribute("UploadStatus", new BigDecimal(1));
                        }
                    } else {
                        System.err.println("file doesn't uploaded, please check UCM server status");
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "UCM");
                    }
                }
            } else {
                System.err.println("attachmentType required***");
                ff.setValue(null);
                AdfFacesContext.getCurrentInstance().addPartialTarget(ff);
            }
        }
    }

    public String toggleOffSession() {
        System.err.println("off");

        Row row = ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getCurrentRow();
        if (row != null) {
            row.setAttribute("Attendance", null);
            row.setAttribute("JudgmentClassification", null);
            row.setAttribute("JudgementClassDetail", null);
        }

        ViewObjectImpl resPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject();
        ViewCriteria vc = resPartsVO.getViewCriteria("CourtBcHearingResPartiesVOCriteria");
        VariableValueManager vm = resPartsVO.ensureVariableManager();
        vm.setVariableValue("ResIdParam", resId);
        resPartsVO.applyViewCriteria(vc);
        resPartsVO.executeQuery();

        System.err.println("count parties : " +
                           ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getEstimatedRowCount());

        Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
        BigDecimal formCaseDescriptionId = null;
        if (row1 != null) {
            formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
        }
        System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

        RowIterator ResPartsIter = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getRowSetIterator();
        Row firstResParts = ResPartsIter.first();
        Row rowResParts = null;
        BigDecimal partyId = null;
        if (firstResParts != null) {
            firstResParts.setAttribute("Attendance", null);
            firstResParts.setAttribute("JudgmentClassification", null);
            firstResParts.setAttribute("JudgementClassDetail", null);
            firstResParts.setAttribute("JudgementType", null);

            partyId = (BigDecimal) firstResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", null);
                desPartsFirst.setAttribute("JudgementClassDetail", null);
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", null);
                desPartsFirst.setAttribute("JudgementClassDetail", null);
            }
        }

        while (ResPartsIter.hasNext()) {
            rowResParts = ResPartsIter.next();
            rowResParts.setAttribute("Attendance", null);
            rowResParts.setAttribute("JudgmentClassification", null);
            rowResParts.setAttribute("JudgementClassDetail", null);
            rowResParts.setAttribute("JudgementType", null);

            partyId = (BigDecimal) rowResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", null);
                desPartsFirst.setAttribute("JudgementClassDetail", null);
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", null);
                desPartsRow.setAttribute("JudgementClassDetail", null);
            }
        }
        return null;
    }

    public String toggleOnSession() {
        System.err.println("on");
        Row row = ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getCurrentRow();
        if (row != null) {
            row.setAttribute("Attendance", null);
            row.setAttribute("JudgmentClassification", null);
            row.setAttribute("JudgementClassDetail", null);
        }

        ViewObjectImpl resPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject();
        ViewCriteria vc = resPartsVO.getViewCriteria("CourtBcHearingResPartiesVOCriteria");
        VariableValueManager vm = resPartsVO.ensureVariableManager();
        vm.setVariableValue("ResIdParam", resId);
        resPartsVO.applyViewCriteria(vc);
        resPartsVO.executeQuery();

        System.err.println("count parties : " +
                           ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getEstimatedRowCount());

        Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
        BigDecimal formCaseDescriptionId = null;
        if (row1 != null) {
            formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
        }
        System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

        RowIterator ResPartsIter = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getRowSetIterator();
        Row firstResParts = ResPartsIter.first();
        Row rowResParts = null;
        BigDecimal partyId = null;
        if (firstResParts != null) {
            firstResParts.setAttribute("Attendance", null);
            firstResParts.setAttribute("JudgmentClassification", null);
            firstResParts.setAttribute("JudgementClassDetail", null);
            firstResParts.setAttribute("JudgementType", null);

            partyId = (BigDecimal) firstResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", null);
                desPartsFirst.setAttribute("JudgementClassDetail", null);
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", null);
                desPartsRow.setAttribute("JudgementClassDetail", null);
            }
        }

        while (ResPartsIter.hasNext()) {
            rowResParts = ResPartsIter.next();
            rowResParts.setAttribute("Attendance", null);
            rowResParts.setAttribute("JudgmentClassification", null);
            rowResParts.setAttribute("JudgementClassDetail", null);
            rowResParts.setAttribute("JudgementType", null);

            partyId = (BigDecimal) rowResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", null);
                desPartsFirst.setAttribute("JudgementClassDetail", null);
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", null);
                desPartsRow.setAttribute("JudgementClassDetail", null);
            }
        }

        return null;
    }

    public void attendanceVCLSession(ValueChangeEvent valueChangeEvent) {

        ViewObjectImpl resPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject();
        ViewCriteria vc = resPartsVO.getViewCriteria("CourtBcHearingResPartiesVOCriteria");
        VariableValueManager vm = resPartsVO.ensureVariableManager();
        vm.setVariableValue("ResIdParam", resId);
        resPartsVO.applyViewCriteria(vc);
        resPartsVO.executeQuery();

        System.err.println("count parties : " +
                           ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getEstimatedRowCount());

        RowIterator ResPartsIter = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getRowSetIterator();
        Row firstResParts = ResPartsIter.first();
        Row rowResParts = null;
        if (firstResParts != null) {
            firstResParts.setAttribute("Attendance", valueChangeEvent.getNewValue());
        }
        while (ResPartsIter.hasNext()) {
            rowResParts = ResPartsIter.next();
            rowResParts.setAttribute("Attendance", valueChangeEvent.getNewValue());
        }

    }

    public void judgmentClassificationVCLSession(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ViewObjectImpl resPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject();
        ViewCriteria vc = resPartsVO.getViewCriteria("CourtBcHearingResPartiesVOCriteria");
        VariableValueManager vm = resPartsVO.ensureVariableManager();
        vm.setVariableValue("ResIdParam", resId);
        resPartsVO.applyViewCriteria(vc);
        resPartsVO.executeQuery();

        System.err.println("count parties : " +
                           ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getEstimatedRowCount());

        Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
        BigDecimal formCaseDescriptionId = null;
        if (row1 != null) {
            formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
        }
        System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

        RowIterator ResPartsIter = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getRowSetIterator();
        Row firstResParts = ResPartsIter.first();
        Row rowResParts = null;
        BigDecimal partyId = null;
        if (firstResParts != null) {
            firstResParts.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            partyId = (BigDecimal) firstResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
        }

        while (ResPartsIter.hasNext()) {
            rowResParts = ResPartsIter.next();
            rowResParts.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());

            partyId = (BigDecimal) rowResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
        }
    }

    public void judgmentClassificationDetailVCLSession(ValueChangeEvent valueChangeEvent) {
        ViewObjectImpl resPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject();
        ViewCriteria vc = resPartsVO.getViewCriteria("CourtBcHearingResPartiesVOCriteria");
        VariableValueManager vm = resPartsVO.ensureVariableManager();
        vm.setVariableValue("ResIdParam", resId);
        resPartsVO.applyViewCriteria(vc);
        resPartsVO.executeQuery();

        System.err.println("count parties : " +
                           ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getEstimatedRowCount());

        Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
        BigDecimal formCaseDescriptionId = null;
        if (row1 != null) {
            formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
        }
        System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

        RowIterator ResPartsIter = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getRowSetIterator();
        Row firstResParts = ResPartsIter.first();
        Row rowResParts = null;
        BigDecimal partyId = null;
        if (firstResParts != null) {
            firstResParts.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
            partyId = (BigDecimal) firstResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
            }
        }

        while (ResPartsIter.hasNext()) {
            rowResParts = ResPartsIter.next();
            rowResParts.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());

            partyId = (BigDecimal) rowResParts.getAttribute("PartyId");
            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {
                ViewCriteria vc1 = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm1 = desPartsVO.ensureVariableManager();
                vm1.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc1);
                desPartsVO.executeQuery();

                System.err.println("count des part 1 : " +
                                   ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getEstimatedRowCount());
            }

            RowIterator desPartsIter =
                ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
            Row desPartsFirst = desPartsIter.first();
            Row desPartsRow = null;
            if (desPartsFirst != null) {
                desPartsFirst.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
            }
            while (desPartsIter.hasNext()) {
                desPartsRow = desPartsIter.next();
                desPartsRow.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
            }
        }
    }


    public String toggleOff() {
        Row row = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow();
        if (row != null) {
            row.setAttribute("JudgmentClassification", null);
            row.setAttribute("JudgementClassDetail", null);
        }

        return null;
    }

    public String toggleOn() {
        RowIterator iter = ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
        Row first = iter.first();
        Row row = null;
        if (first != null) {
            first.setAttribute("JudgmentClassification", null);
            first.setAttribute("JudgementClassDetail", null);
        }
        while (iter.hasNext()) {
            row = iter.next();
            row.setAttribute("JudgmentClassification", null);
            row.setAttribute("JudgementClassDetail", null);
        }

        return null;
    }

    public void judgmentClassificationVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row cur = ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow();
        if (cur != null) {
            cur.setAttribute("JudgementResolutionC", null);
        }

        RowIterator iter = ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
        Row first = iter.first();
        Row row = null;
        if (first != null) {
            if (valueChangeEvent.getNewValue() == null) {
                first.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
                first.setAttribute("JudgementClassDetail", null);
            } else {
                first.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
        }
        while (iter.hasNext()) {
            row = iter.next();
            if (valueChangeEvent.getNewValue() == null) {
                row.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
                row.setAttribute("JudgementClassDetail", null);
            } else {
                row.setAttribute("JudgmentClassification", valueChangeEvent.getNewValue());
            }
        }
    }

    public void judgmentClassificationDetailVCL(ValueChangeEvent valueChangeEvent) {
        RowIterator iter = ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getRowSetIterator();
        Row first = iter.first();
        Row row = null;
        if (first != null) {
            first.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
        }
        while (iter.hasNext()) {
            row = iter.next();
            row.setAttribute("JudgementClassDetail", valueChangeEvent.getNewValue());
        }
    }

    public String addDesPart() {
        // Add event code here...
        oracle.jbo.domain.Number desId = null;
        Row roww = ADFUtil.findIterator("FormCaseDescriptionDesView1Iterator").getCurrentRow();
        if (roww != null) {
            desId = (oracle.jbo.domain.Number) roww.getAttribute("Id");
        }
        System.err.println("descriptionId xe : " + desId);

        ViewObjectImpl desPartsVO =
            (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
        RowFinder rowFinder = (desPartsVO).lookupRowFinder("FormCaseDescriptionDesPartRowFinder");
        NameValuePairs nvp = new NameValuePairs();
        nvp.setAttribute("desIdParam", desId);

        RowIterator rowFound = rowFinder.execute(nvp, (desPartsVO).getDefaultRowSet());
        System.err.println("rowFound : " + rowFound);
        Row roww1 = null;
        if (rowFound.hasNext()) {
            roww1 = rowFound.next();
            System.err.println("row -- " + roww1 + " -- " + roww1.getAttribute("FormCaseDesId"));
        } else {
            Row row = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
            BigDecimal formCaseDescriptionId = null;
            if (row != null) {
                formCaseDescriptionId = (BigDecimal) row.getAttribute("Id");
                System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);
            }
            Row row1 = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getCurrentRow();
            BigDecimal partyId = null;
            BigDecimal judgementType = null;
            BigDecimal judgmentClassification = null;
            BigDecimal judgementClassDetail = null;
            if (row1 != null) {
                partyId = (BigDecimal) row1.getAttribute("PartyId");
                judgementType =
                    (BigDecimal) (row1.getAttribute("JudgementType") == null ? new BigDecimal(1) :
                                  row1.getAttribute("JudgementType"));
                judgmentClassification = (BigDecimal) row1.getAttribute("JudgmentClassification");
                judgementClassDetail = (BigDecimal) row1.getAttribute("JudgementClassDetail");

                System.err.println("partyId : " + partyId + " -- judgementType : " + judgementType +
                                   " -- judgmentClassification : " + judgmentClassification +
                                   " -- judgementClassDetail : " + judgementClassDetail);
            }

            Row row2 = ADFUtil.findIterator("ExcCourtBcSessionJudgmentVO1Iterator").getCurrentRow();
            BigDecimal judgementTypeSession = null;
            if (row2 != null) {
                judgementTypeSession = (BigDecimal) row2.getAttribute("JudgementTypeToggle");
            }
            System.err.println("judgementTypeSession : " + judgementTypeSession);

            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();
            System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);
            Row row3 = ADFUtil.findIterator("FormCaseDescriptionDesView1Iterator").getCurrentRow();
            if (row3 != null) {
                OperationBinding ob1 = ADFUtil.findOperation("CreateWithParams");
                ob1.getParamsMap().put("FormCasePartyId", formCasePartyId);
                ob1.getParamsMap().put("FormCaseDesId", new BigDecimal(row3.getAttribute("Id").toString()));
                ob1.getParamsMap().put("FormCaseDes", (BigDecimal) row3.getAttribute("DescriptionId"));
                if ((judgementType != null && judgementType.equals(new BigDecimal(1))) ||
                    (judgementTypeSession != null && judgementTypeSession.equals(new BigDecimal(2)))) {
                    ob1.getParamsMap().put("JudgmentClassification", judgmentClassification);
                    ob1.getParamsMap().put("JudgementClassDetail", judgementClassDetail);
                }
                ob1.execute();
            } else {
                //JSFUtils.showFacesErrorMessageFromBundle("bundle", "SELECT_DES_FIRST");
            }
        }
        hidePopup("p2");

        return null;
    }

    public void accordionDisclosure(DisclosureEvent disclosureEvent) {
        // Add event code here...
        BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{party.Id}");
        if (disclosureEvent.isExpanded()) {
            System.err.println("id hh : " + id);
            ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").setCurrentRowWithKeyValue(id.toString());

            setPartyIdTF((BigDecimal) JSFUtils.resolveExpression("#{party.PartyId}"));
            setPartyIdTFEdit((BigDecimal) JSFUtils.resolveExpression("#{party.PartyId}"));

            Row row1 = ADFUtil.findIterator("FormCaseDescriptionView1Iterator").getCurrentRow();
            BigDecimal formCaseDescriptionId = null;
            if (row1 != null) {
                formCaseDescriptionId = (BigDecimal) row1.getAttribute("Id");
            }
            System.err.println("formCaseDescriptionId : " + formCaseDescriptionId);

            BigDecimal partyId = (BigDecimal) JSFUtils.resolveExpression("#{party.PartyId}");
            System.err.println("partyId HH : " + partyId);

            OperationBinding ob = ADFUtil.findOperation("getFormCasePartyIdPK");
            ob.getParamsMap().put("formCaseDescriptionId", formCaseDescriptionId);
            ob.getParamsMap().put("partyId", partyId);
            BigDecimal formCasePartyId = (BigDecimal) ob.execute();
            System.err.println("formCasePartyId V-Bean-V : " + formCasePartyId);

            ViewObjectImpl desPartsVO =
                (ViewObjectImpl) ADFUtil.findIterator("FormCaseDescriptionDesPartView1Iterator").getViewObject();
            if (formCasePartyId != null) {

                ViewCriteria vc = desPartsVO.getViewCriteria("FormCaseDescriptionDesPartViewCriteria");
                VariableValueManager vm = desPartsVO.ensureVariableManager();
                vm.setVariableValue("partyIdParam", formCasePartyId);
                desPartsVO.applyViewCriteria(vc);
                desPartsVO.executeQuery();

            } else {
                desPartsVO.executeEmptyRowSet();
            }

            OperationBinding obxx = ADFUtil.findOperation("ExecuteWithParams1");
            obxx.getParamsMap().put("P_PARTY_ID", partyId);
            obxx.execute();

            OperationBinding obyy = ADFUtil.findOperation("ExecuteWithParams");
            obyy.getParamsMap().put("partyId", partyId);
            obyy.execute();

            System.err.println("count des part - accordion : " + desPartsVO.getEstimatedRowCount());
        }
    }

    public String updatePrty() {
        showPopup("p1");
        System.err.println("par id xx : " + partyIdTFEdit);
        return null;
    }

    public String saveParty() {
        OperationBinding ob = ADFUtil.findOperation("updateParPartiesLog");
        ob.getParamsMap().put("parPartyId", partyIdTFEdit);
        ob.execute();
        if (!ob.getErrors().isEmpty()) {
            System.err.println("error occured while update party data");
        }
        hidePopup("p1");
        return null;
    }

    public String closePartyPopup() {
        hidePopup("p1");
        return null;
    }

    public String addDes() {
        showPopup("p2");
        return null;
    }

    public String closeDesPopup() {
        hidePopup("p2");
        return null;
    }

    public String showPopup(String popupId) {
        RichPopup popUp = (RichPopup) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), popupId);
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        popUp.show(hints);
        return null;
    }

    public String hidePopup(String popupId) {
        RichPopup popUp = (RichPopup) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), popupId);
        popUp.hide();
        return null;
    }

    public void setDet(RichShowDetailItem det) {
        this.det = det;
    }

    public RichShowDetailItem getDet() {
        return det;
    }

    public void setDett(RichPanelAccordion dett) {
        this.dett = dett;
    }

    public RichPanelAccordion getDett() {
        return dett;
    }

    public void setDeletedTableName(String deletedTableName) {
        this.deletedTableName = deletedTableName;
    }

    public String getDeletedTableName() {
        return deletedTableName;
    }

    public void setDeletedId(BigDecimal deletedId) {
        this.deletedId = deletedId;
    }

    public BigDecimal getDeletedId() {
        return deletedId;
    }
}
