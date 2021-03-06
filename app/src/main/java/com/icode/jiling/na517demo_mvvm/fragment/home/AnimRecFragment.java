package com.icode.jiling.na517demo_mvvm.fragment.home;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facebook.drawee.view.SimpleDraweeView;
import com.icode.jiling.na517demo_mvvm.Contants;
import com.icode.jiling.na517demo_mvvm.R;
import com.icode.jiling.na517demo_mvvm.adapter.BannerAdapter;
import com.icode.jiling.na517demo_mvvm.adapter.MyRecAdapter;
import com.icode.jiling.na517demo_mvvm.model.RecBanner;
import com.icode.jiling.na517demo_mvvm.model.RecomendAnimModel;
import com.icode.jiling.na517demo_mvvm.utils.TimeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import okhttp3.Call;

/**
 * 动画-推荐
 */
public class AnimRecFragment extends Fragment {

    private SwipeRefreshLayout mSwipeLayout;

    private RecyclerView mRecRecyclerView;

    private List<RecomendAnimModel> mRecLists = new ArrayList<>();

    private List<RecBanner> mBannerLists = new ArrayList<>();

    private List<SimpleDraweeView> mBannerImgLists = new ArrayList<>();

    private ViewPager mBannerViewPager;

    private BannerAdapter mBannerAdapter;

    private MyRecAdapter myRecAdapter;

    private LinearLayout mDotsLayout;

    private boolean mIsLoadingMore = false;

