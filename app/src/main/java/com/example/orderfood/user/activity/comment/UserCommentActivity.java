package com.example.orderfood.user.activity.comment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;



import com.example.orderfood.R;
import com.example.orderfood.bean.UserBean;
import com.example.orderfood.dao.CommentDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;
import com.example.orderfood.tools.XingListen;

import java.io.ByteArrayOutputStream;

/**
 * 用户评论给商家的的内容
 */
public class UserCommentActivity extends AppCompatActivity {
    private  EditText con=null;
    private  ImageView img=null;
    
    private  String account="";

    private  String business="";

   private int score=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comment);





     Intent intent=getIntent();

     business= intent.getStringExtra("business");
        String order= intent.getStringExtra("order");
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        account=sharedPreferences.getString("account","");




        con = findViewById(R.id.comment_con_user);

        img = findViewById(R.id.comment_img_user);

        Button pz=findViewById(R.id.comment_pz_user);
        Button xc=findViewById(R.id.comment_xc_user);


        //接下来用来显示星星
        RadioButton a=findViewById(R.id.comment_one_a);//不可以无星
        RadioButton b=findViewById(R.id.comment_one_b);
        RadioButton c=findViewById(R.id.comment_one_c);
        RadioButton d=findViewById(R.id.comment_one_d);
        RadioButton e=findViewById(R.id.comment_one_e);
        TextView f=findViewById(R.id.comment_one_f);
        a.setOnClickListener(new XingListen(f,1,this));
        b.setOnClickListener(new XingListen(f,2,this));
        c.setOnClickListener(new XingListen(f,3,this));
        d.setOnClickListener(new XingListen(f,4,this));
        e.setOnClickListener(new XingListen(f,5,this));



        //如果没有对应操作相册的权限，则给他权限
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 1);


        }
        //调用相机拍照
        pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        //打开手机相册获取图片
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });





        Toolbar back= findViewById(R.id.comment_toolbar);

       back.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onBackPressed();
           }
       });


        //点击发布的功能
        Button button = findViewById(R.id.comment_put_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sc= f.getText().toString();
                String nr[]={"非常差","差","一般","满意","非常满意"};
                for(int i=0;i<nr.length;i++){
                    if(sc.equals(nr[i])){
                        score=i+1;
                    }
                }


                try {


                    //保存图片
                    Bitmap bitmap1=((BitmapDrawable)img.getDrawable()).getBitmap();//获取图片的map
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();  // 将Bitmap转换为字节数组

                    bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] imageByteArray = byteArrayOutputStream.toByteArray();// 执行插入操作

                    String uuid=Tools.getUUID();
                    String klmPath= Tools.getImagePath()+"/"+uuid+"A.png";//先将图片保存到磁盘，然后只保留路径

                    Tools.saveByteArrayAsPng(imageByteArray,klmPath,UserCommentActivity.this);

                    //这个是通过uuid进行生成的
                    String s_comment_id=order;
                    String s_comment_user_id=account;
                    UserBean user = UserDao.getUser(account);
                    String s_comment_user_name=user.getS_name();
                    String s_comment_user_img=user.getS_img();
                    String s_comment_contenct=con.getText().toString();
                    String s_comment_img=klmPath;
                    String s_comment_time=Tools.getCurrentTime();
                    String s_comment_business_id=business;
                    String s_comment_score=String.valueOf(score);


                    if(s_comment_contenct.isEmpty()){
                        Toast.makeText(UserCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    }else{
                        int a=CommentDao.saveComment(s_comment_id,s_comment_user_id,s_comment_user_name,
                                s_comment_user_img,s_comment_contenct,s_comment_img,
                                s_comment_time,s_comment_business_id,s_comment_score);
                        if(a==1){
                            Toast.makeText(UserCommentActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();

                        }else
                        if(a==0){
                            Toast.makeText(UserCommentActivity.this, "评论失败！", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(UserCommentActivity.this, "已经评论过来无法再评论！", Toast.LENGTH_SHORT).show();
                        }
                    }





                }catch (Exception e) {

                    //代表没有图片

                    String s_comment_id=order;
                    String s_comment_user_id=account;
                    UserBean user = UserDao.getUser(account);
                    String s_comment_user_name=user.getS_name();
                    String s_comment_user_img=user.getS_img();
                    String s_comment_contenct=con.getText().toString();
                    String s_comment_img="";
                    String s_comment_time=Tools.getCurrentTime();
                    String s_comment_business_id=business;
                    String s_comment_score=String.valueOf(score);


                    if(s_comment_contenct.isEmpty()){
                        Toast.makeText(UserCommentActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    }else{
                        int a=CommentDao.saveComment(s_comment_id,s_comment_user_id,s_comment_user_name,
                                s_comment_user_img,s_comment_contenct,s_comment_img,
                                s_comment_time,s_comment_business_id,s_comment_score);
                        if(a==1){
                            Toast.makeText(UserCommentActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();

                        }else
                        if(a==0){
                            Toast.makeText(UserCommentActivity.this, "评论失败！", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(UserCommentActivity.this, "已经评论过来无法再评论！", Toast.LENGTH_SHORT).show();
                        }



                    }




                }
            }
        });







        cameraLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if(result.getResultCode() == Activity.RESULT_OK){//代表拍照
                Intent data = result.getData();
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null && extras.containsKey("data")) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        img.setImageBitmap(imageBitmap);
                    }else{
                        //下面执行的是相册
                        Uri selectedImageUri = data.getData();
                        // 现在你可以处理选中的图片，例如显示在 ImageView 中
                       img.setImageURI(selectedImageUri);

                    }

                }else{

                }
            }

        });
    }

    private ActivityResultLauncher<Intent> cameraLauncher;
    //执行拍照的功能
    public void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(takePictureIntent);

    }
    //打开相册
    public void pickImageFromGallery(){
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraLauncher.launch(pickImageIntent);
    }




}