package com.crazy.crazy.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.crazy.AppCache;
import com.crazy.crazy.R;
import com.crazy.crazy.entity.CircleItem;
import com.crazy.crazy.entity.CommentConfig;
import com.crazy.crazy.entity.CommentItem;
import com.crazy.crazy.entity.FavortItem;
import com.crazy.crazy.interfaces.OnClickListenerDoFavort;
import com.crazy.crazy.interfaces.OnClickListenerUpdateEditTextBody;
import com.crazy.crazy.spannable.ISpanClick;
import com.crazy.crazy.view.FavortListView;


import java.util.List;

/**
 * Created on 2019/8/30 10:54
 *
 * @auther super果
 * @annotation
 */
public class CircleAdapter extends BaseQuickAdapter<CircleItem, BaseViewHolder> {
    private OnClickListenerDoFavort onClickListenerDoFavort;
    private OnClickListenerUpdateEditTextBody onClickListenerUpdateEditTextBody;

    public CircleAdapter() {
        super(R.layout.item_circle_list, null);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CircleItem item) {
        helper.setText(R.id.nameTv, item.getNickName());
        helper.setText(R.id.urlTipTv, item.getContent());
        FavortListView favortListView = helper.getView(R.id.favortListTv);
        FavortListAdapter favortListAdapter = new FavortListAdapter();
        favortListView.setAdapter(favortListAdapter);
        RecyclerView rvComment = helper.getView(R.id.commentList);
        rvComment.setLayoutManager(new LinearLayoutManager(mContext));
        //注释：如果写到下面判断语句 if (hasComment) 就会出现数据复用的现象；
        RelyAdapter relyAdapter = new RelyAdapter();
        rvComment.setAdapter(relyAdapter);
        final List<FavortItem> mFavorList = item.getGoodjobs();
        final List<CommentItem> mCommentList = item.getReplys();
        LinearLayout llPorC = helper.getView(R.id.digCommentBody);//涉及评论+点赞
        View digLine = helper.getView(R.id.lin_dig);//涉及评论+点赞的分割线
        final boolean hasFavort = item.getGoodjobs().size() > 0 ? true : false;
        boolean hasComment = item.getReplys().size() > 0 ? true : false;
        digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);//涉及评论+点赞的分割线
        if (hasFavort || hasComment) {
            llPorC.setVisibility(View.VISIBLE);
            if (hasFavort) {
                favortListView.setSpanClickListener(new ISpanClick() {
                    @Override
                    public void onClick(int position) {
                        //点赞名单的操作事件
                        Toast.makeText(mContext, mFavorList.get(position).getUserNickname(), Toast.LENGTH_SHORT).show();
                    }
                });
                //点赞
                favortListAdapter.setDatas(item.getGoodjobs());
                favortListAdapter.notifyDataSetChanged();
            }

            if (hasComment) {
                //评论
                relyAdapter.setNewData(item.getReplys());

                relyAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        /**
                         * 有两种情况
                         * 1、回复别人
                         * 2、操作自己的评论 可以是复制or删除
                         */
                        CommentItem commentItem = item.getReplys().get(position);
                        if (AppCache.getInstance().getUserId().equals(commentItem.getUserId())) {
                            Toast.makeText(mContext, "复制或者删除自己的评论", Toast.LENGTH_SHORT).show();
                        } else {
                            //进行对别人的评价 键盘弹起  动态的position1 评论的position1
                            CommentConfig config = new CommentConfig();
                            config.circlePosition = helper.getAdapterPosition();
                            config.commentPosition = position;
                            config.commentType = CommentConfig.Type.REPLY;
                            config.setId(commentItem.getUserId());
                            config.setName(commentItem.getUserNickname());
                            onClickListenerUpdateEditTextBody.updateEditTextBodyVisible(View.VISIBLE, config);
                        }
                    }
                });
                relyAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                        return false;
                    }
                });
            }
        } else {
            llPorC.setVisibility(View.GONE);
        }


        //点赞按钮监听事件
        helper.getView(R.id.favortBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 判断是否点赞过
                 * 有 ->取消点赞
                 * 没有 ->点赞
                 */
                //在点赞集合中判断是否 有自己的userId
                String curUserFavortId = item.getCurUserFavortId();
                if (!TextUtils.isEmpty(curUserFavortId)) {
                    //有-要做的操作：取消
                    favort(helper.getAdapterPosition(), "取消");

                } else {
                    //没有-要做的操作：点赞
                    favort(helper.getAdapterPosition(), "赞");

                }
            }
        });


        helper.getView(R.id.commentBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *评论 不是回复别人
                 */
                CommentConfig config = new CommentConfig();
                config.circlePosition = helper.getAdapterPosition();
                config.commentType = CommentConfig.Type.PUBLIC;
                onClickListenerUpdateEditTextBody.updateEditTextBodyVisible(View.VISIBLE, config);
            }
        });


    }

    /**
     * 点赞、取消点赞
     */
    private long mLasttime = 0;

    private void favort(int circlePosition, String mTitle) {
        if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
            return;
        mLasttime = System.currentTimeMillis();
        if ("赞".equals(mTitle)) {
            FavortItem item = new FavortItem(AppCache.getInstance().getUserId(), AppCache.getInstance().getUserName());
            onClickListenerDoFavort.update2AddFavorite(circlePosition, item);

        } else {//取消点赞
            onClickListenerDoFavort.update2DeleteFavort(circlePosition, AppCache.getInstance().getUserId());

        }

    }

    /**
     * 点赞回调接口
     *
     * @param onClickListenerDoFavort
     */
    public void setCallBackFavortListener(OnClickListenerDoFavort onClickListenerDoFavort) {
        this.onClickListenerDoFavort = onClickListenerDoFavort;
    }


    public void setCallBackUpdateEditTextBody(OnClickListenerUpdateEditTextBody onClickListenerUpdateEditTextBody) {
        this.onClickListenerUpdateEditTextBody = onClickListenerUpdateEditTextBody;
    }
}
