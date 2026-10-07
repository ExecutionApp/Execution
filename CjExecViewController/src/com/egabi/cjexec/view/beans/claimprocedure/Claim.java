package com.egabi.cjexec.view.beans.claimprocedure;

import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;

import java.util.Iterator;
import java.util.List;

import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.NameValuePairs;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.server.RowFinder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class Claim {

    private RichShowDetailItem showDetailItem;
    private RichShowDetailItem showDetailItemCase;
    private RichButton saveButton1;
    private RichButton saveButton2;

    public void setShowDetailItem(RichShowDetailItem showDetailItem) {
        this.showDetailItem = showDetailItem;
    }

    public RichShowDetailItem getShowDetailItem() {
        return showDetailItem;
    }

    public void setShowDetailItemCase(RichShowDetailItem showDetailItemCase) {
        this.showDetailItemCase = showDetailItemCase;
    }

    public RichShowDetailItem getShowDetailItemCase() {
        return showDetailItemCase;
    }

    public void setSaveButton1(RichButton saveButton1) {
        this.saveButton1 = saveButton1;
    }

    public RichButton getSaveButton1() {
        return saveButton1;
    }

    public void setSaveButton2(RichButton saveButton2) {
        this.saveButton2 = saveButton2;
    }

    public RichButton getSaveButton2() {
        return saveButton2;
    }

    private String checkNullReturnEmptyString(Object obj) {
        try {
            if (!obj.toString().equals(null))
                return obj.toString();
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }
        return "";
    }

    private double checkNullReturnZero(Object obj) {
        try {
            if (!obj.equals(null))
                return Double.valueOf(obj.toString());
        } catch (Exception e) {
            //e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private String checkNullReturnBooleanBox(Object obj) {
        try {
            System.out.println(obj.toString()+"----"+obj.toString().length());
            if (obj.toString()!= null && !obj.toString().equals("N"))
                if (obj.toString().length()>0)
                return "Y";
        } catch (Exception e) {
            //e.printStackTrace();
            return "N";
        }
        return "N";
    }

    private boolean checkNullAndEmpty(Object obj) {
        try {
            if (!obj.toString().equals(null) && !obj.toString().isEmpty())
                return false;
        } catch (Exception e) {
            //e.printStackTrace();
            return true;
        }
        return true;
    }

    public static void rowDisclosureListenerClaimTable(RowDisclosureEvent rowDisclosureEvent) {
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

    private static void makeDisclosedRowCurrent(RichTable table, RowKeySet keySet) {
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

    public String doSearch() {

        Row claimSearchRow = ADFUtil.findIterator("ClaimSearchFormRVO1Iterator").getCurrentRow();
        Row validatePoliceReportRow = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator").getCurrentRow();
        ViewObject searchResultVO = ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getViewObject();

        String esteanafFlag =
            claimSearchRow.getAttribute("EsteanafFlag") == null ? "N" :
            claimSearchRow.getAttribute("EsteanafFlag").toString();

        BigDecimal kolyaEntity = null;
        BigDecimal policeStation = null;
        BigDecimal businessCaseType = null;
        BigDecimal sequenceNumber = null;
        BigDecimal year = null;
        String repeatedFlag = null;
        BigDecimal folderTypeId = null;
        Integer searchType = null;
        BigDecimal caseNumber = null;

        if (claimSearchRow != null) {
            searchType =
                (Integer) claimSearchRow.getAttribute("SearchType") == null ? new Integer(1) :
                (Integer) claimSearchRow.getAttribute("SearchType");
            sequenceNumber = (BigDecimal) claimSearchRow.getAttribute("SequenceNumber");
            year = (BigDecimal) claimSearchRow.getAttribute("Year");
            folderTypeId = (BigDecimal) claimSearchRow.getAttribute("FolderTypeId");
            repeatedFlag =
                claimSearchRow.getAttribute("RepeatedFlag") == null ? "" :
                claimSearchRow.getAttribute("RepeatedFlag").toString();
            caseNumber = (BigDecimal) claimSearchRow.getAttribute("CaseNumber");
        }

        if (validatePoliceReportRow != null) {
            policeStation = (BigDecimal) validatePoliceReportRow.getAttribute("OrigEntity");
            kolyaEntity = (BigDecimal) validatePoliceReportRow.getAttribute("NeyabKolyaa");
        }

        if (searchType != null && searchType.intValue() == 2) {
            /**
            if (sequenceNumber == null || year == null || repeatedFlag == null ||
                (policeStation == null && esteanafFlag != null && esteanafFlag.equals("N")) ||
                (kolyaEntity == null && esteanafFlag != null && esteanafFlag.equals("Y")) || folderTypeId == null) {

                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
                                    FacesMessage.SEVERITY_ERROR);
                return null;
            }**/



            String condition = " 1 = 1 ";
            String cond = sequenceNumber + "/" + year + "/" + folderTypeId + "/" + repeatedFlag + "/";
            if (esteanafFlag != null && esteanafFlag.equals("Y")) {

                Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
                System.out.println("flgflgflgflgflgflgflg   -----" + flg);
                if (flg)
                    cond += new BigDecimal(JSFUtils.getFromSession("errandEntity") + "");
                else

                    cond += kolyaEntity;
            } else if (esteanafFlag != null && esteanafFlag.equals("N")) {
                cond += policeStation;
            }
            condition +=
                " AND (SEQ_NUMBER LIKE '%" + cond + "' OR SEQ_NUMBER LIKE '%" + cond + "%' OR SEQ_NUMBER LIKE '" +
                cond + "%')";
            System.err.println("condition : " + condition);
            searchResultVO.setWhereClause(condition);
            searchResultVO.executeQuery();

        } else if (searchType != null && searchType.intValue() == 1) {

            businessCaseType =
                (BigDecimal) claimSearchRow.getAttribute("BusinessCaseTypeId") == null ? new BigDecimal(0) :
                (BigDecimal) claimSearchRow.getAttribute("BusinessCaseTypeId");
            /**
           if (caseNumber == null || year == null || businessCaseType == null) {
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
                                    FacesMessage.SEVERITY_ERROR);
                return null;
            }**/

            String condition = " 1 = 1 ";
            String gozeyNumber = null;
            BigDecimal policeStationXX = null;
            BigDecimal businessCaseTypeXX = null;
            BigDecimal yearXX = null;
            BigDecimal caseNumberXX = null;
            if (businessCaseType.intValue() == 3646 || businessCaseType.intValue() == 3647 ||
                businessCaseType.intValue() == 3648 || businessCaseType.intValue() == 3643) {
                OperationBinding ob = ADFUtil.findOperation("getGozeyFromKoly");
                ob.getParamsMap().put("caseNumber", caseNumber);
                ob.getParamsMap().put("caseYear", year);
                ob.getParamsMap().put("businessCaseId", businessCaseType);

                Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
                System.out.println("flgflgflgflgflgflgflg   -----" + flg);
                if (flg)
                    ob.getParamsMap().put("entityKolya", new BigDecimal(JSFUtils.getFromSession("errandEntity") + ""));
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
                    condition +=
                        " AND BUSINESS_CASE_NUMBER LIKE '" + caseNumberXX + "/" + yearXX + "/" + businessCaseTypeXX +
                        "/" + policeStationXX + "%'";
                } else {
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0648\u062c\u062f \u0631\u0642\u0645 \u062c\u0632\u0626\u0649 \u0644\u0647\u0630\u0627 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0643\u0644\u0649",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
                }
            } else {
                condition +=
                    " AND BUSINESS_CASE_NUMBER LIKE '" + caseNumber + "/" + year + "/" + businessCaseType + "/" +
                    policeStation + "%'";
            }

            searchResultVO.setWhereClause(condition);
            searchResultVO.executeQuery();
            //SearchClaimResultRVOCriteria
            //            ViewCriteria vc = searchResultVO.getViewCriteriaManager().getViewCriteria("SearchClaimResultRVOCriteria");
            //            //vc.resetCriteria();
            //            searchResultVO.applyViewCriteria(vc);
            //            searchResultVO.executeQuery();
            System.err.println("QUERY : " + searchResultVO.getQuery());
        }
        saveButton2.setVisible(true);
        ADFUtil.refreshComponent("b8");

        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (searchResultVO.getEstimatedRowCount() == 0 && !aot.isVisible()) {
            aot.setVisible(true);
            ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject().executeEmptyRowSet();
            //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        } else if (searchResultVO.getEstimatedRowCount() != 0 && aot.isVisible()) {
            aot.setVisible(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(aot);

        return null;
    }

    public String resetSearh() {
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            System.err.println("inside aot isVisible--xx--");
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }
        ViewObject vo = ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getViewObject();
        vo.executeEmptyRowSet();
        return null;
    }

    public void businessCaseTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
        System.out.println("flgflgflgflgflgflgflg   -----" + flg);

        System.err.println("-businessCaseTypeVCL-XX-> " + valueChangeEvent.getNewValue());
        if ((((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
             Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39) ||
            flg) {
            RichSelectOneChoice gozeyaSoc =
                (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc5");
            RichSelectOneChoice policeSoc =
                (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc6");
            if ((valueChangeEvent.getNewValue().equals(new BigDecimal(3646)) ||
                 valueChangeEvent.getNewValue().equals(new BigDecimal(3647)) ||
                 valueChangeEvent.getNewValue().equals(new BigDecimal(3648)) ||
                 valueChangeEvent.getNewValue().equals(new BigDecimal(3643)))) {
                if (!flg)
                    gozeyaSoc.setValue("");
                policeSoc.setValue("");
                //                if(!flg)
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

    public void resetSearhResults(ValueChangeEvent valueChangeEvent) {
        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }

        int sor = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        RichInputText seqNumber =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it1");
        RichInputText yearHasr =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it2");
        RichSelectOneChoice folderType =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc1");
        RichSelectOneRadio isRepeated =
            (RichSelectOneRadio) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sor3");

        RichInputText caseNumber =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it5");
        RichInputText yearCase =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it4");
        RichSelectOneChoice businessCaseType =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc9");
        if (sor == 1) {

            seqNumber.setRequired(false);
            yearHasr.setRequired(false);
            folderType.setRequired(false);
            isRepeated.setRequired(false);

            caseNumber.setRequired(true);
            yearCase.setRequired(true);
            businessCaseType.setRequired(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearHasr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(folderType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRepeated);

            AdfFacesContext.getCurrentInstance().addPartialTarget(caseNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearCase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseType);
        } else if (sor == 2) {

            caseNumber.setRequired(false);
            yearCase.setRequired(false);
            businessCaseType.setRequired(false);

            seqNumber.setRequired(true);
            yearHasr.setRequired(true);
            folderType.setRequired(true);
            isRepeated.setRequired(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(seqNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearHasr);
            AdfFacesContext.getCurrentInstance().addPartialTarget(folderType);
            AdfFacesContext.getCurrentInstance().addPartialTarget(isRepeated);

            AdfFacesContext.getCurrentInstance().addPartialTarget(caseNumber);
            AdfFacesContext.getCurrentInstance().addPartialTarget(yearCase);
            AdfFacesContext.getCurrentInstance().addPartialTarget(businessCaseType);
        }

//        ViewObject vo = ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getViewObject();
//        vo.executeEmptyRowSet();

    }

    public void rowDsclsrLstnClaim(RowDisclosureEvent rowDisclosureEvent) {
        rowDisclosureListenerClaimTable(rowDisclosureEvent);

        ADFUtil.findIterator("ClaimInputFormRVO1Iterator").executeQuery();
        ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject().executeEmptyRowSet();

        RichInputText claimNumberIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it12");
        RichSelectOneChoice claimYearSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc3");
        RichSelectBooleanCheckbox repeatedClaimFlagSbc =
            (RichSelectBooleanCheckbox) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sbc3");
        RichInputText claimNotesIt =
            (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it20");

        claimNumberIt.setValue("");
        claimYearSoc.setValue("");
        repeatedClaimFlagSbc.setValue("");
        claimNotesIt.setValue("");

        claimNumberIt.setDisabled(false);
        claimYearSoc.setDisabled(false);
        repeatedClaimFlagSbc.setDisabled(false);
        claimNotesIt.setDisabled(false);

        AdfFacesContext.getCurrentInstance().addPartialTarget(claimNumberIt);
        AdfFacesContext.getCurrentInstance().addPartialTarget(claimYearSoc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(repeatedClaimFlagSbc);
        AdfFacesContext.getCurrentInstance().addPartialTarget(claimNotesIt);

        saveButton2.setDisabled(false);
        AdfFacesContext.getCurrentInstance().addPartialTarget(saveButton2);

        if (showDetailItem.isDisclosed()) {
            Row currentRow = ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getCurrentRow();
            Double fixedPenaltyAmount = 0.0;
            Double fixedPenaltyFees = 0.0;
            Double dailyPenaltyAmount = 0.0;
            Double dailyPenaltyFees = 0.0;
            String penaltyDueDate = null;
            BigDecimal dailyFixedFlag = null;
            BigDecimal businessCaseId = null;
            if (currentRow != null) {
                if (currentRow.getAttribute("PenaltyPerParty") != null) {
                    fixedPenaltyAmount =
                        new Double(currentRow.getAttribute("PenaltyPerParty").toString().split(",", 0)[0].trim());
                }
                if (currentRow.getAttribute("PenaltyPerPartyFees") != null) {
                    fixedPenaltyFees =
                        new Double(currentRow.getAttribute("PenaltyPerPartyFees").toString().split(",", 0)[0].trim());
                }
                if (currentRow.getAttribute("DailyPerParty") != null) {
                    dailyPenaltyAmount =
                        new Double(currentRow.getAttribute("DailyPerParty").toString().split(",", 0)[0].trim());
                }
                if (currentRow.getAttribute("DailyPerPartyFees") != null) {
                    dailyPenaltyFees =
                        new Double(currentRow.getAttribute("DailyPerPartyFees").toString().split(",", 0)[0].trim());
                }
                if (currentRow.getAttribute("PenaltyDueDate") != null) {
                    penaltyDueDate = currentRow.getAttribute("PenaltyDueDate").toString();
                }
                dailyFixedFlag =
                    currentRow.getAttribute("DailyFixedFlag") == null ? new BigDecimal(0) :
                    (BigDecimal) currentRow.getAttribute("DailyFixedFlag");

                businessCaseId =
                    currentRow.getAttribute("BusinessCaseId") == null ? new BigDecimal(0) :
                    (BigDecimal) currentRow.getAttribute("BusinessCaseId");
            }

            OperationBinding ob = ADFUtil.findOperation("getClaimType");
            ob.getParamsMap().put("businessCaseId", businessCaseId);
            int count = (Integer) ob.execute();
            System.err.println("count-claimType-bean-> " + count);
            Double claimTotal = null;

            Row row = ADFUtil.findIterator("ClaimInputFormRVO1Iterator").getCurrentRow();
            if (row != null) {
                if (dailyFixedFlag.intValue() == 2) { // Fixed - && fixedPenaltyAmount != null && fixedPenaltyAmount.intValue() > 0
                    claimTotal = Double.sum(fixedPenaltyAmount, fixedPenaltyFees);
                    row.setAttribute("FixedPenaltyAmount", fixedPenaltyAmount);
                    row.setAttribute("FixedPenaltyFees", fixedPenaltyFees);
                    row.setAttribute("ClaimTotal", claimTotal);
                    row.setAttribute("ClaimAmountToText", getClaimAmountText(claimTotal));
                }
                if (dailyFixedFlag.intValue() == 1) { // Daily - && dailyPenaltyAmount != null && dailyPenaltyAmount.intValue() > 0
                    claimTotal = Double.sum(dailyPenaltyAmount, dailyPenaltyFees);
                    row.setAttribute("DailyPenaltyFlag", "Y");
                    row.setAttribute("DailyPenaltyAmount", dailyPenaltyAmount);
                    row.setAttribute("DailyPenaltyFees", dailyPenaltyFees);
                    row.setAttribute("PenaltyDueDate", penaltyDueDate);
                    row.setAttribute("OriginalPenaltyDueDate", penaltyDueDate);
                    row.setAttribute("ClaimTotal", claimTotal);
                    row.setAttribute("ClaimAmountToText", getClaimAmountText(claimTotal));
                }

                row.setAttribute("ClaimTypeId", count > 0 ? 2 : 1);
            }
        }
    }

    public Object getClaimAmountText(Double amount) {
        OperationBinding ob = ADFUtil.findOperation("numberToText");
        ob.getParamsMap().put("amount", amount);
        return ob.execute();
    }

    public String insertClaim() {

        Row claimInputRow = ADFUtil.findIterator("ClaimInputFormRVO1Iterator").getCurrentRow();
        String claimNumber = null;
        String claimYear = null;
        String repeatedClaimFlag = null;
        Double fixedPenaltyAmount = null;
        Double fixedPenaltyFees = null;
        Double claimTotal = null;
        String dailyPenaltyFlag = null;
        Double dailyPenaltyAmount = null;
        Double dailyPenaltyFees = null;
        String dailyPenaltyDate = null;
        String penaltyDueDate = null;
        String claimRemarks = null;
        //        BigDecimal claimTypeId = null;
        if (claimInputRow != null) {
            claimNumber = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimNumber"));
            claimYear = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimYear"));
            repeatedClaimFlag = checkNullReturnBooleanBox(claimInputRow.getAttribute("RepeatedClaimFlag"));
            fixedPenaltyAmount = (Double) claimInputRow.getAttribute("FixedPenaltyAmount");
            fixedPenaltyFees = (Double) claimInputRow.getAttribute("FixedPenaltyFees");
            claimTotal = (Double) claimInputRow.getAttribute("ClaimTotal");
            dailyPenaltyFlag = checkNullReturnBooleanBox(claimInputRow.getAttribute("DailyPenaltyFlag"));
            dailyPenaltyAmount = (Double) claimInputRow.getAttribute("DailyPenaltyAmount");
            dailyPenaltyFees = (Double) claimInputRow.getAttribute("DailyPenaltyFees");
            dailyPenaltyDate = checkNullReturnEmptyString(claimInputRow.getAttribute("DailyPenaltyDate"));
            penaltyDueDate = checkNullReturnEmptyString(claimInputRow.getAttribute("PenaltyDueDate"));
            claimRemarks = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimRemarks"));
            //            claimTypeId=(BigDecimal) claimInputRow.getAttribute("ClaimTypeId");
        }

        Row claimResultRow = ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getCurrentRow();

        String idNumber = null;
        String folderId = null;
        String kolyaEntityId = null;
        String partialEntityId = null;
        String policeEntityId = null;
        String partyId = null;
        String partyName = null;
        String resPartyId = null;
        String sequenceNumber = null;
        String businessCaseId = null;
        String caseTypeId = null;
        BigDecimal parPoliceId = null;
        String penaltyPerParty = null;
        String dailyPerParty = null;
        BigDecimal penaltyType = null;
        if (claimResultRow != null) {
            idNumber = checkNullReturnEmptyString(claimResultRow.getAttribute("IdNumber"));
            folderId = checkNullReturnEmptyString(claimResultRow.getAttribute("FolderId"));
            kolyaEntityId = checkNullReturnEmptyString(claimResultRow.getAttribute("KolyaEntityId"));
            partialEntityId = checkNullReturnEmptyString(claimResultRow.getAttribute("ParentEntityId")); //Partial
            policeEntityId =
                checkNullReturnEmptyString(claimResultRow.getAttribute("PoliceId")); //PoliceStationEntityId
            partyId = checkNullReturnEmptyString(claimResultRow.getAttribute("PartyId"));
            partyName = checkNullReturnEmptyString(claimResultRow.getAttribute("PartyName"));
            resPartyId = checkNullReturnEmptyString(claimResultRow.getAttribute("ResPartyId"));
            sequenceNumber = checkNullReturnEmptyString(claimResultRow.getAttribute("SeqNumberFull"));
            businessCaseId = checkNullReturnEmptyString(claimResultRow.getAttribute("BusinessCaseId"));
            caseTypeId = checkNullReturnEmptyString(claimResultRow.getAttribute("CasTypeId"));
            parPoliceId =
                claimResultRow.getAttribute("CasTypeId") == null ? new BigDecimal(0) :
                (BigDecimal) claimResultRow.getAttribute("CasTypeId");
            penaltyPerParty = checkNullReturnEmptyString(claimResultRow.getAttribute("PenaltyPerParty"));
            dailyPerParty = checkNullReturnEmptyString(claimResultRow.getAttribute("DailyPerParty"));
            penaltyType =
                claimResultRow.getAttribute("DailyFixedFlag") == null ? new BigDecimal(0) :
                (BigDecimal) claimResultRow.getAttribute("DailyFixedFlag");
        }
        if (kolyaEntityId != null) {
            kolyaEntityId = kolyaEntityId.toString().split(",", 0)[0].trim();
        }
        if (partialEntityId != null) {
            partialEntityId = partialEntityId.toString().split(",", 0)[0].trim();
        }
        if (policeEntityId != null) {
            policeEntityId = policeEntityId.toString().split(",", 0)[0].trim();
        }

        if (checkNullAndEmpty(claimNumber) || checkNullAndEmpty(claimYear)) {
            ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0648 \u0633\u0646\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (checkClaimExist(claimNumber, claimYear, repeatedClaimFlag, policeEntityId, folderId))
        {
            ADFUtil.showMessage("\u062a\u0648\u062c\u062f \u0645\u0637\u0627\u0644\u0628\u0629 \u0633\u0627\u0628\u0642\u0629",
                                FacesMessage.SEVERITY_INFO);
            return null;
        } else {

            ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
            ViewCriteria vc =
                searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckFolderIdExistsForClaimVC");
            vc.resetCriteria();
            System.err.println("folderId-->" + folderId);
            searchClaimResultView.ensureVariableManager().setVariableValue("folderIdParam", folderId);
            searchClaimResultView.ensureVariableManager().setVariableValue("claimNumberParam", claimNumber);
            searchClaimResultView.ensureVariableManager().setVariableValue("claimYearParam", claimYear);
            searchClaimResultView.ensureVariableManager().setVariableValue("repeatedFlagParam", repeatedClaimFlag);
            searchClaimResultView.applyViewCriteria(vc);

            searchClaimResultView.executeQuery();

            BigDecimal fixedPenaltyAmountZZ = null;
            BigDecimal fixedPenaltyFeesZZ = null;
            BigDecimal dailyPenaltyAmountZZ = null;
            BigDecimal dailyPenaltyFeesZZ = null;
            if (searchClaimResultView.getEstimatedRowCount() > 0) {
                Row claimRow = searchClaimResultView.first();
                BigDecimal resParty =
                    claimRow.getAttribute("ClaimResPartyId") == null ? new BigDecimal(0) :
                    new BigDecimal(claimRow.getAttribute("ClaimResPartyId").toString());

                claimRow.setAttribute("DailyPenaltyFlag", 'X'); // Fixed & Daily
                if (Integer.parseInt(penaltyPerParty) > 0) {
                    System.err.println("@@@@@@@@@@@@@@@@@@@@@");
                    OperationBinding ob1 = ADFUtil.findOperation("getPenaltyPerPartyWithFlag");

                    ob1.getParamsMap().put("resParty", resParty);
                    ob1.getParamsMap().put("flag", new BigDecimal(1));
                    fixedPenaltyAmountZZ = (BigDecimal) ob1.execute();

                    OperationBinding ob2 = ADFUtil.findOperation("getPenaltyPerPartyWithFlag");
                    ob2.getParamsMap().put("resParty", resParty);
                    ob2.getParamsMap().put("flag", new BigDecimal(2));
                    fixedPenaltyFeesZZ = (BigDecimal) ob2.execute();

                    System.err.println(fixedPenaltyAmountZZ + " - " + fixedPenaltyFeesZZ);

                    claimRow.setAttribute("FixedPenaltyAmount", fixedPenaltyAmountZZ);
                    claimRow.setAttribute("FixedPenaltyFees", fixedPenaltyFeesZZ);

                } else if (Integer.parseInt(dailyPerParty) > 0) {

                    System.err.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                    OperationBinding ob3 = ADFUtil.findOperation("getDailyPerPartyWithFlag");
                    ob3.getParamsMap().put("resParty", resParty);
                    ob3.getParamsMap().put("flag", new BigDecimal(3));
                    dailyPenaltyAmountZZ = (BigDecimal) ob3.execute();

                    OperationBinding ob4 = ADFUtil.findOperation("getDailyPerPartyWithFlag");
                    ob4.getParamsMap().put("resParty", resParty);
                    ob4.getParamsMap().put("flag", new BigDecimal(4));
                    dailyPenaltyFeesZZ = (BigDecimal) ob4.execute();

                    System.err.println(dailyPenaltyAmountZZ + " - " + dailyPenaltyFeesZZ);

                    claimRow.setAttribute("DailyPenaltyAmount", dailyPenaltyAmountZZ);
                    claimRow.setAttribute("DailyPenaltyFees", dailyPenaltyFeesZZ);
                    claimRow.setAttribute("OriginalPenaltyDueDate", penaltyDueDate);
                    claimRow.setAttribute("PenaltyDueDate", penaltyDueDate);
                }
                ADFUtil.findOperation("Commit1").execute();
                if (folderId != null) {
                    String[] arrOfStr = folderId.split(",", 0);
                    for (String folderIdXX : arrOfStr) {
                        folderIdXX = folderIdXX.trim();
                        System.out.println(folderIdXX);
                        ViewObjectImpl folderDataVO =
                            (ViewObjectImpl) ADFUtil.findIterator("ExcFolderDataViewVO1Iterator").getViewObject();
                        RowFinder rowFinder = (folderDataVO).lookupRowFinder("FolderDataRowFinder");
                        NameValuePairs nvp = new NameValuePairs();
                        nvp.setAttribute("FolderIdParam", folderIdXX);
                        RowIterator rowFound = rowFinder.execute(nvp, (folderDataVO).getDefaultRowSet());
                        if (rowFound.hasNext()) {
                            Row folderRow = rowFound.next();
                            // rowXX.setAttribute("ClaimIssueFlag", 1); // Hasr has claim

                            String fixedDailyFlag =
                                folderRow.getAttribute("ClaimFixedDailyFlag") == null ? "" :
                                folderRow.getAttribute("ClaimFixedDailyFlag").toString();

                            if ((penaltyPerParty != null && Integer.parseInt(penaltyPerParty) > 0 &&
                                 dailyPerParty != null && Integer.parseInt(dailyPerParty) == 0) ||
                                (dailyPerParty != null && Integer.parseInt(dailyPerParty) > 0 &&
                                 penaltyPerParty != null && Integer.parseInt(penaltyPerParty) == 0)) {
                                folderRow.setAttribute("ClaimIssueFlag", 1); // Hasr has claim
                            } else if ((penaltyPerParty != null && Integer.parseInt(penaltyPerParty) > 0) &&
                                       (dailyPerParty != null && Integer.parseInt(dailyPerParty) > 0)) {
                                if (penaltyType.intValue() == 2 && fixedDailyFlag.equals("00")) { // Fixed
                                    folderRow.setAttribute("ClaimFixedDailyFlag", "01");
                                } else if (penaltyType.intValue() == 2 && fixedDailyFlag.equals("10")) { // Fixed
                                    folderRow.setAttribute("ClaimFixedDailyFlag", "11");
                                    folderRow.setAttribute("ClaimIssueFlag", 1); // Hasr has claim
                                } else if (penaltyType.intValue() == 1 && fixedDailyFlag.equals("00")) { // Daily
                                    folderRow.setAttribute("ClaimFixedDailyFlag", "10");
                                } else if (penaltyType.intValue() == 1 && fixedDailyFlag.equals("01")) { // Daily
                                    folderRow.setAttribute("ClaimFixedDailyFlag", "11");
                                    folderRow.setAttribute("ClaimIssueFlag", 1); // Hasr has claim
                                }
                            }
                        }
                    }
                }
                ADFUtil.findOperation("Commit").execute();
            } else {
                OperationBinding ob = ADFUtil.findOperation("insertClaimRow");
                ob.getParamsMap().put("claimNumber", claimNumber);
                ob.getParamsMap().put("claimYear", claimYear);
                ob.getParamsMap().put("repeatedClaimFlag", repeatedClaimFlag);
                ob.getParamsMap().put("fixedPenaltyAmount", fixedPenaltyAmount);
                ob.getParamsMap().put("fixedPenaltyFees", fixedPenaltyFees);
                ob.getParamsMap().put("claimTotal", claimTotal);
                ob.getParamsMap().put("dailyPenaltyFlag", dailyPenaltyFlag);
                ob.getParamsMap().put("dailyPenaltyAmount", dailyPenaltyAmount);
                ob.getParamsMap().put("dailyPenaltyFees", dailyPenaltyFees);
                ob.getParamsMap().put("dailyPenaltyDate", dailyPenaltyDate);
                ob.getParamsMap().put("penaltyDueDate", penaltyDueDate);
                ob.getParamsMap().put("claimRemarks", claimRemarks);
                ob.getParamsMap().put("idNumber", idNumber);
                ob.getParamsMap().put("partyId", partyId);
                ob.getParamsMap().put("partyName", partyName);
                ob.getParamsMap().put("folderId", folderId);
                ob.getParamsMap().put("kolyaEntityId", kolyaEntityId);
                ob.getParamsMap().put("parialEntityId", partialEntityId);
                ob.getParamsMap().put("policeEntityId", policeEntityId);
                ob.getParamsMap().put("sequenceNumber", sequenceNumber);
                ob.getParamsMap().put("resPartyId", resPartyId);
                ob.getParamsMap().put("businessCaseId", businessCaseId);
                ob.getParamsMap().put("caseTypeId", caseTypeId);
                ob.getParamsMap().put("parPoliceId", parPoliceId);
                ob.execute();
            }
            RichInputText claimNumberIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it12");
            RichSelectOneChoice claimYearSoc =
                (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc3");
            RichSelectBooleanCheckbox repeatedClaimFlagSbc =
                (RichSelectBooleanCheckbox) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(),
                                                                   "sbc3");
            RichInputText claimAmountIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it15");
            RichInputText claimExpensesIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it16");
            RichSelectBooleanCheckbox DailyPenaltyFlagSbc =
                (RichSelectBooleanCheckbox) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(),
                                                                   "sbc2");
            RichInputText DailyPenaltyAmountIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it18");
            RichInputDate OriginalPenaltyDueDateId =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id49");
            RichInputText claimTotalIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it17");
            RichInputText ClaimAmountToTextIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it132");
            RichInputText claimNotesIt =
                (RichInputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "it20");
            claimNumberIt.setValue("");
            claimYearSoc.setValue("");
            repeatedClaimFlagSbc.setValue("");
            claimAmountIt.setValue("");
            claimExpensesIt.setValue("");
            DailyPenaltyFlagSbc.setValue("");
            DailyPenaltyAmountIt.setValue("");
            OriginalPenaltyDueDateId.setValue("");
            claimTotalIt.setValue("");
            ClaimAmountToTextIt.setValue("");
            claimNotesIt.setValue("");

            claimNumberIt.setDisabled(true);
            claimYearSoc.setDisabled(true);
            repeatedClaimFlagSbc.setDisabled(true);
            claimNotesIt.setDisabled(true);

            AdfFacesContext.getCurrentInstance().addPartialTarget(claimNumberIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(claimYearSoc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(repeatedClaimFlagSbc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(claimAmountIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(claimExpensesIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(DailyPenaltyFlagSbc);
            AdfFacesContext.getCurrentInstance().addPartialTarget(DailyPenaltyAmountIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(OriginalPenaltyDueDateId);
            AdfFacesContext.getCurrentInstance().addPartialTarget(claimTotalIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(ClaimAmountToTextIt);
            AdfFacesContext.getCurrentInstance().addPartialTarget(claimNotesIt);

            saveButton2.setDisabled(true);
            AdfFacesContext.getCurrentInstance().addPartialTarget(saveButton2);

            ADFUtil.showMessage("\u062a\u0645 \u0627\u0636\u0627\u0641\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                FacesMessage.SEVERITY_INFO);
            ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getViewObject().executeQuery();
            return null;
        }
    }

    public boolean checkClaimExist(String claimNumber, String claimYear, String repeated, String policeId,
                                   String folderId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckClaimExistsVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("claimNumberParam", claimNumber);
        searchClaimResultView.ensureVariableManager().setVariableValue("claimYearParam", claimYear);
        searchClaimResultView.ensureVariableManager().setVariableValue("repeatedFlagParam", repeated);
        searchClaimResultView.ensureVariableManager().setVariableValue("policeIdParam", policeId);
        searchClaimResultView.ensureVariableManager().setVariableValue("folderIdParam", folderId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        if (searchClaimResultView.getEstimatedRowCount() > 0)
            return true;
        else
            return false;

    }
    
    
    public boolean checkClaimExist2( String excFolderId, String claimYear, String repeated, String policeId,
                                   String folderId) {
        
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
        ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckClaimExistsVC2");
        vc.resetCriteria();
//        searchClaimResultView.ensureVariableManager().setVariableValue("claimNumberParam", claimNumber);
        searchClaimResultView.ensureVariableManager().setVariableValue("claimYearParam", claimYear);
        searchClaimResultView.ensureVariableManager().setVariableValue("repeatedFlagParam", repeated);
        searchClaimResultView.ensureVariableManager().setVariableValue("policeIdParam", policeId);
        searchClaimResultView.ensureVariableManager().setVariableValue("folderIdParam", folderId);
        searchClaimResultView.ensureVariableManager().setVariableValue("resPartyIdParam", excFolderId);
        
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        System.out.println("claimYear"+claimYear);
        System.out.println("repeated"+repeated);
        System.out.println("policeId"+policeId);
        System.out.println("folderId"+folderId);
        System.out.println("excFolderId"+excFolderId);
        System.out.println("searchClaimResultView.getRowCount() --"+searchClaimResultView.getRowCount() );
        if (searchClaimResultView.getRowCount() > 0)
            return true;
        else
            return false;

    }
    

    public boolean checkFolderIdClaimExist(String folderId) {
        ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
        ViewCriteria vc =
            searchClaimResultView.getViewCriteriaManager().getViewCriteria("CheckFolderIdExistsForClaimVC");
        vc.resetCriteria();
        searchClaimResultView.ensureVariableManager().setVariableValue("folderIdParam", folderId);
        searchClaimResultView.applyViewCriteria(vc);
        searchClaimResultView.executeQuery();
        if (searchClaimResultView.getEstimatedRowCount() > 0)
            return true;
        else
            return false;
    }

    public void prevCaseClaimsDisclosure(DisclosureEvent disclosureEvent) {
        String businessCaseNumber = "";
        if (JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}") != null) {
            businessCaseNumber = JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}").toString();
        }

        if (!businessCaseNumber.equals("") &&
            ADFUtil.findIterator("SearchClaimResultRVO1Iterator").getViewObject().getEstimatedRowCount() > 0) {
            ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
            ViewCriteria vc = searchClaimResultView.getViewCriteriaManager().getViewCriteria("FilterByCaseNumberVC");
            vc.resetCriteria();
            searchClaimResultView.ensureVariableManager().setVariableValue("caseNumberParam", businessCaseNumber);
            searchClaimResultView.applyViewCriteria(vc);
            searchClaimResultView.executeQuery();
        } else {
            ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject().executeEmptyRowSet();
        }
    }

    public void prevPartyClaimsDisclosure(DisclosureEvent disclosureEvent) {
        String resPartyId = "";
        if (JSFUtils.resolveExpression("#{row.bindings.ResPartyId.inputValue}") != null) {
            resPartyId = JSFUtils.resolveExpression("#{row.bindings.ResPartyId.inputValue}").toString();
        }

        if (!resPartyId.equals("")) {
            ViewObject searchClaimResultView = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject();
            ViewCriteria vc =
                searchClaimResultView.getViewCriteriaManager().getViewCriteria("FilterByResPartyIdClaimsVC");
            vc.resetCriteria();
            searchClaimResultView.ensureVariableManager().setVariableValue("resPartyIdParam", resPartyId);
            searchClaimResultView.applyViewCriteria(vc);
            searchClaimResultView.executeQuery();
        } else {
            ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject().executeEmptyRowSet();
        }
    }

    public void claimExpensesVCL(ValueChangeEvent valueChangeEvent) {
        Double newValue = null;
        if (valueChangeEvent.getNewValue() != null) {
            newValue = new Double(valueChangeEvent.getNewValue().toString());
        } else if (newValue == null || newValue.equals("")) {
            newValue = new Double(0);
        }
        Double claimAmount = (Double) ADFUtil.getElExpression("#{bindings.ClaimAmount.inputValue}");
        if (claimAmount == null) {
            claimAmount = new Double(0);
        }
        Double totalAmount = null;
        if (claimAmount != null && newValue != null) {
            totalAmount = Double.sum(newValue, claimAmount);
        }
        ADFUtil.setEL("#{bindings.ClaimTotal.inputValue}", totalAmount);
        ADFUtil.setEL("#{bindings.ClaimAmountToText.inputValue}", getClaimAmountText(totalAmount));
        ADFUtil.refreshComponent("it132");
        ADFUtil.refreshComponent("it13");
    }

    public static java.util.Date getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        dateFormat.format(date);
        return date;
    }

    public static void main(String[] args) {

        //        String str = "4834";
        //String[] arrOfStr = str.split("@", 0);

        //        System.out.println(str.split(",", 0)[0].trim());

        //        String s = "W-X-Y-Z";
        //        String[] arrOfStr = s.split("-", 0);
        //
        //        System.err.println("ww-> " + arrOfStr[0]);
        //        System.err.println("xx-> " + arrOfStr[1]);
        //        System.err.println("yy-> " + arrOfStr[2]);
        //        System.err.println("zz-> " + arrOfStr[3]);

        //        Double x = 1.0;
        //        Double y = 2.0;
        //        Double z = 3.0;
        //        Double w = 4.0;
        //        System.err.println(Double.sum((Double.sum(Double.sum(x, y), z)), w));
        //        System.err.println(Double.sum(((new Double(x) * x)), z));
        // System.err.println(x + y);

        //for (String a : arrOfStr)
        //System.out.println(a.trim());

        //        String s = "08/09/2018";
        //        Date startDate = null;
        //        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //        try {
        //            startDate = df.parse(s);
        //        } catch (ParseException ex) {
        //            ex.printStackTrace();
        //        }
        //        long diff = getDate().getTime() - startDate.getTime();
        //        System.err.println("diff :: " + (diff / 1000 / 60 / 60 / 24));

        String s = "ww";
        if (!s.isEmpty()) {
            System.err.println("aa");
        }
    }

    public void esteanafFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        RichSelectOneChoice gozeyaSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc8");
        RichSelectOneChoice policeSoc =
            (RichSelectOneChoice) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "soc7");
        if (valueChangeEvent != null && valueChangeEvent.getNewValue().equals(true)) {

            Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
            System.out.println("flgflgflgflgflgflgflg   -----" + flg);
            if (!flg)
                gozeyaSoc.setValue("");
            policeSoc.setValue("");
            if (!gozeyaSoc.isDisabled()) {
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

    public void createClaimForFolder(DialogEvent dialogEvent) {
        // Add event code here...
        Row claimInputRow = ADFUtil.findIterator("ClaimInputFormRVO1Iterator").getCurrentRow();
        Row claimsFoldersRow = ADFUtil.findIterator("ClaimsFoldersROIterator").getCurrentRow();
        String claimNumber = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimNumber"));
        String claimYear = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimYear"));
        String folderId = checkNullReturnEmptyString(claimsFoldersRow.getAttribute("FolderId"));
        String repeatedClaimFlag = "N";
            if( claimInputRow.getAttribute("RepeatedClaimFlag").toString()!= null )
            repeatedClaimFlag = claimInputRow.getAttribute("RepeatedClaimFlag").toString();
           // checkNullReturnBooleanBox(claimInputRow.getAttribute("RepeatedClaimFlag"));
        String feesClaimFlag = checkNullReturnBooleanBox(claimInputRow.getAttribute("AddAllFees"));
        Boolean workPenaltyFlag = false;
        if (JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}") != null) {
              workPenaltyFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}");
            System.out.println("claimInputRow.getAttribute(\"WorkPenaltyFlag\")"+JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}"));
        }
        System.err.println("claimInputRow.getAttribute(\"WorkPenaltyFlag\")"+JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}"));
     
        Object amountValue = claimInputRow.getAttribute("FixedPenaltyAmount");     
        System.err.println("Today is PenaltyDueDate 1 " + claimNumber+" == "+claimYear +"== "+repeatedClaimFlag);
        Number ClaimTotalValue = (Number) claimsFoldersRow.getAttribute("ClaimTotalValue");
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            if(amountValue !=null )
            {
                    Number amountValueNum = (Number)amountValue;
                    if (amountValueNum.doubleValue() <=0)
                        return;
                    if(amountValueNum.doubleValue()>ClaimTotalValue.doubleValue()) 
                    {
                        ADFUtil.showMessage("\u0627\u0644\u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u062f\u0641\u0648\u0639\u0629 \u0627\u0643\u0628\u0631 \u0645\u0646 \u0627\u0644\u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u0633\u062a\u062d\u0642\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                    return;
                    }
                }
int claimYears = 0 ;
if (claimYear != null)
    claimYears= Integer.parseInt(claimYear) ;
            System.out.println("5555@@@@@@@@@@@"+claimInputRow.getAttribute("RepeatedClaimFlag"));
 System.out.println(claimYears+"AAAA"+(checkNullAndEmpty(claimYear) && claimYears >=30 ));
 String ent = "0";
 System.out.println(claimsFoldersRow.getAttribute("Kolaya")+"new BigDecimal (claimsFoldersRow.getAttribute  "+(   new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3 ));
            if (   new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3 &&   new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4)
          ent =   claimsFoldersRow.getAttribute("PoliceId")+"" ;
            else
          ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
            
            if (claimsFoldersRow.getAttribute("Kolaya") != null )
                ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
            
            
            if (new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() ==4)
                ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
            if (  claimYears >=30 && checkClaimExist2( folderId,claimYear, repeatedClaimFlag, ent ,  new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString() 
                                                       
                                                       ))
                       {
                               System.out.println("@#!#@!# 33333");
                               ADFUtil.showMessage("\u062a\u0648\u062c\u062f \u0645\u0637\u0627\u0644\u0628\u0629 \u0633\u0627\u0628\u0642\u0629",
                                                   FacesMessage.SEVERITY_INFO);
                               return ;
                           }
            if ( checkNullAndEmpty(claimYear) ) {
                System.err.println("Today is PenaltyDueDate -- " + claimsFoldersRow.getAttribute("ClaimTotalValue"));
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0648 \u0633\u0646\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                    FacesMessage.SEVERITY_ERROR);
            }
            
            if (checkNullAndEmpty(claimNumber)   && claimYears <30  ) {
                System.err.println(claimYears+"Today is PenaltyDueDate -////- " + claimsFoldersRow.getAttribute("ClaimTotalValue"));
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0648 \u0633\u0646\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                    FacesMessage.SEVERITY_ERROR);

            } 
              if (claimYears <30 && checkClaimExist2( folderId,claimYear, repeatedClaimFlag, ent , 
                                                      
                                                      new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString()  
                                                      
                                                      
                                                      )     )
                       //checkClaimExist(claimNumber, claimYear, repeatedClaimFlag, claimsFoldersRow.getAttribute("PoliceStationEntityId")+"", claimInputRow.getAttribute("ClaimFolderType").toString()))
            {
                System.out.println("@#!#@!# 44444");
                System.err.println("Today is PenaltyDueDate 3 " + claimNumber+" == "+claimYear +"== "+repeatedClaimFlag);

                ADFUtil.showMessage("\u062a\u0648\u062c\u062f \u0645\u0637\u0627\u0644\u0628\u0629 \u0633\u0627\u0628\u0642\u0629",
                                    FacesMessage.SEVERITY_ERROR);

            } else {
                //ClaimsFoldersROIterator
                OperationBinding Operation = ADFUtil.findOperation("getPreviousPaidAmount");
                Operation.getParamsMap().put("party_id",claimsFoldersRow.getAttribute("PartId")+"");
                Operation.getParamsMap().put("folderType",  
                new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString()        
                                             
                                             ); 
                 
                String result = (String) Operation.execute();
                String[] arrOfStr = result.split(",", 0);
                Number workAmount = (Number) claimsFoldersRow.getAttribute("WorkAmount");
                Number claimFoldersId = (Number) (  new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType")  :claimInputRow.getAttribute("ClaimFolderTypeEcon")  );
                ViewObject excClaimsVO = ADFUtil.findIterator("ExcClaimsVO1Iterator1").getViewObject();
                Row excClaimsVORow = excClaimsVO.createRow();
                excClaimsVORow.setAttribute("PreviousPaied", new BigDecimal (arrOfStr[0]+""));
                excClaimsVORow.setAttribute("ClaimNumber", claimNumber);
                excClaimsVORow.setAttribute("ClaimYear", claimYear);
                excClaimsVORow.setAttribute("ExcFolderId", new BigDecimal(folderId));
                excClaimsVORow.setAttribute("RepeatedClaimFlag", repeatedClaimFlag);
                excClaimsVORow.setAttribute("FixedPenaltyAmount", claimInputRow.getAttribute("FixedPenaltyAmount"));
                excClaimsVORow.setAttribute("ClaimTotalOriginal", claimInputRow.getAttribute("FixedPenaltyAmount"));

                if (workAmount.doubleValue() > 0) {
                    excClaimsVORow.setAttribute("DailyPenaltyFlag", "Y");
                } else  {
                    excClaimsVORow.setAttribute("DailyPenaltyFlag", "N");
                    //                            claimsFoldersRow.setAttribute(arg0, arg1);
                }
                Object decisionsIds = claimsFoldersRow.getAttribute("DecisionsIds");
                excClaimsVORow.setAttribute("DailyPenaltyAmount", workAmount);
                String DATE_FORMAT = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                if(claimsFoldersRow.getAttribute("PenaltyDueDate")!=null)
                {
                System.out.println("Today is PenaltyDueDate " +
                                   sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                excClaimsVORow.setAttribute("OriginalPenaltyDueDate",
                                            sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                excClaimsVORow.setAttribute("PenaltyDueDate",
                                            sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                }
                excClaimsVORow.setAttribute("IdNumber", claimsFoldersRow.getAttribute("IdNumber")); //
                excClaimsVORow.setAttribute("PartyId", claimsFoldersRow.getAttribute("PartId"));
                excClaimsVORow.setAttribute("PartyName", claimsFoldersRow.getAttribute("PartyFixedFullName"));
                excClaimsVORow.setAttribute("ClaimFolderId", claimFoldersId);
                excClaimsVORow.setAttribute("ClaimSequenceNumber", claimsFoldersRow.getAttribute("SeqNumberFull"));
                    System.out.println("decisionsIds.doubleValue()  ====" +decisionsIds);
                excClaimsVORow.setAttribute("DecisionId", decisionsIds);
                excClaimsVORow.setAttribute("BusinessCaseId", claimsFoldersRow.getAttribute("BusCaseId"));
                excClaimsVORow.setAttribute("ClaimTypeId", claimsFoldersRow.getAttribute("CasType"));
//                excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceStationEntityId"));
//                                    if (claimsFoldersRow.getAttribute("Kolaya") == null  || new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3)
                    excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceStationEntityId"));
//                    else
//                        excClaimsVORow.setAttribute("ClaimPoliceStationId", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                excClaimsVORow.setAttribute("BusinessCaseNumberFull", claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                if(feesClaimFlag.equals("Y"))
                {             
                    excClaimsVORow.setAttribute("LawyerFees", claimsFoldersRow.getAttribute("LawyerFees"));
                    excClaimsVORow.setAttribute("CourtFees", claimsFoldersRow.getAttribute("CourtFees"));
                    }
                System.out.println("workPenaltyFlag   is "+workPenaltyFlag);
                if(workPenaltyFlag)
                {             
                    excClaimsVORow.setAttribute("WorkPenaltyFlag", "Y");
                   
                    }
                System.err.println(excClaimsVORow.getAttribute("BusinessCaseNumberFull")+"==BusinessCaseNumberFull ==> "+ claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                OperationBinding ob = ADFUtil.findOperation("getSequenceValue");
                ob.execute();
                Object claimId = ob.getResult();
                excClaimsVORow.setAttribute("Id", claimId);
                
                
                
                if( new BigDecimal (arrOfStr[0]+"").intValue() > 0){
                    ViewObject ExcsettlementPenaltyVO = ADFUtil.findIterator("ExcSettlementPenaltyVO1Iterator").getViewObject();
                Row proceClaimFolderRow = ExcsettlementPenaltyVO.createRow();  
                proceClaimFolderRow.setAttribute("ClaimIdSource", new BigDecimal (arrOfStr[1]+""));
                proceClaimFolderRow.setAttribute("ClaimIdDestination", claimId);
                proceClaimFolderRow.setAttribute("SettlementAmount", new BigDecimal (arrOfStr[0]+""));
                }
                  //if(decisionsIds != null) 
                  {
                String[] decisionsIdsArr = decisionsIds.toString().split(",");
                for (String id : decisionsIdsArr) {
                    OperationBinding chargeDecisionfilter = ADFUtil.findOperation("getChargeDecisionRow");
                    chargeDecisionfilter.getParamsMap().put("bindId", id);
                    chargeDecisionfilter.execute();
                    Row chargeDecisionsRow =
                        ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO4Iterator").getCurrentRow();

                    if (workAmount.doubleValue() >= 0)
                        chargeDecisionsRow.setAttribute("ClaimChargeDecisionId", claimId);
                    else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 10)
                        chargeDecisionsRow.setAttribute("ClaimLawyerFeesId", claimId);
                    else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 2)
                        chargeDecisionsRow.setAttribute("ClaimCourtFeesId", claimId);
                    
                 

                }
                  }
                JUCtrlActionBinding commit = (JUCtrlActionBinding) ADFUtil.findOperation("Commit");
                                        commit.invoke();
                                        if (commit.getErrors().isEmpty()) {
                ViewObject vo = ADFUtil.findIterator("ExcClaimsVO3Iterator").getViewObject() ;
                vo.executeQuery();
                
         System.out.println("ClaimNumber  ---- " + vo.createRowSetIterator(null).next().getAttribute("ClaimNumber"));
               
                ADFUtil.findOperation("filterClaimsFoldersRO").execute();
                claimInputRow.setAttribute("ClaimNumber","");
                claimInputRow.setAttribute("ClaimYear","");
                claimInputRow.setAttribute("RepeatedClaimFlag","");
//                JSFUtils.setExpressionValue("#{bindings.RepeatedClaimFlag.inputValue}",false);
                claimInputRow.setAttribute("FixedPenaltyAmount","");
                claimInputRow.setAttribute("ClaimFolderType","");
                claimInputRow.setAttribute("WorkPenaltyFlag","");
                JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}",false);
              //  ADFUtil.findOperation("Rollback").execute();
              ADFUtil.showMessage("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d",
                                  FacesMessage.SEVERITY_INFO);
              
              System.err.println("commit -------------");
            }
                else{
                                            String num =    JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}")+"";
                                                String year =    JSFUtils.resolveExpression("#{bindings.Year.inputValue}")+"";
                                                String type =    JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}")+"";
                                                String police =    JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"";
                                                String gozaya =    JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}")+"";
                                            System.err.println("RollBack -------------"+num);
                                            ADFUtil.findOperation("Rollback").execute();
                                                ADFUtil.showMessage("\u0641\u0634\u0644 \u0627\u0644\u062d\u0641\u0638",
                                                                    FacesMessage.SEVERITY_INFO);
                                            
                                                JSFUtils.setExpressionValue("#{bindings.CaseNumber.inputValue}",num);
                                                JSFUtils.setExpressionValue("#{bindings.Year.inputValue}",year);
                                                JSFUtils.setExpressionValue("#{bindings.BusinessCaseTypeId.inputValue}",type);
                                                JSFUtils.setExpressionValue("#{bindings.NeyabGozayaa.inputValue}",gozaya);
                                                JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}",police);
                                            }
            }
            

        }
        else
        {                claimInputRow.setAttribute("ClaimNumber","");
                claimInputRow.setAttribute("ClaimYear","");
                claimInputRow.setAttribute("RepeatedClaimFlag","");
                claimInputRow.setAttribute("FixedPenaltyAmount","");
                claimInputRow.setAttribute("ClaimFolderType","");
            claimInputRow.setAttribute("WorkPenaltyFlag","");
                JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}",false);
            }
    }






    public void createClaimForFolderCombained(DialogEvent dialogEvent) {
        // Add event code here...
       
        Row claimInputRow = ADFUtil.findIterator("ClaimInputFormRVO1Iterator").getCurrentRow();
        String combainedClaimNumber = null;
        BigDecimal combainedFolder = null;
        
        String claimNumber = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimNumber"));
        String claimYear = checkNullReturnEmptyString(claimInputRow.getAttribute("ClaimYear"));
        int claimYears = 0 ;
        String repeatedClaimFlag = "N";
      repeatedClaimFlag =  claimInputRow.getAttribute("RepeatedClaimFlag").toString();
            //checkNullReturnBooleanBox(claimInputRow.getAttribute("RepeatedClaimFlag"));
        String feesClaimFlag = checkNullReturnBooleanBox(claimInputRow.getAttribute("AddAllFees"));
        Boolean workPenaltyFlag = false;
        
        
        
        if (JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}") != null) {
              workPenaltyFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}");
            System.out.println("claimInputRow.getAttribute(\"WorkPenaltyFlag\")"+JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}"));
        }
        System.err.println("claimInputRow.getAttribute(\"WorkPenaltyFlag\")"+JSFUtils.resolveExpression("#{bindings.WorkPenaltyFlag.inputValue}"));
        
        Object amountValue = claimInputRow.getAttribute("FixedPenaltyAmount");     
        System.err.println("Today is PenaltyDueDate 1 " + claimNumber+" == "+claimYear +"== "+repeatedClaimFlag);
        
      /////////////////////////////////////////////////////////////////////////
      if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
       ViewObject claimsFoldersVO =   ADFUtil.findIterator("ClaimsFoldersROIterator").getViewObject();
        RowSetIterator vc = claimsFoldersVO.createRowSetIterator(null);
            Boolean flag  = false ;
            int count = 0 ;
        while (vc.hasNext()){
        Row next = vc.next();
            
            Number ClaimTotalValue = (Number) next.getAttribute("ClaimTotalValue");
            String folderId = checkNullReturnEmptyString(next.getAttribute("FolderId"));
                System.out.println(folderId+"Combined is "+next.getAttribute("Combained").toString());
                if(next.getAttribute("Combained") != null)
                    flag  =  ((next.getAttribute("Combained").toString()).compareToIgnoreCase("Y") ==0 || (next.getAttribute("Combained").toString()).compareToIgnoreCase("true") ==0)?true:false ;
                if (flag) {
            if(amountValue !=null )
            {
                    Number amountValueNum = (Number)amountValue;
                    if(amountValueNum.doubleValue()>ClaimTotalValue.doubleValue()) 
                    {
                        ADFUtil.showMessage("\u0627\u0644\u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u062f\u0641\u0648\u0639\u0629 \u0627\u0643\u0628\u0631 \u0645\u0646 \u0627\u0644\u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u0633\u062a\u062d\u0642\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                    return;
                    }
                }
           
                String ent = "0";
                System.out.println(next.getAttribute("Kolaya")+"new BigDecimal (claimsFoldersRow.getAttribute  "+(   new BigDecimal (next.getAttribute("BcDegreeId")+"").intValue() < 3 ));
                           if (   new BigDecimal (next.getAttribute("BcDegreeId")+"").intValue() < 3 )
                         ent =   next.getAttribute("PoliceId")+"" ;
                           else
                         ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
                           
                           if (next.getAttribute("Kolaya") != null )
                               ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
           
           
            if (claimYear != null)
            claimYears= Integer.parseInt(claimYear) ;
            System.out.println("999@@@@@@@@@@@"+claimInputRow.getAttribute("RepeatedClaimFlag"));
            System.out.println(claimYears+"AAAA"+(checkNullAndEmpty(claimYear) && claimYears >=30 ));
            if (  claimYears >=30 && checkClaimExist2( folderId,claimYear, repeatedClaimFlag, ent+"", 
                new BigDecimal (next.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString() 
                                                       
                                                       ))
                       {
                System.out.println("@#!#@!# 1111");
                               ADFUtil.showMessage("\u062a\u0648\u062c\u062f \u0645\u0637\u0627\u0644\u0628\u0629 \u0633\u0627\u0628\u0642\u0629",
                                                   FacesMessage.SEVERITY_INFO);
                               return ;
                           }
            if ( checkNullAndEmpty(claimYear) ) {
            
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0648 \u0633\u0646\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                return ;
            }
            
            if (checkNullAndEmpty(claimNumber)   && claimYears <30  ) {
                
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0648 \u0633\u0646\u0629 \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                return ;

            } 
              if (claimYears <30 && checkClaimExist2( folderId,claimYear, repeatedClaimFlag, ent+"",
                  new BigDecimal (next.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString() 
                                                      
                                                      ))
                       //checkClaimExist(claimNumber, claimYear, repeatedClaimFlag, next.getAttribute("PoliceStationEntityId")+"", claimInputRow.getAttribute("ClaimFolderType").toString())) 
            {
                System.out.println("@#!#@!# 22222");
                System.err.println("Today is PenaltyDueDate 3 " + claimNumber+" == "+claimYear +"== "+repeatedClaimFlag);

                ADFUtil.showMessage("\u062a\u0648\u062c\u062f \u0645\u0637\u0627\u0644\u0628\u0629 \u0633\u0627\u0628\u0642\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                return ;

            }  
            
            
         
                count++ ;
            }
        }
        if (count >1){
            System.err.println("Count is "+count);
        RowSetIterator vc2 = claimsFoldersVO.createRowSetIterator(null);
       
   
       int countOrder = 2;
           
        while (vc2.hasNext()){
        Row claimsFoldersRow = vc2.next();
                String ent = "0";
                System.out.println(claimsFoldersRow.getAttribute("Kolaya")+"new BigDecimal (claimsFoldersRow.getAttribute  "+(   new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3 ));
                           if (   new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3 )
                         ent =   claimsFoldersRow.getAttribute("PoliceId")+"" ;
                           else
                         ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";
                           
                           if (claimsFoldersRow.getAttribute("Kolaya") != null )
                               ent =   JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}")+"";   
            //
            flag  =  ((claimsFoldersRow.getAttribute("Combained").toString()).compareToIgnoreCase("Y") ==0 || (claimsFoldersRow.getAttribute("Combained").toString()).compareToIgnoreCase("true") ==0)?true:false ;
            if (flag) {
            Number ClaimTotalValue = (Number) claimsFoldersRow.getAttribute("ClaimTotalValue");
            String folderId = checkNullReturnEmptyString(claimsFoldersRow.getAttribute("FolderId"));

                //ClaimsFoldersROIterator
                OperationBinding Operation = ADFUtil.findOperation("getPreviousPaidAmount");
                Operation.getParamsMap().put("party_id",claimsFoldersRow.getAttribute("PartId")+"");
                Operation.getParamsMap().put("folderType", claimInputRow.getAttribute("ClaimFolderType")+""); 
                 
                String result = (String) Operation.execute();
            String[] arrOfStr = result.split(",", 0);
                Number workAmount = (Number) claimsFoldersRow.getAttribute("WorkAmount");
                Number claimFoldersId = (Number) claimInputRow.getAttribute("ClaimFolderType");
                ViewObject excClaimsVO = ADFUtil.findIterator("ExcClaimsVO1Iterator1").getViewObject();
          if (combainedClaimNumber== null)  
            {
              System.out.println("combained"+folderId);
                combainedFolder =  new BigDecimal(folderId) ;
                System.out.println("create new row");
                Row excClaimsVORow = excClaimsVO.createRow();
                excClaimsVORow.setAttribute("PreviousPaied", new BigDecimal (arrOfStr[0]+""));
                excClaimsVORow.setAttribute("ClaimNumber", claimNumber);
                excClaimsVORow.setAttribute("CombinedOrder", new BigDecimal(1));
                excClaimsVORow.setAttribute("ClaimYear", claimYear);
                excClaimsVORow.setAttribute("ExcFolderId", new BigDecimal(folderId));
                excClaimsVORow.setAttribute("RepeatedClaimFlag", repeatedClaimFlag);
                excClaimsVORow.setAttribute("FixedPenaltyAmount", claimInputRow.getAttribute("FixedPenaltyAmount"));
                excClaimsVORow.setAttribute("ClaimTotalOriginal", claimInputRow.getAttribute("FixedPenaltyAmount"));

                if (workAmount.doubleValue() > 0) {
                    excClaimsVORow.setAttribute("DailyPenaltyFlag", "Y");
                } else  {
                    excClaimsVORow.setAttribute("DailyPenaltyFlag", "N");
                    //                            claimsFoldersRow.setAttribute(arg0, arg1);
                }
                Object decisionsIds = claimsFoldersRow.getAttribute("DecisionsIds");
                excClaimsVORow.setAttribute("DailyPenaltyAmount", workAmount);
                String DATE_FORMAT = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                if(claimsFoldersRow.getAttribute("PenaltyDueDate")!=null)
                {
                System.out.println("Today is PenaltyDueDate " +
                                   sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                excClaimsVORow.setAttribute("OriginalPenaltyDueDate",
                                            sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                excClaimsVORow.setAttribute("PenaltyDueDate",
                                            sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                }
                excClaimsVORow.setAttribute("IdNumber", claimsFoldersRow.getAttribute("IdNumber")); //
                excClaimsVORow.setAttribute("PartyId", claimsFoldersRow.getAttribute("PartId"));
                excClaimsVORow.setAttribute("PartyName", claimsFoldersRow.getAttribute("PartyFixedFullName"));
                excClaimsVORow.setAttribute("ClaimFolderId",   new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString() 
                );
                excClaimsVORow.setAttribute("ClaimSequenceNumber", claimsFoldersRow.getAttribute("SeqNumberFull"));
                    System.out.println("decisionsIds.doubleValue()  ====" +decisionsIds);
                excClaimsVORow.setAttribute("DecisionId", decisionsIds);
                excClaimsVORow.setAttribute("BusinessCaseId", claimsFoldersRow.getAttribute("BusCaseId"));
                excClaimsVORow.setAttribute("ClaimTypeId", claimsFoldersRow.getAttribute("CasType"));
                excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceStationEntityId"));
//                                    if (claimsFoldersRow.getAttribute("Kolaya") == null  || new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3)
//                    excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceId"));
//                    else
//                        excClaimsVORow.setAttribute("ClaimPoliceStationId", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                excClaimsVORow.setAttribute("BusinessCaseNumberFull", claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                if(feesClaimFlag.equals("Y"))
                {             
                    excClaimsVORow.setAttribute("LawyerFees", claimsFoldersRow.getAttribute("LawyerFees"));
                    excClaimsVORow.setAttribute("CourtFees", claimsFoldersRow.getAttribute("CourtFees"));
                    }
                System.out.println("workPenaltyFlag   is "+workPenaltyFlag);
                if(workPenaltyFlag)
                {             
                    excClaimsVORow.setAttribute("WorkPenaltyFlag", "Y");
                   
                    }
                System.err.println(excClaimsVORow.getAttribute("BusinessCaseNumberFull")+"==BusinessCaseNumberFull ==> "+ claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                OperationBinding ob = ADFUtil.findOperation("getSequenceValue");
                ob.execute();
                Object claimId = ob.getResult();
                excClaimsVORow.setAttribute("Id", claimId);
                
                System.err.println("claimId is "+claimId + " ent is "+ent+" claimYears is "+claimYears + "ClaimTypeId is "+claimsFoldersRow.getAttribute("CasType")
                                   +"**"+  claimsFoldersRow.getAttribute("CasType") + claimInputRow.getAttribute("ClaimFolderType") +claimInputRow.getAttribute("ClaimFolderTypeEcon")
                                 
                                   );
                    
                String[] decisionsIdsArr = decisionsIds.toString().split(",");
                for (String id : decisionsIdsArr) {
                    OperationBinding chargeDecisionfilter = ADFUtil.findOperation("getChargeDecisionRow");
                    chargeDecisionfilter.getParamsMap().put("bindId", id);
                    chargeDecisionfilter.execute();
                    Row chargeDecisionsRow =
                        ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO4Iterator").getCurrentRow();

                    if (workAmount.doubleValue() >= 0)
                        chargeDecisionsRow.setAttribute("ClaimChargeDecisionId", claimId);
                    else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 10)
                        chargeDecisionsRow.setAttribute("ClaimLawyerFeesId", claimId);
                    else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 2)
                        chargeDecisionsRow.setAttribute("ClaimCourtFeesId", claimId);
                    
                 

                }
                JUCtrlActionBinding commit = (JUCtrlActionBinding) ADFUtil.findOperation("Commit");
                                        commit.invoke();
                                        if (commit.getErrors().isEmpty()) {
                                            if (claimYears >=30){
                                                System.out.println("Saved Action");
                OperationBinding ob2 = ADFUtil.findOperation("getCalimNumber");
                ob2.getParamsMap().put("claimId", claimId);
               String ClaimNum = (String) ob2.execute(); 
                 //   if (ClaimNum.compareToIgnoreCase("0")==1) 
                        combainedClaimNumber = ClaimNum  ;
                    
                                            }
                                            else
                                                combainedClaimNumber = claimNumber;
                                            
//                                            System.out.println("-----******"+excClaimsVO.getCurrentRow().getAttribute("ClaimNumber"));
//                ViewObject vo = ADFUtil.findIterator("ExcClaimsVO2Iterator").getViewObject() ;
//                     ViewCriteria vcc = vo.createViewCriteria();
//                     ViewCriteriaRow vcr = vcc.createViewCriteriaRow();
//                     vcr.setAttribute("Id", claimId);
//                     vcc.insertRow(vcr);
//                     vo.applyViewCriteria(vcc);
//                vo.executeQuery();
//                System.out.println("vo-----------"+vo.getRowCount());
//              Row rowTemp =  vo.createRowSetIterator(null).next() ;
//                rowTemp.setAttribute("CombinedOrder", new BigDecimal(1));
//                combainedClaimNumber = rowTemp.getAttribute("ClaimNumber")+"" ;
         System.out.println("ClaimNumber  ---- " +combainedClaimNumber );
               
              
              
              System.err.println("commit ------***-------");
            }
                else{
                                            String num =    JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}")+"";
                                                String year =    JSFUtils.resolveExpression("#{bindings.Year.inputValue}")+"";
                                                String type =    JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}")+"";
                                                String police =    JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")+"";
                                                String gozaya =    JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}")+"";
                                            System.err.println("RollBack -------****------"+num);
                                            ADFUtil.findOperation("Rollback").execute();
                                                ADFUtil.showMessage("\u0641\u0634\u0644 \u0627\u0644\u062d\u0641\u0638",
                                                                    FacesMessage.SEVERITY_INFO);
                                            
                                                JSFUtils.setExpressionValue("#{bindings.CaseNumber.inputValue}",num);
                                                JSFUtils.setExpressionValue("#{bindings.Year.inputValue}",year);
                                                JSFUtils.setExpressionValue("#{bindings.BusinessCaseTypeId.inputValue}",type);
                                                JSFUtils.setExpressionValue("#{bindings.NeyabGozayaa.inputValue}",gozaya);
                                                JSFUtils.setExpressionValue("#{bindings.OrigEntity.inputValue}",police);
                                            }
           
            }
            if (combainedFolder.compareTo(new BigDecimal (folderId))!=0)
            {
                
                    Row excClaimsVORow = excClaimsVO.createRow();
                    excClaimsVORow.setAttribute("PreviousPaied", new BigDecimal (arrOfStr[0]+""));
                    excClaimsVORow.setAttribute("ClaimNumber", combainedClaimNumber);
                    excClaimsVORow.setAttribute("CombinedOrder", new BigDecimal(countOrder));
                    countOrder++;
                    excClaimsVORow.setAttribute("ClaimYear", claimYear);
                    excClaimsVORow.setAttribute("ExcFolderId", new BigDecimal(folderId));
                    excClaimsVORow.setAttribute("RepeatedClaimFlag", repeatedClaimFlag);
                    excClaimsVORow.setAttribute("FixedPenaltyAmount", claimInputRow.getAttribute("FixedPenaltyAmount"));
                    excClaimsVORow.setAttribute("ClaimTotalOriginal", claimInputRow.getAttribute("FixedPenaltyAmount"));

                    if (workAmount.doubleValue() > 0) {
                        excClaimsVORow.setAttribute("DailyPenaltyFlag", "Y");
                    } else  {
                        excClaimsVORow.setAttribute("DailyPenaltyFlag", "N");
                        //                            claimsFoldersRow.setAttribute(arg0, arg1);
                    }
                    Object decisionsIds = claimsFoldersRow.getAttribute("DecisionsIds");
                    excClaimsVORow.setAttribute("DailyPenaltyAmount", workAmount);
                    String DATE_FORMAT = "dd/MM/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    if(claimsFoldersRow.getAttribute("PenaltyDueDate")!=null)
                    {
                    System.out.println("Today is PenaltyDueDate " +
                                       sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                    excClaimsVORow.setAttribute("OriginalPenaltyDueDate",
                                                sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                    excClaimsVORow.setAttribute("PenaltyDueDate",
                                                sdf.format(claimsFoldersRow.getAttribute("PenaltyDueDate")));
                    }
                    excClaimsVORow.setAttribute("IdNumber", claimsFoldersRow.getAttribute("IdNumber")); //
                    excClaimsVORow.setAttribute("PartyId", claimsFoldersRow.getAttribute("PartId"));
                    excClaimsVORow.setAttribute("PartyName", claimsFoldersRow.getAttribute("PartyFixedFullName"));
                    excClaimsVORow.setAttribute("ClaimFolderId",  new BigDecimal (claimsFoldersRow.getAttribute("CasType")+"").intValue() !=4 ?  claimInputRow.getAttribute("ClaimFolderType").toString()  :claimInputRow.getAttribute("ClaimFolderTypeEcon").toString() 
                );
                    excClaimsVORow.setAttribute("ClaimSequenceNumber", claimsFoldersRow.getAttribute("SeqNumberFull"));
                        System.out.println("decisionsIds.doubleValue()  ====" +decisionsIds);
                    excClaimsVORow.setAttribute("DecisionId", decisionsIds);
                    excClaimsVORow.setAttribute("BusinessCaseId", claimsFoldersRow.getAttribute("BusCaseId"));
                    excClaimsVORow.setAttribute("ClaimTypeId", claimsFoldersRow.getAttribute("CasType"));
//                    if (claimsFoldersRow.getAttribute("Kolaya") == null  || new BigDecimal (claimsFoldersRow.getAttribute("BcDegreeId")+"").intValue() < 3)
//                    excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceId"));
//                    else
//                        excClaimsVORow.setAttribute("ClaimPoliceStationId", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
                    excClaimsVORow.setAttribute("ClaimPoliceStationId", claimsFoldersRow.getAttribute("PoliceStationEntityId"));// ent
                    excClaimsVORow.setAttribute("BusinessCaseNumberFull", claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                    if(feesClaimFlag.equals("Y"))
                    {             
                        excClaimsVORow.setAttribute("LawyerFees", claimsFoldersRow.getAttribute("LawyerFees"));
                        excClaimsVORow.setAttribute("CourtFees", claimsFoldersRow.getAttribute("CourtFees"));
                        }
                    System.out.println("workPenaltyFlag   is "+workPenaltyFlag);
                    if(workPenaltyFlag)
                    {             
                        excClaimsVORow.setAttribute("WorkPenaltyFlag", "Y");
                       
                        }
                    System.err.println(excClaimsVORow.getAttribute("BusinessCaseNumberFull")+"==BusinessCaseNumberFull ==> "+ claimsFoldersRow.getAttribute("BusinessCaseNumberFull"));
                    OperationBinding ob = ADFUtil.findOperation("getSequenceValue");
                    ob.execute();
                    Object claimId = ob.getResult();
                    excClaimsVORow.setAttribute("Id", claimId);
                    String[] decisionsIdsArr = decisionsIds.toString().split(",");
                    for (String id : decisionsIdsArr) {
                        OperationBinding chargeDecisionfilter = ADFUtil.findOperation("getChargeDecisionRow");
                        chargeDecisionfilter.getParamsMap().put("bindId", id);
                        chargeDecisionfilter.execute();
                        Row chargeDecisionsRow =
                            ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO4Iterator").getCurrentRow();

                        if (workAmount.doubleValue() >= 0)
                            chargeDecisionsRow.setAttribute("ClaimChargeDecisionId", claimId);
                        else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 10)
                            chargeDecisionsRow.setAttribute("ClaimLawyerFeesId", claimId);
                        else if (workAmount.doubleValue() == -1 && claimFoldersId.intValue() == 2)
                            chargeDecisionsRow.setAttribute("ClaimCourtFeesId", claimId);
                        
                     

                    }
                   
                
                
                }
            
            
            
            
        }
           
           
            }
            
        JUCtrlActionBinding commit = (JUCtrlActionBinding) ADFUtil.findOperation("Commit");
                                commit.invoke();
                                if (commit.getErrors().isEmpty()) {

            {                ADFUtil.findOperation("filterClaimsFoldersRO").execute();
                claimInputRow.setAttribute("ClaimNumber","");
                claimInputRow.setAttribute("ClaimYear","");
                claimInputRow.setAttribute("RepeatedClaimFlag","");
            //                JSFUtils.setExpressionValue("#{bindings.RepeatedClaimFlag.inputValue}",false);
                claimInputRow.setAttribute("FixedPenaltyAmount","");
                claimInputRow.setAttribute("ClaimFolderType","");
                claimInputRow.setAttribute("WorkPenaltyFlag","");
                JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}",false);
              //  ADFUtil.findOperation("Rollback").execute();
              ADFUtil.showMessage("\u062a\u0645 \u0627\u0644\u062d\u0641\u0638 \u0628\u0646\u062c\u0627\u062d",
                                  FacesMessage.SEVERITY_INFO);
                }
        
        
        System.err.println("commit -------------");
        }
        
    }
        else
        {
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062e\u062a\u064a\u0627\u0631 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0637\u0631\u0641",
                                    FacesMessage.SEVERITY_ERROR);
                return ;
            }
        }
        else
        {                claimInputRow.setAttribute("ClaimNumber","");
                claimInputRow.setAttribute("ClaimYear","");
                claimInputRow.setAttribute("RepeatedClaimFlag","");
                claimInputRow.setAttribute("FixedPenaltyAmount","");
                claimInputRow.setAttribute("ClaimFolderType","");
            claimInputRow.setAttribute("WorkPenaltyFlag","");
                JSFUtils.setExpressionValue("#{bindings.WorkPenaltyFlag.inputValue}",false);
            }
        
    }
    
    


    public void workVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("VCL   is"+valueChangeEvent.getNewValue());
    }

    public String searchBtn() {
        // Add event code here...
        ADFContext.getCurrent().getSessionScope().put("pClaimDeleted", "N");
        RichShowDetailItem item =
            (RichShowDetailItem) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "sdi1");
        item.setVisible(true);
        AdfFacesContext.getCurrentInstance().addPartialTarget(item);
        return null;
    }
}
