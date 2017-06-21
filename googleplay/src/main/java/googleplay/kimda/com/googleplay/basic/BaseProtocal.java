package googleplay.kimda.com.googleplay.basic;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.MD5Utils;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by BUTTON on 2017-05-30.
 */

/**
 * url = url根 + 不同的页面的根 + 键值对 =====>所以我们可以用 Map集合存储键值对,从而封装了url
 * protocalUrl()固定 + getPager()抽象+抽象Map集合赋值
 */
public abstract class BaseProtocal<T> {
    public Map<String, String> mParams;

    private String protocalUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        String urlTemp = Contans.SERVER_URL + getPager() + "?";
        stringBuilder.append(urlTemp);
        //Map集合键值对赋值
        setMapData(mParams);
        if (mParams != null && mParams.size() > 0) {
            for (Map.Entry<String, String> entry : mParams.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            String url = stringBuilder.toString().substring(0, stringBuilder.length() - 1);//去掉最后的"&"
            Log.d("BaseProtocal", getClass().getSimpleName()+":当前的URL :   " + url);
            return url;
        }
        return urlTemp;
    }


    /**
     * 赋值Map集合的数据
     */
    public abstract void setMapData(Map<String, String> params);

    /**
     * 当前是什么页面  如home   返回"home"即可
     */
    public abstract String getPager();


    /**
     * KIMDA  T是使用了泛型解析,具体的值
     *
     * @return
     * @throws Exception
     */
    public T loadData() throws Exception {

        //KIMDA:第二步 [1]Gson缓存:读取本地优先于网络
        T localT = getLocalCache();//返回T时注意返回泛型解析后的T
        if (localT != null) {
            Log.e("BaseProtocal", getClass().getSimpleName() +"读取本地json文本");
            return localT;
        }

        //////////////////////////////////////////////////////KIMDA 网络
        Log.e("BaseProtocal", "网络获取");
        //完整的url获取
        String url = protocalUrl();

        OkHttpClient okHttpClient = new OkHttpClient();
        //url位置
        Request request = new Request.Builder().url(url).build();
        //当前已经在子线程,不需要.enqueue(callBack)
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String gsonStr = responseBody.string();

            T t = parserJson(gsonStr);//KIMDa 使用泛型解析
            // KIMDA:第一步 [2]Gson缓存: 保存到本地
//            Gson gson = new Gson();
//            return gson.fromJson(gsonStr, new TypeToken<T>() {}.getType());
            saveCache(t);

            return t;
        } else {
            return null;
        }
    }


    /**
     * 保存缓存,传入Bena或者List集合---转换---保存Json数据
     */
    private void saveCache(T t) {
        //KIMDA :单行toJson
        String toJson = new Gson().toJson(t);
        File cacheFile = getCacheFile();

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(cacheFile));
            writer.write(toJson);
            writer.flush();//刷新
            Log.e("BaseProtocal", getClass().getSimpleName() +"  缓存保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


    }


    /**
     * 获取缓存路径
     * SD卡优先于手机内置内存
     *
     * @return
     */
    private File getCacheFile() {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //说明已有SD卡存在
            cacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/com.kimda.googlePlay/gson");
        } else {
            cacheDir = new File(UiUtils.getContext().getCacheDir(), "gson");
        }

        //文件夹不存在
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();//多级创建文件夹
        }

        File cacheFile = new File(cacheDir, MD5Utils.encode(protocalUrl()));
        return cacheFile;
    }


    private T getLocalCache() {
        //拿到缓存的文件
        File cacheFile = getCacheFile();
        //KIMDA:缓存过期清理缓存文件,这里60秒清除
        long lastModified = cacheFile.lastModified();
        long newTime = new Date().getTime();
        if ((newTime - lastModified) > 60000) {//60秒
            cacheFile.delete();//清除
            Log.e("BaseProtocal", getClass().getSimpleName() +"  清除过期缓存");
            return null;
        }

        if (!cacheFile.exists()) {
            return null;
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(cacheFile));
            try {
                String readLine = reader.readLine();
                T t = parserJson(readLine);
                return t;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

        }

        return null;
    }


    /**
     * 原因:因为是基类的问题导致T不是具体的泛型,可以通过getClass().getGenericSuperclass()拿到本类具体的类(基类中拿到使用者的类)
     * 解决:使用泛型解析 ,具体的泛型  type传入中具体的泛型
     *
     * @param gsonStr
     * @return
     */
    private T parserJson(String gsonStr) {
        //KIMDA:使用泛型解析，从子类获取
        Gson gson = new Gson();
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();//具体的类
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        return gson.fromJson(gsonStr, actualTypeArguments[0]);//返回T数据,这里是List集合
    }

}
