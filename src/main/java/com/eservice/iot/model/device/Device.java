package com.eservice.iot.model.device;

public class Device {

    /**
     * device_status : {"last_capture_timestamp":0,"last_status":"string","last_heart_beat_timestamp":0,"device_add_time":0,"status":"string"}
     * device_id : string
     * device_meta : {"device_name":"string","password":"string","map_id":"string","port":0,"enable":true,"ip":"string","docking_config":"object","location":"string","position":{"x":0,"y":0},"type":"string","direction_type":0,"username":"string"}
     * docking_id : string
     */
    private DeviceStatus device_status;
    private String device_id;
    private DeviceMeta device_meta;
    private String docking_id;

    public void setDevice_status(DeviceStatus device_status) {
        this.device_status = device_status;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setDevice_meta(DeviceMeta device_meta) {
        this.device_meta = device_meta;
    }

    public void setDocking_id(String docking_id) {
        this.docking_id = docking_id;
    }

    public DeviceStatus getDevice_status() {
        return device_status;
    }

    public String getDevice_id() {
        return device_id;
    }

    public DeviceMeta getDevice_meta() {
        return device_meta;
    }

    public String getDocking_id() {
        return docking_id;
    }
}
