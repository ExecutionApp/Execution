package com.egabi.model;

public class SMSConfiguration {
    private String smsEndPointUrl;
    private String smsSender;
    private String smsUser;
    private String smsPassword;
//    private String initialCjPassword;


    public void setSmsEndPointUrl(String smsEndPointUrl) {
        this.smsEndPointUrl = smsEndPointUrl;
    }

    public String getSmsEndPointUrl() {
        return smsEndPointUrl;
    }

    public void setSmsSender(String smsSender) {
        this.smsSender = smsSender;
    }

    public String getSmsSender() {
        return smsSender;
    }

    public void setSmsUser(String smsUser) {
        this.smsUser = smsUser;
    }

    public String getSmsUser() {
        return smsUser;
    }

    public void setSmsPassword(String smsPassword) {
        this.smsPassword = smsPassword;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

//    public void setInitialCjPassword(String initialCjPassword) {
//        this.initialCjPassword = initialCjPassword;
//    }
//
//    public String getInitialCjPassword() {
//        return initialCjPassword;
//    }
}
