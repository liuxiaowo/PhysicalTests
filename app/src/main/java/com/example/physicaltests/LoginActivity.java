package com.example.physicaltests;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sqlite.SqliteOpenHelper;

/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name,age;
    private RadioGroup  sex;
    private RadioButton boy,gril;
    private Button login;
    private String sex_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_login);
        initView();
        showLastLoginInfo();
    }

    private void initView(){
        //顶部栏背景颜色
        Window window = getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.blue));
        }
        name = (EditText)findViewById(R.id.user_name);
        sex = (RadioGroup)findViewById(R.id.user_sex);
        boy = (RadioButton)findViewById(R.id.boy);
        gril = (RadioButton)findViewById(R.id.gril);
        age = (EditText)findViewById(R.id.user_age);
        login = (Button)findViewById(R.id.login_btn);
        login.setOnClickListener(this);
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == boy.getId()) {
                    sex_content = "Boy";
                } else if (checkedId == gril.getId()) {
                    sex_content = "Gril";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            //登录
            case R.id.login_btn:
                if(TextUtils.isEmpty(name.getText())||TextUtils.isEmpty(age.getText())){
                    Toast.makeText(getApplicationContext(), "姓名或年龄不能为空！", Toast.LENGTH_LONG).show();
                }else {
                    SqliteOpenHelper fdb = new SqliteOpenHelper(getApplicationContext());
                    SQLiteDatabase db = fdb.getWritableDatabase();
                    String sql, user_name = null;
                    sql = "select * from user";
                    Cursor c = db.rawQuery(sql, null);
                    if (c.getCount() != 0) {
                        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                            user_name = c.getString(c.getColumnIndex("name"));
                        }
                    }
                    if (user_name != null) {
                        if (user_name.equals(name.getText().toString())) {
                            loginSuccess();
                        }else{
                            ContentValues cv = new ContentValues();
                            cv.put("name", name.getText().toString());
                            cv.put("sex", sex_content);
                            cv.put("age", age.getText().toString());
                            db.insert("user", null, cv);
                            db.close();
                            loginSuccess();
                        }
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("name", name.getText().toString());
                        cv.put("sex", sex_content);
                        cv.put("age", age.getText().toString());
                        db.insert("user", null, cv);
                        db.close();
                        loginSuccess();
                }

            }
                break;
        }
    }

    /**
     * 登录成功
     */
    private void loginSuccess(){
        Toast.makeText(getApplicationContext(), name.getText() + "登录成功！", Toast.LENGTH_LONG).show();
        Intent in = new Intent(this,MainActivity.class);
        startActivity(in);
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name",name.getText()+"");
        editor.putString("sex",sex_content);
        editor.putString("age",age.getText()+"");
        editor.commit();
    }
    /**
     * 显示最近一次登录信息
     */
    private void showLastLoginInfo(){
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        String name2 = pref.getString("name","");
        String sex2 = pref.getString("sex","");
        String age2 = pref.getString("age","");
        if(name2!=null&&sex2!=null&&age2!=null){
            name.setText(name2);
            if(sex2.equals("boy")){
                boy.setChecked(true);
            }else{
                gril.setChecked(true);
            }
            age.setText(age2);
        }
    }
}
