/*!
 * B-JUI v1.0 (http://b-jui.com)
 * Git@OSC (http://git.oschina.net/xknaan/B-JUI)
 * Copyright 2014 K'naan (xknaan@163.com).
 * Licensed under Apache (http://www.apache.org/licenses/LICENSE-2.0)
 */

/* ========================================================================
 * B-JUI: bjui-plugins.js v1.0
 * @author K'naan (xknaan@163.com)
 * http://git.oschina.net/xknaan/B-JUI/blob/master/BJUI/js/bjui-plugins.js
 * ========================================================================
 * Copyright 2014 K'naan.
 * Licensed under Apache (http://www.apache.org/licenses/LICENSE-2.0)
 * ======================================================================== */

+function ($) {
    'use strict';
    
    $(document).on(BJUI.eventType.initUI, function(e) {
        var $box    = $(e.target)

        // UI init begin...
        /* i-check */
        var $icheck2 = $box.find('[data-toggle="icheck2"]');
        $icheck2.each(function(i) {
            var $element = $(this),
                id = $element.attr('id'),
                name = $element.attr('name'),
                label = $element.data('label')

            if (label) $element.after('<label for="' + id + '" class="ilabel">' + label + '</label>')

            $element.iCheck({
                    checkboxClass: 'icheckbox_minimal-purple',
                    radioClass: 'iradio_minimal-purple',
                    increaseArea: '20%' // optional
            })
        });



        /* i-check */
        var $icheck = $box.find('[data-toggle="icheck"]')
        
        $icheck.each(function(i) {
            var $element = $(this),
                id       = $element.attr('id'),
                name     = $element.attr('name'),
                ifChecked = $element.attr('icheck-checked'),
                checkedValues = $element.attr('icheck-values'),
                label    = $element.data('label')
            if (label) $element.after('<label for="'+ id +'" class="ilabel">'+ label +'</label>')
            
            $element
                .on('ifCreated', function(e) {
                    /* Fixed validate msgbox position */
                    var $parent = $(this).closest('div'),
                        $ilabel = $parent.next('[for="'+ id +'"]')
                    
                    $parent.attr('data-icheck', name)
                    $ilabel.attr('data-icheck', name)
                })
                .iCheck({
                    checkboxClass : 'icheckbox_minimal-purple',
                    radioClass    : 'iradio_minimal-purple',
                    increaseArea  : '20%' // optional
                })
                .on('ifChecked',function(event){
                	if(ifChecked){
                		eval(ifChecked);
                	}
                	if(checkedValues){
                		eval(checkedValues);
                	}
                    var options = $(this).data();
                    if(typeof(options.onchecked) != "undefined") {
                        var callback = options.onchecked;
                        if (callback) callback = callback.toFunc();
                        if (typeof callback != "undefined") {
                            callback(event);
                        }
                    }
                })
                .on('ifChanged', function() {
                    /* Trigger validation */
                    $(this).trigger('validate');
                                    /*
									 * add by jyy 增加选择icheck控件 获取checkbox值
									 */
                                                var $tr=$(this).closest('tr'),
                                                $table=$(this).closest('.table'),
                                                multi     = $table.data('selectedMulti'),
                                                ichkvalue        = $(this).val(),
                                                clsName   = 'selected',
                                                $selected = $table.closest('.unitBox').find('#bjui-selected')
                                           if($(this).attr('checked')||($(this).attr('checked')=='checked'))
                                           {
                                           $tr.hasClass(clsName)?'':$tr.addClass(clsName);
                                            if (multi) {
                                                     } else {
                                                    if($icheck.filter('.checkboxCtrl').attr('checked')=='checked')
                                                    {
                                                    $tr.siblings().addClass(clsName);
                                                    }else
                                                   $tr.siblings().removeClass(clsName).find('[data-toggle="icheck"]').iCheck('uncheck')
                                                   }

                                           }
                                           else
                                          {
                                           $tr.removeClass(clsName);
                                        $(this).iCheck('uncheck');
                                          }
                                           if (multi) {
                                               ichkvalue = []
                                               $tr.siblings('.'+ clsName).add(($tr.hasClass(clsName) ? $tr : '')).each(function() {
                                                 ichkvalue.push($(this).find(":checkbox").val())
                                               })
                                                 ichkvalue = ichkvalue.join(',')
                                            } else {
                                                 // $tr.siblings().removeClass(clsName).find('[data-toggle="icheck"]').iCheck('uncheck')
                                                if (!$tr.hasClass(clsName)) ichkvalue = ''
                                             }
// $tr.toggleClass(clsName)

                                            if ($selected && $selected.length) {
                                                $selected.val(ichkvalue)
                                            } else {
                                                $selected = $('<input type="hidden" id="bjui-selected" value="'+ ichkvalue +'">')
                                                $selected.appendTo($table.closest('.unitBox'))
                                            }
                    
                })




            if ($element.prop('disabled')) $element.iCheck('disable')
        })
        /* i-check check all */
        $icheck.filter('.checkboxCtrl').on('ifChanged', function(e) {
            var checked = e.target.checked == true ? 'check' : 'uncheck'
            var group   = $(this).data('group')
            var clsName=e.target.checked==true?'selected':'';
            $box.find(':checkbox[name="'+ group +'"]').iCheck(checked).closest('tr').addClass(clsName);
        })

        /* fixed ui style */
        $box.find(':text, :password, textarea, :button, a.btn').each(function() {
            var $element = $(this), $tabledit = $element.closest('table.bjui-tabledit')

            if (($element.is(':text') || $element.is(':password') || $element.isTag('textarea')) && !$element.hasClass('form-control'))
                $element.addClass('form-control')
            if ($element.is(':button')) {
                var icon = $element.data('icon'), large = $element.data('large'), oldClass = $element.attr('class')

                if (!$element.hasClass('btn'))
                    $element.removeClass().addClass('btn').addClass(oldClass)
                if (icon) {
                    var _icon = 'fa-'+ icon.replace('fa-', '')

                    if (!$element.data('bjui.icon')) {
                        $element.html('<i class="fa '+ _icon +'"></i> '+ $element.html())
                            .data('bjui.icon', true)
                    }
                }
            }
            if ($element.isTag('a')) {
                var icon = $element.data('icon'), large = $element.data('large')

                if (icon) {
                    var _icon = 'fa-'+ icon.replace('fa-', '')

                    if (!$element.data('bjui.icon')) {
                        $element.html('<i class="fa '+ _icon +'"></i> '+ $element.html())
                            .data('bjui.icon', true)
                    }
                }
            }
            if ($element.isTag('textarea')) {
                var toggle = $element.data('toggle')

                if (toggle && toggle == 'autoheight' && $.fn.autosize) $element.addClass('autosize').autosize()
            }
            if (!$tabledit.length) {
                var size = $element.attr('size') || $element.attr('cols'), width = size * 10

                if (!size) return
                if (width) $element.css('width', width)
            }
        })

        /* form validate */
        $box.find('form[data-toggle="validate"]').each(function() {
            var $element = $(this), alertmsg = (typeof $element.data('alertmsg') == 'undefined') ? true : $element.data('alertmsg')
           // if($element.data("cclose"))

           // return false;
            $(this)
                .validator({
                    valid: function(form) {
                        $(form).bjuiajax('ajaxForm', $(form).data())
                    },
                    validClass : 'ok',
                    theme      : 'red_right_effect',
                    focusCleanup : true
                })
                .on('invalid.form', function(e, form, errors) {
                    if (alertmsg) $(form).alertmsg('error', FRAG.validateErrorMsg.replace('#validatemsg#', BJUI.regional.validatemsg).replaceMsg(errors.length))
					$(".msg-box").eq(0).next().focus();
                })
        })
        
        /* moreSearch */
        $box.find('[data-toggle="moresearch"]').each(function() {
            var $element = $(this),
                $parent  = $element.closest('.bjui-pageHeader'),
                $more    = $parent && $parent.find('.bjui-moreSearch'),
                name     = $element.data('name'),
                $target  = $parent.find(".bjui-searchBar")
            
            if (!$element.attr('title')) $element.attr('title', '更多查询条件')
            $element.click(function(e) {
                if (!$more.length) {
                    BJUI.debug('Not created \'moresearch\' box[class="bjui-moreSearch"]!')
                    return
                }
                // $more.css('top', $parent.outerHeight() - 1)
                $more.css({
                	'top':$target.position().top+$target.outerHeight(),
                	'left':$target.position().left
                })
                if($more.outerWidth()!=$target.outerWidth()) {
                	$more.css('width',$target.outerWidth())
                }
                if ($more.is(':visible')) {
                    $element.html('<i class="fa fa-angle-double-down"></i>')
                    if (name) $('body').data('moresearch.'+ name, false)
                } else {
                    $element.html('<i class="fa fa-angle-double-up"></i>')
                    if (name) $('body').data('moresearch.'+ name, true)
                }
                $more.fadeToggle('slow', 'linear')
                
                e.preventDefault()
            })
            
            /*
			 * if (name && $('body').data('moresearch.'+ name)) {
			 * $more.css('top', $parent.outerHeight() - 1).fadeIn()
			 * $element.html('<i class="fa fa-angle-double-up"></i>') }
			 */
        })
        
        /* bootstrap - select */
        var $selectpicker       = $box.find('select[data-toggle="selectpicker"]')
		// 用不到
		var _addEmpty = function($select,thisval) {
                if ($select && $select.length) {
                    var emptytxt = $select.data('emptytxt') || '--请选择--',sled='';
                    if((typeof thisval=='undefined')||thisval=='')  sled='selected="selected"'
                    $select.html('<option value="" '+sled+'>'+ emptytxt +'</option>'+$select.html())
                    if(sled!='')$($select.find('option').eq(0)).prop('selected',true)
                    $select.selectpicker('refresh')
                   // _addEmpty($select)
                }
            }
        

		var bjui_select_linkage = function($obj, $next) {
            var refurl    = $obj.data('refurl')
            var _setEmpty = function($select) {
                var $_nextselect = $($select.data('nextselect'))
                
                if ($_nextselect && $_nextselect.length) {
                    var emptytxt = $_nextselect.data('emptytxt') || '--请选择--'
                    
                    $_nextselect.html('<option value="">'+ emptytxt +'</option>').selectpicker('refresh')
                    _setEmpty($_nextselect)
                }
            }
            
            if (($next && $next.length) && refurl) {
                var val = $obj.data('val'), nextVal = $next.data('val'),fortext='text',forvalue='value',nextval='dictid';
                // 加载成功后的回调 add by mapp
                var callBack = function(data) {
                    var callback = $obj.data('callback');
                    if (callback) callback = callback.toFunc();
                    if (typeof callback != "undefined") {
                        callback(data);
                    }
                }
                if($next.data('fortext'))
                fortext=$next.data('fortext');
                if($next.data('forvalue'))
                forvalue=$next.data('forvalue');
                // 解决级联不能回显的问题，modify by xuhg at 2017-06-09
                if (val!=$obj.val() && $obj.val() != "") val = $obj.val()
                var op=$obj.data();
                if(op.nextpar)
                    nextval=op.nextpar;
                // 解决nextval为字符串dictid时，js报错问题 modify by xuhg at 2017-05-08
                if(typeof(nextval)=="string" && "dictid" != nextval)
                    nextval=eval("("+nextval+")")
                if(typeof(nextval)=="object")
                for(var p in nextval)
                {
                    if(nextval[p]=='selected')
                    {
                        nextval[p]=val;
                    }
                }
                else nextval=nextval.replace('selected',val);
                var _todata=nextval;
                $.ajax({
                    type     : 'POST',
                    dataType : 'json', 
                    url      : refurl.replace('{value}', encodeURIComponent(val)), // refurl,
																					// -
																					// 动态级联传值
                    cache    : false,
                    data     : _todata,
                    success  : function(json) {
                        if (!json){ _setEmpty($next); return;}
                        callBack(json.length);
                        var html = '', selected = ''
                        
                        $.each(json, function(i) {
                            var value, label

                            if (json[i] && json[i].length) {
                                value = json[i][0]
                                label = json[i][1]
                            } else {
                                value = json[i][forvalue]
                                label = json[i][fortext]
                            }
                            if (typeof nextVal != 'undefined') selected = value == nextVal ? ' selected' : ''
                            html += '<option value="'+ value +'"'+ selected +'>' + label + '</option>'
                        })
                        
                        $obj.removeAttr('data-val').removeData('val')
                        $next.removeAttr('data-val').removeData('val')


                        var emptytxt=$next.data('emptytxt');
                        if ((typeof emptytxt!='undefined')) {
                            var emptyhtml =  emptytxt|| '&nbsp;'
                            html = '<option value="">'+ emptyhtml +'</option>'+html;
                        }


                        $next.empty().html(html).selectpicker('refresh')
                        var std=0;
                        /*
						 * $next.find('option').each(function(i){
						 * if($(this).val()=="") std=1 });
						 */
                        // modify by xuhaigang 解决级联问题
                        if (nextVal){
                            std=1;
                        }
                        if(std==0&&emptytxt)
                        _setEmpty($next)
                    },
                    error   : BJUI.ajaxError
                })

            }
        }
        
        $selectpicker.each(function() {
            var $element  = $(this),$seletVal=$element.val()?$element.val():''
            var options   = $element.data()
            var $next     = $(options.nextselect)
            var $val=$element.data('val');

            $element.addClass('show-tick')
            if (!options.style) $element.data('style', 'btn-default')
            if (!options.width) $element.data('width', 'auto')
            if (!options.container) $element.data('container', 'body')
            var std=0;
            $element.find('option').each(function(i){
                if($(this).prop('selected')){
                    if((typeof $val!='undefined'||$val!='')&&$val!=$(this).val()){// 如果data-val不等于该值则移除选中
                        $(this).removeAttr('selected')
                    }
                    if($(this).val()=='') std=1
                    if(typeof $val=='undefined')$val=$(this).val()// 如果没有设置data-val值
																	// 则赋值
                }else{
                    if($val==$(this).val()){$(this).attr('selected','selected')}
                    else if($(this).val()==''){std=1}
                }
            });
            $element.data('val',$val?$val:'');
            $element.val($val,$val?$val:'');
            if(std==0&&$element.data("emptytxt"))
                _addEmpty($element,$val);
            // $element.data('val',$val);
            $element.selectpicker()



            if ($next && $next.length && (typeof $next.val() != 'undefined' )&&$val!='')
                bjui_select_linkage($element, $next)
        })
        
        /* bootstrap - select - linkage && Trigger validation */
        $selectpicker.change(function() {
            var $element    = $(this)
            var $nextselect = $($element.data('nextselect'))

            bjui_select_linkage($element, $nextselect)
            
            /* Trigger validation */
            if ($element.attr('aria-required')) {
                $element.trigger('validate')
            }
        })

        /* zTree - plugin */
        $box.find('[data-toggle="ztree"]').each(function() {
            var $this = $(this), op = $this.data(), options = op.options, _setting

            if (options && typeof options == 'string') options = options.toObj()
            if (options) $.extend(op, typeof options == 'object' && options)

            _setting = op.setting

            if (!op.nodes) {
                op.nodes = []
                $this.find('> li').each(function() {
                    var $li   = $(this)
                    var node  = $li.data()

                    if (node.pid) node.pId = node.pid
                    node.name = $li.html()
                    op.nodes.push(node)
                })
                $this.empty()
            } else {
                if (typeof op.nodes == 'string') {
                    if (op.nodes.trim().startsWith('[') || op.nodes.trim().startsWith('{')) {
                        op.nodes = op.nodes.toObj()
                    } else {
                        op.nodes = op.nodes.toFunc()
                    }
                }
                if (typeof op.nodes == 'function') {
                    op.nodes = op.nodes.call(this)
                }

                $this.removeAttr('data-nodes')
            }

            if (!op.showRemoveBtn) op.showRemoveBtn = false
            if (!op.showRenameBtn) op.showRenameBtn = false
            if (op.addHoverDom && typeof op.addHoverDom != 'function')       op.addHoverDom    = (op.addHoverDom == 'edit')    ? _addHoverDom    : op.addHoverDom.toFunc()
            if (op.removeHoverDom && typeof op.removeHoverDom != 'function') op.removeHoverDom = (op.removeHoverDom == 'edit') ? _removeHoverDom : op.removeHoverDom.toFunc()
            if (!op.maxAddLevel)   op.maxAddLevel   = 2

            var setting = {
                view: {
                    addHoverDom    : op.addHoverDom || null,
                    removeHoverDom : op.removeHoverDom || null,
                    addDiyDom      : op.addDiyDom ? op.addDiyDom.toFunc() : null
                },
                edit: {
                    enable        : op.editEnable,
                    showRemoveBtn : op.showRemoveBtn,
                    showRenameBtn : op.showRenameBtn
                },
                check: {
                    enable    : op.checkEnable,
                    chkStyle  : op.chkStyle,
                    radioType : op.radioType
                },
                callback: {
                    onClick       : op.onClick      ? op.onClick.toFunc()      : null,
                    beforeDrag    : op.beforeDrag   ? op.beforeDrag.toFunc()   : _beforeDrag,
                    beforeDrop    : op.beforeDrop   ? op.beforeDrop.toFunc()   : _beforeDrop,
                    onDrop        : op.onDrop       ? op.onDrop.toFunc()       : null,
                    onCheck       : op.onCheck      ? op.onCheck.toFunc()      : null,
                    beforeRemove  : op.beforeRemove ? op.beforeRemove.toFunc() : null,
                    onRemove      : op.onRemove     ? op.onRemove.toFunc()     : null,
                    onNodeCreated : _onNodeCreated,
                    onCollapse    : _onCollapse,
                    onExpand      : _onExpand
                },
                data: {
                    simpleData: {
                        enable: op.simpleData || true
                    },
                    key: {
                        title: op.title || ''
                    }
                }
            }

            if (_setting && typeof _setting == 'string') _setting = _setting.toObj()
            if (_setting) $.extend(true, setting, typeof _setting == 'object' && _setting)

            $.fn.zTree.init($this, setting, op.nodes)

            var IDMark_A = '_a'
            var zTree    = $.fn.zTree.getZTreeObj($this.attr('id'))

            if (op.expandAll) zTree.expandAll(true)

            // onCreated
            function _onNodeCreated(event, treeId, treeNode) {
                if (treeNode.faicon) {
                    var $a    = $('#'+ treeNode.tId +'_a')

                    if (!$a.data('faicon')) {
                        $a.data('faicon', true)
                            .addClass('faicon')
                            .find('> span.button').append('<i class="fa fa-'+ treeNode.faicon +'"></i>')
                    }
                }
                if (op.onNodeCreated) {
                    op.onNodeCreated.toFunc().call(this, event, treeId, treeNode)
                }
            }
            // onCollapse
            function _onCollapse(event, treeId, treeNode) {
                if (treeNode.faiconClose) {
                    $('#'+ treeNode.tId +'_ico').find('> i').attr('class', 'fa fa-'+ treeNode.faiconClose)
                }
                if (op.onCollapse) {
                    op.onCollapse.toFunc().call(this, event, treeId, treeNode)
                }
            }
            // onExpand
            function _onExpand(event, treeId, treeNode) {
                if (treeNode.faicon && treeNode.faiconClose) {
                    $('#'+ treeNode.tId +'_ico').find('> i').attr('class', 'fa fa-'+ treeNode.faicon)
                }
                if (op.onExpand) {
                    op.onExpand.toFunc().call(this, event, treeId, treeNode)
                }
            }
            // add button, del button
            function _addHoverDom(treeId, treeNode) {
                var level = treeNode.level
                var $obj  = $('#'+ treeNode.tId + IDMark_A)
                var $add  = $('#diyBtn_add_'+ treeNode.id)
                var $del  = $('#diyBtn_del_'+ treeNode.id)

                if (!$add.length) {
                    if (level < op.maxAddLevel) {
                        $add = $('<span class="tree_add" id="diyBtn_add_'+ treeNode.id +'" title="添加"></span>')
                        $add.appendTo($obj);
                        $add.on('click', function(){
                            zTree.addNodes(treeNode, {name:'新增Item'})
                        })
                    }
                }

                if (!$del.length) {
                    var $del = $('<span class="tree_del" id="diyBtn_del_'+ treeNode.id +'" title="删除"></span>')

                    $del
                        .appendTo($obj)
                        .on('click', function(event) {
                            var delFn = function() {
                                $del.alertmsg('confirm', '确认要删除 '+ treeNode.name +' 吗？', {
                                    okCall: function() {
                                        zTree.removeNode(treeNode)
                                        if (op.onRemove) {
                                            var fn = op.onRemove.toFunc()

                                            if (fn) fn.call(this, event, treeId, treeNode)
                                        }
                                    },
                                    cancelCall: function () {
                                        return
                                    }
                                })
                            }

                            if (op.beforeRemove) {
                                var fn = op.beforeRemove.toFunc()

                                if (fn) {
                                    var isdel = fn.call(fn, treeId, treeNode)

                                    if (isdel && isdel == true) delFn()
                                }
                            } else {
                                delFn()
                            }
                        }
                    )
                }
            }

            // remove add button && del button
            function _removeHoverDom(treeId, treeNode) {
                var $add = $('#diyBtn_add_'+ treeNode.id)
                var $del = $('#diyBtn_del_'+ treeNode.id)

                if ($add && $add.length) {
                    $add.off('click').remove()
                }

                if ($del && $del.length) {
                    $del.off('click').remove()
                }
            }

            // Drag
            function _beforeDrag(treeId, treeNodes) {
                for (var i = 0; i < treeNodes.length; i++) {
                    if (treeNodes[i].drag === false) {
                        return false
                    }
                }
                return true
            }

            function _beforeDrop(treeId, treeNodes, targetNode, moveType) {
                return targetNode ? targetNode.drop !== false : true
            }
        })

        /* zTree - drop-down selector */
        var $selectzTree = $box.find('[data-toggle="selectztree"]')

        $selectzTree.each(function() {
            var $this   = $(this)
            var options = $this.data(),
                $tree   = $(options.tree),
                w       = parseFloat($this.css('width')),
                h       = $this.outerHeight()

            options.width   = options.width || $this.outerWidth()
            options.height  = options.height || 'auto'

            if (!$tree || !$tree.length) return

            var treeid = $tree.attr('id')
            var $box   = $('#'+ treeid +'_select_box')
            var setPosition = function($box) {
                var top        = $this.offset().top,
                    left       = $this.offset().left,
                    $clone     = $tree.clone().appendTo($('body')),
                    treeHeight = $clone.outerHeight()

                $clone.remove()

                var offsetBot = $(window).height() - treeHeight - top - h,
                    maxHeight = $(window).height() - top - h

                if (options.height == 'auto' && offsetBot < 0) maxHeight = maxHeight + offsetBot
                $box.css({top:(top + h), left:left, 'max-height':maxHeight})
            }

            $this.click(function() {
                if ($box && $box.length) {
                    setPosition($box)
                    $box.show()
                    return
                }

                var zindex = 9999
                var dialog = $.CurrentDialog

                if (dialog && dialog.length) {
                    zindex = dialog.css('zIndex') + 1
                }
                $box  = $('<div id="'+ treeid +'_select_box" class="tree-box"></div>')
                    .css({position:'absolute', 'zIndex':zindex, 'min-width':options.width, height:options.height, overflow:'auto', background:'#FAFAFA', border:'1px #EEE solid'})
                    .hide()
                    .appendTo($('body'))

                $tree.appendTo($box).css('width','100%').data('fromObj', $this).removeClass('hide').show()
                setPosition($box)
                $box.show()
            })

            $('body').on('mousedown', function(e) {
                var $target = $(e.target)

                if (!($this[0] == e.target || ($box && $box.length > 0 && $target.closest('.tree-box').length > 0))) {
                    $box.hide()
                }
            })

            var $scroll = $this.closest('.bjui-pageContent')

            if ($scroll && $scroll.length) {
                $scroll.scroll(function() {
                    if ($box && $box.length) {
                        setPosition($box)
                    }
                })
            }

            // destroy selectzTree
            $this.on('destroy.bjui.selectztree', function() {
                $box.remove()
            })
        })

        /* accordion */
        $box.find('[data-toggle="accordion"]').each(function() {
            var $this = $(this), hBox = $this.data('heightbox'), height = $this.data('height')
            var initAccordion = function(hBox, height) {
                var offsety   = $this.data('offsety') || 0,
                    height    = height || ($(hBox).outerHeight() - (offsety * 1)),
                    $pheader  = $this.find('.panel-heading'),
                    h1        = $pheader.outerHeight()
                
                h1 = (h1 + 1) * $pheader.length
                $this.css('height', height)
                height = height - h1
                $this.find('.panel-collapse').find('.panel-body').css('height', height)
            }
            
            if ($this.find('> .panel').length) {
                if (hBox || height) {
                    initAccordion(hBox, height)
                    $(window).resize(function() {
                        initAccordion(hBox, height)
                    })
                    
                    $this.on('hidden.bs.collapse', function (e) {
                        var $last = $(this).find('> .panel:last'), $a = $last.find('> .panel-heading > h4 > a')
                        
                        if ($a.hasClass('collapsed'))
                            $last.css('border-bottom', '1px #ddd solid')
                    })
                }
            }
        })
        
        /* Kindeditor */
        $box.find('[data-toggle="kindeditor"]').each(function() {
            var $editor = $(this), options = $editor.data()
            
            if (options.items && typeof options.items == 'string')
                options.items = options.items.replaceAll('\'', '').replaceAll(' ', '').split(',')
            if (options.afterUpload)         options.afterUpload = options.afterUpload.toFunc()
            if (options.afterSelectFile) options.afterSelectFile = options.afterSelectFile.toFunc()
            if (options.confirmSelect)     options.confirmSelect = options.confirmSelect.toFunc()
            
            var htmlTags = {
                font : [/* 'color', 'size', 'face', '.background-color' */],
                span : ['.color', '.background-color', '.font-size', '.font-family'
                        /*
						 * '.color', '.background-color', '.font-size',
						 * '.font-family', '.background', '.font-weight',
						 * '.font-style', '.text-decoration', '.vertical-align',
						 * '.line-height'
						 */
                ],
                div : ['.margin', '.padding', '.text-align'
                        /*
						 * 'align', '.border', '.margin', '.padding',
						 * '.text-align', '.color', '.background-color',
						 * '.font-size', '.font-family', '.font-weight',
						 * '.background', '.font-style', '.text-decoration',
						 * '.vertical-align', '.margin-left'
						 */
                ],
                table: ['align', 'width'
                        /*
						 * 'border', 'cellspacing', 'cellpadding', 'width',
						 * 'height', 'align', 'bordercolor', '.padding',
						 * '.margin', '.border', 'bgcolor', '.text-align',
						 * '.color', '.background-color', '.font-size',
						 * '.font-family', '.font-weight', '.font-style',
						 * '.text-decoration', '.background', '.width',
						 * '.height', '.border-collapse'
						 */
                ],
                'td,th': ['align', 'valign', 'width', 'height', 'colspan', 'rowspan'
                        /*
						 * 'align', 'valign', 'width', 'height', 'colspan',
						 * 'rowspan', 'bgcolor', '.text-align', '.color',
						 * '.background-color', '.font-size', '.font-family',
						 * '.font-weight', '.font-style', '.text-decoration',
						 * '.vertical-align', '.background', '.border'
						 */
                ],
                a : ['href', 'target', 'name'],
                embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess'],
                img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],
                'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
                    'class', 'align', '.text-align', '.color', /*
																 * '.background-color',
																 * '.font-size',
																 * '.font-family',
																 * '.background',
																 */
                    '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'
                ],
                pre : ['class'],
                hr : ['class', '.page-break-after'],
                'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : []
            }
            
            KindEditor.create($editor, {
                pasteType                : options.pasteType,
                minHeight                : options.minHeight || 260,
                autoHeightMode           : options.autoHeight || false,
                items                    : options.items || KindEditor.options.items,
                uploadJson               : BJUI.PLUGINPATH +'kindeditor_4.1.10/jsp/upload_json.jsp',
                fileManagerJson          : BJUI.PLUGINPATH +'kindeditor_4.1.10/jsp/file_manager_json.jsp',
                allowFileManager         : true,
                fillDescAfterUploadImage : options.fillDescAfterUploadImage || true, // 上传图片成功后转到属性页，为false则直接插入图片[设为true方便自定义函数(X_afterSelect)]
                afterUpload              : options.afterUpload,
                afterSelectFile          : options.afterSelectFile,
                X_afterSelect            : options.confirmSelect,
                htmlTags                 : htmlTags,
                cssPath                  : [
                                                BJUI.PLUGINPATH + 'kindeditor_4.1.10/editor-content.css', 
                                                BJUI.PLUGINPATH + 'kindeditor_4.1.10/plugins/code/prettify.css'
                                           ],
                afterBlur                : function() { this.sync() }
            })
        })
        
        /* colorpicker */
        $box.find('[data-toggle="colorpicker"]').each(function() {
            var $this     = $(this)
            var isbgcolor = $this.data('bgcolor')
            
            $this.colorpicker()
            if (isbgcolor) {
                $this.on('changeColor', function(ev) {
                    $this.css('background-color', ev.color.toHex())
                })
            }
        })
        
        $box.find('[data-toggle="clearcolor"]').each(function() {
            var $this   = $(this)
            var $target = $this.data('target') ? $($this.data('target')) : null
            
            if ($target && $target.length) {
                $this.click(function() {
                    $target.val('')
                    if ($target.data('bgcolor')) $target.css('background-color', '')
                })
            }
        })
        
        /* tooltip */
        $box.find('[data-toggle="tooltip"]').each(function() {
            $(this).tooltip()
        })
        
        /* fixed dropdown-menu width */
        $box.find('[data-toggle="dropdown"]').parent().on('show.bs.dropdown', function(e) {
            var $this = $(this), width = $this.outerWidth(), $menu = $this.find('> .dropdown-menu'), menuWidth = $menu.outerWidth()
            
            if (width > menuWidth) {
                $menu.css('min-width', width)
            }
        })
        
        /* not validate */
        $box.find('form[data-toggle="ajaxform"]').each(function() {
            // if($(this).data("cclose"))
            // $(this).alertmsg('error', "fsdfadsfa")
            $(this).validator({ignore: ':input'})
            $(this).validator('destroy')
        })

        /*
		 * ========================================================================
		 * @description highCharts @author 小策一喋 <xvpindex@qq.com> @Blog
		 * http://www.topjui.com
		 * ========================================================================
		 */
        var $highcharts = $box.find('[data-toggle="highcharts"]')
        
        $highcharts.each(function(){
            var $element = $(this)
            var options  = $element.data()
            
            $.get(options.url, function(chartData){
                $element.highcharts(chartData)
            }, 'json')
        })

        /*
		 * ========================================================================
		 * @description ECharts @author 小策一喋 <xvpindex@qq.com> @Blog
		 * http://www.topjui.com
		 * ========================================================================
		 */
        var $echarts = $box.find('[data-toggle="echarts"]')
        
        $echarts.each(function(){
            var $element = $(this)
            var options  = $element.data()
            var theme    = options.theme ? options.theme : 'default'
            var typeArr  = options.type.split(',')

            require.config({
                paths: {
                    echarts: BJUI.PLUGINPATH + 'echarts'
                }
            })

            require(
                [
                    'echarts',
                    'echarts/theme/' + theme,
                    'echarts/chart/' + typeArr[0],
                    typeArr[1] ? 'echarts/chart/' + typeArr[1] : 'echarts'
                ],
                function (ec,theme) {
                	var myChart = ec.init($element[0],theme)

                    $.get(options.url, function(chartData){
                        if("undefined" != typeof(chartData.xAxis) 
                        	&& typeof (chartData.xAxis[0].axisLabel) != "undefined"
                            && typeof (chartData.xAxis[0].axisLabel.formatter) != "undefined") {
                            chartData.xAxis[0].axisLabel.formatter=function(val){
                                var resval = val;
                                var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
                                if(reg.test(val)){
                                    // 是汉字，奇数换行显示
                                    var vals = val.split("");
                                    resval = "";
                                    for(var i=0;i<vals.length;i++) {
                                        resval += vals[i];
                                        if(!(i%2==0)) {
                                            resval += "\n";
                                        }
                                    }
                                }
                                return resval;
                            }
                        }
                        if("undefined" != typeof(chartData.yAxis)
                        	&& typeof (chartData.yAxis[0].axisLabel) != "undefined"
                            && typeof (chartData.yAxis[0].axisLabel.formatter) != "undefined") {
                            chartData.yAxis[0].axisLabel.formatter = function (val) {
                                return Number(val);
                            }
                        }
                        myChart.setOption(chartData)
                    }, 'json')
                }
            )
        })


        /**
		 * switch
		 */

        var $switchs=$box.find('[data-toggle="switch"]');
        // 同之前的调用合并
        var $sw=$box.find('.make-switch');
        $switchs=$.unique($.merge($switchs,$sw));
        $switchs.each(function () {
            var $element = $(this)
            // var options = $element.data()
            if($element.is('input'))
                $element=$element.wrap("<div class='make-switch'></div>").parent().data($element.data());
            if($element.is('div')&&!$element.hasClass('make-switch'))
            $element.addClass('make-switch');
            $element.bootstrapSwitch();


        })

        /**
		 * fonticonpicker
		 */

        var $fonticonpicker=$box.find('[data-toggle="fonticonpicker"]');
        $fonticonpicker.each(function () {
            var $element = $(this)
            $element.fontIconPicker({
                    theme: 'fip-bootstrap',
                    source:fa_icon,
                    hasSearch: false
                }
            );
        })

        /**
		 * selectdictpicker
		 */
        // 字典两级select显示
        var $selectdictpicker=$box.find('[data-toggle="selectdictpicker"]');
        $selectdictpicker.each(function () {
            var $element = $(this)
            var options  = $element.data()
            $.ajax({
                url : options.url,
                type : 'POST',
                data : {
                    area : options.area,
                    dictCode : options.dict
                },
                success : function(data){
                    if(options.val == ''){
                        var opt = '<option value="" selected="selected">--请选择--</option>';
                        $element.append(opt);
                    }
                    if(data && data.length > 0){
                        for(var i=0; i<data.length; i++){
                            if(data[i].text > 0 && data[i].id_ == null){
                                $element.append('<optgroup label="'+data[i].name+'" forid="'+data[i].id+'"></optgroup>');
                            }

                            if(data[i].text == 0 && data[i].id_ != null){
                                var opt = '<option value="'+data[i].code+'">'+data[i].name+'</option>';
                                if(options.val != '' && options.val == data[i].code)
                                    opt = '<option value="'+data[i].code+'" selected="selected">'+data[i].name+'</option>';
                                $element.find('optgroup[forid="'+data[i].id_+'"]').append(opt);
                            }
                            if(data[i].text == 0 && data[i].id_ == null){
                                var opt = '<option value="'+data[i].code+'">'+data[i].name+'</option>';
                                if(options.val != '' && options.val == data[i].code)
                                    opt = '<option value="'+data[i].code+'" selected="selected">'+data[i].name+'</option>';
                                $element.append(opt);
                            }
                        }
                    }
                    $element.select2({
                        minimumResultsForSearch: -1,
                    });
                }
            });
        })
        // search select box plugin
        var $select2=$box.find('[data-toggle="select2"]');
        $select2.each(function (){
            var $element = $(this)
            var options  = $element.data()
            var minimum = options.minimum;
            var $url=options.url
            // var $data = $element.attr('data-show') // add by xnn @date
			// 2016-02-17 @desc 用于显示默认的数据
            if(options.view && $element.val()==''){
                $element.closest('td').html('<span></span>');
            }
            $element.select2({
                placeholder:"名称/编码/助记码",
                // value:$data,
                minimumInputLength: typeof(minimum)!="undefined"?minimum:1,
                ajax: {
                    url: $url,
                    type : "POST",
                    dataType: 'json',
                    // quietMillis: 250,
                    data: function (term, page) {
                        return {
                            q: term,
                            page: page
                        };
                    },
                    results: function (data, page) {
                        // for(var i = 0; i<data.length; i++){
                        // data[i].text = data[i].text;//+"["+data[i].id+"]";
                        // }

                        if(options.cwidth)
                            $("#select2-drop").addClass(options.cwidth);
                        else
                            $("#select2-drop").addClass("width1");
                        return { results: data};
                    }
                },
                initSelection: function (element, callback) {
                    $.ajax({
                        url: $url,
                        type : "POST",
                        dataType: 'json',
                        data:{code:element.val()},
                        success: function (data) {
                            if(options.view){
                                $element.hide();
                                if(data.length>0)
                                    $element.closest('td').html('<span>'+data[0].name +'</span>');
                            }
                            if(data.length>0){
                                callback({id: element.val(), name: data[0].name });// 这里初始化+"["+data[0].id+"]"
                            }else{
                                element.val('');
                                callback({id: element.val(), name: '--请重新选择--' });
                            }
                        }
                    });
                },
                dropdownCssClass: "bigdrop",
                escapeMarkup: function (m) { return m; },
                formatResult: repoFormatResult, // omitted for brevity, see the
												// source of this page
                formatSelection: function repoFormatSelection(state) {
                    // 选中时执行回调函数 add by jinliang at 2017-10-27 10:50
                    var callbackfuc = function(state) {
                        var callback  = options && options.callback;
                        if (callback) { callback = callback.toFunc() };
                        if(typeof callback != "undefined") { callback(state); }
                    }
                    callbackfuc(state);
                    return state.name;
                },  // omitted for brevity,
														// see the source of
														// this page
            });
        }).trigger("change");

        // tree grid plugin
        var $treegrid=$box.find('[data-toggle="treegrid"]');
        $treegrid.each(function (){
            var $element = $(this)
            var options  = $element.data()
            var tc = options.treecolumn==null||options.treecolumn.toString()==""?1:options.treecolumn
            var initialState = options.state?options.state:"collapsed";
            $element.treegrid({
                treeColumn: tc,// 第二列显示
                initialState:initialState,// 收起
                expanderExpandedClass: 'glyphicon glyphicon-minus',
                expanderCollapsedClass: 'glyphicon glyphicon-plus'
            });
        });

        // 地区控件
        var $areas = $box.find('[data-toggle="areapicker"]');

        $areas.each(function() {
            var $element = $(this);
            var options = $element.data();
            var $code = _n('input[name="'+$element.attr('for')+'"]');
            if(options.openType == 'dialog')
                $code = _d('input[name="'+$element.attr('for')+'"]');
            var theme = options.theme ? options.theme : 'default';
            // data-changed="点击地区事件" data-completed="选择最终地区事件"
            var minLevel = options.minLevel || 1; // data-minLevel 最小级别 默认1
													// 1~5
            var maxLevel = options.maxLevel || 6; // data-maxLevel 最大级别 默认6
													// 2~6
            var tabid = options.id || new Date().getTime();  // data-id
																// 必须加，每个地区插件不能一样
            var async = options.async || 't';// data-async 是否异步加载数据 t 是，f否，
												// 默认是
            options.float = options.float || 'left';
            $element.wrap('<div class="bs-chinese-region areapicker'+tabid+' flat dropdown" style="float:'+options.float+';margin-right:3px" data-submit-type="id" data-min-level="'+minLevel+'" data-max-level="'+maxLevel+'"></div>');
            var html = '<div class="dropdown-menu" role="menu" style="width:'+options.width+'px;" aria-labelledby="dLabel"><div><ul class="nav nav-tabs" role="tablist">';
            html += getHtmlByLevel(minLevel,maxLevel,tabid);
            $element.after(html);
            $element.attr('data-toggle','dropdown');

            // 每一级的值都保存到数据库表中（默认：只保存最后一级的值：options.saveMode == "single"）
            if (options.saveMode == "multi") {
                setTimeout(function () {
                    // var province_hide = $("#province_hide").val();
                    // if ("" != province_hide) {
                    // $("a[href='#province" + tabid +
					// "']").parent().addClass("active");
                    // $("#province" + tabid).find("a").each(function(){
                    // if ($(this).attr("data-id") == province_hide) {
                    // $(this).addClass("current");
                    // }
                    // });
                    // }
                    // if ("" != $("#city_hide").val()) {
                    // $("a[href='#province" + tabid +
					// "']").parent().removeClass("active");
                    // $("a[href='#city" + tabid +
					// "']").parent().addClass("active");
                    //
                    // $("#city" + tabid).find("a").each(function(){
                    // if ($(this).attr("data-id") == $("#city_hide").val()) {
                    // $(this).addClass("current");
                    // }
                    // });
                    // }
                }, 300);
            }

            if(async == 'f') {
                $.ajax({
                    url: options.url,
                    type: 'POST',
                    data: {},
                    dataType: 'json',
                    success : function(data){
                        $('.areapicker' + tabid).chineseRegion('source', data).on('changed.bs.chinese-region',function(e,areas){
                            if(areas.length>0){
                                $code.val(areas[areas.length-1].id);
                                if(options.changed && options.changed.length>0)
                                    eval(options.changed);
                            }
                        }).on('completed.bs.chinese-region',function(e,areas){
                            // if ('[object Function]' ==
							// Object.prototype.toString.call(options.changed))
                            if(options.completed && options.completed.length>0)
                                eval(options.completed);
                        });
                    }
                });
            }
            if(async == 't') {
                $.ajax({
                    url: options.url,
                    type: 'POST',
                    data: {
                        level:minLevel
                    },
                    dataType: 'json',
                    success : function(data){
                        $('.areapicker' + tabid).chineseRegion('source', data).on('changed.bs.chinese-region',function(e,areas){
                            if(areas.length > 0){
                                $code.val(areas[areas.length-1].id);
                                var idex = parseInt(areas[areas.length-1].level) - parseInt(minLevel) + 1;
                                if(_n('.areapicker'+tabid).find('.tab-content').children().length != idex)
                                    _n('.areapicker'+tabid).find('.tab-content').children('.active').html('loading...');

                                // 每一级的值都保存到数据库表中（默认：只保存最后一级的值：options.saveMode
								// == "single"）
                                if (options.saveMode == "multi") {
                                    $(".areas-item.current").each(function(){
                                        $("#" + $(this).parent().attr("id").replace(/\d/g, "") + "_hide")
                                            .val($(this).attr("data-id"));
                                    });
                                }

                                $.ajax({
                                    url: options.url,
                                    type: 'POST',
                                    data: {
                                        pCode:areas[areas.length-1].id
                                    },
                                    async:false,
                                    dataType: 'json',
                                    success : function(d){
                                        $('.areapicker' + tabid).chineseRegion('source2', d , null, minLevel).on('changed.bs.chinese-region',function(e,areas){
                                            if(!$element.parent().hasClass('open')){

                                            }
                                        });
                                    }
                                });
                                if(options.changed && options.changed.length>0)
                                    eval(options.changed);
                            }
                        }).on('completed.bs.chinese-region',function(e,areas){
                            if (options.completed && options.completed.length>0)
                                eval(options.completed);
                        });
                    }
                });
            }
        });

    })

    function getAreaHtmlUp(index,tabid,active){
        var html = '';
        if(active)
            html = '<li role="presentation" class="active">';
        else
            html = '<li role="presentation">';
        switch (index ){
            case  1 : html += '<a href="#province'+tabid+'" data-next="city'+tabid+'" role="tab" data-toggle="tab">省</a>';break;
            case  2 : html += '<a href="#city'+tabid+'" data-next="district'+tabid+'" role="tab" data-toggle="tab">市</a>';break;
            case  3 : html += '<a href="#district'+tabid+'" data-next="street'+tabid+'" role="tab" data-toggle="tab">县</a>';break;
            case  4 : html += '<a href="#street'+tabid+'" data-next="village'+tabid+'" role="tab" data-toggle="tab">镇</a>';break;
            case  5 : html += '<a href="#village'+tabid+'" data-next="group'+tabid+'" role="tab" data-toggle="tab">村</a>';break;
            case  6 : html += '<a href="#group'+tabid+'" role="tab" data-toggle="tab">组</a>';break;
            default : html += '';
        }
        html +='</li>';
        return html;
    }

    function getAreaHtmlDown(index,tabid,active){
        var html = '';
        if(active)
            html = '<div role="tabpanel" class="tab-pane active" ';
        else
            html = '<div role="tabpanel" class="tab-pane" ';
        switch (index ){
            case  1 : html += 'id="province'+tabid+'"';break;
            case  2 : html += 'id="city'+tabid+'"';break;
            case  3 : html += 'id="district'+tabid+'"';break;
            case  4 : html += 'id="street'+tabid+'"';break;
            case  5 : html += 'id="village'+tabid+'"';break;
            case  6 : html += 'id="group'+tabid+'"';break;
            default : html += '';
        }
        html +='>--</div>';
        return html;
    }

    // 根据设定级别加载不同的地区插件
    function getHtmlByLevel(minLevel,maxLevel,tabid){
        var html = '';
        // 根据设定级别获取对应标签html
        for(var i=minLevel; i<=maxLevel; i++){
            html += getAreaHtmlUp(i,tabid,i==minLevel);
        }
        html += '</ul><div class="tab-content">';
        // 根据设定级别获取对应标签内容页html
        for(var i=minLevel; i<=maxLevel; i++){
            html += getAreaHtmlDown(i,tabid,i==minLevel);
        }
        html += '</div></div></div>';
        return html;
    }

    // select2插件，样式化结果。
    function repoFormatResult_old(state){
        if (!state.id) return state.name; // optgroup
        var html = '<div style="height: 14px;">' +
            '<div style="float: left;width: 118px;font-size:14px" class="nowrap" title="'+ state.name+'"> <i class="fa fa-caret-right"></i> '+ state.name+'</div>' +
            '<div style="float: left;width: 118px;font-size:14px" class="nowrap" title="'+ state.text+'"> <i class="fa fa-caret-right"></i> '+ state.text+'</div>'+
            '</div>';
        if(state.pinyin)
            html = '<div style="height: 36px;">' +
                '<div style="float: left;width: 240px; height:24px;background-color:#eee;font-size:16px" class="nowrap" title="'+ state.name+'"><i class="fa fa-file-o"></i> '+ state.name+'</div>' +
                '<div style="float: left;width: 110px; margin: 0 2px 2px 10px;" class="nowrap" title="'+ state.text+'"> <i class="fa fa-caret-right"></i> '+ state.text+'</div>'+
                '<div style="float: left;width: 110px;" class="nowrap" title="'+ state.pinyin+'"> <i class="fa fa-caret-right"></i> '+ state.pinyin+'</div>'+
                '</div>';
        if(state.type == '0'){
            html = '<div style="height: 28px;">' +
                '<table style="width:100%"><tr>' +
                // '<th title="疾病目录">选择疾病</th>' +
                '<td width="50%" title="'+state.name+'"> <div class="nowrap" style="width:175px;"><i class="fa fa-caret-right"></i> '+ state.name+'</div></td>'+
                // '</tr><tr>' +
                // '<th title="治疗方式目录">治疗方式</th>' +
                '<td width="50%" title="'+ (state.text_ == null?"":state.name_)+'"> <div class="nowrap" style="width:175px;"><i class="fa fa-caret-right"></i> '+ (state.text_ == null?"":state.name_)+'</div></td>'+
                '</tr><tr>' +
                '<td width="50%" title="'+state.text+'"> <div class="nowrap" style="width:175px;"><i class="fa fa-caret-right"></i> '+state.text+'</div></td>'+
                '<td width="50%" title="'+ (state.text_ == null?"":state.text_)+'"> <div class="nowrap" style="width:175px;"><i class="fa fa-caret-right"></i> '+ (state.text_ == null?"":state.text_)+'</div></td>'+
                '</tr></table></div>';
            html+= '<div class="vertical-line"></div>';
        }
        if(state.type == '1'){
            html = '<div style="height: 14px;">' +
                '<div style="float: left;width: 60px; background-color:#eee; text-align: right" class="nowrap" title="治疗方式目录">治疗方式</div>' +
                '<div style="float: left;width: 120px; margin: 0 2px 2px 10px;" class="nowrap" title="'+ state.text+'"> <i class="fa fa-caret-right"></i> '+ state.text+'</div>'+
                '<div style="float: left;width: 150px;" class="nowrap" title="'+ state.name+'"> <i class="fa fa-caret-right"></i> '+ state.name+'</div></div>';
            if(state.text_)
                html +='<div style="height: 14px;">' +
                '<div style="float: left;width: 60px; background-color:#eee; text-align: right" class="nowrap" title="疾病目录">疾病</div>' +
                '<div style="float: left;width: 120px; margin: 0 2px 2px 10px;" class="nowrap" title="'+ state.text_+'"> <i class="fa fa-caret-right"></i> '+ state.text_+'</div>'+
                '<div style="float: left;width: 150px;" class="nowrap" title="'+ state.name_+'"> <i class="fa fa-caret-right"></i> '+ state.name_+'</div>'+
                '</div>';
            html+= '<div class="vertical-line"></div>';
        }
        return html;
    }

    function repoFormatResult(state){
        if (!state.id) return state.name; // optgroup

        // var html = '<div style="height: 14px;">' +
        // '<div style="float: left;width: 118px;font-size:14px" class="nowrap"
		// title="'+ state.name+'"> <i class="fa fa-caret-right"></i> '+
		// state.name+'</div>' +
        // '<div style="float: left;width: 118px;font-size:14px" class="nowrap"
		// title="'+ state.text+'"> <i class="fa fa-caret-right"></i> '+
		// state.text+'</div>'+
        // '</div>';
        var html = '<table class="table2">' +
            '<tr><th>名称</th><td><div class="nowrap" style="width: 150px;" title="'+ state.name+'">'+ state.name+'</div></td></tr>' +
            '<tr><th>编码</th><td><div class="nowrap" style="width: 150px;" title="'+ state.text+'">'+ state.text+'</div></td></tr>' +
            '</table>';

        if(state.pinyin){
            // html = '<div style="height: 36px;">' +
            // '<div style="float: left;width: 240px;
			// height:24px;background-color:#eee;font-size:16px" class="nowrap"
			// title="'+ state.name+'"><i class="fa fa-file-o"></i> '+
			// state.name+'</div>' +
            // '<div style="float: left;width: 110px; margin: 0 2px 2px 10px;"
			// class="nowrap" title="'+ state.text+'"> <i class="fa
			// fa-caret-right"></i> '+ state.text+'</div>'+
            // '<div style="float: left;width: 110px;" class="nowrap" title="'+
			// state.pinyin+'"> <i class="fa fa-caret-right"></i> '+
			// state.pinyin+'</div>'+
            // '</div>';

            html = '<table class="table2">' +
                '<tr><th>名称</th><td><div class="nowrap" style="width: 150px;" title="'+ state.name+'">'+ state.name+'</div></td></tr>' +
                '<tr><th>编码</th><td><div class="nowrap" style="width: 150px;" title="'+ state.text+'">'+ state.text+'</div></td></tr>' +
                '<tr><th>助记码</th><td><div class="nowrap" style="width: 150px;" title="'+ state.pinyin+'">'+ state.pinyin+'</div></td></tr>' +
                '</table>';
        }
        if(state.type == '0'){  // 疾病为主 加载疾病和治疗方式
            state.text_ = state.text_ == null?"":state.text_;
            state.name_ = state.name_ == null?"":state.name_;
            if(state.text_ == "")
                html = '<table class="table2">' +
                    '<tr>' +
                    '<th rowspan="2" style="width: 12%">疾病</th><td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.name+'">'+ state.name+'</div></td>' +
                    '<th rowspan="2" style="width: 12%">治疗<br/>方式</th><td rowspan="2" style="width: 38%"><div class="nowrap" style="width: 130px;">--</div></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.text+'">'+ state.text+'</div></td>' +
                    '</tr>' +
                    '</table>';
            else
                html = '<table class="table2">' +
                    '<tr>' +
                    '<th rowspan="2" style="width: 12%">疾病</th><td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.name+'">'+ state.name+'</div></td>' +
                    '<th rowspan="2" style="width: 12%">治疗<br/>方式</th><td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.name_+'">'+ state.name_+'</div></td>' +
                    '</tr>' +
                    '<tr>' +
                    '<td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.text+'">'+ state.text+'</div></td>' +
                    '<td style="width: 38%"><div class="nowrap" style="width: 130px;" title="'+ state.text_+'">'+ state.text_+'</div></td>' +
                    '</tr>' +
                    '</table>';
        }
        if (state.type == 'YLGXFL' || state.type == 'JXFL') {
        	state.text_ = state.text_ == null?"":"(" + state.text_ + ")";
            state.name_ = state.name_ == null?"":state.name_;
            /*html = '<table class="table2">' +
                '<tr>' +
                '<th style="text-align: center;">代码</th>' +
                '<th style="text-align: center;">名称</th>' +
                '<th style="text-align: center;">大类代码</th>' +
                '<th style="text-align: center;">大类名称</th>' +
                '</tr>' +
                '<tr>' +
                '<td style="text-align: center;"><div class="nowrap" style="width: 50px;" title="'+ state.text+'">'+ state.text+'</div></td>' +
                '<td style="text-align: center;"><div class="nowrap" style="width: 130px;" title="'+ state.name+'">'+ state.name+'</div></td>' +
                '<td style="text-align: center;"><div class="nowrap" style="width: 50px;" title="'+ state.text_+'">'+ state.text_+'</div></td>' +
                '<td style="text-align: center;"><div class="nowrap" style="width: 130px;" title="'+ state.name_+'">'+ state.name_+'</div></td>' +
                '</tr>' +
                '</table>';*/
            html = '<table class="table2">' +
            '<tr><th>名称</th><td><div class="nowrap" style="width: 150px;" title="'+ state.name+'">'+ state.name+'</div></td></tr>' +
            '<tr><th>编码</th><td><div class="nowrap" style="width: 150px;" title="'+ state.text+'">'+ state.text+'</div></td></tr>' +
            '<tr><th>大类</th><td><div class="nowrap" style="width: 150px;" title="'+ state.name_ + state.text_ + '">' + state.name_ + state.text_ + '</div></td></tr>' +
            '</table>';
        }
        // if(state.type == '1'){ //治疗方式为主 加载疾病和治疗方式
        // state.text_ = state.text_ == null?"":state.text_;
        // state.name_ = state.name_ == null?"":state.name_;
        // html = '<table class="table2">' +
        // '<tr>' +
        // '<th rowspan="2" style="width: 12%">治疗<br/>方式</th><td style="width:
		// 38%"><div class="nowrap" style="width: 130px;" title="'+
		// state.name+'">'+ state.name+'</div></td>' +
        // '<th rowspan="2" style="width: 12%">疾病</th><td style="width:
		// 38%"><div class="nowrap" style="width: 130px;" title="'+
		// state.name_+'">'+ state.name_+'</div></td>' +
        // '</tr>' +
        // '<tr>' +
        // '<td style="width: 38%"><div class="nowrap" style="width: 130px;"
		// title="'+ state.text+'">'+ state.text+'</div></td>' +
        // '<td style="width: 38%"><div class="nowrap" style="width: 130px;"
		// title="'+ state.text_+'">'+ state.text_+'</div></td>' +
        // '</tr>' +
        // '</table>';
        // }
        if (state.scataLogCode) {
            html =
                '<div class="nowrap" style="width: 100%;" title="'+ state.name+'">'+ state.name+'</div>';
        }
        return html;
    }

    function repoFormatSelection(state){
        return state.name;
    }

}(jQuery);