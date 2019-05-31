package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.AbstractCommandBus;
import com.ezddd.core.command.Command;
import com.ezddd.core.response.CommandResult;

@EzComponent
public class AsynchronousCommandBus<T> extends AbstractCommandBus<T> {

    @Override
    public CommandResult<T> dispatch(Command cmd) {
        return null;
    }
}
