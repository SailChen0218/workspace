package com.ezddd.core.appservice;

import com.ezddd.core.command.Command;
import com.ezddd.core.response.AppResult;
import com.ezddd.core.response.CommandResult;

public interface AppService {
    CommandResult<?> send(Command command);
}
