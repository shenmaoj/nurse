/**
 * Created by 78645 on 2019/1/7.
 */
!function ($) {
    "use strict";
    ajax('/sys/menu/availableMenu', 'POST`', {}, function (menus) {
        var li = '';
        for (var i = 0; i < menus.length; i++) {
            var one = menus[i];
            li += createMenuItem(one);
        }
        $("#menu").append(li);
    });
    function createMenuItem(menu) {
        if (menu.children) {
            var li = '<li> <a href="#" class="dropdown-toggle"><i class="' + menu.icon + '"></i><span class="menu-text">' + menu.name + '</span> <b class="arrow icon-angle-down"></b></a>';
            li += '<ul class="submenu">';
            for (var i = 0; i < menu.children.length; i++) {
                li += createMenuItem(menu.children[i]);
            }
            li += '</ui>';
            return li;
        } else {
            return '<li><a href="' + menu.url + '"><i class="' + menu.icon + '"></i> <span>' + menu.name + '</span></a>';
        }

    }
}(window.jQuery);