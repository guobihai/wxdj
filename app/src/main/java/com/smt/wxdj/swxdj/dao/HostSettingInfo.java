package com.smt.wxdj.swxdj.dao;

public class HostSettingInfo {

    /**
     * name : 本地开发
     * authUrl : http://192.168.3.29:64999
     * apiUrl : http://192.168.3.29:65225
     * valid : A
     * validDesc : 有效
     * rowVersion : 2021-05-25T14:52:14.688227
     * concurrencyStamp : 67f44804de4249b3863319f63c02bb84
     * isDeleted : false
     * deleterId : null
     * deletionTime : null
     * lastModificationTime : null
     * lastModifierId : null
     * creationTime : 2021-05-25T14:52:14.732503
     * creatorId : 39f838d9-ea90-8d9b-1b49-f035918e21d1
     * id : 39fcb478-73df-807f-22fa-af567fadc83d
     */

    private String name;
    private String authUrl;
    private String apiUrl;
    private String valid;
    private String validDesc;
    private String rowVersion;
    private String concurrencyStamp;
    private boolean isDeleted;
    private Object deleterId;
    private Object deletionTime;
    private Object lastModificationTime;
    private Object lastModifierId;
    private String creationTime;
    private String creatorId;
    private String id;

    private int index;
    boolean select;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getValidDesc() {
        return validDesc;
    }

    public void setValidDesc(String validDesc) {
        this.validDesc = validDesc;
    }

    public String getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(String rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getConcurrencyStamp() {
        return concurrencyStamp;
    }

    public void setConcurrencyStamp(String concurrencyStamp) {
        this.concurrencyStamp = concurrencyStamp;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getDeleterId() {
        return deleterId;
    }

    public void setDeleterId(Object deleterId) {
        this.deleterId = deleterId;
    }

    public Object getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Object deletionTime) {
        this.deletionTime = deletionTime;
    }

    public Object getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Object lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public Object getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Object lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
