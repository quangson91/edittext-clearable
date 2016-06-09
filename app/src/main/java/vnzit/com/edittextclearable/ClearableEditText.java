package vnzit.com.edittextclearable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sh on 6/9/16.
 */
public class ClearableEditText extends EditText {


    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public interface Callback {
        void beforeClear(EditText editText);

        void afterClear(EditText editText);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (isInEditMode()) return;
        updateDeleteIcon(isFocused());

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateDeleteIcon(s.toString(), isFocused());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final Drawable rightDrawable = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (rightDrawable != null && event.getRawX() >= (getRight() - rightDrawable.getBounds().width())) {
                        if (callback != null) callback.beforeClear(ClearableEditText.this);
                        setText("");
                        requestFocus();
                        if (callback != null) callback.afterClear(ClearableEditText.this);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void updateDeleteIcon(boolean focused) {
        updateDeleteIcon(null, focused);
    }

    private void updateDeleteIcon(final String text, final boolean focused) {
        final String currentText = (text != null) ? text : getText().toString();
        post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(currentText) || !focused) {
                    setCompoundDrawables(null, null, null, null);
                } else {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_close_black_24dp, 0);
                }
            }
        });
    }

}
