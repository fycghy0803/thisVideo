$(function(){
    init();
    var flag ;
    //导航栏点击
    $("#titleNav li").on("click",function(){
        var index = $(this).index();
        $("#titleNav li").each(function(i,obj) {
            $(obj).removeClass("active");
            if(i == index) {
                $(obj).toggleClass("active");
            }
        });
        tab(index);
        flag = index;
    });

    //确认登录
    $("#toLogin").on("click",function(){
        login($("#accountNumber"),$("#passWord"))
    });
    //注册
    $("#register").on("click",function(){
        $(".mask").fadeIn();
        $("#registerType1").show().siblings().hide()
    })
    //注册下一步
    $("#toType2").on('click',function(){
        register($("#email"),$("#verification"))
    })
    //获取验证码
    $("#getVerification").on("click",function(){
        setNum($("#email"))
    });
    //注册完成步
    $("#finshed").on("click",function(){
        regFinished($("#adminName"),$("#makePassword"),$("#makenSurePassword"))
    });
    //忘记密码
    $("#forget").on("click",function(){
        $("#forgetPassword").show().siblings().hide()
    })
    //发送
    $("#find").on("click",function(){
        if($("#findPassword").val() == ""){
            alert("邮箱不能为空")
        }
        else if(isMail($("#findPassword").val())== false){
            alert("邮箱格式不正确")
        }
        else{
            alert("新密码已发送至您邮箱");
            $(".mask").fadeOut();
        }
    })

    //播放
    $(".videoBox,#forImg h5").on("click",function(){
        $("#forPlay").show().siblings().hide()
    });

    $("#search").on("click", function () {
        var videoName = $("#searchVideoName").val();
        var url = "searchVideosResult.html?videoName=" + videoName;
        window.open(url,"searchResWindow");
    });
});

function init() {
    var jsonStr = sessionStorage.getItem("userInfo");
    if(jsonStr != undefined) {
        var jsonData = JSON.parse(jsonStr);
        setUserSession(jsonData);
    }

    getVideoList(SYSTEM_VIDEO,CURRENT_PAGE);
    listImageList(1);
}

//分页
function tab(index){
   if(index == 0){
       $("#forSystem").show().siblings().hide();
       turnVideoType(SYSTEM_VIDEO);
   }else if(index == 1){
       $("#forSystem").show().siblings().hide();
       turnVideoType(USER_VIDEO);
   } else {
       var type = index == 2 ? IMAGE_TYPE:TEXT_TYPE;
       turnVideoType(type);
       $("#forImg").show().siblings().hide();
   }
};

//登录判断
function login(box1,box2){
    if(box1.val() == ""){
        alert("请输入用户名或邮箱")
    } else if(box2.val() == ""){
        alert("请输入密码")
    } else{
        var loginStr = "userName=" + box1.val() + "&userPwd=" + box2.val();
        ajaxPost("loginAction/loginUser.action",loginStr,login);
    }
};

login.ajaxSuccess = function(data) {
    if(data && data.ok ) {
        var jsonStr = JSON.stringify(data);
        sessionStorage.setItem("userInfo",jsonStr);
        setUserSession(data);
    } else {
        alert("登录失败!");
    }
}

setUserSession = function(data)  {
    $("#userInfo").html("个人中心(" + data.res.userName + ")");
    $('#loginModal').modal('hide');
    $("#login").fadeOut();
    showAdvs(data.res);
}

function register(box1,box2){
    if(box1.val() == ""){
        alert("用户名不能为空");
        return;
    }

    if(box2.val() == ""){
        alert("密码不能为空");
        return;
    }

    var regStr = "userName=" + box1.val() + "&userPwd=" + box2.val();
    ajaxPost("loginAction/regUser.action",regStr,register);
};

register.ajaxSuccess = function(data) {
   if(data && data.ok) {
       alert("注册成功!");
       $("#registerModal").modal("hide");
   } else {
       alert("注册失败!");
   }
};

getVideoList = function(videoType,pageIndex) {
    var uri = "video/getVideoList.action";
    pageIndex = pageIndex - 1;
    var postdata = "videoType=" + videoType + "&pageIndex=" + pageIndex;
    ajaxPost(uri,postdata,getVideoList);
}

getVideoList.ajaxSuccess = function(data) {
   if(data && data.ok) {
       var videoType = ",1,2,";
       if(videoType.indexOf(currentVideoType) != -1) {
          listVideo(data);
       } else{
           listImageOrText(data);
       }
   } else {
       alert("获取数据失败!");
   }
}

function listVideo(data) {
    var strData = JSON.stringify(data);
    if( !data.res ) return;
    var mes = $("#videoLiTemplete").html();
    var recMsg;
    $("#videoList").empty();
    $.each(data.res,function(index,rec){
        recMsg = transferMes(mes,rec);
        $("#videoList").append(recMsg);
    });
    setTotal(data);
}

function listImageOrText(data) {
    if( !data.res ) return;
    var recMsg;
    $("#imageOrTextList > tbody").remove();
    var mes = $("#templeteImageOrText").html();
    $("#videoList").empty();
    $.each(data.res,function(index,rec){
        recMsg = transferMes(mes,rec);
        $("#imageOrTextList").append(recMsg);
    });
    $("#videoList").html($("#templeteTable").html());
    setTotal(data);
}

function setTotal(data) {
    var info = JSON.parse(data.message);
    TOTAL_PAGE = info.totalPage;

    var options = {
        alignment:"center",//居中显示
        currentPage: CURRENT_PAGE,//当前页数
        numberOfPages:8,
        bootstrapMajorVersion:3,
        totalPages: info.totalPage,//总页数 注意不是总条数
        itemTexts: function (type, page, current) {
            switch (type) {
                case "first": return "首页";
                case "prev": return "上一页";
                case "next": return "下一页";
                case "last": return "末页";
                case "page": return page;
            }
        },
        onPageClicked: function (event, originalEvent, type, page) {
            CURRENT_PAGE = page;
            getVideoList(currentVideoType,page);
        }
    };

    $("#pagintor").bootstrapPaginator(options);
}

function turnVideoType(videoType) {
    CURRENT_PAGE = 1;
    currentVideoType = videoType;
    $("#videoList").empty();
    getVideoList(currentVideoType,CURRENT_PAGE);
}

function uploadSource(type) {
    var url;
    if(type == 3) {
       url = "uploadImages.html";
    } else if(type == 4){
       url = "uploadText.html";
    } else {
        url = "uploadVideo.html";
    }
    window.open(url,"uploadVideo");
}
