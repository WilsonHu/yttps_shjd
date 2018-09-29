package com.eservice.iot.model;

import java.util.List;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 2018/9/7
 */
public class Condition {

    /**
     * type : 2
     * repo_id : {"$in":[100003,100005]}
     * create_timestamp : {"$gte":1536274800}
     */

    private int type;
    private RepoIdBean repo_id;
    private CreateTimestampBean create_timestamp;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public RepoIdBean getRepo_id() {
        return repo_id;
    }

    public void setRepo_id(RepoIdBean repo_id) {
        this.repo_id = repo_id;
    }

    public CreateTimestampBean getCreate_timestamp() {
        return create_timestamp;
    }

    public void setCreate_timestamp(CreateTimestampBean create_timestamp) {
        this.create_timestamp = create_timestamp;
    }
}
