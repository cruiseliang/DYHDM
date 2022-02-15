package com.yuan.dyhdm.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yuan.dyhdm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by liangxuedong on 2021/6/2
 * <p>
 * 原文链接：https://blog.csdn.net/XiFangzheng/article/details/78122127
 * <p>
 * 可以参考地址 ：https://www.jianshu.com/p/6827a8ab737b
 * https://github.com/PhilJay/MPAndroidChart#documentation
 * https://gitee.com/relin/CircleStatisticalView
 * <p>
 * <p>
 * https://blog.csdn.net/csdn_aiyang/article/details/71616979
 * <p>
 * https://www.cnblogs.com/everhad/p/5809982.html
 * <p>
 * 基础边距 圆心位置 半径等变量采取单位为dp
 * 点击事件获取点位px 需要转化后进行比对
 * 文本连线色值 #D8D8D8
 */
public class RingView extends View {

    private Context mContext;
    private Paint mPaint;
    private int mPaintWidth = 0;        // 画笔的宽
    private int topMargin = 30;         // 上边距
    private int leftMargin = 80;        // 左边距
    private Resources mRes;
    private DisplayMetrics dm;
    private int showRateSize = 14; // 展示文字的大小

    private int circleCenterX = 96;     // 圆心点X  要与外圆半径相等
    private int circleCenterY = 96;     // 圆心点Y  要与外圆半径相等

    private int ringOuterRidus = 96;     // 外圆的半径
    private int ringInnerRidus = 73;     // 内圆的半径
    private int ringPointRidus = 96;    // 点所在圆的半径

    private float rate = 0.3f;     //点的外延距离  与  点所在圆半径的长度比率
    private float extendLineWidth = 15;     //点外延后  折的横线的长度

    private RectF rectF;                // 外圆所在的矩形
    private RectF rectFPoint;           // 点所在的矩形

    private List<Integer> colorList;
    private List<Float> rateList;
    private boolean isRing;
    private boolean isShowCenterPoint;
    private boolean isShowRate;

    private int mCurrentItem = -1;
    List<Point> pointList = new ArrayList<>();
    List<Point> pointArcCenterList = new ArrayList<>();

    public RingView(Context context) {
        super(context, null);
    }

    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();

