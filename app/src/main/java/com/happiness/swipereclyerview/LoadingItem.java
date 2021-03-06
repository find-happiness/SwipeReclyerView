package com.happiness.swipereclyerview;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.xwray.groupie.Item;

public class LoadingItem extends Item<LoadingItem.ViewHolder> {

    private int loadingResId;

    public LoadingItem(@StringRes int loadingResId) {
        this.loadingResId = loadingResId;
    }

    @Override
    public void bind(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.loading.setText(loadingResId);
    }

    @Override
    public int getLayout() {
        return R.layout.loading_item;
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(@NonNull View itemView) {
        return new ViewHolder(itemView);
    }

    class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_loading)
        TextView loading;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
        }
    }
}
