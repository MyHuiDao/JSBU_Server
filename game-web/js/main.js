var ctxPath = "/game-admin/";
var root = "/game-web";

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery'], factory);
    } else {
        // Browser globals.
        factory(jQuery);
    }
}(function ($) {

    var pluses = /\+/g;

    function encode(s) {
        return config.raw ? s : encodeURIComponent(s);
    }

    function decode(s) {
        return config.raw ? s : decodeURIComponent(s);
    }

    function stringifyCookieValue(value) {
        return encode(config.json ? JSON.stringify(value) : String(value));
    }

    function parseCookieValue(s) {
        if (s.indexOf('"') === 0) {
            // This is a quoted cookie as according to RFC2068, unescape...
            s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
        }

        try {
            // Replace server-side written pluses with spaces.
            // If we can't decode the cookie, ignore it, it's unusable.
            s = decodeURIComponent(s.replace(pluses, ' '));
        } catch (e) {
            return;
        }

        try {
            // If we can't parse the cookie, ignore it, it's unusable.
            return config.json ? JSON.parse(s) : s;
        } catch (e) {
        }
    }

    function read(s, converter) {
        var value = config.raw ? s : parseCookieValue(s);
        return $.isFunction(converter) ? converter(value) : value;
    }

    var config = $.cookie = function (key, value, options) {

        // Write
        if (value !== undefined && !$.isFunction(value)) {
            options = $.extend({}, config.defaults, options);

            if (typeof options.expires === 'number') {
                var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);
            }

            return (document.cookie = [
                encode(key), '=', stringifyCookieValue(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path ? '; path=' + options.path : '',
                options.domain ? '; domain=' + options.domain : '',
                options.secure ? '; secure' : ''
            ].join(''));
        }

        // Read

        var result = key ? undefined : {};

        // To prevent the for loop in the first place assign an empty array
        // in case there are no cookies at all. Also prevents odd result when
        // calling $.cookie().
        var cookies = document.cookie ? document.cookie.split('; ') : [];

        for (var i = 0, l = cookies.length; i < l; i++) {
            var parts = cookies[i].split('=');
            var name = decode(parts.shift());
            var cookie = parts.join('=');

            if (key && key === name) {
                // If second argument (value) is a function it's a converter...
                result = read(cookie, value);
                break;
            }

            // Prevent storing a cookie that we couldn't decode.
            if (!key && (cookie = read(cookie)) !== undefined) {
                result[name] = cookie;
            }
        }

        return result;
    };

    config.defaults = {};

    $.removeCookie = function (key, options) {
        if ($.cookie(key) !== undefined) {
            // Must not alter options, thus extending a fresh object...
            $.cookie(key, '', $.extend({}, options, {expires: -1}));
            return true;
        }
        return false;
    };

}));

