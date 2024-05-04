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
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.user.activity.UserAddressActivity;
import com.example.orderfood.user.activity.comment.UpdateAddressActivity;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class UpdateGoodActivity extends AppCompatActivity {


    String foodIdT;
    private ActivityResultLauncher<String> getContentLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_good);
        //实现菜单条的返回功能
        Toolbar toolbar = this.findViewById(R.id.update_business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在主界面直接跳转到对应界面
                Intent intent=new Intent(UpdateGoodActivity.this, BusinessActivity.class);
                startActivity(intent);
            }
        });


        //获取当前账号,进行查询
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        String account=sharedPreferences.getString("account","");

        Intent intent=getIntent();
        String foodId=intent.getStringExtra("id");//这个是食物的ID
        foodIdT=foodId;
        //通过食物ID获取食物的相关信息
        FoodBean foodBean = FoodDao.getFoodByID(foodId);//获取到食物的整个信息

        EditText id= findViewById(R.id.update_business_name);//商品名字
        EditText  price= findViewById(R.id.update_business_price);//商品价格
        EditText  des= findViewById(R.id.update_business_des);//商品描述
        Button reg=findViewById(R.id.update_business_button);//昵称
        //上面是实现返回功能
        ImageView img= findViewById(R.id.update_business_tx);//图片

        id.setText(foodBean.getS_food_name());
        price.setText(foodBean.getS_food_price());
        des.setText(foodBean.getS_food_des());

        Bitmap bitmap = BitmapFactory.decodeFile(foodBean.getS_food_img());
        // 将加载的图像设置到ImageView中
        img.setImageBitmap(bitmap);






        //上面已经正确更改过了


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
                            Toast.makeText(UpdateGoodActivity.this, "未选择商品图片", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //



        //Drawable defaultDrawable = ContextCompat.getDrawable(this, R.drawable.default_image);
        Drawable defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idT=id.getText().toString();//商品的名字
                String priceT=price.getText().toString();//价格
                String desT=des.getText().toString();//描述


                Drawable drawable = img.getDrawable();






                if(idT.isEmpty()){
                    Toast.makeText(UpdateGoodActivity.this, "请输入商品名字", Toast.LENGTH_SHORT).show();
                }else if(priceT.isEmpty()){
                    Toast.makeText(UpdateGoodActivity.this, "请输入商品价格", Toast.LENGTH_SHORT).show();
                }else if(desT.isEmpty()){
                    Toast.makeText(UpdateGoodActivity.this, "请输入商品描述", Toast.LENGTH_SHORT).show();
                }if (drawable instanceof BitmapDrawable){

                    //向数据库插入数据
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    // 检查Bitmap是否与默认图片相同
                    if (bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())) {
                        //就代表执行的是默认的
                        Toast.makeText(UpdateGoodActivity.this, "请点击图片进行添加头像", Toast.LENGTH_SHORT).show();
                    }else{


                        Bitmap bitmap1=((BitmapDrawable)img.getDrawable()).getBitmap();//获取图片的map
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作



                        String klmPath= Tools.getImagePath()+"/"+idT+"A.png";//先将图片保存到磁盘，然后只保留路径
                        Tools.saveByteArrayAsPng(imageByteArray,klmPath,UpdateGoodActivity.this);





                        int a=FoodDao.updateGood(idT,priceT,desT,klmPath,foodId);



                        if(a>=1){
                            Toast.makeText(UpdateGoodActivity.this, "更改商品成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateGoodActivity.this, "更改商品失败", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.del_food, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.del_menu){

            FoodDao.delFood(foodIdT);

            Intent intent=new Intent(UpdateGoodActivity.this, BusinessActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);


    }


}