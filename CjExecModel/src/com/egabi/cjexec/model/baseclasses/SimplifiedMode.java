package com.egabi.cjexec.model.baseclasses;

import java.math.BigDecimal;

public class SimplifiedMode {
    
    private BigDecimal bcYear ;
    private BigDecimal tableType ;
    private BigDecimal neyabaaKolyaa ;
    private BigDecimal neyabaaGoziaa ;
    private BigDecimal policeId ;
    
    //    private BigDecimal hasrNo ;
    private BigDecimal hasrYear ; 
    private BigDecimal hasrType ; 
    private BigDecimal hasrRepeaated ; 
    
    private String address  = null; 
    private String nationality ;  
    private BigDecimal goverenment ;
    private BigDecimal attendanceType;
    private BigDecimal idNoType ;
    private String judgText ;
    private String neyabaa ;
    private BigDecimal entityPresident ;
    private BigDecimal counting = new BigDecimal (0);
    private int externalProcedureMode = 2 ; 

    public void setBcYear(BigDecimal bcYear) {
        this.bcYear = bcYear;
    }

    public BigDecimal getBcYear() {
        return bcYear;
    }

    public void setExternalProcedureMode(int externalProcedureMode) {
        this.externalProcedureMode = externalProcedureMode;
    }

    public int getExternalProcedureMode() {
        return externalProcedureMode;
    }

    public void setTableType(BigDecimal tableType) {
        this.tableType = tableType;
    }

    public BigDecimal getTableType() {
        return tableType;
    }

    public void setNeyabaaKolyaa(BigDecimal neyabaaKolyaa) {
        this.neyabaaKolyaa = neyabaaKolyaa;
    }

    public BigDecimal getNeyabaaKolyaa() {
        return neyabaaKolyaa;
    }

    public void setNeyabaaGoziaa(BigDecimal neyabaaGoziaa) {
        this.neyabaaGoziaa = neyabaaGoziaa;
    }

    public BigDecimal getNeyabaaGoziaa() {
        return neyabaaGoziaa;
    }

    public void setPoliceId(BigDecimal policeId) {
        this.policeId = policeId;
    }

    public BigDecimal getPoliceId() {
        return policeId;
    }

    public void setHasrYear(BigDecimal hasrYear) {
        this.hasrYear = hasrYear;
    }

    public BigDecimal getHasrYear() {
        return hasrYear;
    }

    public void setHasrType(BigDecimal hasrType) {
        this.hasrType = hasrType;
    }

    public BigDecimal getHasrType() {
        return hasrType;
    }

    public void setHasrRepeaated(BigDecimal hasrRepeaated) {
        this.hasrRepeaated = hasrRepeaated;
    }

    public BigDecimal getHasrRepeaated() {
        return hasrRepeaated;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationality() {
        return nationality;
    }

    public void setGoverenment(BigDecimal goverenment) {
        this.goverenment = goverenment;
    }

    public BigDecimal getGoverenment() {
        return goverenment;
    }

    public void setAttendanceType(BigDecimal attendanceType) {
        this.attendanceType = attendanceType;
    }

    public BigDecimal getAttendanceType() {
        return attendanceType;
    }

    public void setIdNoType(BigDecimal idNoType) {
        this.idNoType = idNoType;
    }

    public BigDecimal getIdNoType() {
        return idNoType;
    }

    public void setJudgText(String judgText) {
        this.judgText = judgText;
    }

    public String getJudgText() {
        return judgText;
    }

    public SimplifiedMode() {
        super();
    }

    public void setNeyabaa(String neyabaa) {
        this.neyabaa = neyabaa;
    }

    public String getNeyabaa() {
        return neyabaa;
    }

    public void setEntityPresident(BigDecimal entityPresident) {
        this.entityPresident = entityPresident;
    }

    public BigDecimal getEntityPresident() {
        return entityPresident;
    }

    public void setCounting(BigDecimal counting) {
        this.counting = counting;
    }

    public BigDecimal getCounting() {
     System.out.println("counting *********** "+counting);    
        return counting;
    }
}
