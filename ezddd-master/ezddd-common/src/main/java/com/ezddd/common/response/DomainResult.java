package com.ezddd.common.response;

import java.util.HashMap;
import java.util.Map;

public class DomainResult<T> extends AbstractResult {
    private static final long serialVersionUID = 8824421108721269926L;

    private Map<String, Object> attachments;

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void addAttachments(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        if (this.attachments == null) {
            this.attachments = new HashMap<String, Object>();
        }
        this.attachments.putAll(map);
    }

    public <E> E getAttachment(String key) {
        return (E)attachments.get(key);
    }

    public <E> E getAttachment(String key, E defaultValue) {
        E result = (E)attachments.get(key);
        if (result == null) {
            result = defaultValue;
        }
        return result;
    }

    public void setAttachment(String key, Object value) {
        attachments.put(key, value);
    }

    public void setAttachments(Map<String, Object> map) {
        this.attachments = map == null ? new HashMap<String, Object>() : map;
    }
}
