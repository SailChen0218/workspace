package com.ezshop.domain.service;

import com.ezddd.core.annotation.EzDomainService;

@EzDomainService(interfaceType = UploadFile.class)
public class UploadFileImpl implements UploadFile {
    @Override
    public String upload() {
        return "upload finish...";
    }
}
