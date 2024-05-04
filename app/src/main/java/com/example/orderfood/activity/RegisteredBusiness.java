package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class RegisteredBusiness extends AppCompatActivity {
    private ActivityResultLauncher<String> getContentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_business);

        //实现菜单条的返回功能
        Toolbar toolbar = this.findViewById(R.id.reg_business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在主界面直接跳转到对应界面
                Intent intent=new Intent(RegisteredBusiness.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //上面是实现返回功能
        ImageView img= findViewById(R.id.reg_business_tx);//图片
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
                            Toast.makeText(RegisteredBusiness.this, "未选择头像", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //
        EditText id= findViewById(R.id.reg_business_id);//账号
        EditText  pwd= findViewById(R.id.reg_business_pwd);//密码
        EditText  name= findViewById(R.id.reg_business_name);//昵称

        EditText  des= findViewById(R.id.reg_business_des);//描述
        EditText types= findViewById(R.id.reg_business_type);//店铺类型
        Button reg=findViewById(R.id.reg_business_reg);//昵称


        //Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.default_image);
        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idT=id.getText().toString();
                String pwdT=pwd.getText().toString();
                String nameT=name.getText().toString();
                String  addressT= des.getText().toString();
                String  phoneT= types.getText().toString();

                Drawable drawable = img.getDrawable();






                if(idT.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this, "请输入商家账号", Toast.LENGTH_SHORT).show();
                }else if(pwdT.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this, "请输入商家密码", Toast.LENGTH_SHORT).show();
                }else if(nameT.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this, "请输入店铺昵称", Toast.LENGTH_SHORT).show();
                }else if( addressT.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this, "请输入店铺描述", Toast.LENGTH_SHORT).show();
                }else if( phoneT.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this, "请输入店铺类型", Toast.LENGTH_SHORT).show();
                }else    if (drawable instanceof BitmapDrawable){

                    //向数据库插入数据
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // 检查Bitmap是否与默认图片相同
                    if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                        //就代表执行的是默认的
                        Toast.makeText(RegisteredBusiness.this, "请点击图片进行添加头像", Toast.LENGTH_SHORT).show();
                    }else{


                        Bitmap bitmap1=((BitmapDrawable)img.getDrawable()).getBitmap();//获取图片的map
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作



                        String klmPath= Tools.getImagePath()+"/"+idT+"A.png";//先将图片保存到磁盘，然后只保留路径
                        Tools.saveByteArrayAsPng(imageByteArray,klmPath,RegisteredBusiness.this);
                        Long a= UserDao.addBusiness(idT,pwdT,nameT,addressT,phoneT,klmPath);

                        if(a>=1){
                            Toast.makeText(RegisteredBusiness.this, "注册店铺成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(RegisteredBusiness.this, "注册店铺失败,账号已存在", Toast.LENGTH_SHORT).show();
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