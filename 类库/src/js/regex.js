//如果邮箱格式正确返回true，否则返回false
function emailRight(value) {
	if((/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value)) && value.length>0 )
		return true;
	 
	 return false;
}

//如果手机号格式正确返回true，否则返回false
function phoneRight(value) {
	if((/^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(value)) && value.length>0 )
		return true;
	 
	 return false;
}

//如果用户名中有特殊字符返回true，否则返回false
function nameForbidden(value) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（—|{}【】‘；：”“'。，、？]")
    var rs = "";
    for (var i = 0; i < value.length; i++) {
        rs = rs + value.substr(i, 1).replace(pattern, '');
    }
    if(value!=rs){
    	return true;
    }
    return false;
}

//如果全是数字返回true，否则返回false
function allNum(value) {
	if(/^[\d.]+$/.test(value)){
		return true;
	}
	return false;
}