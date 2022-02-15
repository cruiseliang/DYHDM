package com.yuan.dyhdm.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yuan.dyhdm.R;
import com.yuan.dyhdm.entity.PieSerie;
import com.yuan.dyhdm.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PieGraphViewSec extends LinearLayout {
    private AnimaListener animaListener;

    public interface AnimaListener {
        void animaStart();

        void animaRepeat();

        void animaEnd();
    }

    public void setAnimaListener(AnimaListener animaListener) {
        this.animaListener = animaListener;
    }

    private class PieGraphView extends View {

        private float minLength;
        private float radius;

        public PieGraphView(Context context) {
            super(context);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            pieWidth = getWidth();
            height = getHeight();
            minLength = height > pieWidth ? pieWidth : height;
            radius = (minLength / 2f) / 1.2f - ringWidth / 2;
            centerX = pieWidth / 2f;
            centerY = height / 2f;
            float total = 0;

            paint.setColor(Color.BLACK);
            paint.setTextSize(titleSize);
            paint.setTextAlign(Align.CENTER);
            sp = 3 * (minLength / 2f) / 11f;
            float desHeight = series.size() * (desSize + desSpacing);
            float topY = centerY - desHeight / 2;
            canvas.drawText(leftupString, centerX, topY - screen_density * 5, paint);

            oval.left = centerX - radius;
            oval.top = centerY - radius;
            oval.right = centerX + radius;
            oval.bottom = centerY + radius;

            float nextStartAngle = 0;

            getClickedSerie(series);

            for (PieSerie pieSerie : series) {
                if (pieSerie.getNum() < 0) {
                    continue;
                }
                total += pieSerie.getNum();
            }

            if(total==0){
                return;
            }
            int j = 0;
            for (int i = 0; i < series.size(); i++) {
                PieSerie serie = series.get(i);
                serie.setStartAngle(nextStartAngle);

                if (serie.getNum() < 0) {
                    continue;
                }
                float sweepAngle = (float) (serie.getNum() / total * 360);
                if (handlerSweepAngle >= nextStartAngle) {
                    anglePaint.setColor(colors[j]);
                    anglePaint.setAntiAlias(true);
                    if (ringWidth > 0) {
                        anglePaint.setStyle(Paint.Style.STROKE);
                        anglePaint.setStrokeWidth(ringWidth);
                    }
                    anglePaint.setFilterBitmap(true);
                    if (serie.isClicked()) {
                        if (isClickable) {
                            double rAngle = Math
                                    .toRadians(90 - (serie.getStartAngle() + sweepAngle / 2));
                            float translateX = (float) (radius * 0.1 * Math.sin(rAngle));
                            float translateY = (float) (radius * 0.1 * Math.cos(rAngle));
                            oval.offset(translateX, translateY);
                            canvas.drawArc(oval, serie.getStartAngle(), sweepAngle, ringWidth <= 0, anglePaint);
                            oval.offset(-translateX, -translateY);
                        }
                    } else {
                        canvas.drawArc(oval, serie.getStartAngle(), handlerSweepAngle
                                - nextStartAngle, ringWidth <= 0, anglePaint);
                    }
                    j++;
                }
                serie.setSweepAngle(sweepAngle);
                nextStartAngle = serie.getStartAngle() + sweepAngle;
            }

            // draw disclaimer.
            paint.setColor(Color.GRAY);
            paint.setTextSize(3 * titleSize / 5f);
            canvas.drawText(disclaimer, centerX + disclaimerOffsetX, disclaimerOffsetY + centerY + radius + paint.getTextSize() + 1 * sp
                    / 3, paint);

        }

        public void setSweepAngle(int angle) {
            handlerSweepAngle += angle;
            invalidate();
        }

        private void getClickedSerie(List<PieSerie> series) {
            GraphPoint screenGraphPoint = new GraphPoint(oldX, oldY);
            if (isOnPieChart(screenGraphPoint)) {
                double angle = getAngle(screenGraphPoint);
                for (PieSerie pieSerie : series) {

                    if (pieSerie.getStartAngle() <= angle
                            && angle <= pieSerie.getStartAngle() + pieSerie.getSweepAngle()) {
                        if (pieSerie.isClicked()) {
                            pieSerie.setClicked(false);
                        } else {
                            // pieSerie.setClicked(true);
                        }
                    } else {
                        pieSerie.setClicked(false);
                    }
                }
            }
        }

        private boolean isOnPieChart(GraphPoint screenGraphPoint) {
            // Using a bit of Pythagoras
            // inside circle if (x-center_x)**2 + (y-center_y)**2 <= radius**2:

            double sqValue = (Math.pow(centerX - screenGraphPoint.getX(), 2) + Math.pow(centerY
                    - screenGraphPoint.getY(), 2));

            double radiusSquared = radius * radius;

            return sqValue <= radiusSquared;
        }

        private double getAngle(GraphPoint screenGraphPoint) {
            double dx = screenGraphPoint.getX() - centerX;
            // Minus to correct for coord re-mapping
            double dy = -(screenGraphPoint.getY() - centerY);

            double inRads = Math.atan2(dy, dx);

            // We need to map to coord system when 0 degree is at 3 O'clock, 270
            // at 12
            // O'clock
            if (inRads < 0)
                inRads = Math.abs(inRads);
            else
                inRads = 2 * Math.PI - inRads;

            return Math.toDegrees(inRads);
        }

    }

    private class PieDescriptionView extends View {

        private float leftX;
        private float desHeight;
        private float topY;
        private float radius;
        private TextPaint textPaint;

        public PieDescriptionView(Context context) {
            super(context);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            textPaint = new TextPaint();
            textPaint.setTextSize(desSize);
            textPaint.setColor(desTextColor);
            textPaint.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            leftX = 0;// pieWidth + horSpacing
            // height except title.
            if (isPieRight) {
                centerY = getHeight() / 2;
            }
            float textHeight = paint.descent() - paint.ascent();
            desHeight = series.size() * (Math.abs(textHeight) + desSpacing);
            topY = centerY - desHeight / 2;
            float smallRadius = textHeight * 2f / 5f;
            float marginLeft = smallRadius * 2 + screen_density * 5;
            float marginRight = screen_density * 10;
            float destop;
            if (StringUtils.isNullOrEmpty(title)) {
                destop = topY + textHeight;
            } else {
                destop = topY + textHeight + 20 * screen_density;
            }

            paint.setAntiAlias(true);// 抗锯齿

            // draw right up direct text
            paint.setColor(rightUpTextColor);
            paint.setTextSize(rightupSize);
            paint.setTextAlign(Align.RIGHT);
            paint.setFakeBoldText(false);
            canvas.drawText(rightupString, getWidth() - marginRight, topY, paint);

            // draw title.
            paint.setTextSize(titleSize);
            paint.setColor(titleTextColor);
            paint.setFakeBoldText(false);
            paint.setTextAlign(Align.LEFT);
            canvas.drawText(title, leftX + marginLeft, topY, paint);
            float width_des = getWidth() - marginLeft - screen_density * 15;
            // draw des.
            int j = 0;
            for (int i = 0; i < series.size(); i++) {
                if (series.get(i).getNum() < 0) {
                    paint.setColor(desTextColor);
                    if (series.get(i).isChild()) {
                        paint.setTextSize(desSize - (titleSize - desSize));
                    } else {
                        paint.setTextSize(desSize);
                    }
                    canvas.drawText(series.get(i).getDes(), leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                    if (null != series.get(i).getQuotePrice()) {
                        if (isFromESF) {
                            paint.setColor(titleTextColor);
                            canvas.drawText(series.get(i).getQuotePrice().row_optionHouse,
                                    leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                            canvas.drawText(series.get(i).getQuotePrice().row_half_price,
                                    width_des * 6 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                            canvas.drawText(series.get(i).getQuotePrice().row_percent,
                                    width_des * 13 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                        } else {
                            canvas.drawText(series.get(i).getQuotePrice().row_optionHouse,
                                    leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                            canvas.drawText(series.get(i).getQuotePrice().row_half_price,
                                    width_des * 6 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                            canvas.drawText(series.get(i).getQuotePrice().row_percent,
                                    width_des * 13 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                        }

                    }
                    continue;
                }
                if (series.get(i).isClicked()) {
                    paint.setColor(colors[j]);
                    radius = (desSize / 2f) / 1.2f;
                    canvas.drawCircle(leftX + smallRadius, destop + (textHeight + desSpacing) * i - textHeight / 2f, radius, paint);

                    paint.setColor(desTextColor);
                    paint.setTextSize(desSize);
                    canvas.drawText(series.get(i).getDes(), leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                } else {
                    paint.setColor(colors[j]);
                    canvas.drawCircle(leftX + smallRadius, destop + (textHeight + desSpacing) * i - textHeight / 2f, smallRadius, paint);
                    paint.setColor(desTextColor);
                    paint.setTextSize(desSize);

                    canvas.save();
                    //getWidth()表示绘制多宽后换行
                    StaticLayout sl = new StaticLayout(series.get(i).getDes(), textPaint, (int) width_des, Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);
                    //从0,0开始绘制
                    canvas.translate(leftX + marginLeft, destop + (textHeight + desSpacing) * i - textHeight * 1.3f);
                    sl.draw(canvas);
                    canvas.restore();

//                    canvas.drawText(series.get(i).getDes(), leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                    if (null != series.get(i).getQuotePrice()) {
                        canvas.drawText(series.get(i).getQuotePrice().Name,
                                leftX + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                        if (isFromESF) {
                            canvas.drawText("***",
                                    width_des * 6 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                        } else {
                            canvas.drawText(series.get(i).getQuotePrice().Price,
                                    width_des * 6 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                        }
                        canvas.drawText(series.get(i).getQuotePrice().Percent,
                                width_des * 13 / 20 + marginLeft, destop + (textHeight + desSpacing) * i, paint);
                    }
                }
                j++;
            }

        }
    }

    private Context context;
    private LinearLayout mContainer;
    private Paint paint;
    private Paint anglePaint;
    private int pieWidth;
    private int height;
    private float pieWeight = 1f;
    private float desWeight = 2f;
    private PieGraphView pieGraphView;
    private PieDescriptionView pieDesView;
    private List<PieSerie> series;
    private String title;
    private float screen_density;
    private float screen_scaled_density;
    private float sp;
    private float oldX;
    private float oldY;
    private boolean isClickable = false;
    private float horSpacing;
    private float desSpacing;
    private float desSize;
    private float titleSize;
    private float rightupSize;
    private int titleTextColor;
    private int desTextColor;
    private int rightUpTextColor;
    private float ringWidth;
    private float centerX;
    private float centerY;
    private String leftupString;
    private String rightupString;
    private String disclaimer;
    private float disclaimerOffsetX = 0;
    private float disclaimerOffsetY = 0;
    RectF oval = new RectF();
    int handlerSweepAngle;
    Handler mHandler = new Handler();
    private boolean isPieRight;    // 饼状图是否在右边
    private boolean isFromESF; //是否是二手房使用
    /**
     * 是否已经执行过动画。控制动画只执行一次。
     */
    boolean isRotated;
    private int[] colors = {Color.rgb(96, 171, 240),
            Color.rgb(255, 220, 104),
            Color.rgb(129, 210, 186),
            Color.rgb(245, 133, 171),
            Color.rgb(215, 136, 244),
            Color.rgb(23, 197, 255),
            Color.rgb(255, 160, 114),
            Color.rgb(244, 118, 118),
            Color.TRANSPARENT,
            Color.TRANSPARENT};

    public PieGraphViewSec(Context context) {
        super(context);
        this.context = context;
        isRotated = false;
        isPieRight = false;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_density = displayMetrics.density;
        screen_scaled_density = displayMetrics.scaledDensity;

        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mContainer);

        paint = new Paint();
        anglePaint = new Paint();
    }

    public PieGraphViewSec(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        isRotated = false;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_density = displayMetrics.density;
        screen_scaled_density = displayMetrics.scaledDensity;

        // initialize some variable.
        horSpacing = screen_density * 10f;
        desSpacing = screen_density * 10f;
        titleSize = screen_scaled_density * 16f;
        desSize = screen_scaled_density * 14f;
        rightupSize = screen_scaled_density * 12f;
        titleTextColor =context.getResources().getColor(R.color.color_222222);
        desTextColor = context.getResources().getColor(R.color.color_222222);
        rightUpTextColor =context.getResources().getColor(R.color.blue_04);

        mContainer = new LinearLayout(context);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);
        mContainer.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mContainer);

        paint = new Paint();
        anglePaint = new Paint();
    }

    public void setSeries(List<PieSerie> series, String title) {
        setSeries(series, title, "", "", "");
    }

    private LinearLayout ll_result;

    public void setSeries(List<PieSerie> series, String title, String disclaimer, String rightupString, LinearLayout view, Context mContext) {
        ll_result = view;
        ll_result.removeAllViews();
        setComputeResultItem(View.INVISIBLE, View.GONE, View.VISIBLE, View.VISIBLE, "", title, rightupString, 0, mContext, false);
        int j = 0;
        for (int i = 0; i < series.size(); i++) {
            PieSerie ps = series.get(i);
            boolean ifShowCircle = series.get(i).getNum() >= 0;
            setComputeResultItem(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE, ps.getDes(), "", "", colors[j], mContext, ifShowCircle);
            if (ifShowCircle) {
                j++;
            }
        }
        if (!StringUtils.isNullOrEmpty(disclaimer)) {
            setComputeResultItem(View.INVISIBLE, View.GONE, View.VISIBLE, View.GONE, "", disclaimer, "", 0, mContext, false);
        }
        this.series = new ArrayList<PieSerie>();
        int size = series.size();
        for (int i = 0; i < size; i++) {
            if (null == series.get(i) || null == series.get(i).getDes()) {
                continue;
            }
            this.series.add(series.get(i));
        }
        this.title = title;
        this.disclaimer = "";
        this.rightupString = rightupString;
        this.leftupString = "";
        if (mContainer.getChildCount() != 0) {
            mContainer.removeAllViews();
        }
        pieGraphView = new PieGraphView(context);
        mContainer.addView(pieGraphView, new LayoutParams(0, LayoutParams.MATCH_PARENT, pieWeight));
    }

    private void setComputeResultItem(int b1, int b2, int b3, int b4, String s1, String s2, String s3, int color, Context mContext, Boolean ifShowCircle) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_loan_computer_result_item, null);
        LinearLayout item_color = (LinearLayout) view.findViewById(R.id.item_color);
        TextView item_tv = (TextView) view.findViewById(R.id.item_tv);
        TextView item_tv2 = (TextView) view.findViewById(R.id.item_tv2);
        TextView item_click = (TextView) view.findViewById(R.id.item_click);
        LinearLayout item_circle = (LinearLayout) view.findViewById(R.id.item_circle);
        item_color.setVisibility(b1);
        item_tv.setVisibility(b2);
        item_tv2.setVisibility(b3);
        item_click.setVisibility(b4);
        item_tv.setText(s1);
        item_tv2.setText(s2);
        item_click.setText(s3);
        GradientDrawable myGrad = (GradientDrawable) item_circle.getBackground();
        if (ifShowCircle) {
            myGrad.setColor(color);
        } else {
            myGrad.setColor(0);
        }
        ll_result.addView(view);
    }

    public void setSeries(List<PieSerie> series, String title, String disclaimer,
                          String leftupString, String rightupString) {
        this.series = new ArrayList<PieSerie>();
        int size = series.size();
        for (int i = 0; i < size; i++) {
            if (null == series.get(i) || null == series.get(i).getDes()) {
                continue;
            }
            this.series.add(series.get(i));
        }
        this.title = title;
        this.leftupString = leftupString;
        this.rightupString = rightupString;
        this.disclaimer = disclaimer;

        if (mContainer.getChildCount() != 0) {
            mContainer.removeAllViews();
        }

        if (isPieRight) {
            pieDesView = new PieDescriptionView(context);
            mContainer.addView(pieDesView, new LayoutParams(0, LayoutParams.MATCH_PARENT, desWeight));

            View view = new View(context);
            view.setLayoutParams(new LayoutParams((int) (horSpacing), LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            mContainer.addView(view);

            pieGraphView = new PieGraphView(context);
            mContainer.addView(pieGraphView, new LayoutParams(0, LayoutParams.MATCH_PARENT, pieWeight));

        } else {
            pieGraphView = new PieGraphView(context);
            mContainer.addView(pieGraphView, new LayoutParams(0, LayoutParams.MATCH_PARENT, pieWeight));

            View view = new View(context);
            view.setLayoutParams(new LayoutParams((int) (horSpacing), LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            mContainer.addView(view);

            pieDesView = new PieDescriptionView(context);
            mContainer.addView(pieDesView, new LayoutParams(0, LayoutParams.MATCH_PARENT, desWeight));
        }
    }

    public void setFromESF(boolean isFromESF) {
        this.isFromESF = isFromESF;
    }

    public void ShowAndStartAnimation() {
        if (isRotated) {
            return;
        }
        // 初始化handlerSweepAngle为0，以便重新执行动画。
        handlerSweepAngle = 0;
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(100);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // 设置为true，则动画只执行一次。
                // isRotated = true;
                if (animaListener != null) {
                    animaListener.animaStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (animaListener != null) {
                    animaListener.animaRepeat();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (animaListener != null) {
                    animaListener.animaEnd();
                }
                postAnimation();
            }
        });
        pieGraphView.startAnimation(animation);
    }

    private void postAnimation() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                if (handlerSweepAngle < 360) {
                    pieGraphView.setSweepAngle(6);
                    mHandler.post(this);
                }
            }
        };
        mHandler.post(r);
    }

    public void setIsClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // save the x and y so they can be used in the click and long press
            // listeners
            oldX = event.getX();
            oldY = event.getY();
            // if (isClickable) {
            // pieGraphView.invalidate();
            // pieDesView.invalidate();
            // }
        }
        return super.onTouchEvent(event);
    }

    // setter.

    /**
     * default 30.
     *
     * @param spacing
     */
    public void setSpacingWidth(int spacing) {
        this.horSpacing = screen_density * spacing;
    }

    /**
     * default 20.
     *
     * @param spacing
     */
    public void setDesSpacingHeight(int spacing) {
        this.desSpacing = screen_density * spacing;
    }

    /**
     * default 50.
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(float titleTextSize) {
        this.titleSize = screen_scaled_density * titleTextSize;
    }

    /**
     * default 40.
     *
     * @param descriptionTextSize
     */
    public void setDescriptionTextSize(float descriptionTextSize) {
        this.desSize = screen_scaled_density * descriptionTextSize;
    }


    /**
     * 设置要绘制字体的大小
     *
     * @param titleTextSize
     * @param desTextSize
     * @param rightUpSize
     */
    public void setRightTextSize(float titleTextSize, float desTextSize, float rightUpSize) {
        this.titleSize = screen_scaled_density * titleTextSize;
        this.desSize = screen_scaled_density * desTextSize;
        this.rightupSize = screen_scaled_density * rightUpSize;
    }

    /**
     * 设置绘制字体的颜色
     *
     * @param titleTextColor
     * @param desTextColor
     * @param rightUpColor
     */
    public void setRightTextColor(int titleTextColor, int desTextColor, int rightUpColor) {
        this.titleTextColor = titleTextColor;
        this.desTextColor = desTextColor;
        this.rightUpTextColor = rightUpColor;
    }

    /**
     * 设置圆环宽度
     *
     * @param ringWidth 若未设置具体宽度，就默认画饼图
     */
    public void setRingWidth(float ringWidth) {
        this.ringWidth = screen_density * ringWidth;
    }

    // getter.
    public float getTitleTextSize() {
        return this.titleSize;
    }

    public float getDescriptionTextSize() {
        return this.desSize;
    }

    // 如果饼状图在右侧，则在该自定义控件中添加一flag，在初始化View的时候，注意摆放位置
    // 并且要注意第三种颜色的色值
    public void setIsPieRight(boolean isPieRight) {
        this.isPieRight = isPieRight;
        if (null != colors && colors.length >= 3) {
            colors[2] = Color.rgb(249, 137, 33);
        }
    }

    /**
     * 设置disclaimer的偏移量
     *
     * @param x offsetx
     * @param y offsety
     */
    public void setDisclaimerOffsetXY(float x, float y) {
        this.disclaimerOffsetX = x;
        this.disclaimerOffsetY = y;
    }
}
