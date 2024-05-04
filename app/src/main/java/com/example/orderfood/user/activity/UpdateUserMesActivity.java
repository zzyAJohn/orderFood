package com.example.orderfood.user.activity;

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
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;

import com.example.orderfood.bean.UserBean;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;

public class UpdateUserMesActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> getContentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_mes);
        //实现菜单条的返回功能
        Toolbar toolbar = this.findViewById(R.id.reg_user_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在主界面直接跳转到对应界面
                Intent intent=new Intent(UpdateUserMesActivity.this,UserManageActivity.class);
                intent.putExtra("sta","1");
                startActivity(intent);
            }
        });
        //上面是实现返回功能
        ImageView img= findViewById(R.id.reg_user_tx);//图片
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
                            Toast.makeText(UpdateUserMesActivity.this, "未选择头像", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");
        //

        EditText  name= findViewById(R.id.reg_user_name);//昵称
        RadioButton man= findViewById(R.id.reg_user_man);//男
        man.setChecked(true);
        RadioButton woman= findViewById(R.id.reg_user_woman);//女
        EditText  address= findViewById(R.id.reg_user_address);//密码
        EditText  phone= findViewById(R.id.reg_user_phone);//昵称
        Button reg=findViewById(R.id.reg_user_reg);//昵称

        UserBean user = UserDao.getUser(account);
        name.setText(user.getS_name());
        if(user.getS_sex().equals("男")){
            man.setChecked(true);
        }else{
            woman.setChecked(true);
        }
        address.setText(user.getS_address());
        phone.setText(user.getS_phone());
        Tools.showImage(img,user.getS_img(),this);





        //Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.default_image);
        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameT=name.getText().toString();
                String  addressT= address.getText().toString();
                String  phoneT= phone.getText().toString();

                Drawable drawable = img.getDrawable();






                if(nameT.isEmpty()){
                    Toast.makeText(UpdateUserMesActivity.this, "请输入用户昵称", Toast.LENGTH_SHORT).show();
                }else if( addressT.isEmpty()){
                    Toast.makeText(UpdateUserMesActivity.this, "请输入用户地址", Toast.LENGTH_SHORT).show();
                }else if( phoneT.isEmpty()){
                    Toast.makeText(UpdateUserMesActivity.this, "请输入用户联系方式", Toast.LENGTH_SHORT).show();
                }else    if (drawable instanceof BitmapDrawable){

                    //向数据库插入数据
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // 检查Bitmap是否与默认图片相同
                    if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                        //就代表执行的是默认的
                        Toast.makeText(UpdateUserMesActivity.this, "请点击图片进行添加头像", Toast.LENGTH_SHORT).show();
                    }else{


                        Bitmap bitmap1=((BitmapDrawable)img.getDrawable()).getBitmap();//获取图片的map
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作
                        String sex="女";
                        if(man.isChecked()){
                            sex="男";
                        }
                        String idT = Tools.getUUID();
                        String klmPath= Tools.getImagePath()+"/"+idT+".png";//先将图片保存到磁盘，然后只保留路径
                        Tools.saveByteArrayAsPng(imageByteArray,klmPath,UpdateUserMesActivity.this);


                        int a= UserDao.updateUser(nameT,sex,addressT,phoneT,klmPath, account);
                        if(a>=1){
                            Toast.makeText(UpdateUserMesActivity.this, "修改信息成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateUserMesActivity.this, "修改信息失败", Toast.LENGTH_SHORT).show();
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