
function checkVideo() {
    var videoName = $("#videoName").val();
    if(videoName == "") {
        alert("请输入视频名称！");
        $("#videoName").focus();
        return false;
    }

    var videoPath = $("#videoPath").val();
    if(videoPath == "") {
        alert("请选择视频文件!");
        return false;
    }

    return true;
}

function uploadVideo() {
    $("#uploadBtn").attr("disabled",true);
    var lb = checkVideo();
    if(!lb) {
        $("#uploadBtn").attr("disabled",false);
        return false;
    }

    var action="video/uploadVideo.action";
    ajaxUploadFile(action,"uploadForm",uploadVideo,uploadProgress);
}

function initFileInfo() {
    $("#videoPath").val('');
    $("#videoName").val('');
    $("#fileSize").html('');
    $("#fileName").html('');
    $("#fileType").html('');
    $("#progress").css("width",'0%');
    $("#uploadBtn").attr("disabled",false);
}

uploadVideo.ajaxSuccess = function(data) {
    if(data && data.ok) {
        alert("上传完成,请耐心等待管理员审核!");
        initFileInfo();
    } else {
        alert(data.message);
        $("#uploadBtn").attr("disabled",false);
    }
}

function uploadProgress(evt) {
    if (evt.lengthComputable) {
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        $("#progress").css("width",percentComplete + '%');
    } else {
        $("#progress").css("width","100%");
    }
}

function fileSelected(fileObj) {
    var file = fileObj.files[0];
    var fileSize = 0;
    if(file.size < (1024 * 1024)) {
        fileSize = Math.round(file.size /1024)  + "KB";
    } else {
        fileSize = Math.round(file.size /1024/1024)  + "MB";
    }

    $("#fileSize").html(fileSize);
    $("#fileName").html(file.name);
    $("#fileType").html(file.type);

}
