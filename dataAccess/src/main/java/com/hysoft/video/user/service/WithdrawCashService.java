package com.hysoft.video.user.service;

import com.hysoft.video.user.dto.WithdrawCashDto;

import java.util.List;

/**
 * Created by yulifan on 2017/1/7.
 */
public interface WithdrawCashService {
    public void insertWithdrawCash(WithdrawCashDto withdrawCashDto) throws Exception;
    public void updateProcRes(WithdrawCashDto withdrawCashDto) throws Exception;
    public List<WithdrawCashDto> getWithdrawList(WithdrawCashDto withdrawCashDto);
}
