package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;

public class UpdateBusinessMesActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_business_mes);

        //实现返回的功能

        //实现返回功能
        Toolbar toolbar=findViewById(R.id.update_business_mes);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UpdateBusinessMesActivity.this,BusinessMyActivity.class);
                startActivity(intent);
            }
        });

        //先将信息加载到界面上然后再进行更改内容
        //通过按钮的点击时间实现修改密码update_business_pwd_button
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        BusinessBean user = UserDao.getBusinessUser(account);



        //点击图片实现功能
        ImageView img= findViewById(R.id.update_business_mes_tx);//图片
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开手机相册，实现上传图片
                // 在Activity中使用Intent启动文件选择器
                openGallery( view);

            }
        });
        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback(){
                    @Override
                    public void onActivityResult(Object result) {
                        if(result!=null){
                            Uri uri=(Uri)result;
                            img.setImageURI(uri);

                        }else{
                            Toast.makeText(UpdateBusinessMesActivity.this, "未选择头像", Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        EditText  name= findViewById(R.id.update_business_mes_name);//昵称
        name.setText(user.getS_name());
        EditText  des= findViewById(R.id.update_business_mes_des);//描述
        des.setText(user.getS_describe());
        EditText types= findViewById(R.id.update_business_mes_type);//店铺类型
        types.setText(user.getS_type());

        Tools.showImage(img,user.getS_img(),this);

        Button reg=findViewById(R.id.update_business_mes_button);//昵称

        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameT=name.getText().toString();
                String  addressT= des.getText().toString();
                String  phoneT= types.getText().toString();

                Drawable drawable = img.getDrawable();






               if(nameT.isEmpty()){
                    Toast.makeText(UpdateBusinessMesActivity.this, "请输入店铺昵称", Toast.LENGTH_SHORT).show();
                }else if( addressT.isEmpty()){
                    Toast.makeText(UpdateBusinessMesActivity.this, "请输入店铺描述", Toast.LENGTH_SHORT).show();
                }else if( phoneT.isEmpty()){
                    Toast.makeText(UpdateBusinessMesActivity.this, "请输入店铺类型", Toast.LENGTH_SHORT).show();
                }else    if (drawable instanceof BitmapDrawable){

                    //向数据库插入数据
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // 检查Bitmap是否与默认图片相同
                    if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                        //就代表执行的是默认的
                        Toast.makeText(UpdateBusinessMesActivity.this, "请点击图片进行添加头像", Toast.LENGTH_SHORT).show();
                    }else{


                        Bitmap bitmap1=((BitmapDrawable)img.getDrawable()).getBitmap();//获取图片的map
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作



                        String klmPath= Tools.getImagePath()+"/"+account+"A.png";//先将图片保存到磁盘，然后只保留路径
                        Tools.saveByteArrayAsPng(imageByteArray,klmPath,UpdateBusinessMesActivity.this);
                        int a= UserDao.updateBusiness(nameT,addressT,phoneT,klmPath,account);

                        if(a>=1){
                            Toast.makeText(UpdateBusinessMesActivity.this, "更改店铺信息成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateBusinessMesActivity.this, "更改店铺信息失败", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            }
        });

    }

    public void openGallery(View view) {
        // 打开相册并等待用户选择图片
        getContentLauncher.launch("image/*");
    }
}