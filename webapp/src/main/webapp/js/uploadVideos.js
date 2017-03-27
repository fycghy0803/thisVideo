
function checkImage() {
    var videoPath = $("#imagePath").val();
    if(videoPath == "") {
        alert("pls select Video Files!");
        return false;
    }

    return true;
}

function uploadVideos() {
    $("#uploadBtn").attr("disabled",true);
    var lb = checkImage();
    if(!lb) {
        $("#uploadBtn").attr("disabled",false);
        return false;
    }

    var action="video/uploadBatchVideos.action";
    ajaxUploadFile(action,"uploadForm",uploadVideos,uploadProgress);
}

function initFileInfo() {
    $("#imagePath").val('');
    $("#imageInfos").empty();
    $("#progress").css("width",'0%');
    $("#uploadBtn").attr("disabled",false);
}

uploadVideos.ajaxSuccess = function(data) {
    if(data && data.ok) {
        alert("upload Success,pls wait the administrator's Apply!");
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
    if( !fileObj.files) {
        return;
    }

    var mes = $("#templeteImageInfo").html();
    $("#imageInfos").empty();
    $(fileObj.files).each(function(index,file){
        var fileSize = 0;
        if(file.size < (1024 * 1024)) {
            fileSize = Math.round(file.size /1024)  + "KB";
        } else {
            fileSize = Math.round(file.size /1024/1024)  + "MB";
        }
        var rec = {"fileName":file.name,"fileSize":fileSize,"fileType":file.type};
        var recMsg = transferMes(mes,rec);
        $("#imageInfos").append(recMsg);
    });
}
