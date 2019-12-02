package com.crazy.crazy.fuzajiemian;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.crazy.fuzajiemian.provider.HeadTextImgItemProvider;
import com.crazy.crazy.fuzajiemian.provider.HeadTextVoiceItemProvider;
import com.crazy.crazy.fuzajiemian.provider.TextImgItemProvider;
import com.crazy.crazy.fuzajiemian.provider.TextItemProvider;
import com.crazy.crazy.fuzajiemian.provider.TextVoiceItemProvider;

import java.util.List;

/**
 * Created on 2019/11/26 15:37
 *
 * @auther super果
 * @annotation
 */
public class ItemListAdapter extends MultipleItemRvAdapter<NormalMultipleEntity, BaseViewHolder> {

    public static final int TYPE_TEXT = 100;//纯文字
    public static final int TYPE_TEXT_VOICE = 200;//文字+视频
    public static final int TYPE_TEXT_IMG = 300;//文字+图片
    public static final int TYPE_HEAD_TEXT_IMG = 400;//头部文字+图片
    public static final int TYPE_HEAD_TEXT_VOICE = 500;//头部文字+视频

    public ItemListAdapter() {
        super(null);

        finishInitialize();
    }

    @Override
    protected int getViewType(NormalMultipleEntity entity) {
        if (entity.type == NormalMultipleEntity.SINGLE_TEXT) {
            return TYPE_TEXT;
        } else if (entity.type == NormalMultipleEntity.TEXT_VOICE) {
            return TYPE_TEXT_VOICE;
        } else if (entity.type == NormalMultipleEntity.TEXT_IMG) {
            return TYPE_TEXT_IMG;
        } else if (entity.type == NormalMultipleEntity.HEAD_TEXT_IMG) {
            return TYPE_HEAD_TEXT_IMG;
        } else if (entity.type == NormalMultipleEntity.HEAD_TEXT_VIDEO) {
            return TYPE_HEAD_TEXT_VOICE;
        }

        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册相关的条目provider
        //Register related entries provider
        mProviderDelegate.registerProvider(new TextItemProvider());
        mProviderDelegate.registerProvider(new TextVoiceItemProvider());
        mProviderDelegate.registerProvider(new TextImgItemProvider());
        mProviderDelegate.registerProvider(new HeadTextImgItemProvider());
        mProviderDelegate.registerProvider(new HeadTextVoiceItemProvider());

    }
}
