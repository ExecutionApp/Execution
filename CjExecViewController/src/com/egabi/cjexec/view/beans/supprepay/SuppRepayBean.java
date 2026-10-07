package com.egabi.cjexec.view.beans.supprepay;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.JSFUtils;

import java.math.BigDecimal;

import java.util.Calendar;

import java.util.List;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class SuppRepayBean {

    private int groupNumberAll, receiptNumberAll;

    public SuppRepayBean() {
    }

    public String suppRepayActionLis() {
        String result = null;
        try {
            BigDecimal procID = addProcedure();
            //  addAction(procID);
            System.out.println(procID);
            int updated = updateSelectedValues(procID);
            int updateed2 = updateInstallmentValues(procID);
            if (updated == 1) {
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "GROUP_NUMBER_RECEIPT_NUMBER_ERROR");
                result = null;
            } else if (updateed2 == 1) {
                JSFUtils.showFacesErrorMessageFromBundle("bundle", "GROUP_NUMBER_RECEIPT_NUMBER_ERROR");
                result = null;
            } else {
                ADFUtil.executeVoidOperation("Commit", null);
                result = "save";
            }
        } catch (Exception e) {
            System.out.println(">>>>>>>>Error" + e.getMessage());
        }
        return result;
    }


    public int updateSelectedValues(BigDecimal procID) throws Exception {
        int updated = 0;

        DCIteratorBinding iter = ADFUtil.findIterator("RepayExcCourtBcPartyDebtIterator");
        ViewObject vo = iter.getViewObject();
        Row[] values = vo.getAllRowsInRange();

        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");

        for (Row r : values) {
            System.out.println(r.getAttribute("SettlementProcId"));
            String groupNumber = r.getAttribute("GroupNumber").toString();
            String receiptNumber = r.getAttribute("ReceiptNumber").toString();
            long resPartyId = Long.parseLong(r.getAttribute("ResPartyId").toString());


            int countGroupNumber = am.countRepayExcCourtBcPartyDebtByGroupNumber(groupNumber);
            int countReceiptNumber = am.countRepayExcCourtBcPartyDebtByReceiptNumber(receiptNumber);

            if (countGroupNumber > 0 || countReceiptNumber > 0) {
                updated = 1;
            } else {
                if (r.getAttribute("SettlementProcId") == null) {
                    updated = 0;
                    r.setAttribute("SettlementProcId", procID);
                    r.setAttribute("PaidAmount", r.getAttribute("Amount"));

                    ADFUtil.executeVoidOperation("Commit", null);
                    ADFUtil.executeOperation("updateRepayInfo", "feesType", r.getAttribute("FeesTypeId"), "settlProcID",
                                             procID, "groupID", r.getAttribute("GroupNumber"), "settlDate",
                                             r.getAttribute("SettlementDate"), "paidAmount", r.getAttribute("Amount"),
                                             "settAuthID", r.getAttribute("SettlementAuthId"), "recipNum",
                                             r.getAttribute("ReceiptNumber"));

                    if (Integer.valueOf(r.getAttribute("FeesTypeId").toString()) == 4) {
                        ADFUtil.setBoundAttributeValue("ExecutionFlag", 2);
                    }
                }
            }


        }

        return updated;
    }


    public int updateInstallmentValues(BigDecimal procID) {
        int updated = 0;

        DCIteratorBinding iter = ADFUtil.findIterator("RepayExcFolderProcInstallIterator");
        ViewObject vo = iter.getViewObject();
        Row[] values = vo.getAllRowsInRange();

        CjExecAMImpl am = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");

        for (Row r : values) {

            String groupNumber = r.getAttribute("GroupNumber").toString();
            String receiptNumber = r.getAttribute("ReceiptNumber").toString();

            int countGroupNumber = am.countRepayExcFolderProcInstallByGroupNumber(groupNumber);
            int countReceiptNumber = am.countRepayExcFolderProcInstallByReceiptNumber(receiptNumber);

            if (countGroupNumber > 0 || countReceiptNumber > 0) {
                updated = 1;
            } else {
                if (r.getAttribute("SettlementProcId") == null) {
                    r.setAttribute("SettlementProcId", procID);
                    r.setAttribute("PaidAmount", r.getAttribute("InstallmentAmount"));
                    updateTotalAmountValue(Float.valueOf(r.getAttribute("InstallmentAmount").toString()));
                }
            }
        }
        return updated;
    }

    public void updateTotalAmountValue(float paidInstall) {
        List<Row> bcParties = ADFUtil.getIteratorAsList("ExcCourtBcPartyDebtIterator");
        for (Row r : bcParties) {
            int feeType = Integer.valueOf(r.getAttribute("FeesTypeId").toString());
            System.out.println(">>>>>>>>Fee type" + feeType);
            if (feeType == 3) {
                System.out.println(">>>>>>>>Fee type" + feeType);
                if (r.getAttribute("PaidAmount") == null)
                    r.setAttribute("PaidAmount", paidInstall);
                else {
                    float newVal = Float.valueOf(r.getAttribute("PaidAmount").toString()) + paidInstall;
                    r.setAttribute("PaidAmount", newVal);
                }
                break;
            }
        }
    }


    public BigDecimal addProcedure() throws Exception {

        BigDecimal procID = (BigDecimal) ADFUtil.executeOperation("createFProcedureAndAction", null);

        /* java.sql.Date currentTime=new java.sql.Date(Calendar.getInstance().getTimeInMillis());
           DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderProceduresIterator");
           ViewObject vo = iter.getViewObject();
           Row row = vo.createRow();

           row.setAttribute("ResPartyId", ADFUtil.getAttributeValue("ResPartyId"));
          // row.setAttribute("CapacityType", 1);
           row.setAttribute("CreatedDate", currentTime);
           row.setAttribute("CreatedBy", 3169);
           row.setAttribute("ProcedureStatus", 12);

           row.setAttribute("ProcedureDate", currentTime);

           vo.insertRow(row);

           try {
               ADFUtil.executeVoidOperation("Commit", null);

               iter.executeQuery();
               vo.executeQuery();


           } catch (Exception e) {
               System.out.println(">>>>>>Error"+e.getMessage());
           }*/

        return procID;
    }

    /*   public void addAction(BigDecimal procID)
       {
               DCIteratorBinding iter = ADFUtil.findIterator("ExcFolderActionsIterator");
               ViewObject vo = iter.getViewObject();

               Row row = vo.createRow();
               row.setAttribute("ActionType", 7);
              // row.setAttribute("FolderRecordId", ADFUtil.getAttributeValue("Id"));
               row.setAttribute("ProcedureId", procID);

               vo.insertRow(row);

           }*/


    public void excuteInstallment() {
        // Add event code here...
        System.out.println(ADFUtil.getAttributeValue("ResPartyId"));

        try {
            ADFUtil.executeVoidOperation("Execute", null);
        } catch (Exception e) {
        }
    }

    public String groupReceiptNumbersToAll() {

        ViewObject repayExcCourtBcPartyDebtView =
            ADFUtil.findIterator("RepayExcCourtBcPartyDebtIterator").getViewObject();
        Row[] repayExcCourtBcPartyDebtRow = repayExcCourtBcPartyDebtView.getAllRowsInRange();

        for (Row row : repayExcCourtBcPartyDebtRow) {
            if (groupNumberAll != 0) {
                row.setAttribute("GroupNumber", groupNumberAll);
            }
            if (receiptNumberAll != 0) {
                row.setAttribute("ReceiptNumber", receiptNumberAll);
            }
        }
        repayExcCourtBcPartyDebtView.executeQuery();

        ViewObject repayExcFolderProcInstalView =
            ADFUtil.findIterator("RepayExcFolderProcInstallIterator").getViewObject();
        if (repayExcFolderProcInstalView.getEstimatedRowCount() > 0) {
            Row[] repayExcFolderProcInstalRows = repayExcFolderProcInstalView.getAllRowsInRange();
            for (Row row : repayExcFolderProcInstalRows) {
                if (groupNumberAll != 0) {
                    row.setAttribute("GroupNumber", groupNumberAll);
                }
                if (receiptNumberAll != 0) {
                    row.setAttribute("ReceiptNumber", receiptNumberAll);
                }
            }
            repayExcFolderProcInstalView.executeQuery();
        }
        return null;
    }


    public void setGroupNumberAll(int groupNumberAll) {
        this.groupNumberAll = groupNumberAll;
    }

    public int getGroupNumberAll() {
        return groupNumberAll;
    }

    public void setReceiptNumberAll(int receiptNumberAll) {
        this.receiptNumberAll = receiptNumberAll;
    }

    public int getReceiptNumberAll() {
        return receiptNumberAll;
    }
}
