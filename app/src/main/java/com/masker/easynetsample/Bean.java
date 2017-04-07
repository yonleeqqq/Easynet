package com.masker.easynetsample;

import java.util.List;

/**
 * Author: masker.
 * Date: 2017/4/7.
 * Description :
 */

public class Bean {

    /**
     * error : false
     * results : [{"_id":"58e5c0fc421aa90d6bc75ac6","createdAt":"2017-04-06T12:15:56.949Z","desc":"第三方SDK集成库(授权|分享|支付)","publishedAt":"2017-04-07T12:02:31.316Z","source":"web","type":"Android","url":"http://reezy.me/2017-03-31/sdk3rd-authorize-share-payment/","used":true,"who":"ezy"},{"_id":"58e5c1da421aa90d6bc75ac7","createdAt":"2017-04-06T12:19:38.450Z","desc":"果冻肉效果的Toolbar","images":["http://img.gank.io/2cee21f4-aed8-43e7-a3b6-28a6175ff08a"],"publishedAt":"2017-04-07T12:02:31.316Z","source":"chrome","type":"Android","url":"https://github.com/Yalantis/JellyToolbar","used":true,"who":"Dear宅学长"},{"_id":"58e60002421aa90d611986a9","createdAt":"2017-04-06T16:44:50.760Z","desc":"一个封装多种特性的ViewPager","images":["http://img.gank.io/5b44d502-88d8-498b-8b33-892b0f4cfa65"],"publishedAt":"2017-04-07T12:02:31.316Z","source":"chrome","type":"Android","url":"https://github.com/alibaba/UltraViewPager","used":true,"who":"Dear宅学长"},{"_id":"58e6d5bd421aa90d611986af","createdAt":"2017-04-07T07:56:45.796Z","desc":"仅使用一张资源图片为 View 设置具有按下效果的背景-OneDrawable","images":["http://img.gank.io/c03bcc08-1421-4332-8df3-e5122fa77801"],"publishedAt":"2017-04-07T12:02:31.316Z","source":"chrome","type":"Android","url":"https://github.com/maoruibin/OneDrawable","used":true,"who":"咕咚"},{"_id":"58e6f89e421aa90d6f211e4c","createdAt":"2017-04-07T10:25:34.853Z","desc":"Android 粒子动画效果，做个有逼格的 App，就靠这些了！","publishedAt":"2017-04-07T12:02:31.316Z","source":"chrome","type":"Android","url":"https://github.com/glomadrian/Grav","used":true,"who":"代码家"},{"_id":"58e6f8e2421aa90d611986b1","createdAt":"2017-04-07T10:26:42.506Z","desc":"目前来看，用起来最简洁的指纹管理工具。","images":["http://img.gank.io/a533b415-d722-40e7-a22d-0e05fed38272"],"publishedAt":"2017-04-07T12:02:31.316Z","source":"chrome","type":"Android","url":"https://github.com/JesusM/FingerprintManager","used":true,"who":"代码家"},{"_id":"58e7073c421aa90d6bc75ad2","createdAt":"2017-04-07T11:27:56.114Z","desc":"压缩库，主要通过尺寸压缩和质量压缩，以达到清晰度最优","images":["http://img.gank.io/511b72e2-380a-4441-a5b7-35fb95bddaa1"],"publishedAt":"2017-04-07T12:02:31.316Z","source":"web","type":"Android","url":"https://github.com/nanchen2251/CompressHelper","used":true,"who":"LiuShilin"},{"_id":"58e4a70b421aa90d6bc75abe","createdAt":"2017-04-05T16:12:59.265Z","desc":"Android小票效果","images":["http://img.gank.io/8be2fb0e-deec-4bf7-9a03-a50ec61aed2d"],"publishedAt":"2017-04-06T12:06:10.17Z","source":"web","type":"Android","url":"https://github.com/vivian8725118/CardView","used":true,"who":"Vivian"},{"_id":"58e4cddf421aa90d611986a1","createdAt":"2017-04-05T18:58:39.528Z","desc":"详解Realm在Android上的使用","publishedAt":"2017-04-06T12:06:10.17Z","source":"web","type":"Android","url":"https://zhuanlan.zhihu.com/p/26173366","used":true,"who":"黎赵太郎"},{"_id":"58e4d067421aa90d66eef5b2","createdAt":"2017-04-05T19:09:27.997Z","desc":"Android应用继续瘦身，以及一些注意事项","publishedAt":"2017-04-06T12:06:10.17Z","source":"web","type":"Android","url":"http://blog.coderclock.com/2017/03/31/android/Android%E5%BA%94%E7%94%A8%E7%BB%A7%E7%BB%AD%E7%98%A6%E8%BA%AB%EF%BC%8C%E4%BB%A5%E5%8F%8A%E4%B8%80%E4%BA%9B%E6%B3%A8%E6%84%8F%E4%BA%8B%E9%A1%B9/","used":true,"who":"D_clock"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 58e5c0fc421aa90d6bc75ac6
         * createdAt : 2017-04-06T12:15:56.949Z
         * desc : 第三方SDK集成库(授权|分享|支付)
         * publishedAt : 2017-04-07T12:02:31.316Z
         * source : web
         * type : Android
         * url : http://reezy.me/2017-03-31/sdk3rd-authorize-share-payment/
         * used : true
         * who : ezy
         * images : ["http://img.gank.io/2cee21f4-aed8-43e7-a3b6-28a6175ff08a"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
