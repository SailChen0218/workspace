package com.ezddd.core.command;

import java.io.Serializable;

public class AbstractCommand implements Command, Serializable {
    private static final long serialVersionUID = -7893841488153415564L;
    protected String bizCode;
    protected String bizDetailCode;
}
