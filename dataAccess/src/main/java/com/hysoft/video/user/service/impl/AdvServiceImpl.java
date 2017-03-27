package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dao.AdvDao;
import com.hysoft.video.user.dto.AdvDto;
import com.hysoft.video.user.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yulifan on 2017/2/24.
 */
@Service("advService")
public class AdvServiceImpl implements AdvService {
    @Autowired
    private AdvDao advDao;

    public void insertAdv(AdvDto advDto) {
         advDao.insertAdv(advDto);
    }

    public List<AdvDto> getAdvListByModule(AdvDto advDto) {
        List<AdvDto>  advDtoList = advDao.getAdvListByModule(advDto);
        return advDtoList;
    }

    public void deleteAdv(AdvDto advDto) {
        advDao.deleteAdv(advDto);
    }
}
