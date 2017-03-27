package com.hysoft.video.user.dto;

import java.util.Date;

/**
 * Created by yulifan on 2017/1/7.
 */
public class WithdrawCashDto extends BaseDto{
    private Integer wcId;
    private Integer userId;
    private Float cashAmount;
    private Date createDate;
    private String procRes;
    private String userName;
    private String userWxpayaccount;
    private String userPayaccount;
    private Float userIncome;

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

    public Integer getWcId() {
        return wcId;
    }

    public void setWcId(Integer wcId) {
        this.wcId = wcId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcRes() {
        return procRes;
    }

    public void setProcRes(String procRes) {
        this.procRes = procRes;
    }

    public Float getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Float cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getUserIncome() {
        return userIncome;
    }

    public void setUserIncome(Float userIncome) {
        this.userIncome = userIncome;
    }
}
