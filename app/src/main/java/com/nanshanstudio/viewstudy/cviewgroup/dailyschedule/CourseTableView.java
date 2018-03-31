package com.nanshanstudio.viewstudy.cviewgroup.dailyschedule;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nanshanstudio.viewstudy.R;
import com.nanshanstudio.viewstudy.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CourseTableView extends RelativeLayout {
    // 从周一到周日课程格子的背景图
    private static final int[] COURSE_BG = {
            R.drawable.course_info_light_blue,
            R.drawable.course_info_green,
            R.drawable.course_info_red,
            R.drawable.course_info_blue,
            R.drawable.course_info_yellow,
            R.drawable.course_info_orange,
            R.drawable.course_info_purple};

    private static final int FIRST_TV = 555;
    private static final int SHOW_WEEKS_FIRST_COL = 3;
    private List<? extends Course> coursesData;

    private String[] DAYS = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul","Aug", "Sep", "Oct", "Nov", "Dec"};

    //转换为和中国星期对应上
    private int[] US_DAYS_NUMS = {7, 1, 2, 3, 4, 5, 6};
    private FrameLayout flCourseContent;

    //保存由数据生成的View的容器
    private List<View> myCacheViews = new ArrayList<View>();

    private int firstRowHeight;
    private int firstColumnWidth;
    private int notFirstEveryColumnsWidth;
    private int notFirstEveryRowHeight;

    private int todayNum;
    private String[] datesOfMonth;

    private int twoW;
    private int oneW;
    private String whichMonth;

    private int totalJC = 12;
    private int sevenDays = 7;

    private OnCourseItemClickListener onCourseItemClickListener;

    public void setOnCourseItemClickListener(OnCourseItemClickListener onCourseItemClickListener) {
        this.onCourseItemClickListener = onCourseItemClickListener;
    }

    public interface OnCourseItemClickListener {
        void onCourseItemClick(TextView tv, int jieci, int day, String des);
    }

    public CourseTableView(Context context) {
        this(context, null);
    }

    public CourseTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CourseTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray ta = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CourseTable, defStyleAttr, 0);
        sevenDays = ta.getInt(R.styleable.CourseTable_totalDays, sevenDays);
        totalJC = ta.getInt(R.styleable.CourseTable_totalJC, totalJC);
        ta.recycle();

        init(context);
        drawFrame();

    }

    private void init(Context context) {
        Calendar toDayCal = Calendar.getInstance();
        toDayCal.setTimeInMillis(System.currentTimeMillis());
        twoW = Utils.dpToPx(context, 2);
        oneW = Utils.dpToPx(context, 1);
        todayNum = toDayCal.get(Calendar.DAY_OF_WEEK) - 1;

        datesOfMonth = getOneWeekDatesOfMonth();

        int screenWidth = getScreenWidth();
        int screenHeight = getScreenHeight();
        firstRowHeight = Utils.dpToPx(getContext(), 40);
        notFirstEveryColumnsWidth = screenWidth * 2 / (2 * sevenDays + 1);
        notFirstEveryRowHeight = (screenHeight - firstRowHeight) / totalJC + Utils.dpToPx(getContext(), 5);
        firstColumnWidth = notFirstEveryColumnsWidth / 2;
    }

    private void drawFrame() {

        drawFirstRow();
        addBottomRestView();
    }

    private void addBottomRestView() {
        //底部第一层包裹容器
        ScrollView sv = new ScrollView(getContext());
        LayoutParams rlp = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.addRule(RelativeLayout.BELOW, tv_whichMonth.getId());
        sv.setLayoutParams(rlp);
        sv.setVerticalScrollBarEnabled(false);

        //底部第二层包裹容器，默认水平方向
        LinearLayout llBottom = new LinearLayout(getContext());
        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llBottom.setLayoutParams(vlp);

        //底部第二层包裹容器的左边子布局
        LinearLayout llLeftCol = new LinearLayout(getContext());
        LinearLayout.LayoutParams llp1 = new LinearLayout.LayoutParams(firstColumnWidth,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        llLeftCol.setLayoutParams(llp1);
        llLeftCol.setOrientation(LinearLayout.VERTICAL);
        initLeftTextViews(llLeftCol);
        llBottom.addView(llLeftCol);

        //底部第二层包裹容器的左边子布局
        flCourseContent = new FrameLayout(getContext());
        LinearLayout.LayoutParams llp2 = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        flCourseContent.setLayoutParams(llp2);
            //绘制底部左侧的背景框
        for (int i = 0; i < sevenDays * totalJC; i++) {
            final int row = i / sevenDays;
            final int col = i % sevenDays;
            FrameLayout fl = new FrameLayout(getContext());
            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                    notFirstEveryColumnsWidth, notFirstEveryRowHeight);
            fl.setBackgroundResource(R.drawable.course_table_bg);
            flp.setMargins(col * notFirstEveryColumnsWidth, row * notFirstEveryRowHeight, 0, 0);
            fl.setLayoutParams(flp);
            flCourseContent.addView(fl);
        }
        llBottom.addView(flCourseContent);

        sv.addView(llBottom);
        addView(sv);
    }

    private void initLeftTextViews(LinearLayout llLeftCol) {
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(firstColumnWidth, notFirstEveryRowHeight);
        TextView textView;
        for (int i = 0; i < totalJC; i++) {
            textView = new TextView(getContext());
            textView.setLayoutParams(rlp);
            textView.setBackgroundResource(R.drawable.course_table_bg);
            textView.setText("" + (i + 1));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            llLeftCol.addView(textView);
        }
    }

    /**
     * 绘制第一行
     */
    private TextView tv_whichMonth;
    @SuppressWarnings("ResourceType")
    private void drawFirstRow() {
        //添加左上角第一个文本，在RelativeLayout中以这个文本控件为锚点，所有布局参照此点
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                firstColumnWidth, firstRowHeight);
        tv_whichMonth = new TextView(getContext());
        tv_whichMonth.setId(FIRST_TV);
        tv_whichMonth.setBackgroundResource(R.drawable.course_table_bg);
        tv_whichMonth.setText(whichMonth);
        tv_whichMonth.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        tv_whichMonth.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        tv_whichMonth.setPadding(oneW, twoW, oneW, twoW);
        tv_whichMonth.setLayoutParams(rlp);
        addView(tv_whichMonth);

        //添加从周一到周日
        initRestTv();

    }

    private void initRestTv() {
        LinearLayout linearLayout;
        RelativeLayout.LayoutParams rlp;
        TextView textView;
        for (int i = 0; i < sevenDays; i++) {
            //这使用LinearLayout(垂直)包裹两个TextView
            linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            //设置一个Id，加上前缀以防止重复（突然发现不加也行）
            linearLayout.setId(SHOW_WEEKS_FIRST_COL + i);
            //设置宽高
            rlp = new RelativeLayout.LayoutParams(notFirstEveryColumnsWidth,
                    firstRowHeight);
            //如果是第一个，则在左上角的TextView右侧
            if (i == 0)
                rlp.addRule(RelativeLayout.RIGHT_OF, tv_whichMonth.getId());
                //剩余的则后一个在前一个右侧
            else
                rlp.addRule(RelativeLayout.RIGHT_OF, SHOW_WEEKS_FIRST_COL + i - 1);
            linearLayout.setBackgroundResource(R.drawable.course_table_bg);
            linearLayout.setLayoutParams(rlp);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            //上方的显示日期的TextView
            textView = new TextView(getContext());
            textView.setText(datesOfMonth[i]);
            textView.setLayoutParams(llp);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(twoW, twoW, twoW, twoW);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            linearLayout.addView(textView);
            llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            //下方的显示星期数的TextView
            textView = new TextView(getContext());
            textView.setLayoutParams(llp);
            textView.setText(DAYS[i]);
            textView.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            //此处在今天这个格子中做高亮处理
            if (US_DAYS_NUMS[todayNum] - 1 == i) {
                linearLayout.setBackgroundColor(0x77069ee9);
            }
            textView.setPadding(twoW, 0, twoW, twoW * 2);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            linearLayout.addView(textView);
            addView(linearLayout);
        }
    }

    /**
     * 当前天所在的周的所有天（从周一到周日）
     */
    private String[] getOneWeekDatesOfMonth() {
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTimeInMillis(System.currentTimeMillis());
        String[] temp = new String[sevenDays];
        //---start 以下操作将Calendar偏移到本周所在的周一
        int transfer = US_DAYS_NUMS[todayCal.get(Calendar.DAY_OF_WEEK) - 1];//让美历的周跟把周一作为一周开始的制度做映射
        //7是美历的下个星期的周一，而在中国是星期日。如果不为7，则直接拿到这周的周一，如果为7则需要拿到上周的周一(美历)
        if (transfer != 7) {
            todayCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//将日期偏移到当前Calendar所在的周一
        } else {
            todayCal.add(Calendar.WEEK_OF_MONTH, -1);//跳转到上周今日
            todayCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//将日期偏移到当前Calendar所在的周一
        }
        //---end
        int whichday = 0;
        for (int i = 1; i < sevenDays; i++) {
            if (i == 1) {
                whichday = todayCal.get(Calendar.DAY_OF_MONTH);
                temp[i - 1] = todayCal.get(Calendar.DAY_OF_MONTH) + "";
                whichMonth = MONTHS[todayCal.get(Calendar.MONTH)];
            }
            todayCal.add(Calendar.DATE, 1);
            if (todayCal.get(Calendar.DAY_OF_MONTH) < whichday) {
                temp[i] = MONTHS[todayCal.get(Calendar.MONTH)];
                whichday = todayCal.get(Calendar.DAY_OF_MONTH);
            } else {
                temp[i] = todayCal.get(Calendar.DAY_OF_MONTH) + "";
            }
        }
        return temp;
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getScreenHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public void setTotalJC(int totalJC) {
        this.totalJC = totalJC;
        refreshCurrentLayout();
    }

    public void setSevenDays(int sevenDays) {
        this.sevenDays = sevenDays;
        refreshCurrentLayout();
    }

    private void refreshCurrentLayout() {
        removeAllViews();
        init(getContext());
        drawFrame();
        updateCourseViews();
    }

    public void updateCourseViews(List<? extends Course> coursesData) {
        this.coursesData = coursesData;
        updateCourseViews();
    }

    private void updateCourseViews() {
        clearViewsIfNeeded();
        FrameLayout fl;
        FrameLayout.LayoutParams flp;
        TextView tv;
        for (final Course c : coursesData) {
            final int jieci = c.getJieci();
            final int day = c.getDay();
            fl = new FrameLayout(getContext());
            flp = new FrameLayout.LayoutParams(notFirstEveryColumnsWidth,
                    notFirstEveryRowHeight * c.getSpanNum());
            flp.setMargins((day - 1) * notFirstEveryColumnsWidth, (jieci - 1) * notFirstEveryRowHeight, 0, 0);
            fl.setLayoutParams(flp);
            fl.setPadding(twoW, twoW, twoW, twoW);

            tv = new TextView(getContext());//文本的直接上一级容器是FrameLayout
            flp = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setBackgroundResource(COURSE_BG[day - 1]);
            tv.setLayoutParams(flp);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(twoW, twoW, twoW, twoW);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setLines(7);
            tv.setText(c.getDes());
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCourseItemClickListener != null)
                        onCourseItemClickListener.onCourseItemClick((TextView) v, jieci, day, c.getDes());
                }
            });
            fl.addView(tv);

            myCacheViews.add(fl);
            flCourseContent.addView(fl);
        }
    }

    private void clearViewsIfNeeded() {
        if (myCacheViews == null || myCacheViews.isEmpty())
            return;

        for (int i = myCacheViews.size() - 1; i >= 0; i--) {
            flCourseContent.removeView(myCacheViews.get(i));
            myCacheViews.remove(i);
        }
    }

}
