package com.example.android.myapplication;

        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.util.AttributeSet;
        import android.view.MotionEvent;
        import android.view.View;

        import java.util.ArrayList;
        import java.util.List;

public class CanvasView extends View
{

    int POINT_RADIUS = 1;
    int CIRCLE_COLOR = 0xE0E0E0FF;
    int POINT_COLOR  = 0xFF000000;

    public Circle canvasCircle;
    public List<Point> canvasPoints = new ArrayList<>();
    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    private Paint mPaint;
    private float mX, mY;

    public CanvasView(Context c, AttributeSet attrs)
    {
        super(c, attrs);
        context = c;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    public void randomPoints(double q)
    {
        double scale = Math.min(mCanvas.getWidth(), mCanvas.getHeight());
        canvasPoints.clear();
        for (int i = 0; i < q; i++) {
            Point r = randomGaussianPair();
            r.x = r.x * scale * 0.7 + mCanvas.getWidth() / 2;
            r.y = r.y * scale * 0.7 + mCanvas.getHeight() / 2;
            canvasPoints.add(r);
        }
        refreshCanvasCircle();
    }


    public void clearCanvas()
    {
        canvasCircle = null;
        canvasPoints.clear();
        invalidate();
    }

    public void refreshCanvasCircle()
    {
        // Recompute circle
        canvasCircle = SmallestCircle.makeCircle(canvasPoints);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        mX = event.getX();
        mY = event.getY();
        Point e = new Point(mX, mY);

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                canvasPoints.add(e);
                refreshCanvasCircle();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvasCircle != null){
            mPaint.setColor(CIRCLE_COLOR);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle((float) canvasCircle.c.x, (float) canvasCircle.c.y, (float) canvasCircle.r, mPaint);
        }
        for (int i = 0; i < canvasPoints.size(); ++i) {
            mPaint.setColor(POINT_COLOR);
            canvas.drawCircle((float) canvasPoints.get(i).x, (float) canvasPoints.get(i).y, POINT_RADIUS, mPaint);
        }
    }


    public Point randomGaussianPair() {
        double x, y, magsqr;
        do {
            x = Math.random() * 2 - 1;
            y = Math.random() * 2 - 1;
            magsqr = x * x + y * y;
        } while (magsqr >= 1 || magsqr == 0);
        // Box-Muller transform
        double temp = Math.sqrt(-2 * Math.log(magsqr) / magsqr);
        Point p = new Point(x, y);
        return p;
    }
}
