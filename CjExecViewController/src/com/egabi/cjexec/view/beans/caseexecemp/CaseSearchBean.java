package com.egabi.cjexec.view.beans.caseexecemp;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

import oracle.adf.view.rich.component.rich.output.RichOutputFormatted;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

public class CaseSearchBean {
    public CaseSearchBean() {
        super();
    }
    
    public void degreeVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("XXXX VCL YYYY -- " + valueChangeEvent.getNewValue());
        RichInputListOfValues hearingDates = (RichInputListOfValues) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(),
                                                           "courtCircleHearingDate");
        hearingDates.setDisabled(false);
        if (valueChangeEvent.getNewValue().equals("")) {
            hearingDates.setValue(null);
        }
        //        ViewObject hearingDatesVO = ADFUtil.findIterator("CourtCirclesHearingDatesRVO1Iterator").getViewObject();
        //        hearingDatesVO.setNamedWhereClauseParam("degreeIdParam", valueChangeEvent.getNewValue());
        //        hearingDatesVO.executeQuery();
        ViewObjectImpl hearingDatesVO =
            (ViewObjectImpl) ADFUtil.findIterator("CourtCirclesHearingDatesRVO1Iterator").getViewObject();
        ViewCriteria vc = hearingDatesVO.getViewCriteria("CourtCirclesHearingDatesRVOCriteria");
        VariableValueManager vm = hearingDatesVO.ensureVariableManager();
        vm.setVariableValue("degreeIdParam", valueChangeEvent.getNewValue());
        hearingDatesVO.applyViewCriteria(vc);
        hearingDatesVO.executeQuery();
    }
    
    
    public void hearingDateVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        //        oracle.jbo.domain.Number entityIdxx =
        //            (oracle.jbo.domain.Number) JSFUtils.resolveExpression("#{bindings.Id.inputValue}");
    /*
        Row row1 = ADFUtil.findIterator("CasesSearchFormROVIterator").getCurrentRow();
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

        //Date hearingDate = convertDomainDateToUtilDate((oracle.jbo.domain.Date) valueChangeEvent.getNewValue());

        ViewObject ob1 = ADFUtil.findIterator("ProvisionedCasesInSession1Iterator").getViewObject();
        ViewObject ob2 = ADFUtil.findIterator("AllCasesInSessionRVO1Iterator").getViewObject();
        ob1.setNamedWhereClauseParam("degreeId", degreeId);
        ob1.setNamedWhereClauseParam("entityId", entityId);
        ob1.setNamedWhereClauseParam("hearingDateId", hearingDateId);

        ob1.setNamedWhereClauseParam("degreeId", degreeId);
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
    */
    }

}
