package com.project.entity;

import java.util.Date;

public class ToDo {
    String uuid;
    String content;
    String userName;
    Date createdTime;
    Date updatedTime;

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public ToDo(String uuid, String content, String userName, Date createdTime, Date updatedTime) {
        this.uuid = uuid;
        this.content = content;
        this.userName = userName;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public ToDo() {
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
