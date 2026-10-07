package com.egabi.cjexec.view.beans.security;

import com.egabi.cjexec.model.baseclasses.SimplifiedMode;
import com.egabi.cjexec.view.utils.ADFUtil;
import com.egabi.cjexec.view.utils.JSFUtils;

import java.io.IOException;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.controller.ControllerContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;

public class Welcome {

    private String welcomeText;

    public Welcome() {

    }

    public String getWelcomeText() {
        return welcomeText;
    }

    public void onUnloadHandler(ClientEvent clientEvent) {
        System.err.println("onUnloadHandler");
    }

    @PostConstruct
    public void init() {
        // checkUserAuth();
        getUserInfo();

    }




    String mode = "2";

    public void beforePhase(PhaseEvent phaseEvent) {
        JSFUtils.addToSession("ClaimDetails", false);
ViewObject vo = ADFUtil.findIterator("SettlementsCodeROVO1Iterator").getViewObject();
        RowSetIterator rws =     vo.createRowSetIterator(null);
while (rws.hasNext()  )
{System.out.println("SettlementsCodeROVO1Iterator");
    Row rw = rws.next();
    if((rw.getAttribute("Type")+"").equalsIgnoreCase("EF")){
        JSFUtils.addToSession("pSettlementCodeEF", rw.getAttribute("SettlementCode"));
        JSFUtils.addToSession("pFixedBenfit", rw.getAttribute("FixedBenfit"));
        JSFUtils.addToSession("pPercentageBenfit", rw.getAttribute("PercentageBenfit"));
        JSFUtils.addToSession("pBillerId", rw.getAttribute("BillerId"));
    }
    else  if((rw.getAttribute("Type")+"").equalsIgnoreCase("GOV")){
        JSFUtils.addToSession("pSettlementCodeGOV", rw.getAttribute("SettlementCode"));
        JSFUtils.addToSession("pFixedBenfitGOV", rw.getAttribute("FixedBenfit"));
        JSFUtils.addToSession("pPercentageBenfitGOV", rw.getAttribute("PercentageBenfit"));
    }
        else  if((rw.getAttribute("Type")+"").equalsIgnoreCase("AUTO")){
            JSFUtils.addToSession("pSettlementCodeAUTO", rw.getAttribute("SettlementCode"));
            JSFUtils.addToSession("pFixedBenfitAUTO", rw.getAttribute("FixedBenfit"));
            JSFUtils.addToSession("pPercentageBenfitAUTO", rw.getAttribute("PercentageBenfit"));
        }
    }
        JSFUtils.addToSession("pMoveFlg", false);
//        JSFUtils.addToSession("disabledValidate", true);
                    FacesContext facesCtx = FacesContext.getCurrentInstance();
            ExternalContext extCtx = facesCtx.getExternalContext();        
    HttpServletRequest req = (HttpServletRequest) extCtx.getRequest();
            
        Map<String, String> map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String loginid = (String) JSFUtils.getFromSession("loginId");
        mode = (String) (JSFUtils.getFromSession("mode") == null ? map.get("mode") : JSFUtils.getFromSession("mode"));
        //                if (map.get("mode") != null)
        //                    mode = map.get("mode") ;
        System.err.println(mode + " ***************** loginid         ----------*******-----  " + loginid);
        JSFUtils.addToSession("pSearchType", "1");
        JSFUtils.addToSession("PFlAG", new BigDecimal (0));
        System.err.println(mode + " ***************** Session Id         ----------*******-----  " + req.getSession().getId());
//                        JSFUtils.storeOnSession("loginid", loginid);
        JSFUtils.storeOnSession("sessionid", req.getSession().getId());
        JSFUtils.storeOnSession("mode", mode);
        UserInfo userInfo =
            (JSFUtils.getFromSession("userInfo") == null) ? new UserInfo() :
            (UserInfo) JSFUtils.getFromSession("userInfo");

        SimplifiedMode sm =
            (JSFUtils.getFromSession("SM") == null) ? new SimplifiedMode() :
            (SimplifiedMode) JSFUtils.getFromSession("SM");

        if (JSFUtils.getFromSession("SM") == null) {

            JSFUtils.storeOnSession("SM", sm);
        }

        if (JSFUtils.getFromSession("userInfo") == null) {
            userInfo.setUserName("null");

            JSFUtils.storeOnSession("userInfo", userInfo);
        }
        
      
        if (userInfo.getEntityId() == null) //handle by farahat if login without log in screen
        {
            userInfo.setEntityId(ADFUtil.getBoundAttributeValue("EntityId").toString());
            ADFContext.getCurrent().getSessionScope().put("AgendaEnt",new BigDecimal (ADFUtil.getBoundAttributeValue("EntityId").toString()));
        }
        ADFContext.getCurrent().getSessionScope().put("userNameToBC", userInfo.getFullName());
        ADFContext.getCurrent().getSessionScope().put("entityIdToBC", userInfo.getEntityId());
        ADFContext.getCurrent().getSessionScope().put("userIdToBC", userInfo.getUserId());
        //            if(phaseEvent.getPhaseId().toString().equalsIgnoreCase("RENDER_RESPONSE 6")){
        //
        //                onPageLoad();
        //
        //                }

    }

