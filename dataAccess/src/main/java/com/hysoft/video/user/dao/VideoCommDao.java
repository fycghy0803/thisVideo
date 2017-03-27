package com.hysoft.video.user.dao;

import com.hysoft.video.user.dto.VideoCommDto;

import java.util.List;

/**
 * Created by yulifan on 2017/1/23.
 */
public interface VideoCommDao {
    public void insertCommon(VideoCommDto videoCommDto);
    public List<VideoCommDto> getVideoCommList(VideoCommDto videoCommDto);
}
