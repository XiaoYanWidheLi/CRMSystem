package com.yrgo.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Transactional
@Repository
public class CustomerDaoJdbcTemplateImpl implements CustomerDao {

    private static final String CREATE_TABLE_CUSTOMER_SQL =
            "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "CUSTOMER_ID VARCHAR(50) PRIMARY KEY, " +
            "COMPANY_NAME VARCHAR(255), " +
            "NOTES VARCHAR(255))";
    private static final String CREATE_TABLE_CALLS_SQL =
            "CREATE TABLE IF NOT EXISTS CALLS (" +
            "CALL_ID IDENTITY PRIMARY KEY, " +
            "CUSTOMER_ID VARCHAR(50), " +
            "NOTES VARCHAR(255), " +
            "CALL_DATE DATE, " +
            "FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID))";
    private static final String CREATE_SQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, COMPANY_NAME, NOTES) VALUES (?, ?, ?)";
    private static final String GET_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private static final String GET_BY_NAME_SQL = "SELECT * FROM CUSTOMER WHERE COMPANY_NAME LIKE ?";
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET COMPANY_NAME = ?, NOTES = ? WHERE CUSTOMER_ID = ?";
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private static final String GET_ALL_SQL = "SELECT * FROM CUSTOMER";
    private static final String GET_FULL_DETAIL_SQL = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private static final String ADD_CALL_SQL = "INSERT INTO CALLS (CUSTOMER_ID, NOTES, CALL_DATE) VALUES (?, ?, ?)";
    private static final String GET_FULL_CALLS_SQL = "SELECT * FROM CALLS WHERE CUSTOMER_ID = ?";


    private JdbcTemplate jdbcTemplate;

   @Autowired
    public CustomerDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTables(){
            try {
        // Create CUSTOMER table
        this.jdbcTemplate.update(CREATE_TABLE_CUSTOMER_SQL);

        // Create CALL table
        this.jdbcTemplate.update(CREATE_TABLE_CALLS_SQL);
    } catch (org.springframework.jdbc.BadSqlGrammarException e) {
        System.out.println(e);
    }
}
    @Override
    public void create(Customer customer) {
        jdbcTemplate.update(CREATE_SQL, customer.getCustomerId(), customer.getCompanyName(), customer.getEmail());
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {

            List<Customer> customers = jdbcTemplate.query(GET_BY_ID_SQL, new CustomerRowMapper(), customerId);

            if (customers.isEmpty()) {
               throw new RecordNotFoundException("Customer not found with ID: " + customerId);
            }
            return customers.get(0);
    }

    @Override
    public List<Customer> getByName(String name) {
        return jdbcTemplate.query(GET_BY_NAME_SQL, new CustomerRowMapper(), "%" + name + "%");
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        int updatedRows = jdbcTemplate.update(UPDATE_SQL, customerToUpdate.getCustomerId(), customerToUpdate.getEmail(), customerToUpdate.getCustomerId());
        if (updatedRows == 0) {
            throw new RecordNotFoundException("Customer not found with ID: " + customerToUpdate.getCustomerId());
        }
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        int deletedRows = jdbcTemplate.update(DELETE_SQL, oldCustomer.getCustomerId());
        if (deletedRows == 0) {
            throw new RecordNotFoundException("Customer not found with ID: " + oldCustomer.getCustomerId());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return jdbcTemplate.query(GET_ALL_SQL, new CustomerRowMapper());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer customer = getById(customerId);
        List<Call> calls = jdbcTemplate.query(GET_FULL_CALLS_SQL, new CallRowMapper(), customerId);
        customer.setCalls(calls);

        return customer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        try {
            Customer customer = getById(customerId);
            jdbcTemplate.update(ADD_CALL_SQL, customer.getCustomerId(), newCall.getNotes(), newCall.getTimeAndDate());
            update(customer);
        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("Customer not found with ID:" + customerId);
        }
    }
}

class CustomerRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String customerId = rs.getString("CUSTOMER_ID");
        String name = rs.getString("COMPANY_NAME");
        String notes = rs.getString("NOTES");
        return new Customer(customerId, name, notes);
    }
}

class CallRowMapper implements RowMapper<Call> {
    @Override
    public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
        String notes = rs.getString("NOTES");
        Timestamp timeAndDate = rs.getTimestamp("CALL_DATE");
        return new Call(notes, timeAndDate);
    }
}
