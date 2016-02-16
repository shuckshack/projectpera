/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gkh.sample.git.simplewebapp.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gkh.sample.git.simplewebapp.service.SimpleService;
import org.gkh.sample.git.simplewebapp.model.bo.Customer;
import org.gkh.sample.git.simplewebapp.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author hepgk
 */
@Component
public class CustomerService implements SimpleService {

    static final Logger LOG = LogManager.getLogger(CustomerService.class.getName());

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void testRun() {
        LOG.debug("testRun()");
        Customer[] testCustomers = createTestData();
        insertTestData(testCustomers);
        readTestData();
        deleteTestData(testCustomers);
        readTestData();
    }

    public Customer[] createTestData() {
        LOG.debug("createTestData()");
        Customer[] testCustomers = new Customer[2];
        testCustomers[0] = new Customer("Gene", "Heptonstall");
        testCustomers[1] = new Customer("Elemelie", "Heptonstall");
        return testCustomers;
    }

    public void insertTestData(Customer[] testCustomers) {
        LOG.debug("insertTestData()");
        for (Customer cust : testCustomers) {
            customerRepository.insert(cust);
        }
    }

    public void readTestData() {
        LOG.debug("readTestData()");
        List<Customer> customerList = customerRepository.findAll();
        customerList.stream().forEach((customer) -> {
            LOG.debug(customer);
        });
    }

    public void deleteTestData(Customer[] testCustomers) {
        LOG.debug("deleteTestData()");
        for (Customer cust : testCustomers) {
            customerRepository.delete(cust);
        }
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

}
