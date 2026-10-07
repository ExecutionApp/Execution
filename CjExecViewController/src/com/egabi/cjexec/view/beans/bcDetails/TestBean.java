package com.egabi.cjexec.view.beans.bcDetails;

import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;
import com.egabi.cjexec.view.utils.PrintEnabledBean;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelHeader;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class TestBean extends PrintEnabledBean {
    private RichInputListOfValues partyDec;
    private RichInputListOfValues manyPartyDesc;
    private java.util.Date date = new java.util.Date();

    public void setDate(java.util.Date date) {
        this.date = date;
    }
    
    public String printReport25()
    {
            BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id.inputValue}");
            System.err.println(JSFUtils.resolveExpression("#{item.bindings.Id.inputValue}")+"ID  of   Folder  : "+id);
         String    nameOfReport = "ExecutionReport25";
          String  reportName = "ExecutionReport25";
          
            String pathReport = "/u02/config/report/";
            if(JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}")!=null)
            pathReport = (String) JSFUtils.resolveExpression("#{bindings.ValuesUrl.inputValue}");
            try
            {
            super.print(pathReport,nameOfReport, "procedures", reportName);
            } catch(Exception e)
            {
            System.err.println("Error Print Report " + e.getMessage());
            }
            
            return null;
        }

    public java.util.Date getDate() {
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(date);
           calendar.add(Calendar.DATE, 0);
           Date yesterday = calendar.getTime();

           System.out.println(yesterday);
           System.out.println(date);
        return yesterday;
    }

    public TestBean() {
    }

    public void enter(ClientEvent clientEvent) {
        // Add event code here...RichButton
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov1");
        if (lov.getValue() != null) {
            System.err.println("GGGGGGGGGGGGGGGG");
            ADFUtil.findOperation("AsignDescionDesToParty").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("ilov1");
            JSFUtils.refreshComponent("b1");
            RichButton btn = (RichButton) JSFUtils.findComponentInRoot("b1");
            btn.setDisabled(false);
            JSFUtils.refreshComponent("b1");
        }
    }
    
    Vector vec33 = new Vector ();
    
    public Boolean check (BigDecimal id)
    {
        
        System.out.println("GGG  "+ id);
            Boolean check = true ;
                      Vector vect = new Vector ();
                      if (ADFContext.getCurrent().getSessionScope().get("vec3") != null)
                           vect = (Vector) ADFContext.getCurrent().getSessionScope().get("vec3");
                      
                    System.err.println("VEC-------***"+ADFContext.getCurrent().getSessionScope().get("vec3") ); 
                     
                          for (int x =0 ;x< vect.size() ; x++)  
                      
                      {
                           System.out.println( " ===================== " + vect.get(x)); 
                          
                          
                              
                              BigDecimal  crimId = (BigDecimal) vect.get(x);
                          
                     if (  crimId.compareTo(id)==0)
                     {
                         check = false ;
                        System.out.println("FOUNDDDDDDDDDDDDDDDDDD");
                         return check;
                     }
                      
                          }
            vec33.add(id);
            ADFContext.getCurrent().getSessionScope().put("vec3" ,vec33 );
        
            return check;
        }
    
    public void removeDescId (BigDecimal id)
    {
            Vector vect = new Vector ();
            if (ADFContext.getCurrent().getSessionScope().get("vec3") != null)
                 vect = (Vector) ADFContext.getCurrent().getSessionScope().get("vec3"); 
        
            vect.remove(id);
            ADFContext.getCurrent().getSessionScope().put("vec3" ,vect );
        }
    

    public void enterMany(ClientEvent clientEvent) {
        // Add event code here...
        System.err.println("##########");
        
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("descionId2");
        if (lov.getValue() != null) {
            RichButton normalSave = (RichButton) JSFUtils.findComponentInRoot("b1");
            normalSave.setRendered(false);
            JSFUtils.refreshComponent("b1");
            JSFUtils.setExpressionValue("#{bindings.Flag.inputValue}", "true");
            //   RichPanelHeader
            RichPanelHeader phChargMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph6");
            RichPanelHeader phDescMany = (RichPanelHeader) JSFUtils.findComponentInRoot("ph7");
            RichPanelHeader phChargOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph2");
            RichPanelHeader phDescOne = (RichPanelHeader) JSFUtils.findComponentInRoot("ph3");
            phDescOne.setRendered(false);
            phChargOne.setRendered(false);
            phChargMany.setRendered(true);
            phDescMany.setRendered(true);
            System.err.println("GGGGGGGGGGGGGGGG"); 
           BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Id3.inputValue}");
            System.err.println("GGGGGGGGGGGGGGGG"+id); 
            if(check(id))
            ADFUtil.findOperation("CreateWithParams").execute();

            lov.setValue("");
            JSFUtils.refreshComponent("descionId2");
        }
        JSFUtils.refreshComponent("pg25");
    }

    public void setPartyDec(RichInputListOfValues partyDec) {
        this.partyDec = partyDec;
    }

    public RichInputListOfValues getPartyDec() {
        return partyDec;
    }

    public void setManyPartyDesc(RichInputListOfValues manyPartyDesc) {
        this.manyPartyDesc = manyPartyDesc;
    }

    public RichInputListOfValues getManyPartyDesc() {
        return manyPartyDesc;
    }

    public void addParty(ClientEvent clientEvent) {
        // Add event code here...

        System.err.println("GGGGGGGGGGGGGGGG");
        ADFUtil.findOperation("addNewconvictedParty").execute();
        ADFUtil.findOperation("CreateInsertFolderData").execute();
      //  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
      //  JSFUtils.setExpressionValue("#{bindings.IsActive.inputValue}", "N");
        JSFUtils.refreshComponent("pt5");
//        this.getSaveBTN().setDisabled(true);
        RichInputListOfValues lov = (RichInputListOfValues) JSFUtils.findComponentInRoot("ilov3");
        lov.setValue("");
    }


    public void yearVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
    //  Integer newVal = (Integer)  valueChangeEvent.getNewValue();
        
        if (valueChangeEvent.getNewValue() != null){
System.err.println(valueChangeEvent.getNewValue()+"Get years :  "+JSFUtils.resolveExpression("#{bindings.PrisonYear.inputValue}"));
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.InPrisonYears.inputValue}", valueChangeEvent.getNewValue());
        print();
        }
        else
        {
                System.err.println(valueChangeEvent.getOldValue()+"Get old years :  "+JSFUtils.resolveExpression("#{bindings.PrisonYear.inputValue}"));
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", new BigDecimal (0));
                JSFUtils.setExpressionValue("#{bindings.InPrisonYears.inputValue}", new BigDecimal (0));
                        print();
            
            }
        
        
        
        //        String judgText = "";
        //        BigDecimal years = BigDecimal.ZERO;
        //        BigDecimal days = BigDecimal.ZERO;
        //        BigDecimal months = BigDecimal.ZERO;
        //
        //
        //        String stopExecution = (String) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.attributeValue}");
        //        years = (BigDecimal) valueChangeEvent.getNewValue();
        //        days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}");
        //        months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}");
        //
        //        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
        //            months.compareTo(BigDecimal.ZERO) == 1) {
        //            judgText = " ï¿½ï¿½ï¿½ ";
        //            if (years.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + years + " ï¿½ï¿½ï¿½ ";
        //            }
        //            if (months.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ " + months + " ï¿½ï¿½ï¿½ ";
        //            }
        //            if (days.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ "  + days + " ï¿½ï¿½ï¿½ ";
        //            }
        //                         if (stopExecution.equalsIgnoreCase("Y"))
        //                         judgText = judgText+" ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ";
        //                         else
        //                         judgText = judgText+" ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ";
        //
        //
        //
        //        }
        //        JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", judgText);
        //        System.out.println("Judge  >>> " + judgText);
    }


    public void dayVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null){
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.InPrisonDays.inputValue}", valueChangeEvent.getNewValue());
        print();
        }
        else{
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", new BigDecimal (0) );
                JSFUtils.setExpressionValue("#{bindings.InPrisonDays.inputValue}", new BigDecimal (0) );
                print();
            
            }

        //        String judgText = "";
        //        BigDecimal years = BigDecimal.ZERO;
        //        BigDecimal days = BigDecimal.ZERO;
        //        BigDecimal months = BigDecimal.ZERO;
        //        String stopExecution = (String) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.attributeValue}");
        //        days = (BigDecimal) valueChangeEvent.getNewValue();
        //        months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}");
        //        years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}");
        //
        //        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
        //            months.compareTo(BigDecimal.ZERO) == 1) {
        //            judgText = "ï¿½ï¿½ï¿½";
        //            if (years.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + years + "ï¿½ï¿½ï¿½";
        //            }
        //            if (months.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ "  + months + " ï¿½ï¿½ï¿½ ";
        //            }
        //            if (days.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ "  + days + "ï¿½ï¿½ï¿½ ";
        //            }
        //                         if (stopExecution.equalsIgnoreCase("N"))
        //                         judgText = judgText+"ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½";
        //                         else
        //                         judgText = judgText+"ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½";
        //
        //
        //
        //        }
        //        JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", judgText);
        //        System.out.println("Judge  >>> " + judgText);
    }

    public void monthVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue() != null){
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.InPrisonMonths.inputValue}", valueChangeEvent.getNewValue());
        print();
        }
        else
        {
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", new BigDecimal (0) );
                JSFUtils.setExpressionValue("#{bindings.InPrisonMonths.inputValue}", new BigDecimal (0) );
                print();   
            }

        //        String judgText = "";
        //        BigDecimal years = BigDecimal.ZERO;
        //        BigDecimal days = BigDecimal.ZERO;
        //        BigDecimal months = BigDecimal.ZERO;
        //        System.out.println(JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}"));
        //        Boolean stopExecution = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}");
        //        months = (BigDecimal) valueChangeEvent.getNewValue();
        //        days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}");
        //        years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}");
        //
        //        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||
        //
        //            months.compareTo(BigDecimal.ZERO) == 1) {
        //            judgText = " ï¿½ï¿½ï¿½ ";
        //            if (years.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + years + " ï¿½ï¿½ï¿½ ";
        //            }
        //            if (months.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ "  + months + " ï¿½ï¿½ï¿½ ";
        //            }
        //            if (days.compareTo(BigDecimal.ZERO) == 1) {
        //                judgText = judgText + " ï¿½ "  + days + " ï¿½ï¿½ï¿½ ";
        //            }
        //                         if (!stopExecution )
        //                         judgText = judgText+" ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ";
        //                         else
        //                         judgText = judgText+" ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ ";
        //
        //
        //
        //        }
        //        JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", judgText);
        //        System.out.println("Judge  >>> " + judgText);
    }


    /////////////////////////////////////////////////////////////////////


    public void year1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        BigDecimal years = BigDecimal.ZERO;
        years = (BigDecimal) valueChangeEvent.getNewValue();
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears1.inputValue}", years);
        printTwo();

    }


    public void day1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        BigDecimal days = BigDecimal.ZERO;

        days = (BigDecimal) valueChangeEvent.getNewValue();

        JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays1.inputValue}", days);

        printTwo();
    }

    public void month1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        BigDecimal months = BigDecimal.ZERO;

        months = (BigDecimal) valueChangeEvent.getNewValue();
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths1.inputValue}", months);
        printTwo();

    }



    

    // method generate judement text for all parties

    public void printTwo() {
        String judgText = "";
        String comments = "";
        BigDecimal years = BigDecimal.ZERO;
        BigDecimal days = BigDecimal.ZERO;
        BigDecimal months = BigDecimal.ZERO;
        BigDecimal attendance = BigDecimal.ZERO;
        BigDecimal guranteeAmount = BigDecimal.ZERO;
        BigDecimal workAmount = BigDecimal.ZERO;
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        BigDecimal PenaltyFlag = BigDecimal.ONE;
        Boolean executionFlag = false ;
        Boolean foreverFlag = false ;
        Boolean hardLabourFlag = false ;
        
        if (JSFUtils.resolveExpression("#{bindings.ExecutionFlag3.inputValue}") != null)
            executionFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ExecutionFlag3.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ForeverFlag1.inputValue}") != null)
            foreverFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ForeverFlag1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.HardLabourFlag1.inputValue}") != null)
            hardLabourFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.HardLabourFlag1.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}") != null)
            PenaltyFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}");
        else if (JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.WorkAmount1.inputValue}") != null)
            workAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.WorkAmount1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.PenaltyAmount1.inputValue}") != null)
            penaltyAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PenaltyAmount1.inputValue}");
        comments = (String) JSFUtils.resolveExpression("#{bindings.Comments1.inputValue}");
        System.err.println(attendance + "              PenaltyFlag         " + PenaltyFlag);
        //            BigDecimal attendance = new BigDecimal (attend.toString());bindings.Comments.inputValue
            if (PenaltyFlag.compareTo(new BigDecimal(2)) == 0) {
                judgText = " \u0627\u0645\u0631\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629 ";

            } else if (PenaltyFlag.compareTo(new BigDecimal(1)) == 0) {
                judgText = " \u062d\u0643\u0645\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629";
            } else if (PenaltyFlag.compareTo(new BigDecimal(3)) == 0) {
                judgText = "\u0627\u0645\u0631\u062a \u0627\u0644\u0646\u064a\u0627\u0628\u0629 ";
            }
            if (attendance.compareTo(new BigDecimal(1)) == 0) {
                judgText = judgText + "  \u062d  ";
            } else if (attendance.compareTo(new BigDecimal(2)) == 0) {
                judgText = judgText + "  \u062d \u002d \u0623 ";
            } else if (attendance.compareTo(new BigDecimal(3)) == 0) {
                judgText = judgText + " \u063a ";
            }
        else if (attendance.compareTo(new BigDecimal(4)) == 0) {
                   judgText = judgText + " \u062d \u002d\u062a  ";
               }
        else if (attendance.compareTo(new BigDecimal(5)) == 0) {
                System.out.println("5555");
                
           judgText = judgText + " \u063a\u002d\u0642  ";
        }


        Boolean stopExecutionPrison = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag1.inputValue}");
        
        Boolean stopExecution = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag2.inputValue}");
        Boolean penFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.PenaltyFlag1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths1.inputValue}") != null)
            months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentDays1.inputValue}") != null)
            days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays1.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears1.inputValue}") != null)
            years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears1.inputValue}");

        if (foreverFlag)
            judgText = judgText + " \u0628\u0627\u0644\u0645\u0624\u0628\u062f ";

        
        if (executionFlag)
        judgText = judgText + " \u0628\u0627\u0644\u0627\u0639\u062f\u0627\u0645 ";
        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||

            months.compareTo(BigDecimal.ZERO) == 1) {
            judgText = judgText + " \u062d\u0628\u0633 ";
            if (years.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + years + " \u0633\u0646\u0629 ";
            }
            if (months.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + months + " \u0634\u0647\u0631 ";
            }
            if (days.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + days + " \u064a\u0648\u0645 ";
            }


        }
        
        
        
        if (hardLabourFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0627\u0634\u063a\u0627\u0644 \u0627\u0644\u0634\u0627\u0642\u0647 ";

        System.err.println(stopExecution + "@@@@@@@@@@  " + penFlag);
        if (!stopExecution && penFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0646\u0641\u0627\u0630 ";
        else if (stopExecution && !penFlag) {
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u0627\u0644\u062a\u0646\u0641\u064a\u0630 ";
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));
        } else if (stopExecution && penFlag)
            judgText = judgText + " ";
        
        if (stopExecutionPrison)
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u062a\u0646\u0641\u064a\u0630 \u0627\u0644\u062d\u0628\u0633 "; 

        if (JSFUtils.resolveExpression("#{bindings.GuranteeAmount1.inputValue}") != null)
            guranteeAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.GuranteeAmount1.inputValue}");

            if (((BigDecimal) guranteeAmount).intValue() > 0) {
                judgText = judgText + " \u0648 \u0643\u0641\u0627\u0644\u0629 " + guranteeAmount + " \u062c\u002d\u0645 ";
            }

            if (((BigDecimal) workAmount).intValue() > 0) {
                judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u064a\u0648\u0645\u064a\u0629  " + workAmount + " \u062c\u002d\u0645 ";
            }
            if (((BigDecimal) penaltyAmount).intValue() > 0) {
                judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u062b\u0627\u0628\u062a\u0629  " + penaltyAmount + " \u062c\u002d\u0645 ";
            }

        System.err.println("MAGGGGGGGGGGG" + comments);
        if (comments != null) {
            //                    judgText = judgText+" ï¿½  " +comments ;   bindings.Judgement.inputValue
            judgText = judgText + "  " + comments;
        }
        JSFUtils.addToPageFlowScope("judgText", judgText);
        JSFUtils.addToSession("judgeText", judgText);
        JSFUtils.setExpressionValue("#{pageFlowScope.judgText}", judgText);
        System.out.println("Judge  >>> " + judgText);
        
        
        System.out.println(stopExecutionPrison +"Judge  >>> " + judgText+"-"+workAmount+"-"+ "-"+penaltyAmount);
        
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (1));
        
        if (stopExecutionPrison && (((BigDecimal) workAmount).intValue() <= 0 &&  ((BigDecimal) penaltyAmount).intValue() <= 0))

        {
            
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
            
            
            }
        if (stopExecution)
        {
            
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
            
            
            }
        
        
        JSFUtils.setExpressionValue("#{bindings.Judgement.inputValue}", judgText);
        JSFUtils.refreshComponent("ot9");
       
        System.err.println(years);
    }


    // method to generate judgement text for each one party  
    public void print() {
        try{
        RichSelectOneChoice soc63 = (RichSelectOneChoice) JSFUtils.findComponentInRoot("soc63");
        soc63.setRequired(true);
        JSFUtils.refreshComponent("soc63");
        }
         catch(Exception e)
        {
        System.err.println("Error  " + e.getMessage());
        }
        String judgText = "";
        String judg = "";
        String comments = "";
        BigDecimal years = BigDecimal.ZERO;
        BigDecimal days = BigDecimal.ZERO;
        BigDecimal months = BigDecimal.ZERO;
        BigDecimal attendance = BigDecimal.ZERO;
        BigDecimal guranteeAmount = BigDecimal.ZERO;
        BigDecimal workAmount = BigDecimal.ZERO;
        BigDecimal penaltyAmount = BigDecimal.ZERO;
        BigDecimal undefinedPenalty = BigDecimal.ZERO;
        BigDecimal PenaltyFlag = BigDecimal.ONE;
        Boolean executionFlag = false ;
        Boolean foreverFlag = false ;
        Boolean hardLabourFlag = false ;
        Boolean stopExecutionPrison = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");
        System.out.println("JSFUtils.resolveExpression(\"#{bindings.ExecutionFlag.inputValue}\")"+JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}"));
        
        if (JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}") != null)
            executionFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ExecutionFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}") != null)
            foreverFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.ForeverFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.HardLabourFlag.inputValue}") != null)
            hardLabourFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.HardLabourFlag.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}") != null)
            judg = (String) JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}") != null)
            PenaltyFlag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.JudgmentType.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}");
        else if (JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}") != null)
            attendance = (BigDecimal) JSFUtils.resolveExpression("#{bindings.AttendanceAll.inputValue}");
        
        if (JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}") != null)
            workAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.WorkAmount.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}") != null)
            penaltyAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PenaltyAmount.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}") != null)
            undefinedPenalty = (BigDecimal) JSFUtils.resolveExpression("#{bindings.UnDefineedPenalty.inputValue}");
        comments = (String) JSFUtils.resolveExpression("#{bindings.Comments.inputValue}");
        System.err.println(attendance + "              PenaltyFlag         " + PenaltyFlag);
        //            BigDecimal attendance = new BigDecimal (attend.toString());bindings.Comments.inputValue
        if (PenaltyFlag.compareTo(new BigDecimal(2)) == 0) {
            judgText = " \u0627\u0645\u0631\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629 ";

        } else if (PenaltyFlag.compareTo(new BigDecimal(1)) == 0) {
            judgText = " \u062d\u0643\u0645\u062a \u0627\u0644\u0645\u062d\u0643\u0645\u0629";
        } else if (PenaltyFlag.compareTo(new BigDecimal(3)) == 0) {
            judgText = "\u0627\u0645\u0631\u062a \u0627\u0644\u0646\u064a\u0627\u0628\u0629 ";
        }
        
        System.out.println("attendance   *** "+attendance);
        
        if (attendance.compareTo(new BigDecimal(1)) == 0) {
            judgText = judgText + "  \u062d  ";
        } else if (attendance.compareTo(new BigDecimal(2)) == 0) {
            judgText = judgText + "  \u062d \u002d \u0623 ";
        } else if (attendance.compareTo(new BigDecimal(3)) == 0) {
            judgText = judgText + " \u063a ";
        }
        else if (attendance.compareTo(new BigDecimal(4)) == 0) {
                   judgText = judgText + " \u062d \u002d\u062a  ";
               }
         else if (attendance.compareTo(new BigDecimal(5)) == 0) {
            System.out.println("5555");
            judgText = judgText + " \u063a\u002d\u0642  ";
        }
        
        
        
        if (judg != null)
        {
            judgText = judgText +     judg + " ";
            }
        
        System.out.println("GFFGFFGFG   - "+ JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}"));

        
      
        Boolean stopExecution = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}");
        Boolean penFlag = (Boolean) JSFUtils.resolveExpression("#{bindings.PenaltyFlag.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}") != null)
            months = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentMonths.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}") != null)
            days = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentDays.inputValue}");
        if (JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}") != null)
            years = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ImprisonmentYears.inputValue}");

        if (foreverFlag)
            judgText = judgText + " \u0628\u0627\u0644\u0645\u0624\u0628\u062f ";

        
