package com.crazy.crazy.ui;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazy.crazy.AppCache;
import com.crazy.crazy.R;
import com.crazy.crazy.adapter.CircleAdapter;
import com.crazy.crazy.data.Constants;
import com.crazy.crazy.entity.CommentConfig;
import com.crazy.crazy.entity.CommentItem;
import com.crazy.crazy.entity.FavortItem;
import com.crazy.crazy.interfaces.OnClickListenerDoFavort;
import com.crazy.crazy.interfaces.OnClickListenerUpdateEditTextBody;
import com.crazy.crazy.util.DisplayUtil;
import com.crazy.crazy.util.KeyBordUtil;
import com.crazy.crazy.util.ToastUitl;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListenerDoFavort, OnClickListenerUpdateEditTextBody {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private RecyclerView irc;
    private LinearLayout mEditTextBodyLl;//底部评论框
    private EditText mEdt;
    private ImageView mImgSend;
    private CircleAdapter adapter;
    private CommentConfig mCommentConfig;
    private LinearLayoutManager linearLayoutManager;
    private int mScreenHeight;
    private int mEditTextBodyHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        initView();
        initData();
        initEvent();
    }

    private void initVariable() {

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        irc = findViewById(R.id.rv);
        mEditTextBodyLl = findViewById(R.id.editTextBodyLl);
        mEdt = findViewById(R.id.circleEt);
        mImgSend = findViewById(R.id.sendIv);
        linearLayoutManager = new LinearLayoutManager(this);
        irc.setLayoutManager(linearLayoutManager);
        /**
         * 滑动列表关闭输入框
         */
        irc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mEditTextBodyLl.getVisibility() == View.VISIBLE)
                    updateEditTextBodyVisible(View.GONE, null);
                return false;
            }
        });


        irc.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://滚动停止
                        Log.e(TAG, "SCROLL_STATE_IDLE: ");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://手指 拖动
                        Log.e(TAG, "SCROLL_STATE_DRAGGING: ");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滚动
                        Log.e(TAG, "SCROLL_STATE_SETTLING: ");
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "dx: " + dx);
                int scrollY = 0;
                scrollY += dy;
                Log.e(TAG, "scrollY: " + scrollY);
            }
        });
    }

    private void initData() {
        adapter = new CircleAdapter();
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setCallBackFavortListener(this);
        adapter.setCallBackUpdateEditTextBody(this);
        adapter.setNewData(Constants.getCircleItem());
        irc.setAdapter(adapter);
        setViewTreeObserver();
    }

    /**
     * 监听recyclerview滑动
     */
    private void setViewTreeObserver() {
        final ViewTreeObserver swipeRefreshLayoutVTO = irc.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();//系统状态栏会影响getWindowVisibleDisplayFrame()执行结果outRect中的top属性的值
                irc.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = irc.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);//如果虚拟键是隐藏的则r.bottom就是屏幕的高度
//                LogUtils.logd("screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if (keyboardH == mCurrentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度
                mEditTextBodyHeight = mEditTextBodyLl.getHeight();//评论输入框的控件

                //偏移listview
                if (irc != null && mCommentConfig != null) {
//                    int index = mCommentConfig.circlePosition+1;
                    int index = mCommentConfig.circlePosition;
                    linearLayoutManager.scrollToPositionWithOffset(index, getListviewOffset(mCommentConfig));
                }
            }
        });
    }

    /**
     * 测量偏移量
     *
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight - toolbar.getMeasuredHeight();
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset - toolbar.getMeasuredHeight();
        }
        return listviewOffset;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void initEvent() {
        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发布评论
                String content = mEdt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUitl.showToastWithImg("评论内容不能为空", R.drawable.ic_warm);
                    return;
                }
                update2AddComment(content, mCommentConfig);//发布评论
                updateEditTextBodyVisible(View.GONE, null);
            }
        });
    }

    public void update2AddComment(String content, CommentConfig addItem) {
        if (addItem != null) {
            adapter.getData().get(addItem.circlePosition).getReplys().add(new CommentItem(AppCache.getInstance().getUserId(), AppCache.getInstance().getUserName(), addItem.getName(), addItem.getId(), content));
            adapter.notifyItemChanged(addItem.circlePosition);
        }
        //清空评论文本
        mEdt.setText("");
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        Log.e("test", "update2AddFavorite: ");
        adapter.getData().get(circlePosition).getGoodjobs().add(addItem);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String userId) {
        Log.e("test", "update2DeleteFavort: ");
        List<FavortItem> items = adapter.getData().get(circlePosition).getGoodjobs();
        for (int i = 0; i < items.size(); i++) {
            if (userId.equals(items.get(i).getUserId())) {
                items.remove(i);
                adapter.notifyItemChanged(circlePosition);
                adapter.getData().toString();
                return;
            }
        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        mCommentConfig = commentConfig;
        mEditTextBodyLl.setVisibility(visibility);
        measureCircleItemHighAndCommentItemOffset(commentConfig);
        if (commentConfig != null && CommentConfig.Type.REPLY.equals(commentConfig.getCommentType())) {
            mEdt.setHint("回复" + commentConfig.getName() + ":");
        } else {
            mEdt.setHint("说点什么吧");
        }
        if (View.VISIBLE == visibility) {
            mEdt.requestFocus();
            //弹出键盘
            KeyBordUtil.showSoftKeyboard(mEdt);
        } else if (View.GONE == visibility) {
            //隐藏键盘
            KeyBordUtil.hideSoftKeyboard(mEdt);
        }
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;
//        int headViewCount = irc.getHeaderContainer().getChildCount();
        //当前选中的view
//        int selectPostion = commentConfig.circlePosition + headViewCount + 1;
        int selectPostion = commentConfig.circlePosition + 1;
        View selectCircleItem = linearLayoutManager.findViewByPosition(selectPostion);

        if (selectCircleItem != null) {
            mSelectCircleItemH = selectCircleItem.getHeight() - DisplayUtil.dip2px(48);
            //获取评论view,计算出该view距离所属动态底部的距离
            if (commentConfig.commentType == CommentConfig.Type.REPLY) {
                //回复评论的情况
                RecyclerView commentLv = selectCircleItem.findViewById(R.id.commentList);
                if (commentLv != null) {
                    //找到要回复的评论view,计算出该view距离所属动态底部的距离
                    View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                    if (selectCommentItem != null) {
                        //选择的commentItem距选择的CircleItem底部的距离
                        mSelectCommentItemOffset = 0;
                        View parentView = selectCommentItem;//被回复评论的view
                        do {
                            int subItemBottom = parentView.getBottom();
                            parentView = (View) parentView.getParent();
                            if (parentView != null) {
                                mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                            }
                        }
                        while (parentView != null && parentView != selectCircleItem);
                    }
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (mEditTextBodyLl != null && mEditTextBodyLl.getVisibility() == View.VISIBLE) {
                mEditTextBodyLl.setVisibility(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
