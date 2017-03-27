package com.hysoft.video.user.service;

import com.hysoft.video.user.dto.InfoTotalCount;
import com.hysoft.video.user.dto.UserDto;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
public interface UserService {
    public UserDto queryUserlogin(UserDto userDto);
    public void insertRegisterUser(UserDto userDto) throws Exception;
    public void updateUserDeposit(UserDto userDto) throws Exception;
    public void updateDeposByPayAccount(UserDto userDto) throws Exception;
    public void updateUserIncome(UserDto userDto) throws Exception;
    public UserDto queryUserInfo(Integer userId);
    public void updateUserInfo(UserDto userDto) throws Exception;
    public void updateWithdrawCash(UserDto userDto) throws Exception;
    public InfoTotalCount getInfoTotalCount();
    public List<UserDto> getUserList(UserDto userDto);
}

