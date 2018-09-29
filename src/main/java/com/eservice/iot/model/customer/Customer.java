package com.eservice.iot.model.customer;

import java.util.Date;
import javax.persistence.*;

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 客户名称（一般为企业名称）
     */
    @Column(name = "customer_name")
    private String customerName;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取客户名称（一般为企业名称）
     *
     * @return customer_name - 客户名称（一般为企业名称）
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * 设置客户名称（一般为企业名称）
     *
     * @param customerName 客户名称（一般为企业名称）
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}