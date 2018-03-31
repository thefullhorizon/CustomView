package com.nanshanstudio.viewstudy.referedclassstudy.ssroller;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.nanshanstudio.viewstudy.utils.Utils;

import java.util.List;


public class OptionWidget extends LinearLayout {

    private Scroller mScroller;
    private List<String> sortList;
    private Context context;

    private int itemHeight = 40;

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OptionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.TRANSPARENT);

    }

    public void showOrHide() {
        if (getScrollY() == 0) {
            smoothScrollTo(0, 400);
        }else {
            setVisibility(View.VISIBLE);
            smoothScrollTo(0, 0);
        }
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollY = getScrollY();
        int deltaY = destY - scrollY;
        mScroller.startScroll(0, scrollY, 0, deltaY, 1000);
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }


    public interface OnItemClickListener{
        void onClick(int potion);
    }

    public void setSortList(Context context,List<String> sortList) {
        this.sortList = sortList;

        setScrollY(Utils.dpToPx(context,itemHeight * sortList.size()));
        for (int i = 0;i < sortList.size();i++){
            TextView tvItem = new TextView(context);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    Utils.dpToPx(context,itemHeight));
            tvItem.setLayoutParams(layoutParams);
            tvItem.setBackgroundColor(Color.WHITE);
            tvItem.setGravity(Gravity.CENTER);
            tvItem.setText(sortList.get(i));
            tvItem.setTag(i);
            tvItem.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOrHide();
                    if (onItemClickListener != null)
                        onItemClickListener.onClick((int)v.getTag());
                }
            });
            addView(tvItem);
        }

    }

}
