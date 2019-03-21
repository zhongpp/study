package com.zpp.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 事务执行完成后执行的操作
 */
@Slf4j
@Component
public class AfterTransactionService extends TransactionSynchronizationAdapter {

    public static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public void beforeCommit(boolean readOnly) {
        log.info("beforeCommit:"+readOnly);
    }

    @Override
    public void beforeCompletion() {
        log.info("readOnly beforeCompletion");
    }

    @Override
    public void afterCommit() {
        Map<String, LinkedBlockingQueue<Runnable>> afterStack =  AfterTransactionOpt.getAfterTransactionOpts().get();
        //将任务提交到线程池执行
        LinkedBlockingQueue<Runnable> executeQueue =  afterStack.get(AfterTransactionOpt.getTransactionName());
        while(!executeQueue.isEmpty()){
            log.info("afterCommit afterCommit start....");
            //poll 非阻塞方法
            executorService.submit(executeQueue.poll());
            log.info("execute map key name is:"+AfterTransactionOpt.getTransactionName());
            log.info("afterCommit afterCommit end....");
        }
    }

    //事务执行完成后删除操作
    @Override
    public void afterCompletion(int status) {
        log.info("afterCompletion status,start remove localthread data...");
        AfterTransactionOpt.getAfterTransactionOpts().remove();

        Assert.isNull(AfterTransactionOpt.getAfterTransactionOpts().get(),"getAfterTransactionOpts为空null");
    }
}
