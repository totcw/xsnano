package com.betterda.xsnano.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CityDbOpenHelper extends SQLiteOpenHelper {

    public CityDbOpenHelper(Context context ) {
        super(context, "city.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库第一次创建的时候被调用的方法  所以在这方法里面创建表
        System.out.println("第一次创建");
        db.execSQL("create table if not exists city(id integer primary key autoincrement,city_id varchar(20),name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
