package com.eservice.iot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 考勤记录
 * @author HT
 */
public class AttendanceRecord {
    @JsonProperty("date_str")
    private String dateStr;
    @JsonProperty("last")
    private FaceOnDeviceRecord last;
    @JsonProperty("person")
    private Person person;
    @JsonProperty("attendance_policy_id")
    private String attendancePolicyId;
    @JsonProperty("first")
    private FaceOnDeviceRecord first;
    @JsonProperty("timestamp")
    private Integer timestamp;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public FaceOnDeviceRecord getLast() {
        return last;
    }

    public void setLast(FaceOnDeviceRecord last) {
        this.last = last;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getAttendancePolicyId() {
        return attendancePolicyId;
    }

    public void setAttendancePolicyId(String attendancePolicyId) {
        this.attendancePolicyId = attendancePolicyId;
    }

    public FaceOnDeviceRecord getFirst() {
        return first;
    }

    public void setFirst(FaceOnDeviceRecord first) {
        this.first = first;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }
}