        initAnims();
        initAction();

    }

    public void setShow(List<Integer> colorList, List<Float> rateList) {
        setShow(colorList, rateList, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing) {
        setShow(colorList, rateList, isRing, false);
    }

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate) {
        setShow(colorList, rateList, isRing, isShowRate, false);
    }

    public float[] angles;

    public void setShow(List<Integer> colorList, List<Float> rateList, boolean isRing, boolean isShowRate, boolean isShowCenterPoint) {
        this.colorList = colorList;
        this.rateList = rateList;
        this.isRing = isRing;
        this.isShowRate = isShowRate;
        this.isShowCenterPoint = isShowCenterPoint;

        angles = new float[rateList.size()];
        double total = 0;
        for (int i = 0; i < rateList.size(); i++) {
            total += rateList.get(i);
        }

        for (int j = 0; j < rateList.size(); j++) {
            angles[j] = (float) ((rateList.get(j) / total) * 360f);
        }

        runShowOutAnim();

    }

    private void initView() {
        this.mRes = mContext.getResources();
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
        leftMargin = (px2dip(screenWidth) - (2 * circleCenterX)) / 2;

        mPaint.setColor(getResources().getColor(R.color.color_Red));
        mPaint.setStrokeWidth(dip2px(mPaintWidth));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        rectF = new RectF(dip2px(mPaintWidth + leftMargin),
                dip2px(mPaintWidth + topMargin),
                dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin));

        rectFPoint = new RectF(dip2px(mPaintWidth + leftMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(mPaintWidth + topMargin + (ringOuterRidus - ringPointRidus)),
                dip2px(circleCenterX + ringPointRidus + mPaintWidth * 2 + leftMargin),
                dip2px(circleCenterY + ringPointRidus + mPaintWidth * 2 + topMargin));

        Log.e("矩形点:", dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2) + " --- " + dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2));

    }

    private void initAction() {
        this.setLongClickable(false);
    }

    private static final int ANIM_MODE_NONE = 0;
    private static final int ANIM_MODE_ROTATE = 1;
    private static final int ANIM_MODE_SHOW_OUT = 2;
    private static final int ANIM_MODE_GROW = 3;
    public static final int GROW_MODE_MOVE_OUT = 1;
    public static final int GROW_MODE_BOLD = 2;
    public static final int ANIM_MODE_FINISHED=4;

    private Animation mAnimShowOut;//初始动画

    private Animation mAnimGrow;//点击动画
    private int mGrowMode = GROW_MODE_MOVE_OUT;

    private int mGrowDuration = 200;
    private int mShowOutDuration = 500;

    private int mAnimMode = ANIM_MODE_NONE;

    private float mShowOutProgress = 1f;
    // region 增大某扇形相关字段
    private int mGrownItem = -1;
    private float mGrowProgress = 1f;

    private void initAnims() {
        mAnimShowOut = new Animation() {
            @Override
            protected void applyTransformation(final float interpolatedTime, final Transformation t) {
                mShowOutProgress = interpolatedTime;
                invalidate();
                if (interpolatedTime >= 1.0f) {
                    cancel();
                    mAnimMode = ANIM_MODE_FINISHED;
                    invalidate();
                    // mAnimMode = ANIM_MODE_NONE;
                    // 目前动画都是通过Animation完成的，而不是在onDraw中递归invalidate实现，所以为了
                    // 避免两个连续的动画产生“跳跃”，将下一个旋转动画放到下个UI循环中
                    post(new Runnable() {
                        @Override
                        public void run() {
                            int item = Math.max(0, angles.length - 1);
//                            setCurrentItem(item, false);
                        }
                    });
                }
            }
        };
        mAnimShowOut.setDuration(mShowOutDuration);

        mAnimGrow = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, final Transformation t) {
                mGrowProgress = interpolatedTime;
                invalidate();
                if (interpolatedTime >= 1.0f) {
                    cancel();
                    mAnimMode = ANIM_MODE_NONE;
                    if (mItemChangeListener != null) {
                        mItemChangeListener.onItemSelected();
                    }
                }
            }
        };
        mAnimGrow.setDuration(mGrowDuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointList.clear();
        mPaint.setStyle(Paint.Style.FILL);
        if (rateList == null)
            return;

//        drawArcAndText(canvas);


        switch (mAnimMode) {

            case ANIM_MODE_SHOW_OUT:
                animDrawProceed(canvas, mShowOutProgress);
                if (isRing) {
                    drawInner(canvas);
                }
                if (isShowCenterPoint) {
                    drawCenterPoint(canvas);
                }

//                canvas.drawArc(rectF, 0, 360, true, mPaint);
                break;
            case ANIM_MODE_GROW:
                drawArcAndText(canvas);
                break;
            case ANIM_MODE_NONE:
//                drawGrownPie(canvas);
                break;
            case ANIM_MODE_FINISHED:
                //动画完成后绘制折现 文本
//                drawableText(canvas);
                drawArcAndText(canvas);
//                mAnimMode=ANIM_MODE_FINISHED;

        }
    }

    /**
     * 根据进度值，以动画方式画饼状图。动画方式：圆环行进的方式从头到尾出现直至完全展示。
     *
     * @param canvas   onDraw中得到的画布
     * @param progress 进度 0~1
     */
    private void animDrawProceed(Canvas canvas, float progress) {
        float rotatedStart = -90;
        float end = rotatedStart + 360f * progress;
        drawPieFromEnd(canvas, rotatedStart, end);
//        drawablePieFromStart(canvas, rotatedStart, end);
    }

    /**
     * 从尾部开始绘制圆环，只绘制endAngle到startAngle之间的，不一定绘制所有圆环。
     *
     * @param canvas
     * @param startAngle
     */
    private void drawPieFromEnd(Canvas canvas, float startAngle, float endAngle1) {
        if (angles == null) return;

        for (int i = angles.length - 1; i >= 0; i--) {
            float itemAngle = angles[i] + 0.5f;
            float sweepStart = endAngle1 - itemAngle;
            mPaint.setColor(mRes.getColor(colorList.get(i)));


            if (sweepStart >= startAngle) {
                canvas.drawArc(rectF, sweepStart, itemAngle, true, mPaint);

            } else {
                itemAngle = endAngle1 - startAngle;

                canvas.drawArc(rectF, startAngle, itemAngle, true, mPaint);
                break;
            }

            endAngle1 -= itemAngle;


        }
    }
    private void drawableText(Canvas canvas){
        for (int i=0;i<rateList.size();i++){

                if (rateList != null) {
                    endAngle = getAngle(rateList.get(i));
                }
                if (isShowRate) {
                    drawArcCenterPoint(canvas, i);
                }
                preAngle = preAngle +angles[i];
        }
    }

    private void drawablePieFromStart(Canvas canvas, float startAngle, float endAngle) {
        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);

                float itemAngle = angles[i] + 0.5f;
                float sweepStart = endAngle - itemAngle;

                if (sweepStart >= startAngle) {
                    canvas.drawArc(rectF, sweepStart, itemAngle, true, mPaint);
                    if (isShowRate) {
                        drawArcCenterPoint(canvas, i);
                    }
                } else {
                    itemAngle = endAngle - startAngle;

                    canvas.drawArc(rectF, startAngle, itemAngle, true, mPaint);

                    if (isShowRate) {
                        drawArcCenterPoint(canvas, i);
                    }
                    break;
                }


