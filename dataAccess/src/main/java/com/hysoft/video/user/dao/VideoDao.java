package com.hysoft.video.user.dao;

import com.hysoft.video.user.dto.VideoDto;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
public interface VideoDao {
    public List<VideoDto> getVideoList(VideoDto videoDto);
    public void insertVideo(VideoDto videoDto);
    public void updateVideoSeeCount(VideoDto videoDto);
    public List<VideoDto> getApplyVideoList(VideoDto videoDto);
    public void updateApplyRes(VideoDto videoDto);
    public List<VideoDto> getSearchVideos(VideoDto videoDto);
    public VideoDto getVideoInfo(VideoDto videoDto);
    public Integer getVideoCountByType(VideoDto videoDto);
}