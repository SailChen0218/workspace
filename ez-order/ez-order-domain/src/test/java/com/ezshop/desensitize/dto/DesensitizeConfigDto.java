package com.ezshop.desensitize.dto;

import java.io.Serializable;

public class DesensitizeConfigDto implements Serializable {
    private static final long serialVersionUID = -8505473536505202734L;
    private String nodePath;
    private int configValue;

    public DesensitizeConfigDto() {
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public int getConfigValue() {
        return configValue;
    }

    public void setConfigValue(int configValue) {
        this.configValue = configValue;
    }

    @Override
    public String toString() {
        return "DesensitizeConfigDto{" +
                "nodePath='" + nodePath + '\'' +
                ", configValue=" + configValue +
                '}';
    }
}
