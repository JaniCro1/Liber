package com.libers.unnc.liber.model.bean;

/**
 * Created by a11 on 17/2/28.
 */

public class TagItem {
    private String tag;
    private String content;

    public TagItem(String tag, String content) {
        this.tag = tag;
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
