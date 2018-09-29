package com.eservice.iot.model;

import java.util.List;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 8/15/2018
 */
public class Visitor {

    /**
     * face_list : [{"face_id":"string","face_image_id":"string","scene_image_id":"string"}]
     * identity : STAFF
     * last_visiting_time : 0
     * meta : {}
     * person_information : {"card_no":"string","company":"string","identity_number":"string","name":"string","phone":"string","visit_end_timestamp":0,"visit_purpose":"0","visit_start_timestamp":0,"visit_time_type":"0","visitee_name":"string"}
     * tag_id_list : ["string"]
     * upload_time : 0
     * visiting_counts : 0
     * visitor_id : string
     */

    private String identity;
    private int last_visiting_time;
    private String meta;
    private PersonInformation person_information;
    private int upload_time;
    private int visiting_counts;
    private String visitor_id;
    private List<FaceListBean> face_list;
    private List<String> tag_id_list;

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getLast_visiting_time() {
        return last_visiting_time;
    }

    public void setLast_visiting_time(int last_visiting_time) {
        this.last_visiting_time = last_visiting_time;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public PersonInformation getPerson_information() {
        return person_information;
    }

    public void setPerson_information(PersonInformation person_information) {
        this.person_information = person_information;
    }

    public int getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(int upload_time) {
        this.upload_time = upload_time;
    }

    public int getVisiting_counts() {
        return visiting_counts;
    }

    public void setVisiting_counts(int visiting_counts) {
        this.visiting_counts = visiting_counts;
    }

    public String getVisitor_id() {
        return visitor_id;
    }

    public void setVisitor_id(String visitor_id) {
        this.visitor_id = visitor_id;
    }

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }

    public List<String> getTag_id_list() {
        return tag_id_list;
    }

    public void setTag_id_list(List<String> tag_id_list) {
        this.tag_id_list = tag_id_list;
    }

    /**
     * 目前判断相同的条件是人名、电话都不变
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Visitor) {
            Visitor person = (Visitor) obj;
            return (person.getPerson_information().getName().equals(person_information.getName())
                    && person.getPerson_information().getPhone().equals(person_information.getPhone()));
        } else {
            return super.equals(obj);

        }
    }
}
