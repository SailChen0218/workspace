package cn.com.servyou.task.vo;

/**
 * <p>
 * 工程: tygzt
 * </p>
 * <p>
 * 描述: cn.com.servyou.tygzt.vo.TaskVo
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
public class TaskVo {

    private String rwPk;
    private String systemId;
    private String systemName;
    private String privilegeId;
    private String swjgDm;
    private String gwDm;
    private String swryDm;
    private String sfyxsjbl;
    private String rwId;
    private String rwMs;
    private String fqsj;
    private String blsx;
    private String fqr;
    private String blUrl;
    private String rwfl;
    private String rwzt;
    private String dqzt;
    private String tbsj;
    private String description;
    private String url;
    private String priority;
    private String yxbz;

    public TaskVo() {}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGwDm() {
        return gwDm;
    }

    public void setGwDm(String gwDm) {
        this.gwDm = gwDm;
    }

    public String getSwryDm() {
        return swryDm;
    }

    public void setSwryDm(String swryDm) {
        this.swryDm = swryDm;
    }

    public String getRwPk() {
        return rwPk;
    }

    public void setRwPk(String rwPk) {
        this.rwPk = rwPk;
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

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getSwjgDm() {
        return swjgDm;
    }

    public void setSwjgDm(String swjgDm) {
        this.swjgDm = swjgDm;
    }

    public String getSfyxsjbl() {
        return sfyxsjbl;
    }

    public void setSfyxsjbl(String sfyxsjbl) {
        this.sfyxsjbl = sfyxsjbl;
    }

    public String getRwId() {
        return rwId;
    }

    public void setRwId(String rwId) {
        this.rwId = rwId;
    }

    public String getRwMs() {
        return rwMs;
    }

    public void setRwMs(String rwMs) {
        this.rwMs = rwMs;
    }

    public String getFqsj() {
        return fqsj;
    }

    public void setFqsj(String fqsj) {
        this.fqsj = fqsj;
    }

    public String getBlsx() {
        return blsx;
    }

    public void setBlsx(String blsx) {
        this.blsx = blsx;
    }

    public String getFqr() {
        return fqr;
    }

    public void setFqr(String fqr) {
        this.fqr = fqr;
    }

    public String getBlUrl() {
        return blUrl;
    }

    public void setBlUrl(String blUrl) {
        this.blUrl = blUrl;
    }

    public String getRwfl() {
        return rwfl;
    }

    public void setRwfl(String rwfl) {
        this.rwfl = rwfl;
    }

    public String getRwzt() {
        return rwzt;
    }

    public void setRwzt(String rwzt) {
        this.rwzt = rwzt;
    }

    public String getDqzt() {
        return dqzt;
    }

    public void setDqzt(String dqzt) {
        this.dqzt = dqzt;
    }

    public String getTbsj() {
        return tbsj;
    }

    public void setTbsj(String tbsj) {
        this.tbsj = tbsj;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getYxbz() {
        return yxbz;
    }

    public void setYxbz(String yxbz) {
        this.yxbz = yxbz;
    }

    @Override
    public String toString() {
        return "TaskVo{" +
                "rwPk='" + rwPk + '\'' +
                ", systemId='" + systemId + '\'' +
                ", systemName='" + systemName + '\'' +
                ", privilegeId='" + privilegeId + '\'' +
                ", swjgDm='" + swjgDm + '\'' +
                ", gwDm='" + gwDm + '\'' +
                ", swryDm='" + swryDm + '\'' +
                ", sfyxsjbl='" + sfyxsjbl + '\'' +
                ", rwId='" + rwId + '\'' +
                ", rwMs='" + rwMs + '\'' +
                ", fqsj='" + fqsj + '\'' +
                ", blsx='" + blsx + '\'' +
                ", fqr='" + fqr + '\'' +
                ", blUrl='" + blUrl + '\'' +
                ", rwfl='" + rwfl + '\'' +
                ", rwzt='" + rwzt + '\'' +
                ", dqzt='" + dqzt + '\'' +
                ", tbsj='" + tbsj + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", priority='" + priority + '\'' +
                ", yxbz='" + yxbz + '\'' +
                '}';
    }
}
