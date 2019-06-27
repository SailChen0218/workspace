package com.ezshop.desensitize.dto;

import java.io.Serializable;

public class ParameterConfigDto implements Serializable {
    private static final long serialVersionUID = 4512173850185742452L;
    private String name;
    private int configValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfigValue() {
        return configValue;
    }

    public void setConfigValue(int configValue) {
        this.configValue = configValue;
    }

    @Override
    public String toString() {
        return "ParameterConfigDto{" +
                "name='" + name + '\'' +
                ", configValue=" + configValue +
                '}';
    }
}
