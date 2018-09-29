package com.eservice.iot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 人脸在某个设备上出现的记录
 * @author HT
 */
public class FaceOnDeviceRecord {
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("face_image_id")
    private String faceImageId;
    @JsonProperty("scene_image_id")
    private String sceneImageId;
    @JsonProperty("timestamp")
    private Integer timestamp;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFaceImageId() {
        return faceImageId;
    }

    public void setFaceImageId(String faceImageId) {
        this.faceImageId = faceImageId;
    }

    public String getSceneImageId() {
        return sceneImageId;
    }

    public void setSceneImageId(String sceneImageId) {
        this.sceneImageId = sceneImageId;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}