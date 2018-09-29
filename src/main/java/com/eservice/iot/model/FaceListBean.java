package com.eservice.iot.model;

/**
 * Class Description:
 *
 * @author Wilson Hu
 * @date 8/15/2018
 */
public class FaceListBean {
    /**
     * face_image_id : 5b71cfd4a628820cde1e4216
     * scene_image_id : 5b71cfd4a628820cde1e4215
     * face_id : 9204231738438451209
     */

    private String face_image_id;
    private String scene_image_id;
    private String face_id;

    public String getFace_image_id() {
        return face_image_id;
    }

    public void setFace_image_id(String face_image_id) {
        this.face_image_id = face_image_id;
    }

    public String getScene_image_id() {
        return scene_image_id;
    }

    public void setScene_image_id(String scene_image_id) {
        this.scene_image_id = scene_image_id;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }
}
