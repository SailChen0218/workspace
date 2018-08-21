package cn.com.servyou.task.synchronizer.vo;

import java.io.Serializable;

/**
 * <p>
 * 工程: tygzt
 * </p>
 * <p>
 * 描述: cn.com.servyou.tygzt.vo.SystemVo
 * </p>
 * <p>
 * 版权:税友软件集团股份有限公司
 * </p>
 * <p>
 * 创建时间: 2018/5/15
 * </p>
 * <p>
 * 作者：cqf
 * </p>
 */
public class SystemVo {
    private String smNo;
    private String systemId;
    private String systemName;
    private String description;
    private String available;
    private String url;
    private String charset;
    private String smDm;
    private String smjkUri;
    private String zjsmsj;
    private String smIp;
    private String scopeMin;
    private String zjsmsjLimit;

    public String getSmNo() {
        return smNo;
    }

    public void setSmNo(String smNo) {
        this.smNo = smNo;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getZjsmsj() {
        return zjsmsj;
    }

    public void setZjsmsj(String zjsmsj) {
        this.zjsmsj = zjsmsj;
    }

    public String getSmDm() {
        return smDm;
    }

    public void setSmDm(String smDm) {
        this.smDm = smDm;
    }

    public String getSmjkUri() {
        return smjkUri;
    }

    public void setSmjkUri(String smjkUri) {
        this.smjkUri = smjkUri;
    }

    public String getSmIp() {
        return smIp;
    }

    public void setSmIp(String smIp) {
        this.smIp = smIp;
    }

    public String getScopeMin() {
        return scopeMin;
    }

    public void setScopeMin(String scopeMin) {
        this.scopeMin = scopeMin;
    }

    public String getZjsmsjLimit() {
        return zjsmsjLimit;
    }

    public void setZjsmsjLimit(String zjsmsjLimit) {
        this.zjsmsjLimit = zjsmsjLimit;
    }

    @Override
    public String toString() {
        return "SystemVo{" +
                "smNo='" + smNo + '\'' +
                ", systemId='" + systemId + '\'' +
                ", systemName='" + systemName + '\'' +
                ", description='" + description + '\'' +
                ", available='" + available + '\'' +
                ", url='" + url + '\'' +
                ", charset='" + charset + '\'' +
                ", smDm='" + smDm + '\'' +
                ", smjkUri='" + smjkUri + '\'' +
                ", zjsmsj='" + zjsmsj + '\'' +
                ", smIp='" + smIp + '\'' +
                ", scopeMin='" + scopeMin + '\'' +
                ", zjsmsjLimit='" + zjsmsjLimit + '\'' +
                '}';
    }
}