//                drawOuter(canvas, i);
                endAngle -= itemAngle;
            }

        }

    }

    private void drawArcAndText(Canvas canvas) {
        //mGrownItem  针对选中item进行圆弧半径扩大处理  文本展示相应扩大
        pointList.clear();
        if (colorList != null) {
            for (int i = 0; i < colorList.size(); i++) {
                mPaint.setColor(mRes.getColor(colorList.get(i)));
                mPaint.setStyle(Paint.Style.FILL);
                drawOuter(canvas, i);
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        if (isRing) {
            drawInner(canvas);
        }
        if (isShowCenterPoint) {
            drawCenterPoint(canvas);
        }
    }

    private void drawOuter(Canvas canvas, int position) {
//        canvas.drawCircle(circleCenterX, circleCenterY, ringInnerRidus, mPaint);
        if (rateList != null) {
            endAngle = getAngle(rateList.get(position));
        }

        if (mGrownItem>=0&&mGrownItem==position){
            rectF = new RectF(dip2px(mPaintWidth + leftMargin-10),
                    dip2px(mPaintWidth + topMargin-10),
                    dip2px(circleCenterX + ringOuterRidus+10 + mPaintWidth * 2 + leftMargin),
                    dip2px(circleCenterY + ringOuterRidus+10  + mPaintWidth * 2 + topMargin));
        }else{
            rectF = new RectF(dip2px(mPaintWidth + leftMargin),
                    dip2px(mPaintWidth + topMargin),
                    dip2px(circleCenterX + ringOuterRidus + mPaintWidth * 2 + leftMargin),
                    dip2px(circleCenterY + ringOuterRidus + mPaintWidth * 2 + topMargin));
        }
        canvas.drawArc(rectF, preAngle, endAngle, true, mPaint);


//        dealPoint(rectF, preAngle, endAngle, pointList);
        if (isShowRate) {
            drawArcCenterPoint(canvas, position);
        }
        preAngle = preAngle + endAngle;
    }

    private void drawCenterPoint(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.color_Red));
//        Log.e("中心点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(1), mPaint);
    }

    private void drawInner(Canvas canvas) {
        mPaint.setColor(mRes.getColor(R.color.colorWhite));
//        Log.e("内部圆点:", dip2px(circleCenterX + mPaintWidth * 2 + leftMargin) + " --- " + dip2px(circleCenterY + mPaintWidth * 2 + topMargin));
        canvas.drawCircle(dip2px(circleCenterX + mPaintWidth * 2 + leftMargin), dip2px(circleCenterY + mPaintWidth * 2 + topMargin), dip2px(ringInnerRidus), mPaint);
    }

    private float preRate;

    private void drawArcCenterPoint(Canvas canvas, int position) {


        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRes.getColor(R.color.color_colorTransparent));
        mPaint.setStrokeWidth(dip2px(1));
        canvas.drawArc(rectFPoint, preAngle, (endAngle) / 2, true, mPaint);
        dealPoint(rectFPoint, preAngle, (endAngle) / 2, pointArcCenterList);
        Point point = pointArcCenterList.get(position);
        mPaint.setColor(mRes.getColor(R.color.color_D8D8D8));
//        canvas.drawCircle(point.x, point.y, dip2px(2), mPaint);

        if (preRate / 2 + rateList.get(position) / 2 < 5) {
            extendLineWidth = 17;
            rate = 0.3f;
        } else {
            extendLineWidth = 17;
            rate = 0.3f;
        }

        rate = 15f / ringPointRidus;

        // 外延画折线
        float lineXPoint1 = (point.x - dip2px(leftMargin + ringOuterRidus)) * (1 + rate);
        float lineYPoint1 = (point.y - dip2px(topMargin + ringOuterRidus)) * (1 + rate);

        float[] floats = new float[8];
        floats[0] = point.x;
        floats[1] = point.y;
        floats[2] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[3] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        floats[4] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1;
        floats[5] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        if (point.x >= dip2px(leftMargin + ringOuterRidus)) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 + dip2px(extendLineWidth);
        } else {
            mPaint.setTextAlign(Paint.Align.RIGHT);
            floats[6] = dip2px(leftMargin + ringOuterRidus) + lineXPoint1 - dip2px(extendLineWidth);
        }
        floats[7] = dip2px(topMargin + ringOuterRidus) + lineYPoint1;
        mPaint.setColor(getResources().getColor(android.R.color.black));
        canvas.drawLines(floats, mPaint);
        mPaint.setTextSize(dip2px(showRateSize));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRes.getColor(R.color.color_Red));
        canvas.drawText(rateList.get(position) + "%", floats[6], floats[7] + dip2px(showRateSize) / 3, mPaint);

        preRate = rateList.get(position);
