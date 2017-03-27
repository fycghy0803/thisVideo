package com.hysoft.video.user.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by shen on 2016/11/13.
 */
public class UserDto extends BaseDto {
    private Integer userId;
    private String userName;
    private String userPwd;
    private Float userDeposit;
    private Float userIncome;
    private Integer fromUserId;
    private String userWxpayaccount;
    private String userPayaccount;
    private Date createDate;
    private Integer pageIndex = 0;
    private Integer pageSize = 50;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Float getUserDeposit() {
        return userDeposit;
    }

    public void setUserDeposit(Float userDeposit) {
        this.userDeposit = userDeposit;
    }

    public Float getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(Float userIncome) {
        this.userIncome = userIncome;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getUserWxpayaccount() {
        return userWxpayaccount;
    }

    public void setUserWxpayaccount(String userWxpayaccount) {
        this.userWxpayaccount = userWxpayaccount;
    }

    public String getUserPayaccount() {
        return userPayaccount;
    }

    public void setUserPayaccount(String userPayaccount) {
        this.userPayaccount = userPayaccount;
    }
}
