package com.zpp.springboot;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pingpingZhong
 *         Date: 2017/6/2
 *         Time: 14:04
 */
@Log
@Service
public class UserService implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDemoService userDemoService;

    @Autowired
    private StateMachineBuilder.Builder<RegStatusEnum, RegEventEnum> userStateMachineBuilder;

    @Autowired
    private UserRegStateMachinePersister userRegStateMachinePersister;


    @Transactional(rollbackFor = {Exception.class})
    public User triggerEvent(String uuid, String eventName) throws Exception {
        StateMachine<RegStatusEnum, RegEventEnum> userStateMachine = userStateMachineBuilder.build();
        User user = userDao.findOne(uuid);

        try {
            userRegStateMachinePersister.restore(userStateMachine, uuid);
        } catch (Exception e) {
            //logger.error("reset state machine error: " + id, e);
            e.printStackTrace();
            throw new Exception("reset state machine error: " + uuid);
        }

        boolean accepted = userStateMachine.sendEvent(RegEventEnum.valueOf(eventName));
        if (!accepted) {
            //logger.error(String.format("current state: %s, event: %s",invoiceStateMachine.getState().getId().toString(),event.toString()));
            throw new Exception("event not accepted: " + eventName);
        }

        try {
            userRegStateMachinePersister.persist(userStateMachine, uuid);
        } catch (Exception e) {
            //logger.error("persist state machine error", e);
            e.printStackTrace();
            throw new Exception("persist state machine error");
        }

        user.setStatus(userStateMachine.getState().getId().toString());
        return user;
    }


    public void run(String... args) throws Exception {
        userDemoService.create();

        //User user = userDao.findByUuid("0a573051-1495-4543-83f6-483a22481d2c");

        //String bigBlob = user.getDescription();
        //log.info(bigBlob);


    }

}
