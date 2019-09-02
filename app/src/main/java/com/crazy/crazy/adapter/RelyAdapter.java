package com.crazy.crazy.adapter;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.crazy.R;
import com.crazy.crazy.entity.CommentItem;
import com.crazy.crazy.spannable.CircleMovementMethod;
import com.crazy.crazy.spannable.NameClickListener;
import com.crazy.crazy.spannable.NameClickable;


/**
 * Created on 2019/8/30 15:15
 *
 * @auther super果
 * @annotation
 */
public class RelyAdapter extends BaseQuickAdapter<CommentItem, BaseViewHolder> {

    public RelyAdapter() {
        super(R.layout.item_social_comment, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentItem bean) {
        String toReplyName = "";
        if (bean.getAppointUserid() != null) {
            toReplyName = bean.getAppointUserNickname();
        }
        String name = bean.getUserNickname();
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setClickableSpan(name,bean.getUserId(), 0));
        if (!TextUtils.isEmpty(toReplyName)) {

            builder.append(" 回复 ");
            builder.append(setClickableSpan(toReplyName,bean.getAppointUserNickname(), 1));
        }
        builder.append(": ");
        //转换表情字符
        String contentBodyStr = bean.getContent();
        builder.append(contentBodyStr);
        helper.setText(R.id.commentTv,builder);
    }
    @NonNull
    private SpannableString setClickableSpan(String textStr, String userId, int position) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new NameClickable(new NameClickListener(subjectSpanText,userId), position), 0, subjectSpanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }
}
