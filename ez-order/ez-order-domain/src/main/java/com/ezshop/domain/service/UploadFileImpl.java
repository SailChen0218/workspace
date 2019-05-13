package com.ezshop.domain.service;

import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.constants.Area;
import com.ezddd.core.remote.protocol.ProtocolType;

@EzDomainService(interfaceType = UploadFile.class, protocol = ProtocolType.HESSIAN, priority = 1, area = Area.HB)
public class UploadFileImpl implements UploadFile {
    @Override
    public String upload() {
        return "upload finish...";
    }
}
