package com.hysoft.video.user.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * Created by yulifan on 2017/2/24.
 */
public class AdvDto extends BaseDto{
    private Integer eaiId;
    private Integer advModule;
    private Integer advType;
    private String  advUrl;
    private String  advImgpath;
    private Date createdDate;
    private MultipartFile imgFile;
    public MultipartFile getImgFile() {
        return imgFile;
    }

    public void setImgFile(MultipartFile imgFile) {
        this.imgFile = imgFile;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getEaiId() {
        return eaiId;
    }

    public void setEaiId(Integer eaiId) {
        this.eaiId = eaiId;
    }

    public Integer getAdvModule() {
        return advModule;
    }

    public void setAdvModule(Integer advModule) {
        this.advModule = advModule;
    }

    public Integer getAdvType() {
        return advType;
    }

    public void setAdvType(Integer advType) {
        this.advType = advType;
    }

    public String getAdvUrl() {
        return advUrl;
    }

    public void setAdvUrl(String advUrl) {
        this.advUrl = advUrl;
    }

    public String getAdvImgpath() {
        return advImgpath;
    }

    public void setAdvImgpath(String advImgpath) {
        this.advImgpath = advImgpath;
    }
}
