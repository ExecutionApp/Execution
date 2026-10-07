package com.egabi.cjexec.view.utils;

import com.egabi.cjexec.model.appmodules.CjExecAMImpl;
import com.egabi.cjexec.model.appmodules.SimplifiedModeAMImpl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.math.BigDecimal;

import java.util.Base64;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;

import oracle.stellent.ridc.model.DataObject;

public class WebCamUtil {
    private RichLink photo;
    private RichLink video;
    private RichButton camBTS;

    public WebCamUtil() {
    }

    public void setPartyID(String PartyID) {
        this.PartyID = PartyID;
    }

    public String getPartyID() {
        return PartyID;
    }

    public void setFolderActionID(String FolderActionID) {
        this.FolderActionID = FolderActionID;
    }

    public String getFolderActionID() {
        return FolderActionID;
    }
    String PartyID;
    String Document_ID;
    String FolderActionID;

    public void webCam(ClientEvent clientEvent) {
        // Add event code here...
        String picture = (String) clientEvent.getParameters().get("picture");
        //        System.out.println("picture   **  "+picture);
        String prefix = "base64,";
        int startIndex = picture.indexOf(prefix) + prefix.length();
        byte[] imageData = Base64.getDecoder().decode(picture.substring(startIndex).getBytes());
        InputStream inputStream = new ByteArrayInputStream(imageData);

        //ADFContext.getCurrent().getPageFlowScope().put("PersonalPicIS", inputStream);
	//System.out.println("DocumentID  ***  " + dID + "Party ID " + this.getPartyID());
	try {
	    DataObject do_ = CjUtils.insertAttachmentAsFileIntoUCM(inputStream, 11, 1, 11, 1, "webcam", "jpeg");
	    String dDocName = do_.get("dDocName");
	    String dID = do_.get("dID");
	  ADFContext.getCurrent().getPageFlowScope().put("DID", dID);
	  ADFContext.getCurrent().getPageFlowScope().put("DName", dDocName);
	  this.setDocument_ID(dID); 
	  ADFContext.getCurrent().getSessionScope().put("DOCUMENTID", dID);
	} catch (Exception e) {
	    System.out.println("UCM Exceptioooooooooooooooooooooooooon");
	    e.printStackTrace();
	}

    }


    public void getPartyPicture() {
//        CjExecAMImpl root = (CjExecAMImpl) ADFUtil.getApplicationModuleForDataControl("CjExecAMDataControl");
//        SimplifiedModeAMImpl am = (SimplifiedModeAMImpl) root.getSimplifiedModeAM1();
//
//        ViewObject view = am.getCriminalJusticeFilesDetailsVO2();
//        view.setWhereClause("BUSINESSCASEID = " + ADFUtil.evaluateEL("#{sessionScope.businessCaseId}") +
//                            " AND PARTY_ID = " + ADFUtil.evaluateEL("#{pageFlowScope.WebCamUtil.partyID}"));
//        view.executeQuery();
      JSFUtils.addToPageFlowScope("DID",null);
      JSFUtils.addToPageFlowScope("DName",null);
      JSFUtils.addToPageFlowScope("PersonalPicIS",null);
      ADFContext.getCurrent().getSessionScope().put("DOCUMENTID", null);
    }


    private String SwitchFaces = "Face1";

    public void setSwitchFaces(String SwitchFaces) {
        this.SwitchFaces = SwitchFaces;
        System.err.println("SwitchFaces " + SwitchFaces);
    }

    public String getSwitchFaces() {
        return SwitchFaces;
    }

    public void setDocument_ID(String Document_ID) {
        this.Document_ID = Document_ID;
    }

    public String getDocument_ID() {
        return Document_ID;
    }

    public void Camera(ActionEvent actionEvent) {
        // Add event code here...

        this.getVideo().setVisible(true);
        this.getPhoto().setVisible(false);
        //        this.getCamBTS().setVisible(true);

    }

    public void Capture(ActionEvent actionEvent) {
        // Add event code here...
        this.getVideo().setVisible(false);
        this.getPhoto().setVisible(true);
    }

    public void setPhoto(RichLink photo) {
        this.photo = photo;
    }

    public RichLink getPhoto() {
        return photo;
    }

    public void setVideo(RichLink video) {
        this.video = video;
    }

    public RichLink getVideo() {
        return video;
    }

    public void Cam(ActionEvent actionEvent) {
        // Add event code here...
        this.getPhoto().setVisible(false);
    }

    public String Shoot() {
        // Add event code here...
        this.getPhoto().setVisible(true);
        this.getVideo().setVisible(false);
        this.getVideo().setVisible(true);
     return null;
    }

    public void setCamBTS(RichButton camBTS) {
        this.camBTS = camBTS;
    }

    public RichButton getCamBTS() {
        return camBTS;
    }


}
