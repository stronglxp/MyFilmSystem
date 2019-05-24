package com.zmq.filmsystem.entity;

/**
 * 影院表
 */
public class Canema {

    // 影院id
    private Integer canemaId;
    // 影院名称
    private String canemaName;
    // 影院地址
    private String canemaAddress;
    // 影院联系方式
    private String canemaLine;
    // 影院的招牌
    private String canemaImgUrl;

    public Integer getCanemaId() {
        return canemaId;
    }

    public void setCanemaId(Integer canemaId) {
        this.canemaId = canemaId;
    }

    public String getCanemaName() {
        return canemaName;
    }

    public void setCanemaName(String canemaName) {
        this.canemaName = canemaName;
    }

    public String getCanemaAddress() {
        return canemaAddress;
    }

    public void setCanemaAddress(String canemaAddress) {
        this.canemaAddress = canemaAddress;
    }

    public String getCanemaLine() {
        return canemaLine;
    }

    public void setCanemaLine(String canemaLine) {
        this.canemaLine = canemaLine;
    }

    public String getCanemaImgUrl() {
        return canemaImgUrl;
    }

    public void setCanemaImgUrl(String canemaImgUrl) {
        this.canemaImgUrl = canemaImgUrl;
    }

    @Override
    public String toString() {
        return "Canema{" +
                "canemaId=" + canemaId +
                ", canemaName='" + canemaName + '\'' +
                ", canemaAddress='" + canemaAddress + '\'' +
                ", canemaLine='" + canemaLine + '\'' +
                ", canemaImgUrl='" + canemaImgUrl + '\'' +
                '}';
    }
}
