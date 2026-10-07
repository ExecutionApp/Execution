package com.egabi.cjexec.view.beans.audit;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.model.appmodules.SimplifiedModeAMImpl;
import com.egabi.cjexec.view.beans.claimprocedure.Utils;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.event.LaunchPopupEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;

public class Audit{
    //    public Audit() {
    //        super();
    //    }

    private RichInputListOfValues prosecutionKolyaLOV;
    private RichInputListOfValues prosecutionPartialLOV;
    private RichInputListOfValues policeStationEntityLOV;

    protected CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
    protected SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
    private RichShowDetailItem showDetailItem;
    private RichShowDetailItem showDetailItemCase;
    private RichButton saveButton1;
    private RichButton saveButton2;
    private RichInputListOfValues policeStationEntityLOV2;
    private RichInputListOfValues prosecutionKolyaLOV2;
    private RichInputListOfValues prosecutionPartialLOV2;
    private RichInputListOfValues policeStationEntityLOV21;
    private RichInputListOfValues prosecutionKolyaLOV12;
    private RichPanelGroupLayout casePanel;
    private RichPanelGroupLayout searchPanel2;
    private RichPanelGroupLayout searchPanel1;


    public String doSearchInventory() {
        System.out.println("><<<<<<<<<<<<<<<<<<<<<<<<<<<");
        ViewObject searchInventoryView = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject();
        Row searchInventoryRow = searchInventoryView.getCurrentRow();
        int searchType =
            Integer.parseInt(searchInventoryRow.getAttribute("SearchType") == null ? "1" :
                             searchInventoryRow.getAttribute("SearchType").toString());
        if (searchType == 2) {
            System.err.println("><<<<<<<<<<<2<<<<<<<<<<<<,,");
            if (searchInventoryRow.getAttribute("SequenceNumber") == null ||
                searchInventoryRow.getAttribute("Year") == null || searchInventoryRow.getAttribute("RepeatedFlag") == null ||

                searchInventoryRow.getAttribute("FolderTypeId") == null) {
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
                                    FacesMessage.SEVERITY_ERROR);
                return null;
            }

            ViewObject searchInventoryResultView =
                ADFUtil.findIterator("SearchClaimInventoryResultRVO1Iterator").getViewObject();


            String condition = " 1 = 1 ";
            condition += " AND SEQUENCE_NUMBER = " + searchInventoryRow.getAttribute("SequenceNumber");
            condition += " AND YEAR = " + searchInventoryRow.getAttribute("Year");
            condition += " AND REPEATED_FLAG = '" + searchInventoryRow.getAttribute("RepeatedFlag") + "' ";
            //condition += " AND POLICE_STATION_ENTITY_ID = " + searchInventoryRow.getAttribute("PoliceStationEntityId");
            condition += " AND FOLDER_TYPE = " + searchInventoryRow.getAttribute("FolderTypeId");
            searchInventoryResultView.setWhereClause(condition);

            searchInventoryResultView.executeQuery();
        } else if (searchType == 1) {
            System.err.println("><<<<<<<<<<<1<<<<<<<<<<<<,,");
            if (searchInventoryRow.getAttribute("CaseNumber") == null ||
                searchInventoryRow.getAttribute("Year") == null ||
                searchInventoryRow.getAttribute("PoliceStationEntityId") == null ||
                searchInventoryRow.getAttribute("BusinessCaseTypeId") == null) {
                ADFUtil.showMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u0643\u0644 \u0645\u062d\u062f\u062f\u0627\u062a \u0627\u0644\u0628\u062d\u062b",
                                    FacesMessage.SEVERITY_ERROR);
                return null;
            }
            ViewObject resultView = ADFUtil.findIterator("SearchClaimInventoryCaseResultRVO1Iterator").getViewObject();


