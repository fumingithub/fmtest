/**
 * Created by jinliang on 2017/1/3 0003.
 */

+function ($) {
    'use strict';

    // ZTREE-EXTEND GLOBAL ELEMENTS
    var TOP_CODE = "", TOP_NAME = "无上级";

    // ZTREE-EXTEND CLASS DEFINITION
    var ZTreeExtend = function (element, options) {
        this.$element = $(element);
        this.options  = options;
        this.setting = this.setSetting();
    };

    ZTreeExtend.DEFAULTS = {
        mode                : "singletree", // 默认：单独模式（三种模式：导航树模式-navtree、单独模式-singletree、表单元素模式-formtree）
        url                 : "",
        urlParam            : "",
        isasync             : false,
        ischeck             : false,
        issimpledata        : true,
        issearch            : false,
        searchId            : "name",
        nodeCode            : "code",
        nodeParentCode      : "pCode",
        nodeName            : "name",
        nodeUrl             : "",
        divid               : "sonList",
        hidParentCode       : "hid_pCode",
        hidParentName       : "hid_pName"
    };

    ZTreeExtend.prototype.setSetting = function () {
        var that = this, options = $.extend({}, ZTreeExtend.DEFAULTS, that.options);
        var url = options.url + "?isasync=" + options.isasync;
        var urlParams = options.urlParam.split(","), len = urlParams.length;
        if ("" != options.urlParam && len > 0) {
            for (var i = 0; i < len; i++) {
                if ($("#" + urlParams[i]).length) {
                    url += "&" + urlParams[i] + "=" + $("#" + urlParams[i]).val();
                }
            }
        }
        var setting = {
            async: {
                autoParam: ["id"],
                enable: options.isasync,
                dataType: "text",
                url: url,
                dataFilter: function (treeId, parentNode, responseData) {
                    return that.zTreeFilter(responseData, options, treeId, parentNode);
                },
                type: "post"
            },
            callback: {
                onNodeCreated: function (event, treeId, treeNode) {
                    that.zTreeOnNodeCreated(event, treeId, treeNode, options);
                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    that.zTreeOnAsyncSuccess(event, treeId, treeNode, msg);
                },
                onAsyncError: function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
                    that.zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown);
                },
                beforeClick: function (treeId, treeNode, clickFlag) {
                    that.zTreeBeforeClick(treeId, treeNode, clickFlag);
                },
                onClick: function (event, treeId, treeNode) {
                    that.zTreeOnClick(event, treeId, treeNode, options);
                },
                onDblClick: function (event, treeId, treeNode) {
                    that.zTreeOnDblClick(event, treeId, treeNode, options);
                },
                onCheck: function (event, treeId, treeNode) {
                    that.zTreeOnCheck(event, treeId, treeNode);
                }
            },
            check: {
                enable: options.ischeck,
                chkStyle: "radio"
            },
            data: {
                simpleData: {
                    enable: options.issimpledata,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: null
                }
            }
        }
        return setting;
    };

    ZTreeExtend.prototype.zTreeFilter = function (responseData, options, treeId, parentNode) {
        var arr = [], sum = 0;
        if (responseData) {
            var nodeCode = "", nodeParentCode = "", nodeName = "", nodeObj = null;
            for (var i = 0; i < responseData.length; i++) {
                nodeCode = responseData[i][options.nodeCode];
                nodeParentCode = responseData[i][options.nodeParentCode];
                nodeName = responseData[i][options.nodeName];

                nodeObj = {
                    id: nodeCode,
                    pid: nodeParentCode,
                    name: nodeName
                };

                if ("navtree" == options.mode) {
                    if (nodeCode == "-1") {
                        nodeObj.isParent = true;
                        nodeObj.chkDisabled = true;
                    } else {
                        if (options.isasync) {
                            nodeObj.isParent = true;
                        }
                        nodeObj.url = options.nodeUrl + nodeCode;
                        nodeObj.divid = options.divid;
                    }
                } else {
                    if (options.isasync) {
                        nodeObj.isParent = true;
                    }
                }
                arr[i] = nodeObj;

                if (null == nodeParentCode) {
                    ++sum;
                }
            }
            if (sum == 1) {
                if ("none" != responseData[0].rootId) {TOP_CODE = responseData[0].rootId;}
                if ("none" != responseData[0].rootName) {TOP_NAME = responseData[0].rootName;}
            }
        }
        return arr;
    };

    ZTreeExtend.prototype.zTreeOnNodeCreated = function (event, treeId, treeNode, options) {
        // isSearch为true时生效
        if (options.issearch) {
            var name = $.trim($("#" + options.searchId).val()); // 被搜索的名称
            var treeObj = $.fn.zTree.getZTreeObj(treeId);

            // 只有搜索参数不为空且该节点为父节点时才进行异步加载
            if(name != "" && treeNode.isParent){
                treeObj.reAsyncChildNodes(treeNode, "refresh");
            }
        }
    };

    ZTreeExtend.prototype.zTreeOnAsyncSuccess = function (event, treeId, treeNode, msg) {
        // todo 优化无匹配结果的显示 目前存在问题 但不影响使用
        if ("" == $("#" + treeId).html()) {
            $("#" + treeId).text("无匹配的结果");
        }
    };

    ZTreeExtend.prototype.zTreeOnAsyncError = function (event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {

    };

    ZTreeExtend.prototype.zTreeBeforeClick = function (treeId, treeNode, clickFlag) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        zTree.checkNode(treeNode, !treeNode.checked, true);
        return true;
    };

    ZTreeExtend.prototype.zTreeOnClick = function (event, treeId, treeNode, options) {
//        var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
//        zTreeObj.expandNode(treeNode);

        // 导航树模式
        if ("navtree" == options.mode) {
            $("#" + options.hidParentCode).val(treeNode.id);
            $("#" + options.hidParentName).val(treeNode.name);
            $(event.target).bjuiajax('doLoad', {url: treeNode.url, target: treeNode.divid, loadingmask: true});
            event.preventDefault();
        }
    };

    ZTreeExtend.prototype.zTreeOnDblClick = function (event, treeId, treeNode, options) {
        if ("" != options.onDblClick) {
            eval(options.onDblClick + "(event, treeId, treeNode, options);");
        }
    };

    ZTreeExtend.prototype.zTreeOnCheck = function (event, treeId, treeNode) {

    };

    ZTreeExtend.prototype.init = function (options) {
        var that = this, $element = that.$element, setting = that.setting;
        if (options.isasync) { // 异步加载
            // 默认根结点
            // var rootNode = {"id":"-1", "pid":null, "name":"顶级节点", "open":true, "isParent":true};
            // $.fn.zTree.init($element, setting, rootNode);
            $.fn.zTree.init($element, setting);
        } else { // 一次性加载所有节点
            options = $.extend({}, ZTreeExtend.DEFAULTS, options);

            var url = options.url + "?isasync=" + options.isasync;
            var urlParams = options.urlParam.split(","), len = urlParams.length;
            if ("" != options.urlParam && len > 0) {
                for (var i = 0; i < len; i++) {
                    if ($("#" + urlParams[i]).length) {
                        url += "&" + urlParams[i] + "=" + $("#" + urlParams[i]).val();
                    }
                }
            }

            $.post(url, {}, function(data){
                var arr = that.zTreeFilter(data, options);
                var nodes = JSON.stringify(arr);
                nodes = JSON.parse(nodes);
                $.fn.zTree.init($element, setting, nodes);
            });
        }
    };

    ZTreeExtend.prototype.searchTree = function (options) {
        var name = $.trim($("#" + options.searchId).val());
        var treeObj = $.fn.zTree.getZTreeObj(this.$element.attr("id"));

        if (options.isasync) {
            var node = treeObj.getNodeByParam("id", TOP_CODE, null);
            if("" != name){
                name = encodeURI(encodeURI(name));
                treeObj.setting.async.otherParam = ["name", name];
            } else {
                // 搜索参数为空时必须将参数数组设为空
                treeObj.setting.async.otherParam = [];
            }
            treeObj.reAsyncChildNodes(node, "refresh");
        } else {
            var nodes = treeObj.getNodes();
            if ("" != name) {
                treeObj.hideNodes(nodes[0].children);
                var filterNodes = treeObj.getNodesByParamFuzzy("name", name, null);
                if (filterNodes.length) {
                    treeObj.showNodes(filterNodes);
                    treeObj.expandAll(true);
                }
            } else {
                treeObj.showNodes(nodes[0].children);
            }
        }
    };

    // ZTREE-EXTEND PLUGIN DEFINITION
    function Plugin(option) {
        var args = arguments;
        var property = option;

        return this.each(function () {
            var $this   = $(this);
            var options = $.extend({}, ZTreeExtend.DEFAULTS, $this.data(), typeof option == "object" && option);
            var data = new ZTreeExtend(this, options);
            if (typeof property == "string" && $.isFunction(data[property])) {
                [].shift.apply(args);
                if (!args) {
                    data[property]();
                } else {
                    data[property].apply(data, args);
                }
            }
        });
    }

    var old = $.fn.zTreeExtend;

    $.fn.zTreeExtend             = Plugin;
    $.fn.zTreeExtend.Constructor = ZTreeExtend;

    // ZTREE-EXTEND NO CONFLICT
    $.fn.zTreeExtend.noConflict = function () {
        $.fn.zTreeExtend = old;
        return this;
    };

    // PAGE INIT LOAD ZTREE
    $(document).on(BJUI.eventType.initUI, function(e) {
        var $this = $(e.target).find('ul[data-toggle="ztree_extend"]');
        if ($this.length) {
            var options = $this.data();
            Plugin.call($this, "init", options);
            e.preventDefault();

            setTimeout(function () {
                // DEFAULT : EXPAND SECOND LEVEL
                if ("" != TOP_CODE) {
                    var zTreeObj = $.fn.zTree.getZTreeObj($this.attr("id"));
                    var node = zTreeObj.getNodeByParam("id", TOP_CODE, null);
                    zTreeObj.expandNode(node);
                } else {
                    TOP_NAME = "无上级";
                }

                // 导航树模式
                if ("navtree" == options.mode) {
                    // DEFAULT : RIGHT LIST LOAD ROOT AND NEXT LEVEL DATA
                    $("#" + options.hidParentCode).val(TOP_CODE);
                    $("#" + options.hidParentName).val(TOP_NAME);
                    $(e.target).bjuiajax('doLoad',
                        {
                            url: options.nodeUrl + TOP_CODE,
                            target: options.divid,
                            loadingmask: true
                        }
                    );
                    e.preventDefault();
                }
            }, 200);

            // BIND SEARCH TREE BUTTON CLICK EVENT
            if (options.issearch) {
                $("#" + options.searchId + "Search").click( function (e) {
                    Plugin.call($this, "searchTree", options);
                    e.preventDefault();
                });
                $("#" + options.searchId).keydown( function (e) {
                    if (e.which === 13) {
                        Plugin.call($this, "searchTree", options);
                        e.preventDefault();
                    }
                });
            } else {
                $(".treeSearch").css("display", "none");
            }
        }
    });

}(window.jQuery);