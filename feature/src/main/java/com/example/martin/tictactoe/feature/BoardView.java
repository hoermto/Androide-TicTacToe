package com.example.martin.tictactoe.feature;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class BoardView extends View {

    private static final int LINE_THICK = 5;
    private static final int ELT_MARGIN = 20;
    private static final int ELT_STROKE_WIDTH = 15;
    private int width, height, eltW, eltH;
    private Paint gridPaint, oPaint, xPaint;
    private GameEngine gameEngine;
    private MainActivity activity;

    private ImageView[] icons_o;
    private ImageView[] icons_x;


    public BoardView(Context context) {
        super(context);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        gridPaint = new Paint();
        oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        oPaint.setColor(Color.RED);
        oPaint.setStyle(Paint.Style.STROKE);
        oPaint.setStrokeWidth(ELT_STROKE_WIDTH);
        xPaint = new Paint(oPaint);
        xPaint.setColor(Color.BLUE);
    }

    public void setMainActivity(MainActivity a) {
        activity = a;

        // initialize array of images
        icons_o = new ImageView[9];
        icons_x = new ImageView[9];
        icons_o[0] = (ImageView) activity.findViewById(R.id.p_o1);
        icons_o[1] = (ImageView) activity.findViewById(R.id.p_o2);
        icons_o[2] = (ImageView) activity.findViewById(R.id.p_o3);
        icons_o[3] = (ImageView) activity.findViewById(R.id.p_o4);
        icons_o[4] = (ImageView) activity.findViewById(R.id.p_o5);
        icons_o[5] = (ImageView) activity.findViewById(R.id.p_o6);
        icons_o[6] = (ImageView) activity.findViewById(R.id.p_o7);
        icons_o[7] = (ImageView) activity.findViewById(R.id.p_o8);
        icons_o[8] = (ImageView) activity.findViewById(R.id.p_o9);

        icons_x[0] = (ImageView) activity.findViewById(R.id.p_x1);
        icons_x[1] = (ImageView) activity.findViewById(R.id.p_x2);
        icons_x[2] = (ImageView) activity.findViewById(R.id.p_x3);
        icons_x[3] = (ImageView) activity.findViewById(R.id.p_x4);
        icons_x[4] = (ImageView) activity.findViewById(R.id.p_x5);
        icons_x[5] = (ImageView) activity.findViewById(R.id.p_x6);
        icons_x[6] = (ImageView) activity.findViewById(R.id.p_x7);
        icons_x[7] = (ImageView) activity.findViewById(R.id.p_x8);
        icons_x[8] = (ImageView) activity.findViewById(R.id.p_x9);
    }

    public void setGameEngine(GameEngine g) {
        gameEngine = g;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        height = View.MeasureSpec.getSize(heightMeasureSpec);
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        eltW = (width - LINE_THICK) / 3;
        eltH = (height - LINE_THICK) / 3;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        drawBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!gameEngine.isEnded() && event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) (event.getX() / eltW);
            int y = (int) (event.getY() / eltH);
            char win = gameEngine.play(x, y);
            invalidate();

            if( win == 'F' ) {
                activity.gameError();
            } else if (win != ' ') {
                activity.gameEnded(win);
            } else {
                // computer plays ...
                win = gameEngine.computer();
                invalidate();

                if (win != ' ') {
                    activity.gameEnded(win);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void drawBoard(Canvas canvas) {

        // set positions and size
        for( int x = 0; x <= 2; x++ ) {
            for( int y = 0; y <= 2; y++ ) {

                icons_o[x*3+y].getLayoutParams().height = (height - ELT_MARGIN) / 3;
                icons_o[x*3+y].getLayoutParams().width = (width - ELT_MARGIN) / 3;

                icons_x[x*3+y].getLayoutParams().height = (height - ELT_MARGIN) / 3;
                icons_x[x*3+y].getLayoutParams().width = (width - ELT_MARGIN) / 3;

                icons_o[x*3+y].setX((width/3)*x + LINE_THICK);
                icons_o[x*3+y].setY((height/3)*y + LINE_THICK);

                icons_x[x*3+y].setX((width/3)*x + LINE_THICK);
                icons_x[x*3+y].setY((height/3)*y + LINE_THICK);

                icons_o[x*3+y].setVisibility(View.INVISIBLE);
                icons_x[x*3+y].setVisibility(View.INVISIBLE);

            }
        }
        for (int x = 0; x < 3; y++) {
            for (int y = 0; y < 3; y++) {
                drawElt(canvas, gameEngine.getField(x, y), x, y);
            }
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int i = 0; i < 2; i++) {
            // vertical lines
            float left = eltW * (i + 1);
            float right = left + LINE_THICK;
            float top = 0;
            float bottom = height;

            canvas.drawRect(left, top, right, bottom, gridPaint);

            // horizontal lines
            float left2 = 0;
            float right2 = width;
            float top2 = eltH * (i + 1);
            float bottom2 = top2 + LINE_THICK;

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint);
        }
    }

    private void drawElt(Canvas canvas, char c, int x, int y) {
        if (c == 'O') {
            icons_o[x*3+y].setVisibility(View.VISIBLE);
        } else if (c == 'X') {
            icons_x[x*3+y].setVisibility(View.VISIBLE);
        }
    }

}
