package com.zpp.transaction;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Executable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
public class AfterTransactionOpt implements ExecutorInterface {

    @Autowired
    AfterTransactionService afterTransactionService;

    @Getter
    private static final ThreadLocal<Map<String, LinkedBlockingQueue<Runnable>>> afterTransactionOpts;

    static {
        afterTransactionOpts = new NamedThreadLocal<>("redis后置操作thread-local");
    }

    @Override
    public void execute(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            //manager的synchronizations容器是set,可以随便注册,不会重复
            TransactionSynchronizationManager.registerSynchronization(afterTransactionService);
            // Executable executables = getExecutablesCreateIfNecessary();
            Map<String, LinkedBlockingQueue<Runnable>> stack = this.getIfNesses();
            try {
                stack.get(this.getTransactionName()).put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            afterTransactionOpts.set(stack);
        } else {
            log.error("事务后置操作*必须*在一个活跃的事务中");
        }
    }

    /**
     * 获取当前线程执行数据栈
     *
     * @return
     */
    public Map<String, LinkedBlockingQueue<Runnable>> getIfNesses() {
        Map<String, LinkedBlockingQueue<Runnable>> stack = null;
        //如果没有
        if (afterTransactionOpts.get() == null) {
            stack = new HashMap<>();
        }
        if (stack.get(this.getTransactionName()) == null) {
            LinkedBlockingQueue<Runnable> runnables = new LinkedBlockingQueue<>(1000);
            stack.put(this.getTransactionName(), runnables);
        }
        return stack;
    }

    /**
     * 获取当前事务名称
     *
     * @return
     */
    public static String getTransactionName() {
        String transName = "defaulttrans";
        transName = TransactionSynchronizationManager.getCurrentTransactionName();
        return transName;
    }

}