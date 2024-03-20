package com.example.test_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_REGISTER = 1;
    private Button btnLogin;
    private EditText etAccount, etPassword;
    private CheckBox cbRemember, cbAutoLogin;

    private String tempAccount = "zzy";
    private String tempPassword = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化控件
        initView();

        //初始化数据
        initData();

        //点击登录，验证账号和密码是否正确
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();

                String TAG = "tag";
                Log.d(TAG, "-----------------account:" + account);
                Log.d(TAG, "-----------------password:" + password);

                if (TextUtils.isEmpty(tempAccount)) {
                    Toast.makeText(LoginActivity.this, "还没有注册账号", Toast.LENGTH_SHORT).show();
                    return;
                }

                //验证账号
                if (TextUtils.equals(account, tempAccount)) {
                    //验证密码
                    if (TextUtils.equals(password, tempPassword)) {

                        //账号和密码都正确
                        Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();

                        //把记住密码的状态保存到spRecord
                        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);
                        SharedPreferences.Editor edit = spRecord.edit();
                        //如果记住密码
                        if (cbRemember.isChecked()) {
                            edit.putString("account", account);
                            edit.putString("password", password);
                            edit.putBoolean("isRemember", true);
                            //如果自动登录
                            if (cbAutoLogin.isChecked()) {
                                edit.putBoolean("isAutoLogin", true);
                            } else {
                                edit.putBoolean("isAutoLogin", false);
                            }
                        } else {
                            edit.putBoolean("isRemember", false);
                        }
                        //debug
                        edit.putString("account", account);

                        edit.apply();

                        //intent传参
                        Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);

                        intent.putExtra("account", account);

                        startActivity(intent);
                        //从栈中销毁登录页面
                        LoginActivity.this.finish();

                    } else Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

                } else Toast.makeText(LoginActivity.this, "账号错误", Toast.LENGTH_SHORT).show();
            }
        });

        //如果勾选自动登录，则必须勾选记住密码
        cbAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRemember.setChecked(true);
                }
            }
        });

        //如果取消记住密码，则必须取消自动登录
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cbAutoLogin.setChecked(false);
                }
            }
        });
    }

    //初始化控件
    private void initView() {
        btnLogin = findViewById(R.id.btn_login);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRemember = findViewById(R.id.cb_remember);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
    }

    //判断是否记住密码
    private void initData() {

        //取数据
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);

        //先取是否自动登录
        boolean isAutoLogin = spRecord.getBoolean("isAutoLogin", false);

        //如果自动登录
        if (isAutoLogin) {

            //intent传参
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            String account = spRecord.getString("account", "");
            intent.putExtra("account", account);

            startActivity(intent);
            //从栈中销毁登录页面
//            LoginActivity.this.finish();

        }

        //取是否记住密码
        boolean isRemember = spRecord.getBoolean("isRemember", false);
        //如果记住密码了
        if (isRemember) {
            //取账号和密码
            String account = spRecord.getString("account", "");
            String password = spRecord.getString("password", "");
            //配置到文本框里
            etAccount.setText(account);
            etPassword.setText(password);
            cbRemember.setChecked(true);
//        etContent.setSelection(content.length()); //光标最后

        }
    }

    //显示跳转app内部注册页面
    public void toRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        //回传数据
        startActivityForResult(intent, REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_REGISTER && resultCode == RegisterActivity.RESULT_CODE_REGISTER && data != null) {

            Bundle extras = data.getExtras();
            String account = extras.getString("account", "");
            String password = extras.getString("password", "");

            etAccount.setText(account);
            etPassword.setText(password);

            tempAccount = account;
            tempPassword = password;
        }
    }


    //隐式跳转外部
//    public void toBaidu(View view) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("http://www.baidu.com"));
//        startActivity(intent);
//    }

//    public void popDialog(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("温馨提示");
//        builder.setMessage("你好");
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setPositiveButton("确认", null);
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}