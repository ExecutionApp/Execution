package cjexecviewcontroller;

import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.CjUtils;
import com.egabi.cjexec.view.utils.FilesModel;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import oracle.stellent.ridc.model.DataObject;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;

public class AuditUpdateBean {
    public AuditUpdateBean() {
    }
    private final String CHARGE_DESIONS_ITERATOR = "ExcCourtBcChargeDecisionsVO1Iterator";
    private final String searchInventoryResultRVOAudit = "SearchInventoryResultRVOAuditIterator";
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
    public void rowDisclourre(RowDisclosureEvent rowDisclosureEvent) {
        // make row current //
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
        ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").executeQuery();
        //        setJudgmentText();
        //        Object folderId = ADFUtil.getElExpression("#{bindings.IdFolderData.inputValue}");
        //        ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow().setAttribute("TransFolderId", folderId);
        //        ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow().setAttribute("TransFolderId",
        //        folderId);
        //        ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow().setAttribute("TransFolderId",
        //        folderId);
    }

    public void discloseToFilterPartiesData(DisclosureEvent disclosureEvent) {
        // Add event code here...
        if (disclosureEvent.isExpanded() == true) {
            String PartyID = disclosureEvent.getComponent().getAttributes().get("Id").toString();
            String iteratorName = disclosureEvent.getComponent().getAttributes().get("Iterator").toString();
            Key key = new Key(new Object[] { PartyID });
            RowSetIterator binding = ADFUtil.findIterator(iteratorName).getRowSetIterator();
            //  System.out.println("before =" + binding.getCurrentRowIndex());
            Row[] find = binding.findByKey(key, 1);
            if (iteratorName.equals("SearchInventoryResultRVOAuditIterator")) {
                RowSetIterator rws = ADFUtil.findIterator(iteratorName).getViewObject().createRowSetIterator(null);
                System.out.println("SearchInventoryResultRVOAuditIterator " + rws.getRowCount());
                rws.reset();
              ViewObject vvo =  ADFUtil.findIterator("FormCaseDescriptionPartiesVO4Iterator").getViewObject();
              RowSetIterator rrws =  vvo.createRowSetIterator(null) ;
                while (rws.hasNext()) {
                    Row rw = rws.next();
                    Object rwsPartyId = rw.getAttribute("ResPartyId");
                    Object PartyId = rw.getAttribute("PartyId");
                    System.err.println(PartyID + "%%" + rwsPartyId);
                    if (rwsPartyId.toString().compareTo(PartyID) == 0) {
                        binding.setCurrentRow(rw);
                        while (rrws.hasNext()) {
                            Row rrw = rrws.next();
                            Object PartyId2 = rrw.getAttribute("PartyId");
                            if (PartyId.toString().compareTo(PartyId2.toString()) == 0) {
                                vvo.setCurrentRow(rrw);
                            }
                        }
                        
                        break;
                    }
                }
            } else
                binding.setCurrentRow(find[0]);

            //   ADFUtil.findIterator("ParPartiesVO2Iterator").getViewObject().setCurrentRow(find);
            //   System.out.println("after =" + binding.getCurrentRowIndex());
        }


        setJudgmentText();
        Object folderId = ADFUtil.getElExpression("#{bindings.IdFolderData.inputValue}");
        System.out.println("folderId    -----"+folderId);
        if (ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow() != null)
        ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow().setAttribute("TransFolderId",
                                                                                                 folderId);
        if (ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow() != null)
            ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow().setAttribute("TransFolderId",
                                                                                                      folderId);
        if (ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow() != null)
            ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getCurrentRow().setAttribute("TransFolderId",
                                                                                                      folderId);

    }
    
    
    
