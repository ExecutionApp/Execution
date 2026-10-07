package com.egabi.cjexec.view.utils;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ResourceBundle;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class CjUtils {
    public CjUtils() {
        super();
    }

    //This method is responsible for the following:-
    //1.Uploading attachments to UCM server
    //2.Inserting attachments into citizen requests attachments table
    public static void insertAttachmentsIntoUCM(String fileUrl, int userId, int businessCaseId, int parID, int entityId,
                                                String fileName) throws MalformedURLException, IOException, Exception {
        if (fileUrl != null) {
            RIDCService ridcSerivce = new RIDCService();

            URL url = new URL(fileUrl);
            System.out.println("url is:" + url);
            InputStream is = url.openStream();
            FilesModel fm = new FilesModel();
            fm.setFileContent(is);
            fm.setFileName(fileName);
            fm.setFileLength(is.available());
            fm.setEntityId(entityId);
            fm.setUserId(userId);
            fm.setBusinessCaseId(businessCaseId);
            fm.setParId(parID);

            DataObject do_ = ridcSerivce.AddContent(fm);
            String dDocName = do_.get("dDocName");
            String dID = do_.get("dID");
            System.out.println("document id is:" + dID);


        }

    }

    public static DataObject insertAttachmentAsFileIntoUCM(InputStream is, int userId, int businessCaseId, int parID,
                                                           int entityId, String fileName) throws MalformedURLException,
                                                                                                 IOException,
                                                                                                 Exception {

        return insertAttachmentAsFileIntoUCM(is, userId, businessCaseId, parID, entityId, fileName, null);

    }


    public static DataObject insertAttachmentAsFileIntoUCM(InputStream is, int userId, int businessCaseId, int parID,
                                                           int entityId, String fileName,
                                                           String mimeType) throws MalformedURLException, IOException,
                                                                                   Exception {
        RIDCService ridcSerivce = new RIDCService();

        FilesModel fm = new FilesModel();
        fm.setFileContent(is);
        fm.setFileName(fileName);
        fm.setFileLength(is.available());
        fm.setEntityId(entityId);
        fm.setUserId(userId);
        fm.setBusinessCaseId(businessCaseId);
        fm.setParId(parID);
        fm.setMimeType(mimeType);

        DataObject do_ = ridcSerivce.AddContent(fm);

        /** Updated by M.Salah - Check Null **/
        String dDocName = null;
        String dID = null;
        if (do_ != null) {
            dDocName = do_.get("dDocName");
            dID = do_.get("dID");
            System.err.println("did is: " + dID + "dDocName is: " + dDocName);
        } else {
            System.err.println("Failed to connect to UCM - Please check UCM Credentials");
        }

        return do_;
    }


    public static FilesModel DownloadContent(String dID, String dDocName) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.egabi.cjexec.view.utils.wccBundle");

        IdcClientManager manager = new IdcClientManager();
        IdcClient myIdcClient;
        FilesModel file = null;

        try {
            myIdcClient = manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
            @SuppressWarnings("deprecation")
            IdcContext myIdcContext =
                new IdcContext(resourceBundle.getString("RIDC_CLIENT_USERNAME"),
                               resourceBundle.getString("RIDC_CLIENT_PASSWORD")); // populate the binder with the parameters
            DataBinder myRequestDataBinder = myIdcClient.createBinder();
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_SERVICE_NAME_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_SERVICE_NAME_VALUE"));
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_DOCUMNET_ID_KEY"), dID);
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_DOCUMNET_NAME_KEY"), dDocName);
            ServiceResponse myServiceResponse = myIdcClient.sendRequest(myIdcContext, myRequestDataBinder);

            String contentType = myServiceResponse.getHeader("Content-Type");
            //    String contentType = myServiceResponse.getResponseType();
            file = new FilesModel();
            file.setFileContent(myServiceResponse.getResponseStream());
            file.setMimeType(contentType);
            //  System.out.println("Mime type" + contentType);
        } catch (IdcClientException idcce) {
            System.out.println("IDC Client Exception occurred. Unable to retrieve file. Message: " +
                               idcce.getMessage() + ", Stack trace: ");
            idcce.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception occurred. Unable to retrieve file. Message: " + e.getMessage() +
                               ", Stack trace: ");
            e.printStackTrace();
        }
        return file;
    }


    public InputStream getFileInputStream(String dId) {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("com.egabi.cjexec.view.utils.wccBundle");

        IdcClientManager manager = new IdcClientManager();
        IdcClient myIdcClient;


        //        try {
        //            myIdcClient = manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
        //            @SuppressWarnings("deprecation")
        //            IdcContext myIdcContext =
        //                new IdcContext(resourceBundle.getString("RIDC_CLIENT_USERNAME"),
        //                               resourceBundle.getString("RIDC_CLIENT_PASSWORD")); // populate the binder with the parameters
        //            DataBinder binder = myIdcClient.createBinder();
        //
        //            binder.putLocal("IdcService", "GET_FILE");
        //            binder.putLocal("dID", dId);
        //            binder.putLocal("allowInterrupt", "1");
        //            binder.putLocal("Rendition", "Primary");
        //            response = idcClient.sendRequest(this.userContext, binder);
        //            return response.getResponseStream();
        //        } catch (IdcClientException e) {
        //            e.printStackTrace();
        //        }
        return null;
    }
}
