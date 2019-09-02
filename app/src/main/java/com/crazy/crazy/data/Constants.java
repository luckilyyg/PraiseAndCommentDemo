package com.crazy.crazy.data;



import com.crazy.crazy.entity.CircleItem;
import com.crazy.crazy.entity.CommentItem;
import com.crazy.crazy.entity.FavortItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019/8/29 18:00
 *
 * @auther super果
 * @annotation 1、super果  10000
 * 2、Jack     10001
 * 3、Maria    10002
 * 4、Jim      10003
 * 5、Jon      10004
 */
public class Constants {
    public static List<CircleItem> getCircleItem() {
        List<CircleItem> circleItemList = new ArrayList<>();
        //-----------------------posion0  三个赞 三个评论-----------------------
        CircleItem item1 = new CircleItem();
        item1.setContent("小猪佩奇第1集");
        item1.setUserId("10000");
        item1.setNickName("super果");
        List<FavortItem> mGoodsjobs1 = new ArrayList<>();//第一个item有三个赞
        mGoodsjobs1.add(new FavortItem("10000", "super果"));
        item1.setGoodjobs(mGoodsjobs1);
        List<CommentItem> mCommentLists1 = new ArrayList<>();//第一个item有三个评论
        item1.setReplys(mCommentLists1);
        circleItemList.add(item1);
        //-----------------------posion1  有一个赞 三个评论-----------------------
        CircleItem item2 = new CircleItem();
        item2.setContent("小猪佩奇第2集");
        item2.setUserId("10002");
        item2.setNickName("Maria");
        List<FavortItem> mGoodsjobs2 = new ArrayList<>();//第二个item有一个赞
        mGoodsjobs2.add(new FavortItem("10000", "super果"));
        mGoodsjobs2.add(new FavortItem("10001", "Jack"));
        mGoodsjobs2.add(new FavortItem("10002", "Maria"));
        item2.setGoodjobs(mGoodsjobs2);
        List<CommentItem> mCommentLists2 = new ArrayList<>();//第二个item有三个评论
        mCommentLists2.add(new CommentItem("10001", "Jack", "", "", "nice to me you"));
        item2.setReplys(mCommentLists2);
        circleItemList.add(item2);
        //-----------------------posion2  四个赞  五个评论-----------------------
        CircleItem item3 = new CircleItem();
        item3.setContent("小猪佩奇第3集");
        item3.setUserId("10001");
        item3.setNickName("Jack");
        List<FavortItem> mGoodsjobs3 = new ArrayList<>();//第三个item有四个赞
        mGoodsjobs3.add(new FavortItem("10001", "Jack"));
        mGoodsjobs3.add(new FavortItem("10002", "Maria"));
        mGoodsjobs3.add(new FavortItem("10003", "Jim"));
        mGoodsjobs3.add(new FavortItem("10004", "Jon"));
        item3.setGoodjobs(mGoodsjobs3);
        List<CommentItem> mCommentLists3 = new ArrayList<>();//第三个item有五个评论
        mCommentLists3.add(new CommentItem("10001", "Jack", "", "", "nice to me you"));
        mCommentLists3.add(new CommentItem("10002", "Maria", "Jack", "10001", "nice to me you to"));
        item3.setReplys(mCommentLists3);
        circleItemList.add(item3);
        //-----------------------posion3  两个赞 一个评论-----------------------
        CircleItem item4 = new CircleItem();
        item4.setContent("小猪佩奇第4集");
        item4.setUserId("10003");
        item4.setNickName("Jim");
        List<FavortItem> mGoodsjobs4 = new ArrayList<>();//第四个item有两个赞
        mGoodsjobs4.add(new FavortItem("10004", "Jon"));
        mGoodsjobs4.add(new FavortItem("10003", "Jim"));
        item4.setGoodjobs(mGoodsjobs4);
        List<CommentItem> mCommentLists4 = new ArrayList<>();//第四个item有一个评论
        mCommentLists4.add(new CommentItem("10002", "Maria", "", "", "nice to me you"));
        mCommentLists4.add(new CommentItem("10001", "Jack", "", "", "abc"));
        mCommentLists4.add(new CommentItem("10002", "Maria", "Jack", "10001", "abc to"));
        item4.setReplys(mCommentLists4);
        circleItemList.add(item4);
        //-----------------------posion4  五个赞 五个评论-----------------------
        CircleItem item5 = new CircleItem();
        item5.setContent("小猪佩奇第5集");
        item5.setUserId("10004");
        item5.setNickName("Jon");
        List<FavortItem> mGoodsjobs5 = new ArrayList<>();//第五个item有五个赞
        mGoodsjobs5.add(new FavortItem("10000", "super果"));
        mGoodsjobs5.add(new FavortItem("10003", "Jim"));
        mGoodsjobs5.add(new FavortItem("10001", "Jack"));
        mGoodsjobs5.add(new FavortItem("10002", "Maria"));
        mGoodsjobs5.add(new FavortItem("10004", "Jon"));
        item5.setGoodjobs(mGoodsjobs5);
        List<CommentItem> mCommentLists5 = new ArrayList<>();//第五个item有五个评论
        mCommentLists5.add(new CommentItem("10002", "Maria", "Jack", "10001", "abc to"));
        mCommentLists5.add(new CommentItem("10003", "Jim", "", "", "def "));
        mCommentLists5.add(new CommentItem("10004", "Jon", "Jack", "10001", "ha hah  "));
        mCommentLists5.add(new CommentItem("10000", "super果", "", "", "love "));
        item5.setReplys(mCommentLists5);
        circleItemList.add(item5);
        //-----------------------posion5  一个赞 零个评论-----------------------
        CircleItem item6 = new CircleItem();
        item6.setContent("小猪佩奇第6集");
        item6.setUserId("10000");
        item6.setNickName("super果");
        List<FavortItem> mGoodsjobs6 = new ArrayList<>();//第六个item有一个赞
        mGoodsjobs6.add(new FavortItem("10000", "super果"));
        item6.setGoodjobs(mGoodsjobs6);
        List<CommentItem> mCommentLists6 = new ArrayList<>();//第六个item有零个评论
        mCommentLists6.add(new CommentItem("10002", "Maria", "Jack", "10001", "abc to"));
        mCommentLists6.add(new CommentItem("10003", "Jim", "", "", "def "));
        mCommentLists6.add(new CommentItem("10004", "Jon", "Jack", "10001", "ha hah  "));
        mCommentLists6.add(new CommentItem("10000", "super果", "", "", "love "));
        mCommentLists6.add(new CommentItem("10001", "Jack", "", "", "abc"));
        item6.setReplys(mCommentLists6);
        circleItemList.add(item6);
        //-----------------------posion6  零个赞  一个评论-----------------------
        CircleItem item7 = new CircleItem();
        item7.setContent("小猪佩奇第7集");
        item7.setUserId("10002");
        item7.setNickName("Maria");
        List<FavortItem> mGoodsjobs7 = new ArrayList<>();//第七个item有零个赞
        item7.setGoodjobs(mGoodsjobs7);
        List<CommentItem> mCommentLists7 = new ArrayList<>();//第七个item有一个评论
        mCommentLists7.add(new CommentItem("10001", "Jack", "", "", "abc"));
        mCommentLists7.add(new CommentItem("10002", "Maria", "Jack", "10001", "abc to"));
        mCommentLists7.add(new CommentItem("10003", "Jim", "", "", "def "));
        mCommentLists7.add(new CommentItem("10004", "Jon", "Jack", "10001", "ha hah  "));
        mCommentLists7.add(new CommentItem("10000", "super果", "", "", "love "));
        mCommentLists7.add(new CommentItem("10001", "Jack", "", "", "abc to"));
//        mCommentLists7.add(new CommentItem("10000", "super果", "", "", "love to"));
        item7.setReplys(mCommentLists7);
        circleItemList.add(item7);
        //-----------------------posion7 零个赞 零个评论-----------------------
        CircleItem item8 = new CircleItem();
        item8.setContent("小猪佩奇第8集");
        item8.setUserId("10001");
        item8.setNickName("Jack");
        List<FavortItem> mGoodsjobs8 = new ArrayList<>();//第七个item有零个赞
        item8.setGoodjobs(mGoodsjobs8);
        List<CommentItem> mCommentLists8 = new ArrayList<>();//第七个item有零个评论
        mCommentLists8.add(new CommentItem("10004", "Jon", "", "", "love you"));
        mCommentLists8.add(new CommentItem("10003", "Jim", "", "", "happy yea"));
        mCommentLists8.add(new CommentItem("10002", "Maria", "Jack", "10001", "nice to me you to"));
        mCommentLists8.add(new CommentItem("10003", "Jim", "", "", "love"));
        mCommentLists8.add(new CommentItem("10001", "Jack", "", "", "nice to me you"));
        mCommentLists8.add(new CommentItem("10003", "Jim", "", "", "love you"));
        mCommentLists8.add(new CommentItem("10004", "Jon", "Jack", "10001", "wa wa wa to"));
//        mCommentLists8.add(new CommentItem("10000", "super果", "", "", "love you"));
        item8.setReplys(mCommentLists8);
        circleItemList.add(item8);
        return circleItemList;
    }
}
