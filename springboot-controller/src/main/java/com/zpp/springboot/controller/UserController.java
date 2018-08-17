package com.zpp.springboot.controller;

import com.zpp.springboot.vo.UserVO;
import com.zpp.springboot.json.Body;
import io.swagger.annotations.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author pingpingZhong
 *         Date: 2017/6/5
 *         Time: 16:48
 */

@Api(value = "用户api", tags = "user API", description = "用户描述信息")
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    static Map<Long, UserVO> users = Collections.synchronizedMap(new HashMap<Long, UserVO>());

    @ApiOperation(value = "获取用户列表", notes = "")
    @GetMapping
    public Body getUserList(@RequestParam(required = false, name = "pageSize", defaultValue = "1") int pageSize,
                            @RequestParam(required = false, name = "pageNum", defaultValue = "20") int pageNum,
                            HttpServletResponse response) {
        List<UserVO> list = new ArrayList<UserVO>(users.values());
        Body resp = new Body();
        try {
            resp.put("users", list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.putError("1", e.getMessage(), "保存用户失败");
        }
        return resp;
    }


    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "payload", value = "用户信息", required = true, dataType = "Body")
    @PostMapping
    public Body postUser(@RequestBody Body payload, HttpServletRequest request, HttpServletResponse response) {
        Body resp = new Body();
        try {
            UserVO userVO = payload.getByClass(UserVO.class);
            userVO.setCreateAt(DateTime.now());
            users.put(userVO.getId(), userVO);
            resp.put("user", userVO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.putError("1", e.getMessage(), "保存用户失败");
        }
        return resp;
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Body getUser(@PathVariable Long id, HttpServletResponse response) {
        Body resp = new Body();
        try {
            UserVO u = users.get(id);
            resp.put("user", u);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.putError("4", e.getMessage(), "查询用户失败");
        }
        return resp;
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "payload", value = "用户详细实体user", required = true, dataType = "Body")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Body putUser(@PathVariable Long id, @RequestBody Body payload, HttpServletResponse response) {
        Body resp = new Body();
        try {
            UserVO u = users.get(id);
            UserVO userVO = payload.getByClass(UserVO.class);
            u.setName(userVO.getName());
            users.put(userVO.getId(), u);
            resp.put("user", u);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.putError("3", e.getMessage(), "更新用户失败");
        }
        return resp;
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Body deleteUser(@PathVariable Long id, HttpServletResponse response) {
        Body resp = new Body();
        try {
            users.remove(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.putError("2", e.getMessage(), "删除用户失败");
        }
        return resp;
    }
}
