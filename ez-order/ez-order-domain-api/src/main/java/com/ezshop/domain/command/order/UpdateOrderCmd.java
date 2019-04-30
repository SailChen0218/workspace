package com.ezshop.domain.command.order;

import com.ezddd.common.command.AbstractCommand;

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
public class UpdateOrderCmd extends AbstractCommand {
    private static final long serialVersionUID = 3458724402444187156L;
    private String id;
    private Instant createTime;
    private String commodity;
    private String postAddress;
}
