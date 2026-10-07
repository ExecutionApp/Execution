package com.egabi.cjexec.view.beans.appealDigits;

import com.egabi.cjexec.model.views.bcdetails.BusinessCaseDetailsVO;
import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import java.util.Iterator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class AppealDigits {
    public AppealDigits() {
    }

    BigDecimal ledgerId = new BigDecimal(0);

    public void SearchAppeal() {
        BigDecimal entityId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}");
        BigDecimal entityKolayaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        
        String policeNo = JSFUtils.resolveExpression("#{bindings.PoliceNo.inputValue}")+"";
//        OperationBinding myOperation = ADFUtil.findOperation("searchAppealsBC");
//        myOperation.getParamsMap().put("entityId", entityId);
//        myOperation.execute();
        
                ViewObjectImpl vo =
            (ViewObjectImpl) ADFUtil.findIterator("AppealBCROVO1Iterator").getViewObject();
        ViewCriteria criteria = null;
        if ((policeNo == null)||(null!=policeNo && "null".equals(policeNo))) {   
            System.out.println("AppealBCROVOCriteria");
                
            criteria = vo.getViewCriteria("AppealBCROVOCriteria");
        }
        else{
            System.out.println("AppealBCROVOCriteria1");
            criteria = vo.getViewCriteria("AppealBCROVOCriteria1"); 
        }
        
        vo.applyViewCriteria(criteria);
        
        vo.setNamedWhereClauseParam("pEntityId", entityId);
        vo.setNamedWhereClauseParam("pKolyaEntity", entityKolayaId);
        if (policeNo != null && !"null".equals(policeNo))
        vo.setNamedWhereClauseParam("pPoliceNo", policeNo);
        System.out.println("policeNo --"+policeNo);
        System.out.println("entityId --"+entityId);
        System.out.println("pKolyaEntity --"+entityKolayaId);
        System.out.println("vo  query is "+vo.getQuery());
        vo.executeQuery();
        
    }

    public String searchBC() {
        // Add event code here...
//        ADFUtil.findOperation("Rollback").execute();
        SearchAppeal();
        BigDecimal entityKolayaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
        System.out.println("flgflgflgflgflgflgflg   -----"+flg);
        if (flg)
            entityKolayaId =new BigDecimal(JSFUtils.getFromSession("errandEntity2") +"");


        OperationBinding myOperation2 = ADFUtil.findOperation("getLedgerId");
        myOperation2.getParamsMap().put("entityKolayaId", entityKolayaId);
        BigDecimal id = (BigDecimal) myOperation2.execute();
        this.setLedgerId(id);

        //        if(ADFUtil.findIterator("AppealBCROVO1Iterator").getViewObject().getRowCount()>0)
        //             if (JSFUtils.resolveExpression("#{bindings.Id.inputValue}")== null){
        //
        //        ADFContext.getCurrent().getSessionScope().put("BC_ID", (BigDecimal)  JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
        //        ADFUtil.findOperation("CreateInserBCDetals").execute();
        //        ADFUtil.findOperation("CreateInsertLedgerSeq").execute();
        //        JSFUtils.setExpressionValue("#{bindings.BusinessCaseId.inputValue}", (BigDecimal)  JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
        //        JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", new BigDecimal (3643));
        //             }
        return null;
    }

    public void LedgerVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.out.println(JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") + "DDSDSD  " +
                           JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));


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

    public void rowDisclosureListenerContractTable(RowDisclosureEvent rowDisclosureEvent) {
        // make row current //
        
        System.out.println(this.getLedgerId()+"UUUU***UU" + JSFUtils.resolveExpressionAsString("#{row.bindings.Id.inputValue}"));

        RichTable table = (RichTable) rowDisclosureEvent.getSource();
        
        RowKeySet discloseRowKeySet = table.getDisclosedRowKeys();
        RowKeySet lastAddedRowKeySet = rowDisclosureEvent.getAddedSet();
        Iterator lastAddedRowKeySetIter = lastAddedRowKeySet.iterator();
        System.out.println("****"+lastAddedRowKeySetIter.hasNext());
        if (lastAddedRowKeySetIter.hasNext()) {
            System.err.println("**********111*");
            discloseRowKeySet.clear();
            Object lastRowKey = lastAddedRowKeySetIter.next();
            discloseRowKeySet.add(lastRowKey);
            makeDisclosedRowCurrent(table, lastAddedRowKeySet);
            AdfFacesContext adfFacesContext = null;
            adfFacesContext = AdfFacesContext.getCurrentInstance();
            adfFacesContext.addPartialTarget(table.getParent());
            BigDecimal CaseSchemaId = new  BigDecimal (0);
            if (JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}") != null) 
                CaseSchemaId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.CaseSchemaId.inputValue}");
            System.err.println(CaseSchemaId+"****4444**CaseSchemaId*****"+JSFUtils.resolveExpression("#{bindings.Id.inputValue}"));
            if (JSFUtils.resolveExpression("#{bindings.Id.inputValue}") == null || CaseSchemaId.compareTo(new BigDecimal (3643)) !=0 ) {
                System.err.println("***********");
                ADFContext.getCurrent().getSessionScope().put("pBCIDD",
                                                              (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
                ADFContext.getCurrent().getSessionScope().put("BC_ID",
                                                              (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
//                ViewObject BusinessCaseDetailsVO =ADFUtil.findIterator("BusinessCaseDetailsVO1Iterator").getViewObject();
//                BusinessCaseDetailsVO.ensureVariableManager().setVariableValue("pBCIDD", (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
//                BusinessCaseDetailsVO.executeQuery();
                ADFUtil.findOperation("CreateInserBCDetals").execute();
                System.out.println("***********-------");
                ADFUtil.findOperation("CreateInsertLedgerSeq").execute();
                System.out.println("***********");
                JSFUtils.setExpressionValue("#{bindings.BusinessCaseId.inputValue}",
                                            (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
                JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", new BigDecimal(3643));
                JSFUtils.setExpressionValue("#{bindings.LedgerId.inputValue}", this.getLedgerId());

            }


        }
        // / Make row current ////
        //         ViewObject view3 = ADFUtil.findIterator("ActionsRVO1Iterator").getViewObject();
        //        view3.setWhereClause(null);
        //        view3.executeQuery();


    }


    public Boolean validateHasrKolyaa() {
        BigDecimal entityId = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}") != null)
            entityId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}");
        Boolean flg = (Boolean) "Y".equalsIgnoreCase(JSFUtils.getFromSession("errandF").toString());
        System.out.println("flgflgflgflgflgflgflg   -----"+flg);
        if (flg)
            entityId =new BigDecimal(JSFUtils.getFromSession("errandEntity") +"");
        BigDecimal year = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.Year1.inputValue}") != null)
            year = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Year1.inputValue}");
        BigDecimal seq = new BigDecimal(0);
        if (JSFUtils.resolveExpression("#{bindings.Seq.inputValue}") != null)
            seq = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Seq.inputValue}");
        BigDecimal CASE_SCHEMA_ID = new BigDecimal(0);
        System.out.println("JSFUtils.resolveExpression(\"#{bindings.SchemaId.inputValue}\")----"+JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
        if (JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
            CASE_SCHEMA_ID = (BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");

        OperationBinding myOperation = ADFUtil.findOperation("validateHasrBC");
        myOperation.getParamsMap().put("entityId", entityId);
        myOperation.getParamsMap().put("year", year);
        myOperation.getParamsMap().put("seq", seq);
        myOperation.getParamsMap().put("CASE_SCHEMA_ID", CASE_SCHEMA_ID);
        myOperation.getParamsMap().put("ledgerId",  this.getLedgerId());
       
        Boolean result = (Boolean) myOperation.execute();
        return result;
    }

    public void selectionTable(SelectionEvent selectionEvent) {
        // Add event code here...
        RowKeySet oldKeySet = selectionEvent.getRemovedSet();

        RichTable table = (RichTable) selectionEvent.getSource();
        if (JSFUtils.resolveExpression("#{bindings.UpdatedBy.inputValue}") != null ||
            JSFUtils.resolveExpression("#{bindings.Seq.inputValue}") != null ||
            JSFUtils.resolveExpression("#{bindings.Year1.inputValue}") != null ||
            JSFUtils.resolveExpression("#{bindings.LedgerAppeal.inputValue}") != null) {
            ADFUtil.invokeEL("#{bindings.AppealBCROVO11.collectionModel.makeCurrent}",
                             new Class[] { SelectionEvent.class }, new Object[] { selectionEvent });
            System.out.println("TTT");
            //            Row selectedRow = (Row) ADFUtil.evaluateEL("#{bindings.DepartmentsVO1Iterator.currentRow}");

            if (JSFUtils.resolveExpression("#{bindings.Id.inputValue}") == null) {
                ADFContext.getCurrent().getSessionScope().put("BC_ID",
                                                              (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
                ADFUtil.findOperation("CreateInserBCDetals").execute();
                ADFUtil.findOperation("CreateInsertLedgerSeq").execute();
                JSFUtils.setExpressionValue("#{bindings.BusinessCaseId.inputValue}",
                                            (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id1.inputValue}"));
                JSFUtils.setExpressionValue("#{bindings.CaseSchemaId.inputValue}", new BigDecimal(3643));

            }
        }

        else {
            System.out.println("ELSE  RRRRRRRRRRRR");
            table.setSelectedRowKeys(oldKeySet);

            AdfFacesContext adfFacesCtx = AdfFacesContext.getCurrentInstance();

            adfFacesCtx.addPartialTarget(table);
            FacesContext fctx = FacesContext.getCurrentInstance();

            fctx.addMessage("\u062a\u0646\u0628\u064a\u0647",
                            new FacesMessage("\u0628\u0631\u062c\u0627\u0621 \u0627\u062f\u062e\u0627\u0644 \u0627\u0644\u0645\u062d\u062f\u0627\u062f\u062a"));

            fctx.renderResponse();
        }
    }

    public void updateReservation() {
        ViewObject vo = ADFUtil.findIterator("CourtBcHearingReservationVO1Iterator").getViewObject();
        RowSetIterator rws = vo.createRowSetIterator(null);
        System.out.println("row Count " + rws.getRowCount());
        if (rws.getRowCount() > 0) {
            rws.next().setAttribute("SentToCourt", "O");
            System.out.println("Updated");
        }
    }

    public String confirmAppealDigits() {
        // Add event code here...NeyabaaKolyaa
        if(JSFUtils.resolveExpression("#{bindings.Seq.inputValue}") != null && JSFUtils.resolveExpression("#{bindings.Year1.inputValue}")!= null )
        {
        JSFUtils.storeOnSession("actionTypeId",new BigDecimal(3));
        ADFContext.getCurrent().getSessionScope().put("NeyabaaKolyaa",
                                                      (BigDecimal) JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));

        Boolean valide = false;
        System.out.println("FFFFF   "+ this.getLedgerId());
        JSFUtils.setExpressionValue("#{bindings.LedgerId.inputValue}", this.getLedgerId());


        valide = validateHasrKolyaa();
        System.out.println("Validation  : " + valide);
        if (!valide) {
            updateReservation();
            ADFUtil.findOperation("Commit").execute();

            SearchAppeal();


        }
        else {
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "HASR_BC_KOLYAA");
            }
        }
        return null;
    }

    public void setLedgerId(BigDecimal ledgerId) {
        this.ledgerId = ledgerId;
    }

    public BigDecimal getLedgerId() {
        return ledgerId;
    }
}
