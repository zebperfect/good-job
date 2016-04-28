//去掉前后空格  
function trim(str) {  
    var strnew = str.replace(/^\s*|\s*$/g, "");  
    return strnew;  
}  
// 推荐人手机(ajax验证手机号码是否存在)  
function checkjbPhone() {  
    var jbPhone = document.form1.jbPhone.value;  
    var re= /(^1[3|5|8][0-9]{9}$)/;  
    if (trim(jbPhone) == "") {  
        document.getElementById("jbPhoneTip").innerHTML = "<font color='#339933'>√ 手机号码为空，无推荐人</font>";  
        return true;  
    } else if(trim(jbPhone) != ""){  
        if(!re.test(jbPhone)){  
            document.getElementById("jbPhoneTip").innerHTML = "<font color='red'>× 请输入有效的手机号码</font>";  
            return false;  
        }else{  
            document.getElementById("jbPhoneTip").innerHTML = "<font color='#339933'>√ 手机号码输入正确</font>";  
            // 向后台发送处理数据  
            $.ajax({  
                url : "findUsersByTelephone.action",// 目标地址  
                data : {'user.telephone' : jbPhone}, // 目标参数  
                type : "POST", // 用POST方式传输  
                dataType : "text", // 数据格式:text  
                success : function(data) {
                    if (data != null && data == "true") {    
                        $("#jbPhoneTip").html("<font color='#339933'>√ 推荐人手机号码存在，输入正确</font>");  
                        return true;
                    } else if(data != null && data == "false"){ 
                        $("#jbPhoneTip").html("<font color='red'>× 推荐人手机号码不存在，请重新输入</font>");   
                        document.form1.jbPhone.value='';
                        return false;
                    }  else{
                    	$("#jbPhoneTip").html("<font color='red'>× 服务器连接失败</font>");   
                    	document.form1.jbPhone.value='';
                        return false;
                    }
                }  
            }); 
        }  
    }  
}  
// 姓名  
function checknickname() {  
    var nickname = document.form1.nickname.value;  
    if (trim(nickname) == "") {  
        document.getElementById("nicknameTip").innerHTML = "<font color='red'>× 姓名不能为空</font>";  
        return false;  
    } else {  
        document.getElementById("nicknameTip").innerHTML = "<font color='#339933'>√ 姓名输入正确</font>";  
        return true;  
    }  
}
// 证件号码  
function checkjbCredentialsCode() {  
    var jbCredentialsCode = document.form1.jbCredentialsCode.value;  
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;   
    if (trim(jbCredentialsCode) == "") {  
        document.getElementById("jbCredentialsCodeTip").innerHTML = "<font color='red'>× 证件号码不能为空</font>";  
        return false;  
    } else if(trim(jbCredentialsCode) != ""){  
        if(!reg.test(jbCredentialsCode)){  
            document.getElementById("jbCredentialsCodeTip").innerHTML = "<font color='red'>× 请输入合法的证件号码</font>";  
            document.form1.jbCredentialsCode.value='';
            return false;  
        }else{  
            document.getElementById("jbCredentialsCodeTip").innerHTML = "<font color='#339933'>√ 证件号码输入正确</font>";  
            return true;  
        }  
    }  
}    
// 登录密码  
function checkpassword() {  
    var password = document.form1.password.value;  
    if (password.length < 6 || password.length > 30) {  
        document.getElementById("passwordTip").innerHTML = "<font color='red'>× 密码长度不能小于6位，大于30位</font>";  
        return false;  
    } else if (!isNaN(password)) {  
        document.getElementById("passwordTip").innerHTML = "<font color='red'>× 密码不能全是数字</font>";  
        return false;  
    } else {  
        document.getElementById("passwordTip").innerHTML = "<font color='#339933'>√ 密码合格</font>";  
        return true;  
    }  
}  
// 确认密码  
function checkpasswrodb() {  
    var password = document.form1.password.value;  
    var passwordRepeat = document.form1.passwordRepeat.value;  
	if (trim(password) == "") {  
        document.getElementById("passwordRepeatTip").innerHTML = "<font color='red'>× 密码不可以为空</font>";  
        return false;  
	}
    if (trim(password) != trim(passwordRepeat)) {  
        document.getElementById("passwordRepeatTip").innerHTML = "<font color='red'>× 两次密码输入必须一致</font>";  
        return false;  
    } else {  
        document.getElementById("passwordRepeatTip").innerHTML = "<font color='#339933'>√ 密码输入一致</font>";  
        return true;  
    }  
}  
// 注册人手机(ajax验证手机号码是否已经存在)  
function checkregPhone() {  
    var regPhone = document.form1.regPhone.value;  
    var re= /(^1[3|5|8][0-9]{9}$)/;  
    if (trim(regPhone) == "") {  
        document.getElementById("regPhoneTip").innerHTML = "<font color='red'>× 手机号码不能为空</font>";  
        return false;  
    } else if(trim(regPhone) != ""){  
        if(!re.test(regPhone)){  
            document.getElementById("regPhoneTip").innerHTML = "<font color='red'>× 请输入有效的手机号码</font>";  
            document.form1.regPhone.value='';
            return false;  
        }else{  
            document.getElementById("regPhoneTip").innerHTML = "<font color='#339933'>√ 手机号码输入正确</font>";  
            // 向后台发送处理数据  
            $.ajax({  
                url : "findUsersByTelephone.action",// 目标地址  
                data : {'user.telephone' : regPhone}, // 目标参数  
                type : "POST", // 用POST方式传输  
                dataType : "text", // 数据格式:text  
                success : function(data) {
                    if (data != null && data == "true") {  
                        $("#regPhoneTip").html("<font color='red'>× 该手机号码已经被注册，请重新输入</font>");    
                        document.form1.regPhone.value='';
                        return false;
                    } else if(data != null && data == "false"){  
                        $("#regPhoneTip").html("<font color='#339933'>√ 该手机号码可以注册，输入正确</font>");  
                        return true;
                    }  else{
                    	$("#regPhoneTip").html("<font color='red'>× 服务器连接失败</font>");     
                        document.form1.regPhone.value='';
                        return false;
                    }
                }  
            });  
            return true;  
        }  
    }  
}  
// 验证码  
function checkNumber() {  
    var checkNum = document.form1.checkNum.value;  
    if (trim(checkNum) == "") {  
        document.getElementById("checkNumTip").innerHTML = "<font color='red'>× 验证码不能为空</font>";  
        return false;  
    } else {  
        document.getElementById("checkNumTip").innerHTML = "<font color='#339933'>√ 验证码合格</font>";  
        // 向后台发送处理数据  
        $.ajax({  
            url : "verifyCode.action",// 目标地址  
            data : {delData : checkNum}, // 目标参数  
            type : "POST", // 用POST方式传输  
            dataType : "text", // 数据格式:text  
            success : function(data) {  
                if (data != null && data == "true") {  
                    $("#checkNumTip").html("<font color='#339933'>√ 验证码正确</font>");  
                } else {  
                	$("#checkNumTip").html("<font color='red'>× 验证码错误</font>");  
                }  
            }  
        });  
        return true;  
    }  
}  
var InterValObj; //timer变量，控制时间  
var count = 120; //间隔函数，1秒执行  
var curCount;//当前剩余秒数  
var code = ""; //验证码  
var codeLength = 6;//验证码长度  
function sendMessage() {  
    curCount = count;  
    var regPhone = $("#regPhone").val();  
    var regPhoneTip = $("#regPhoneTip").text();  
    if (trim(regPhone) != "") {  
        if(regPhoneTip == "√ 该手机号码可以注册，输入正确" || regPhoneTip == "√ 短信验证码已发到您的手机,请查收"){  
            // 产生验证码  
            for ( var i = 0; i < codeLength; i++) {  
                code += parseInt(Math.random() * 9).toString();  
            }  
            // 设置button效果，开始计时  
            $("#btnSendCode").attr("disabled", "true");  
            $("#btnSendCode").val("请在" + curCount + "秒内输入");  
            InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次  
            // 向后台发送处理数据  
            $.ajax({  
                type: "POST", // 用POST方式传输  
                dataType: "text", // 数据格式:JSON  
                url: "sendSms.action", // 目标地址  
                data: "regPhone=" + regPhone ,  
                error: function (XMLHttpRequest, textStatus, errorThrown) {   
                      
                },  
                success: function (data){ 
                    if(data != null && data == "true"){  
                        $("#regPhoneTip").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");  
                    }else if(data != null && data == "false"){  
                        $("#regPhoneTip").html("<font color='red'>× 短信验证码发送失败，请重新发送</font>");  
                    }
                }  
            });  
        }  
    }else{  
        $("#regPhoneTip").html("<font color='red'>× 手机号码不能为空</font>");  
    }  
}  
  
