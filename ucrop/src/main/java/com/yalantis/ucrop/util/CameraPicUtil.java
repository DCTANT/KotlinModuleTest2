package com.yalantis.ucrop.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Dechert on 2017-09-11.
 */

public class CameraPicUtil {
    private static String TAG= CameraPicUtil.class.getSimpleName();
    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            left = 0;
            top = 0;
            right = width;
            bottom = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas
        paint.setColor(color);

        // 以下有两种方法画圆,drawRounRect和drawCircle
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); //以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }


    public static Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static void startCrop(String filePath, Activity activity, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(filePath)), "image/*");
// crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
// aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
// outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 220);
        intent.putExtra("outputY", 220);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     *
     * @param bitmap  原始图片
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    public static String getBase64EncodeImage(File file, int size) {
        byte[] imageArray = null;
        String encodeString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        BitmapFactory.Options options = new BitmapFactory.Options();
//        Log.i(TAG,"file.getAbsolutePath:"+file.getAbsolutePath());
        options.inJustDecodeBounds = false;
        Bitmap bitmapOut = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        Matrix m = new Matrix();
        m.setScale(0.75f, 0.75f);
        while (bitmapOut.getByteCount() > size) {
            bitmapOut = Bitmap.createBitmap(bitmapOut, 0, 0, bitmapOut.getWidth(), bitmapOut.getHeight(), m, false);
        }
        bitmapOut.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//		BASE64Encoder base64Encoder = new BASE64Encoder();
        encodeString = "data:image/jpg;base64," + new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
//		Log.i (TAG, "size:" + bos.toByteArray().length);
        return encodeString;
    }

    public static void getCompressedImage(final Bitmap inputBitmap, final int size, final OnCompressFinish onCompressFinish) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] imageArray = null;
                String encodeString = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                BitmapFactory.Options options = new BitmapFactory.Options();
//        Log.i(TAG,"file.getAbsolutePath:"+file.getAbsolutePath());
                options.inJustDecodeBounds = false;
                Bitmap bitmapOut = inputBitmap;
                Matrix m = new Matrix();
                m.setScale(0.75f, 0.75f);
                while (bitmapOut.getByteCount() > size) {
                    Log.i(TAG,"size："+bitmapOut.getByteCount());
                    bitmapOut = Bitmap.createBitmap(bitmapOut, 0, 0, bitmapOut.getWidth(), bitmapOut.getHeight(), m, false);
                }
                bitmapOut.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//		BASE64Encoder base64Encoder = new BASE64Encoder();
//        encodeString = "data:image/jpg;base64," + new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
                Log.i (TAG, "size:" + bos.toByteArray().length);
                onCompressFinish.onFinish(bitmapOut);
            }
        }).start();
    }

    public interface OnCompressFinish{
        void onFinish(Bitmap bitmap);
    }

}
