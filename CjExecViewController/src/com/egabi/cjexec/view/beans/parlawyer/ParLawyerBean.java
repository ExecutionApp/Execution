package com.egabi.cjexec.view.beans.parlawyer;

import com.egabi.cjexec.view.utils.ADFUtil;

import java.math.BigDecimal;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import org.apache.myfaces.trinidad.util.ComponentReference;

public class ParLawyerBean {
    
    public ParLawyerBean() {
        super();
    }
    
    public void clickButton(ActionEvent actionEvent) {
        
        System.out.println(">>>>>>>>>>>>>>>>>>>>  Add Lawyer     <<<<<<<<<<<<<<<<<<<<");
        ADFUtil.findOperation("addLawyer").execute();
    }

    public String setAction() {
        // Add event code here...
        ADFUtil.addMessage(null, ADFUtil.evaluateEL("#{bundle.LAWYER_MESSAGE}").toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>  Add Lawyer  =  Done   <<<<<<<<<<<<<<<<<<<<");
        return "back";
    }
}
