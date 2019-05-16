package com.ezddd.core.event;

import com.ezddd.core.repository.RepositoryProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractEventListener implements EventListener {
    @Autowired
    protected RepositoryProvider repositoryProvider;
}
