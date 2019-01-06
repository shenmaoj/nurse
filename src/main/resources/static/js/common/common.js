(function ($) {
    $.fn.setFormValue = function (object) {
        var formId = $(this).attr("id");
        $.each($("#" + formId).find('[name]'), function (i, e) {
            var type = $(this)[0].nodeName.toLowerCase();
            var name = $(this).attr('name');
            if (object['' + name + ''] != null && object['' + name + ''] != '') {
                if (type == "textarea") {
                    $("#" + formId + " " + type + "[name='" + name + "']").text(object['' + name + '']);
                } else {
                    if ($(this).attr('type') == 'radio') {
                        if ($(this).val() == object['' + name + '']) {
                            $(this).attr("checked", true);
                        }
                    } else {
                        $("#" + formId + " " + type + "[name='" + name + "']").val(object['' + name + '']);
                    }
                }
            }
        });
        return this;
    };
    $.fn.buildFormData = function (otherData) {
        var formData = {};
        $(this).find("[name]").each(function (i, e) {
            var value = "";
            var type = $(this).attr('type');
            var name = $(this).attr('name');
            if (type === "textarea") {
                value = $(this).text();
            } else {
                if (type === "radio" || type === "checkbox") {
                    value = $("input:radio[name=" + name + "]:checked").val();
                } else {
                    value = $(this).val();
                }
            }
            if (value !== "") {
                formData[$(e).attr("name")] = value;
            }
        });
        if (otherData) {
            $.each(otherData, function (name, value) {
                formData[name] = value;
            });
        }
        return formData;
    };
})(jQuery);
$(function () {
    $.ajaxSetup({
        cache: false,
        async: false,
        contentType: "application/json;charset=utf-8",
    });
    $("#login-out-btn").click(function () {
        window.location.href = "/loginOut.php";
    });
});
var CommonUtil = {
    alert: function (text, pattern) {
        // pattern success成功、error失败
        swal("操作提示!", text, pattern);
    },
    warningAlert: function (options) {
        swal({
            title: "操作提示",
            text: options.text,
            type: "warning",
            showCancelButton: true,
            cancelButtonClass: 'btn-secondary waves-effect',
            cancelButtonText: '取消',
            confirmButtonClass: 'btn-danger waves-effect waves-light',
            confirmButtonText: '确定',
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: options.url,
                type: "POST",
                data: options.data,
                dataType: "json",
                contentType: 'application/json;charset=UTF-8',
                success: function (json) {
                    if (json && success_code == json.resCode && json.data) {
                        if (options.tree) {
                            $(options.tree).jstree(true).refresh();
                        }
                        if (options.table) {
                            $(options.table).DataTable().draw(false);
                        }
                        commonModal.alert(option_success, pattern[0]);
                    } else {
                        commonModal.alert(option_fail, pattern[1]);
                    }
                }
            });
        });
    },

    dangerAlert: function (text, options) {
        swal({
            title: "操作提示",
            text: options.text,
            type: "error",
            showCancelButton: true,
            cancelButtonClass: 'btn-secondary waves-effect',
            cancelButtonText: '取消',
            confirmButtonClass: 'btn-danger waves-effect waves-light',
            confirmButtonText: '确定',
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: options.url,
                type: "POST",
                data: options.data,
                dataType: "json",
                contentType: 'application/json;charset=UTF-8',
                success: function (json) {
                    if (json && success_code == json.resCode && json.data) {
                        if (options.tree) {
                            $(options.tree).jstree(true).refresh();
                        }
                        if (options.table) {
                            $(options.table).DataTable().draw(false);
                        }
                        commonModal.alert(option_success, pattern[0]);
                    } else {
                        commonModal.alert(option_fail, pattern[1]);
                    }
                }
            });
        });
    },

    timeAlert: function (text, timer) {
        // timer 秒
        swal({
            title: "操作提示!",
            text: text,
            timer: timer,
            showConfirmButton: false
        });
    },
};

