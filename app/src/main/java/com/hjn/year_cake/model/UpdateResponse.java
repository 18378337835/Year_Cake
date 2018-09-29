package com.hjn.year_cake.model;

/**
 * Created by YearCake on 2018/9/29.
 * description:
 * version v1.0 content:
 */

public class UpdateResponse {
    /**
     * code : 0
     * msg : succ
     * result : {"fileUrl":"http://up.jrweid.com/jrwd_ApplicationTreasure_v4.5.2_20170906_Ba.apk","isForceUpdate":"0","upCount":"3","updateDesc":"1、新增签到送积分功能 2、优化4.4版本部分功能","updateTitle":"发现新版本","versionNumber":"1.1.0"}
     */

    private String code;
    private String     msg;
    private ResultBean result;

    public String getCode() { return code;}

    public void setCode(String code) { this.code = code;}

    public String getMsg() { return msg;}

    public void setMsg(String msg) { this.msg = msg;}

    public ResultBean getResult() { return result;}

    public void setResult(ResultBean result) { this.result = result;}

    public static class ResultBean {
        /**
         * fileUrl : http://up.jrweid.com/jrwd_ApplicationTreasure_v4.5.2_20170906_Ba.apk
         * isForceUpdate : 0
         * upCount : 3
         * updateDesc : 1、新增签到送积分功能 2、优化4.4版本部分功能
         * updateTitle : 发现新版本
         * versionNumber : 1.1.0
         */

        private String fileUrl;
        private String isForceUpdate;
        private String upCount;
        private String updateDesc;
        private String updateTitle;
        private String versionNumber;

        public String getFileUrl() { return fileUrl;}

        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl;}

        public String getIsForceUpdate() { return isForceUpdate;}

        public void setIsForceUpdate(String isForceUpdate) { this.isForceUpdate = isForceUpdate;}

        public String getUpCount() { return upCount;}

        public void setUpCount(String upCount) { this.upCount = upCount;}

        public String getUpdateDesc() { return updateDesc;}

        public void setUpdateDesc(String updateDesc) { this.updateDesc = updateDesc;}

        public String getUpdateTitle() { return updateTitle;}

        public void setUpdateTitle(String updateTitle) { this.updateTitle = updateTitle;}

        public String getVersionNumber() { return versionNumber;}

        public void setVersionNumber(String versionNumber) { this.versionNumber = versionNumber;}
    }
}
