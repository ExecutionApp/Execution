package com.egabi.cjexec.view.NTRA;

public class Token {
    private String tokenValue;
    private String tokenType;
    private String expireIn;

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setExpireIn(String expireIn) {
        this.expireIn = expireIn;
    }

    public String getExpireIn() {
        return expireIn;
    }
}
