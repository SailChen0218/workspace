package com.ezshop.order.executor;


import com.ezddd.app.command.AbstractCommandExecutor;
import com.ezddd.app.command.Command;
import com.ezddd.common.annotation.EzCommandExecutor;
import com.ezddd.common.annotation.EzRemoting;
import com.ezddd.common.response.AppResult;
import com.ezshop.domain.service.UploadFile;
import com.ezshop.order.command.GoodsAddCmd;

@EzCommandExecutor(commandType = GoodsAddCmd.class)
public class GoodsAddCmdExe extends AbstractCommandExecutor {

    @EzRemoting
    UploadFile uploadFile;

    @Override
    public AppResult<String> execute(Command cmd) {
        try {
            return AppResult.valueOfSuccess(uploadFile.upload());
        } catch (Exception ex) {
            return AppResult.valueOfSuccess(ex.getMessage());
        }
    }
}
