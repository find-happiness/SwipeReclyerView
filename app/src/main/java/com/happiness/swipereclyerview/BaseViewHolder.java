package com.happiness.swipereclyerview;

import android.support.annotation.NonNull;
import android.view.View;
import butterknife.ButterKnife;
import com.xwray.groupie.ViewHolder;

public class BaseViewHolder extends ViewHolder {

    public BaseViewHolder(@NonNull View rootView) {
        super(rootView);
        ButterKnife.bind(this, rootView);
    }
}