function chose_mult_set_ini(select, values) {
    var arr = values.split(',');
    var length = arr.length;
    var value = '';
    for (i = 0; i < length; i++) {
        value = arr[i];
        $(select + " option[value='" + value + "']").attr('selected', 'selected');
    }
    $(select).trigger("chosen:updated");
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
var messageAlert = {
    success: function (title, msg) {
        Command: toastr["success"](msg, (title == "" ? "提示" : title))
    },
    warning: function (title, msg) {
        Command: toastr["warning"](msg, (title == "" ? "提示" : title))
    },
    error: function (title, msg) {
        Command: toastr["error"](msg, (title == "" ? "提示" : title))
    },
    info: function (title, msg) {
        Command: toastr["info"](msg, (title == "" ? "提示" : title))
    }
}
function initChose(element) {
    $(element).chosen({
        no_results_text: "没有匹配到",
        width: "100%",
        search_contains: true
    });
}
function formatDictionary(dictionaryCode, value) {
    var id = "hide_" + dictionaryCode;
    if ($("body").find("#" + id).length === 0) {
        var param = {
            "dictionaryCode": dictionaryCode,
        };
        $.ajax({
            type: 'POST',
            async: false,
            url: "/system-dictionary/getDictionary/request.php",
            dataType: "json",
            data: JSON.stringify(param),
            contentType: 'application/json;charset=UTF-8',
            success: function (json) {
                var html = "";
                html += '<ul id="' + id + '" style="display:none;">';
                for (var i = 0; i < json.data.length; i++) {
                    html += '<li id="hide_' + json.data[i].code + '">' + json.data[i].name + '</li>';
                }
                html += '</ul>';
                $("body").append(html);
            }
        });
    }
    var text = $("#" + id).find("#hide_" + value).html();
    if (text) {
        return text;
    } else {
        return value;
    }
}
function formatDictionaryName(dictionaryCode, value) {
    var name = value;
    var id = "hide_" + dictionaryCode;
    if ($("body").find("#" + id).length === 0) {
        var param = {
            "dictionaryCode": dictionaryCode,
        };
        $.ajax({
            type: 'POST',
            async: false,
            url: "/system-dictionary/getDictionary/request.php",
            dataType: "json",
            data: JSON.stringify(param),
            contentType: 'application/json;charset=UTF-8',
            success: function (json) {
                var html = "";
                html += '<ul id="' + id + '" style="display:none;">';
                for (var i = 0; i < json.data.length; i++) {
                    html += '<li id="hide_' + json.data[i].code + '">' + json.data[i].name + '</li>';
                }
                html += '</ul>';
                $("body").append(html);
            }
        });
    }

    $.each($("#" + id).find("li"), function () {
        if ($(this).text() === value) {
            name = $(this).attr("id").replace("hide_", "");
        }
    });

    return name;

}
function initDictionary(element, dictionaryCode, parentCode, level) {
    var param = {
        "dictionaryCode": dictionaryCode,
    }
    if (null !== parentCode) {
        param.parentCode = parentCode;
    }
    if (null !== level) {
        param.level = level;
    }
    $.ajax({
        type: 'POST',
        async: false,
        url: "/system-dictionary/getDictionary/request.php",
        dataType: "json",
        data: JSON.stringify(param),
        contentType: 'application/json;charset=UTF-8',
        success: function (json) {
            var html = "";
            if (json && success_code === json.resCode && json.data && json.data.length > 0) {
                for (var i = 0; i < json.data.length; i++) {
                    html += '<option value="' + json.data[i].code + '">' + json.data[i].name + '</option>';
                }
            }
            $(element).append(html);
        }
    });
}
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

function getTableCheckRowData(element) {
    var rowsObject = [];
    var checkBoxs = $(element).children("tbody").find("input[type='checkbox']:checked");
    $.each(checkBoxs, function (i, e) {
        var rowIndex = $(e).closest("tr").index();
        var rowData = $(element).DataTable().row(rowIndex).data();
        rowsObject.push(rowData);
    });
    return rowsObject;
}
$.fn.datepicker.dates['zh-CN'] = {
    days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
    daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
    daysMin: ["日", "一", "二", "三", "四", "五", "六"],
    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    today: "今日",
    clear: "清除",
    format: "yyyy-mm-dd",
    titleFormat: "yyyy-mm",
    weekStart: 1
};
jQuery.extend(jQuery.validator.messages, {
    required: "必填字段",
    remote: "请修正该字段",
    email: "请输入正确格式的电子邮件",
    url: "请输入合法的网址",
    date: "请输入合法的日期",
    dateISO: "请输入合法的日期 (ISO).",
    number: "请输入合法的数字",
    digits: "只能输入整数",
    creditcard: "请输入合法的信用卡号",
    equalTo: "请再次输入相同的值",
    accept: "请输入拥有合法后缀名的字符串",
    maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
    minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
    rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
    range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
    max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
    min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

function ajax(url,type,data,callback) {
    $.ajax({
        url: url,
        type: type,
        data: JSON.stringify(data),
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        success: function (json) {
            if(json.resCode === success_code ){
                callback.call(this,json.data);
            }
        },
        error: function (e) {
            CommonUtil.alert(e,"error");
        }
    })
}
