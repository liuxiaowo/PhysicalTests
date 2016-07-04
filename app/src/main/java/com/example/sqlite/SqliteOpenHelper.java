package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 本地数据库
 * Created by Administrator on 2016/7/4 0004.
 */
public class SqliteOpenHelper extends SQLiteOpenHelper {

    public SqliteOpenHelper(Context context) {
        super(context, "db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        //军人信息
        db.execSQL("create table user(user_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name varchar(100)," +
                "sex varchar(10)," +
                "age int)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists db");
        onCreate(db);
    }
}
