package com.ezshop.domain.service;

import com.ezddd.common.annotation.EzDomainService;

@EzDomainService
public class UploadFileImpl implements UploadFile {
    @Override
    public String upload() {
        return "upload finish...";
    }
}
