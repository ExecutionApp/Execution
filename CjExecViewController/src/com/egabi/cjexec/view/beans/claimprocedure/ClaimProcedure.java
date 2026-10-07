package com.egabi.cjexec.view.beans.claimprocedure;

import com.egabi.cjexec.view.beans.inventory.Javaencrypt;
import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.JSFUtils;

import com.egabi.cjexec.view.utils.PersonData;

import com.egabi.cjexec.view.utils.PrintEnabledBean;

import com.rss.cj.dal.jeeimpl.dto.BusinessCaseType;

import java.io.InputStream;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Iterator;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.domain.Number;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;

import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.component.rich.output.RichOutputLabel;

import oracle.jbo.NameValuePairs;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableManager;
import oracle.jbo.server.RowFinder;

import oracle.stellent.ridc.model.DataObject;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

/** Added by M.Salah **/
public class ClaimProcedure extends PrintEnabledBean {

    PersonData pd;
    private String idNum;
    private RichButton editPartyBind;
    private RichInputText testTxtBind;
    

    public void setEditPartyBind(RichButton editPartyBind) {
        this.editPartyBind = editPartyBind;
    }

    public RichButton getEditPartyBind() {
        return editPartyBind;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getIdNum() {
        return idNum;
    }

    public ClaimProcedure() {
        super();
    }

    public String insertNIdServiceCallLogsearchClaimProcedure() {
        // Add event code here...

        BigDecimal loggedInEntityId = null;
        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
            Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityId =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                   Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityId =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        String claimNumber = null;
        BigDecimal claimYear = null;
        String claimRepeated = null;
        BigDecimal claimType = null;
        BigDecimal claimColya = null;
        BigDecimal claimParial = null;
        BigDecimal claimPolice = null;

        Row row = ADFUtil.findIterator("ClaimProcedureSearchFormRVO1Iterator").getCurrentRow();
        if (row != null) {
            if (row.getAttribute("ClaimNumber") != null) {
                claimNumber = row.getAttribute("ClaimNumber").toString();
            }

            claimYear = (BigDecimal) row.getAttribute("ClaimYear");

            if (row.getAttribute("RepeatedFlag") != null) {
                claimRepeated = row.getAttribute("RepeatedFlag").toString();
            }
            if (row.getAttribute("ClaimTypeId") != null) {
                claimType = new BigDecimal(row.getAttribute("ClaimTypeId").toString());
            }
        }

        Row policeRow = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator").getCurrentRow();
        if (policeRow != null) {
            claimColya = (BigDecimal) policeRow.getAttribute("NeyabKolyaa");
            claimParial = (BigDecimal) policeRow.getAttribute("NeyabGozayaa");
            claimPolice = (BigDecimal) policeRow.getAttribute("OrigEntity");
        }

        ViewObjectImpl claimVO = (ViewObjectImpl) ADFUtil.findIterator("ExcClaimsVO1Iterator").getViewObject();

        String condition = " 1 = 1 AND ACTIVE_FLAG = 1";

        if (claimNumber != null) {
            condition += " AND CLAIM_NUMBER = " + claimNumber;
        }

        if (claimYear != null) {
            condition += " AND CLAIM_YEAR = " + claimYear;
        }

        if (claimType != null) {
            condition += " AND CLAIM_TYPE_ID = " + claimType;
        }

        if (claimPolice != null) {
            condition += " AND CLAIM_POLICE_STATION_ID = " + claimPolice;
        }

        if (claimRepeated != null) {
            condition += " AND REPEATED_CLAIM_FLAG = '" + claimRepeated + "'";
        }

        claimVO.setWhereClause(condition);
        System.err.println(claimVO.getQuery());
        claimVO.executeQuery();


        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserPrevMap().get(5041) != null &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserPrevMap().get(5041)) {
            if (claimColya != null && loggedInEntityId != null &&
                claimColya.intValue() != loggedInEntityId.intValue()) {
                OperationBinding ob = ADFUtil.findOperation("insertClaimProcedureLog");
                ob.getParamsMap().put("claimId", null);
                ob.getParamsMap().put("claimNumber", claimNumber);
                ob.getParamsMap().put("claimYear", claimYear);
                ob.getParamsMap().put("claimRepeatedFlag", claimRepeated);
                ob.getParamsMap().put("claimTypeId", claimType);
                ob.getParamsMap().put("claimEntityColya", claimColya);
                ob.getParamsMap().put("claimEntityPartial", claimParial);
                ob.getParamsMap().put("claimEntityPolice", claimPolice);
                ob.getParamsMap().put("procedureId", null);
                ob.getParamsMap().put("procedureNumber", null);
                ob.getParamsMap().put("procedureTypeId", new BigDecimal(0));
                ob.getParamsMap().put("procedureDate", null);
                ob.getParamsMap().put("loggedInEntityId", loggedInEntityId);
                ob.execute();
                if (ob.getErrors().isEmpty()) {
                    //JSFUtils.showFacesErrorMessageFromBundle("bundle", "SAVE_PROCEDURE");
                }
            }
        }

        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (claimVO.getEstimatedRowCount() == 0 && !aot.isVisible()) {
            aot.setVisible(true);
            //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        } else if (claimVO.getEstimatedRowCount() != 0 && aot.isVisible()) {
            aot.setVisible(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        return null;
    }


    public String resetSearchClaimProcedure() {
        // Add event code here...
      //  ADFContext.getCurrent().getSessionScope().put("pClaimDeleted","N");
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }

        RichInputText claimNumberIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it1");
        RichSelectOneChoice claimYearSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc8");
        RichSelectOneChoice businessCaseTypeSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc4");
        RichSelectOneChoice policeStationSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc10");
        RichSelectOneRadio isRequiredSor =
            (RichSelectOneRadio) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sor3");

        claimNumberIt.setValue("");
        claimYearSoc.setValue("");
        businessCaseTypeSoc.setValue("");
        policeStationSoc.setValue("");
        isRequiredSor.setValue("");

        AdfFacesContext.getCurrentInstance().addPartialTarget(claimNumberIt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(claimYearSoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseTypeSoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(policeStationSoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(isRequiredSor);

        ADFUtil.findIterator("ExcClaimsVO1Iterator").getViewObject().executeEmptyRowSet();

        return null;
    }

    public void rowDisclosureListenerResultsTable(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
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
    }

    public void rowDisclosureListenerResultsTableParty(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
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
        Row currRow = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getViewObject().getCurrentRow() ;
         BigDecimal partyId =
             (BigDecimal) currRow.getAttribute("PartyId");
         BigDecimal FolderId =
             (BigDecimal) currRow.getAttribute("FolderId");
         System.err.println("partyId : " + partyId);
       
        ViewObject partyAddressesVO = ADFUtil.findIterator("PartyAddressesRVO1Iterator").getViewObject();
        partyAddressesVO.setNamedWhereClauseParam("ParPartyIdBind", partyId);
        partyAddressesVO.executeQuery();

        ViewObject partyFoldersVO = ADFUtil.findIterator("PartyFoldersRVO1Iterator").getViewObject();
        partyFoldersVO.setNamedWhereClauseParam("ParPartyIdBind", partyId);
        partyFoldersVO.executeQuery();

        ViewObject partyClaimsVO = ADFUtil.findIterator("PartyClaimsRVO1Iterator").getViewObject();
        partyClaimsVO.setNamedWhereClauseParam("ParPartyIdBind", FolderId);
        partyClaimsVO.executeQuery();

        ViewObject partyCrimesVO = ADFUtil.findIterator("PartyCrimesRVO1Iterator").getViewObject();
        partyCrimesVO.setNamedWhereClauseParam("ParPartyIdBind", partyId);
        partyCrimesVO.executeQuery();
    }

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

    public static java.util.Date getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        dateFormat.format(date);
        return date;
    }

    public boolean checkProClaimExist(String claimId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcProcedureClaimVO1Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckProClaimExistsVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("claimIdParam", claimId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        if (searchClaimResultView.getEstimatedRowCount() > 0)
            return true;
        else
            return false;

    }

    public void rowDisclosureListenerClaimTable(RowDisclosureEvent rowDisclosureEvent) {
        // Add event code here...
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

        RichPanelGroupLayout collectionTypePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl18");
        RichPanelGroupLayout paymentNumberPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl13");
        RichPanelGroupLayout procedureAmountPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl12");
        RichPanelGroupLayout procedureRemainingPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl11");
        RichPanelGroupLayout amountTextPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl10");

        RichPanelGroupLayout cancellationTypePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl157");

        RichInputText paymentNumberIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it86");
        RichInputText procedureAmountIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it2");
        RichInputText procedureRemainingIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it6");
        RichInputText amountTextIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it9");
   RichButton b4fara =      ((RichButton)JSFUtils.findComponentInRoot("b4fara"));
        b4fara.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(b4fara);
        RichButton b202 =      ((RichButton)JSFUtils.findComponentInRoot("b202"));
             b202.setDisabled(false);
             AdfFacesContext.getCurrentInstance().addPartialTarget(b202);
        collectionTypePGL.setVisible(false);

        paymentNumberPGL.setVisible(false);
        paymentNumberIt.setRequired(false);

        procedureAmountPGL.setVisible(false);
        procedureAmountIt.setRequired(false);

        procedureRemainingPGL.setVisible(false);
        procedureRemainingIt.setRequired(false);

        amountTextPGL.setVisible(false);
        amountTextIt.setRequired(false);
        RichButton button = (RichButton) JSFUtils.findComponentInRoot("b202");
        button.setVisible(false);
        JSFUtils.refreshComponent("b202");
        cancellationTypePGL.setVisible(false);

        AdfFacesContext.getCurrentInstance().addPartialTarget(collectionTypePGL);

        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentNumberPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentNumberIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureAmountPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureAmountIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureRemainingPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureRemainingIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(amountTextPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amountTextIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(cancellationTypePGL);
        
        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();

        Double fixedPenaltyAmount = null;
        Double fixedPenaltyFees = null;
        Double dailyPenaltyAmount = null;
        Double dailyPenaltyRemaining = null;
        Double dailyPenaltyFees = null;
        String penaltyDueDate = null;
        Double claimTotal = null;
        String dailyPenaltyFlag = null;
        String claimId = null;

        if (claimRow != null && procedureRow != null) {

            if (claimRow.getAttribute("DailyPenaltyFlag") != null) {
                dailyPenaltyFlag = claimRow.getAttribute("DailyPenaltyFlag").toString();
            }

            fixedPenaltyAmount =
                (Double) claimRow.getAttribute("FixedPenaltyAmount") == null ? new Double(0) :
                (Double) claimRow.getAttribute("FixedPenaltyAmount");

            fixedPenaltyFees =
                (Double) claimRow.getAttribute("FixedPenaltyFees") == null ? new Double(0) :
                (Double) claimRow.getAttribute("FixedPenaltyFees");

            dailyPenaltyAmount =
                (Double) claimRow.getAttribute("DailyPenaltyAmount") == null ? new Double(0) :
                (Double) claimRow.getAttribute("DailyPenaltyAmount");

            dailyPenaltyRemaining =
                (Double) claimRow.getAttribute("DailyPenaltyRemaining") == null ? new Double(0) :
                (Double) claimRow.getAttribute("DailyPenaltyRemaining");

            dailyPenaltyFees =
                (Double) claimRow.getAttribute("DailyPenaltyFees") == null ? new Double(0) :
                (Double) claimRow.getAttribute("DailyPenaltyFees");

            if (claimRow.getAttribute("PenaltyDueDate") != null) {
                penaltyDueDate = claimRow.getAttribute("PenaltyDueDate").toString();
            }

            if (claimRow.getAttribute("Id") != null) {
                claimId = claimRow.getAttribute("Id").toString();
            }

            claimTotal = (Double) claimRow.getAttribute("ClaimTotal");

            System.err.println("xx-> " + !checkProClaimExist(claimId));

            if (dailyPenaltyFlag != null && dailyPenaltyFlag.equals("N")) {
                //                if (!checkProClaimExist(claimId)) {
                claimTotal = Double.sum(fixedPenaltyAmount, fixedPenaltyFees);
                //                }
                procedureRow.setAttribute("FixedPenaltyAmount", fixedPenaltyAmount);
                procedureRow.setAttribute("FixedPenaltyFees", fixedPenaltyFees);

            } else if (dailyPenaltyFlag != null && dailyPenaltyFlag.equals("Y")) {
                //                if (!checkProClaimExist(claimId)) {
                claimTotal = Double.sum(dailyPenaltyRemaining, dailyPenaltyFees);
                //                }
                procedureRow.setAttribute("DailyPenaltyAmount", dailyPenaltyAmount);
                procedureRow.setAttribute("PenaltyDueDate", penaltyDueDate);
                procedureRow.setAttribute("DailyPenaltyRemaining", dailyPenaltyRemaining);
                procedureRow.setAttribute("DailyPenaltyFees", dailyPenaltyFees);

            } else if (dailyPenaltyFlag != null && dailyPenaltyFlag.equals("X")) {

                if (!checkProClaimExist(claimId)) {
                    claimTotal =
                        Double.sum((Double.sum(Double.sum(fixedPenaltyAmount, fixedPenaltyFees),
                                               dailyPenaltyRemaining)), dailyPenaltyFees);
                }
                procedureRow.setAttribute("FixedPenaltyAmount", fixedPenaltyAmount);
                procedureRow.setAttribute("FixedPenaltyFees", fixedPenaltyFees);
                procedureRow.setAttribute("DailyPenaltyAmount", dailyPenaltyAmount);
                procedureRow.setAttribute("PenaltyDueDate", penaltyDueDate);
                procedureRow.setAttribute("DailyPenaltyRemaining", dailyPenaltyRemaining);
                procedureRow.setAttribute("DailyPenaltyFees", dailyPenaltyFees);

            }

            procedureRow.setAttribute("ClaimTotal", dailyPenaltyRemaining);

            procedureRow.setAttribute("ProcedureNumber", null);
            procedureRow.setAttribute("ProcedureType", null);
            procedureRow.setAttribute("CancellationType", null);
            procedureRow.setAttribute("ProcedureDate", null);
            procedureRow.setAttribute("ProcedureAmount", null);
            procedureRow.setAttribute("ProcedureRemaining", null);
            procedureRow.setAttribute("AmountText", null);
            procedureRow.setAttribute("ProcedureNotes", null);
            procedureRow.setAttribute("CollectionType", null);
            procedureRow.setAttribute("PaymentNumber", null);
            procedureRow.setAttribute("NumberOfDays", null);
        }
    }

    public void procedureDateVCLFolder(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String penaltyDueDate = null;
        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        System.err.println("ProcedureType item-->" + procedureRow.getAttribute("ProcedureType"));
        BigDecimal procedureType = (BigDecimal) procedureRow.getAttribute("ProcedureType");
        if (procedureType != null && (procedureType.intValue() == 8  || procedureType.intValue() == 12)) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            Date startDate = null;
            int diff = 0;
            if (claimRow.getAttribute("PenaltyDueDate") != null &&
                claimRow.getAttribute("LastDailyPenaltyDate") == null) {
                penaltyDueDate = claimRow.getAttribute("PenaltyDueDate").toString();
                try {
                    Double DailyPenaltyRemainingTemp =
                        claimRow.getAttribute("DailyPenaltyRemaining") == null ? new Double(0) :
                        (Double) claimRow.getAttribute("DailyPenaltyRemaining");
                    Double dailyPenaltyAmount = (Double) claimRow.getAttribute("DailyPenaltyAmount");
                    startDate = dateFormat.parse(penaltyDueDate);
                    date = (java.util.Date) valueChangeEvent.getNewValue();
                    Object pocedureEndDateTemp = procedureRow.getAttribute("ProcedureEndDate");
                    if (pocedureEndDateTemp != null)
                        date = (Date) pocedureEndDateTemp;

                    diff = getDifferenceDays(startDate, date);
                    procedureRow.setAttribute("ProcedureAmount", 0.0);
                    procedureRow.setAttribute("NumberOfDays", diff);
                    procedureRow.setAttribute("ClaimTotal", (diff * dailyPenaltyAmount) + DailyPenaltyRemainingTemp);
                    ADFUtil.refreshComponent("it24");
                    ADFUtil.refreshComponent("it21");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void procedureDateVCLFolderLastDate(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String penaltyDueDate = null;
        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        BigDecimal procedureType = (BigDecimal) procedureRow.getAttribute("ProcedureType");
        if (procedureType != null &&( procedureType.intValue() == 8 || procedureType.intValue() == 12 )) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            Date startDate = null;
            int diff = 0;
            penaltyDueDate = claimRow.getAttribute("PenaltyDueDate").toString();
            try {
                Double DailyPenaltyRemainingTemp =
                    claimRow.getAttribute("DailyPenaltyRemaining") == null ? new Double(0) :
                    (Double) claimRow.getAttribute("DailyPenaltyRemaining");
                Double dailyPenaltyAmount = (Double) claimRow.getAttribute("DailyPenaltyAmount");
                startDate = dateFormat.parse(penaltyDueDate);
                if (valueChangeEvent.getNewValue() != null) {
                    date = (java.util.Date) valueChangeEvent.getNewValue();
                    diff = getDifferenceDays(startDate, date);
                } else {
                    Object pocedureEndDateTemp = procedureRow.getAttribute("ProcedureDate");
                    if (pocedureEndDateTemp != null) {
                        date = (Date) pocedureEndDateTemp;
                        diff = getDifferenceDays(startDate, date);
                    }
                }

                procedureRow.setAttribute("NumberOfDays", diff);
                procedureRow.setAttribute("ClaimTotal", (diff * dailyPenaltyAmount) + DailyPenaltyRemainingTemp);
                ADFUtil.refreshComponent("it24");
                ADFUtil.refreshComponent("it21");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

        }
    }

    public int getDifferenceDays(Date d1, Date d2) {
        int daysdiff = 0;
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        daysdiff = (int) diffDays;
        System.err.println("days ==>" + daysdiff);
        return daysdiff;
    }

    public void procedureDateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = (java.util.Date) valueChangeEvent.getNewValue();
        dateFormat.format(date);

        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();

        Double fixedPenaltyAmount = null;
        Double fixedPenaltyFees = null;
        Double fixedPenaltyTotal = null;
        Double dailyPenaltyAmount = null;
        String penaltyDueDate = null;
        long diff = 0;
        Double claimTotal = null;
        Double dailyPenaltyRemaining = null;
        Double dailyPenaltyFees = null;
        Date procedureDate = null;
        String dailyPenaltyFlag = null;
        BigDecimal procedureType = null;
        Double procedureRemaining = null;

        if (claimRow != null && procedureRow != null) {
            if (claimRow.getAttribute("DailyPenaltyFlag") != null) {
                dailyPenaltyFlag = claimRow.getAttribute("DailyPenaltyFlag").toString();
            }
            claimTotal = (Double) claimRow.getAttribute("ClaimTotal");
            fixedPenaltyAmount =
                (Double) procedureRow.getAttribute("FixedPenaltyAmount") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("FixedPenaltyAmount");
            fixedPenaltyFees =
                (Double) procedureRow.getAttribute("FixedPenaltyFees") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("FixedPenaltyFees");
            dailyPenaltyAmount =
                (Double) procedureRow.getAttribute("DailyPenaltyAmount") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("DailyPenaltyAmount");
            dailyPenaltyRemaining =
                (Double) procedureRow.getAttribute("DailyPenaltyRemaining") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("DailyPenaltyRemaining");
            dailyPenaltyFees =
                (Double) procedureRow.getAttribute("DailyPenaltyFees") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("DailyPenaltyFees");
            if (procedureRow.getAttribute("PenaltyDueDate") != null) {
                penaltyDueDate = procedureRow.getAttribute("PenaltyDueDate").toString();
            }
            procedureDate = (Date) procedureRow.getAttribute("ProcedureDate");
            procedureType = (BigDecimal) procedureRow.getAttribute("ProcedureType");

            procedureRemaining =
                (Double) procedureRow.getAttribute("ProcedureRemaining") == null ? new Double(0) :
                (Double) procedureRow.getAttribute("ProcedureRemaining");
        }

        fixedPenaltyTotal = Double.sum(fixedPenaltyAmount, fixedPenaltyFees);

        if (dailyPenaltyFlag != null && dailyPenaltyFlag.equals("Y")) {

            Date startDate = null;
            if (penaltyDueDate != null) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = df.parse(penaltyDueDate);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            if (startDate != null) {
                diff = ((date.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24);
            }

            if (dailyPenaltyRemaining == null) {
                dailyPenaltyRemaining = new Double(0);
            }

            if (diff <= 0 && dailyPenaltyAmount != null && dailyPenaltyAmount.intValue() > 0 &&
                dailyPenaltyRemaining != null && dailyPenaltyRemaining.intValue() >= 0) {
                if (dailyPenaltyFees != null && dailyPenaltyFees.intValue() == 0) {
                    claimTotal = dailyPenaltyRemaining;
                } else if (dailyPenaltyFees != null && dailyPenaltyFees.intValue() > 0) {
                    claimTotal = dailyPenaltyRemaining + dailyPenaltyFees;
                }
            } else if (diff > 0 && dailyPenaltyAmount != null && dailyPenaltyAmount.intValue() > 0 &&
                       dailyPenaltyRemaining != null && dailyPenaltyRemaining.intValue() >= 0) {
                if (dailyPenaltyFees != null && dailyPenaltyFees.intValue() == 0) {
                    claimTotal = Double.sum(((new Double(diff) * dailyPenaltyAmount)), dailyPenaltyRemaining);
                } else if (dailyPenaltyFees != null && dailyPenaltyFees.intValue() > 0) {
                    claimTotal =
                        Double.sum(Double.sum(((new Double(diff) * dailyPenaltyAmount)), dailyPenaltyRemaining),
                                   dailyPenaltyFees);
                }
            }

            if (procedureRow != null) {
                procedureRow.setAttribute("NumberOfDays", diff);
                procedureRow.setAttribute("ClaimTotal", claimTotal);
            }
            ADFUtil.refreshComponent("it24");
            ADFUtil.refreshComponent("it21");

        } else if (dailyPenaltyFlag != null && dailyPenaltyFlag.equals("X")) {

            Date startDate = null;
            if (penaltyDueDate != null) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    startDate = df.parse(penaltyDueDate);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            if (startDate != null) {
                diff = ((date.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24);
            }

            if (dailyPenaltyRemaining == null) {
                dailyPenaltyRemaining = new Double(0);
            }


            System.err.println("dailyPenaltyAmount-->" + dailyPenaltyAmount);
            claimTotal = Double.sum(((new Double(diff) * dailyPenaltyAmount)), claimTotal);

            if (procedureRow != null) {
                procedureRow.setAttribute("NumberOfDays", diff);
                procedureRow.setAttribute("ClaimTotal", claimTotal);
                /**ADDED**/
                //procedureRow.setAttribute("DailyPenaltyRemaining", dailyPenaltyRemaining);
            }
            ADFUtil.refreshComponent("it24");
            ADFUtil.refreshComponent("it21");
            //ADFUtil.refreshComponent("it149");

        }

        if (procedureType != null && procedureType.intValue() == 1) {

            // System.err.println("inside ekraaaah : " + addDays(date, 90));
            // Date c = new Date((date.getTime() + (90 * 24 * 60 * 60 * 1000)));
            BigDecimal numDaysInput = (BigDecimal) procedureRow.getAttribute("NumDaysInput");
            RichInputText numDaysIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it74");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date c = null;
            try {
                c = format.parse(date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c = addDays(c, numDaysInput != null ? numDaysInput.intValue() : 0);

            // Date c = new Date(addDays(date, 5));
            DateFormat dateFormatc = new SimpleDateFormat("dd/MM/yyyy");

            dateFormatc.format(c);
            System.err.println("inside ekraaaah : " + c);
            procedureRow.setAttribute("ProcedureEndDate", c);
            Double amount = new Double((numDaysInput != null ? numDaysInput.intValue() : 0) * 5);
            System.err.println("amount-> " + amount);
            procedureRow.setAttribute("ProcedureAmount", amount);
            procedureRow.setAttribute("ProcedureRemaining", (claimTotal - amount));
            procedureRow.setAttribute("AmountText", getAmountText(amount));
            ADFUtil.refreshComponent("id3");
            ADFUtil.refreshComponent("it2");
            ADFUtil.refreshComponent("it6");
            ADFUtil.refreshComponent("it9");
            // procedureRow.setAttribute("ProcedureRemaining", Double.sum(procedureRemaining, (new Double(diff) * dailyPenaltyAmount)));
            // ADFUtil.refreshComponent("it6");
        }
    }

    public void numDaysVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
     BigDecimal numDays =    (BigDecimal) valueChangeEvent.getNewValue() ; 
     if(numDays.compareTo(new BigDecimal (91))==-1)
        calcutionNumDays ((BigDecimal) valueChangeEvent.getNewValue());
     else
        ADFUtil.showMessage("��� ���� ������� ������ ��� �� 90 ���",
                            FacesMessage.SEVERITY_INFO);
         
    }
    
    public void calcutionNumDays(BigDecimal days)
    {
        System.out.println("days   is "+days);
        if(days!=null){
            Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
            Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
            BigDecimal numDays =days;
            Double claimTotal = (Double) claimRow.getAttribute("FixedPenaltyAmount");
            RichInputDate procedureDate =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id2");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date c = null;
            try {
                c = format.parse(procedureDate.getValue() != null ? procedureDate.getValue().toString() :
                                 new Date().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c = addDays(c, numDays != null ? numDays.intValue() : 0);

            Double amount = new Double((numDays != null ? numDays.intValue() : 0) * 5);
            System.err.println("amount-> claimTotal -> " + amount + " - " + claimTotal);

            RichInputText procedureAmountIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it2");
            procedureAmountIt.setValue(amount);

            procedureRow.setAttribute("ProcedureEndDate", c);
            procedureRow.setAttribute("ProcedureAmount", amount);
            procedureRow.setAttribute("ProcedureRemaining", (claimTotal - amount));
            procedureRow.setAttribute("AmountText", getAmountText(amount));
            ADFUtil.refreshComponent("id3");
            ADFUtil.refreshComponent("it2");
            ADFUtil.refreshComponent("it6");
            ADFUtil.refreshComponent("it9");
        }
        
        }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public Object getAmountText(Double amount) {
        OperationBinding ob = ADFUtil.findOperation("numberToText");
        ob.getParamsMap().put("amount", amount);
        return ob.execute();
    }

    public void showErrorMessageForComponentById(String message, String id) {
        FacesContext.getCurrentInstance().addMessage(ADFUtil.findComponentInRoot(id).getClientId(),
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

    public void amountChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Row row = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        Row ghramaInstallment = ADFUtil.findIterator("ExcLkpGharamaInstallments1Iterator").getCurrentRow();
         BigDecimal procedureType = (BigDecimal) row.getAttribute("ProcedureType");
//        System.out.println(procedureType.intValue()+"row.getAttribute(\"ProcedureType\");"+row.getAttribute("ProcedureType"));
        
        Double remainFixedPenaltyAmount = null;

        remainFixedPenaltyAmount =
            claimRow.getAttribute("FixedPenaltyAmount") == null ? new Double(0) :
            (Double) claimRow.getAttribute("FixedPenaltyAmount");
        Double claimTotal = null;
        Double previousPaid = null;
        if (claimRow.getAttribute("PreviousPaied") != null)
            previousPaid = Double.parseDouble(claimRow.getAttribute("PreviousPaied")+"");
        
        claimTotal =
            claimRow.getAttribute("ClaimTotal") == null ? new Double(0) :
            (Double) claimRow.getAttribute("ClaimTotal");

        Double claimTotalDaliy =
            row.getAttribute("ClaimTotal") == null ? new Double(0) : (Double) row.getAttribute("ClaimTotal");
        Double amount =
            valueChangeEvent.getNewValue() == null ? new Double(0) : (Double) valueChangeEvent.getNewValue();
        if (row != null) {
            
            if ( procedureType.intValue() != 28 && procedureType.intValue() != 32 
                 && procedureType.intValue() != 26 && procedureType.intValue() != 13  
                 && procedureType.intValue() != 15) {
            row.setAttribute("AmountText", getAmountText(amount));
            if (amount > remainFixedPenaltyAmount && procedureType.intValue() != 8) {
                System.out.println("IIIIIIIIIi");
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
            } else if (amount > claimTotalDaliy.intValue() && procedureType.intValue() == 8) {
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");

            } else {

                if (procedureType.intValue() != 8) {
                    row.setAttribute("ProcedureRemaining", (remainFixedPenaltyAmount - amount));
                    if (procedureType.intValue() == 9 && ghramaInstallment != null) {
                        System.out.println("ExcLkpGharamaInstallments1.getAtt  ==>" +
                                           JSFUtils.resolveExpression("#{bindings.ExcLkpGharamaInstallments1.inputValue}"));
                        BigDecimal noOffInstallments =
                            (BigDecimal) JSFUtils.resolveExpression("#{bindings.ExcLkpGharamaInstallments1.inputValue}");
                        System.err.println("  ===result for cal farahat ghramaInstallment==>" + noOffInstallments);
                        if (!noOffInstallments.toString().equals("0")) {
                            System.err.println("result for cal ==>" +
                                               Math.round((remainFixedPenaltyAmount - amount) /
                                                          noOffInstallments.intValue()));
                            row.setAttribute("NumDaysInput",
                                             new BigDecimal(Math.round((remainFixedPenaltyAmount - amount) /
                                                                       noOffInstallments.intValue())));
                            
                        } else
                            row.setAttribute("NumDaysInput", 0);
                    }
                } else if (procedureType.intValue() == 8) {
                    row.setAttribute("ProcedureRemaining", (claimTotalDaliy - amount));

                } else
                    row.setAttribute("AmountText", getAmountText(amount));
            }
        }
            else{
                System.out.println(amount+"EEEEE"+claimTotal+"EEEEEe"+remainFixedPenaltyAmount);
            if( amount > claimTotal && procedureType.intValue() != 32 && procedureType.intValue() != 26)
            {
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
            
            }
            }
            
            if (procedureType.intValue() == 32)
            {
                    if( amount > previousPaid)
                    {
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                    
                    }
                }
            
        }
    }

    public boolean checkProcTypeEkrahExist(Number claimId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcProcedureClaimVO3Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckProcTypeEkrahExistsVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("claimIdParam", claimId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        if (searchClaimResultView.getEstimatedRowCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkProcPaymentNumExist(String paymentNumber, BigDecimal entityId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcProcedureClaimVO3Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckProcPaymentNumExistsVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("paymentNumParam", paymentNumber);
        searchClaimResultView.ensureVariableManager().setVariableValue("entityIdParam", entityId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        if (searchClaimResultView.getEstimatedRowCount() > 0)
            return true;
        else
            return false;
    }

    public String saveClaimProcedure() {
        BigDecimal loggedInEntityId = null;
        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
            Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityId =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                   Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityId =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }
        BigDecimal loggedInUserId = null;
        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId() != null) {
            loggedInUserId = new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());
        }
        Row policeRow = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator").getCurrentRow();
        BigDecimal claimColyaXX = null;
        BigDecimal claimGozyaXX = null;
        BigDecimal claimPoliceXX = null;
        if (policeRow != null) {
            claimColyaXX = (BigDecimal) policeRow.getAttribute("NeyabKolyaa");
            claimGozyaXX = (BigDecimal) policeRow.getAttribute("NeyabGozayaa");
            claimPoliceXX = (BigDecimal) policeRow.getAttribute("OrigEntity");
        }
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        Number claimId = null;
        String claimNumber = null;
        BigDecimal claimYear = null;
        String claimRepeated = null;
        BigDecimal claimType = null;
        BigDecimal claimColya = null;
        BigDecimal claimParial = null;
        BigDecimal claimPolice = null;
        String claimResParty = null;
        String claimFolder = null;
        Double claimTotal = null;
        Double remainFixedPenaltyAmount = null;
        BigDecimal businessCaseId = null;
        String partyId = null;
        BigDecimal parPoliceId = null;
        if (claimRow != null) {
            claimId = (Number) claimRow.getAttribute("Id");
            if (claimRow.getAttribute("ClaimNumber") != null) {
                claimNumber = claimRow.getAttribute("ClaimNumber").toString();
            }
            claimYear = (BigDecimal) claimRow.getAttribute("ClaimYear");
            if (claimRow.getAttribute("RepeatedClaimFlag") != null) {
                claimRepeated = claimRow.getAttribute("RepeatedClaimFlag").toString();
            }
            claimType = (BigDecimal) claimRow.getAttribute("ClaimTypeId");
            claimColya = (BigDecimal) claimRow.getAttribute("ClaimColyaId");
            claimParial = (BigDecimal) claimRow.getAttribute("ClaimPartialId");
            claimPolice = (BigDecimal) claimRow.getAttribute("ClaimPoliceStationId");
            if (claimRow.getAttribute("ClaimResPartyId") != null) {
                claimResParty = claimRow.getAttribute("ClaimResPartyId").toString();
            }
            if (claimRow.getAttribute("ClaimFolderId") != null) {
                claimFolder = claimRow.getAttribute("ClaimFolderId").toString();
            }
            businessCaseId =
                claimRow.getAttribute("BusinessCaseId") == null ? new BigDecimal(0) :
                (BigDecimal) claimRow.getAttribute("BusinessCaseId");
            if (claimRow.getAttribute("PartyId") != null) {
                partyId = claimRow.getAttribute("PartyId").toString();
            }
            parPoliceId = (BigDecimal) claimRow.getAttribute("ParPoliceId");
        }
        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        String procedureNumber = null;
        BigDecimal procedureType = null;
        BigDecimal cancellationType = null;
        Timestamp procedureDate = null;
        Double procedureAmount = null;
        Double procedureRemaining = null;
        Double dailyPenaltyRemainingZZ = null;
        String procedureNotes = null;
        String collectionType = null;
        String paymentNumber = null;
        if (procedureRow != null) {
            if (procedureRow.getAttribute("ProcedureNumber") != null) {
                procedureNumber = procedureRow.getAttribute("ProcedureNumber").toString();
            }
            procedureType = (BigDecimal) procedureRow.getAttribute("ProcedureType");

            cancellationType = (BigDecimal) procedureRow.getAttribute("CancellationType");
            procedureDate = (Timestamp) procedureRow.getAttribute("ProcedureDate");
            procedureAmount = (Double) procedureRow.getAttribute("ProcedureAmount");
            procedureRemaining = (Double) procedureRow.getAttribute("ProcedureRemaining");
            if (procedureRow.getAttribute("ProcedureNotes") != null) {
                procedureNotes = procedureRow.getAttribute("ProcedureNotes").toString();
            }
            if (procedureRow.getAttribute("CollectionType") != null) {
                collectionType = procedureRow.getAttribute("CollectionType").toString();
            }
            if (procedureRow.getAttribute("PaymentNumber") != null) {
                paymentNumber = procedureRow.getAttribute("PaymentNumber").toString();
            }
            claimTotal = (Double) procedureRow.getAttribute("ClaimTotal");
            remainFixedPenaltyAmount = (Double) claimRow.getAttribute("FixedPenaltyAmount");
            dailyPenaltyRemainingZZ = (Double) procedureRow.getAttribute("DailyPenaltyRemaining");
        }
        oracle.jbo.domain.Number procedureId = null;
        if (procedureAmount != null && remainFixedPenaltyAmount != null &&
            procedureAmount.intValue() > remainFixedPenaltyAmount.intValue()) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
            return null;
        } else if (procedureType != null && procedureType.intValue() == 1 && checkProcTypeEkrahExist(claimId)) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROC_EKRAH_UNIQUE");
            return null;
        } else if (procedureType != null && procedureType.intValue() == 1 && procedureAmount != null &&
                   procedureAmount.intValue() > 900) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROC_EKRAH_AMOUNT");
            return null;
        } else if (paymentNumber != null && loggedInEntityId != null &&
                   checkProcPaymentNumExist(paymentNumber, loggedInEntityId)) {
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROC_PAYMENT_NUMBER");
            return null;
        } else {
            System.out.println("claimId   --" + claimId);
            OperationBinding ob = ADFUtil.findOperation("insertClaimProcedureRow");
            ob.getParamsMap().put("claimId", claimId);
            ob.getParamsMap().put("claimNumber", claimNumber);
            ob.getParamsMap().put("claimYear", claimYear);
            ob.getParamsMap().put("claimRepeatedFlag", claimRepeated);
            ob.getParamsMap().put("claimTypeId", claimType);
            ob.getParamsMap().put("claimEntityColya", claimColya);
            ob.getParamsMap().put("claimEntityPartial", claimParial);
            ob.getParamsMap().put("claimEntityPolice", claimPolice);
            ob.getParamsMap().put("resPartyId", claimResParty);
            ob.getParamsMap().put("claimFolderId", claimFolder);
            ob.getParamsMap().put("procedureNumber", procedureNumber);
            ob.getParamsMap().put("procedureTypeId", procedureType);
            ob.getParamsMap().put("cancellationType", cancellationType);
            ob.getParamsMap().put("procedureDate", procedureDate);
            ob.getParamsMap().put("procedureAmount", procedureAmount);
            ob.getParamsMap().put("procedureRemaining", procedureRemaining);
            ob.getParamsMap().put("procedureNotes", procedureNotes);
            ob.getParamsMap().put("collectionType", collectionType);
            ob.getParamsMap().put("paymentNumber", paymentNumber);
            ob.getParamsMap().put("businessCaseId", businessCaseId);
            /**ADDED**/
            ob.getParamsMap().put("procedureRemainingZZ", claimTotal);
            ob.getParamsMap().put("partyId", partyId);
            ob.getParamsMap().put("userId", loggedInUserId);
            ob.getParamsMap().put("entityId", loggedInEntityId);
            procedureId = (oracle.jbo.domain.Number) ob.execute();
            if (ob.getErrors().isEmpty()) {
                String fileName =
                    JSFUtils.getFromPageFlowScope("fileName") == null ? "" :
                    JSFUtils.getFromPageFlowScope("fileName").toString();
                String documentId =
                    JSFUtils.getFromPageFlowScope("documentId") == null ? "" :
                    JSFUtils.getFromPageFlowScope("documentId").toString();
                String docName =
                    JSFUtils.getFromPageFlowScope("docName") == null ? "" :
                    JSFUtils.getFromPageFlowScope("docName").toString();
                if (procedureId != null && partyId != null && !documentId.isEmpty()) {
                    String[] arrOfStr = null;
                    if (procedureType != null && procedureType.intValue() == 1) {
                        arrOfStr = partyId.split(",", 0);
                        for (String partyIdXX : arrOfStr) {
                            partyIdXX = partyIdXX.trim();
                            insertIntoCJFileDetails(loggedInEntityId, loggedInUserId, procedureId.bigDecimalValue(),
                                                    documentId, docName, fileName, new BigDecimal(40),
                                                    new BigDecimal(40), new BigDecimal(partyIdXX), parPoliceId,
                                                    businessCaseId);
                        }
                    } else if (procedureType != null && procedureType.intValue() == 2) {
                        arrOfStr = partyId.split(",", 0);
                        for (String partyIdXX : arrOfStr) {
                            partyIdXX = partyIdXX.trim();
                            insertIntoCJFileDetails(loggedInEntityId, loggedInUserId, procedureId.bigDecimalValue(),
                                                    documentId, docName, fileName, new BigDecimal(41),
                                                    new BigDecimal(41), new BigDecimal(partyIdXX), parPoliceId,
                                                    businessCaseId);
                        }
                    } else if (procedureType != null && procedureType.intValue() == 3) {
                        arrOfStr = partyId.split(",", 0);
                        for (String partyIdXX : arrOfStr) {
                            partyIdXX = partyIdXX.trim();
                            insertIntoCJFileDetails(loggedInEntityId, loggedInUserId, procedureId.bigDecimalValue(),
                                                    documentId, docName, fileName, new BigDecimal(42),
                                                    new BigDecimal(42), new BigDecimal(partyIdXX), parPoliceId,
                                                    businessCaseId);
                        }
                    } else if (procedureType != null && procedureType.intValue() == 4) {
                        arrOfStr = partyId.split(",", 0);
                        for (String partyIdXX : arrOfStr) {
                            partyIdXX = partyIdXX.trim();
                            insertIntoCJFileDetails(loggedInEntityId, loggedInUserId, procedureId.bigDecimalValue(),
                                                    documentId, docName, fileName, new BigDecimal(43),
                                                    new BigDecimal(43), new BigDecimal(partyIdXX), parPoliceId,
                                                    businessCaseId);
                        }
                    }
                }
                JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_PROCEDURE");
            }
            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserPrevMap().get(5041) != null &&
                ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserPrevMap().get(5041)) {
                if (claimColyaXX != null && loggedInEntityId != null &&
                    claimColyaXX.intValue() != loggedInEntityId.intValue()) {
                    OperationBinding ob2 = ADFUtil.findOperation("insertClaimProcedureLog");
                    ob2.getParamsMap().put("claimId", claimId);
                    ob2.getParamsMap().put("claimNumber", claimNumber);
                    ob2.getParamsMap().put("claimYear", claimYear);
                    ob2.getParamsMap().put("claimRepeatedFlag", claimRepeated);
                    ob2.getParamsMap().put("claimTypeId", claimType);
                    ob2.getParamsMap().put("claimEntityColya", claimColyaXX);
                    ob2.getParamsMap().put("claimEntityPartial", claimGozyaXX);
                    ob2.getParamsMap().put("claimEntityPolice", claimPoliceXX);
                    ob2.getParamsMap().put("procedureId", procedureId);
                    ob2.getParamsMap().put("procedureNumber", procedureNumber);
                    ob2.getParamsMap().put("procedureTypeId", procedureType);
                    ob2.getParamsMap().put("procedureDate", procedureDate);
                    ob2.getParamsMap().put("loggedInEntityId", loggedInEntityId);
                    ob2.execute();
                    if (ob2.getErrors().isEmpty()) {
                        //                        JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_PROCEDURE");
                    }
                }
            }
            RichTable table = (RichTable) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "t1");
            RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
            discloseRowKeySet.clear();
            AdfFacesContext adfFacesContext = null;
            adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(table.getParent());
            ADFUtil.findIterator("ExcClaimsVO1Iterator").getViewObject().executeEmptyRowSet();
            ADFUtil.findIterator("ExcClaimsVO1Iterator").executeQuery();
            adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(table.getParent());
        }
        return null;
    }
    
    public String callPOS2() {
        Row proceClaimRow = ADFUtil.findIterator("ExcProcedureClaimVO1Iterator").getCurrentRow();
      String temp = (String) proceClaimRow.getAttribute("ProcedureNumber");
        System.out.println("temp        -----*"+temp+"**----");
    String procId ="";
        String idNumber ="";
        String partyName ="";
        double payment =      0.0;
        BigDecimal  feesTypeId = new BigDecimal (0);
      String procedureResult =  temp;
          //saveClaimProcedures(2);
        String[] split = procedureResult.split(",");
         if (split.length > 1) {
                 procId = split[0] ;
                 partyName = split[3] ;
                 idNumber = split[4] ;
                 payment  = Double.parseDouble(split[1]) ;
                 feesTypeId = new BigDecimal (split[2]) ;
             }
        String requestId = "";
               String currentDate = null;  
               
               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");
                        LocalDateTime now = LocalDateTime.now();
                          currentDate = dtf.format(now);
                     System.out.println(JSFUtils.getFromSession("pBillerId")+"currentDate   is "+currentDate);
        
        
      
     
        Double FixedBenfit = Double.valueOf(JSFUtils.getFromSession("pFixedBenfit")+"");
           Double FixedBenfitAUTO = Double.valueOf(JSFUtils.getFromSession("pFixedBenfitAUTO")+"");
        Double PercentageBenfit =   Double.valueOf( JSFUtils.getFromSession("pPercentageBenfit")+"");
        int BillerId = Integer.parseInt(JSFUtils.getFromSession("pBillerId")+""); 
        String SettlementCodeEF = JSFUtils.getFromSession("pSettlementCodeEF")+"";
        String SettlementCodeGOV = JSFUtils.getFromSession("pSettlementCodeGOV")+""; 
           String SettlementCodeAUTO = JSFUtils.getFromSession("pSettlementCodeAUTO")+""; 
                 
        //            this.setProcedureId(procId);
                 double percentageNum=  Double.valueOf(String.format ("%.2f",((payment+FixedBenfitAUTO)*(PercentageBenfit/100))));
                 System.out.println("Fees is ----------   "+percentageNum);
                 double fees=0.0;
                 if(percentageNum>FixedBenfit)
                 fees=percentageNum;
                 else
           fees=FixedBenfit ;
          
          System.out.println("feesTypeId   is "+feesTypeId);
          String description = "����� ������� ��������  ";
               description= "������ - " ;
          if (feesTypeId.compareTo(new BigDecimal (4)) == 0){
                  description = description+"���� ����� ����� ";
              }
              else if (feesTypeId.compareTo(new BigDecimal (8)) == 0){
                       description =description+" ���� ����� ����� " ;
                   }
               else if (feesTypeId.compareTo(new BigDecimal (9)) == 0){
                        description =description+" ��� ������� " ;
                    }
               
              
               ViewObject vo = ADFUtil.findIterator("PosMasterVO1Iterator").getViewObject();
               Row rw = vo.createRow();
               System.out.println("****"+rw.getAttribute("Id"));
               rw.setAttribute("BillerId", ""+BillerId);
               rw.setAttribute("ServiceName", ""+description);
               description = description+ " - " ;
             
               rw.setAttribute("BillId", ""+procId);
                rw.setAttribute("ApplicantName", partyName);
        if (FixedBenfitAUTO==0){
        rw.setAttribute("Amount",payment+"," +fees);
        rw.setAttribute("SettelmentCodes",SettlementCodeGOV + "," + SettlementCodeEF);
        }
        else
        {
                rw.setAttribute("Amount",payment+","+FixedBenfitAUTO+"," +fees);
                rw.setAttribute("SettelmentCodes",SettlementCodeGOV + ","+SettlementCodeAUTO+"," + SettlementCodeEF);
            }
        rw.setAttribute("EfinanceFees", fees);
        rw.setAttribute("GovernmentalFees",payment );
        rw.setAttribute("AutomationFees",FixedBenfitAUTO);
               rw.setAttribute("Description",description+partyName);
                rw.setAttribute("BillingAccount", idNumber);
                rw.setAttribute("BillNumber","EXC"+procId+currentDate );
        rw.setAttribute("PaidAmount",payment+fees+FixedBenfitAUTO );
                vo.insertRow(rw);
               ADFUtil.findOperation("Commit").execute();
         
    
       
       
        
       
             

                       
    
       
         requestId =  ""+procId+",EXC"+procId+currentDate;
    
               Javaencrypt encrypt = new Javaencrypt();
               StringBuilder a = new StringBuilder();
               char stx = (char) Integer.parseInt("2", 16);
               char etx = (char) Integer.parseInt("3", 16);
               a.append(stx);
               a.append("1," + requestId + ",818,000001,000000"+","+BillerId);
               a.append(etx);
        System.out.println("Bill number ::   "+a);
               byte[] buffer = encrypt.encryptDecryptXor(a.toString().getBytes(), a.toString().length());
               String bufferStr = "";
               bufferStr = new String(buffer);
               System.out.println("bufferStr:::::::   " + bufferStr);

               FacesContext facesContext = FacesContext.getCurrentInstance();
               ExtendedRenderKitService service =
                   (ExtendedRenderKitService) org.apache
                                                                                .myfaces
                                                                                .trinidad
                                                                                .util
                                                                                .Service
                                                                                .getRenderKitService(facesContext,
                                                                                                      ExtendedRenderKitService.class);

               String clientId = testTxtBind.getClientId();
               bufferStr = bufferStr.replace("\\", "\\\\");
               service.addScript(facesContext, "sendSerialLine('" + bufferStr + "','" + clientId + "');");
             
             
        
        
        return null;
    }
    
    
    
    public String callPOS() {
    String procId ="";
        String idNumber ="";
        String partyName ="";
        double payment =      0.0;
        BigDecimal  feesTypeId = new BigDecimal (0);
      String procedureResult =  saveClaimProcedures(2);
        String[] split = procedureResult.split(",");
         if (split.length > 1) {
                 procId = split[0] ;
                 partyName = split[3] ;
                 idNumber = split[4] ;
                 payment  = Double.parseDouble(split[1]) ;
                 feesTypeId = new BigDecimal (split[2]) ;
             }
        String requestId = "";
               String currentDate = null;  
               
               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");
                        LocalDateTime now = LocalDateTime.now();
                          currentDate = dtf.format(now);
                     System.out.println(JSFUtils.getFromSession("pBillerId")+"currentDate   is "+currentDate);
        
        
      
     
            Double FixedBenfit = Double.valueOf(JSFUtils.getFromSession("pFixedBenfit")+"");
            Double PercentageBenfit =   Double.valueOf( JSFUtils.getFromSession("pPercentageBenfit")+"");
            int BillerId = Integer.parseInt(JSFUtils.getFromSession("pBillerId")+""); 
            String SettlementCodeEF = JSFUtils.getFromSession("pSettlementCodeEF")+"";
            String SettlementCodeGOV = JSFUtils.getFromSession("pSettlementCodeGOV")+"";     
                     
//            this.setProcedureId(procId);
                     double percentageNum=  Double.valueOf(String.format ("%.2f",(payment*(PercentageBenfit/100))));
                     System.out.println("Fees is ----------   "+percentageNum);
                     double fees=0.0;
                     if(percentageNum>FixedBenfit)
                     fees=percentageNum;
                     else
               fees=FixedBenfit ;
          
          System.out.println("feesTypeId   is "+feesTypeId);
          String description = "����� ������� ��������  ";
               description= "������ - " ;
          if (feesTypeId.compareTo(new BigDecimal (4)) == 0){
                  description = description+"���� ����� ����� ";
              }
              else if (feesTypeId.compareTo(new BigDecimal (8)) == 0){
                       description =description+" ���� ����� ����� " ;
                   }
               else if (feesTypeId.compareTo(new BigDecimal (9)) == 0){
                        description =description+" ��� ������� " ;
                    }
               
              
               ViewObject vo = ADFUtil.findIterator("PosMasterVO1Iterator").getViewObject();
               Row rw = vo.createRow();
               System.out.println("****"+rw.getAttribute("Id"));
               rw.setAttribute("BillerId", ""+BillerId);
               rw.setAttribute("ServiceName", ""+description);
               description = description+ " - " ;
               rw.setAttribute("SettelmentCodes",SettlementCodeGOV + "," + SettlementCodeEF);
               rw.setAttribute("BillId", ""+procId);
                rw.setAttribute("ApplicantName", partyName);
                rw.setAttribute("Amount",payment+"," +fees);
               rw.setAttribute("Description",description+partyName);
                rw.setAttribute("BillingAccount", idNumber);
                rw.setAttribute("BillNumber","EXC"+procId+currentDate );
               rw.setAttribute("PaidAmount",payment+fees );
                vo.insertRow(rw);
               ADFUtil.findOperation("Commit").execute();
         
    
       
       
        
       
             

                       
    
       
         requestId =  ""+procId+",EXC"+procId+currentDate;
 
               Javaencrypt encrypt = new Javaencrypt();
               StringBuilder a = new StringBuilder();
               char stx = (char) Integer.parseInt("2", 16);
               char etx = (char) Integer.parseInt("3", 16);
               a.append(stx);
               a.append("1," + requestId + ",818,000001,000000"+","+BillerId);
               a.append(etx);
        System.out.println("Bill number ::   "+a);
               byte[] buffer = encrypt.encryptDecryptXor(a.toString().getBytes(), a.toString().length());
               String bufferStr = "";
               bufferStr = new String(buffer);
               System.out.println("bufferStr:::::::   " + bufferStr);

               FacesContext facesContext = FacesContext.getCurrentInstance();
               ExtendedRenderKitService service =
                   (ExtendedRenderKitService) org.apache
                                                                                .myfaces
                                                                                .trinidad
                                                                                .util
                                                                                .Service
                                                                                .getRenderKitService(facesContext,
                                                                                                      ExtendedRenderKitService.class);

               String clientId = testTxtBind.getClientId();
               bufferStr = bufferStr.replace("\\", "\\\\");
               service.addScript(facesContext, "sendSerialLine('" + bufferStr + "','" + clientId + "');");
             
             
        
        
        return null;
    }

public String saveClaimProcedures( int type)
{
        try {
            Double claimTotal = null;
            BigDecimal cancelAmount = new BigDecimal(0);
            Double previousPaid = null;
            Double remainFixedPenaltyAmount = null;
            Double claimTotalDaliy = null;
            
          
            RichInputText penaltyValue2 =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it19");
            RichInputText claimSource =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it20");
            RichInputText GuranteeValue2 =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it15");
            RichInputText procedureFolder =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it16");
            Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
            Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
            ViewObject ExcProcedureClaimVO = ADFUtil.findIterator("ExcProcedureClaimVO1Iterator").getViewObject();
            ViewObject ExcProcedureClaimFolderVO = ADFUtil.findIterator("ExcProcedureClaimFolderVO1Iterator").getViewObject();
            ViewObject ExcsettlementPenaltyVO = ADFUtil.findIterator("ExcSettlementPenaltyVO1Iterator").getViewObject();
            int excFolderId =   Integer.parseInt( claimRow.getAttribute("ExcFolderId")+"");
            Double procedureAmount = null;
            if (claimRow != null) {
                remainFixedPenaltyAmount = (Double) claimRow.getAttribute("FixedPenaltyAmount");
                procedureAmount =
                    procedureRow.getAttribute("ProcedureAmount") == null ? new Double(0) :
                    (Double) procedureRow.getAttribute("ProcedureAmount");
                if (procedureRow.getAttribute("ClaimTotal") != null)
                    claimTotalDaliy = (Double) procedureRow.getAttribute("ClaimTotal");
                if (claimRow.getAttribute("CancelAmount") != null)
                cancelAmount = new BigDecimal( claimRow.getAttribute("CancelAmount")+"") ;
                if (claimRow.getAttribute("PreviousPaied") != null)
                    previousPaid = Double.parseDouble(claimRow.getAttribute("PreviousPaied")+"");
                BigDecimal procedureType = (BigDecimal) procedureRow.getAttribute("ProcedureType");
                claimTotal =
                    claimRow.getAttribute("ClaimTotal") == null ? new Double(0) :
                    (Double) claimRow.getAttribute("ClaimTotal");
                Double dailyPenaltyFees =
                    claimRow.getAttribute("DailyPenaltyFees") == null ? new Double(0) :
                    (Double) claimRow.getAttribute("DailyPenaltyFees");
                Double dailyPenaltyRemaining =
                    claimRow.getAttribute("DailyPenaltyRemaining") == null ? new Double(0) :
                    (Double) claimRow.getAttribute("DailyPenaltyRemaining");
                
                
                BigDecimal combainedOrder = (BigDecimal) claimRow.getAttribute("CombinedOrder");
                
                if (procedureType.intValue() == 26 || procedureType.intValue() == 28 || procedureType.intValue() == 13 ||
                    procedureType.intValue() == 15) {
                    
                if( claimTotal < procedureAmount)
                {System.out.println("@@@@@@asdsdssd@@@@@2");
                    if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15) {
                  JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                    return null;
                }
                }
                }
                
                if (procedureAmount != null && remainFixedPenaltyAmount != null &&
                    procedureAmount.intValue() > remainFixedPenaltyAmount.intValue() && procedureType.intValue() != 8 &&
                    procedureType.intValue() != 10 && procedureType.intValue() != 11) {
                    System.out.println("@@@@@@@@qqqqq@@@2");
                    if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15) {
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                    return null;
                    }
                } else if (procedureAmount != null && claimTotalDaliy != null &&
                           procedureAmount.intValue() > claimTotalDaliy.intValue() && procedureType.intValue() == 8) {
                    System.out.println("@@@@@@@@aaaaa@@@2");
                    if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15) {
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                    return null;}
                }
                else  if (procedureType.intValue() == 13) {
                    if (claimTotal < procedureAmount){
                        if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15 ) {
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                        return null; }
                    }
                }
                
                if (procedureType.intValue() == 3) {
                    System.out.println(GuranteeValue2.getValue()+"procedu--"+procedureAmount+"-reFolder  "+procedureFolder.getValue());
                   
                    if(new BigDecimal(procedureFolder.getValue()+"").compareTo(new BigDecimal (0))==1  ){
                        if( new BigDecimal(GuranteeValue2.getValue()+"").doubleValue() < procedureAmount)
                        {
                                    if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15 ) {
                                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                                return null; }}
                    }}
                
                
                if (procedureType.intValue() == 14) {
                    
                    if(new BigDecimal(claimSource.getValue()+"").compareTo(new BigDecimal (0))==1  ){
                        if( new BigDecimal(penaltyValue2.getValue()+"").doubleValue() < procedureAmount)
                        {System.out.println("@@@@@ssss@@@@@@2");
                                    if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15) {
                                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                                return null;
                                    }
                                }
                    }}
                
                            if (procedureType.intValue() == 26 || procedureType.intValue() == 28 || procedureType.intValue() == 13 ||
                                procedureType.intValue() == 15) {
                                
                if( claimTotal < procedureAmount)
                {
                    System.out.println("dddddddd");
                                if (procedureType.intValue() != 26 && procedureType.intValue() != 32 && procedureType.intValue() != 13 && procedureType.intValue() != 28 && procedureType.intValue() != 15) {
                                
                                JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                        return null;
                            }
                }
                        }
                
                //  ProcedureType
                BigDecimal cancellationType = null;
                Timestamp procedureDate = null;
                Timestamp procedureEndDate = null;
                Double procedureRemaining = null;
                String procedureNotes = null;
                String collectionType = null;
                String paymentNumber = null;

                cancellationType = (BigDecimal) procedureRow.getAttribute("CancellationType");
                procedureDate = (Timestamp) procedureRow.getAttribute("ProcedureDate");
                procedureRemaining = (Double) procedureRow.getAttribute("ProcedureRemaining");
                if (procedureRow.getAttribute("ProcedureEndDate") != null) {
                    procedureEndDate = (Timestamp) procedureRow.getAttribute("ProcedureEndDate");
                }
                if (procedureRow.getAttribute("ProcedureNotes") != null) {
                    procedureNotes = procedureRow.getAttribute("ProcedureNotes").toString();
                }
                if (procedureRow.getAttribute("CollectionType") != null) {
                    collectionType = procedureRow.getAttribute("CollectionType").toString();
                }
                if (procedureRow.getAttribute("PaymentNumber") != null) {
                    paymentNumber = procedureRow.getAttribute("PaymentNumber").toString();
                }
                System.out.println("cancellationType == >" + cancellationType + "collectionType == >" + collectionType);

                //////////////////////
                if (procedureType.intValue() == 1 || procedureType.intValue() == 32 || procedureType.intValue() == 31 || procedureType.intValue() == 27 ||procedureType.intValue() == 29||procedureType.intValue() == 25 ||procedureType.intValue() == 19
                    ||procedureType.intValue() == 15 ||procedureType.intValue() == 24|| procedureType.intValue() == 2 ||  procedureType.intValue() == 14 ||procedureType.intValue() == 3 ||procedureType.intValue() == 4 || procedureType.intValue() == 7 ||
                    procedureType.intValue() == 6 || procedureType.intValue() == 9  ) {
                    System.out.println("@@@@@@@@@@@2");
                    if (procedureType.intValue() == 6) {
                        procedureAmount = remainFixedPenaltyAmount;
                        claimRow.setAttribute("DailyPenaltyFlag", "N");
                    }
                    if (procedureType.intValue() != 15){
                    

                    remainFixedPenaltyAmount = remainFixedPenaltyAmount - procedureAmount;
                    claimTotal = claimTotal + procedureAmount;
                    }
                    if (procedureType.intValue() == 15){
                    
                            remainFixedPenaltyAmount = remainFixedPenaltyAmount + procedureAmount;
                    claimTotal = claimTotal - procedureAmount;
                    }
                    if (procedureType.intValue() == 31){
                    

                  
                    previousPaid = previousPaid - procedureAmount;
                        claimRow.setAttribute("PreviousPaied", previousPaid);
                    }
                    
                    if (procedureType.intValue() == 32){
                    

                    
                    previousPaid = previousPaid - procedureAmount;
                        claimRow.setAttribute("PreviousPaied", previousPaid);
                    }
                    if (procedureType.intValue() != 32){
                    
 if (type == 1)
                    claimRow.setAttribute("FixedPenaltyAmount", remainFixedPenaltyAmount);
                    if (procedureType.intValue() != 24){
                        if (type == 1 &&(procedureType.intValue() != 6 && procedureType.intValue() != 7))
                    claimRow.setAttribute("ClaimTotal", claimTotal);
                        if(procedureType.intValue() == 6 || procedureType.intValue() == 7)
                            claimRow.setAttribute("CancelAmount", cancelAmount.add(new BigDecimal(procedureAmount)) );
                    System.out.println("########ClaimTotal##########");
                    }
                    }
                } else if (procedureType.intValue() == 5) {
                    claimRow.setAttribute("DailyPenaltyFlag", "N");
                } else if (procedureType.intValue() == 8) {
                    dailyPenaltyFees = dailyPenaltyFees + procedureAmount;
                    dailyPenaltyRemaining = claimTotalDaliy - procedureAmount;
                    claimRow.setAttribute("DailyPenaltyFees", dailyPenaltyFees);
                    claimRow.setAttribute("DailyPenaltyRemaining", dailyPenaltyRemaining);
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    procedureRow.setAttribute("ClaimTotal", dailyPenaltyRemaining);
                    if (procedureRow.getAttribute("ProcedureEndDate") != null)
                        claimRow.setAttribute("LastDailyPenaltyDate",
                                              dateFormat.format(procedureRow.getAttribute("ProcedureEndDate")));
                    else if (procedureRow.getAttribute("ProcedureDate") != null)
                        claimRow.setAttribute("PenaltyDueDate",
                                              dateFormat.format(procedureRow.getAttribute("ProcedureDate")));

                }
                else if (procedureType.intValue() == 12) {
                    if (remainFixedPenaltyAmount == null)
                        remainFixedPenaltyAmount=0.0;
                    System.out.println("remainFixedPenaltyAmount   "+remainFixedPenaltyAmount);
                                    System.out.println("dailyPenaltyFees   "+claimTotalDaliy);
                                    remainFixedPenaltyAmount = remainFixedPenaltyAmount + claimTotalDaliy;
                                    claimTotal = claimTotal + dailyPenaltyFees;
                                    claimRow.setAttribute("FixedPenaltyAmount", remainFixedPenaltyAmount);
 
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    procedureRow.setAttribute("ClaimTotal", dailyPenaltyRemaining);
                                    if (procedureRow.getAttribute("ProcedureEndDate") != null)
                                        claimRow.setAttribute("LastDailyPenaltyDate",
                                                              dateFormat.format(procedureRow.getAttribute("ProcedureEndDate")));
                                    else if (procedureRow.getAttribute("ProcedureDate") != null)
                                        claimRow.setAttribute("PenaltyDueDate",
                                                              dateFormat.format(procedureRow.getAttribute("ProcedureDate")));

                                }
                                
                
                else if (procedureType.intValue() == 13) {
                    if (remainFixedPenaltyAmount == null)
                        remainFixedPenaltyAmount=0.0;
                    System.out.println("remainFixedPenaltyAmount   "+remainFixedPenaltyAmount);
                                    System.out.println("dailyPenaltyFees   "+claimTotalDaliy);
                                    remainFixedPenaltyAmount = remainFixedPenaltyAmount + procedureAmount;
                                    claimTotal = claimTotal - procedureAmount;
                                    claimRow.setAttribute("FixedPenaltyAmount", remainFixedPenaltyAmount);
                                    claimRow.setAttribute("ClaimTotal", claimTotal);
                                    claimRow.setAttribute("ActiveFlag", "2");
                                   
                                }
                if (procedureType.intValue() == 19 || procedureType.intValue() == 25) 
                claimRow.setAttribute("ActiveFlag", "2");
                
                
                Row proceClaimRow = ExcProcedureClaimVO.createRow();
                if (procedureType.intValue() == 9) {

                    proceClaimRow.setAttribute("ValueOffInstallment",
                                               JSFUtils.resolveExpression("#{bindings.NumDaysInput.inputValue}"));
                    proceClaimRow.setAttribute("NoOffInstallments",
                                               JSFUtils.resolveExpression("#{bindings.ExcLkpGharamaInstallments1.inputValue}"));
                } else if (procedureType.intValue() == 10) {
                    procedureAmount =
                        claimRow.getAttribute("LawyerFees") == null ? null :
                        (Double) claimRow.getAttribute("LawyerFees");
                    claimRow.setAttribute("LawyerFees", 0);
                } else if (procedureType.intValue() == 11) {
                    procedureAmount =
                        claimRow.getAttribute("CourtFees") == null ? null : (Double) claimRow.getAttribute("CourtFees");
                    claimRow.setAttribute("CourtFees", 0);
                }
                if (procedureType.intValue() == 15){
                
                    proceClaimRow.setAttribute("ExternalEntity", procedureRow.getAttribute("ExternalEntity") );
                }
                proceClaimRow.setAttribute("ClaimId", claimRow.getAttribute("Id"));
                proceClaimRow.setAttribute("ProcedureTypeId", procedureType);
                System.out.println("procedureType == >" + procedureType);
                proceClaimRow.setAttribute("ProcedureDate", procedureDate);
                proceClaimRow.setAttribute("ProcedureAmount", procedureAmount);
                proceClaimRow.setAttribute("ProcedureRemaining", remainFixedPenaltyAmount);
                proceClaimRow.setAttribute("ProcedureNotes", procedureNotes);
                proceClaimRow.setAttribute("CollectionType", collectionType);
                if (type == 1)
                proceClaimRow.setAttribute("PaymentNumber", paymentNumber);
                proceClaimRow.setAttribute("CancellationType", cancellationType);
                proceClaimRow.setAttribute("ProcedureEndDate", procedureEndDate);
                OperationBinding ob = ADFUtil.findOperation("getClaimProcedureSeqNumber");
                ob.execute();
                Object proceClaimId = ob.getResult();
                proceClaimRow.setAttribute("Id", proceClaimId);
                String temp = proceClaimId+","+procedureAmount+","+procedureType+","+claimRow.getAttribute("PartyName")+","+claimRow.getAttribute("IdNumber") ;
                proceClaimRow.setAttribute("ProcedureNumber", temp);
               
                if (procedureType.intValue() == 3) {
                    System.out.println(GuranteeValue2.getValue()+"procedureFolder  "+procedureFolder.getValue());
                    if(new BigDecimal(procedureFolder.getValue()+"").intValue() > 0){
                    Row proceClaimFolderRow = ExcProcedureClaimFolderVO.createRow();  
                    proceClaimFolderRow.setAttribute("FolderProcedureId", procedureFolder.getValue());
                    proceClaimFolderRow.setAttribute("ClaimProcedureId", proceClaimId);
                    proceClaimFolderRow.setAttribute("SettlementAmount", procedureAmount);
                    }
                }
                
                if (procedureType.intValue() == 14) {
                    System.out.println(GuranteeValue2.getValue()+"procedureFolder  "+procedureFolder.getValue());
                    if(new BigDecimal(claimSource.getValue()+"").intValue() > 0){
                    Row proceClaimFolderRow = ExcsettlementPenaltyVO.createRow();  
                    proceClaimFolderRow.setAttribute("ClaimIdSource", claimSource.getValue());
                    proceClaimFolderRow.setAttribute("ClaimIdDestination", proceClaimId);
                    proceClaimFolderRow.setAttribute("SettlementAmount", procedureAmount);
                    
                    }
                }
                
                
                
                
                {
                        BigDecimal loggedInEntityId = null;
                        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                            Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
                            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
                            loggedInEntityId =
                                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
                        } else if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                                   Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
                            loggedInEntityId =
                                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
                        }
                        BigDecimal loggedInUserId = null;
                        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId() != null) {
                            loggedInUserId = new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());
                        }
                        String fileName =
                            JSFUtils.getFromPageFlowScope("fileName") == null ? "" :
                            JSFUtils.getFromPageFlowScope("fileName").toString();
                        String documentId =
                            JSFUtils.getFromPageFlowScope("documentId") == null ? "" :
                            JSFUtils.getFromPageFlowScope("documentId").toString();
                        String docName =
                            JSFUtils.getFromPageFlowScope("docName") == null ? "" :
                            JSFUtils.getFromPageFlowScope("docName").toString();
                        if(JSFUtils.getFromPageFlowScope("fileName") != null)
                        insertIntoCJFileDetails(loggedInEntityId, loggedInUserId, new BigDecimal (proceClaimId+""),
                                                documentId, docName, fileName, new BigDecimal(43),
                                                new BigDecimal(43), new BigDecimal(claimRow.getAttribute("PartyId")+""), new BigDecimal(claimRow.getAttribute("ClaimPoliceStationId")+""),
                                                new BigDecimal(claimRow.getAttribute("BusinessCaseId")+""));
                        JSFUtils.addToPageFlowScope("fileName", null);
                    }
                if (combainedOrder.compareTo(new BigDecimal(0))==1){
                OperationBinding ob2 = ADFUtil.findOperation("paidCombaied");
                ob2.getParamsMap().put("claimId", claimRow.getAttribute("Id")+"");
                ob2.getParamsMap().put("claimNumber", claimRow.getAttribute("ClaimNumber")+"");
                ob2.getParamsMap().put("claimYear", claimRow.getAttribute("ClaimYear")+"");
                ob2.getParamsMap().put("claimRepeated", claimRow.getAttribute("RepeatedClaimFlag")+"");
                ob2.getParamsMap().put("claimfolder", claimRow.getAttribute("ClaimFolderId")+"");
                ob2.getParamsMap().put("claimPolice", claimRow.getAttribute("ClaimPoliceStationId")+"");
                ob2.getParamsMap().put("amount",Double.parseDouble( claimRow.getAttribute("FixedPenaltyAmount")+""));
                ob2.execute();
                
                //ExcClaimsVO1Iterator
                }
                
                ADFUtil.findOperation("Commit").execute();
                OperationBinding ob2 = ADFUtil.findOperation("modefiyFolderExecutionFlag");
                ob2.getParamsMap().put("folderId", excFolderId);
                ob2.execute();
                if (combainedOrder.compareTo(new BigDecimal(0))==1)
                ADFUtil.findIterator("ExcClaimsVO1Iterator").getViewObject().executeQuery();
                
//  b4fara                
                if (type == 1){
                RichButton b4fara =      ((RichButton)JSFUtils.findComponentInRoot("b4fara"));
                     b4fara.setDisabled(true);
                    RichButton b202 =      ((RichButton)JSFUtils.findComponentInRoot("b202"));
                         b202.setDisabled(true);
                         AdfFacesContext.getCurrentInstance().addPartialTarget(b202);
                     AdfFacesContext.getCurrentInstance().addPartialTarget(b4fara);
                     
                JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_PROCEDURE");
                }
                else
                {
                        RichButton b4fara =      ((RichButton)JSFUtils.findComponentInRoot("b4fara"));
                             b4fara.setDisabled(true);
                            RichButton b202 =      ((RichButton)JSFUtils.findComponentInRoot("b202"));
                                 b202.setDisabled(true);
                                 AdfFacesContext.getCurrentInstance().addPartialTarget(b202);
                             AdfFacesContext.getCurrentInstance().addPartialTarget(b4fara);
                    }
                    
                procedureRow.setAttribute("ProcedureAmount", 0);
                procedureRow.setAttribute("ProcedureDate", null);
                return proceClaimId+","+procedureAmount+","+procedureType+","+claimRow.getAttribute("PartyName")+","+claimRow.getAttribute("IdNumber");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        }
    public String saveClaimProcedureFolder() {
        // Add event code here...
        saveClaimProcedures(1);
        return null;
    }

    private void insertIntoCJFileDetails(BigDecimal entityId, BigDecimal userId, BigDecimal procedureId, String did,
                                         String Ddocname, String fileName, BigDecimal typeId, BigDecimal caseSchemaId,
                                         BigDecimal partyId, BigDecimal policeId, BigDecimal businessCaseId) {

        OperationBinding opXX = ADFUtil.findOperation("getBusinessCaseDetailId");
        opXX.getParamsMap().put("businessCaseId", new Long(businessCaseId.toString()));
        BigDecimal businessCaseDetailId = (BigDecimal) opXX.execute();
        OperationBinding op = ADFUtil.findOperation("insertIntoCJFileDetailsClaimProc");
        op.getParamsMap().put("businessCaseDetailId", businessCaseDetailId);
        op.getParamsMap().put("businessCaseId", businessCaseId);
        op.getParamsMap().put("createdBy", userId);
        op.getParamsMap().put("did", did);
        op.getParamsMap().put("Ddocname", Ddocname);
        op.getParamsMap().put("entityId", entityId);
        op.getParamsMap().put("partyId", partyId);
        op.getParamsMap().put("fileName", fileName);
        op.getParamsMap().put("typeId", typeId);
        op.getParamsMap().put("Parid", policeId); //?�?�?� ?�?�?�?� ?�?�?�?�?�?�
        op.getParamsMap().put("caseSchemaId", caseSchemaId);
        op.getParamsMap().put("procedureId", procedureId);
        op.execute();
    }

    public static java.util.Date convertDomainDateToUtilDate(oracle.jbo.domain.Date domainDate) {
        java.util.Date date = null;
        if (domainDate != null) {
            java.sql.Date sqldate = domainDate.dateValue();
            date = new Date(sqldate.getTime());
        }
        return date;
    }

    public String searchClaimProcedureLog() {
        // Add event code here...
        BigDecimal entityKolya = null;
        Timestamp fromDate = null;
        Timestamp toDate = null;
        String today = null;
        Row row = ADFUtil.findIterator("ClaimProcedureLogSearchFormRVO1Iterator").getCurrentRow();

        if (row != null) {
            fromDate = (Timestamp) row.getAttribute("FromDate");
            toDate = (Timestamp) row.getAttribute("ToDate");
            if (row.getAttribute("Today") != null) {
                today = row.getAttribute("Today").toString();
            }
        }

        Row policeRow = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getCurrentRow();
        if (policeRow != null) {
            entityKolya = (BigDecimal) policeRow.getAttribute("ProsecutionKolyaId");
        }
        System.err.println("entityKolya-XX-> " + entityKolya);
        ViewObjectImpl claimVO = (ViewObjectImpl) ADFUtil.findIterator("ExcProcedureClaimLog1Iterator").getViewObject();
        BigDecimal loggedInEntityDegree = null;
        if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null) {
            loggedInEntityDegree =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString());
        }