//timer处理函数  
function SetRemainTime() {  
    if (curCount == 0) {                  
        window.clearInterval(InterValObj);// 停止计时器  
        $("#btnSendCode").removeAttr("disabled");// 启用按钮  
        $("#btnSendCode").val("重新发送验证码");  
        code = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效  
    }else {  
        curCount--;  
        $("#btnSendCode").val("请在" + curCount + "秒内输入");  
    }  
}  
  
$(document).ready(function() {  
    $("#SmsCheckCode").blur(function() {  
        var SmsCheckCodeVal = $("#SmsCheckCode").val();  
        // 向后台发送处理数据  
        $.ajax({  
            url : "smsCode.action",   
            data : {delData : SmsCheckCodeVal},   
            type : "POST",   
            dataType : "text",   
            success : function(data) {  
                if (data != null && data == "true") {  
                    $("#SmsCheckCodeTip").html("<font color='#339933'>√ 短信验证码正确，请继续</font>");  
                } else {  
                    $("#SmsCheckCodeTip").html("<font color='red'>× 短信验证码有误，请核实后重新填写</font>");  
                }  
            }  
        });  
    });  
});  
// 开户行名称
function checkbankname() {  
    var bankname = document.form1.bankname.value;  
    if (trim(bankname) == "") {  
        document.getElementById("banknameTip").innerHTML = "<font color='red'>× 开户银行不能为空</font>";  
        return false;  
    } else {  
        document.getElementById("banknameTip").innerHTML = "<font color='#339933'>√ 开户银行输入正确</font>";  
        return true;  
    }  
}
// 开户行
function checkbankaddress() {  
	alert("复制查询开户行的全称精确到当地支行即可");
	var bankaddress = document.form1.bankname.value;  
	if (trim(bankaddress) == "") {  
		document.getElementById("bankaddressTip").innerHTML = "<font color='red'>× 开户行不能为空</font>";  
		return false;  
	} else {  
		document.getElementById("bankaddressTip").innerHTML = "<font color='#339933'>√ 开户行输入正确</font>";  
		return true;  
	}  
}
//开户行
function comfrimBank() {  
	alert("点击查询按钮，复制查询（精确到当地支行）全称即可");
	window.open('https://www.hebbank.com/corporbank/otherBankQueryWeb.do');
}
// 快递方式
function checkLogs() {  
    var logs = document.form1.logs.value;  
    if (trim(logs) == "") {  
        document.getElementById("logsTip").innerHTML = "<font color='red'>× 物流方式不能为空</font>";  
        return false;  
    } else {  
        document.getElementById("logsTip").innerHTML = "<font color='#339933'>√ 选择输入正确</font>";  
        return true;  
    }  
}
// 银行卡号 
function checkBankcard() {
    var bankcard = document.form1.bankcard.value;  
   // var reg = /^(998801|998802|622525|622526|435744|435745|483536|528020|526855|622156|622155|356869|531659|622157|627066|627067|627068|627069)d{10}$/;  
    if (trim(bankcard) == "") {  
        document.getElementById("bankcardTip").innerHTML = "<font color='red'>× 银行卡号不能为空</font>";  
        return false;  
    } else if(trim(bankcard) != ""){  
      //  if(!reg.test(bankcard)){  
      //      document.getElementById("bankcardTip").innerHTML = "<font color='red'>× 请输入合法的银行卡号</font>";  
      //      return false;  
     //   }else{  
            document.getElementById("bankcardTip").innerHTML = "<font color='#339933'>√ 银行卡号输入正确</font>";  
            return true;  
     //   }  
    }  
} 

function changeCheckNum() {  
    var checkNumImage_ = document.getElementById("checkNumImage");  
    checkNumImage_.src = "${pageContext.request.contextPath}/rand.action?timeStamp="+ new Date().getTime();  
}  