package com.egabi.cjexec.view.NTRA;
 

import java.io.IOException;

import java.sql.Connection;

import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;

public class NtraClient {
    public GeneralResponse callNtraLogin(Connection dbConnection  ,String url, String userName, String password, String loggedUserName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String reqDate = dtf.format(now);
//        System.out.println("reqDate NtraLogin: " + reqDate);

        GeneralResponse loginResponse = new GeneralResponse();
        NtraDBHelper op = new NtraDBHelper();
        Token loginToken = new Token();
        OkHttpClient client = new OkHttpClient();
        String requestBody = "";
        //      requestBody =  "{\r\n\"Username\": \"Stage_SS\",\r\n\"Password\": \"AR$weS$z7e2\"\r\n}"
        //        requestBody =
        //            "{\r\n\"Username\": \"" + tawtheeqInfo.getTawtheeqUserName() + "\",\r\n\"Password\": \"" +
        //            tawtheeqInfo.getTawtheeqPassword() + "\"\r\n}";
        requestBody = "{\r\n\"Username\": \"" + userName + "\",\r\n\"Password\": \"" + password + "\"\r\n}";
        //        requestBody =  "{\r\n\"Username\": \"Stage_SSs\",\r\n\"Password\": \"AR$weS$z7e2\"\r\n}";
//        System.out.println("requestBody : " + requestBody);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder().url(url + "login")
                                               .post(body)
                                               .addHeader("Content-Type", "application/json")
                                               .addHeader("Cache-Control", "no-cache")
                                               .build();
        try {
            client = new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS) // connect timeout
                                               .writeTimeout(40, TimeUnit.SECONDS) // write timeout
                                               .readTimeout(40, TimeUnit.SECONDS) // read timeout
                                               .build();
            
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
//            System.out.println("NTRA login Response : " + responseStr);
            if (!responseStr.equals("")) {
                try {
                    org.json.JSONObject responseJson = new org.json.JSONObject(responseStr);
                    if (responseJson != null) {
                        if (responseStr.contains("token")) {
                            if (responseJson.getString("token") != null) {
                                loginToken.setTokenValue(String.valueOf(responseJson.get("token")));
                            }
                            if (responseJson.getString("tokenType") != null) {
                                loginToken.setTokenType(String.valueOf(responseJson.get("tokenType")));
                            }
                            if (responseJson.getString("expireIn") != null) {
                                loginToken.setExpireIn(String.valueOf(responseJson.get("expireIn")));
                            }

                            loginResponse.setErrorCode("200");
                            loginResponse.setMessage("Success");
                            loginResponse.setToken(loginToken);
//                            op.insertNtraLog(dbConnection,"NTRA_Login", loginToken.getTokenValue(),"","",
//                                             loggedUserName, reqDate, "200", "Success",
//                                                     requestBody, responseStr,"","");
                        } else {
                            if (responseJson.get("StatusCode") != null) {
                                loginResponse.setErrorCode(String.valueOf(responseJson.get("StatusCode")));
                            }
                            if (responseJson.get("StatusMessage") != null) {
                                loginResponse.setMessage(String.valueOf(responseJson.get("StatusMessage")));
                            }
                            op.insertNtraLog(dbConnection,"NTRA_Login", "","","",
                                                     loggedUserName, reqDate, loginResponse.getErrorCode(),
                                                     loginResponse.getMessage(), requestBody, responseStr,"","");
                        }
                    } else {
                        loginResponse.setErrorCode("400");
                        loginResponse.setMessage("Error occurred");
                        op.insertNtraLog(dbConnection,"NTRA_Login", "","","",
                                                 loggedUserName, reqDate, "400", "Error occurred", requestBody,
                                                 responseStr,"","");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    loginResponse.setErrorCode("401");
                    loginResponse.setMessage("Exception");
                    op.insertNtraLog(dbConnection,"NTRA_Login", "","","",
                                             loggedUserName, reqDate, "401", "Exception", requestBody, responseStr,"","");
                } 
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            loginResponse.setErrorCode("401");
            loginResponse.setMessage("Exception");
            op.insertNtraLog(dbConnection,"NTRA_Login", "","","", loggedUserName, reqDate,
                                     "401", "Exception", requestBody, "","","");
        } 

        return loginResponse;
    }
    
    
    public GeneralResponse callNtraSubmit(Connection dbConnection ,String nationalId, String mobileNo, String userName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String reqDate = dtf.format(now);
        System.out.println("reqDate NTRA Submit : " + reqDate);

        OkHttpClient client = new OkHttpClient();
        String tokenValue = "";
        String requestBody = "";
        GeneralResponse ntraResponse = new GeneralResponse();
        NtraDBHelper op = new NtraDBHelper();
        NtraPPOConfig ntraInfo = op.getNtraPPOConfig(dbConnection);
        GeneralResponse loginResponse = new GeneralResponse();
        NtraResponseData ntraData = new NtraResponseData();
        loginResponse =
            callNtraLogin(dbConnection,ntraInfo.getNtraUrl(), ntraInfo.getNtraUserName(),
                                 ntraInfo.getNtraPassword(), userName);
        if (loginResponse.getToken() != null) {
            tokenValue = loginResponse.getToken().getTokenValue();
            requestBody = "{\n" + 
            "   \"GovTalkMessage\":{\n" + 
            "      \"@xmlns\":\"http://www.govtalk.gov.uk/CM/envelope\",\n" + 
            "      \"EnvelopeVersion\":\"2.0\",\n" + 
            "      \"Header\":{\n" + 
            "         \"MessageDetails\":{\n" + 
            "            \"Class\":\"G2C_NTRA_NIDPhoneCheck\",\n" + 
            "            \"Qualifier\":\"request\",\n" + 
            "            \"Function\":\"submit\"\n" + 
            "         }\n" + 
            "      },\n" + 
            "      \"Body\":{\n" + 
            "         \"Message\":{\n" + 
            "            \"@xmlns\":\"urn:gg:eg:envelope:request:v1\",\n" + 
            "            \"Header\":{\n" + 
            "               \"correlationId\":\"1\",\n" + 
            "               \"originatingChannel\":\"1\",\n" + 
            "               \"channelRequestId\":\"1\",\n" + 
            "               \"originatingUserType\":\"1\",\n" + 
            "               \"originatingUserIdentifier\":\"1\",\n" + 
            "               \"serviceEntityId\": \"1\",\n" + 
            "               \"ServiceSlug\":\"SUHI-01\"\n" +
            "            },\n" + 
            "            \"Data\":{\n" + 
            "               \"@encrypted\":\"encrypted1\",\n" + 
            "               \"@gzip\":\"gzip1\",\n" + 
            "               \"@xmlns\":\"urn:data:request\",\n" + 
            "               \"MOBILE\":\""+mobileNo+"\",\n" + 
            "               \"NATIONALID\":\""+nationalId+"\"\n" + 
            "            }\n" + 
            "         }\n" + 
            "      }\n" + 
            "   }\n" + 
            "}";
            MediaType mediaType = MediaType.parse("application/json");

            RequestBody body = RequestBody.create(mediaType, requestBody);


            Request request = new Request.Builder().url(ntraInfo.getNtraUrl() + "submitgovtalk")
                                                   .post(body)
                                                   .addHeader("Content-Type", "application/json")
                                                   .addHeader("Authorization", tokenValue)
                                                   .addHeader("Cache-Control", "no-cache")
                                                   .build();

            try {
                client = new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS) // connect timeout
                                                   .writeTimeout(40, TimeUnit.SECONDS) // write timeout
                                                   .readTimeout(40, TimeUnit.SECONDS) // read timeout
                                                   .build();

                Response response = client.newCall(request).execute();
                String responseStr = response.body().string();
                System.out.println("Submit Ntra Response : " + responseStr);

                if (!responseStr.equals("")) {
                    try {
                        org.json.JSONObject responseJson = new org.json.JSONObject(responseStr);
                        if (responseJson != null) {
                            if (responseStr.contains("Data")) { // case success
                                org.json.JSONObject InquiryResponseJ = responseJson.getJSONObject("Message");
                                org.json.JSONObject data = InquiryResponseJ.getJSONObject("Data");
                                String responseCode = data.get("responseCode").toString();
                                String responseMessage = data.get("responseMessage").toString();
                                if (null != responseCode && responseCode.equalsIgnoreCase("200")) {
                                    ntraResponse.setErrorCode(responseCode);
                                    ntraResponse.setMessage(responseMessage);
                                    
                                    if (responseStr.contains("VERIFICATION_RESULT")){
                                        if (null != data.get("VERIFICATION_RESULT")) {
                                            ntraData.setVerficationResult(data.get("VERIFICATION_RESULT").toString());
                                        }
                                    }
                                    if (responseStr.contains("comments")){
                                        if (null != data.get("comments")) {
                                            ntraData.setComments(data.get("comments").toString());
                                        }
                                    }
                                    ntraResponse.setNtraResponseData(ntraData);
                                    System.out.println("ntraData.getVerficationResult() : " +ntraData.getVerficationResult().toString());
                                    op.insertNtraLog(dbConnection,"NtraSubmit", "",ntraData.getVerficationResult(),ntraData.getComments(), userName, reqDate,
                                                             responseCode,responseMessage, requestBody,responseStr,nationalId,mobileNo);
                                }else{
                                    ntraResponse.setErrorCode(responseCode);
                                    ntraResponse.setMessage(responseMessage);
                                    op.insertNtraLog(dbConnection,"NtraSubmit", "","","", userName, reqDate,
                                                             responseCode,responseMessage, requestBody,responseStr,nationalId,mobileNo);
                                }
                                
                                
                               

                            } else if (responseStr.contains("StatusCode")) { // fail case
                                String statusCode = responseJson.get("StatusCode").toString();
                                String statusMessage = responseJson.get("StatusMessage").toString();
                                ntraResponse.setErrorCode(statusCode);
                                ntraResponse.setMessage(statusMessage);
                                op.insertNtraLog(dbConnection,"NtraSubmit","", "", "", userName,
                                                         reqDate, statusCode, statusMessage, requestBody, responseStr,nationalId,mobileNo);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ntraResponse.setErrorCode("-201");
                        ntraResponse.setMessage(e.getMessage());
                        op.insertNtraLog(dbConnection , "NtraSubmit", "", "", "", userName, reqDate,
                                                 "-201", e.getMessage(), requestBody, responseStr,nationalId,mobileNo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                ntraResponse.setErrorCode("-202");
                ntraResponse.setMessage(e.getMessage());
                op.insertNtraLog(dbConnection , "NtraSubmit", "", "", "", userName, reqDate,
                                         "-202", e.getMessage(), requestBody, "",nationalId,mobileNo);
            }

        } else {
            ntraResponse.setErrorCode("-200");
            ntraResponse.setMessage("Login to get Token Error");
            op.insertNtraLog(dbConnection , "NtraSubmit", "", "", "", userName, reqDate,
                                         "-200", "Login to get Token Error", requestBody, "",nationalId,mobileNo);
        }
        try {
            dbConnection.close();
        } catch (SQLException e) {
        }
        return ntraResponse;
    }


}
