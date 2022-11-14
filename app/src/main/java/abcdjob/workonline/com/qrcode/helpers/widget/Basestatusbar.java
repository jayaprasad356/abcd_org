package abcdjob.workonline.com.qrcode.helpers.widget;


import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

public class Basestatusbar extends View {
    private int mStatusBarHeight;

    public Basestatusbar(Context context) {
        this(context, null);
    }

    public Basestatusbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        mStatusBarHeight = dpToPx();
        return insets.consumeSystemWindowInsets();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mStatusBarHeight);
    }

    private int dpToPx() {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round((float) 24.0 * density);
    }
}
