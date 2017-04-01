$(function(){
    var videoId = UrlParm.parm("videoId");
    seeVideo(videoId);
    var jsonStr = sessionStorage.getItem("userInfo");
    if(jsonStr != undefined) {
        var jsonData = JSON.parse(jsonStr);
        showAdvs(jsonData.res);
    }

    listImageList(2);
});

function seeVideo(videoId) {
    var uri = "video/seeVideo.action";
    var postdata = "videoId=" + videoId;
    ajaxPost(uri, postdata, seeVideo);
}

seeVideo.ajaxSuccess = function(data) {
    if(data && data.ok) {
        $("<source src='" + data.res.videoPath + "' type='video/mp4'>").appendTo("#my-video_html5_api");
        $("<source src='" + data.res.videoPath + "' type='video/mp4'>").appendTo("#my-video");
        $("#videoName").html(data.res.videoName);
        $("#viewCount").html(data.res.seeCount);
        $("#inComes").html(data.res.income);
        $("#userName").html(data.res.userName);
        $("#userId").val(data.res.userId);
        $.each(data.res.videoCommDtoList,function(index,rec){
            setCommInfo(rec);
        });
    } else {
        alert("观看失败!" + data.message);
    }
}

function donate() {
    var income = $("#inCome").val();
    if(income < 1) {
        alert("请录入赠币数量!");
        return;
    } else {
        $("#confirmBtn").attr("disabled",true);
        var postdata = "userIncome=" + income + "&userId=" + $("#userId").val();
        ajaxPost("loginAction/donate.action",postdata,donate);
    }
}

donate.ajaxSuccess = function(data) {
    if(data && data.ok) {
        alert("赠币成功!");
        $("#incomeModal").modal("hide");
    }  else {
        alert("赠币失败!" + data.message);
    }
    $("#confirmBtn").attr("disabled",false);
}

insertVideoComm = function() {
    var content = $("#content").val();
    if(content == "") {
        alert("请录入评论内容!");
        return;
    } else {
        $("#publishBtn").attr("disabled",true);
        var url = "video/insertVideoComm.action";
        var videoId = UrlParm.parm("videoId");
        var postData = "videoId=" + videoId + "&content=" + content;
        ajaxPost(url,postData,insertVideoComm);
    }
}

insertVideoComm.ajaxSuccess = function(data) {
    if(data.ok) {
        alert("评论成功!");
        var info = {"userName":"我","content":$("#content").val(),"createDate":new Date().Format("yyyy-MM-dd hh:mm:ss")};
        setCommInfo(info);
        $("#content").val("");
    }  else {
        alert("评论失败!" + data.message);
    }
    $("#publishBtn").attr("disabled",false);
}

setCommInfo = function(info) {
    var mes = $("#commentTemplete").html();
    var recMsg = transferMes(mes,info);
    $("#commentUl").append(recMsg);
}
