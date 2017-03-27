package com.hysoft.video.user.service;

import com.hysoft.video.user.dto.VideoCommDto;

import java.util.List;

/**
 * Created by yulifan on 2017/1/23.
 */
public interface VideoCommService {
    public void insertCommon(VideoCommDto videoCommDto);
    public List<VideoCommDto> getVideoCommList(VideoCommDto videoCommDto);
}
