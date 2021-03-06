package com.travel.dx.godaxing.modules.home.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.travel.dx.godaxing.modules.home.bean.YueJiCardDetailsInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18 0018.
 */
public class YueJiCardRollAdapter extends StaticPagerAdapter {
    private  List<YueJiCardDetailsInfo.DataBean.ImagesBean> adv;
    private Context context;
    private Bitmap bitmap;
    private final int w;
    private final int h;

    public YueJiCardRollAdapter(RollPagerView rollPagerView, Context context, List<YueJiCardDetailsInfo.DataBean.ImagesBean> adv) {
        super();
        this.context=context;
        this.adv=adv;
        w = rollPagerView.getWidth();
        h = rollPagerView.getHeight();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String url = adv.get(position).getImg_path();
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return adv.size();
    }
}