    public void discloseToFilterClaims(DisclosureEvent disclosureEvent) {
        // Add event code here...
        if (disclosureEvent.isExpanded() == true) {
            String claimID = disclosureEvent.getComponent().getAttributes().get("Id").toString();
            String iteratorName = disclosureEvent.getComponent().getAttributes().get("Iterator").toString();
            Key key = new Key(new Object[] { claimID });
            ViewObject binding = ADFUtil.findIterator(iteratorName).getViewObject();
            //  System.out.println("before =" + binding.getCurrentRowIndex());
            Row[] find = binding.findByKey(key, 1);
            if (iteratorName.equals("ExcClaimsVO4Iterator")) {
                RowSetIterator rws = ADFUtil.findIterator(iteratorName).getViewObject().createRowSetIterator(null);
                System.out.println("ExcClaimsVO4Iterator " + rws.getRowCount());
                rws.reset();
              ViewObject vvo =  ADFUtil.findIterator("ExcProcedureClaimVO4Iterator").getViewObject();
              System.out.println("vvo  count"+vvo.getRowCount());
              RowSetIterator rrws =  vvo.createRowSetIterator(null) ;
                while (rws.hasNext()) {
                    Row rw = rws.next();
                    Object ClaimId = rw.getAttribute("Id");
                   
                    System.err.println(claimID + "%%" + ClaimId);
                    if (ClaimId.toString().compareTo(claimID) == 0) {
                        binding.setCurrentRow(rw);
                        while (rrws.hasNext()) {
                            Row rrw = rrws.next();
                            Object ClaimIdd = rrw.getAttribute("ClaimId");
                            if (ClaimIdd.toString().compareTo(ClaimId.toString()) == 0) {
                                vvo.setCurrentRow(rrw);
                                System.err.println("vvo  count"+vvo.getRowCount());
                            }
                        }
                        
                        break;
                    }
                }
            } else
                binding.setCurrentRow(find[0]);

            //   ADFUtil.findIterator("ParPartiesVO2Iterator").getViewObject().setCurrentRow(find);
            //   System.out.println("after =" + binding.getCurrentRowIndex());
        }


        

    }
    

    public void changeCaseType(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFUtil.setBoundAttributeValue("BusinessCaseTypePolice", valueChangeEvent.getNewValue());
        ADFUtil.setBoundAttributeValue("BusinessCaseTypeBus", valueChangeEvent.getNewValue());
    }

    public void changeCaseNumber(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFUtil.setBoundAttributeValue("PoliceNumber", valueChangeEvent.getNewValue());

    }

    public void changeEntityId(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("valueChangeEvent " + valueChangeEvent.getNewValue());
        ADFUtil.setBoundAttributeValue("EntityId", valueChangeEvent.getNewValue());

    }

    private String getValFromSearchIterator(String attribute) {
        return ADFUtil.findIterator(searchInventoryResultRVOAudit).getCurrentRow().getAttribute(attribute) + "";
    }

    private boolean validateIfBusinessCaseFoundBefore() {
        if (ADFUtil.getDCBindingContainer().findIteratorBinding("searchInventoryResultRVOAudit") != null) {
        String caseNumber = getValFromSearchIterator("CaseNumber");
        String year = getValFromSearchIterator("CaseYear");
        String bCaseType = getValFromSearchIterator("CasTypeId");
        String police = getValFromSearchIterator("PoliceId");
        //      System.err.println(caseNumber +" - "+year+" -- "+bCaseType+"== "+police);
        String newCaseNumber = ADFUtil.evaluateEL("#{bindings.CaseNumber.inputValue}") + "";
        String newYear = ADFUtil.evaluateEL("#{bindings.ParYear1.inputValue}") + "";
        String newbCaseType = ADFUtil.evaluateEL("#{bindings.CaseType.inputValue}") + "";
        String newPolice = ADFUtil.evaluateEL("#{bindings.OriginEntity.inputValue}") + "";
        //      System.err.println(newCaseNumber +" - "+newYear+" -- "+newbCaseType+"== "+newPolice);
        if (caseNumber.equalsIgnoreCase(newCaseNumber) && year.equalsIgnoreCase(newYear) &&
            bCaseType.equalsIgnoreCase(newbCaseType) && police.equalsIgnoreCase(newPolice)) {
            return false;
        } else {
            OperationBinding binding = ADFUtil.findOperation("ValidatePoliceReportForLogCheck");
            binding.execute();
            if (binding.getResult().toString().equals("0")) {
                return false;
            } else {
                return true;
            }
        }
        }else {
                return false;
            }
    }

