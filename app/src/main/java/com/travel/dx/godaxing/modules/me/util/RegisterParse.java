package com.travel.dx.godaxing.modules.me.util;

import com.travel.dx.godaxing.modules.me.bean.LoginInfo;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class RegisterParse {
    public static LoginInfo parseRegister(String json) {
        LoginInfo info = null;
        JSONObject subJson = null;
        try {
            subJson = new JSONObject(json);
            info = new LoginInfo();
            info.setMsg(subJson.getString("msg"));
            info.setToken(subJson.getString("token"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }

}
