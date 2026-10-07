package com.egabi.cjexec.view.utils;



//import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.model.TransferFile;
import oracle.stellent.ridc.protocol.ServiceResponse;


public class RIDCService  {
    private static ResourceBundle resourceBundle;
  private String hostName;

    public RIDCService() {
        resourceBundle = ResourceBundle.getBundle("com.egabi.cjexec.view.utils.wccBundle");
       // hostName="CJECMProd:16200";
       hostName="CJECMProd:16200";
//        hostName="CJECMProd:16200";
    }

    public DataObject AddContent(FilesModel file) {
       
        IdcClientManager manager = new IdcClientManager();
        IdcClient myIdcClient;
        ServiceResponse myServiceResponse = null;
        InputStream fileStream = null;
        DataObject dataObject = null;
        try {
                  
            //UUID.randomUUID().toString()+ 
            String fileName = file.getFileName();
            long fileLength = file.getFileLength();
            fileStream=file.getFileContent();     
            myIdcClient =
                    manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
         //  myIdcClient.getConfig().setSocketTimeout(900000);
           
         //  System.out.println(resourceBundle.getString("RIDC_CLIENT_USERNAME") + "userNameee");
            @SuppressWarnings("deprecation")
            IdcContext myIdcContext =
                new IdcContext(resourceBundle.getString("RIDC_CLIENT_USERNAME"),
                               resourceBundle.getString("RIDC_CLIENT_PASSWORD")); // populate the binder with the parameters
            DataBinder myRequestDataBinder = myIdcClient.createBinder();
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_SERVICE_NAME_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_ADD_SERVICE_NAME_VALUE"));
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_DOC_TYPE_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_ADD_DOC_TYPE_VALUE"));
            // Title of the Uploaded file
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_DOC_TITLE_KEY"),
                                         fileName);
            // Name of Author
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_DOC_AUTHOR_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_ADD_DOC_AUTHOR_VALUE"));
            // Security for the content (Group and Account)
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_SECURITY_GROUP_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_ADD_SECURITY_GROUP_VALUE"));
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_DOC_ACCOUNT_KEY"),
                                         "");							 
			//start comment
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XBUSINESS_CASE_ID"), file.getBusinessCaseId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XENTITY_ID"), file.getEntityId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XUSER_ID"), file.getUserId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XCASE_SCHEMA_NAME"), file.getSchemaName()+"");           
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XTEMPLATE_OBJECT_ID"), file.getTemplateObjectId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XBUSINESS_CASE_DETILED_ID"), file.getBusinessCaseDetailedId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XPAR_ID"), file.getParId()+"");
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_XPIR_ID"), file.getPirId()+"");
            myRequestDataBinder.putLocal("dFormat", file.getMimeType());
//            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_ADD_XCOLLECTION_ID_KEY"),
//                                         getFolderIdFromPath(myIdcClient,
//                                                             myIdcContext,
//                                                             resourceBundle.getString("RIDC_CLIENT_ADD_XCOLLECTION_ID_VALUE")));
            myRequestDataBinder.addFile(resourceBundle.getString("RIDC_CLIENT_ADD_PRIMARY_FILE_KEY"),
                                        new TransferFile(fileStream, fileName,
                                                         fileLength));
			
			
			
            myServiceResponse =
                    myIdcClient.sendRequest(myIdcContext, myRequestDataBinder);
            String myResponseString = myServiceResponse.getResponseAsString();
