package com.soul.hodgepodge.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public
/**
 * Created by Chjr on 2020/9/2
 *  图片加载工具
 *
 */
class PictureUtils {
    /**
     *
     * @param path 目标地址
     * @param destWidth 目标宽
     * @param destHeight 目标高
     * @return 返回目标地址的缩略图
     */
    public static Bitmap getScaledBitmap(String path,int destWidth,int destHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if(srcHeight > destHeight || srcWidth >destWidth){
            float height = srcHeight/destHeight;
            float width = srcWidth/destWidth;

            inSampleSize = Math.round(Math.max(height, width));
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path,options);
    }

    /**
     * 获取根据屏幕尺寸，根据屏幕尺寸缩放图片
     * @param path 目标地址
     * @param activity context
     * @return bitmap
     */
    public static Bitmap getScaledBitmap (String path , Activity activity){
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path,size.x,size.y);
    }
}