    private Handler bannerHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(mBannerAdapter != null && msg.what == 0x160 && !mSwipeLayout.isRefreshing()) {
                mBannerAdapter.notifyDataSetChanged();
                if (mBannerAdapter.getCount() > 1) { // 多于1个，才循环
                    int index = mBannerViewPager.getCurrentItem();
                    if(index != -1) {
                        index = (index + 1) % mBannerAdapter.getCount();
                        mBannerViewPager.setCurrentItem(index, true);
                        if (bannerHandler != null) {
                            Message message = new Message();
                            message.obj = 1;
                            message.what = 0x160;
                            bannerHandler.sendMessageDelayed(message, 2500);
                        }
                    }
                }
            }
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_anim_rec,container,false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        mSwipeLayout.setRefreshing(true);
        initData();
    }

    private void initView(View view) {
        mSwipeLayout = view.findViewById(R.id.swipe_rec);
        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        mRecRecyclerView = view.findViewById(R.id.rc_rec_list);
        mRecRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        myRecAdapter = new MyRecAdapter(mRecLists);
        View mBanner = getLayoutInflater().inflate(R.layout.header_banner,mRecRecyclerView,false);
        mBannerViewPager = mBanner.findViewById(R.id.vp_banner);
        mDotsLayout= mBanner.findViewById(R.id.ll_indicator_dot);
        mBannerAdapter = new BannerAdapter(mBannerImgLists,mBannerLists);
        mBannerViewPager.setAdapter(mBannerAdapter);

        myRecAdapter.addHeaderView(mBanner);
        mRecRecyclerView.setAdapter(myRecAdapter);
    }

    private void initData() {
        if(mSwipeLayout.isRefreshing() || mIsLoadingMore){
            bannerHandler.removeMessages(0x160);
        }
        OkHttpUtils.get()
                .url(Contants.BANNER)
                .addParams("ts", System.currentTimeMillis()+"")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        String sdf = e.getMessage();
                        Toast.makeText(getContext(), "请检查网络连接", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject result = (JSONObject) JSON.parse(response);
                        JSONArray recArray = result.getJSONArray("data");

                        if(recArray != null && recArray.size() > 0 && mSwipeLayout.isRefreshing()) {
                            mRecLists.clear();
                        }

                        mBannerLists.clear();
                        mBannerImgLists.clear();

                        for (int i = 0; i < recArray.size(); i++) {
                            JSONObject data = (JSONObject) result.getJSONArray("data").get(i);
                            if(i == 0){
                                JSONArray banner_item = data.getJSONArray("banner_item");
                                for (int j = 0; j < banner_item.size(); j++) {
                                    RecBanner recBanner = new RecBanner();
                                    JSONObject banner = (JSONObject) banner_item.get(j);
                                    recBanner.setTitle(banner.getString("title"));
                                    recBanner.setImgUrl(banner.getString("image"));
                                    recBanner.setAd(banner.getBoolean("is_ad")==null?false:true);
                                    recBanner.setImgUrl(banner.getString("image"));
                                    mBannerLists.add(recBanner);
                                }
                            }else{
                                RecomendAnimModel recomendAnimModel = new RecomendAnimModel();
                                recomendAnimModel.setImg(data.getString("cover"));
                                recomendAnimModel.setType(data.getString("tname"));
                                recomendAnimModel.setPlayCount(""+data.getIntValue("play"));
                                recomendAnimModel.setCommentCount(""+data.getIntValue("danmaku"));
                                recomendAnimModel.setContent(data.getString("title"));
                                recomendAnimModel.setVedioTime(TimeUtils.getTimeStr(data.getIntValue("duration")));
                                mRecLists.add(recomendAnimModel);
                            }
                        }

                        filterUnique(mRecLists,mBannerLists);

                        setBannerImgs();
                        if(!mRecLists.isEmpty()){
                            myRecAdapter.notifyDataSetChanged();
                            if(mIsLoadingMore){
                                mIsLoadingMore = !mIsLoadingMore;
                            }
                            if(mSwipeLayout.isRefreshing()) {
                                mSwipeLayout.setRefreshing(false);
                            }
                        }
                    }
                });
    }

    private void initEvent() {
        mBannerViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mRecRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
                if (mSwipeLayout.isRefreshing()  || mIsLoadingMore) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        mBannerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
                    ImageView child = (ImageView) mDotsLayout.getChildAt(i);
                    if(position == i){
                        child.setImageResource(R.drawable.banner_selected_shape);
                    }else{
                        child.setImageResource(R.drawable.banner_inditor_shape);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                initData();
            }
        });
        mRecRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastItemPosition = 0;
                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    lastItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                }
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    int totalItemCount  = layoutManager.getItemCount();
                    int lastVisibleItem = lastItemPosition;
                    if(lastVisibleItem >= totalItemCount-1 && !mSwipeLayout.isRefreshing()){
                        mIsLoadingMore = true;
                        initData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setBannerImgs() {
        if(!mBannerLists.isEmpty()){
            if(mDotsLayout!= null && mDotsLayout.getChildCount() > 0){
                mDotsLayout.removeAllViews();
            }
            for (int i=0;i<mBannerLists.size();i++) {
                RecBanner recBanner = mBannerLists.get(i);
                SimpleDraweeView draweeView = new SimpleDraweeView(getContext());
                draweeView.setImageURI( recBanner.getImgUrl());
                mBannerImgLists.add(draweeView);
                ImageView imageView = new ImageView(getContext());
                if(i == 0) {
                    imageView.setImageResource(R.drawable.banner_selected_shape);
                }else{
                    imageView.setImageResource(R.drawable.banner_inditor_shape);
                }
                imageView.setPadding(5,0,5,0);
                mDotsLayout.addView(imageView);
            }
            Message message = new Message();
            message.obj = 1;
            message.what = 0x160;
            bannerHandler.sendMessageDelayed(message,2500);
        }
    }

    private void filterUnique(List<RecomendAnimModel> mRecLists, List<RecBanner> mBannerLists) {
        //去重并且按照自然顺序排列
        mRecLists = new ArrayList(new TreeSet(mRecLists));
        mBannerLists = new ArrayList(new TreeSet(mBannerLists));

    }

    public static Fragment getInstance() {
        return new AnimRecFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bannerHandler = null;
    }
}
