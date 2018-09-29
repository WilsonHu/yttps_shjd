package com.eservice.iot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 员工modal
 *
 * @author HT
 */
public class Staff {
    @JsonProperty("tag_id_list")
    private List<String> tagIdList;
    @JsonProperty("upload_time")
    private Integer uploadTime;
    @JsonProperty("person_information")
    private PersonInformation personInformation;
    @JsonProperty("face_list")
    private List<FaceListBean> face_list;
    @JsonProperty("identity")
    private String identity;
    @JsonProperty("meta")
    private String meta;
    @JsonProperty("scene_image_id")
    private String sceneImageId;
    @JsonProperty("staff_id")
    private String staffId;

    public List<String> getTag_id_list() {
        return tagIdList;
    }

    public void setTagIdList(List<String> tagIdList) {
        this.tagIdList = tagIdList;
    }

    public Integer getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Integer uploadTime) {
        this.uploadTime = uploadTime;
    }

    public PersonInformation getPersonInformation() {
        return personInformation;
    }

    public void setPersonInformation(PersonInformation personInformation) {
        this.personInformation = personInformation;
    }

    public List<FaceListBean> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListBean> face_list) {
        this.face_list = face_list;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getSceneImageId() {
        return sceneImageId;
    }

    public void setSceneImageId(String sceneImageId) {
        this.sceneImageId = sceneImageId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    /**
     * 目前判断相同的条件是人名、电话都不变
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Staff) {
            Staff person = (Staff) obj;
            return (person.personInformation.getName().equals(personInformation.getName())
                        && person.personInformation.getPhone().equals(personInformation.getPhone()));
        } else {
            return super.equals(obj);

        }
    }
}