        BigDecimal loggedInEntityIdKolya = null;
        if (loggedInEntityDegree != null && loggedInEntityDegree.intValue() == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityIdKolya =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        }

        BigDecimal loggedInEntityIdPartial = null;
        if (loggedInEntityDegree != null && loggedInEntityDegree.intValue() == 3 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityIdPartial =
                new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        String condition = " 1 = 1 ";

        if (loggedInEntityIdKolya != null) {

            condition += " AND CLAIM_ENTITY_COLYA = " + loggedInEntityIdKolya;
        } else if (loggedInEntityIdPartial != null) {

            condition += " AND CLAIM_ENTITY_PARTIAL = " + loggedInEntityIdPartial;
        }

        if (entityKolya != null) {
            condition +=
                " AND (LOGGED_IN_ENTITY_ID = " + entityKolya +
                " OR LOGGED_IN_ENTITY_ID IN (SELECT ID FROM ENTITY WHERE PARENT_ENTITY_ID = " + entityKolya + "))";
        }

        if (fromDate != null && toDate != null) {
            condition += " AND CREATED_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        }

        if (today != null && today.equals("Y")) {
            condition += " AND CREATED_DATE = TO_CHAR(SYSDATE, 'yyyy-mm-dd')";
        }

        claimVO.setWhereClause(condition);
        System.err.println(claimVO.getQuery());
        claimVO.executeQuery();

        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (claimVO.getEstimatedRowCount() == 0 && !aot.isVisible()) {
            aot.setVisible(true);
            //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        } else if (claimVO.getEstimatedRowCount() != 0 && aot.isVisible()) {
            aot.setVisible(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(aot);

        return null;
    }

    public String resetSearchClaimProcedureLog() {
        // Add event code here...
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            System.err.println("inside aot isVisible--xx--");
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }

        RichInputDate fromDate =
            (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id1");
        RichInputDate toDate =
            (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id2");
        fromDate.setValue(null);
        toDate.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toDate);

        ADFUtil.findIterator("ExcProcedureClaimLog1Iterator").getViewObject().executeEmptyRowSet();

        return null;
    }

    public void todayCBChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(false)) {
            ViewObjectImpl claimVO =
                (ViewObjectImpl) ADFUtil.findIterator("ExcProcedureClaimLog1Iterator").getViewObject();
            VariableValueManager vm = claimVO.ensureVariableManager();
            vm.setVariableValue("ClaimNumberParam", null);
            vm.setVariableValue("ClaimYearParam", null);
            vm.setVariableValue("ClaimRepeatedParam", null);
            vm.setVariableValue("ClaimTypeParam", null);
            vm.setVariableValue("ClaimPoliceParam", null);
            vm.setVariableValue("FromDateParam", null);
            vm.setVariableValue("ToDateParam", null);
            vm.setVariableValue("TodayParam", null);
            claimVO.applyViewCriteria(null);
            claimVO.executeQuery();
        } else if (valueChangeEvent.getNewValue().equals(true)) {
            RichInputDate fromDate =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id1");
            RichInputDate toDate =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id2");
            fromDate.setValue(null);
            toDate.setValue(null);
        }
    }

    public String searchPartyHasrCase() {
System.out.println("HHHHHHHHHHHHHHH");
        JSFUtils.addToSession("pSearchType", "1");
        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.userPrevMap[101010]}"))
            JSFUtils.addToSession("pEntSVar", "0");
        else
        JSFUtils.addToSession("pEntSVar", JSFUtils.getFromSession("UserEnt")+"");
        
        
       System.out.println("*********"+ JSFUtils.getFromSession("pEntSVar"));
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");

        Row searchRow = ADFUtil.findIterator("SearchByPartyFormRVO1Iterator").getCurrentRow();

        Integer searchType = null;
        String partyName = null;
        String idNumber = null;
        BigDecimal isRequired = null;
        BigDecimal claimFlag = null;
        BigDecimal paidFlag = null;
        BigDecimal policeStation = null;
        BigDecimal businessCaseTypeId = null;
        BigDecimal folderTypeId = null;
        String year = null;
        String sequenceNumber = null;
        String caseNumber = null;
        String repeatedFlag = null;
        String esteanafFlag = null;
        BigDecimal kolyaEntity = null;
        BigDecimal gozayaaEntity = null;

        if (searchRow != null) {
            searchType = (Integer) searchRow.getAttribute("SearchType");
            if (searchType == 3 || searchType == 1)
                JSFUtils.addToSession("pSearchType", "2");
            if (searchRow.getAttribute("PartyName") != null) {
                partyName = searchRow.getAttribute("PartyName").toString();
                System.out.println("partyName-1-> " + partyName);
                OperationBinding ob = ADFUtil.findOperation("getPartyNameFixedSearch");
                ob.getParamsMap().put("partyName", partyName);
                partyName = (String) ob.execute();
                System.out.println("partyName-2-> " + partyName);
                System.out.println("*******   " + partyName.split(" ").length);
                if (partyName.split(" ").length < 4) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0627\u0644\u0627\u0633\u0645 \u062b\u0644\u0627\u062b\u0649",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
                }
            }

            if (searchRow.getAttribute("IdNumber") != null) {
                idNumber = searchRow.getAttribute("IdNumber").toString();
            }
            isRequired =
                (BigDecimal) searchRow.getAttribute("IsRequired") == null ? new BigDecimal(0) :
                (BigDecimal) searchRow.getAttribute("IsRequired");
            claimFlag =
                (BigDecimal) searchRow.getAttribute("ClaimFlag") == null ? new BigDecimal(0) :
                (BigDecimal) searchRow.getAttribute("ClaimFlag");
            paidFlag =
                (BigDecimal) searchRow.getAttribute("PaidFlag") == null ? new BigDecimal(0) :
                (BigDecimal) searchRow.getAttribute("PaidFlag");
            if (searchRow.getAttribute("SequenceNumber") != null) {
                sequenceNumber = searchRow.getAttribute("SequenceNumber").toString();
            }
            if (searchRow.getAttribute("CaseNumber") != null) {
                caseNumber = searchRow.getAttribute("CaseNumber").toString();
            }
            if (searchRow.getAttribute("Year") != null) {
                year = searchRow.getAttribute("Year").toString();
            }
            if (searchRow.getAttribute("RepeatedFlag") != null) {
                repeatedFlag = searchRow.getAttribute("RepeatedFlag").toString();
            }
            businessCaseTypeId = (BigDecimal) searchRow.getAttribute("BusinessCaseTypeId");
            folderTypeId = (BigDecimal) searchRow.getAttribute("FolderTypeId");
            // policeStation = (BigDecimal) searchInventoryRow.getAttribute("PoliceStationEntityId");
            esteanafFlag =
                searchRow.getAttribute("EsteanafFlag") == null ? "N" :
                searchRow.getAttribute("EsteanafFlag").toString();
        }
        Row policeRow = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator").getCurrentRow();
        if (policeRow != null) {
            policeStation = (BigDecimal) policeRow.getAttribute("OrigEntity");
            kolyaEntity = (BigDecimal) policeRow.getAttribute("NeyabKolyaa");
            gozayaaEntity = (BigDecimal) policeRow.getAttribute("NeyabGozayaa");
        }

