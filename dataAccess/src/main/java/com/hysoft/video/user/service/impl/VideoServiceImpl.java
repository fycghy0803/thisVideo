package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dao.VideoDao;
import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.dto.VideoCommDto;
import com.hysoft.video.user.dto.VideoDto;
import com.hysoft.video.user.service.UserService;
import com.hysoft.video.user.service.VideoCommService;
import com.hysoft.video.user.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by shen on 2016/11/13.
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoDao videoDao;
    @Autowired
    UserService userService;
    @Autowired
    VideoCommService videoCommService;

    public List<VideoDto> getVideoList(VideoDto videoDto) {
        Integer count = videoDao.getVideoCountByType(videoDto);
        Integer pageSize = videoDto.getPageSize();
        Integer pageTotal = count / pageSize;
        videoDto.setTotalCount(count);
        Integer left = count % pageSize;
        if(left != 0) {pageTotal++;}
        videoDto.setTotalCount(count);
        videoDto.setTotalPage(pageTotal);
        return videoDao.getVideoList(videoDto);
    }

    public void insertVideo(VideoDto videoDto) throws Exception{
        videoDao.insertVideo(videoDto);
    }

    public void updateSeeVideo(VideoDto videoDto,UserDto userDto) throws Exception {
        videoDao.updateVideoSeeCount(videoDto);
        userService.updateUserIncome(userDto);
    }

    public List<VideoDto> getApplyVideoList(VideoDto videoDto) {
        return videoDao.getApplyVideoList(videoDto);
    }

    public void updateApplyRes(VideoDto videoDto) {
        videoDao.updateApplyRes(videoDto);
    }

    public List<VideoDto> getSearchVideos(VideoDto videoDto) {
        List<VideoDto> list = videoDao.getSearchVideos(videoDto);
        return list;
    }

    public VideoDto getVideoInfo(VideoDto videoDto){
        VideoDto resDto =  videoDao.getVideoInfo(videoDto);
        VideoCommDto videoCommDto = new VideoCommDto();
        videoCommDto.setVideoId(videoDto.getVideoId());
        List<VideoCommDto> tmpCommDto = videoCommService.getVideoCommList(videoCommDto);
        resDto.setVideoCommDtoList(tmpCommDto);
        return  resDto;
    }

    public void updateSeeCount(VideoDto videoDto) throws Exception{
        videoDao.updateVideoSeeCount(videoDto);
    }

    public void insertVideoComm(VideoCommDto videoCommDto){
        videoCommService.insertCommon(videoCommDto);
    }

    public List<VideoCommDto> getVideoCommList(VideoCommDto videoCommDto){
        return videoCommService.getVideoCommList(videoCommDto);
    }

    public void insertBatchVideos(List<VideoDto> videoDtoList) throws Exception {
        for(VideoDto videoDto : videoDtoList) {
            videoDao.insertVideo(videoDto);
        }
    }
}
