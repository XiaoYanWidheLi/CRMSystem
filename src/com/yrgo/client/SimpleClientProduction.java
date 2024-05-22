
package com.yrgo.client;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.calls.CallHandlingService;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerManagementServiceProductionImpl;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

@Transactional
public class SimpleClientProduction {

    public static void main(String[] args) throws CustomerNotFoundException {
        //ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application-annotation.xml", "application.xml");
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application-annotation2.xml", "application.xml");
        //ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

        //CustomerManagementService customerService = container.getBean(CustomerManagementServiceProductionImpl.class);
        CustomerManagementService customerService = (CustomerManagementService) container.getBean("customerManagementServiceProductionImpl");

        customerService.newCustomer(new Customer("OB74", "Fargo Ltd", "some notes"));
        customerService.newCustomer(new Customer("AA33", "IKEA", "They have the best toilet brush"));
        customerService.newCustomer(new Customer("A836", "IKEA", "They have a lot of good stuff"));


        CallHandlingService callService = container.getBean(CallHandlingService.class);

        callService.recordCall("OB74", new Call("Dom called from Twin Peaks Company"));
        callService.recordCall("OB74", new Call("Mike called from Twin Peaks Company"));
        callService.recordCall("AA33", new Call("IKEA need to build more elevators"));

        //newCall = new Call("Dom called from Twin Peaks Company",new GregorianCalendar(2019,12,10));
        Customer customer = customerService.getFullCustomerDetail("OB74");
        System.out.println(customer);
        List<Call> calls = customer.getCalls();
        for (Call call:calls) {
            System.out.println(call);

        }
        container.close();
    }
}