    public void onPageLoad() {
        System.out.println("onPageLoad");
    }

    private void checkUserAuth() {

        // Stored in ApplicationFilter class
        //        String loginToken = (String)JSFUtils.resolveExpression("#{sessionScope.loginId}");

        DCIteratorBinding cIteratorBinding = ADFUtil.findIterator("SecUsersSessionsVOIterator");

        //        System.err.println("CSFDSDF:: " + cIteratorBinding.getEstimatedRowCount());

        // Filter user session iter by token id [inserted from outside]
        OperationBinding binding = ADFUtil.findOperation("ExecuteWithParams");
        //        binding.getParamsMap().put("loginToken", loginToken);
        binding.execute();

        long count = cIteratorBinding.getEstimatedRowCount();

        //        System.err.println("GGGGGGGG:: " + count);

        // Check filterign result
        //        Row rsRow = (Row)binding.getResult();

        if (count == 0) {
            // Invalid login
            JSFUtils.storeOnSession("isUserSignedIn", false);
            JSFUtils.storeOnSession("loginErrorMsg", ADFUtil.evaluateEL("#{bundle.FAILED_LOGIN}"));
            // Redirect to Error page
            FacesContext facesCtx = FacesContext.getCurrentInstance();
            ExternalContext extCtx = facesCtx.getExternalContext();
            HttpSession session = (HttpSession) extCtx.getSession(false);
            HttpServletRequest req = (HttpServletRequest) extCtx.getRequest();
            
            try {
                session.invalidate();
                extCtx.redirect(req.getServletContext().getContextPath() + "/faces/AuthenticationError");
            } catch (IOException e) {
                JSFUtils.storeOnSession("loginErrorMsg", e.getMessage());
            }
            return;
        }

        // Get values from filterd iterator
        String username = (String) ADFUtil.getBoundAttributeValue("Username");
        String userId =
            ADFUtil.getBoundAttributeValue("UserId") != null ?
            ((BigDecimal) ADFUtil.getBoundAttributeValue("UserId")).toString() : null;
        
        System.out.println("*/**///*///EntityPresident/////////"+ADFUtil.getBoundAttributeValue("EntityPresident"));
        ADFContext.getCurrent().getSessionScope().put("Entity_President",ADFUtil.getBoundAttributeValue("EntityPresident"));

        System.err.println("userId : " + userId + " - username : " + username);
        String fullName = (String) ADFUtil.getBoundAttributeValue("FullName");
        String entityId = ((BigDecimal) JSFUtils.resolveExpression("#{bindings.EntityId.inputValue}")).toString();
        String roleId = ((BigDecimal) JSFUtils.resolveExpression("#{bindings.RoleId.inputValue}")).toString();
        String entityDegree =
            ((BigDecimal) JSFUtils.resolveExpression("#{bindings.EntityDegree.inputValue}")).toString();
        String parentEntityId =
            ((BigDecimal) JSFUtils.resolveExpression("#{bindings.ParentEntityId.inputValue}")).toString();
        String entityName = (String) ADFUtil.getBoundAttributeValue("EntityName");
        String firstName = (String) ADFUtil.getBoundAttributeValue("FirstName");
        // Successfully login
        JSFUtils.storeOnSession("isUserSignedIn", true);
        // Store attributes in session
        JSFUtils.storeOnSession("userName", username);
        JSFUtils.storeOnSession("fullName", fullName);
        JSFUtils.storeOnSession("entityId", entityId);
        JSFUtils.storeOnSession("entityName", entityName);
        JSFUtils.storeOnSession("firstName", firstName);
        if (entityDegree.equalsIgnoreCase("3")) {
            System.out.println("************  User Gozyaaaaaaaaaaa  *************************");
            System.err.println("entityId1" + entityId + "  entityId2 " + parentEntityId);
            JSFUtils.storeOnSession("entityId1", entityId);
            JSFUtils.storeOnSession("entityId2", parentEntityId);
        } else if (entityDegree.equalsIgnoreCase("39")) {
            System.out.println("************  User Kolyaaaaaaaaaaa  *************************");
            System.err.println("  entityId2 " + parentEntityId);
            JSFUtils.storeOnSession("entityId2", entityId);
        }
        JSFUtils.storeOnSession("roleId", roleId);
        JSFUtils.storeOnSession("userId", userId);
        JSFUtils.storeOnSession("entityDegree", entityDegree);


        SimplifiedMode sm = new SimplifiedMode();
        JSFUtils.storeOnSession("SM", sm);

        // User menu pages
        List<String> menus = new ArrayList<String>();
        DCIteratorBinding iter = ADFUtil.findIterator("MenuRolesROVIterator");
        iter.executeQuery();
        RowSetIterator rowIter = iter.getViewObject().createRowSetIterator("");
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            String menuUrl = (String) row.getAttribute("Url");
            menus.add(menuUrl);
        }