function initHead(prefix) {
    if (prefix === null || prefix === undefined) {
        prefix = "";
    }
    if (getCookie("userId") == null) {
        if (window.parent) {
            window.parent.location.href = root + "/login.html";
        } else {
            window.location.href = root + "/login.html";
        }
    }
    var ajax = $.ajax;
    $.ajax = function (opt) {
        //备份opt中error和success方法
        var fn = {
            success: function (data, textStatus, jqXHR) {
            }
        };
        if (opt.success) {
            fn.success = opt.success;
        }
        //扩展增强处理
        var _opt = $.extend(opt, {
            success: function (data, textStatus, jqXHR) {
                if (opt.successExec) {
                    fn.success(data, textStatus, jqXHR);
                    return;
                }
                if (data.code == "-1") {
                    toastr.error(data.msg);
                    return;
                }
                if (data.code == "-2") {
                    toastr.error(data.msg);
                    toastr.call(function () {
                        if (window.parent) {
                            window.parent.location.href = root + "/login.html";
                        } else {
                            window.location.href = root + "/login.html";
                        }
                    });
                    return;
                }
                fn.success(data, textStatus, jqXHR);
            }
        });
        _opt.url = ctxPath + encodeURI(_opt.url);
        var def = ajax.call($, _opt);
        return def;
    };
    var headHtml = "";
    if (getCookie("userId")) {
        var codes = getPermissionCodes();
        var str = "";
        if (codes) {
            for (var i = 0; i < codes.length; i++) {
                str += ",." + codes[i];
            }

            if (str != "") {
                str = str.substring(1, str.length);
            }
            str += "{display:none !important;}";
            headHtml += '<style>' + str + '</style>';
        }
    }
    headHtml += '<style>.btn-group>.btn>.caret{margin-top:0px !important;}</style>';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'js/ztree/css/demo.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'js/ztree/css/zTreeStyle/zTreeStyle.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'js/toastr.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'css/style.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/bootstrap.min.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/font-awesome.min.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/ace-fonts.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/ace.min.css"';
    headHtml += '	id="main-ace-style" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/ace-custom.css" />';
    headHtml += '<link rel="stylesheet" href="' + prefix + 'assets/css/bootstrap-table.css" />';
    headHtml += '<!--[if lte IE 9]>';
    headHtml += '		<link rel="stylesheet" href="' + prefix + 'assets/css/ace-part2.min.css" />';
    headHtml += '	<![endif]-->';
    headHtml += '<!--[if lte IE 9]>';
    headHtml += '		 <link rel="stylesheet" href="' + prefix + 'assets/css/ace-ie.min.css" />';

    headHtml += '	<![endif]-->';
    headHtml += '<script src="' + prefix + 'assets/js/ace-extra.min.js"></script>';
    headHtml += '<!--[if lte IE 8]>';
    headHtml += '		<script src="' + prefix + 'assets/js/html5shiv.min.js"></script>';
    headHtml += '		<script src="' + prefix + 'assets/js/respond.min.js"></script>';
    headHtml += '	<![endif]-->';
    headHtml += '	<script type="text/javascript">';
    headHtml += '		if (\'ontouchstart\' in document.documentElement)';
    headHtml += '			document';
    headHtml += '					.write("<script src=\'' + prefix + 'assets/js/jquery.mobile.custom.min.js\'>"';
    headHtml += '							+ "<"+"/script>");';
    headHtml += '	</script>';
    headHtml += '	<script src="' + prefix + 'assets/js/bootstrap.min.js"></script>';
    headHtml += '	<!--[if lte IE 8]>';
    headHtml += '			  <script src="' + prefix + 'assets/js/excanvas.min.js"></script>';
    headHtml += '		<![endif]-->';
    headHtml += '	<script src="' + prefix + 'assets/js/jquery-ui.custom.min.js"></script>';
    headHtml += '	<script src="' + prefix + 'assets/js/jquery.ui.touch-punch.min.js"></script>';
    headHtml += '<script src="' + prefix + 'assets/js/jquery.validate.min.js" type="text/javascript"></script>';
    headHtml += '<script src="' + prefix + 'assets/js/jquery.validate.zh_ch.js"></script>';
    headHtml += '<script src="' + prefix + 'assets/js/bootstrap-table.js"></script>';
    headHtml += '<script src="' + prefix + 'assets/js/bootstrap-table-locale-all.min.js"></script>';
    headHtml += '<script src="' + prefix + 'js/ztree/js/jquery.ztree.core.js"></script>';
    headHtml += '<script src="' + prefix + 'js/ztree/js/jquery.ztree.excheck.js"></script>';
    headHtml += '<script src="' + prefix + 'assets/js/bootstrap-table-zh-CN.js"></script>';
    headHtml += '<script src="' + prefix + 'js/toastr.js"></script>';
    document.write(headHtml);
    //设置显示配置
    var messageOpts = {
        "closeButton": true,//是否显示关闭按钮
        "debug": false,//是否使用debug模式
        "positionClass": "toast-bottom-right",//弹出窗的位置
        "onclick": null,
        "showDuration": "300",//显示的动画时间
        "hideDuration": "1000",//消失的动画时间
        "timeOut": "5000",//展现时间
        "extendedTimeOut": "1000",//加长展示时间
        "showEasing": "swing",//显示时的动画缓冲方式
        "hideEasing": "linear",//消失时的动画缓冲方式
        "showMethod": "fadeIn",//显示时的动画方式
        "hideMethod": "fadeOut" //消失时的动画方式
    };
    var refreshFlag=[];
    $(function () {
        toastr.options = messageOpts;
        toastr.call = function (fun) {
            setTimeout(function () {
                fun();
            }, 1000)
        };
        $.fn.bootstrapTable.Constructor.prototype.refresh = function (params) {
            if(!refreshFlag[this]){
                refreshFlag[this]=true;
                params.pageNumber=1;
            }
            if (params && params.url) {
                this.options.url = params.url;
            }
            if (params && params.pageNumber) {
                this.options.pageNumber = params.pageNumber;
            }
            if (params && params.pageSize) {
                this.options.pageSize = params.pageSize;
            }
            this.initServer(params && params.silent,
                params && params.query, params && params.url);
            this.trigger('refresh', params);
        };
    });
}


