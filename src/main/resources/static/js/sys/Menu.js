/**
 * Created by shenmj on 2019/1/7.
 */


var Menu = {
    loadRender: function () {
        ajax('/sys/menu/jsTree', 'post', {}, function (json) {
            Menu.buildTreeData(json);
        });
        Menu.loadDataTable("root_999");
        $("#tree_search").click(function () {
            $("#tree").jstree(true).search($("#tree_search_input").val());
        });

        $("#new").click(function () {
            var original = $('#tree').jstree(true).get_selected(true)[0].original;
            if (!original.id) {
                CommonUtil.alert("请选中一条左侧菜单");
                return;
            }
            Menu.inputModal();
        });
    },
    buildTreeData: function (data) {
        $("#tree").jstree({
            core: {
                themes: {
                    "theme": "classic",
                    "dots": false,
                    "icons": false
                },
                data: data,
                ui: {
                    theme_name: "classic"
                },
                lang: {
                    loading: "目录加载中……"
                }
            },
            expand_selected_onload: true,
            plugins: ["search"]
        }).bind("select_node.jstree", function (e, data) {
            Menu.loadDataTable(data.node.id);
        })
    },
    loadDataTable: function (parentId) {
        $('#menu-table').DataTable({
            searching: false, //禁用搜索
            ordering: false, //禁用排序
            serverSide: true,
            processing: true,
            paging: true,
            autoWidth: false,
            destroy: true,
            ajax: {
                url: "/sys/menu/of/children",
                type: 'POST',
                contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
                data: function (d) {
                    d.parentId = parentId;
                    return d;
                }
            },
            dataType: "json",
            columns: [
                {data: null, sortable: false},
                {
                    data: "id", sortable: false, render: function (data, type, row, meta) {
                        return ' <label> <input name="checkbox" type="checkbox" value="' + data + '" class="ace" /><span class="lbl"> </span> </label>';
                    }
                },
                {
                    data: 'name', sortable: false, render: function (a, b, c, d) {
                        return a;
                    }
                },
                {data: 'code', sortable: false},
                {data: 'icon', sortable: false},
                {data: 'url', sortable: false},
                {data: 'menuOrder', sortable: false},
                {
                    data: "id", sortable: false, render: function (data) {
                        var btn = '<button type="button" onclick="Menu.inputRender(\''+data+'\')" class="btn btn-minier btn-primary mr5" title="修改"><i class="ace-icon glyphicon glyphicon-edit"></i>修改</button>';
                        btn += '<button type="button" onclick="Menu.delRender(\''+data+'\')" class="btn btn-minier btn-danger mr5" title="删除"><i class="ace-icon glyphicon glyphicon-trash"></i>删除</button>';
                        return btn;
                    }
                }
            ],
            rowCallback: function (row, data, iDisplayIndex) {
                var info = this.api().page.info();
                var page = info.page;
                var length = info.length;
                var index = (page * length + (iDisplayIndex + 1));
                $('td:eq(0)', row).html(index);
            },
            error: function (msg) {
            }
        });
    },

    inputModal: function(id){
        $("#modal-input").load("/page/sys/menu/menu-input.html", function () {
            $("#modal-input").modal({
                show: 1,
                backdrop: 'static'
            });
            $("#parentName").val(original.text);
            Menu.inputRender(id)
        })
    },
    inputRender: function (id) {
        if(id){
            ajax('/sys/menu/of/'+id,null,{},function (data) {
                $("#menu-form").setFormValue(data);
                if(data.authz){
                    var authz = data.authz.split(",");
                    authz.map(e =>
                    $('select[name="authz"]').find("option[value='"+e+"']").prop("selected","selected")
                    );

                }
            });
        }
        var authz = $('select[name="authz"]').bootstrapDualListbox({infoTextFiltered: '<span class="label label-purple label-lg">筛选</span>'});
        var container = authz.bootstrapDualListbox('getContainer');
        container.find('.btn').addClass('btn-info btn-bold');
        Menu.validForm();
        $("#confirm").click(function () {
            var valid = $('#menu-form').valid();
            if(valid){
                ajax('/sys/menu/save',null,$('#menu-form').buildFormData(),function () {
                    CommonUtil.alert('操作成功');
                    $("#tree").jstree(true).refresh();
                    $('#menu-table').DataTable().draw(false)
                })
            }
        })
        
    },
    validForm: function () {
        $('#menu-form').validate({
            errorElement: 'div',
            errorClass: 'help-block pull-right',
            focusInvalid: false,
            ignore: "",
            rules: {
                name: {
                    required: true
                },
                code: {
                    required: true,
                    remote:{
                        url: '/sys/menu/validCode',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            id: function () {
                                return $("#id").val()
                            }
                        }
                    }
                },
                menuOrder: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                name: {
                    required: "菜单名称为必填项"
                },
                code: {
                    required: "菜单编码为必填项"
                },
                menuOrder: {
                    required: "菜单排序为必填项",
                    digits: '菜单排序必须为数字'
                }
            },
            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },
            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                if (element.is('input[type=checkbox]') || element.is('input[type=radio]')) {
                    var controls = element.closest('div[class*="col-"]');
                    if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if (element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if (element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
            },
            submitHandler: function (form) {
            },
            invalidHandler: function (form) {
            }
        });
    },
    delRender: function (id) {
        var options = {
            url: '/sys/menu/del/'+id,
            data: {},
            tree: '#tree',
            table: '#menu-table'
        };
        CommonUtil.dangerAlert('是否删除该菜单及其子菜单',options)
    }
}