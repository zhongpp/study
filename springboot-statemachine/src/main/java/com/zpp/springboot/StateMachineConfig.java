package com.zpp.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachineContextRepository;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultExtendedState;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * @author pingpingZhong
 *         on 2017/5/23.
 */
@Configuration
public class StateMachineConfig {

    @Autowired
    private ApplicationContext appContext;

    public StateMachineListener<RegStatusEnum, RegEventEnum> listener() {
        return new StateMachineListenerAdapter<RegStatusEnum, RegEventEnum>() {
            @Override
            public void stateChanged(State<RegStatusEnum, RegEventEnum> from, State<RegStatusEnum, RegEventEnum> to) {
                // logger.info("State change to " + to.getId());
            }
        };
    }

    @Bean
    public UserStateMachineContextRepository userStateMachineContextRepository() {
        return new UserStateMachineContextRepository();
    }

    @Bean
    public UserRegStateMachinePersister userStateMachinePersister() {
        return new UserRegStateMachinePersister(
                new RepositoryStateMachinePersist(
                        userStateMachineContextRepository()));
    }

    @Bean
    public StateMachineBuilder.Builder<RegStatusEnum, RegEventEnum> userStateMachineBuilder() {
        StateMachineBuilder.Builder<RegStatusEnum, RegEventEnum> builder = StateMachineBuilder.builder();

        try {
            builder.configureConfiguration()
                    .withConfiguration()
                    .beanFactory(appContext.getAutowireCapableBeanFactory())
                    .autoStartup(true)
                    .listener(listener());
            builder.configureStates()
                    .withStates()
                    .initial(RegStatusEnum.UNCONNECTED)
                    .states(EnumSet.allOf(RegStatusEnum.class));
            builder.configureTransitions()
                    // 1.连接事件
                    // 未连接 -> 已连接
                    .withExternal()
                    .source(RegStatusEnum.UNCONNECTED)
                    .target(RegStatusEnum.CONNECTED)
                    .event(RegEventEnum.CONNECT)
                    .and()

                    // 2.注册事件
                    // 已连接 -> 注册中
                    .withExternal()
                    .source(RegStatusEnum.CONNECTED)
                    .target(RegStatusEnum.REGISTERING)
                    .event(RegEventEnum.REGISTER)
                    .and()

                    // 3.注册成功事件
                    // 注册中 -> 已注册
                    .withExternal()
                    .source(RegStatusEnum.REGISTERING)
                    .target(RegStatusEnum.REGISTERED)
                    .event(RegEventEnum.REGISTER_SUCCESS)
                    .and()

                    // 5.注销事件
                    // 已连接 -> 未连接
                    .withExternal()
                    .source(RegStatusEnum.CONNECTED)
                    .target(RegStatusEnum.UNCONNECTED)
                    .event(RegEventEnum.UN_REGISTER)
                    .and()

                    // 注册中 -> 未连接
                    .withExternal()
                    .source(RegStatusEnum.REGISTERING)
                    .target(RegStatusEnum.UNCONNECTED)
                    .event(RegEventEnum.UN_REGISTER)
                    .and()

                    // 已注册 -> 未连接
                    .withExternal()
                    .source(RegStatusEnum.REGISTERED)
                    .target(RegStatusEnum.UNCONNECTED)
                    .event(RegEventEnum.UN_REGISTER);
            return builder;
        } catch (Exception e) {
            return null;
        }
    }
}

@Configuration
class UserStateMachineContextRepository implements StateMachineContextRepository<RegStatusEnum, RegEventEnum, StateMachineContext<RegStatusEnum, RegEventEnum>> {

    @Autowired
    private UserDao userDao;

    public void save(StateMachineContext<RegStatusEnum, RegEventEnum> context, String uuid) {
        User user = userDao.findByUuid(uuid);
        userDao.updateUserStatus(uuid, context.getState().toString(), user.getStatus());
    }

    public StateMachineContext<RegStatusEnum, RegEventEnum> getContext(String uuid) {
        User user = userDao.findByUuid(uuid);
        RegStatusEnum state = RegStatusEnum.valueOf(user.getStatus());
        return new DefaultStateMachineContext<RegStatusEnum, RegEventEnum>(state, null, null, new DefaultExtendedState());
    }

}
