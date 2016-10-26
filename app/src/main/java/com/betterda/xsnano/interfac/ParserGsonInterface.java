package com.betterda.xsnano.interfac;

import com.google.gson.JsonElement;

/**
 * Created by lyf
 * 解析gson成功的回调方法
 */
public interface ParserGsonInterface {
   public void onSuccess(String result, String resultMsg);
}
