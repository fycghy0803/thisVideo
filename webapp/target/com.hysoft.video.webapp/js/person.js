$(function(){
    //返回首页
    $("#backIndex").on("click",function(){
        location.href = "index.html"
    });

    $("#signOut").on("click",function(){
        logout();
    });

    getUserInfo();
    listImageList(3);
});

getUserInfo = function() {
    var url = "loginAction/getUserInfo.action";
    ajaxPost(url,"",getUserInfo);
}

getUserInfo.ajaxSuccess = function(data) {
    if(data.ok) {
        $("#userName").val(data.res.userName);
        $("#userPayaccount").val(data.res.userPayaccount);
        $("#userWxpayaccount").val(data.res.userWxpayaccount);
        $("#userDeposit").val(data.res.userDeposit);
        $("#userIncome").val(data.res.userIncome);

        if(data.res.userId == 1) {
            showAdvs(data.res);
            $("#applyDiv").show();
        } else {
            $("#applyDiv").hidden();
        }
    } else {
        alert("获取用户信息失败!");
    }
}

updateUserinfo = function() {
    var url = "loginAction/updateUserInfo.action";
    var postdata = "userPayaccount=" + $("#userPayaccount").val();
    postdata += "&userWxpayaccount=" + $("#userWxpayaccount").val();
    postdata += "&userPwd=" + $("#userPwd").val();
    ajaxPost(url,postdata,updateUserinfo);
}

updateUserinfo.ajaxSuccess = function(data) {
    if(data.ok) {
        alert("保存成功!");
    } else {
        alert("保存失败!" + data.message);
    }
}

withdrawCash = function() {
    var url = "loginAction/withdrawCash.action";
    var withdraw = $("#withdrawCash").val();
    if(!withdraw) {
        alert("请输入提现金额!");
        return;
    }

    var income = $("#userIncome").val();

    if(parseFloat(withdraw) > parseFloat(income) || parseFloat(withdraw) <= 0) {
        alert("提现金额超限额!");
        return;
    }

    var postdata = "userIncome=" + withdraw;
    ajaxPost(url,postdata,withdrawCash);
}

withdrawCash.ajaxSuccess = function(data) {
    if(data.ok) {
        alert("提现成功!请等候审核!");
        getUserInfo();
    } else {
        alert("提现失败!" + data.message);
    }
}
