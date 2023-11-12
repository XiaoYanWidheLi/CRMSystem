package com.yrgo.services.calls;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {

    private final CustomerManagementService customerManagementService;
    private final DiaryManagementService diaryManagementService;

    // 构造函数注入依赖项
    public CallHandlingServiceImpl(CustomerManagementService customerManagementService, DiaryManagementService diaryManagementService) {
        this.customerManagementService = customerManagementService;
        this.diaryManagementService = diaryManagementService;
    }
    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        // 调用CustomerManagementService的recordCall方法
        customerManagementService.recordCall(customerId, newCall);

        // 调用DiaryManagementService的recordActions方法
       for (Action action : actions) {
            diaryManagementService.recordAction(action);
        }
    }
    @Override
    public CustomerManagementService getCustomerManagementService() {
        return customerManagementService;
    }
    @Override
    public void recordActions(String customerId, Collection<Action> actions) {

    }
}

