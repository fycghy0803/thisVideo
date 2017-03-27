package com.hysoft.video.user.dao;

import com.hysoft.video.user.dto.InfoTotalCount;
import com.hysoft.video.user.dto.UserDto;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
public interface UserDao {
    public UserDto queryUserlogin(UserDto userDto);
    public int insertRegisterUser(UserDto userDto) throws Exception;
    public int updateUserDeposit(UserDto userDto) throws Exception;
    public int updateAddUserIncome(UserDto userDto) throws Exception;
    public int updateReduceUserIncome(UserDto userDto) throws Exception;
    public UserDto queryUserInfo(Integer userId);
    public int updateDonate(UserDto userDto);
    public void updateUserInfo(UserDto userDto) throws Exception;
    public int updateDeposByPayAccount(UserDto userDto) throws Exception;
    public InfoTotalCount getInfoTotalCount();
    public List<UserDto> getUserList(UserDto userDto);
}
