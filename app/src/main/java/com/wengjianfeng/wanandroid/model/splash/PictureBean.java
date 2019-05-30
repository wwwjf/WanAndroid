package com.wengjianfeng.wanandroid.model.splash;

public class PictureBean {

    /**
     * data : {"enddate":"20190530","url":"https://www4.bing.com/az/hprichbg/rb/Manhattanhenge_ZH-CN4659585143_1920x1080.jpg","bmiddle_pic":null,"original_pic":null,"thumbnail_pic":null}
     * status : {"code":200,"message":""}
     */

    private DataBean data;
    private StatusBean status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * enddate : 20190530
         * url : https://www4.bing.com/az/hprichbg/rb/Manhattanhenge_ZH-CN4659585143_1920x1080.jpg
         * bmiddle_pic : null
         * original_pic : null
         * thumbnail_pic : null
         */

        private String enddate;
        private String url;
        private Object bmiddle_pic;
        private Object original_pic;
        private Object thumbnail_pic;

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getBmiddle_pic() {
            return bmiddle_pic;
        }

        public void setBmiddle_pic(Object bmiddle_pic) {
            this.bmiddle_pic = bmiddle_pic;
        }

        public Object getOriginal_pic() {
            return original_pic;
        }

        public void setOriginal_pic(Object original_pic) {
            this.original_pic = original_pic;
        }

        public Object getThumbnail_pic() {
            return thumbnail_pic;
        }

        public void setThumbnail_pic(Object thumbnail_pic) {
            this.thumbnail_pic = thumbnail_pic;
        }
    }

    public static class StatusBean {
        /**
         * code : 200
         * message :
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
