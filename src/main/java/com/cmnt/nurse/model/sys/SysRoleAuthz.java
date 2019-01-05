package com.cmnt.nurse.model.sys;

public class SysRoleAuthz {
    private String id;

    private String roleId;

    private String menuId;

    private String authz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getAuthz() {
        return authz;
    }

    public void setAuthz(String authz) {
        this.authz = authz == null ? null : authz.trim();
    }
}