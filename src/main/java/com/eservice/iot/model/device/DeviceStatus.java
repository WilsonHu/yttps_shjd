package com.eservice.iot.model.device;

public class DeviceStatus {

    /**
     * last_capture_timestamp : 0
     * last_status : string
     * last_heart_beat_timestamp : 0
     * device_add_time : 0
     * status : string
     */
    private int last_capture_timestamp;
    private String last_status;
    private int last_heart_beat_timestamp;
    private int device_add_time;
    private String status;

    public void setLast_capture_timestamp(int last_capture_timestamp) {
        this.last_capture_timestamp = last_capture_timestamp;
    }

    public void setLast_status(String last_status) {
        this.last_status = last_status;
    }

    public void setLast_heart_beat_timestamp(int last_heart_beat_timestamp) {
        this.last_heart_beat_timestamp = last_heart_beat_timestamp;
    }

    public void setDevice_add_time(int device_add_time) {
        this.device_add_time = device_add_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLast_capture_timestamp() {
        return last_capture_timestamp;
    }

    public String getLast_status() {
        return last_status;
    }

    public int getLast_heart_beat_timestamp() {
        return last_heart_beat_timestamp;
    }

    public int getDevice_add_time() {
        return device_add_time;
    }

    public String getStatus() {
        return status;
    }
}
