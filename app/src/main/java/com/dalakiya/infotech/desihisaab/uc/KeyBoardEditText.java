package com.dalakiya.infotech.desihisaab.uc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class KeyBoardEditText extends AppCompatEditText {
    private Listener listener = null;

    public KeyBoardEditText(@NonNull Context context) {
        super(context);
    }

    public KeyBoardEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if(listener != null) {
                listener.onImeBack();
            }
        }
        return super.onKeyPreIme(keyCode, event);
    }

    public void setKeyBoardBackListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onImeBack();
    }
}
