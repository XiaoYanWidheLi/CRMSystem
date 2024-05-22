package com.yrgo.dataaccess;

import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao {

	void create(Customer customer);

	Customer getById(String customerId) throws RecordNotFoundException;

	List<Customer> getByName(String name);

	void update(Customer customerToUpdate) throws RecordNotFoundException;

	void delete(Customer oldCustomer) throws RecordNotFoundException;

	List<Customer> getAllCustomers();


	Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException;


	void addCall (Call newCall, String customerId) throws RecordNotFoundException;
}
