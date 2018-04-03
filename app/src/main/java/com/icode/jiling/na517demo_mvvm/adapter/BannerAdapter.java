package com.icode.jiling.na517demo_mvvm.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.icode.jiling.na517demo_mvvm.MyApplication;
import com.icode.jiling.na517demo_mvvm.R;
import com.icode.jiling.na517demo_mvvm.model.RecBanner;
import com.icode.jiling.na517demo_mvvm.utils.ScreenUtils;

import java.util.List;

public class BannerAdapter extends PagerAdapter {

    private List<SimpleDraweeView> mList;
    private List<RecBanner> mBanner;

    public BannerAdapter(List<SimpleDraweeView> mList, List<RecBanner> mBannerLists) {

        this.mList = mList;
        this.mBanner = mBannerLists;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView = mList.get(position);
        simpleDraweeView.setImageURI(mBanner.get(position).getImgUrl());
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setCornersRadius(MyApplication.applicaitonContext.getResources().getDimension(R.dimen.dimen_3));
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(MyApplication.applicaitonContext.getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setRoundingParams(roundingParams)
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
        container.addView(simpleDraweeView);

        return mList.get(position);

    }

    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }

    public int getCount() {
        return mList.size();
    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

}