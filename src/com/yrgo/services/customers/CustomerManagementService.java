package com.yrgo.services.customers;

import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerManagementService {

	void newCustomer(Customer newCustomer);


	void updateCustomer(Customer changedCustomer);


	void deleteCustomer(Customer oldCustomer);


	Customer findCustomerById(String customerId) throws CustomerNotFoundException;

	List<Customer> findCustomersByName (String name);

	List<Customer> getAllCustomers();


	Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException;

	
	void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;
}