if (executionFlag)
    judgText = judgText + " \u0628\u0627\u0644\u0627\u0639\u062f\u0627\u0645 ";
        if (years.compareTo(BigDecimal.ZERO) == 1 || days.compareTo(BigDecimal.ZERO) == 1 ||

            months.compareTo(BigDecimal.ZERO) == 1) {
            if( JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null){
    int SchemaId  = Integer.parseInt(JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}")+"");
    if (SchemaId== 3647  || SchemaId== 3646  || SchemaId== 3648){
        if(years.compareTo(new BigDecimal(5)) == 1)
        judgText = judgText + " \u0633\u062c\u0646 ";  
        else
             judgText = judgText + " \u062d\u0628\u0633 ";
    }
            }
            else{
                BigDecimal foldType = new BigDecimal (1);
                if(JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}") != null)
                    foldType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
                
                if ((foldType.compareTo(new BigDecimal(13)) == 0 || foldType.compareTo(new BigDecimal(15)) == 0 ||
                    foldType.compareTo(new BigDecimal(14)) == 0 || foldType.compareTo(new BigDecimal(18)) == 0 ||
                    foldType.compareTo(new BigDecimal(16)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0
                    || foldType.compareTo(new BigDecimal(19)) == 0
                ) ){
                    if (foldType.compareTo(new BigDecimal(13))  == 0)
                    judgText = judgText + " \u062a\u0648\u0628\u064a\u062e ";
                    
                    if (foldType.compareTo(new BigDecimal(14))  == 0)
                    judgText = judgText + " \u062a\u0633\u0644\u064a\u0645 \u0627\u0644\u062d\u062f\u062b ";
                    
                    if (foldType.compareTo(new BigDecimal(15))  == 0)
                    judgText = judgText + " \u0627\u0644\u062a\u062d\u0627\u0642 \u0628\u062a\u062f\u0631\u064a\u0628 \u0645\u0647\u0646\u0649 ";
                    
                    if (foldType.compareTo(new BigDecimal(16))  == 0)
                    judgText = judgText + " \u0627\u0644\u0632\u0627\u0645 \u0628\u0648\u0627\u062c\u0628\u0627\u062a \u0645\u0639\u064a\u0646\u0629 ";
                    
                    if (foldType.compareTo(new BigDecimal(17))  == 0)
                    judgText = judgText + " \u0627\u0644\u0627\u062e\u062a\u0628\u0627\u0631 \u0627\u0644\u0642\u0636\u0627\u0626\u0649 ";
                    
                    if (foldType.compareTo(new BigDecimal(18))  == 0)
                    judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0624\u0633\u0633\u0627\u062a \u0627\u0644\u0631\u0639\u0627\u064a\u0629 \u0627\u0644\u0627\u062c\u062a\u0645\u0627\u0639\u064a\u0629 ";
                    
                    if (foldType.compareTo(new BigDecimal(19))  == 0)
                    judgText = judgText + " \u0627\u064a\u062f\u0627\u0639 \u0641\u0649 \u0627\u062d\u062f \u0645\u0633\u062a\u0634\u0641\u064a\u0627\u062a \u0627\u0644\u0645\u062a\u062e\u0635\u0635\u0629 ";
                    
                    if ((foldType.compareTo(new BigDecimal(15)) == 0 || foldType.compareTo(new BigDecimal(17)) == 0) )
                    judgText = judgText + " \u0639\u062f\u062f ";
                }
                else
                judgText = judgText + " \u062d\u0628\u0633 ";
                
            }
            if (years.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + years + " \u0633\u0646\u0629 ";
            }
            if (months.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + months + " \u0634\u0647\u0631 ";
            }
            if (days.compareTo(BigDecimal.ZERO) == 1) {
                judgText = judgText + " \u0648 " + days + " \u064a\u0648\u0645 ";
            }


        }
        
        if (hardLabourFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0627\u0634\u063a\u0627\u0644 \u0627\u0644\u0634\u0627\u0642\u0647 ";

        if (JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}") != null)
            guranteeAmount = (BigDecimal) JSFUtils.resolveExpression("#{bindings.GuranteeAmount.inputValue}");
        if (((BigDecimal) guranteeAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u0643\u0641\u0627\u0644\u0629 " + guranteeAmount + " \u062c\u002d\u0645 ";
        }

        if (((BigDecimal) workAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u064a\u0648\u0645\u064a\u0629  " + workAmount + " \u062c\u002d\u0645 ";
        }
        if (((BigDecimal) penaltyAmount).intValue() > 0) {
            judgText = judgText + " \u0648 \u063a\u0631\u0627\u0645\u0629 \u062b\u0627\u0628\u062a\u0629  " + penaltyAmount + " \u062c\u002d\u0645 ";
        }
        if (((BigDecimal) undefinedPenalty).intValue() > 0) {
            judgText = judgText + " \u0648\u063a\u0631\u0627\u0645\u0629 " + undefinedPenalty + " % ";
        }
        
        System.err.println(stopExecution + "@@@@@@@@@@  " + penFlag);
        if (!stopExecution && penFlag)
            judgText = judgText + " \u0645\u0639 \u0627\u0644\u0646\u0641\u0627\u0630 ";
        else if (stopExecution && !penFlag) {
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u0627\u0644\u062a\u0646\u0641\u064a\u0630 ";
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal(0));
        } else if (stopExecution && penFlag)
            judgText = judgText + " ";


        if (stopExecutionPrison)
            judgText = judgText + " \u0645\u0639 \u0627\u064a\u0642\u0627\u0641 \u062a\u0646\u0641\u064a\u0630 \u0627\u0644\u062d\u0628\u0633 ";

        System.err.println("MAGGGGGGGGGGG" + comments);
        if (comments != null) {
            //                    judgText = judgText+" ï¿½  " +comments ;
            judgText = judgText + "  " + comments;
        }

        JSFUtils.setExpressionValue("#{bindings.JudgmentText.inputValue}", judgText);
        System.out.println(stopExecutionPrison +"Judge  >>> " + judgText+"-"+workAmount+"-"+undefinedPenalty+"-"+penaltyAmount);
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (1));
        if (stopExecutionPrison && (((BigDecimal) workAmount).intValue() <= 0 && ((BigDecimal) undefinedPenalty).intValue() <= 0 && ((BigDecimal) penaltyAmount).intValue() <= 0))

        {
            
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
            
            
            }
        if (stopExecution)
        {
            
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
            
            
            }
        

        System.err.println(years);
        
        JSFUtils.refreshComponent("ot8999");
    }
    
    
    
    



    public void penaltyFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...JudgmentType
        BigDecimal  flag = new BigDecimal (1);
        if (valueChangeEvent.getNewValue() != null){
            flag = (BigDecimal) valueChangeEvent.getNewValue();
        System.out.println("Flag  ." + valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.JudgmentType.inputValue}", flag);
        if (flag.compareTo(new BigDecimal(1)) == 0) {
                try{
            JSFUtils.addToSession("prosequtionFlag", new BigDecimal(1));
            JSFUtils.addToPageFlowScope("CJP", "false");
                BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
                    if (bcType.compareTo(new BigDecimal(19))!=0){
            JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(2)));
            JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", (new BigDecimal(2)));
            JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", (new BigDecimal(2)));
                JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", new BigDecimal(2));
                    }
            
            JSFUtils.setExpressionValue("#{bindings.ProsecutionJudegementFlag.inputValue}", new BigDecimal(1));
            JSFUtils.setExpressionValue("#{bindings.IsActive.inputValue}", "Y");
            } catch(Exception e){
                    e.printStackTrace();}
        } else if (flag.compareTo(new BigDecimal(2)) == 0) {
                try{
                JSFUtils.addToSession("prosequtionFlag", new BigDecimal(2));
                JSFUtils.addToPageFlowScope("CJP", "false");
                BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
                    if (bcType.compareTo(new BigDecimal(1))==1){
             JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", new BigDecimal(3));
              JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(3)));
           JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", (new BigDecimal(3)));
        JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", (new BigDecimal(3)));
                }
            JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1)); 
            JSFUtils.setExpressionValue("#{bindings.ProsecutionJudegementFlag.inputValue}", new BigDecimal(1));
            JSFUtils.setExpressionValue("#{bindings.IsActive.inputValue}", "Y");
            } catch(Exception e){ e.printStackTrace();}
        } else if (flag.compareTo(new BigDecimal(3)) == 0) {
                  JSFUtils.addToPageFlowScope("CJP", "true");
                  try{
                  JSFUtils.addToSession("prosequtionFlag", new BigDecimal(3));
                      BigDecimal    bcType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}");
                          if (bcType.compareTo(new BigDecimal(1))==1){
                      JSFUtils.setExpressionValue("#{bindings.FolderType2.inputValue}", new BigDecimal(3));
                 JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", (new BigDecimal(3)));
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", (new BigDecimal(3)));
           JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", (new BigDecimal(3)));
                          }
                  JSFUtils.setExpressionValue("#{bindings.BusinessCaseDegree.inputValue}", new BigDecimal(1)); 
            JSFUtils.setExpressionValue("#{bindings.Source1.inputValue}", "simplifiedModeCJ");
            JSFUtils.setExpressionValue("#{bindings.ProsecutionJudegementFlag.inputValue}", new BigDecimal(2));
            JSFUtils.setExpressionValue("#{bindings.IsActive.inputValue}", "Y");
                  } catch(Exception e){ e.printStackTrace();}
                  JSFUtils.addToPageFlowScope("degFlag", false);
              }
        //        print ();

        Boolean flag2 = (Boolean) JSFUtils.resolveExpression("#{bindings.Flag.inputValue}");


        if (!flag2)
            print();
        else
            printTwo();

        }
    }
    
    public void sequenceFolder()
    {
        boolean check =true;
            HashMap<BigDecimal,BigDecimal> map  = new HashMap<BigDecimal,BigDecimal>(); ;
        if (ADFContext.getCurrent().getSessionScope().get("mapSeq") != null)
        {
              map  =
                    (HashMap<BigDecimal, BigDecimal>) ADFContext.getCurrent().getSessionScope().get("mapSeq");
            }
       
      
                 
                  BigDecimal id = (BigDecimal)  JSFUtils.resolveExpression("#{bindings.Id14.inputValue}");
             System.out.println("Iterating Hashmap...");  
             if (map.size()>0)
             for(Map.Entry m : map.entrySet()){    
                 BigDecimal key = (BigDecimal)  m.getKey();
                 if (key.compareTo(id)==0)
                 {
                         check =false;
                         JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}",  m.getValue());
                         JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}",  m.getValue());
                     break;
                     }
              System.out.println(m.getKey()+" "+m.getValue());    
             }  
        
        if( check )
        {
            OperationBinding binding = ADFUtil.findOperation("getSequenceValue");
            binding.getParamsMap().put("SequenceName", "EXC_FOLDER_DATA2_SEQ");
            BigDecimal seq = (BigDecimal) binding.execute();
            JSFUtils.setExpressionValue("#{bindings.SequenceNumber.inputValue}", seq);
            JSFUtils.setExpressionValue("#{bindings.SeqFolder.inputValue}", seq);
            
            map.put(id,seq); 


            ADFContext.getCurrent().getSessionScope().put("mapSeq",map);
        }
    }

    public void attendanceVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            try{
        Boolean flag = (Boolean) JSFUtils.resolveExpression("#{bindings.Flag.inputValue}");
        Boolean manualSeq = (Boolean) JSFUtils.resolveExpression("#{bindings.ManualSeq.inputValue}");
        BigDecimal att = new BigDecimal(5);
                if(valueChangeEvent.getNewValue() != null)
           att = (BigDecimal) valueChangeEvent.getNewValue();
        JSFUtils.setExpressionValue("#{bindings.Attendance.inputValue}", att);
            JSFUtils.setExpressionValue("#{bindings.PartyAttendance.inputValue}", att);
        System.out.println(flag+"valueChangeEvent "+att+"  ------"+valueChangeEvent.getNewValue()+"/////////"+JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}"));
        if (!flag)
            print();
        else
            printTwo();
        
       
//       BigDecimal att = (BigDecimal)valueChangeEvent.getNewValue();
        if (att.compareTo(new BigDecimal(1))==0 && manualSeq)
        {
                BigDecimal bType =
                    new BigDecimal(JSFUtils.resolveExpression("#{bindings.BusinessCaseType1.inputValue}") + "");     
                if (bType.compareTo(new BigDecimal(4)) != 0){
            System.out.println("EEDEDDEDDEEDEDED");
//                sequenceFolder(); 
                JSFUtils.setExpressionValue("#{bindings.FolderType.inputValue}", new BigDecimal (1));
                JSFUtils.setExpressionValue("#{bindings.FolderTypeCrime.inputValue}", new BigDecimal (1));
                JSFUtils.setExpressionValue("#{bindings.FolderTypeAll.inputValue}", new BigDecimal (1));
                JSFUtils.setExpressionValue("#{bindings.FolderAttend.inputValue}", new BigDecimal (1));
                } 
            }
        
        changeExecutionFlg();
        entityVCL() ;
        }
        catch (Exception ex){}
    }
    
    
    
    public void entityVCL()
    {
            BigDecimal typeFlag = new BigDecimal (0);
               JSFUtils.addToSession("NeyabaaGozayaa",
                                     "" + JSFUtils.resolveExpression("#{bindings.NeyabGozayaa.inputValue}"));
            System.err.println("###  "+ JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}"));
            if ( JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}") != null)
                typeFlag =(BigDecimal) JSFUtils.resolveExpression("#{bindings.SchemaId.inputValue}");
//               if (typeFlag.compareTo(new BigDecimal (3643))== 0)
//               {
//                   if (JSFUtils.resolveExpression("#{bindings.CriminalType.inputValue}") != null)
//                       JSFUtils.setExpressionValue("#{bindings.CriminalType.inputValue}", new BigDecimal(1));
//                       if ( JSFUtils.resolveExpression("#{bindings.PoliceStationEntityId.inputValue}") != null)
//                       JSFUtils.setExpressionValue("#{bindings.PoliceStationEntityId.inputValue}", JSFUtils.resolveExpression("#{bindings.NeyabKolyaa.inputValue}"));
//                   }
        
        }

    
    public void changeExecutionFlg(){
        System.out.println("ATT    ----"+JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}"));
            
                BigDecimal bcDeg = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BusinessCaseDegree.inputValue}");
                if (bcDeg ==null)
                    bcDeg = (BigDecimal) JSFUtils.resolveExpression("#{bindings.BcDegreeGonaha.inputValue}");
            BigDecimal attendance = new BigDecimal(5);
            if (bcDeg ==null)
                bcDeg = new BigDecimal(1);
                
                
                if(JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}") != null)
                    attendance =  (BigDecimal) JSFUtils.resolveExpression("#{bindings.Attendance.inputValue}");
            BigDecimal folderType = (BigDecimal) JSFUtils.resolveExpression("#{bindings.FolderType.inputValue}");
               Boolean stopExec = (Boolean)  JSFUtils.resolveExpression("#{bindings.StopExecutionFlag1.inputValue}");
            Boolean stopExecPris = (Boolean)  JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");
            Boolean stopExecPrison = (Boolean) JSFUtils.resolveExpression("#{bindings.StopExecutionPrisonFlag.inputValue}");
     System.out.println("bcDeg:"+bcDeg+" folderType:"+folderType+" attendance:"+attendance+" stopExec:"+stopExec+" stopExecPrison:"+stopExecPrison);
          
            if(bcDeg.compareTo(new BigDecimal(1))==0 && attendance.compareTo(new BigDecimal(1))==0 &&stopExec && (folderType.compareTo(new BigDecimal(1))==0 ||   folderType.compareTo(new BigDecimal(2))==0))
             {
              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +2);
                  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));   
              
              }
          else if(bcDeg.compareTo(new BigDecimal(2))==0 &&stopExec)
          {
              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +2);
                  JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));   
              
              }
            
            
            
          else{
              System.err.println("bcDeg : "+bcDeg+" Execution  Flag  ---  " +1);
            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (1));   
          }