        ViewObject searchResultView = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getViewObject();
        ViewObject searchByPartyNameRVO = ADFUtil.findIterator("SearchByPartyNameRVO1Iterator").getViewObject();

        String condition = " 1 = 1 ";

        String conditionSearch = " 1 = 1 ";
        if (searchType != null && searchType.intValue() == 1) {
            if (aot.isVisible()) {
                aot.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
            }

            //            String condition = " 1 = 1 ";
            if (partyName != null) {
                condition += " AND PARTY_NAME like '" + partyName + "%' ";
                conditionSearch += " AND  PARTY_FIXED_FULL_NAME like '" + partyName + "%' ";
            }
            if (idNumber != null) {
                condition += " AND ID_NUMBER = '" + idNumber + "' ";
                conditionSearch += " AND ID_NUMBER = '" + idNumber + "' ";
            }
            if (isRequired != null && isRequired.intValue() != 0 && isRequired.intValue() == 1) {
                condition += " AND EXECUTION_FLAG = 1"; /** ?�?�?�?�?� **/
                conditionSearch += " AND EXECUTION_FLAG = 1"; /** ?�?�?�?�?� **/
            } else if (isRequired != null && isRequired.intValue() != 0 && isRequired.intValue() == 2) {
                condition += " AND EXECUTION_FLAG = 2"; /** ?????� ?�?�?�?�?� **/
                conditionSearch += " AND EXECUTION_FLAG = 2"; /** ?????� ?�?�?�?�?� **/
            } else if (isRequired != null && isRequired.intValue() != 0 && isRequired.intValue() == 4) {
                condition += " AND EXECUTION_FLAG = 4"; /** ?�?�?�?�?� **/
                conditionSearch += " AND EXECUTION_FLAG = 4"; /** ?�?�?�?�?� **/
            }
            if (claimFlag != null && claimFlag.intValue() != 0 && claimFlag.intValue() == 1) {
                condition += " AND PARTY_CLAIM_FLAG = 'Y'";
                conditionSearch += " AND PARTY_CLAIM_FLAG = 'Y'";
                if (paidFlag != null && paidFlag.intValue() != 0 && paidFlag.intValue() == 1) {
                    condition += " AND REMAINING = 0";
                    conditionSearch += " AND REMAINING = 0";
                } else if (paidFlag != null && paidFlag.intValue() != 0 && paidFlag.intValue() == 2) {
                    condition += " AND REMAINING > 0";
                    conditionSearch += " AND REMAINING > 0";
                }
            } else if (claimFlag != null && claimFlag.intValue() != 0 && claimFlag.intValue() == 2) {
                condition += " AND PARTY_CLAIM_FLAG = 'N'";
                conditionSearch += " AND PARTY_CLAIM_FLAG = 'N'";
            }

            if (policeStation != null) {
                condition += " AND POLICE_STATION_ENTITY_ID = " + policeStation;
                conditionSearch += " AND POLICE_STATION_ENTITY_ID = " + policeStation;
            }
            //            searchResultView.setWhereClause(condition);
            //            searchResultView.executeQuery();
        } else if (searchType != null && searchType.intValue() == 2) {
            if (aot.isVisible()) {
                aot.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
            }
            if ((policeStation == null && esteanafFlag != null && esteanafFlag.equals("N")) ||
                (kolyaEntity == null && esteanafFlag != null && esteanafFlag.equals("Y"))) {
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
                                    FacesMessage.SEVERITY_ERROR);
                return null;
            }
            //            String condition = " 1 = 1 ";
            condition += " AND SEQUENCE_NUMBER = " + sequenceNumber;
            condition += " AND YEAR = " + year;
            condition += " AND REPEATED_FLAG = '" + repeatedFlag + "' ";
            if (esteanafFlag != null && esteanafFlag.equals("Y")) {
                Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
                System.out.println("flgflgflgflgflgflgflg   -----" + flg);
                if (flg)
                    condition += " AND POLICE_STATION_ENTITY_ID = " + gozayaaEntity;
                else
                    condition += " AND POLICE_STATION_ENTITY_ID = " + kolyaEntity;
            } else if (esteanafFlag != null && esteanafFlag.equals("N")) {
                condition += " AND POLICE_STATION_ENTITY_ID = " + policeStation;
            }
            condition += " AND FOLDER_TYPE = " + folderTypeId; //SearchByHasrVC
            searchResultView.executeQuery();
            System.err.println("inside hasr--1->" + searchResultView.getQuery());
            /** ViewCriteria vc = searchResultView.getViewCriteriaManager().getViewCriteria("SearchByHasrVC");
            vc.resetCriteria();
            searchResultView.ensureVariableManager().setVariableValue("seqNumParam", sequenceNumber);
            searchResultView.ensureVariableManager().setVariableValue("yearParam", year);
            searchResultView.ensureVariableManager().setVariableValue("repeatedParam", repeatedFlag);
            searchResultView.ensureVariableManager().setVariableValue("folderTypeParam", folderTypeId);
            if (esteanafFlag != null && esteanafFlag.equals("Y")) {
                            searchResultView.ensureVariableManager().setVariableValue("policeParam", kolyaEntity);
            } else if (esteanafFlag != null && esteanafFlag.equals("N")) {
                            searchResultView.ensureVariableManager().setVariableValue("policeParam", policeStation);
            }

            searchResultView.applyViewCriteria(vc);
            searchResultView.executeQuery();**/
            System.err.println("inside hasr--2->" + searchResultView.getQuery());
            // condition += " AND CAS_TYPE_ID = " + businessCaseTypeId;
            //            searchResultView.setWhereClause(condition);
            //            searchResultView.executeQuery();
        } else if (searchType != null && searchType.intValue() == 3) {
            if (aot.isVisible()) {
                aot.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
            }
            //            if (policeStation == null) {
            //                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
            //                                    FacesMessage.SEVERITY_ERROR);
            //                return null;
            //            }
            //            String condition = " 1 = 1 ";

            String gozeyNumber = null;
            BigDecimal policeStationXX = null;
            BigDecimal businessCaseTypeXX = null;
            BigDecimal yearXX = null;
            BigDecimal caseNumberXX = null;
            if (businessCaseTypeId.intValue() == 3646 || businessCaseTypeId.intValue() == 3647 ||
                businessCaseTypeId.intValue() == 3648 || businessCaseTypeId.intValue() == 3643) {
                OperationBinding ob = ADFUtil.findOperation("getGozeyFromKoly");
                ob.getParamsMap().put("caseNumber", caseNumber);
                ob.getParamsMap().put("caseYear", year);
                ob.getParamsMap().put("businessCaseId", businessCaseTypeId);
                Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
                System.out.println("flgflgflgflgflgflgflg   -----" + flg);
                if (flg)
                    ob.getParamsMap().put("entityKolya", gozayaaEntity);
                else
                    ob.getParamsMap().put("entityKolya", kolyaEntity);
                gozeyNumber = (String) ob.execute();
                System.err.println("gozeyNumber-bean-> " + gozeyNumber);
                if (gozeyNumber != null) {
                    String[] arrOfStr = gozeyNumber.split("-", 0);
                    caseNumberXX = new BigDecimal(arrOfStr[0].trim().toString());
                    yearXX = new BigDecimal(arrOfStr[1].trim().toString());
                    businessCaseTypeXX = new BigDecimal(arrOfStr[2].trim().toString());
                    policeStationXX = new BigDecimal(arrOfStr[3].trim().toString());
                    condition += " AND CASE_NUMBER = " + caseNumberXX;
                    condition += " AND CASE_YEAR = " + yearXX;
                    //                    if (flg)
                    //                     condition += " AND POLICE_STATION_ENTITY_ID = " +  gozayaaEntity;
                    //                    else
                    //                    condition += " AND POLICE_STATION_ENTITY_ID = " + kolyaEntity;
                    condition += " AND POLICE_STATION_ENTITY_ID = " + policeStationXX;
                    condition += " AND CAS_TYPE_ID = " + businessCaseTypeXX;
                    JSFUtils.addToSession("pSearchType", "1");
                    System.out.println("******----" + JSFUtils.getFromSession("pSearchType"));

                } else {
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0648\u062c\u062f \u0631\u0642\u0645 \u062c\u0632\u0626\u0649 \u0644\u0647\u0630\u0627 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0643\u0644\u0649",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
                }
            } else {
                condition += " AND CASE_NUMBER = " + caseNumber;
                condition += " AND CASE_YEAR = " + year;
                condition += " AND POLICE_ID = " + policeStation; // POLICE_STATION_ENTITY_ID
                condition += " AND CAS_TYPE_ID = " + businessCaseTypeId;
            }
            //            searchResultView.setWhereClause(condition);
            //            searchResultView.executeQuery();
        }
        searchByPartyNameRVO.setRangeSize(-1);
        searchResultView.setRangeSize(-1);
        if (1 == searchType) {
            //           JSFUtils.addToSession("pSearchType", "1");
            JSFUtils.addToSession("pSearchTypeVar", "1");
            //           System.err.println("conditionSearch:"+conditionSearch);
            //           System.err.println("getWhereClause:"+searchByPartyNameRVO.getQuery());
            searchByPartyNameRVO.setWhereClause(conditionSearch);
            searchByPartyNameRVO.executeQuery();
            System.out.println("RRRRRRRR    "+ searchByPartyNameRVO.getQuery());
            if (searchByPartyNameRVO.getEstimatedRowCount() == 0) {
                aot.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
                //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
            }
        } else {
            searchResultView.setWhereClause(condition);
            searchResultView.executeQuery();
            System.out.println("sssssssssss    "+ searchResultView.getQuery());
            //            System.err.println("condition:"+condition);
            //                System.err.println("Query:"+searchResultView.getQuery());
            if (searchResultView.getEstimatedRowCount() == 0) {
                aot.setVisible(true);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
                //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
            }
        }


        //        System.err.println("query--xx--> " + searchResultView.getQuery());
        return null;
    }

    public void resetSearhPartyHasrCase() {
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }
        ViewObject view2 = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getViewObject();
        view2.executeEmptyRowSet();
        ViewObject searchByPartyNameRVO = ADFUtil.findIterator("SearchByPartyNameRVO1Iterator").getViewObject();
        searchByPartyNameRVO.executeEmptyRowSet();
    }

    public void resetSearhPartyHasrCaseVCL(ValueChangeEvent valueChangeEvent) {
        JSFUtils.addToSession("pSearchTypeVar", "0");
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }
        ViewObject view2 = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getViewObject();
        view2.executeEmptyRowSet();
        ViewObject view3 = ADFUtil.findIterator("SearchByPartyNameRVO1Iterator").getViewObject();
        view3.executeEmptyRowSet();
        RichInputText partyName =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it1");
        RichSelectOneChoice isRequired =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc3");
        RichSelectOneRadio isRepeated =
            (RichSelectOneRadio) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sor3");
        RichInputText seqNumber =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it3");
        RichInputText caseNumber =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it5");
        RichInputText yearHasr =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it4");
        RichInputText yearCase =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it6");
        RichSelectOneChoice folderType =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc1");
        RichSelectOneChoice businessCaseType =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc2");
        RichSelectOneChoice originEntity =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc10");
        RichSelectOneChoice gozeyaEntity =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc17");
        RichSelectBooleanCheckbox esteanafFlag =
            (RichSelectBooleanCheckbox) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sbc1");
        int sor = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        if (sor == 3) {
            if ((((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                 Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                 39)) {
                esteanafFlag.setValue(null);
                if (gozeyaEntity.isDisabled()) {
                    gozeyaEntity.setDisabled(false);
                }
                if (originEntity.isDisabled()) {
                    originEntity.setDisabled(false);
                }
            }
            seqNumber.setRequired(false);
            caseNumber.setRequired(true);
            yearHasr.setRequired(false);
            yearCase.setValue(null);
            yearCase.setRequired(true);
            folderType.setRequired(false);
            businessCaseType.setValue(null);
            businessCaseType.setRequired(true);
            isRepeated.setRequired(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(caseNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearHasr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearCase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(folderType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRepeated);
            AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(originEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(gozeyaEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(esteanafFlag);
        } else if (sor == 2) {
            if ((((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                 Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                 39)) {
                esteanafFlag.setValue(null);
                if (gozeyaEntity.isDisabled()) {
                    gozeyaEntity.setDisabled(false);
                }
                if (originEntity.isDisabled()) {
                    originEntity.setDisabled(false);
                }
            }
            seqNumber.setRequired(true);
            yearHasr.setValue(null);
            yearHasr.setRequired(true);
            yearCase.setRequired(false);
            folderType.setValue(null);
            folderType.setRequired(true);
            businessCaseType.setRequired(false);
            isRepeated.setRequired(true);
            caseNumber.setRequired(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearHasr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearCase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(folderType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRepeated);
            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(caseNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(originEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(gozeyaEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(esteanafFlag);
        } else if (sor == 1) {
            if ((((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                 Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                 39)) {
                esteanafFlag.setValue(null);
                if (gozeyaEntity.isDisabled()) {
                    gozeyaEntity.setDisabled(false);
                }
                if (originEntity.isDisabled()) {
                    originEntity.setDisabled(false);
                }
            }
            partyName.setValue(null);
            isRequired.setValue(null);
            isRepeated.setRequired(false);
            seqNumber.setRequired(false);
            caseNumber.setRequired(false);
            yearCase.setRequired(false);
            yearHasr.setRequired(false);
            folderType.setRequired(false);
            businessCaseType.setRequired(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRequired);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRepeated);
            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(caseNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearCase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearHasr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(folderType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(originEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(gozeyaEntity);
            AdfFacesContext.getCurrentInstance().addPartialTarget(esteanafFlag);

        }
    }

    public void businessCaseTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here....
        System.out.println("VCL Business case type" + valueChangeEvent.getNewValue());
        Boolean flg = (Boolean) "N".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
        if ((((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
             Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39) ||
            !flg) {
            RichSelectOneChoice gozeyaSoc =
                (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc17");
            RichSelectOneChoice policeSoc =
                (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc10");
            if (valueChangeEvent.getNewValue().equals(new BigDecimal(3646)) ||
                valueChangeEvent.getNewValue().equals(new BigDecimal(3647)) ||
                valueChangeEvent.getNewValue().equals(new BigDecimal(3648)) || valueChangeEvent.getNewValue().equals(new BigDecimal(3643))) { //5015

                System.out.println(gozeyaSoc.isDisabled() + "flgflgflgflgflgflgflg   -----" + flg);
                if (flg)
                    gozeyaSoc.setValue("");
                policeSoc.setValue("");
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.errandEntUser eq '0'}"))
                    gozeyaSoc.setDisabled(true);
                policeSoc.setDisabled(true);
            } else {
                if (gozeyaSoc.isDisabled()) {
                    gozeyaSoc.setDisabled(false);
                }
                if (policeSoc.isDisabled()) {
                    policeSoc.setDisabled(false);
                }
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(gozeyaSoc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(policeSoc);
        }
    }

    public Date getMaxDateVal() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    public Date getMinDateVal() {
        //        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        //        Date penaltyDueDate = new Date(procedureRow.getAttribute("PenaltyDueDate").toString());
        //        Calendar cal = Calendar.getInstance();
        //        cal.setTime(penaltyDueDate);
        //        cal.add(Calendar.DATE, 0);
        //        return cal.getTime();
        return null;
    }

    public void totalAmountValidator(FacesContext facesContext, UIComponent uIComponent, Object object) {
        // Add event code here...
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        BigDecimal claimTotal = null;
        BigDecimal amount = new BigDecimal(object.toString());
        if (claimRow != null) {
            claimTotal = (BigDecimal) claimRow.getAttribute("ClaimTotal");
        }
        FacesMessage fm = new FacesMessage("Amount greater than total");
        if (claimTotal != null && object != null && amount.intValue() > claimTotal.intValue()) {
            //  throw new ValidatorException(fm);






















        }
    }

    public void getNIDValues(ReturnEvent returnEvent) {
        // Add event code here...
        String clickedButton = null;
        if (JSFUtils.getFromSession("clickedButton") != null) {
            clickedButton = JSFUtils.getFromSession("clickedButton").toString();
        }
        if (clickedButton != null && clickedButton.equals("save")) {
            PersonData personData = (PersonData) JSFUtils.getFromPageFlowScope("nIdResponseParam");
            String[] arrOfStr = null;
            String firstName = null;
            String secondName = null;
            String thirdName = null;
            String fourthName = null;
            String address = null;
            String nationalId = null;
            String placeOfBirth = null;
            String dateOfBirth = null;
            String jobTitle = null;
            String updatedBy = null;
            String fullFatherName = null;
            String fullName = null;
            String partyIds = null;

            if (personData != null) {
                fullFatherName = personData.getFatherFullName();
                firstName = personData.getFirstName();
                if (fullFatherName != null) {
                    arrOfStr = fullFatherName.split(" ", 3);
                    for (String a : arrOfStr) {
                        System.out.println(a);
                    }
                    secondName = arrOfStr[0];
                    thirdName = arrOfStr[1];
                    fourthName = arrOfStr[2];
                }
                fullName = firstName + " " + fullFatherName;
                address = personData.getAddress();
                placeOfBirth = personData.getPoliceAddress() + " " + personData.getGovernate();
                dateOfBirth = personData.getBirthDate();
                nationalId = personData.getNationalId();
                jobTitle = personData.getJobDescription();
                updatedBy = ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserName().toString();
                List<BigDecimal> partyIdList = new ArrayList<BigDecimal>();
                partyIdList = (ArrayList<BigDecimal>) JSFUtils.getFromPageFlowScope("partyIdList");
                if (partyIdList != null && partyIdList.size() > 0) {
                    partyIds = partyIdList.toString();
                    partyIds = partyIds.substring(1, partyIds.length() - 1);
                } else {
                    partyIds = "-1";
                }
                OperationBinding ob = ADFUtil.findOperation("updateParParties");
                ob.getParamsMap().put("firstName", firstName);
                ob.getParamsMap().put("secondName", secondName);
                ob.getParamsMap().put("thirdName", thirdName);
                ob.getParamsMap().put("fourthName", fourthName);
                ob.getParamsMap().put("address", address);
                ob.getParamsMap().put("placeOfBirth", placeOfBirth);
                ob.getParamsMap().put("dateOfBirth", null);
                ob.getParamsMap().put("idNumber", nationalId);
                ob.getParamsMap().put("updatedBy", updatedBy);
                ob.getParamsMap().put("jobTitle", jobTitle);
                ob.getParamsMap().put("fullName", fullName);
                ob.getParamsMap().put("partyIds", partyIds);
                ob.getParamsMap().put("updateType", "Y");
                ob.execute();
            }
        } else if (clickedButton != null && clickedButton.equals("cancel")) {
            //            ADFUtil.setBoundAttributeValue("ClaimNumber", null);
            //            ADFUtil.refreshComponent("it1");
            /** --Test--
            String partyIds = null;
            List<BigDecimal> partyIdList = new ArrayList<BigDecimal>();
            partyIdList = (ArrayList<BigDecimal>) JSFUtils.getFromPageFlowScope("partyIdList");
            System.err.println("partyIdList-YY--> " + partyIdList);
            if (partyIdList != null && partyIdList.size() > 0) {
                partyIds = partyIdList.toString();
                partyIds = partyIds.substring(1, partyIds.length() - 1);
            } else {
                partyIds = "-1";
            }

            //            String c = "1981-12-10";
            //            Date dateFormat =null;
            //            try {
            //                 dateFormat = new SimpleDateFormat("dd/MM/yyyy").parse(c);
            //            } catch (ParseException e) {
            //                e.printStackTrace();
            //            }
            //            ;
            //java.util.Date date = new java.util.Date();
            //dateFormat.format(c)..parse(sDate1);

            OperationBinding ob = ADFUtil.findOperation("updateParParties");
            ob.getParamsMap().put("firstName", "XX");
            ob.getParamsMap().put("secondName", "XX");
            ob.getParamsMap().put("thirdName", "XX");
            ob.getParamsMap().put("fourthName", "XX");
            ob.getParamsMap().put("address", "XX");
            ob.getParamsMap().put("placeOfBirth", "XX");
            ob.getParamsMap().put("dateOfBirth", null);
            ob.getParamsMap().put("idNumber", "XX");
            ob.getParamsMap().put("updatedBy", "XX");
            ob.getParamsMap().put("jobTitle", "XX");
            ob.getParamsMap().put("fullName", "XX");
            ob.getParamsMap().put("partyIds", partyIds);
            ob.execute();
**/editPartyBind.setDisabled(true);
            ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").executeQuery();
        }
    }

    public void getParId(ActionEvent e) {
        // Add event code here...
        RowSetIterator rsi = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getRowSetIterator();
        Row first = null;
        if (rsi != null) {
            first = rsi.first();
        }
        List<BigDecimal> partyIdList = new ArrayList<BigDecimal>();
        if (first != null) {
            if (first.getAttribute("CheckParty") != null &&
                ((BigDecimal) first.getAttribute("CheckParty")).intValue() == 1) {
                partyIdList.add((BigDecimal) first.getAttribute("PartyId"));
            }
        }
        Row row = null;
        while (rsi.hasNext()) {
            row = rsi.next();
            if (row != null) {
                if (row.getAttribute("CheckParty") != null &&
                    ((BigDecimal) row.getAttribute("CheckParty")).intValue() == 1) {
                    partyIdList.add((BigDecimal) row.getAttribute("PartyId"));
                }
            }
        }
        if (partyIdList != null && partyIdList.size() > 0) {
            JSFUtils.addToPageFlowScope("partyIdList", partyIdList);
        }
    }

    public void checkChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        boolean editPartyBindValue = (Boolean) valueChangeEvent.getNewValue();
        boolean editPartyBindDisabled = editPartyBind.isDisabled();
        if (editPartyBindValue == true && editPartyBindDisabled == true) {
            editPartyBind.setDisabled(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(editPartyBind);
        } else if (editPartyBindValue == false && editPartyBindDisabled == false) {
            RowSetIterator rsi = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getRowSetIterator();
            Row first = null;
            if (rsi != null) {
                first = rsi.first();
            }
            List<BigDecimal> partyIdList = new ArrayList<BigDecimal>();
            if (first != null) {
                if (first.getAttribute("CheckParty") != null &&
                    ((BigDecimal) first.getAttribute("CheckParty")).intValue() == 1) {
                    partyIdList.add((BigDecimal) first.getAttribute("PartyId"));
                }
            }
            Row row = null;
            while (rsi.hasNext()) {
                row = rsi.next();
                if (row != null) {
                    if (row.getAttribute("CheckParty") != null &&
                        ((BigDecimal) row.getAttribute("CheckParty")).intValue() == 1) {
                        partyIdList.add((BigDecimal) row.getAttribute("PartyId"));
                    }
                }
            }
            if (partyIdList != null && partyIdList.size() > 1) {
                editPartyBind.setDisabled(false);
            } else {
                editPartyBind.setDisabled(true);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(editPartyBind);
        }
    }

    public void procedureTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("valueChangeEvent   " + valueChangeEvent.getNewValue());
      //  if (  Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 || Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 7 || Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 8 || Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9) {
       if (  Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 ){
        RichButton button = (RichButton) JSFUtils.findComponentInRoot("b202");
        button.setVisible(true);
        JSFUtils.refreshComponent("b202");
//            RichInputText text = (RichInputText) JSFUtils.findComponentInRoot("it86");
//            text.setDisabled(true);
//            JSFUtils.refreshComponent("it86");
        
         
//           if (  Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9 )
//        {
//                Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
//                Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
//                Object newValue = valueChangeEvent.getNewValue();
//                Double fixedPenaltyAmount = new Double(0);
//                Double procedureAmount = new Double(0);
//                BigDecimal numOfInstallments = new BigDecimal(1);
//                if (procedureRow.getAttribute("ProcedureAmount") != null)
//                    procedureAmount = (Double) procedureRow.getAttribute("ProcedureAmount");
//                if (newValue != null && claimRow.getAttribute("FixedPenaltyAmount") != null) {
//                    fixedPenaltyAmount = (Double) claimRow.getAttribute("FixedPenaltyAmount");
//
//                    if (fixedPenaltyAmount.intValue() > 0 && !newValue.toString().equals("0")) {
//                        numOfInstallments = (BigDecimal) newValue;
//                        System.err.println("result for cal ==>" +
//                                           Math.round((fixedPenaltyAmount - procedureAmount) / numOfInstallments.intValue()));
//                        procedureRow.setAttribute("NumDaysInput",
//                                                  new BigDecimal(Math.round((fixedPenaltyAmount - procedureAmount) /
//                                                                            numOfInstallments.intValue())));
//
//                        procedureRow.setAttribute("NumDaysInput",
//                                                  new BigDecimal(Math.round((fixedPenaltyAmount - procedureAmount) /
//                                                                            numOfInstallments.intValue())));
//
//                    } else
//                        procedureRow.setAttribute("NumDaysInput", 0);
//                }
//            
//            }
        
        }
        else{
                RichButton button = (RichButton) JSFUtils.findComponentInRoot("b202");
                button.setVisible(false);
                JSFUtils.refreshComponent("b202");
            
//                RichInputText text = (RichInputText) JSFUtils.findComponentInRoot("it86");
//                text.setDisabled(false);
//                JSFUtils.refreshComponent("it86");
            }
        RichPanelGroupLayout collectionTypePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl18");
        RichPanelGroupLayout validateGuranteePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl27");
        RichPanelGroupLayout paymentNumberPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl13");
        RichPanelGroupLayout procedureAmountPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl12");
        RichPanelGroupLayout procedureRemainingPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl11");
        RichPanelGroupLayout amountTextPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl10");

        RichPanelGroupLayout cancellationTypePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl157");

        RichPanelGroupLayout procedureEndDatePGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl7");
        RichPanelGroupLayout externalEntityPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl43");
        RichPanelGroupLayout numDaysPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl177");
        RichPanelGroupLayout validatePenaltyPGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl37");
        RichPanelGroupLayout  PGL =
            (RichPanelGroupLayout) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "pgl14");
        
        RichInputText paymentNumberIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it86");
        RichInputText procedureAmountIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it2");
        RichInputText procedureRemainingIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it6");
        RichInputText amountTextIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it9");
        RichInputText numDaysIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it74");
        Row row = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        
        
        Row procedureRow = row;
        
        procedureRow.setAttribute("ProcedureNumber", null);
                   procedureRow.setAttribute("NumDaysInput", null);
                   procedureRow.setAttribute("CancellationType", null);
                   procedureRow.setAttribute("ProcedureDate", null);
        procedureRow.setAttribute("ProcedureEndDate", null);
                   procedureRow.setAttribute("ProcedureAmount", null);
                   procedureRow.setAttribute("ProcedureRemaining", null);
                   procedureRow.setAttribute("AmountText", null);
                   procedureRow.setAttribute("ProcedureNotes", null);
                   procedureRow.setAttribute("CollectionType", null);
                   procedureRow.setAttribute("PaymentNumber", null);
                   procedureRow.setAttribute("NumberOfDays", null);
        
        System.err.println("procedureRow item-->" + row);
        System.err.println("ProcedureType item-->" + row.getAttribute("ProcedureType"));
        externalEntityPGL.setVisible(false);
        collectionTypePGL.setVisible(false);
        validateGuranteePGL.setVisible(false);
        procedureAmountPGL.setVisible(false);
        procedureAmountIt.setDisabled(false);
        paymentNumberPGL.setVisible(false);
        procedureRemainingPGL.setVisible(false);
        cancellationTypePGL.setVisible(false);
        procedureEndDatePGL.setVisible(false);
        amountTextPGL.setVisible(false);
        numDaysPGL.setVisible(false);
        validatePenaltyPGL.setVisible(false);
        PGL.setVisible(false);
//        procedureAmountIt.setVisible(false);
//
//        procedureRemainingIt.setVisible(false);
//        
//        amountTextIt.setVisible(false);
//        numDaysIt.setVisible(false);
        
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 5  ) {
            collectionTypePGL.setVisible(false);
            procedureEndDatePGL.setVisible(false);
            numDaysPGL.setVisible(false);

            paymentNumberPGL.setVisible(false);
            paymentNumberIt.setRequired(false);

            procedureAmountPGL.setVisible(false);
            procedureAmountIt.setRequired(false);

            procedureRemainingPGL.setVisible(false);
            procedureRemainingIt.setRequired(false);

            amountTextPGL.setVisible(false);
            amountTextIt.setRequired(false);

            cancellationTypePGL.setVisible(false);

        } 
        else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 7) {
            collectionTypePGL.setVisible(false);
            procedureEndDatePGL.setVisible(false);
            numDaysPGL.setVisible(false);

            paymentNumberPGL.setVisible(false);
            paymentNumberIt.setRequired(false);

            procedureAmountPGL.setVisible(true);
//            procedureAmountIt.setRequired(true);

            procedureRemainingPGL.setVisible(true);
            procedureRemainingIt.setRequired(true);

            amountTextPGL.setVisible(true);
            amountTextIt.setRequired(true);

            cancellationTypePGL.setVisible(true);
        } 
        else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 6 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 10 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 11) {
            collectionTypePGL.setVisible(false);
            procedureEndDatePGL.setVisible(false);
            numDaysPGL.setVisible(false);

            paymentNumberPGL.setVisible(false);
            paymentNumberIt.setRequired(false);

            procedureAmountPGL.setVisible(false);
            procedureAmountIt.setRequired(false);

            procedureRemainingPGL.setVisible(false);
            procedureRemainingIt.setRequired(false);

            amountTextPGL.setVisible(false);
            amountTextIt.setRequired(false);

            cancellationTypePGL.setVisible(true);
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 10 ||
                Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 11)
                cancellationTypePGL.setVisible(false);
        } 
        else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 2 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 3 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 4 || 
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 8 || 
                 Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 12 ||
                   Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9) {
            procedureAmountIt.setValue(null);
            numDaysIt.setValue(null);

            row.setAttribute("ProcedureRemaining", null);
            row.setAttribute("AmountText", null);

            procedureEndDatePGL.setVisible(false);
            numDaysPGL.setVisible(false);
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 8 ||
                Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 12 ||
                Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9)
                procedureEndDatePGL.setVisible(true);
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9) {
                numDaysPGL.setVisible(true);
                numDaysIt.setReadOnly(true);
                row.setAttribute("NumDaysInput", null);
            }

            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 1) {
                System.out.println("&&&&&&&&&&&&&&");
                numDaysPGL.setVisible(true);
                numDaysIt.setReadOnly(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(numDaysIt);
            }
            procedureAmountIt.setDisabled(false);
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) != 12 ){
            collectionTypePGL.setVisible(true);

            paymentNumberPGL.setVisible(true);}
            if (paymentNumberIt.isRequired() == false) {
                paymentNumberIt.setRequired(false);
            }
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) != 12 ){
            procedureAmountPGL.setVisible(true);
//            procedureAmountIt.setRequired(true);
            }
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9)
                procedureAmountIt.setRequired(false);
            if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) != 12 ){
            procedureRemainingPGL.setVisible(true);
            procedureRemainingIt.setRequired(true);

            amountTextPGL.setVisible(true);
            amountTextIt.setRequired(true);
            }
            cancellationTypePGL.setVisible(false);
            
        
            
        }
        else if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 1) {
            collectionTypePGL.setVisible(false);
            procedureEndDatePGL.setVisible(true);

            paymentNumberPGL.setVisible(false);
            paymentNumberIt.setRequired(false);

            procedureAmountPGL.setVisible(true);
            procedureAmountIt.setRequired(false);

            numDaysPGL.setVisible(true);
//            procedureAmountIt.setValue(new Double(450));
//            numDaysIt.setValue(90);
            //            Row row = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();

//            row.setAttribute("ProcedureDate", new Date());
//            row.setAttribute("NumDaysInput", new BigDecimal(90));
//            calcutionNumDays(new BigDecimal(90));
            Double amount = (Double) procedureAmountIt.getValue();
            Double fixedPenaltyFees = null;
            Double dailyPenaltyFees = null;
            Double claimTotal = null;
            if (row != null) {
                fixedPenaltyFees =
                    (Double) row.getAttribute("FixedPenaltyFees") == null ? new Double(0) :
                    (Double) row.getAttribute("FixedPenaltyFees");
                dailyPenaltyFees =
                    (Double) row.getAttribute("DailyPenaltyFees") == null ? new Double(0) :
                    (Double) row.getAttribute("DailyPenaltyFees");
                claimTotal = (Double) row.getAttribute("ClaimTotal");
                if (claimTotal != null && claimTotal > 0 && amount != null && amount > 0) {
                    if (amount > claimTotal) {
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE");
                    } else if ((fixedPenaltyFees > 0 && amount < fixedPenaltyFees) ||
                               (dailyPenaltyFees > 0 && amount < dailyPenaltyFees)) {
                        JSFUtils.showFacesErrorMessageFromBundle("bundle", "ERROR_PROCEDURE_FESS");
                    } else {
                        row.setAttribute("ProcedureRemaining", (claimTotal - amount));
                        row.setAttribute("AmountText", getAmountText(amount));
                    }
                }
            }
            procedureAmountIt.setDisabled(true);

            procedureRemainingPGL.setVisible(true);
            procedureRemainingIt.setRequired(true);

            amountTextPGL.setVisible(true);
            amountTextIt.setRequired(true);

            cancellationTypePGL.setVisible(false);
        }
        row.setAttribute("ProcedureAmount", 0);
       
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9){
            System.out.println("!!!!!!!!");
            procedureAmountPGL.setVisible(false);
            amountTextPGL.setVisible(false);
            PGL.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(PGL);
        }
        else
            PGL.setVisible(true);
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 1) {
        //            numDaysPGL.setVisible(true);
                    numDaysIt.setReadOnly(false);
                   
                }
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 13) {
        //            numDaysPGL.setVisible(true);
                    procedureAmountPGL.setVisible(true);
                   
                }  
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 2) {
        //            numDaysPGL.setVisible(true); validateGuranteePGL
                    collectionTypePGL.setVisible(false);
                    paymentNumberPGL.setVisible(false);
                   
                } 
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 3) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    validateGuranteePGL.setVisible(true);
                
                   
                } 
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 14) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    validatePenaltyPGL.setVisible(true);
                    paymentNumberPGL.setVisible(true);
                    procedureAmountPGL.setVisible(true);
                    procedureRemainingPGL.setVisible(true);
                    amountTextPGL.setVisible(true);
                   
                } 
      
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 24) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    
//                    paymentNumberPGL.setVisible(true);
                    procedureAmountPGL.setVisible(true);
                    procedureRemainingPGL.setVisible(true);
                    amountTextPGL.setVisible(true);
                   
                } 
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 15) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    externalEntityPGL.setVisible(true);
                         paymentNumberPGL.setVisible(false);
                    procedureAmountPGL.setVisible(true);
                    procedureRemainingPGL.setVisible(true);
                    amountTextPGL.setVisible(true);
                   
                } 
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 31 || 
            Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 32) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    
                         paymentNumberPGL.setVisible(false);
                    procedureAmountPGL.setVisible(true);
                    procedureRemainingPGL.setVisible(false);
                    amountTextPGL.setVisible(true);
                   
                }
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 27 || Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 29) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    
        //                    paymentNumberPGL.setVisible(true);
                    procedureAmountPGL.setVisible(true);
                    procedureRemainingPGL.setVisible(true);
                    amountTextPGL.setVisible(true);
                   
                } 
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 26 
            || Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 28) {
        //            numDaysPGL.setVisible(true); 
        collectionTypePGL.setVisible(false);
                    
        //                    paymentNumberPGL.setVisible(true);
                    procedureAmountPGL.setVisible(true);
//                    procedureRemainingPGL.setVisible(true);
                    amountTextPGL.setVisible(true);
                   
                } 
        
        if (Integer.parseInt(valueChangeEvent.getNewValue().toString()) == 9)
        {
            System.out.println("RRRRRRRRRr");
                PGL.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(PGL);
                paymentNumberPGL.setVisible(false);
                procedureAmountPGL.setVisible(false);
                procedureRemainingPGL.setVisible(false);
                amountTextPGL.setVisible(false);
            
            }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(validatePenaltyPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(validateGuranteePGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(PGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(collectionTypePGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentNumberPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(paymentNumberIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureAmountPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureAmountIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureRemainingPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureRemainingIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(amountTextPGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(amountTextIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(numDaysIt);

        AdfFacesContext.getCurrentInstance().addPartialTarget(cancellationTypePGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureEndDatePGL);
        AdfFacesContext.getCurrentInstance().addPartialTarget(numDaysPGL);
        ADFUtil.refreshComponent("id2");
    }

    public void esteanafFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        RichSelectOneChoice gozeyaSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc17");
        RichSelectOneChoice policeSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc10");
        if (valueChangeEvent != null && valueChangeEvent.getNewValue().equals(true)) {
            Boolean flg = (Boolean) "N".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
            System.out.println("flgflgflgflgflgflgflg   -----" + flg);
            if (flg)
                gozeyaSoc.setValue("");
            policeSoc.setValue("");
            if (!gozeyaSoc.isDisabled()) {
                if (flg)
                    gozeyaSoc.setDisabled(true);
            }
            if (!policeSoc.isDisabled()) {
                policeSoc.setDisabled(true);
            }
        } else {
            if (gozeyaSoc.isDisabled()) {
                gozeyaSoc.setDisabled(false);
            }
            if (policeSoc.isDisabled()) {
                policeSoc.setDisabled(false);
            }
        }

        AdfFacesContext.getCurrentInstance().addPartialTarget(gozeyaSoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(policeSoc);
    }

    public void uploadFile(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("upload file vcl ... " + valueChangeEvent.getNewValue());
        UploadedFile uf1 = (UploadedFile) valueChangeEvent.getNewValue();
        InputStream is = null;
        String fileName = uf1.getFilename() == null ? "" : uf1.getFilename();
        String documentId = null;
        String docName = null;
        RichInputFile ff =
            (RichInputFile) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "if1");
        ff.setValue(fileName);
        AdfFacesContext.getCurrentInstance().addPartialTarget(ff);
        try {
            is = uf1.getInputStream();
            System.err.println("is -- " + is);
            DataObject obj = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, uf1.getFilename());
            if (obj != null) {
                docName = obj.get("dDocName");
                documentId = obj.get("dID");
                if (documentId != null) {
                    System.err.println("uf1 : " + uf1 + "\n -- " + fileName);
                    System.err.println("fileName : " + fileName + " - documentId : " + documentId + " - docName : " +
                                       docName);
                    JSFUtils.addToPageFlowScope("fileName", fileName);
                    JSFUtils.addToPageFlowScope("documentId", documentId);
                    JSFUtils.addToPageFlowScope("docName", docName);
                } else {
                    System.err.println("file doesn't uploaded, please check UCM server status");
                    JSFUtils.showFacesErrorMessageFromBundle("bundle", "UCM");
                }
            } else {
                System.err.println("file doesn't uploaded, please check UCM server status");
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "UCM");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("file doesn't uploaded, please check UCM server status");
            JSFUtils.showFacesErrorMessageFromBundle("bundle", "UCM");
        }
    }

    public String PrintReportAction() {
        Integer criminalType = null;
        if (ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("CriminalType") !=
            null) {
            criminalType =
                Integer.parseInt(ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("CriminalType").toString());
        }
        String pathReport = "/u02/config/report/";
        System.err.println("CriminalType-x-> : " + criminalType);
        try {
            // ExecutionReport25 - executionReport26
            if (criminalType != null && criminalType == 1) { // Gona7
                super.print(pathReport, "ExecutionReport25", "procedures", "ExecutionReport25");
            } else if (criminalType != null && criminalType == 2) { // Genayat
                super.print(pathReport, "executionReport26", "procedures", "executionReport26");
            }
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }
        return null;
    }

    @Override
    public void populateParamsMap(HashMap parametersMap) {
        // TODO Implement this method
        Integer userId = null;
        Integer folderId = null;
        Integer partyId = null;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }
        if (ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("FolderId") != null) {
            folderId =
                Integer.parseInt(ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("FolderId").toString());
        }
        if (ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("PartyId") != null) {
            partyId =
                Integer.parseInt(ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getCurrentRow().getAttribute("PartyId").toString());
        }
        parametersMap.put("userId", userId);
        parametersMap.put("FOLDER_ID", folderId);
        parametersMap.put("PARTY_ID", partyId);
    }

    /** --Test--
    public String insertNIDLog() {
        BigDecimal entityId = null;
        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            entityId = new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            entityId = new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        BigDecimal userId = null;
        userId = new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());

        System.err.println("nid-data-XX-> " + entityId + " - " + userId);

        OperationBinding binding = ADFUtil.findOperation("insertNIdServiceCallLog");
        binding.getParamsMap().put("userId", userId);
        binding.getParamsMap().put("entityId", entityId);
        binding.getParamsMap().put("nId", "28007280102512");
        binding.execute();

        return null;
    }
     **/

    public static void main(String[] args) {
        //        String fullFatherName = "?�?�?�?� ?�?�?� ?�?�?�?�";
        //        List<BigDecimal> partyIdList = new ArrayList<BigDecimal>();
        //        partyIdList.add(new BigDecimal(1));
        //        partyIdList.add(new BigDecimal(2));
        //        partyIdList.add(new BigDecimal(3));
        //        System.err.println(partyIdList);
        //        String s = partyIdList.toString();
        //        s = s.substring(1, s.length() - 1);
        //        System.err.println(s);
        //        String[] arrOfStr = null;
        //        if (fullFatherName != null) {
        //            arrOfStr = fullFatherName.split(" ", 3);
        //            for (String a : arrOfStr) {
        //                System.out.println(a);
        //            }
        //        }
    }

    public void claimProctabDisclosureListener(DisclosureEvent disclosureEvent) {
        // Add event code here...
        String claimId = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow().getAttribute("Id").toString();
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcProcedureClaimVO1Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckProClaimExistsVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("claimIdParam", claimId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();

    }

    //execute master table to hnadle performance issue when search by name
    public String openPartyDetails() {
        //        System.err.println("11111111111111111111111111111");
        Integer createdByBusinessCaseId = null;
        try {
            Row currentRow = ADFUtil.findIterator("SearchByPartyNameRVO1Iterator").getCurrentRow();
            if (currentRow.getAttribute("CreatedByBusinessCaseId") != null) {
                createdByBusinessCaseId =
                    Integer.parseInt(currentRow.getAttribute("CreatedByBusinessCaseId").toString());
            }
            //filter SearchByPartyResultRVO1
            int partyId = 0;
            if (null != currentRow.getAttribute("Id")) {
                partyId = Integer.parseInt(currentRow.getAttribute("Id").toString());
            }
            int policeStationEntityId = 0;
            //                if (null!=currentRow.getAttribute("PoliceStationEntityId")) {
            //                policeStationEntityId = Integer.parseInt(currentRow.getAttribute("PoliceStationEntityId").toString());
            //            }
            //                System.err.println("createdByBusinessCaseId:"+createdByBusinessCaseId);
            //                System.err.println("partyId:"+partyId);
            filterSearchByPartyResultRVO(createdByBusinessCaseId, partyId, policeStationEntityId);
        } catch (Exception e) {
            System.err.println("Error Print Report " + e.getMessage());
        }

        return null;
    }

    private void filterSearchByPartyResultRVO(Integer createdByBusinessCaseId, Integer partyId,
                                              Integer policeStationEntityId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("SearchByPartyResultRVO1Iterator").getViewObject();

        searchClaimResultView.setNamedWhereClauseParam("ParPartyIdBind", partyId);
        searchClaimResultView.executeQuery();


        //        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("FilterByBussinessCaseId");
        //        vc.resetCriteria();
        //        searchClaimResultView.ensureVariableManager().setVariableValue("pBussinessCaseId", createdByBusinessCaseId);
        //        searchClaimResultView.ensureVariableManager().setVariableValue("pPartyId", partyId);
        //        searchClaimResultView.ensureVariableManager().setVariableValue("pPoliceStationEntityId", policeStationEntityId);
        //        searchClaimResultView.applyViewCriteria(vc);
        //        searchClaimResultView.executeQuery();
        System.err.println("searchClaimResultView.getEstimatedRowCount() :" + searchClaimResultView.getRowCount());
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (searchClaimResultView.getAllRowsInRange().length == 0) {
            aot.setVisible(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
            //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        } else {
            if (aot.isVisible()) {
                aot.setVisible(false);
                AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
            }
            JSFUtils.showPopup("p4");
        }
    }

    public void calculateGramanInstaalmentValue(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
            RichInputText numDaysIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it74");
          
        Row procedureRow = ADFUtil.findIterator("ClaimProcedureInputFormRVO1Iterator").getCurrentRow();
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        Object newValue = valueChangeEvent.getNewValue();
        Double fixedPenaltyAmount = new Double(0);
        Double procedureAmount = new Double(0);
        BigDecimal numOfInstallments = new BigDecimal(1);
        if (procedureRow.getAttribute("ProcedureAmount") != null)
            procedureAmount = (Double) procedureRow.getAttribute("ProcedureAmount");
        if (newValue != null && claimRow.getAttribute("FixedPenaltyAmount") != null) {
            fixedPenaltyAmount = (Double) claimRow.getAttribute("FixedPenaltyAmount");

            if (fixedPenaltyAmount.intValue() > 0 && !newValue.toString().equals("0")) {
                numOfInstallments = (BigDecimal) newValue;
                System.err.println("result for cal ==>" +
                                   Math.round((fixedPenaltyAmount - procedureAmount) / numOfInstallments.intValue()));
                procedureRow.setAttribute("NumDaysInput",
                                          new BigDecimal(Math.round((fixedPenaltyAmount - procedureAmount) /
                                                                    numOfInstallments.intValue())));
numDaysIt.setValue(new BigDecimal(Math.round((fixedPenaltyAmount - procedureAmount) /
                                                                    numOfInstallments.intValue())));
                procedureRow.setAttribute("NumDaysInput",
                                          new BigDecimal(Math.round((fixedPenaltyAmount - procedureAmount) /
                                                                    numOfInstallments.intValue())));

            } else{
                numDaysIt.setValue(new BigDecimal(0));

                procedureRow.setAttribute("NumDaysInput", 0);
                
            }
        }
    }
    public void posResultValueChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        // Add event code here...
                String[] arr;
                String posStatus = "";
                String transId = "";
                String billNumber = "";
                String terminal = "";
                Double amount = 0.0;
                Javaencrypt encrypt = new Javaencrypt();
                String responsePOS = "";
                responsePOS = String.valueOf(valueChangeEvent.getNewValue());
                System.out.println("responsePOS : " + responsePOS);
                byte[] bufferResponse = encrypt.encryptDecryptXor(responsePOS.getBytes(), responsePOS.length());
                String responseDecoded = new String(bufferResponse);
                System.out.println("responseDecoded :::::::   " + responseDecoded);
            responseDecoded = responseDecoded.replace("|", ";");
                arr = responseDecoded.split(";");
                if (arr != null && arr.length != 0) {
                    posStatus = arr[0];
                    System.out.println("posStatus : " + posStatus);
                    if (posStatus.equalsIgnoreCase("Approved")) {
                        transId = arr[2];
                        billNumber = arr[1];
                        terminal = arr[3];
                        amount = Double.valueOf(arr[4]);
                        OperationBinding bindingId = ADFUtil.findOperation("setPosPaymentClaims");
                        bindingId.getParamsMap().put("BillNumber", billNumber);
                        bindingId.getParamsMap().put("transactionId", transId);
                        bindingId.getParamsMap().put("terminal",terminal);
                        bindingId.getParamsMap().put("amount", amount);
                        Object obj = bindingId.execute();
                        ADFUtil.findOperation("Commit").execute();
                        ADFUtil.findIterator("ExcClaimsVO1Iterator").getViewObject().executeQuery();
                                        FacesMessage Message = new FacesMessage("\u062a\u0645 \u0627\u0644\u062f\u0641\u0639 \u0628\u0646\u062c\u0627\u062d");
                                        Message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, Message);
                    } else if (posStatus.equalsIgnoreCase("rejected")) {
    //                        JSF_UTILS.showFacesErrorMessageFromBundle("bundle", "PAID_FAILED_ONLINE");

                                        FacesMessage Message = new FacesMessage("\u0641\u0634\u0644 \u0627\u0644\u062f\u0641\u0639");
                                        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, Message);
                    }
                }

                //        onlinePaymentBtnBind.setDisabled(false);
    }

    

    public void setTestTxtBind(RichInputText testTxtBind) {
        this.testTxtBind = testTxtBind;
    }

    public RichInputText getTestTxtBind() {
        return testTxtBind;
    }

    public String vlaidateGurantee() {
        // Add event code here...
   
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        BigDecimal policeNo = new BigDecimal(0);
        BigDecimal ParYear = new BigDecimal(0);
        BigDecimal BusinessCaseType = new BigDecimal(0);
        BigDecimal OrigEntity = new BigDecimal(0);
        String idNumber   = "";
        if(JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}") != null)
          policeNo = new BigDecimal(JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")+"");
        if(JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}") != null)
          ParYear = new BigDecimal(JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}")+"");
        if(JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}") != null)
          BusinessCaseType = new BigDecimal(JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}")+"");
        if(JSFUtils.resolveExpression("#{bindings.OrigEntity1.inputValue}") != null)
          OrigEntity = new BigDecimal(JSFUtils.resolveExpression("#{bindings.OrigEntity1.inputValue}")+"");
        if(claimRow.getAttribute("IdNumber") != null)
          idNumber =  claimRow.getAttribute("IdNumber")+"" ;
         System.out.println("--"+  JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}"));
        System.out.println("--"+  JSFUtils.resolveExpression("#{bindings.ParYear.inputValue}"));
        System.out.println("--"+  JSFUtils.resolveExpression("#{bindings.BusinessCaseType.inputValue}"));
        System.out.println("--"+  JSFUtils.resolveExpression("#{bindings.OrigEntity1.inputValue}"));
        System.out.println("--"+  claimRow.getAttribute("IdNumber"));
        OperationBinding Operation = ADFUtil.findOperation("ValidatePoliceGurantee");
        Operation.getParamsMap().put("PoliceNo", policeNo);
        Operation.getParamsMap().put("BusinessCaseType", BusinessCaseType);
        Operation.getParamsMap().put("ParYear", ParYear);
        Operation.getParamsMap().put("OrigEntity", OrigEntity);
        Operation.getParamsMap().put("idNumber", idNumber);
    String result = (String) Operation.execute();
        System.out.println("result is-"+result);
        String[] split = result.split(",");
        RichInputText GuranteeValue =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it13");
        RichInputText GuranteeValue2 =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it15");
        RichInputText procedureFolder =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it16");
         if (split.length > 1) {
              BigDecimal   procId =new BigDecimal ( split[0]) ;
             Double    gurantee  = Double.parseDouble(split[1]) ;
                 Double    SettlementSum  = Double.parseDouble(split[3]) ;
               String  paymentNumber =   split[2]  ;
               if(procId.compareTo(new BigDecimal(0))==1){
                 GuranteeValue.setValue(gurantee-SettlementSum);
                   GuranteeValue2.setValue(gurantee-SettlementSum);
                   procedureFolder.setValue(procId);
               }
               else{
                 GuranteeValue.setValue("Not Found");
                   GuranteeValue2.setValue(0);
                   procedureFolder.setValue(0);
               }
             }
        
         else{
           GuranteeValue.setValue("Not Found");
             GuranteeValue2.setValue(0); 
             procedureFolder.setValue(0);
         }
        AdfFacesContext.getCurrentInstance().addPartialTarget(GuranteeValue);
        AdfFacesContext.getCurrentInstance().addPartialTarget(GuranteeValue2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(procedureFolder);
        return null;
    }

    public String validatePenalty() {
        // Add event code here...
        
        RichInputText penaltyValue =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it18");
        RichInputText penaltyValue2 =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it19");
        RichInputText claimSource =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it20");
        
        RichInputText procedurenumIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it17");
        
        RichSelectOneChoice yearCL =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc24");
        
        RichSelectOneChoice folderTypeCL =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc21");
        
        Row claimRow = ADFUtil.findIterator("ExcClaimsVO1Iterator").getCurrentRow();
        System.out.println(procedurenumIt.getValue()+"-yealCL-"+yearCL.getValue()+"-folderTypeCL-"+folderTypeCL.getValue()+"claimRow  -"+claimRow.getAttribute("ClaimPoliceStationId"));
        
        
        OperationBinding Operation = ADFUtil.findOperation("ValidateSettlementPenalty");
        Operation.getParamsMap().put("number", procedurenumIt.getValue()+"");
        Operation.getParamsMap().put("folderType", folderTypeCL.getValue()+"");
        Operation.getParamsMap().put("Year", yearCL.getValue()+"");
        Operation.getParamsMap().put("entity", claimRow.getAttribute("ClaimPoliceStationId")+"");
        Operation.getParamsMap().put("nationalID", claimRow.getAttribute("IdNumber")+"");
        String result = (String) Operation.execute();
        System.out.println("result is "+result);
        String[] split = result.split(",");
        if (split.length > 1) {
             BigDecimal   claimSourceId =new BigDecimal ( split[1]) ;
            Double    paidPenalty  = Double.parseDouble(split[0]) ;
            Double    sumSettlementPenalty  = Double.parseDouble(split[2]) ;
            
            if(claimSourceId.compareTo(new BigDecimal(0))==1){
              penaltyValue.setValue(paidPenalty-sumSettlementPenalty);
                penaltyValue2.setValue(paidPenalty-sumSettlementPenalty);
                claimSource.setValue(claimSourceId);
            }
            else{
              penaltyValue.setValue("Not Found");
                penaltyValue2.setValue(0);
                claimSource.setValue(0);
            }
            
        }
        else{
          penaltyValue.setValue("Not Found");
            penaltyValue2.setValue(0); 
            claimSource.setValue(0);
        }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(penaltyValue);
        AdfFacesContext.getCurrentInstance().addPartialTarget(penaltyValue2);
        AdfFacesContext.getCurrentInstance().addPartialTarget(claimSource);
        
        return null;
    }
}
