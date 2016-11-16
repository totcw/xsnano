package com.betterda.xsnano.util;

import android.content.Context;
import android.util.Xml;

import com.betterda.xsnano.dao.CityDao;
import com.betterda.xsnano.dao.CityDomain;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 解析区域id,和获取区域id
 * Created by Administrator on 2016/11/16.
 */

public class XmlParser {

    public static void parserCity(Context context) {

        try {
            CityDao cityDao = new CityDao(context);
            if (cityDao.isExit()) {

                return;
            }

            CityDomain cityDomain = null;
            InputStream inputstream = context.getAssets().open("city.xml");
            XmlPullParser parser = Xml.newPullParser();
            //为PULL解析器设置要解析的XML数据
            parser.setInput(inputstream, "UTF-8");

            int eventCode = parser.getEventType();

            while (eventCode != XmlPullParser.END_DOCUMENT) {
                switch (eventCode) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG: //开始一个标签

                        if ("city".equals(parser.getName())) {
                            cityDomain = new CityDomain();

                        } else {
                            if ("id".equals(parser.getName())) {
                                cityDomain.setId(parser.nextText());

                            } else if ("name".equals(parser.getName())) {

                                cityDomain.setName(parser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG://结束一个标签
                        if (cityDao != null && cityDomain != null) {
                            cityDao.add(cityDomain.getId(), cityDomain.getName());
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        if (inputstream != null) {
                            inputstream.close();
                        }
                        break;
                }
                eventCode = parser.next();

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取区域ID
     * @param context
     * @param name
     * @return
     */
    public static String getCityId(Context context,String name) {
        String id = null;
        try {
            CityDao cityDao = new CityDao(context);
            id = cityDao.find(name);
        } catch (Exception e) {

        }
        return id;
    }
}
