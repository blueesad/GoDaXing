package com.travel.dx.godaxing.modules.home.dao;

import android.util.Log;

import com.travel.dx.godaxing.i.BaseCallBack;
import com.travel.dx.godaxing.modules.home.bean.YueJiCardDetailsInfo;
import com.travel.dx.godaxing.modules.home.util.YueJiCardDetailsParse;
import com.travel.dx.godaxing.net.HttpUtil;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
public class YueJiCardDetailsDao {
    public static void requestYueJiCardList(String id,final BaseCallBack baseCallBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        HttpUtil.doHttpReqeust("POST",
                "http://dxlv.iyoudaxing.com/Api/Sight/getContent/id/?", params,
                new BaseCallBack()
                {
                    @Override
                    public void success(Object data)
                    {
                        // TODO 请求成功后的处理
                        if (baseCallBack != null)
                        {
                            YueJiCardDetailsInfo.DataBean tempList=  YueJiCardDetailsParse.YueJiCardDetails(data.toString());
                            baseCallBack.success(tempList);
                            Log.e("**", "tmpList"+tempList );
                        }
                    }

                    @Override
                    public void failed(int errorCode, Object data)
                    {
                        // TODO 请求失败后的处理
                        if (baseCallBack != null)
                        {
                            baseCallBack.failed(errorCode, data);
                        }
                    }
                });
    }
}
