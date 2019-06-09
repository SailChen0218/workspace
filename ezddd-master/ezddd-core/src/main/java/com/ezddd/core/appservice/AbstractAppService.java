package com.ezddd.core.appservice;

import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandGateway;
import com.ezddd.core.command.query.QueryGateway;
import com.ezddd.core.response.AppResult;
import com.ezddd.core.response.CommandResult;

public abstract class AbstractAppService implements AppService {

    @EzRemoting
    protected CommandGateway commandGateway;

    @EzRemoting
    protected QueryGateway queryGateway;


    public AppResult<?> send(Command command, String successMsgCode, String errorMsgCode) {
        CommandResult result = commandGateway.send(command);
        if (!result.isSuccess()) {
            return AppResult.valueOfError(result.getExceptionContent(), errorMsgCode);
        } else {
            return AppResult.valueOfSuccess(result.getValue(), successMsgCode);
        }
    }

//    public <T> AppResult<T> query(QueryParameters queryParameters,
//                              String successMsgCode, String errorMsgCode, String emptyMsgCode) {
//        QueryResult<T> result = queryGateway.query(queryParameters);
//        if (!result.isSuccess()) {
//            return AppResult.valueOfError(result.getExceptionContent(), errorMsgCode);
//        } else {
//            if (result.isEmpty()) {
//                return AppResult.valueOfSuccess(result.getValue(), emptyMsgCode);
//            } else {
//                return AppResult.valueOfSuccess(result.getValue(), successMsgCode);
//            }
//        }
//    }
}
