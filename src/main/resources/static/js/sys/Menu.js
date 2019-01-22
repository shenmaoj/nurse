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
                return
            }
            $("#modal-input").load("/page/sys/menu/menu-input.html", function () {
                $("#modal-input").modal({
                    show: 1,
                    backdrop: 'static'
                });
                $("#parentName").val(original.text)
            })

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
                    data: null, sortable: false, render: function (data, type, row, meta) {
                    return ' <label> <input name="checkbox" type="checkbox" value="' + data + '" class="ace" /><span class="lbl"> </span> </label>';
                }
                },
                {
                    data: 'name', sortable: false, render: function (a, b, c, d) {
                    console.log(a, b, c, d);
                    return a;
                }
                },
                {data: 'code', sortable: false},
                {data: 'icon', sortable: false},
                {data: 'url', sortable: false},
                {data: 'menuOrder', sortable: false},
                {
                    data: null, sortable: false, render: function () {
                    var btn = '<button type="button" class="btn btn-minier btn-primary mr5" title="修改"><i class="ace-icon glyphicon glyphicon-edit"></i>修改</button>';
                    btn += '<button type="button" class="btn btn-minier btn-danger mr5" title="删除"><i class="ace-icon glyphicon glyphicon-trash"></i>删除</button>';
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
    }
}