package googleplay.kimda.com.googleplay.beans;

import java.util.List;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailBean {


    private String author;
    private String date;
    private String des;
    private String downloadNum;
    private String downloadUrl;
    private String iconUrl;
    private int id;
    private String name;
    private String packageName;
    private int size;
    private double stars;
    private String version;
    private List<SafeBean> safe;
    private List<String> screen;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSafe(List<SafeBean> safe) {
        this.safe = safe;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getDes() {
        return des;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
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

    public int getSize() {
        return size;
    }

    public double getStars() {
        return stars;
    }

    public String getVersion() {
        return version;
    }

    public List<SafeBean> getSafe() {
        return safe;
    }

    public List<String> getScreen() {
        return screen;
    }

    public static class SafeBean {
        /**
         * safeDes : 已通过安智市场安全检测，请放心使用
         * safeDesColor : 0
         * safeDesUrl : app/com.itheima.www/safeDesUrl0.jpg
         * safeUrl : app/com.itheima.www/safeIcon0.jpg
         */

        private String safeDes;
        private int safeDesColor;
        private String safeDesUrl;
        private String safeUrl;

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public void setSafeDesColor(int safeDesColor) {
            this.safeDesColor = safeDesColor;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }

        public String getSafeDes() {
            return safeDes;
        }

        public int getSafeDesColor() {
            return safeDesColor;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public String getSafeUrl() {
            return safeUrl;
        }
    }

    @Override
    public String toString() {
        return "DetailBean{" +
                "author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", des='" + des + '\'' +
                ", downloadNum='" + downloadNum + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", packageName='" + packageName + '\'' +
                ", size=" + size +
                ", stars=" + stars +
                ", version='" + version + '\'' +
                ", safe=" + safe +
                ", screen=" + screen +
                '}';
    }
}
