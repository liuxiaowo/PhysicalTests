package com.example.physicaltests;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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
    }

    private void initView(){
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
                    String sql,user_name = null;
                    sql="select * from user";
                    Cursor c=db.rawQuery(sql, null);
                    if(c.getCount()!=0){
                        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
                            user_name=c.getString(c.getColumnIndex("name"));
                        }
                    }
                    if(user_name.equals(name.getText().toString())){
                        loginSuccess();
                    }else{
                        ContentValues cv = new ContentValues();
                        cv.put("name", name.getText().toString());
                        cv.put("sex", sex_content);
                        cv.put("age", age.getText().toString());
                        db.insert("user", null, cv);
                        loginSuccess();
                    }
                    db.close();
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
    }
}
