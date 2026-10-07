package com.egabi.cjexec.view.beans.bcDetails;

import java.math.BigDecimal;


public class SimplifiedHasr {
    public SimplifiedHasr() {
        super();
    }
    
    private BigDecimal bcYear ;
    private BigDecimal tableType ;
    private BigDecimal neyabaaKolyaa ;
    private BigDecimal neyabaaGoziaa ;
    private BigDecimal policeId ;
   
//    private BigDecimal hasrNo ; 
    private BigDecimal hasrYear ; 
    private BigDecimal hasrType ; 
    private BigDecimal hasrRepeaated ; 
    
    private String address ; 
    private String nationality ;  
    private BigDecimal goverenment ;
    private BigDecimal attendanceType;
    private BigDecimal idNoType ;
    private String judgText ;


    public void setJudgText(String judgText) {
        this.judgText = judgText;
    }

    public String getJudgText() {
        return judgText;
    }
}