//            
//            if (stopExecPris)
//            {
//                    JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));   
//                }
            
            
            
            
            
            
            if (stopExec)
            {
                
                    JSFUtils.setExpressionValue("#{bindings.ExecutionFlag1.inputValue}", new BigDecimal (2));
                
                
                }
            
            
            
         
        
        
        }

    public void executionFlagVCL(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.err.println("valueChangeEvent.getNewValue() ;  " + valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag1.inputValue}", valueChangeEvent.getNewValue());

        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        //       System.err.println("MEGAAAAAAAAAAAAA     "+flag);
        if (flag){
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag.inputValue}", new BigDecimal(0));
            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag2.inputValue}", new BigDecimal(2));
            
        }
        else
        {
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag2.inputValue}", new BigDecimal(1));
                
            }
        //
        print();
        changeExecutionFlg();
    }


    /////


    public void penaltyTypeFlagVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        System.err.println("MEGAAAAAAAAAAAAA     " + flag);

        System.err.println("MEGAAAAAAAAAAAAA     " + flag);
        if (flag) {

            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag1.inputValue}", "N");

        }
        JSFUtils.setExpressionValue("#{bindings.PenaltyFlag.inputValue}", valueChangeEvent.getNewValue());
        print();
    }


    //////


    public void guranteeAmountVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.Guarantee.inputValue}", valueChangeEvent.getNewValue());
        print();
    }

    public void workAmountVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.DailyPayments.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.storeOnSession("workAmountPenaltyComb", valueChangeEvent.getNewValue());
         
        print();
    }

    public void penaltyAmountVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.Amount.inputValue}", valueChangeEvent.getNewValue());
        ADFUtil.findIterator("CourtBcHearingResPartiesVO1Iterator").getCurrentRow().setAttribute("Amount", valueChangeEvent.getNewValue());
        System.out.println(JSFUtils.resolveExpression("#{bindings.Amount.inputValue}")+"WWWWWWWWWWW   --"+valueChangeEvent.getNewValue());
        JSFUtils.storeOnSession("PenaltyComb",valueChangeEvent.getNewValue());
        
        print();
    }

    public void commentsVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.Comments.inputValue}", valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.Tmp.inputValue}", valueChangeEvent.getNewValue());
        print();
    }

    public void comments1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...

        JSFUtils.setExpressionValue("#{bindings.Comments1.inputValue}", valueChangeEvent.getNewValue());

        printTwo();
    }

    public void executionFlag1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.err.println("valueChangeEvent.getNewValue() ;  " + valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag2.inputValue}", valueChangeEvent.getNewValue());

        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        //       System.err.println("MEGAAAAAAAAAAAAA     "+flag);
        if (flag)
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag1.inputValue}", new BigDecimal(0));
        //
        printTwo();
    }

    public void penaltyFlag1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        System.err.println("MEGAAAAAAAAAAAAA     " + flag);

        System.err.println("MEGAAAAAAAAAAAAA     " + flag);
        if (flag) {

            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag2.inputValue}", "N");

        }
        JSFUtils.setExpressionValue("#{bindings.PenaltyFlag1.inputValue}", valueChangeEvent.getNewValue());
        printTwo();
    }

    public void workAmount1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.WorkAmount1.inputValue}", valueChangeEvent.getNewValue());
        printTwo();
    }

    public void penaltyAmoint1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.PenaltyAmount1.inputValue}", valueChangeEvent.getNewValue());
        printTwo();
    }
    
    
    public void unKnownpenaltyAmoint1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.UnDefineedPenalty1.inputValue}", valueChangeEvent.getNewValue());
        printTwo();
    }

    public void guranteeAmount1VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        JSFUtils.setExpressionValue("#{bindings.GuranteeAmount1.inputValue}", valueChangeEvent.getNewValue());
        printTwo();
    }
    
    public void dailyPenalityCBvcl1(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean checkFlag = (Boolean) valueChangeEvent.getNewValue();
        if (!checkFlag){
        JSFUtils.setExpressionValue("#{bindings.WorkAmount1.inputValue}", new BigDecimal(0));
        JSFUtils.setExpressionValue("#{bindings.PenaltyDueDate.inputValue}", "");
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        printTwo();}
    }

    public void penaltyAmountCBvcl1(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        Boolean checkFlag = (Boolean) valueChangeEvent.getNewValue();
        if (!checkFlag){
        JSFUtils.setExpressionValue("#{bindings.PenaltyAmount1.inputValue}", new BigDecimal(0));
         
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        printTwo();}
    }
    
    

    public void dailyPenalityCBvcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        Boolean checkFlag = (Boolean) valueChangeEvent.getNewValue();
        if (!checkFlag){
        JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}", new BigDecimal(0));
        JSFUtils.setExpressionValue("#{bindings.PenaltyDueDate1.inputValue}", "");
        if (JSFUtils.resolveExpressionAsBoolean("#{bindings.WorkAmountComFlag.inputValue}")!= null)
            JSFUtils.setExpressionValue("#{bindings.WorkAmountComFlag.inputValue}", "N");
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        print();}
    }

    public void penaltyAmountCBvcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        Boolean checkFlag = (Boolean) valueChangeEvent.getNewValue();
        if (!checkFlag){
        JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}", new BigDecimal(0));
         
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());

            if (JSFUtils.resolveExpressionAsBoolean("#{bindings.PenaltyAmountComFlag.inputValue}")!= null)
            JSFUtils.setExpressionValue("#{bindings.PenaltyAmountComFlag.inputValue}", "N");
        print();}
    }

    public void unknownPenaltyCBvcl(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        System.out.println("dailyPenalityCBvcl  " + valueChangeEvent.getNewValue());
        Boolean checkFlag = (Boolean) valueChangeEvent.getNewValue();
        if (!checkFlag){
        JSFUtils.setExpressionValue("#{bindings.UnDefineedPenalty.inputValue}", new BigDecimal(0));

            if (JSFUtils.resolveExpressionAsBoolean("#{bindings.UndefineedPenaltyComFlag.inputValue}")!= null)
            JSFUtils.setExpressionValue("#{bindings.UndefineedPenaltyComFlag.inputValue}", "N");
        
        print();}
    }

    public void unDefinedPenVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
