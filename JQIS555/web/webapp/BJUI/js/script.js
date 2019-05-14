$(function(){
	/*滑动层*/
	$('.mCon .bs').hover(function(){
		$(this).css('z-index',102);
		$('.tc_01').stop().animate({width:480+'px'},400);
	},function(){
		$(this).css('z-index',100);
		$('.tc_01').stop().animate({width:0+'px'},400);
	});
	
	$('.mCon .jg').hover(function(){
		$(this).css('z-index',99);
		$('.tc_02').stop().animate({height:216+'px'},400);
	},function(){
		$(this).css('z-index',98);
		$('.tc_02').stop().animate({height:0+'px'},400);
	});
	
	$('.zn').hover(function(){
		$(this).css('z-index',100);
		$('.tc_03').stop().animate({width:425+'px'},400);
	},function(){
		$(this).css('z-index',98);
		$('.tc_03').stop().animate({width:0+'px'},400);
	});
	
	
	/*focus*/
	var Focus1 = new Focus(".tabBox .tabCon ul",".tabBox .tabNav li");
	
	/*新闻统计切换*/
	var Focus_01 = new Focus(".tabBox_01",".newsTit span");
	
	/*input焦点清空*/
	var clsResIpt_01 = (new clsResIpt('.clsResIpt')).init();
	
	/*select模拟*/
	var selectCopy_01 = (new selectCopy('.selectCol_01')).init();
	
})
/*focus*/
function Focus(pic,btn){
	this.oUl = $(pic);
	this.oBtn = $(btn);
	this.i = 0;
	this.init();
}
Focus.prototype = {
	constructor:	Focus,
	init:	function(){
		this.next();
		//this.prev();
	},
	next:	function(){
		var that = this;
		this.oBtn.click(function(){
			that.i = that.oBtn.index(this);
			that.showImg(that.i);
		});
	},
	showImg:	function(n){
		this.oUl.stop(true,false).animate({left : -480*this.i + "px"},500);
	}
	
}

/*input焦点清空*/
function clsResIpt(ipt){
	this.ipt = $(ipt) || null;
}
clsResIpt.prototype = {
	constructor: clsResIpt,
	init: function(){
		var that = this;
		this.ipt.focus(function(e){
			that.cls(e);
		});
		this.ipt.blur(function(e){
			that.res(e);
		});
	},
	cls: function(e){
		var e = e ? e : window.event;
		var obj = e.srcElement ? e.srcElement : e.target;
		if(obj.value==obj.defaultValue){
			obj.value = "";
		}
	},
	res: function(e){
		var e = e ? e : window.event;
		var obj = e.srcElement ? e.srcElement : e.target;
		if(obj.value==""){
			obj.value = obj.defaultValue;
		}
	}
}

/*select模拟*/
function selectCopy(sel){
	this.sel = $(sel) || null;
	this.s = this.sel.find('.sel_01');
	this.txt = this.s.find('b');
	this.m = this.sel.find('.m');
	this.mLi = this.m.find('li');
	this.on = false;
}
selectCopy.prototype = {
	constructor: selectCopy,
	init: function(){
		this.mToggle();
		this.selHover();
		this.selClick();
	},
	mToggle: function(){
		var that = this;
		this.s.hover(
			function(){
				if(!that.on){
					that.m.slideDown('normal', function(){
						that.on = true;
					});
				}
			},
			function(){
				if(that.on){
					that.m.slideUp('normal', function(){
						that.on = false;
					});
				}
			}
		);
	},
	selHover: function(){
		var that = this;
		this.mLi.hover(
			function(){
				$(this).addClass('cur');
			},
			function(){
				$(this).removeClass('cur');
			}
		);
	},
	selClick: function(){
		var that = this;
		this.mLi.click(function(){
			that.txt.text($(this).text());
			that.txt.val($(this).val());
			if(that.on){
				that.m.slideUp('normal', function(){
					that.on = false;
				});
			}
		});
	}
}