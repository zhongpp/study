package com.zpp.springboot;

import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.AbstractStateMachinePersister;

/**
 * @author pingpingZhong
 *         Date: 2017/6/2
 *         Time: 15:38
 */
public class UserRegStateMachinePersister extends AbstractStateMachinePersister<RegStatusEnum, RegEventEnum, String> {
    UserRegStateMachinePersister(StateMachinePersist<RegStatusEnum, RegEventEnum, String> persist) {
        super(persist);
    }
}
