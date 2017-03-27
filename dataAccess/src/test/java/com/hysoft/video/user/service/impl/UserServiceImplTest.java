package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.service.UserService;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * UserServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/14/2016</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class UserServiceImplTest extends TestCase {
    @Resource
    UserService userService;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    @org.junit.Test
    public void testLogin() {
        UserDto userDto = new UserDto();
        userDto.setUserName("admin");
        userDto.setUserPwd("1234");
        UserDto resDto = userService.queryUserlogin(userDto);
        Assert.assertEquals(resDto.getUserId(), Integer.valueOf(1));
    }

    @org.junit.Test
    public void testRegUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserName("admin11");
        userDto.setUserPwd("1234");
        userService.insertRegisterUser(userDto);
    }

    @org.junit.Test
    public void testUpdateDeposit() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(1);
        userDto.setUserDeposit((float) 2);
        userService.updateUserDeposit(userDto);
    }

    @org.junit.Test
    public void testUpdateIncome() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUserId(1);
        userDto.setFromUserId(4);
        userDto.setUserIncome((float) 0.1);
        userService.updateUserIncome(userDto);
    }

    @org.junit.Test
    public void testQueryUserInfo() {
        UserDto userDto = userService.queryUserInfo(1);
        org.junit.Assert.assertEquals("admin", userDto.getUserName());
    }


}
