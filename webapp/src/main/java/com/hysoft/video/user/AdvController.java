package com.hysoft.video.user;

import com.hysoft.video.user.dto.AdvDto;
import com.hysoft.video.user.service.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by yulifan on 2017/2/24.
 */
@RestController
@RequestMapping("/imageList")
public class AdvController extends BaseController {
    @Autowired
    AdvService advService;

    @RequestMapping("/insertImageList")
    public Map<String,Object> insertAdv(AdvDto advDto) throws Exception{
        AdvDto resDto = new AdvDto();
        setErrorMsg("插入广告失败!");

        if (isAdmin()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String advPath = request.getServletContext().getInitParameter("advPath");
            String imgFileName = saveFile(advDto.getImgFile(), advPath);
            advDto.setAdvImgpath("advPics/" + imgFileName);
            advService.insertAdv(advDto);
            resDto.setAdvUrl(advDto.getAdvUrl());
            resDto.setAdvImgpath(advDto.getAdvImgpath());
            resDto.setAdvType(advDto.getAdvType());
        } else {
            throw new Exception("invalid User!");
        }

        return handleResMap(resDto);
    }

    @RequestMapping("/deleteImageList")
    public Map<String,Object>  deleteAdv(AdvDto advDto) throws Exception{
        AdvDto resDto = new AdvDto();
        setErrorMsg("删除广告失败！");
        if (isAdmin()) {
            advService.deleteAdv(advDto);
        } else {
            throw new Exception("invalid User!");
        }

        return handleResMap(resDto);
    }

    @RequestMapping("/getImageListByModule")
    public Map<String,Object> getAdvListByModule(AdvDto advDto) throws Exception{
        setErrorMsg("获取列表失败!");
        List<AdvDto> advDtoList = advService.getAdvListByModule(advDto);
        return handleResMap(advDtoList);
    }
}
