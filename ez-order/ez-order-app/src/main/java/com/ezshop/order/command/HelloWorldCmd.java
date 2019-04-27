package com.ezshop.order.command;

import com.ezddd.app.command.AbstractCommand;
import com.ezddd.common.annotation.EzCommand;

@EzCommand
public class HelloWorldCmd extends AbstractCommand {
    private static final long serialVersionUID = 4085467105451536781L;
    private String msg;
    private String content;
}
