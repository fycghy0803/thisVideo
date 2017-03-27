package com.hysoft.video.user;

import cn.jpush.api.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hysoft.common.Constant;
import com.hysoft.video.transfer.VideoTransferImage;
import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.dto.VideoCommDto;
import com.hysoft.video.user.dto.VideoDto;
import com.hysoft.video.user.service.UserService;
import com.hysoft.video.user.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yulifan on 2016/11/15.
 */
@RestController
@RequestMapping("/video")
public class VideoController extends BaseController{
    @Autowired
    VideoService videoService;

    @RequestMapping("/getVideoList")
    public Map<String,Object> getVideoList(VideoDto videoDto) {
        List<VideoDto> list =  videoService.getVideoList(videoDto);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("totalCount",videoDto.getTotalCount());
        jsonObject.put("totalPage", videoDto.getTotalPage());
        return handleResMap(jsonObject.toJSONString(), list);
    }

    @RequestMapping("getVideoApplyList")
    public Map<String,Object>  getVideoApplyList() throws Exception{
        String returnMsg = "";
        setErrorMsg("获取列表失败");
        checkUserAuth();
        VideoDto videoDto = new VideoDto();
        List<VideoDto> list = videoService.getApplyVideoList(videoDto);
        return handleResMap(returnMsg,list);
    }

