package com.egabi.cjexec.view.utils;
import java.io.InputStream;
import java.io.IOException;
 
public class FilesModel{
    
       private String fileName;
       private String mimeType;
       private InputStream fileContent;
       private int businessCaseId;
       private int userId;
       private int entityId;
       private int businessCaseDetailedId;
       private int parId;
       private int templateObjectId;
       private String schemaName;
       private long fileLength;
       private String dID;
       private String dDocName;
       private int pirId;
       private String url;
       private int detailedId;

    public void setCaseSchemaId(int caseSchemaId) {
        this.caseSchemaId = caseSchemaId;
    }

    public int getCaseSchemaId() {
        return caseSchemaId;
    }
    private int caseSchemaId;

    public void setDetailedId(int detailedId) {
        this.detailedId = detailedId;
    }

    public int getDetailedId() {
        return detailedId;
    }
    
    public FilesModel() {
        super();
    }

    public FilesModel(String fileName, String mimeType, InputStream fileContent, int businessCaseId, int userId,
                      int entityId, int businessCaseDetailedId, int templateObjectId, String schemaName,
                      long fileLength) {
        super();
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileContent = fileContent;
        this.businessCaseId = businessCaseId;
        this.userId = userId;
        this.entityId = entityId;
        this.businessCaseDetailedId = businessCaseDetailedId;
        this.templateObjectId = templateObjectId;
        this.schemaName = schemaName;
        this.fileLength = fileLength;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setFileContent(InputStream fileContent) {
        this.fileContent = fileContent;
    }

    public InputStream getFileContent() {
        return fileContent;
    }

    public void setBusinessCaseId(int businessCaseId) {
        this.businessCaseId = businessCaseId;
    }

    public int getBusinessCaseId() {
        return businessCaseId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setBusinessCaseDetailedId(int businessCaseDetailedId) {
        this.businessCaseDetailedId = businessCaseDetailedId;
    }

    public int getBusinessCaseDetailedId() {
        return businessCaseDetailedId;
    }


    public void setTemplateObjectId(int templateObjectId) {
        this.templateObjectId = templateObjectId;
    }

    public int getTemplateObjectId() {
        return templateObjectId;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setParId(int parId) {
        this.parId = parId;
    }

    public int getParId() {
        return parId;
    }

    public void setDID(String dID) {
        this.dID = dID;
    }

    public String getDID() {
        return dID;
    }

    public void setDDocName(String dDocName) {
        this.dDocName = dDocName;
    }

    public String getDDocName() {
        return dDocName;
    }


    public void setPirId(int pirId) {
        this.pirId = pirId;
    }

    public int getPirId() {
        return pirId;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
   
}