        ViewObject userRolesView = ADFUtil.findIterator("UserRolesRVO1Iterator").getViewObject();
        userRolesView.setNamedWhereClauseParam("roleIdBind", roleId);
        userRolesView.setNamedWhereClauseParam("userIdBind", userId);
        userRolesView.executeQuery();

        long userRolesViewCount = userRolesView.getEstimatedRowCount();


        ArrayList roles = new ArrayList();
        RowSetIterator rsi = userRolesView.createRowSetIterator(null);

        for (int i = 0; i < userRolesViewCount; i++) {
            Row row = rsi.next();
            roles.add(row.getAttribute("FunctionId"));
        }


        JSFUtils.storeOnSession("roles", roles);


        JSFUtils.storeOnSession("menusList", menus);

        welcomeText = ADFUtil.evaluateEL("#{bundle.WELCOME}") + " " + firstName;
        System.err.println("==> " + firstName);
    }

    /** Added by M.Salah **/
    private void getUserInfo() {

        String userName = null;
        String entityId = null;
        String parentEntityId = null;
        String entityDegree = null;
        String roleId = null;
        String userId = null;
        String loginToken = null;
        String firstName = null;
        String entityName = null;
        String fullName = null;
        String automationFlag = null;
        String entityAppeal = null;
        String errandFlag   = null;
        String errandEntUser   = null;
        String nidUserId = null;
        String nidChannelId = null;
        String nidTokenId = null;
        String nidCallPermission = null;
        String nidUserName = null;
        String nidPassword = null;
        String excErrandEntityId = null ;
        ADFContext.getCurrent().getSessionScope().put("pClaimDeleted", "A");
        UserInfo userInfo =
            (JSFUtils.getFromSession("userInfo") == null) ? new UserInfo() :
            (UserInfo) JSFUtils.getFromSession("userInfo");
        JSFUtils.storeOnSession("ClaimEntity", "0");
        DCIteratorBinding cIteratorBinding = ADFUtil.findIterator("SecUsersSessionsVOIterator");
        OperationBinding binding = ADFUtil.findOperation("ExecuteWithParams");
        binding.execute();
        long count = cIteratorBinding.getEstimatedRowCount();
        if (count == 0) {
            JSFUtils.storeOnSession("isUserSignedIn", false);
            if (ADFUtil.evaluateEL("#{bundle.FAILED_LOGIN}") != null) {
                JSFUtils.storeOnSession("loginErrorMsg", ADFUtil.evaluateEL("#{bundle.FAILED_LOGIN}").toString());
            }
            FacesContext facesCtx = FacesContext.getCurrentInstance();
            ExternalContext extCtx = facesCtx.getExternalContext();
            HttpSession session = (HttpSession) extCtx.getSession(false);
            HttpServletRequest req = (HttpServletRequest) extCtx.getRequest();
            try {
                session.invalidate();
                extCtx.redirect(req.getServletContext().getContextPath() + "/faces/AuthenticationError");
            } catch (IOException e) {
                JSFUtils.storeOnSession("loginErrorMsg", e.getMessage());
            }
            return;
        }

        if (ADFUtil.getBoundAttributeValue("Username") != null) {
            userName = ADFUtil.getBoundAttributeValue("Username").toString();
        }
        if (ADFUtil.getBoundAttributeValue("AutomationFlag") != null) {
            automationFlag = ADFUtil.getBoundAttributeValue("AutomationFlag").toString();
        }

        if (ADFUtil.getBoundAttributeValue("UserId") != null) {
            userId = ADFUtil.getBoundAttributeValue("UserId").toString();
        }

        if (ADFUtil.getBoundAttributeValue("RoleId") != null) {
            roleId = ADFUtil.getBoundAttributeValue("RoleId").toString();
        }

        if (ADFUtil.getBoundAttributeValue("EntityDegree") != null) {
            entityDegree = ADFUtil.getBoundAttributeValue("EntityDegree").toString();

        }
        
        if (ADFUtil.getBoundAttributeValue("ExcErrandUserFlag") != null) {
            errandEntUser = ADFUtil.getBoundAttributeValue("ExcErrandUserFlag").toString();

        }
        if (ADFUtil.getBoundAttributeValue("ErrandFlag") != null) {
            errandFlag = ADFUtil.getBoundAttributeValue("ErrandFlag").toString();

        }
        JSFUtils.storeOnSession("RDF", "N"); 
        if (ADFUtil.getBoundAttributeValue("ExcDisRuleFlag") != null) {
            String flag = ADFUtil.getBoundAttributeValue("ExcDisRuleFlag").toString();
            JSFUtils.storeOnSession("RDF", flag); 
        }
        
        
        
        if (ADFUtil.getBoundAttributeValue("ExcErrandEntityId") != null) {
            System.out.println(ADFUtil.getBoundAttributeValue("EntityId").toString()+"   ExcErrandEntityId    "+ADFUtil.getBoundAttributeValue("ExcErrandEntityId").toString());
            String flag = ADFUtil.getBoundAttributeValue("ExcErrandUserFlag").toString();
            int entId =Integer.parseInt(ADFUtil.getBoundAttributeValue("EntityId").toString());
             int excErrandId =Integer.parseInt(ADFUtil.getBoundAttributeValue("ExcErrandEntityId").toString());
          System.err.println(flag+"--"+ ADFUtil.getBoundAttributeValue("EntityId")+"--"+ADFUtil.getBoundAttributeValue("ExcErrandEntityId"));  
            System.err.println((!flag.equalsIgnoreCase("Y") && 
                entId == excErrandId));
            if (flag.equalsIgnoreCase("Y") && 
                ( ADFUtil.getBoundAttributeValue("EntityId").toString()
                .equalsIgnoreCase(ADFUtil.getBoundAttributeValue("ExcErrandEntityId").toString())))
            { excErrandEntityId =  ADFUtil.getBoundAttributeValue("EntityId").toString();
                errandEntUser = ADFUtil.getBoundAttributeValue("EntityId").toString();
                System.err.println("******");
            }
                    else {
                System.out.println("QWQWQWQWQWQW");
                excErrandEntityId =ADFUtil.getBoundAttributeValue("ExcErrandEntityId").toString();
                        errandEntUser = "0";
                    }
            userInfo.setErrandEntUser(errandEntUser);
            JSFUtils.storeOnSession("errandEntUser", errandEntUser);
            System.out.println("errandEntUser value is "+errandEntUser);
        }
        if (ADFUtil.getBoundAttributeValue("EntityId") != null) {
            entityId = ADFUtil.getBoundAttributeValue("EntityId").toString();
            JSFUtils.storeOnSession("UserEnt", entityId);
        }

        if (ADFUtil.getBoundAttributeValue("ParentEntityId") != null) {
            parentEntityId = ADFUtil.getBoundAttributeValue("ParentEntityId").toString();

        }

        if (JSFUtils.resolveExpression("#{sessionScope.loginId}") != null) {
            loginToken = JSFUtils.resolveExpression("#{sessionScope.loginId}").toString();
        }

        if (ADFUtil.getBoundAttributeValue("EntityName") != null) {
            entityName = ADFUtil.getBoundAttributeValue("EntityName").toString();
        }

        if (ADFUtil.getBoundAttributeValue("FullName") != null) {
            fullName = ADFUtil.getBoundAttributeValue("FullName").toString();
        }

        if (ADFUtil.getBoundAttributeValue("EntityAppeal") != null) {
            entityAppeal = ADFUtil.getBoundAttributeValue("EntityAppeal").toString();
        }

        if (ADFUtil.getBoundAttributeValue("NidUserId") != null) {
            nidUserId = ADFUtil.getBoundAttributeValue("NidUserId").toString();
        }
        if (ADFUtil.getBoundAttributeValue("NidChannelId") != null) {
            nidChannelId = ADFUtil.getBoundAttributeValue("NidChannelId").toString();
        }
        if (ADFUtil.getBoundAttributeValue("NidTokenId") != null) {
            nidTokenId = ADFUtil.getBoundAttributeValue("NidTokenId").toString();
        }
        if (ADFUtil.getBoundAttributeValue("NidCallPermission") != null) {
            nidCallPermission = ADFUtil.getBoundAttributeValue("NidCallPermission").toString();
        }
        System.out.println("*/**///*///EntityPresident/////////"+ADFUtil.getBoundAttributeValue("EntityPresident"));
        ADFContext.getCurrent().getSessionScope().put("Entity_President",ADFUtil.getBoundAttributeValue("EntityPresident"));
        ViewObject cjConfigVO = ADFUtil.findIterator("CjConfigRoVOIterator").getViewObject();
        cjConfigVO.setNamedWhereClauseParam("keyParam", "NID_USER_NAME");
        cjConfigVO.executeQuery();
        System.err.println("cjConfigVO count 1 : " + cjConfigVO.getEstimatedRowCount());
        cjConfigVO.first();
        System.err.println("cjConfigVO count 1 : " + cjConfigVO.getEstimatedRowCount());
        Row cjConfigRow = cjConfigVO.getCurrentRow();
        if (cjConfigRow != null) {
            nidUserName =
                cjConfigRow.getAttribute("ValuesUrl") != null ? cjConfigRow.getAttribute("ValuesUrl").toString() : "";
        }

        ViewObject cjConfigVOO = ADFUtil.findIterator("CjConfigRoVOIterator").getViewObject();
        cjConfigVOO.setNamedWhereClauseParam("keyParam", "NID_PASSWORD");
        cjConfigVOO.executeQuery();
        System.err.println("cjConfigVO count 2 : " + cjConfigVOO.getEstimatedRowCount());
        cjConfigVOO.first();
        System.err.println("cjConfigVO count 2 : " + cjConfigVOO.getEstimatedRowCount());
        Row cjConfigRoww = cjConfigVOO.getCurrentRow();
        if (cjConfigRoww != null) {
            nidPassword =
                cjConfigRoww.getAttribute("ValuesUrl") != null ? cjConfigRoww.getAttribute("ValuesUrl").toString() : "";
        }
        
        //
        
        
        ViewObject cjConfigVO2 = ADFUtil.findIterator("CjConfigRoVOIterator").getViewObject();
        cjConfigVO2.setNamedWhereClauseParam("keyParam", "NID_INTEGRATION_STATUS");
        cjConfigVO2.executeQuery();
        System.err.println("cjConfigVO count 1 : " + cjConfigVO2.getEstimatedRowCount());
        cjConfigVO2.first();
        System.err.println("cjConfigVO count 1 : " + cjConfigVO2.getEstimatedRowCount());
        Row cjConfigRow2 = cjConfigVO2.getCurrentRow();
        if (cjConfigRow2 != null) {
            String nidStatus =
                cjConfigRow2.getAttribute("ValuesUrl") != null ? cjConfigRow2.getAttribute("ValuesUrl").toString() : "Y";
            JSFUtils.storeOnSession("nidStatus", nidStatus); 
        }
        
        ///////
        
        ViewObject cjConfigVO3 = ADFUtil.findIterator("CjConfigRoVOIterator").getViewObject();
        cjConfigVO3.setNamedWhereClauseParam("keyParam", "RULE_DISTRIBUTION_FLAG");
        cjConfigVO3.executeQuery();
        System.err.println("cjConfigVO count 3 : " + cjConfigVO3.getEstimatedRowCount());
        cjConfigVO3.first();
        System.err.println("cjConfigVO count 3 : " + cjConfigVO3.getEstimatedRowCount());
        Row cjConfigRow3 = cjConfigVO3.getCurrentRow();
        if (cjConfigRow3 != null) {
            String rdf =
                cjConfigRow3.getAttribute("ValuesUrl") != null ? cjConfigRow3.getAttribute("ValuesUrl").toString() : "Y";
           // JSFUtils.storeOnSession("RDF", rdf); 
        }
        
        
        
        //
        
        
        
        

        userInfo.setUserName(userName);
        userInfo.setUserId(userId);
        userInfo.setRoleId(roleId);
        userInfo.setEntityDegree(entityDegree);
        ADFContext.getCurrent().getSessionScope().put("UserEntDeg",new BigDecimal (entityDegree+""));
        userInfo.setAutomationFlag(automationFlag);
        userInfo.setLoginToken(loginToken);
        if (errandEntUser.equalsIgnoreCase("0"))
        userInfo.setEntityName(entityName);
        else
            userInfo.setEntityName(" \u0645\u0623\u0645\u0648\u0631\u064a\u0647 \u0625\u0633\u062a\u0626\u0646\u0627\u0641 "+entityName);
        userInfo.setFullName(fullName);
        userInfo.setErrandFlag(errandFlag);
        userInfo.setNidUserId(nidUserId);
        userInfo.setNidChannelId(nidChannelId);
        userInfo.setNidTokenId(nidTokenId);
        userInfo.setNidCallPermission(nidCallPermission);
        userInfo.setNidUserName(nidUserName);
        userInfo.setNidPassword(nidPassword);
        
        JSFUtils.storeOnSession("errandF", "N"); 

        if (entityDegree != null && entityDegree.equalsIgnoreCase("3")) {
            userInfo.setEntityId(entityId);
            ADFContext.getCurrent().getSessionScope().put("AgendaEnt",new BigDecimal (entityId+""));
            
            userInfo.setParentEntityId(parentEntityId);
            if (errandFlag.equalsIgnoreCase("Y")){
               
                JSFUtils.storeOnSession("errandEntity", entityId); 
                if (!excErrandEntityId.equalsIgnoreCase("0")){
                JSFUtils.storeOnSession("errandEntity2", excErrandEntityId); 
                System.out.println("QQQQQQQQQQ"+excErrandEntityId);
                }
                else{
                    JSFUtils.storeOnSession("errandEntity2", entityId); 
                    System.out.println("@@@@@@@@@@"+excErrandEntityId);
                }
                
                
                
                JSFUtils.storeOnSession("errandF", errandFlag); 
                
                
                System.out.println(JSFUtils.getFromSession("errandEntity2")+"errandEntityerrandEntityerrandEntityerrandEntity"+excErrandEntityId);
            }

            userInfo.setEntityAppeal(entityAppeal);
        } else if (entityDegree != null && entityDegree.equalsIgnoreCase("39")) {
            userInfo.setParentEntityId(entityId);
            JSFUtils.storeOnSession("errandEntity", entityId);
            JSFUtils.storeOnSession("errandEntity2", entityId);
            JSFUtils.storeOnSession("ClaimEntity", ""+entityId);
            userInfo.setEntityAppeal(parentEntityId);


        } else if (entityDegree != null && entityDegree.equalsIgnoreCase("38")) {
            userInfo.setEntityAppeal(entityId);
        }
        
        if (entityId.equalsIgnoreCase("87"))
            JSFUtils.storeOnSession("errandEntity2", entityId);
        System.err.println(userId+">>>> "+roleId+"---"+entityId);
        ViewObject secUserFunctionsView = ADFUtil.findIterator("SecUserFunctionsRVO1Iterator").getViewObject();
       /*Belal Abdalhuk*/
        VariableValueManager vm = secUserFunctionsView.ensureVariableManager();
        vm.setVariableValue("userIdParam", userId);
        vm.setVariableValue("roleIdParam", roleId);
//        secUserFunctionsView.setNamedWhereClauseParam("userIdParam", userId);
//        secUserFunctionsView.setNamedWhereClauseParam("roleIdParam", roleId);
        /* /Belal Abdalhuk*/
        secUserFunctionsView.executeQuery();
        
        System.err.println(">>>> "+secUserFunctionsView.getQuery());
        while (secUserFunctionsView.hasNext()) {
            Row secUserFunctionsRow = secUserFunctionsView.next();
            if (secUserFunctionsRow.getAttribute("FunctionId") != null) {
                userInfo.getUserPrevMap().put(secUserFunctionsRow.getAttribute("FunctionId").toString());
                /*Belal Abdalhuk*/
                if (secUserFunctionsRow.getAttribute("Url") != null &&
                    secUserFunctionsRow.getAttribute("Url").toString().startsWith("/faces"))
                    userInfo.getPagesHmap().put(secUserFunctionsRow.getAttribute("Url").toString(),
                                                Integer.parseInt(secUserFunctionsRow.getAttribute("FunctionId").toString()));
            }
            /* / Belal Abdalhuk*/
        }
        int counter =0;
            for (Map.Entry me : userInfo.getPagesHmap().entrySet()) {
                      counter++;
                    }
        System.err.println("count"+counter);
        ViewObject acceptedFormatsView = ADFUtil.findIterator("AcceptedFormatsROV1Iterator").getViewObject();
        Row row = acceptedFormatsView.first();
        List<String> formatsList = new ArrayList<String>();
        if (row != null && row.getAttribute("Name") != null) {
            formatsList.add(row.getAttribute("Name").toString());
        }
        while (acceptedFormatsView.hasNext()) {
            Row current = acceptedFormatsView.next();
            if (current != null && current.getAttribute("Name") != null) {
                formatsList.add(current.getAttribute("Name").toString());
            }
        }
        JSFUtils.storeOnSession("acceptedFormats", formatsList);
        System.err.println("welcome bean --> formatsList : " + formatsList);

        /**
        List<String> menus = new ArrayList<String>();
        DCIteratorBinding iter = ADFUtil.findIterator("MenuRolesROVIterator");
        iter.executeQuery();
        RowSetIterator rowIter = iter.getViewObject().createRowSetIterator("");
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            String menuUrl = (String) row.getAttribute("Url");
            menus.add(menuUrl);
        }
        //        JSFUtils.storeOnSession("menusList", menus);

        ViewObject userRolesView = ADFUtil.findIterator("UserRolesRVO1Iterator").getViewObject();
        userRolesView.setNamedWhereClauseParam("roleIdBind", roleId);
        userRolesView.setNamedWhereClauseParam("userIdBind", userId);
        userRolesView.executeQuery();
        long userRolesViewCount = userRolesView.getEstimatedRowCount();
        ArrayList roles = new ArrayList();
        RowSetIterator rsi = userRolesView.createRowSetIterator(null);
        for (int i = 0; i < userRolesViewCount; i++) {
            Row row = rsi.next();
            roles.add(row.getAttribute("FunctionId"));
        }
        //        JSFUtils.storeOnSession("roles", roles);
**/


        JSFUtils.storeOnSession("simplifiedMode", new SimplifiedMode());
        JSFUtils.storeOnSession("isUserSignedIn", true);


        if (ADFUtil.getBoundAttributeValue("FirstName") != null) {
            firstName = ADFUtil.getBoundAttributeValue("FirstName").toString();
        }

        userInfo.setFirstName(firstName);

        JSFUtils.storeOnSession("userInfo", userInfo);

        welcomeText = ADFUtil.evaluateEL("#{bundle.WELCOME}") + " " + firstName;

        System.err.println("--X-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidUserId}"));
        System.err.println("--Y-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidChannelId}"));
        System.err.println("--XX-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidTokenId}"));
        System.err.println("--YY-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidCallPermission}"));
        System.err.println("--XXX-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidUserName}"));
        System.err.println("--YYY-W--> " + ADFUtil.evaluateEL("#{sessionScope.userInfo.nidPassword}"));

    }

}
