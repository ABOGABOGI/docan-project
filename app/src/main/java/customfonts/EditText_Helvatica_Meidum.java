package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class EditText_Helvatica_Meidum extends TextView {

    public EditText_Helvatica_Meidum(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_Helvatica_Meidum(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Helvatica_Meidum(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/OpenSans-Bold.ttf");
            setTypeface(tf);
        }
    }

}