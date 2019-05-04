package com.ezshop.domain.command;

import com.ezddd.core.command.Command;

public class AbstractCommand implements Command {
    private String bizCode;
    private String bizDetailCode;
    public String getBizCode() {
        return bizCode;
    }
    public String getBizDetailCode() {
        return bizDetailCode;
    }
}