//        addView(getTextView(rateList.get(position)+""));

    }

    private TextView getTextView(String content) {
        TextView textView = new TextView(mContext);
        textView.setTextColor(getResources().getColor(R.color.colorWhite));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        textView.setBackgroundColor(getResources().getColor(R.color.color_4C4C4C));
        textView.setMaxLines(1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        if ((textView.getLayoutParams()) instanceof LinearLayout.LayoutParams) {

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(dip2px(7), dip2px(4), dip2px(7), dip2px(4));
            textView.setLayoutParams(layoutParams);
        } else if ((textView.getLayoutParams()) instanceof RelativeLayout.LayoutParams) {

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(dip2px(7), dip2px(4), dip2px(7), dip2px(4));
            textView.setLayoutParams(layoutParams);
        }
        textView.setText(content);

        return textView;
    }


    private void dealPoint(RectF rectF, float startAngle, float endAngle, List<Point> pointList) {
        Path orbit = new Path();
        //通过Path类画一个90度（180—270）的内切圆弧路径
        orbit.addArc(rectF, startAngle, endAngle);

        PathMeasure measure = new PathMeasure(orbit, false);
        Log.e("路径的测量长度:", "" + measure.getLength());

        float[] coords = new float[]{0f, 0f};
        //利用PathMeasure分别测量出各个点的坐标值coords
        int divisor = 1;
        measure.getPosTan(measure.getLength() / divisor, coords, null);
        Log.e("coords:", "x轴:" + coords[0] + " -- y轴:" + coords[1]);
        float x = coords[0];
        float y = coords[1];
        Point point = new Point(Math.round(x), Math.round(y));
        pointList.add(point);
    }


    private void runShowOutAnim() {
        clearAnimation();
        mAnimMode = ANIM_MODE_SHOW_OUT;
        startAnimation(mAnimShowOut);
    }

    private void growItem(int item) {
        mGrownItem = item;
        runGrowAnim();
    }

    private void runGrowAnim() {
        mAnimMode = ANIM_MODE_GROW;
        clearAnimation();
        startAnimation(mAnimGrow);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int item = calcClickItem(event.getX(), event.getY());
            if (item >= 0 && item < rateList.size()) {
                setCurrentItem(item, true);
            }
        }

        return super.onTouchEvent(event);
    }

    private void setCurrentItem(int item, boolean smartRotate) {
        Toast.makeText(mContext, item + "//" + rateList.get(item) + "%", Toast.LENGTH_SHORT).show();
        if (mCurrentItem != item) {
            mCurrentItem = item;
//            rotateToDegree(calcCenter(item), smartRotate);
//            growItem(item);
        } else {
//            growItem(item);
        }
        mGrownItem=item;
        mAnimMode = ANIM_MODE_FINISHED;
        invalidate();

    }

    private int calcClickItem(float x, float y) {
        if (rateList == null) return -1;
        final float centerX = rectF.centerX();
        final float centerY = rectF.centerY();
        float outerRadius = rectF.width() / 2;
        float innerRadius = 80;

        // 计算点击的坐标(x, y)和圆中心点形成的角度，角度从0-360，顺时针增加
        int clickedDegree = GeomTool.calcAngle(x, y, centerX, centerY);
        double clickRadius = GeomTool.calcDistance(x, y, centerX, centerY);


        if (clickRadius < innerRadius) {
            // 点击发生在小圆内部，也就是点击到标题区域
//            return -1;
        } else if (clickRadius > outerRadius) {
            // 点击发生在大圆环外
            return -2;
        }

        // 计算出来的clickedDegree是整个View原始的，被点击item需要考虑startAngle。
        int startAngle = -90;
        int angleStart = startAngle;
        for (int i = 0; i < angles.length; i++) {
            int itemStart = (angleStart + 360) % 360;
            float end = itemStart + angles[i];
            if (end >= 360f) {
                if (clickedDegree >= itemStart && clickedDegree < 360) return i;
                if (clickedDegree >= 0 && clickedDegree < (end - 360)) return i;
            } else {
                if (clickedDegree >= itemStart && clickedDegree < end) {
                    return i;
                }
            }

            angleStart += angles[i];
        }

        return -3;
    }


    private ItemChangeListener mItemChangeListener;

    public interface ItemChangeListener {
        void onItemSelected();
    }

    public void setItemChangeListener(ItemChangeListener listener) {
        mItemChangeListener = listener;
    }


    private float preAngle = -90;
    private float endAngle = -90;

    /**
     * @param percent 百分比
     * @return
     */
    private float getAngle(float percent) {
        float a = 360f / 100f * percent;
        return a;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int px2dip(float pxValue) {
        return (int) (pxValue / dm.density + 0.5f);
    }


}
