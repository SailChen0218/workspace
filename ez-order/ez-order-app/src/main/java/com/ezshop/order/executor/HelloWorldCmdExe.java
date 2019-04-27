package com.ezshop.order.executor;

import com.ezddd.app.command.AbstractCommandExecutor;
import com.ezddd.app.command.Command;
import com.ezddd.common.annotation.EzCommandExecutor;
import com.ezddd.common.response.AppResult;
import com.ezshop.order.command.HelloWorldCmd;

@EzCommandExecutor(commandType = HelloWorldCmd.class)
public class HelloWorldCmdExe extends AbstractCommandExecutor {

//    @Autowired
//    HelloWorldEntity helloWorldEntity;

    @Override
    public AppResult<String> execute(Command cmd) {
        return AppResult.valueOfSuccess("command success!!!!!");
    }


    public String methodTest() {
        return "methodTest";
    }
}
