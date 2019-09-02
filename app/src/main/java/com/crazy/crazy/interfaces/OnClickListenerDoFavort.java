package com.crazy.crazy.interfaces;


import com.crazy.crazy.entity.FavortItem;

/**
 * Created on 2019/9/2 10:11
 *
 * @auther super果
 * @annotation
 *
 */
public interface OnClickListenerDoFavort {
    void update2AddFavorite(int circlePosition, FavortItem addItem);
    void update2DeleteFavort(int circlePosition, String UserId);
}
