package com.betterda.xsnano.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class CityDao {
    private CityDbOpenHelper helper;

    public CityDao(Context context) {
        helper = new CityDbOpenHelper(context);
    }

    /**
     * 添加数据
     * @param city_id
     * @param name
     * @return
     */

    public long add(String city_id,String name){
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("city_id", city_id);
        values.put("name", name);
        long result= db.insert("city", null, values);
        db.close();
        return result;
    }

    /**
     * 根据name 查询数据
     * @param name
     * @return
     */
    public String find(String name){
        String result=null;
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.query("city", null, "name=?", new String[]{name}, null, null, null);

            if (cursor.moveToNext()) {
                //拿到id
                result = cursor.getString(1);
            }
            cursor.close();
            db.close();
        } catch (Exception e) {

        }


        return result;
    }


    public List<CityDomain> findAll(){
        try {
            SQLiteDatabase db = helper.getWritableDatabase();
            List<CityDomain> persons = new ArrayList<CityDomain>();
            Cursor cursor = db.query("city", new String[]{"id", "city_id", "name"}, null, null, null, null, null);

            while (cursor.moveToNext()) {

                String id = cursor.getString(1);
                String name = cursor.getString(2);
                CityDomain p = new CityDomain();
                p.setId(id);
                p.setName(name);

                persons.add(p);

            }
            cursor.close();
            db.close();
            return persons;
        } catch (Exception e) {

        }
     return  null;
    }


    /**
     * 判断这个表是否存在
     * @return
     */
    public boolean isExit() {
        boolean result = false;
        try {
            String sql = "select count(*)  from city";
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {

        }
        return result;
    }
}