    public void saveAction(ActionEvent actionEvent) {
        try {
           
          setJudgmentText();
            validateBusiness();
            if (!validateIfBusinessCaseFoundBefore()) {
                ADFUtil.findOperation("Commit").execute();
                ADFUtil.findIterator("BusinessCase1Iterator").getCurrentRow().refresh(Row.REFRESH_UNDO_CHANGES |
                                                                                      Row.REFRESH_WITH_DB_FORGET_CHANGES);
                ADFUtil.findIterator("ParPoliceAuthorityReportVO1Iterator").getCurrentRow().refresh(Row.REFRESH_UNDO_CHANGES |
                                                                                                    Row.REFRESH_WITH_DB_FORGET_CHANGES);
                if (ADFUtil.findIterator("ParPartiesVO2Iterator").getCurrentRow() != null)
                    ADFUtil.findIterator("ParPartiesVO2Iterator").getCurrentRow().refresh(Row.REFRESH_UNDO_CHANGES |
                                                                                          Row.REFRESH_WITH_DB_FORGET_CHANGES);
                //      ADFUtil.findOperation("Rollback").execute();//BusinessCase1Iterator
                ADFUtil.findOperation("Commit").execute();
//                ADFUtil.findOperation("Rollback").execute();
                JSFUtils.addFacesInformationMessage(ADFUtil.evaluateEL("#{bundle.SAVEDONE}") + "");
            } else {
                JSFUtils.addFacesInformationMessage(ADFUtil.evaluateEL("#{bundle.POLICE_REP_LOG}") + "");
            }
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
            if (e.getMessage().contains("PAR_POLICE_AUTHORITY_REPO_UK"))
                JSFUtils.addFacesInformationMessage("this Number found");
            else
                JSFUtils.addFacesInformationMessage("do not save");
        }

        // Add event code here...
    }

    public void changePoliceStationEntId(ValueChangeEvent valueChangeEvent) {
        // Add event code here... PoliceStationEntityId
        if (valueChangeEvent.getOldValue().equals(ADFUtil.getBoundAttributeValue("PoliceStationEntityId")))
            ADFUtil.setBoundAttributeValue("PoliceStationEntityId", valueChangeEvent.getNewValue());
    }

