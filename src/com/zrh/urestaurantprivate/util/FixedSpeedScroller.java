package com.zrh.urestaurantprivate.util;
import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;
/**
 * @copyright 中荣恒科技有限公司
 * @function 滑动速度
 * @author 吴强
 * @version v1.1
 * @date 2015-02-06
 */
public class FixedSpeedScroller extends Scroller {  
    private int mDuration = 0;  
  
    public FixedSpeedScroller(Context context) {  
        super(context);  
    }  
  
    public FixedSpeedScroller(Context context, Interpolator interpolator) {  
        super(context, interpolator);  
    }  
  
    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {  
        super(context, interpolator, flywheel);  
    }  
  
  
    @Override  
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {  
        super.startScroll(startX, startY, dx, dy, mDuration);  
    }  
  
    @Override  
    public void startScroll(int startX, int startY, int dx, int dy) {  
        super.startScroll(startX, startY, dx, dy, mDuration);  
    }  
}