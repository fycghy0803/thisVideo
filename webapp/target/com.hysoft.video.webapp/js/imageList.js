var currentAdvModule = 1;
var currentAdvType = 1;
var currentAdvObj = null;

deleteAdv = function(eaiId,obj) {
    currentAdvObj = obj;
    var url = "imageList/deleteImageList.action";
    var postData = "eaiId=" + eaiId;
    ajaxPost(url,postData,deleteAdv);
}

deleteAdv.ajaxSuccess = function(data) {
    if(data && data.ok) {
        closeAdv(currentAdvObj);
    } else {
       alert(data.message);
    }
}

setCurrentAdvType = function(type) {
    currentAdvType = type;
    $("#advType").val(type);
}

listImageList = function(module) {
    var url = "imageList/getImageListByModule.action";
    var postData = "advModule=" + module;
    currentAdvModule = module;
    $("#advModule").val(module);
    ajaxPost(url,postData,listImageList);
}

listImageList.ajaxSuccess = function(data) {
    if(data) {
        var mes,retMes;
        $.each(data.res,function(index,recObj){
            setAdvObj(recObj);
        });
    } else {
        alert(data.message);
    }
}

function setAdvObj(recObj) {
    if(recObj.advType == "1"){
        $("#adv1Href").attr("href",recObj.advUrl);
        $("#adv1ImgPath").attr("src",recObj.advImgpath);
    } else if(recObj.advType == "2"){
        mes = $("#templeteAdv").html();
        retMes = transferMes(mes,recObj);
        $("#adv2").append(retMes);
    } else if(recObj.advType == "3"){
        mes = $("#asideTempleteAdv").html();
        retMes = transferMes(mes,recObj);
        $("#adv3").append(retMes);
    } else if(recObj.advType == 10) {
        $("#zfPayAccount").html(recObj.advUrl);
        $("#zfbImgPath").attr("src",recObj.advImgpath);
    } else if(recObj.advType == 11){
        $("#wxPayAccount").html(recObj.advUrl);
        $("#wxImgPath").attr("src",recObj.advImgpath);
    } else {
        mes = $("#asideTempleteAdv").html();
        retMes = transferMes(mes,recObj);
        $("#adv4").append(retMes);
    }
}

uploadAdv = function() {
    var advUrl = $("#advUrl").val();
    if(!advUrl) {
        alert("广告url地址为空!");
        return;
    }

    var url = "imageList/insertImageList.action";
    ajaxUploadFile(url,"uploadAdvForm",uploadAdv,uploadProgress);
}

uploadAdv.ajaxSuccess = function(data) {
    if(data && data.ok) {
        setAdvObj(data.res);
        $('#uploadAdcModal').modal('hide');
    } else {
        alert(data.message);
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
