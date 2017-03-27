package com.hysoft.video.user.service;

import com.hysoft.video.user.dto.AdvDto;

import java.util.List;

/**
 * Created by yulifan on 2017/2/24.
 */
public interface AdvService {
    void insertAdv(AdvDto advDto);
    List<AdvDto> getAdvListByModule(AdvDto advDto);
    void deleteAdv(AdvDto advDto);
}