//        System.err.println("value = "+valueChangeEvent.getNewValue());
        JSFUtils.setExpressionValue("#{bindings.UnDefineedPenalty.inputValue}", valueChangeEvent.getNewValue());
//       JSFUtils.storeOnSession("undefinedPenaltyComb", valueChangeEvent.getNewValue());
        print();
    }

    public void executionVCL(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        flag = (Boolean) valueChangeEvent.getNewValue();
        if (flag){
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag1.inputValue}", "false");
            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag.inputValue}",flag);
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag.inputValue}", "false");
       
        }
        else 
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag.inputValue}", flag);
        // Add event code here...
        
        print();
    }
    
    public void foreverVCL(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        flag = (Boolean) valueChangeEvent.getNewValue();
        
        if (flag){
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag1.inputValue}", "false");
           
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag.inputValue}", "false");
            JSFUtils.setExpressionValue("#{bindings.ExecutionType.inputValue}", new BigDecimal (1));
        System.out.println("KLKLKLKLKLKLKLKLKLKLK**********");
        }
        else
            JSFUtils.setExpressionValue("#{bindings.ExecutionType.inputValue}", new BigDecimal (0));
       
        // Add event code here...
        
        print();
    }
    
    
    public void hardLabourVCL(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        flag = (Boolean) valueChangeEvent.getNewValue();
        
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
           
        // Add event code here...
        
        print();
    }
    
    
    public void executionVCL1(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        flag = (Boolean) valueChangeEvent.getNewValue();
        if (flag){
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag2.inputValue}", "false");
            JSFUtils.setExpressionValue("#{bindings.ExecutionFlag3.inputValue}",flag);
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag1.inputValue}", "false");
       
        }
        else 
        JSFUtils.setExpressionValue("#{bindings.ExecutionFlag3.inputValue}", flag);
        // Add event code here...
        
        
        printTwo();
    }
    
    public void foreverVCL1(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        flag = (Boolean) valueChangeEvent.getNewValue();
        
        if (flag){
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.GuranteeAmount1.inputValue}", new BigDecimal (0));
            JSFUtils.setExpressionValue("#{bindings.StopExecutionFlag2.inputValue}", "false");
           
            JSFUtils.setExpressionValue("#{bindings.PenaltyFlag1.inputValue}", "false");
        
        }
       
        // Add event code here...
        
        printTwo();
    }
    
    
    public void hardLabourVCL1(ValueChangeEvent valueChangeEvent) {
        Boolean flag  = false ;
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        flag = (Boolean) valueChangeEvent.getNewValue();
        
        System.err.println("valueChangeEvent  "+valueChangeEvent.getNewValue());
           
        // Add event code here...
        
        printTwo();
    }
    
    
    public void callJsFunction(String funName) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, funName + "();");
    }
    public void objectionVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.out.println("New Value "+valueChangeEvent.getNewValue());
        BigDecimal flag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ObjectionFlag.inputValue}");
        if  (flag.compareTo(new BigDecimal (1))==0)
        {
            System.err.println(" -- PrisonYear is : "+ JSFUtils.resolveExpression("#{bindings.PrisonYear.inputValue}"));
    JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}",
                                JSFUtils.resolveExpression("#{bindings.PrisonYear.inputValue}")); 
    
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.PrisonMonths.inputValue}")); 
                
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.PrisonDays.inputValue}")); 
                JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.GuaranteeGuarantee.inputValue}")); 
                JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.DailyPaymentsDailyPayments.inputValue}")); 
                JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}",
                                            JSFUtils.resolveExpression("#{bindings.AmountAmount.inputValue}")); 
                if (JSFUtils.resolveExpression("#{bindings.AmountAmount.inputValue}") != null){
                    System.err.println("++++++ "+Integer.parseInt(JSFUtils.resolveExpression("#{bindings.AmountAmount.inputValue}")+""));
                    JSFUtils.setExpressionValue("#{bindings.PenaltyAmountFixedFlag1.inputValue}",true);             
                }

                print();
            
            }
    else    {
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}",
                                new BigDecimal (0)); 
        
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}",
                                            new BigDecimal (0)); 
                
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}",
                                            new BigDecimal (0)); 
                print();
            
            }
        
        System.out.println("New Value -------- "+JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}"));
        System.out.println("New Value -----****--- "+JSFUtils.resolveExpression("#{bindings.ObjectionFlag.inputValue}"));
        
    }

    @Override
    public void populateParamsMap(HashMap parametersMap) {
        // TODO Implement this method...
        BigDecimal id = (BigDecimal) JSFUtils.resolveExpression("#{item.bindings.Id.inputValue}");
        
        System.err.println("" +
            "GG  "+ id);
        Integer userId = null;
        if(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}") != null)
          {
            userId = Integer.parseInt(ADFUtil.evaluateEL("#{sessionScope.userInfo.userId}").toString());
          }
        
        int folderId = Integer.parseInt(id.toString());
        
        parametersMap.put("userId", userId);
        parametersMap.put("FOLDER_ID", folderId);
    }

    public void objectionGnayaatVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        
        
        
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        System.out.println("New Value "+valueChangeEvent.getNewValue());
        BigDecimal flag = (BigDecimal) JSFUtils.resolveExpression("#{bindings.ObjectionFlag.inputValue}");
        if  (flag.compareTo(new BigDecimal (1))==0)
        {
            BigDecimal partyId = (BigDecimal) JSFUtils.resolveExpression("#{bindings.PartyId1.inputValue}");
           
            OperationBinding myOperation = ADFUtil.findOperation("getExPenalty");
                myOperation.getParamsMap().put("partyId",partyId);
               String[] data = (String[]) myOperation.execute(); 
                System.err.println(data [7]+" -- PrisonDays is : "+data [8]+"---"+data [9] );
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}",
                                new BigDecimal (data [2])); 
        
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}",
                                           new BigDecimal ( data [1])); 
                
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}",
                                            new BigDecimal (data [0])); 
                JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}",
                                             new BigDecimal (data [3])); 
                JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}",
                                            new BigDecimal (data [5])); 
                JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}",
                                           new BigDecimal (data [4])); 
                
                JSFUtils.setExpressionValue("#{bindings.HardLabourFlag.inputValue}",
                                            data [7].equalsIgnoreCase("Y") ?  true:false); 
                JSFUtils.setExpressionValue("#{bindings.ForeverFlag.inputValue}",
                                            data [8].equalsIgnoreCase("Y") ?  true:false); 
                JSFUtils.setExpressionValue("#{bindings.ExecutionFlag.inputValue}",
                                           data [9].equalsIgnoreCase("Y") ?  true:false); 
                print();
            
            }
        else    {
        JSFUtils.setExpressionValue("#{bindings.ImprisonmentYears.inputValue}",
                                new BigDecimal (0)); 
        
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentMonths.inputValue}",
                                            new BigDecimal (0)); 
                
                JSFUtils.setExpressionValue("#{bindings.ImprisonmentDays.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.GuranteeAmount.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.WorkAmount.inputValue}",
                                            new BigDecimal (0)); 
                JSFUtils.setExpressionValue("#{bindings.PenaltyAmount.inputValue}",
                                            new BigDecimal (0)); 
                print();
            
            }
        
        System.out.println("New Value -------- "+JSFUtils.resolveExpression("#{bindings.ObjectionText.inputValue}"));
        System.out.println("New Value -----****--- "+JSFUtils.resolveExpression("#{bindings.ObjectionFlag.inputValue}"));
        
       // callJsFunction("pressButton");
    }

    public void stopExecPrisonVCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        System.err.println("MEGAAAAAAAAAAAAA     " + flag);
   print();
        changeExecutionFlg();
    }

    public void stopExecPrison2VCL(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        Boolean flag = (Boolean) valueChangeEvent.getNewValue();
        System.err.println("MEGAAAAAAAAAAAAA     " + flag);
        printTwo();
    }
}
