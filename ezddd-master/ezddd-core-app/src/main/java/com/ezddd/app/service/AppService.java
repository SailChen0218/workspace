package com.ezddd.app.service;

import com.ezddd.common.command.Command;
import com.ezddd.common.response.AppResult;

public interface AppService {
    <R> AppResult<R> send(Command command);
}
