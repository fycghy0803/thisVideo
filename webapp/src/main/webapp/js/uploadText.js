
function checkText() {
    var videoName = $("#videoName").val();
    if(videoName == "") {
        alert("请输入标题！");
        return false;
    }

    var videoPath = $("#form-content").val();
    if(videoPath == "") {
        alert("请输入内容!");
        return false;
    }

    return true;
}

function uploadText() {
    $("#uploadBtn").attr("disabled",true);
    var lb = checkText();
    if(!lb) {
        $("#uploadBtn").attr("disabled",false);
        return false;
    }

    var action="video/uploadText.action";
    ajaxUploadFile(action,"uploadForm",uploadText);
}

function initFileInfo() {
    $("#videoName").val('');
    $("#form-content").empty();

    $("#uploadBtn").attr("disabled",false);
}

uploadText.ajaxSuccess = function(data) {
    if(data && data.ok) {
        alert("提交成功,请耐心等待管理员审核!");
        initFileInfo();
    } else {
        alert(data.message);
        $("#uploadBtn").attr("disabled",false);
    }
};

