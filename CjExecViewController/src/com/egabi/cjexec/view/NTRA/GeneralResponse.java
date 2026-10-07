package com.egabi.cjexec.view.NTRA;

public class GeneralResponse {
    private String errorCode;
    private String message;
    private Token token;
    private NtraResponseData ntraResponseData;

    public void setNtraResponseData(NtraResponseData ntraResponseData) {
        this.ntraResponseData = ntraResponseData;
    }

    public NtraResponseData getNtraResponseData() {
        return ntraResponseData;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }
}
