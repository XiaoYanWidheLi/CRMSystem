package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class JpaCustomerDao implements CustomerDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void create(Customer customer) {
        System.out.println("using jpa");
        em.persist(customer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException("Customer not found for id: " + customerId);
        }
        return customer;
    }

    @Override
    public List<Customer> getByName(String name) {
        return null;
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {

    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {

    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }


    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        return null;
    }

    @Override
    @Transactional
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = getById(customerId);
        customer.addCall(newCall);
        em.merge(customer);
    }
}