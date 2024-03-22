package com.example.test_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etAccount, etNickName, etSchool, etSign;
    private RadioButton rbBoy, rbGirl;
    private TextView tvBirthTime;
    private AppCompatSpinner spinnerCity;

    private String[] cities;
    private int selectedCityPosition;
    private String selectedCity;

    private String birthTime;


    String TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //初始化控件
        initView();

        //初始化数据
        initData();

        //设置监听器
        initEvent();

    }


    //初始化控件
    private void initView() {
        etAccount = findViewById(R.id.et_account_text);
        etNickName = findViewById(R.id.et_nick_name_text);
        etSchool = findViewById(R.id.et_school_text);
        etSign = findViewById(R.id.et_sign_text);

        rbBoy = findViewById(R.id.rb_boy);
        rbGirl = findViewById(R.id.rb_girl);

        tvBirthTime = findViewById(R.id.tv_birth_time_text);

        spinnerCity = findViewById(R.id.sp_city);
    }

    //初始化数据
    private void initData() {
        cities = getResources().getStringArray(R.array.cities);
        //从文件取数据
        getDataFromSpf();
    }

    //从文件取数据
    //特殊：年龄通过出生时间推算，性别通过rb的check
    private void getDataFromSpf() {
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);

        String nickName = spRecord.getString("nickName", "");
        String gender = spRecord.getString("gender", "");

        String account = spRecord.getString("account", "");
        String birthTime = spRecord.getString("birthTime", "2002年7月20日");
        String city = spRecord.getString("city", "");
        String school = spRecord.getString("school", "");
        String sign = spRecord.getString("sign", "");

        etNickName.setText(nickName);


        etAccount.setText(account);
        tvBirthTime.setText(birthTime);

        etSchool.setText(school);
        etSign.setText(sign);

        //设置性别
        if (TextUtils.equals("女", gender)) {
            rbGirl.setChecked(true);
        }
        if (TextUtils.equals("男", gender)) {
            rbBoy.setChecked(true);
        }

        //设置城市
        for (int i = 0; i < cities.length; i++) {
            if (TextUtils.equals(cities[i], city)) {
                selectedCityPosition = i;
                break;
            }
        }
        spinnerCity.setSelection(selectedCityPosition);
    }


    //设置监听器
    private void initEvent() {
        //监听下拉栏选择的城市
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCityPosition = position;
                selectedCity = cities[position];

                Log.d(TAG, "position: " + position);
                Log.d(TAG, "selectedCity: " + selectedCity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //监听出生时间
        tvBirthTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 在 Android 中，DatePickerDialog 中月份的索引是从 0 开始的，即 0 表示一月，1 表示二月，以此类推，11 表示十二月。
                        // 但是在实际显示月份时，我们习惯性地使用 1 表示一月，2 表示二月，以此类推，12 表示十二月。因此，在处理日期选择器返回的月份时，需要注意将索引值加 1 才能得到真实的月份。
                        int realMonth = month + 1;
                        birthTime = year + "年" + realMonth + "月" + dayOfMonth + "日";
                        Log.d(TAG, "birthTime: " + birthTime);
                        tvBirthTime.setText(birthTime);

                        //选择时间的，暂时不用
//                        popTimePick();
                    }


                }, 2002, 6, 20).show();

            }
        });
    }

    //选择时间的，暂时不用
//    private String birthDayTime;
//    private void popTimePick() {
//        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                birthDayTime = birthTime + hourOfDay + "时" + minute + "分";
//                Log.d(TAG, "birthDayTime: " + birthDayTime);
//            }
//        }, 12, 36, true).show();
//
//    }


    //保存修改的资料
    public void save(View view) {
        String nickName = etNickName.getText().toString();
        String gender = "女";
        if (rbBoy.isChecked()) {
            gender = "男";
        }

        String account = etAccount.getText().toString();
        String school = etSchool.getText().toString();
        String sign = etSign.getText().toString();


        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);
        SharedPreferences.Editor edit = spRecord.edit();

        edit.putString("nickName", nickName);
        edit.putString("gender", gender);


        edit.putString("account", account);
        edit.putString("birthTime", birthTime);
        edit.putString("city", selectedCity);
        edit.putString("school", school);
        edit.putString("sign", sign);

        edit.apply();

        EditProfileActivity.this.finish();
    }


}