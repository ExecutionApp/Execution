package com.egabi.cjexec.view.beans.executionfolders;

import com.egabi.cjexec.view.utils.ADFUtil;

import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.binding.OperationBinding;

public class ExecutionFoldersBean {
    private RichButton executButton;
    private RichButton printPrison;
    private RichButton printImplement;

    public ExecutionFoldersBean() {

    }


    public String executionMethod() {
        // Add event code here...

        System.out.println("============================= ");
        String action = "";


        OperationBinding b = ADFUtil.findOperation("executePrison");
        b.execute();

        //  ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.DONE_SUCCESSFULLY}").toString());
        getExecutButton().setDisabled(true);
//        getPrintImplement().setDisabled(false);
//        getPrintPrison().setDisabled(false);
        OperationBinding op = ADFUtil.findOperation("ExecuteWithParams");
        op.execute();
        //        OperationBinding oppp = ADFUtil.findOperation("Execute");
        //        oppp.execute();

        return action;
    }

    public String validationMethod() {
        // Add event code here...
        //  "#{bindings.importData.execute}"
        System.out.println("============================= ");
        String action = "";


//        OperationBinding b = ADFUtil.findOperation("validateActions");
//        b.execute();
        int result;
        result = 0;//Integer.parseInt(b.getResult().toString());
        System.out.println("result ============================= " + result);
        if (result == 1) {
            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.CAN_NOT_DO_OPPOSITION_TWICE}").toString());
        } else if (result == 2) {
            System.out.println("result ************************" + result);
            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_DO_ENABLE_OPPOSITION}").toString());
        } else if (result == 3) {
            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.CAN_NOT_DO_OPPOSITION_PERSONAL_ATTEND}").toString());
        } else if (result == 4) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.DURATION_EXCEED}").toString());
        } else if (result == 5) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.JUDGE_TYPE_MUST_BE}").toString());
        } else if (result == 6) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.INSTALLMENT_PAYED}").toString());
        } else if (result == 7) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.NO_INSTALLMENT}").toString());
        } else if (result == 8) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_DO_SUPPLY_AMOUNT_FIRST}").toString());
        } else if (result == 9) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_BE_PERSONAL_ATTEND}").toString());
        } else if (result == 10) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_BE_WANTED}").toString());
        } else if (result == 11) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.BAIL_PAYED}").toString());
        } else if (result == 12) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.CAN_NOT_INSTALL_TWICE}").toString());
        } else if (result == 13) {
            System.out.println("result ************************" + result);

            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.HAVE_NO_FINE}").toString());
        } else if (result == 14) {
            System.out.println("result ************************" + result);
            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_HAVE_INSTALLMENT_REQUEST}").toString());
        }else if (result == 15) {
            System.out.println("result ************************" + result);
            ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.MUST_EXECUT_PRISON_FIRST}").toString());
        }
        else if (result == 16) {
                    System.out.println("result ************************" + result);
                    ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.CANCEL_EXECUT_PRISON_DONE}").toString());
                    action = "action";
                }
        /*********************** By Sohaila Esawy **************************/
        else if (result == 17) {
                    System.out.println("result ************************" + result);
                    ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.INSTALLMENT_CANCELLATION}").toString());
                    action = "";
                }
        else if (result == 0) {
            System.out.println("result ************************" + result);
            action = "action";
        }
        //ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.DONE_SUCCESSFULLY}").toString());

        return action;
    }

    public void setExecutButton(RichButton executButton) {
        this.executButton = executButton;
    }

    public RichButton getExecutButton() {
        return executButton;
    }

    public void setPrintPrison(RichButton printPrison) {
        this.printPrison = printPrison;
    }

    public RichButton getPrintPrison() {
        return printPrison;
    }

    public void setPrintImplement(RichButton printImplement) {
        this.printImplement = printImplement;
    }

    public RichButton getPrintImplement() {
        return printImplement;
    }
}
