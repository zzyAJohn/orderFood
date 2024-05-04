package com.example.orderfood.tools;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Tools {

    // 将 byte[] 转换为 Bitmap
    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }



    // 保存 Bitmap 为 PNG 图像文件
    public static boolean saveBitmapAsPng(Context context,Bitmap bitmap, String fileName) {
        if (bitmap == null||context==null) {
            return false;
        }

        // 检查是否已授予写入外部存储的权限
//        if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            Log.e("图片权限", "应用缺少写入外部存储的权限");
//            return false;
//        }

        // 使用ContentValues构建图像的元数据
        ContentValues values=new ContentValues();

        values.put(MediaStore.Images.Media.DESCRIPTION, "这个食物图片");
        //--------------------------------------------------------


        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        //values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg/png");

        //--------------------------------------------------------
        // 获取外部公共图库的URI

        Uri externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 在外部公共图库中创建一个新的图像文件
        Uri imageUri = context.getContentResolver().insert(externalContentUri, values);


        if (imageUri != null) {
            try {
                // 打开输出流以保存位图数据到图像文件
                OutputStream outputStream = context.getContentResolver().openOutputStream(imageUri);
                if (outputStream != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("图片权限", "保存图像时出错: " + e.getMessage());
            }
        } else {
            Log.e("图片权限", "无法创建图像文件");
        }

        return false;

    }





    /**
     * 获取图片路径
     * @return
     */
    public static  String getImagePath(){


// 获取相册的路径
        String picturesPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();





        return  picturesPath;
    }



    // 将 byte[] 转换为 PNG 图像并保存到指定路径
    public static boolean saveByteArrayAsPng(byte[] byteArray, String filePath,Context context) {

        String[] result = filePath.split("/");
        String fileName=result[result.length-1];


        Bitmap bitmap = byteArrayToBitmap(byteArray);
        return saveBitmapAsPng(context,bitmap, fileName);

    }

    /**
     * 封装的显示图片内容
     * @param img
     * @param path
     */
    public static void showImage(ImageView img,String path,Context context){
        //读取文件
//        if (!path.equals("")) {
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            // 将加载的图像设置到ImageView中
//            img.setImageBitmap(bitmap);
//        }


        Glide.with(context)
                .load(path)
                .into(img);


    }

    /**
     * 获取uuid
     * @return
     */

    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String res=randomUUIDString.replace("-","");



        return res;
    }

    public static Float decimalToTwo(float su){
        return Float.parseFloat(String.format("%.2f",su));

    }


    /**
     * 获取当前时间的方法
     * @return
     */
    public static String getCurrentTime() {
        // 获取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 你可以根据需要调整日期时间格式

        // 格式化当前时间为字符串
        String formattedTime = currentTime.format(formatter);

        return formattedTime;
    }

    public static void main(String[] args) {
        // 调用获取当前时间的方法
        String currentTime = getCurrentTime();

        System.out.println("当前时间: " + currentTime);
    }


}