    public void setImprisonmentZero(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println(valueChangeEvent.getNewValue());
        BigDecimal type = new BigDecimal(valueChangeEvent.getNewValue()+"");
        
//        if(type.compareTo(new BigDecimal(23))==0  || type.compareTo(new BigDecimal(42))==0  || type.compareTo(new BigDecimal(27))==0 || type.compareTo(new BigDecimal(101))==0
//        ||type.compareTo(new BigDecimal(28))==0  || type.compareTo(new BigDecimal(9))==0){
//            ADFUtil.setBoundAttributeValue("UpdatedDate2",  new Date());
//            ADFUtil.setBoundAttributeValue("UpdatedDate1", new Date());
//        
//        }
//        
        
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().equals("3")) {
            ADFUtil.setBoundAttributeValue("ImprisonmentDays", 0);
            ADFUtil.setBoundAttributeValue("ImprisonmentMonths", 0);
            ADFUtil.setBoundAttributeValue("ImprisonmentYears", 0);
        }
        if (valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue().toString().equals("35")) {
            ADFUtil.setBoundAttributeValue("Attendance", null);
            ADFUtil.setBoundAttributeValue("DeletedFlag4", "Y");
            System.out.println("AAAAAAAAAAAAAAAAtendance");
            
        }
    }

    public void reuploadFileListener(ValueChangeEvent valueChangeEvent) {
        System.err.println("><><><><>< BELAL");
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        try {
            Row row = ADFUtil.findIterator("CriminalJusticeFilesDetailsVO6Iterator").getCurrentRow();
            UploadedFile uf = (UploadedFile) valueChangeEvent.getNewValue();
            DataObject do_;
            do_ = CjUtils.insertAttachmentAsFileIntoUCM(uf.getInputStream(), 1, 1, 1, 1, uf.getFilename());
            String dDocName = do_.get("dDocName");
            String documentId = do_.get("dID");
            row.setAttribute("Did", documentId);
            row.setAttribute("Ddocname", dDocName);
            row.setAttribute("Filename", uf.getFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private BigDecimal getBusinessCaseDetailId(Long businesscaseid) {
        OperationBinding oper = ADFUtil.findOperation("getBusinessCaseDetailId");
        oper.getParamsMap().put("businessCaseId", businesscaseid);
        Object res = oper.execute();
        if (res != null)
            return new BigDecimal(res.toString());
        else
            return new BigDecimal(0);
    }

    private BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    public String newRowAction() {
        OperationBinding operationBinding = getBindings().getOperationBinding("CreateInsert");
        Object result = operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
            return null;
        }

        Object businesscaseid = ADFUtil.findIterator("BusinessCase1Iterator").getCurrentRow().getAttribute("Id");
        int userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
        BigDecimal businessCaseDetailId = getBusinessCaseDetailId(new Long(businesscaseid.toString()));
        Row row = ADFUtil.findIterator("CriminalJusticeFilesDetailsVO6Iterator").getCurrentRow();
        row.setAttribute("Businesscaseid", businesscaseid);
        row.setAttribute("Userid", userId);
        row.setAttribute("CreatedBy", userId);
        row.setAttribute("CreatedDate", new Date());
        row.setAttribute("Entityid",
                         Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString()));
        //    row.setAttribute("Businesscasedetailedid", null); //TODO
        //    row.setAttribute("PartyId", null); //TODO
        return null;
    }

    public void downloadAllAttachedFiles(FacesContext context, OutputStream out) throws IOException {
        InputStream fis = null;
        try {
            FilesModel content = CjUtils.DownloadContent(ADFUtil.evaluateEL("#{row.Did}").toString(), "");
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


    public void setJudgmentText() {

        Object obImprisonmentYears = ADFUtil.getElExpression("#{bindings." + "ImprisonmentYears" + ".inputValue}");
        Object obImprisonmentMonths = ADFUtil.getElExpression("#{bindings." + "ImprisonmentMonths" + ".inputValue}");
        Object obImprisonmentDays = ADFUtil.getElExpression("#{bindings." + "ImprisonmentDays" + ".inputValue}");
        Object obAttendence = ADFUtil.getElExpression("#{bindings." + "Attendance" + ".inputValue}");
        Object obGuranteeAmount = ADFUtil.getElExpression("#{bindings." + "GuranteeAmount" + ".inputValue}");
        Object obStopExecutionFlag = ADFUtil.getElExpression("#{bindings." + "StopExecutionFlag" + ".inputValue}");
        Object obStopExecutionPrisFlag = ADFUtil.getElExpression("#{bindings." + "StopExecutionPrisonFlag" + ".inputValue}");
        Object obPenaltyFlag = ADFUtil.getElExpression("#{bindings." + "PenaltyFlag" + ".inputValue}");
        Object obExecutionFlag = ADFUtil.getElExpression("#{bindings." + "ExecutionFlag" + ".inputValue}");
        Object obForeverFlag = ADFUtil.getElExpression("#{bindings." + "ForeverFlag" + ".inputValue}");
        Object obHardLabourFlag = ADFUtil.getElExpression("#{bindings." + "HardLabourFlag" + ".inputValue}");
        //        Object obWorkAmountFlag =ADFUtil.getElExpression("#{bindings."+"WorkAmountFlag "+".inputValue}");
        Object obWorkAmount = ADFUtil.getElExpression("#{bindings." + "WorkAmount" + ".inputValue}");
        //        Object obPenaltyDueDate =ADFUtil.getElExpression("#{bindings."+"PenaltyDueDate"+".inputValue}");
        //        Object obWorkAmountComFlag =ADFUtil.getElExpression("#{bindings."+"WorkAmountComFlag"+".inputValue}");
        //        Object obPenaltyAmountFixedFlag =ADFUtil.getElExpression("#{bindings."+"PenaltyAmountFixedFlag"+".inputValue}");
        Object obPenaltyAmount = ADFUtil.getElExpression("#{bindings." + "PenaltyAmount" + ".inputValue}");
        //        Object obPenaltyAmountComFlag =ADFUtil.getElExpression("#{bindings."+"PenaltyAmountComFlag"+".inputValue}");
        //        Object obUndefinedPenaltyFlag =ADFUtil.getElExpression("#{bindings."+"UndefinedPenaltyFlag"+".inputValue}");
        //        Object obUnDefineedPenalty = ADFUtil.getElExpression("#{bindings."+"UnDefineedPenalty"+".inputValue}");
        //        Object obUndefineedPenaltyComFlag = ADFUtil.getElExpression("#{bindings."+"UndefineedPenaltyComFlag"+".inputValue}");
        String comment =
            ADFUtil.getElExpression("#{bindings." + "Comments1" + ".inputValue}") != null ?
            ADFUtil.getElExpression("#{bindings." + "Comments1" + ".inputValue}").toString() : null;

        String note =
            ADFUtil.getElExpression("#{bindings." + "Notes" + ".inputValue}") != null ?
            ADFUtil.getElExpression("#{bindings." + "Notes" + ".inputValue}").toString() : "";

System.out.println("%%%%%%%%%%%%%%%"+obStopExecutionPrisFlag);
        String judgmentText =
            JSFUtils.getJudementText(JSFUtils.getBigDecimal(obImprisonmentYears),
                                     JSFUtils.getBigDecimal(obImprisonmentMonths),
                                     JSFUtils.getBigDecimal(obImprisonmentDays), JSFUtils.getBigDecimal(obAttendence),
                                     JSFUtils.getBigDecimal(obGuranteeAmount), JSFUtils.getBigDecimal(obWorkAmount),
                                     JSFUtils.getBigDecimal(obPenaltyAmount), JSFUtils.getBoolean(obPenaltyFlag),
                                     JSFUtils.getBoolean(obExecutionFlag), JSFUtils.getBoolean(obStopExecutionFlag),
                                     JSFUtils.getBoolean(obForeverFlag), JSFUtils.getBoolean(obHardLabourFlag),
                                     comment , JSFUtils.getBoolean(obStopExecutionPrisFlag));
        System.err.println((JSFUtils.getBigDecimal(obImprisonmentYears)+"---"+
                                     JSFUtils.getBigDecimal(obImprisonmentMonths)+"---"+
                                     JSFUtils.getBigDecimal(obImprisonmentDays)+"---"+ JSFUtils.getBigDecimal(obAttendence)+"---"+
                                     JSFUtils.getBigDecimal(obGuranteeAmount)+"---"+ JSFUtils.getBigDecimal(obWorkAmount)+"---"+
                                     JSFUtils.getBigDecimal(obPenaltyAmount)+"---"+ JSFUtils.getBoolean(obPenaltyFlag)+"---"+ "--DD-"+ obPenaltyFlag+"--DDD-"+
                                     JSFUtils.getBoolean(obExecutionFlag)+"---"+ JSFUtils.getBoolean(obStopExecutionFlag)+"---"+
                                     JSFUtils.getBoolean(obForeverFlag)+"---"+ JSFUtils.getBoolean(obHardLabourFlag)+"---"+
                                     comment));
        System.out.println(obPenaltyFlag+"ffffffffffffffff"+judgmentText);

        ADFUtil.setEL("#{bindings.JudgmentText.inputValue}", judgmentText + " \n " + note);
        //    System.err.println(">>>Belal>> "+judgmentText);
    }

    private boolean validateBusiness() {
        boolean isOk = true;
        Object folderType = ADFUtil.getBoundAttributeValue("FolderType");
        if (folderType != null && ADFUtil.invals(folderType, 3, 6)) {
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "ImprisonmentDays", 0);
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "ImprisonmentMonths", 0);
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "ImprisonmentYears", 0);
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "HardLabourFlag", null);
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "ForeverFlag", null);
            ADFUtil.setAttributeValue(CHARGE_DESIONS_ITERATOR, "ExecutionFlag", null);
        }
        return isOk;
    }
    public void refresh() {
              FacesContext facesContext = FacesContext.getCurrentInstance();
              String refreshpage = facesContext.getViewRoot().getViewId();
              ViewHandler  viewHandler =facesContext.getApplication().getViewHandler();
              UIViewRoot viewroot =  viewHandler.createView( facesContext, refreshpage); 
              viewroot.setViewId(refreshpage); 
              facesContext.setViewRoot(viewroot);
        }

    public String searchAction() {
//        RichPanelGroupLayout pt = (RichPanelGroupLayout) JSFUtils.findComponentInRoot("pg5");
//        pt.setRendered(false);
        ADFContext.getCurrent().getSessionScope().put("pClaimDeleted", "N");
//ViewObject  viewObjectVO = ADFUtil.findIterator("BusinessCaseDetailsVO1Iterator").getViewObject();pt1
//    viewObjectVO.executeQuery();
//    System.out.println(viewObjectVO.getRowCount()+"-----------------");
        if (ADFUtil.findIterator("BusinessCase1Iterator").getEstimatedRowCount() == 0) {
            JSFUtils.showInfCaseNotFound();

        } else {
            System.out.println("ID    ------" + JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
            RichSelectOneChoice af = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc11");
            af.setValue(new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") + ""));
            JSFUtils.setExpressionValue("#{bindings.NeyabKolyaaTran.inputValue}",
                                        new BigDecimal(JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") +
                                                       ""));
            JSFUtils.setExpressionValue("#{bindings.EntityId2.inputValue}",
                                        JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
       
            refresh();
        }
        
        
//        pt.setRendered(true);
        return null;
    }

    public void degreeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        JSFUtils.resolveExpression("#{row.bindings.NewDegree.attribute}");
        System.out.println(valueChangeEvent.getNewValue() + "-----" +
                           JSFUtils.resolveExpression("#{row.bindings.NewDegree.attribute}"));

        JSFUtils.storeOnSession("actionTypeId",
                                new BigDecimal(JSFUtils.resolveExpression("#{row.bindings.NewDegree.attribute}").toString()));

        System.out.println("actionTypeId  is " + JSFUtils.getFromSession("actionTypeId"));

        String appealEntity = ADFUtil.evaluateEL("#{bindings.NeyabKolyaaTran.inputValue}") + "";
        String gozEntity = ADFUtil.evaluateEL("#{bindings.EntityId2.inputValue}") + "";
        if (valueChangeEvent.getNewValue().toString().compareTo("4") == 0)
            JSFUtils.addToSession("AppealEntity", appealEntity);
        else
            JSFUtils.addToSession("AppealEntity", gozEntity);
        JSFUtils.addToSession("GozEntity", gozEntity);
    }

    public void activateFolderData(ValueChangeEvent valueChangeEvent) {
        System.err.println("activation is "+valueChangeEvent.getNewValue());
        if((Boolean)(valueChangeEvent.getNewValue()))
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal(1));
        else
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal(2));
        
        // Add event code here...
    }

    public void deleteProcedure(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
//       Row rw = ADFUtil.findIterator("CourtBcHearingResPartiesVO2Iterator").getViewObject().getCurrentRow() ;
//       System.out.println(rw.getAttribute("Id")+"  ----");
        System.err.println(JSFUtils.resolveExpression("#{bindings.DeletedFlag1.inputValue}")+"delete provedure flag is "+valueChangeEvent.getNewValue());
        if(JSFUtils.resolveExpression("#{bindings.DeletedFlag1.inputValue}")!= null){
            if((Boolean)(valueChangeEvent.getNewValue())){
        JSFUtils.setExpressionValue("#{bindings.DeletedFlag1.inputValue}", "Y");
//               rw.setAttribute("DeletedFlag", "Y");
            }
            else{
        JSFUtils.setExpressionValue("#{bindings.DeletedFlag1.inputValue}", "N");
//                rw.setAttribute("DeletedFlag", "N");
            }
        }
      
//        System.out.println("---"+rw.getAttribute("DeletedFlag"));
    }

    public String newChargParties() {
        
        
        ViewObject formCaseDescriptionDesVO = ADFUtil.findIterator("FormCaseDescriptionDesPartVO4Iterator").getViewObject();
      RowSetIterator rws =  formCaseDescriptionDesVO.createRowSetIterator(null);
      if(rws.hasNext()){
          ViewObject vo = ADFUtil.findIterator("SearchInventoryResultRVOAuditIterator").getViewObject();
        Row currRow =  vo.getCurrentRow();
          System.out.println("-----"+currRow.getAttribute("DecisionId"));
        BigDecimal decisionId = (BigDecimal) currRow.getAttribute("DecisionId");
        BigDecimal resPartyId = (BigDecimal)   currRow.getAttribute("ResPartyId");
          BigDecimal chargId = new BigDecimal (0);
          while(rws.hasNext()){
              Row rw = rws.next();
              if(chargId.compareTo(new BigDecimal (0))==0){
              ViewObject ChargeDecisionsVo = ADFUtil.findIterator("ExcCourtBcChargeDecisionsVO1Iterator").getViewObject();
              Row ChargeDecisionsrw = ChargeDecisionsVo.createRow();
              ChargeDecisionsrw.setAttribute("DecisionId", decisionId);
                 chargId = (BigDecimal)   ChargeDecisionsrw.getAttribute("Id");
              System.out.println("***"+ChargeDecisionsrw.getAttribute("Id"));
              ChargeDecisionsVo.insertRow(ChargeDecisionsrw);
              }
              ViewObject chargesPartiesvo = ADFUtil.findIterator("ExcCourtBcChargesPartiesVO2Iterator").getViewObject();
              Row  chargesPartiesrw = chargesPartiesvo.createRow();
              chargesPartiesrw.setAttribute("PartyCrimeId", rw.getAttribute("Id"));
              chargesPartiesrw.setAttribute("DecisionId", decisionId);
              chargesPartiesrw.setAttribute("ChargeDecisionId", chargId);
              chargesPartiesrw.setAttribute("ResPartyId", resPartyId);
              
              chargesPartiesvo.insertRow(chargesPartiesrw);
        
          }
        
      }
      else
      {}
        return null;
    }

    public void guranteeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.Guarantee.inputValue}", valueChangeEvent.getNewValue());
    }

    public void seqKVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(  valueChangeEvent.getNewValue() != null ){
      int seq = Integer.parseInt( valueChangeEvent.getNewValue().toString());
      int year = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.Year1.inputValue}")+""  );
      int caseSchema = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}")+""  );
      String type="";
      if (caseSchema==3643)
         type= "\u062c\u0646\u062d \u0645\u0633\u062a\u0623\u0646\u0641";
           
              if (caseSchema==3647)
                type=  "\u062c\u0646\u0627\u064a\u0627\u062a \u0643\u0644\u064a\u0629";
                  if (caseSchema==3648)
                   type=   "\u062c\u0646\u0627\u064a\u0627\u062a \u0637\u0648\u0627\u0631\u0626 \u0627\u0645\u0646 \u062f\u0648\u0644\u0629 ";
                      if (caseSchema==3646)
         type= "\u062c\u0646\u0627\u064a\u0627\u062a \u0627\u0642\u062a\u0635\u0627\u062f\u064a\u0629";
      System.out.println("eeee"+JSFUtils.resolveExpression("#{bindings.Seq.inputValue}"));  
       System.out.println("wwww"+ JSFUtils.resolveExpression("#{bindings.Year1.inputValue}"));  
        System.out.println("qqqq"+JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}"));   
        
        
            JSFUtils.setExpressionValue("#{bindings.Label1.inputValue}", seq +"/"+year+type);
        }
    }

    public void yearKVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if(  valueChangeEvent.getNewValue() != null ){
        int year = Integer.parseInt( valueChangeEvent.getNewValue().toString());
        int seq = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.Seq.inputValue}")+""  );
        int caseSchema = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}")+""  );
        String type="";
        if (caseSchema==3643)
         type= "\u062c\u0646\u062d \u0645\u0633\u062a\u0623\u0646\u0641";
           
              if (caseSchema==3647)
                type=  "\u062c\u0646\u0627\u064a\u0627\u062a \u0643\u0644\u064a\u0629";
                  if (caseSchema==3648)
                   type=   "\u062c\u0646\u0627\u064a\u0627\u062a \u0637\u0648\u0627\u0631\u0626 \u0627\u0645\u0646 \u062f\u0648\u0644\u0629 ";
                      if (caseSchema==3646)
         type= "\u062c\u0646\u0627\u064a\u0627\u062a \u0627\u0642\u062a\u0635\u0627\u062f\u064a\u0629";
        System.out.println("eeee"+JSFUtils.resolveExpression("#{bindings.Seq.inputValue}"));
        System.out.println("wwww"+ JSFUtils.resolveExpression("#{bindings.Year1.inputValue}"));
        System.out.println("qqqq"+JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}"));   
        
        
            JSFUtils.setExpressionValue("#{bindings.Label1.inputValue}", seq +"/"+year+type);
        }
    }
}
