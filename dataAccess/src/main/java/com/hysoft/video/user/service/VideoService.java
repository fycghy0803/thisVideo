package com.hysoft.video.user.service;

import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.dto.VideoCommDto;
import com.hysoft.video.user.dto.VideoDto;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
public interface VideoService {
    public List<VideoDto> getVideoList(VideoDto videoDto);
    public void insertVideo(VideoDto videoDto) throws Exception;
    public void updateSeeVideo(VideoDto videoDto,UserDto userDto) throws Exception;
    public void updateSeeCount(VideoDto videoDto) throws Exception;
    public List<VideoDto> getApplyVideoList(VideoDto videoDto);
    public void updateApplyRes(VideoDto videoDto);
    public List<VideoDto> getSearchVideos(VideoDto videoDto);
    public VideoDto getVideoInfo(VideoDto videoDto);
    public void insertVideoComm(VideoCommDto videoCommDto);
    public List<VideoCommDto> getVideoCommList(VideoCommDto videoCommDto);
    public void insertBatchVideos(List<VideoDto> videoDtoList) throws Exception;
}
