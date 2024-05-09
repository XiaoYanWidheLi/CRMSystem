
package com.yrgo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import com.yrgo.services.customers.CustomerManagementServiceProductionImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.calls.CallHandlingService;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

        CustomerManagementService customerService = container.getBean(CustomerManagementServiceProductionImpl.class);
        DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

        customerService.newCustomer(new Customer("OB74", "Fargo Ltd", "some notes"));

        CallHandlingService callService = container.getBean(CallHandlingService.class);

        Call newCall = new Call("Dom called from Twin Peaks Company");
        Action action1 = new Action ("Call back Dom as soon as possible for feedback",
                new GregorianCalendar(2019,12,10), "user");
        Action action2 = new Action ("Check if Dom called again",
                new GregorianCalendar(2019,12,11), "user");
        List<Action>actions = new ArrayList<Action>();
        actions.add(action1);
        actions.add(action2);

        try {
            callService.recordCall("OB74", newCall, actions);
        }catch(CustomerNotFoundException e) {
            //System.err.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Here are the actions:");
        Collection<Action>incompleteActions = diaryService.getAllIncompleteActions("user");
        for(Action action:incompleteActions) {
            System.out.println(action);
        }

        container.close();
    }
}