package com.eservice.iot.model;

/**
 * Class Description: IoT unified date structure
 *
 * @author Wilson Hu
 * @date 6/27/2018
 */
public class IoTDataModel {
    /**
     * 参数名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
