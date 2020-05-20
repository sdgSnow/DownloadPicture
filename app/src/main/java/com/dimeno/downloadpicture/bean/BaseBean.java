package com.dimeno.downloadpicture.bean;

public class BaseBean {

    private String url;//文件下载路径

    private String filename;//文件名

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
