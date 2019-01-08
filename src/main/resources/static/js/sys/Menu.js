/**
 * Created by shenmj on 2019/1/7.
 */


var Menu = {
    loadRender: function () {
        ajax('/sys/menu/jsTree', 'post', {}, function (json) {
            Menu.bulidTreeData(json);
        });
        Menu.laodDataTable("root_999");
        $("#tree_search").click(function () {
            $("#tree").jstree(true).search($("#tree_search_input").val());
        });
    },
    bulidTreeData: function (data) {
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
            plugins:["search"],
            //如果使用checkbox效率会降低, 'wholerow'会把线隐藏掉
        }).bind("select_node.jstree", function (e, data) {
            console.log(data);
            Menu.laodDataTable(data.node.id);
        })
    },
    laodDataTable: function (parentId) {
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
                {data: 'name', sortable: false, render: function (a, b, c, d) {
                        console.log(a, b, c, d);
                        return a;
                     }
                },
                {data: 'code', sortable: false},
                {data: 'icon', sortable: false},
                {data: 'url', sortable: false},
                {data: 'menuOrder', sortable: false},
                {data: 'authz', sortable: false}
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