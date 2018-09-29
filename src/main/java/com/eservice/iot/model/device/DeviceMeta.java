package com.eservice.iot.model.device;

public class DeviceMeta {


    /**
     * device_name : string
     * password : string
     * map_id : string
     * port : 0
     * enable : true
     * ip : string
     * docking_config : object
     * location : string
     * position : {"x":0,"y":0}
     * type : string
     * direction_type : 0
     * username : string
     */
    private String device_name;
    private String password;
    private String map_id;
    private int port;
    private boolean enable;
    private String ip;
    private String docking_config;
    private String location;
    private Position position;
    private String type;
    private int direction_type;
    private String username;

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setDocking_config(String docking_config) {
        this.docking_config = docking_config;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDirection_type(int direction_type) {
        this.direction_type = direction_type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getPassword() {
        return password;
    }

    public String getMap_id() {
        return map_id;
    }

    public int getPort() {
        return port;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getIp() {
        return ip;
    }

    public String getDocking_config() {
        return docking_config;
    }

    public String getLocation() {
        return location;
    }

    public Position getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public int getDirection_type() {
        return direction_type;
    }

    public String getUsername() {
        return username;
    }
}
