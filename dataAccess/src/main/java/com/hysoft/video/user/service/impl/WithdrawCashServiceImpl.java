package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dao.WithdrawCashDao;
import com.hysoft.video.user.dto.WithdrawCashDto;
import com.hysoft.video.user.service.WithdrawCashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yulifan on 2017/1/7.
 */
@Service("withdrawService")
public class WithdrawCashServiceImpl implements WithdrawCashService {
    @Autowired
    WithdrawCashDao withdrawCashDao;

    public void insertWithdrawCash(WithdrawCashDto withdrawCashDto) throws Exception {
        withdrawCashDao.insertWithdrawCash(withdrawCashDto);
    }

    public void updateProcRes(WithdrawCashDto withdrawCashDto) throws Exception {
        withdrawCashDao.updateProcRes(withdrawCashDto);
    }

    public List<WithdrawCashDto> getWithdrawList(WithdrawCashDto withdrawCashDto) {
        return withdrawCashDao.getWithdrawList(withdrawCashDto);
    }
}
