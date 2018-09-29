package com.eservice.iot.service.impl;

import com.eservice.iot.dao.CustomerMapper;
import com.eservice.iot.model.customer.Customer;
import com.eservice.iot.service.CustomerService;
import com.eservice.iot.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
* Class Description: xxx
* @author Wilson Hu
* @date 2018/08/21.
*/
@Service
@Transactional
public class CustomerServiceImpl extends AbstractService<Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;

    public List<Customer> search(String customerName) {
        return customerMapper.search(customerName);
    }
}
