package com.ezshop.domain.command.order;

import com.ezddd.common.command.AbstractCommand;
import com.ezddd.common.command.Command;

import java.time.Instant;

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
public class CreateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3163630996471381514L;
    private String commodity;
    private String postAddress;

    public String getCommodity() {
        return commodity;
    }

    public String getPostAddress() {
        return postAddress;
    }
}
