package com.hysoft.video.user;

import com.hysoft.common.Constant;
import com.hysoft.video.user.dto.InfoTotalCount;
import com.hysoft.video.user.dto.UserDto;
import com.hysoft.video.user.dto.WithdrawCashDto;
import com.hysoft.video.user.service.UserService;
import com.hysoft.video.user.service.WithdrawCashService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by yulifan on 2016/11/14.
 */
@RestController
@RequestMapping("/loginAction")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    WithdrawCashService withdrawService;

    //登录验证
    @RequestMapping("/loginUser")
    public Map<String,Object> loginUser(UserDto userDto) throws Exception{
        String returnMsg = "";
        setErrorMsg("登录失败,请检查对应的信息是否正确!");
        UserDto sessionUserDto = userService.queryUserlogin(userDto);
        if(sessionUserDto == null) throw new Exception();
        getSession().setAttribute(USERINFO, sessionUserDto);
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/regUser")
    public Map<String,Object> regUser(UserDto userDto) throws Exception{
        String returnMsg = "";
        UserDto userDto1 = new UserDto();
        setErrorMsg("注册用户失败!");
        userService.insertRegisterUser(userDto);
        return handleResMap(returnMsg,userDto1);
    }

    @RequestMapping("/donate")
    public  Map<String,Object> donate(UserDto userDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        setErrorMsg("赠送金币失败!");

        if(sessionUserDto == null) {
            throw new Exception("无效用户,请先登录!");
        } else {
            Integer fromUserId = sessionUserDto.getUserId();
            userDto.setFromUserId(fromUserId);
            userService.updateUserIncome(userDto);
        }
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/deposit")
    public Map<String,Object> deposit(UserDto userDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        setErrorMsg("充值失败");
        checkUserAuth();
        userService.updateDeposByPayAccount(userDto);
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/getUserInfo")
    public Map<String,Object> getUserInfo() throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        UserDto userDto = new UserDto();
        if(sessionUserDto == null) {
            throw new Exception("无效用户,请先登录!");
        } else {
            userDto = userService.queryUserInfo(sessionUserDto.getUserId());
        }

        return handleResMap(returnMsg,userDto);
    }

    @RequestMapping("/updateUserInfo")
    public Map<String,Object> updateUserInfo(UserDto userDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";

        if(sessionUserDto == null) {
             throw  new Exception("无效用户,请先登录!");
        } else {
            userDto.setUserId(sessionUserDto.getUserId());
            userService.updateUserInfo(userDto);
        }

        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/withdrawCash")
    public Map<String,Object> updateWithdrawCash(UserDto userDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";

        if(sessionUserDto == null) {
            returnMsg = "无效用户,请先登录!";
        } else {
            userDto.setUserId(sessionUserDto.getUserId());
            userService.updateWithdrawCash(userDto);
        }

        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/updatewithdrawRes")
    public Map<String,Object> updatewithdrawRes(WithdrawCashDto withdrawCashDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        boolean isSuccess = false;
        String returnMsg = "";
        setErrorMsg("取现失败");
        checkUserAuth();
        withdrawService.updateProcRes(withdrawCashDto);
        return handleResMap(returnMsg,sessionUserDto);
    }

    @RequestMapping("/getWithdrawList")
    public Map<String,Object> getWithdrawList(WithdrawCashDto withdrawCashDto) throws Exception{
        UserDto sessionUserDto = (UserDto)getSession().getAttribute(USERINFO);
        String returnMsg = "";
        List<WithdrawCashDto>  list = null;
        setErrorMsg("取现失败!");
        checkUserAuth();
        withdrawCashDto.setUserId(sessionUserDto.getUserId());
        list = withdrawService.getWithdrawList(withdrawCashDto);
        return handleResMap(returnMsg,list);
    }

    @RequestMapping("/logOut")
    public void logout() {
        boolean isSuccess = true;
        String returnMsg = "";
        UserDto userDto = new UserDto();
        getSession().removeAttribute(USERINFO);
        String resultStr = handleResult(isSuccess, returnMsg, userDto);
        tryCatch(resultStr);
    }

    @RequestMapping("/getInfoTotalCount")
    public Map<String,Object> getInfoTotalCount() throws Exception{
        String returnMsg = "";
        setErrorMsg("取合计数量失败!");
        InfoTotalCount infoTotalCount = userService.getInfoTotalCount();
        return handleResMap(returnMsg,infoTotalCount);
    }

    @RequestMapping("/getUserList")
    public Map<String ,Object> getUserList(UserDto userDto) throws Exception {
        String returnMsg = "";
        List<UserDto> userDtoList = null;
        checkUserAuth();
        userDto.setPageIndex(userDto.getPageIndex() * userDto.getPageSize());
        userDtoList = userService.getUserList(userDto);
        return handleResMap(returnMsg,userDtoList);
    }
}
