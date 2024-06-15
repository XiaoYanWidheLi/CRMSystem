package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.awt.print.Book;
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
        return em.createQuery("select customer from Customer as customer where customer.name:=name")
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        if (em.find(Customer.class, customerToUpdate.getCustomerId()) == null) {
            throw new RecordNotFoundException("Customer not found");
        }
        else {
            em.merge(customerToUpdate);
        }
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        try {
            em.remove(oldCustomer);
        }
        catch (javax.persistence.EntityNotFoundException e) {
            throw new RecordNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("select customer from Customer as customer", Customer.class).getResultList();
    }


    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer customer = (Customer) em.createQuery("select customer from Customer as customer left join fetch customer.calls where customer.customerId=:customerId")
                .setParameter("customerId",customerId)
                .getSingleResult();
        return customer;
    }

    @Override
    @Transactional
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = getById(customerId);
        customer.addCall(newCall);
        em.merge(customer);
    }
}