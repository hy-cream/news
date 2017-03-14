package com.example.huyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huyu on 2017/3/13.
 */

public class XiaBean implements Serializable{

    private boolean error;
    private List<Results> results;

    public void setError(boolean error) {
        this.error = error;
    }
    public boolean getError() {
        return error;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
    public List<Results> getResults() {
        return results;
    }
    public class Results {

        private String Id;
        private String createdat;
        private String desc;
        private List<String> images;
        private String publishedat;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        public void setId(String Id) {
            this.Id = Id;
        }
        public String getId() {
            return Id;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }
        public String getCreatedat() {
            return createdat;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
        public String getDesc() {
            return desc;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
        public List<String> getImages() {
            return images;
        }

        public void setPublishedat(String publishedat) {
            this.publishedat = publishedat;
        }
        public String getPublishedat() {
            return publishedat;
        }

        public void setSource(String source) {
            this.source = source;
        }
        public String getSource() {
            return source;
        }

        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }
        public boolean getUsed() {
            return used;
        }

        public void setWho(String who) {
            this.who = who;
        }
        public String getWho() {
            return who;
        }

    }
}
