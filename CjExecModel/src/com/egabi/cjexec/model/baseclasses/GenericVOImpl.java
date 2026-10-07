package com.egabi.cjexec.model.baseclasses;

import java.text.SimpleDateFormat;

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.Key;
import oracle.jbo.server.ViewObjectImpl;

public class GenericVOImpl extends ViewObjectImpl {
    private static final ADFLogger _logger = ADFLogger.createADFLogger(GenericVOImpl.class);
    private Key currentRowKey;
    private int currentRowIndexInRange;
    //
    //    public void beforeRollback(TransactionEvent TransactionEvent) {
    //            try {
    //        System.err.println("$$$$$$$$$$$$$$$");
    //        if (this.isExecuted()) {
    //            ViewRowImpl currentRow = (ViewRowImpl)this.getCurrentRow();
    //            if (currentRow != null) {
    //                byte newRowState = currentRow.getNewRowState();
    //                if (newRowState != Row.STATUS_INITIALIZED && newRowState != Row.STATUS_NEW) {
    //                    currentRowKey = currentRow.getKey();
    //                    int rangeIndexOfCurrentRow = this.getRangeIndexOf(currentRow);
    //                    currentRowIndexInRange = rangeIndexOfCurrentRow;
    //                }
    //            }
    //        }
    //        super.beforeRollback(TransactionEvent);
    //
    //        }
    //        catch (Exception e )
    //        {e.printStackTrace();}
    //    }
    //
    //    public void afterRollback(TransactionEvent TransactionEvent) {
    //        try {
    //        super.afterRollback(TransactionEvent);
    //        if (currentRowKey != null) {
    //            _logger.fine("STARTING CURRENT ROW RESET");
    //
    //            this.executeQuery();
    //            Key k = new Key(currentRowKey.getAttributeValues());
    //            Row[] found = this.findByKey(k, 1);
    //            if (found != null && found.length == 1) {
    //                Row r = this.getRow(k);
    //                this.setCurrentRow(r);
    //                if (currentRowIndexInRange >= 0) {
    //                    this.scrollRangeTo(r, currentRowIndexInRange);
    //                }
    //            }
    //
    //            _logger.fine("FINISHING CURRENT ROW RESET");
    //        }
    //        currentRowKey = null;
    //        }
    //        catch (Exception e )
    //        {e.printStackTrace();}
    //    }

    protected Object formatDate(Object ob) {
        if (ob != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return simpleDateFormat.format(new java.util.Date(ob.toString().replace("-", "/")));
        } else
            return null;
    }
}
