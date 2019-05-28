public class MonitorLogDto {
    private String tranId;
    private String channelId;
    private String tranServiceId;
    private String retCode;
    private long rt;

    public MonitorLogDto() {
    }

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTranServiceId() {
        return tranServiceId;
    }

    public void setTranServiceId(String tranServiceId) {
        this.tranServiceId = tranServiceId;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public long getRt() {
        return rt;
    }

    public void setRt(long rt) {
        this.rt = rt;
    }
}
