package com.yrgo.services.calls;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Transactional
public class CallHandlingServiceImpl implements CallHandlingService {

    private final CustomerManagementService customerManagementService;
    private final DiaryManagementService diaryManagementService;

    // 构造函数注入依赖项
    @Autowired
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
    public void recordCall(String customerId, Call newCall) throws CustomerNotFoundException {
        customerManagementService.recordCall(customerId, newCall);
    }
}

