package com.eservice.iot.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 人员详细信息
 * @author HT
 */
public class PersonInformation {
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("visit_purpose")
    private String visit_purpose;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("identity_number")
    private String identity_number;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("name")
    private String name;
    @JsonProperty("company")
    private String company;
    @JsonProperty("id")
    private String id;
    @JsonProperty("visitee_name")
    private String visitee_name;
    @JsonProperty("employed_date")
    private String employed_date;
    @JsonProperty("visit_end_timestamp")
    private Long visit_end_timestamp;
    @JsonProperty("visit_start_timestamp")
    private Long visit_start_timestamp;
    @JsonProperty("visit_time_type")
    private String visit_time_type;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getVisit_purpose() {
        return visit_purpose;
    }

    public void setVisit_purpose(String visitPurpose) {
        this.visit_purpose = visitPurpose;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identityNumber) {
        this.identity_number = identityNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitee_name() {
        return visitee_name;
    }

    public void setVisitee_name(String visiteeName) {
        this.visitee_name = visiteeName;
    }

    public String getEmployed_date() {
        return employed_date;
    }

    public void setEmployed_date(String employedDate) {
        this.employed_date = employedDate;
    }

    public Long getVisit_end_timestamp() {
        return visit_end_timestamp;
    }

    public void setVisit_end_timestamp(Long visitEndTimestamp) {
        this.visit_end_timestamp = visitEndTimestamp;
    }

    public Long getVisit_start_timestamp() {
        return visit_start_timestamp;
    }

    public void setVisit_start_timestamp(Long visitStartTimestamp) {
        this.visit_start_timestamp = visitStartTimestamp;
    }

    public String getVisit_time_type() {
        return visit_time_type;
    }

    public void setVisit_time_type(String visitTimeType) {
        this.visit_time_type = visitTimeType;
    }
}