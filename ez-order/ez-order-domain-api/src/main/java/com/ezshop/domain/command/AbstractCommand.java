package com.ezshop.domain.command;

import com.ezddd.common.command.Command;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2019/4/30</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
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
