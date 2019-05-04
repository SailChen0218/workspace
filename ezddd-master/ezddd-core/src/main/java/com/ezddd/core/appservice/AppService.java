package com.ezddd.core.appservice;

import com.ezddd.core.command.Command;
import com.ezddd.core.response.AppResult;

public interface AppService {
    <R> AppResult<R> send(Command command);
}
