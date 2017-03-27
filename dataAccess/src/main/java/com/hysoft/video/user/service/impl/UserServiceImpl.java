package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dao.UserDao;
import com.hysoft.video.user.dto.InfoTotalCount;
import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.dto.WithdrawCashDto;
import com.hysoft.video.user.service.UserService;
import com.hysoft.video.user.service.WithdrawCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    WithdrawCashService withdrawService;

    public UserDto queryUserlogin(UserDto userDto) {
        UserDto resDto = userDao.queryUserlogin(userDto);
        return resDto;
    }

    public void insertRegisterUser(UserDto userDto) throws Exception{
        userDao.insertRegisterUser(userDto);
    }

    public void updateUserDeposit(UserDto userDto) throws Exception{
        userDao.updateUserDeposit(userDto);
        userDao.updateAddUserIncome(userDto);
    }

    public void updateDeposByPayAccount(UserDto userDto) throws Exception {
        userDao.updateDeposByPayAccount(userDto);
    }

    public void updateUserIncome(UserDto userDto) throws Exception{
        UserDto userTo = new UserDto();
        userTo.setUserId(userDto.getUserId());
        userTo.setUserIncome(userDto.getUserIncome());

        UserDto userFrom = new UserDto();
        userFrom.setUserId(userDto.getFromUserId());
        userFrom.setUserIncome(userDto.getUserIncome());

        int count = userDao.updateAddUserIncome(userTo);
        if(count == 0) {
            throw new Exception("update failure!");
        }

        count = userDao.updateReduceUserIncome(userFrom);
        if(count == 0) {
            throw new Exception("update failure!");
        }
    }

    public UserDto queryUserInfo(Integer userId) {
        return userDao.queryUserInfo(userId);
    }

    public void updateUserInfo(UserDto userDto) throws Exception {
        userDao.updateUserInfo(userDto);
    }

    public void updateWithdrawCash(UserDto userDto) throws Exception{
        userDao.updateReduceUserIncome(userDto);
        WithdrawCashDto withdrawCashDto = new WithdrawCashDto();
        withdrawCashDto.setUserId(userDto.getUserId());
        withdrawCashDto.setCashAmount(userDto.getUserIncome());
        withdrawService.insertWithdrawCash(withdrawCashDto);
    }

    public InfoTotalCount getInfoTotalCount() {
        InfoTotalCount infoTotalCount =  userDao.getInfoTotalCount();
        UserDto userDto = new UserDto();
        List<UserDto> userDtoList = userDao.getUserList(userDto);
        infoTotalCount.setUserDtoList(userDtoList);
        return infoTotalCount;
    }

    public List<UserDto> getUserList(UserDto userDto) {
        List<UserDto> userDtoList = userDao.getUserList(userDto);
        return userDtoList;
    }
}
