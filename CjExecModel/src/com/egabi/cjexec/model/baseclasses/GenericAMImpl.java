package com.egabi.cjexec.model.baseclasses;

import java.math.BigDecimal;

import oracle.jbo.Session;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.ViewObjectImpl;

public class GenericAMImpl extends ApplicationModuleImpl  {
    public GenericAMImpl() {
        super();
    }
    
    protected void prepareSession(Session session) {
        super.prepareSession(session);
        
        this.getDBTransaction().setClearCacheOnRollback(false);
    }
    
    public ViewObjectImpl filterByWhereClause(String viewObjectName, String whereClause) {
        ViewObjectImpl vo = (ViewObjectImpl) findViewObject(viewObjectName);
        vo.setRangeSize(-1);
        vo.setWhereClause(null);
        vo.executeQuery();
        vo.setWhereClause(whereClause);
        vo.executeQuery();
        return vo;
    }


    public void clearViewWhereClause(String viewObjectName) {
        ViewObject view = findViewObject(viewObjectName);
        if (view != null) {
            view.setWhereClause(null);
            view.executeQuery();
            view.reset();
        }
    }

    public ViewObjectImpl filterByViewCriteria(String viewObjectName, String columns, String values) {
        System.err.println(columns);
        System.err.println(values);
//        System.err.println(viewObjectName);
        ViewObjectImpl vo = (ViewObjectImpl) findViewObject(viewObjectName);
        ViewCriteria vc = vo.createViewCriteria();
        ViewCriteriaRow vcr = vc.createViewCriteriaRow();
        String[] attr_param = columns.split(",");
        String[] vars_param = values.split(",");
        for (int i = 0; i < attr_param.length; i++) {
            String col = "";
            if (attr_param[i] != null)
                col = attr_param[i];
            String val = vars_param[i];
            System.err.println("col : " + col);
            System.err.println("val : " + val);
            if (!(val.equalsIgnoreCase("null")))
                vcr.setAttribute(col, val);
            else
                vcr.setAttribute(col, "");
        }
        vc.insertRow(vcr);
        vo.applyViewCriteria(vc);
        vo.executeQuery();

        return vo;

    }
    
    
    public ViewObjectImpl clearViewCriteria(String viewObjectName){
            ViewObjectImpl vo = (ViewObjectImpl) findViewObject(viewObjectName);
            vo.applyViewCriteria(null);
            vo.executeQuery();
            return vo;
        }
    
    
    public BigDecimal getSequenceValue(String SequenceName) {
        SequenceImpl MySeq = new SequenceImpl(SequenceName, getDBTransaction());

        return MySeq.getSequenceNumber().getBigDecimalValue();
    }
    
    
}
