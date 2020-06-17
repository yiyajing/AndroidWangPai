package com.woaiwangpai.iwb.utils.cache;

import android.content.Context;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Author : YiYaJing
 * @Data : 2020/6/17 9:02
 * @Email : yiyajing8023@163.com
 * @Description : 二级缓存
 */
public class FileCache {
    //缓存文件目录
    private File mCacheDir;
    /**
     * 创建缓存文件目录，如果有SD卡，则使用SD，如果没有则使用系统自带缓存目录
     * @param context
     * @param cacheDir 图片缓存的一级目录
     */
    public FileCache(Context context, File cacheDir, String dir){
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        mCacheDir = new File(cacheDir, dir);
      else
        mCacheDir = context.getCacheDir();// 如何获取系统内置的缓存存储路径
        if(!mCacheDir.exists())  mCacheDir.mkdirs();
    }
    public File getFile(String url){
        File f=null;
        try {
//对url进行编辑，解决中文路径问题
            String filename = URLEncoder.encode(url,"utf-8");
            f = new File(mCacheDir, filename);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return f;
    }
    public void clear(){//清除缓存文件
        File[] files = mCacheDir.listFiles();
        for(File f:files)f.delete();
    }
}
