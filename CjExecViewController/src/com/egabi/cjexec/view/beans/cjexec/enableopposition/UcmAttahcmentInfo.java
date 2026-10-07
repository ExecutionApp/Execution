package com.egabi.cjexec.view.beans.cjexec.enableopposition;

import java.io.InputStream;

import java.math.BigDecimal;

public class UcmAttahcmentInfo {
    public InputStream fileInputStream;
    public String fileName;
    public BigDecimal attachId;
    
    public UcmAttahcmentInfo() {
        super();
    }


    public UcmAttahcmentInfo(InputStream fileInputStream, String fileName, BigDecimal attachId) {
        this.fileInputStream = fileInputStream;
        this.fileName = fileName;
        this.attachId = attachId;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setAttachId(BigDecimal attachId) {
        this.attachId = attachId;
    }

    public BigDecimal getAttachId() {
        return attachId;
    }

}
