package googleplay.kimda.com.googleplay.beans;

/**
 * Created by BUTTON on 2017-05-27.
 */

public class AppBean {
    /**
     * des : 天翼导航”产品是由中国电信号码百事通信息服务有限公司提供的基于Android平
     * downloadUrl : app/com.pdager/com.pdager.apk
     * iconUrl : app/com.pdager/icon.jpg
     * id : 1588642
     * name : 天翼导航
     * packageName : com.pdager
     * size : 9862216
     * stars : 2
     */



        private String des;
        private String downloadUrl;
        private String iconUrl;
        private int id;
        private String name;
        private String packageName;
        private int size;
        private int stars;
        public void setDes(String des) {
            this.des = des;
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

        public void setStars(int stars) {
            this.stars = stars;
        }

        public String getDes() {
            return des;
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

        public int getStars() {
            return stars;
        }
}
