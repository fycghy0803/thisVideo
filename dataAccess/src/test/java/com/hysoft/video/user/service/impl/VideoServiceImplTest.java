package com.hysoft.video.user.service.impl;

import com.hysoft.video.user.dto.VideoDto;
import com.hysoft.video.user.service.VideoService;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * VideoServiceImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>11/14/2016</pre>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class VideoServiceImplTest extends TestCase {
    @Resource
    VideoService videoService;

    public void setUp() throws Exception {
        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    @org.junit.Test
    public void testGetVideoList() throws Exception {
        VideoDto videoDto = new VideoDto();
        videoDto.setVideoType("1");
        videoDto.setPageIndex(1);
        List<VideoDto> list = videoService.getVideoList(videoDto);
        System.out.println("list.size = " + list.size());
    }

    @org.junit.Test
    public void testInsertVideo() throws Exception {
        VideoDto videoDto = new VideoDto();
        videoDto.setUserId(1);
        videoDto.setVideoName("test video");
        videoDto.setVideoPath("videos/cc.mp4");
        videoService.insertVideo(videoDto);
    }
}
