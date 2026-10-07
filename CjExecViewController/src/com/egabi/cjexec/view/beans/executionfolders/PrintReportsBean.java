package com.egabi.cjexec.view.beans.executionfolders;

import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.math.BigDecimal;

import java.util.HashMap;

import javax.faces.event.ActionEvent;

import net.sf.jasperreports.engine.JRException;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;

public class PrintReportsBean extends PrintEnabledBean {
    public PrintReportsBean() {
        reportFolder = "";
        reportName = "";
    }


    public void printReports(ActionEvent actionEvent) throws FileNotFoundException, JRException, IOException,
                                                             Exception {
        // Add event code here...
        String reportName = "", reportFolder = "";
        reportName = ADFUtil.evaluateEL("#{pageFlowScope.reportName}").toString();
        reportFolder = ADFUtil.evaluateEL("#{pageFlowScope.reportFolder}").toString();
        this.reportFolder = reportFolder;
        this.reportName = reportName;
        OperationBinding op = ADFUtil.findOperation("ExecuteWithParams");
        op.execute();
       
        if( ADFUtil.evaluateEL("#{bindings.ProcedureId.inputValue}")== null) {
           // ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.DONE_SUCCESSFULLY}").toString());
           ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.ACTION_BEFORE_PRINT}").toString());
        } 
        else if (((this.reportName.equals("Prison") || this.reportName.equals("Implement")))&&( Integer.parseInt(ADFUtil.evaluateEL("#{bindings.JudgementId.inputValue}").toString()) == 7)) {
               
                   ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.ACTION_BEFORE_PRINT}").toString());
                        
            }
        else
        {
             if (this.reportName.equals("Person")) {
                            int CAPACITY_TYPE =
                                Integer.parseInt(ADFUtil.evaluateEL("#{bindings.CapacityType.inputValue}").toString());
                            if(CAPACITY_TYPE == 2)
                                this.reportName = "Authorization";
                            else if(CAPACITY_TYPE == 3)
                                this.reportName = "AuthorizationOnAuthorization";
            }
        print();
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public void populateParamsMap(HashMap parametersMap) {

            int PROCEDURE_ID =
                Integer.parseInt(ADFUtil.evaluateEL("#{bindings.ProcedureId.inputValue}").toString());
            int USER_ID = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
            System.out.println("PROCEDURE_ID ======================== ###########  = " + PROCEDURE_ID);
            if (this.reportName.equals("Implement")) {
                parametersMap.put("PROCEDURE_ID", new BigDecimal(PROCEDURE_ID));
                parametersMap.put("userId", USER_ID);

            } else if (this.reportName.equals("Prison")) {

                parametersMap.put("PROCEDURE_ID", new BigDecimal(PROCEDURE_ID));


            } else if (this.reportName.equals("EnableOpposition")) {
                parametersMap.put("PROCEDURE_ID", new BigDecimal(PROCEDURE_ID));
            } else if (this.reportName.equals("Person") || this.reportName.equals("Authorization") || this.reportName.equals("AuthorizationOnAuthorization")) {
                
                System.out.println("reportName ======================== ###########  = " +  this.reportName);
                parametersMap.put("userId", USER_ID);
                parametersMap.put("PROCEDURE_ID", new BigDecimal(PROCEDURE_ID));
            }
            else if (this.reportName.equals("InstallmentFine")) {
                            parametersMap.put("PROCEDURE_ID", new BigDecimal(PROCEDURE_ID));
                        }

        }


    //}
}
