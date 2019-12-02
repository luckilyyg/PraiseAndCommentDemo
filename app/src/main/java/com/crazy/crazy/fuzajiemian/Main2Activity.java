package com.crazy.crazy.fuzajiemian;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crazy.crazy.R;


import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main2Activity";
    private TextView tvMessage;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv;
    private List<NormalMultipleEntity> mData;
    private ItemListAdapter itemListAdapter;
    private View notDataView;
    private View errorView;
    private boolean mError = true;
    private boolean mNoData = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initAdapter();
        initRefreshLayout();
        refreshLayout.setRefreshing(true);
        onRefreshs();
    }


    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshs();
            }
        });
    }

    private void initView() {
        tvMessage = findViewById(R.id.tv_message);
        rv = findViewById(R.id.rv);
        refreshLayout = findViewById(R.id.refresh);
        rv.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) rv.getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);
                onRefreshs();
            }
        });
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) rv.getParent(), false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLayout.setRefreshing(true);
                onRefreshs();
            }
        });

    }

    private void onRefreshs() {
        //仿请求网络数据 正在加载中...
//        itemListAdapter.setEmptyView(R.layout.loading_view, (ViewGroup) rv.getParent());
        itemListAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mError) {//仿网络错误
                    itemListAdapter.setEmptyView(errorView);
                    mError = false;
                    itemListAdapter.setEnableLoadMore(true);
                    refreshLayout.setRefreshing(false);

                } else {//服务器数据返回为空
                    if (mNoData) {
                        itemListAdapter.setEmptyView(notDataView);
                        mNoData = false;
                        itemListAdapter.setEnableLoadMore(true);
                        refreshLayout.setRefreshing(false);

                    } else {//有数据
                        itemListAdapter.setNewData(getNormalMultipleEntities());
                        itemListAdapter.setEnableLoadMore(true);
                        refreshLayout.setRefreshing(false);

                    }
                }
            }
        }, 2000);
    }

    private void initAdapter() {
        itemListAdapter = new ItemListAdapter();
        rv.setAdapter(itemListAdapter);
    }


    //http://img5.mtime.cn/mg/2018/07/06/093947.51483272.jpg
    //http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4
    public List<NormalMultipleEntity> getNormalMultipleEntities() {
        List<News> mList = new ArrayList<>();
        mList.add(new News("我是第一个item", "", "http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4"));
        mList.add(new News("我是第二个item", "", "http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4"));
        mList.add(new News("我是第三个item", "http://img5.mtime.cn/mg/2018/07/06/093947.51483272.jpg", ""));
        mList.add(new News("我是第四个item", "", ""));
        mList.add(new News("我是第五个item", "http://img5.mtime.cn/mg/2018/07/06/093947.51483272.jpg", ""));
        mList.add(new News("我是第六个item", "", ""));
        mList.add(new News("我是第七个item", "", ""));
        mList.add(new News("我是第八个item", "http://img5.mtime.cn/mg/2018/07/06/093947.51483272.jpg", ""));
        mList.add(new News("我是第九个item", "", ""));
        mList.add(new News("我是第十个item", "", "http://vfx.mtime.cn/Video/2018/07/06/mp4/180706094003288023.mp4"));


        List<NormalMultipleEntity> list = new ArrayList<>();
        //重新组装数据进行测试
        for (int i = 0; i < mList.size(); i++) {
            if (mList.size() == 1) {
                //第一个显示视频
                if (mList.get(0).getVoicePath().equals("") && mList.get(0).getImgPath().equals("")) {//纯文字
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.SINGLE_TEXT, mList.get(0).getTitle(), ""));
                } else if (mList.get(0).getVoicePath().equals("") && !mList.get(0).getImgPath().equals("")) {//文字+图片
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.HEAD_TEXT_IMG, mList.get(0).getTitle(), mList.get(0).getImgPath()));
                } else if (!mList.get(0).getVoicePath().equals("") && mList.get(0).getImgPath().equals("")) {//文字+视频
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.HEAD_TEXT_VIDEO, mList.get(0).getTitle(), mList.get(0).getVoicePath()));
                }

            } else {//从第二个视图开始
                if (mList.get(i).getVoicePath().equals("") && mList.get(i).getImgPath().equals("")) {//纯文字
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.SINGLE_TEXT, mList.get(i).getTitle(), ""));
                } else if (mList.get(i).getVoicePath().equals("") && !mList.get(i).getImgPath().equals("")) {//文字+图片
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.TEXT_IMG, mList.get(i).getTitle(), mList.get(i).getImgPath()));
                } else if (!mList.get(i).getVoicePath().equals("") && mList.get(i).getImgPath().equals("")) {//文字+视频
                    list.add(new NormalMultipleEntity(NormalMultipleEntity.TEXT_VOICE, mList.get(i).getTitle(), mList.get(i).getVoicePath()));
                }
            }
        }

        return list;


    }

    public class News {

        private String title;
        private String imgPath;
        private String voicePath;
//其它属性可以参考UI

        public News(String title, String imgPath, String voicePath) {
            this.title = title;
            this.imgPath = imgPath;
            this.voicePath = voicePath;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgPath() {
            return imgPath == null ? "" : imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getVoicePath() {
            return voicePath == null ? "" : voicePath;
        }

        public void setVoicePath(String voicePath) {
            this.voicePath = voicePath;
        }
    }


}
