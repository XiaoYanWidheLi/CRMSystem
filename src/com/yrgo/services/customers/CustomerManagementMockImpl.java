package com.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockImpl() {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
        customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
        customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
    }

    @Override
    public void newCustomer(Customer newCustomer) {
        customerMap.put(newCustomer.getCustomerId(), newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        customerMap.put(changedCustomer.getCustomerId(),changedCustomer);
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        customerMap.remove(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws com.yrgo.services.customers.CustomerNotFoundException {

        if (customerMap.get(customerId) == null) {

            throw new com.yrgo.services.customers.CustomerNotFoundException();
        }

        return customerMap.get(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        List<Customer> customersWithSameName = new ArrayList<>();

        for (Customer customer : customerMap.values()) {
            if (customer.getCompanyName().equals(name)) {
                customersWithSameName.add(customer);
            }
        }
        return customersWithSameName;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws com.yrgo.services.customers.CustomerNotFoundException {

        if (customerMap.get(customerId) == null) {
            throw new com.yrgo.services.customers.CustomerNotFoundException();
        }
        return customerMap.get(customerId);
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws com.yrgo.services.customers.CustomerNotFoundException {
        if (customerMap.get(customerId) == null) {
            throw new com.yrgo.services.customers.CustomerNotFoundException();
        }
        //First find the customer
        Customer customer = customerMap.get(customerId);
        //Call the addCall on the customer
        customer.addCall(callDetails);
    }

}