            String condition = " 1 = 1 ";
            condition += " AND CASE_NUMBER = " + searchInventoryRow.getAttribute("CaseNumber");
            condition += " AND CASE_YEAR = " + searchInventoryRow.getAttribute("Year");
            condition += " AND POLICE_ID = " + searchInventoryRow.getAttribute("PoliceStationEntityId");
            condition += " AND CAS_TYPE_ID = " + searchInventoryRow.getAttribute("BusinessCaseTypeId");
            resultView.setWhereClause(condition);
            resultView.executeQuery();
            System.err.println("resultView.getEstimatedRangePageCount()" + resultView.getEstimatedRangePageCount());
        }
        
        excuteData();
        
        casePanel.setVisible(true);
        ADFUtil.refreshComponent(casePanel);
        if (searchType == 1) {
        searchPanel1.setVisible(true);
        ADFUtil.refreshComponent(searchPanel1);
        }
        if (searchType == 2) {
        searchPanel2.setVisible(true);
        ADFUtil.refreshComponent(searchPanel2);
        }
        ADFUtil.refreshComponent(casePanel);
        
        
        
        
        
        return null;
    }

    public void resetSearhResults(ValueChangeEvent valueChangeEvent) {
        ViewObject view1 = ADFUtil.findIterator("SearchClaimInventoryResultRVO1Iterator").getViewObject();
        view1.executeEmptyRowSet();

        ViewObject view2 = ADFUtil.findIterator("SearchClaimInventoryCaseResultRVO1Iterator").getViewObject();
        view2.executeEmptyRowSet();
    }


    public void clearOnChangeProsecutionKolya(ValueChangeEvent valueChangeEvent) {

        prosecutionPartialLOV.setValue(null);
        prosecutionPartialLOV.setLabel(null);
        policeStationEntityLOV.setValue(null);
        policeStationEntityLOV.setLabel(null);
    }

    public void clearOnLaunchingProsecutionKolya(LaunchPopupEvent launchPopupEvent) {

        prosecutionPartialLOV.setValue(null);
        prosecutionPartialLOV.setLabel(null);
        policeStationEntityLOV.setValue(null);
        policeStationEntityLOV.setLabel(null);
    }

    public void clearOnChangeProsecutionPartial(ValueChangeEvent valueChangeEvent) {

        policeStationEntityLOV.setValue(null);
        policeStationEntityLOV.setLabel(null);
    }

    public void clearOnLaunchingProsecutionPartial(LaunchPopupEvent launchPopupEvent) {

        policeStationEntityLOV.setValue(null);
        policeStationEntityLOV.setLabel(null);
    }

    public void setProsecutionKolyaLOV(RichInputListOfValues prosecutionKolyaLOV) {
        this.prosecutionKolyaLOV = prosecutionKolyaLOV;
    }

    public RichInputListOfValues getProsecutionKolyaLOV() {
        return prosecutionKolyaLOV;
    }

    public void setProsecutionPartialLOV(RichInputListOfValues prosecutionPartialLOV) {
        this.prosecutionPartialLOV = prosecutionPartialLOV;
    }

    public RichInputListOfValues getProsecutionPartialLOV() {
        return prosecutionPartialLOV;
    }

    public void setPoliceStationEntityLOV(RichInputListOfValues policeStationEntityLOV) {
        this.policeStationEntityLOV = policeStationEntityLOV;
    }

    public RichInputListOfValues getPoliceStationEntityLOV() {
        return policeStationEntityLOV;
    }

    public void rowDsclsrLstnClaim(RowDisclosureEvent rowDisclosureEvent) {
        Utils.rowDisclosureListenerClaimTable(rowDisclosureEvent);


    }

    public Object getInsertAuditRowFunc(String type, String editedAttribute, String oldValue, String newValue,
                                        String businessCaseId) {
        OperationBinding ob = ADFUtil.findOperation("insertAuditRow");
        ob.getParamsMap().put("type", type);
        ob.getParamsMap().put("editedAttribute", editedAttribute);
        ob.getParamsMap().put("oldValue", oldValue);
        ob.getParamsMap().put("newValue", newValue);
        ob.getParamsMap().put("businessCaseId", businessCaseId);
        return ob.execute();
    }

    public void rowDsclsrLstnClaimCase(RowDisclosureEvent rowDisclosureEvent) {
        Utils.rowDisclosureListenerClaimTable(rowDisclosureEvent);
    }

    public String excuteData() {

        ViewObject searchInventoryView = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject();
        Row searchInventoryRow = searchInventoryView.getCurrentRow();

        int searchType =
            Integer.parseInt(searchInventoryRow.getAttribute("SearchType") == null ? "1" :
                             searchInventoryRow.getAttribute("SearchType").toString());
        Row currentRow = null;

        if (searchType == 2) {
            currentRow = ADFUtil.findIterator("SearchClaimInventoryResultRVO1Iterator").getCurrentRow();
        }

        if (searchType == 1) {
            currentRow = ADFUtil.findIterator("SearchClaimInventoryCaseResultRVO1Iterator").getCurrentRow();
        }

        String bcId = checkNullReturnEmptyString(currentRow.getAttribute("BusinessCaseId"));

        System.out.println(bcId + "><<<<<<<<<<<<<<<<<<<<<<<<");


        ViewObject searchClaimResultView = am.getAuditParPoliceAuthorityReportVO1();
        searchClaimResultView =
            applyCr("bc_id", bcId, "AuditParPoliceAuthorityReportVOByIdCriteria", searchClaimResultView);

        if (searchClaimResultView.getEstimatedRowCount() > 0) {

            //getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", bcId);


        }


        ViewObject searchClaimResultView1 = am.getAuditBusinessCaseVO1();
        searchClaimResultView1 = applyCr("bc_id", bcId, "AuditBusinessCaseVOByIdCriteria", searchClaimResultView1);

        if (searchClaimResultView1.getEstimatedRowCount() > 0) {

            //getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", bcId);


        }

        ViewObject searchClaimResultView2 = am.getAuditBusinessCaseDetailsVO1();
        searchClaimResultView2 = applyCr("bc_id", bcId, "AuditBusinessCaseDetailsVOCriteria", searchClaimResultView2);

        String bc_detail_id = "";

        if (searchClaimResultView2.getEstimatedRowCount() > 0) {
            bc_detail_id = checkNullReturnEmptyString(searchClaimResultView2.first().getAttribute("Id"));

            //getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", bcId);

        }


        ViewObject searchClaimResultView3 = am.getAuditBusinessCaseLedgerSeqVO1();
        searchClaimResultView3 =
            applyCr("bc_detail_id", bc_detail_id, "AuditBusinessCaseLedgerSeqVOCriteria", searchClaimResultView3);

        if (searchClaimResultView3.getEstimatedRowCount() > 0) {

            //getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", bcId);

        }

        //ADFUtil.findOperation("Commit").execute();
        //        getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", "businessCaseId");

        return null;

    }


    public void prevDescLstnr(DisclosureEvent disclosureEvent) {


    }

    public void prevDescLstnrCase(DisclosureEvent disclosureEvent) {


    }

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

    private String checkNullReturnBooleanBox(Object obj) {
        try {
            if (!obj.toString().equals(null) && !obj.toString().equals("N"))
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

    public String userRoles() {
        int entityDegree = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.entityDegree}").toString());
        ArrayList roles = (ArrayList) ADFUtil.evaluateEL("#{sessionScope.roles}");
        for (int i = 0; i < roles.size(); i++) {
            int role = Integer.parseInt(roles.get(i).toString());
            if (role == 3278) {
                ADFUtil.setEL("#{pageFlowScope.showProcedures}", role);
            }
            if (role == 3279) {
                ADFUtil.setEL("#{pageFlowScope.insertProcedures}", role);
            }
            if (role == 3298) {
                ADFUtil.setEL("#{pageFlowScope.showAllEntities}", role);
            }
            if (role == 3300) {
                ADFUtil.setEL("#{pageFlowScope.insertAllEntities}", role);
            }

        }

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        if (entityDegree == 39) {
            ViewObjectImpl securiedEntitiesView = am.getSecuriedEntities1();
            ViewCriteria vc = securiedEntitiesView.getViewCriteria("SecuriedEntitiesCriteria_Koleyaa");
            securiedEntitiesView.applyViewCriteria(vc);
            securiedEntitiesView.executeQuery();
            Row securiedEntitiesRow = securiedEntitiesView.first();

            if (securiedEntitiesRow != null) {
                ADFUtil.setEL("#{pageFlowScope.entitKoleyaaName}", securiedEntitiesRow.getAttribute("EntityName"));
                ADFUtil.setEL("#{sessionScope.entitKoleyaaId}", securiedEntitiesRow.getAttribute("Id"));
            }
        }

        if (entityDegree == 3) {
            ViewObjectImpl securiedEntitiesPartialView = am.getSecuriedEntities2();
            ViewCriteria vc2 = securiedEntitiesPartialView.getViewCriteria("SecuriedEntitiesCriteria_Partial");
            securiedEntitiesPartialView.applyViewCriteria(vc2);
            securiedEntitiesPartialView.executeQuery();
            Row securiedEntitiesParialRow = securiedEntitiesPartialView.first();

            if (securiedEntitiesParialRow != null) {
                ADFUtil.setEL("#{pageFlowScope.entitPartialName}",
                              securiedEntitiesParialRow.getAttribute("EntityName"));
                ADFUtil.setEL("#{sessionScope.entitPartialId}", securiedEntitiesParialRow.getAttribute("Id"));
                ADFUtil.setEL("#{pageFlowScope.entitKoleyaaName}",
                              securiedEntitiesParialRow.getAttribute("ParentName"));
            }
        }
        return "go";
    }

    public void setPoliceStationEntityLOV2(RichInputListOfValues policeStationEntityLOV2) {
        this.policeStationEntityLOV2 = policeStationEntityLOV2;
    }

    public RichInputListOfValues getPoliceStationEntityLOV2() {
        return policeStationEntityLOV2;
    }

    public void setProsecutionKolyaLOV2(RichInputListOfValues prosecutionKolyaLOV2) {
        this.prosecutionKolyaLOV2 = prosecutionKolyaLOV2;
    }

    public RichInputListOfValues getProsecutionKolyaLOV2() {
        return prosecutionKolyaLOV2;
    }

    public void setProsecutionPartialLOV2(RichInputListOfValues prosecutionPartialLOV2) {
        this.prosecutionPartialLOV2 = prosecutionPartialLOV2;
    }

    public RichInputListOfValues getProsecutionPartialLOV2() {
        return prosecutionPartialLOV2;
    }

    public void setPoliceStationEntityLOV21(RichInputListOfValues policeStationEntityLOV21) {
        this.policeStationEntityLOV21 = policeStationEntityLOV21;
    }

    public RichInputListOfValues getPoliceStationEntityLOV21() {
        return policeStationEntityLOV21;
    }

    public void setProsecutionKolyaLOV12(RichInputListOfValues prosecutionKolyaLOV12) {
        this.prosecutionKolyaLOV12 = prosecutionKolyaLOV12;
    }

    public RichInputListOfValues getProsecutionKolyaLOV12() {
        return prosecutionKolyaLOV12;
    }

    private ViewObject applyCr(String crParam, String crParamValue, String crName, ViewObject viewObject) {
        ViewCriteria vc = viewObject.getViewCriteriaManager().getViewCriteria(crName);
        vc.resetCriteria();
        viewObject.ensureVariableManager().setVariableValue(crParam, crParamValue);
        viewObject.applyViewCriteria(vc);
        viewObject.executeQuery();
        return viewObject;
    }

    public void setCasePanel(RichPanelGroupLayout casePanel) {
        this.casePanel = casePanel;
    }

    public RichPanelGroupLayout getCasePanel() {
        return casePanel;
    }

    public void setSearchPanel2(RichPanelGroupLayout searchPanel2) {
        this.searchPanel2 = searchPanel2;
    }

    public RichPanelGroupLayout getSearchPanel2() {
        return searchPanel2;
    }

    public void setSearchPanel1(RichPanelGroupLayout searchPanel1) {
        this.searchPanel1 = searchPanel1;
    }

    public RichPanelGroupLayout getSearchPanel1() {
        return searchPanel1;
    }

    public String editBtn() {
        
        //String BC_CASE_NUMBER = ADFUtil.getElExpression("#{bindings.BC_CASE_NUMBER.inputValue}").toString();
        //String BC_CASE_TYPE = ADFUtil.getElExpression("#{bindings.BC_CASE_TYPE.inputValue}").toString();
        //String BC_ENTITY_ID = ADFUtil.getElExpression("#{bindings.BC_ENTITY_ID.inputValue}").toString();
        String PPAR_POLICE_NUMBER = ADFUtil.getElExpression("#{bindings.PPAR_POLICE_NUMBER.inputValue}").toString();
        String PPAR_ENTITY_ID = ADFUtil.getElExpression("#{bindings.PPAR_ENTITY_ID.inputValue}").toString();
        String PPAR_BUSINESS_CASE_TYPE = ADFUtil.getElExpression("#{bindings.PPAR_BUSINESS_CASE_TYPE.inputValue}").toString();
        String PPAR_ORIGIN_ENTITY = ADFUtil.getElExpression("#{bindings.PPAR_ORIGIN_ENTITY.inputValue}").toString();
        String PPAR_PAR_YEAR = ADFUtil.getElExpression("#{bindings.PPAR_PAR_YEAR.inputValue}").toString();
        String BCLS_SEQ = ADFUtil.getElExpression("#{bindings.BCLS_SEQ.inputValue}").toString();
        String BCLS_YEAR = ADFUtil.getElExpression("#{bindings.BCLS_YEAR.inputValue}").toString();
        String BCLS_LEDGER_ID = ADFUtil.getElExpression("#{bindings.BCLS_LEDGER_ID.inputValue}").toString();
        String BCD_ENTITY_ID = ADFUtil.getElExpression("#{bindings.BCD_ENTITY_ID.inputValue}").toString();
        
        ADFUtil.findIterator("AuditBusinessCaseVO1Iterator").getCurrentRow().setAttribute("CaseNumber", PPAR_POLICE_NUMBER);
        getInsertAuditRowFunc("type", "editedAttribute", "oldValue", "newValue", "bcId");
        ADFUtil.findOperation("Commit").execute();
        
        return null;
    }

    public void test(ActionEvent actionEvent) {
        // Add event code here...
        System.err.println("Test*************************");
    }
}
