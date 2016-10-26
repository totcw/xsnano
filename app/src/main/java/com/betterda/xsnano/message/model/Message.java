package com.betterda.xsnano.message.model;

/**
 * Created by lyf
 */
public class Message {
    private String name; //消息名
    private String content;//消息内容
    private boolean isRead; //是否读过

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