function parentShowIframe(href) {
    parent.$("#showContent").attr("src", href);
}

/**
 * cookie操作
 */
/**
 * cookie操作
 */
function setCookie(name, value, time) {
    var exp = new Date();
    if (time == 0) {
        time = 365;
    }
    if (time != null) {
        $.cookie(name, value, {expires: time, path: '/'});
    } else {
        $.cookie(name, value, {path: '/'});
    }

}

function getCookie(name) {
    return $.cookie(name);
}

function delCookie(name) {
    $.removeCookie(name, {path: '/'});
}


function getRequest(pathName) {
    var url = location.search; // 获取url中"?"符后的字串
    var theRequest = {};
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURI((strs[i].split("=")[1]));
        }
    }
    return theRequest[pathName];
}


function addParamUrl(url, val) {
    if (url.indexOf("?") == -1) {
        url += "?" + val;
    } else {
        url += "&" + val;
    }
    return url;
}


function initInputHtml(obj, data) {
    $(obj).html("");
    var objs = $(obj);
    for (var i = 0; i < objs.length; i++) {
        var name = $(objs[i]).attr("name");
        var names = name.split(".");
        var str = initInputResult(data, names);
        $(objs[i]).html(str);
    }
}

function initInputVal(obj, data) {
    $(obj).val("");
    var objs = $(obj);
    for (var i = 0; i < objs.length; i++) {
        var name = $(objs[i]).attr("name");
        if (!name) {
            continue;
        }
        var names = name.split(".");
        var str = initInputResult(data, names);
        if ($(objs[i]).is("select")) {
            $(objs[i]).find("option").each(function () {
                if ($(this).val() == str) {
                    $(this).attr("selected", "selected");
                    return;
                }
            });
            continue;
        }
        $(objs[i]).val(str);
    }
}

function initInputResult(data, names) {
    var result = data;
    for (var j = 0; j < names.length; j++) {
        if (result === null || result === undefined) {
            return "";
        }
        result = result[names[j]];
    }
    return result;
}


function resetForm(jobj) {
    jobj[0].reset();
}


var showLevelZtree = function (treeObj, level) {
    if (treeObj == null) {
        return;
    }
    var node = treeObj.getNodes();
    var showLevel = function (node, level) {
        if (node != null) {
            for (var i = 0; i < node.length; i++) {
                if (node[i].level > level) {
                    continue;
                }
                treeObj.expandNode(node[i], true);
                var childrenNode = node[i].children;
                showLevel(childrenNode, level);
            }
        }
    };
    showLevel(node, level);
};

//获取相差月份
function getMonths(date1, date2) {
    //用-分成数组
    date1 = date1.split("-");
    date2 = date2.split("-");
    //获取年,月数
    var year1 = parseInt(date1[0]),
        month1 = parseInt(date1[1]),
        year2 = parseInt(date2[0]),
        month2 = parseInt(date2[1]),
    //通过年,月差计算月份差
        months = (year2 - year1) * 12 + (month2 - month1) + 1;
    return months;
}

//获取周
function getYearWeek(dayWeek) {
    var date = new Date(dayWeek);
    var date2 = new Date(date.getFullYear(), 0, 1);
    var day1 = date.getDay();
    if (day1 == 0) day1 = 7;
    var day2 = date2.getDay();
    if (day2 == 0) day2 = 7;
    var d = Math.round((date.getTime() - date2.getTime() + (day2 - day1) * (24 * 60 * 60 * 1000)) / 86400000);
    return Math.ceil(d / 7);
}

//获取相差天数
function getDays(strDateStart, strDateEnd) {
    var strSeparator = "-"; //日期分隔符
    var oDate1;
    var oDate2;
    var iDays;
    oDate1 = strDateStart.split(strSeparator);
    oDate2 = strDateEnd.split(strSeparator);
    var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
    var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
    iDays = parseInt(Math.abs(strDateS - strDateE) / 1000 / 60 / 60 / 24)//把相差的毫秒数转换为天数
    return iDays;
}

