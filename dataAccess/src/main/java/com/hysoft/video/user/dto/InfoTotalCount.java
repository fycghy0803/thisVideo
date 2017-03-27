package com.hysoft.video.user.dto;

import java.util.List;

/**
 * Created by yulifan on 2017/1/24.
 */
public class InfoTotalCount extends BaseDto{
    public Integer sysVideoCount;
    public Integer netVideoCount;
    public Integer userCount;
    public Integer seeCount;
    public List<UserDto> userDtoList;

    public Integer getSeeCount() {
        return seeCount;
    }

    public void setSeeCount(Integer seeCount) {
        this.seeCount = seeCount;
    }

    public Integer getSysVideoCount() {
        return sysVideoCount;
    }

    public void setSysVideoCount(Integer sysVideoCount) {
        this.sysVideoCount = sysVideoCount;
    }

    public Integer getNetVideoCount() {
        return netVideoCount;
    }

    public void setNetVideoCount(Integer netVideoCount) {
        this.netVideoCount = netVideoCount;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public List<UserDto> getUserDtoList() {
        return userDtoList;
    }

    public void setUserDtoList(List<UserDto> userDtoList) {
        this.userDtoList = userDtoList;
    }
}
