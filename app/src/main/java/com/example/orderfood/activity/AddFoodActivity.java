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
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AddFoodActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        //实现菜单条的返回功能
        Toolbar toolbar = this.findViewById(R.id.add_business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在主界面直接跳转到对应界面
                Intent intent = new Intent(AddFoodActivity.this, BusinessActivity.class);
                startActivity(intent);
            }
        });


        //获取当前账号,进行查询
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");

        //上面已经正确更改过了

        //上面是实现返回功能
        ImageView img = findViewById(R.id.add_business_tx);//图片
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开手机相册，实现上传图片
                // 在Activity中使用Intent启动文件选择器
                openGallery(view);

            }
        });

        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback() {
                    @Override
                    public void onActivityResult(Object result) {
                        if (result != null) {
                            Uri uri = (Uri) result;
                            img.setImageURI(uri);

                        } else {
                            Toast.makeText(AddFoodActivity.this, "未选择商品图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //
        EditText id = findViewById(R.id.add_business_name);//商品名字
        EditText price = findViewById(R.id.add_business_price);//商品价格
        EditText des = findViewById(R.id.add_business_des);//商品描述
        Button reg = findViewById(R.id.add_business_button);//昵称


        //Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.default_image);
        Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.upimg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idT = id.getText().toString();//商品的名字
                String priceT = price.getText().toString();//价格
                String desT = des.getText().toString();//描述


                Drawable drawable = img.getDrawable();


                if (idT.isEmpty()) {
                    Toast.makeText(AddFoodActivity.this, "请输入商品名字", Toast.LENGTH_SHORT).show();
                } else if (priceT.isEmpty()) {
                    Toast.makeText(AddFoodActivity.this, "请输入商品价格", Toast.LENGTH_SHORT).show();
                } else if (desT.isEmpty()) {
                    Toast.makeText(AddFoodActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
                }
                if (drawable instanceof BitmapDrawable) {

                    //向数据库插入数据
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // 检查Bitmap是否与默认图片相同
                    if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                        //就代表执行的是默认的
                        Toast.makeText(AddFoodActivity.this, "请点击图片进行添加头像", Toast.LENGTH_SHORT).show();
                    } else {


                        Bitmap bitmap1 = ((BitmapDrawable) img.getDrawable()).getBitmap();//获取图片的map
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作


                        String klmPath = Tools.getImagePath() + "/" + idT + "A.png";//先将图片保存到磁盘，然后只保留路径
                        Tools.saveByteArrayAsPng(imageByteArray, klmPath, AddFoodActivity.this);


                        //实现自动生成商品的ID内容
                        UUID id = UUID.randomUUID();//"aaa-aaaa"
                        String cid = id.toString().replace("-", "");


                        // db.execSQL("insert into d_food (s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img) values(?,?,?,?,?,?)",da);
                        Long a = UserDao.addGood(cid, account, idT, desT, priceT, klmPath);

                        if (a >= 1) {
                            Toast.makeText(AddFoodActivity.this, "添加商品成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddFoodActivity.this, "添加商品失败", Toast.LENGTH_SHORT).show();
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