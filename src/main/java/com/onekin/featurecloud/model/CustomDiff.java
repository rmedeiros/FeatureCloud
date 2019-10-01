package com.onekin.featurecloud.model;

public class CustomDiff {

    private String type;
    private String content;
    private String path;

    public CustomDiff(String type, String content, String path) {
        super();
        this.type = type;
        this.content = content;
        this.path = path;
    }

    public CustomDiff() {
        super();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "CustomDiff [type=" + type + ", content=" + content + ", path=" + path + "]";
    }

}
