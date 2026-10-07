package com.egabi.cjexec.view.beans.claimprocedure;

import java.util.Iterator;
import java.util.List;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class Utils {

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
}
