
window.FRAG = {
    alertBoxFrag: '<div id="bjui-alertMsgBox" class="bjui-alert"><div class="alertContent"><div class="#type#"><div class="alertInner"><h1><i class="fa #fa#"></i>#title#</h1><div class="msg">#message#</div></div><div class="toolBar"><ul>#btnFragment#</ul></div></div></div></div>'
    ,alertBtnFrag: '<li><button class="btn btn-#class#" rel="#callback#" type="button">#btnMsg#</button></li>'
    ,alertBackground: '<div class="bjui-alertBackground"></div>'
}
var BJUI={
    keyCode: {
        ENTER : 13, ESC  : 27, END : 35, HOME : 36,
        SHIFT : 16, CTRL : 17, TAB : 9,
        LEFT  : 37, RIGHT: 39, UP  : 38, DOWN : 40,
        DELETE: 46, BACKSPACE: 8
    },
regional: {},
setRegional: function(key, value) {
    BJUI.regional[key] = value
}
}

/* 消息提示框 */
BJUI.setRegional('alertmsg', {
    title  : {error : '错误提示', info : '信息提示', warn : '警告信息', correct : '成功信息', confirm : '确认信息'},
    btnMsg : {ok    : '确定', yes  : '是',   no   : '否',   cancel  : '取消'}
})
/* 消息提示框 */
BJUI.setRegional('alertBtnFrag', {
    title  : {error : '错误提示', info : '信息提示', warn : '警告信息', correct : '成功信息', confirm : '确认信息'},
    btnMsg : {ok    : '确定', yes  : '是',   no   : '否',   cancel  : '取消'}
})

$(document).ready(function () {
        $("#UserName").focus();
       	/*var time1 = setTimeout('$("#worntext").html("")', 4000);
		$(".logo").animate({ marginLeft: 0, opacity: 1 }, 1000);
        $(".headr").animate({ marginTop: 10 }, 600);
        $(".logfix").animate({ marginLeft: -180, opacity: 0.98 }, 400);
        setTimeout('$(".footer").fadeIn();', 800);
		//收藏夹
        $("#addfavo").click(function () {
            if (!$.browser.msie)
            { alert('你的浏览器不支持加入收藏夹，请按 Ctrl + D 手动添加'); }
            else {
                var host = window.location.href;
                window.external.AddFavorite(host, '新农合省市县三级平台')
            }
        });
        $._messengerDefaults = {
            extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
        };
        if ($.browser.msie && ($.browser.version == 6.0 || $.browser.version == 7.0)) {

                $('.content').alertmsg('warn',
                    '温馨提示：您的浏览器版本过低！为避免使用系统时出现问题，建议升级一下浏览器<br /><a target="_blank" style="color:#fc5555;margin:0 auto" href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie">马上升级</a>'
                )
            // <br /><a target="_blank" style="color:#f0f0f0;margin:0 auto" href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie">马上升级</a>
        }*/

});

//回车键直接提交登录表单
$(window).keydown(function(event){
	 if(event.keyCode==13){
		$("#LoginButton").click();
	 }
});
function login(){
	var username=null;
	username=$("#UserName").val();
	var password=null;
	password=$("#Password").val();
	if(!username){
		$('#worn').html("用户名不能为空");
		$("#UserName").focus();
	}else if(!password){
		$('#worn').html("密码不能为空！");
		$("#Password").focus();
	}else{
		$.ajax({
			async :true,
			url:"${ctx}/../../j_spring_security_check",
			data:$("form").serialize(),
			dataType:"JSON",
			success:function(data){
			  if(data.statusCode=="200"){
				  $("form").submit();
			  } else{
				  $("#UserName").focus();
				  $('#worn').html(data.message);
			  }
			},
			error:function(XMLHttpRequest, textStatus, errorThrow){
				$('#worn').html(errorThrow);
			}
		})
	}
}