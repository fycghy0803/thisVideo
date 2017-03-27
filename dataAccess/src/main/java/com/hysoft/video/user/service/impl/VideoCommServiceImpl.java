package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dao.VideoCommDao;
import com.hysoft.video.user.dao.VideoDao;
import com.hysoft.video.user.dto.VideoCommDto;
import com.hysoft.video.user.service.VideoCommService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yulifan on 2017/1/23.
 */
@Service("videoCommService")
public class VideoCommServiceImpl implements VideoCommService {
    @Autowired
    VideoCommDao videoCommDao;
    public void insertCommon(VideoCommDto videoCommDto) {
        videoCommDao.insertCommon(videoCommDto);
    }

    public List<VideoCommDto> getVideoCommList(VideoCommDto videoCommDto) {
        List<VideoCommDto> commList =  videoCommDao.getVideoCommList(videoCommDto);
        return commList;
    }
}
