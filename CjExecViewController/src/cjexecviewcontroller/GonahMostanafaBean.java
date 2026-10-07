package cjexecviewcontroller;

import com.egabi.cjexec.view.utils.ADFUtil;

import com.egabi.cjexec.view.utils.JSFUtils;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

public class GonahMostanafaBean {


    public void mamoreyaVCL(ValueChangeEvent valueChangeEvent) {
        ADFUtil.setEL("#{bindings.NeyabKolyaa.inputValue}", null);
    }

    public void mamoreyaCheckBox(ValueChangeEvent valueChangeEvent) {
        RichPanelGroupLayout koleya = (oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout) ADFUtil.findComponentInRoot("pg46");
        RichPanelGroupLayout mamoreya = (oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout) ADFUtil.findComponentInRoot("pgl9");

        if (valueChangeEvent.getNewValue() != null && Boolean.parseBoolean(valueChangeEvent.getNewValue()+"") == true){
            koleya.setVisible(false);  
            mamoreya.setVisible(true);
            ADFUtil.setEL("#{bindings.NeyabKolyaa.inputValue}", null);
            JSFUtils.refreshComponent("pg46");
            JSFUtils.refreshComponent("pgl9");
        }else{
            koleya.setVisible(true);      
            mamoreya.setVisible(false);
            ADFUtil.setEL("#{bindings.NeyabaMamoreya.inputValue}", null);
            JSFUtils.refreshComponent("pg46");
            JSFUtils.refreshComponent("pgl9");
        }
    }
}