function DateAdd(interval, number, date) {
    /*
     *   功能:实现VBScript的DateAdd功能.
     *   参数:interval,字符串表达式，表示要添加的时间间隔.
     *   参数:number,数值表达式，表示要添加的时间间隔的个数.
     *   参数:date,时间对象.
     *   返回:新的时间对象.
     *   var   now   =   new   Date();
     *   var   newDate   =   DateAdd( "d ",5,now);
     *---------------   DateAdd(interval,number,date)   -----------------
     */
    switch (interval) {
        case   "y"   :
        {
            date.setFullYear(date.getFullYear() + number);
            return date;
        }
        case   "q"   :
        {
            date.setMonth(date.getMonth() + number * 3);
            return date;
        }
        case   "m"   :
        {
            date.setMonth(date.getMonth() + number);
            return date;
        }
        case   "w"   :
        {
            date.setDate(date.getDate() + number * 7);
            return date;
        }
        case   "d"   :
        {
            date.setDate(date.getDate() + number);
            return date;
        }
        case   "h"   :
        {
            date.setHours(date.getHours() + number);
            return date;
        }
        case   "m"   :
        {
            date.setMinutes(date.getMinutes() + number);
            return date;
        }
        case   "s"   :
        {
            date.setSeconds(date.getSeconds() + number);
            return date;
        }
        default   :
        {
            date.setDate(d.getDate() + number);
            return date;
        }
    }
}
//分页查询
//标签id,url,字段显示，需要传递的参数 {key:v,}
var grigFlag = [];
function paging_query(grid_selector, data_url, col_list, data) {
    var hrefUrl = window.location.href;
    var lastIndex = hrefUrl.lastIndexOf("#");
    if (lastIndex != -1) {
        hrefUrl = hrefUrl.substring(0, lastIndex);
    }
    if (window.parent) {
        if (!window.parent.query) {
            window.parent.query = [];
        }
    }
    var page = 1;
    var pageSize = 10;
    if (grigFlag[grid_selector] == null) {
        var reloadObj = window.parent.query[hrefUrl + grid_selector];
        if (reloadObj) {
            data_url = reloadObj['url'];
            var dataPage = reloadObj['data'];
            page = dataPage._page;
            pageSize = dataPage._size;
        }
    }

    var bootstrapTable1=$(grid_selector).bootstrapTable;
    $(grid_selector).bootstrapTable({
        //请求方法
        method: 'get',
        //是否显示行间隔色
        striped: true,
        //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        cache: false,
        //是否显示分页（*）
        pagination: true,
        //是否启用排序
        sortable: false,
        //初始化加载第一页，默认第一页
        //我设置了这一项，但是貌似没起作用，而且我这默认是0,- -
        //pageNumber:1,
        //每页的记录行数（*）
        pageSize: pageSize,
        pageNumber: page,
        //可供选择的每页的行数（*）
        pageList: [10, 25, 50, 100],
        clickToSelect: true, //是否启用点击选中行
        //这个接口需要处理bootstrap table传递的固定参数,并返回特定格式的json数据
        url: data_url,
        //默认值为 'limit',传给服务端的参数为：limit, offset, search, sort, order Else
        //queryParamsType:'',
        ////查询参数,每次调用是会带上这个参数，可自定义
        queryParams: function (params) {
            if (data == null) {
                data = {};
            }
            data._page = (params.offset + params.limit) / params.limit;
            data._size = params.limit;
            if (window.parent) {
                if (!window.parent.query) {
                    window.parent.query = [];
                }
            }
            if (grigFlag[grid_selector] == null) {
                var reloadObj = window.parent.query[hrefUrl + grid_selector];
                if (reloadObj) {
                    data = reloadObj['data'];
                }
            }
            grigFlag[grid_selector] = true;
            window.parent.query[hrefUrl + grid_selector] = {'url': data_url, 'data': data};
            return data;
        },
        responseHandler: function (res) {
            return {
                "total": res.data.records,//总记录数
                "rows": res.data.rows   //数据
            };
        },
        //分页方式：client客户端分页，server服务端分页（*）
        sidePagination: "server",
        //是否显示搜索
        search: false,
        //Enable the strict search.
        strictSearch: true,
        //Indicate which field is an identity field.
        idField: "id",
        columns: col_list
    });
}
//获取用户权限code,将code存储到cookie中
function getPermissionCodes() {
    var codes = null;
    $.ajax({
        url: "sysUser/getPermissionCodes",
        type: "get",
        async: false,
        success: function (msg) {
            codes = msg.data;
            setCookie("codes", msg.data);
        }
    });
    return codes;
}
