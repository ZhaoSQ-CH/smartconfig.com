package example.demosmartconfig.package_tools_javaClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${sunJie} on 2018/12/3.
 */

public class Sj_RoundIcon extends View
{
    Bitmap bitmap ;

    public Sj_RoundIcon(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setIcon(Bitmap bitmap)
    {
        this.bitmap = bitmap ;
        invalidate();
    }

    public void setIcon(Drawable drawable)
    {
        if(drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
            setIcon(bitmapDrawable.getBitmap());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取View控件的相关参数
        int w = getWidth();
        int h = getHeight();
        int dd = w<h?w:h ;

        float cx = w/2 ;
        float cy = h/2 ;
        float radius = dd/2 ;

        //圆形边框
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0XFF000000);
//        paint.setStrokeWidth(1f);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx,cy,radius,paint);

        if(bitmap != null)
        {
            //设置clip区域
            Path path = new Path();
            path.addCircle(cx,cy,radius, Path.Direction.CCW);
            canvas.clipPath(path);

            //画Circle
            Rect srcRect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
            Rect dscRect = new Rect(0,0,w,h);
            canvas.drawBitmap(bitmap,srcRect,dscRect,new Paint());

        }
    }
}
