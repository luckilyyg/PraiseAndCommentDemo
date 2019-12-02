package com.crazy.crazy.fuzajiemian.provider;

import android.widget.Toast;

import com.chad.library.adapter.base.BaseViewHolder;
import com.crazy.crazy.R;
import com.crazy.crazy.fuzajiemian.BaseItemProvider;
import com.crazy.crazy.fuzajiemian.ItemListAdapter;
import com.crazy.crazy.fuzajiemian.NormalMultipleEntity;

/**
 * Created on 2019/11/26 16:11
 *
 * @auther super果
 * @annotation
 */
public class TextItemProvider extends BaseItemProvider<NormalMultipleEntity, BaseViewHolder> {
    @Override
    public int viewType() {
        return ItemListAdapter.TYPE_TEXT;
    }

    @Override
    public int layout() {
        return R.layout.item_common_text_view;
    }

    @Override
    public void convert(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        helper.setText(R.id.tv, data.title);
    }
    @Override
    public void onClick(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, NormalMultipleEntity data, int position) {
        Toast.makeText(mContext, "longClick", Toast.LENGTH_SHORT).show();
        return true;
    }
}
