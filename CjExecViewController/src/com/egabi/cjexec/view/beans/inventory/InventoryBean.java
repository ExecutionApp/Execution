package com.egabi.cjexec.view.beans.inventory;

import com.rss.cj.business.adfimpl.valueobjects.UserData;
import com.rss.cj.notarizationRetainer.NotarizationRetainerPartners;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.model.appmodules.OppositionAMImpl;
import com.egabi.cjexec.model.appmodules.SimplifiedModeAMImpl;
import com.egabi.cjexec.model.baseclasses.DateConversionUtils;
import com.egabi.cjexec.model.roviews.inventory.PartyProceduresRVORowImpl;
import com.egabi.cjexec.model.views.inventory.ExcFolderProceduresViewVORowImpl;
import com.egabi.cjexec.view.NTRA.GeneralResponse;
import com.egabi.cjexec.view.NTRA.NtraClient;
import com.egabi.cjexec.view.NTRA.NtraDBHelper;
import com.egabi.cjexec.view.beans.bcDetails.BCDetails;
import com.egabi.cjexec.view.beans.common.ExcTemplate;
import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PersonData;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import groovy.text.Template;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;

import java.math.BigDecimal;

import java.nio.file.Files;

import java.sql.Connection;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Instant;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Locale;

import java.util.Vector;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputComboboxListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.stellent.ridc.model.DataObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;


public class InventoryBean extends PrintEnabledBean implements Serializable  {
    //Logger logger = Logger.getLogger(InventoryBean.class.getName());
    private String procedureSerial, procedureStatus, primaryLawerRetainerNumber, primaryLawerName, secondryLawerRetainerNumber, secondryLawerName;
    private String requesterLabel;
    private int rulesOrProcedures;
 private double gharamaYawmeya;
    private double gharamaYawmeyaRemain;
    private double taqseetGharamaRemain;
    private int tashgeelDays;
    //  private double penaltyPaymentAmount;
    private boolean toDay;
    private UploadedFile tasdekFile;
    private UploadedFile personalPic;
    private UploadedFile procurationPic;
    private UploadedFile cancelFile;
    private UploadedFile editFile;
    private UploadedFile execReport9;
    private UploadedFile ReinstatelProcedureTemplate;
    private UploadedFile ReinstatelProcedureTemplateSecond;
   
    //  private UploadedFile reportPic;
    //  private UploadedFile report26Pic;
    private UploadedFile oppositionReport;
    private UploadedFile oppositionRequestReport;
    private UploadedFile reportAppeal;
    //  private UploadedFile appealRequestReport;
    private UploadedFile criminalOrderReport;
    //  private UploadedFile criminalOrderRequestReport;
    private UploadedFile payment;
    private RichInputListOfValues prosecutionKolyaLOV;
    private RichInputListOfValues prosecutionPartialLOV;
    private RichInputListOfValues policeStationEntityLOV;
    private RichInputListOfValues prosecutionKolyaLOV2;
    private RichInputListOfValues prosecutionPartialLOV2;
    private RichInputListOfValues policeStationEntityLOV2;
    private BigDecimal guranteeAmount, penaltyAmount;
    private Date minDate;
    private java.util.Date date = new java.util.Date();

    /* Iterator used in screen */
    private final String ACTION_ITERATOR = "ActionsRVO1Iterator";
    private final String RESULT_SEARCH_ITERATOR = "SearchInventoryCaseResultRVO1Iterator";
    private final String SERCH_INVENTORY_ITERATOR = "SearchInventoryRVO1Iterator";
    private final String PREV_ALL_PROCEDURE_ITERATOR = "PreviousProceduresRVO2Iterator";
    private final String PREV_ROW_PROCEDURE_ITERATOR = "PreviousProceduresRVO3Iterator";
    private final String FILE_TYPES_ITERATOR = "FilesTypesVO1Iterator";
    private final String REUPLOAD_FILE_TYPES_ITERATOR = "PreviousProceduresRVO4Iterator";
    private final String PARAMS_ITERATOR = "ParamsRoView1Iterator";

    private RichInputFile uploadFileComp;
    private RichInputText outVisible;
    private RichActiveOutputText messBind;
    private RichSelectOneChoice degreeBind;
    private RichInputDate dateHearBind;
    private RichInputFile jdgeFile;
    private RichPanelGroupLayout hidePanals;
    private RichInputDate endDateBind;
    private RichInputDate startDateBind;
    private RichInputDate deliveryDateBind;
    private RichInputText actionNotesBind;
    private RichInputText kinshipBind;
    private RichInputText PhoneNumBind;
    private RichInputText guardianNameBind;
    private RichInputText guardianNationalIdBind;
    private RichSelectOneChoice actionTypeIdBind;
    private RichInputDate actionDateBind;
    private RichInputText testTxtBind;
    private RichInputText onlinePaymentBtnBind;
    /* / Iterator used in screen */

    //////// Start instances ///////////
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

    public void setPrimaryLawerRetainerNumber(String primaryLawerRetainerNumber) {
        this.primaryLawerRetainerNumber = primaryLawerRetainerNumber;
    }

    public String getPrimaryLawerRetainerNumber() {
        return primaryLawerRetainerNumber;
    }

    public void setPrimaryLawerName(String primaryLawerName) {
        this.primaryLawerName = primaryLawerName;
    }

    public String getPrimaryLawerName() {
        return primaryLawerName;
    }

    public void setSecondryLawerRetainerNumber(String secondryLawerRetainerNumber) {
        this.secondryLawerRetainerNumber = secondryLawerRetainerNumber;
    }

    public String getSecondryLawerRetainerNumber() {
        return secondryLawerRetainerNumber;
    }

    public void setSecondryLawerName(String secondryLawerName) {
        this.secondryLawerName = secondryLawerName;
    }

    public String getSecondryLawerName() {
        return secondryLawerName;
    }

    public void setRequesterLabel(String requesterLabel) {
        this.requesterLabel = requesterLabel;
    }

    public String getRequesterLabel() {
        return requesterLabel;
    }

    public void setTasdekFile(UploadedFile tasdekFile) {
        this.tasdekFile = tasdekFile;
    }

 public UploadedFile getTasdekFile() {
        System.out.println("-------------------------------- Oraby getTasdekFile---------------------------");
        System.out.println(JSFUtils.getFromSession("sessionid") + " getTasdekFile() method  session id is " +
                           JSFUtils.getFromSession("loginid"));
        System.out.println("---------------------------------------------------------------------");
        return tasdekFile;
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

    public void setPoliceStationEntityLOV2(RichInputListOfValues policeStationEntityLOV2) {
        this.policeStationEntityLOV2 = policeStationEntityLOV2;
    }

    public RichInputListOfValues getPoliceStationEntityLOV2() {
        return policeStationEntityLOV2;
    }

    public void setGuranteeAmount(BigDecimal guranteeAmount) {
        this.guranteeAmount = guranteeAmount;
    }

    public BigDecimal getGuranteeAmount() {
        return guranteeAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setRulesOrProcedures(int rulesOrProcedures) {
        this.rulesOrProcedures = rulesOrProcedures;
    }

    public int getRulesOrProcedures() {
        return rulesOrProcedures;
    }

    public void setToDay(boolean toDay) {
        this.toDay = toDay;
    }

    public boolean isToDay() {
        return toDay;
    }

    public void setPersonalPic(UploadedFile personalPic) {
        this.personalPic = personalPic;
    }

    public UploadedFile getPersonalPic() {
        return personalPic;
    }

    public void setProcurationPic(UploadedFile procurationPic) {
        this.procurationPic = procurationPic;
    }

    public UploadedFile getProcurationPic() {
        return procurationPic;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public java.util.Date getDate() {
        return date;
    }


    public java.util.Date getDate2() {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -1);


        return c.getTime();
    }

    public void setReportAppeal(UploadedFile reportAppeal) {
        this.reportAppeal = reportAppeal;
    }

    public UploadedFile getReportAppeal() {
        return reportAppeal;
    }

    public void setOppositionReport(UploadedFile oppositionReport) {
        this.oppositionReport = oppositionReport;
    }

    public UploadedFile getOppositionReport() {
        return oppositionReport;
    }

    public void setOppositionRequestReport(UploadedFile oppositionRequestReport) {
        this.oppositionRequestReport = oppositionRequestReport;
    }

    public UploadedFile getOppositionRequestReport() {
        return oppositionRequestReport;
    }


    public void setCriminalOrderReport(UploadedFile criminalOrderReport) {
        this.criminalOrderReport = criminalOrderReport;
    }

    public UploadedFile getCriminalOrderReport() {
        return criminalOrderReport;
    }

    public void setPayment(UploadedFile payment) {
        this.payment = payment;
    }

    public UploadedFile getPayment() {
        return payment;
    }


    //////// End instances ///////////
    /*
   * Globals Methods in this Bean
   * B.By Belal Abdalhuk
   * */
    private Object getAttributeViewValue(String iterator, String attrName) {
        return ADFUtil.findIterator(iterator).getViewObject().first().getAttribute(attrName);
    }

    private void setAttributeViewValue(String iterator, String attrName, Object value) {
        ViewObjectImpl vo = (ViewObjectImpl) ADFUtil.findIterator(iterator).getViewObject();
        vo.first().setAttribute(attrName, value);
    }

    private Object getAttributeValue(String iterator, String attrName) {
        return ADFUtil.findIterator(iterator).getCurrentRow().getAttribute(attrName);
    }

    private void setAttributeValue(String iterator, String attrName, Object value) {
        ADFUtil.findIterator(iterator).getCurrentRow().setAttribute(attrName, value);
    }

    private UserInfo getUserInfo() {
        return (UserInfo) JSFUtils.getFromSession("userInfo");
    }

    private boolean invals(Object value, Object... valueList) {
        if ((value == null) || (valueList == null)) {
            return false;
        }
        for (int i = 0; i < valueList.length; i++) {
            if (valueList[i] != null) {
                if (value.toString().equalsIgnoreCase(valueList[i].toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public void callJsFunction(String funName) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, funName + "();");
    }

    public void showErrorMessageForComponentById(String message, String id) {
        FacesContext.getCurrentInstance().addMessage(ADFUtil.findComponentInRoot(id).getClientId(),
                                                     new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
    }

    public java.util.Date convertFromObjectToUtilDate(Object dateObject) {
        if (dateObject == null)
            return null;
        String inputDate = dateObject.toString();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date empSheetDtUtil = null;
        try {
            empSheetDtUtil = formatter.parse(inputDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empSheetDtUtil;
    }

    @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
    private void saveDataWithMessage() {
        try {
            //            System.err.println("ERRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
            ADFUtil.executeVoidOperation("Commit", null);
            saveMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveMessage() {
        JSFUtils.showFacesInfoMessageFromBundle("bundle", "SAVE_SUCCESS");
    }

    private double getDoubleValueFromObject(Object value) {
        double theVal = value != null ? Double.parseDouble(value.toString()) : 0.0;
        return theVal;
    }

    private int getIntValueFromObject(Object value) {
        int theVal = value != null ? Integer.parseInt(value.toString()) : 0;
        return theVal;
    }

    private BigDecimal getBigDecimalValueFromObject(Object value) {
        BigDecimal theVal = value != null ? new BigDecimal(value.toString()) : null;
        return theVal;
    }

    // End Global ////

    // used in screen egraa hasr from abroad
    public String doSearch() {
        int userEntityId = 0;
        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            userEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            userEntityId = Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        ViewObject searchInventoryView = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject();
        Row searchInventoryRow = searchInventoryView.getCurrentRow();

        int kolya =
            Integer.parseInt(searchInventoryRow.getAttribute("ProsecutionKolyaId") == null ? "0" :
                             searchInventoryRow.getAttribute("ProsecutionKolyaId").toString());

        ViewObject resultView = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();

        //            am.getExcProceduresLogView1();

        String condition =
            " (ENTITY_ID = " + userEntityId + " OR ENTITY_ID IN (SELECT ID FROM ENTITY WHERE PARENT_ENTITY_ID = " +
            userEntityId + ")) ";

        if (kolya != 0) {
            condition += " AND LOGGED_IN_ENTITY_ID = " + searchInventoryRow.getAttribute("ProsecutionKolyaId");
            //                " OR LOGGED_IN_ENTITY_ID IN (SELECT ID FROM ENTITY WHERE PARENT_ENTITY_ID = " + searchInventoryRow.getAttribute("ProsecutionKolyaId") + ")";
        }
        //        if(searchInventoryRow.getAttribute("ProsecutionPartialId") != null){
        //            condition += " AND LOGGED_IN_ENTITY_ID = " + searchInventoryRow.getAttribute("ProsecutionPartialId") ;
        //        }

        if (searchInventoryRow.getAttribute("DateFrom") != null && searchInventoryRow.getAttribute("DateTo") != null) {
            condition +=
                " AND CREATED_DATE BETWEEN to_date('" + searchInventoryRow.getAttribute("DateFrom") +
                "','yyyy-mm-dd') AND to_date('" + searchInventoryRow.getAttribute("DateTo") + "','yyyy-mm-dd')";
        }

        if (toDay) {
            condition += " AND  CREATED_DATE = to_date(to_char(SYSDATE,'yyyy-mm-dd'),'yyyy-mm-dd') ";
        }
        resultView.setWhereClause(condition);
        //        System.err.println(resultView.getQuery());
        resultView.executeQuery();
        if (resultView.getEstimatedRowCount() == 0)
            JSFUtils.showInfCaseNotFound();
        return null;
    }

    //???????
    public void capacityChangeListener(ValueChangeEvent valueChangeEvent) {
        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        ViewObject searchInventoryResultView = am.getSearchInventoryResultRVO1();
        Row searchInventoryResultRow = searchInventoryResultView.first();

        ViewObject actionsView = am.getActionsRVO1();
        Row actionsRow = actionsView.getCurrentRow();

        long folderId = Long.parseLong(searchInventoryResultRow.getAttribute("FolderId").toString());
        long partyId = Long.parseLong(searchInventoryResultRow.getAttribute("PartyId").toString());

        OppositionAMImpl oppoAM = (OppositionAMImpl) root.getOppositionAM1();
        procedureSerial = oppoAM.getProcedureIdFromExcFolderActions(folderId);

        if (procedureSerial != "") {
            procedureStatus =
                "\u062a\u0645 \u0627\u0639\u062a\u0645\u0627\u062f \u0631\u0626\u064a\u0633 \u0627\u0644\u0646\u064a\u0627\u0628\u0629 " +
                "\u0644\u0637\u0644\u0628 \u0627\u0644\u062a\u0645\u0643\u064a\u0646";
            ADFUtil.setEL("#{pageFlowScope.showProcedureSerial}", "show");
        }
        int capacityTypeId = Integer.parseInt(valueChangeEvent.getNewValue().toString());

        if (capacityTypeId == 2 || capacityTypeId == 3) {
            System.out.println("capacityTypeId == 2   ------------------");
            String lawyer[] = am.getLawyerData(partyId);
            actionsRow.setAttribute("PrimaryLawerId", lawyer[0]);
            primaryLawerRetainerNumber = lawyer[1];
            primaryLawerName = lawyer[2];
        }
    }

    private boolean checkNullVals(String iterator, String attrName, String id) {
        System.err.println("attrName " + attrName);
        if (getAttributeValue(iterator, attrName) == null) {
            String id9Error = "\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0627\u0644\u062D\u0642\u0644 ";
            showErrorMessageForComponentById(id9Error, id);
            return false;
        }

        return true;
    }
//----
    private int validateActionsRow() {
        System.out.println("validateActionsRow   -------------- ");
        int caseSchema =
            ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
            Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;
System.out.println(checkNullVals(ACTION_ITERATOR, "ActionTypeId", "soc5")+"***checkNullVals***"+checkNullVals(ACTION_ITERATOR, "ActionDate", "id9") + "------"+checkNullVals(ACTION_ITERATOR, "CapacityTypeId", "soc27"));
        // gonah sahafeen
        int journalistFlag =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") + "") : -1;

        if (checkNullVals(ACTION_ITERATOR, "ActionTypeId", "soc5") == false)
            return 0;

        if (checkNullVals(ACTION_ITERATOR, "ActionDate", "id9") == false)
            return 0;

        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        if (actionTypeId == 36) {
            if (!hasPrevDegreeProcedure()) {
                return 0;

            }
        }
        if (!invals(actionTypeId, 32, 33, 31, 30) &&
            (checkNullVals(ACTION_ITERATOR, "CapacityTypeId", "soc27") == false))
            return 0;

        if (actionTypeId == 7 || actionTypeId == 77) {
            int feesTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "FeesType").toString());
            String errMessage = "\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0627\u0644\u062D\u0642\u0644";
            if (feesTypeId == 3) {
                if (getAttributeValue(ACTION_ITERATOR, "ClaimFolderId") == null) {
                        showErrorMessageForComponentById(errMessage, "soc34");
                    return 0;
                }

                if ((actionTypeId == 7 || actionTypeId == 77) && feesTypeId == 3 &&
                    getAttributeValue(ACTION_ITERATOR, "ClaimFolderId") != null) {
                        Row searchInventoryCaseResultRow = null;

                    ViewObject searchInventoryCaseResultView =
                        ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
                        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

                        OperationBinding bindingId = ADFUtil.findOperation("ValidatePreviousClaim");
                    bindingId.getParamsMap().put("foldId",
                                                 new BigDecimal(searchInventoryCaseResultRow.getAttribute("FolderId").toString()));

                        Object obj = bindingId.execute();
                        BigDecimal resId = null;
                        if (obj != null) {
                            resId = new BigDecimal(obj.toString());
                        if (resId.compareTo(new BigDecimal(0)) == 1) {

                            showErrorMessageForComponentById("\u0644\u0627 \u064a\u0645\u0643\u0646\u0643 \u0627\u0644\u0633\u062f\u0627\u062f \u0644\u0648\u062c\u0648\u062f \u0645\u0637\u0627\u0644\u0628\u0629",
                                                             "soc34");
                                return 0;
                            }

                        }

                }


            }
            System.out.println("validateActionsRow   -------------- 11111111");
            if (getAttributeValue(ACTION_ITERATOR, "FeesType") == null) {
                showErrorMessageForComponentById(errMessage, "soc4");
                return 0;
            } else if (getAttributeValue(ACTION_ITERATOR, "NumberOfPaied") == null) {
                if (actionTypeId == 7) {
                showErrorMessageForComponentById(errMessage, "it23");
                return 0;
                }
            } else if (getAttributeValue(ACTION_ITERATOR, "PaymentAmount") == null) {
                double totalVal =
                    getAttributeValue(ACTION_ITERATOR, "TotalAmount") != null ?
                    Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "TotalAmount") + "") :
                    Double.parseDouble("0");
                double paidUpAmount =
                    getAttributeValue(ACTION_ITERATOR, "PainUpAmount") != null ?
                    Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "PainUpAmount") + "") :
                    Double.parseDouble("0");
                if (totalVal > paidUpAmount) {
                    showErrorMessageForComponentById(errMessage, "it19");
                    return 0;
                }
            }
        }
        System.out.println("validateActionsRow   -------------- 2222222222222222");
        if (actionTypeId == 34 && getAttributeValue(ACTION_ITERATOR, "Adress") == null) {
            String errMessage = "\u064A\u062C\u0628 \u0627\u0636\u0627\u0641\u0629 \u0639\u0646\u0648\u0627\u0646";
            showErrorMessageForComponentById(errMessage, "it12");
            return 0;
        }
        if (actionTypeId == 37) {
            RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it33");
            String fullName = inp.getValue().toString();
            if (actionTypeId == 37 && fullName == null) {
                System.out.println("FFFFFFFFFFF");
                String errMessage = "\u064A\u062C\u0628 \u0627\u0636\u0627\u0641\u0629 \u0639\u0646\u0648\u0627\u0646";
                showErrorMessageForComponentById(errMessage, "it33");
                return 0;
            } else {
                //            ADFUtil.findOperation("Rollback");
                System.out.println("TTTTTTTTTTTTTTTT");

                JSFUtils.setExpressionValue("#{bindings.EditName.inputValue}", fullName);
             //   setName(fullName);
                //                JSFUtils.setExpressionValue("#{bindings.PartyName1.inputValue}", fullName);
            }

        }
        System.out.println("validateActionsRow   -------------- 333333333333333");
        if (actionTypeId == 5 &&
            (getAttributeValue(ACTION_ITERATOR, "DateFrom") == null ||
             getAttributeValue(ACTION_ITERATOR, "DateTo") == null)) {
            if (checkNullVals(ACTION_ITERATOR, "DateFrom", "id2") == false)
                return 0;

            if (checkNullVals(ACTION_ITERATOR, "DateTo", "id3") == false)
                return 0;
        }
        if (invals(actionTypeId, 149, 150, 151, 156, 153, 154, 155)) {
                if (checkNullVals(ACTION_ITERATOR, "VetoDate", "id17") == false)
                    return 0;
            }

        if ((invals(actionTypeId, 1, 2, 8, 9, 23, 24, 14 , 168) && journalistFlag != 1) ||
            (invals(actionTypeId, 19) && caseSchema == -1)) {
            if (getAttributeValue(ACTION_ITERATOR, "HearingDateId") == null &&
                getAttributeValue(ACTION_ITERATOR, "PreviousVeto") ==
                null) {
                //(checkNullVals(ACTION_ITERATOR, "HearingDateId", "hearingDateAllId") == false  || checkNullVals(ACTION_ITERATOR, "PreviousVeto", "id9") == false)
                System.out.println(getAttributeValue(ACTION_ITERATOR, "HearingDateId") + "**------------***" +
                                   getAttributeValue(ACTION_ITERATOR, "PreviousVeto"));
            System.err.println("validateActionsRow   ----------wwwwww---- 4444444444444");
            int BCTypeId = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString());
            if (invals(actionTypeId , 168) && BCTypeId == 19 && "1".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionJudegementFlag") + ""))
                return 1;
            else if (invals(actionTypeId , 168) && BCTypeId == 19 && "2".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionJudegementFlag") + ""))
            return 1;
            else
            return 0;
        }
        }

        if (getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber") != null ||
            getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") != null) {
            if ("1".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") + "")) {
                String skipId = getAttributeValue(RESULT_SEARCH_ITERATOR, "SkipId") + ""; // N
                if (skipId.equalsIgnoreCase("N")) {

             String Passport = getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber").toString();
                    if (Passport.length() < 8) {
                        showErrorMessageForComponentById("\u062C\u0648\u0627\u0632 \u0627\u0644\u0633\u0641\u0631 \u063A\u064A\u0631 \u0635\u062D\u064A\u062D",
                                                         "it7");
                 System.err.println("validateActionsRow   ----------555585---- 4444444444444");
                        return 0;
            }
                }
            }

        }
        System.out.println("validateActionsRow   -------------- 4444444444444");
        if (!invals(actionTypeId, 32, 33, 31, 30) &&
            (getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber") == null ||
             getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") == null)) {
            String errMessage =
                "\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0648\u0631\u0642\u0645 \u062A\u062D\u0642\u064A\u0642 \u0627\u0644\u0634\u062E\u0635\u064A\u0629\n";
            String skipId = getAttributeValue(RESULT_SEARCH_ITERATOR, "SkipId") + ""; // N
            if (skipId.equalsIgnoreCase("N")) {
            if (actionTypeId != 7) {
                if (getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber") == null) {


                        BigDecimal ty =
                            (BigDecimal) JSFUtils.resolveExpression("#{bindings.CapacityTypeId.inputValue}");
                        System.out.println("GGGG --" +
                                           JSFUtils.resolveExpression("#{bindings.CapacityTypeId.inputValue}"));

                    if (actionTypeId != 12) {
                        if (ty.compareTo(new BigDecimal(3)) == 0)
                            return 1;
                        showErrorMessageForComponentById(errMessage, "it7");
                    }
                }

                if (actionTypeId == 12) {
                    return 1;
                }
                if (getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") == null) {
                    showErrorMessageForComponentById(errMessage, "soc2");
                }
                System.out.println("FFFFFFFFFFFFF");
                    return 0;
            }
            }
        } else
            return 1;
        System.err.println(getAttributeValue(ACTION_ITERATOR, "FolderGharama") + ">>>>>>>" + isVisibleHabs());
        if (actionTypeId == 33 && isVisibleHabs() &&
            ("N".equalsIgnoreCase(getAttributeValue(ACTION_ITERATOR, "FolderGharama") + ""))) {
            String errMessage =
                "\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0628\u064A\u0627\u0646\u0627\u062A \u0627\u0644\u062D\u0635\u0631";

            if (getAttributeValue(ACTION_ITERATOR, "SequenceNum") == null) {
                JSFUtils.addFacesErrorMessage(errMessage);
                return 0;
            }

            if (getAttributeValue(ACTION_ITERATOR, "Year") == null) {
                JSFUtils.addFacesErrorMessage(errMessage);
                return 0;
            }

        }
        System.out.println("validateActionsRow   -------------- 555555555555");
        if (actionTypeId == 33 && isVisibleHabs() &&
            ("Y".equalsIgnoreCase(getAttributeValue(ACTION_ITERATOR, "FolderGharama") + ""))) {
            String errMessage =
                "\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0627\u0644\u0639\u0642\u0648\u0628\u0629";
            JSFUtils.addFacesErrorMessage(errMessage);
            return 0;
        }


        return 1;
    }

    //    public static void main(String[] args) {
    //      new InventoryBean().dateConvert("01-NOV-20")  ;
    //   }

    public static String dateConvert(String deliverydate) {
        System.out.println(deliverydate.split("-").length + "deliverydate is----**** " + deliverydate);
        BigDecimal year = new BigDecimal(0);
        String dateAfterTerra = null;
        if (deliverydate.split("-").length < 2) {
           String[] split = deliverydate.split("/");
            if (split.length > 1) {
                if (split[2].length() < 3) {
                    System.err.println("split.length" + split[2].length());
                    year = new BigDecimal(split[2]).add(new BigDecimal(2000));
                } else {
                    System.err.println("split.length" + split[2].length());

                    year = new BigDecimal(split[2]);
                }
                dateAfterTerra = split[0].concat("-" + split[1].concat("-" + year));

            }

        } else {
          String[] split = deliverydate.split("-");
                if (split.length > 1) {
                    if (split[2].length() < 3) {
                        System.err.println("split.length" + split[2].length());
                        year = new BigDecimal(split[2]).add(new BigDecimal(2000));
                    } else {
                        System.err.println("split.length" + split[2].length());

                        year = new BigDecimal(split[2]);
                    }
                    dateAfterTerra = split[0].concat("-" + split[1].concat("-" + year));

                }
            }
        SimpleDateFormat sdf;
        if (deliverydate.split("-").length < 2)
           sdf = new SimpleDateFormat("dd-MM-yyyy");
       else
        sdf = new SimpleDateFormat("dd-MMM-yyyy");


        java.util.Date date = null;
        try {
            System.out.println("dateAfterTerra is " + dateAfterTerra);
            if (dateAfterTerra != null)
                date = sdf.parse(dateAfterTerra);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (date != null)
            return sdf.format(date);
        else
            return null;
    }

    private boolean validateDateTaqseerGharama() {
        boolean isOk = true;
        java.util.Date procedureDate = null;
        java.util.Date previousProcedureDate = null;
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (actionTypeId == 67) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (66)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            System.out.println("QQQQQQQQQQQ4444");
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0623\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0637\u0644\u0628 \u062a\u0642\u0633\u064a\u0637 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return isOk;
    }

    private boolean validateDateTanfeezMoaqat() {
        boolean isOk = true;
        java.util.Date procedureDate = null;
        java.util.Date previousProcedureDate = null;
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (actionTypeId == 57) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (64)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            System.out.println("QQQQQQQQQQQ555");
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0623\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0637\u0644\u0628 \u0648\u0642\u0641 \u062a\u0646\u0641\u064a\u0630 \u0645\u0624\u0642\u062a\u0627",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return isOk;
    }

    private boolean validateDateEsterdadKafala() {
        boolean isOk = true;
        java.util.Date procedureDate = null;
        java.util.Date previousProcedureDate = null;
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (actionTypeId == 53) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (52)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0637\u0644\u0628 \u0625\u0633\u062a\u0631\u062f\u0627\u062f \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return isOk;
    }

    private boolean validateDateGharamaYawmeya() {
        boolean isOk = true;
        java.util.Date procedureDate = null;
        java.util.Date penaltyDueDate = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
            penaltyDueDate = sdf.parse(ADFUtil.evaluateEL("#{pageFlowScope.penaltyDueDate}") + "");
            boolean procedureAfterPenalty = ADFUtil.dateAfterDate(penaltyDueDate, procedureDate);
            if (!procedureAfterPenalty) {
                setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0623\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u062a\u0627\u0631\u064a\u062e \u0644\u0627\u062d\u0642 \u0644\u062a\u0627\u0631\u064a\u062e \u0628\u062f\u0621 \u062d\u0633\u0627\u0628 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isOk;
    }

    private boolean validateHearingDateTaqseerFatra() {
        boolean isOk = true;
        java.util.Date procedureDate = null;
        java.util.Date previousProcedureDate = null;
        java.util.Date previousHearingDate = null;
        java.util.Date hearingDateAll = null;

        java.util.Date previousActionDate = null;
        java.util.Date firstHearingDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (getAttributeValue(ACTION_ITERATOR, "HearingDateAll") != null) {
                hearingDateAll = sdf.parse(getAttributeValue(ACTION_ITERATOR, "HearingDateAll").toString());
            }
            procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());

            ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                //                view2.setWhereClause("PROCEDURE_TYPE in (45)");
                //                view2.executeQuery();
                //                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                ////                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0639\u0645\u0644 \u0627\u062c\u0631\u0627\u0621 \u062a\u0642\u0635\u064a\u0631 \u062c\u0644\u0633\u0629 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0645\u0631\u0629",
                ////                                           FacesMessage.SEVERITY_ERROR);
                ////                    return false;
                ////                    previousActionDate = sdf.parse(view2.first().getAttribute("ProcedureDate")+"");
                ////
                ////                    boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousActionDate, procedureDate);
                ////                    if (!procedureAfterProcedure) {
                ////                        setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                ////                     ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u0627\u0644\u0633\u0627\u0628\u0642",
                ////                                         FacesMessage.SEVERITY_ERROR);
                ////                    return false;
                ////                    }
                ////                    firstHearingDate = sdf.parse(view2.first().getAttribute("HearingDate")+"");
                ////
                ////                    boolean secondhearingBeforeFirsthearing = ADFUtil.dateAfterDate(hearingDateAll, firstHearingDate);
                ////                    if (!secondhearingBeforeFirsthearing) {
                ////                        setAttributeValue(ACTION_ITERATOR, "HearingDateAll", null);
                ////                     ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0642\u0628\u0644 \u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u0627\u0644\u0633\u0627\u0628\u0642",
                ////                                         FacesMessage.SEVERITY_ERROR);
                ////                    return false;
                ////                    }
                //                }

                view2.setWhereClause("PROCEDURE_TYPE in (1,2,9,45)");
                view2.setOrderByClause("ACTS_TYPE_ID desc");
                view2.executeQuery();

                if ((view2.getRowCount() == 2 || view2.getRowCount() == 4 || view2.getRowCount() == 6) &&
                    Integer.parseInt(view2.first().getAttribute("ActsTypeId") + "") == 45) {
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u062a\u0642\u0635\u064a\u0631 \u062c\u0644\u0633\u0629",
                                        FacesMessage.SEVERITY_ERROR);
                    view2.setWhereClause(null);
                    view2.executeQuery();
                    return false;
                }
                view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,24)");
                view2.setOrderByClause("id desc");
                view2.executeQuery();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    previousProcedureDate = sdf.parse(view2.first().getAttribute("ProcedureDate") + "");
                    System.err.println("previousProcedureDate = " + previousProcedureDate);
                    previousHearingDate = sdf.parse(view2.first().getAttribute("HearingDate") + "");
                    System.err.println("HearingDate = " + previousHearingDate);
                }
                boolean procedureAfterreservation = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                if (!procedureAfterreservation) {
                    System.out.println("QQQQQQQQQQQ66666");
                    setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
                boolean procedureBeforeHearing = ADFUtil.dateAfterDate(procedureDate, previousHearingDate);
                if (!procedureBeforeHearing) {
                    System.out.println("QQQQQQQQQQQ77777777");
                    setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
                if (hearingDateAll == null) {
                    setAttributeValue(ACTION_ITERATOR, "HearingDateAll", null);
                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
                boolean hearingBeforeHearing = ADFUtil.dateAfterDate(hearingDateAll, previousHearingDate);
                if (!hearingBeforeHearing) {
                    setAttributeValue(ACTION_ITERATOR, "HearingDateAll", null);
                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isOk;
    }



    private boolean validateEmps() {
        System.out.println("ddddddddddddddddddddddddddd");
        boolean isOk = true;
        
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        int BCTypeId = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString());
        
        if(BCTypeId == 19 && (actionTypeId == 1 || actionTypeId == 9))
        {
                System.out.println("empSSSSSSSSSSSSSSSSS");
            
                ADFUtil.showMessage("\u0625\u062C\u0631\u0627\u0621 \u063A\u064A\u0631 \u0645\u0633\u0645\u0648\u062D \u0628\u0647",
                                    FacesMessage.SEVERITY_ERROR);
                return false;
            
            }
        
        if(BCTypeId == 19)
        if (actionTypeId == 159  ) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                 
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (158 ,168)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count <1) { 
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
               
                view.setWhereClause(null);
                view.executeQuery();}
                else
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        
        if (  actionTypeId == 161 ) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                 
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (160)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count <1) { 
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
               
                view.setWhereClause(null);
                view.executeQuery();}
                else
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
         
    return isOk;
    }
    
    
    
    private boolean validateEconmics() {
        System.out.println("ddddddddddddddddddddddddddd");
        boolean isOk = true;
        
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        int BCTypeId = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString());
       /* 
        if(BCTypeId == 4 && (actionTypeId == 1 || actionTypeId == 9))
        {
                System.out.println("empSSSSSSSSSSSSSSSSS");
            
                ADFUtil.showMessage("\u0625\u062C\u0631\u0627\u0621 \u063A\u064A\u0631 \u0645\u0633\u0645\u0648\u062D \u0628\u0647",
                                    FacesMessage.SEVERITY_ERROR);
                return false;
            
            }
        
        if(BCTypeId == 4)
        if (actionTypeId == 164  ) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                 
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (163)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count <1) { 
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
               
                view.setWhereClause(null);
                view.executeQuery();}
                else
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        
        if (  actionTypeId == 166 ) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                 
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (165)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count <1) { 
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
               
                view.setWhereClause(null);
                view.executeQuery();}
                else
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        */
         
    return isOk;
    }


    private boolean validateHearingDate() {
        boolean isOk = true;
        java.util.Date hearingDateAll = null;
        java.util.Date procedureDate = null;
        java.util.Date reservationDate = null;
        java.util.Date previousProcedureDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());

        if (actionTypeId == 149 || actionTypeId == 150 || actionTypeId == 151 || actionTypeId == 156 ||
            actionTypeId == 153 || actionTypeId == 154 || actionTypeId == 155) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                previousProcedureDate  = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());

                System.out.println(previousProcedureDate + "OOO" + procedureDate);
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                System.out.println(previousProcedureDate + "OOO" + procedureDate + "YYYYY" + procedureAfterProcedure);
                        if (procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0645\u0631\u0627\u062c\u0639\u0647 \u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0637\u0639\u0646 \u0628\u0627\u0644\u0646\u0642\u0636",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (actionTypeId == actionTypeId) {
            try {
                reservationDate = sdf.parse(getAttributeValue(RESULT_SEARCH_ITERATOR, "HearingDate") + "");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                boolean procedureAfterreservation = ADFUtil.dateAfterDate(reservationDate, procedureDate);
                if (!procedureAfterreservation) {
                    System.out.println("QQQQQQQQQQQ8888888");
                    setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0623\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u062c\u0644\u0633\u0629 \u0627\u0644\u062d\u0643\u0645",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (actionTypeId == 8 || actionTypeId == 23 || actionTypeId == 157) {
            try {
                System.out.println("Veto Approved 5555555555555555555");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (55)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (actionTypeId == 150) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (149,150)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("HearingDate").toString());
                        System.err.println(previousProcedureDate + "VETOOOOOOOOOOOOOOOO" + procedureDate);
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    } else {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
                } else {
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);
                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (actionTypeId == 156) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO156");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (21)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count < 1) {
                            System.err.println("VETOOOOOOOOOOOOOOOO156");
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }

                    else

                    {
                            System.out.println("VETOOOOOOO***OOOOOOOOO");
                            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                view.setWhereClause("PROCEDURE_TYPE in (156)");
                                view.setOrderByClause("id desc");
                                view.executeQuery();
                            int count2 =
                                (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                                view.setWhereClause(null);
                                view.executeQuery();
                            if (count2 > 0) {

                                        view.setWhereClause("PROCEDURE_TYPE in (35)");
                                        view.setOrderByClause("id desc");
                                        view.executeQuery();
                                int count222 =
                                    (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                                        view.setWhereClause(null);
                                        view.executeQuery();


                                if (count222 < 1) {
                                        System.err.println("VETOOOOOOOO*****OOOOOOOO");
                                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                            FacesMessage.SEVERITY_ERROR);
                                     return false;
                                    }
                                }
                            }

                        }
                } else {
                        System.out.println("VETOOOOOOO-*-*-*-*-OOOOOOOOO");
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);
                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //////////////////////////////////////////


        if (actionTypeId == 149) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (20)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count < 1) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }

                    else

                    {

                            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                view.setWhereClause("PROCEDURE_TYPE in (149)");
                                view.setOrderByClause("id desc");
                                view.executeQuery();
                            int count2 =
                                (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                                view.setWhereClause(null);
                                view.executeQuery();
                            if (count2 > 0) {
                                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                            FacesMessage.SEVERITY_ERROR);
                                     return false;
                                    }
                            }

                        }
                } else {
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);
                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (actionTypeId == 151) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO222");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (149,150,151)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();


                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("HearingDate").toString());
                        System.err.println(previousProcedureDate + "VETOOOOOOOOOOOOOOOO22222" + procedureDate);
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    } else {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
                } else {
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);

                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ///////////////////////////////////////////

        if (actionTypeId == 154) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (153,154)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();
                    System.err.println("VETOOOOOOOOOOOOOOOO count " + count);
                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("HearingDate").toString());
                        System.err.println(previousProcedureDate + "VETOOOOOOOOOOOOOOOO" + procedureDate);
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    } else {
                            System.err.println("VETOOOOOOOOOOOOOOOO count else");
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
                } else {
                        System.err.println("VETOOOOOOOOOOOOOOOO count elsesssssss");
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);
                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (actionTypeId == 153) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (152)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    
                    BigDecimal DegreeId = null; 
                    DegreeId = (BigDecimal) getAttributeValue(RESULT_SEARCH_ITERATOR, "DegreeId");
                    
                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                   if (count < 1 && DegreeId != null && DegreeId.compareTo(BigDecimal.valueOf(7)) != 0) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }

                    else

                    {

                            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                view.setWhereClause("PROCEDURE_TYPE in (153)");
                                view.setOrderByClause("id desc");
                                view.executeQuery();
                            int count2 =
                                (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                                view.setWhereClause(null);
                                view.executeQuery();
                            if (count2 > 0 && DegreeId != null && DegreeId.compareTo(BigDecimal.valueOf(7)) != 0) {
                                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                            FacesMessage.SEVERITY_ERROR);
                                     return false;
                                    }
                            }

                        }
                } else {
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);
                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        if (actionTypeId == 155) {
            try {
                System.out.println("VETOOOOOOOOOOOOOOOO222");
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "VetoDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (153,154,155)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();


                    int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

                    view.setWhereClause(null);
                    view.executeQuery();
                    if (count > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("HearingDate").toString());
                        System.err.println(previousProcedureDate + "VETOOOOOOOOOOOOOOOO22222" + procedureDate);
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    } else {
                            setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                            ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                                FacesMessage.SEVERITY_ERROR);
                         return false;
                        }
                } else {
                        setAttributeValue(ACTION_ITERATOR, "VetoDate", null);
                        ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u0639\u0627\u062f\u0647 \u062a\u0631\u062a\u064a\u0628 \u0627\u0644\u0627\u062c\u0631\u0627\u062a",
                                            FacesMessage.SEVERITY_ERROR);

                     return false;
                    }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ///////////////////////////////////////////


        if (actionTypeId == 55 || actionTypeId == 56) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (54)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0637\u0644\u0628 \u062a\u0645\u0643\u064a\u0646 \u0645\u0646 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (actionTypeId == 47 || actionTypeId == 48) {
            try {
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (46)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                        boolean procedureAfterProcedure = ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                        if (!procedureAfterProcedure) {
                            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0637\u0644\u0628 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            return false;
                        }
                    }
                }
                view.setWhereClause(null);
                view.executeQuery();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        try {
            reservationDate = sdf.parse(getAttributeValue(RESULT_SEARCH_ITERATOR, "HearingDate") + "");
            procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
            boolean procedureAfterreservation = ADFUtil.dateAfterDate(reservationDate, procedureDate);
            if (!procedureAfterreservation) {
                System.out.println("QQQQQQQQQQQ9999");
                setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0623\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u062c\u0644\u0633\u0629 \u0627\u0644\u062d\u0643\u0645",
                                    FacesMessage.SEVERITY_ERROR);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        }
        if (getAttributeValue(ACTION_ITERATOR, "HearingDateAll") != null) {
            try {
                hearingDateAll = sdf.parse(getAttributeValue(ACTION_ITERATOR, "HearingDateAll").toString());
                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

            boolean hearingDateAfterActionDate = true;
            if (hearingDateAll != null) {
                hearingDateAfterActionDate = ADFUtil.dateAfterDate(procedureDate, hearingDateAll);
                if (!hearingDateAfterActionDate) {
                    System.out.println("QQQQQQQQQQQ10000000");
                    setAttributeValue(ACTION_ITERATOR, "HearingDateAll", null);
                    ADFUtil.showMessage("\u062A\u0627\u0631\u064A\u062E \u0627\u0644\u0625\u062C\u0631\u0627\u0621 \u064A\u062C\u0628 \u0623\u0646 \u064A\u0643\u0648\u0646 \u0633\u0627\u0628\u0642 \u0644\u062A\u0627\u0631\u064A\u062E \u0627\u0644\u062C\u0644\u0633\u0629",
                                        FacesMessage.SEVERITY_ERROR);
                    return false;
                }
            }
        }
        return isOk;
    }

 private boolean validateHearingDatePPO() {
        boolean isOk = true;

        java.util.Date procedureDate = null;
        java.util.Date reservationDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            reservationDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "AppealDates") + "");
            procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
            System.out.println("reservationDate is " + reservationDate);
            System.out.println("procedureDate is " + procedureDate);
            boolean procedureAfterreservation = ADFUtil.dateAfterDate(reservationDate, procedureDate);
            if (procedureAfterreservation) {
                System.out.println("QQQQQQQeeeeeeeQQQQ");
                setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                ADFUtil.showMessage("\u062A\u0627\u0631\u064A\u062E \u0627\u0644\u0625\u062C\u0631\u0627\u0621 \u064A\u062C\u0628 \u0623\u0646 \u064A\u0643\u0648\u0646 \u0633\u0627\u0628\u0642 \u0644\u062A\u0627\u0631\u064A\u062E \u062C\u0644\u0633\u0629 \u0627\u0644\u062D\u0643\u0645",
                                    FacesMessage.SEVERITY_ERROR);
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isOk;
    }

    @SuppressWarnings("unchecked")
    public String saveActionPopup() {
        RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc31");

        ADFUtil.setEL("#{bindings.CapacityTypeId.inputValue}", sc.getValue());

        RichInputText da = (RichInputText) JSFUtils.findComponentInRoot("it52");

        ADFUtil.setEL("#{row.IdNumber}", da.getValue());

        System.out.println(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") + "--sc.getValue()--" +
                           sc.getValue());
        ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));

        BigDecimal nnn = (BigDecimal) ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}");
        if (nnn.compareTo(new BigDecimal(6)) != 0)
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
        else
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabGozayaa.inputValue}"));
        int userId = 0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }
        ADFUtil.setEL("#{pageFlowScope.procedureId}", null);
        int loggedInEntityId = 0;
        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        Row actionsRow = actionsView.getCurrentRow();
        int actionTypeId = Integer.parseInt(actionsRow.getAttribute("ActionTypeId").toString());

        if (actionTypeId == 33) {
            setJudgmentText();
        }


        if (invals(actionTypeId, 34,   37, 35)) { // , 35
            setAttributeValue(ACTION_ITERATOR, "CapacityTypeId", "1");
        }

        int validate = 0;
        if (validateActionsRow() == 0)
            return null;
        else
            validate = 1;
        boolean validateDate = validateHearingDate();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");//validateEconmics
       boolean validateEmp =  validateEmps();
        if (!validateEmp)
            return null;
        
        boolean validateEconmic =  validateEconmics();
         if (!validateEconmic)
             return null;
         
        if (!validateDate)
            return null;

        int sequenceNumber = 0, resPartyId = 0, folderId = 0, attendance = 0, sequenceYear = 0, folderTypeId =
            0, degree = 0, userRole = 0;

        long partyId = 0, businessCaseId = 0;

        int capacityTypeId =
            actionsRow.getAttribute("CapacityTypeId") != null ?
            Integer.parseInt(actionsRow.getAttribute("CapacityTypeId").toString()) : 1;
        long hearingDateId =
            Long.parseLong(actionsRow.getAttribute("HearingDateId") == null ? "0" :
                           actionsRow.getAttribute("HearingDateId").toString());

        int accusedRequired =
            Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ExecutionFlag") == null ? "1" :
                             getAttributeValue(ACTION_ITERATOR, "ExecutionFlag").toString());

        int entityId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "OriginEntity").toString());
        int prosecutionPartial = Integer.parseInt(JSFUtils.getFromPageFlowScope("entity").toString());
        int degreeId = degree(actionTypeId);

        String actionDate =
            actionsRow.getAttribute("ActionDate") != null ? actionsRow.getAttribute("ActionDate").toString() : "";
        String procedureText = "", documentId = null, idNumber = "", paymentAmount = "0", partyName = "", repeatedFlag =
            "";
        String numberPaid =
            actionsRow.getAttribute("NumberOfPaied") == null ? "" : actionsRow.getAttribute("NumberOfPaied").toString();

        BigDecimal feesTypeId = null;
        if (actionsRow.getAttribute("FeesType") != null)
            feesTypeId = new BigDecimal(actionsRow.getAttribute("FeesType").toString());

        if (actionsRow.getAttribute("ProcedureText") != null) {
            procedureText = actionsRow.getAttribute("ProcedureText").toString();
        }
        if (ADFUtil.evaluateEL("#{pageFlowScope.documentId}") != null) {
            documentId = ADFUtil.evaluateEL("#{pageFlowScope.documentId}").toString();
        }

        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;

        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

        int rulOrProc = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur").toString());
        BigDecimal idType = new BigDecimal(searchInventoryCaseResultRow.getAttribute("IdType").toString());
        resPartyId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("ResPartyId").toString());
        folderId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderId").toString());
        partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());
        businessCaseId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("BusinessCaseId").toString());
        partyName = searchInventoryCaseResultRow.getAttribute("PartyName").toString();


        if (searchInventoryCaseResultRow.getAttribute("DegreeId") != null)
            degree = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("DegreeId").toString());
        attendance =
            Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Attendance") == null ? "0" :
                             searchInventoryCaseResultRow.getAttribute("Attendance").toString());
        sequenceNumber = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("SequenceNumber").toString());
        repeatedFlag = searchInventoryCaseResultRow.getAttribute("RepeatedFlag").toString();
        sequenceYear = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Year").toString());
        idNumber =
            searchInventoryCaseResultRow.getAttribute("IdNumber") != null ?
            searchInventoryCaseResultRow.getAttribute("IdNumber").toString() : "";
      String  PPhoneNumber =
            searchInventoryCaseResultRow.getAttribute("PhoneNumber") != null ?
            searchInventoryCaseResultRow.getAttribute("PhoneNumber").toString() : "";
        folderTypeId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderType").toString());


        Date dateFrom = (Date) actionsRow.getAttribute("DateFrom");
        Date dateTo = (Date) actionsRow.getAttribute("DateTo");
        double payment = 0;
        if (actionTypeId == 7 || actionTypeId == 77) {
            paymentAmount =
                actionsRow.getAttribute("PaymentAmount") == null ? "0" :
                actionsRow.getAttribute("PaymentAmount").toString();
            payment = Double.parseDouble(paymentAmount);
        }
        int lastAction = lastProcedureAction(folderId, actionTypeId);
        int countPrevAction = countPrevActions(resPartyId, rulOrProc);
        System.out.println(countPrevAction + "--" + lastAction + "*//*/*/*//*/*//*------" + folderId + "--" +
                           actionTypeId + "--" + resPartyId + "--" + rulOrProc + "----ddd--" + degree);
        if (!invals(actionTypeId, 22, 5, 7, 77, 10, 12, 13, 15, 16, 24, 68, 69, 70, 71, 72, 19, 11, 30, 28, 20, 34, 36,
                    37, 43, 44)) {
            if (countPrevAction > 0) {
                if ((!(capacityTypeId == 3 && actionTypeId == 1)) && lastAction != 22) {
                    System.out.println("TTTTTTTTTTTTTTTTT111111");
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0625\u062a\u062e\u0627\u0630 \u0623\u0643\u062b\u0631 \u0645\u0646 " +
                                        "\u0625\u062c\u0631\u0627\u0621 \u0639\u0644\u0649 \u0646\u0641\u0633 \u0627\u0644\u062d\u0635\u0631",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
                }
            }
        }

        boolean functionId =
            Boolean.parseBoolean(ADFUtil.evaluateEL("#{sessionScope.userInfo.userPrevMap[2962]}").toString());
        if (functionId == true && entityId != loggedInEntityId) {
            userRole = 1;
        }
        //    int validate = validateActionsRow(actionsRow, actionsView);
        if (validate == 1) {
            int resIdFlag = 0;
            int countHearingId = checkHearingDate(businessCaseId, hearingDateId);
            if (countHearingId != 0) {
                // added by belal to return res id in appmodule impl refer to ****
                resIdFlag = countHearingId;
            }
            // Start Enhanced from here
            ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,24)");
                view2.setOrderByClause("id desc");
                view2.executeQuery();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    resPartyId = Integer.parseInt(view2.first().getAttribute("NewResPartyId").toString());
                }
            }
            view2.setWhereClause(null);
            view2.executeQuery();
            ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();
            //      System.err.println(">>>>>> ResPartyId
            OperationBinding bindingId = ADFUtil.findOperation("getReservationId");
            bindingId.getParamsMap().put("businessCaseId", businessCaseId);
            bindingId.getParamsMap().put("hearingDateId", hearingDateId);
            bindingId.getParamsMap().put("degree", degreeId);
            Object obj = bindingId.execute();
            BigDecimal resId = null;
            if (obj != null) {
                resId = new BigDecimal(obj.toString());
            }

            int prosecutionJudegementFlag = 1;
            if (invals(actionTypeId, 14 , 168) &&
                "2".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionJudegementFlag") + "")) {
                prosecutionJudegementFlag = 0;
            }
            double grantee = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "Guarantee"));
            double inpresonYear = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonYears"));
            double inpresonMonth =
                getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonMonths"));
            double inpresonday = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonDays"));
            double amount = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "PenaltyAmount"));
            double dailyPayment = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "DailyPayments"));
            double procecutionObjection = 0.0;
            /*
         * in case the hasr is eatrad on amr geanay
         * */
            int judgmentType =
                getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") != null ?
                Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") + "") : -1;
            if (judgmentType == 3 && ( actionTypeId == 14 || actionTypeId == 168 ))
                procecutionObjection = 1;
            else if (judgmentType == 2 && (actionTypeId == 14 || actionTypeId == 168 ))
                procecutionObjection = 2;

            int caseSchema =
                ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
                Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;

            setAttributeViewValue(PARAMS_ITERATOR, "resPartyId", resId);
            setAttributeViewValue(PARAMS_ITERATOR, "capacityTypeId", capacityTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "userId", userId);
            setAttributeViewValue(PARAMS_ITERATOR, "hearingDateId", hearingDateId);
            setAttributeViewValue(PARAMS_ITERATOR, "actionTypeId", actionTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "partyId", partyId);
            setAttributeViewValue(PARAMS_ITERATOR, "actionDate", actionDate);
            setAttributeViewValue(PARAMS_ITERATOR, "procedureText", procedureText);
            setAttributeViewValue(PARAMS_ITERATOR, "folderId", folderId);
            setAttributeViewValue(PARAMS_ITERATOR, "accusedRequired", accusedRequired);
            setAttributeViewValue(PARAMS_ITERATOR, "dateFrom", dateFrom);
            setAttributeViewValue(PARAMS_ITERATOR, "dateTo", dateTo);
            setAttributeViewValue(PARAMS_ITERATOR, "idNumber", idNumber);
            setAttributeViewValue(PARAMS_ITERATOR, "PartyPhoneNumber", PPhoneNumber);
            setAttributeViewValue(PARAMS_ITERATOR, "PreviousVetoDate",
                                  getAttributeValue(ACTION_ITERATOR, "PreviousVeto"));
           // setAttributeViewValue(PARAMS_ITERATOR, "PartyFName", null);
            setAttributeViewValue(PARAMS_ITERATOR, "paymentAmount", payment);
            setAttributeViewValue(PARAMS_ITERATOR, "entityId", prosecutionPartial);
            System.out.println(folderId + "folderId  " + resId + "degreeIddegree-------" + degree +
                               "IddegreeIddegreeIddegreeId" + degreeId);
            setAttributeViewValue(PARAMS_ITERATOR, "degree", degreeId);
            setAttributeViewValue(PARAMS_ITERATOR, "resIdFlag", resIdFlag);
            setAttributeViewValue(PARAMS_ITERATOR, "businessCaseId", businessCaseId);
            setAttributeViewValue(PARAMS_ITERATOR, "prevResPartyId", resPartyId);
            setAttributeViewValue(PARAMS_ITERATOR, "numberPaid", numberPaid);
            setAttributeViewValue(PARAMS_ITERATOR, "idType", idType);
            setAttributeViewValue(PARAMS_ITERATOR, "feesTypeId", feesTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "prosecutionJudegementFlag", prosecutionJudegementFlag);
            setAttributeViewValue(PARAMS_ITERATOR, "grantee", grantee);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonYear", inpresonYear);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonMonth", inpresonMonth);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonday", inpresonday);
            setAttributeViewValue(PARAMS_ITERATOR, "amount", amount);
            setAttributeViewValue(PARAMS_ITERATOR, "dailyPayment", dailyPayment);
            setAttributeViewValue(PARAMS_ITERATOR, "procecutionObjection", procecutionObjection);
            setAttributeViewValue(PARAMS_ITERATOR, "caseSchema", caseSchema);
            setAttributeViewValue(PARAMS_ITERATOR, "partyName", partyName);
            setAttributeViewValue(PARAMS_ITERATOR, "Adress", getAttributeValue(ACTION_ITERATOR, "Adress"));
            setAttributeViewValue(PARAMS_ITERATOR, "City", getAttributeValue(ACTION_ITERATOR, "City"));
            setAttributeViewValue(PARAMS_ITERATOR, "EntityPolice", getAttributeValue(ACTION_ITERATOR, "EntityPolice"));
            setAttributeViewValue(PARAMS_ITERATOR, "EntityPolice", getAttributeValue(ACTION_ITERATOR, "EntityPolice"));
            setAttributeViewValue(PARAMS_ITERATOR, "VetoStatus", getAttributeValue(ACTION_ITERATOR, "VetoStatus"));
            Integer procId = null;
            int appealPlace = Integer.parseInt(getAttributeValue("AppealPlaceStaticLKPIterator", "Id") + "");
            System.out.println("appealPlace  is -------- " + appealPlace);
            int degSequenntial = degreeId;
            if (degreeId == 5)
                degSequenntial = 2;
            if (degreeId == 6)
                degSequenntial = 4;

            if (degreeId == 3 && (actionTypeId == 14 || actionTypeId == 168) )
                degSequenntial = 6;
            if (degreeId == 1 && (actionTypeId == 14 || actionTypeId == 168 ))
                degSequenntial = 7;

            if (capacityTypeId == 3 && attendance == 1 && actionTypeId == 152)
                degSequenntial = 8;
            if (capacityTypeId == 3 && (attendance == 3) && actionTypeId == 152)
                degSequenntial = 9;
            if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 1)
                degSequenntial = 10;
            if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 2)
                degSequenntial = 11;
            if (capacityTypeId == 8  && actionTypeId == 152)
                degSequenntial = 12;
            if (capacityTypeId == 9  && actionTypeId == 152)
                degSequenntial = 13;


            Boolean objResult = true;
            RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it44");
            RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it45");
            setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", null);
            RichInputText nid = (RichInputText) JSFUtils.findComponentInRoot("it55");
            RichInputText registerationId = (RichInputText) JSFUtils.findComponentInRoot("it56");
            RichInputText name = (RichInputText) JSFUtils.findComponentInRoot("it54");
            RichInputText comments = (RichInputText) JSFUtils.findComponentInRoot("it58");
            RichInputText retainerNumber = (RichInputText) JSFUtils.findComponentInRoot("it57");
            RichInputText lawyerPhoneNumber = (RichInputText) JSFUtils.findComponentInRoot("it77");
            Object objNID = nid.getValue();
            Object objName = name.getValue();
            Object objRetainerNumber = retainerNumber.getValue();
        //                if(objNID != null || objName != null  || objRetainerNumber !=null)
        //                {
        //                    System.out.println(objNID+"SDSSSSSSSSSSSSS"+objName+"--------"+objRetainerNumber);
        //                        showErrorMessageForComponentById("\u0627\u0644\u062a\u0623\u0643\u062f \u0645\u0646 \u0625\u062f\u062e\u0627\u0644 \u0628\u064a\u0627\u0646\u0627\u062a \u0627\u0644\u062a\u0648\u0643\u064a\u0644",
        //                                                         "it37");
        //                         return null;
            //

      Boolean manual = (Boolean) JSFUtils.resolveExpression("#{bindings.SequentialManual.inputValue}");
            System.out.println("MMMMM****** " + manual);
            if (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 8 || actionTypeId == 14 || actionTypeId == 168  || 
                actionTypeId == 23 || actionTypeId == 9 || actionTypeId == 152) {

            if ((inputNum.getValue() == null || inputYear.getValue() == null) && manual) {
               showErrorMessageForComponentById("\u0628\u0631\u062c\u0627\u0621 \u0625\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639",
                                                     "it35");

                return null;
                }
                if (inputNum.getValue() != null && inputYear.getValue() != null &&
                    (manual || JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}"))) {
                    int numSequential = 0;
                    int yearSequential = 0;
                int curryear = Calendar.getInstance().get(Calendar.YEAR);
                    try {

                        numSequential = Integer.parseInt(inputNum.getValue() + "");
                        yearSequential = Integer.parseInt(inputYear.getValue() + "");

                    } catch (Exception e) {
                        showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                         "it35");
                        return null;
                    }

                    if (numSequential > 0 && yearSequential > 2000 && numSequential < 9999999 &&
                        yearSequential < 2023) {

            OperationBinding binding = ADFUtil.findOperation("validatSequentionalNumber");

            binding.getParamsMap().put("folderId",  folderId);
                        binding.getParamsMap().put("degree", degSequenntial);
                        binding.getParamsMap().put("number", numSequential);
                        binding.getParamsMap().put("year", yearSequential);
                        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"))
                            binding.getParamsMap().put("flagAuto", 1);
                    else
                            binding.getParamsMap().put("flagAuto", 0);
                 objResult = (Boolean) binding.execute();
                    } else {
                   showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                    "it44");
                    return null;

                }
                }
            }
            if (!objResult) {

                showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u0645\u0643\u0631\u0631",
                                                 "it44");
            } else {

                if (capacityTypeId == 2 && nid.getValue() != null && name.getValue() != null &&
                    retainerNumber.getValue() != null) {
                    BigDecimal parId = new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceId").toString());

                    String comm = null;
                    if (comments.getValue() != null)
                        comm = comments.getValue().toString();

                    String reg = "0";
                    if (registerationId.getValue() != null)
                        reg = registerationId.getValue().toString();

                    System.err.println(registerationId.getValue() + "comm   is  " + comm);

                    OperationBinding operation = getBindings().getOperationBinding("insertLawyer");
                    operation.getParamsMap().put("parId", parId);
                    operation.getParamsMap().put("nid", nid.getValue().toString());
                    operation.getParamsMap().put("registerationId", new BigDecimal(reg.toString()));
                    operation.getParamsMap().put("name", name.getValue().toString());
                    operation.getParamsMap().put("comments", comm);
                    operation.getParamsMap().put("retainerNumber", retainerNumber.getValue().toString());
                    operation.getParamsMap().put("phoneNumber", lawyerPhoneNumber.getValue().toString());
                    operation.getParamsMap().put("createdBy",
                                                 ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
                    BigDecimal LawyerId = (BigDecimal) operation.execute();
                    //                    BigDecimal LawyerId=     am.insertLawyer(parId, nid.getValue().toString(),new BigDecimal( reg.toString()),
                    //                                        name.getValue().toString(),comm , retainerNumber.getValue().toString(), ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
                    setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", LawyerId);
                    System.err.println("---------  LawyerId   i s" + LawyerId);
                }

                ArrayList<Integer> save =
                    (ArrayList<Integer>) getBindings().getOperationBinding("saveInventory").execute();

                //                ArrayList<Integer> save = am.saveInventory();
                JSFUtils.hidePopup("p2");
                inputNum.setValue("");
                inputYear.setValue("");
                nid.setValue("");
                registerationId.setValue("");
                name.setValue("");
                comments.setValue("");
                retainerNumber.setValue("");

                if (save.size() > 0) {
                    procId = save.get(0);
                }


                ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
                ADFUtil.setEL("#{pageFlowScope.entityId}", entityId);
                ADFUtil.setEL("#{pageFlowScope.partyId}", partyId);
                ADFUtil.setEL("#{pageFlowScope.resPartyId}", save.size() > 0 ? save.get(2) : null);
                ADFUtil.setEL("#{pageFlowScope.procedureId}", procId);
                ADFUtil.setEL("#{pageFlowScope.actionSaved}", 1);


                uploadIdTawkelSadadTasdekPicFiles();

                if (procId != null) {
                    OperationBinding oper2 = getBindings().getOperationBinding("getProsecutionUnderKolyea");
                    oper2.getParamsMap().put("kolyeaId", loggedInEntityId);
                    Object result = oper2.execute();

                    if (result == null)
                        result = new ArrayList();

                    ArrayList<Integer> prosecutionUnderKolyea = (ArrayList<Integer>) result;

                    boolean underKolyea = false;
                    for (int i = 0; prosecutionUnderKolyea.size() > i; i++) {
                        if (prosecutionUnderKolyea.get(i) == prosecutionPartial) {
                            underKolyea = true;
                            ADFUtil.setEL("#{pageFlowScope.underKolyea}", 1);
                        }
                    }
                    //                if (loggedInEntityId != entityId && !underKolyea){
                    if (actionTypeId == 25 || actionTypeId == 26 || actionTypeId == 27) {
                        BigDecimal logId =
                            (BigDecimal) insertIntoExcProceduresLogView(actionTypeId, businessCaseId, userId,
                                                                        prosecutionPartial, 0, loggedInEntityId,
                                                                        new BigDecimal(procId), sequenceNumber, 1,
                                                                        folderTypeId, repeatedFlag, sequenceYear,
                                                                        actionDate);
                        ADFUtil.setEL("#{pageFlowScope.logId}", save.size() > 0 ? save.get(0) : null);
                        ADFUtil.setEL("#{pageFlowScope.actionId}", save.size() > 0 ? save.get(1) : null);
                        ADFUtil.setEL("#{pageFlowScope.procedureLogId}", logId);
                    }
                    ADFUtil.setEL("#{pageFlowScope.folderId}", folderId);
                }

                ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().executeQuery();
                ADFUtil.findIterator("PartyFilesRVO1Iterator").getViewObject().executeQuery();

                if (actionTypeId == 7 || actionTypeId == 77)
                    ADFUtil.findIterator("PreviousProceduresRVO5Iterator").getViewObject().executeQuery();

                if (!invals(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur"), 1))
                    filterFilesTypes(actionTypeId);

                ADFUtil.findIterator(REUPLOAD_FILE_TYPES_ITERATOR).getViewObject().executeQuery();

                //            printAndUploadReportAfterSave(actionTypeId);
   BigDecimal type = (BigDecimal) ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}");
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}") && !manual &&
                    (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 3 || actionTypeId == 8 ||
                     actionTypeId == 9 || actionTypeId == 23 || actionTypeId == 14  || actionTypeId == 168  || actionTypeId == 152)) {
                OperationBinding binding = ADFUtil.findOperation("sequentialAutomation");

                binding.getParamsMap().put("folderId",  folderId);
                    if (type.compareTo(new BigDecimal(3)) == 0) {
                        binding.getParamsMap().put("degree", 33);
                    } else
                        binding.getParamsMap().put("degree", degSequenntial);
                binding.execute();

            }
            saveDataWithMessage();
            JSFUtils.hidePopup("p2");
            if (actionTypeId == 2)
                JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0647 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629");

            if (actionTypeId == 1)
                JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641");

            //  showErrorMessageForComponentById("", "if18");
        }


            if (actionTypeId == 36 || actionTypeId == 37) {
            System.out.println("SearchInventoryCaseResultRVO1Iterator    ---------");
             ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").executeQuery();
        }

        }
        return null;

    }

    @SuppressWarnings("unchecked")
    public String saveAction2() {
        System.out.println("reeeeeeeeeeeeeeeeeeeeeeeee");
        boolean validateEmp =  validateEmps();
         if (!validateEmp)
             return null;
        boolean validateEconmic =  validateEconmics();
         if (!validateEconmic)
             return null;
        saveAction();

        ADFUtil.findOperation("Rollback").execute();
        ADFUtil.setEL("#{pageFlowScope.procedureId}", null);
        ADFUtil.setEL("#{pageFlowScope.actionSaved}", null);
        ADFUtil.findIterator(FILE_TYPES_ITERATOR).getViewObject().executeEmptyRowSet();
        ViewObject view3 = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        view3.setWhereClause(null);
        view3.executeQuery();
        ADFUtil.setEL("#{pageFlowScope.idRport}", null);
        ADFUtil.setEL("#{pageFlowScope.printOppositionReport}", null);
        ADFUtil.setEL("#{pageFlowScope.AppealAppeal}", null);
        ADFUtil.setEL("#{pageFlowScope.printReport26}", null);
        ADFUtil.setEL("#{pageFlowScope.Appeal}", null);
        ADFUtil.setEL("#{sessionScope.actionTypeId}", null);
        ADFUtil.setEL("#{pageFlowScope.DName}", null);
        ADFUtil.setEL("#{pageFlowScope.DID}", null);
        ADFContext.getCurrent().getSessionScope().put("DOCUMENTID", null);
        rulesOrProcedures = 0;
        personalPic = null;
        payment = null;
        procurationPic = null;
        tasdekFile = null;
        cancelFile = null;
        editFile = null;
        execReport9 = null;
        ReinstatelProcedureTemplate =null;
        ReinstatelProcedureTemplateSecond =null;
        setPenaltyAmountValue();
        stPunchmentsValues();
        ADFUtil.setAttributeValue(ACTION_ITERATOR, "ExecutionFlag",
                                  getAttributeValue(RESULT_SEARCH_ITERATOR, "ExecutionFlag"));
        return null;

    }

    @SuppressWarnings("unchecked")
    public String saveAction() {
                                    
        System.err.println("Start public String saveAction()OOOOOOOOOOOOOOOOOOO");
        boolean validateEmp =  validateEmps();
         if (!validateEmp)
             return null;
        boolean validateEconmic =  validateEconmics();
         if (!validateEconmic)
             return null;
        //Bayoumi28_12
        //        boolean  checkIsProdceduerUnique= checkIsProdceduerUnique((BigDecimal) getActionTypeIdBind().getValue());
        //                if( checkIsProdceduerUnique==false) {
        //                    System.err.println("checkIsProdceduerUnique"+checkIsProdceduerUnique);
        //                    JSFUtils.addFacesErrorMessage("\u0647\u0630\u0627 \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u062a\u0645 \u0625\u0636\u0627\u0641\u062a\u0629 \u0645\u0646 \u0642\u0628\u0644");
        //                    return null;
        //                }


        RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc27");

        ADFUtil.setEL("#{bindings.CapacityTypeId.inputValue}", sc.getValue());

        RichInputText da = (RichInputText) JSFUtils.findComponentInRoot("it7");

        ADFUtil.setEL("#{row.IdNumber}", da.getValue());
        System.out.println(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") + "--sc.getValue()--" +
                           sc.getValue());
        ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));

        BigDecimal nnn = (BigDecimal) ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}");
        if (nnn.compareTo(new BigDecimal(6)) != 0)
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
        else
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabGozayaa.inputValue}"));
        int userId = 0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }
        ADFUtil.setEL("#{pageFlowScope.procedureId}", null);
        int loggedInEntityId = 0;
        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        Row actionsRow = actionsView.getCurrentRow();

        Date deliveryDate = (Date) getDeliveryDateBind().getValue();
        Date startDate = (Date) getStartDateBind().getValue();
        Date endDate = (Date) getEndDateBind().getValue();
        String actionNotes = (String) getActionNotesBind().getValue();
        String guardianName = (String) getGuardianNameBind().getValue();
        String guardianNationalId = (String) getGuardianNationalIdBind().getValue();
        String kinship = (String) getKinshipBind().getValue();
        String phoneNum = (String) getPhoneNumBind().getValue();
        System.err.println("Bayoumi1 >>>deliveryDate********** =" + deliveryDate + "\n startDate=" + startDate +
                           "\n endDate=" + endDate + "\n actionNotes=" + actionNotes);
        int actionTypeId = Integer.parseInt(actionsRow.getAttribute("ActionTypeId").toString());
        BigDecimal feesTypeId = null;
        if (actionsRow.getAttribute("FeesType") != null)
            feesTypeId = new BigDecimal(actionsRow.getAttribute("FeesType").toString());

        if (actionTypeId == 33) {
            setJudgmentText();
        }


        if (invals(actionTypeId, 34,   37, 35)) { // , 35
            setAttributeValue(ACTION_ITERATOR, "CapacityTypeId", "1");
        }

        int validate = 0;
        System.out.println("validateActionsRow()" + validateActionsRow());
        if (validateActionsRow() == 0)
            return null;
        else
            validate = 1;
        boolean validateDateTaqseer = true;
        boolean validateDateGharama = true;

        if (actionTypeId == 45)
            validateHearingDateTaqseerFatra();

        boolean validateDate = validateHearingDate();
        boolean validateDateKafala = validateDateEsterdadKafala();
        boolean validateDateMoaqat = validateDateTanfeezMoaqat();
        boolean validateTaqseerGharama = validateDateTaqseerGharama();
        System.out.println("validateDate" + validateDate);
        System.out.println("validateDateKafala" + validateDateKafala);
        System.out.println("validateDateMoaqat" + validateDateMoaqat);
        System.out.println("validateTaqseerGharama" + validateTaqseerGharama);
        if (!validateDateTaqseer)
            return null;

        if (!validateDate)
            return null;

        if (!validateDateKafala)
            return null;

        if (!validateDateMoaqat)
            return null;

        if (!validateTaqseerGharama)
            return null;
        
      
        RichInputText inputPhonee = (RichInputText) JSFUtils.findComponentInRoot("it78");
        if (actionTypeId != 24  && actionTypeId != 58  && actionTypeId != 60 && actionTypeId != 97 ){
        if ( inputPhonee.getValue() != null){
        if (  inputPhonee.getValue().toString().length() != 11) {
           
            ADFUtil.showMessage("Phone Number Required",
                                FacesMessage.SEVERITY_ERROR);
            return null; 
        
        }
        }
        else {
                ADFUtil.showMessage("Phone Number Required",
                                    FacesMessage.SEVERITY_ERROR);
                return null; 
                
            }
        }

        if ((actionsRow.getAttribute("HearingDateId") == null && actionsRow.getAttribute("PreviousVeto") == null) &&
            actionTypeId == 45) {
            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                FacesMessage.SEVERITY_ERROR);
            return null;
        }
        int sequenceNumber = 0, resPartyId = 0, folderId = 0, attendance = 0, sequenceYear = 0, folderTypeId =
            0, degree = 0, userRole = 0;

        long partyId = 0, businessCaseId = 0;

        int capacityTypeId =
            actionsRow.getAttribute("CapacityTypeId") != null ?
            Integer.parseInt(actionsRow.getAttribute("CapacityTypeId").toString()) : 1;
        long hearingDateId =
            Long.parseLong(actionsRow.getAttribute("HearingDateId") == null ? "0" :
                           actionsRow.getAttribute("HearingDateId").toString());

        int accusedRequired =
            Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ExecutionFlag") == null ? "1" :
                             getAttributeValue(ACTION_ITERATOR, "ExecutionFlag").toString());

        int entityId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "OriginEntity").toString());


        String niidType = getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") + "";
       String nidStatus = ADFUtil.evaluateEL("#{sessionScope.nidStatus}").toString();
        String skipId = getAttributeValue(RESULT_SEARCH_ITERATOR, "SkipId") + ""; // N
        if (skipId.equalsIgnoreCase("N"))
            if ((niidType.equalsIgnoreCase("2") || niidType.equalsIgnoreCase("3")) && nidStatus.equalsIgnoreCase("Y")) {
                int idYear =
                    Integer.parseInt((getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber") + "").substring(1, 3));
                System.out.println("idYear   is   ------------------  " + idYear);
                if (!(idYear > 9 && idYear < 29)) {
               String nidFlag =   "N";
                    if (getAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag") != null)
                        nidFlag = getAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag") + ""; // N
                    String updateType = getAttributeValue(RESULT_SEARCH_ITERATOR, "UpdateType") + ""; // N
                    String unknownId = getAttributeValue(RESULT_SEARCH_ITERATOR, "UnknownId") + ""; // N
                    System.out.println(nidFlag + " nidFlag " + updateType + " updateType " + unknownId + " unknownId");
                    if (updateType.equalsIgnoreCase("N") && unknownId.equalsIgnoreCase("N") &&
                        nidFlag.equalsIgnoreCase("N")) {
                      ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0627\u0644\u062a\u062d\u0642\u0642 \u0645\u0646 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0642\u0648\u0645\u0649",
                                          FacesMessage.SEVERITY_ERROR);
                        return null;
                  }
              }
        }

        System.out.println(getAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber") +
                           "  idYear  is -------------------------------------------------- ");
        int prosecutionPartial = Integer.parseInt(JSFUtils.getFromPageFlowScope("entity").toString());
        int degreeId = degree(actionTypeId);
        //        int reservationPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());

        String actionDate =
            actionsRow.getAttribute("ActionDate") != null ? actionsRow.getAttribute("ActionDate").toString() : "";
        String procedureText = "", documentId = null, idNumber = "", penaltyAmount = "0", work = "0", paymentAmount =
            "0", partyName = "", repeatedFlag = "";
        String numberPaid =
            actionsRow.getAttribute("NumberOfPaied") == null ? "" : actionsRow.getAttribute("NumberOfPaied").toString();


        if (actionsRow.getAttribute("ProcedureText") != null) {
            procedureText = actionsRow.getAttribute("ProcedureText").toString();
        }
        if (ADFUtil.evaluateEL("#{pageFlowScope.documentId}") != null) {
            documentId = ADFUtil.evaluateEL("#{pageFlowScope.documentId}").toString();
        }

        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;

        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

        int rulOrProc = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur").toString());
        BigDecimal idType = new BigDecimal(searchInventoryCaseResultRow.getAttribute("IdType").toString());
        BigDecimal claimFolderId = new BigDecimal(0);
        if (getAttributeValue(ACTION_ITERATOR, "ClaimFolderId") != null)
            claimFolderId = new BigDecimal(getAttributeValue(ACTION_ITERATOR, "ClaimFolderId").toString());
        resPartyId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("ResPartyId").toString());
        folderId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderId").toString());
        partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());
        businessCaseId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("BusinessCaseId").toString());
        partyName = searchInventoryCaseResultRow.getAttribute("PartyName").toString();


        if (searchInventoryCaseResultRow.getAttribute("DegreeId") != null)
            degree = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("DegreeId").toString());
        attendance =
            Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Attendance") == null ? "0" :
                             searchInventoryCaseResultRow.getAttribute("Attendance").toString());
        sequenceNumber = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("SequenceNumber").toString());
        repeatedFlag = searchInventoryCaseResultRow.getAttribute("RepeatedFlag").toString();
        sequenceYear = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Year").toString());
        idNumber =
            searchInventoryCaseResultRow.getAttribute("IdNumber") != null ?
            searchInventoryCaseResultRow.getAttribute("IdNumber").toString() : "";
        String  PPhoneNumber =
              searchInventoryCaseResultRow.getAttribute("PhoneNumber") != null ?
              searchInventoryCaseResultRow.getAttribute("PhoneNumber").toString() : "";
        folderTypeId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderType").toString());

        Date dateFrom = (Date) actionsRow.getAttribute("DateFrom");
        Date dateTo = (Date) actionsRow.getAttribute("DateTo");
        double payment = 0;
        if (actionTypeId == 7 || actionTypeId == 77) {
            paymentAmount =
                actionsRow.getAttribute("PaymentAmount") == null ? "0" :
                actionsRow.getAttribute("PaymentAmount").toString();
            payment = Double.parseDouble(paymentAmount);
            if (feesTypeId.equals(new BigDecimal(4))) {
                if (actionsRow.getAttribute("TotalAmount") != null &&
                    Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") == 0) {
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0648\u062c\u062f \u0645\u0628\u0644\u063a \u0643\u0641\u0627\u0644\u0629",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
        }
            }
        }

        int lastAction = lastProcedureAction(folderId, actionTypeId);
        int countPrevAction = countPrevActions(resPartyId, rulOrProc);
        System.out.println(countPrevAction + "--" + lastAction + "*//*/*/*//*/*//*------" + folderId + "--" +
                           actionTypeId + "--" + resPartyId + "--" + rulOrProc + "----ddd--" + degree);
        if (!invals(actionTypeId, 22, 5, 7, 77, 10, 12, 13, 15, 16, 24, 68, 69, 70, 71, 72, 19, 11, 30, 28,  34, 36,
                    37, 43, 44, 45, 46, 47, 48,162)) {
            if (countPrevAction > 0) {
                if ((!(capacityTypeId == 3 && actionTypeId == 1)) && lastAction != 22) {
                    if (actionTypeId != 147 && actionTypeId != 148 && actionTypeId != 149 && actionTypeId != 156 &&
                        actionTypeId != 150 && actionTypeId != 151 && actionTypeId != 103 && (lastAction != 21 && actionTypeId != 153)) { //Add && actionTypeId != 103 by XXX As per cleint //Add && actionTypeId && (lastAction != 21 && actionTypeId != 153) by XXX As per cleint
                    System.out.println("TTTTTTTTTTTTTTTTT222222");
                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0625\u062a\u062e\u0627\u0630 \u0623\u0643\u062b\u0631 \u0645\u0646 " +
                                        "\u0625\u062c\u0631\u0627\u0621 \u0639\u0644\u0649 \u0646\u0641\u0633 \u0627\u0644\u062d\u0635\u0631",
                                        FacesMessage.SEVERITY_ERROR);
                    return null;
                    }
                }
            }
        }

        boolean functionId =
            Boolean.parseBoolean(ADFUtil.evaluateEL("#{sessionScope.userInfo.userPrevMap[2962]}").toString());
        if (functionId == true && entityId != loggedInEntityId) {
            userRole = 1;
        }
        //    int validate = validateActionsRow(actionsRow, actionsView);
        if (validate == 1) {
            int resIdFlag = 0;
            int countHearingId = checkHearingDate(businessCaseId, hearingDateId);
            if (countHearingId != 0) {
                // added by belal to return res id in appmodule impl refer to ****
                resIdFlag = countHearingId;
            }

            if (actionTypeId == 46) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                //                System.err.println("--- "+view.getQuery());
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (46)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0637\u0644\u0628 \u062a\u062e\u0641\u064a\u0636 \u0643\u0641\u0627\u0644\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                    view.setWhereClause("FEES_TYPE_ID = 4");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0639\u0645\u0644 \u0627\u062c\u0631\u0627\u0621 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0647 \u0628\u0639\u062f \u0633\u062f\u0627\u062f \u0627\u0644\u0643\u0641\u0627\u0644\u0647",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }

            if (actionTypeId == 52) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                //                System.err.println("--- "+view.getQuery());
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (52)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0637\u0644\u0628 \u0625\u0633\u062a\u0631\u062f\u0627\u062f \u0643\u0641\u0627\u0644\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }

            if (actionTypeId == 49) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                //                System.err.println("--- "+view.getQuery());
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (49)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0625\u0642\u0627\u0644\u0629 \u0645\u0646 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }

            if (actionTypeId == 62) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (62)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0623\u0645\u0631 \u062a\u0634\u063a\u064a\u0644",
                                                        FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }

            if (actionTypeId == 64) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (64)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0637\u0644\u0628 \u0648\u0642\u0641 \u062a\u0646\u0641\u064a\u0630 \u0645\u0624\u0642\u062a\u0627",
                                                        FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }

            if (actionTypeId == 65) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (65)");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0627\u0644\u062a\u0646\u0627\u0632\u0644 \u0639\u0646 \u0627\u0644\u062d\u0642 \u0641\u064a \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629",
                                                        FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }
            if (actionTypeId == 162) {
                System.err.println("11111111111111actionTypeId");
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() >= 0) {
                    System.err.println("222222222222222222222actionTypeId" + ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount());
                    view.setWhereClause("PROCEDURE_TYPE in (5)");
                    view.executeQuery();
                    System.err.println("view.getQuery()=" + view.getQuery());
                    System.err.println("3333333333333333333333actionTypeId" + ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount());
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() <= 0) {
                        System.err.println("444444444444444444444444444444444actionTypeId" + ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount());
                                    ADFUtil.showMessage("\u064A\u062C\u0628 \u0627\u062C\u0631\u0627\u0621 \u0627\u0644\u0637\u0639\u0646 \u0628\u0627\u0644\u0646\u0642\u0636 \u0642\u0628\u0644 \u0625\u064A\u062F\u0627\u0639 \u0628\u0627\u0644\u0633\u062C\u0646",
                                                        FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                }
            }


            int tempResPartyId = 0;

            if (null != feesTypeId) {
                if (feesTypeId.equals(new BigDecimal(4))) {
                    //                if (payment == 0 || payment == 0.0)
                    //                    ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0643\u0628\u0631 \u0645\u0646 \u0635\u0641\u0631",
                    //                                                FacesMessage.SEVERITY_ERROR);

                    double totalVal =
                        actionsRow.getAttribute("TotalAmount") != null ?
                        Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") : Double.parseDouble("0");

                    if (totalVal != payment && (actionTypeId == 7 || actionTypeId == 77)) {
                        ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0633\u0627\u0648\u064a \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a",
                                            FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                    ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        view.setWhereClause("PROCEDURE_TYPE in (47)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                            try {
                                System.out.println("QQQQQQQQ");
                                java.util.Date procedureDate = null;
                                java.util.Date previousProcedureDate = null;
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                previousProcedureDate =
                                    sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                                procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                                boolean procedureAfterProcedure =
                                    ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                                if (!procedureAfterProcedure) {
                                    System.out.println("QQQQQaaaaaaaaQQQQQQ");
                                    setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                                    ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0625\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                        FacesMessage.SEVERITY_ERROR);
                                    view.setWhereClause(null);
                                    view.executeQuery();
                                    return null;
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        view.setWhereClause("PROCEDURE_TYPE in (46,47,48)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() == 1) {
                            ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u0644\u0633\u062f\u0627\u062f \u062d\u062a\u0649 \u064a\u062a\u0645 \u0642\u0628\u0648\u0644 \u0627\u0648 \u0631\u0641\u0636 \u0637\u0644\u0628 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                FacesMessage.SEVERITY_ERROR);
                            view.setWhereClause(null);
                            view.executeQuery();
                            actionsRow.setAttribute("FeesType", null);
                            return null;
                        }
                    }
                } else if (feesTypeId.equals(new BigDecimal(7))) {
                    tempResPartyId = resPartyId;
                    RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it44");
                    //                    System.err.println("inputNum.getValue() = "+inputNum.getValue());
                    if (inputNum.getValue() == null ||
                        (inputNum.getValue() != null && Double.parseDouble(inputNum.getValue() + "") == 0)) {
                        ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f",
                                            FacesMessage.SEVERITY_ERROR);
                        return null;
                    }

                    validateDateGharama = validateDateGharamaYawmeya();
                }
            }

            if (!validateDateGharama)
                return null;
            // Start Enhanced from here
            //            ViewObject view3 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
            //            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
            //                view3.setWhereClause("PROCEDURE_TYPE in (45)");
            //                view3.executeQuery();
            //                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
            //                    ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0639\u0645\u0644 \u0627\u062c\u0631\u0627\u0621 \u062a\u0642\u0635\u064a\u0631 \u062c\u0644\u0633\u0629 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0645\u0631\u0629",
            //                                        FacesMessage.SEVERITY_ERROR);
            //                    return null;
            //                }
            //            }
            //            view3.setWhereClause(null);
            //            view3.executeQuery();
            //            ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();

            // Start Enhanced from here
            ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
            if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,24)");
                view2.setOrderByClause("id desc");
                view2.executeQuery();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    resPartyId = Integer.parseInt(view2.first().getAttribute("NewResPartyId").toString());
                }
            }

            view2.setWhereClause(null);
            view2.executeQuery();
            ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();
            //      System.err.println(">>>>>> ResPartyId

            if (actionTypeId == 47 || actionTypeId == 48) {
                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (47,48)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0642\u0631\u0627\u0631 \u0642\u0628\u0648\u0644\u002f\u0631\u0641\u0636 \u062a\u062e\u0641\u064a\u0636 \u0643\u0641\u0627\u0644\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    }
                    view.setWhereClause(null);
                    view.executeQuery();
                }

                //                System.err.println(view.getQuery());
            }

            if (actionTypeId == 62) {
                RichInputText remaining = (RichInputText) JSFUtils.findComponentInRoot("it53");
                setAttributeViewValue(PARAMS_ITERATOR, "penaltyAmount", Double.parseDouble(remaining.getValue() + ""));
            }

            OperationBinding bindingId = ADFUtil.findOperation("getReservationId");
            bindingId.getParamsMap().put("businessCaseId", businessCaseId);
            bindingId.getParamsMap().put("hearingDateId", hearingDateId);
            bindingId.getParamsMap().put("degree", degreeId);
            Object obj = bindingId.execute();
            BigDecimal resId = null;
            if (obj != null) {
                resId = new BigDecimal(obj.toString());
            }

            int prosecutionJudegementFlag = 1;
            if (invals(actionTypeId, 14 ,168) &&
                "2".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionJudegementFlag") + "")) {
                prosecutionJudegementFlag = 0;
            }
            //            double gurantee = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "GuranteeAmount"));
            double grantee = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "Guarantee"));
            double inpresonYear = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonYears"));
            double inpresonMonth =
                getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonMonths"));
            double inpresonday = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonDays"));
            double amount = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "PenaltyAmount"));
            double dailyPayment = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "DailyPayments"));
            double procecutionObjection = 0.0;
            double fixedFine = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "FixedFine"));
            double dailyFine = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "DailyFine"));
            double guarantees = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "Guarantee"));
            double expenses = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "Expenses"));
            double lawyerFees = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "LawyerFees"));

            /*
	 * in case the hasr is eatrad on amr geanay
	 * */
            int judgmentType =
                getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") != null ?
                Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") + "") : -1;
            if (judgmentType == 3 && (actionTypeId == 14 || actionTypeId == 168 ))
                procecutionObjection = 1;
            else if (judgmentType == 2 && (actionTypeId == 14 || actionTypeId == 168 ))
                procecutionObjection = 2;

            int caseSchema =
                ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
                Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;

            System.err.println("resPartyId = " + resPartyId);
            double totalVal =
                actionsRow.getAttribute("TotalAmount") != null ?
                Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") : Double.parseDouble("0");
            //            double oldVal = getTotalPenaltyOrGuranteeAmount(resPartyId, 4);
            if (actionTypeId == 47 && totalVal >= grantee) {
                ADFUtil.showMessage("\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0641\u0627\u0644\u0629 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0623\u0642\u0644 \u0645\u0646 " +
                                    grantee, FacesMessage.SEVERITY_ERROR);
                return null;
            }

            BigDecimal feesTypeGharamaId = null;
            if (actionsRow.getAttribute("FeesTypeGharama") != null)
                feesTypeGharamaId = new BigDecimal(actionsRow.getAttribute("FeesTypeGharama").toString());


            double penalty = 0;
            if (actionTypeId == 63 && feesTypeGharamaId != null && feesTypeGharamaId.equals(new BigDecimal(3))) {
                penaltyAmount =
                    actionsRow.getAttribute("PenaltyAmount") == null ? "0" :
                    actionsRow.getAttribute("PenaltyAmount").toString();
                penalty = Double.parseDouble(penaltyAmount);
                RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it49");
                if (inp.getValue() == null) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                    FacesMessage.SEVERITY_ERROR);
                    return null;
                } else if (inp.getValue() != null && Double.parseDouble(inp.getValue() + "") == 0) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                    FacesMessage.SEVERITY_ERROR);
                    return null;
                }

            }

            double workAmount = 0;
            if (actionTypeId == 63 && feesTypeGharamaId != null && feesTypeGharamaId.equals(new BigDecimal(7))) {
                work =
                    actionsRow.getAttribute("WorkAmount") == null ? "0" :
                    actionsRow.getAttribute("WorkAmount").toString();
                workAmount = Double.parseDouble(work);

                RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id14");
                RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it50");
                if (inp.getValue() == null) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u063a\u0631\u0627\u0645\u0629 \u0627\u0644\u064a\u0648\u0645\u064a\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                    return null;
                } else if (inp.getValue() != null && Double.parseDouble(inp.getValue() + "") == 0) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u063a\u0631\u0627\u0645\u0629 \u0627\u0644\u064a\u0648\u0645\u064a\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                    return null;
                } else if (inpDate.getValue() == null) {
                    ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u062a\u0627\u0631\u064a\u062e \u0628\u062f\u0621 \u062d\u0633\u0627\u0628 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                    FacesMessage.SEVERITY_ERROR);
                    return null;
            }
            }

            if (actionsRow.getAttribute("TotalAmount") == null)
                setAttributeViewValue(PARAMS_ITERATOR, "grantee", grantee);
            else
                setAttributeViewValue(PARAMS_ITERATOR, "grantee", totalVal);
            setAttributeViewValue(PARAMS_ITERATOR, "penaltyAmount", penalty);
            setAttributeViewValue(PARAMS_ITERATOR, "resPartyId", resId);
            setAttributeViewValue(PARAMS_ITERATOR, "capacityTypeId", capacityTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "userId", userId);
            setAttributeViewValue(PARAMS_ITERATOR, "hearingDateId", hearingDateId);
            setAttributeViewValue(PARAMS_ITERATOR, "actionTypeId", actionTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "partyId", partyId);
            setAttributeViewValue(PARAMS_ITERATOR, "actionDate", actionDate);
            setAttributeViewValue(PARAMS_ITERATOR, "procedureText", procedureText);
            setAttributeViewValue(PARAMS_ITERATOR, "folderId", folderId);
            setAttributeViewValue(PARAMS_ITERATOR, "accusedRequired", accusedRequired);
            setAttributeViewValue(PARAMS_ITERATOR, "dateFrom", dateFrom);
            setAttributeViewValue(PARAMS_ITERATOR, "dateTo", dateTo);
            setAttributeViewValue(PARAMS_ITERATOR, "idNumber", idNumber);
            setAttributeViewValue(PARAMS_ITERATOR, "PartyPhoneNumber", PPhoneNumber);
            setAttributeViewValue(PARAMS_ITERATOR, "PreviousVetoDate",
                                  getAttributeValue(ACTION_ITERATOR, "PreviousVeto"));
           // setAttributeViewValue(PARAMS_ITERATOR, "PartyFName", null);
            setAttributeViewValue(PARAMS_ITERATOR, "ClaimFolderId", claimFolderId);
            setAttributeViewValue(PARAMS_ITERATOR, "paymentAmount", payment);
            setAttributeViewValue(PARAMS_ITERATOR, "entityId", prosecutionPartial);
            setAttributeViewValue(PARAMS_ITERATOR, "fixedFine", fixedFine);
            setAttributeViewValue(PARAMS_ITERATOR, "dailyFine", dailyFine);
            setAttributeViewValue(PARAMS_ITERATOR, "guarantees", guarantees);
            setAttributeViewValue(PARAMS_ITERATOR, "expenses", expenses);
            setAttributeViewValue(PARAMS_ITERATOR, "lawyerFees", lawyerFees);
            RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
            RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id14");
            if (inp.getValue() != null)
            setAttributeViewValue(PARAMS_ITERATOR, "penaltyEndDate", inp.getValue());
            if (inpDate.getValue() != null)
                setAttributeViewValue(PARAMS_ITERATOR, "penaltyDueDate", inpDate.getValue());
            System.out.println(folderId + "folderId  " + resId + "degreeIddegree-------" + degree +
                               "IddegreeIddegreeIddegreeId" + degreeId);
            setAttributeViewValue(PARAMS_ITERATOR, "degree", degree);
            setAttributeViewValue(PARAMS_ITERATOR, "resIdFlag", resIdFlag);
            setAttributeViewValue(PARAMS_ITERATOR, "businessCaseId", businessCaseId);
            if (feesTypeId != null && feesTypeId.equals(new BigDecimal(7)))
                setAttributeViewValue(PARAMS_ITERATOR, "prevResPartyId", tempResPartyId);
            else
                setAttributeViewValue(PARAMS_ITERATOR, "prevResPartyId", resPartyId);
            setAttributeViewValue(PARAMS_ITERATOR, "workAmount", workAmount);
            setAttributeViewValue(PARAMS_ITERATOR, "numberPaid", numberPaid);
            setAttributeViewValue(PARAMS_ITERATOR, "idType", idType);
            System.err.println("feesTypeId = " + feesTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "feesTypeId", feesTypeId);
            setAttributeViewValue(PARAMS_ITERATOR, "feesTypeGharamaId", feesTypeGharamaId);
            setAttributeViewValue(PARAMS_ITERATOR, "prosecutionJudegementFlag", prosecutionJudegementFlag);
            //            setAttributeViewValue(PARAMS_ITERATOR, "grantee", grantee);
            //            setAttributeViewValue(PARAMS_ITERATOR, "inpresonYear", inpresonYear);
            //            setAttributeViewValue(PARAMS_ITERATOR, "inpresonMonth", inpresonMonth);
            //            setAttributeViewValue(PARAMS_ITERATOR, "inpresonday", inpresonday);
            //            setAttributeViewValue(PARAMS_ITERATOR, "amount", amount);
            //            setAttributeViewValue(PARAMS_ITERATOR, "dailyPayment", dailyPayment);
            setAttributeViewValue(PARAMS_ITERATOR, "procecutionObjection", procecutionObjection);
            setAttributeViewValue(PARAMS_ITERATOR, "caseSchema", caseSchema);
            setAttributeViewValue(PARAMS_ITERATOR, "PrisonNameDesc",
                                  getAttributeValue(ACTION_ITERATOR, "PrisonNameDesc"));
            setAttributeViewValue(PARAMS_ITERATOR, "partyName", partyName);
            setAttributeViewValue(PARAMS_ITERATOR, "Adress", getAttributeValue(ACTION_ITERATOR, "Adress"));
            setAttributeViewValue(PARAMS_ITERATOR, "VetoNo", getAttributeValue(ACTION_ITERATOR, "VetoNo"));
            setAttributeViewValue(PARAMS_ITERATOR, "NeyabKolyaa",
                                  ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
            setAttributeViewValue(PARAMS_ITERATOR, "VetoDate", getAttributeValue(ACTION_ITERATOR, "VetoDate"));
            setAttributeViewValue(PARAMS_ITERATOR, "VetoYear", getAttributeValue(ACTION_ITERATOR, "VetoYear"));
            setAttributeViewValue(PARAMS_ITERATOR, "City", getAttributeValue(ACTION_ITERATOR, "City"));
            setAttributeViewValue(PARAMS_ITERATOR, "EntityPolice", getAttributeValue(ACTION_ITERATOR, "EntityPolice"));
            setAttributeViewValue(PARAMS_ITERATOR, "VetoStatus", getAttributeValue(ACTION_ITERATOR, "VetoStatus"));
            Integer procId = null;
            int appealPlace = Integer.parseInt(getAttributeValue("AppealPlaceStaticLKPIterator", "Id") + "");
            System.out.println("appealPlace  is -------- " + appealPlace);
            int degSequenntial = degreeId;
            if (degreeId == 5)
                degSequenntial = 2;
            if (degreeId == 6)
                degSequenntial = 4;

            if (degreeId == 3 && (actionTypeId == 14 || actionTypeId == 168  ))
                degSequenntial = 6;
            if (degreeId == 1 &&( actionTypeId == 14 || actionTypeId == 168  ))
                degSequenntial = 7;

            if (capacityTypeId == 3 && attendance == 1 && actionTypeId == 152)
                degSequenntial = 8;
            if (capacityTypeId == 3 && (attendance == 3) && actionTypeId == 152)
                degSequenntial = 9;
            if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 1)
                degSequenntial = 10;
            if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 2)
                degSequenntial = 11;
            if (capacityTypeId == 8  && actionTypeId == 152)
                degSequenntial = 12;
            if (capacityTypeId == 9  && actionTypeId == 152)
                degSequenntial = 13;


            Boolean objResult = true;
            RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it35");
            RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it36");
            setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", null);

            RichInputText comments = (RichInputText) JSFUtils.findComponentInRoot("it41");
            RichInputText retainerNumber = (RichInputText) JSFUtils.findComponentInRoot("it40");
           
            RichInputText name = (RichInputText) JSFUtils.findComponentInRoot("it37");
            RichInputText nid = (RichInputText) JSFUtils.findComponentInRoot("it38");
            RichInputText lawyerPhoneNumber = (RichInputText) JSFUtils.findComponentInRoot("it77");
            RichInputText registerationId = (RichInputText) JSFUtils.findComponentInRoot("it39");
            RichInputText notarizationId = (RichInputText) JSFUtils.findComponentInRoot("it68");
            RichInputText notarizationHarf = (RichInputText) JSFUtils.findComponentInRoot("it69");
            RichInputText notarizationYear = (RichInputText) JSFUtils.findComponentInRoot("it70");
            RichSelectOneChoice notarizationType = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc25");
            RichInputComboboxListOfValues notarizationOfficeId = (RichInputComboboxListOfValues) JSFUtils.findComponentInRoot("notarizationOfficeId");
            RichInputText NotarizationOfficeCode = (RichInputText) JSFUtils.findComponentInRoot("it71");
             

            Object objNID = nid.getValue();
            Object objName = name.getValue();
            Object objRetainerNumber = retainerNumber.getValue();
            
            
            
            
            //                if(objNID != null || objName != null  || objRetainerNumber !=null)
            //                {
            //                    System.out.println(objNID+"SDSSSSSSSSSSSSS"+objName+"--------"+objRetainerNumber);
            //                        showErrorMessageForComponentById("\u0627\u0644\u062a\u0623\u0643\u062f \u0645\u0646 \u0625\u062f\u062e\u0627\u0644 \u0628\u064a\u0627\u0646\u0627\u062a \u0627\u0644\u062a\u0648\u0643\u064a\u0644",
            //                                                         "it37");
            //                         return null;
            //                    }


            if (actionTypeId == 53) {
                RichInputText guaranteeAmount = (RichInputText) JSFUtils.findComponentInRoot("it47");
                RichInputText paidAmount = (RichInputText) JSFUtils.findComponentInRoot("it46");
                if (paidAmount.getValue() != null && guaranteeAmount.getValue() != null) {
                    BigDecimal paid = (BigDecimal) paidAmount.getValue();
                    System.err.println("paid = " + paid);
                    BigDecimal guarantee = (BigDecimal) guaranteeAmount.getValue();
                    if (paid.compareTo(guarantee) > 0) {
                        ADFUtil.showMessage("\u0645\u0628\u0644\u063a \u0627\u0644\u0627\u0633\u062a\u0631\u062f\u0627\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0642\u0644 \u0627\u0648 \u064a\u0633\u0627\u0648\u064a \u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                        FacesMessage.SEVERITY_ERROR);
                        return null;
                    } else
                        setAttributeViewValue(PARAMS_ITERATOR, "paymentAmount", paid);
                } else if (paidAmount.getValue() == null) {
                        ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0645\u0628\u0644\u063a \u0627\u0644\u0627\u0633\u062a\u0631\u062f\u0627\u062f",
                                        FacesMessage.SEVERITY_ERROR);
                        return null;
                }

                ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view.setWhereClause("PROCEDURE_TYPE in (53)");
                    view.setOrderByClause("id desc");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u062c\u0631\u0627\u0621 \u0627\u0643\u062b\u0631 \u0645\u0646 \u0642\u0631\u0627\u0631 \u0625\u0633\u062a\u0631\u062f\u0627\u062f \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        return null;
                    }
                    view.setWhereClause("FEES_TYPE_ID = 4");
                    view.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() < 1) {
                        ADFUtil.showMessage("\u0644\u0627 \u064a\u0648\u062c\u062f \u0627\u062c\u0631\u0627\u0621 \u0633\u062f\u0627\u062f \u0643\u0641\u0627\u0644\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                        view.setWhereClause(null);
                        view.executeQuery();
                        return null;
                    } else {
                        java.util.Date procedureDate = null;
                        java.util.Date previousProcedureDate = null;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            procedureDate = sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                            previousProcedureDate = sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                            System.out.println("&&&&&&&&&&&&");
                            boolean procedureAfterProcedure =
                                ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                            if (!procedureAfterProcedure) {
                                setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                                ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0627\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0627\u062c\u0631\u0627\u0621 \u0633\u062f\u0627\u062f \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                    FacesMessage.SEVERITY_ERROR);
                                view.setWhereClause(null);
                                view.executeQuery();
                                return null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    view.setWhereClause(null);
                    view.executeQuery();
                }
            }

                 Boolean manual = (Boolean) JSFUtils.resolveExpression("#{bindings.SequentialManual.inputValue}");
            System.out.println("AAAAAAAAAAAAA****** " + manual);
            if (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 8 || actionTypeId == 14 || actionTypeId == 168  ||
                actionTypeId == 23 || actionTypeId == 9 || actionTypeId == 152) {
            if (inputNum.getValue() == null && inputYear.getValue() == null && manual) {
               showErrorMessageForComponentById("\u0628\u0631\u062c\u0627\u0621 \u0625\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639",
                                                     "it35");

                return null;
                }


                if (inputNum.getValue() != null && inputYear.getValue() != null &&
                    (manual || JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}"))) {
                    int numSequential = 0;
                    int yearSequential = 0;
                int curryear = Calendar.getInstance().get(Calendar.YEAR);
                    try {

                        numSequential = Integer.parseInt(inputNum.getValue() + "");
                        yearSequential = Integer.parseInt(inputYear.getValue() + "");

                    } catch (Exception e) {
                        showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                         "it35");
                        return null;
                    }

                    if (numSequential > 0 && yearSequential > 2000 && numSequential < 9999999 &&
                        yearSequential < 2023) {

            OperationBinding binding = ADFUtil.findOperation("validatSequentionalNumber");

            binding.getParamsMap().put("folderId",  folderId);
                        binding.getParamsMap().put("degree", degSequenntial);
                        binding.getParamsMap().put("number", numSequential);
                        binding.getParamsMap().put("year", yearSequential);
                        if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"))
                            binding.getParamsMap().put("flagAuto", 1);
            else
                            binding.getParamsMap().put("flagAuto", 0);
                 objResult = (Boolean) binding.execute();
                    } else {
                   showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                    "it35");
                    return null;

                }
                }
            }
            if (objResult != null && !objResult) {

                showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u0645\u0643\u0631\u0631",
                                                 "it35");
            } else {
                //---------------------
//                System.err.println("capacityTypeId=============" + capacityTypeId);
//            System.err.println("nid.getValue()=============" + nid.getValue());
//            System.err.println("name.getValue()=============" + name.getValue());
//            System.err.println("lawyerPhoneNumber.getValue()=============" + lawyerPhoneNumber.getValue());
//            System.err.println("registerationId.getValue()=============" + registerationId.getValue());
//            System.err.println("notarizationId.getValue()=============" + notarizationId.getValue());
//            System.err.println("notarizationHarf.getValue()=============" + notarizationHarf.getValue());
//            System.err.println("notarizationYear.getValue()=============" + notarizationYear.getValue());
//           System.err.println("notarizationType.getValue()=============" + notarizationType.getValue());
//           System.err.println("notarizationOfficeId.getValue()=============" + notarizationOfficeId.getValue());
                
                if ((capacityTypeId == 2 ||capacityTypeId == 10)&& (nid.getValue() == null || name.getValue() == null ||
                    lawyerPhoneNumber.getValue() == null || //registerationId.getValue() == null ||
                    notarizationId.getValue() == null || notarizationHarf.getValue() == null ||
                    notarizationYear.getValue() == null || notarizationType.getValue() == null ||
                    notarizationOfficeId.getValue() == null || NotarizationOfficeCode.getValue() == null) ) {
              
                    System.out.println("capacityTypeId =================== " + capacityTypeId);
					
					FacesMessage Message =
                    new FacesMessage("\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u062C\u0645\u064A\u0639 \u0628\u064A\u0627\u0646\u0627\u062A \u0627\u0644\u062A\u0648\u0643\u064A\u0644");
                    Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, Message);
                    return null;
                }
            if(capacityTypeId == 2 && registerationId.getValue() == null){
            FacesMessage Message =  new FacesMessage("\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u0631\u0642\u0645 \u0642\u064A\u062F \u0627\u0644\u0645\u062D\u0627\u0645\u0649");
            Message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
            return null;
            }
            //---------------------
//                System.err.println("capacityTypeId====" + capacityTypeId);
//                System.err.println("nid.getValue()=" + nid.getValue());
//            System.err.println("name.getValue()=" + name.getValue());
//            System.err.println("retainerNumber.getValue()=" + retainerNumber.getValue());
                           if (capacityTypeId == 2 && nid.getValue() != null && name.getValue() != null &&
                    notarizationId.getValue() != null) {
                    BigDecimal parId = new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceId").toString());

                    String comm = null;
                    if (comments.getValue() != null)
                        comm = comments.getValue().toString();

                    String reg = "0";
                    if (registerationId.getValue() != null)
                        reg = registerationId.getValue().toString();

                    System.err.println(registerationId.getValue() + "comm   is  " + comm);

                    retainerNumber.setValue(notarizationId.getValue() + " / " + notarizationHarf.getValue() + " / " + notarizationYear.getValue() + " / " +
                                            notarizationOfficeId.getValue());
                    
                    OperationBinding operation = getBindings().getOperationBinding("insertLawyerHomePage");
                    operation.getParamsMap().put("parId", parId);
                    operation.getParamsMap().put("nid", nid.getValue().toString());
                    operation.getParamsMap().put("registerationId", new BigDecimal(reg.toString()));
                    operation.getParamsMap().put("name", name.getValue().toString());
                    operation.getParamsMap().put("comments", comm);
                    operation.getParamsMap().put("retainerNumber", notarizationId.getValue().toString());
                    operation.getParamsMap().put("phoneNumber", lawyerPhoneNumber.getValue().toString());
                    
                    operation.getParamsMap().put("createdBy",
                                                 ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
		   //------------------------ADD New 2026-----------------------
//                    System.err.println("NotarizationOfficeCode===" + NotarizationOfficeCode.getValue().toString());
                    operation.getParamsMap().put("year", notarizationYear.getValue().toString());
                    operation.getParamsMap().put("retainerCategory", notarizationType.getValue().toString());
                   operation.getParamsMap().put("harf", notarizationHarf.getValue().toString());
                   operation.getParamsMap().put("releasedFrom", NotarizationOfficeCode.getValue());
                    operation.getParamsMap().put("retainerSerail", retainerNumber.getValue().toString());
                    //--------------------------------ADD New 2026--------------------------
                    System.out.println("operation=====" + operation.getOperationInfo().getOperationName());
                    BigDecimal LawyerId = (BigDecimal) operation.execute();

                    //                    BigDecimal LawyerId=     am.insertLawyer(parId, nid.getValue().toString(),new BigDecimal( reg.toString()),
                    //                                        name.getValue().toString(),comm , retainerNumber.getValue().toString(), ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
                    setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", LawyerId);
                    System.err.println("---------  LawyerId   i s" + LawyerId);
                    
                    
                }
            //-----------------Set DECISION_ID
                if(actionTypeId == 21 || actionTypeId == 35){
                    
                    BigDecimal DecisionId = new BigDecimal(0);
                    
                    if (getAttributeValue(ACTION_ITERATOR, "DecisionId") != null){
                        DecisionId = new BigDecimal(getAttributeValue(ACTION_ITERATOR, "DecisionId").toString());
                         setAttributeViewValue(PARAMS_ITERATOR, "DecisionId", DecisionId);
                    }else{
                        System.err.println("DecisionId eq NULL");
                        FacesMessage Message =
                        new FacesMessage("\u064A\u062C\u0628 \u0627\u062E\u062A\u064A\u0627\u0631 \u0642\u0631\u0627\u0631 \u0627\u0644\u0646\u064A\u0627\u0628\u0629 \u0627\u0644\u0645\u062A\u062E\u0630");
                        Message.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fc = FacesContext.getCurrentInstance();
                        fc.addMessage(null, Message);
                        return null;
                    }
                }
            //-----------------Set DECISION_ID
                
                System.err.println("Bayoumi>>>>Call  saveInventory2 >>1");
                
                OperationBinding bindingSaveInventory2 = ADFUtil.findOperation("saveInventory2");
                bindingSaveInventory2.getParamsMap().put("deliveryDate", deliveryDate);
                bindingSaveInventory2.getParamsMap().put("startDate", startDate);
                bindingSaveInventory2.getParamsMap().put("endDate", endDate);
                bindingSaveInventory2.getParamsMap().put("actionNotes", actionNotes);
            bindingSaveInventory2.getParamsMap().put("guardianName", guardianName);

            bindingSaveInventory2.getParamsMap().put("guardianNationalId", guardianNationalId);
            bindingSaveInventory2.getParamsMap().put("kinship", kinship);
            bindingSaveInventory2.getParamsMap().put("phoneNum", phoneNum);

                ArrayList<Integer> save = (ArrayList<Integer>) bindingSaveInventory2.execute();
                //                    am.saveInventory();


                RichInputText inp2 = (RichInputText) JSFUtils.findComponentInRoot("it42");
                inp2.setDisabled(true);
                if (save.size() > 0) {
                    saveMessage();
                    procId = save.get(0);
                }


                ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
                ADFUtil.setEL("#{pageFlowScope.entityId}", entityId);
                ADFUtil.setEL("#{pageFlowScope.partyId}", partyId);
                ADFUtil.setEL("#{pageFlowScope.resPartyId}", save.size() > 0 ? save.get(2) : null);
                ADFUtil.setEL("#{pageFlowScope.procedureId}", procId);
                ADFUtil.setEL("#{pageFlowScope.actionSaved}", 1);
                System.err.println(getAttributeValue(ACTION_ITERATOR, "ActionTypeId") + " 999999 " +
                                   getAttributeValue(ACTION_ITERATOR, "IdNumIs"));


                uploadIdTawkelSadadTasdekPicFiles();

            ADFUtil.findIterator(ACTION_ITERATOR).getViewObject().executeQuery();
                if (procId != null) {
                    OperationBinding oper2 = getBindings().getOperationBinding("getProsecutionUnderKolyea");
                    oper2.getParamsMap().put("kolyeaId", loggedInEntityId);
                    Object result = oper2.execute();

                    if (result == null)
                        result = new ArrayList();

                    ArrayList<Integer> prosecutionUnderKolyea = (ArrayList<Integer>) result;

                    boolean underKolyea = false;
                    for (int i = 0; prosecutionUnderKolyea.size() > i; i++) {
                        if (prosecutionUnderKolyea.get(i) == prosecutionPartial) {
                            underKolyea = true;
                            ADFUtil.setEL("#{pageFlowScope.underKolyea}", 1);
                        }
                    }
                    //                if (loggedInEntityId != entityId && !underKolyea){
                    if (actionTypeId == 25 || actionTypeId == 26 || actionTypeId == 27) {
                        BigDecimal logId =
                            (BigDecimal) insertIntoExcProceduresLogView(actionTypeId, businessCaseId, userId,
                                                                        prosecutionPartial, 0, loggedInEntityId,
                                                                        new BigDecimal(procId), sequenceNumber, 1,
                                                                        folderTypeId, repeatedFlag, sequenceYear,
                                                                        actionDate);


                        ADFUtil.setEL("#{pageFlowScope.logId}", save.size() > 0 ? save.get(0) : null);
                        ADFUtil.setEL("#{pageFlowScope.actionId}", save.size() > 0 ? save.get(1) : null);
                        ADFUtil.setEL("#{pageFlowScope.procedureLogId}", logId);
                    }
                    ADFUtil.setEL("#{pageFlowScope.folderId}", folderId);
                }

                ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().executeQuery();
                ADFUtil.findIterator("PartyFilesRVO1Iterator").getViewObject().executeQuery();

                if (actionTypeId == 7 || actionTypeId == 77)
                    ADFUtil.findIterator("PreviousProceduresRVO5Iterator").getViewObject().executeQuery();

                if (!invals(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur"), 1))
                    filterFilesTypes(actionTypeId);

                ADFUtil.findIterator(REUPLOAD_FILE_TYPES_ITERATOR).getViewObject().executeQuery();

                //            printAndUploadReportAfterSave(actionTypeId);
                BigDecimal type = (BigDecimal) ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}");
                System.out.println("******--******" +
                                   JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"));
                System.out.println("******manual******" + manual);
                System.out.println("******actionTypeId******" + actionTypeId);
                if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}") && !manual &&
                    (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 3 || actionTypeId == 8 ||
                     actionTypeId == 9 || actionTypeId == 23 || actionTypeId == 14 || actionTypeId == 168 || actionTypeId == 152))

        {

                OperationBinding binding = ADFUtil.findOperation("sequentialAutomation");

                binding.getParamsMap().put("folderId",  folderId);
                    if (type.compareTo(new BigDecimal(3)) == 0) {
                        binding.getParamsMap().put("degree", 33);
                    } else
                        binding.getParamsMap().put("degree", degSequenntial);
                binding.execute();
                    System.err.println("Automation");
            }
            saveDataWithMessage();

            if (actionTypeId == 2)
                JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0647 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629");

            if (actionTypeId == 1)
                JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641");

            //	showErrorMessageForComponentById("", "if18");
        }


            if (actionTypeId == 36 || actionTypeId == 37) {
            System.out.println("SearchInventoryCaseResultRVO1Iterator    ---------");
             ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").executeQuery();
            } else if (actionTypeId == 47 || actionTypeId == 51 || actionTypeId == 10 || actionTypeId == 16 ||
                       actionTypeId == 43)
                ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").executeQuery();
        }


        RichInputText inputID = (RichInputText) JSFUtils.findComponentInRoot("it7");
                RichInputText inputPhone = (RichInputText) JSFUtils.findComponentInRoot("it78");
        if (inputID.getValue() != null && inputPhone.getValue() != null) {
            if (inputID.getValue().toString().length() == 14 && inputPhone.getValue().toString().length() == 11) {
                OperationBinding bindingId1 = ADFUtil.findOperation("checkNTRAResultsIsTrue");
                bindingId1.getParamsMap().put("nationalId", inputID.getValue().toString());
                bindingId1.getParamsMap().put("mobileNo", inputPhone.getValue().toString());
                bindingId1.execute();

                boolean check = (Boolean) bindingId1.getResult();
                System.out.println(" :check: "+check);
                if (check == false) {

                        NtraClient client = new NtraClient();
                                NtraDBHelper op = new NtraDBHelper();
                                GeneralResponse response = new GeneralResponse();
                                Connection dbConnection = null;
                                try {
                                    dbConnection = op.getConnection();
                                    // 29207010101096   01161919320

                        response =
                            client.callNtraSubmit(dbConnection, inputID.getValue().toString(),
                                                  inputPhone.getValue().toString(),
                                                  ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserName().toString());
                        System.out.println("response.getNtraResponseData().getVerficationResult()-----" +
                                           response.getNtraResponseData().getVerficationResult());


                                    OperationBinding bindingId = ADFUtil.findOperation("insertNTRAResults");
                                    bindingId.getParamsMap().put("NationalId", inputID.getValue().toString());
                                    bindingId.getParamsMap().put("MobileNo", inputPhone.getValue().toString());
                        bindingId.getParamsMap().put("PartyType", new BigDecimal(1));
                        bindingId.getParamsMap().put("res", "" + response.getNtraResponseData().getVerficationResult());

                                     bindingId.execute();
                        ADFUtil.findOperation("Commit1").execute();

                                    ViewObject actionsView2 = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
                                    Row actionsRow2 = actionsView2.getCurrentRow();
                                    actionsRow2.setAttribute("PartyNF", response.getNtraResponseData().getVerficationResult());
//                                    //------------------insertSmsQueue---------------------
//                                    
//                                    System.err.println("InsertSmsQueuePartyId = " + partyId);
//                                    System.err.println("InsertSmsQueueUserId = " + userId);
//                                    System.err.println("actionDate = " +actionDate);
//                                    
//                                    System.err.println("JudgmentText = " + searchInventoryCaseResultRow.getAttribute("JudgmentText"));
//                                    
////                                    String smsBody = actionDate
//                                    OperationBinding bindingInsertSmsQueue = ADFUtil.findOperation("insertSmsQueue");
//                                    
//                                    
//                                    bindingInsertSmsQueue.getParamsMap().put("MobileNo", inputPhone.getValue().toString()); 
//                                    bindingInsertSmsQueue.getParamsMap().put("SmsBody", "\u062A\u0627\u0631\u064A\u062E \u0627\u0644\u0625\u062C\u0631\u0627\u0621 " + actionDate + " " + "\u0627\u0644\u062D\u0643\u0645 " + searchInventoryCaseResultRow.getAttribute("JudgmentText"));
//                                    bindingInsertSmsQueue.getParamsMap().put("CreatedBy", userId);                                    
//                                    bindingInsertSmsQueue.getParamsMap().put("PartyId", partyId);
//                                    bindingInsertSmsQueue.getParamsMap().put("businessCaseId", businessCaseId); 
//                                    
//                                     bindingInsertSmsQueue.execute();
//                                    ADFUtil.findOperation("Commit1").execute();
//                                    //------------------insertSmsQueue---------------------

                        if (response.getNtraResponseData().getVerficationResult() != null &&
                            response.getNtraResponseData().getVerficationResult().equals("T")) {
                            FacesMessage Message =
                                new FacesMessage("\u0647\u0630\u0627\u0020\u0627\u0644\u0631\u0642\u0645\u0020\u0645\u0637\u0627\u0628\u0642\u0020\u0644\u0644\u0631\u0642\u0645\u0020\u0627\u0644\u0642\u0648\u0645\u064a");
                                        Message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, Message);
                        } else if (response.getNtraResponseData().getVerficationResult() != null &&
                                   response.getNtraResponseData().getVerficationResult().equals("F")) {
                            FacesMessage Message =
                                new FacesMessage("\u0647\u0630\u0627\u0020\u0627\u0644\u0631\u0642\u0645\u0020\u063a\u064a\u0631\u0020\u0645\u0637\u0627\u0628\u0642\u0020\u0644\u0644\u0631\u0642\u0645\u0020\u0627\u0644\u0642\u0648\u0645\u064a");
                                        Message.setSeverity(FacesMessage.SEVERITY_INFO);
                                        FacesContext fc = FacesContext.getCurrentInstance();
                                        fc.addMessage(null, Message);
                                    }

                                    ADFUtil.setEL("#{pageFlowScope.actionSaved}", 1);


                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                }else if (check == true){
                    FacesMessage Message =
                        new FacesMessage("\u0647\u0630\u0627\u0020\u0627\u0644\u0631\u0642\u0645\u0020\u0645\u0637\u0627\u0628\u0642\u0020\u0644\u0644\u0631\u0642\u0645\u0020\u0627\u0644\u0642\u0648\u0645\u064a");
                    Message.setSeverity(FacesMessage.SEVERITY_INFO);
                    FacesContext fc = FacesContext.getCurrentInstance();
                    fc.addMessage(null, Message);
                    }

                }
        }


        return null;
    }
    /*
     * This Method used to print report and upload data after save procedure
     * */
    private void printAndUploadReportAfterSave(int actionTypeId) {

        if (actionTypeId == 2 || actionTypeId == 1 || actionTypeId == 9 || actionTypeId == 152) {
            String reportName = "";
            int typeId = 0;
            if (actionTypeId == 2) {
                typeId = 15;
                reportName = nameOfReport(typeId);
            } else if (actionTypeId == 9) {
                reportName = "AppealAuthorization";
            } else if (actionTypeId == 152) {
                                reportName = "APPEALVETO";
            } else {
                typeId = 21;
                reportName = nameOfReport(typeId);
            }
            String pathReport = "";
            if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
                pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");


            try {
                super.print(pathReport, reportName, "procedures", reportName);

            } catch (Exception e) {
                System.err.println("Error Print Report " + e.getMessage());
            }
            File pdf = null;
            FileInputStream reportStream = null;
            String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
            String pdfFileName = reportName + "-" + toDate + "." + "pdf";
            try {
                System.err.println("Imp   = " + JSFUtils.getFromSession("fillRep"));
                JasperPrint jp = (JasperPrint) JSFUtils.getFromSession("fillRep");
                pdf = File.createTempFile(reportName, ".pdf");
                reportStream = new FileInputStream(pdf);
                JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                uploadDocumntTOUCM(reportStream, pdfFileName, typeId, getCaseSchemaId(actionTypeId));
                //                Files.deleteIfExists(pdf.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void printAndUploadReport(String actionTypeId) {

        if (actionTypeId.equals("2") || actionTypeId.equals("1")) {
            String reportName = "";
            int typeId = 0;
            if (actionTypeId.equals("2")) {
                typeId = 15;
                reportName = nameOfReport(typeId);
            } else {
                typeId = 21;
                reportName = nameOfReport(typeId);
            }
            String pathReport = "";
            if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
                pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");


            try {
                super.print(pathReport, reportName, "procedures", reportName);

            } catch (Exception e) {
                System.err.println("Error Print Report " + e.getMessage());
            }
            File pdf = null;
            FileInputStream reportStream = null;
            String toDate = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
            String pdfFileName = reportName + "-" + toDate + "." + "pdf";
            try {
                System.err.println("Imp   = " + JSFUtils.getFromSession("fillRep"));
                JasperPrint jp = (JasperPrint) JSFUtils.getFromSession("fillRep");
                pdf = File.createTempFile(reportName, ".pdf");
                reportStream = new FileInputStream(pdf);
                JasperExportManager.exportReportToPdfStream(jp, new FileOutputStream(pdf));

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                //                uploadDocumntTOUCM(reportStream, pdfFileName, typeId, getCaseSchemaId(Integer.parseInt(actionTypeId)));
                //                Files.deleteIfExists(pdf.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private int lastActionMoardaEstEatrad() {
        // ط£ع©ط¢آ¹ط£ع©ط¢آ´ط£ع©ط¢آ§ط£â„¢أ¢â‚¬آ  ط£â„¢ط¥آ ط£ع©ط¢آ¸ط£â„¢أ¢â‚¬طŒط£ع©ط¢آ± ط£ع©ط¢آ§ط£â„¢أ¢â‚¬ï؟½? inputfile ط£ع©ط¢آ§ط£â„¢أ¢â‚¬ï؟½?ط£ع©ط¢آ®ط£ع©ط¢آ§ط£ع©ط¢آµ ط£ع©ط¢آ¨ط£ع©ط¢آ§ط£â„¢أ¢â‚¬ï؟½?ط£ع©ط¢آ§ط£ع©ط¢آ¬ط£ع©ط¢آ±ط£ع©ط¢آ§ط£ع©ط¢طŒ ط£â„¢أ¢â‚¬آ¦ط£â„¢أ¢â‚¬آ  ط£ع©ط¢آ§ط£â„¢أ¢â‚¬ï؟½?ط£ع©ط¢آ¯ط£ع©ط¢آ§ط£ع©ط¢آ®ط£â„¢أ¢â‚¬ï؟½? ط£ع©ط¢آ³ط£â„¢ط«â€ ط£ع©ط¢آ§ط£ع©ط¢طŒ ط£â„¢ط¦â€™ط£ع©ط¢آ§ط£â„¢أ¢â‚¬آ  ط£â„¢أ¢â‚¬آ¦ط£ع©ط¢آ¹ط£ع©ط¢آ§ط£ع©ط¢آ±ط£ع©ط¢آ¶ط£ع©ط¢آ© ط£ع©ط¢آ§ط£ع©ط¢آ³ط£ع©ط¢ع¾ط£ع©ط¢آ£ط£â„¢أ¢â‚¬آ ط£ع©ط¢آ§ط£آ¯ط¢طںط¢آ½? ط£ع©ط¢آ§ط£â„¢ط«â€  ط£ع©ط¢آ§ط£â„¢أ¢â‚¬آ¦ط£ع©ط¢آ± ط£ع©ط¢آ¬ط£â„¢أ¢â‚¬آ ط£ع©ط¢آ§ط£ع©ط¢آ¦ط£â„¢ط¥آ 

        int max = 0;
        Object lastAction = null;
        ADFUtil.findIterator(PREV_ROW_PROCEDURE_ITERATOR).setRangeSize(-1);
        for (Row r : ADFUtil.findIterator(PREV_ROW_PROCEDURE_ITERATOR).getAllRowsInRange()) {
            if (invals(r.getAttribute("ProcedureType"), 1, 2, 14)) {
                if (Integer.parseInt(r.getAttribute("Id") + "") > max) {
                    max = Integer.parseInt(r.getAttribute("Id") + "");
                    lastAction = r.getAttribute("ProcedureType");
                }
            }
        }

        if (lastAction == null)
            return 0;
        else {
            int val = Integer.parseInt(lastAction + "");
            return val;
        }
    }

    private void uploadIdTawkelSadadTasdekPicFiles() {
        
        System.out.println(ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject().getRowCount());
        System.err.println(getAttributeValue(ACTION_ITERATOR, "ActionTypeId") + " 888888888 **" +
                           getAttributeValue(ACTION_ITERATOR, "IdNumIs"));
        
        
        System.out.println("-------"+JSFUtils.getFromPageFlowScope("DID")+"***"+ JSFUtils.getFromPageFlowScope("DName"));
        
        if ((  getAttributeValue(ACTION_ITERATOR, "IdNumIs") != null ||
              (JSFUtils.getFromPageFlowScope("DID") != null && JSFUtils.getFromPageFlowScope("DName") != null) ||
             getAttributeValue(ACTION_ITERATOR, "SadadIs") != null ||
             getAttributeValue(ACTION_ITERATOR, "TawkeelIs") != null ||
             getAttributeValue(ACTION_ITERATOR, "TasdekIS") != null ||
             getAttributeValue(ACTION_ITERATOR, "CancelIs") != null ||
            getAttributeValue(ACTION_ITERATOR, "ExecReport") != null ||
            getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplate") != null ||
            getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplateSecond") != null ||
             getAttributeValue(ACTION_ITERATOR, "EditIs") != null) &&
            getAttributeValue(ACTION_ITERATOR, "ActionTypeId") != null) {
            System.out.println("888888888");
            int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
            BigDecimal caseSchemaId = null;

            caseSchemaId = getCaseSchemaId(actionTypeId);

            try {
                if (getAttributeValue(ACTION_ITERATOR, "IdNumIs") != null) {
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "IdNumIs");
                    uploadDocumntTOUCM(is, personalPic.getFilename(), 17, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "TawkeelIs") != null) {
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "TawkeelIs");
                    uploadDocumntTOUCM(is, procurationPic.getFilename(), 18, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "SadadIs") != null) {
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "SadadIs");
                    uploadDocumntTOUCM(is, payment.getFilename(), 26, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "TasdekIS") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "TasdekIS");
                    uploadDocumntTOUCM(is, tasdekFile.getFilename(), 34, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "CancelIs") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "CancelIs");
                    uploadDocumntTOUCM(is, cancelFile.getFilename(), 46, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "EditIs") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "EditIs");
                    uploadDocumntTOUCM(is, editFile.getFilename(), 47, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "ExecReport") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>ExecReport>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "ExecReport");
                    uploadDocumntTOUCM(is, execReport9.getFilename(), 68, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplate") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>ReinstatelProcedureTemplate>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplate");
                    uploadDocumntTOUCM(is, ReinstatelProcedureTemplate.getFilename(), 69, caseSchemaId);
                }
                if (getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplateSecond") != null) {
                    System.err.println("ENTER  UPLOAD FILES  >>>ReinstatelProcedureTemplateSecond>>> ");
                    InputStream is = (InputStream) getAttributeValue(ACTION_ITERATOR, "ReinstatelProcedureTemplateSecond");
                    uploadDocumntTOUCM(is, ReinstatelProcedureTemplateSecond.getFilename(), 70, caseSchemaId);
                }
                if (JSFUtils.getFromPageFlowScope("DID") != null && JSFUtils.getFromPageFlowScope("DName") != null) {
                    String dID = (String) JSFUtils.getFromPageFlowScope("DID");
                    String dDocName = (String) JSFUtils.getFromPageFlowScope("DName");
                    String fileName = "\u0635\u0648\u0631\u0629 \u0627\u0644\u0645\u062A\u0647\u0645.jpg";

                    uploadAttaches(JSFUtils.getFromPageFlowScope("procedureId"), 20, fileName, dID, dDocName,
                                   new BigDecimal(6000));
                    ADFContext.getCurrent().getSessionScope().put("DOCUMENTID", dID);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadDocumntTOUCM(InputStream is, String fileName, int fileType,
                                    BigDecimal caseSchemaId) throws Exception {
        
        System.err.println("><><> " + is);
        int logId = 0;
        if (ADFUtil.evaluateEL("#{pageFlowScope.procedureLogId}") != null) {
            logId = Integer.parseInt(ADFUtil.evaluateEL("#{pageFlowScope.procedureLogId}").toString());
        }

        DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, fileName);
        String dDocName = do_.get("dDocName");
        String documentId = do_.get("dID");
        uploadAttaches(JSFUtils.getFromPageFlowScope("procedureId"), fileType, fileName, documentId, dDocName,
                       caseSchemaId);
        updateProcedureLogDocuments(logId, documentId, fileType);
    }

    private int lastProcedureAction(int folerId, int actionTypeId) {
        OperationBinding oper = getBindings().getOperationBinding("lastProcedureAction");
        oper.getParamsMap().put("folerId", folerId);
        oper.getParamsMap().put("actionTypeId", actionTypeId);
        int result = (Integer) oper.execute();
        return result;
    }

    @SuppressWarnings("unchecked")
    public int countPrevActions(int resPartyId, int rulesOrProcedures) {
        OperationBinding oper = getBindings().getOperationBinding("countPrevActions");
        oper.getParamsMap().put("resPartyId", resPartyId);
        oper.getParamsMap().put("rulesOrProcedures", rulesOrProcedures);
        int result = (Integer) oper.execute();
        return result;
    }

    @Override
    public void populateParamsMap(HashMap parametersMap) {

        ViewObject view = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        Row row = view.getCurrentRow();

        Integer userId = null;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }

        BigDecimal procedureId = null;
        if (ADFUtil.evaluateEL("#{pageFlowScope.procedureId}") != null)
            procedureId = new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.procedureId}").toString());
        BigDecimal actionTypeId =
            new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.actionTypeId}") != null ?
                           ADFUtil.evaluateEL("#{pageFlowScope.actionTypeId}").toString() : "0");

        //    System.err.println(ADFUtil.evaluateEL("#{pageFlowScope.documentId}"));
        BigDecimal did =
            new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.documentId}") == null ? "0" :
                           ADFUtil.evaluateEL("#{pageFlowScope.documentId}").toString());
        int folderId = 0;
        if (ADFUtil.evaluateEL("#{pageFlowScope.folderId}") != null)
            folderId = Integer.parseInt(ADFUtil.evaluateEL("#{pageFlowScope.folderId}").toString());
        else if (getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderId") != null)
            folderId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderId") + "");

        System.err.println("folderId = " + folderId);

        String abroadFlag =
            ADFUtil.evaluateEL("#{pageFlowScope.AbroadFlag}") == null ? null :
            ADFUtil.evaluateEL("#{pageFlowScope.AbroadFlag}").toString();
        InputStream partyImage = (InputStream) JSFUtils.getFromSession("partyImage");
        //    System.out.println("partyImage ::::>>>> " + partyImage);	
        parametersMap.put("AbroadFlag", abroadFlag);
        parametersMap.put("partyId", getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId"));
        System.err.println("partyId = " + getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId"));
        parametersMap.put("PROCEDURE_ID", procedureId);
        parametersMap.put("userId", userId);
        parametersMap.put("ACTION_TYPE", actionTypeId);
        parametersMap.put("imageId", partyImage);
        parametersMap.put("FOLDER_ID", folderId);
        parametersMap.put("DID", did);
        JSFUtils.setSessionAttribute("partyImage", null);
        JSFUtils.addToPageFlowScope("documentId", null);

        //    System.out.println("procedureId
        //    System.out.println("userId
        //    System.out.println("ACTION_TYPE
    }

    private int degree(int actionTypeId) {
        int degree = 0;
        int judgmentType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") + "") : -1;
        if (actionTypeId == 1) {
            degree = 3;
        } else if (actionTypeId == 2) {
            degree = 2;
        } else if (actionTypeId == 8) {
            degree = 5;
        } else if (actionTypeId == 9) {
            degree = 4;
        } else if (actionTypeId == 23) {
            degree = 6;
        } else if (actionTypeId == 24) {
            Object obj = getAttributeValue("ActionsRVO1Iterator", "AppealDegree");
            if (obj != null)
                degree = Integer.parseInt(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "");
        } else if (actionTypeId == 14 || actionTypeId == 168 ) {
            if (judgmentType == 2) // amr genaya mahkama
            {
                degree = 3;
            } else if (judgmentType == 3) {
                degree = 1;
            }
        }
        return degree;
    }

    public void actionTypeChangeListener(ValueChangeEvent valueChangeEvent) {
        int actionType = 0;
        int actionTypeId = 0;
        ADFUtil.setEL("#{sessionScope.actionTypeId}", null);
        if (valueChangeEvent.getNewValue() != null) {
            actionType = Integer.parseInt(valueChangeEvent.getNewValue().toString());
        }
        if (actionType == 1) {
            actionTypeId = 3;
        } else if (actionType == 2) {
            actionTypeId = 2;
        } else if (actionType == 8) {
            actionTypeId = 2;
        } else {
            actionTypeId = 4;
        }

        ADFUtil.setEL("#{sessionScope.actionTypeId}", actionTypeId);
    }

    // button search in inventory search
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public String doSearchInventory() {
        JSFUtils.addToSession("pSearchType", "1");
        ADFContext.getCurrent().getSessionScope().put("ruleEnt",
                                                      JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));

        ADFUtil.setEL("#{pageFlowScope.caseSchema}", null);
        ADFUtil.setEL("#{pageFlowScope.businessCaseId}", null);
        ADFUtil.setEL("#{pageFlowScope.resPartyId}", null);
        ADFUtil.setEL("#{sessionScope.prosecutionUnderKolyea}", null);
        ADFUtil.setEL("#{pageFlowScope.actionSaved}", null);
        ADFUtil.findOperation("ValidatePoliceReportGenericT").execute();
        
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        System.err.println("getStatusForBusinessCaseId--Star");
        System.out.println("****"+JSFUtils.getFromSession("bcidp"));
        BigDecimal businessCaseNmber = (BigDecimal) JSFUtils.getFromSession("bcidp");
        OperationBinding oper = getBindings().getOperationBinding("getStatusForBusinessCaseId");
        oper.getParamsMap().put("businessCaseId", businessCaseNmber);
        Object obj = oper.execute();
        BigDecimal returnStatus = null;
        if (obj != null) {
        returnStatus = new BigDecimal(obj.toString());
        if (returnStatus.compareTo(new BigDecimal("2")) == 0) {
            JSFUtils.showFacesInfoMessageFromBundle("bundle","caseIsUnderInvestigation");
        return null;
        }}
        System.err.println("returnStatus----------=" + returnStatus);
        System.err.println("getStatusForBusinessCaseId--End");
        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
        
        Row searchInventoryRow = ADFUtil.findIterator("SearchInventoryRVO1Iterator").getViewObject().getCurrentRow();
        //        ADFUtil.setEL("#{sessionScope.policeId}", searchInventoryRow.getAttribute("NeyabGozayaa"));
        ADFUtil.setEL("#{sessionScope.policeId}", JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
        System.out.println("BusinessCaseTypeId*-*-*-*-BusinessCaseTypeId ---" +
                           JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}"));
        
        if (((BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}")).compareTo(new BigDecimal(4)) ==
            0)
        ADFUtil.setEL("#{sessionScope.policeId}", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));

        int searchType =
            Integer.parseInt(searchInventoryRow.getAttribute("SearchType") == null ? "1" :
                             searchInventoryRow.getAttribute("SearchType").toString());
        if (searchType == 1)
            JSFUtils.addToSession("pSearchType", "2");
        ///mahmoud
        int loggedInEntityId = 0;
        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            loggedInEntityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }
        // / mahmoud
        int entityId = 0;
        int sequenceNumber = 0;
        int folderId = 0;
        long resId = 0;
        BigDecimal BCID = new BigDecimal(0);
        int userId = 0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }

        int entity =
            Integer.parseInt(searchInventoryRow.getAttribute("NeyabGozayaa") == null ? "0" :
                             searchInventoryRow.getAttribute("NeyabGozayaa").toString());
        ADFUtil.setEL("#{pageFlowScope.entity}", entity);

        String repeatedFlag = "";
        int sequenceYear = 0;
        long businessCaseId = 0;
System.err.println("entity------>" + entity);
    
        ADFUtil.setEL("#{pageFlowScope.caseType}", null);

        ViewObject resultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        String error =
            "\u064A\u062C\u0628 \u0623\u062F\u062E\u0627\u0644 \u0643\u0644 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B";


        if (searchType == 1 &&
            (searchInventoryRow.getAttribute("Year") == null ||
             searchInventoryRow.getAttribute("NeyabKolyaa") == null ||
            searchInventoryRow.getAttribute("NeyabGozayaa") == null ||
             searchInventoryRow.getAttribute("BusinessCaseTypeId") == null ||
             searchInventoryRow.getAttribute("CaseNumber") == null)) {
            resultView.executeEmptyRowSet();
            
            ADFUtil.showMessage(error, FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (searchType == 1 && !invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643) &&
            searchInventoryRow.getAttribute("OrigEntity") == null) {
            ADFUtil.showMessage(error, FacesMessage.SEVERITY_ERROR);
            return null;
        }

        if (searchType == 2 &&
            (searchInventoryRow.getAttribute("Year") == null ||
             searchInventoryRow.getAttribute("SequenceNumber") == null ||
             searchInventoryRow.getAttribute("RepeatedFlag") == null ||
             searchInventoryRow.getAttribute("FolderTypeId") == null)) {
            ADFUtil.showMessage(error, FacesMessage.SEVERITY_ERROR);
            return null;
        }

        //EstanafGozayaTask
        if (searchType == 2 && !invals(getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag"), "Y") &&
            (searchInventoryRow.getAttribute("OrigEntity") == null ||
             searchInventoryRow.getAttribute("NeyabGozayaa") == null)) {
            ADFUtil.showMessage(error, FacesMessage.SEVERITY_ERROR);
            return null;
        }
        String[] gozayaNumber = new String[5];

        System.err.println("case Number = " + searchInventoryRow.getAttribute("CaseNumber") + " -- " +
                           searchInventoryRow.getAttribute("Year") + " == " +
                           searchInventoryRow.getAttribute("BusinessCaseTypeId") + " 000 " +
                           searchInventoryRow.getAttribute("NeyabKolyaa"));
        Boolean flg =
            (Boolean) "N".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString());
        System.out.println(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString() + "result flg   :" +
                           flg);
        System.out.println("searchInventoryRow.getAttribute(\"NeyabGozayaa\")    " +
                           searchInventoryRow.getAttribute("NeyabGozayaa"));
        //        if(flg)
        // if he search by genayat or gonah Mostanaf
        if (searchInventoryRow.getAttribute("BusinessCaseTypeId") != null &&
            invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
            Object val =
                getGozayFromKolyNumber(searchInventoryRow.getAttribute("CaseNumber"),
                                       searchInventoryRow.getAttribute("Year"),
                                       searchInventoryRow.getAttribute("BusinessCaseTypeId"),
                                       flg ? searchInventoryRow.getAttribute("NeyabKolyaa") :
                                       searchInventoryRow.getAttribute("NeyabGozayaa"));
            if (val != null)
                gozayaNumber = val.toString().split("-");

            if (searchInventoryRow.getAttribute("NeyabGozayaa") == null) {
                entity = getIntValueFromObject(gozayaNumber[4]);
                ADFUtil.setEL("#{pageFlowScope.entity}", entity);
                ADFUtil.setEL("#{sessionScope.policeId}", entity);
            }
        }


        JSFUtils.storeOnSession("ppFolderType", new BigDecimal(0));
        JSFUtils.storeOnSession("ppCaseNumber", new BigDecimal(0));
        JSFUtils.storeOnSession("ppSeqNumber", new BigDecimal(0));
        JSFUtils.storeOnSession("ppCaseType", new BigDecimal(0));
        JSFUtils.storeOnSession("ppRepeatFlag", "A");
        JSFUtils.storeOnSession("ppFoldYear", new BigDecimal(0));
        JSFUtils.storeOnSession("ppCaseYear", new BigDecimal(0));
        JSFUtils.storeOnSession("ppFoldOrigin", new BigDecimal(0));
        JSFUtils.storeOnSession("ppCaseOrigin", new BigDecimal(0));

        String condition = " 1 = 1 ";


        if (searchInventoryRow.getAttribute("FolderTypeId") != null) {
            condition += " AND FOLDER_TYPE = " + searchInventoryRow.getAttribute("FolderTypeId");
            JSFUtils.storeOnSession("ppFolderType",
                                    new BigDecimal(searchInventoryRow.getAttribute("FolderTypeId") + ""));
        }
        System.out.println("belal-*----------------" + searchInventoryRow.getAttribute("CaseNumber") + ".....---");
        if (searchInventoryRow.getAttribute("CaseNumber") != null &&
            (searchInventoryRow.getAttribute("CaseNumber") + "").length() > 0) {
            System.out.println("****************");
            if (searchInventoryRow.getAttribute("BusinessCaseTypeId") != null &&
                invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
                condition += " AND CASE_NUMBER = " + gozayaNumber[0];
                JSFUtils.storeOnSession("ppCaseNumber", new BigDecimal(gozayaNumber[0] + ""));
                //                BCID =new BigDecimal(gozayaNumber[5]+"");
                JSFUtils.storeOnSession("ppBCID", BCID);

            } else {
                condition += " AND CASE_NUMBER = " + searchInventoryRow.getAttribute("CaseNumber");
                JSFUtils.storeOnSession("ppCaseNumber",
                                        new BigDecimal(searchInventoryRow.getAttribute("CaseNumber") + ""));
            }
        }

        if (searchInventoryRow.getAttribute("SequenceNumber") != null) {
            condition += " AND SEQUENCE_NUMBER = " + searchInventoryRow.getAttribute("SequenceNumber");
            JSFUtils.storeOnSession("ppSeqNumber",
                                    new BigDecimal(searchInventoryRow.getAttribute("SequenceNumber") + ""));
        }
        if (searchInventoryRow.getAttribute("BusinessCaseTypeId") != null) {
            if (invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
                condition += " AND CAS_TYPE_ID = " + gozayaNumber[2];
                JSFUtils.storeOnSession("ppCaseType", new BigDecimal(gozayaNumber[2] + ""));
            } else {
                condition += " AND CAS_TYPE_ID = " + searchInventoryRow.getAttribute("BusinessCaseTypeId");
                JSFUtils.storeOnSession("ppCaseType",
                                        new BigDecimal(searchInventoryRow.getAttribute("BusinessCaseTypeId") + ""));
            }
        }
        if (searchInventoryRow.getAttribute("RepeatedFlag") != null) {
            condition += " AND REPEATED_FLAG = '" + searchInventoryRow.getAttribute("RepeatedFlag") + "' ";
            JSFUtils.storeOnSession("ppRepeatFlag", searchInventoryRow.getAttribute("RepeatedFlag") + "");
        }

        if (searchType == 1) {
            if (searchInventoryRow.getAttribute("BusinessCaseTypeId") != null &&
                invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
                condition += " AND YEAR = " + gozayaNumber[1];
                JSFUtils.storeOnSession("ppFoldYear", new BigDecimal(gozayaNumber[1] + ""));
            } else {
                condition += " AND CASE_YEAR = " + searchInventoryRow.getAttribute("Year");
                JSFUtils.storeOnSession("ppCaseYear", new BigDecimal(searchInventoryRow.getAttribute("Year") + ""));
            }
        } else {
            condition += " AND YEAR = " + searchInventoryRow.getAttribute("Year");
            JSFUtils.storeOnSession("ppFoldYear", new BigDecimal(searchInventoryRow.getAttribute("Year") + ""));
        }
        //EstanafGozayaTask
        if ((searchType == 2 && invals(getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag"), "Y")) ||
            (searchType == 1 &&
             invals(searchInventoryRow.getAttribute("BusinessCaseTypeId"), 3646, 3647, 3648, 3643))) {
            condition +=
                " AND POLICE_STATION_ENTITY_ID = " +
                (flg ? searchInventoryRow.getAttribute("NeyabKolyaa") :
                 searchInventoryRow.getAttribute("NeyabGozayaa"));
            JSFUtils.storeOnSession("ppFoldOrigin",
                                    new BigDecimal((flg ? searchInventoryRow.getAttribute("NeyabKolyaa") :
                                                    searchInventoryRow.getAttribute("NeyabGozayaa")) + ""));
        } else {
            condition += " AND Origin_Entity = " + searchInventoryRow.getAttribute("OrigEntity");
            JSFUtils.storeOnSession("ppCaseOrigin", new BigDecimal(searchInventoryRow.getAttribute("OrigEntity") + ""));

        }
        //        if  (searchType == 2){
        //
        //               OperationBinding oper =   ADFUtil.findOperation("getIdBusinessCaseFromFolder");
        //            oper.execute();
        //                if (oper.getErrors().isEmpty()) {
        //                resultView.executeQuery();
        //                ADFUtil.findIterator("ResPartiesGozayaaROVO1Iterator").getViewObject().executeQuery();
        //            }
        //        }
        //        System.out.println("HHHGHGHGHGHGH"+BCID);
        //        if  (searchType == 1 &&( BCID.compareTo(new BigDecimal(0))== 0 || BCID != null))
        //            {
        //
        //                           OperationBinding oper =   ADFUtil.findOperation("getIdBusinessCaseFrompolice");
        //                        oper.execute();
        //                            if (oper.getErrors().isEmpty()) {
        //                            resultView.executeQuery();
        //                            ADFUtil.findIterator("ResPartiesGozayaaROVO1Iterator").getViewObject().executeQuery();
        //                        }
        //                    }


        System.out.println("ppCaseOrigin is :" + JSFUtils.getFromSession("ppCaseOrigin"));
        System.out.println("ppFoldOrigin is :" + JSFUtils.getFromSession("ppFoldOrigin"));
        System.out.println("ppFoldYear is :" + JSFUtils.getFromSession("ppFoldYear"));
        System.out.println("ppCaseYear is :" + JSFUtils.getFromSession("ppCaseYear"));
        System.out.println("ppRepeatFlag is :" + JSFUtils.getFromSession("ppRepeatFlag"));
        System.out.println("ppCaseType is :" + JSFUtils.getFromSession("ppCaseType"));
        System.out.println("ppSeqNumber is :" + JSFUtils.getFromSession("ppSeqNumber"));
        System.out.println("ppCaseNumber is :" + JSFUtils.getFromSession("ppCaseNumber"));
        System.out.println("ppFolderType is :" + JSFUtils.getFromSession("ppFolderType"));
        System.out.println("ppBCID is :" + JSFUtils.getFromSession("ppBCID"));
        System.out.println("condition is :" + condition);

        //        resultView.setWhereClause(condition);

        resultView.executeQuery();
        ADFUtil.findIterator("ResPartiesGozayaaROVO1Iterator").getViewObject().executeQuery();
//        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
//        System.err.println("getStatusForBusinessCaseId--Star");
//        System.out.println("****"+JSFUtils.getFromSession("bcidp"));
//        BigDecimal businessCaseNmber = (BigDecimal) JSFUtils.getFromSession("bcidp");
//        OperationBinding oper = getBindings().getOperationBinding("getStatusForBusinessCaseId");
//        oper.getParamsMap().put("businessCaseId", businessCaseNmber);
//        Object obj = oper.execute();
//        BigDecimal returnStatus = null;
//        if (obj != null) {
//        returnStatus = new BigDecimal(obj.toString());
//        if (returnStatus.compareTo(new BigDecimal("2")) == 0) {
//        JSFUtils.showFacesInfoMessageFromBundle("bundle","caseIsUnderInvestigation");
//        return null;
//        }}
//        System.err.println("returnStatus----------=" + returnStatus);
//        System.err.println("getStatusForBusinessCaseId--End");
//        //------------------ReturnStatusForBusinessCaseIdParam----------------------------------
           System.err.println(resultView.getQuery());
        if (resultView.getEstimatedRowCount() > 0) {
            ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
            if (actionsView.getRowCount() == 0) {
            Row actionsRow = actionsView.createRow();
            actionsView.insertRow(actionsRow);
            }
            Row row = resultView.first();
            //            System.out.println(businessCaseId+"JSFUtils.resolveExpression(\"#{bindings.BusinessCaseId.inputValue}\")"+JSFUtils.resolveExpression("#{bindings.BusinessCaseId.inputValue}"));
            //            JSFUtils.addToSession("ppBCID", new BigDecimal(businessCaseId+""));
            //

            sequenceNumber = Integer.parseInt(row.getAttribute("SequenceNumber").toString());
            businessCaseId = Long.parseLong(row.getAttribute("BusinessCaseId").toString());
            folderId = Integer.parseInt(row.getAttribute("FolderId").toString());
            ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
            repeatedFlag = row.getAttribute("RepeatedFlag").toString();
            sequenceYear = Integer.parseInt(row.getAttribute("Year").toString());
            resId = Long.parseLong(row.getAttribute("BusinessCaseId").toString());

            ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
            ADFUtil.setEL("#{sessionScope.businessCaseId}", businessCaseId);
            

            
            int caseSchema = getCaseSchema(businessCaseId);
            if (caseSchema != 0) {
              int  entDeg = Integer.parseInt(row.getAttribute("EntDeg").toString());
              if (entDeg == 3)
                  ADFUtil.setEL("#{pageFlowScope.caseSchema}", -1);
              else
                ADFUtil.setEL("#{pageFlowScope.caseSchema}", caseSchema);
                System.out.println("set case schema " + caseSchema);
            }

            /* By Belal
     *to avoid read the case from abroad when gozaya == null so we need for gozayaa to determine from abroad or not so use function getEntityIdFromBusinseCase */
            if ((searchType == 2 && invals(getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag"), "Y")) &&
                entity == 0) {
                entity = getEntityIdFromBusinseCase(businessCaseId);
                ADFUtil.setEL("#{pageFlowScope.entity}", entity);
                ADFUtil.setEL("#{sessionScope.policeId}", entity);
            }

            ADFUtil.setEL("#{pageFlowScope.prosecutionUnderKolyea}", null);
            ADFUtil.setEL("#{sessionScope.prosecutionUnderKolyea}", null);
            if (loggedInEntityId == entity) {
                ADFUtil.setEL("#{pageFlowScope.prosecutionUnderKolyea}", 1);
                ADFUtil.setEL("#{sessionScope.prosecutionUnderKolyea}", 1);
            }
            OperationBinding oper2 = getBindings().getOperationBinding("getProsecutionUnderKolyea");
            oper2.getParamsMap().put("kolyeaId", loggedInEntityId);
            Object result = oper2.execute();

            if (result == null)
                result = new ArrayList();

            ArrayList<Integer> prosecutionUnderKolyea = (ArrayList<Integer>) result;

            boolean underKolyea = false;
            for (int i = 0; prosecutionUnderKolyea.size() > i; i++) {
                if (prosecutionUnderKolyea.get(i) == entity) {
                    underKolyea = true;
                    ADFUtil.setEL("#{pageFlowScope.prosecutionUnderKolyea}", 1);
                    ADFUtil.setEL("#{sessionScope.prosecutionUnderKolyea}", 1);
                }
            }
            if (entity != loggedInEntityId && !underKolyea) {
                // log search procedure log
                String procedureDate = searchInventoryRow.getAttribute("SearchProcedureDate").toString();
                insertIntoExcProceduresLogView(0, businessCaseId, userId, entity, 0, loggedInEntityId,
                                               new BigDecimal(0), sequenceNumber, 1, 0, repeatedFlag, sequenceYear,
                                               procedureDate);
            }
        } else {
            ADFUtil.setEL("#{pageFlowScope.businessCaseId}", null);
            JSFUtils.showInfCaseNotFound();
        }
        getRuleID();
        return null;
    }

    private int getEntityIdFromBusinseCase(long bcid) {
        OperationBinding oper = getBindings().getOperationBinding("getEntityIdFromBusinseCase");
        oper.getParamsMap().put("bcid", bcid);
        int result = Integer.parseInt(oper.execute() + "");
        return result;
    }

    private int getCaseSchema(long businessCaseId) {
        OperationBinding oper = getBindings().getOperationBinding("validateCaseCriminal");
        oper.getParamsMap().put("businessCaseId", businessCaseId);
        int caseSchema = Integer.parseInt(oper.execute() + "");
        System.err.println("caseSchema  is -------------" + caseSchema);
        return caseSchema;
    }

    private String getGozayFromKolyNumber(Object caseNumber, Object caseYear, Object businessCaseId,
                                          Object entityKolya) {
        OperationBinding oper = getBindings().getOperationBinding("getGozeyFromKoly");
        oper.getParamsMap().put("caseNumber", getBigDecimalValueFromObject(caseNumber));
        oper.getParamsMap().put("caseYear", getBigDecimalValueFromObject(caseYear));
        oper.getParamsMap().put("businessCaseId", getBigDecimalValueFromObject(businessCaseId));
        oper.getParamsMap().put("entityKolya", getBigDecimalValueFromObject(entityKolya));
        Object result = oper.execute();
        return result != null ? result.toString() : null;
    }

    private Object insertIntoExcProceduresLogView(int actionTypeId, long businessCaseId, int userId, int entityId,
                                                  int excClaimId, int loggedInEntityId, BigDecimal procId,
                                                  int sequenceNumber, int modeType, int folderTypeId,
                                                  String repeatedFlag, int sequenceYear, String procedureDate) {
        OperationBinding oper3 = getBindings().getOperationBinding("insertIntoExcProceduresLogView");
        oper3.getParamsMap().put("actionTypeId", actionTypeId);
        oper3.getParamsMap().put("businessCaseId", businessCaseId);
        oper3.getParamsMap().put("userId", userId);
        oper3.getParamsMap().put("entityId", entityId);
        oper3.getParamsMap().put("excClaimId", excClaimId);
        oper3.getParamsMap().put("loggedInEntityId", loggedInEntityId);
        oper3.getParamsMap().put("procId", procId);
        oper3.getParamsMap().put("sequenceNumber", sequenceNumber);
        oper3.getParamsMap().put("modeType", modeType);
        oper3.getParamsMap().put("folderTypeId", folderTypeId);
        oper3.getParamsMap().put("repeatedFlag", repeatedFlag);
        oper3.getParamsMap().put("sequenceYear", sequenceYear);
        oper3.getParamsMap().put("procedureDate", procedureDate);
        Object result3 = oper3.execute();
        return result3;
    }


    /* OTHMAN */


    public void resetSearhResults(ValueChangeEvent valueChangeEvent) {
        System.out.println("vvv  " + valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.SearchType.inputValue}",
                                    Integer.parseInt(valueChangeEvent.getNewValue() + ""));
        JSFUtils.addToSession("pType", Integer.parseInt(valueChangeEvent.getNewValue() + ""));
        JSFUtils.refreshComponent("sor1");
        ViewObject view1 = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        view1.executeEmptyRowSet();
        ViewObject view3 = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        view3.executeEmptyRowSet();

        ADFUtil.setEL("#{pageFlowScope.postbonde}", null);
        ADFUtil.setEL("#{pageFlowScope.showAppeal}", null);
    }

    public void upload(ValueChangeEvent valueChangeEvent) {
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();

            String documentId = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, uf.getFilename()).get("dID");
            ADFUtil.setEL("#{pageFlowScope.documentId}", documentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getCaseSchemaId(int actionTypeId) {
        BigDecimal caseSchemaId = new BigDecimal(0);
        if (actionTypeId == 1) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??
            caseSchemaId = new BigDecimal(4425);
        } else if (actionTypeId == 2) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(3424);
        } else if (actionTypeId == 5) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(3104);
        } else if (actionTypeId == 7 || actionTypeId == 77) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5016);
        } else if (actionTypeId == 8) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5002);
        } else if (actionTypeId == 9) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5003);
        } else if (actionTypeId == 10) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5004);
        } else if (actionTypeId == 11) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5005);
        } else if (actionTypeId == 12) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(3606);
        } else if (actionTypeId == 13) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½?? ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5006);
        } else if (actionTypeId == 14 || actionTypeId == 168 ) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(4480);
        } else if (actionTypeId == 15) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5007);
        } else if (actionTypeId == 16) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5008);
        } else if (actionTypeId == 25) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5009);
        } else if (actionTypeId == 20) { //Tan be el nakd
            if (ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null) {
                caseSchemaId = new BigDecimal(5001); // tan be elnakd gnayat
            } else {
                caseSchemaId = new BigDecimal(4183); // tan be elnakd Gonah
            }
        } else if (actionTypeId == 21) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5010);
        } else if (actionTypeId == 35) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(6005);
        } else if (actionTypeId == 22) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5011);
        } else if (actionTypeId == 23) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5012);
        } else if (actionTypeId == 24) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(3785);
        } else if (actionTypeId == 36) { //
                       caseSchemaId = new BigDecimal(9999);
        } else if (actionTypeId == 26) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½?? ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5013);
        } else if (actionTypeId == 27) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(5014);
        } else if (actionTypeId == 28) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(3043);
        } else if (actionTypeId == 29) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½??
            caseSchemaId = new BigDecimal(5015000);
        } else if (actionTypeId == 30) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(6017000);
        } else if (actionTypeId == 31) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(6019000);
        } else if (actionTypeId == 32) { // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
            caseSchemaId = new BigDecimal(6020000);
        } else if (actionTypeId == 33) { // edit
            caseSchemaId = new BigDecimal(6021000);
        }
        return caseSchemaId;
    }

    public void reUploadReport(ValueChangeEvent valueChangeEvent) {
        try {

            if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType") == null) {
                JSFUtils.addFacesErrorMessage("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u0645\u0644\u0641");
                return;
            }


            int typeId = Integer.parseInt(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType").toString());
            int actionTypeId = 0;
            if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ActsTypeId") != null)
                actionTypeId =
                    Integer.parseInt(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ActsTypeId").toString());
            if (actionTypeId == 20) {
                //                if(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ReasonDate") == null){
                //                    JSFUtils.addFacesErrorMessage("\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u062A\u0627\u0631\u064A\u062E \u0627\u064A\u062F\u0627\u0639 \u0627\u0633\u0628\u0627\u0628 \u0627\u0644\u0637\u0639\u0646 \u0642\u0628\u0644 \u0627\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0637\u0639\u0646");
                //                    return ;
                //                }
                if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ReasonDate") != null) {
                    OperationBinding ob = getBindings().getOperationBinding("updateReasonDateExcProcedure");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date =
                        formatter.format(convertFromObjectToUtilDate(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR,
                                                                                       "ReasonDate")));
                    BigDecimal partyId =
                        new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId").toString());
                    String partyName = getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyName").toString();
                    int prosecutionPartial = Integer.parseInt(JSFUtils.getFromPageFlowScope("entity").toString());
                    BigDecimal businessCaseId =
                        new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.businessCaseId}").toString());

                    ob.getParamsMap().put("reasonDate", date);
                    ob.getParamsMap().put("businessCaseId", businessCaseId);
                    ob.getParamsMap().put("partyId", partyId);
                    ob.getParamsMap().put("entityId", prosecutionPartial);
                    ob.getParamsMap().put("partyName", partyName);
                    ob.execute();
                }
            }
            int entityId = getIntValueFromObject(getUserInfo().getEntityId());
            int userId = getIntValueFromObject(getUserInfo().getUserId());
            int procedureId = getIntValueFromObject(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "Id"));
            BigDecimal caseSchemaId = null;

            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(uf.getInputStream(), 1, 1, 1, 1, uf.getFilename());
            String dDocName = do_.get("dDocName");
            String documentId = do_.get("dID");
            uploadAttaches(procedureId, typeId, uf.getFilename(), documentId, dDocName, new BigDecimal(5600));

            int logId = getProcedureLogDocId(procedureId);
            updateProcedureLog(logId, documentId);
            if (invals(actionTypeId, 25, 26, 27)) {
                updateProcedureLogDocuments(logId, documentId, typeId);
            }
            saveDataWithMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType", null);
        setAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileUpload", null);

    }


    public void reUploadReport2(ValueChangeEvent valueChangeEvent) {
        try {

            if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType") == null) {
                JSFUtils.addFacesErrorMessage("\u064A\u062C\u0628 \u0627\u062F\u062E\u0627\u0644 \u0646\u0648\u0639 \u0627\u0644\u0645\u0644\u0641");
                return;
            }


            int typeId = Integer.parseInt(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType").toString());
            int actionTypeId = 0;
            if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ActsTypeId") != null)
                actionTypeId =
                    Integer.parseInt(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ActsTypeId").toString());
            if (actionTypeId == 20) {
                //                if(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ReasonDate") == null){
                //                    JSFUtils.addFacesErrorMessage("\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u062A\u0627\u0631\u064A\u062E \u0627\u064A\u062F\u0627\u0639 \u0627\u0633\u0628\u0627\u0628 \u0627\u0644\u0637\u0639\u0646 \u0642\u0628\u0644 \u0627\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0637\u0639\u0646");
                //                    return ;
                //                }
                if (getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "ReasonDate") != null) {
                    OperationBinding ob = getBindings().getOperationBinding("updateReasonDateExcProcedure");
                    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date =
                        formatter.format(convertFromObjectToUtilDate(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR,
                                                                                       "ReasonDate")));
                    BigDecimal partyId =
                        new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId").toString());
                    String partyName = getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyName").toString();
                    int prosecutionPartial = Integer.parseInt(JSFUtils.getFromPageFlowScope("entity").toString());
                    BigDecimal businessCaseId =
                        new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.businessCaseId}").toString());

                    ob.getParamsMap().put("reasonDate", date);
                    ob.getParamsMap().put("businessCaseId", businessCaseId);
                    ob.getParamsMap().put("partyId", partyId);
                    ob.getParamsMap().put("entityId", prosecutionPartial);
                    ob.getParamsMap().put("partyName", partyName);
                    ob.execute();
                }
            }
            int entityId = getIntValueFromObject(getUserInfo().getEntityId());
            int userId = getIntValueFromObject(getUserInfo().getUserId());
            int procedureId = getIntValueFromObject(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "Id"));
            int newReservationNumber =
                getIntValueFromObject(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "NewReservationId"));
            BigDecimal caseSchemaId = null;

            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(uf.getInputStream(), 1, 1, 1, 1, uf.getFilename());
            String dDocName = do_.get("dDocName");
            String documentId = do_.get("dID");
            uploadFileAfterProcedure(procedureId, documentId, dDocName, uf.getFilename(), new BigDecimal(typeId),
                                     new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceId") + ""),
                                     new BigDecimal(5600), new BigDecimal(newReservationNumber));

            int logId = getProcedureLogDocId(procedureId);
            updateProcedureLog(logId, documentId);
            if (invals(actionTypeId, 25, 26, 27)) {
                updateProcedureLogDocuments(logId, documentId, typeId);
            }
            saveDataWithMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileType", null);
        setAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "FileUpload", null);

        int newResNumber = getIntValueFromObject(getAttributeValue(REUPLOAD_FILE_TYPES_ITERATOR, "NewReservationId"));

        OperationBinding op = getBindings().getOperationBinding("deleteSentToCourt");
        op.getParamsMap().put("resId", newResNumber);
        op.execute();

    }

    private void updateProcedureLog(int id, String documentId) {
        OperationBinding ob = getBindings().getOperationBinding("updateProcedureLog");
        ob.getParamsMap().put("id", id);
        ob.getParamsMap().put("documentId", documentId);
        Object result = ob.execute();

    }

    private int getProcedureLogDocId(int procedureId) {
        OperationBinding ob = getBindings().getOperationBinding("getProcedureLogDocId");
        ob.getParamsMap().put("procedureId", procedureId);
        Object result = ob.execute();
        return getIntValueFromObject(result);
    }

    public void uploadReport(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //        System.err.println("vals = " + outVisible.getValue());
        if (outVisible.getValue() != null) {
            try {
                uploadReportFile(valueChangeEvent);
                saveMessage();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                outVisible.setValue(null);
            }
        }
    }

    private void updateCriminalJusticeFilesByProc(String did, String fileName, String docName, int typeId,
                                                  String procId, String partyId) {
        OperationBinding bindingId = ADFUtil.findOperation("updateCriminalJusticeFilesByProc");
        bindingId.getParamsMap().put("did", did);
        bindingId.getParamsMap().put("fileName", fileName);
        bindingId.getParamsMap().put("docName", docName);
        bindingId.getParamsMap().put("typeId", typeId);
        bindingId.getParamsMap().put("procId", procId);
        bindingId.getParamsMap().put("partyId", partyId);
        bindingId.execute();
    }

    private void uploadReportFile(ValueChangeEvent valueChangeEvent) throws Exception {
        int typeId = Integer.parseInt(getAttributeValue(FILE_TYPES_ITERATOR, "Id").toString());
        //from java script
        typeId = Integer.parseInt(outVisible.getValue().toString());
        outVisible.setValue(null);
        int actionTypeId = 0;
        if (getAttributeValue(ACTION_ITERATOR, "ActionTypeId") != null)
            actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
        // I commented this lines becase it was update already file
        if ((actionTypeId == 1 && typeId == 21) || (actionTypeId == 2 && typeId == 15) ||
            (actionTypeId == 8 && typeId == 15)) {
            int logId = 0;
            if (ADFUtil.evaluateEL("#{pageFlowScope.procedureLogId}") != null) {
                logId = Integer.parseInt(ADFUtil.evaluateEL("#{pageFlowScope.procedureLogId}").toString());
            }
            DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(uf.getInputStream(), 1, 1, 1, 1, uf.getFilename());
            String dDocName = do_.get("dDocName");
            String documentId = do_.get("dID");
            uploadAttaches(JSFUtils.getFromPageFlowScope("procedureId"), typeId, uf.getFilename(), documentId, dDocName,
                           getCaseSchemaId(actionTypeId));
            updateProcedureLogDocuments(logId, documentId, typeId);
            updateCriminalJusticeFilesByProc(documentId, uf.getFilename(), dDocName, typeId,
                                             JSFUtils.getFromPageFlowScope("procedureId") + "",
                                             getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId") + "");
        } else {
            uploadDocumntTOUCM(uf.getInputStream(), uf.getFilename(), typeId, getCaseSchemaId(actionTypeId));
        }
    }

    private String nameOfReport(int typeId) {
        int capacityTypeId =
            Integer.parseInt(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") == null ||
                             "".equalsIgnoreCase(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") + "") ?
                             "1" : ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}").toString());
        String nameOfReport = "";
        if (typeId == 15 || typeId == 30) {

            if (typeId == 30)
                JSFUtils.addToPageFlowScope("AbroadFlag", "Y");
            else
                JSFUtils.addToPageFlowScope("AbroadFlag", null);

            if (capacityTypeId == 1) {
                nameOfReport = "Person";
            } else if (capacityTypeId == 2) {
                nameOfReport = "Authorization";
            }
        } else if (typeId == 21 || typeId == 31) {
            if (typeId == 31)
                JSFUtils.addToPageFlowScope("AbroadFlag", "Y");
            else
                JSFUtils.addToPageFlowScope("AbroadFlag", null);

            nameOfReport = "Appeal";
        } else if (typeId == 16) {
            nameOfReport = "ExecutionReport26";
        } else if (typeId == 19) {
            nameOfReport = "AppealJudgement";
        } else if (typeId == 22) {
            nameOfReport = "AppealAppeal";
        } else if (typeId == 27) {
            nameOfReport = "ExecutionReport25";
        } else if (typeId == 23 || typeId == 32) {
            if (typeId == 32)
                JSFUtils.addToPageFlowScope("AbroadFlag", "Y");
            else
                JSFUtils.addToPageFlowScope("AbroadFlag", null);

            nameOfReport = "Criminal";
        } else if (typeId == 25) {
            nameOfReport = "AppealCriminal";
        } else if (typeId == 44) {
            nameOfReport = "Cassation";
        }

        return nameOfReport;
    }

    public String PrintReportAction() {

        if (outVisible.getValue() != null) {

            int typeId = Integer.parseInt(getAttributeValue(FILE_TYPES_ITERATOR, "Id").toString());
            //from java script
            typeId = Integer.parseInt(outVisible.getValue().toString());
            outVisible.setValue(null);
            String nameOfReport = nameOfReport(typeId);


                if (typeId == 2)
                    nameOfReport = "Authorization";
                else if (typeId == 1)
                    nameOfReport = "Appeal";
                else if (typeId == 9)
                    nameOfReport = "AppealAuthorization";

            String pathReport = "";
            if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
            pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");

            try {
                super.print(pathReport, nameOfReport, "procedures", nameOfReport);
            } catch (Exception e) {
                System.err.println("Error Print Report " + e.getMessage());
            }

        }
        return null;
    }

    public String PrintReport() {
            
            System.out.println(((RichLink) JSFUtils.findComponentInRoot("b110")).getShortDesc());
            System.out.println("outVisible.getValue()    is" + outVisible.getValue());

            int flag = Integer.parseInt(((RichLink) JSFUtils.findComponentInRoot("b110")).getShortDesc() + "");
            System.out.println("flag   is " + flag);
            System.out.println("ActsTypeId   is " + ADFUtil.evaluateEL("#{bindings.ActsTypeId.inputValue}"));
            System.out.println("ActsTypeId222   is " + ADFUtil.evaluateEL("#{sessionScope.actTypeId}"));
            if (outVisible.getValue() != null) {
                System.out.println("$$$$$$222$$$$$$" +
                                   ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().getRowCount());
                        
                int capacityTypeId =
                            Integer.parseInt(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") == null ||
                                             "".equalsIgnoreCase(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") + "") ?
                                             "1" : ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}").toString());
                
                System.err.println("capacityTypeId =" + capacityTypeId);
                
                int CapacityTypeId1 =
                            Integer.parseInt(ADFUtil.evaluateEL("#{bindings.CapacityTypeId1.inputValue}") == null ||
                                             "".equalsIgnoreCase(ADFUtil.evaluateEL("#{bindings.CapacityTypeId1.inputValue}") + "") ?
                                             "1" : ADFUtil.evaluateEL("#{bindings.CapacityTypeId1.inputValue}").toString());
                
                
                int VetoStatus1 =
                            Integer.parseInt(ADFUtil.evaluateEL("#{bindings.VetoStatus1.inputValue}") == null ||
                                             "".equalsIgnoreCase(ADFUtil.evaluateEL("#{bindings.VetoStatus1.inputValue}") + "") ?
                                             "0" : ADFUtil.evaluateEL("#{bindings.VetoStatus1.inputValue}").toString());
                
                System.err.println("CapacityTypeId1 =" + CapacityTypeId1);
                System.err.println("VetoStatus1 =" + VetoStatus1);
//                CapacityType
                

                    
                int typeId = 0;
                if (ADFUtil.evaluateEL("#{bindings.ActsTypeId.inputValue}") != null)
                    typeId = flag;
                //                    Integer.parseInt(ADFUtil.evaluateEL("#{row2.ActsTypeId}")+"");
                //from java script
                BigDecimal procedureId = new BigDecimal(0);
                if (ADFUtil.evaluateEL("#{bindings.Id.inputValue}") != null)
                    procedureId = new BigDecimal(ADFUtil.evaluateEL("#{bindings.Id.inputValue}") + "");

                int userId = 0;

                System.err.println("Display REPORT  ::::::::::::::::::::::");
                String pathReport = "";
                if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
                pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
                System.err.println(pathReport + "  Display REPORT  :::::::::::::userInfo.userId is:::::::::" +
                                   ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}"));
                if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
                    userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
                }
                System.err.println("userId=::::::::::::::::::::::" + userId);
                System.err.println("procedureId=::::::::::::::::::::::" +procedureId);
                outVisible.setValue(null);
                String nameOfReport = null;
                    if (typeId == 2)
                        nameOfReport = "Authorization";
                    else if (typeId == 8)
                        nameOfReport = "Authorization";
                    else if (typeId == 1 || typeId == 163  )
                        nameOfReport = "Appeal";
                    else if (typeId == 9 || typeId == 165)
                        nameOfReport = "AppealAuthorization";
                    else if (typeId == 23)
                        nameOfReport = "AppealAuthorization";
                    else if (typeId == 14 || typeId == 168 )
                        nameOfReport = "AuthorizationCriminal";
                    else if (typeId == 146)
                        nameOfReport = "PayRequest";
                else if (typeId == 147)
                    nameOfReport = "VETOREASONS";
                else if (typeId == 20)
                    nameOfReport = "VETO";
                else if (typeId == 5)
                    nameOfReport = "ExecReport9";
                else if (typeId == 69)
                    nameOfReport = "ReinstatelProcedureTemplate";
                else if (typeId == 70)
                    nameOfReport = "ReinstatelProcedureTemplateSecond"; 
                else if (typeId == 152 || typeId == 158 || typeId == 160)
                    nameOfReport = "APPEALVETO";
                    else if ((typeId == 21 || typeId == 35) && CapacityTypeId1==1){
                        if(VetoStatus1 == 2)
                            nameOfReport = "ExecReportPartiesPrison";
                            else
                    nameOfReport = "ExecReportParties";
                    
                    }
                else if ((typeId == 21 || typeId == 35) && CapacityTypeId1==2)
                    nameOfReport = "ExecReProceduresLawyer";
                try {
                    super.print2(pathReport, nameOfReport, "procedures", nameOfReport, procedureId, userId);
                } catch (Exception e) {
                    System.err.println("Error Print Report " + e.getMessage());
                }

            }
            return null;
        }

    private void filterFilesTypes(int actionTypeId) {
        String where = "";
        int namozag = 0;
        if ((Boolean) ADFUtil.evaluateEL("#{pageFlowScope.caseSchema ne null}") ||
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId") + "") == 15) {
            namozag = 16;
        } else {
            namozag = 27;
        }
        //   if (actionTypeId !=  30)
        where = "id in (" + namozag;
        //   else
        //     where = "id in ( ";

        if (actionTypeId == 2 || actionTypeId == 8) {
            where += ",15,30";
        } else if (actionTypeId == 1) {
            where += ",21,31";
        } else if (actionTypeId == 14) {
            where += ",23,32";
        } else if (actionTypeId == 25) {
            where += ",19";
        } else if (actionTypeId == 26) {
            where += ",22";
        } else if (actionTypeId == 27) {
            where += ",25";
        } else if (actionTypeId == 30) {
            int lasproc = lastActionMoardaEstEatrad();
            if (lasproc == 2)
                where += ",15";
            else if (lasproc == 1)
                where += ",21";
            else if (lasproc == 14)
                where += ",23";

            where += ",29";
        } else if (actionTypeId == 20) {
            where += ",45,44";
        }
        where += ")";
        ViewObjectImpl vImp = (ViewObjectImpl) ADFUtil.findIterator(FILE_TYPES_ITERATOR).getViewObject();
        vImp.setWhereClause(where);
        vImp.executeQuery();
    }

    public void uploadPersonalPic(ValueChangeEvent valueChangeEvent) {
        System.out.println("44444");
        uploadCancelAttachment(valueChangeEvent, "IdNumIs");
    }

    public void uploadProcurationPic(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "TawkeelIs");
    }

    private void updateProcedureLogDocuments(int logId, String documentId, int typeId) {
        OperationBinding ob = ADFUtil.findOperation("updateProcedureLogDocuments");
        ob.getParamsMap().put("id", logId);
        ob.getParamsMap().put("documentId", documentId);
        ob.getParamsMap().put("typeId", typeId);
        ob.execute();
    }

    public void uploadPayment(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "SadadIs");
    }

    public void uploadCancelAttachment(ValueChangeEvent valueChangeEvent, String attribute) {
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is;
            is = uf.getInputStream();
            setAttributeValue(ACTION_ITERATOR, attribute, is);
            System.out.println("**** " + getAttributeValue(ACTION_ITERATOR, "IdNumIs"));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void uploadTasdek(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "TasdekIS");
    }

    public void uploadCancel(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "CancelIs");
    }

    public void uploadEdit(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "EditIs");
    }
    public void uploadExecReport9(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "ExecReport");
    }
    public void uploadReinstatelProcedureTemplate(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "ReinstatelProcedureTemplate");
    }
    public void uploadReinstatelProcedureTemplateSecond(ValueChangeEvent valueChangeEvent) {
        uploadCancelAttachment(valueChangeEvent, "ReinstatelProcedureTemplateSecond");
    }

    private void uploadAttaches(Object procedId, int typeId, String fileName, String documentId, String dDocName,
                                BigDecimal caseSchemaId) {
        insertIntoCriminalJusticeFilesDetailsVO(procedId, documentId, dDocName, fileName, new BigDecimal(typeId),
                                                new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceId") +
                                                               ""), caseSchemaId);
    }

    private void insertIntoCriminalJusticeFilesDetailsVO(Object procedId, String did, String Ddocname, String fileName,
                                                         BigDecimal typeId, BigDecimal Parid, BigDecimal caseSchemaId) {
        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        BigDecimal partyId = new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId").toString());
        BigDecimal businessCaseId = new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.businessCaseId}").toString());

        OperationBinding operation = getBindings().getOperationBinding("getBusinessCaseDetailId");
        operation.getParamsMap().put("businessCaseId", new Long(businessCaseId.toString()));

        BigDecimal businessCaseDetailId = (BigDecimal) operation.execute();

        //            am.getBusinessCaseDetailId(new Long(businessCaseId.toString()));
        int userId = 0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }

        int entityId = 0;

        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            entityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            entityId = Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        OperationBinding op = getBindings().getOperationBinding("insertIntoCriminalJusticeFilesDetailsVO");
        op.getParamsMap().put("businessCaseDetailId", businessCaseDetailId);
        op.getParamsMap().put("businessCaseId", businessCaseId);
        op.getParamsMap().put("createdBy", userId);
        op.getParamsMap().put("did", did);
        op.getParamsMap().put("Ddocname", Ddocname);
        op.getParamsMap().put("entityId", entityId);
        op.getParamsMap().put("partyId", partyId);
        op.getParamsMap().put("fileName", fileName);
        op.getParamsMap().put("typeId", typeId);
        op.getParamsMap().put("Parid", Parid);
        op.getParamsMap().put("caseSchemaId", caseSchemaId);
        op.getParamsMap().put("procedureId", getBigDecimalValueFromObject(procedId));
        /** Added by M.Salah **/
        //        System.err.println("salah-resId-egraa_hasr-bean: " + JSFUtils.getFromPageFlowScope("resPartyId"));
        op.getParamsMap().put("resId", JSFUtils.getFromPageFlowScope("resPartyId"));
        op.execute();
    }


    private void uploadFileAfterProcedure(int procedId, String did, String Ddocname, String fileName, BigDecimal typeId,
                                          BigDecimal Parid, BigDecimal caseSchemaId, BigDecimal reservationId) {
        //        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        //        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();

        BigDecimal partyId = new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PartyId").toString());
        BigDecimal businessCaseId = new BigDecimal(ADFUtil.evaluateEL("#{pageFlowScope.businessCaseId}").toString());
        OperationBinding operation = getBindings().getOperationBinding("getBusinessCaseDetailId");
        operation.getParamsMap().put("businessCaseId", new Long(businessCaseId.toString()));

        BigDecimal businessCaseDetailId = (BigDecimal) operation.execute();

        //            am.getBusinessCaseDetailId(new Long(businessCaseId.toString()));

        int userId = 0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }

        int entityId = 0;

        if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
            ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
            entityId =
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
        } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                   3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
            entityId = Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
        }

        OperationBinding op = getBindings().getOperationBinding("insertIntoCriminalJusticeFilesDetailsVO");
        op.getParamsMap().put("businessCaseDetailId", businessCaseDetailId);
        op.getParamsMap().put("businessCaseId", businessCaseId);
        op.getParamsMap().put("createdBy", userId);
        op.getParamsMap().put("did", did);
        op.getParamsMap().put("Ddocname", Ddocname);
        op.getParamsMap().put("entityId", entityId);
        op.getParamsMap().put("partyId", partyId);
        op.getParamsMap().put("fileName", fileName);
        op.getParamsMap().put("typeId", typeId);
        op.getParamsMap().put("Parid", Parid);
        op.getParamsMap().put("caseSchemaId", caseSchemaId);
        op.getParamsMap().put("procedureId", getBigDecimalValueFromObject(procedId));
        System.err.println("reservationId-egraa_hasr-bean: " + reservationId);
        op.getParamsMap().put("resId", reservationId);
        op.execute();
    }

    public void downloadAllAttachedFiles(FacesContext context, OutputStream out) throws IOException  {
        downloadGeneric(ADFUtil.evaluateEL("#{row.Did}").toString(), out);
      }

      public void downloadArrestWarrantsFile(FacesContext context, OutputStream out) throws IOException  {
        downloadGeneric(ADFUtil.evaluateEL("#{row10.Did}").toString(), out);
      }

    private void downloadGeneric(String did, OutputStream out) throws IOException {
          InputStream fis = null;
          try {
              FilesModel content = CjUtils.DownloadContent(did, "");
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

    public void downloadReport(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        //        Number fileDid = (Number)row.getAttribute("DocumentId") ;
        InputStream fis = null;

        if (row.getAttribute("DocumentId") == null) {
            JSFUtils.addFacesErrorMessage("FIle dID is null");
            return;
        }
        try {
            String reportName = "\u062a\u0642\u0631\u064a\u0631 ";
            if (row.getAttribute("ActionTypeName") != null) {
                reportName += row.getAttribute("ActionTypeName").toString();
            }
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("DocumentId").toString(), reportName);
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

    public void downloadAppealRequest(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("AppealRequest").toString(), "");
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

    public void downloadOppositionRequest(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("OppositionRequest").toString(), "");
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

    @SuppressWarnings("oracle.jdeveloper.java.nested-assignment")
    public void downloadCriminalRequest(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("CriminalRequest").toString(), "");
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

    public void downloadReport26(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("ExcProceduresLogView1Iterator").getViewObject();
        Row row = view.getCurrentRow();
        //        Number fileDid = (Number)row.getAttribute("DocumentId") ;
        InputStream fis = null;

        if (row.getAttribute("DocumentId2") == null) {
            JSFUtils.addFacesErrorMessage("FIle dID is null");
            return;
        }

        try {
            String reportName = "\u062a\u0642\u0631\u064a\u0631 ";
            if (row.getAttribute("ActionTypeName") != null) {
                reportName += row.getAttribute("ActionTypeName").toString();
            }
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("DocumentId2").toString(), reportName);
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

    public void download(FacesContext context, OutputStream out) throws IOException {
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(ADFUtil.evaluateEL("#{row3.Did}").toString(), "");

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

    public void downloadReportPrevActions2(FacesContext context, OutputStream out) throws IOException {
        InputStream fis = null;

        if (ADFUtil.evaluateEL("#{pageFlowScope.DocumentId1}") == null) {
            JSFUtils.addFacesErrorMessage("FIle dID is null");
            return;
        }

        try {

            FilesModel content =
                CjUtils.DownloadContent(ADFUtil.evaluateEL("#{pageFlowScope.DocumentId1}").toString(), "");
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

    public void downloadReportPrevActions3(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("PartyProceduresRVO2Iterator").getViewObject();
        Row row = view.getCurrentRow();
        //        Number fileDid = (Number)row.getAttribute("DocumentId") ;
        InputStream fis = null;

        if (row.getAttribute("DocumentId1") == null) {
            JSFUtils.addFacesErrorMessage("FIle dID is null");
            return;
        }
        try {
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("DocumentId1").toString(), "");
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

    public void downloadReportPrevActions4(FacesContext context, OutputStream out) throws IOException {
        ViewObject view = ADFUtil.findIterator("PartyProceduresRVO2Iterator").getViewObject();
        Row row = view.getCurrentRow();
        //        Number fileDid = (Number)row.getAttribute("DocumentId") ;
        InputStream fis = null;
        if (row.getAttribute("DocumentId2") == null) {
            JSFUtils.addFacesErrorMessage("FIle dID is null");
            return;
        }
        try {
            FilesModel content = CjUtils.DownloadContent(row.getAttribute("DocumentId2").toString(), "");
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

    private double getPreviousPaid(int type) {
        double sumPrevVals = 0.0;

        ADFUtil.findIterator("PreviousProceduresRVO3Iterator").setRangeSize(-1);
     ViewObject   searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
       Row searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

        for (Row r : ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getAllRowsInRange()) {
            System.out.println(Integer.parseInt(searchInventoryCaseResultRow.getAttribute("ResPartyId").toString()) +
                               "GGKKHHHTT" + r.getAttribute("ResPartyId"));
            String numberPaied = (String) r.getAttribute("NumberPaid");
            System.out.println("numberPaied  is " + numberPaied);
            if (searchInventoryCaseResultRow.getAttribute("ResPartyId").toString().compareTo(r.getAttribute("ResPartyId").toString()) ==
                0) {
            if (type == 3) {
                    if (("7".equalsIgnoreCase(r.getAttribute("ProcedureType") + "") ||
                         "77".equalsIgnoreCase(r.getAttribute("ProcedureType") + "")) &&
                    "3".equalsIgnoreCase(r.getAttribute("FeesTypeId") + "")) {
                        if (numberPaied != null)
                    sumPrevVals += Double.parseDouble(r.getAttribute("AdvancePaymentAmount") + "");
                }
            } else if (type == 4) {
                    if (("7".equalsIgnoreCase(r.getAttribute("ProcedureType") + "") ||
                         "77".equalsIgnoreCase(r.getAttribute("ProcedureType") + "")) &&
                    "4".equalsIgnoreCase(r.getAttribute("FeesTypeId") + "")) {
                        if (numberPaied != null)
                    sumPrevVals += Double.parseDouble(r.getAttribute("AdvancePaymentAmount") + "");
                }
            } else if (type == 1) {
                    if (("7".equalsIgnoreCase(r.getAttribute("ProcedureType") + "") ||
                         "77".equalsIgnoreCase(r.getAttribute("ProcedureType") + "")) &&
                    "1".equalsIgnoreCase(r.getAttribute("FeesTypeId") + "")) {
                        if (numberPaied != null)
                    sumPrevVals += Double.parseDouble(r.getAttribute("AdvancePaymentAmount") + "");
                }
            } else if (type == 2) {
                if ("7".equalsIgnoreCase(r.getAttribute("ProcedureType") + "") &&
                    "2".equalsIgnoreCase(r.getAttribute("FeesTypeId") + "")) {
                    sumPrevVals += Double.parseDouble(r.getAttribute("AdvancePaymentAmount") + "");
                }
            }
            if (type == 5) {
                    if (("7".equalsIgnoreCase(r.getAttribute("ProcedureType") + "") ||
                         "77".equalsIgnoreCase(r.getAttribute("ProcedureType") + "")) &&
                    "5".equalsIgnoreCase(r.getAttribute("FeesTypeId") + "")) {
                        if (numberPaied != null)
                    sumPrevVals += Double.parseDouble(r.getAttribute("AdvancePaymentAmount") + "");
                }
            }
        }
        }
        System.err.println("sumPrevVals = " + sumPrevVals);
        return sumPrevVals;
    }

    public void feesTypeChangeListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //        System.err.println("valueChangeEvent = " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().executeQuery();
            int resPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());
            //	BigDecimal resId = new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "ReservationId").toString());
            Row actionRow = ADFUtil.findIterator(ACTION_ITERATOR).getViewObject().getCurrentRow();
            actionRow.setAttribute("PaymentAmount", null);
            /*total amount */
            if ("3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                if (invals(getAttributeValue(RESULT_SEARCH_ITERATOR, "ClaimIssueFlag"), 1)) {
                    setAttributeValue(ACTION_ITERATOR, "FeesType", null);
                    setAttributeValue(ACTION_ITERATOR, "PaymentAmount", null);
                    setAttributeValue(ACTION_ITERATOR, "NumberOfPaied", null);
                    setAttributeValue(ACTION_ITERATOR, "PainUpAmount", 0);
                    setAttributeValue(ACTION_ITERATOR, "TotalAmount", 0);
                    showErrorMessageForComponentById("\u0628\u0631\u062C\u0627\u0621 \u0623\u062C\u0631\u0627\u0621 \u0633\u062F\u0627\u062F \u0627\u0644\u0645\u0637\u0627\u0644\u0628\u0629",
                                                     "soc4");
                    return;
                }
            }
            if ("3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString()) ||
                "4".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                if ("3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString()))
                    actionRow.setAttribute("TotalAmount", getTotalPenaltyOrGuranteeAmount(resPartyId, 3));
                if ("4".equalsIgnoreCase(valueChangeEvent.getNewValue().toString()))
                    actionRow.setAttribute("TotalAmount", getTotalPenaltyOrGuranteeAmount(resPartyId, 4));
            } else {
                if  (valueChangeEvent.getNewValue() != null) {
                OperationBinding oper = getBindings().getOperationBinding("getTotalOfPaymentValues");
                oper.getParamsMap().put("resevationId", new BigDecimal(resPartyId));
                oper.getParamsMap().put("typeID", Integer.parseInt(valueChangeEvent.getNewValue().toString()));
                Object val = oper.execute();
                actionRow.setAttribute("TotalAmount",
                                       val != null ? Double.parseDouble(val + "") : Double.parseDouble("0"));
                }
            }
            /* / total amount */
            /* paidUp amount*/
            if ("5".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(5));
            } else if ("1".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(1));
            } else if ("2".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(2));
            } else if ("3".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(3));
            } else if ("4".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(4));
            }
            /* /paidup amount */
            ADFUtil.setEL("#{pageFlowScope.guranteeAmount}", actionRow.getAttribute("TotalAmount"));
            ADFUtil.setEL("#{pageFlowScope.penaltyAmount}", actionRow.getAttribute("TotalAmount"));
            ADFUtil.setEL("#{pageFlowScope.inventoryBean.gharamaYawmeya}", 0);
            setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
            RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
            inp.resetValue();
            RichSelectBooleanCheckbox checkBox = (RichSelectBooleanCheckbox) JSFUtils.findComponentInRoot("sbc4");
            gharamaYawmeyaRemain = 0;
            if ("7".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
                ViewObject view = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO5Iterator").getViewObject();
                view.setWhereClause("RES_PARTY_ID = " + resPartyId);
                view.executeQuery();
                RowSetIterator rsi = view.createRowSetIterator(null);
                int chargeDecisionId = 0;
                while (rsi.hasNext()) {
                    Row chargePartiesRow = rsi.next();
                    chargeDecisionId = Integer.parseInt(chargePartiesRow.getAttribute("ChargeDecisionId").toString());
                }
                view.setWhereClause(null);
                view.executeQuery();

                ViewObject view2 = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO4Iterator").getViewObject();
                view2.setWhereClause("ID = " + chargeDecisionId);
                view2.executeQuery();
                RowSetIterator rsi2 = view2.createRowSetIterator(null);
                java.util.Date penaltyEndDate = null;
                while (rsi2.hasNext()) {
                    Row chargeDecisionsRow = rsi2.next();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (chargeDecisionsRow.getAttribute("PenaltyEndDate") != null) {
                        try {
                            penaltyEndDate = sdf.parse(chargeDecisionsRow.getAttribute("PenaltyEndDate") + "");
                            inp.setValue(DateConversionUtils.convertDateFromUtilToJbo(penaltyEndDate));
                            inp.setReadOnly(true);
                            inp.setVisible(true);
                            checkBox.setDisabled(true);
                            checkBox.setVisible(true);
                            checkBox.setValue(true);
                            System.err.println("disable true");
                            gharamaYawmeyaEndDateCaculation(inp.getValue() + "");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        inp.setReadOnly(false);
                        checkBox.setDisabled(false);
                        System.err.println("disable false");
                    }
                }
                view2.setWhereClause(null);
                view2.executeQuery();
            }
        }
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
        // / Make row current ////
        ADFUtil.setEL("#{pageFlowScope.procedureId}", null);
        ADFUtil.setEL("#{pageFlowScope.actionSaved}", null);
        ADFUtil.findIterator(FILE_TYPES_ITERATOR).getViewObject().executeEmptyRowSet();
        ViewObject view3 = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        view3.setWhereClause(null);
        view3.executeQuery();
        ADFContext.getCurrent().getSessionScope().put("pClaimDeleted", "N");
        //      ViewObject view4 =  ADFUtil.findIterator("ExcClaimsVO5Iterator").getViewObject();
        //        view4.setWhereClause("Exc_Folder_Id  = "+getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderId"));
        //        view4.executeQuery();
        ADFUtil.setEL("#{pageFlowScope.idRport}", null);
        ADFUtil.setEL("#{pageFlowScope.printOppositionReport}", null);
        ADFUtil.setEL("#{pageFlowScope.AppealAppeal}", null);
        ADFUtil.setEL("#{pageFlowScope.printReport26}", null);
        ADFUtil.setEL("#{pageFlowScope.Appeal}", null);
        ADFUtil.setEL("#{sessionScope.actionTypeId}", null);
        ADFUtil.setEL("#{pageFlowScope.DName}", null);
        ADFUtil.setEL("#{pageFlowScope.DID}", null);
        ADFContext.getCurrent().getSessionScope().put("DOCUMENTID", null);
        rulesOrProcedures = 0;
        personalPic = null;
        payment = null;
        procurationPic = null;
        tasdekFile = null;
        cancelFile = null;
        editFile = null;
        execReport9 = null;
        ReinstatelProcedureTemplate = null;
        ReinstatelProcedureTemplateSecond = null;
        setPenaltyAmountValue();
        stPunchmentsValues();
        setAttributeViewValue(PARAMS_ITERATOR, "PartyFName", null);
        ADFUtil.setAttributeValue(ACTION_ITERATOR, "ExecutionFlag",
                                  getAttributeValue(RESULT_SEARCH_ITERATOR, "ExecutionFlag"));

    }

    public void setJudgmentText() {
        Object obImprisonmentYears = getAttributeValue(ACTION_ITERATOR, "ImprisonmentYears");
        Object obImprisonmentMonths = getAttributeValue(ACTION_ITERATOR, "ImprisonmentMonths");
        Object obImprisonmentDays = getAttributeValue(ACTION_ITERATOR, "ImprisonmentDays");
        Object obAttendence = getAttributeValue(RESULT_SEARCH_ITERATOR, "Attendance");
        Object obGuranteeAmount = getAttributeValue(ACTION_ITERATOR, "ImprisonMentGuranteeAmount");
        Object obWorkAmount = getAttributeValue(ACTION_ITERATOR, "WorkAmount");
        Object obPenaltyAmountFixedFlag = getAttributeValue(ACTION_ITERATOR, "PenaltyAmountFixedFlag");
        Object obPenaltyAmount = getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount");
        String judgmentText =
            JSFUtils.getJudementText(JSFUtils.getBigDecimal(obImprisonmentYears),
                                     JSFUtils.getBigDecimal(obImprisonmentMonths),
                                     JSFUtils.getBigDecimal(obImprisonmentDays), JSFUtils.getBigDecimal(obAttendence),
                                     JSFUtils.getBigDecimal(obGuranteeAmount), JSFUtils.getBigDecimal(obWorkAmount),
                                     JSFUtils.getBigDecimal(obPenaltyAmount),
                                     JSFUtils.getBoolean(obPenaltyAmountFixedFlag), false, false, false, false, "",false);

        setAttributeValue(ACTION_ITERATOR, "JudgmentContent", judgmentText);
        //    System.err.println(">>>Belal>> "+judgmentText);
    }

    private void setPenaltyAmountValue() {
        OperationBinding oper = ADFUtil.findOperation("getPenaltyAmount");
        oper.getParamsMap().put("respartyId", getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId") + "");
        Object penaltyResult = oper.execute();
        setAttributeValue(RESULT_SEARCH_ITERATOR, "PenaltyAmount", penaltyResult);
    }

    private void stPunchmentsValues() {

        OperationBinding oper = ADFUtil.findOperation("getPunshment");
        oper.getParamsMap().put("respartyId", getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId") + "");
        String punshment = oper.execute().toString();
        //        System.err.println(punshment);
        if (!"null".equals(punshment) && punshment != null && punshment.contains(",")) {
            String[] vals = punshment.split(",");


            setAttributeViewValue(PARAMS_ITERATOR, "grantee", vals[3]);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonYear", vals[0]);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonMonth", vals[1]);
            setAttributeViewValue(PARAMS_ITERATOR, "inpresonday", vals[2]);
            setAttributeViewValue(PARAMS_ITERATOR, "amount", vals[8]);
            setAttributeViewValue(PARAMS_ITERATOR, "dailyPayment", vals[5]);


            setAttributeValue(ACTION_ITERATOR, "ImprisonmentYears", vals[0]);
            setAttributeValue(ACTION_ITERATOR, "ImprisonmentMonths", vals[1]);
            setAttributeValue(ACTION_ITERATOR, "ImprisonmentDays", vals[2]);
            setAttributeValue(ACTION_ITERATOR, "ImprisonMentGuranteeAmount", vals[3]);
            setAttributeValue(ACTION_ITERATOR, "WorkAmountFlag", vals[4]);
            setAttributeValue(ACTION_ITERATOR, "WorkAmount", vals[5]);
            //            setAttributeValue(ACTION_ITERATOR, "PenaltyDueDate", vals[6] != null ? formatDateWithChar(vals[6]) : null);


            try {
                if (vals[6] != null) {
                    ADFUtil.setEL("#{pageFlowScope.penaltyDueDate}", dateConvert(vals[6]));
                    JSFUtils.addToPageFlowScope("penaltyDueDate", dateConvert(vals[6]));
                    System.out.println(vals[6] + "penaltyDueDate from pageflowscope is " +
                                       JSFUtils.getFromPageFlowScope("penaltyDueDate"));
                    setAttributeValue(ACTION_ITERATOR, "PenaltyDueDate", dateConvert(vals[6]));
                } else if (getAttributeValue(ACTION_ITERATOR, "PenaltyDueDate") != null)
                    ADFUtil.setEL("#{pageFlowScope.penaltyDueDate}",
                                  getAttributeValue(ACTION_ITERATOR, "PenaltyDueDate"));
            } catch (Exception e) {
            }

            setAttributeValue(ACTION_ITERATOR, "PenaltyAmountFixedFlag", vals[7]);
            setAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount", vals[8]);
            setAttributeValue(ACTION_ITERATOR, "IdPrevPunshment", vals[9]);
            setAttributeValue(ACTION_ITERATOR, "IdPrevPartiesPunshment", vals[10]);
            System.err.println("SANIOUR");
            if (vals.length > 11) {
                if (vals[11] != null) {
                    System.err.println("VALS = " + vals[11]);
                    ADFUtil.setEL("#{pageFlowScope.penaltyEndDate}", dateConvert(vals[11]));
                    setAttributeValue(ACTION_ITERATOR, "PenaltyEndDate", vals[11]);
            }
            }
            setAttributeValue(ACTION_ITERATOR, "Year", getAttributeValue(RESULT_SEARCH_ITERATOR, "Year"));
            setAttributeValue(ACTION_ITERATOR, "FolderTypeESS", 6);
            setAttributeValue(ACTION_ITERATOR, "CriminalType", 1);
            setAttributeValue(ACTION_ITERATOR, "RepeatedFlg", "N");
            if ("5".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderType") + "")) {
                setAttributeValue(ACTION_ITERATOR, "FolderGharama", "Y");
            }
        }
    }

    private String formatDateWithChar(Object ob) {
        //        System.err.println("ob = " + ob);
        if (ob == null || "".equalsIgnoreCase(ob + ""))
            return null;

        SimpleDateFormat formatter2 = new SimpleDateFormat("YYY-MMM-DD");
        java.util.Date date2 = null;
        try {
            date2 = formatter2.parse(ob + "");
        } catch (ParseException e) {
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //        System.err.println(formatter.format(date2));
        return formatter.format(date2);
    }

    private double getTotalPenaltyOrGuranteeAmount(int resPartyId, int type) {
        ViewObjectImpl courtBcHearingResParties =
            (ViewObjectImpl) ADFUtil.findIterator("CourtBcHearingResPartiesView1Iterator").getViewObject();
        courtBcHearingResParties.setWhereClause("ID = " + resPartyId);
        courtBcHearingResParties.executeQuery();
        RowSetIterator rsi2 = courtBcHearingResParties.createRowSetIterator(null);

        ViewObjectImpl guranteePenaltyAmountView =
            (ViewObjectImpl) ADFUtil.findIterator("GuranteePenaltyAmountRVO1Iterator").getViewObject();
        guranteePenaltyAmountView.setWhereClause("RES_PARTY_ID = " + resPartyId);
        guranteePenaltyAmountView.executeQuery();
        RowSetIterator rsi = guranteePenaltyAmountView.createRowSetIterator(null);
        double amount = 0.0;
        while (rsi.hasNext()) {
            Row guranteePenaltyAmountRow = rsi.next();
         if (type == 4) {
                amount = Double.parseDouble(guranteePenaltyAmountRow.getAttribute("GuranteeAmount").toString());
            } else if (type == 3) {
                amount = Double.parseDouble(guranteePenaltyAmountRow.getAttribute("PenaltyAmount").toString());
            }
        }
        while (rsi2.hasNext()) {
            Row courtBcHearingResPartiesRow = rsi2.next();
            if (type == 4) {
                System.out.println("*-*-*-" + courtBcHearingResPartiesRow.getAttribute("Guarantee"));
                if (courtBcHearingResPartiesRow.getAttribute("Guarantee") != null)
                amount = Double.parseDouble(courtBcHearingResPartiesRow.getAttribute("Guarantee").toString());
            }
        }
        return amount;
    }

    public void payMentAmountListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (valueChangeEvent.getNewValue() != null) {
            Row actionRow = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject().getCurrentRow();
            double totalVal =
                actionRow.getAttribute("TotalAmount") != null ?
                Double.parseDouble(actionRow.getAttribute("TotalAmount") + "") : Double.parseDouble("0");
            double paidUpAmount =
                actionRow.getAttribute("PainUpAmount") != null ?
                Double.parseDouble(actionRow.getAttribute("PainUpAmount") + "") : Double.parseDouble("0");

            if ((Double.parseDouble(valueChangeEvent.getNewValue().toString()) + paidUpAmount) > totalVal) {
                setAttributeValue(ACTION_ITERATOR, "PaymentAmount", null);
                //        actionRow.setAttribute("PaymentAmount", null);
                showErrorMessageForComponentById("\u0627\u0644\u0642\u064A\u0645\u0629 \u0623\u0643\u0628\u0631 \u0645\u0646 \u0627\u0644\u0645\u0637\u0644\u0648\u0628 \u0633\u062F\u0627\u062F\u0647",
                                                 "it19");
                return;
            }
        }
    }

    /// ???????
    public void userRoles() {

        System.err.println("userRoles   ---------   userRoles-          -----------   userRoles");
        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
        ViewObject view = am.getExcProceduresLogView1();
        view.executeEmptyRowSet();
        ViewObject secUserFunctionsView = am.getSecUserFunctionsRVO1();
        RowSetIterator rsi = secUserFunctionsView.createRowSetIterator(null);
        int entityDegree = 39; //Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.entityDegree}").toString());
        int userRole = 0;
        while (rsi.hasNext()) {
            Row secUserFunctionsRow = rsi.next();
            int role = Integer.parseInt(secUserFunctionsRow.getAttribute("FunctionId").toString());
            if (role == 2961) { // locale user
                ADFUtil.setEL("#{pageFlowScope.showProcedures}", role);
            }
            if (role == 2962) { // outter user
                ADFUtil.setEL("#{pageFlowScope.insertProceduresFromAll}", role);
            }
        }
        if (entityDegree == 39 && userRole == 0) {
            ViewObjectImpl securiedEntitiesView = am.getSecuriedEntities1();
            ViewCriteria vc = securiedEntitiesView.getViewCriteria("SecuriedEntitiesCriteria_Koleyaa");
            securiedEntitiesView.applyViewCriteria(vc);
            securiedEntitiesView.executeQuery();
            Row securiedEntitiesRow = securiedEntitiesView.first();
            if (securiedEntitiesRow != null) {
            }
        }
        if (entityDegree == 3 && userRole == 0) {
            ViewObjectImpl securiedEntitiesPartialView = am.getSecuriedEntities2();
            ViewCriteria vc2 = securiedEntitiesPartialView.getViewCriteria("SecuriedEntitiesCriteria_Partial");
            securiedEntitiesPartialView.applyViewCriteria(vc2);
            securiedEntitiesPartialView.executeQuery();
            Row securiedEntitiesParialRow = securiedEntitiesPartialView.first();

            if (securiedEntitiesParialRow != null) {
            }
        }
    }

    // rulesorProcedures valueChangeListener
    public void rulesOrProceduresValueChangeListener(ValueChangeEvent valueChangeEvent) {
      this.setRefreshComp(true);
        System.out.println(valueChangeEvent.getNewValue() + "eeeeeeeeeeee");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if (!"".equalsIgnoreCase(valueChangeEvent.getNewValue() + "")) {
            if ("1".equalsIgnoreCase(valueChangeEvent.getNewValue() + "")) {
            } else if ("2".equalsIgnoreCase(valueChangeEvent.getNewValue() + "") ||
                       "1".equalsIgnoreCase(valueChangeEvent.getNewValue() + "")) {
                ViewObject view2 = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
                Row row2 = view2.getCurrentRow();
                System.out.println("QQQQQQQQQ" + row2.getAttribute("HearingDate"));
                ADFUtil.setEL("#{sessionScope.hearingDateId}", row2.getAttribute("HearingDate"));
            }
            setAttributeValue(ACTION_ITERATOR, "degreeIdBind", getParamUsed()[1]);
            if (getParamUsed()[1] != null && "1".equalsIgnoreCase(getParamUsed()[1] + "")) {
                setAttributeValue(ACTION_ITERATOR, "FirstDegree", 1);
            } else {
                setAttributeValue(ACTION_ITERATOR, "FirstDegree", null);
            }
            setAttributeValue(ACTION_ITERATOR, "CapacityTypeId", 1);
        }
        setAttributeValue(ACTION_ITERATOR, "CapacityTypeId", 1);
    }

    public String saveAttachements() {
        saveDataWithMessage();
        return null;
    }

    //
    private Object[] getDataFromPreviousProcedures(Object degree) {
        Object arr[] = new Object[5];
        ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO2Iterator").getViewObject();
        view2.executeQuery();
        if (degree != null)
            if (ADFUtil.findIterator("PreviousProceduresRVO2Iterator").getEstimatedRowCount() > 0) {
                view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,24) and PROCEDURE_TYPE = '" + degree +
                                     "' and TO_Date(HEARING_DATE , 'yyyy-mm-dd') >  TO_Date(sysdate , 'yyyy-mm-dd')");
                view2.setOrderByClause("ID desc");
                view2.executeQuery();
                if (view2.getRowCount() > 0) {
                    arr[0] = view2.first().getAttribute("HearingDateId");
                    arr[1] = view2.first().getAttribute("DateEntReservation");
                }
            }
        view2.setWhereClause(null);
        view2.executeQuery();
        return arr;
    }

    public void restMerge() {
            this.getMessBind().setRendered(false);
     ViewObject vo =   ADFUtil.findIterator("FolderROVO1Iterator").getViewObject();

       Row rw = vo.getCurrentRow();
            rw.setAttribute("Seq", "");
            rw.setAttribute("Year", "");
            rw.setAttribute("CrimType", new BigDecimal(1));
            rw.setAttribute("FoldType", "");
            rw.setAttribute("RepeatFlag", "N");
            rw.setAttribute("FoldId", "");
            rw.setAttribute("Status",  new BigDecimal(0));
            JSFUtils.setExpressionValue("#{bindings.Seq.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Year2.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.CrimType.inputValue}", new BigDecimal(1));
            JSFUtils.setExpressionValue("#{bindings.FoldType.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.RepeatFlag.inputValue}", "N");
            JSFUtils.setExpressionValue("#{bindings.FoldId.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Status.inputValue}", new BigDecimal(0));


            JSFUtils.refreshComponent("pg14");
            JSFUtils.refreshComponent("pfl2");
            JSFUtils.refreshComponent("it31");

        }

    @SuppressWarnings("unchecked")
    public void getRuleID() {


        if (ADFContext.getCurrent().getSessionScope().get("RDF").toString().equalsIgnoreCase("Y")) {

            ADFContext.getCurrent().getSessionScope().put("ruleId", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleNum", new BigDecimal(1));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId1", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId1", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId2", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId2", new BigDecimal(0));

            ADFContext.getCurrent().getSessionScope().put("ruleId3", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId3", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId4", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId4", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId5", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId5", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId6", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId6", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId7", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId7", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId8", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId8", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId9", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId9", new BigDecimal(0));

            ADFContext.getCurrent().getSessionScope().put("ruleId10", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId10", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId11", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId11", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId12", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId12", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId13", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId13", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId14", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId14", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId15", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId15", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId16", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId16", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId17", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId17", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId18", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId18", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleId19", new BigDecimal(0));
            ADFContext.getCurrent().getSessionScope().put("ruleEntId19", new BigDecimal(0));
            
            ADFContext.getCurrent().getSessionScope().put("ruleNum", new BigDecimal(1));
            ADFContext.getCurrent().getSessionScope().put("ruleEnt",
                                                          JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            ADFContext.getCurrent().getSessionScope().put("ruleEntt",
                                                          JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
            ViewObject object = ADFUtil.findIterator("DistributionRulesROVO1Iterator").getViewObject();
       object.executeQuery();
       RowSetIterator rws = object.createRowSetIterator(null);
            System.out.println("#########" + ADFContext.getCurrent().getSessionScope().get("hearingDateId"));
            if (ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject().getRowCount() > 0) {
       int entityId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "OriginEntity").toString());
                System.out.println(" OriginEntity  is   ----" + entityId);
       String bcId =  (getAttributeValue(RESULT_SEARCH_ITERATOR, "CaseNumber").toString());
       String bcType =  (getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId").toString());
                System.out.println(JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") +
                                   "entityId    -----------" + entityId+ ":bcType"+bcType);
              //  System.out.println(bcId.substring(bcId.length() - 2) + "bcId    --hearingDateId---------" + bcId);
                Vector ruleID = new Vector();
                Vector ruleEntID = new Vector();
       while (rws.hasNext()) {
          Row rw = rws.next();
          String rule = ((String) rw.getAttribute("Rule")).split("WHERE")[1];
       String val = (String) rw.getAttribute("RuleValues");


                    String[] vals = val.split(",");
                    String[] rules = rule.split("\\?");
                    Vector v = new Vector();
                    Vector vv = new Vector();
                    Vector vvv = new Vector();
                    for (int x = 0; x < vals.length; x++) {//
                        System.out.println(rules[x] + "**aaaaaa**" + rules[x].contains("ORIGIN_ENTITY"));
                        if (rules[x].contains("ORIGIN_ENTITY"))
           v.add(vals[x]);
            else  if (rules[x].contains("CASE_TYPE"))
           vvv.add(vals[x]);
          else
               vv.add(vals[x]);

           }
       System.err.println(v.size());
           
           if (vvv.size() > 0 && vvv.indexOf(bcType + "") > -1){
           
                    if (v.size() > 0) {
                        System.err.println(v.get(0) + "---**ccccc**---" + (v.indexOf(entityId + "") > -1));
                        if (v.indexOf(entityId + "") > -1)

                  {
                            System.err.println(vv.size() + "---**8888888**---" +
                                               vv.indexOf(bcId.substring(bcId.length() - 1) + ""));
                            if (vv.size() > 0) {
                                if (bcId.length() > 0)
                                    if (vv.indexOf(bcId.substring(bcId.length() - 1) + "") > -1) {


                                      BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                      BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                      ruleEntID.add(ruleEntId);
                                      ruleID.add(ruleId);
                                    //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                        System.out.println("ruleId    -----------" + ruleId);
                                //  break;
                                  }

                          }
                            if (vv.size() > 1) {
                                if (bcId.length() > 1)
                                    if (vv.indexOf(bcId.substring(bcId.length() - 2) + "") > -1) {


                                      BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                      BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                      ruleEntID.add(ruleEntId);
                                      ruleID.add(ruleId);
                                    //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                        System.out.println("ruleId    -----------" + ruleId);
                                //  break;
                                  }

                          }
                            if (vv.size() > 2) {
                                if (bcId.length() > 2)
                                    if (vv.indexOf(bcId.substring(bcId.length() - 3) + "") > -1) {


                                      BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                      BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                      ruleEntID.add(ruleEntId);
                                      ruleID.add(ruleId);
                                    //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                        System.out.println("ruleId    -----------" + ruleId);
                                //  break;
                                  }

                          }
                            if (vv.size() > 3) {
                                if (bcId.length() > 3)
                                    if (vv.indexOf(bcId.substring(bcId.length() - 4) + "") > -1) {


                                      BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                      BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                      ruleEntID.add(ruleEntId);
                                      ruleID.add(ruleId);
                                    //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                        System.out.println("ruleId    -----------" + ruleId);
                                //  break;
                                  }

                          }
                            
                          if (vv.size() > 4) {
                              if (bcId.length() > 4)
                                  if (vv.indexOf(bcId.substring(bcId.length() - 5) + "") > -1) {


                                    BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                    BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                    ruleEntID.add(ruleEntId);
                                    ruleID.add(ruleId);
                                  //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                      System.out.println("ruleId    -----------" + ruleId);
                              //  break;
                                }

                          }
                          
                          if (vv.size() > 5) {
                              if (bcId.length() > 5)
                                  if (vv.indexOf(bcId.substring(bcId.length() - 6) + "") > -1) {


                                    BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                                    BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                                    ruleEntID.add(ruleEntId);
                                    ruleID.add(ruleId);
                                  //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                      System.out.println("ruleId    -----------" + ruleId);
                              //  break;
                                }

                          }


                      }
                  
                  
                  
                  
                  
                    }
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    else {

                        if (vv.size() > 0) {
                            if (bcId.length() > 0)
                                if (vv.indexOf(bcId.substring(bcId.length() - 1) + "") > -1) {


                               BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                               BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                               ruleEntID.add(ruleEntId);
                               ruleID.add(ruleId);
                             //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                    System.out.println("ruleId    -----------" + ruleId);
                         //  break;
                           }

                   }
                        if (vv.size() > 1) {
                            if (bcId.length() > 1)
                                if (vv.indexOf(bcId.substring(bcId.length() - 2) + "") > -1) {


                               BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                               BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                               ruleEntID.add(ruleEntId);
                               ruleID.add(ruleId);
                             //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                    System.out.println("ruleId    -----------" + ruleId);
                         //  break;
                           }

                   }
                        if (vv.size() > 2) {
                            if (bcId.length() > 2)
                                if (vv.indexOf(bcId.substring(bcId.length() - 3) + "") > -1) {


                               BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                               BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                               ruleEntID.add(ruleEntId);
                               ruleID.add(ruleId);
                             //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                    System.out.println("ruleId    -----------" + ruleId);
                         //  break;
                           }

                   }
                        if (vv.size() > 3) {
                            if (bcId.length() > 3)
                                if (vv.indexOf(bcId.substring(bcId.length() - 4) + "") > -1) {


                               BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                               BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                               ruleEntID.add(ruleEntId);
                               ruleID.add(ruleId);
                             //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                                    System.out.println("ruleId    -----------" + ruleId);
                         //  break;
                           }

                   }
                        
                   if (vv.size() > 4) {
                       if (bcId.length() > 4)
                           if (vv.indexOf(bcId.substring(bcId.length() - 5) + "") > -1) {


                          BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                          BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                          ruleEntID.add(ruleEntId);
                          ruleID.add(ruleId);
                        //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                               System.out.println("ruleId    -----------" + ruleId);
                    //  break;
                      }

                   }
                        
                   if (vv.size() > 5) {
                       if (bcId.length() > 5)
                           if (vv.indexOf(bcId.substring(bcId.length() - 6) + "") > -1) {


                          BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
                          BigDecimal ruleEntId = (BigDecimal) rw.getAttribute("CourtId");
                          ruleEntID.add(ruleEntId);
                          ruleID.add(ruleId);
                        //  ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
                               System.out.println("ruleId    -----------" + ruleId);
                    //  break;
                      }

                   }

               }
           }

       }


                System.out.println(ruleEntID.size() + "postponedd" +
                                   ADFContext.getCurrent().getSessionScope().get("postponedd"));
                if (ruleEntID.size() > 0) {
                    ADFContext.getCurrent().getSessionScope().put("ruleId", ruleID.get(0));
                    ADFContext.getCurrent().getSessionScope().put("ruleEntId", ruleEntID.get(0));
                    System.out.println(ruleID.get(0) + "---1111111***" + ruleEntID.get(0));
       }
                if (ruleEntID.size() - 1 > 0) {
                    ADFContext.getCurrent().getSessionScope().put("ruleId1", ruleID.get(1));
                    ADFContext.getCurrent().getSessionScope().put("ruleEntId1", ruleEntID.get(1));
                    System.out.println(ruleID.get(1) + "---222222222****" + ruleEntID.get(1));
       }
                if (ruleEntID.size() - 2 > 0) {
                    ADFContext.getCurrent().getSessionScope().put("ruleId2", ruleID.get(2));
                    ADFContext.getCurrent().getSessionScope().put("ruleEntId2", ruleEntID.get(2));
           System.out.println("33333333");
       }
                
                ////////////
                
                if (ruleEntID.size() - 3 > 0) {
                    ADFContext.getCurrent().getSessionScope().put("ruleId3", ruleID.get(3));
                    ADFContext.getCurrent().getSessionScope().put("ruleEntId3", ruleEntID.get(3));
                System.out.println("444444444");
                }
           if (ruleEntID.size() - 4 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId4", ruleID.get(4));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId4", ruleEntID.get(4));
           System.out.println("5555555555");
           }
           if (ruleEntID.size() - 5 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId5", ruleID.get(5));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId5", ruleEntID.get(5));
           System.out.println("666666666");
           }
           if (ruleEntID.size() - 6 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId6", ruleID.get(6));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId6", ruleEntID.get(6));
           System.out.println("77777777777");
           }
           if (ruleEntID.size() - 7 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId7", ruleID.get(7));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId7", ruleEntID.get(7));
           System.out.println("888888888888");
           }
           if (ruleEntID.size() - 8 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId8", ruleID.get(8));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId8", ruleEntID.get(8));
           System.out.println("99999999999");
           }
           if (ruleEntID.size() - 9 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId9", ruleID.get(9));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId9", ruleEntID.get(9));
           System.out.println("100000000000000000");
           }
           
           
           ////////////
           
           if (ruleEntID.size() > 10) {
               ADFContext.getCurrent().getSessionScope().put("ruleId10", ruleID.get(10));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId10", ruleEntID.get(10));
               System.out.println(ruleID.get(0) + "---1111111111111***" + ruleEntID.get(0));
           }
           if (ruleEntID.size() - 1 > 11) {
               ADFContext.getCurrent().getSessionScope().put("ruleId11", ruleID.get(11));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId11", ruleEntID.get(11));
               System.out.println(ruleID.get(1) + "---11222222222****" + ruleEntID.get(1));
           }
           if (ruleEntID.size() - 12 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId12", ruleID.get(12));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId12", ruleEntID.get(12));
           System.out.println("133333333");
           }
           
           
           if (ruleEntID.size() - 13 > 0) {
               ADFContext.getCurrent().getSessionScope().put("ruleId13", ruleID.get(13));
               ADFContext.getCurrent().getSessionScope().put("ruleEntId13", ruleEntID.get(13));
           System.out.println("1444444444");
           }
           if (ruleEntID.size() - 14 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId14", ruleID.get(14));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId14", ruleEntID.get(14));
           System.out.println("15555555555");
           }
           if (ruleEntID.size() - 15 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId15", ruleID.get(15));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId15", ruleEntID.get(15));
           System.out.println("1666666666");
           }
           if (ruleEntID.size() - 16 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId16", ruleID.get(16));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId16", ruleEntID.get(16));
           System.out.println("177777777777");
           }
           if (ruleEntID.size() - 17 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId17", ruleID.get(17));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId17", ruleEntID.get(17));
           System.out.println("1888888888888");
           }
           if (ruleEntID.size() - 18 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId18", ruleID.get(18));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId18", ruleEntID.get(18));
           System.out.println("199999999999");
           }
           if (ruleEntID.size() - 19 > 0) {
           ADFContext.getCurrent().getSessionScope().put("ruleId19", ruleID.get(19));
           ADFContext.getCurrent().getSessionScope().put("ruleEntId19", ruleEntID.get(19));
           System.out.println("200000000000000000");
           }
                
                
     //  ADFUtil.findIterator("CourtCirclesDatesHearingInventory2Iterator").getViewObject().executeQuery();
       System.out.println("888");
       }
        }
   }

    public List onSuggestDates(String decision) {
        ArrayList<SelectItem> selectItems = new ArrayList<SelectItem>();
        System.err.println(JSFUtils.getFromSession("ruleNum") + "+++++ " + decision);
          BindingContext bctx = BindingContext.getCurrent();
          BindingContainer bindings = bctx.getCurrentBindingsEntry();

          OperationBinding setVariable = (OperationBinding) bindings.get("ExecuteWithParams");
        //  ruleNum
        setVariable.getParamsMap().put("RDF", ADFContext.getCurrent().getSessionScope().get("RDF").toString());
          setVariable.getParamsMap().put("degreeIdBind", null);
        setVariable.getParamsMap().put("entId",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleEntId") + ""));
        setVariable.getParamsMap().put("entId1",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleEntId1") +
                                                      ""));
        setVariable.getParamsMap().put("entId2",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleEntId2") +
                                                      ""));
        setVariable.getParamsMap().put("entityId", new BigDecimal(JSFUtils.getFromSession("policeId") + ""));
        setVariable.getParamsMap().put("firstDegree", null);
        setVariable.getParamsMap().put("pnum", new BigDecimal(JSFUtils.getFromSession("ruleNum") + ""));
        setVariable.getParamsMap().put("postponedBind",
                                       ADFContext.getCurrent().getSessionScope().get("postponedd") + "");
        setVariable.getParamsMap().put("postponedBindd",
                                       ADFContext.getCurrent().getSessionScope().get("postponedd").toString());
        setVariable.getParamsMap().put("prevDate", (JSFUtils.getFromSession("hearingDateId") + ""));
        setVariable.getParamsMap().put("ruleId",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleId") + ""));
        setVariable.getParamsMap().put("ruleId1",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleId1") + ""));
        setVariable.getParamsMap().put("ruleId2",
                                       new BigDecimal(ADFContext.getCurrent().getSessionScope().get("ruleId2") + ""));
        setVariable.execute();

        ViewObject vo = ADFUtil.findIterator("CourtCirclesDatesHearingInventory2Iterator").getViewObject();
        System.err.println("rows = " + vo.getRowCount());

        RowSetIterator rws = vo.createRowSetIterator(null);
        while (rws.hasNext()) {
         Row rw = rws.next();
            selectItems.add(new SelectItem(new BigDecimal(rw.getAttribute("Id") + ""),
                                           (String) rw.getAttribute("HearingDate")));
          }

        return selectItems;
    }


    public void actionTypeChange(ValueChangeEvent valueChangeEvent) {
        //        boolean  checkIsProdceduerUnique= checkIsProdceduerUnique((BigDecimal) valueChangeEvent.getNewValue());
        //        System.err.println("valueChangeEvent.getNewValue()"+valueChangeEvent.getNewValue());
        //               if( checkIsProdceduerUnique==false) {
        //                   System.err.println("checkIsProdceduerUnique"+checkIsProdceduerUnique);
        //                   JSFUtils.addFacesErrorMessage("\u0647\u0630\u0627 \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u062a\u0645 \u0625\u0636\u0627\u0641\u062a\u0629 \u0645\u0646 \u0642\u0628\u0644");
        //                   return;
        //               }

        //         ViewObject object = ADFUtil.findIterator("DistributionRulesROVO1Iterator").getViewObject() ;
        //
        //        RowSetIterator rws = object.createRowSetIterator(null);
        //        int entityId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "OriginEntity").toString());
        //        String bcId =  (getAttributeValue(RESULT_SEARCH_ITERATOR, "CaseNumber").toString());
        //        System.out.println("entityId    -----------"+entityId);
        //        System.out.println("bcId    -----------"+bcId);
        //        while (rws.hasNext()) {
        //            Row rw = rws.next();
        //            String rule = (String) rw.getAttribute("Rule");
        //           String rules = rule.split("where")[1] ;
        //            String ruleValues = (String) rw.getAttribute("RuleValues");
        //            String[] vals =  ruleValues.split(",") ;
        //            System.out.println("bcId    --sssssss---------"+vals.length);
        //         if(vals.length > 1)
        //         {
        //             if (entityId == Integer.parseInt(vals[0]))
        //             {
        //                     System.out.println(vals[1]+"bcId    --sssssss---------"+bcId.charAt(bcId.length()-1));
        //                 if (  vals[1].equalsIgnoreCase( bcId.charAt(bcId.length()-1)+"" ))
        //                 {
        //                         BigDecimal ruleId = (BigDecimal) rw.getAttribute("RuleId");
        //                         ADFContext.getCurrent().getSessionScope().put("ruleId",ruleId);
        //                         System.out.println("ruleId    -----------"+ruleId);
        //                     }
        //                 }
        //
        //             }
        //
        //        }

      
       // int actionTypeId = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ActionTypeId").toString());
        int BCTypeId = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString());
        RichSelectOneChoice soc5 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc5");
        System.out.println("soc5    ---------" + soc5.getValue());
        if (JSFUtils.findComponentInRoot("soc5") != null) {
            int typee = Integer.parseInt(soc5.getValue().toString());
            if (typee == 24 || BCTypeId == 4)
                ADFContext.getCurrent().getSessionScope().put("delay", "Y");
            else
                ADFContext.getCurrent().getSessionScope().put("delay", "N");
            getRuleID();
            System.out.println("soc5    ---------" +  ADFContext.getCurrent().getSessionScope().get("delay"));
            if (typee == 1 || typee == 159)
                ADFContext.getCurrent().getSessionScope().put("postponeddd", "3");
            else if (typee == 2)
                ADFContext.getCurrent().getSessionScope().put("postponedd", "2");
            else if (typee == 3)
                ADFContext.getCurrent().getSessionScope().put("postponedd", "2");
            else if (typee == 14) {
                ADFContext.getCurrent().getSessionScope().put("postponedd", "1");
                ADFContext.getCurrent().getSessionScope().put("postponeddd", "3");
            } else if (typee == 8)
                ADFContext.getCurrent().getSessionScope().put("postponedd", "2");
            else if (typee == 9 || typee == 161)
                ADFContext.getCurrent().getSessionScope().put("postponeddd", "4");
            else if (typee == 23)
                ADFContext.getCurrent().getSessionScope().put("postponeddd", "4");

            else
                ADFContext.getCurrent().getSessionScope().put("postponedd", "2");


            if (typee == 14 || typee == 9 || typee == 8 || typee == 14 || typee == 3 || typee == 2 || typee == 1 ||
                typee == 157) {
                //            BindingContext bctx = BindingContext.getCurrent();
                //            BindingContainer bindings = bctx.getCurrentBindingsEntry();
                //            OperationBinding setVariable =  (OperationBinding) bindings.get("Execute");
                //            setVariable.execute();
            System.out.println("Execute-----------------------");
            }

        } else
            ADFContext.getCurrent().getSessionScope().put("postponedd", "2");


        setAttributeValue(ACTION_ITERATOR, "FeesType", null);
        RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it42");
        inp.setDisabled(false);
        System.out.println("-------------------------------- Oraby actionTypeChange---------------------------");
        System.out.println(JSFUtils.getFromSession("sessionid") + " actionTypeChangeVCL() method " +
                           valueChangeEvent.getNewValue() + " session id is " + JSFUtils.getFromSession("loginid"));
        System.out.println("---------------------------------------------------------------------");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        //callJsFunction("pressButton");
        RichInputDate deliveryDate = (RichInputDate) JSFUtils.findComponentInRoot("id19");
        RichInputDate startDate = (RichInputDate) JSFUtils.findComponentInRoot("id23");
        RichInputDate endDate = (RichInputDate) JSFUtils.findComponentInRoot("id18");
        RichInputText guardianName = (RichInputText) JSFUtils.findComponentInRoot("it60");
        RichInputText guardianNationalId = (RichInputText) JSFUtils.findComponentInRoot("it62");
        RichInputText kinship = (RichInputText) JSFUtils.findComponentInRoot("it61");
        RichInputText actionNotes = (RichInputText) JSFUtils.findComponentInRoot("it59");
        guardianName.setValue(null);
        guardianNationalId.setValue(null);
        kinship.setValue(null);
        deliveryDate.resetValue();
        startDate.setValue(null);
        endDate.setValue(null);
        actionNotes.setValue(null);


        int casTypeId =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId") + "") : -1;
        if (valueChangeEvent.getNewValue() != null && !valueChangeEvent.getNewValue().equals("")) {
            int action = Integer.parseInt(valueChangeEvent.getNewValue().toString());

            {
                restMerge();
                // this part to determine estanaf with entity gozaya or moarda estanafia entity kolya
                if (invals(action, 9, 23) || casTypeId == 15) {
                    setAttributeValue(ACTION_ITERATOR, "GozayaOrKolyaEstanafia",
                                      getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabKolyaa"));
                    BigDecimal errandEntUser =
                        new BigDecimal(ADFContext.getCurrent().getSessionScope().get("errandEntUser") + "");
                    if (errandEntUser.compareTo(new BigDecimal(0)) == 1)
                        setAttributeValue(ACTION_ITERATOR, "GozayaOrKolyaEstanafia", errandEntUser);

                } else
                    setAttributeValue(ACTION_ITERATOR, "GozayaOrKolyaEstanafia",
                                      getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa"));
                //end part
            }

            setAttributeValue(ACTION_ITERATOR, "JudgmentType",
                              getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType"));

            /* this block to determine if case tefl or not  */
            {
                setAttributeValue(ACTION_ITERATOR, "CasTypeId", casTypeId);
            }

            setAttributeValue(ACTION_ITERATOR, "AppealDegree", null);
            ADFUtil.setEL("#{pageFlowScope.postbonde}", null);
            ADFUtil.setEL("#{pageFlowScope.showAppeal}", null);
            setAttributeValue(ACTION_ITERATOR, "ShowAppeal", null);

            ViewObject viewActions = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
            Row rowActions = viewActions.getCurrentRow();
            if (action == 24 || action == 68 ||
                action ==
                69) { //
                System.out.println(JSFUtils.resolveExpression("#{sessionScope.hearingDateId}") + "ZZZZZZZZZZZZz" +
                                   getAttributeValue("ActionsRVO1Iterator", "AppealDegree"));
                if (getAttributeValue("ActionsRVO1Iterator", "AppealDegree") == null)
                    setAttributeValue("ActionsRVO1Iterator", "AppealDegree", 2);
                ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23)");
                    view2.setOrderByClause("ID desc");
                    view2.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        setAttributeValue("ActionsRVO1Iterator", "AppealDegree",
                                          view2.first().getAttribute("ProcedureType"));
                        ADFUtil.setEL("#{sessionScope.hearingDateId}", view2.first().getAttribute("HearingDate"));
                        ADFUtil.setEL("#{sessionScope.postponed}", view2.first().getAttribute("ProcedureType"));
                    }
                }
                view2.setWhereClause(null);
                view2.executeQuery();
                ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();
                ADFUtil.setEL("#{pageFlowScope.postbonde}", 1);
                if ("1".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "9".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "157".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "23".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "")) {
                    setAttributeValue(ACTION_ITERATOR, "ShowAppeal", 1);
                    ADFUtil.setEL("#{pageFlowScope.showAppeal}", 1);
                }
            }
            if (action ==
                45) { //
                System.out.println(JSFUtils.resolveExpression("#{sessionScope.hearingDateId}") + "ZZZZZZZZZZZZz" +
                                   getAttributeValue("ActionsRVO1Iterator", "AppealDegree"));
                if (getAttributeValue("ActionsRVO1Iterator", "AppealDegree") == null)
                    setAttributeValue("ActionsRVO1Iterator", "AppealDegree", 2);
                ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                    view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23)");
                    view2.setOrderByClause("ID desc");
                    view2.executeQuery();
                    if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        setAttributeValue("ActionsRVO1Iterator", "AppealDegree",
                                          view2.first().getAttribute("ProcedureType"));
                        ADFUtil.setEL("#{sessionScope.hearingDateId}", view2.first().getAttribute("HearingDate"));
                        ADFUtil.setEL("#{sessionScope.postponed}", view2.first().getAttribute("ProcedureType"));
                    }
                }
                view2.setWhereClause(null);
                view2.executeQuery();
                ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();
                ADFUtil.setEL("#{pageFlowScope.postbonde}", 1);
                if ("1".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "9".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "157".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "") ||
                    "23".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "AppealDegree") + "")) {
                    setAttributeValue(ACTION_ITERATOR, "ShowAppeal", 1);
                    ADFUtil.setEL("#{pageFlowScope.showAppeal}", 1);
                }
            }

            if (action == 53) {
                int resPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());
                Row actionRow = ADFUtil.findIterator(ACTION_ITERATOR).getViewObject().getCurrentRow();
                actionRow.setAttribute("TotalAmount", getTotalPenaltyOrGuranteeAmount(resPartyId, 4));
            }

            if (action == 67) {
                int resPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());
                Row actionRow = ADFUtil.findIterator(ACTION_ITERATOR).getViewObject().getCurrentRow();
                actionRow.setAttribute("TotalAmount", getTotalPenaltyOrGuranteeAmount(resPartyId, 3));
                actionRow.setAttribute("PainUpAmount", getPreviousPaid(3));
                System.err.println("++++++++ " + getTotalPenaltyOrGuranteeAmount(resPartyId, 3) + " " +
                                   getPreviousPaid(3));
                RichInputText remain = (RichInputText) JSFUtils.findComponentInRoot("it57");
                remain.setValue(getTotalPenaltyOrGuranteeAmount(resPartyId, 3) - getPreviousPaid(3));
                RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it58");
                input.setValue(0);
                RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc22");
                sc.setValue(0);
            }

            if (action == 62) {
                RichInputText fixedPenaltyAmount = (RichInputText) JSFUtils.findComponentInRoot("it48");
                fixedPenaltyAmount.setValue(getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount") + "");
                RichInputText days = (RichInputText) JSFUtils.findComponentInRoot("it51");
                days.setValue(0);
                RichInputDate from = (RichInputDate) JSFUtils.findComponentInRoot("id12");
                from.setValue(null);
                RichInputDate to = (RichInputDate) JSFUtils.findComponentInRoot("id13");
                to.setValue(null);
                RichInputText paid = (RichInputText) JSFUtils.findComponentInRoot("it52");
                RichInputText remaining = (RichInputText) JSFUtils.findComponentInRoot("it53");
                paid.setValue(0);
                remaining.setValue(0);
            }

            //            if (action == 67){
            //                RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it57");
            //                input.setValue(null);
            //            }

            if (invals(valueChangeEvent.getNewValue(), 1, 2, 8, 9, 23, 24, 68, 69)) {
                Object ob[] = getDataFromPreviousProcedures(valueChangeEvent.getNewValue());
                if (invals(valueChangeEvent.getNewValue(), 1, 9, 23) ||
                    (invals(valueChangeEvent.getNewValue(), 24) &&
                     "1".equalsIgnoreCase(JSFUtils.getFromPageFlowScope("showAppeal") + ""))) {
                    rowActions.setAttribute("HearingDateAll", ob[1]);
                    rowActions.setAttribute("HearingDateId", ob[0]);
                } else if (invals(valueChangeEvent.getNewValue(), 2, 8) ||
                           (invals(valueChangeEvent.getNewValue(), 24) &&
                            JSFUtils.getFromPageFlowScope("showAppeal") == null)) {
                    rowActions.setAttribute("HearingDateAll", ob[1]);
                    rowActions.setAttribute("HearingDateId", ob[0]);
                 }
            }
            RichPanelGroupLayout comp =
                (oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl72");
            if (invals(action, 34,   37, 35)) { // , 35
                setAttributeViewValue(ACTION_ITERATOR, "CapacityTypeId", "1");
                comp.setVisible(false);
            } else {
                comp.setVisible(true);
            }
        }

        ADFUtil.refreshComponent("pgl110");
    }

    // ValueChangeListener for actionTypeId
    public void actionTypeChangePPO(ValueChangeEvent valueChangeEvent) {
        if ((new BigDecimal(valueChangeEvent.getNewValue() + "")).compareTo(new BigDecimal(1)) == 0) {
            ((RichInputListOfValues) JSFUtils.findComponentInRoot("appealDatesId")).setDisabled(false);
          JSFUtils.refreshComponent("appealDatesId");
               ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
            if (actionsView.getRowCount() == 0) {
               Row actionsRow = actionsView.createRow();
               actionsView.insertRow(actionsRow);
               }
            System.out.println("#@#@#" + actionsView.getRowCount());
        ADFUtil.setEL("#{sessionScope.hearingDateId}",
                      ADFUtil.findIterator("ResPartiesGozayaaROVO1Iterator").getViewObject().first().getAttribute("HearingDate").toString());


        ADFUtil.setEL("#{sessionScope.policeId}",
                      new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}") + ""));

            System.out.println(JSFUtils.getFromSession("policeId") + "****" + JSFUtils.getFromSession("hearingDateId"));
               ADFUtil.findIterator("AppealHearingDateROVO1Iterator").executeQuery();
        } else {
            ((RichInputListOfValues) JSFUtils.findComponentInRoot("appealDatesId")).setDisabled(true);
                JSFUtils.refreshComponent("appealDatesId");
            }
    }

    private int checkHearingDate(long businessCaseId, long resId) {
        OperationBinding bindingId = ADFUtil.findOperation("checkDatesHearing");
        bindingId.getParamsMap().put("businessCaseId", businessCaseId);
        bindingId.getParamsMap().put("id", resId);
        Object obj = bindingId.execute();
        System.out.println(" checkHearingDate is  *" + obj);
        int id = Integer.parseInt(obj.toString());
        return id;
    }

    public void updateUpload(ValueChangeEvent valueChangeEvent) {
        try {
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            InputStream is = uf.getInputStream();
            String documentId = CjUtils.insertAttachmentAsFileIntoUCM(is, 1, 1, 1, 1, uf.getFilename()).get("dID");
            long id = 0;
            ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
            Row row = view.getCurrentRow();
            ViewObject filesView = ADFUtil.findIterator("PartyFilesRVO1Iterator").getViewObject();
            Row filesRow = filesView.getCurrentRow();
            id = Long.parseLong(filesRow.getAttribute("Id").toString());
            OperationBinding bindingId = ADFUtil.findOperation("updateCriminalJusticeFiles");
            bindingId.getParamsMap().put("id", id);
            bindingId.getParamsMap().put("documentId", documentId);
            bindingId.execute();
            int logId = Integer.parseInt(row.getAttribute("Id").toString());
            int typeId = Integer.parseInt(filesRow.getAttribute("Typeid").toString());
            updateProcedureLogDocuments(logId, documentId, typeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshIterator(String iterator) {
        ViewObject view = ADFUtil.findIterator(iterator).getViewObject();
        view.executeQuery();
        ADFUtil.findIterator(iterator).executeQuery();
    }

    // refresh previousProcedures for party
    public void previousProceduresDiclosListener(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            refreshIterator("PreviousProceduresRVO3Iterator");
        }
    }

    // refresh previousProcedures for all parties
    public void previousProceduresAll(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            refreshIterator("PreviousProceduresRVO2Iterator");
        }
    }

    public void refreshAttachments(DisclosureEvent disclosureEvent) {
        if (disclosureEvent.isExpanded()) {
            refreshIterator("PartyFilesRVO2Iterator");
        }
    }

    public String refreshPartyProcedures() {
        refreshIterator("PreviousProceduresRVO3Iterator");
        return null;
    }

    // return action when call Idnumber service
    public void getNIDValues(ReturnEvent returnEvent) {
        // Add event code here...
        String clickedButton = null;
        if (JSFUtils.getFromSession("clickedButton") != null) {
            clickedButton = JSFUtils.getFromSession("clickedButton").toString();
        }

        if (clickedButton != null && clickedButton.equals("save")) {
            PersonData personData = (PersonData) JSFUtils.getFromPageFlowScope("nIdResponseParam");
            if (personData != null) {
                //        ADFUtil.setBoundAttributeValue("ClaimNumber", personData.getFirstName());
                //        ADFUtil.refreshComponent("it1");

                OperationBinding validateDublicateNID = getBindings().getOperationBinding("validateDublicateNID");
                validateDublicateNID.getParamsMap().put("parId",
                                                        new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR,
                                                                                         "PoliceId") + ""));
                validateDublicateNID.getParamsMap().put("NID", personData.getNationalId());
                BigDecimal validateDublicateNIDCount = (BigDecimal) validateDublicateNID.execute();

                if (validateDublicateNIDCount.compareTo(new BigDecimal(0)) == 0) {

                    setAttributeViewValue(PARAMS_ITERATOR, "PartyFName",
                                          personData.getFirstName() + " " + personData.getFatherFullName());
                if (personData.getPoliceAddressCode().length() > 0)
                        setAttributeViewValue(PARAMS_ITERATOR, "PartyPolice",
                                              new BigDecimal(personData.getPoliceAddressCode()));
                setAttributeViewValue(PARAMS_ITERATOR, "PartyGov", new BigDecimal(personData.getGovernateCode()));
                    setAttributeViewValue(PARAMS_ITERATOR, "PartyAddress",
                                          personData.getGovernate() + " - " + personData.getPoliceAddress() + " - " +
                                          personData.getAddress());

                    setAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag", "Y");

                } else {
                    setAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag", "N");
                        //      ADFUtil.setBoundAttributeValue("ClaimNumber", null);
                        setAttributeValue(RESULT_SEARCH_ITERATOR, "IdType", null);
                        setAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber", null);
                        showErrorMessageForComponentById("\u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0642\u0648\u0645\u0649 \u0645\u062A\u0643\u0631\u0631 \u0639\u0644\u0649 \u0627\u0644\u0642\u0636\u064A\u0629",
                                                         "it7");

                    }


            }
        } else if (clickedButton != null && clickedButton.equals("cancel")) {


            PersonData pd = (PersonData) JSFUtils.getFromPageFlowScope("nIdResponseParam");
            if (pd != null) {

                if (pd.getResponseMessage().trim().equalsIgnoreCase("\u0628\u0637\u0627\u0642\u0629 \u0645\u0646\u062a\u0647\u064a\u0629 \u0627\u0644\u0635\u0644\u0627\u062d\u064a\u0629")) {
                    System.err.println(pd.getResponseCode() + "Expired NID" + pd.getResponseMessage());

                    setAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag", "E");

                } else if (pd.getResponseMessage().trim().equalsIgnoreCase("\u0627\u0644\u0628\u0637\u0627\u0642\u0629 \u063a\u064a\u0631 \u0645\u0648\u062c\u0648\u062f\u0629")) {
                    System.err.println(pd.getResponseCode() + "Unknown NID" + pd.getResponseMessage());

                    setAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag", "U");

                } else {
                    setAttributeValue(RESULT_SEARCH_ITERATOR, "NidFlag", "N");
            //      ADFUtil.setBoundAttributeValue("ClaimNumber", null);
            setAttributeValue(RESULT_SEARCH_ITERATOR, "IdType", null);
            setAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber", null);
            ADFUtil.refreshComponent("it7");
        }
    }
        }
    }

    // used if user press cancel in popup
    public void cancelListener() {
        setAttributeValue(RESULT_SEARCH_ITERATOR, "IdType", null);
        setAttributeValue(RESULT_SEARCH_ITERATOR, "IdNumber", null);
        ADFUtil.refreshComponent("it7");
    }

    public void idNumberListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if ("2".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "IdType") + "")) {
            callJsFunction("pressButton");
        }
    }

    private boolean tanazolMoarda() {
        ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
        view2.setWhereClause("PROCEDURE_TYPE in (65)");
        view2.executeQuery();

        if (view2.getRowCount() > 0) {
            view2.setWhereClause(null);
            view2.executeQuery();
              return true;
        } else {
            view2.setWhereClause(null);
            view2.executeQuery();
            return false;
        }
    }

    private boolean hasPrevDegreeProcedure() {
        System.out.println("hasPrevDegreeProcedure");

        ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
        view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,14)");
        view2.setOrderByClause("ID desc");
        view2.executeQuery();
        Object lastProced = null, lastResPartyDegree = null, PartyId = null;


        if (view2.getRowCount() > 0) {
            lastProced = view2.first().getAttribute("ProcedureType");
            lastResPartyDegree = view2.first().getAttribute("NewResPartyId");
            PartyId = view2.first().getAttribute("PartyId");

        }
        Object lastTageel = null, lastResPartyTageel = null;
        view2.setWhereClause("PROCEDURE_TYPE in (24) and  New_Res_Party_Id > " + lastResPartyDegree);
        view2.setOrderByClause("ID desc");
        view2.executeQuery();

        if (view2.getRowCount() > 0) {
            lastTageel = view2.first().getAttribute("ProcedureType");
            lastResPartyTageel = view2.first().getAttribute("NewResPartyId");
        }
        view2.setWhereClause(null);
        view2.executeQuery();
        System.err.println("previous rows = " + view2.getRowCount());
        OperationBinding bindingId = getBindings().getOperationBinding("isHasred");
        bindingId.getParamsMap().put("lastResPartyTageel",
                                     lastResPartyTageel != null ? Integer.parseInt(lastResPartyTageel.toString()) : -1);
        bindingId.getParamsMap().put("lastResPartyDegree", PartyId != null ? Integer.parseInt(PartyId.toString()) : -1);
        Boolean isHasered = (Boolean) bindingId.execute();

        System.out.println("lastProced is " + lastProced);
        System.out.println("isHasered is " + isHasered);
        if (invals(lastProced, 1, 2, 8, 9, 23, 14) && !isHasered)
            return true;
        else
            return false;

    }

    /* Procedures Business */
    List<SelectItem> selectItemList = null;
    ArrayList<SelectItem> newSelectItemList = null;
    SelectItem s = null;

    private SelectItem getSelectItem(SelectItem li) {
        SelectItem s = new SelectItem();
        s.setLabel(li.getLabel());
        s.setValue(li.getValue());
        return s;
    }

    private Integer valueOfSelectItem(SelectItem s) {
        return (s.getValue() != null && !"".equalsIgnoreCase(s.getValue().toString())) ?
               Integer.parseInt(s.getValue().toString()) : null;
    }
    Boolean refreshComp = false;

    public List<SelectItem> getSelectItemList() {

        if (refreshComp) {
            this.setRefreshComp(false);
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprfact = fctx.getApplication().getExpressionFactory();
            ValueExpression vexpr =
                exprfact.createValueExpression(elctx, "#{bindings.ActionTypeId.items}", Object.class);
        selectItemList = (List<SelectItem>) vexpr.getValue(elctx);
            System.out.println("SSSSSSS " + getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur"));
        if (getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur") != null) {
            if ("1".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur") + "")) {
               System.out.println("FFFF");
                    selectItemList = rulesLov();
                this.setSelectItemList(selectItemList);
                return selectItemList;
            } else if ("2".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur") + "")) {
                    System.out.println("dddd");
                BigDecimal gadwal = getGadwal();
                    int count21 = 0, count20 = 0, count35 = 0;
                    if (gadwal.compareTo(new BigDecimal(3646)) == 0 || gadwal.compareTo(new BigDecimal(3647)) == 0 ||
                        gadwal.compareTo(new BigDecimal(3648)) == 0) {
                        count21 = getPartyProceduresRep(new BigDecimal(21));
                        count20 = getPartyProceduresRep(new BigDecimal(20));
                        count35 = getPartyProceduresRep(new BigDecimal(35));
                }
                    selectItemList = lastProceduresLov(count21, count20, count35, gadwal);
                 this.setSelectItemList(selectItemList);
                 return selectItemList;
            } else if ("3".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur") + "")) {
                    System.out.println("wwwww");
                    selectItemList = lastabroadLov();
                 this.setSelectItemList(selectItemList);

               return selectItemList;
            } else if ("4".equalsIgnoreCase(getAttributeValue("ActionsRVO1Iterator", "RulesOProcedur") + "")) {
                    System.out.println("rrrr");
                    selectItemList = rulesProsecutionLov();
                 this.setSelectItemList(selectItemList);

               return selectItemList;
            }
        }


        }
        return selectItemList;
    }

    /* rulesProsecution Business */
    private ArrayList<SelectItem> rulesProsecutionLov() {
        //        boolean isallowTageel = hasPrevDegreeProcedure();
        ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();

        newSelectItemList = new ArrayList<SelectItem>();
        for (SelectItem li : selectItemList) {
            s = getSelectItem(li);

            if (s.getValue() != null && invals(s.getValue(), 55, 56, 86)) {
                s.setDisabled(true);
            }

            view.setWhereClause("PROCEDURE_TYPE in (57)");
            view.executeQuery();

            if (view.getRowCount() > 0 && invals(s.getValue(), 86)) {
                s.setDisabled(false);
            }
            view.setWhereClause("PROCEDURE_TYPE in (54,55,56)");
            view.setOrderByClause("id desc");
            view.executeQuery();
            if (view.getRowCount() > 0 && invals(s.getValue(), 55, 56) &&
                view.first().getAttribute("ProcedureType") != null &&
                Integer.parseInt(view.first().getAttribute("ProcedureType").toString()) == 54) {
                s.setDisabled(false);
            }


            newSelectItemList.add(s);
        }
        view.setWhereClause(null);
        view.executeQuery();
        return newSelectItemList;
    }

    /* rules Business */
    private ArrayList<SelectItem> rulesLov() {
        boolean isallowTageel = hasPrevDegreeProcedure();

        int crimeType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "CriminalType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "CriminalType") + "") : -1;
        String entityDegree = (String) getParamUsed()[0];
        int caseSchema = (Integer) getParamUsed()[4];
        System.out.println("caseSchema    ----- " + caseSchema);
        int journalistFlag = (Integer) getParamUsed()[6];
        newSelectItemList = new ArrayList<SelectItem>();
        for (SelectItem li : selectItemList) {
            s = getSelectItem(li);
            if (caseSchema != -1 || journalistFlag == 1) {
                // nyabaa kolya and folder is gonah (tagreem Shahed ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½)
                if ("39".equalsIgnoreCase(entityDegree) && crimeType == 1) {
                    disableEnableSelectItem(s, 11); // ekala mn el garama
                }
            } else {
                System.out.println(s.getValue() + "dddddddddddddddddddddddddddddddd****" + isallowTageel);
                if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().getRowCount() == 0) {
                    s.setDisabled(true);

                } else {
                    if (s.getValue() != null && isallowTageel) {
                        s.setDisabled(false);
                    } else {
                        System.out.println("&&&&&&&&&&&&");
                        s.setDisabled(true);

                            ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                        view.setWhereClause("PROCEDURE_TYPE in (46)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        int flag = 0;

                        if (view.getRowCount() > 0) {
                            disableEnableSelectItem(s, 47, 48);
                            flag = 1;
                        } else
                            flag = 0;
                        view.setWhereClause("PROCEDURE_TYPE in (52)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        if (view.getRowCount() > 0) {
                            if (flag == 0)
                                disableEnableSelectItem(s, 53);
                            else
                                disableEnableSelectItem(s,  47, 48, 53);
                        }
                        view.setWhereClause("PROCEDURE_TYPE in (58)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        if (view.getRowCount() > 0) {
                            if (flag == 0)
                                disableEnableSelectItem(s, 60, 61);
                            else
                                disableEnableSelectItem(s, 47, 48, 60, 61);
                        }
                        view.setWhereClause("PROCEDURE_TYPE in (64)");
                        view.setOrderByClause("id desc");
                        view.executeQuery();
                        if (view.getRowCount() > 0) {
                            if (flag == 0)
                                disableEnableSelectItem(s, 57);
                            else
                                disableEnableSelectItem(s, 47, 48, 57);
                        }

                        view.setWhereClause("PROCEDURE_TYPE in (66)");
                        view.executeQuery();
                        if (view.getRowCount() > 0) {
                            if (flag == 0)
                                disableEnableSelectItem(s, 67);
                            else
                                disableEnableSelectItem(s, 47, 48, 67);
                        }

                        view.setWhereClause("PROCEDURE_TYPE in (67)");
                        view.executeQuery();
                        if (view.getRowCount() > 0 && invals(s.getValue(), 67)) {
                            s.setDisabled(true);
                        }

                        view.setWhereClause(null);
                        view.executeQuery();


                    }
                }
            }
            System.out.println("-----------------999");
            if (s.getValue() != null && invals(s.getValue(), 46)) {
                s.setDisabled(false);
            }
            //            =========================othman & mahmoud =================================
            // get party id

            System.out.println("----------------- OTHMAN ");
            if (s.getValue() != null && invals(s.getValue(), 46)) {
                s.setDisabled(false);
            }

            newSelectItemList.add(s);
        }
        return newSelectItemList;
    }
    /* / rules Business */

    /*  abroad RequestBusiness */
    private ArrayList<SelectItem> lastabroadLov() {
        /*
     * param used
     * */
        String entityDegree = (String) getParamUsed()[0];
        int degreeId = (Integer) getParamUsed()[1];
        int attendence = (Integer) getParamUsed()[2];
        int judgmentType = (Integer) getParamUsed()[3];
        int folderType = (Integer) getParamUsed()[5];
        int caseSchema = (Integer) getParamUsed()[4];
        int journalistFlag = (Integer) getParamUsed()[6];
        boolean hasPrevProcedurre = hasPrevDegreeProcedure();

        newSelectItemList = new ArrayList<SelectItem>();
        for (SelectItem li : selectItemList) {
            s = getSelectItem(li);

            if (s.getValue() != null) {
                if (invals(judgmentType, 2, 3)) // amr genaya
                {
                    disableEnableSelectItem(s, 7, 77, 27); //  sada ,eatrad on amr geanaya
                } else if (journalistFlag == 1) // gonah sahafeen
                {
                    if (attendence ==
                        3) // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
                    {
                        disableEnableSelectItem(s, 25, 7, 77);
                    } else {
                        disableEnableSelectItem(s, 0);
                    }
                } else {
                    if (degreeId == 1) {
                        if (attendence != 3) //attendence
                        {

                            disableEnableSelectItem(s, 26, 7, 77);
                        } else {
                            disableEnableSelectItem(s, 25, 26, 7, 77);
                        }
                    } else if (degreeId == 2) {
                        if (attendence != 3) //attendence
                        {
                            disableEnableSelectItem(s, 26, 7, 77);
                        } else {
                            disableEnableSelectItem(s, 26, 7, 77);
                        }
                    } else if (degreeId == 3 || degreeId == 4) {
                        disableEnableSelectItem(s, 7, 77);
                    }
                }
            }
            if (s.getValue() != null && invals(s.getValue(), 30)) {
                //                System.err.println(">>> BELAL");
                // this part exception for egraa ط£ع©ط¢آ§ط£ع©ط¢آ¹ط£ع©ط¢ع¾ط£â„¢أ¢â‚¬آ¦ط£ع©ط¢آ§ط£ع©ط¢آ¯ ط£ع©ط¢ع¾ط£ع©ط¢آ³ط£â„¢أ¢â‚¬ï؟½?ط£â„¢ط¥آ ط£â„¢أ¢â‚¬آ¦ ط£ع©ط¢آ´ط£â„¢أ¢â‚¬طŒط£ع©ط¢آ§ط£ع©ط¢آ¯ط£ع©ط¢آ©
                if (hasPrevProcedurre) {
                    s.setDisabled(false);
                } else {
                    s.setDisabled(true);
                }
            }
            System.out.println("*****************9999");
            if (s.getValue() != null && invals(s.getValue(), 46)) {
                s.setDisabled(false);
            }
            newSelectItemList.add(s);
        }
        return newSelectItemList;
    }
    /* / abroad RequestBusiness */

    // this method used to return params used to procedures
    private Object[] getParamUsed() {
        Object a[] = new Object[11];
        /*
    * param used
    * */
        String entityDegree = getUserInfo().getEntityDegree();
        int degreeId =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "DegreeId") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "DegreeId") + "") : -1;
        int attendence =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "Attendance") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "Attendance") + "") : -1;
        int judgmentType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") + "") : -1;
        int folderType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderType") + "") : -1;
        int caseSchema =
            ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
            Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;
        int journalistFlag =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") + "") : -1;
        int crimeType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "CriminalType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "CriminalType") + "") : -1;
        int casTypeId =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "CasTypeId") + "") : -1;

        int prosectionOpjection =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionObjection") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionObjection") + "") : -1;

        int objectionType =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "ObjectionType") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ObjectionType") + "") : -1; // kan lam ya kon
        a[0] = entityDegree;
        a[1] = degreeId;
        a[2] = attendence;
        a[3] = judgmentType;
        a[4] = caseSchema;
        a[5] = folderType;
        a[6] = journalistFlag;
        a[7] = crimeType;
        a[8] = casTypeId;
        a[9] = prosectionOpjection;
        a[10] = objectionType;

        ///*******
        return a;
    }

    // disable and enable select Item
    private void disableEnableSelectItem(SelectItem s, Object... valueList) {
        if (!invals(valueOfSelectItem(s), valueList)) {
            s.setDisabled(true);
        } else {
            s.setDisabled(false);
        }

    }
    // / disable and enable select item

    /* Procedures Business inside */
    private ArrayList<SelectItem> lastProceduresLov(int count21, int count20, int count35, BigDecimal gadwal) {
        /*
    * param used
    * */String entityDegree = (String) getParamUsed()[0];
        int degreeId = (Integer) getParamUsed()[1];
        int attendence = (Integer) getParamUsed()[2];
        int judgmentType = (Integer) getParamUsed()[3];
        int folderType = (Integer) getParamUsed()[5];
        int caseSchema = (Integer) getParamUsed()[4];
        int journalistFlag = (Integer) getParamUsed()[6];
        int crimeType = (Integer) getParamUsed()[7];
        int casTypeId = (Integer) getParamUsed()[8];
        boolean hasPrevProcedurre = hasPrevDegreeProcedure();
        int prosctionObjection = 0; //(Integer) getParamUsed()[9];
        int objectionType = (Integer) getParamUsed()[10];
        String guiltlessnessFlag = getAttributeValue(RESULT_SEARCH_ITERATOR, "GuiltlessnessFlag") + "";
        //        String degreeId = getAttributeValue(RESULT_SEARCH_ITERATOR, "DegreeId") + "";
        Boolean flg =
            (Boolean) "Y".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString());
        boolean tanazolMoarda = tanazolMoarda();

        System.err.println("-*-------EgraaSwitcher-----" + getAttributeValue(ACTION_ITERATOR, "EgraaSwitcher"));
        System.out.println(

            "entityDegree" + entityDegree + "degreeId" + degreeId + "attendence" + attendence + "folderType" +
            folderType + "caseSchema" + caseSchema + "journalistFlag" + journalistFlag + "crimeType" + crimeType +
            "casTypeId" + casTypeId + "hasPrevProcedurre" + hasPrevProcedurre + "prosctionObjection" +
            prosctionObjection + "objectionType" + objectionType);


        //        System.err.println(">>>>>>>>> " + attendence + "LLLLLL");
        ///*******
        ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();

        newSelectItemList = new ArrayList<SelectItem>();
        for (SelectItem li : selectItemList) {
            s = getSelectItem(li);
            //      System.err.println(prosctionObjection +":::: ");
            if (caseSchema == -1) // gonha
            {
                if ("Y".equalsIgnoreCase(guiltlessnessFlag)) { // Baraa degree 1

                    disableEnableSelectItem(s, 1); // estanaf

                } else if (casTypeId == 10 && (folderType == 5 || folderType == 6)) // gonah amn dawla tawarea
                {
                    if (folderType == 5) { // habs
                        disableEnableSelectItem(s, 7, 77, 31, 32, 33, 34, 37); // cancel ,edit,tasdek



                    } else if (folderType == 6) { //gharama
                        disableEnableSelectItem(s, 7, 77, 34, 37);
                    }
                } else if ("39".equalsIgnoreCase(entityDegree) || "46".equalsIgnoreCase(entityDegree) ||
                           "38".equalsIgnoreCase(entityDegree) || flg) // nyaba  Kolya // gonah...
                {
                    System.out.println("FFFFFFFFFFfffffffffffffffffffffffffffffffff");

                    if (invals(judgmentType, 2, 3)) // amr genay
                    {
                        System.out.println("***************judgmentType************");
                        disableEnableSelectItem(s, 7, 77, 14, 34, 37 ,168); //  sada ,eatrad on amr geanaya
                    } else {
                        System.err.println("***************judgmentType************");
                        appearProcReservationsKolya(s, degreeId, attendence, prosctionObjection, objectionType,
                                                    folderType);
                    }
                } else if (("46".equalsIgnoreCase(entityDegree) || "38".equalsIgnoreCase(entityDegree) ||
                            "3".equalsIgnoreCase(entityDegree)) &&
                           (!invals(journalistFlag, 1) || casTypeId == 11)) // nyaba  gozaya // gonah
                {
                    if (invals(judgmentType, 2, 3)) // amr genaya
                    {
                        disableEnableSelectItem(s, 7, 77, 14, 34, 37 ,168); //  sadad ,eatrad on amr geanaya
                    } else {
                        appearProcReservationGozaya(s, degreeId, attendence, prosctionObjection, objectionType);
                    }
                } else if (("46".equalsIgnoreCase(entityDegree) || "38".equalsIgnoreCase(entityDegree) ||
                            "39".equalsIgnoreCase(entityDegree)) &&
                           journalistFlag ==
                           1) // nyaba  Kolya // gonah sahfeen ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
                {
                    if (crimeType == 1) { // nyabaa kolya and folder is gonah (tagreem Shahed )
                        disableEnableSelectItem(s, 7, 77, 34, 37); // sadad



                    } else if (crimeType == 2) { // default gonah sahafeen
                        if (attendence == 3) // attendence  absence
                        {
                            disableEnableSelectItem(s, 2, 7, 77, 34, 37);
                        } else {

                            disableEnableSelectItem(s, 0);
                        }
                    }
                } else if ("46".equalsIgnoreCase(entityDegree) || "38".equalsIgnoreCase(entityDegree) ||
                           "39".equalsIgnoreCase(entityDegree)) // nyaba  Kolya // gonah
                {

                    if (invals(judgmentType, 2, 3)) // amr genay
                    {
                        disableEnableSelectItem(s, 7, 77, 14, 34, 37 ,168); //  sada ,eatrad on amr geanaya
                    } else {
                        System.out.println("%%%%%%%%%%%%%");
                        appearProcReservationsKolya(s, degreeId, attendence, prosctionObjection, objectionType,
                                                    folderType);
                    }
                } else {
                    disableEnableSelectItem(s, 0);
                }
            } else {
                // Genayat
                if (("46".equalsIgnoreCase(entityDegree) || "38".equalsIgnoreCase(entityDegree) ||
                     "39".equalsIgnoreCase(entityDegree)) &&
                    crimeType == 2) // nyabaa kolya (genayat) and hasr is genayat
                {
                    if (caseSchema == 3646) { // Genayat Ektsadia
                        if (attendence == 3) // attendence not absence
                        {
                            disableEnableSelectItem(s, 7, 77, 21, 34, 37); //sadad,eadat egraat, address
                        } else if (attendence == 5) {
                            disableEnableSelectItem(s, 7, 77, 34, 37); //Taan,sadad,eadat egraat mara tanya, address 35,
                        } else {
                            disableEnableSelectItem(s, 20, 7, 77, 34, 37); //sadad,taan,eshkal bewakf eltanfiz
                        }
                    } else if (caseSchema == 3647) { // Genayat Koly
                        if (attendence == 3) // attendence not absence
                        {
                            disableEnableSelectItem(s, 7, 77, 21, 34, 37); //sadad,eadat egraat.
                        } else if (attendence == 5) {
                            disableEnableSelectItem(s, 7, 77, 34, 37); //sadad,Taan,eadat egraat mara tanya, address 35,
                        } else {
                            disableEnableSelectItem(s, 20, 7, 77, 34, 37); //sadad,taan,eshkal bewakf eltanfiz
                        }
                    } else if (caseSchema == 3648 || casTypeId == 10) { // Genayat amn dawla tawarea
                        disableEnableSelectItem(s, 7, 77, 31, 32, 34, 37); // tasdek , elghaa, sadad



                    } else if (casTypeId ==
                               15) { // Genayat Tefl

                        appearProcReservationsKolya(s, degreeId, attendence, prosctionObjection, objectionType,
                                                    folderType);
                    }
                } else if (("46".equalsIgnoreCase(entityDegree) || "38".equalsIgnoreCase(entityDegree) ||
                            "39".equalsIgnoreCase(entityDegree) || journalistFlag == 1) && crimeType == 1) { // nyabaa kolya (genayat) and hasr is Gonah (tagreem Shahed ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½)
                    disableEnableSelectItem(s, 7, 77, 34, 37); // sadad



                } else {
                    disableEnableSelectItem(s, 0);
                }
            }

            if (s.getValue() != null && invals(s.getValue(), 8, 23)) {
                s.setDisabled(true);
            }

            if (s.getValue() != null && invals(s.getValue(), 91, 92, 93, 95, 96, 105, 146,8,23, 157)) {
                s.setDisabled(false);
            }

            if (s.getValue() != null && invals(s.getValue(), 94)) {
                if (checkIsProdceduerUnique(new BigDecimal(105))) {
                s.setDisabled(true);
                } else {
                s.setDisabled(false);
                }
            }

            // this part exception for egraa ط£ع©ط¢آ§ط£ع©ط¢آ¹ط£ع©ط¢ع¾ط£â„¢أ¢â‚¬آ¦ط£ع©ط¢آ§ط£ع©ط¢آ¯ ط£ع©ط¢ع¾ط£ع©ط¢آ³ط£â„¢أ¢â‚¬ï؟½?ط£â„¢ط¥آ ط£â„¢أ¢â‚¬آ¦ ط£ع©ط¢آ´ط£â„¢أ¢â‚¬طŒط£ع©ط¢آ§ط£ع©ط¢آ¯ط£ع©ط¢آ©

            if (s.getValue() != null && invals(s.getValue(), 30)) {
                System.err.println(">>> BELAL");
                // this part exception for egraa ط£ع©ط¢آ§ط£ع©ط¢آ¹ط£ع©ط¢ع¾ط£â„¢أ¢â‚¬آ¦ط£ع©ط¢آ§ط£ع©ط¢آ¯ ط£ع©ط¢ع¾ط£ع©ط¢آ³ط£â„¢أ¢â‚¬ï؟½?ط£â„¢ط¥آ ط£â„¢أ¢â‚¬آ¦ ط£ع©ط¢آ´ط£â„¢أ¢â‚¬طŒط£ع©ط¢آ§ط£ع©ط¢آ¯ط£ع©ط¢آ©
                if (hasPrevProcedurre) {
                    s.setDisabled(false);
                } else {
                    s.setDisabled(true);
                }
            }
            //======================20221225===========
            //            "ExcCourtBcChrgDecDepOrdVO3Iterator"
            //               int resPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());
            enableDiasleDecDepOrd();

            view.setWhereClause("PROCEDURE_TYPE in (54,55,56)");
            view.setOrderByClause("id desc");
            view.executeQuery();
            if (view.getRowCount() > 0 && invals(s.getValue(), 8) && degreeId == 2 &&
                view.first().getAttribute("ProcedureType") != null &&
                Integer.parseInt(view.first().getAttribute("ProcedureType").toString()) == 55) {
                s.setDisabled(false);
            }

             if (s.getValue() != null && invals(s.getValue(), 49)) {
                s.setDisabled(false);
            }


            if (s.getValue() != null && invals(s.getValue(), 59)) {
                s.setDisabled(false);
            }

            if (view.getRowCount() > 0 && invals(s.getValue(), 23) && (degreeId == 4 || degreeId == 3) &&
                view.first().getAttribute("ProcedureType") != null &&
                Integer.parseInt(view.first().getAttribute("ProcedureType").toString()) == 55) {
                s.setDisabled(false);
            }

            if (s.getValue() != null && invals(s.getValue(), 5, 46, 50, 51, 52, 54, 58, 59, 63, 64, 65, 66)) {
                s.setDisabled(false);
            }

            //1- OTHMAN open always prosedures
            if (s.getValue() != null &&
                invals(s.getValue(), 20, 103, 147, 148, 149, 150, 151, 156, 152, 153, 154, 155, 104, 19, 35, 158 , 159 , 160 , 161, 162,163,164,165,166)) // , 35
            {
                s.setDisabled(false);
            }

            if (gadwal.compareTo(new BigDecimal(3646)) == 0 || gadwal.compareTo(new BigDecimal(3647)) == 0 ||
                gadwal.compareTo(new BigDecimal(3648)) == 0) {
            // 2- re prosedures 1
            System.out.println("----------------- OTHMAN ");

            if (count21 <= 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(21)));
                if (s.getValue() != null && invals(s.getValue(), 21)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 21)) {
                    s.setDisabled(true);
                }
            }

            // 3- re prosedures 2
            System.out.println("----------------- OTHMAN ");
            if (count35 <= 0 && count21 > 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(35)));
                if (s.getValue() != null && invals(s.getValue(), -35)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), -35)) {
                    s.setDisabled(true);
                }
            }

            // 3- ta3n belnakd
            System.out.println("----------------- OTHMAN ");
            if (count20 < 2) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(20)));
                if (s.getValue() != null && invals(s.getValue(), 20)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 20)) {
                    s.setDisabled(true);
                }
            }
            //3646, 3647, 3648

            System.out.println("gadwal : " + gadwal);

            if (gadwal.compareTo(new BigDecimal(3648)) == 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(98)));
                if (s.getValue() != null && invals(s.getValue(), 98)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 98)) {
                    s.setDisabled(true);
                }
            }

            if (gadwal.compareTo(new BigDecimal(3648)) == 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(99)));
                if (s.getValue() != null && invals(s.getValue(), 99)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 99)) {
                    s.setDisabled(true);
                }
            }


            if (gadwal.compareTo(new BigDecimal(3646)) == 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(100)));
                if (s.getValue() != null && invals(s.getValue(), 100)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 100)) {
                    s.setDisabled(true);
                }
            }


            if (gadwal.compareTo(new BigDecimal(3646)) == 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(101)));
                if (s.getValue() != null && invals(s.getValue(), 101)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 101)) {
                    s.setDisabled(true);
                }
            }


            if (gadwal.compareTo(new BigDecimal(3646)) == 0) {
                    //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(102)));
                if (s.getValue() != null && invals(s.getValue(), 102)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 102)) {
                    s.setDisabled(true);
                }
            }
            }

            ViewObject searchInventoryCaseResultView = null;
            Row searchInventoryCaseResultRow = null;
            searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
            searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();
            BigDecimal daatfter = new BigDecimal(searchInventoryCaseResultRow.getAttribute("FolderType").toString());
            System.out.println("OTH daatfter : " + daatfter);

            //
            //            if (null != attributeValue) {
            //                daatfter = new BigDecimal(attributeValue.toString());
            //
            //                System.out.println("daatfter : "+daatfter);
            //            }

            if (daatfter.compareTo(new BigDecimal(22)) == 0) {
                //                System.out.println(" OTH 1 " + getPartyProceduresRep(new BigDecimal(97)));
                if (s.getValue() != null && invals(s.getValue(), 97)) {
                    s.setDisabled(false);
                }
            } else {
                if (s.getValue() != null && invals(s.getValue(), 97)) {
                    s.setDisabled(true);
                }
            }


            view.setWhereClause("PROCEDURE_TYPE in (66)");
            view.executeQuery();
            if (view.getRowCount() > 0 && invals(s.getValue(), 66)) {
                s.setDisabled(true);
            }
            int resPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());
            double result = getTotalPenaltyOrGuranteeAmount(resPartyId, 4);

            if (invals(s.getValue(), 46) && (result == 0 || result == 0.0)) {
                s.setDisabled(true);
            }
            System.err.println("attendence:" + attendence);
            System.err.println("tanazolMoarda:" + tanazolMoarda);
            System.err.println("degreeId:" + degreeId);

            if (invals(s.getValue(), 1) && attendence == 3) {
                s.setDisabled(true);
            }
            //==================================20221231===============
            if (invals(s.getValue(), 1) && 2 == degreeId) {
                s.setDisabled(false);
            }
            //20230101
            if (invals(s.getValue(), 8) && (1 == degreeId && (1 == attendence || 2 == attendence || 4 == attendence))) {
                s.setDisabled(false);
            }

            if (invals(s.getValue(), 1) && tanazolMoarda)
                s.setDisabled(false);

            if (invals(s.getValue(), 2, 8) && tanazolMoarda)
                s.setDisabled(true);

            double workAmount = 0;
            if (getAttributeValue(ACTION_ITERATOR, "WorkAmount") != null)
                workAmount = Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "WorkAmount") + "");

            System.err.println("workAmount = " + workAmount);

            //            if(invals(s.getValue(), 49) && workAmount > 0){
            //                s.setDisabled(false);
            //            }
            double penaltyAmount = 0;
            System.err.println("penaltyAmount = " + getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount"));
            if (getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount") != null &&
                Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount") + "") != 0 &&
                invals(s.getValue(), 62)) {
                penaltyAmount = Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "FixedPenaltyAmount") + "");
                s.setDisabled(false);
            }
System.out.println("");
            
            //OTHMAN
            if (caseSchema != -1 &&
                invals(s.getValue(), 31, 32, 20, 19, 7, 77, 21, 34, 37, 97, 98, 99, 100, 101, 102, 103, 147, 148, 149,
                       150, 151, 156, 152, 153,158 , 159 , 160 , 161,163,164,165,166, 154, 155, 104, 7, 5, 19, 35, 20, 21) && casTypeId != 15) // , 35
                newSelectItemList.add(s);

            if (caseSchema == -1 && !invals(s.getValue(), 21, 35) // , 35
                && casTypeId != 15)
                newSelectItemList.add(s);

            if (casTypeId == 15) {
                newSelectItemList.add(s);
            }
        }
        view.setWhereClause(null);
        view.executeQuery();
        return newSelectItemList;
    }

    private void appearProcReservationGozaya(SelectItem s, int degreeId, int attendence, int prosctionObjection,
                                             int objectionType) {
        System.out.println(prosctionObjection + "attendence is :" + attendence);
        if (degreeId == 1) //degree ola
        {
            if (attendence != 3 && attendence != 2 && attendence != 4) //attendence
            {
                System.out.println("HerRRRRRRRRRRRRRRRRRRRR");
                disableEnableSelectItem(s, 1, 7, 77, 5, 34, 37); // estanaf , sadad, eshkal
            } else {

                if (prosctionObjection == 1) {
                    System.out.println("------------prosctionObjection--------------");
                    disableEnableSelectItem(s, 7, 77, 19, 34, 37);
                } else {
                    if (attendence != 4)
                        disableEnableSelectItem(s, 1, 2, 7, 77, 5, 19, 34, 37, 13, 43,
                                                8); // moarda ,estanaf,sadad,eshkal
                    else
                        disableEnableSelectItem(s, 1, 7, 77, 5, 19, 34, 37, 13, 43, 8); // moarda ,estanaf,sadad,eshkal
                }
            }
        } else if (degreeId == 2) // degree moarda
        {
            if (objectionType == 1) { // kan lam yakon
                disableEnableSelectItem(s, 1, 8, 7, 77, 34, 37); // estanaf , moarda be eltamkeen, sadad, change address



            } else {
                disableEnableSelectItem(s, 1, 7, 77, 5, 34, 37); // estanaf ,eshkal ,sadad
            }
        } else {
            disableEnableSelectItem(s, 7, 77, 34, 37);
        }
    }

    private void appearProcReservationsKolya(SelectItem s, int degreeId, int attendence, int prosctionObjection,
                                             int objectionType, Object folderType) {
        System.out.println("prosctionObjection " + prosctionObjection);
        System.out.println("degreeId " + degreeId);
        System.out.println("folderType " + folderType);
        System.out.println("attendence " + attendence);
        if (degreeId == 1) // degree ola
        {
            if (attendence != 3) // attendence not absence
            {
                disableEnableSelectItem(s, 1, 7, 77, 19, 34, 37); // estanaf , sadad, eshkal
            } else {
                if (prosctionObjection == 1) {
                    System.out.println("DSDSDSDDSDSDSDSDSD");
                    disableEnableSelectItem(s, 7, 77, 19, 34, 37);
                } else {
                    System.err.println("DSDSDSDDSDSDSDSDSD");
                    disableEnableSelectItem(s, 1, 2, 7, 77, 5, 19, 34, 37); // moarda ,estanaf,sadad,eshkal

                }
            }
        } else if (degreeId == 2) //moarda
        {
            if (attendence != 3) // attendence not absence
            {
                disableEnableSelectItem(s, 1, 7, 77, 19, 34, 37); // estanaf , sadad, eshkal
            } else {
                disableEnableSelectItem(s, 1, 8, 7, 77, 19, 34, 37); // moarda be el tamkeen ,estanaf , sadad , eshkal
            }
        } else if (degreeId == 8) // moarda be eltamkeen  deprecated
        {
            System.out.println("$$$----");
            disableEnableSelectItem(s, 1, 7, 77, 19, 34, 37,8,23,157); // estanaf , sadad, eshkal, eshkal
        } else if (degreeId == 3) // estanaf
        {
            if (attendence != 3 && attendence != 2) // attendence not absence
            {
                System.out.println("&&&&&&&&&&&&");
                disableEnableSelectItem(s, 20, 7, 77, 19, 34, 37); // taan ,sadad , eshkal
            } else {
                disableEnableSelectItem(s, 9, 7, 77, 19, 34, 37); // taan , sadad ,moarda estanafia , eshkal
            }
        } else if (degreeId == 4) // moarda estanafia
        {
            if (attendence != 3) // attendence not absence
            {
                disableEnableSelectItem(s, 20, 7, 77, 19, 34, 37); // taan ,sadad ,eshkal
            } else {
                disableEnableSelectItem(s, 23, 20, 7, 77, 19, 34,
                                        37); // taan ,sadad , moarda estanafia be el tamkeen  ,eshkal
            }

        } else if (degreeId == 8) // tan be el nakd
        {
            System.out.println("$$$");
            disableEnableSelectItem(s, 20, 7, 77, 34, 37); // tan ,sadad
        } else {
            disableEnableSelectItem(s, 7, 77, 34, 37); // sadad
        }

        if (!invals(folderType,
                    3)) // ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½ط£آ¯ط¢طںط¢آ½
        {
            if (invals(s.getValue(), 5)) {
                s.setDisabled(false);
            }
        }
    }
    /* / Procedures Business */
    public boolean isHearingDateVisible() {
        Object actionTypeId = getAttributeValue(ACTION_ITERATOR, "ActionTypeId");
        int caseSchema =
            ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
            Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;

        int journalistFlag =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") + "") : -1;
        System.out.println(journalistFlag + "********" + getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") +
                           "*********" + actionTypeId);
        if (journalistFlag == 1 && invals(actionTypeId, 2)) // gonah sahafeen
        {
            System.out.println("****1111 false");
            return false;
        } else if (invals(actionTypeId, 1, 2, 8, 9, 23, 24, 14, 45, 68, 69,8,23, 157,159,161 ,164 , 166  ) ||
                   (invals(actionTypeId, 19) && caseSchema == -1)) {
            System.out.println("**** true......");
            return true;

        } else {
            System.out.println("**** false");
            return false;

        }
    }
    
    
    
    public Boolean isPrisonVisible() {
        ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
        if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
            view.setWhereClause("PROCEDURE_TYPE in (5)");
            view.setOrderByClause("id desc");
            view.executeQuery();
            int count = (int) ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount();

            view.setWhereClause(null);
            view.executeQuery();
            if (count >0) { 
                    view.setWhereClause(null);
                    view.executeQuery();
                 return true ;
                }
        }
  
     
        return false;
    }
    
    
    public boolean isHearingDateVisible2() {
        Object actionTypeId = getAttributeValue(ACTION_ITERATOR, "ActionTypeId");
        int caseSchema =
            ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
            Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;

        int journalistFlag =
            getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") != null ?
            Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") + "") : -1;
        System.out.println(journalistFlag + "********" + getAttributeValue(RESULT_SEARCH_ITERATOR, "JournalistFlag") +
                           "*********" + actionTypeId);
        if (journalistFlag == 1 && invals(actionTypeId, 2)) // gonah sahafeen
        {
            System.out.println("****1111 false");
            return false;
        } else if (invals(actionTypeId, 1, 2, 8, 9, 23, 24, 14, 45, 68, 69,8,23, 157 ) ||
                   (invals(actionTypeId, 19) && caseSchema == -1)) {
            System.out.println("**** true......");
            return true;

        } else {
            System.out.println("**** false");
            return false;

        }
    }

    //to appear mofraganho in eadat egraat
    public boolean isApppearLabel() {
        if (invals(getAttributeValue(ACTION_ITERATOR, "ActionTypeId"),   21, 24, 35)) { // , 35
            System.out.println("FFFFFFF OOOO");
            JSFUtils.refreshComponent("soc3");
            ADFUtil.setEL("#{pageFlowScope.actionSaved2}", 2);
            return true;
        } else {
            ADFUtil.setEL("#{pageFlowScope.actionSaved2}", 1);
            return false;
        }
    }
    // to appear executionFlag attribute
    public boolean isAccusedRequiredVisible() {
        if (invals(getAttributeValue(ACTION_ITERATOR, "ActionTypeId"), 1, 14, 11, 6, 7, 77, 4, 3, 2, 9, 8, 13, 20, 23,168,
                   19, 35, 21, 24)) { // , 35
            return true;
        } else {
            return false;
        }
    }

    /* listener of estanaf gozaia checkbox
  * empty origantity and nyabaaGozaya
  * disabled them*/
    public void estanafGozayaListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("KKKKKKK : " + valueChangeEvent.getNewValue().toString());
        if (valueChangeEvent.getNewValue() != null && (Boolean) valueChangeEvent.getNewValue() == true) {
            Boolean flg =
                (Boolean) "N".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString());
            if (flg)
                setAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa", null);
            setAttributeValue(SERCH_INVENTORY_ITERATOR, "OrigEntity", null);
        }
    }

    public boolean isNyabaaGozaiaDisabled() {
        boolean hasAbroadPriv = getUserInfo().getUserPrevMap().get(2962);
        String estanafGozaiaFlag =
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag") != null ?
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag").toString() : "N";
        boolean nyabaKolya =
            getUserInfo().getEntityDegree() != null && "39".equalsIgnoreCase(getUserInfo().getEntityDegree());
        boolean nyabaGozaya =
            getUserInfo().getEntityDegree() != null && "3".equalsIgnoreCase(getUserInfo().getEntityDegree());
        String searchTypeHasr =
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "SearchType") != null ?
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "SearchType").toString() : null;

        if (searchTypeHasr != null && !"1".equalsIgnoreCase(searchTypeHasr)) {
            if (hasAbroadPriv == true && nyabaKolya == true && "Y".equalsIgnoreCase(estanafGozaiaFlag))
                return true;
            else if (hasAbroadPriv == false && nyabaKolya == true && "Y".equalsIgnoreCase(estanafGozaiaFlag))
                return true;
            else if (hasAbroadPriv == false && nyabaKolya == false &&
                     "Y".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString()) &&
                     "Y".equalsIgnoreCase(estanafGozaiaFlag))
                return true;
            else if (hasAbroadPriv == true && nyabaKolya == true && "N".equalsIgnoreCase(estanafGozaiaFlag))
                return false;
            else if (hasAbroadPriv == false && nyabaKolya == true && "N".equalsIgnoreCase(estanafGozaiaFlag))
                return false;
            else if (hasAbroadPriv == true && nyabaGozaya == true && "N".equalsIgnoreCase(estanafGozaiaFlag))
                return false;
            else if (hasAbroadPriv == false && nyabaGozaya == true && "N".equalsIgnoreCase(estanafGozaiaFlag))
                return true;
            else
                return false;
        } else {
            if (getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId") != null &&
                invals(getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
                return true;
            }

            if (hasAbroadPriv == true)
                return false;

            else if (nyabaGozaya == true)
                return true;
            else
                return false;
        }
    }

    public boolean isOrigEntityDisabled() {
        String estanafGozaiaFlag =
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag") != null ?
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "EstanafGozayaFlag").toString() : "N";
        boolean nyabaKolya =
            getUserInfo().getEntityDegree() != null && "39".equalsIgnoreCase(getUserInfo().getEntityDegree());
        String searchTypeHasr =
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "SearchType") != null ?
            getAttributeValue(SERCH_INVENTORY_ITERATOR, "SearchType").toString() : null;

        if (searchTypeHasr != null && !"1".equalsIgnoreCase(searchTypeHasr)) {
            if ((nyabaKolya == true ||
                 "Y".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString()) &&
                 "Y".equalsIgnoreCase(estanafGozaiaFlag)))
                return true;
            else
                return false;
        } else {
            if (getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId") != null &&
                invals(getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId"), 3646, 3647, 3648, 3643)) {
                return true;
            }
            return false;
        }
    }

    public void businessCaseTypeListener(ValueChangeEvent valueChangeEvent) {
        JSFUtils.addToSession("pBCType", valueChangeEvent.getNewValue() + "");
        if (valueChangeEvent.getNewValue() != null && invals(valueChangeEvent.getNewValue(), 3646, 3647, 3648, 3643)) {
            Boolean flg =
                (Boolean) "N".equalsIgnoreCase(getAttributeValue(SERCH_INVENTORY_ITERATOR, "ErrandFlag").toString());
            if (flg)
                setAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa", null);
            setAttributeValue(SERCH_INVENTORY_ITERATOR, "OrigEntity", null);
        }
        
        
//        String BusinessCaseTypeId =             getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId") != null ?
//            getAttributeValue(SERCH_INVENTORY_ITERATOR, "BusinessCaseTypeId").toString() : null;
//        
//        String NeyabKolyaa =             getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabKolyaa") != null ?
//            getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabKolyaa").toString() : null;
//        
//        String NeyabGozayaa =             getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa") != null ?
//            getAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa").toString() : null;
//        
//        if(null == BusinessCaseTypeId || null == NeyabKolyaa || null == NeyabGozayaa ){
////            setAttributeValue(SERCH_INVENTORY_ITERATOR, "NeyabGozayaa", null);
//            setAttributeValue(SERCH_INVENTORY_ITERATOR, "OrigEntity", null);
//        }
    }

    public void setUploadFileComp(RichInputFile uploadFileComp) {
        this.uploadFileComp = uploadFileComp;
    }

    public RichInputFile getUploadFileComp() {
        return uploadFileComp;
    }


    public void setOutVisible(RichInputText outVisible) {
        this.outVisible = outVisible;
    }

    public RichInputText getOutVisible() {
        return outVisible;
    }

    public void setCancelFile(UploadedFile cancelFile) {
        this.cancelFile = cancelFile;
    }

    public UploadedFile getCancelFile() {
        return cancelFile;
    }

    public void setEditFile(UploadedFile editFile) {
        this.editFile = editFile;
    }

    public UploadedFile getEditFile() {
        return editFile;
    }
    public void setExecReport9(UploadedFile execReport9) {
        this.execReport9 = execReport9;
    }
    public void setReinstatelProcedureTemplate(UploadedFile ReinstatelProcedureTemplate) {
        this.ReinstatelProcedureTemplate = ReinstatelProcedureTemplate;
    }

    public UploadedFile getExecReport9() {
        return execReport9;
    }
    public UploadedFile getReinstatelProcedureTemplate() {
        return ReinstatelProcedureTemplate;
    }
    public void setReinstatelProcedureTemplateSecond(UploadedFile ReinstatelProcedureTemplateSecond) {
        this.ReinstatelProcedureTemplateSecond = ReinstatelProcedureTemplateSecond;
    }
    public UploadedFile getReinstatelProcedureTemplateSecond() {
        return ReinstatelProcedureTemplateSecond;
    }

    private void validateSequenceNumber() {
        OperationBinding bindingId = ADFUtil.findOperation("validateSequenceNumber");
        bindingId.getParamsMap().put("folderType", 6);
        bindingId.getParamsMap().put("repeatedFlag", getAttributeValue(ACTION_ITERATOR, "RepeatedFlg"));
        bindingId.getParamsMap().put("sequenceNumber", getAttributeValue(ACTION_ITERATOR, "SequenceNum"));
        bindingId.getParamsMap().put("year", getAttributeValue(ACTION_ITERATOR, "Year"));
        bindingId.getParamsMap().put("policenumber",
                                     getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceStationEntityId"));
        Object obj = bindingId.execute();
        if (obj != null && Integer.parseInt(obj + "") > 0) {
            setAttributeValue(ACTION_ITERATOR, "SequenceNum", null);
            showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062D\u0635\u0631 \u0645\u0648\u062C\u0648\u062F \u0633\u0627\u0628\u0642\u0627",
                                             "pit8");
        }
    }

    public void hasrYearListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setAttributeValue(ACTION_ITERATOR, "Year", valueChangeEvent.getNewValue());
        validateSequenceNumber();
    }

    public void hasrSequenceListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        setAttributeValue(ACTION_ITERATOR, "SequenceNum", valueChangeEvent.getNewValue());
        validateSequenceNumber();
    }

    private boolean isVisibleHabs() {
        if (isNull(getAttributeValue(ACTION_ITERATOR, "ImprisonmentYears")) &&
            isNull(getAttributeValue(ACTION_ITERATOR, "ImprisonmentMonths")) &&
            isNull(getAttributeValue(ACTION_ITERATOR, "ImprisonmentDays"))) {
            return true;
        }
        return false;
    }

    private boolean isNull(Object val) {
        if (val == null || "0".equalsIgnoreCase(val + "") || "".equalsIgnoreCase(val + "")) {
            return true;
        }
        return false;
    }

    public void folderGharamaListener(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println(valueChangeEvent.getNewValue().toString());
        if ("true".equalsIgnoreCase(valueChangeEvent.getNewValue().toString())) {
            setAttributeValue(ACTION_ITERATOR, "FolderGharama", "N");
            setAttributeValue(ACTION_ITERATOR, "ImprisonmentYears", 0);
            setAttributeValue(ACTION_ITERATOR, "ImprisonmentMonths", 0);
            setAttributeValue(ACTION_ITERATOR, "ImprisonmentDays", 0);
            setAttributeValue(ACTION_ITERATOR, "ImprisonMentGuranteeAmount", 0);
        } else {
            setAttributeValue(ACTION_ITERATOR, "FolderGharama", "Y");
            stPunchmentsValues();
            //            String punshment = getAttributeValue(RESULT_SEARCH_ITERATOR, "Punishment") + "";
            //            String[] vals = punshment.split(",");
            //            setAttributeValue(ACTION_ITERATOR, "ImprisonmentYears", vals[0]);
            //            setAttributeValue(ACTION_ITERATOR, "ImprisonmentMonths", vals[1]);
            //            setAttributeValue(ACTION_ITERATOR, "ImprisonmentDays", vals[2]);
            //            setAttributeValue(ACTION_ITERATOR, "ImprisonMentGuranteeAmount", vals[3]);
        }
    }
    List<SelectItem> capacityTypeList = null;

    public boolean isCapacityTypeAvailable() {
        Object actionTypeId = getAttributeValue(ACTION_ITERATOR, "ActionTypeId");
        System.err.println("LLL " + actionTypeId);
        if (invals(actionTypeId, 34, 37)) {
            return false;
        }
        return true;
    }

    public List<SelectItem> getCapacityTypeList() {
        System.out.println("-------------------------------- Oraby getCapacityTypeList---------------------------");
        System.out.println(JSFUtils.getFromSession("sessionid") + " getCapacityTypeList() method  session token is " +
                           JSFUtils.getFromSession("loginid"));
        System.out.println("---------------------------------------------------------------------");

        String guiltlessnessFlag = getAttributeValue(RESULT_SEARCH_ITERATOR, "GuiltlessnessFlag") + "";

        int degreeId = (Integer) getParamUsed()[1];
        int folderType = (Integer) getParamUsed()[5];
        Object actionTypeId = getAttributeValue(ACTION_ITERATOR, "ActionTypeId");
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory exprfact = fctx.getApplication().getExpressionFactory();
        ValueExpression vexpr = exprfact.createValueExpression(elctx, "#{bindings.CapacityTypeId.items}", Object.class);
        capacityTypeList = (List<SelectItem>) vexpr.getValue(elctx);
        List<SelectItem> newcapacityTypeList = new ArrayList<SelectItem>();
        for (SelectItem li : capacityTypeList) {
            s = getSelectItem(li);
            if ("Y".equalsIgnoreCase(guiltlessnessFlag)) { // Baraa degree 1
                disableEnableSelectItem(s, 3);
            } else if (invals(actionTypeId, 1, 20, 147, 148, 149, 150, 151, 156, 152, 153,158  , 159 , 160 , 161,163,164,165,166, 154, 155)) {
                disableEnableSelectItem(s, 1, 2, 3,10,11,12);
            } else {
                disableEnableSelectItem(s, 1, 2,10,11,12);
            }
            newcapacityTypeList.add(s);
        }
        return newcapacityTypeList;
    }

    public void yearVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.addToSession("pYear", valueChangeEvent.getNewValue() + "");
    }

    public void seqVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.addToSession("pSequence", valueChangeEvent.getNewValue() + "");
    }

    public void policeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.addToSession("pPolice", valueChangeEvent.getNewValue() + "");
    }

    public void bcTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.addToSession("pBCType", valueChangeEvent.getNewValue() + "");
    }

    public String moveToSMScreen() {
        // Add event code here...
        BigDecimal seq = (BigDecimal) JSFUtils.resolveExpression("#{sessionScope.pSequence}");
        BigDecimal year = (BigDecimal) JSFUtils.resolveExpression("#{sessionScope.pYear}");
        BigDecimal type = (BigDecimal) JSFUtils.resolveExpression("#{sessionScope.pBCType}");
        BigDecimal police = (BigDecimal) JSFUtils.resolveExpression("#{sessionScope.pPolice}");
        JSFUtils.setExpressionValue("#{sessionScope.ExcTemplateBean.source}", "SimplifiedModeBC");
        System.out.println("MOVED ---- back");
        ExcTemplate temp = new ExcTemplate();
        temp.checkDirtyDataAction();
        System.out.println("MOVED ---- back");

        JSFUtils.setExpressionValue("#{bindings.SearchType.inputValue}", new BigDecimal(1));
        JSFUtils.addToSession("pSequence", (seq));
        JSFUtils.addToSession("pYear", (year));
        JSFUtils.addToSession("pBCType", (type));
        JSFUtils.addToSession("pType", new Integer(1));
        JSFUtils.addToSession("pMoveFlg", true);
        JSFUtils.addToSession("pPolice", (police));
        //        BCDetails bc = new BCDetails();
        //        bc.ValidateBC();

        //        JSFUtils.setExpressionValue("#{bindings.SearchType.inputValue}",new BigDecimal(1));
        //        JSFUtils.addToSession("pSequence", new BigDecimal(""+JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}")));
        //        JSFUtils.addToSession("pYear", new BigDecimal(""+JSFUtils.resolveExpression("#{bindings.Year.inputValue}")));
        //        JSFUtils.addToSession("pBCType",new BigDecimal(""+JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}")));
        //        JSFUtils.addToSession("pType", new Integer(1));
        //        JSFUtils.addToSession("pMoveFlg", true);
        //        JSFUtils.addToSession("pPolice",new BigDecimal(""+JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")));
        //        InventoryBean inv = new InventoryBean();
        //        inv.doSearchInventory();
        return null;
    }

    public String validateMergeBusiness() {
        // Add event code here...
        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;
        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();


        Long partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());


        Object oo = JSFUtils.resolveExpression("#{bindings.Seq.inputValue}");
        System.out.println("************-" + oo + "-");
        setAttributeViewValue(PARAMS_ITERATOR, "partyId", partyId);
        Object rep = false;
        BigDecimal crim = new BigDecimal(JSFUtils.resolveExpression("#{bindings.CrimType.inputValue}") + "");
        if (JSFUtils.resolveExpression("#{bindings.RepeatFlag.inputValue}") != null)
            rep = JSFUtils.resolveExpression("#{bindings.RepeatFlag.inputValue}");
        OperationBinding Operation = ADFUtil.findOperation("validateMergebusiness");
        Operation.getParamsMap().put("seq",
                                     new BigDecimal(JSFUtils.resolveExpression("#{bindings.Seq.inputValue}") + ""));
        Operation.getParamsMap().put("year",
                                     new BigDecimal(JSFUtils.resolveExpression("#{bindings.Year2.inputValue}") + ""));
        Operation.getParamsMap().put("crim", crim);
        Operation.getParamsMap().put("fold",
                                     new BigDecimal(JSFUtils.resolveExpression("#{bindings.FoldType.inputValue}") +
                                                    ""));
        if (oo.toString().length() > 0 && JSFUtils.resolveExpression("#{bindings.Police.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.Police.inputValue}") != "")
            Operation.getParamsMap().put("ent",
                                         new BigDecimal(JSFUtils.resolveExpression("#{bindings.Police.inputValue}") +
                                                        ""));
        else
            Operation.getParamsMap().put("ent",
                                         new BigDecimal(JSFUtils.resolveExpression("#{bindings.Neyabaa.inputValue}") +
                                                        ""));

        Operation.getParamsMap().put("repeat", rep == "true" ? "Y" : "N");
        Operation.execute();

        JSFUtils.refreshComponent("pfl2");
        JSFUtils.refreshComponent("pg1");
        JSFUtils.refreshComponent("it32");
        JSFUtils.refreshComponent("b3");


        BigDecimal status = new BigDecimal(JSFUtils.resolveExpression("#{bindings.Status.inputValue}") + "");
        if (status.compareTo(new BigDecimal(0)) == 0) {
            System.out.println("FFFFFFFFFFFF");
            this.getMessBind().setRendered(true);
            JSFUtils.refreshComponent("aot1");
        }


        return null;
    }

    public void crimVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        BigDecimal newVal = (BigDecimal) valueChangeEvent.getNewValue();
        if (newVal.compareTo(new BigDecimal(1)) == 0) {

        } else if (newVal.compareTo(new BigDecimal(2)) == 0) {
        }
        JSFUtils.refreshComponent("pg137");
    }

    public void setMessBind(RichActiveOutputText messBind) {
        this.messBind = messBind;
    }

    public RichActiveOutputText getMessBind() {
        return messBind;
    }

    public String saveNewReservation() {
        // Add event code here...yyyy-mm-dd
        ADFContext.getCurrent().getSessionScope().put("screenType", "egra");
        ADFContext.getCurrent().getSessionScope().put("BusinessCaseType", "0");
        ADFContext.getCurrent().getSessionScope().put("GnayaatFlag", false);
        ADFContext.getCurrent().getSessionScope().put("ProsecutionJudegementFlag", new BigDecimal(1));
        try {
            String dddd = JSFUtils.resolveExpression("#{bindings.HearingDate1.inputValue}") + "";
            System.err.println("dddd-" + new Date(dddd));
            java.util.Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dddd);

            System.out.println("this.getDateHearBind().getValue()   " + this.getDateHearBind().getValue());
            System.out.println("this.getDegreeBind().getValue()   " + this.getDegreeBind().getValue());

            OperationBinding myOperation = ADFUtil.findOperation("validateCourtBCHearingDate");
            myOperation.getParamsMap().put("entityId", JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}"));
            myOperation.getParamsMap().put("hearingDate", new Date(dddd));
            myOperation.getParamsMap().put("bcDegree", new BigDecimal(this.getDegreeBind().getValue() + ""));
            Boolean result = (Boolean) myOperation.execute();

            Boolean flg = (Boolean) ADFContext.getCurrent().getSessionScope().get("addedFlag");
            System.out.println("flg   -" + flg);
            if (flg) {
                ADFUtil.findOperation("Commit1").execute();
                JSFUtils.showFacesInfoMessageFromBundle("bundle", "ADD_SUC");

                ADFUtil.findIterator("AppealCirclesLOV1Iterator").executeQuery();
                ADFUtil.findIterator("CourtCirclesDatesHearingInventory2Iterator").executeQuery();


                //                    ADFUtil.findIterator("ActionsRVO1Iterator").executeQuery();


            } else
                JSFUtils.showFacesInfoMessageFromBundle("bundle", "ADD_FAIL");


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setDegreeBind(RichSelectOneChoice degreeBind) {
        this.degreeBind = degreeBind;
    }

    public RichSelectOneChoice getDegreeBind() {
        return degreeBind;
    }

    public void setDateHearBind(RichInputDate dateHearBind) {
        this.dateHearBind = dateHearBind;
    }

    public RichInputDate getDateHearBind() {
        return dateHearBind;
    }


    public void setFileJudgeDecision(UploadedFile fileJudgeDecision) {
        this.fileJudgeDecision = fileJudgeDecision;
    }

    public UploadedFile getFileJudgeDecision() {
        return fileJudgeDecision;
    }

    public void setFileSessionRepor(UploadedFile fileSessionRepor) {
        this.fileSessionRepor = fileSessionRepor;
    }

    public UploadedFile getFileSessionRepor() {
        return fileSessionRepor;
    }

    public void setFileDocument29(UploadedFile fileDocument29) {
        this.fileDocument29 = fileDocument29;
    }

    public UploadedFile getFileDocument29() {
        return fileDocument29;
    }
    private UploadedFile fileJudgeDecision;
    private UploadedFile fileSessionRepor;
    private UploadedFile fileDocument29;


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

            //            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            //            Row businessCaseRow = businessCaseView.getCurrentRow();
            //
            //            ViewObject courtBcHearingResPartiesView =
            //                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            //            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();

            insertIntoCriminalJusticeFilesDetailsVO(null, DID, DName, filName, new BigDecimal(12), null, null);


            ADFUtil.findIterator("PartyFilesRVO2Iterator").executeQuery();

            RichInputFile in = (RichInputFile) JSFUtils.findComponentInRoot("if50");
            in.setValue("");

            //            JSFUtils.setExpressionValue("#{pageFlowScope.inventoryBean.fileDocument29}", "");
            //            JSFUtils.refreshComponent("if7");
            saveMessage();
            System.out.println("UUUUUUUUUUUUUUUUu");

            //            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            //            BigDecimal businessCaseDetailId = businessCaseId;
            //            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            //            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            //            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            //            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId),
            //                                                       DID, DName, new BigDecimal(entityId), new BigDecimal(partyId),
            //                                                       filName, new BigDecimal(12), null, null,null);
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

            //            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            //            Row businessCaseRow = businessCaseView.getCurrentRow();
            //
            //            ViewObject courtBcHearingResPartiesView =
            //                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            //            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();


            insertIntoCriminalJusticeFilesDetailsVO(null, DID, DName, filName, new BigDecimal(14), null, null);


            ADFUtil.findIterator("PartyFilesRVO2Iterator").executeQuery();
            RichInputFile in = (RichInputFile) JSFUtils.findComponentInRoot("if20");
            in.setValue("");
            JSFUtils.setExpressionValue("#{pageFlowScope.inventoryBean.fileJudgeDecision}", " ");
            JSFUtils.refreshComponent("if20");
            saveMessage();
            System.out.println("UUUUUUUUUUUUUUUUu");


            //            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            //            BigDecimal businessCaseDetailId =businessCaseId;
            //            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            //            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            //            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());
            //            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId),
            //                                                       DID, DName, new BigDecimal(entityId), new BigDecimal(partyId),
            //                                                       filName, new BigDecimal(14), null, null,null);
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

            //            ViewObject businessCaseView = ADFUtil.findIterator("BusinessCase1Iterator").getViewObject();
            //            Row businessCaseRow = businessCaseView.getCurrentRow();
            //
            //            ViewObject courtBcHearingResPartiesView =
            //                ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getViewObject();
            //            Row courtBcHearingResPartiesRow = courtBcHearingResPartiesView.getCurrentRow();

            String filName = uf.getFilename();
            //            BigDecimal businessCaseId = (BigDecimal) businessCaseRow.getAttribute("Id");
            //            BigDecimal businessCaseDetailId = businessCaseId;
            int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            //            int entityId = Integer.parseInt(businessCaseRow.getAttribute("EntityId").toString());
            //            long partyId = Long.parseLong(courtBcHearingResPartiesRow.getAttribute("PartyId").toString());


            insertIntoCriminalJusticeFilesDetailsVO(null, DID, DName, filName, new BigDecimal(33), null, null);


            ADFUtil.findIterator("PartyFilesRVO2Iterator").executeQuery();
            //            JSFUtils.setExpressionValue("#{pageFlowScope.inventoryBean.fileDocument29}", "");
            //            JSFUtils.refreshComponent("if7");

            RichInputFile in = (RichInputFile) JSFUtils.findComponentInRoot("if7");
            in.setValue("");

            saveMessage();
            System.out.println("UUUUUUUUUUUUUUUUu");

            //            insertAttacheFile(businessCaseDetailId, businessCaseId, new BigDecimal(userId),
            //                                                       DID, DName, new BigDecimal(entityId), new BigDecimal(partyId),
            //                                                       filName, new BigDecimal(33), null, null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJdgeFile(RichInputFile jdgeFile) {
        this.jdgeFile = jdgeFile;
    }

    public RichInputFile getJdgeFile() {
        return jdgeFile;
    }


    public void setName(String fullName) {
        String name = fullName.trim();
        System.out.println("**************-" + name + "-");

        String[] args = name.split(" ");
        if (args.length > 0) {
            String fName = args[0];
            System.out.println("fName ...>" + fName);
            JSFUtils.setExpressionValue("#{bindings.Fname.inputValue}", fName);
        } else {
            JSFUtils.setExpressionValue("#{bindings.Fname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Sname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Tname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", "");

        }

        if (args.length > 1) {
            String sName = args[1];
            System.out.println("sName ...>" + sName);
            JSFUtils.setExpressionValue("#{bindings.Sname.inputValue}", sName);
        } else {
            JSFUtils.setExpressionValue("#{bindings.Sname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Tname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", "");

        }
        if (args.length > 2) {
            String tName = args[2];
            System.out.println("tName ...>" + tName);
            JSFUtils.setExpressionValue("#{bindings.Tname.inputValue}", tName);
        } else {
            JSFUtils.setExpressionValue("#{bindings.Tname.inputValue}", "");
            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", "");

        }
        if (args.length > 3) {
            String lName = args[3];
            System.out.println("lName ...>" + lName);
            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", lName);
        } else {

            JSFUtils.setExpressionValue("#{bindings.Fourthname.inputValue}", "");

        }
    }

    public void fullNameVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        String fullName = (String) valueChangeEvent.getNewValue();
        setName(fullName);
        JSFUtils.setExpressionValue("#{bindings.PartyName1.inputValue}", fullName);
    }

    public void setRefreshComp(Boolean refreshComp) {
        this.refreshComp = refreshComp;
    }

    public Boolean getRefreshComp() {
        return refreshComp;
    }

    public void setSelectItemList(List<SelectItem> selectItemList) {
        this.selectItemList = selectItemList;
    }

    public void capacityTypeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("valueChangeEvent******-----*******" + valueChangeEvent.getNewValue());
        //        BigDecimal val = (BigDecimal) valueChangeEvent.getNewValue();
        //        if (val.compareTo(new BigDecimal(2))==0){
        //         RichPanelGroupLayout pg = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl83");
        //            pg.setRendered(true);
        //            JSFUtils.refreshComponent("pgl83");
        //        }

    }

    public String showpopup() {
        // Add event code here...
        JSFUtils.showPopup("p2");
        return null;
    }

    public void egraaDate(ValueChangeEvent valueChangeEvent) {
        System.out.println("egraaDate = " + ADFContext.getCurrent().getSessionScope().get("egraaDate"));
        System.out.println("entityId = " + ADFContext.getCurrent().getSessionScope().get("policeId"));
        System.out.println("postponed = " + ADFContext.getCurrent().getSessionScope().get("postponed"));
        System.out.println("prevDate = " + ADFContext.getCurrent().getSessionScope().get("hearingDateId"));

        if (valueChangeEvent.getNewValue() != null)
            ADFUtil.setEL("#{sessionScope.hearingDateId}", ADFContext.getCurrent().getSessionScope().get("egraaDate"));
        System.out.println("prevDate222 = " + ADFContext.getCurrent().getSessionScope().get("hearingDateId"));
           RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc4");

           RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
           RichInputDate startDate = (RichInputDate) JSFUtils.findComponentInRoot("it43");
    //        BigDecimal feesTypeId = null;
    //        if (getAttributeValue(ACTION_ITERATOR, "FeesType") != null)
    //            feesTypeId = new BigDecimal(getAttributeValue(ACTION_ITERATOR, "FeesType").toString());
    //       if (sc.getValue() != null && !sc.getValue().equals("") && Integer.parseInt(sc.getValue() + "") == 7){

        RichSelectOneChoice soc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc5");

        if (soc.getValue() != null && !soc.getValue().equals("") && Integer.parseInt(soc.getValue() + "") == 67) {
           if (valueChangeEvent.getNewValue() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date egraaDate = null;
                try {
                    egraaDate = sdf.parse(valueChangeEvent.getNewValue() + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.setTime(egraaDate);
                c.add(Calendar.MONTH, 1);
                java.util.Date egraDatePlusOne = c.getTime();
                RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id15");
                inpDate.setValue(egraDatePlusOne);


            }

        }


        if (valueChangeEvent.getNewValue() != null) {
               ADFUtil.setEL("#{sessionScope.egraaDate}", valueChangeEvent.getNewValue().toString());
            System.err.println("startDAte = " + startDate.getValue());
            System.err.println("egraaDate = " + valueChangeEvent.getNewValue());
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
               BigDecimal days = new BigDecimal(0);
               java.util.Date egraDate = null;
               java.util.Date stDate = null;
            if (sc.getValue() != null && !sc.getValue().equals("") && Integer.parseInt(sc.getValue() + "") == 7) {

               try {
                   egraDate = sdf.parse(valueChangeEvent.getNewValue() + "");
                   if (startDate.getValue() != null)
                        stDate = sdf.parse(startDate.getValue() + "");
               } catch (Exception e) {
                   e.printStackTrace();
               }

                if (stDate != null) {
               long diff = stDate.getTime() - egraDate.getTime();
               long diffDays = diff / (24 * 60 * 60 * 1000);
               days = new BigDecimal(diffDays);
                    System.err.println("Days = " + days);

                    if (days.equals(new BigDecimal(0)) || days.compareTo(new BigDecimal(0)) == 1) {
                   ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0628\u062f\u0621 \u062d\u0633\u0627\u0628 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                            FacesMessage.SEVERITY_ERROR);
                   gharamaYawmeya = 0;
                   RichInputDate input = (RichInputDate) JSFUtils.findComponentInRoot("id8");
                   input.setValue(null);
                    } else
                   gharamaYawmeyaCalculation(valueChangeEvent.getNewValue().toString());

           } else if (inp.getValue() == null)
               gharamaYawmeya = 0;
           }
     }
       }

    public void gharamaEndDateListener(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null)
            gharamaYawmeyaEndDateCaculation(valueChangeEvent.getNewValue() + "");
    }

    public void gharamaYawmeyaEndDateCaculation(String value) {
        java.util.Date penaltyDueDate = null;
        java.util.Date gharamaEndDate = null;
        BigDecimal days = new BigDecimal(0);
        //        String nwValue = "";
        RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id8");
        try {
            if (value != null) {
        //                nwValue = dateConvert(delDate(valueChangeEvent.getNewValue().toString()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                gharamaEndDate = sdf.parse(value);
                penaltyDueDate = sdf.parse(ADFUtil.evaluateEL("#{pageFlowScope.penaltyDueDate}") + "");
                System.err.println("penaltyDueDate = " + penaltyDueDate);
                System.err.println("gharamaEndDate = " + gharamaEndDate);
                long diff = gharamaEndDate.getTime() - penaltyDueDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                days = new BigDecimal(diffDays);
                System.err.println("DAYS = " + days);
                double workAmount = 0;
                if (getAttributeValue(ACTION_ITERATOR, "WorkAmount") != null)
                    workAmount = Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "WorkAmount").toString());
                double penalty = workAmount * Integer.parseInt(days + "");
                System.err.println("penalty = " + penalty);
                double paid = 0;
                int folderId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderId") + "");
                System.err.println("FOLDER ID = " + folderId);
                ViewObjectImpl excFolderActions =
                    (ViewObjectImpl) ADFUtil.findIterator("ExcFolderActionsVO3Iterator").getViewObject();
                ViewObjectImpl excFolderProcedures =
                    (ViewObjectImpl) ADFUtil.findIterator("ExcFolderProceduresViewVO1Iterator").getViewObject();
                excFolderActions.setWhereClause("(ACTION_TYPE = 7 or ACTION_TYPE = 77) AND FOLDER_RECORD_ID = " +
                                                folderId);
                excFolderActions.executeQuery();
                System.err.println(excFolderActions.getQuery());
                RowSetIterator rsi = excFolderActions.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row excFolderActionsRow = rsi.next();
                    int procedureId = Integer.parseInt(excFolderActionsRow.getAttribute("ProcedureId").toString());
                    excFolderProcedures.setWhereClause("ID = " + procedureId +
                                                       " AND FEES_TYPE_ID = 7 and NUMBER_PAID is not null ");
                    excFolderProcedures.executeQuery();
                    System.err.println(excFolderProcedures.getQuery());
                    RowSetIterator rsiProc = excFolderProcedures.createRowSetIterator(null);
                    while (rsiProc.hasNext()) {
                        Row excFolderProceduresRow = rsiProc.next();
                        if (excFolderProceduresRow.getAttribute("AdvancePaymentAmount") != null)
                            paid +=
                                Double.parseDouble(excFolderProceduresRow.getAttribute("AdvancePaymentAmount") + "");
                    }
                }
                System.err.println("paid = " + paid);
                gharamaYawmeya = penalty - paid;

                if (getAttributeValue(ACTION_ITERATOR, "PaymentAmount") != null)
                    gharamaYawmeyaRemain =
                        gharamaYawmeya - Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "PaymentAmount") + "");
            } else if (inp.getValue() == null)
                gharamaYawmeya = 0;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String delDate(String conDate) {
        //    String myDate = "Tue Nov 18 00:00:00 GMT 2014";
        String strMnth = conDate.substring(4, 7);
        String day = conDate.substring(8, 10);
        String year = conDate.substring(24, 28);
        String date = day + "-" + strMnth + "-" + year;

        return date;
    }

    public void setGharamaYawmeya(double gharamaYawmeya) {
        this.gharamaYawmeya = gharamaYawmeya;
    }

    public double getGharamaYawmeya() {
        return gharamaYawmeya;
    }

    public void ghramVCL(ValueChangeEvent valueChangeEvent) {

        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
        RichInputText payment = (RichInputText) JSFUtils.findComponentInRoot("it44");
        RichInputDate inpEgraa = (RichInputDate) JSFUtils.findComponentInRoot("id8");
        if (inp.getValue() != null && Boolean.parseBoolean(valueChangeEvent.getNewValue() + "")) {
            gharamaYawmeyaEndDateCaculation(inp.getValue().toString());
            inp.setVisible(true);
        } else if (!Boolean.parseBoolean(valueChangeEvent.getNewValue() + ""))
            inp.setVisible(false);
        else if (Boolean.parseBoolean(valueChangeEvent.getNewValue() + ""))
            inp.setVisible(true);
        //            inp.setValue(null);
        //        else if (inpEgraa.getValue() == null){
        //            gharamaYawmeyaRemain = 0;
        //            gharamaYawmeya = 0;
        //            payment.setValue(null);
        //        }

        System.err.println("ghramVCL " + valueChangeEvent.getNewValue());
        try {
            if (inpEgraa.getValue() != null) {
                if (!Boolean.parseBoolean(valueChangeEvent.getNewValue() + "")) {
                 if (inpEgraa.getValue() != null)
                        gharamaYawmeyaCalculation(inpEgraa.getValue() + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    public void taqseetGharamaRemainVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it55");
            if (inp != null) {
                if (Double.parseDouble(valueChangeEvent.getNewValue() + "") > Double.parseDouble(inp.getValue() + "")) {
                    ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u062f\u0641\u0648\u0639 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0642\u0644 \u0645\u0646 \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a",
                                        FacesMessage.SEVERITY_ERROR);
                    RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it56");
                    input.setValue(null);
                    taqseetGharamaRemain = 0;
                } else {
                    taqseetGharamaRemain =
                        Double.parseDouble(inp.getValue() + "") -
                        Double.parseDouble(valueChangeEvent.getNewValue() + "");
                    if (taqseetGharamaRemain > 0) {
                        RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it58");
                        input.setValue(taqseetGharamaRemain /
                                       Integer.parseInt(ADFUtil.evaluateEL("#{bindings.ExcLkpGharamaInstallments1.inputValue}").toString()));
                    }
                }
            }
        }
    }

    public void taqseetGharamaInstallmentsVCL(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it57");
            RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it58");
            if (inp != null && Double.parseDouble(inp.getValue() + "") > 0 &&
                Integer.parseInt(valueChangeEvent.getNewValue() + "") != 0) {
                input.setValue(Double.parseDouble(inp.getValue() + "") /
                               Integer.parseInt(valueChangeEvent.getNewValue() + ""));
            } else if (Integer.parseInt(valueChangeEvent.getNewValue() + "") == 0)
                input.setValue(0);
        }
    }

    public void gharamaRemain(ValueChangeEvent valueChangeEvent) {
        if (valueChangeEvent.getNewValue() != null) {
            RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("ot39");
            if (inp != null) {
                if (Double.parseDouble(valueChangeEvent.getNewValue() + "") > Double.parseDouble(inp.getValue() + "")) {
                    ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0642\u0644 \u0627\u0648 \u064a\u0633\u0627\u0648\u064a \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                        FacesMessage.SEVERITY_ERROR);
                    RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it44");
                    input.setValue(null);
                    gharamaYawmeyaRemain = 0;
                } else
                    gharamaYawmeyaRemain =
                        Double.parseDouble(inp.getValue() + "") -
                        Double.parseDouble(valueChangeEvent.getNewValue() + "");
            }
        }
    }

    public void setGharamaYawmeyaRemain(double gharamaYawmeyaRemain) {
        this.gharamaYawmeyaRemain = gharamaYawmeyaRemain;
    }

    public double getGharamaYawmeyaRemain() {
        return gharamaYawmeyaRemain;
    }

    public void gharamaYawmeyaCalculation(String value) {
        //        RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
        RichSelectBooleanCheckbox checkBox = (RichSelectBooleanCheckbox) JSFUtils.findComponentInRoot("sbc4");
        java.util.Date egraaEndDate = null;
        java.util.Date penaltyDueDate = null;
        BigDecimal days = new BigDecimal(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nwValue = value;
        try {
            if (!Boolean.parseBoolean(checkBox.getValue() + "")) {
                penaltyDueDate = sdf.parse(ADFUtil.evaluateEL("#{pageFlowScope.penaltyDueDate}") + "");
                egraaEndDate = sdf.parse(nwValue);
                System.err.println("penaltyDueDate = " + penaltyDueDate);
                System.err.println("gharamaEndDate = " + egraaEndDate);
                long diff = egraaEndDate.getTime() - penaltyDueDate.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);
                days = new BigDecimal(diffDays);
                System.err.println("DAYS = " + days);
                double workAmount = 0;
                if (getAttributeValue(ACTION_ITERATOR, "WorkAmount") != null)
                    workAmount = Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "WorkAmount").toString());
                double penalty = workAmount * Integer.parseInt(days + "");
                System.err.println("penalty = " + penalty);
                int paid = 0;
                int folderId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "FolderId") + "");
                System.err.println("FOLDER ID = " + folderId);
                ViewObjectImpl excFolderActions =
                    (ViewObjectImpl) ADFUtil.findIterator("ExcFolderActionsVO3Iterator").getViewObject();
                ViewObjectImpl excFolderProcedures =
                    (ViewObjectImpl) ADFUtil.findIterator("ExcFolderProceduresViewVO1Iterator").getViewObject();
                excFolderActions.setWhereClause("(ACTION_TYPE = 7 or ACTION_TYPE = 77) AND FOLDER_RECORD_ID = " +
                                                folderId);
                excFolderActions.executeQuery();
                RowSetIterator rsi = excFolderActions.createRowSetIterator(null);
                while (rsi.hasNext()) {
                    Row excFolderActionsRow = rsi.next();
                    int procedureId = Integer.parseInt(excFolderActionsRow.getAttribute("ProcedureId").toString());
                    excFolderProcedures.setWhereClause("ID = " + procedureId +
                                                       " AND FEES_TYPE_ID = 7 and NUMBER_PAID is not null ");
                    excFolderProcedures.executeQuery();
                    RowSetIterator rsiProc = excFolderProcedures.createRowSetIterator(null);
                    while (rsiProc.hasNext()) {
                        Row excFolderProceduresRow = rsiProc.next();
                        paid +=
                            Integer.parseInt(excFolderProceduresRow.getAttribute("AdvancePaymentAmount").toString());
                    }
                }
                System.err.println("paid = " + paid);
                gharamaYawmeya = penalty - paid;

                if (getAttributeValue(ACTION_ITERATOR, "PaymentAmount") != null)
                    gharamaYawmeyaRemain =
                        gharamaYawmeya - Double.parseDouble(getAttributeValue(ACTION_ITERATOR, "PaymentAmount") + "");
                else
                    gharamaYawmeyaRemain = gharamaYawmeya;
            }
            System.out.println("gharamaYawmeyaRemain  is " + gharamaYawmeyaRemain);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appealPPO(ActionEvent actionEvent) {
           // Add event code here...
        int actionType = 1;
        int capacityType = 3;
        String notes = "";

        RichInputText note = (RichInputText) JSFUtils.findComponentInRoot("it75");

        System.out.println("***capacity  notes------  " +
                           ((RichInputText) JSFUtils.findComponentInRoot("it75")).getValue());
        if (note.getValue() != null)
            notes = note.getValue() + "";

        RichSelectOneChoice capacity = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc333");

        System.out.println("***capacity  notes------  " +
                           ((RichSelectOneChoice) JSFUtils.findComponentInRoot("soc333")).getValue());
        if (capacity.getValue() != null && (capacity.getValue() + "").length() > 0)
            capacityType = Integer.parseInt(capacity.getValue() + "");

        if (capacityType == 1 || capacityType == 2)
            capacityType = 3;

           RichSelectOneChoice action = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc19");
        System.out.println("***" + action.getValue());
        if (action.getValue() != null)
            actionType = Integer.parseInt(action.getValue() + "");

        if (actionType == 152 || (actionType == 20 || validateHearingDatePPO())) {

                   ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
                   Row actionsRow = actionsView.getCurrentRow();
                   long   businessCaseId = 0;
            //                       capacityType = Integer.parseInt(actionsRow.getAttribute("CapacityTypeId").toString()+"");
            System.out.println(ADFUtil.evaluateEL("#{pageFlowScope.capacityTypeId}") + "***capacity " + capacityType);
                   long hearingDateId =
                       Long.parseLong(actionsRow.getAttribute("HearingDateId") == null ? "0" :
                                      actionsRow.getAttribute("HearingDateId").toString());
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));

                       BigDecimal nnn = (BigDecimal) ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}");
            if (nnn.compareTo(new BigDecimal(6)) != 0)
                ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
                       else
                ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabGozayaa.inputValue}"));
            System.out.println(" hearingDateId is  " + hearingDateId);


               ViewObject vo = ADFUtil.findIterator("ResPartiesGozayaaROVO1Iterator").getViewObject();
                   RowSetIterator vc = vo.createRowSetIterator(null);
            Boolean flag = false;
            while (vc.hasNext()) {
                   Row next = vc.next();
                System.out.println(next.getAttribute("SelectedFlag"));

                if (next.getAttribute("SelectedFlag") != null)
                    flag = (Boolean) next.getAttribute("SelectedFlag");
                if (flag) {
                               businessCaseId = Long.parseLong(next.getAttribute("BusinessCaseId").toString());
                               
                    System.out.println("businessCaseId is " + businessCaseId);
                    break;
                           }
               }

            if (flag) {
                                  int countHearingId = checkHearingDate(businessCaseId, hearingDateId);
                       if (countHearingId != 0) {

                    System.out.println(countHearingId);

                       }

                       int userId = 0;
                       if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
                           userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
                       }


                       int loggedInEntityId = 0;
                if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                    39 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
                           loggedInEntityId =
                        Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
                } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                           3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
                           loggedInEntityId =
                               Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
                       }
                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                       OperationBinding bindingId = ADFUtil.findOperation("savePPO");
                       bindingId.getParamsMap().put("hearingDateId", hearingDateId);  // long
                       bindingId.getParamsMap().put("businessCaseId", businessCaseId);
                bindingId.getParamsMap().put("resID", new BigDecimal(countHearingId)); // dec
                   try {
                    bindingId.getParamsMap().put("actionDate",
                                                 getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                } catch (Exception e) {
                   }
                   ;
                       bindingId.getParamsMap().put("userId", userId);
                       bindingId.getParamsMap().put("entityId", loggedInEntityId);
                       bindingId.getParamsMap().put("actionType", actionType);
                       bindingId.getParamsMap().put("capacityType", capacityType);
                       bindingId.getParamsMap().put("notes", notes);
                       Object obj = bindingId.execute();


                                  saveDataWithMessage();
            } else {
                           ADFUtil.showMessage("\u0628\u0631\u062c\u0627\u0621 \u0625\u062e\u062a\u064a\u0627\u0631 \u0627\u0644\u0627\u0637\u0631\u0627\u0641",
                                               FacesMessage.SEVERITY_ERROR);
                       }

                   }

    }


    public int getPartyProceduresRep(BigDecimal procedureId) {
        System.err.println("procedureId :********** " + procedureId);
        int res = 0;
        BigDecimal resPartyId = new BigDecimal(0);
        //        Object partyIdAttr = ADFUtil.getAttributeValue("ResPartyId");
        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;
        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();
        resPartyId = new BigDecimal(searchInventoryCaseResultRow.getAttribute("ResPartyId").toString());
        System.out.println("OTH resPartyIdBD : " + resPartyId);
        // get party Procedures
        DCIteratorBinding partyProceduresItr = ADFUtil.findIterator("PartyProceduresRVOIterator");
        ViewObject object = partyProceduresItr.getViewObject();
    //        object.setWhereClause("RES_PARTY_ID = " + resPartyId + " AND PROCEDURE_TYPE = " + procedureId);
    //        object.executeQuery();
    //        int count = (int) object.getRowCount();
    //        System.out.println("object.getEstimatedRowCount() = " + count);
    //        if (null != object && count > 0) {
    //            res = count;
    //        }
    //        object.setWhereClause(null);
        
    //        --------------------------------------------
        if(procedureId.compareTo(new BigDecimal("21")) == 0 || procedureId.compareTo(new BigDecimal("35")) == 0){
        BigDecimal PartyId = new BigDecimal(0);
                PartyId = new BigDecimal(searchInventoryCaseResultRow.getAttribute("PartyId").toString());
        System.out.println("OTH PartyIdBD : " + PartyId);
        object.setWhereClause("PARTY_ID = " + PartyId + " AND PROCEDURE_TYPE = " + procedureId);
        object.executeQuery();
        int count2 = (int) object.getRowCount();
        System.out.println("object.getEstimatedRowCount() count2= " + count2);
        if (null != object && count2 > 0) {
            res = count2;
        }
        object.setWhereClause(null);
        }else{
            object.setWhereClause("RES_PARTY_ID = " + resPartyId + " AND PROCEDURE_TYPE = " + procedureId);
            object.executeQuery();
            int count = (int) object.getRowCount();
            System.out.println("object.getEstimatedRowCount() = " + count);
            if (null != object && count > 0) {
                res = count;
            }
            object.setWhereClause(null);  
        }
    //        --------------------------------------------
        //        object.executeQuery();
        return res;
    }

    public BigDecimal getGadwal() {
        //        Object attributeValue = ADFUtil.getAttributeValue("BusinessCaseTypeId");
        Object attributeValue = ADFUtil.getElExpression("#{pageFlowScope.caseSchema}");
        BigDecimal gadwal = new BigDecimal(0);
        if (null != attributeValue) {
            gadwal = new BigDecimal(attributeValue.toString());
            System.out.println(" gadwal : " + gadwal);
        }
        return gadwal;
    }

    public void appealPlaceVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                System.out.println("valueChangeEvent : " + valueChangeEvent.getNewValue());
        DCIteratorBinding itr = ADFUtil.findIterator("AppealPlaceStaticLKPIterator");
        //        Row currentRow = itr.getCurrentRow();
        //        RichPanelGroupLayout isn = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pgl100");
        //
        //        if (null != currentRow) {
        //            BigDecimal id = new BigDecimal(currentRow.getAttribute("Id").toString());
        //            System.err.println("Othman >>> id="+id);
        //            if (id.compareTo(new BigDecimal(2)) == 0) {
        //                System.err.println("Othman >>> 222="+id.compareTo(new BigDecimal(2)));
        //             //   getHidePanals().setVisible(true);
        //                isn.setVisible(true);
        //                ADFUtil.refreshComponent("pgl100");
        //            }
        //
        //
        //        }


    }

    public void setHidePanals(RichPanelGroupLayout hidePanals) {
        this.hidePanals = hidePanals;
    }

    public RichPanelGroupLayout getHidePanals() {
        return hidePanals;
    }


    private void enableDiasleDecDepOrd() {
        boolean isSequenceNumberFound = checkRowsInExcCourtBcChrgDecDepOrdVO();
        if (isSequenceNumberFound && s.getValue() != null &&
            (invals(s.getValue(), 87) /* || invals(s.getValue(), 88) */ || invals(s.getValue(), 89) ||
             invals(s.getValue(), 90))) {
            //                            disableEnableSelectItem(s, 87, 88,89,90);
            s.setDisabled(false);
       }
    }

    private boolean checkRowsInExcCourtBcChrgDecDepOrdVO() {
        DCIteratorBinding iterator = ADFUtil.findIterator("ExcCourtBcChrgDecDepOrdVO3Iterator");
        Row[] allRowsInRange = iterator.getAllRowsInRange();
        boolean isSequenceNumberFound = false;
        if (allRowsInRange.length > 0) {

            for (Row row : allRowsInRange) {
                Object seq = row.getAttribute("SequenceNumber");
                if (null != seq && Integer.parseInt(seq.toString()) > 0) {
                    isSequenceNumberFound = true;
            }
        }
        }
        return isSequenceNumberFound;
    }

    public void amrTashgeelDays(ValueChangeEvent valueChangeEvent) {
        RichInputDate fromDate = (RichInputDate) JSFUtils.findComponentInRoot("id12");
        RichInputDate toDate = (RichInputDate) JSFUtils.findComponentInRoot("id13");
        java.util.Date fDate = null;
        java.util.Date tDate = null;
        oracle.jbo.domain.Date fDDate = null;
        oracle.jbo.domain.Date tDDate = null;

        BigDecimal days = new BigDecimal(0);

        //        SimpleDateFormat sdf=new SimpleDateFormat("E MMM dd hh:mm:ss Z yyyy");
        //        System.err.println("fromDate.getValue() = "+fromDate.getValue());
        if (fromDate.getValue() != null && toDate.getValue() != null) {
            try {
                fDDate = (oracle.jbo.domain.Date) fromDate.getValue();
                tDDate = (oracle.jbo.domain.Date) toDate.getValue();
                fDate = convertDomainDateToUtilDate(fDDate);
                tDate = convertDomainDateToUtilDate(tDDate);

            } catch (Exception e) {
                e.printStackTrace();
            }


            //        long diff = tDate.getTime() - fDate.getTime();
            //        long diffDays = diff / (24 * 60 * 60 * 1000);
            //            System.err.println("fDate = "+fDate);
            //            System.err.println("tDate = "+tDate);
        days = getDays(fDate, tDate);
        RichInputText paid = (RichInputText) JSFUtils.findComponentInRoot("it52");
        RichInputText remaining = (RichInputText) JSFUtils.findComponentInRoot("it53");
        RichInputText total = (RichInputText) JSFUtils.findComponentInRoot("it48");

            if (Integer.parseInt(days + "") > 90) {
            tashgeelDays = 0;
            ADFUtil.showMessage("\u0639\u062f\u062f \u0627\u0644\u0627\u064a\u0627\u0645 \u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u0646 \u064a\u0632\u064a\u062f \u0639\u0646 \u0039\u0030 \u064a\u0648\u0645",
                                FacesMessage.SEVERITY_ERROR);
            toDate.setValue(null);
            paid.setValue(0);
            remaining.setValue(0);
            } else if (Integer.parseInt(days + "") < 1) {
            tashgeelDays = 0;
            ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0649 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0645\u0646",
                                FacesMessage.SEVERITY_ERROR);
            toDate.setValue(null);
            paid.setValue(0);
            remaining.setValue(0);
            } else {
                tashgeelDays = Integer.parseInt(days + "");
            paid.setValue(tashgeelDays * 5);
                if (total.getValue() != null) {
                    double remain = Double.parseDouble(total.getValue() + "") - (tashgeelDays * 5);
                    if (remain < 0) {
                    tashgeelDays = 0;
                    ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u062f\u0641\u0648\u0639 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0642\u0644 \u0645\u0646 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                        FacesMessage.SEVERITY_ERROR);
                    toDate.setValue(null);
                    paid.setValue(0);
                    remaining.setValue(0);
                    } else
                    remaining.setValue(remain);

        }
        }

        }
    }

    public void setTashgeelDays(int tashgeelDays) {
        this.tashgeelDays = tashgeelDays;
    }

    public int getTashgeelDays() {
        return tashgeelDays;
    }

    public BigDecimal getDays(java.util.Date start, java.util.Date releaseDate) {
            BigDecimal days = new BigDecimal(0);
            if (start != null && releaseDate != null) {
                try {
                    // Custom date format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date getStart = dateFormat.parse(dateFormat.format(start).toString());
                    java.util.Date getReleaseDate = dateFormat.parse(dateFormat.format(releaseDate).toString());
                    System.err.println("getStart >>> " + getStart + "||||| getReleaseDate>>>>>" + getReleaseDate);
                    long diff = getReleaseDate.getTime() - getStart.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);
                    days = new BigDecimal(diffDays);
                    System.err.println("days >>>>> >>> " + days);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (null != days) {
                return days.add(new BigDecimal(1));
            } else
                return days;

        }

    public static java.util.Date convertDomainDateToUtilDate(oracle.jbo.domain.Date domainDate) {
        if (domainDate != null) {
            return domainDate.getValue();
        }
        return null;
    }

    public void feesTypeGharamaVCL(ValueChangeEvent valueChangeEvent) {
        RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it49");
        inp.setValue(null);
        RichInputText input = (RichInputText) JSFUtils.findComponentInRoot("it50");
        input.setValue(null);
        RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id14");
        inpDate.setValue(null);
    }

    public void setTaqseetGharamaRemain(double taqseetGharamaRemain) {
        this.taqseetGharamaRemain = taqseetGharamaRemain;
    }

    public double getTaqseetGharamaRemain() {
        return taqseetGharamaRemain;
    }

    public void deliveryDateVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("deliveryDateVCL >>" + valueChangeEvent.getNewValue());
    }

    public void startDateVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("startDateVCL >>" + valueChangeEvent.getNewValue());

    }

    public void endDateVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("endDateVCL >>" + valueChangeEvent.getNewValue());
        boolean dateValidation =
            compareDates((Date) getStartDateBind().getValue(), (Date) valueChangeEvent.getNewValue());
        if (dateValidation == false) {
                  JSFUtils.addFacesErrorMessage("\u064a\u062c\u0628 \u0625\u062f\u062e\u0627\u0644 \u062a\u0627\u0631\u064a\u062e \u0644\u0627\u062d\u0642 \u0645\u0646 \u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0628\u062f\u0627\u064a\u0629");
              }
    }

    public void notesVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("notesVCL >>" + valueChangeEvent.getNewValue());

    }

    public void setEndDateBind(RichInputDate endDateBind) {
        this.endDateBind = endDateBind;
    }

    public RichInputDate getEndDateBind() {
        return endDateBind;
    }

    public void setStartDateBind(RichInputDate startDateBind) {
        this.startDateBind = startDateBind;
    }

    public RichInputDate getStartDateBind() {
        return startDateBind;
    }

    public void setDeliveryDateBind(RichInputDate deliveryDateBind) {
        this.deliveryDateBind = deliveryDateBind;
    }

    public RichInputDate getDeliveryDateBind() {
        return deliveryDateBind;
    }

    public void setActionNotesBind(RichInputText actionNotesBind) {
        this.actionNotesBind = actionNotesBind;
    }

    public RichInputText getActionNotesBind() {
        return actionNotesBind;
    }

    public void guardianNameVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("guardianNameVCL >>" + valueChangeEvent.getNewValue());
    }

    public void guardianNationalIdVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("guardianNationalIdVCL >>" + valueChangeEvent.getNewValue());
    }

    public void kinshipVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("kinshipVCL >>" + valueChangeEvent.getNewValue());
    }

    public void setKinshipBind(RichInputText kinshipBind) {
        this.kinshipBind = kinshipBind;
    }

    public RichInputText getKinshipBind() {
        return kinshipBind;
    }
    public void setPhoneNumBind(RichInputText PhoneNumBind) {
        this.PhoneNumBind = PhoneNumBind;
    }

    public RichInputText getPhoneNumBind() {
        return PhoneNumBind;
    }
    public void setGuardianNameBind(RichInputText guardianNameBind) {
        this.guardianNameBind = guardianNameBind;
    }

    public RichInputText getGuardianNameBind() {
        return guardianNameBind;
    }

    public void setGuardianNationalIdBind(RichInputText guardianNationalIdBind) {
        this.guardianNationalIdBind = guardianNationalIdBind;
    }

    public RichInputText getGuardianNationalIdBind() {
        return guardianNationalIdBind;
    }

    private boolean  compareDates(Date startDate, Date endDate) {
        //object of SimpleDateFormat class
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //comparing dates
        if (startDate.compareTo(endDate) > 0) {
            System.out.println("startDate  comes after endDate");
            return false;
        } else if (startDate.compareTo(endDate) < 0) {
            System.out.println("Date 1 comes before Date 2");
            return true;
        } else if (startDate.compareTo(endDate) == 0) {
            System.out.println("Both dates are equal");
            return true;
        }
        return false;
    }

    public boolean checkIsProdceduerUnique(BigDecimal actionId) {
        boolean val = true;
        ViewObject object = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
        //Row[] filteredRows = object.getFilteredRows("ACTS_TYPE_ID", actionId);
        object.setWhereClause("ACTS_TYPE_ID =" + actionId);
        object.executeQuery();
        int count = object.getRowCount();
        System.err.println("Bayoumi >>> PreviousProceduresRVO3Iterator Row Count" + count);
        if (count > 0) {
            val = false;
        }
        object.setWhereClause(null);
        return val;
    }

    public void setActionTypeIdBind(RichSelectOneChoice actionTypeIdBind) {
        this.actionTypeIdBind = actionTypeIdBind;
    }

    public RichSelectOneChoice getActionTypeIdBind() {
        return actionTypeIdBind;
    }

    public void setActionDateBind(RichInputDate actionDateBind) {
        this.actionDateBind = actionDateBind;
    }

    public RichInputDate getActionDateBind() {
        return actionDateBind;
    }


        public String printReport255() {
            
        String    nameOfReport = "EQRAR";
         String  reportName = "EQRAR";
        String pathReport = "/u02/config/report/";
        if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
        pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
        
        int userId =0;
        if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        }
        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;

        
        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

         
        
        BigDecimal folderid = new BigDecimal (searchInventoryCaseResultRow.getAttribute("FolderId").toString()) ;
        
    try {
        super.print3(pathReport, nameOfReport, "procedures", nameOfReport, folderid, userId);
    } catch (Exception e) {
        System.err.println("Error Print Report " + e.getMessage());
    }

    return null;
    }
    public String printReportEqrarLawyer() {
        
    String    nameOfReport = "EQRAR_LAWYERS";
     String  reportName = "EQRAR_LAWYERS";
    String pathReport = "/u02/config/report/";
    if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
    pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
    
    int userId =0;
    if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
        userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
    }
    ViewObject searchInventoryCaseResultView = null;
    Row searchInventoryCaseResultRow = null;

    
    searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
    searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

     
    
    BigDecimal folderid = new BigDecimal (searchInventoryCaseResultRow.getAttribute("FolderId").toString()) ;
    
    try {
    super.print3(pathReport, nameOfReport, "procedures", nameOfReport, folderid, userId);
    } catch (Exception e) {
    System.err.println("Error Print Report " + e.getMessage());
    }

    return null;
    }
    public String printReport25() {
        //            BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id.inputValue}");
        //            System.err.println(JSFUtils.resolveExpression("#{item.bindings.Id.inputValue}")+"ID  of   Folder  : "+id);
        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;

        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

       long partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());

        ViewObject view = ADFUtil.findIterator("PreviousJudgements1Iterator").getViewObject();
        view.setNamedWhereClauseParam("PARTY_ID", partyId);
        view.setOrderByClause("DEGREE_ID ASC");
        view.executeQuery();


        String    nameOfReport = "ExecutionReport25";
         String  reportName = "ExecutionReport25";

        for (Row r : ADFUtil.findIterator("PreviousJudgements1Iterator").getAllRowsInRange()) {
            System.err.println("hello = " + r.getAttribute("DegreeId"));
            if (Integer.parseInt(r.getAttribute("DegreeId") + "") == 3) {
                nameOfReport = "ExecReport26";
                reportName = "ExecReport26";
            }
        }

                String pathReport = "/u02/config/report/";
        if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
                pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
        try {
            super.print(pathReport, nameOfReport, "procedures", reportName);
        } catch (Exception e) {
                System.err.println("Error Print Report " + e.getMessage());
                }

                return null;
    }
    public String printReinstatelProcedureTemplate() {
        //            BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id.inputValue}");
        //            System.err.println(JSFUtils.resolveExpression("#{item.bindings.Id.inputValue}")+"ID  of   Folder  : "+id);
        ViewObject searchInventoryCaseResultView = null;
        Row searchInventoryCaseResultRow = null;

        searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
        searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

       long partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());

        ViewObject view = ADFUtil.findIterator("PreviousJudgements1Iterator").getViewObject();
        view.setNamedWhereClauseParam("PARTY_ID", partyId);
        view.setOrderByClause("DEGREE_ID ASC");
        view.executeQuery();


        String    nameOfReport = "ExecutionReport25";
         String  reportName = "ExecutionReport25";

        for (Row r : ADFUtil.findIterator("PreviousJudgements1Iterator").getAllRowsInRange()) {
            System.err.println("hello = " + r.getAttribute("DegreeId"));
            if (Integer.parseInt(r.getAttribute("DegreeId") + "") == 3) {
                nameOfReport = "ExecReport26";
                reportName = "ExecReport26";
            }
        }

                String pathReport = "/u02/config/report/";
        if (JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}") != null)
                pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
        try {
            super.print(pathReport, nameOfReport, "procedures", reportName);
        } catch (Exception e) {
                System.err.println("Error Print Report " + e.getMessage());
                }

                return null;
    }

    int procedureId = 0;

    public String callPOS() {
        // Add event code here...
        // Add event code here...
        Integer procId = null;
        String currentDate = null;
        {


               RichSelectOneChoice sc = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc27");

            ADFUtil.setEL("#{bindings.CapacityTypeId.inputValue}", sc.getValue());

               RichInputText da = (RichInputText) JSFUtils.findComponentInRoot("it7");

            ADFUtil.setEL("#{row.IdNumber}", da.getValue());
               System.out.println(ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}") + "--sc.getValue()--" +
                                  sc.getValue());
            ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));

               BigDecimal nnn = (BigDecimal) ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}");
            if (nnn.compareTo(new BigDecimal(6)) != 0)
                ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
               else
                ADFUtil.setEL("#{sessionScope.NeyabaINV}", ADFUtil.evaluateEL("#{bindings.NeyabGozayaa.inputValue}"));
               int userId = 0;
               if (ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null) {
                   userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
               }
               ADFUtil.setEL("#{pageFlowScope.procedureId}", null);
               int loggedInEntityId = 0;
               if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
                   ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
                   loggedInEntityId =
                       Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
               } else if (Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                          3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
                   loggedInEntityId =
                       Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
               }


               ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
               Row actionsRow = actionsView.getCurrentRow();

            Date deliveryDate = (Date) getDeliveryDateBind().getValue();
            Date startDate = (Date) getStartDateBind().getValue();
            Date endDate = (Date) getEndDateBind().getValue();
               String actionNotes = (String) getActionNotesBind().getValue();
               String guardianName = (String) getGuardianNameBind().getValue();
               String guardianNationalId = (String) getGuardianNationalIdBind().getValue();
               String kinship = (String) getKinshipBind().getValue();
            System.err.println("Bayoumi1 >>>deliveryDate =" + deliveryDate + "\n startDate=" + startDate +
                               "\n endDate=" + endDate + "\n actionNotes=" + actionNotes);
               int actionTypeId = Integer.parseInt(actionsRow.getAttribute("ActionTypeId").toString());
               BigDecimal feesTypeId = null;
               if (actionsRow.getAttribute("FeesType") != null)
                   feesTypeId = new BigDecimal(actionsRow.getAttribute("FeesType").toString());

               if (actionTypeId == 33) {
                   setJudgmentText();
               }


               if (invals(actionTypeId, 34,   37, 35)) { // , 35
                   setAttributeValue(ACTION_ITERATOR, "CapacityTypeId", "1");
               }

               int validate = 0;
               if (validateActionsRow() == 0)
                   return null;
               else
                   validate = 1;
               boolean validateDateTaqseer = true;
               boolean validateDateGharama = true;

               if (actionTypeId == 45)
                   validateHearingDateTaqseerFatra();

            //

            if ((actionsRow.getAttribute("HearingDateId") == null && actionsRow.getAttribute("PreviousVeto") == null) &&
                actionTypeId == 45) {
                   ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u062d\u062c\u0632 \u0627\u0644\u062c\u0644\u0633\u0629 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                       FacesMessage.SEVERITY_ERROR);
                   return null;
               }
               int sequenceNumber = 0, resPartyId = 0, folderId = 0, attendance = 0, sequenceYear = 0, folderTypeId =
                   0, degree = 0, userRole = 0;

               long partyId = 0, businessCaseId = 0;

               int capacityTypeId =
                   actionsRow.getAttribute("CapacityTypeId") != null ?
                   Integer.parseInt(actionsRow.getAttribute("CapacityTypeId").toString()) : 1;
               long hearingDateId =
                   Long.parseLong(actionsRow.getAttribute("HearingDateId") == null ? "0" :
                                  actionsRow.getAttribute("HearingDateId").toString());

               int accusedRequired =
                   Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "ExecutionFlag") == null ? "1" :
                                    getAttributeValue(ACTION_ITERATOR, "ExecutionFlag").toString());

               int entityId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "OriginEntity").toString());
               int prosecutionPartial = Integer.parseInt(JSFUtils.getFromPageFlowScope("entity").toString());
               int degreeId = degree(actionTypeId);
               //        int reservationPartyId = Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "ResPartyId").toString());

               String actionDate =
                   actionsRow.getAttribute("ActionDate") != null ? actionsRow.getAttribute("ActionDate").toString() : "";
            String procedureText = "", documentId = null, idNumber = "", penaltyAmount = "0", work =
                "0", paymentAmount = "0", partyName = "", repeatedFlag = "";
               String numberPaid =
                   actionsRow.getAttribute("NumberOfPaied") == null ? "" :
                   actionsRow.getAttribute("NumberOfPaied").toString();


               if (actionsRow.getAttribute("ProcedureText") != null) {
                   procedureText = actionsRow.getAttribute("ProcedureText").toString();
               }
               if (ADFUtil.evaluateEL("#{pageFlowScope.documentId}") != null) {
                   documentId = ADFUtil.evaluateEL("#{pageFlowScope.documentId}").toString();
               }

               ViewObject searchInventoryCaseResultView = null;
               Row searchInventoryCaseResultRow = null;

               searchInventoryCaseResultView = ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject();
               searchInventoryCaseResultRow = searchInventoryCaseResultView.getCurrentRow();

               int rulOrProc = Integer.parseInt(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur").toString());
               BigDecimal idType = new BigDecimal(searchInventoryCaseResultRow.getAttribute("IdType").toString());
               resPartyId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("ResPartyId").toString());
               folderId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderId").toString());
               partyId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("PartyId").toString());
               businessCaseId = Long.parseLong(searchInventoryCaseResultRow.getAttribute("BusinessCaseId").toString());
               partyName = searchInventoryCaseResultRow.getAttribute("PartyName").toString();


               if (searchInventoryCaseResultRow.getAttribute("DegreeId") != null)
                   degree = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("DegreeId").toString());
               attendance =
                   Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Attendance") == null ? "0" :
                                    searchInventoryCaseResultRow.getAttribute("Attendance").toString());
               sequenceNumber = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("SequenceNumber").toString());
               repeatedFlag = searchInventoryCaseResultRow.getAttribute("RepeatedFlag").toString();
               sequenceYear = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("Year").toString());
               idNumber =
                   searchInventoryCaseResultRow.getAttribute("IdNumber") != null ?
                   searchInventoryCaseResultRow.getAttribute("IdNumber").toString() : "";
               String  PPhoneNumber =
                     searchInventoryCaseResultRow.getAttribute("PhoneNumber") != null ?
                     searchInventoryCaseResultRow.getAttribute("PhoneNumber").toString() : "";
               folderTypeId = Integer.parseInt(searchInventoryCaseResultRow.getAttribute("FolderType").toString());

               Date dateFrom = (Date) actionsRow.getAttribute("DateFrom");
               Date dateTo = (Date) actionsRow.getAttribute("DateTo");
               double payment = 0;
               if (actionTypeId == 7 || actionTypeId == 77) {
                   paymentAmount =
                       actionsRow.getAttribute("PaymentAmount") == null ? "0" :
                       actionsRow.getAttribute("PaymentAmount").toString();
                   payment = Double.parseDouble(paymentAmount);
                if (feesTypeId.equals(new BigDecimal(4))) {
                       if (actionsRow.getAttribute("TotalAmount") != null &&
                           Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") == 0) {
                           ADFUtil.showMessage("\u0644\u0627 \u064a\u0648\u062c\u062f \u0645\u0628\u0644\u063a \u0643\u0641\u0627\u0644\u0629",
                                               FacesMessage.SEVERITY_ERROR);
                           return null;
               }
                   }
               }

               int lastAction = lastProcedureAction(folderId, actionTypeId);
               int countPrevAction = countPrevActions(resPartyId, rulOrProc);
               System.out.println(countPrevAction + "--" + lastAction + "*//*/*/*//*/*//*------" + folderId + "--" +
                                  actionTypeId + "--" + resPartyId + "--" + rulOrProc + "----ddd--" + degree);
            if (!invals(actionTypeId, 22, 5, 7, 77, 10, 12, 13, 15, 16, 24, 68, 69, 70, 71, 72, 19, 11, 30, 28, 20, 34,
                        36, 37, 43, 44, 45, 46, 47, 48)) {
                   if (countPrevAction > 0) {
                       if ((!(capacityTypeId == 3 && actionTypeId == 1)) && lastAction != 22) {

                           System.out.println("TTTTTTTTTTTTTTTTT3333");
                           ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0625\u062a\u062e\u0627\u0630 \u0623\u0643\u062b\u0631 \u0645\u0646 " +
                                               "\u0625\u062c\u0631\u0627\u0621 \u0639\u0644\u0649 \u0646\u0641\u0633 \u0627\u0644\u062d\u0635\u0631",
                                               FacesMessage.SEVERITY_ERROR);
                           return null;
                       }
                   }
               }

               boolean functionId =
                   Boolean.parseBoolean(ADFUtil.evaluateEL("#{sessionScope.userInfo.userPrevMap[2962]}").toString());
               if (functionId == true && entityId != loggedInEntityId) {
                   userRole = 1;
               }
               //    int validate = validateActionsRow(actionsRow, actionsView);
               if (validate == 1) {
                   int resIdFlag = 0;
                   int countHearingId = checkHearingDate(businessCaseId, hearingDateId);
                   if (countHearingId != 0) {
                       // added by belal to return res id in appmodule impl refer to ****
                       resIdFlag = countHearingId;
                   }


                   int tempResPartyId = 0;

                   if (null != feesTypeId) {
                       if (feesTypeId.equals(new BigDecimal(4))) {
                           //                if (payment == 0 || payment == 0.0)
                           //                    ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0627\u0643\u0628\u0631 \u0645\u0646 \u0635\u0641\u0631",
                           //                                                FacesMessage.SEVERITY_ERROR);

                           double totalVal =
                               actionsRow.getAttribute("TotalAmount") != null ?
                               Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") : Double.parseDouble("0");

                           if (totalVal != payment && (actionTypeId == 7 || actionTypeId == 77)) {
                               ADFUtil.showMessage("\u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f \u064a\u062c\u0628 \u0627\u0646 \u064a\u0633\u0627\u0648\u064a \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a",
                                                   FacesMessage.SEVERITY_ERROR);
                               return null;
                           }
                           ViewObject view = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                           if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                               view.setWhereClause("PROCEDURE_TYPE in (47)");
                               view.setOrderByClause("id desc");
                               view.executeQuery();
                               if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                                   try {
                                       java.util.Date procedureDate = null;
                                       java.util.Date previousProcedureDate = null;
                                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    previousProcedureDate =
                                        sdf.parse(view.first().getAttribute("ProcedureDate").toString());
                                    procedureDate =
                                        sdf.parse(getAttributeValue(ACTION_ITERATOR, "ActionDate").toString());
                                       boolean procedureAfterProcedure =
                                           ADFUtil.dateAfterDate(previousProcedureDate, procedureDate);
                                       if (!procedureAfterProcedure) {
                                           System.out.println("QQQQQQQQQQQ2222");
                                           setAttributeValue(ACTION_ITERATOR, "ActionDate", null);
                                           ADFUtil.showMessage("\u062a\u0627\u0631\u064a\u062e \u0627\u0644\u0625\u062c\u0631\u0627\u0621 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0628\u0639\u062f \u062a\u0627\u0631\u064a\u062e \u0625\u062c\u0631\u0627\u0621 \u0642\u0628\u0648\u0644 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                               FacesMessage.SEVERITY_ERROR);
                                           view.setWhereClause(null);
                                           view.executeQuery();
                                           return null;
                                       }
                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                               }
                               view.setWhereClause("PROCEDURE_TYPE in (46,47,48)");
                               view.setOrderByClause("id desc");
                               view.executeQuery();
                               if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() == 1) {
                                   ADFUtil.showMessage("\u0644\u0627 \u064a\u0645\u0643\u0646 \u0627\u0644\u0633\u062f\u0627\u062f \u062d\u062a\u0649 \u064a\u062a\u0645 \u0642\u0628\u0648\u0644 \u0627\u0648 \u0631\u0641\u0636 \u0637\u0644\u0628 \u062a\u062e\u0641\u064a\u0636 \u0627\u0644\u0643\u0641\u0627\u0644\u0629",
                                                       FacesMessage.SEVERITY_ERROR);
                                   view.setWhereClause(null);
                                   view.executeQuery();
                                   actionsRow.setAttribute("FeesType", null);
                                   return null;
                               }
                           }
                       } else if (feesTypeId.equals(new BigDecimal(7))) {
                           tempResPartyId = resPartyId;
                           RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it44");
        //                    System.err.println("inputNum.getValue() = "+inputNum.getValue());
                           if (inputNum.getValue() == null ||
                               (inputNum.getValue() != null && Double.parseDouble(inputNum.getValue() + "") == 0)) {
                               ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0645\u0633\u062f\u062f",
                                                   FacesMessage.SEVERITY_ERROR);
                               return null;
                           }

                           validateDateGharama = validateDateGharamaYawmeya();
                       }
                   }

                   if (!validateDateGharama)
                       return null;


                   ViewObject view2 = ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject();
                   if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                       view2.setWhereClause("PROCEDURE_TYPE in (1,2,8,9,23,24)");
                       view2.setOrderByClause("id desc");
                       view2.executeQuery();
                       if (ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getEstimatedRowCount() > 0) {
                        resPartyId = Integer.parseInt(view2.first().getAttribute("NewResPartyId").toString());
                       }
                   }

                   view2.setWhereClause(null);
                   view2.executeQuery();
                   ADFUtil.findIterator("PreviousProceduresRVO3Iterator").executeQuery();
                   //      System.err.println(">>>>>> ResPartyId


                   OperationBinding bindingId = ADFUtil.findOperation("getReservationId");
                   bindingId.getParamsMap().put("businessCaseId", businessCaseId);
                   bindingId.getParamsMap().put("hearingDateId", hearingDateId);
                   bindingId.getParamsMap().put("degree", degreeId);
                   Object obj = bindingId.execute();
                   BigDecimal resId = null;
                   if (obj != null) {
                       resId = new BigDecimal(obj.toString());
                   }

                   int prosecutionJudegementFlag = 1;
                   if (invals(actionTypeId, 14,168) &&
                       "2".equalsIgnoreCase(getAttributeValue(RESULT_SEARCH_ITERATOR, "ProsecutionJudegementFlag") + "")) {
                       prosecutionJudegementFlag = 0;
                   }
                   //            double gurantee = getDoubleValueFromObject(getAttributeValue(ACTION_ITERATOR, "GuranteeAmount"));
                   double grantee = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "Guarantee"));
                double inpresonYear =
                    getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonYears"));
                   double inpresonMonth =
                       getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonMonths"));
                double inpresonday =
                    getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "InPrisonDays"));
                   double amount = getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "PenaltyAmount"));
                double dailyPayment =
                    getDoubleValueFromObject(getAttributeValue(RESULT_SEARCH_ITERATOR, "DailyPayments"));
                   double procecutionObjection = 0.0;
                   /*
                * in case the hasr is eatrad on amr geanay
                * */
                   int judgmentType =
                       getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") != null ?
                       Integer.parseInt(getAttributeValue(RESULT_SEARCH_ITERATOR, "JudgmentType") + "") : -1;
                   if (judgmentType == 3 &&( actionTypeId == 14 || actionTypeId == 168 ))
                       procecutionObjection = 1;
                   else if (judgmentType == 2 && (actionTypeId == 14 || actionTypeId == 168  ))
                       procecutionObjection = 2;

                   int caseSchema =
                       ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") != null ?
                       Integer.parseInt(ADFUtil.getElExpression("#{pageFlowScope.caseSchema}") + "") : -1;

                   System.err.println("resPartyId = " + resPartyId);
                   double totalVal =
                       actionsRow.getAttribute("TotalAmount") != null ?
                       Double.parseDouble(actionsRow.getAttribute("TotalAmount") + "") : Double.parseDouble("0");
                   //            double oldVal = getTotalPenaltyOrGuranteeAmount(resPartyId, 4);
                   if (actionTypeId == 47 && totalVal >= grantee) {
                       ADFUtil.showMessage("\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0641\u0627\u0644\u0629 \u064a\u062c\u0628 \u0627\u0646 \u064a\u0643\u0648\u0646 \u0623\u0642\u0644 \u0645\u0646 " +
                                           grantee, FacesMessage.SEVERITY_ERROR);
                       return null;
                   }

                   BigDecimal feesTypeGharamaId = null;
                   if (actionsRow.getAttribute("FeesTypeGharama") != null)
                       feesTypeGharamaId = new BigDecimal(actionsRow.getAttribute("FeesTypeGharama").toString());


                   double penalty = 0;
                if (actionTypeId == 63 && feesTypeGharamaId != null && feesTypeGharamaId.equals(new BigDecimal(3))) {
                       penaltyAmount =
                           actionsRow.getAttribute("PenaltyAmount") == null ? "0" :
                           actionsRow.getAttribute("PenaltyAmount").toString();
                       penalty = Double.parseDouble(penaltyAmount);
                       RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it49");
                    if (inp.getValue() == null) {
                           ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                           FacesMessage.SEVERITY_ERROR);
                           return null;
                    } else if (inp.getValue() != null && Double.parseDouble(inp.getValue() + "") == 0) {
                           ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0627\u0644\u0645\u0628\u0644\u063a \u0627\u0644\u0643\u0644\u064a \u0644\u0644\u0633\u062f\u0627\u062f",
                                           FacesMessage.SEVERITY_ERROR);
                           return null;
                       }

                   }

                   double workAmount = 0;
                if (actionTypeId == 63 && feesTypeGharamaId != null && feesTypeGharamaId.equals(new BigDecimal(7))) {
                       work =
                           actionsRow.getAttribute("WorkAmount") == null ? "0" :
                           actionsRow.getAttribute("WorkAmount").toString();
                       workAmount = Double.parseDouble(work);

                       RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id14");
                       RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it50");
                    if (inp.getValue() == null) {
                           ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u063a\u0631\u0627\u0645\u0629 \u0627\u0644\u064a\u0648\u0645\u064a\u0629",
                                           FacesMessage.SEVERITY_ERROR);
                           return null;
                    } else if (inp.getValue() != null && Double.parseDouble(inp.getValue() + "") == 0) {
                           ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u0642\u064a\u0645\u0629 \u0627\u0644\u063a\u0631\u0627\u0645\u0629 \u0627\u0644\u064a\u0648\u0645\u064a\u0629",
                                           FacesMessage.SEVERITY_ERROR);
                           return null;
                    } else if (inpDate.getValue() == null) {
                           ADFUtil.showMessage("\u064a\u062c\u0628 \u0627\u062f\u062e\u0627\u0644 \u062a\u0627\u0631\u064a\u062e \u0628\u062f\u0621 \u062d\u0633\u0627\u0628 \u0627\u0644\u063a\u0631\u0627\u0645\u0629",
                                           FacesMessage.SEVERITY_ERROR);
                           return null;
                   }
                }

                   if (actionsRow.getAttribute("TotalAmount") == null)
                       setAttributeViewValue(PARAMS_ITERATOR, "grantee", grantee);
                   else
                       setAttributeViewValue(PARAMS_ITERATOR, "grantee", totalVal);
                   setAttributeViewValue(PARAMS_ITERATOR, "penaltyAmount", penalty);
                   setAttributeViewValue(PARAMS_ITERATOR, "resPartyId", resId);
                   setAttributeViewValue(PARAMS_ITERATOR, "capacityTypeId", capacityTypeId);
                   setAttributeViewValue(PARAMS_ITERATOR, "userId", userId);
                   setAttributeViewValue(PARAMS_ITERATOR, "hearingDateId", hearingDateId);
                   setAttributeViewValue(PARAMS_ITERATOR, "actionTypeId", actionTypeId);
                   setAttributeViewValue(PARAMS_ITERATOR, "partyId", partyId);
                   setAttributeViewValue(PARAMS_ITERATOR, "actionDate", actionDate);
                   setAttributeViewValue(PARAMS_ITERATOR, "procedureText", procedureText);
                   setAttributeViewValue(PARAMS_ITERATOR, "folderId", folderId);
                   setAttributeViewValue(PARAMS_ITERATOR, "accusedRequired", accusedRequired);
                   setAttributeViewValue(PARAMS_ITERATOR, "dateFrom", dateFrom);
                   setAttributeViewValue(PARAMS_ITERATOR, "dateTo", dateTo);
                   setAttributeViewValue(PARAMS_ITERATOR, "idNumber", idNumber);
                   setAttributeViewValue(PARAMS_ITERATOR, "PartyPhoneNumber", PPhoneNumber);
                setAttributeViewValue(PARAMS_ITERATOR, "PreviousVetoDate",
                                      getAttributeValue(ACTION_ITERATOR, "PreviousVeto"));
                  // setAttributeViewValue(PARAMS_ITERATOR, "PartyFName", null);
                   setAttributeViewValue(PARAMS_ITERATOR, "paymentAmount", payment);
                   setAttributeViewValue(PARAMS_ITERATOR, "entityId", prosecutionPartial);
                   RichInputDate inp = (RichInputDate) JSFUtils.findComponentInRoot("id11");
                   RichInputDate inpDate = (RichInputDate) JSFUtils.findComponentInRoot("id14");
                   if (inp.getValue() != null)
                   setAttributeViewValue(PARAMS_ITERATOR, "penaltyEndDate", inp.getValue());
                   if (inpDate.getValue() != null)
                       setAttributeViewValue(PARAMS_ITERATOR, "penaltyDueDate", inpDate.getValue());
                   System.out.println(folderId + "folderId  " + resId + "degreeIddegree-------" + degree +
                                      "IddegreeIddegreeIddegreeId" + degreeId);
                   setAttributeViewValue(PARAMS_ITERATOR, "degree", degree);
                   setAttributeViewValue(PARAMS_ITERATOR, "resIdFlag", resIdFlag);
                   setAttributeViewValue(PARAMS_ITERATOR, "businessCaseId", businessCaseId);
                   if (feesTypeId != null && feesTypeId.equals(new BigDecimal(7)))
                       setAttributeViewValue(PARAMS_ITERATOR, "prevResPartyId", tempResPartyId);
                   else
                       setAttributeViewValue(PARAMS_ITERATOR, "prevResPartyId", resPartyId);
                   setAttributeViewValue(PARAMS_ITERATOR, "workAmount", workAmount);
                   setAttributeViewValue(PARAMS_ITERATOR, "numberPaid", numberPaid);
                   setAttributeViewValue(PARAMS_ITERATOR, "idType", idType);
                System.err.println("feesTypeId = " + feesTypeId);
                   setAttributeViewValue(PARAMS_ITERATOR, "feesTypeId", feesTypeId);
                   setAttributeViewValue(PARAMS_ITERATOR, "feesTypeGharamaId", feesTypeGharamaId);
                   setAttributeViewValue(PARAMS_ITERATOR, "prosecutionJudegementFlag", prosecutionJudegementFlag);
                   setAttributeViewValue(PARAMS_ITERATOR, "VetoNo", getAttributeValue(ACTION_ITERATOR, "VetoNo"));
                   setAttributeViewValue(PARAMS_ITERATOR, "VetoDate", getAttributeValue(ACTION_ITERATOR, "VetoDate"));
                   setAttributeViewValue(PARAMS_ITERATOR, "VetoYear", getAttributeValue(ACTION_ITERATOR, "VetoYear"));
                   setAttributeViewValue(PARAMS_ITERATOR, "procecutionObjection", procecutionObjection);
                setAttributeViewValue(PARAMS_ITERATOR, "NeyabKolyaa",
                                      ADFUtil.evaluateEL("#{bindings.NeyabKolyaa.inputValue}"));
                   setAttributeViewValue(PARAMS_ITERATOR, "caseSchema", caseSchema);
                   setAttributeViewValue(PARAMS_ITERATOR, "partyName", partyName);
                   setAttributeViewValue(PARAMS_ITERATOR, "Adress", getAttributeValue(ACTION_ITERATOR, "Adress"));
                   setAttributeViewValue(PARAMS_ITERATOR, "City", getAttributeValue(ACTION_ITERATOR, "City"));
                setAttributeViewValue(PARAMS_ITERATOR, "EntityPolice",
                                      getAttributeValue(ACTION_ITERATOR, "EntityPolice"));
                setAttributeViewValue(PARAMS_ITERATOR, "PrisonNameDesc",
                                      getAttributeValue(ACTION_ITERATOR, "PrisonNameDesc"));
                   setAttributeViewValue(PARAMS_ITERATOR, "VetoStatus", getAttributeValue(ACTION_ITERATOR, "VetoStatus"));
                int appealPlace = Integer.parseInt(getAttributeValue("AppealPlaceStaticLKPIterator", "Id") + "");
                System.out.println("appealPlace  is -------- " + appealPlace);
                   int degSequenntial = degreeId;
                   if (degreeId == 5)
                       degSequenntial = 2;
                   if (degreeId == 6)
                       degSequenntial = 4;

                   if (degreeId == 3 && (actionTypeId == 14 || actionTypeId == 168 ))
                       degSequenntial = 6;
                if (degreeId == 1 && (actionTypeId == 14 || actionTypeId == 168 ))
                    degSequenntial = 7;


                   if (capacityTypeId == 3 && attendance == 1 && actionTypeId == 152)
                       degSequenntial = 8;
                if (capacityTypeId == 3 && (attendance == 3) && actionTypeId == 152)
                       degSequenntial = 9;
                   if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 1)
                       degSequenntial = 10;
                if ((capacityTypeId == 2 || capacityTypeId == 1) && actionTypeId == 152 && appealPlace == 2)
                       degSequenntial = 11;
                   if (capacityTypeId == 8  && actionTypeId == 152)
                       degSequenntial = 12;
                   if (capacityTypeId == 9  && actionTypeId == 152)
                       degSequenntial = 13;


                   Boolean objResult = true;
                RichInputText inputNum = (RichInputText) JSFUtils.findComponentInRoot("it35");
                RichInputText inputYear = (RichInputText) JSFUtils.findComponentInRoot("it36");
                   setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", null);
                RichInputText nid = (RichInputText) JSFUtils.findComponentInRoot("it38");
                RichInputText registerationId = (RichInputText) JSFUtils.findComponentInRoot("it39");
                RichInputText name = (RichInputText) JSFUtils.findComponentInRoot("it37");
                RichInputText lawyerPhoneNumber = (RichInputText) JSFUtils.findComponentInRoot("it77");
                RichInputText comments = (RichInputText) JSFUtils.findComponentInRoot("it41");
                RichInputText retainerNumber = (RichInputText) JSFUtils.findComponentInRoot("it40");


                        Boolean manual = (Boolean) JSFUtils.resolveExpression("#{bindings.SequentialManual.inputValue}");
                System.out.println("VVVVVVVVVVVV****** " + manual);
                   if (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 8 || actionTypeId == 14 || actionTypeId == 168  ||
                       actionTypeId == 23 || actionTypeId == 9) {
                   if (inputNum.getValue() == null && inputYear.getValue() == null && manual) {
                      showErrorMessageForComponentById("\u0628\u0631\u062c\u0627\u0621 \u0625\u062f\u062e\u0627\u0644 \u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639",
                                                         "it35");

                       return null;
                    }


                    if (inputNum.getValue() != null && inputYear.getValue() != null &&
                        (manual ||
                         JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'N'}"))) {
                        int numSequential = 0;
                        int yearSequential = 0;
                       int curryear = Calendar.getInstance().get(Calendar.YEAR);
                        try {

                            numSequential = Integer.parseInt(inputNum.getValue() + "");
                            yearSequential = Integer.parseInt(inputYear.getValue() + "");

                        } catch (Exception e) {
                               showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                             "it35");
                               return null;
                           }

                           if (numSequential > 0 && yearSequential > 2000 && numSequential < 9999999 &&
                               yearSequential < 2023) {

                   OperationBinding binding = ADFUtil.findOperation("validatSequentionalNumber");

                   binding.getParamsMap().put("folderId",  folderId);
                            binding.getParamsMap().put("degree", degSequenntial);
                            binding.getParamsMap().put("number", numSequential);
                            binding.getParamsMap().put("year", yearSequential);
                            if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"))
                                binding.getParamsMap().put("flagAuto", 1);
                   else
                                binding.getParamsMap().put("flagAuto", 0);
                        objResult = (Boolean) binding.execute();
                           } else {
                          showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u063a\u064a\u0631 \u0635\u062d\u064a\u062d",
                                                           "it35");
                           return null;

                       }
                       }
                   }
                   if (objResult != null && !objResult) {

                       showErrorMessageForComponentById("\u0631\u0642\u0645 \u0627\u0644\u062a\u062a\u0627\u0628\u0639 \u0645\u0643\u0631\u0631",
                                                        "it35");
                } else {

                                  if (capacityTypeId == 2 && nid.getValue() != null && name.getValue() != null &&
                           retainerNumber.getValue() != null) {
                        BigDecimal parId =
                            new BigDecimal(getAttributeValue(RESULT_SEARCH_ITERATOR, "PoliceId").toString());

                           String comm = null;
                           if (comments.getValue() != null)
                               comm = comments.getValue().toString();

                           String reg = "0";
                           if (registerationId.getValue() != null)
                               reg = registerationId.getValue().toString();

                           System.err.println(registerationId.getValue() + "comm   is  " + comm);

                           OperationBinding operation = getBindings().getOperationBinding("insertLawyer");
                           operation.getParamsMap().put("parId", parId);
                           operation.getParamsMap().put("nid", nid.getValue().toString());
                           operation.getParamsMap().put("registerationId", new BigDecimal(reg.toString()));
                           operation.getParamsMap().put("name", name.getValue().toString());
                           operation.getParamsMap().put("comments", comm);
                           operation.getParamsMap().put("retainerNumber", retainerNumber.getValue().toString());
                           operation.getParamsMap().put("phoneNumber", lawyerPhoneNumber.getValue().toString());
                        operation.getParamsMap().put("createdBy",
                                                     ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
                           BigDecimal LawyerId = (BigDecimal) operation.execute();

                           //                    BigDecimal LawyerId=     am.insertLawyer(parId, nid.getValue().toString(),new BigDecimal( reg.toString()),
                           //                                        name.getValue().toString(),comm , retainerNumber.getValue().toString(), ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());
                           setAttributeViewValue(PARAMS_ITERATOR, "PrimaryLawyerId", LawyerId);
                           System.err.println("---------  LawyerId   i s" + LawyerId);
                       }
                       System.err.println("Bayoumi>>>>Call  saveInventory2 >>1");

                       //
                       OperationBinding bindingSaveInventory2 = ADFUtil.findOperation("saveInventory2");
                       bindingSaveInventory2.getParamsMap().put("deliveryDate", deliveryDate);
                       bindingSaveInventory2.getParamsMap().put("startDate", startDate);
                       bindingSaveInventory2.getParamsMap().put("endDate", endDate);
                       bindingSaveInventory2.getParamsMap().put("actionNotes", actionNotes);
                   bindingSaveInventory2.getParamsMap().put("guardianName", guardianName);

                   bindingSaveInventory2.getParamsMap().put("guardianNationalId", guardianNationalId);
                   bindingSaveInventory2.getParamsMap().put("kinship", kinship);

                    ArrayList<Integer> save = (ArrayList<Integer>) bindingSaveInventory2.execute();


                    //                   FacesMessage Message = new FacesMessage("تم ح�?ظ الاجراء برجاء الانظار لإتمام عمليه الد�?ع الاكترونى");
                    //                   Message.setSeverity(FacesMessage.SEVERITY_INFO);
                    //                   FacesContext fc = FacesContext.getCurrentInstance();
                    //                   fc.addMessage(null, Message);
                    //                       saveMessage();  doneee
                       RichInputText inp2 = (RichInputText) JSFUtils.findComponentInRoot("it42");
                       inp2.setDisabled(true);
                       if (save.size() > 0) {
                           procId = save.get(0);
                       }


                       ADFUtil.setEL("#{pageFlowScope.businessCaseId}", businessCaseId);
                       ADFUtil.setEL("#{pageFlowScope.entityId}", entityId);
                       ADFUtil.setEL("#{pageFlowScope.partyId}", partyId);
                       ADFUtil.setEL("#{pageFlowScope.resPartyId}", save.size() > 0 ? save.get(2) : null);
                       ADFUtil.setEL("#{pageFlowScope.procedureId}", procId);
                       ADFUtil.setEL("#{pageFlowScope.actionSaved}", 1);
                    System.err.println(getAttributeValue(ACTION_ITERATOR, "ActionTypeId") + " 999999 " +
                                       getAttributeValue(ACTION_ITERATOR, "IdNumIs"));


                       uploadIdTawkelSadadTasdekPicFiles();

                   ADFUtil.findIterator(ACTION_ITERATOR).getViewObject().executeQuery();
                       if (procId != null) {
                           OperationBinding oper2 = getBindings().getOperationBinding("getProsecutionUnderKolyea");
                           oper2.getParamsMap().put("kolyeaId", loggedInEntityId);
                           Object result = oper2.execute();

                           if (result == null)
                               result = new ArrayList();

                           ArrayList<Integer> prosecutionUnderKolyea = (ArrayList<Integer>) result;

                           boolean underKolyea = false;
                           for (int i = 0; prosecutionUnderKolyea.size() > i; i++) {
                               if (prosecutionUnderKolyea.get(i) == prosecutionPartial) {
                                   underKolyea = true;
                                   ADFUtil.setEL("#{pageFlowScope.underKolyea}", 1);
                               }
                           }
                           //                if (loggedInEntityId != entityId && !underKolyea){
                           if (actionTypeId == 25 || actionTypeId == 26 || actionTypeId == 27) {
                               BigDecimal logId =
                                   (BigDecimal) insertIntoExcProceduresLogView(actionTypeId, businessCaseId, userId,
                                                                               prosecutionPartial, 0, loggedInEntityId,
                                                                               new BigDecimal(procId), sequenceNumber, 1,
                                                                               folderTypeId, repeatedFlag, sequenceYear,
                                                                               actionDate);


                               ADFUtil.setEL("#{pageFlowScope.logId}", save.size() > 0 ? save.get(0) : null);
                               ADFUtil.setEL("#{pageFlowScope.actionId}", save.size() > 0 ? save.get(1) : null);
                               ADFUtil.setEL("#{pageFlowScope.procedureLogId}", logId);
                           }
                           ADFUtil.setEL("#{pageFlowScope.folderId}", folderId);
                       }

                    ADFUtil.findIterator("PreviousProceduresRVO3Iterator").getViewObject().executeQuery();
                    ADFUtil.findIterator("PartyFilesRVO1Iterator").getViewObject().executeQuery();

                       if (actionTypeId == 7 || actionTypeId == 77)
                        ADFUtil.findIterator("PreviousProceduresRVO5Iterator").getViewObject().executeQuery();

                       if (!invals(getAttributeValue(ACTION_ITERATOR, "RulesOProcedur"), 1))
                           filterFilesTypes(actionTypeId);

                    ADFUtil.findIterator(REUPLOAD_FILE_TYPES_ITERATOR).getViewObject().executeQuery();

                       //            printAndUploadReportAfterSave(actionTypeId);
                       BigDecimal type = (BigDecimal) ADFUtil.evaluateEL("#{bindings.CapacityTypeId.inputValue}");
                       System.out.println("******--******" +
                                          JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}"));
                    System.out.println("******manual******" + manual);
                    System.out.println("******actionTypeId******" + actionTypeId);
                    if (JSFUtils.resolveExpressionAsBoolean("#{sessionScope.userInfo.automationFlag eq 'Y'}") &&
                        !manual &&
                           (actionTypeId == 1 || actionTypeId == 2 || actionTypeId == 3 || actionTypeId == 8 ||
                            actionTypeId == 9 || actionTypeId == 23 || actionTypeId == 14 || actionTypeId == 168 ))

               {

                       OperationBinding binding = ADFUtil.findOperation("sequentialAutomation");

                       binding.getParamsMap().put("folderId",  folderId);
                        if (type.compareTo(new BigDecimal(3)) == 0) {
                            binding.getParamsMap().put("degree", 33);
                           } else
                            binding.getParamsMap().put("degree", degSequenntial);
                       binding.execute();
                        System.err.println("Automation");
                   }


                   if (actionTypeId == 2)
                       JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0647 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0645\u0639\u0627\u0631\u0636\u0629");

                   if (actionTypeId == 1)
                       JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0623\u0631\u0641\u0627\u0642 \u0645\u0644\u0641 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641 \u0644\u0623\u062A\u0645\u0627\u0645 \u0639\u0645\u0644\u064A\u0629 \u0627\u0644\u0627\u0633\u062A\u0626\u0646\u0627\u0641");

                   //  showErrorMessageForComponentById("", "if18");
               }


                if (actionTypeId == 36 || actionTypeId == 37) {
                   System.out.println("SearchInventoryCaseResultRVO1Iterator    ---------");
                    ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").executeQuery();
                   } else if (actionTypeId == 47 || actionTypeId == 51 || actionTypeId == 10 || actionTypeId == 16 ||
                              actionTypeId == 43)
                       ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").executeQuery();
               }

               DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddHHmmss");
                        LocalDateTime now = LocalDateTime.now();
                          currentDate = dtf.format(now);
            System.out.println(JSFUtils.getFromSession("pBillerId") + "currentDate   is " + currentDate);

            Double FixedBenfit = Double.valueOf(JSFUtils.getFromSession("pFixedBenfit") + "");
            Double FixedBenfitAUTO = Double.valueOf(JSFUtils.getFromSession("pFixedBenfitAUTO") + "");
            Double PercentageBenfit = Double.valueOf(JSFUtils.getFromSession("pPercentageBenfit") + "");
            int BillerId = Integer.parseInt(JSFUtils.getFromSession("pBillerId") + "");
            String SettlementCodeEF = JSFUtils.getFromSession("pSettlementCodeEF") + "";
            String SettlementCodeGOV = JSFUtils.getFromSession("pSettlementCodeGOV") + "";
            String SettlementCodeAUTO = JSFUtils.getFromSession("pSettlementCodeAUTO") + "";

            //            this.setProcedureId(procId);
            double percentageNum =
                Double.valueOf(String.format("%.2f", ((payment + FixedBenfitAUTO) * (PercentageBenfit / 100))));
            System.out.println("Fees is ----------   " + percentageNum);
            double fees = 0.0;
            if (percentageNum > FixedBenfit)
                fees = percentageNum;
                     else
                fees = FixedBenfit;

            System.out.println("feesTypeId   is " + feesTypeId);
          String description = "����� ������� ��������  ";
            description = " ����� ����  ";
            if (feesTypeId.compareTo(new BigDecimal(1)) == 0) {
                description = description + " ����� ������ ";
            } else if (feesTypeId.compareTo(new BigDecimal(2)) == 0) {
                description = description + " ������ ����� ";
            } else if (feesTypeId.compareTo(new BigDecimal(3)) == 0) {
                description = description + " ����� ";
            } else if (feesTypeId.compareTo(new BigDecimal(4)) == 0) {
                description = description + " ����� ";
            } else if (feesTypeId.compareTo(new BigDecimal(5)) == 0) {
                description = description + " ����� ���� ";
            } else if (feesTypeId.compareTo(new BigDecimal(6)) == 0) {
                description = description + " ����� ������ ������ ";
            } else if (feesTypeId.compareTo(new BigDecimal(7)) == 0) {
                description = description + " ����� ����� ";
              }

               ViewObject vo = ADFUtil.findIterator("PosMasterVO1Iterator").getViewObject();
               Row rw = vo.createRow();
            System.out.println("****" + rw.getAttribute("Id"));
            rw.setAttribute("BillerId", "" + BillerId);
            rw.setAttribute("ServiceName", "" + description);
            description = description + " - ";

            rw.setAttribute("BillId", "" + procId);
                rw.setAttribute("ApplicantName", partyName);
            if (FixedBenfitAUTO == 0) {
                rw.setAttribute("Amount", payment + "," + fees);
                rw.setAttribute("SettelmentCodes", SettlementCodeGOV + "," + SettlementCodeEF);
            } else {
                rw.setAttribute("Amount", payment + "," + FixedBenfitAUTO + "," + fees);
                rw.setAttribute("SettelmentCodes",
                                SettlementCodeGOV + "," + SettlementCodeAUTO + "," + SettlementCodeEF);
                }

               rw.setAttribute("EfinanceFees", fees);
            rw.setAttribute("GovernmentalFees", payment);
            rw.setAttribute("AutomationFees", FixedBenfitAUTO);
            rw.setAttribute("Description", description + partyName);
                rw.setAttribute("BillingAccount", idNumber);
            rw.setAttribute("BillNumber", "EXC" + procId + currentDate);
            rw.setAttribute("PaidAmount", payment + fees + FixedBenfitAUTO);
                vo.insertRow(rw);
               ADFUtil.findOperation("Commit").execute();
           }


        int BillerId = Integer.parseInt(JSFUtils.getFromSession("pBillerId") + "");

        System.out.println("########################pos");
               String requestId = "";


        requestId = "" + procId + ",EXC" + procId + currentDate;
        //        "19489,2302144125";
               Javaencrypt encrypt = new Javaencrypt();
               StringBuilder a = new StringBuilder();
               char stx = (char) Integer.parseInt("2", 16);
               char etx = (char) Integer.parseInt("3", 16);
               a.append(stx);
        a.append("1," + requestId + ",818,000001,000000" + "," + BillerId);
               a.append(etx);
        System.out.println("Bill number ::   " + a);
               byte[] buffer = encrypt.encryptDecryptXor(a.toString().getBytes(), a.toString().length());
               String bufferStr = "";
               bufferStr = new String(buffer);
               System.out.println("bufferStr:::::::   " + bufferStr);

               FacesContext facesContext = FacesContext.getCurrentInstance();
               ExtendedRenderKitService service =
            (ExtendedRenderKitService) org.apache.myfaces.trinidad.util.Service.getRenderKitService(facesContext,
                                                                                                      ExtendedRenderKitService.class);

               String clientId = testTxtBind.getClientId();
               bufferStr = bufferStr.replace("\\", "\\\\");
               service.addScript(facesContext, "sendSerialLine('" + bufferStr + "','" + clientId + "');");
               //        onlinePaymentBtnBind.setDisabled(true);

               return null;
    }

    public void setTestTxtBind(RichInputText testTxtBind) {
        this.testTxtBind = testTxtBind;
    }

    public RichInputText getTestTxtBind() {
        return testTxtBind;
    }

    //public static void main(String[] args) {
    //    System.out.println(String.format ("%.2f",(14*(1.5/100))));
    //      System.out.println( (14*(1.5/100)));
    //   }
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
                        OperationBinding bindingId = ADFUtil.findOperation("setPosPayment");
                        bindingId.getParamsMap().put("BillNumber", billNumber);
                        bindingId.getParamsMap().put("transactionId", transId);
                bindingId.getParamsMap().put("terminal", terminal);
                        bindingId.getParamsMap().put("amount", amount);
                        Object obj = bindingId.execute();
                        ADFUtil.findOperation("Commit").execute();

                FacesMessage Message =
                    new FacesMessage("\u062a\u0645 \u0627\u0644\u062f\u0641\u0639 \u0628\u0646\u062c\u0627\u062d");
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

    public void setOnlinePaymentBtnBind(RichInputText onlinePaymentBtnBind) {
        this.onlinePaymentBtnBind = onlinePaymentBtnBind;
    }

    public RichInputText getOnlinePaymentBtnBind() {
        return onlinePaymentBtnBind;
    }

    public void setProcedureId(int procedureId) {
        this.procedureId = procedureId;
    }

    public int getProcedureId() {
        return procedureId;
    }

    public void fixedFineVCL(ValueChangeEvent valueChangeEvent) {
        System.err.println("hello = " + valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue() != null) {
            RichInputText inp = (RichInputText) JSFUtils.findComponentInRoot("it63");

            if ("Y".equals(valueChangeEvent.getNewValue() + ""))
                inp.setRendered(true);
            else
                inp.setRendered(false);
        }
    }

    public void checkNotarization(ActionEvent actionEvent) {
        // Add event code here...
        HttpServletRequest httpServletRequest =
            (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ip = httpServletRequest.getRemoteAddr();
        RichInputText num = (RichInputText) JSFUtils.findComponentInRoot("it68");
        RichInputText harff = (RichInputText) JSFUtils.findComponentInRoot("it69");
        RichInputText year = (RichInputText) JSFUtils.findComponentInRoot("it70");
        RichInputText officecode = (RichInputText) JSFUtils.findComponentInRoot("it71");
        RichSelectOneChoice type = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc25");
        RichInputComboboxListOfValues office =
            (RichInputComboboxListOfValues) JSFUtils.findComponentInRoot("notarizationOfficeId");
       
        if (null != type.getValue() && null != num.getValue() && null != harff.getValue() && null != year.getValue() &&
            null != office.getValue()) {
            try {
                System.out.println("------------------" +
                                   JSFUtils.resolveExpression("#{bindings.NotarizationOffice1.attributeValue}"));

            OperationBinding bindingId = ADFUtil.findOperation("findNotarization");
                bindingId.getParamsMap().put("retainerSerial", "" + num.getValue());
                bindingId.getParamsMap().put("harf", "" + harff.getValue());
                bindingId.getParamsMap().put("year", "" + year.getValue());
                bindingId.getParamsMap().put("OfficeId", "" + officecode.getValue());
                bindingId.getParamsMap().put("Type", "" + type.getValue());
            Integer obj = (Integer) bindingId.execute();

                //            OperationBinding bindingId1 = ADFUtil.findOperation("getOfficeName");
                //            bindingId1.getParamsMap().put("code", ""+office.getValue());
                //            String officeName = (String) bindingId1.execute();
              String officeName = (String) office.getValue();
                RichInputText retainerNumber = (RichInputText) JSFUtils.findComponentInRoot("it40");
                retainerNumber.setValue(num.getValue() + " / " + harff.getValue() + " / " + year.getValue() + " / " +
                                        officeName);
                if (obj == -1) {
            UserData userData = new UserData();
                    userData.setUserId(Long.parseLong(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") + ""));
            userData.setEntityId(Long.parseLong(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString()));
            userData.setUserName(ADFUtil.evaluateEL("#{sessionScope.userInfo.userName}").toString());

            String api =
                        NotarizationRetainerPartners.getRetainerDataFromRestApi("" +
                                                                                ADFUtil.findIterator(RESULT_SEARCH_ITERATOR).getViewObject().getCurrentRow().getAttribute("BusinessCaseId").toString(),
                                                                                "" + num.getValue(),
                                                                                "" + officecode.getValue(),
                                                                                "" + harff.getValue(),
                                                                                "" + year.getValue(), 0, 0, userData,
                                                                                "" + type.getValue());

            System.out.println("api api api : " + api);
                    obj = 0;
            }
                System.out.println("ip  is" + ip);
                //            FacesContext.getCurrentInstance().getExternalContext().redirect("https://10.21.35.46/statApp-N/RetainerView?harf=�&retainerSerail=1&year=2022&officeId=1501&lawyerId=59759");
            FacesContext fctx = FacesContext.getCurrentInstance();
                String taskflowURL =
                    "https://" + httpServletRequest.getServerName() + "/statApp-N/RetainerView?harf=" +
                    harff.getValue() + "&retainerSerail=" + num.getValue() + "&year=" + year.getValue() + "&officeId=" +
                    officecode.getValue() + "&lawyerId=" + obj;
            ExtendedRenderKitService erks = Service.getRenderKitService(fctx, ExtendedRenderKitService.class);
            StringBuilder script = new StringBuilder();
            script.append("window.open(\"" + taskflowURL + "\", 'popup','width=800,height=600');");
            erks.addScript(fctx, script.toString());
        } catch (Exception e) {
        }
        } else {
                JSFUtils.addFacesInformationMessage("\u0628\u0631\u062C\u0627\u0621 \u0627\u062F\u062E\u0627\u0644 \u062C\u0645\u064A\u0639 \u0628\u064A\u0627\u0646\u0627\u062A \u0627\u0644\u062A\u0648\u0643\u064A\u0644");
                
            }
    }


    // button search in inventory search
    @SuppressWarnings("oracle.jdeveloper.java.unchecked-conversion-or-cast")
    public String doSearchProsApproveProc() {


        //        JSFUtils.storeOnSession("ppCaseNumber", new BigDecimal(-1));
        //        JSFUtils.storeOnSession("ppCaseType", new BigDecimal(-1));
        //        JSFUtils.storeOnSession("ppCaseYear", new BigDecimal(-1));
        //        JSFUtils.storeOnSession("ppCaseOrigin", new BigDecimal(-1));


        System.out.println("caseNumber" +
                           JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}").toString().length() +
                           "Year" + JSFUtils.resolveExpression("#{bindings.Year.inputValue}").toString().length() +
                           "BusinessCaseTypeId" +
                           JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString().length() +
                           "OrigEntity" +
                           JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}").toString().length());
        if (JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}").toString().length() > 0)
            JSFUtils.addToSession("ppCaseNumberR",
                                  new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}")));
        if (JSFUtils.resolveExpression("#{bindings.Year.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.Year.inputValue}").toString().length() > 0)
            JSFUtils.addToSession("ppCaseYear",
                                  new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.Year.inputValue}")));
        if (JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString().length() > 0)
            JSFUtils.addToSession("ppCaseType",
                                  new BigDecimal("" +
                                                 JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}")));
        if (JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}").toString().length() > 0)
            JSFUtils.addToSession("ppCaseOrigin",
                                  new BigDecimal("" + JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}")));


        if (JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.Year.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}") != null &&
            JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}") != null &&

            JSFUtils.resolveExpression("#{bindings.CaseNumber.inputValue}").toString().length() > 0 &&
            JSFUtils.resolveExpression("#{bindings.Year.inputValue}").toString().length() > 0 &&
            JSFUtils.resolveExpression("#{bindings.BusinessCaseTypeId.inputValue}").toString().length() > 0 &&
            JSFUtils.resolveExpression("#{bindings.OrigEntity.inputValue}").toString().length() > 0)

        {
            ViewObject resultView = ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").getViewObject();

        resultView.executeQuery();
    //  System.out.println("  resultView.getRowCount()   "+  resultView.getRowCount());
        } else {
                FacesMessage Message = new FacesMessage("unvalid Data");
                Message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);

            }
        return null;
    }
    public void judgmentAppealApprove(ActionEvent actionEvent) {
       
        String activeType = JSFUtils.resolveExpression("#{bindings.IsActive.inputValue}") + "";
        
        String FActionsActive = "O";


        Object expression = JSFUtils.resolveExpression("#{bindings.ProcedureId.inputValue}");

        System.err.println("expression =" + expression);
        ViewObject judgmentAppealApprovalView =
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject();
        

        BigDecimal procedureId = null;

        if (judgmentAppealApprovalView.getEstimatedRowCount() > 0) {
            System.err.println("----->>>judgmentAppealApprovalView.getEstimatedRowCount() > 0");
            Row judgmentAppealApprovalRow = judgmentAppealApprovalView.getCurrentRow();
            procedureId = new BigDecimal(judgmentAppealApprovalRow.getAttribute("ProcedureId").toString());
            
            OperationBinding bindingIdExcFolderActions = ADFUtil.findOperation("updateExcFolderActions");
            bindingIdExcFolderActions.getParamsMap().put("procedureId",procedureId);
            bindingIdExcFolderActions.getParamsMap().put("isActive",FActionsActive); 

            Integer obj = (Integer) bindingIdExcFolderActions.execute();
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject().executeQuery();
            FacesMessage Message =
                new FacesMessage("\u062a\u0645 \u0627\u0644\u0627\u0639\u062a\u0645\u0627\u062f \u0628\u0646\u062c\u0627\u062d");
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
        }else{
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject().executeQuery();
            FacesMessage Message =
                new FacesMessage("\u062D\u062F\u062B \u062E\u0637\u0623 \u0628\u0631\u062C\u0627\u0621 \u0627\u0644\u0631\u062C\u0648\u0639 \u0644\u0645\u062F\u064A\u0631 \u0627\u0644\u0646\u0638\u0627\u0645");
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
        }
        System.out.println("folder id is " + JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}"));
        System.err.println("procedureId = " + procedureId);
        
        
    }
    public void judgmentAppealReject(ActionEvent actionEvent) {
        String FActionsActive = "X";

        Object expression = JSFUtils.resolveExpression("#{bindings.ProcedureId.inputValue}");
        System.err.println("expression =" + expression);
        
        ViewObject judgmentAppealApprovalView =
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject();
        
        BigDecimal procedureId = null;

        if (judgmentAppealApprovalView.getEstimatedRowCount() > 0) {
            System.err.println("----->>>judgmentAppealApprovalView.getEstimatedRowCount() > 0");
            Row judgmentAppealApprovalRow = judgmentAppealApprovalView.getCurrentRow();
            procedureId = new BigDecimal(judgmentAppealApprovalRow.getAttribute("ProcedureId").toString());
            
            OperationBinding bindingIdExcFolderActions = ADFUtil.findOperation("updateExcFolderActions");
            bindingIdExcFolderActions.getParamsMap().put("procedureId",procedureId);
            bindingIdExcFolderActions.getParamsMap().put("isActive",FActionsActive); 

            Integer obj = (Integer) bindingIdExcFolderActions.execute();
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject().executeQuery();
            FacesMessage Message =
                new FacesMessage("\u062A\u0645 \u0627\u0644\u0631\u0641\u0636 \u0628\u0646\u062C\u0627\u062D");
            Message.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.addMessage(null, Message);
            }else{
                ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject().executeQuery();
                FacesMessage Message =
                    new FacesMessage("\u062D\u062F\u062B \u062E\u0637\u0623 \u0628\u0631\u062C\u0627\u0621 \u0627\u0644\u0631\u062C\u0648\u0639 \u0644\u0645\u062F\u064A\u0631 \u0627\u0644\u0646\u0638\u0627\u0645");
                Message.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(null, Message);
            }
        System.out.println("folder id is " + JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}"));
        System.err.println("procedureId = " + procedureId);
    }

    public void prosecutorApprove(ActionEvent actionEvent) {
        // Add event code here...
        String activeType = JSFUtils.resolveExpression("#{bindings.IsActive.inputValue}") + "";

        if (activeType.equalsIgnoreCase("E")) {
            setName(JSFUtils.resolveExpression("#{bindings.EditName.inputValue}") + "");


     }

        System.out.println("folder id is " + JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}"));
        OperationBinding bindingId = ADFUtil.findOperation("updateProsecutorFolderData");
        bindingId.getParamsMap().put("folderId",
                                     new BigDecimal(JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}") +
                                                    ""));
        if (activeType.equalsIgnoreCase("P"))
        bindingId.getParamsMap().put("confirmation", "N");
        else
        bindingId.getParamsMap().put("confirmation", "Y");

        Integer obj = (Integer) bindingId.execute();
        ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").getViewObject().executeQuery();
        FacesMessage Message =
            new FacesMessage("\u062a\u0645 \u0627\u0644\u0627\u0639\u062a\u0645\u0627\u062f \u0628\u0646\u062c\u0627\u062d");
        Message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, Message);
    }


    public void prosecutorReject(ActionEvent actionEvent) {
        // Add event code here...
        String activeType = JSFUtils.resolveExpression("#{bindings.IsActive.inputValue}") + "";

        System.out.println("folder id is " + JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}"));
        OperationBinding bindingId = ADFUtil.findOperation("updateProsecutorFolderData");
        bindingId.getParamsMap().put("folderId",
                                     new BigDecimal(JSFUtils.resolveExpression("#{bindings.FolderId.inputValue}") +
                                                    ""));

        bindingId.getParamsMap().put("confirmation", "Y");


        Integer obj = (Integer) bindingId.execute();
        ADFUtil.findIterator("SearchInventoryCaseResultRVO1Iterator").getViewObject().executeQuery();
        FacesMessage Message =
            new FacesMessage("\u062a\u0645 \u0627\u0644\u0631\u0641\u0636 \u0628\u0646\u062c\u0627\u062d");
        Message.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, Message);
    }

    public void prisonFlag(ValueChangeEvent valueChangeEvent) {
        // Add event code here...\
        
        if(isPrisonVisible()){
        System.out.println("the pary is in prison");
        System.out.println(valueChangeEvent.getNewValue());
        if (valueChangeEvent.getNewValue().toString().equalsIgnoreCase("true"))
            ADFContext.getCurrent().getSessionScope().put("ruleNum", new BigDecimal(7));
        else
            ADFContext.getCurrent().getSessionScope().put("ruleNum", new BigDecimal(1));
        }
    }

    public void lawyerPhone(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //
        //        NtraClient client = new NtraClient();
        //        NtraDBHelper op = new NtraDBHelper();
        //        GeneralResponse response = new GeneralResponse();
        //        Connection dbConnection = null;
        //        try {
        //            dbConnection = op.getConnection();
        //            // 29207010101096   0116191920
        //            response = client.callNtraSubmit(dbConnection, "29009011404754", "01099195097", "kamal");
        //            System.out.println("response.getNtraResponseData().getVerficationResult()-----"+response.getNtraResponseData().getVerficationResult());
        //
        //        } catch (SQLException e) {
        //            e.printStackTrace();
        //        }
    }

    public String validateNTRAParty() {
        // Add event code here...
        RichInputText inputID = (RichInputText) JSFUtils.findComponentInRoot("it7");
        RichInputText inputPhone = (RichInputText) JSFUtils.findComponentInRoot("it78");
        if (inputID.getValue() != null && inputPhone.getValue() != null) {
            if (inputID.getValue().toString().length() == 14 && inputPhone.getValue().toString().length() == 11) {
        NtraClient client = new NtraClient();
        NtraDBHelper op = new NtraDBHelper();
        GeneralResponse response = new GeneralResponse();
        Connection dbConnection = null;
        try {
            dbConnection = op.getConnection();
            // 29207010101096   0116191920
                    response =
                        client.callNtraSubmit(dbConnection, inputID.getValue().toString(),
                                              inputPhone.getValue().toString(),
                                              ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserName().toString());
                    System.out.println("response.getNtraResponseData().getVerficationResult()-----" +
                                       response.getNtraResponseData().getVerficationResult());


            OperationBinding bindingId = ADFUtil.findOperation("insertNTRAResults");
            bindingId.getParamsMap().put("NationalId", inputID.getValue().toString());
            bindingId.getParamsMap().put("MobileNo", inputPhone.getValue().toString());
                    bindingId.getParamsMap().put("PartyType", new BigDecimal(1));
                    bindingId.getParamsMap().put("res", "" + response.getNtraResponseData().getVerficationResult());

             bindingId.execute();


            ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
            Row actionsRow = actionsView.getCurrentRow();
  actionsRow.setAttribute("PartyNF", response.getNtraResponseData().getVerficationResult());


        } catch (SQLException e) {
            e.printStackTrace();
        }
            }
        }
        return null;
    }

    public String validateNTRALawyer() {
        // Add event code here...
        RichInputText inputID = (RichInputText) JSFUtils.findComponentInRoot("it38");
        RichInputText inputPhone = (RichInputText) JSFUtils.findComponentInRoot("it77");
        if (inputID.getValue() != null && inputPhone.getValue() != null) {
            if (inputID.getValue().toString().length() == 14 && inputPhone.getValue().toString().length() == 11) {
        NtraClient client = new NtraClient();
        NtraDBHelper op = new NtraDBHelper();
        GeneralResponse response = new GeneralResponse();
        Connection dbConnection = null;
        try {
            dbConnection = op.getConnection();
            // 29207010101096   0116191920
                    response =
                        client.callNtraSubmit(dbConnection, inputID.getValue().toString(),
                                              inputPhone.getValue().toString(),
                                              ((UserInfo) JSFUtils.getFromSession("userInfo")).getUserName().toString());
                    System.out.println("response.getNtraResponseData().getVerficationResult()-----" +
                                       response.getNtraResponseData().getVerficationResult());

            OperationBinding bindingId = ADFUtil.findOperation("insertNTRAResults");
            bindingId.getParamsMap().put("NationalId", inputID.getValue().toString());
            bindingId.getParamsMap().put("MobileNo", inputPhone.getValue().toString());
                    bindingId.getParamsMap().put("PartyType", new BigDecimal(2));
                    bindingId.getParamsMap().put("res", "" + response.getNtraResponseData().getVerficationResult());

             bindingId.execute();


            ViewObject actionsView = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
            Row actionsRow = actionsView.getCurrentRow();
    actionsRow.setAttribute("LawyerNF", response.getNtraResponseData().getVerficationResult());


        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

        }

        return null;

    }
    public void judgmentAppealSearch() {
            System.err.println("======judgmentAppealApprovalSearch=======");
            ADFUtil.findIterator("JudgmentAppealApprovalRVO1Iterator").getViewObject().executeEmptyRowSet();
        }
    public void judgmentAppealSearchValidate(){
        System.err.println("judgmentAppealSearchValidate");
        
    }

}
