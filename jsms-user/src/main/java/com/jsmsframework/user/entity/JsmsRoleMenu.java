package com.jsmsframework.user.entity;


/**
 * @description 角色菜单表
 * @author huangwenjie
 * @date 2017-08-09
 */
public class JsmsRoleMenu {
    
    // 主键id
    private Integer roleMenuId;
    // 角色id
    private Integer roleId;
    // 菜单id
    private Integer menuId;
    
    public Integer getRoleMenuId() {
        return roleMenuId;
    }
    
    public void setRoleMenuId(Integer roleMenuId) {
        this.roleMenuId = roleMenuId ;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId(Integer roleId) {
        this.roleId = roleId ;
    }
    
    public Integer getMenuId() {
        return menuId;
    }
    
    public void setMenuId(Integer menuId) {
        this.menuId = menuId ;
    }
    
}