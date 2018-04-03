package com.icode.jiling.na517demo_mvvm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.icode.jiling.na517demo_mvvm.R;
import com.icode.jiling.na517demo_mvvm.model.RecomendAnimModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by jiling on 2018/4/3.
 */

public class MyRecAdapter extends BaseQuickAdapter<RecomendAnimModel,BaseViewHolder> {

    public MyRecAdapter(List<RecomendAnimModel> recLists) {
        super(R.layout.rec_list_item,recLists);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecomendAnimModel item) {
        helper.setText(R.id.tv_anim_type,item.getType());
        helper.setText(R.id.tv_content,item.getContent());
        helper.setText(R.id.tv_comment_time,item.getCommentCount());
        helper.setText(R.id.tv_vedio_time,item.getVedioTime());
        float parseInt = Integer.parseInt(item.getPlayCount());
        float playCount;
        DecimalFormat fnum = new DecimalFormat("##0.0");
        if(parseInt > 10000){
            playCount = parseInt/10000;
            helper.setText(R.id.tv_play_time,fnum.format(playCount)+"万");
        }else{
            helper.setText(R.id.tv_play_time,item.getPlayCount());
        }

        SimpleDraweeView simpleDraweeView = helper.getView(R.id.iv_anim_pic);
        simpleDraweeView.setImageURI(item.getImg());
    }
}