    @RequestMapping("applyVideo")
    public Map<String,Object>  applyVideo(VideoDto videoDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        setErrorMsg("审核失败!");
        checkUserAuth();
        videoService.updateApplyRes(videoDto);
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/seeImageOrText")
    public Map<String,Object> seeImageOrText(VideoDto videoDto) throws Exception{
        String returnMsg = "";
        VideoDto retVideoDto = videoService.getVideoInfo(videoDto);
        if(retVideoDto == null) throw new Exception("获取数据失败!");
        return handleResMap(returnMsg,retVideoDto);
    }

    @RequestMapping("/seeVideo")
    public Map<String,Object> seeVideo(VideoDto videoDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        VideoDto retVideoDto = new VideoDto();
        setErrorMsg("观看失败,无法完成扣费!");

        if(videoDto == null || (videoDto.getVideoId() == null) ) {
             throw new Exception("无效的请求参数,视频ID为空!");
        } else {
            retVideoDto = videoService.getVideoInfo(videoDto);
            if(retVideoDto == null) {
                throw new Exception("无法获取视频内容!");
            }  else {
                videoDto.setSeeCount(1);
                if(Constant.SYS_VIDEO_TYPE.equals(retVideoDto.getVideoType())) {
                    videoService.updateSeeCount(videoDto);
                } else {
                    if(sessionUserDto == null) {
                        returnMsg = "无效用户,请先登录!";
                    } else {
                        String lastVideoStr = "lastVideoId";
                        Integer lastVideoId = (Integer)getSession().getAttribute(lastVideoStr);
                        if( (lastVideoId != null && (lastVideoId == videoDto.getVideoId()) )
                                || (sessionUserDto.getUserId() == Constant.ADMIN_USER_ID)
                                ) {
                            videoService.updateSeeCount(videoDto);
                        }  else {
                            videoDto.setIncome(1.00f);
                            UserDto userDto = new UserDto();
                            userDto.setUserIncome(videoDto.getIncome());
                            userDto.setUserId(retVideoDto.getUserId());
                            userDto.setFromUserId(sessionUserDto.getUserId());
                            videoService.updateSeeVideo(videoDto, userDto);
                            getSession().setAttribute("lastVideoId", videoDto.getVideoId());
                        }
                    }
                }
            }
        }
        return handleResMap(returnMsg,retVideoDto);
    }

    @RequestMapping("/uploadVideo")
    public Map<String,Object>  uploadVideo(VideoDto videoDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        VideoDto resDto = new VideoDto();
        setErrorMsg("upload video failure!");

        if(sessionUserDto == null) {
            throw new Exception("无效用户,请先登录!");
        } else {
            Integer userId = sessionUserDto.getUserId();
            String isApplied = userId == Constant.ADMIN_USER_ID ? "Y":"N";
            String videoType = userId == Constant.ADMIN_USER_ID ? "1" : "2";

            //保存图片及视频信息
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String picPath = request.getServletContext().getInitParameter("picPath");
            String videoPath = request.getServletContext().getInitParameter("videoPath");
            String vidoeFileName = saveFile(videoDto.getVideoFile(), videoPath);
            String ffmpegPath = request.getServletContext().getInitParameter("ffmpegPath");
            if(vidoeFileName != null) {
                String imageFileName = saveImageFile(videoPath + "/" + vidoeFileName,picPath,ffmpegPath);
                vidoeFileName = transVideoToMp4(vidoeFileName, videoPath,ffmpegPath);
                resDto.setIsApplied(isApplied);
                resDto.setPicPath("videoPics/" + imageFileName);
                resDto.setVideoPath("videos/" + vidoeFileName);
                resDto.setVideoName(videoDto.getVideoName());
                resDto.setVideoType(videoType);
                resDto.setUserId(userId);
                videoService.insertVideo(resDto);
            }
        }
        return handleResMap(returnMsg,resDto);
    }

    @RequestMapping("/uploadBatchVideos")
    public void uploadVideos(VideoDto videoDto) {
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        boolean isSuccess = false;

        try {
            if(sessionUserDto == null) {
                returnMsg = "invalid user,pls login first!";
            } else {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                Integer userId = sessionUserDto.getUserId();
                if(userId != Constant.ADMIN_USER_ID) {
                    returnMsg = "only administrator can do the batch upload Videos!";
                } else {
                    String isApplied = "Y";
                    String videoType = "1";
                    String picPath = request.getServletContext().getInitParameter("picPath");
                    String videoPath = request.getServletContext().getInitParameter("videoPath");
                    String ffmpegPath = request.getServletContext().getInitParameter("ffmpegPath");
                    List<VideoDto> videoDtos = new ArrayList<VideoDto>();

                    for(MultipartFile tmpFile :videoDto.getVideoFiles()) {
                        String vidoeFileName = saveFile(tmpFile,videoPath);
                        String imageFileName = saveImageFile(videoPath + "/" + vidoeFileName, picPath, ffmpegPath);
                        vidoeFileName = transVideoToMp4(vidoeFileName, videoPath,ffmpegPath);
                        VideoDto resDto = new VideoDto();
                        resDto.setIsApplied(isApplied);
                        resDto.setPicPath("videoPics/" + imageFileName);
                        resDto.setVideoPath("videos/" + vidoeFileName);

                        VideoTransferImage videoTransferImage = new VideoTransferImage();
                        String orgFileName = tmpFile.getOriginalFilename();
                        String strType = videoTransferImage.getStrType(orgFileName);
                        orgFileName = orgFileName.replaceFirst("." + strType,"");
                        resDto.setVideoName(orgFileName);
                        resDto.setVideoType(videoType);
                        resDto.setUserId(userId);
                        videoDtos.add(resDto);
                    }

                    videoService.insertBatchVideos(videoDtos);
                    isSuccess = true;
                }
            }
        } catch (Exception e) {
            returnMsg = "uload failure!" + e.getMessage();
            e.printStackTrace();
        } finally {
            String resultStr = handleResult(isSuccess, returnMsg, new VideoDto());
            tryCatch(resultStr);
        }
    }

    @RequestMapping("/uploadText")
    public Map<String,Object> uploadText(VideoDto videoDto) throws Exception{
        String returnMsg = "";
        VideoDto resDto = new VideoDto();
        setErrorMsg("upload Text failure!");

        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        Integer userId = sessionUserDto == null ? -1 : sessionUserDto.getUserId();
        String isApplied = userId == Constant.ADMIN_USER_ID ? "Y":"N";
        String videoType = "4";
        resDto.setIsApplied(isApplied);
        resDto.setPicPath(videoDto.getPicPath());
        resDto.setVideoName(videoDto.getVideoName());
        resDto.setVideoType(videoType);
        resDto.setUserId(userId);
        videoService.insertVideo(resDto);
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/uploadImages")
    public Map<String,Object> uploadImages(VideoDto videoDto) throws Exception{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String picPath = request.getServletContext().getInitParameter("picPath");
        String picPaths = "";
        String returnMsg = "";
        VideoDto resDto = new VideoDto();
        setErrorMsg("upload image failure!");

        for( MultipartFile multipartFile : videoDto.getImageFiles() ) {
            String picName = saveFile(multipartFile,picPath);
            picPaths += "videoPics/" + picName + ",";
        }
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        Integer userId = null;
        if(sessionUserDto == null) {
            userId = -1;
        } else {
            userId = sessionUserDto.getUserId();
        }

        String isApplied = userId == Constant.ADMIN_USER_ID ? "Y":"N";
        String videoType = "3";
        resDto.setIsApplied(isApplied);
        resDto.setPicPath(picPaths);
        resDto.setVideoName(videoDto.getVideoName());
        resDto.setVideoType(videoType);
        resDto.setUserId(userId);
        videoService.insertVideo(resDto);
        return handleResMap(returnMsg,resDto);
    }

    private String transVideoToMp4(String vidoeFile,String videoPath,String ffmpegPath) throws Exception{
        String newFile = vidoeFile;
        VideoTransferImage videoTransferImage = new VideoTransferImage();
        String fileType = videoTransferImage.getStrType(vidoeFile);
        if( !"mp4".equals(fileType)) {
            Long ll = new Random().nextLong();
            newFile = getCurrDatePath(videoPath) + "/" + Math.abs(ll) + ".mp4";
            String newFullFile =  videoPath + "/" + newFile;
            videoTransferImage.setFfmpegPath(ffmpegPath);
            videoTransferImage.transferVideoToMp4(videoPath + "/" + vidoeFile,newFullFile);
        }

        return newFile;
    }

    @RequestMapping("/searchVideos")
    public void searchVideos(VideoDto videoDto) {
        boolean isSuccess = true;
        String returnMsg = "";
        List<VideoDto> list = null;
        if(StringUtils.isEmpty(videoDto.getVideoName())) {
            returnMsg = "搜索关键字不允许为空!";
            isSuccess = false;
        } else {
            list = videoService.getSearchVideos(videoDto);
        }

        String resultStr = handleResult(isSuccess, returnMsg, list);
        tryCatch(resultStr);
    }

    @RequestMapping("/insertVideoComm")
    public Map<String,Object> insertVideoComm(VideoCommDto videoCommDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        setErrorMsg("发表评论失败!");
        if(sessionUserDto == null) {
            throw new Exception("无效用户,请先登录!");
        } else {
            if(videoCommDto == null || "".equals(videoCommDto.getContent())) {
                throw new Exception("评论内容为空!");
            } else {
                videoCommDto.setUserId(sessionUserDto.getUserId());
                videoService.insertVideoComm(videoCommDto);
            }
        }

        return handleResMap(returnMsg,sessionUserDto);
    }
}
