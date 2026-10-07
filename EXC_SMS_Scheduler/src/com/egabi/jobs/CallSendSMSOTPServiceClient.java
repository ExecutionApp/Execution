package com.egabi.jobs;


import com.egabi.common.SmsDBOperation;

import com.egabi.model.SMSConfiguration;
import com.egabi.model.SmsData;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


import java.io.ByteArrayOutputStream;

import java.util.List;

import javax.naming.NamingException;

import oracle.security.crypto.cert.IssuerAndSerialNo;

import org.w3c.dom.NodeList;

import sun.net.www.protocol.http.Handler;

public class CallSendSMSOTPServiceClient {


    public void callSms() {
        SmsDBOperation op = new SmsDBOperation();
        List<SmsData> smsList = op.readSmsQueueB();

        for (int i = 0; i < smsList.size(); i++) {
            SMSConfiguration smsConfig = op.getSmsConfig();
            String smsResponse =
                callSendSMSService(smsConfig.getSmsEndPointUrl(), smsList.get(i).getMobileNo(),
                                   smsList.get(i).getSmsBody(), smsConfig.getSmsSender(), smsConfig.getSmsUser(),
                                   smsConfig.getSmsPassword());

            if (smsResponse != null || !smsResponse.equals("")) {
                op.updateSMSRequestResult(smsList.get(i).getId(), smsResponse);
            } else {
                op.updateSMSRequestResult(smsList.get(i).getId(), "E");
            }
        }

    }

    public String callSendSMSService(String endPointUrl, String mobileNo, String body, String sender, String user,
                                     String password) {
        String soapAction = "http://tempuri.org/Send_SMSWithoutMsg_ID";

        System.out.println("EndPointUrlSMS : " + endPointUrl);
        System.out.println("SoapActionSMS : " + soapAction);
        System.err.println("user-----------111>" + user);
        System.err.println("password-----------222>" + password);
        String response = "";
        response = callSoapWebServiceSendSMS(endPointUrl, soapAction, mobileNo, body, sender, user, password);
        System.err.println("user----------->" + user);
        System.err.println("password----------->" + password);
        //        response = callSoapWebServiceSendSMS("http://www.ezagel.com/portex_ws/service.asmx?wsdl", soapAction, "+201099195097","856","ppo.gov.eg","Nyaba-Criminal-ORG","DPDDdEHq82");
        System.out.println("response callSendSMSService : " + response);
        return response;
    }

    private String callSoapWebServiceSendSMS(String soapEndpointUrl, String soapAction, String mobileNo, String body,
                                             String sender, String user, String password) {
        String response = "";
        URL endpoint = null;
        String requestString = "";
        String responseString = "";
        String responseDate = "";
System.err.println("callSoapWebServiceSendSMS----->");

        try {
            endpoint = new URL(null, soapEndpointUrl, new Handler() {
                protected URLConnection openConnection(URL url) throws IOException, MalformedURLException {
                    // The url is the parent of this stream handler, so must create clone
                    System.out.println("enter time out");
                    URL clone = new URL(url.toString());
                    URLConnection connection = clone.openConnection();
                    connection.setConnectTimeout(10 * 1000); // 15 sec
                    connection.setReadTimeout(180 * 1000); // 15 sec

                    return connection;
                }
            });
        } catch (MalformedURLException e) {
        }
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage requestSoapMessage =
                createSOAPRequestSendSMS(soapAction, mobileNo, body, sender, user, password);
            requestString = soapMessageToString(requestSoapMessage);
            SOAPMessage soapResponse = soapConnection.call(requestSoapMessage, endpoint);
            responseString = soapMessageToString(soapResponse);
            // Print the SOAP Response
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            responseDate = dateFormat.format(date);
            System.out.println("Response SOAP Message in " + responseDate + " :");
            soapResponse.writeTo(System.out);
            System.out.println();
            if (soapResponse.getSOAPBody().getElementsByTagName("Send_SMSWithoutMsg_IDResult").item(0) != null) {
                response =
                    soapResponse.getSOAPBody().getElementsByTagName("Send_SMSWithoutMsg_IDResult").item(0).getFirstChild().getNodeValue();
            } else {
                response = "Fail";
            }

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            response = "down;" + e.getMessage();
        }

        return response;
    }

    private SOAPMessage createSOAPRequestSendSMS(String soapAction, String mobileNo, String body, String sender,
                                                 String user, String password) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();

        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelopeSendSMS(soapMessage, mobileNo, body, sender, user, password);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        Date date = new Date();
        System.out.println("Request SOAP Message in " + dateFormat.format(date) + " :");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private void createSoapEnvelopeSendSMS(SOAPMessage soapMessage, String mobileNo, String body, String sender,
                                           String user, String password) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "tem";
        String myNamespaceURI = "http://tempuri.org/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("Send_SMSWithoutMsg_ID", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("Mobile_NO", myNamespace);
        soapBodyElem1.addTextNode(mobileNo);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Body", myNamespace);
        soapBodyElem2.addTextNode(body);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("Validty", myNamespace);
        soapBodyElem3.addTextNode("");
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("StartTime", myNamespace);
        soapBodyElem4.addTextNode("");
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("Sender", myNamespace);
        soapBodyElem5.addTextNode(sender);
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("User", myNamespace);
        soapBodyElem6.addTextNode(user);
        SOAPElement soapBodyElem7 = soapBodyElem.addChildElement("Password", myNamespace);
        soapBodyElem7.addTextNode(password);
        SOAPElement soapBodyElem8 = soapBodyElem.addChildElement("Service", myNamespace);
        soapBodyElem8.addTextNode("");

    }


    public String soapMessageToString(SOAPMessage message) {
        String result = null;
        if (message != null) {
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                message.writeTo(baos);
                result = baos.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (baos != null) {
                    try {
                        baos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
