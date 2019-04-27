package com.ezddd.common.config;

import com.ezddd.common.annotation.EzComponent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;

@EzComponent
public class ServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int port;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.port = event.getWebServer().getPort();
    }

    public int getPort() {
        return port;
    }
}
