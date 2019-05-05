package com.ezshop.domain.service;

import com.ezddd.core.annotation.EzService;

@EzService(interfaceType = UploadFile.class)
public class UploadFileImpl implements UploadFile {
    @Override
    public String upload() {
        return "upload finish...";
    }
}
