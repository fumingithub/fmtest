// JavaScript Document

$(function(){
    $('.hnav ul li').click(function(){
        $(this).addClass('current').siblings().removeClass('current');    
    })
}).keydown(function (e) {
    if (e.which === 27) {
    	 /* 这里编写当ESC按下时的处理逻辑 */
        if ($.CurrentDialog && $.CurrentDialog.data("options")) {
        	if ($.CurrentDialog.data("options").id == "skipWeb"||$.CurrentDialog.data("options").id == "within_apply_list") {
        		
        	} else {
        		$(this).dialog("close", $.CurrentDialog);
        	}
        	
        }

        // 关闭 datepicker 插件
        var bjuicalendar = $.CurrentDialog.find("#bjui-calendar");
        if (bjuicalendar) {
            $("#bjui-calendar .close").click();
        }
    }
});

/**
 * @des: 显示隐藏左侧树
 * @param divId
 */
function toggleZtree(divId) {
    var $width = $("#"+divId).css("width");
    if($width=="0px") {
        $("#"+divId).animate({width:250},1000, function () {
            $(window).trigger(BJUI.eventType.resizeGrid);
        });
        $("#"+divId).css("overflow","auto");
        _n(".bjui-pageContent").css({right:0});
    } else {
        $("#"+divId).animate({width:0, paddingRight:0}, 1000, function () {
            $(window).trigger(BJUI.eventType.resizeGrid);
        })
    }
}


function saveForm(obj, url, formId, refreshId) {
    var dataForms = $("#" + formId);
    var $formFlag = dataForms.isValid();
    if ($formFlag) {
        $.ajax({
            type : "POST",
            url : url,
            data : $("#" + formId).serialize(),
            success : function(data) {
                var dataJson = eval('(' + data + ')');
                if (dataJson.statusCode == 300) {
                    $(this).alertmsg('warn', dataJson.message);
                } else if (dataJson.statusCode == 200) {
                    $(this).alertmsg('correct', dataJson.message);
                    $(this).dialog("close", $.CurrentDialog);
                    if($(obj).attr('data-refreshCurrentPage') == "true"){//true:新增、编辑保存后页码定位
                    	$('#refresh').attr('data-refreshCurrentPage', true);
                    }

                    if (refreshId) {
                        $('#' + refreshId).click();
                    } else {
                        $('#refresh').click();
                    }
                }
            }
        })
    }
};

function changeEnabledButt(id,isEnabled){
    var disabledCSS = "<i class='fa fa-minus font24 iconbg01'></i>禁用";
    var enabledCSS = "<i class='fa fa-check font24 iconbg02'></i>启用";
    if(isEnabled == "1"){
        $("#"+id).html(disabledCSS);
    }else if(isEnabled == "0"){
        $("#"+id).html(enabledCSS);
    }else{
        $("#"+id).html(enabledCSS);
    }
};

function changeEnabledButtByDItem(id, isEnabled, enabled, disabled) {
    var disabledCSS = "<i class='fa fa-minus font24 iconbg01'></i>禁用";
    var enabledCSS = "<i class='fa fa-check font24 iconbg02'></i>启用";
    if (isEnabled == enabled) {
        $("#" + id).html(disabledCSS);
    } else if (isEnabled == disabled) {
        $("#" + id).html(enabledCSS);
    } else {
        $("#" + id).html(enabledCSS);
    }
}

function recordMoreParams(){
	 var args = arguments;
	 var moreParams = "";
	 for(var i=0; i < args.length - 1; i++){
		 if(i==0){
			 moreParams = args[i];
		 } else {
			 moreParams = moreParams +"ⓐ" +args[i];
		 }
	 }
//	 var tableLen = $(".table").size();
//	 if (tableLen > 1) {
//		 $table = $(".table:eq(" + (tableLen - 1) + ")");
//	 } else {
//		 $table = $(".table");
//	 }
	 var obj = args[args.length - 1];
	 $table = $(obj).closest(".table");
	 $moreParams = $table.closest('.unitBox').find('#bjui-moreParams');
	 var multi  = $table.data('selectedMulti');
	 if(multi){//多选追加参数
		 moreParams =  $moreParams.val()+"ⓑ"+moreParams;
	 }
	 if ($moreParams && $moreParams.length) {
		 $moreParams.val(moreParams)
     } else {
    	 $moreParams = $('<input type="hidden" id="bjui-moreParams" value="'+ moreParams +'">')
         $moreParams.appendTo($table.closest('.unitBox'))
     }
};

/**
 * 行政区划用0补全12位
 * @param areaCode 行政区划编码
 * @author jinliang
 * @createTime 2017-06-10
 */
function rPadAreaCode(areaCode) {
    var zero = "000000000000";
    var areaCodeLen = areaCode.length;
    if (areaCodeLen) {
        areaCode += zero.substr(areaCodeLen);
    }
    return areaCode;
}

/**
 * 根据文本内容选中某一项数据
 * @param selector	下拉框selectpicker的id
 * @param text	    下拉框selectpicker的文本
 */
function selectedByText(selector, text) {
	$("#" + selector).find("option").each(function(){
		if ($(this).text() == text) {
			$(this).parent().selectpicker("val", $(this).val());
		}
	});
} 