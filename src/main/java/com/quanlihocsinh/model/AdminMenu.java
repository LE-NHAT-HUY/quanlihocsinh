package com.quanlihocsinh.model;

public class AdminMenu {
    private long AdminMenulD;
    private String ItemName;
    private int ItemLevel;
    private int ParentLevel;
    private int ItemOrder;
    private boolean IsActive;
    private String ItemTarget;
    private String AreaName;
    private String ControllerName;
    private String ActionName;
    private String Icon;
    private String IdName;

    // Getter / Setter
    public long getAdminMenulD() {
        return AdminMenulD;
    }

    public void setAdminMenulD(long adminMenulD) {
        this.AdminMenulD = adminMenulD;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }

    public int getItemLevel() {
        return ItemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.ItemLevel = itemLevel;
    }

    public int getParentLevel() {
        return ParentLevel;
    }

    public void setParentLevel(int parentLevel) {
        this.ParentLevel = parentLevel;
    }

    public int getItemOrder() {
        return ItemOrder;
    }

    public void setItemOrder(int itemOrder) {
        this.ItemOrder = itemOrder;
    }

    public boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean isActive) {
        this.IsActive = isActive;
    }

    public String getItemTarget() {
        return ItemTarget;
    }

    public void setItemTarget(String itemTarget) {
        this.ItemTarget = itemTarget;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        this.AreaName = areaName;
    }

    public String getControllerName() {
        return ControllerName;
    }

    public void setControllerName(String controllerName) {
        this.ControllerName = controllerName;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        this.ActionName = actionName;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        this.Icon = icon;
    }

    public String getIdName() {
        return IdName;
    }

    public void setIdName(String idName) {
        this.IdName = idName;
    }
}
