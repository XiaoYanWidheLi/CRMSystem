package com.yrgo.services.customers;

import com.yrgo.dataaccess.CustomerDao;
import com.yrgo.dataaccess.RecordNotFoundException;
import com.yrgo.domain.Customer;

import java.util.List;
import com.yrgo.domain.Call;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
@Qualifier("CustomerManagementServiceProductionImpl")
public class CustomerManagementServiceProductionImpl implements CustomerManagementService {



    private CustomerDao customerDao;

    // Setter method for injecting CustomerDao

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerDao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        try {
            customerDao.update(changedCustomer);
        } catch (RecordNotFoundException e) {
            // Handle exception
            e.printStackTrace();
        }
    }


    @Override
    public void deleteCustomer(Customer oldCustomer) {
        try {
            customerDao.delete(oldCustomer);
        } catch (RecordNotFoundException e) {
            // Handle exception
            e.printStackTrace();
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        try {
            return customerDao.getById(customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        return customerDao.getByName(name);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }


    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        // Assuming CustomerDao implementation provides a method to get full customer detail
        try {
            Customer customer = customerDao.getFullCustomerDetail(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException();
            }
            return customer;
        } catch (RecordNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        try {
            customerDao.addCall(callDetails, customerId);
        } catch (RecordNotFoundException e) {
            throw new CustomerNotFoundException(e.getMessage());
        }
    }
}