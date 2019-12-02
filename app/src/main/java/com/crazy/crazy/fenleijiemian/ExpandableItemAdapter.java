package com.crazy.crazy.fenleijiemian;

import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.crazy.crazy.R;

import java.util.List;

/**
 * Created on 2019/11/27 14:54
 *
 * @auther super果
 * @annotation
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv2);
    }

    @Override
    protected void convert(final BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.title, lv1.title)
                        .setText(R.id.sub_title, "下级："+ lv1.subTitle)
                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.drawable.item_pull_up_btn : R.drawable.item_drop_down_btn);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 1 item pos: " + pos);
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getAdapterPosition();
                        remove(pos);
                        return true;
                    }
                });
                break;
            case TYPE_PERSON:
                final Person person = (Person) item;
                holder.setText(R.id.childtitle, person.name);
                holder.setText(R.id.sub_childtitle,"下级："+ person.age );
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getAdapterPosition();
                        remove(pos);
                    }
                });
                break;
        }
    }
}
