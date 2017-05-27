package googleplay.kimda.com.googleplay.beans;

import java.util.List;

/**
 * Created by BUTTON on 2017-05-27.
 */
public class HomeBean {
    private List<String> picture;
    private List<ListBean> list;

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<String> getPicture() {
        return picture;
    }

    public List<ListBean> getList() {
        return list;
    }

    public static class ListBean {
        private int id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private int size;
        private String downloadUrl;
        private String des;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public float getStars() {
            return stars;
        }

        public int getSize() {
            return size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public String getDes() {
            return des;
        }
    }
}



