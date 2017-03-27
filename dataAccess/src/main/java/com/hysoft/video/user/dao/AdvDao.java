package com.hysoft.video.user.dao;

import com.hysoft.video.user.dto.AdvDto;

import java.util.List;

/**
 * Created by yulifan on 2017/2/24.
 */
public interface AdvDao {
    public void insertAdv(AdvDto advDto);
    public List<AdvDto> getAdvListByModule(AdvDto advDto);
    public void deleteAdv(AdvDto advDto);
}
