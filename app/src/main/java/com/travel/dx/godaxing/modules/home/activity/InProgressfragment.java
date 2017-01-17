package com.travel.dx.godaxing.modules.home.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.travel.dx.godaxing.R;
import com.travel.dx.godaxing.activity.BaseFragment;
import com.travel.dx.godaxing.activity.CommonDetailsActivity;
import com.travel.dx.godaxing.adapter.CommonAdapter;
import com.travel.dx.godaxing.adapter.ViewHolder;
import com.travel.dx.godaxing.i.BaseCallBack;
import com.travel.dx.godaxing.i.DetailsType;
import com.travel.dx.godaxing.modules.home.bean.PlayTogeInfo;
import com.travel.dx.godaxing.modules.home.dao.PlaytogetherDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/22 0022.
 */
public class InProgressfragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private int page = 1;
    private List<PlayTogeInfo> list;
    private View footer;
    private CommonAdapter<PlayTogeInfo> adapter;

    @Override
    protected void findViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        listView = (ListView) view.findViewById(R.id.inprogress_lv);

    }

    @Override
    protected void initEvent() {
        swipeRefreshLayout

                .setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // TODO 执行下拉刷新的业务
                        page = 1;
                        loadData();


                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getContext(), CommonDetailsActivity.class);
                intent.putExtra("id", Integer.parseInt(list.get(position).getId()));
                intent.putExtra("type", DetailsType.TYPE_PLAY);
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 当listview滑动完全停止后开始判断是否加载下一页
                if (scrollState == SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = listView.getLastVisiblePosition();// 获取当前最后一个可见的item的位置
                    // 最后一个item显示出来了
                    if (lastVisiblePosition == list.size() + listView.getHeaderViewsCount()) {
                        // 获取最后一item控件的对象
                        View childView = listView
                                .getChildAt(listView.getChildCount() - 1);
                        // 如果最后一item的底部和listview的底部重合就加载下一页
                        if (childView.getBottom() == listView.getBottom()) {
                            // TODO 加载下一页
                            page++;
                            loadData();

                        }
                    }

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
            }
        });

    }

    @Override
    protected void init() {

        footer = getActivity().getLayoutInflater()
                .inflate(R.layout.layout_loading, listView, false);
        list = new ArrayList<>();
        // 使用万能适配器只需要设置item的布局和数据集，然后完成数据填充
        adapter = new CommonAdapter<PlayTogeInfo>(getActivity(), list,
                R.layout.adapter_play_inprogress_info) {
            @Override
            public void fillData(ViewHolder helper, int position, PlayTogeInfo item) {
                // TODO 填充数据


                helper.setText(R.id.inprogress_name, item.getName());// 给一个Item中的TextView设置数据

                if (!item.getPrice().equals("null")) {
                    helper.setText(R.id.price, "￥" + item.getPrice());
                    TextView oldPrice = helper.getView(R.id.originalprice);
                    oldPrice.getPaint().setAntiAlias(true);//抗锯齿
                    oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中间横线
                    helper.setText(R.id.originalprice, "￥" + item.getOriginalprice());
                } else {
                    helper.setText(R.id.price, "免费");
                    helper.setText(R.id.originalprice, "");
                }
                helper.setImageByUrl(R.id.iv, item.getImg());// 加载一张网络图片
                //填写开始和结束的时间
                String start = timedate(item.getS_time() + "");
                String end = timedate(item.getE_time() + "");
                helper.setText(R.id.activity_time, start + "-" + end);

                int stock = item.getStock();

                helper.setText(R.id.baoming, item.getSalecount() + "/" + stock);


            }
        };


        listView.addFooterView(footer);// 兼容4.0以前的版本
        listView.setAdapter(adapter);
        listView.removeFooterView(footer);

    }

    private String timedate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM/dd");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

    @Override
    protected void loadData() {
        // TODO 请求网络数据，解析后进行界面更新
        PlaytogetherDao.requestPlaytogertherinprogressList(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
//                list.clear();
                swipeRefreshLayout.setRefreshing(false);// 将下拉刷新控件隐藏
                List<PlayTogeInfo> tempList = (List<PlayTogeInfo>) data;
                List<PlayTogeInfo> newtempList = new ArrayList<PlayTogeInfo>();
                for (int i = 0; i < tempList.size(); i++) {
                    if (tempList.get(i).getId().equals("100")) {
                        Log.i("newtempListi", "success: " + i);
                        newtempList.add(tempList.get(i));
                    }
                }
                if (page == 1) {
                    list.clear();
                }
                // 仅当listview中不存在footer的时候才可以添加footer
                if (listView.getFooterViewsCount() == 0) {
                    listView.addFooterView(footer);
                }
                if (tempList.size() < 10) {
                    Toast.makeText(getActivity(), "数据加载完毕！", Toast.LENGTH_SHORT).show();
                    listView.removeFooterView(footer);
                }

                if (newtempList != null) {

                    list.addAll(newtempList);
                    adapter.notifyDataSetChanged();
                }
            }


            @Override
            public void failed(int errorCode, Object data) {
                swipeRefreshLayout.setRefreshing(false);// 将下拉刷新控件隐藏
                Toast.makeText(getActivity(), "数据加载失败，请重新载入", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_inprogress;
    }
}
