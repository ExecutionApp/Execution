package com.egabi.cjexec.view.beans.security;

import com.egabi.cjexec.model.baseclasses.SimplifiedMode;

import java.util.HashMap;
import java.util.Map;

/** Added by M.Salah **/
public class UserInfo {

    public UserInfo() {
        super();
    }

    private String userName;
    private String entityId;
    private String parentEntityId;
    private String entityDegree;
    private String roleId;
    private String userId;
    private String loginToken;
    private String automationFlag;
    private String firstName;
    private String entityName;
    private String fullName;
    private String entityAppeal;
    private String errandFlag ;
    private String errandEntUser;
    private String nidUserId = null;
    private String nidChannelId = null;
    private String nidTokenId = null;
    private String nidCallPermission = null;
    private String nidUserName = null;
    private String nidPassword = null;

    private HashMap<String, Integer> pagesHmap = new HashMap<String, Integer>();
    private UserPrevMap userPrevMap = new UserPrevMap();


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUserPrevMap(UserPrevMap userPrevMap) {
        this.userPrevMap = userPrevMap;
    }

    public UserPrevMap getUserPrevMap() {
        return userPrevMap;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setParentEntityId(String parentEntityId) {
        this.parentEntityId = parentEntityId;
    }

    public String getParentEntityId() {
        return parentEntityId;
    }

    public void setEntityDegree(String entityDegree) {
        this.entityDegree = entityDegree;
    }

    public String getEntityDegree() {
        return entityDegree;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setPagesHmap(HashMap<String, Integer> pagesHmap) {
        this.pagesHmap = pagesHmap;
    }

    public HashMap<String, Integer> getPagesHmap() {
        return pagesHmap;
    }

    public void setEntityAppeal(String entityAppeal) {
        this.entityAppeal = entityAppeal;
    }

    public String getEntityAppeal() {
        return entityAppeal;
    }

    public void setNidUserId(String nidUserId) {
        this.nidUserId = nidUserId;
    }

    public String getNidUserId() {
        return nidUserId;
    }

    public void setNidChannelId(String nidChannelId) {
        this.nidChannelId = nidChannelId;
    }

    public String getNidChannelId() {
        return nidChannelId;
    }

    public void setNidTokenId(String nidTokenId) {
        this.nidTokenId = nidTokenId;
    }

    public String getNidTokenId() {
        return nidTokenId;
    }

    public void setNidCallPermission(String nidCallPermission) {
        this.nidCallPermission = nidCallPermission;
    }

    public String getNidCallPermission() {
        return nidCallPermission;
    }

    public void setNidUserName(String nidUserName) {
        this.nidUserName = nidUserName;
    }

    public String getNidUserName() {
        return nidUserName;
    }

    public void setNidPassword(String nidPassword) {
        this.nidPassword = nidPassword;
    }

    public String getNidPassword() {
        return nidPassword;
    }

    public void setErrandFlag(String errandFlag) {
        this.errandFlag = errandFlag;
    }

    public String getErrandFlag() {
        return errandFlag;
    }

    public void setErrandEntUser(String errandEntUser) {
        this.errandEntUser = errandEntUser;
    }

    public String getErrandEntUser() {
        return errandEntUser;
    }

    public void setAutomationFlag(String automationFlag) {
        this.automationFlag = automationFlag;
    }

    public String getAutomationFlag() {
        return automationFlag;
    }
}
