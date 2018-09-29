package com.eservice.iot.model;

import java.util.List;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 8/15/2018
 */
public class Tag {

    /**
     * count : 0
     * create_time : 0
     * meta : {}
     * tag_id : string
     * tag_name : string
     * visible_identity : ["STAFF"]
     */

    private int count;
    private int create_time;
    private String meta;
    private String tag_id;
    private String tag_name;
    private List<String> visible_identity;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public List<String> getVisible_identity() {
        return visible_identity;
    }

    public void setVisible_identity(List<String> visible_identity) {
        this.visible_identity = visible_identity;
    }
}