System.out.println("myResponseString ::::::::::::: "+myResponseString);
            DataBinder binder = myServiceResponse.getResponseAsBinder();
            dataObject = binder.getLocalData();

                       
        } catch (IdcClientException idcce) {
 
            idcce.printStackTrace();
//        } catch (IOException ioe) {
//
//            ioe.printStackTrace();
       } 
          catch (Exception e) {

            e.printStackTrace();
        } finally {
            if (myServiceResponse != null) {
                myServiceResponse.close();
            } else {
    
            }
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
              
                }
            } else {
        
            }
        }
        return dataObject;
    }


 
 
     public Map<String,String> searchFiles(StringBuilder query) {
        Map<String, String> uploadedFiles = new HashMap<String, String>();       
        
        IdcClientManager manager = new IdcClientManager();
            ServiceResponse serviceResponse = null;
         //   List<FilesModel> list = new ArrayList<FilesModel>();
           
            try {

              //  System.out.println("Search Query: " + query.toString());
                IdcClient client = manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
                DataBinder dataBinderReq = client.createBinder();

            //Perform the search using IDC Service GET_SEARCH_RESULTS
                dataBinderReq.putLocal("IdcService", "GET_SEARCH_RESULTS");
                dataBinderReq.putLocal("QueryText", query.toString());
           
            //Display first 100 results
            //  dataBinderReq.putLocal("ResultCount", "100");
       
                serviceResponse = client.sendRequest(new IdcContext(), dataBinderReq);
                DataBinder dataBinderRes = serviceResponse.getResponseAsBinder();
                DataResultSet resultSet = dataBinderRes.getResultSet("SearchResults");
                  //Add results to a list
                for (DataObject dataObject : resultSet.getRows()) {
                //    FilesModel fm = new FilesModel();
                   String url = hostName+ dataObject.get("URL");
                    //System.out.println(dataObject.get("URL").toString());
          //        System.out.println("amany  ==  "+dataObject.get("dDocTitle").substring(dataObject.get("dDocTitle").indexOf("-")+1).trim());  
                    if(dataObject.get("dDocTitle").substring(dataObject.get("dDocTitle").indexOf("-")+1)!=null &&dataObject.get("dDocTitle").substring(dataObject.get("dDocTitle").indexOf("-")+1).trim().length()>1  )
                    uploadedFiles.put(dataObject.get("dID") +","+dataObject.get("dDocName"),dataObject.get("dDocTitle")+"|"+url);
                
            //        System.out.println((dataObject.get("dDocTitle")+"|"+dataObject.get("URL")).toString());


//                  
// fm.setFileName(dataObject.get("dDocTitle"));
//                    fm.setUrl(hostName+dataObject.get("URL")); 
//                    fm.setDID(dataObject.get("dID"));
//                    fm.setDDocName(dataObject.get("dDocName"));
//                    System.out.println(dataObject.get("dDocName"));
//                    System.out.println(dataObject.get("dID"));
//                    list.add(fm);
                }
               
           
            } catch(Exception ex) {
                System.out.println("Error Search: " + ex.getMessage());
            } finally {
                if (serviceResponse != null) {
                    serviceResponse.close();
                }
            } 
         return uploadedFiles;
    }
    public DataObject updateDocumentFile(String dID, 
                                        String dDocName, int isDeleted) {

         //  System.out.println("Amany inside update ridc");
          
           DataObject result = null;
           IdcClientManager manager = new IdcClientManager();
           IdcClient myIdcClient;
           ServiceResponse myServiceResponse = null;
           InputStream fileStream = null;
           DataObject dataObject = null;
           try

           {
               myIdcClient =
                       manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
               IdcContext myIdcContext =
               new IdcContext(resourceBundle.getString("RIDC_CLIENT_USERNAME"),
                        resourceBundle.getString("RIDC_CLIENT_PASSWORD")); // populate the binder with the parameters
                        DataBinder dataBinder = myIdcClient.createBinder();
               dataBinder.putLocal("IdcService", "UPDATE_DOCINFO");
               dataBinder.putLocal("dID", dID);
               
               dataBinder.putLocal("dDocName", dDocName);
               dataBinder.putLocal("xIsDeleted", isDeleted+"");
               ServiceResponse sr = myIdcClient.sendRequest(myIdcContext, dataBinder);
               result = sr.getResponseAsBinder().getLocalData();
           }
           
           catch (Exception ex)
           {
               ex.printStackTrace();
           }
           return result;
       }
 
    public List<FilesModel> search(StringBuilder query) {

         //   System.out.println("Search Username: " + username);
        IdcClientManager manager = new IdcClientManager();
            ServiceResponse serviceResponse = null;
            List<FilesModel> list = new ArrayList<FilesModel>();
           
            try {
//                StringBuilder query = new StringBuilder();
//
//            //If title is not null build the query
//                if (businessCaseId != null) {
//                    query.append("xBusinessCaseId <matches> `");   
//                    query.append(businessCaseId);
//                    query.append("`");
//                }
              
               
              //  System.out.println("Search Query: " + query.toString());
               
                IdcClient client = manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
                DataBinder dataBinderReq = client.createBinder();

            //Perform the search using IDC Service GET_SEARCH_RESULTS
                dataBinderReq.putLocal("IdcService", "GET_SEARCH_RESULTS");
                dataBinderReq.putLocal("QueryText", query.toString());
           
            //Display first 100 results
            //  dataBinderReq.putLocal("ResultCount", "100");
       
//                serviceResponse = client.sendRequest(new IdcContext(), dataBinderReq);
//                DataBinder dataBinderRes = serviceResponse.getResponseAsBinder();
//                DataResultSet resultSet = dataBinderRes.getResultSet("SearchResults");
//           
//            //Add results to a list
//                for (DataObject dataObject : resultSet.getRows()) {
//                   FilesModel fm = new FilesModel();
//                fm.setFileName(dataObject.get("dDocTitle"));
//                   fm.setDID(dataObject.get("dID"));
//                    fm.setDDocName(dataObject.get("dDocName"));
//                    System.out.println(dataObject.get("dDocName"));
//                    System.out.println(dataObject.get("dID"));
//                list.add(fm);
//                }
//               
           
            } catch(Exception ex) {
                System.out.println("Error Search: " + ex.getMessage());
            } finally {
                if (serviceResponse != null) {
                    serviceResponse.close();
                }
            } 
         return list;
    }//
    public FilesModel DownloadContent(String dID, String dDocName) {
        IdcClientManager manager = new IdcClientManager();
        IdcClient myIdcClient;
        FilesModel file = null;
        
        try {
            myIdcClient =
          manager.createClient(resourceBundle.getString("RIDC_CLIENT_URL"));
            @SuppressWarnings("deprecation")
            IdcContext myIdcContext =
                new IdcContext(resourceBundle.getString("RIDC_CLIENT_USERNAME"),
                               resourceBundle.getString("RIDC_CLIENT_PASSWORD")); // populate the binder with the parameters
            DataBinder myRequestDataBinder = myIdcClient.createBinder();
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_SERVICE_NAME_KEY"),
                                         resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_SERVICE_NAME_VALUE"));
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_DOCUMNET_ID_KEY"),
                                         dID);
            myRequestDataBinder.putLocal(resourceBundle.getString("RIDC_CLIENT_DOWNLOAD_DOCUMNET_NAME_KEY"),
                                         dDocName);
            ServiceResponse myServiceResponse =
                myIdcClient.sendRequest(myIdcContext, myRequestDataBinder);
            
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
            System.out.println("Exception occurred. Unable to retrieve file. Message: " +
                               e.getMessage() + ", Stack trace: ");
            e.printStackTrace();
        }
        return file;
    }
    
    
    public InputStream DownloadContent2(String dID, String dDocName) {
        return DownloadContent(dID, dDocName).getFileContent();
    }
}

