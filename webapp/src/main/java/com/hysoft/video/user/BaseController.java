package com.hysoft.video.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hysoft.common.Constant;
import com.hysoft.video.transfer.VideoTransferImage;
import com.hysoft.video.user.dto.BaseDto;
import com.hysoft.video.user.dto.UserDto;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangqianjin on 2016/7/13.
 */
@Controller
@RequestMapping("/base")
public class BaseController {
    private static Logger logger = Logger.getLogger(BaseController.class);
   public final static String ERROR = "error";
    public static  String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";
    public static  String USERINFO = "USERINFO";

    /**
     * 得到上传文件的文件头
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static SerializerFeature[] features = { SerializerFeature.WriteNullStringAsEmpty,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};

    public String handleResult(boolean isSuccess,String resultMsg,Integer pageNum,Integer pageSize,Long totalNum, Object resObj){
        Map<String ,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ok",isSuccess);
        resultMap.put("message",resultMsg);
        if(!StringUtils.isEmpty(pageNum)){
            resultMap.put("pageNum",pageNum);
        }
        if(!StringUtils.isEmpty(pageSize)){
            resultMap.put("pageSize",pageSize);
        }
        if(!StringUtils.isEmpty(totalNum)){
            resultMap.put("totalNum",totalNum);
        }
        resultMap.put("res", resObj);
        String result=  JSON.toJSONString(resultMap, features);
        result = result.replace("null","\"\"");
        if(resObj instanceof  List){
            result = result.replace("\"res\":\"\"","\"res\":[]");
        }else{
            result = result.replace("\"res\":\"\"","\"res\":{}");
        }
        return  result;
    }

    public String handleResult(boolean isSuccess,String resultMsg,Object resObj,Object resObj2){
        Map<String ,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ok",isSuccess);
        resultMap.put("message",resultMsg);
        resultMap.put("res",resObj);
        resultMap.put("res2", resObj2);
        String result = JSON.toJSONString(resultMap, features);
        result = result.replace(":null",":\"\"");

        if(resObj instanceof  List){
            result = result.replace("\"res\":\"\"","\"res\":[]");
        }else{
            result = result.replace("\"res\":\"\"","\"res\":{}");
        }
        if(resObj2 instanceof  List){
            result = result.replace("\"res2\":\"\"","\"res2\":[]");
        }else{
            result = result.replace("\"res2\":\"\"","\"res2\":{}");
        }

        return  result;
    }

    public String handleResult(boolean isSuccess,String resultMsg,Object resObj){
        return handleRes(isSuccess,resultMsg,resObj);
    }

    public String handleResult(boolean isSuccess,String resultMsg,BaseDto resObj){
        if(resObj == null) resObj = new BaseDto();
        return handleRes(isSuccess,resultMsg,resObj);
    }

    public  Map<String ,Object> handleResMap(boolean isSuccess,String resultMsg,Object resObj) {
        Map<String ,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ok",isSuccess);
        resultMap.put("message", resultMsg);
        resultMap.put("res", resObj);
        return resultMap;
    }

    public  Map<String ,Object> handleResMap(String resultMsg,Object resObj) {
        Map<String ,Object> resultMap = handleResMap(true,resultMsg,resObj);
        return resultMap;
    }

    public  Map<String ,Object> handleResMap(Object resObj) {
        Map<String ,Object> resultMap = handleResMap("",resObj);
        return resultMap;
    }

    public String handleRes(boolean isSuccess,String resultMsg,Object resObj) {
        Map<String ,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ok",isSuccess);
        resultMap.put("message", resultMsg);
        resultMap.put("res", resObj);
        String result = JSON.toJSONString(resultMap, features);
        result = transNullForResObj(resObj, result);
        result = result.replace(":null",":\"\"");
        return  result;
    }

    public String handleResult(boolean isSuccess,String resultMsg,List resObj){
        String result = null;
        if(resObj == null) {
            result = handleRes(isSuccess, resultMsg, new ArrayList(0));
        } else {
            result = handleRes(isSuccess, resultMsg, resObj);
        }

        return  result;
    }

    public String transNullForResObj(Object resObj,String result) {
        String res = result;
        if(resObj == null) {
            res = res.replace(":null",":{}");
            return res;
        }

        if(resObj instanceof  List){
            for(Object tmpObj:(List)resObj) {
                res = transferNulObj(tmpObj,res);
            }
        } else {
           res = transferNulObj(resObj, res);
        }

        return res;
    }

    public String  transferNulObj(Object resObj,String res){
        Class clsObj = resObj.getClass();
        Field[] filedArray = clsObj.getDeclaredFields();

        for(Field tmpField: filedArray) {
            String className = tmpField.getGenericType().toString();
            try {
                if ("class java.lang.String".equals(className)) {
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":\"\"");
                } else if("class java.lang.Integer".equals(className)) {
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":\"\"");
                } else if("class java.lang.Double".equals(className) ) {
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":\"\"");
                } else if("class java.util.List".equals(className)) {
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":[]");
                } else if("class java.util.Date".equals(className)){
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":\"\"");
                } else {
                    res = res.replace("\"" + tmpField.getName() + "\":null", "\"" + tmpField.getName() + "\":{}");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public String handleResult(boolean isSuccess,String resultMsg){
        Map<String ,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("ok",isSuccess);
        resultMap.put("message",resultMsg);
        String result=  JSON.toJSONString(resultMap, features);
        result = result.replace(":null",":\"\"");
        return  result;
    }

    public String handleResult(boolean isSuccess,String resultMsg,Integer pageNum,Object resObj){
        return handleResult(isSuccess,resultMsg,pageNum,null,null,resObj);
    }

    public void tryCatch(String resultStr){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        try{
            response.setHeader("Content-Type","application/json;charset=utf-8");
            response.getWriter().print(resultStr);
        }catch (Exception es){
            logger.error(es);
        }
    }

    public HttpSession getSession(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        return session;
    }

    protected String saveImageFile(String videoPath,String imagePath,String ffmpegPath) throws Exception{
        Long ll = new Random().nextLong();
        String picName = getCurrDatePath(imagePath) + "/" + Math.abs(ll) + ".jpg";
        imagePath += "/" + picName;
        VideoTransferImage videoTransferImage = new VideoTransferImage();
        videoTransferImage.setFfmpegPath(ffmpegPath);
        boolean lb = videoTransferImage.transferVideoToImage(videoPath, imagePath);
        if(!lb) throw new Exception("save Image failure!");
        return picName;
    }

    protected String saveFile(MultipartFile file,String path) throws Exception{
        String filePath = null;
        // 判断文件是否为空
        if (!file.isEmpty()) {
            VideoTransferImage videoTransferImage = new VideoTransferImage();
            String strType = videoTransferImage.getStrType(file.getOriginalFilename());
            filePath = getCurrDatePath(path);
            Long ll = new Random().nextLong();
            filePath += "/" + Math.abs(ll) + "." + strType;
            //转存文件
            file.transferTo(new File(path + "/" + filePath));
        }

        return filePath;
    }

    protected String getCurrDatePath(String path) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filePath = sdf.format(new Date());
        File tmpFile = new File(path + "/" + filePath);
        if( !tmpFile.exists() ) {
            boolean lb = tmpFile.mkdirs();
            if(!lb) throw new Exception("create directory failure!");
        }
        return filePath;
    }


    protected boolean isAdmin() {
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        boolean ret = false;
        if(sessionUserDto != null && sessionUserDto.getUserId() == Constant.ADMIN_USER_ID) {
            ret = true;
        }

        return ret;
    }

    public Integer getCurrentUserId(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        UserDto userDTO = (UserDto) session.getAttribute(USERINFO);
        if(userDTO != null){
            return userDTO.getUserId();
        }
        return null;
    }

    public void writeToClinet(String str) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            out.write(str);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void setErrorMsg(String errMsg) {
        HttpServletRequest  httpServletRequest =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        httpServletRequest.setAttribute(ERROR, errMsg);
    }

    public void checkUserAuth() throws Exception {
        UserDto userDto = (UserDto)getSession().getAttribute(USERINFO);
        if(userDto == null) throw new Exception("无效用户,请先登录!");
        if(userDto.getUserId() != Constant.ADMIN_USER_ID) throw new Exception("你无权执行该操作!");
    }
}
