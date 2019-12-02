package com.crazy.crazy.fuzajiemian;

import java.util.List;

/**
 * Created on 2019/11/26 15:39
 *
 * @auther super果
 * @annotation
 */
public class NormalMultipleEntity {
    public static final int SINGLE_TEXT = 1;//纯文字
    public static final int TEXT_VOICE = 2;//文字加视频
    public static final int TEXT_IMG = 3;//文字加图片

    public static final int HEAD_TEXT_IMG = 4;//头部文字+图片
    public static final int HEAD_TEXT_VIDEO = 5;//头部文字+视频

    public int type;
    public String title, path;//path:图片的地址或者是视频的地址

    public NormalMultipleEntity(int type) {
        this.type = type;
    }

    public NormalMultipleEntity(int type, String title, String path) {
        this.type = type;
        this.path = path;
        this.title = title;
    }
}
