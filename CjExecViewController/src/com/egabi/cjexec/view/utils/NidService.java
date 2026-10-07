package com.egabi.cjexec.view.utils;

import com.egabi.cjexec.view.beans.security.UserInfo;
import com.egabi.nid.integration.GGProxyLayer;
import com.egabi.nid.integration.IGGProxyLayer;

import java.math.BigDecimal;

import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import javax.xml.ws.BindingProvider;

import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.output.RichActiveOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;

import org.json.JSONException;
import org.json.JSONObject;

/** Added by M.Salah **/
public class NidService {

    PersonData pd;
    private String idNum;

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getPersonDataGG() {
        return null;
    }

    public String getPersonData() {
        // Add event code here...
        String nId = null;
        String fcn = null;
        String userId = null;
        String channelId = null;
        String tokenId = null;
        String callPermission = null;
        String userName = null;
        String password = null;
        String endPointUrl = null;

        if (JSFUtils.resolveExpression("#{pageFlowScope.nIdBean.idNum}") != null) {
            nId = JSFUtils.resolveExpression("#{pageFlowScope.nIdBean.idNum}").toString();
        } else if (JSFUtils.resolveExpression("#{pageFlowScope.nIdParam}") != null) {
            nId = JSFUtils.resolveExpression("#{pageFlowScope.nIdParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.fcnParam}") != null) {
            fcn = JSFUtils.resolveExpression("#{pageFlowScope.fcnParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.userIdParam}") != null) {
            userId = JSFUtils.resolveExpression("#{pageFlowScope.userIdParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.channelIdParam}") != null) {
            channelId = JSFUtils.resolveExpression("#{pageFlowScope.channelIdParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.tokenIdParam}") != null) {
            tokenId = JSFUtils.resolveExpression("#{pageFlowScope.tokenIdParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.callPermissionParam}") != null) {
            callPermission = JSFUtils.resolveExpression("#{pageFlowScope.callPermissionParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.userNameParam}") != null) {
            userName = JSFUtils.resolveExpression("#{pageFlowScope.userNameParam}").toString();
        }
        if (JSFUtils.resolveExpression("#{pageFlowScope.passwordParam}") != null) {
            password = JSFUtils.resolveExpression("#{pageFlowScope.passwordParam}").toString();
        }

        ViewObject cjConfigVO = ADFUtil.findIterator("CjConfigRoVOIterator").getViewObject();
        cjConfigVO.setNamedWhereClauseParam("keyParam", "NID_INTEGRATION_END_POINT");
        cjConfigVO.executeQuery();
        cjConfigVO.first();
        Row cjConfigRoww = cjConfigVO.getCurrentRow();
        if (cjConfigRoww != null) {
            endPointUrl =
                cjConfigRoww.getAttribute("ValuesUrl") != null ? cjConfigRoww.getAttribute("ValuesUrl").toString() : "";
        }

        GGProxyLayer gGProxyLayer = new GGProxyLayer();
        IGGProxyLayer iGGProxyLayer = gGProxyLayer.getBasicHttpBindingIGGProxyLayer();

        BindingProvider bp = (BindingProvider) iGGProxyLayer;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPointUrl);
        System.err.println("endPointUrl : " + bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY));

        String payload = null;
        if (fcn == null) {
            fcn = "";
        }
        if (nId !=
            null) {
            payload =
         "       {\n" + "   \"Message\":{\n" + "      \"@xmlns\":\"urn:gg:eg:envelope:request:v1\",\n" +
         "      \"Header\":{\n" + "         \"X-IBM-Client-Id\":\"" + tokenId + "\",\n" + "         \"x-user-id\":\"" +
         userId + "\",\n" + "           \"x-user-channel\" : \"" + channelId + "\",\n" +
         "         \"TxName\":\"G2G_CSO_NID\",\n" + "         \"Credentials\":{\n" +
         "            \"@type\":\"username\",\n" + "            \"Username\":\"" + userName + "\",\n" +
         "            \"Password\":\"" + password + "\"\n" + "         }\n" + "      },\n" + "      \"Data\":{\n" +
         "         \"@xmlns\":\"urn:data:request\",\n" + "         \"@gzip\":\"no\",\n" +
         "         \"@encrypted\":\"no\",\n" + "         \"nid\":\"" + nId + "\",\n" + "         \"fcn\":\"" + fcn +
         "\"\n" + "      }\n" + "   }\n" + "}      ";
        }
       
        String json = iGGProxyLayer.submitJSONOnLayer(payload);
        System.out.println("json is :"+json);
        JSONObject data = null;
        JSONObject dataXX = null;
        Integer httpCode = null;
        BigDecimal govMapId = null;
        BigDecimal PoliceMapId = null;
        pd = (PersonData) JSFUtils.resolveExpression("#{pageFlowScope.personDataBean}");

        if (callPermission != null && !callPermission.isEmpty() && callPermission.equals("Y")) {
            pd.setRequest(payload);
            pd.setResponse(json);
            try {
                data = new JSONObject(json).getJSONObject("Message").getJSONObject("Data");
                dataXX = new JSONObject(json).getJSONObject("Message").getJSONObject("Header");
            } catch (JSONException jsone) {
                // TODO: Add catch code
                // jsone.printStackTrace();
                pd.setHttpCode(0);
                pd.setHttpMessage("\u062d\u062f\u062b \u062e\u0637\u0623 \u0623\u062b\u0646\u0627\u0621 \u0627\u0644\u0627\u062a\u0635\u0627\u0644 \u0628\u0627\u0644\u0633\u0631\u0641\u0631 \u0628\u0631\u062c\u0627\u0621 \u0627\u0644\u0645\u062d\u0627\u0648\u0644\u0647 \u0645\u0631\u0629 \u0623\u062e\u0631\u0649"); //JSFUtils.getFromBundle("bundle", "BAD_FORMAT").toString()
            }
            System.err.println("salah XX :: " + data + "\n --- \n " + dataXX);
            if (data != null && dataXX != null) {
                httpCode = Integer.parseInt(dataXX.getString("responseCode"));
                pd.setHttpCode(httpCode);
                if (httpCode == 200) {
                    try {
                        OperationBinding bindingGov = ADFUtil.findOperation("getNidMapId");
                        bindingGov.getParamsMap().put("flag", 1); /** Government **/
                        bindingGov.getParamsMap().put("code", data.getString("addressGovCode"));
                        govMapId = (BigDecimal) bindingGov.execute();

                        OperationBinding bindingPolice = ADFUtil.findOperation("getNidMapId");
                        bindingPolice.getParamsMap().put("flag", 2); /** Police **/
                        bindingPolice.getParamsMap().put("code", data.getString("addressPoliceCode"));
                        PoliceMapId = (BigDecimal) bindingPolice.execute();

                        pd.setNationalId(data.getString("nid"));
                        pd.setFactoryCardNumber(data.getString("fcn"));
                        pd.setCardExpirationDate(data.getString("cardExpirationDate"));
                        pd.setJobDescription(data.getString("jobDescription"));
                        pd.setGovernateCode(govMapId != null ? govMapId.toString() : "");
                        pd.setGovernate(data.getString("addressGov"));
                        pd.setPoliceAddressCode(PoliceMapId != null ? PoliceMapId.toString() : "");
                        pd.setPoliceAddress(data.getString("addressPolice"));
                        pd.setDateOfDeath(data.getString("deathDate"));
                        pd.setFirstName(data.getString("firstName"));
                        pd.setFatherFullName(data.getString("otherNames"));
                        pd.setGender(data.getString("gender"));
                        pd.setBirthDate(data.getString("birthDate"));
                        pd.setMaritalStatus(data.getString("maritalStatus"));
                        pd.setMotherFirstName(data.getString("motherFirstName"));
                        pd.setMotherSecondName(data.getString("motherSecondName"));
                       // pd.setMotherThirdName(data.getString("motherThirdName"));
                        pd.setAddress(data.getString("address"));
                        pd.setStatus(data.getString("status"));
                        pd.setResponseCode(new Integer(0));
                        pd.setResponseMessage("\u0645\u0639\u0627\u0645\u0644\u0647 \u0646\u0627\u062c\u062d\u0647"); //JSFUtils.getFromBundle("bundle", "SUCCESS_OPERATION").toString()
                    } catch (JSONException ex) {
                        // TODO: Add catch code
                        System.out.println(ex);
                        pd.setResponseCode(Integer.parseInt(data.getString("businessErrorCode")));
                        pd.setResponseMessage(data.getString("businessError"));
                    }
                } else if (httpCode == 401) {
                    pd.setHttpMessage("\u063a\u064a\u0631 \u0645\u0635\u0631\u062d"); //JSFUtils.getFromBundle("bundle", "UNAUTHORIZED").toString()
                } else if (httpCode == 429) {
                    pd.setHttpMessage("\u0637\u0644\u0628\u0627\u062a \u0643\u062b\u064a\u0631\u0647 \u0644\u0644\u063a\u0627\u064a\u0647"); //JSFUtils.getFromBundle("bundle", "TO_MANY_REQUESTS").toString()
                } else if (httpCode == 400) {
                    pd.setHttpMessage("\u062d\u062f\u062b \u062e\u0637\u0623 \u0623\u062b\u0646\u0627\u0621 \u0627\u0644\u0627\u062a\u0635\u0627\u0644 \u0628\u0627\u0644\u0633\u0631\u0641\u0631 \u0628\u0631\u062c\u0627\u0621 \u0627\u0644\u0645\u062d\u0627\u0648\u0644\u0647 \u0645\u0631\u0629 \u0623\u062e\u0631\u0649"); //JSFUtils.getFromBundle("bundle", "BAD_FORMAT").toString()
                } else if (httpCode == 404) {
                    pd.setHttpMessage("\u063a\u064a\u0631 \u0645\u0648\u062c\u0648\u062f"); //JSFUtils.getFromBundle("bundle", "NOT_FOUND").toString()
                } else if (httpCode == 500) {
                    pd.setHttpMessage("\u062e\u0637\u0623 \u0641\u064a \u0627\u0644\u062e\u0627\u062f\u0645"); //JSFUtils.getFromBundle("bundle", "INTERNAL_SERVER_ERROR").toString()
                }
            }

            /** Inquery Log **/
            BigDecimal loggedInEntityId = null;
            BigDecimal loggedInUserId = null;

            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
                ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
                loggedInEntityId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
            } else if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                       Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                       3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
                loggedInEntityId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
            }

            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId() != null) {
                loggedInUserId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());
            }

            OperationBinding binding = ADFUtil.findOperation("insertNIdServiceCallLog");
            binding.getParamsMap().put("userId", loggedInUserId);
            binding.getParamsMap().put("entityId", loggedInEntityId);
            binding.getParamsMap().put("nId", nId != null ? nId : "");
            binding.getParamsMap().put("callType", "N"); /** Inquery **/
            binding.getParamsMap().put("request", payload != null ? payload : "");
            binding.getParamsMap().put("response", json != null ? json : "");
            binding.execute();

        } else {
            pd.setHttpMessage("\u063a\u064a\u0631 \u0645\u0633\u0645\u0648\u062d \u0644\u0647\u0630\u0627 \u0627\u0644\u0645\u0633\u062a\u062e\u062f\u0645 \u0628\u0627\u0633\u062a\u062e\u062f\u0627\u0645 \u062e\u062f\u0645\u0629 \u0627\u0644\u0631\u0642\u0645 \u0627\u0644\u0642\u0648\u0645\u0649");
        }
        return null;
    }

    public String savePopUp() {
        // Add event code here...
        if (pd != null) {
            JSFUtils.addToPageFlowScope("nIdResponseParam", pd);

            /** Action Log **/
            BigDecimal loggedInEntityId = null;
            BigDecimal loggedInUserId = null;

            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) == 39 &&
                ((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId() != null) {
                loggedInEntityId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getParentEntityId().toString());
            } else if (((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree() != null &&
                       Integer.parseInt(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityDegree().toString()) ==
                       3 && ((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId() != null) {
                loggedInEntityId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getEntityId().toString());
            }

            if (((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId() != null) {
                loggedInUserId =
                    new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());
            }

            OperationBinding binding = ADFUtil.findOperation("insertNIdServiceCallLog");
            binding.getParamsMap().put("userId", loggedInUserId);
            binding.getParamsMap().put("entityId", loggedInEntityId);
            binding.getParamsMap().put("nId", pd.getNationalId() != null ? pd.getNationalId() : "");
            binding.getParamsMap().put("callType", "Y"); /** Action **/
            binding.getParamsMap().put("request", pd.getRequest() != null ? pd.getRequest() : "");
            binding.getParamsMap().put("response", pd.getResponse() != null ? pd.getResponse() : "");
            binding.execute();
        }

        JSFUtils.addToSession("clickedButton", "save");
        return "close";
    }

    public String cancelPopUp() {
        // Add event code here...
        JSFUtils.addToPageFlowScope("nIdResponseParam", pd);

        JSFUtils.addToSession("clickedButton", "cancel");
        return "close";
    }

    public String searchNIdLog() {
        // Add event code here...
        BigDecimal entityKolya = null;
        BigDecimal entityParial = null;
        Timestamp fromDate = null;
        Timestamp toDate = null;
        String today = null;
        BigDecimal callType = null;
        Row policeRow = ADFUtil.findIterator("ValidatePoliceReportROVO1Iterator").getCurrentRow();
        if (policeRow != null) {
            entityKolya = (BigDecimal) policeRow.getAttribute("NeyabKolyaa");
            entityParial = (BigDecimal) policeRow.getAttribute("NeyabGozayaa");
        }

        Row row = ADFUtil.findIterator("ClaimProcedureLogSearchFormRVO1Iterator").getCurrentRow();
        if (row != null) {
            fromDate = (Timestamp) row.getAttribute("FromDate");
            toDate = (Timestamp) row.getAttribute("ToDate");
            System.err.println("--> fromDate: " + fromDate + " - toDate: " + toDate);
            if (row.getAttribute("Today") != null) {
                today = row.getAttribute("Today").toString();
            }
            callType = (BigDecimal) row.getAttribute("CallType");
        }

        BigDecimal loggedInUserId =
            new BigDecimal(((UserInfo) JSFUtils.getFromSession("userInfo")).getUserId().toString());

        ViewObjectImpl nIdLogVO = (ViewObjectImpl) ADFUtil.findIterator("NIdServiceLogVO1Iterator").getViewObject();

        String condition = " 1 = 1 ";

        if (entityParial != null && entityParial.intValue() == -1) {
            condition +=
                " AND ENTITY_ID IN(SELECT " + entityKolya +
                " FROM ENTITY UNION SELECT ID FROM ENTITY where ENTITY_TYPE_ID = 1 AND PARENT_ENTITY_ID = " +
                entityKolya + ")";
        } else if (entityParial != null && entityParial.intValue() != -1) {
            condition += " AND ENTITY_ID = " + entityParial;
        } else if (entityParial == null) {
            condition += " AND ENTITY_ID = " + entityKolya;
        }

        if (callType != null && callType.intValue() == 1) {
            condition += " AND CALL_Type = 'N' ";
        } else if (callType != null && callType.intValue() == 2) {
            condition += " AND CALL_Type = 'Y' ";
        }

        if (fromDate != null && toDate != null) {
            condition += " AND CALL_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        }

        if (today != null && today.equals("Y")) {
            condition += " AND CALL_DATE = TO_CHAR(SYSDATE, 'yyyy-mm-dd')";
        }

        //        if (loggedInUserId != null) {
        //            condition += " AND USER_ID = " + loggedInUserId;
        //        }

        nIdLogVO.setWhereClause(condition);
        System.err.println("Query---> " + nIdLogVO.getQuery());
        nIdLogVO.executeQuery();

        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (nIdLogVO.getEstimatedRowCount() == 0 && !aot.isVisible()) {
            aot.setVisible(true);
            //JSFUtils.addFacesWarningMessage("\u0644\u0627 \u062A\u0648\u062C\u062F \u0646\u062A\u0627\u0626\u062C \u062A\u0637\u0627\u0628\u0642 \u0645\u062D\u062F\u062F\u0627\u062A \u0627\u0644\u0628\u062D\u062B");
        } else if (nIdLogVO.getEstimatedRowCount() != 0 && aot.isVisible()) {
            aot.setVisible(false);
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(aot);

        return null;
    }

    public String resetSearchNIdLog() {
        // Add event code here...

        RichActiveOutputText aot =
            (RichActiveOutputText) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "aot1");
        if (aot.isVisible()) {
            aot.setVisible(false);
            AdfFacesContext.getCurrentInstance().addPartialTarget(aot);
        }

        RichInputDate fromDate =
            (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id1");
        RichInputDate toDate =
            (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id2");
        fromDate.setValue(null);
        toDate.setValue(null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(fromDate);
        AdfFacesContext.getCurrentInstance().addPartialTarget(toDate);
        ADFUtil.findIterator("NIdServiceLogVO1Iterator").getViewObject().executeEmptyRowSet();
        return null;
    }

    public void todayCBChanged(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        if (valueChangeEvent.getNewValue().equals(true)) {
            RichInputDate fromDate =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id1");
            RichInputDate toDate =
                (RichInputDate) JSFUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), "id2");
            fromDate.setValue(null);
            toDate.setValue(null);
            AdfFacesContext.getCurrentInstance().addPartialTarget(fromDate);
            AdfFacesContext.getCurrentInstance().addPartialTarget(toDate);
        }
    }
}
