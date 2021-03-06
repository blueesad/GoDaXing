package com.travel.dx.godaxing.util;


import com.travel.dx.godaxing.bean.CommonDetailsInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommonDetails_GoodsParse {
    public static List<CommonDetailsInfo.DataBean.GoodsBean> parseShopDetails(String json) {
        List<CommonDetailsInfo.DataBean.GoodsBean> goodsBeanList = new ArrayList<>();
        CommonDetailsInfo.DataBean.GoodsBean goodBean = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject = jsonObject.getJSONObject("data");
            JSONArray goods = jsonObject.getJSONArray("goods");
            for (int i = 0; i < goods.length(); i++) {
                JSONObject subJson = goods.getJSONObject(i);
                goodBean = new CommonDetailsInfo.DataBean.GoodsBean();
                goodBean.setName(subJson.getString("name"));
                goodBean.setPrice(subJson.getInt("price") + "");
                goodBean.setContent(subJson.getString("content"));
                goodBean.setOriginalprice(subJson.getString("originalprice"));
                goodBean.setStock(subJson.getString("stock"));
                goodBean.setS_time(subJson.getString("s_time"));
                goodBean.setE_time(subJson.getString("e_time"));
                goodBean.setScore(subJson.getString("score"));
                goodBean.setSold(subJson.getString("sold"));
                goodsBeanList.add(goodBean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsBeanList;
    }
}
