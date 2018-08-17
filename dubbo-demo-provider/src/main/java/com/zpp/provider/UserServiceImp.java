package com.zpp.provider;

import com.zpp.common.UserServiceBo;
import org.springframework.stereotype.Service;

/**
 * @author pingpingZhong
 * Date: 2018/4/4
 * Time: 14:10
 */
@Service("userService")
public class UserServiceImp implements UserServiceBo {
    @Override
    public String sayHello(String name) {
        return name;
    }
}
