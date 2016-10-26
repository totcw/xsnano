package com.betterda.xsnano.goods.model;

/**
 * Created by Administrator on 2016/5/17.
 */
public class Comment {
    private String url;  //图片路径
    private String name; //用户名
    private String time;//时间
    private String content;//内容
    private int star;

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
