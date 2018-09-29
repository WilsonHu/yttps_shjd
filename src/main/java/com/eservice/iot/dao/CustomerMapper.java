package com.eservice.iot.dao;

import com.eservice.iot.core.Mapper;
import com.eservice.iot.model.customer.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper extends Mapper<Customer> {
    List<Customer> search(@Param("customer_name")String cuatomerName);
}