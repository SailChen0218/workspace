package com.ezshop.order.executor.exstension;


import com.ezddd.app.command.Command;
import com.ezddd.common.annotation.EzCommandExecutor;
import com.ezddd.common.response.AppResult;
import com.ezshop.order.command.HelloWorldCmd;
import com.ezshop.order.executor.HelloWorldCmdExe;

@EzCommandExecutor(commandType = HelloWorldCmd.class, priority = 1)
public class HBHelloWorlCmdExe extends HelloWorldCmdExe {
    @Override
    public AppResult<String> execute(Command cmd) {
        return AppResult.valueOfSuccess(methodTest());
    }
}
